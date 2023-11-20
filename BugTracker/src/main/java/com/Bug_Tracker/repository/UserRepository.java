package com.Bug_Tracker.repository;

import com.Bug_Tracker.Model.User;
import com.Bug_Tracker.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

// spring is smart enough to know to build a query based off of findUserByUsername etc
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);
    User findUserByEmail(String username);
    @Query(value = "SELECT new com.Bug_Tracker.dto.UserDTO(u.username, u.firstName, u.lastName, u.email, u.role) FROM User u JOIN u.project p WHERE p.id = :idProject")
    List<UserDTO> findUsersByProjectId(Long idProject);

}
