package org.backend.voyage.Heber.DTo;

import jakarta.validation.constraints.NotNull;

public record HeberDto(
        @NotNull
         String nom,
        @NotNull
         String ville
) {
}
