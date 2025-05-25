package com.jobseekerpro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Job job;
    @ManyToOne
    private AppUser seeker;
    private String status; //PENDING, ACCEPTED, REJECTED

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Job getJob(){
        return job;
    }

    public void setJob(Job job){
        this.job = job;
    }

    public AppUser getSeeker(){
        return seeker;
    }

    public void setSeeker(AppUser seeker){
        this.seeker = seeker;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

}
