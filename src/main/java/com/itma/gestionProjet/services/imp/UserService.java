package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.UserDTO;
import com.itma.gestionProjet.entities.Role;
import com.itma.gestionProjet.entities.User;
import com.itma.gestionProjet.entities.VerificationToken;
import com.itma.gestionProjet.exceptions.EmailAlreadyExistsException;
import com.itma.gestionProjet.repositories.RoleRepository;
import com.itma.gestionProjet.repositories.VerificationTokenRepository;
import com.itma.gestionProjet.requests.UserRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itma.gestionProjet.repositories.UserRepository;
import com.itma.gestionProjet.services.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService  implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
  private RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;
     @Autowired
    private  VerificationTokenRepository tokenRepository;
    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(UserRequest p) {

        Optional<User>  optionalUser = userRepository.findByEmail(p.getEmail());
        if(optionalUser.isPresent())
            throw new EmailAlreadyExistsException("Email déjà existant!");
        User newUser=new User();
        newUser.setEmail(p.getEmail());
        newUser.setLastname(p.getLastname());
        newUser.setFirstname(p.getFirstname());
        newUser.setDate_of_birth(p.getDate_of_birth());
        newUser.setPlace_of_birth(p.getDate_of_birth());
        newUser.setEnabled(p.getEnabled());
        newUser.setPassword(p.getPassword());
        userRepository.save(newUser);
        Role r = roleRepository.findRoleByName("Super Admin");
        List<Role> roles = new ArrayList<>();
        roles.add(r);
        newUser.setRoles(roles);
       return userRepository.save(newUser)    ;
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
    public List<User> getAllUsers() {
        return   userRepository.findAll();
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
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
    }


}
