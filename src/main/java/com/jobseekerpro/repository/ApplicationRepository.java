package com.jobseekerpro.repository;

import com.jobseekerpro.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long>{
    List<Application> findBySeekerId(Long userId);
    List<Application> findByJobId(Long jobId);

}
