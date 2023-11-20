package com.Bug_Tracker.service;

import com.Bug_Tracker.Model.Bug;
import com.Bug_Tracker.Model.Project;
import com.Bug_Tracker.dto.BugDTO;
import com.Bug_Tracker.dto.ProjectDTO;
import com.Bug_Tracker.dto.UserDTO;

import java.util.List;
import java.util.Set;

public interface ProjectService {
    List<Project> getProjects();
    Project addProject(String projectName, String projectDescription);
    Project assignProjectToUser(String projectName, String username);
    Project findProjectByProjectName(String projectName);
    List<ProjectDTO> listUserProjects(String username);

    Project assignProjectToBug(String projectName, String bugId);
}
