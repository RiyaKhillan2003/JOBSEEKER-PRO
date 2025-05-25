package com.jobseekerpro.controller;

import com.jobseekerpro.entity.Application;
import com.jobseekerpro.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    //Apply to a job
    @PostMapping("{jobId}")
    public ResponseEntity<?> applyToJob(@PathVariable Long jobId, @RequestBody ApplicationRequest request){
        try{
            Application application = applicationService.applyToJob(jobId, request.getSeekerId());
            return ResponseEntity.ok(application);
        } catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Get all applications
    @GetMapping
    public List<Application> getAllApplications(){
        return applicationService.getAllApplications();
    }

    //Get applications by seeker
    @GetMapping("/seeker/{seekerId}")
    public List<Application> getApplicationsBySeeker(@PathVariable Long seekerId){
        return applicationService.getApplicationsBySeeker(seekerId);
    }

    //Get applications by job
    @GetMapping("/job/{jobId}")
    public List<Application> getApplicationsByJob(@PathVariable Long jobId){
        return applicationService.getApplicationsByJob(jobId);
    }

    //Update application status
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long applicationId,@RequestParam String status){
        try{
            Application updatedApplication = applicationService.updateStatus(applicationId, status);
            return ResponseEntity.ok(updatedApplication);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Delete an application
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<?> deleteApplication(@PathVariable Long applicationId){
        try{
            applicationService.deleteApplication(applicationId);
            return ResponseEntity.ok("Application deleted successfully");
        }
        catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}

class ApplicationRequest {
    private Long seekerId;

    public Long getSeekerId() {
        return seekerId;
    }
    public void setSeekeriD(Long seekerId){
        this.seekerId=seekerId;
    }
}
