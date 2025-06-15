package com.example.fitplus.set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SetRepository extends JpaRepository<ExerciseSet, Long>
{
    @Query("SELECT es FROM ExerciseSet es WHERE es.workoutDetails.workoutDetailsId= :workoutDetailsId and es.setId = :setId")
    Optional<ExerciseSet> findByWorkoutDetailsIdAndSetId(long workoutDetailsId, long setId) throws Exception;

    List<ExerciseSet> findAllByWorkoutDetailsWorkoutDetailsId(long workoutDetailsId);
}
