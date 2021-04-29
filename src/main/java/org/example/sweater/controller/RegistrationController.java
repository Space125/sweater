package org.example.sweater.controller;

import org.example.sweater.domain.User;
import org.example.sweater.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

import static org.example.sweater.controller.ControllerUtils.getErrors;

/**
 * @author Ivan Kurilov on 22.04.2021
 */

@Controller
public class RegistrationController {


    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirmation,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {

        boolean isConfirmEmpty = ObjectUtils.isEmpty(passwordConfirmation);
        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }

        boolean isDifferentPassword = user.getPassword() != null && !user.getPassword().equals(passwordConfirmation);
        if (isDifferentPassword) {
            model.addAttribute("passwordError", "Password are different!");
        }

        if (isConfirmEmpty || isDifferentPassword || bindingResult.hasErrors()) {
            Map<String, String> errorsMap = getErrors(bindingResult);
            model.mergeAttributes(errorsMap);

            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User already exist");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }
        return "login";
    }
}
