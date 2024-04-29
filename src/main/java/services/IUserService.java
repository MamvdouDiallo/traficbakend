package services;

import dtos.UserDTO;
import entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findUserByEmail(String email);

    UserDTO saveUser(UserDTO p);
    UserDTO updateUser(UserDTO p);
    UserDTO getUser(Long id);
    List<UserDTO> getAllUsers();

    void deleteUser(User p);
    void deleteUserById(Long id);


    UserDTO convertEntityToDto(User p);
    User convertDtoToEntity(UserDTO UserDTO);
    
}
