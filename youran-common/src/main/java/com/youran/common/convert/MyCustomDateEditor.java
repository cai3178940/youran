package com.youran.common.convert;

import com.youran.common.util.DateUtil;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * <p>Title:自定义日期装换</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2018/2/23
 */
public class MyCustomDateEditor extends PropertyEditorSupport {


    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            // Treat empty String as null value.
            setValue(null);
        }else {
            setValue(DateUtil.parseDate(text));
        }
    }

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        return DateUtil.getDateStr(value);
    }


}
