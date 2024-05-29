package com.itma.gestionProjet.controllers;


import com.itma.gestionProjet.Password.PasswordRequest;
import com.itma.gestionProjet.Password.PasswordRequestUtil;
import com.itma.gestionProjet.Password.PasswordResetUtil;
import com.itma.gestionProjet.dtos.ApiResponse;
import com.itma.gestionProjet.dtos.AuthResponseDTO;
import com.itma.gestionProjet.dtos.LoginDTO;
import com.itma.gestionProjet.dtos.UserDTO;
import com.itma.gestionProjet.entities.User;
import com.itma.gestionProjet.entities.VerificationToken;
import com.itma.gestionProjet.events.RegistrationCompleteEvent;
import com.itma.gestionProjet.events.listenner.RegistrationCompleteEventListener;
import com.itma.gestionProjet.repositories.UserRepository;
import com.itma.gestionProjet.repositories.VerificationTokenRepository;
import com.itma.gestionProjet.requests.UserRequest;
import com.itma.gestionProjet.security.JWTGenerator;
import com.itma.gestionProjet.services.imp.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private AuthenticationManager authenticationManager;
    private  final ApplicationEventPublisher publisher;
@Autowired
    private  RegistrationCompleteEventListener eventListener;

    @Autowired
    private  HttpServletRequest servletRequest;
    private  final  VerificationTokenRepository tokenRepository;

    public UserController(ApplicationEventPublisher publisher, VerificationTokenRepository tokenRepository) {
        this.publisher = publisher;
        this.tokenRepository = tokenRepository;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ApiResponse<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ApiResponse<>(HttpStatus.OK.value(), "Liste des utilisateurs récupérée avec succès", users);
    }



    @RequestMapping(path = "/createUser", method = RequestMethod.POST)
    public  ApiResponse<User> createUser(@RequestBody UserRequest userRequest, final HttpServletRequest request) {
        User user = userService.saveUser(userRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return   new ApiResponse<>(HttpStatus.OK.value(), "User cree avec succés",user);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()){
            String token = jwtGenerator.generateToken(authentication);
            Optional<User> user=userRepository.findByEmail(loginDto.getEmail());
                User user1 = user.get();
                UserDTO userDTO = userService.convertEntityToDto(user1);
                return new ResponseEntity<>(new AuthResponseDTO(token, Optional.ofNullable(userDTO)), HttpStatus.OK);
        }
        else {
            throw  new UsernameNotFoundException("Invalid credentials");
        }

    }

    @PostMapping("/reset")
    public ApiResponse resetPasswordRequest(@RequestBody PasswordRequest passwordRequest,
                                            final HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException {
        Optional<User> user = userService.findUserByEmail(passwordRequest.getEmail());
        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            String passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(servletRequest), passwordResetToken);
            userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
            return new ApiResponse(HttpStatus.OK.value(), "Un mail vous est envoyé pour réinitialiser votre mot de passe", null);
        } else {
            return new ApiResponse(HttpStatus.NOT_FOUND.value(), "C'est addresse mail n'existe pas dans notre base de données", null);
        }
    }

    private String passwordResetEmailLink(User user, String applicationUrl, String passwordToken) throws MessagingException, UnsupportedEncodingException {
       // String url = applicationUrl+"/users/reset-password?token="+passwordToken;
        String url = "http://localhost:4200/auth/login-2?token=" + passwordToken;
        http://localhost:4200/auth/login-2
        eventListener.sendPasswordResetVerificationEmail(url,user);
        log.info("Click the link to reset your password :  {}", url);
        return url;
    }

    @PostMapping("/reset-password")
    public ApiResponse resetPassword(@RequestBody PasswordResetUtil passwordRequestUtil){
        String token=passwordRequestUtil.getToken();
        String tokenVerificationResult = userService.validatePasswordResetToken(token);
     /*
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return   new ApiResponse(400,"Invalid token",null);
        }
        */
        Optional<User> theUser = Optional.ofNullable(userService.findUserByToken(token));
        if (theUser.isPresent()) {
            userService.changePassword(theUser.get(), passwordRequestUtil.getNewPassword());
            userService.deletePasswordResetToken(token);
            return new ApiResponse(400,"Password has been reset successfully",null);
        }
        return new ApiResponse(400," Invalid password reset token",null);
    }
    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getUser().getEnabled()){
            return "This account has already been verified, please, login.";
        }
        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return "Compte activeted successfully. Now you can login to your account";
        }
        return "Invalid verification token";
    }


    @PostMapping("/change-password")
    public String changePassword(@RequestBody PasswordRequestUtil requestUtil){
        User user = userService.findUserByEmail(requestUtil.getEmail()).get();
        if (!userService.oldPasswordIsValid(user, requestUtil.getOldPassword())){
            return "Incorrect old password";
        }
        userService.changePassword(user, requestUtil.getNewPassword());
        return "Password changed successfully";
    }
    public String applicationUrl(HttpServletRequest httpServlet) {
        return "http://"+httpServlet.getServerName()+":"+httpServlet.getServerPort()+httpServlet.getContextPath();

    }

}
