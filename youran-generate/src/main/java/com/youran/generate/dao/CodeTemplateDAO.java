package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.CodeTemplatePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 【代码模板】数据库操作
 *
 * @author cbb
 * @date 2019/10/24
 */
@Repository
@Mapper
public interface CodeTemplateDAO extends DAO<CodeTemplatePO> {

    boolean notUnique(@Param("code") String code,
                      @Param("templateVersion") String templateVersion,
                      @Param("templateId") Integer templateId);

}



