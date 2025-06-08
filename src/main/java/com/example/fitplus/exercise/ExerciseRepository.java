package com.example.fitplus.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long>
{
    List<Exercise> findAllByUserId(Long userID);

    boolean existsByIdAndUserId(long id, long userId);

    Optional<Exercise> findByIdAndUserId(long id, long userId);
}
