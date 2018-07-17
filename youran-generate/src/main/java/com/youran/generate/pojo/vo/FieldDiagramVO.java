package com.youran.generate.pojo.vo;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author cbb
 * @date 2018/7/17
 */
public class FieldDiagramVO {

    public static final String PRIMARY_KEY = "pk";
    public static final String FOREIGN_KEY = "fk";
    public static final String DELETE = "delete";
    public static final String VERSION = "version";

    private String name;
    private String type;


    public FieldDiagramVO() {
    }

    public FieldDiagramVO(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
