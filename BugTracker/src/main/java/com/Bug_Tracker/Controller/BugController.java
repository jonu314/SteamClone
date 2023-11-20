package com.Bug_Tracker.Controller;
import com.Bug_Tracker.Model.Bug;
import com.Bug_Tracker.dto.BugDTO;
import com.Bug_Tracker.repository.BugRepository;
import com.Bug_Tracker.repository.ProjectRepository;
import com.Bug_Tracker.service.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping(path = {"/" ,"/bug"})
@CrossOrigin("http://localost:4200")
public class BugController
{
        private BugService bugService;
    private ProjectRepository projectRepository;
    private BugRepository bugRepository;

        
        @Autowired
        public BugController(BugService bugService, ProjectRepository projectRepository, BugRepository bugRepository) {
            this.bugService = bugService;
            this.projectRepository = projectRepository;
            this.bugRepository = bugRepository;


        }
        @PostMapping("/listProjectBugs")
        public List<BugDTO> listProjectBugs(@RequestParam("projectName") String projectName){
        return bugService.listProjectBugs(projectName);
    }


        @PostMapping("/addBug")
     //  @PreAuthorize("hasAnyAuthority('user:create')")
        public List<BugDTO> addNewBug(@RequestParam("bugDescription") String description,
                                      @RequestParam("bugType") String bugType,
                                      @RequestParam("bugLocation") String bugLocation,
                                      @RequestParam("bugPriority") String priority,
                                      @RequestParam("isActive") String isActive,
                                      @RequestParam("projectName") String projectName

                                                )
                 {


           return bugService.addNewBug (description, bugType, bugLocation, priority, Boolean.parseBoolean(isActive), projectName);



        }


        @PostMapping("/updateBug")
       @PreAuthorize("hasAnyAuthority('user:update')")
        public Bug updateBug(@RequestParam("bugId") String bugId,
                                             @RequestParam("bugDescription") String bugDescription,
                                             @RequestParam("bugLocation") String bugLocation,
                                             @RequestParam("bugPriority") String bugPriority,
                                             @RequestParam("bugType") String bugType,
                                             @RequestParam("isActive") String isActive)
        {
           return bugService.updateBug(bugId,bugDescription,bugLocation,bugPriority, bugType, Boolean.parseBoolean(isActive));

        }




    @GetMapping("/list")
    public List<Bug> getAllBugs() {
        return bugService.getBugs();

    }

    @DeleteMapping("/delete/{bugId}")
   @PreAuthorize("hasAnyAuthority('user:delete')")
    public String deleteBug(@PathVariable("bugId") String id) throws AccessDeniedException {
         return bugService.deleteBug(id);

    }





}


