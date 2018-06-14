package com.youran.common.service;

import com.youran.common.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Title: 抽象Service
 * Description:
 * Author: cbb
 * Create Time: 2018/6/13 18:19
 */
public abstract class AbstractService {

    @Autowired
    protected LoginContext loginContext;

}
