package com.youran.generate.pojo.vo;

import java.util.Objects;

/**
 * <p>Title: 前端进度条VO</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/1/28
 */
public class ProgressVO {

    // 进行中
    public static final int PROGRESSING = 1;
    // 完成
    public static final int DONE = 2;
    // 异常
    public static final int ERROR = 3;

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

    public static ProgressVO initProgress(String sessionId){
        ProgressVO vo = new ProgressVO();
        vo.setStatus(PROGRESSING);
        vo.setPercentage(0);
        vo.setSessionId(sessionId);
        threadLocal.set(vo);
        return vo;
    }


    public static ProgressVO progressing(int addPercent,String msg){
        ProgressVO vo = threadLocal.get();
        if(vo==null){
            throw new RuntimeException("进度条VO未初始化");
        }
        // 如果不是进行中，则直接返回
        if(!Objects.equals(vo.getStatus(),PROGRESSING)){
            return vo;
        }
        // 进度增长过程中，进度值不能超过99%
        int currentPercent = vo.getPercentage()+addPercent;
        if(currentPercent>=99){
            currentPercent = 99;
        }
        vo.setPercentage(currentPercent);
        if(msg!=null) {
            vo.setMsg(msg);
        }
        return vo;
    }




    public static ProgressVO done(String msg){
        ProgressVO vo = threadLocal.get();
        if(vo==null){
            throw new RuntimeException("进度条VO未初始化");
        }
        vo.setStatus(DONE);
        vo.setPercentage(100);
        vo.setMsg(msg);
        return vo;
    }


    public static ProgressVO error(String msg){
        ProgressVO vo = threadLocal.get();
        if(vo==null){
            throw new RuntimeException("进度条VO未初始化");
        }
        vo.setStatus(ERROR);
        vo.setPercentage(-1);
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

}
