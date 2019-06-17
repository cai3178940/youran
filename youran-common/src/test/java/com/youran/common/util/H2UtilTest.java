package com.youran.common.util;

import org.junit.Test;
import org.springframework.util.Assert;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/6/17
 */
public class H2UtilTest {


    @Test
    public void filterScript() {
        String filter = H2Util.filterScript(") ENGINE = InnoDB DEFAULT CHARSET = utf8 comment ='sku band表，从hive表同步';");
        Assert.doesNotContain(filter,"comment","contain 'comment'");
        Assert.doesNotContain(filter,"band表","contain 'band表'");
        String filter2 = H2Util.filterScript(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='gen_history' ;");
        Assert.doesNotContain(filter2,"COMMENT","contain 'comment'");
        Assert.isTrue(filter2.indexOf(";") > -1,"not contain ';'");
    }



}
