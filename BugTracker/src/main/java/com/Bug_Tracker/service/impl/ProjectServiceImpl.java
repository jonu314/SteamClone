package com.Bug_Tracker.service.impl;

import com.Bug_Tracker.Model.Bug;
import com.Bug_Tracker.Model.Project;
import com.Bug_Tracker.Model.User;
import com.Bug_Tracker.dto.BugDTO;
import com.Bug_Tracker.dto.ProjectDTO;
import com.Bug_Tracker.dto.UserDTO;
import com.Bug_Tracker.repository.BugRepository;
import com.Bug_Tracker.repository.ProjectRepository;
import com.Bug_Tracker.repository.UserRepository;
import com.Bug_Tracker.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class ProjectServiceImpl implements ProjectService {
     private UserRepository userRepository;
     private ProjectRepository projectRepository;
     private BugRepository bugRepository;

     @Autowired
    ProjectServiceImpl(UserRepository userRepository, ProjectRepository projectRepository,BugRepository bugRepository){
         this.userRepository = userRepository;
         this.projectRepository = projectRepository;
         this.bugRepository = bugRepository;
     }

    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project addProject(String projectName, String projectDescription) {
        Project project = new Project();
        project.setProjectName(projectName);
        project.setProjectDescription(projectDescription);
        projectRepository.save(project);
        return project;
    }
// and user to a project
    @Override
    public Project assignProjectToUser(String projectName,String username) {
        Project project = projectRepository.findProjectByProjectName(projectName);
        User user = userRepository.findUserByUsername(username);
        user.getProject().add(project);
        project.getUsers().add(user);

        userRepository.save(user);
        projectRepository.save(project);
        return project;
    }

    @Override
    public Project findProjectByProjectName(String projectName) {
        return null;
    }

    @Override
    public List<ProjectDTO> listUserProjects(String username) {
        User user = userRepository.findUserByUsername(username);
        Long userId= user.getId();
        return projectRepository.findProjectsByUserId(userId);
    }


    @Override
    public Project assignProjectToBug(String projectName, String bugId) {
        Project p = projectRepository.findProjectByProjectName(projectName);
        Bug b = bugRepository.findBugByBugId(bugId);
        p.getBugs().add(b);
        return p;
    }



}
