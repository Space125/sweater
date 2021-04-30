package org.example.sweater.controller;

import org.example.sweater.domain.User;
import org.example.sweater.domain.dto.CaptchaResponseDto;
import org.example.sweater.properties.RecaptchaProperties;
import org.example.sweater.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

import static org.example.sweater.controller.ControllerUtils.getErrors;

/**
 * @author Ivan Kurilov on 22.04.2021
 */

@Controller
public class RegistrationController {

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    private final UserService userService;

    private final RecaptchaProperties recaptchaProperties;

    private final RestTemplate restTemplate;

    public RegistrationController(UserService userService,
                                  RecaptchaProperties recaptchaProperties,
                                  RestTemplate restTemplate) {
        this.userService = userService;
        this.recaptchaProperties = recaptchaProperties;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirmation,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            RedirectAttributes redirectAttributes,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {

        String url = String.format(CAPTCHA_URL, recaptchaProperties.getSecret(), captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if(!response.isSuccess()){
            model.addAttribute("captchaError", "Fill captcha");
        }

        boolean isConfirmEmpty = ObjectUtils.isEmpty(passwordConfirmation);
        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }

        boolean isDifferentPassword = user.getPassword() != null && !user.getPassword().equals(passwordConfirmation);
        if (isDifferentPassword) {
            model.addAttribute("passwordError", "Password are different!");
        }

        if (isConfirmEmpty || isDifferentPassword || !response.isSuccess() || bindingResult.hasErrors()) {
            Map<String, String> errorsMap = getErrors(bindingResult);
            model.mergeAttributes(errorsMap);

            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User already exist");
            return "registration";
        } else {
            redirectAttributes.addFlashAttribute("messageType", "info");
            redirectAttributes.addFlashAttribute("message", "Visit your mail address: " + user.getEmail() + " for activate account");
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }
        return "login";
    }
}
