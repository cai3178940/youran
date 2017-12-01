package com.youran.common.pojo.po;

import java.util.Date;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/8/24 17:20
 */
public abstract class AbstractPO{

    public void preInsert(String createBy){
        Date now=new Date();
        if(this instanceof DelSign){
            ((DelSign)this).setDelSign(0);
        }
        if(this instanceof CreateDate){
            ((CreateDate)this).setCreateDate(now);
        }
        if(this instanceof OperateDate){
            ((OperateDate)this).setOperateDate(now);
        }
        if(this instanceof CreateBy){
            ((CreateBy)this).setCreateBy(createBy);
        }
        if(this instanceof OperateBy){
            ((OperateBy)this).setOperateBy(createBy);
        }
        if(this instanceof Version){
            ((Version)this).setVersion(1);
        }
    }

    public void preUpdate(String operateBy){
        if(this instanceof OperateDate){
            ((OperateDate)this).setOperateDate(new Date());
        }
        if(this instanceof OperateBy){
            ((OperateBy)this).setOperateBy(operateBy);
        }
    }
}
