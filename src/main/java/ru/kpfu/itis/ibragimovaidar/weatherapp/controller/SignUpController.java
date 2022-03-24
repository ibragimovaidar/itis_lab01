package ru.kpfu.itis.ibragimovaidar.weatherapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.CreateUserDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class SignUpController {

  private final UserService userService;

  @GetMapping("/sign_up")
  public String getSignUpPage(Model model){
    model.addAttribute("user", new CreateUserDto());
    return "sign_up";
  }

  @PostMapping("/sign_up")
  public String signUp(@ModelAttribute(name = "user") CreateUserDto userDto, HttpServletRequest request){
    String url = request.getRequestURL().toString().replace(request.getServletPath(), "");
    userService.createUser(userDto, url);
    return "sign_up_success";
  }

  @GetMapping("/verification")
  public String verify(@Param("code") String code) {
    if (userService.verify(code)) {
      return "redirect:/login";
    } else {
      return "verification_failed";
    }
  }
}
