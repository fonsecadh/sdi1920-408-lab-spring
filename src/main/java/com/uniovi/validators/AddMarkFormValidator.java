package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Mark;

@Component
public class AddMarkFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return Mark.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Mark mark = (Mark) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "score", "Error.empty");

		if (mark.getScore() < 0 || mark.getScore() > 10) {
			errors.rejectValue("score", "Error.mark.add.score");
		}
		if (mark.getDescription().length() < 20) {
			errors.rejectValue("description", "Error.mark.add.description");
		}
	}

}
