package com.Bug_Tracker.service;

import com.Bug_Tracker.Model.Bug;
import com.Bug_Tracker.dto.BugDTO;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

public interface BugService {

    List<Bug> getBugs();
   void assignProjectToBug(String projectName, String bugId);
    Bug findBugByBugId(String id);

    List<BugDTO> addNewBug(String description,String bugType,String bugLocation,String priority, boolean isActive,String projectName) ;

    List<BugDTO> listProjectBugs(String bugId);
    Bug updateBug(String bugId,String bugDescription,String bugLocation, String bugPriority, String bugType, boolean newIsActive);

    String deleteBug(String id) throws AccessDeniedException;

}
