package com.youran.common.validator;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义校验注解：常量校验
 * <p>校验常量值是否合法
 *
 * @author: cbb
 * @date: 2017/6/15
 */
@Target({FIELD, METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = {Const.Checker.class})
@Documented
public @interface Const {

    String DEFAULT_MESSAGE = "常量不合法";

    Class constClass();

    String message() default DEFAULT_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Checker implements ConstraintValidator<Const, Object> {

        private static final Logger logger = LoggerFactory.getLogger(Checker.class);

        private Class constClass;
        private Method checkMethod;
        private String defaultMsg;

        @Override
        public void initialize(Const constAnnotation) {
            this.constClass = constAnnotation.constClass();
            List<Method> checkMethods = MethodUtils.getMethodsListWithAnnotation(this.constClass, Check.class);
            if (CollectionUtils.isNotEmpty(checkMethods)) {
                this.checkMethod = checkMethods.get(0);
            }
            if (this.checkMethod != null && DEFAULT_MESSAGE.equals(constAnnotation.message())) {
                Check check = this.checkMethod.getAnnotation(Check.class);
                this.defaultMsg = check.message();
            }
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            if (checkMethod == null) {
                return false;
            }
            boolean success;
            try {
                Object result = checkMethod.invoke(null, value);
                if (result instanceof Boolean) {
                    success = (Boolean) result;
                } else {
                    throw new RuntimeException("校验方法返回值类型必须是boolean");
                }
            } catch (IllegalAccessException e) {
                logger.error("自定义校验异常", e);
                throw new RuntimeException("自定义校验异常", e);
            } catch (InvocationTargetException e) {
                logger.error("自定义校验异常", e);
                throw new RuntimeException("自定义校验异常", e);
            }

            if (!success && StringUtils.isNotBlank(defaultMsg)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(defaultMsg)
                    .addConstraintViolation();
            }
            return success;
        }
    }

}
