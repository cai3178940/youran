package com.youran.generate.util;

import com.google.common.base.Joiner;
import com.granveaud.mysql2h2converter.SQLParserManager;
import com.granveaud.mysql2h2converter.sql.*;
import com.youran.common.util.TempDirUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p>Title:H2数据库工具类</p>
 * <p>Description:</p>
 * @author: june
 * @author: cbb
 * @date: 2019/9/24
 */
public class H2Util {

    public static void main(String[] args) {
        getH2Script("DB/xx.sql");
    }

    /**
     * 修改数据库脚本并保存到临时目录
     * @param pathInClass 文件在classpath下路径
     * @return 临时文件路径
     */
    public static String getH2Script(String pathInClass) {
        String tempFolder =  TempDirUtil.getTmpDir(null,true,false);
        String path = tempFolder+File.separator+pathInClass;
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            ClassLoader classLoader = H2Util.class.getClassLoader();
            URL resource = classLoader.getResource(pathInClass);
            assert resource != null;
            InputStream inputStream = resource.openStream();
            StringBuilder h2Sql = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            Iterator<SqlStatement> sourceIterator = SQLParserManager.parseScript(reader);

            // conversion and execution
            Iterator<SqlStatement> it = H2Converter.convertScript(sourceIterator);
            while (it.hasNext()) {
                SqlStatement st = it.next();
                h2Sql.append(st.toString()).append(";");
            }
            FileUtils.write(file, h2Sql.toString(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    static class H2Converter {
        final static private Logger LOGGER = LoggerFactory.getLogger(H2Converter.class);

        static Iterator<SqlStatement> convertScript(Iterator<SqlStatement> sourceIterator) {
            return new ConverterIterator(sourceIterator);
        }

        static class ConverterIterator implements Iterator<SqlStatement> {
            private Map<String, Integer> indexNameOccurrences = new HashMap<>();
            private List<SqlStatement> delayedStatements = new ArrayList<>();

            private Iterator<SqlStatement> sourceIterator;
            private List<SqlStatement> nextStatements = new ArrayList<>();
            private Iterator<SqlStatement> nextStatementIterator, delayedStatementsIterator;

            ConverterIterator(Iterator<SqlStatement> sourceIterator) {
                this.sourceIterator = sourceIterator;
                loadNextStatements();
            }

            private void loadNextStatements() {
                while (true) {
                    SqlStatement sourceNextStatement = sourceIterator.next();
                    if (sourceNextStatement == null) {
                        // finished source statements
                        nextStatementIterator = null;
                        if (!delayedStatements.isEmpty()) {
                            // now iterate on delayed statements
                            delayedStatementsIterator = delayedStatements.iterator();
                        }
                        return;
                    }
                    nextStatements.clear();
                    convertStatement(sourceNextStatement, nextStatements);

                    if (!nextStatements.isEmpty()) {
                        nextStatementIterator = nextStatements.iterator();
                        return;
                    }
                }
            }

            @Override
            public boolean hasNext() {
                return (nextStatementIterator != null) || (delayedStatementsIterator != null && delayedStatementsIterator.hasNext());
            }

            @Override
            public SqlStatement next() {
                // iterator is on delayed statements
                if (delayedStatementsIterator != null) {
                    return delayedStatementsIterator.next();
                }

                // iterator is on conversion of source iterator
                SqlStatement next = nextStatementIterator.next();
                if (!nextStatementIterator.hasNext()) {
                    loadNextStatements();
                }

                return next;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private void convertStatement(SqlStatement sourceStatement, List<SqlStatement> result) {
                if (sourceStatement instanceof EmptyStatement) {
                    // ignore empty statements
                } else if (sourceStatement instanceof LockTablesStatement || sourceStatement instanceof UnlockTablesStatement) {
                    // do not copy, MySQL specific
                } else if (sourceStatement instanceof SetVariableStatement) {
                    // do not copy, SET statement are usually MySQL specific
                } else if (sourceStatement instanceof StartTransactionStatement) {
                    // replace with H2 equivalent
                    result.add(new SetStatement("AUTOCOMMIT", Collections.singletonList(new ExpressionValue("OFF"))));
                } else if (sourceStatement instanceof CommitTransactionStatement) {
                    // replace with H2 equivalent
                    result.add(new CommitTransactionStatement());
                    result.add(new SetStatement("AUTOCOMMIT", Collections.singletonList(new ExpressionValue("ON"))));
                } else if (sourceStatement instanceof UseStatement) {
                    // USE dbName => SET SCHEMA dbName
                    UseStatement useStatement = (UseStatement) sourceStatement;
                    result.add(new SetStatement("SCHEMA", Collections.singletonList(new StringValue(useStatement.getDbName()))));

                } else if (sourceStatement instanceof CreateDatabaseStatement) {
                    // CREATE DATABASE => CREATE SCHEMA
                    CreateDatabaseStatement createStatement = (CreateDatabaseStatement) sourceStatement;
                    result.add(new CreateSchemaStatement(createStatement.getDbName(), createStatement.isIfNotExists()));

                } else if (sourceStatement instanceof CreateTableStatement) {
                    CreateTableStatement createStatement = (CreateTableStatement) sourceStatement;

                    // ignore MySQL create table specific options
                    createStatement.setOptions(null);

                    // handle duplicate index names in KEY and index names conflicting with reserved keywords
                    handleCreateTableIndexNames(createStatement, indexNameOccurrences);

                    // handle CHARACTER SET and COLLATION column definition, remove ON UPDATE
                    handleCreateTableColumnDefinitions(createStatement);

                    // handle KEY (colName(length)): length cannot be specified with H2
                    handleCreateTableKeyColumnNameLength(createStatement);

                    // 1) handle when foreign key check is disabled: add foreign key constraints at the end
                    // 2) remove USING indexType
                    handleCreateTableConstraints(createStatement, delayedStatements);

                    result.add(sourceStatement);

                } else if (sourceStatement instanceof InsertStatement) {
                    InsertStatement insertStatement = (InsertStatement) sourceStatement;

                    // handle invalid '0000-00-00 00:00:00' datetime and binary values
                    if (insertStatement.getValues() != null) {
                        handleInsertValues(insertStatement);
                    }

                    result.add(sourceStatement);

                } else {
                    // general case: add statement unchanged
                    result.add(sourceStatement);
                }
            }

            private void handleCreateTableIndexNames(CreateTableStatement createStatement, Map<String, Integer> indexNameOccurrences) {
                for (ColumnConstraint constraint : createStatement.getDefinition().getConstraints()) {
                    if (constraint.getIndexName() != null) {
                        String indexName = DbUtils.unescapeDbObjectName(constraint.getIndexName()).toUpperCase();

                        // replace with unique name
                        Integer occurrence = indexNameOccurrences.getOrDefault(indexName, 0);
                        constraint.setIndexName("`" + indexName + "_" + occurrence + "`");

                        // increment occurrence for next time
                        indexNameOccurrences.put(indexName, occurrence + 1);
                    }
                }
            }

            private void handleCreateTableConstraints(CreateTableStatement createStatement, List<SqlStatement> delayedStatements) {
                Iterator<ColumnConstraint> it = createStatement.getDefinition().getConstraints().iterator();
                while (it.hasNext()) {
                    ColumnConstraint constraint = it.next();

                    if ("FOREIGN KEY".equals(constraint.getType())) {
                        delayedStatements.add(new AlterTableStatement(false, createStatement.getTableName(), Collections.singletonList(new AlterTableSpecification("ADD", constraint))));
                        it.remove();
                    }
                    if ("KEY".equals(constraint.getType())) {
                        //  Translate index types
                        delayedStatements.add(new CreateIndexStatement(false, null, false, constraint.getIndexName(), createStatement.getTableName(), constraint.getIndexColumnNames()));
                        it.remove();
                    }

                    constraint.setIndexType(null);
                }
            }

            private void handleCreateTableColumnDefinitions(CreateTableStatement createTableStatement) {
                for (ColumnDefinition def : createTableStatement.getDefinition().getColumnDefinitions()) {
                    def.getColumnType().setCharsetName(null);
                    def.getColumnType().setCollationName(null);
                    def.setUpdateValue(null);
                }
            }

            private void handleCreateTableKeyColumnNameLength(CreateTableStatement createTableStatement) {
                for (ColumnConstraint constraint : createTableStatement.getDefinition().getConstraints()) {
                    for (ColumnName columnName : constraint.getIndexColumnNames()) {
                        if (columnName.getLength() != null) {
                            LOGGER.warn("Remove length value in key/index column name");
                            columnName.setLength(null);
                        }
                    }
                }
            }

            private void handleInsertValues(InsertStatement insertStatement) {
                for (ValueList valueList : insertStatement.getValues()) {
                    for (int i = 0; i < valueList.getValues().size(); i++) {
                        Value value = valueList.getValues().get(i);
                        if (value instanceof StringValue) {
                            StringValue strValue = (StringValue) value;
                            if ("'0000-00-00 00:00:00'".equals(strValue.getValue())) {
                                // replace '0000-00-00 00:00:00' datetime value
                                // this is not correct because '0000-00-00 00:00:00' could be a real string value
                                LOGGER.warn("Replace '0000-00-00 00:00:00' with valid H2 datetime (unsafe replacement)");
                                value = new StringValue("'0001-01-01 00:00:00'");
                            } else if (strValue.getValue().contains("\\")) {
                                // handle \n, \' ...
                                value = DbUtils.transformStringValue(strValue.getValue());
                            }

                            valueList.getValues().set(i, value);

                        } else if (value instanceof BinaryValue) {
                            // be sure to use X'hex' format
                            ((BinaryValue) value).setFormat(BinaryValue.Format.FORMAT1);
                        } else if (value instanceof BitFieldValue) {
                            BitFieldValue bitFieldValue = (BitFieldValue) value;
                            if ("1".equals(bitFieldValue.getBits())) {
                                valueList.getValues().set(i, new BooleanValue(true));
                            } else if ("0".equals(bitFieldValue.getBits())) {
                                valueList.getValues().set(i, new BooleanValue(false));
                            } else {
                                LOGGER.warn("Don't know how to convert BitFieldValue " + bitFieldValue.getBits() + " for H2");
                            }
                        }
                    }
                }
            }
        }
    }

    static class DbUtils {

        static String unescapeDbObjectName(String str) {
            if (str.startsWith("\"") || str.startsWith("`") || str.startsWith("'")) {
                return str.substring(1, str.length() - 1);
            }
            return str;
        }

        static Value transformStringValue(String str) {
            // we just have to transform \' to ''. STRINGDECODE will take care of the rest
            StringBuilder sb = new StringBuilder();
            int i = 1;
            while (i < str.length() - 1) {
                char c = str.charAt(i++);
                if (c == '\\') {
                    if (i < str.length() - 1) {
                        char nextChar = str.charAt(i++);
                        if (nextChar == '\'') {
                            sb.append("''");
                        } else {
                            sb.append(c).append(nextChar);
                        }
                    } else {
                        sb.append(c);
                    }
                } else {
                    sb.append(c);
                }
            }
            return new ExpressionValue("STRINGDECODE('" + sb.toString() + "')");
        }
    }

    static class CreateIndexStatement implements SqlStatement {
        private boolean unique, ifNotExists;
        private String tableName;
        private String indexType;
        private String indexName;
        private List<ColumnName> columnNames;

        CreateIndexStatement(boolean unique, String indexType, boolean ifNotExists, String indexName, String tableName, List<ColumnName> columnNames) {
            this.unique = unique;
            this.indexName = indexName;
            this.indexType = indexType;
            this.ifNotExists = ifNotExists;
            this.tableName = tableName;
            this.columnNames = columnNames;
        }

        @Override
        public String toString() {
            return "CREATE" + (unique ? " UNIQUE" : "") + (indexType != null ? " " + indexType : "") + " INDEX" + (ifNotExists ? " IF NOT EXISTS" : "") +
                    (indexName != null ? " " + indexName : "") +
                    " ON " + tableName + " (" + Joiner.on(',').join(columnNames) + ")"
                    ;
        }
    }
}
