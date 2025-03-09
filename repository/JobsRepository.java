package com.jobentry.repository;

import com.jobentry.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepository extends JpaRepository<Jobs, Integer> {

    @Query(value = "SELECT * FROM jobs j ORDER BY j.posted_date DESC LIMIT 10", nativeQuery = true)
    public List<Jobs> getAllJobs();

    @Query(value = "SELECT * FROM jobs j ORDER BY j.posted_date", nativeQuery = true)
    public List<Jobs> getAll();

    @Query(value = "SELECT * FROM jobs j WHERE j.job_type='Full-Time' ORDER BY j.posted_date DESC LIMIT 10", nativeQuery = true)
    public List<Jobs> getFullTimeJobs();

    @Query(value = "SELECT * FROM jobs j WHERE j.job_type='Part-Time' ORDER BY j.posted_date DESC LIMIT 10", nativeQuery = true)
    public List<Jobs> getParttimeJobs();

    @Query(value = "SELECT * FROM jobs j WHERE j.job_type='Remote' ORDER BY j.posted_date DESC LIMIT 10", nativeQuery = true)
    public List<Jobs> getRemote();

    @Query(value = "SELECT * FROM jobs j WHERE j.categoryID=?", nativeQuery = true)
    public List<Jobs> getJobsByCategory(@Param("id") int id);

    @Query(value = "SELECT * FROM jobs j WHERE j.jobsID=?", nativeQuery = true)
    public Jobs getJobDetails(@Param("id") int id);

    @Query(value = "SELECT j FROM Jobs j WHERE j.jobTitle LIKE %:job% AND j.category.categoryName LIKE %:category% AND " +
            "j.location LIKE %:location%", nativeQuery = false)
    public List<Jobs> searchJobs(@Param("job") String job,
                                 @Param("category") String category,
                                 @Param("location") String location);

    @Query(value = "SELECT * FROM Jobs j WHERE j.posted_by_id = ?", nativeQuery = true)
    public List<Jobs> getJobsByUser(int userID);

}
