package com.Bug_Tracker.service.impl;
import com.Bug_Tracker.Model.Project;
import com.Bug_Tracker.dto.UserDTO;
import com.Bug_Tracker.enumeration.Role;

import com.Bug_Tracker.Model.User;
import com.Bug_Tracker.Model.UserPrincipal;
import com.Bug_Tracker.exception.domain.EmailExistException;
import com.Bug_Tracker.exception.domain.UsernameExistException;
import com.Bug_Tracker.repository.ProjectRepository;
import com.Bug_Tracker.repository.UserRepository;
import com.Bug_Tracker.service.ProjectService;
import com.Bug_Tracker.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;


@Transactional // data safety precaution especially for CRUD operations
@Service // same functionality as component except its service annotation
@Qualifier("UserDetailService") // this allows us to inject it directly to a method
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ProjectRepository projectRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,ProjectRepository projectRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.projectRepository = projectRepository;
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public User register(String firstName, String lastName, String username, String password, String email) throws UsernameExistException, EmailExistException {
        validateNewUsernameAndEmail(username,email);
        User user = new User();
        user.setUserId(generateUserId());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodePassword(password));
        user.setActive(true);
        user.setRole(Role.ROLE_MANAGER.name());
        user.setAuthorities(Role.ROLE_MANAGER.getAuthorities());
        userRepository.save(user); // saves user in mysql database
        return user;

    }

    private void validateNewUsernameAndEmail(String newUsername, String newEmail) throws UsernameExistException, EmailExistException {
        User userByNewUsername = findUserByUsername(newUsername); // if you find it then it isnt null, which means the username is already in db, so if not null then throw excepeption
        User userByNewEmail = findUserByEmail(newEmail);

            if(userByNewUsername != null) {
                throw new UsernameExistException("Username already exists");
            }
            if(userByNewEmail != null) {
                throw new EmailExistException("Email already exists");
            }

        }




    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User updateUserRole(String role,String username) {

        User user = userRepository.findUserByUsername(username);
        if(role.equals("ROLE_MANAGER")){
            user.setRole(Role.ROLE_MANAGER.name());
        }
        else if (role.equals("ROLE_ADMIN"))
            user.setRole(Role.ROLE_ADMIN.name());
        else
            user.setRole(Role.ROLE_USER.name());
               return user;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @Override
    public List<UserDTO> listProjectUsers(String projectName) {
        Project project = projectRepository.findProjectByProjectName(projectName);
        Long projectId= project.getId();
        return userRepository.findUsersByProjectId(projectId);
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepository.findUserByUsername(username);
        userRepository.deleteById(user.getId());

    }


// whenever you authenticate the user or call the authenticate method, this will automatically be called to load the user by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if(user == null){ throw new UsernameNotFoundException("User not found by username: " + username);}
        else
        {
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            return userPrincipal;
        }

    }


}
