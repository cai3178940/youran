package com.youran.generate.util;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.JavaKeyword;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 10/26/2019 17:51
 */
public class JfieldNameCheckUtil {

    public static void check(String value){
        if(JavaKeyword.isKeyword(value)){
            throw new BusinessException(ErrorCode.BAD_REQUEST,"字段名“"+value+"”是java关键字");
        }
    }

}
