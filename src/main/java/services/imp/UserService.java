package services.imp;

import dtos.UserDTO;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.UserRepository;
import services.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService  implements IUserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO saveUser(UserDTO p) {
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
        return null;
    }

    @Override
    public User convertDtoToEntity(UserDTO UserDTO) {
        return null;
    }


}
