package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.dao.CodeTemplateDAO;
import com.youran.generate.pojo.dto.CodeTemplateAddDTO;
import com.youran.generate.pojo.dto.CodeTemplateUpdateDTO;
import com.youran.generate.pojo.mapper.CodeTemplateMapper;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.pojo.qo.CodeTemplateQO;
import com.youran.generate.pojo.vo.CodeTemplateListVO;
import com.youran.generate.pojo.vo.CodeTemplateShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Title: 【代码模板】删改查服务</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/10/24
 */
@Service
public class CodeTemplateService {

    @Autowired
    private CodeTemplateDAO codeTemplateDAO;

    /**
     * 新增【代码模板】
     * @param codeTemplateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public CodeTemplatePO save(CodeTemplateAddDTO codeTemplateDTO) {
        CodeTemplatePO codeTemplate = CodeTemplateMapper.INSTANCE.fromAddDTO(codeTemplateDTO);
        codeTemplate.setInnerVersion(0);
        codeTemplateDAO.save(codeTemplate);
        return codeTemplate;
    }

    /**
     * 修改【代码模板】
     * @param codeTemplateUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public CodeTemplatePO update(CodeTemplateUpdateDTO codeTemplateUpdateDTO) {
        Integer templateId = codeTemplateUpdateDTO.getTemplateId();
        CodeTemplatePO codeTemplate = this.getCodeTemplate(templateId, true);
        CodeTemplateMapper.INSTANCE.setUpdateDTO(codeTemplate,codeTemplateUpdateDTO);
        codeTemplateDAO.update(codeTemplate);
        return codeTemplate;
    }
    /**
     * 查询分页列表
     * @param codeTemplateQO
     * @return
     */
    public PageVO<CodeTemplateListVO> list(CodeTemplateQO codeTemplateQO) {
        PageVO<CodeTemplateListVO> page = codeTemplateDAO.findByPage(codeTemplateQO);
        return page;
    }

    /**
     * 根据主键获取【代码模板】
     * @param templateId 主键
     * @param force 是否强制获取
     * @return
     */
    public CodeTemplatePO getCodeTemplate(Integer templateId, boolean force){
        CodeTemplatePO codeTemplate = codeTemplateDAO.findById(templateId);
        if (force && codeTemplate == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND);
        }
        return codeTemplate;
    }


    /**
     * 查询【代码模板】详情
     * @param templateId
     * @return
     */
    public CodeTemplateShowVO show(Integer templateId) {
        CodeTemplatePO codeTemplate = this.getCodeTemplate(templateId, true);
        CodeTemplateShowVO showVO = CodeTemplateMapper.INSTANCE.toShowVO(codeTemplate);
        return showVO;
    }

    /**
     * 删除【代码模板】
     * @param templateIds
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... templateIds) {
        int count = 0;
        for (Integer templateId : templateIds) {
            count += codeTemplateDAO.delete(templateId);
        }
        return count;
    }


}


