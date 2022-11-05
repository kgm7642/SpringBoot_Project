package com.project.controller;

import java.net.Socket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/")
@Controller
public class ChatController extends Socket{
	
    @GetMapping("chat")
    public String chatGET(){
        log.info("@ChatController, chat GET()");
        
        return "/chat";
    }
}