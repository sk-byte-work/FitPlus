package com.example.fitplus.set;

import jakarta.validation.constraints.NotNull;

public record SetRequestDTO(@NotNull Integer weight, @NotNull Integer reps) {
}
