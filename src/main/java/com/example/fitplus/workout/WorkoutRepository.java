package com.example.fitplus.workout;

import com.example.fitplus.WorkOutStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long>
{
    boolean existsByStatus(WorkOutStatus workOutStatus);
    List<Workout> findByUserId(long userId);
}
