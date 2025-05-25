package com.jobseekerpro.service;

import com.jobseekerpro.entity.Application;
import com.jobseekerpro.entity.Job;
import com.jobseekerpro.entity.AppUser;
import com.jobseekerpro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;


    public Application applyToJob(Long jobId, Long seekerId) {

        //validate job exists
        Optional<Job> jobOpt = jobRepository.findById(jobId);
        if(jobOpt.isEmpty()){
            throw new RuntimeException("Job with ID "+jobId+" not found");
        }
        Job job = jobOpt.get();

        //validate seeker exists
        Optional<AppUser> seekerOpt = userRepository.findById(seekerId);
        if(seekerOpt.isEmpty()){
            throw new RuntimeException("User with ID "+seekerId+" not found");
        }

        AppUser seeker=seekerOpt.get();

        //validate user role
        if(!"JOB_SEEKER".equals(seeker.getRole())){
            throw new RuntimeException("User with ID "+seekerId +" is not a job seeker");
        }
        
        //Create and save the application
        Application application = new Application();
        application.setJob(job);
        application.setSeeker(seeker);
        application.setStatus("PENDING");
        return applicationRepository.save(application);

    }

    // Get all applications
    public List<Application> getAllApplications(){
        return applicationRepository.findAll();
    }

    //Get applications by User id
    public List<Application> getApplicationsBySeeker(Long seekerId){
        return applicationRepository.findBySeekerId(seekerId);
    }

    //Get applications by job ID
    public List<Application> getApplicationsByJob(Long jobId){
        return applicationRepository.findByJobId(jobId);
    }

    //Update Application status
    public Application updateStatus(Long applicationId, String status){
        Optional<Application> applicationOpt = applicationRepository.findById(applicationId);
        if(applicationOpt.isEmpty()){
            throw new RuntimeException("Application with ID "+applicationId+" not found");
        }

        Application application = applicationOpt.get();

        //validate status
        if(!List.of("PENDING", "ACCEPTED", "REJECTED").contains(status)){
            throw new RuntimeException("Invalid status: "+ status + ". Must be PENDING, ACCEPTED, REJECTED");
        }
        application.setStatus(status);
        return applicationRepository.save(application);
    }

    //Delete an application
    public void deleteApplication(Long applicationId){
        Optional<Application> applicationOpt = applicationRepository.findById(applicationId);
        if(applicationOpt.isEmpty()){
            throw new RuntimeException("Application with ID "+applicationId+" not found");
        }

        applicationRepository.deleteById(applicationId);
    }
}
