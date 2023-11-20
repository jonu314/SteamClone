package com.Bug_Tracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProjectDTO {


        private String projectName;
        private String projectDescription;

        public ProjectDTO(String projectName, String projectDescription) {
            this.projectName = projectName;
            this.projectDescription = projectDescription;
        }

}
