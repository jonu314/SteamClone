package com.Bug_Tracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class BugDTO {
    private String bugId;
    private String bugDescription;
    private String bugType;
    private String bugLocation;
    private String bugPriority;
    private Date bugDate;
    @JsonProperty(value="isActive")
    private boolean isActive;
    public BugDTO(){}
    public BugDTO(String bugId, String bugDescription, String bugLocation, String bugPriority, String bugType, Date bugDate,boolean isActive) {

        this.bugId = bugId;
        this.bugDescription = bugDescription;
        this.bugLocation = bugLocation;
        this.bugPriority = bugPriority;
        this.bugDate = bugDate;
        this.isActive = isActive;
        this.bugType = bugType;

    }
}
