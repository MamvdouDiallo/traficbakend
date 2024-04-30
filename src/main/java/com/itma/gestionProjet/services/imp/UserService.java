package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.RoleDTO;
import com.itma.gestionProjet.dtos.UserDTO;
import com.itma.gestionProjet.entities.Role;
import com.itma.gestionProjet.entities.User;
import com.itma.gestionProjet.exceptions.EmailAlreadyExistsException;
import com.itma.gestionProjet.exceptions.RoleAlreadyExistsException;
import com.itma.gestionProjet.requests.UserRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.itma.gestionProjet.repositories.UserRepository;
import com.itma.gestionProjet.services.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService  implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO saveUser(UserRequest p) {

        Optional<User>  optionalUser = userRepository.findByEmail(p.getEmail());
        if(optionalUser.isPresent())
            throw new EmailAlreadyExistsException("Email déjà existant!");
       return null;
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
        return List.of();
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




}
