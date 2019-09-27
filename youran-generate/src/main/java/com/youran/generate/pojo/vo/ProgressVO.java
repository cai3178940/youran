package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * <p>Title: 前端进度条VO</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/1/28
 */
public class ProgressVO extends AbstractVO {

    /**
     * 进行中
     */
    public static final int PROGRESSING = 1;
    /**
     * 完成
     */
    public static final int SUCCESS = 2;
    /**
     * 异常
     */
    public static final int ERROR = 3;
    /**
     * 完成之前不再增长的百分比：99%
     */
    public static final int LAST_PERCENT = 99;

    private static ThreadLocal<ProgressVO> threadLocal = new ThreadLocal<>();

    /**
     * ws会话id
     */
    private String sessionId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 进度百分比
     */
    private Integer percentage;
    /**
     * 进度消息
     */
    private String msg;

    /**
     * 初始化进度条
     * @param sessionId
     * @return
     */
    public static ProgressVO initProgress(String sessionId){
        ProgressVO vo = new ProgressVO();
        vo.setStatus(PROGRESSING);
        vo.setPercentage(0);
        vo.setSessionId(sessionId);
        threadLocal.set(vo);
        return vo;
    }


    /**
     * 进度增加
     * @param addPercent 增加进度
     * @param msg 消息
     * @return
     */
    public static ProgressVO progressing(int minPercent,int maxPercent,int addPercent,String msg){
        Assert.isTrue(maxPercent>=minPercent,"最大百分比必须大于最小百分比");
        ProgressVO vo = threadLocal.get();
        if(vo==null){
            throw new RuntimeException("进度条VO未初始化");
        }
        // 如果不是进行中，则直接返回
        if(!Objects.equals(vo.getStatus(),PROGRESSING)){
            return vo;
        }
        Integer oldPercent = vo.getPercentage();
        int currentPercent = oldPercent +addPercent;
        // 如果当前进度小于最小值，则赋值成下限
        if(currentPercent < minPercent){
            currentPercent = minPercent;
        }
        // 如果之前进度就大于最大值，则保持不变
        if(oldPercent > maxPercent){
            currentPercent = oldPercent;
        }
        // 如果当前进度大于最大值，则赋值成上限
        else if(currentPercent > maxPercent){
            currentPercent = maxPercent;
        }
        // 进度增长过程中，进度值不能超过99%
        if(currentPercent >= LAST_PERCENT){
            currentPercent = LAST_PERCENT;
        }
        vo.setPercentage(currentPercent);
        if(msg!=null) {
            vo.setMsg(msg);
        }
        return vo;
    }



    /**
     * 返回成功
     * @param msg
     * @return
     */
    public static ProgressVO success(String msg){
        return done(msg,SUCCESS,100);
    }

    /**
     * 返回异常
     * @param msg
     * @return
     */
    public static ProgressVO error(String msg){
        return done(msg,ERROR,-1);
    }

    /**
     * 进度条完成
     * @param msg
     * @param status
     * @param percent
     * @return
     */
    private static ProgressVO done(String msg,int status, int percent){
        ProgressVO vo = threadLocal.get();
        threadLocal.remove();
        if(vo==null){
            throw new RuntimeException("进度条VO未初始化");
        }
        vo.setStatus(status);
        vo.setPercentage(percent);
        vo.setMsg(msg);
        return vo;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("sessionId", sessionId)
            .append("status", status)
            .append("percentage", percentage)
            .append("msg", msg)
            .toString();
    }
}
