package com.sangxiang.dao.utils;
import com.sangxiang.dao.exception.BizException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Set;

public class ValidatedUtil {

	private static Validator validator;

	static {
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		validator = vf.getValidator();
	}

	public static void valid(Object object, Class<?>... groups) {
		Set<ConstraintViolation<Object>> set = validator.validate(object, groups);
		if (set.size() > 0) {
			StringBuilder validateError = new StringBuilder();
			for (ConstraintViolation<Object> constraintViolation : set) {
					validateError.append(constraintViolation.getPropertyPath()).append(constraintViolation.getMessage() + " ;"); 
			}
			BizException.throwException(9002, validateError.toString());
		}
	}

	public static void fieldNotBank(Object object, String... keys) {
		for (String key : keys) {
			Object value;
			try {
				value = PropertyUtils.getProperty(object, key);
			} catch (Exception e) {
				continue;
			}
			notBlank(key, value);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void notBlank(String key, Object value) {
		if (value == null) {
			BizException.throwException(9001, key);
		}
		if (value instanceof CharSequence) {
			if (StringUtils.isBlank((CharSequence) value)) {
				BizException.throwException(9001, key);
			}
		}
		if (value instanceof Collection) {
			if (CollectionUtils.isEmpty((Collection) value)) {
				BizException.throwException(9001, key);
			}
		}
		if (value.getClass().isArray()) {
			if (Array.getLength(value) == 0) {
				BizException.throwException(9001, key);
			}
		}
	}
}
