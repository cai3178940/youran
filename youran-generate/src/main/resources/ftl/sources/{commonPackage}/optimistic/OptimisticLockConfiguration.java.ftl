<#include "/common.ftl">
package ${commonPackage}.optimistic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

<@classCom "乐观锁配置"></@classCom>
public class OptimisticLockConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(OptimisticLockConfiguration.class);

    @Bean
    public OptimisticLockAspect optimisticLockAspect(){
        logger.info("创建OptimisticLockAspect");
        return new OptimisticLockAspect();
    }

}
