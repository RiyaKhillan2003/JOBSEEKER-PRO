package com.jobseekerpro.controller;

import com.jobseekerpro.entity.Job;
import com.jobseekerpro.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping
    public Job createJob(@RequestBody JobRequest request) {
        return jobService.createJob(
            request.getTitle(),
            request.getDescription(),
            request.getLocation(),
            request.getEmployerId()
        );
    }

    @GetMapping("/search")
    public List<Job> searchJobs(@RequestParam String keyword){
        return jobService.searchJobs(keyword);
    }
}

class JobRequest{
    private String title;
    private String description;
    private String location;
    private Long employerId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
     public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

     public String getLocation() {
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

     public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId){
        this.employerId = employerId;
    }
}
