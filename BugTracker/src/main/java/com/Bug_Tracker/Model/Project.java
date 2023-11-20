package com.Bug_Tracker.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="project_name")
    java.lang.String projectName;
    @Column(name="project_description")
    java.lang.String projectDescription;

   public Project convert(Object[] o){
        String projectName = (String) o[0];
        String projectDescription = (String) o[1];
        Project p = new Project(projectName, projectDescription);
        return p;
    }

    public Project(){}
    public Project(Project o){
        o.setProjectName(projectName);
        o.setProjectDescription(projectDescription);
    }

    Project(String projectName, String projectDescription){
       // this.id = id;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "project")
    private Set<User> users =new HashSet<>();

   // many bugs have one project
   @JsonIgnore
   @OneToMany
   @JoinColumn( name = "project_fk", referencedColumnName = "id")
   private Set<Bug> bugs = new HashSet<>();

    public Set<Bug> getBugs() {
        return bugs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
    public Set<User> getUsers(){
        return users;
    }
}
