package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.annotation.RequireScope;

@RestController
@RequestMapping("/api")
public class UserController {

	@GetMapping("/profile")
	@RequireScope("USER_READ")
	public String profile() {
	    return "Welcome to Protected API";
	}

}