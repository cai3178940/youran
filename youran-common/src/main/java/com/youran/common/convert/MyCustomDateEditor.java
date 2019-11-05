package com.youran.common.convert;

import com.youran.common.util.DateUtil;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * 自定义日期转换
 *
 * @author: cbb
 * @date: 2018/2/23
 */
public class MyCustomDateEditor extends PropertyEditorSupport {


    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            // Treat empty String as null value.
            setValue(null);
        } else {
            setValue(DateUtil.parseDate(text));
        }
    }

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        return DateUtil.getDateStr(value);
    }


}
