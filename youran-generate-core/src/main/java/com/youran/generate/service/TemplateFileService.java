package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.util.Base64Util;
import com.youran.generate.constant.ContextType;
import com.youran.generate.constant.TemplateFileType;
import com.youran.generate.dao.CodeTemplateDAO;
import com.youran.generate.dao.TemplateFileDAO;
import com.youran.generate.pojo.dto.TemplateFileAddDTO;
import com.youran.generate.pojo.dto.TemplateFileContentUpdateDTO;
import com.youran.generate.pojo.dto.TemplateFileUpdateDTO;
import com.youran.generate.pojo.mapper.TemplateFileMapper;
import com.youran.generate.pojo.po.TemplateFilePO;
import com.youran.generate.pojo.qo.TemplateFileQO;
import com.youran.generate.pojo.vo.TemplateFileListVO;
import com.youran.generate.pojo.vo.TemplateFileShowVO;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * 【模板文件】删改查服务
 *
 * @author cbb
 * @date 2019/10/24
 */
@Service
public class TemplateFileService {

    @Autowired
    private CodeTemplateDAO codeTemplateDAO;
    @Autowired
    private TemplateFileDAO templateFileDAO;
    @Autowired
    private CodeTemplateService codeTemplateService;

    /**
     * 校验目录渲染文件是否存在
     *
     * @param templateId
     * @param fileDir
     */
    public void checkDirPathExists(Integer templateId, String fileDir) {
        if (templateFileDAO.dirPathExists(templateId, TemplateFileType.PARENT_PATH.getValue(), fileDir)) {
            throw new BusinessException(ErrorCode.DUPLICATE_KEY,"当前目录下已经存在目录渲染文件");
        }
    }

    /**
     * 校验数据唯一性
     *
     * @param templateFile
     * @param isUpdate     是否更新校验
     */
    private void checkUnique(TemplateFilePO templateFile, boolean isUpdate) {
        if (templateFileDAO.notUnique(templateFile.getTemplateId(), templateFile.getFileDir(), templateFile.getFileName(), isUpdate ? templateFile.getFileId() : null)) {
            throw new BusinessException(ErrorCode.DUPLICATE_KEY);
        }
    }

    /**
     * 新增【模板文件】
     *
     * @param templateFileDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public TemplateFilePO save(TemplateFileAddDTO templateFileDTO) {
        TemplateFilePO templateFile = TemplateFileMapper.INSTANCE.fromAddDTO(templateFileDTO);
        if (templateFile.getTemplateId() != null) {
            Assert.isTrue(codeTemplateDAO.exist(templateFile.getTemplateId()), "模板id有误");
        }
        // 初始化文件内容为空
        templateFile.setContent("");
        templateFile.setFileDir(this.normalizeTemplateFileDir(templateFile.getFileDir()));
        // 非普通文件，则上下文类型一律都是GLOBAL全局
        if(!templateFile.isGeneralFile()){
            templateFile.setContextType(ContextType.GLOBAL.getValue());
        }
        // 唯一性校验
        this.checkUnique(templateFile, false);
        this.doSave(templateFile);
        // 更新模板内部版本号
        codeTemplateService.updateInnerVersion(templateFile.getTemplateId());
        return templateFile;
    }

    /**
     * 新增二进制【模板文件】
     *
     * @param templateId 模板id
     * @param fileDir    文件目录
     * @param filename   文件名
     * @param bytes      字节数组
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public TemplateFilePO saveBinary(Integer templateId, String fileDir, String filename, byte[] bytes) {
        if (templateId != null) {
            Assert.isTrue(codeTemplateDAO.exist(templateId), "模板id有误");
        }
        TemplateFilePO templateFile = new TemplateFilePO();
        templateFile.setFileName(filename);
        templateFile.setFileDir(this.normalizeTemplateFileDir(fileDir));
        templateFile.setTemplateId(templateId);
        templateFile.setContextType(ContextType.GLOBAL.getValue());
        templateFile.setFileType(TemplateFileType.BINARY.getValue());
        templateFile.setContent(Base64Util.encode(bytes));
        // 唯一性校验
        this.checkUnique(templateFile, false);
        this.doSave(templateFile);
        // 更新模板内部版本号
        codeTemplateService.updateInnerVersion(templateFile.getTemplateId());
        return templateFile;
    }

    /**
     * 执行保存
     *
     * @param po
     */
    public void doSave(TemplateFilePO po) {
        templateFileDAO.save(po);
    }

    /**
     * 修改【模板文件】
     *
     * @param templateFileUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public TemplateFilePO update(TemplateFileUpdateDTO templateFileUpdateDTO) {
        Integer fileId = templateFileUpdateDTO.getFileId();
        TemplateFilePO templateFile = this.getTemplateFile(fileId, true);
        TemplateFileMapper.INSTANCE.setUpdateDTO(templateFile, templateFileUpdateDTO);
        if (templateFile.getTemplateId() != null) {
            Assert.isTrue(codeTemplateDAO.exist(templateFile.getTemplateId()), "模板id有误");
        }
        templateFile.setFileDir(this.normalizeTemplateFileDir(templateFile.getFileDir()));
        // 唯一性校验
        this.checkUnique(templateFile, true);
        templateFileDAO.update(templateFile);
        // 更新模板内部版本号
        codeTemplateService.updateInnerVersion(templateFile.getTemplateId());
        return templateFile;
    }

    /**
     * 标准化文件目录
     *
     * @param fileDir
     * @return
     */
    private String normalizeTemplateFileDir(String fileDir) {
        fileDir = StringUtils.trim(fileDir);
        if (StringUtils.isBlank(fileDir)) {
            return "/";
        }
        fileDir = FilenameUtils.normalizeNoEndSeparator(fileDir, true);
        if (fileDir == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "目录不合法");
        }
        fileDir = fileDir.replaceAll("\\/+", "/");
        if (fileDir.endsWith("/")) {
            fileDir = fileDir.substring(0, fileDir.length() - 1);
        }
        if (!fileDir.startsWith("/")) {
            fileDir = "/" + fileDir;
        }
        return fileDir;
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public TemplateFilePO updateContent(TemplateFileContentUpdateDTO dto) {
        Integer fileId = dto.getFileId();
        TemplateFilePO templateFile = this.getTemplateFile(fileId, true);
        if (!Objects.equals(templateFile.getVersion(), dto.getVersion())) {
            throw new BusinessException(ErrorCode.CONFLICT, "文件内容更新冲突");
        }
        templateFile.setContent(dto.getContent());
        templateFileDAO.update(templateFile);
        // 更新模板内部版本号
        codeTemplateService.updateInnerVersion(templateFile.getTemplateId());
        return templateFile;
    }

    /**
     * 查询列表
     *
     * @param templateFileQO
     * @return
     */
    public List<TemplateFileListVO> list(TemplateFileQO templateFileQO) {
        List<TemplateFileListVO> list = templateFileDAO.findListByQuery(templateFileQO);
        return list;
    }

    /**
     * 根据主键获取【模板文件】
     *
     * @param fileId 主键
     * @param force  是否强制获取
     * @return
     */
    public TemplateFilePO getTemplateFile(Integer fileId, boolean force) {
        TemplateFilePO templateFile = templateFileDAO.findById(fileId);
        if (force && templateFile == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND);
        }
        return templateFile;
    }

    /**
     * 查询所有模板文件
     *
     * @param templateId 模板id
     * @return
     */
    public List<TemplateFilePO> getAllTemplateFiles(Integer templateId) {
        return templateFileDAO.findAll(templateId);
    }


    /**
     * 查询【模板文件】详情
     *
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
     *
     * @param fileIds
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... fileIds) {
        int count = 0;
        Integer templateId = null;
        for (Integer fileId : fileIds) {
            if (templateId == null) {
                TemplateFilePO templateFile = getTemplateFile(fileId, true);
                templateId = templateFile.getTemplateId();
            }
            count += templateFileDAO.delete(fileId);
        }
        if (templateId != null) {
            // 更新模板内部版本号
            codeTemplateService.updateInnerVersion(templateId);
        }
        return count;
    }


}


