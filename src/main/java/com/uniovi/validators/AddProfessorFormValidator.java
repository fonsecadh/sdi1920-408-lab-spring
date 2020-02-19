package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Professor;
import com.uniovi.services.ProfessorService;

@Component
public class AddProfessorFormValidator implements Validator {

	@Autowired
	private ProfessorService professorService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Professor.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Professor professor = (Professor) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");

		if (professor.getDni().length() != 9) {
			errors.rejectValue("dni", "Error.professor.add.dni.length");
		}
		if (professorService.getUserByDni(professor.getDni()) != null) {
			errors.rejectValue("dni", "Error.professor.add.dni.duplicate");
		}
		if (!Character.isLetter(professor.getDni().charAt(professor.getDni().length() - 1))) {
			errors.rejectValue("dni", "Error.professor.add.dni.letter");
		}
	}

}
