package com.example.fitplus.workoutdetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutDetailsRepository extends JpaRepository<WorkoutDetails, Long>
{
    boolean existsByWorkoutIdAndExerciseId(long workoutId, long exerciseId);

    Optional<WorkoutDetails> findByWorkoutIdAndExerciseId(long workoutId, long exerciseId);

    List<WorkoutDetails> findAllByWorkoutId(long workoutId);
}
