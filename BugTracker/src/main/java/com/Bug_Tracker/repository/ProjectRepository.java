package com.Bug_Tracker.repository;

import com.Bug_Tracker.Model.Project;
import com.Bug_Tracker.dto.BugDTO;
import com.Bug_Tracker.dto.ProjectDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findProjectByProjectName(String projectName);

    @Query(value = "SELECT new com.Bug_Tracker.dto.ProjectDTO(p.projectName, p.projectDescription) FROM Project p JOIN p.users u WHERE u.id = :idUser")
    List<ProjectDTO> findProjectsByUserId(Long idUser);


      @Query(value = "SELECT new com.Bug_Tracker.dto.BugDTO(b.bugId, b.bugDescription, b.bugLocation, b.bugPriority, b.bugType,b.bugDate,b.isActive) FROM Project p JOIN p.bugs b WHERE p.id = :projectId")
    List<BugDTO> findBugsByProjectName(Long projectId);

}
