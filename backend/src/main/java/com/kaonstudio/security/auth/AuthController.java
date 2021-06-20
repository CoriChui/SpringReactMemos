package com.kaonstudio.security.auth;

import com.kaonstudio.security.appuser.AppUser;
import com.kaonstudio.security.auth.login.LoginRequest;
import com.kaonstudio.security.auth.registration.RegistrationRequest;
import com.kaonstudio.security.exception.ApiRequestException;
import com.kaonstudio.security.exception.email.EmailExpiredException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest request) {
        String msgSuccess = "Confirmation email for %s %s was sent to %s";
        try {
            AppUser user = authService.register(request);
            return new ResponseEntity<>(
                    String.format(msgSuccess, user.getFirstName(), user.getLastName(), user.getEmail()),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            String result = authService.login(request);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @GetMapping(path = "/confirm")
    public ModelAndView confirm(@RequestParam("token") String token) {
        String redirect = "redirect:http://localhost:3000/confirm";
        ModelMap model = new ModelMap();
        try {
            authService.confirmToken(token);
            model.addAttribute("response", "Email confirmed");
            return new ModelAndView(redirect, model, HttpStatus.OK);
            //return ResponseEntity.status(HttpStatus.FOUND).location(redirect).body("Email confirmed");
        } catch (Exception e) {
            if (e instanceof EmailExpiredException) {
                model.addAttribute("response", e.getMessage());
                model.addAttribute("token", ((EmailExpiredException) e).getToken());
                return new ModelAndView(redirect, model, HttpStatus.BAD_REQUEST);
            } else {
                throw new ApiRequestException(e.getMessage());
            }
        }
    }

    @GetMapping(path = "/resend-email-token")
    public ResponseEntity<String> resendEmailToken(@RequestParam("token") String token) {
        try {
            authService.resendEmailVerificationToken(token);
            return new ResponseEntity<>("Confirmation link resent", HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRequestException("Token not found");
        }
    }
}
