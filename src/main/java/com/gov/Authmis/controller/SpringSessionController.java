package com.gov.Authmis.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpringSessionController {
	static Logger    logger = LoggerFactory.getLogger(SpringSessionController.class);
	@GetMapping("/asdasd")
	public String process(Model model, HttpSession session) {
//		@SuppressWarnings("unchecked")
//		List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
//		System.out.println("Enterecd into process=========1");
//		if (messages == null) {
//			messages = new ArrayList<>();
//		}
//		model.addAttribute("sessionMessages", messages);
//		System.out.println("model attribute has been added.");

		return "index";
	}
	
	@GetMapping("/index2")
	public String index2(Model model, HttpSession session) {
//		@SuppressWarnings("unchecked")
//		List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
//		System.out.println("Enterecd into process=========1");
//		if (messages == null) {
//			messages = new ArrayList<>();
//		}
//		model.addAttribute("sessionMessages", messages);
//		System.out.println("model attribute has been added.");

		return "index2";
	}

	@PostMapping("/persistMessage2")
	public String persistMessage2(@RequestParam("msg") String msg, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		
		logger.info("SpringSessionController======>Enterd into process=========2");
		if (messages == null) {
			System.out.println("messages is null");
			messages = new ArrayList<>();
			request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
		}
		logger.info("SpringSessionController======>messages=="+messages);
		logger.info("SpringSessionController======>msg=="+msg);
		
		messages.add(msg);
		request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
		return "redirect:/index2";
	}
	
	@PostMapping("/persistMessage")
	public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		logger.info("SpringSessionController===>Enterecd into process=========2");
		if (messages == null) {
			logger.info("SpringSessionController===>messages is null");
			messages = new ArrayList<>();
			request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
		}
		
		logger.info("SpringSessionController===>messages=="+messages);
		logger.info("SpringSessionController===>msg=="+msg);
		messages.add(msg);
		request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
		return "redirect:/";
	}

	@PostMapping("/destroy")
	public String destroySession(HttpServletRequest request) {
		logger.info("SpringSessionController===>Enterecd into process=========3");
		request.getSession().invalidate();
		return "redirect:/";
	}
}
