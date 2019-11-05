package com.youran.generate.web.rest;

import com.google.common.collect.Lists;
import com.youran.common.util.JsonUtil;
import com.youran.generate.help.CodeTemplateHelper;
import com.youran.generate.help.TemplateFileHelper;
import com.youran.generate.pojo.dto.TemplateFileAddDTO;
import com.youran.generate.pojo.dto.TemplateFileUpdateDTO;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.pojo.po.TemplateFilePO;
import com.youran.generate.web.AbstractWebTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 【模板文件】单元测试
 *
 * @author cbb
 * @date 2019/10/24
 */
public class TemplateFileControllerTest extends AbstractWebTest {

    @Autowired
    private CodeTemplateHelper codeTemplateHelper;
    @Autowired
    private TemplateFileHelper templateFileHelper;


    /**
     * 新增【模板文件】
     */
    @Test
    public void save() throws Exception {
        CodeTemplatePO codeTemplate = codeTemplateHelper.saveCodeTemplateExample();
        TemplateFileAddDTO addDTO = templateFileHelper.getTemplateFileAddDTO(codeTemplate.getTemplateId());
        restMockMvc.perform(post(getApiPath() + "/template_file")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isCreated());
    }

    /**
     * 修改【模板文件】
     */
    @Test
    public void update() throws Exception {
        CodeTemplatePO codeTemplate = codeTemplateHelper.saveCodeTemplateExample();
        TemplateFilePO templateFile = templateFileHelper.saveTemplateFileExample(codeTemplate.getTemplateId());
        TemplateFileUpdateDTO updateDTO = templateFileHelper.getTemplateFileUpdateDTO(templateFile);
        restMockMvc.perform(put(getApiPath() + "/template_file")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    /**
     * 列表查询【模板文件】
     */
    @Test
    public void list() throws Exception {
        CodeTemplatePO codeTemplate = codeTemplateHelper.saveCodeTemplateExample();
        TemplateFilePO templateFile = templateFileHelper.saveTemplateFileExample(codeTemplate.getTemplateId());
        restMockMvc.perform(get(getApiPath() + "/template_file"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    /**
     * 查看【模板文件】详情
     */
    @Test
    public void show() throws Exception {
        CodeTemplatePO codeTemplate = codeTemplateHelper.saveCodeTemplateExample();
        TemplateFilePO templateFile = templateFileHelper.saveTemplateFileExample(codeTemplate.getTemplateId());
        restMockMvc.perform(get(getApiPath() + "/template_file/{fileId}", templateFile.getFileId()))
            .andExpect(status().isOk());
    }

    /**
     * 删除单个【模板文件】
     */
    @Test
    public void del() throws Exception {
        CodeTemplatePO codeTemplate = codeTemplateHelper.saveCodeTemplateExample();
        TemplateFilePO templateFile = templateFileHelper.saveTemplateFileExample(codeTemplate.getTemplateId());
        restMockMvc.perform(delete(getApiPath() + "/template_file/{fileId}", templateFile.getFileId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }

    /**
     * 批量删除【模板文件】
     */
    @Test
    public void deleteBatch() throws Exception {
        CodeTemplatePO codeTemplate = codeTemplateHelper.saveCodeTemplateExample();
        TemplateFilePO templateFile = templateFileHelper.saveTemplateFileExample(codeTemplate.getTemplateId());
        restMockMvc.perform(delete(getApiPath() + "/template_file")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(templateFile.getFileId()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }


}
