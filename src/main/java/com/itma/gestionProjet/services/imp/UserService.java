package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.Password.PasswordResetTokenService;
import com.itma.gestionProjet.dtos.UserDTO;
import com.itma.gestionProjet.entities.Role;
import com.itma.gestionProjet.entities.User;
import com.itma.gestionProjet.entities.VerificationToken;
import com.itma.gestionProjet.exceptions.EmailAlreadyExistsException;
import com.itma.gestionProjet.repositories.RoleRepository;
import com.itma.gestionProjet.repositories.VerificationTokenRepository;
import com.itma.gestionProjet.requests.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.itma.gestionProjet.repositories.UserRepository;
import com.itma.gestionProjet.services.IUserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService  implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
  private RoleRepository roleRepository;

   @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @Autowired
    ModelMapper modelMapper;
     @Autowired
    private  VerificationTokenRepository tokenRepository;
    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(UserRequest p) {

        Optional<User> optionalUser = userRepository.findByEmail(p.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email déjà existant!");
        } else {
            User newUser = new User();
            newUser.setEmail(p.getEmail());
            newUser.setLastname(p.getLastname());
            newUser.setFirstname(p.getFirstname());
            newUser.setDate_of_birth(p.getDate_of_birth());
            newUser.setPlace_of_birth(p.getDate_of_birth());
            newUser.setEnabled(p.getEnabled());
            newUser.setPassword(bCryptPasswordEncoder.encode(p.getPassword()));
            Role r = roleRepository.findRoleByName("Super Admin");
            List<Role> roles = new ArrayList<>();
            roles.add(r);
            newUser.setRoles(roles);
            return  userRepository.save(newUser) ;
        }
    }
    @Override
    public UserDTO updateUser(UserDTO p) {
        return null;
    }

    @Override
    public UserDTO getUser(Long id) {
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return   userRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteUser(User p) {
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public UserDTO convertEntityToDto(User p) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        UserDTO userDTO=  modelMapper.map(p, UserDTO.class);
        return userDTO;
    }

    @Override
    public User convertDtoToEntity(UserRequest userRequest) {
        User user = new User();
        user = modelMapper.map(userRequest, User.class);
        return user;
    }


    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            tokenRepository.delete(token);
            return "Token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordResetToken) {
        passwordResetTokenService.createPasswordResetTokenForUser(user, passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validatePasswordResetToken(token);
    }

    @Override
    public void deletePasswordResetToken(String token) {
        passwordResetTokenService.deletePasswordResetToken(token);
    }


    @Override
    public User findUserByToken(String token) {
        return passwordResetTokenService.findUserByPasswordToken(token).get();
    }

    @Override
    public void changePassword(User theUser, String newPassword) {
        theUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(theUser);
    }

    @Override
    public boolean oldPasswordIsValid(User user, String oldPassword){
        return bCryptPasswordEncoder.matches(oldPassword, user.getPassword());
    }

}
