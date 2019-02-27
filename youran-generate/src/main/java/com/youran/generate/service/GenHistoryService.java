package com.youran.generate.service;

import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.dao.GenHistoryDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.po.GenHistoryPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.qo.GenHistoryQO;
import com.youran.generate.pojo.vo.GenHistoryListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


/**
 * <p>Title:【生成历史】删改查服务</p>
 * <p>Description:</p>
 * Project: bbs
 * @author: cbb
 * @date: 2018-03-17
 */
@Service
public class GenHistoryService {

    @Autowired
    private GenHistoryDAO genHistoryDAO;
    @Autowired
    private GenerateProperties generateProperties;

    /**
     * 新增【生成历史】
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public GenHistoryPO save(MetaProjectPO project,String commit,String branch) {
        GenHistoryPO genHistory = new GenHistoryPO();
        genHistory.setProjectId(project.getProjectId());
        genHistory.setRemoteUrl(project.getRemoteUrl());
        genHistory.setCommit(commit);
        genHistory.setBranch(branch);
        genHistory.setSysVersion(generateProperties.getVersion());
        genHistory.setProjectVersion(project.getProjectVersion());
        genHistoryDAO.save(genHistory);
        return genHistory;
    }

    /**
     * 查询分页列表
     * @param genHistoryQO
     * @return
     */
    public PageVO<GenHistoryListVO> list(GenHistoryQO genHistoryQO) {
        PageVO<GenHistoryListVO> page = genHistoryDAO.findByPage(genHistoryQO);
        return page;
    }


    /**
     * 根据主键获取【生成历史】
     * @param historyId
     * @param force
     * @return
     */
    public GenHistoryPO getGenHistory(Integer historyId, boolean force){
        GenHistoryPO genHistory = genHistoryDAO.findById(historyId);
        if (force && genHistory == null) {
            throw new GenerateException("未查询到记录");
        }
        return genHistory;
    }


    /**
     * 删除【生成历史】
     * @param historyIds
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... historyIds) {
        int count = 0;
        for (Integer historyId : historyIds) {
            count += genHistoryDAO.delete(historyId);
        }
        return count;
    }


    /**
     * 校验提交版本
     * @param project
     * @param genHistory
     */
    public void checkVersion(MetaProjectPO project, GenHistoryPO genHistory) {
        if(Objects.equals(genHistory.getProjectVersion(),project.getProjectVersion())
            && Objects.equals(genHistory.getSysVersion(),generateProperties.getVersion())){
            throw new GenerateException("远程仓库分支【"+genHistory.getBranch()+"】已经是最新版本");
        }
    }
}


