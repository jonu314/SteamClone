package com.Bug_Tracker.service.impl;

import com.Bug_Tracker.Model.Admin;
import com.Bug_Tracker.Model.AdminPrinciple;
import com.Bug_Tracker.enumeration.Role;
import com.Bug_Tracker.repository.AdminRepository;
import com.Bug_Tracker.repository.UserRepository;
import com.Bug_Tracker.service.AdminService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Qualifier("adminServiceImpl")
public class AdminServiceImpl implements AdminService, UserDetailsService {
    UserRepository userRepository; // synced up with db
    AdminRepository adminRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }
// note: this is just here to register a user in postman, I dont use it in frontend
    @Override
    public Admin register(String firstName, String lastName, String username, String password, String email)  {
        Admin user = new Admin();
        user.setAdminId(generateUserId());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodePassword(password));
        user.setRole(Role.ROLE_ADMIN.name());
        user.setAuthorities(Role.ROLE_ADMIN.getAuthorities());
        adminRepository.save(user); // saves user in mysql database
        return user;
    }


    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findAdminByUsername(username);
        if(admin == null){
            throw new UsernameNotFoundException("User not found by username: " + username);}
        else
        {
            adminRepository.save(admin);
            AdminPrinciple adminPrincipal = new AdminPrinciple(admin);
            return adminPrincipal;
        }

    }


}

