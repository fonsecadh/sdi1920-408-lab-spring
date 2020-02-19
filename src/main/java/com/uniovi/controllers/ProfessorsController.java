package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Professor;
import com.uniovi.services.ProfessorService;
import com.uniovi.validators.AddProfessorFormValidator;

@Controller
public class ProfessorsController {

	@Autowired // Inyectar el servicio
	private ProfessorService professorService;
	
	@Autowired
	private AddProfessorFormValidator addProfessorFormValidor;

	@RequestMapping("/professor/list")
	public String getList(Model model) {
		model.addAttribute("professorList", professorService.getProfessors());
		return "professor/list";
	}

	@RequestMapping(value = "/professor/add", method = RequestMethod.POST)
	public String setProfessor(@Validated Professor professor, BindingResult result) {
		addProfessorFormValidor.validate(professor, result);
		if (result.hasErrors()) {
			return "/professor/add";
		}		
		professorService.addProfessor(professor);
		return "redirect:/professor/list";
	}

	@RequestMapping("/professor/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("professor", professorService.getProfessor(id));
		return "professor/details";
	}

	@RequestMapping("/professor/delete/{id}")
	public String deleteProfessor(@PathVariable Long id) {
		professorService.deleteProfessor(id);
		return "redirect:/professor/list";
	}

	@RequestMapping(value = "/professor/add")
	public String getProfessor(Model model) {
		model.addAttribute("professor", new Professor());
		return "professor/add";
	}

}
