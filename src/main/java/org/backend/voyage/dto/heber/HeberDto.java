package org.backend.voyage.dto.heber;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record HeberDto(
        @NotEmpty
         String nom,
        @NotEmpty
         String ville,
        String website

) {
}
