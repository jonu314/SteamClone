package com.Bug_Tracker.Controller;

import com.Bug_Tracker.Model.*;
import com.Bug_Tracker.exception.domain.EmailExistException;
import com.Bug_Tracker.exception.domain.UsernameExistException;
import com.Bug_Tracker.repository.AdminRepository;
import com.Bug_Tracker.repository.UserRepository;
import com.Bug_Tracker.service.AdminService;
import com.Bug_Tracker.service.UserService;
import com.Bug_Tracker.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path ={"/","adminPortal"})

@CrossOrigin("http://localost:4200")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminController {

   private UserService userService;

   private JWTTokenProvider jwtTokenProvider;

   private AuthenticationManager authenticationManager;

   private AdminService adminService;

   private AdminRepository adminRepository;

   private UserRepository userRepository;

 @Autowired // we can autowire UserSerivce since implementation uses @service annotation
    public AdminController(UserService userService, JWTTokenProvider jwtTokenProvider, @Qualifier("auth2") AuthenticationManager authenticationManager, UserRepository userRepository, AdminService adminService, AdminRepository adminRepository){
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.adminService = adminService;
        this.adminRepository = adminRepository;
    }
 //this is just here in backend, it is not implemented in frontend because we do not want users being able to register an admin account
    @PostMapping("/register")
    public ResponseEntity<Admin> register(@RequestBody Admin user)    {
        Admin admin = adminService.register(user.getFirstName(),user.getLastName(),user.getUsername(),user.getPassword(), user.getEmail());
        return new ResponseEntity<>(admin,OK);

    }




    @PostMapping("/login")
    public ResponseEntity<Admin> login(@RequestBody Admin adminUser) throws UsernameNotFoundException {
        authenticate(adminUser.getUsername(),adminUser.getPassword()); // success for kp 123
        Admin loginAdmin = adminRepository.findAdminByUsername(adminUser.getUsername());
        AdminPrinciple adminPrincipal = new AdminPrinciple(loginAdmin);
        HttpHeaders headers = getJWTHeader(adminPrincipal);
        return new ResponseEntity<>(loginAdmin, headers, OK);


    }
    // this gives us token
    private HttpHeaders getJWTHeader(AdminPrinciple admin)  {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Jwt-Token",jwtTokenProvider.generateAdminJwtToken(admin));

        return headers;
    }
// authenticate the admin
    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }



    @DeleteMapping("/delete/{username}") // allows admin to delete user
    @PreAuthorize("hasAnyAuthority('admin:delete')")
    public void deleteUser(@PathVariable("username") String username) throws IOException {
        userService.deleteUser(username);

    }
    @PostMapping("/updateUserRole")
   @PreAuthorize("hasAnyAuthority('admin:update')")
    public User updateUserRole(@RequestParam("role") String role,
                                 @RequestParam("username") String username)
    {
        return userService.updateUserRole(role,username);

    }





}

