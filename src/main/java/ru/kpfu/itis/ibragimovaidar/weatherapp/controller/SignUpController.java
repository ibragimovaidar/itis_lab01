package ru.kpfu.itis.ibragimovaidar.weatherapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.CreateUserDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.service.UserService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/sign_up")
public class SignUpController {

  private final UserService userService;

  @GetMapping
  public String getSignUpPage(Model model){
    model.addAttribute("user", new CreateUserDto());
    return "sign_up";
  }

  @PostMapping
  public String signUp(@ModelAttribute(name = "user") CreateUserDto userDto){
    userService.createUser(userDto);
    return "sign_up_success";
  }
}
