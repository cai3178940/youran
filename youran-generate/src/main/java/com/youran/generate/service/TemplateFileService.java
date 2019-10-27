package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.CodeTemplateDAO;
import com.youran.generate.dao.TemplateFileDAO;
import com.youran.generate.pojo.dto.TemplateFileAddDTO;
import com.youran.generate.pojo.dto.TemplateFileUpdateDTO;
import com.youran.generate.pojo.mapper.TemplateFileMapper;
import com.youran.generate.pojo.po.TemplateFilePO;
import com.youran.generate.pojo.qo.TemplateFileQO;
import com.youran.generate.pojo.vo.TemplateFileListVO;
import com.youran.generate.pojo.vo.TemplateFileShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>Title: 【模板文件】删改查服务</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/10/24
 */
@Service
public class TemplateFileService {

    @Autowired
    private CodeTemplateDAO codeTemplateDAO;
    @Autowired
    private TemplateFileDAO templateFileDAO;


    /**
     * 新增【模板文件】
     * @param templateFileDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public TemplateFilePO save(TemplateFileAddDTO templateFileDTO) {
        TemplateFilePO templateFile = TemplateFileMapper.INSTANCE.fromAddDTO(templateFileDTO);
        if(templateFile.getTemplateId() != null){
            Assert.isTrue(codeTemplateDAO.exist(templateFile.getTemplateId()),"模板id有误");
        }
        templateFileDAO.save(templateFile);
        return templateFile;
    }

    /**
     * 修改【模板文件】
     * @param templateFileUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public TemplateFilePO update(TemplateFileUpdateDTO templateFileUpdateDTO) {
        Integer fileId = templateFileUpdateDTO.getFileId();
        TemplateFilePO templateFile = this.getTemplateFile(fileId, true);
        TemplateFileMapper.INSTANCE.setUpdateDTO(templateFile,templateFileUpdateDTO);
        if(templateFile.getTemplateId() != null){
            Assert.isTrue(codeTemplateDAO.exist(templateFile.getTemplateId()),"模板id有误");
        }
        templateFileDAO.update(templateFile);
        return templateFile;
    }
    /**
     * 查询列表
     * @param templateFileQO
     * @return
     */
    public List<TemplateFileListVO> list(TemplateFileQO templateFileQO) {
        List<TemplateFileListVO> list = templateFileDAO.findListByQuery(templateFileQO);
        return list;
    }

    /**
     * 根据主键获取【模板文件】
     * @param fileId 主键
     * @param force 是否强制获取
     * @return
     */
    public TemplateFilePO getTemplateFile(Integer fileId, boolean force){
        TemplateFilePO templateFile = templateFileDAO.findById(fileId);
        if (force && templateFile == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND);
        }
        return templateFile;
    }


    /**
     * 查询【模板文件】详情
     * @param fileId
     * @return
     */
    public TemplateFileShowVO show(Integer fileId) {
        TemplateFilePO templateFile = this.getTemplateFile(fileId, true);
        TemplateFileShowVO showVO = TemplateFileMapper.INSTANCE.toShowVO(templateFile);
        return showVO;
    }

    /**
     * 删除【模板文件】
     * @param fileIds
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... fileIds) {
        int count = 0;
        for (Integer fileId : fileIds) {
            count += templateFileDAO.delete(fileId);
        }
        return count;
    }


}


