package com.example.demo2.controller;

import javax.validation.Valid;

import com.example.demo2.model.*;
import com.example.demo2.repository.*;
import com.example.demo2.exception.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class LoginController implements WebMvcConfigurer{

    @Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/result/success").setViewName("result/success");
	}

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginFrom(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    
    @PostMapping("/login")
    public String loginSubmit(@Valid User user, BindingResult bindingResult, Model model) {
        System.out.println("Has errors="+bindingResult.hasErrors()+", "+bindingResult.getErrorCount()); // Output: Has errors=true, count
        for (FieldError err:bindingResult.getFieldErrors()){
            System.out.println(err.getDefaultMessage()); // Output: must be greater than or equal to 10
        }

        model.addAttribute("user", user);
        if (bindingResult.hasErrors()) {
			return "login";
		}
        if (!userRepository.findById(user.getName()).isPresent()) {
            return "result/userError";
        }else {
            User validateUser = userRepository.findById(user.getName())
                .orElseThrow(() -> new ResourceNotFoundException());
            String sha256hex = DigestUtils.sha256Hex(user.getPassword());
            user.setPassword("1");
            if (validateUser.getPwdHash().equals(sha256hex) ) {
                user.setPwdHash(sha256hex);
                return "result/success";
            }else {
                return "result/passwordError";
            }
        }
    }
}