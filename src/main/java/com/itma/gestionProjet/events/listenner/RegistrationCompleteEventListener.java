package com.itma.gestionProjet.events.listenner;

import com.itma.gestionProjet.entities.User;
import com.itma.gestionProjet.events.RegistrationCompleteEvent;
import com.itma.gestionProjet.services.imp.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationCompleteEventListener  implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        String  verificationToken= UUID.randomUUID().toString();
        User theUser=event.getUser();
        String url=event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
        userService.saveUserVerificationToken(theUser,verificationToken);
        log.info("Click the link to verify your registration : {}",url);
    }
}
