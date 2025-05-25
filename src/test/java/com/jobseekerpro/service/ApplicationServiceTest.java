package com.jobseekerpro.service;

import com.jobseekerpro.entity.*;
import com.jobseekerpro.repository.UserRepository;
import com.jobseekerpro.repository.JobRepository;
import com.jobseekerpro.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApplicationServiceTest {
    
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testApplyToJob(){
        //Set up test data
        AppUser employer = userService.signup("employer@example.com", "pass123", "Employer", "EMPLOYER");
        AppUser seeker = userService.signup("seeker@example.com", "pass123", "Seeker", "JOB_SEEKER");
        Job job = new Job();
        job.setTitle("Software Engineer");
        job.setDescription("Develop apps");
        job.setLocation("New York");
        job.setEmployer(employer);
        job = jobRepository.save(job);

          System.out.println("Seeker ID = " + seeker.getId() + ", Role = " + seeker.getRole());


        //Apply to job
        Application application = applicationService.applyToJob(job.getId(),seeker.getId());
        assertNotNull(application);
        assertEquals(job.getId(),application.getJob().getId());
        assertEquals(seeker.getId(), application.getSeeker().getId());
    }

    @Test
    public void testApplyToJobWrongSeeker(){
        AppUser employer = userService.signup("employer2@example.com","pass123","Employer2","EMPLOYER");
        Job job = new Job();
        job.setTitle("Data Scientist");
        job.setDescription("Analyze data");
        job.setLocation("London");
        job.setEmployer(employer);
        Job savedjob = jobRepository.save(job);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            applicationService.applyToJob(savedjob.getId(), 999L);
        });
        assertEquals("User with ID 999 not found", exception.getMessage());
    }
}
