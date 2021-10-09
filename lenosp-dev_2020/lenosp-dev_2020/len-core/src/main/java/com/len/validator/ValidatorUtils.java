package com.len.validator;

import com.len.exception.LenException;
import com.len.exception.ServiceException;
import com.len.util.MsHelper;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @ClassName: ValidatorUtils
 * @Description: hibernate-validator校验工具类 加强校验
 * @author: liutao
 * @date: 2018年3月16日 上午11:56:34
 * @date: 2021/3/22 加强校验
 */
public class ValidatorUtils {

    private static Validator validator;


    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws LenException 校验不通过，则报BusinessException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws LenException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint =
                    constraintViolations.iterator().next();
            throw new ServiceException(constraint.getMessage());
        }
    }

    public static void notEmpty(String str, String msg, Object... args) {
        if (str == null || str.isEmpty()) {
            validateEntity(msg, args);
        }
    }

    public static void notNull(Object obj, String msg, Object... args) {
        if (obj == null) {
            validateEntity(msg, args);
        }
    }

    private static void validateEntity(String msg, Object... args) {
        throw new ServiceException(MsHelper.getMessage(msg, args));
    }
}
