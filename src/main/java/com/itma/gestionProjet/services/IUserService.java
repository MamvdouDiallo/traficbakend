package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.UserDTO;
import com.itma.gestionProjet.entities.User;
import com.itma.gestionProjet.requests.UserRequest;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findUserByEmail(String email);

    User saveUser(UserRequest p);
    UserDTO updateUser(UserDTO p);
    UserDTO getUser(Long id);
    List<User> getAllUsers();



    void saveUserVerificationToken(User theUser, String verificationToken);
    void deleteUser(User p);
    void deleteUserById(Long id);


    UserDTO convertEntityToDto(User p);
  //  User convertDtoToEntity(UserRequest UserDTO);
    
}
