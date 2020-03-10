package com.youran.generate.dao;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.TemplateFilePO;
import com.youran.generate.pojo.qo.TemplateFileQO;
import com.youran.generate.pojo.vo.TemplateFileListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 【模板文件】数据库操作
 *
 * @author cbb
 * @date 2019/10/24
 */
@Repository
@Mapper
public interface TemplateFileDAO extends DAO<TemplateFilePO> {

    /**
     * 根据条件查询【模板文件】列表
     *
     * @param templateFileQO
     * @return
     */
    List<TemplateFileListVO> findListByQuery(TemplateFileQO templateFileQO);

    int getCountByTemplateId(Integer templateId);

    boolean notUnique(@Param("templateId") Integer templateId,
                      @Param("fileDir") String fileDir,
                      @Param("fileName") String fileName,
                      @Param("fileId") Integer fileId);

    List<TemplateFilePO> findAll(Integer templateId);


    boolean dirPathExists(@Param("templateId") Integer templateId,
                          @Param("fileType") Integer fileType,
                          @Param("fileDir") String fileDir);
}



