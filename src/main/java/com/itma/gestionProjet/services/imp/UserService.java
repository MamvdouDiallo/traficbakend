package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.Password.PasswordResetTokenService;
import com.itma.gestionProjet.dtos.UserDTO;
import com.itma.gestionProjet.entities.*;
import com.itma.gestionProjet.exceptions.ContactMobileAlreadyExistsException;
import com.itma.gestionProjet.exceptions.EmailAlreadyExistsException;
import com.itma.gestionProjet.exceptions.UserNotFoundException;
import com.itma.gestionProjet.repositories.*;
import com.itma.gestionProjet.requests.ConsultantRequest;
import com.itma.gestionProjet.requests.UserRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.itma.gestionProjet.services.IUserService;

import java.util.*;
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
    private ImageRepository imageRepository;

    @Autowired
    private ProjectRepository projectRepository;
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
            newUser.setContact(p.getContact());
            newUser.setLocality(p.getLocality());
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
    public UserDTO saveMo(UserRequest p) {
        // Check if a user with the given email already exists
        if (userRepository.findByEmail(p.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email déjà existant!");
        }

        // Check if a user with the given contact number already exists
        if (userRepository.findByContact(p.getContact()).isPresent()) {
            throw new ContactMobileAlreadyExistsException("Ce numero téléphone est dejà utilisé");
        }

        // Create a new Mo
        User newUser = new User();
        newUser.setEmail(p.getEmail());
        newUser.setLastname(p.getLastname());
        newUser.setFirstname(p.getFirstname());
        newUser.setContact(p.getContact());
        newUser.setDate_of_birth(p.getDate_of_birth());
        newUser.setLocality(p.getLocality());
        newUser.setPlace_of_birth(p.getPlace_of_birth()); // Assuming this should be 'getPlaceOfBirth'
        newUser.setEnabled(false);
        newUser.setPassword(bCryptPasswordEncoder.encode("Passer@123"));
         newUser.setImage(p.getImage());
        // Assign roles to the new user
        Role r = roleRepository.findRoleByName("Maitre d'ouvrage");
        List<Role> roles = new ArrayList<>();
        roles.add(r);
        newUser.setRoles(roles);

        // Assign projects to the new user
        List<Long> projectIds = p.getProject_ids();
        List<Project> projects = projectRepository.findAllById(projectIds);
        newUser.setProjects(projects);
        // Update each project with the new user
        for (Project project : projects) {
            project.getUsers().add(newUser);
        }
        // Save the new user
        return    convertEntityToDto(userRepository.save(newUser));
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            Optional<User> user = userRepository.findById(Math.toIntExact(id));
            return user;

        } catch (Exception e) {
            // Gérer l'exception et lancer une nouvelle exception personnalisée
            throw new UsernameNotFoundException("Cet utilisateur n'est pas trouvé", e);
        }
    }

    @Override
    public UserDTO updateUser(UserRequest p) {
        User existingUser = userRepository.findById(Math.toIntExact(p.getId()))
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + p.getId()));

        existingUser.setEmail(p.getEmail());
        existingUser.setLastname(p.getLastname());
        existingUser.setFirstname(p.getFirstname());
        existingUser.setContact(p.getContact());
        existingUser.setDate_of_birth(p.getDate_of_birth());
        existingUser.setLocality(p.getLocality());
        existingUser.setPlace_of_birth(p.getPlace_of_birth()); // Assuming this should be 'getPlaceOfBirth'
        existingUser.setEnabled(false);
        existingUser.setPassword(bCryptPasswordEncoder.encode("Passer@123"));
        existingUser.setImage(p.getImage());

        for (Project project : existingUser.getProjects()) {
            project.getUsers().remove(existingUser);
        }
        existingUser.getProjects().clear();

        // Assign projects to the new user
       List<Long> projectIds = p.getProject_ids();
        List<Project> projects = projectRepository.findAllById(projectIds);
        existingUser.setProjects(projects);
        // Update each project with the new user
        for (Project project : projects) {
            project.getUsers().add(existingUser);
        }

        // Assign roles to the new user
        Role r = roleRepository.findRoleByName("Maitre d'ouvrage");
        List<Role> roles = new ArrayList<>();
        roles.add(r);
        existingUser.setRoles(roles);
        return  convertEntityToDto(userRepository.save(existingUser));

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
    public List<UserDTO> getUsersByRoleName(String roleName) {
        return userRepository.findUsersByRoleName(roleName).stream().map(this::convertEntityToDto).collect(Collectors.toList())    ;
    }


    @Override
    public void deleteUser(User p) {
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        // Remove the user's associations in user_project
        userRepository.deleteUserAssociations(id);

        // Remove the user's associations with roles and projects
        if (user.getImage() != null) {
            Image image = user.getImage();
            user.setImage(null);
            imageRepository.delete(image);
        }
        user.getRoles().clear();
        user.getProjects().clear();
        userRepository.save(user);

        // Delete the user
        userRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public UserDTO convertEntityToDto(User p) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(p, UserDTO.class);
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

    @Override
    public UserDTO saveConsultant(ConsultantRequest p) {
        if (userRepository.findByEmail(p.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email déjà existant!");
        }

        // Check if a user with the given contact number already exists
        if (userRepository.findByContact(p.getContact()).isPresent()) {
            throw new ContactMobileAlreadyExistsException("Ce numero téléphone est dejà utilisé");
        }

        // Create a new Mo
        User newUser = new User();
        newUser.setEmail(p.getEmail());
        newUser.setLastname(p.getLastname());
        newUser.setFirstname(p.getFirstname());
        newUser.setContact(p.getContact());
        newUser.setDate_of_birth(p.getDate_of_birth());
        newUser.setLocality(p.getLocality());
        newUser.setPlace_of_birth(p.getPlace_of_birth()); // Assuming this should be 'getPlaceOfBirth'
        newUser.setEnabled(false);
        newUser.setPassword(bCryptPasswordEncoder.encode("Passer@123"));
        newUser.setImage(p.getImage());
        // Assign roles to the new user
        Role r = roleRepository.findRoleByName("Consultant");
        List<Role> roles = new ArrayList<>();
        roles.add(r);
        newUser.setRoles(roles);
        // Assign projects to the new user
        List<Long> projectIds = Collections.singletonList(p.getProject_id());
        List<Project> projects = projectRepository.findAllById(projectIds);
        newUser.setProjects(projects);// Update each project with the new user
        for (Project project : projects) {
            project.getUsers().add(newUser);
        }
        // Save the new user
        return    convertEntityToDto(userRepository.save(newUser));
    }






    @Override
    public UserDTO updateConsultant(Long id, ConsultantRequest p) {
        // Find the user by ID
        User existingUser = userRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + p.getId()));

        // Check if the email is being changed and if the new email already exists
        // Vérification de l'email
        userRepository.findByEmail(p.getEmail()).ifPresent(user -> {

            if (!Objects.equals(user.getId(), existingUser.getId())) {
                throw new EmailAlreadyExistsException("Email déjà existant!");
            }

        });


        // Vérification du numéro de contact

        userRepository.findByContact(p.getContact()).ifPresent(user -> {

            if (!Objects.equals(user.getId(), existingUser.getId())) {
                throw new EmailAlreadyExistsException("Email déjà existant!");
            }

        });

        // Update the user's information
        existingUser.setEmail(p.getEmail());
        existingUser.setLastname(p.getLastname());
        existingUser.setFirstname(p.getFirstname());
        existingUser.setContact(p.getContact());
        existingUser.setDate_of_birth(p.getDate_of_birth());
        existingUser.setLocality(p.getLocality());
        existingUser.setPlace_of_birth(p.getPlace_of_birth());
        existingUser.setImage(p.getImage());

        List<Long> projectIds = Collections.singletonList(p.getProject_id());
        List<Project> projects = projectRepository.findAllById(projectIds);
        existingUser.setProjects(projects);// Update each project with the new user
        for (Project project : projects) {
            project.getUsers().add(existingUser);
        }

        // Save the updated user
        return convertEntityToDto(userRepository.save(existingUser));
    }








}
