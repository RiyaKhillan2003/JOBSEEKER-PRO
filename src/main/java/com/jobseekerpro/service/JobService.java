package com.jobseekerpro.service;

import com.jobseekerpro.entity.Job;
import com.jobseekerpro.entity.AppUser;
import com.jobseekerpro.repository.JobRepository;
import com.jobseekerpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired UserRepository userRepository;

    public Job createJob(String title, String description, String location, Long employerId){
        AppUser employer = userRepository.findById(employerId).orElseThrow(() -> new RuntimeException("Employer not found"));
        Job job = new Job();
        job.setTitle(title);
        job.setDescription(description);
        job.setLocation(location);
        job.setEmployer(employer);
        return jobRepository.save(job);
    }

    public List<Job> searchJobs(String keyword){
        return jobRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
