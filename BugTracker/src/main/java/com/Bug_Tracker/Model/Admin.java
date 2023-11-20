
package com.Bug_Tracker.Model;

import javax.persistence.*;
@Entity
public class Admin {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false, updatable = false)
        private Long id;
        private String adminId;
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private String email;
        private String role;
        private String[] authorities;

      public Admin(){}

        public Admin(Long id, String userId, String firstName, String lastName, String username, String password, String email, String role, String[] authorities, boolean isActive) {
                this.id = id;
                this.adminId = userId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.username = username;
                this.password = password;
                this.email = email;
                this.role = role;
                this.authorities = authorities;

        }

        public void setId(Long id) {
                this.id = id;
        }

        public void setAdminId(String adminId) {
                this.adminId = adminId;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public void setRole(String role) {
                this.role = role;
        }

        public void setAuthorities(String[] authorities) {
                this.authorities = authorities;
        }

        public Long getId() {
                return id;
        }

        public String getAdminId() {
                return adminId;
        }

        public String getFirstName() {
                return firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public String getUsername() {
                return username;
        }

        public String getPassword() {
                return password;
        }

        public String getEmail() {
                return email;
        }

        public String getRole() {
                return role;
        }

        public String[] getAuthorities() {
                return authorities;
        }
}

