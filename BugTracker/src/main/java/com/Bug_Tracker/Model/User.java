package com.Bug_Tracker.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String user_id; // this is not same as joincolumn user_id, user_id from join is referring to my long id
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String role;
    private String[] authorities;
    private boolean is_active;


  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "user_projects",
          joinColumns = { @JoinColumn(name = "user_id")}, // both these keys are foreign keys
          inverseJoinColumns = { @JoinColumn (name = "project_id")})
        private Set<Project> project = new HashSet<>();

    public User setProject(Set<Project> project){
        this.project = project;
        return this;
    }

    public User(){}

    public User(Long id, String user_id, String first_name, String last_name, String username, String password, String email,  String role, String[] authorities, boolean is_active, Set<Project> project,Project projects) {
        this.id = id;
        this.user_id = user_id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.username = username;
        this.password = password;
        this.email = email;

        this.role = role;
        this.authorities = authorities;
        this.is_active = is_active;
        this.project= project;

    }



    public Set<Project> getProject() {
        return project;
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    public boolean isActive() {
        return is_active;
    }

    public void setActive(boolean active) {
        is_active = active;
    }

}
