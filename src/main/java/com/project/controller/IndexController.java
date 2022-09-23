package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.domain.GoogleUsers;

@Controller
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public String index(GoogleUsers users) {
		return "index";
	}
}
