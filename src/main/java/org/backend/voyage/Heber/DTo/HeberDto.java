package org.backend.voyage.Heber.DTo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record HeberDto(
        @NotEmpty
         String nom,
        @NotEmpty
         String ville,
        @NotNull
        String website

) {
}
