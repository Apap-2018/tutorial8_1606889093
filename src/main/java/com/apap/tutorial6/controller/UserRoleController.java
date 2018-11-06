package com.apap.tutorial6.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tutorial6.model.PasswordModel;
import com.apap.tutorial6.model.UserRoleModel;
import com.apap.tutorial6.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	public boolean validatePassword(String password) {
		if(password.length()>7 && Pattern.compile("[a-zA-Z]").matcher(password).find() && Pattern.compile("[0-9]").matcher(password).find()) {
				return true;
		}
		return false;
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user, Model model) {
		String msg = "";
		
		if(this.validatePassword(user.getPassword())) {
			userService.addUser(user);
			msg = null;
		}
		
		else {
			msg = "Password harus terdiri dari 8 karakter yang di dalamnya ada huruf dan angka";
		}
		model.addAttribute("message", msg);
		return "home";
	}
	
	@RequestMapping(value="/passwordSubmit", method = RequestMethod.POST)
	private ModelAndView passwordSubmit(@ModelAttribute PasswordModel password, Model model, RedirectAttributes redir) {
		BCryptPasswordEncoder passwordEncoder = new BcryptPasswordEncoder();
		UserRoleModel user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		String msg = "";
		System.out.println(password.getConfirmPassword());
		System.out.println(password.getNewPassword());
		System.out.println(password.getOldPassword());
		if(this.validatePassword(password.getOldPassword()) && this.validatePassword(password.getNewPassword()) && this.validatePassword(password.getConfirmPassword())) {
			if(password.getConfirmPassword().equals(password.getNewPassword())) {
				if(passwordEncoder.matches(password.getOldPassword(), user.getPassword())) {
					userService.gantiPassword(user, password.getNewPassword());
					msg = "Password Berhasil diupdate";
				}
				
				else {
					msg = "Password lama yang dimasukkan tidak sesuai";
				}
			}
			
			else {
				msg = "Password baru tidak sesuai";
			}
		
		}
		else {
			msg = "Password harus terdiri dari 8 karakter yang di dalamnya ada huruf dan angka";
		}
	
		
		ModelAndView modelAndView = new ModelAndView("redirect:/user/updatePassword");
		redir.addFlashAttribute("msg", msg);
		return modelAndView;
	}
	
	@RequestMapping("/updatePassword")
	public String updatePassword() {
		return "update-password-success";
	}
}
