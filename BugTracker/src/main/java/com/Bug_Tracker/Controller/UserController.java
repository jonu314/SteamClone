package com.Bug_Tracker.Controller;



import com.Bug_Tracker.Model.User;
import com.Bug_Tracker.Model.UserPrincipal;
import com.Bug_Tracker.dto.UserDTO;
import com.Bug_Tracker.exception.domain.EmailExistException;
import com.Bug_Tracker.exception.domain.UsernameExistException;
import com.Bug_Tracker.service.UserService;
import com.Bug_Tracker.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping(path = {"/user"})
@CrossOrigin("http://localost:4200")
public class UserController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    @Autowired // were using @Qualifier("auth1") implementation from our config file
    public UserController(UserService userService, @Qualifier("auth1") AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;

    }

    @GetMapping("/list") // list all the users in db for admin to check
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UsernameExistException, EmailExistException {
        User newUser =  userService.register(user.getFirstName(),user.getLastName(),user.getUsername(),user.getPassword(), user.getEmail());
            return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) throws UsernameNotFoundException {
        authenticate(user.getUsername(),user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders headers = getJWTHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, headers, OK);
    }


    private HttpHeaders getJWTHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Jwt-Token",jwtTokenProvider.generateJwtToken(user));

        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

    }
    @PostMapping("/updateUserRole")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public User updateUserRole(@RequestParam("role") String role,
                                 @RequestParam("username") String username)
    {
        return userService.updateUserRole(role,username);

    }
    @PostMapping("/listProjectUsers")
    public List<UserDTO> listProjectUsers(@RequestParam("projectName") String projectName){
        return userService.listProjectUsers(projectName);
    }
}
