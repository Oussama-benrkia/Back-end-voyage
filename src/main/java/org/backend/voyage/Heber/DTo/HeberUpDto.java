package org.backend.voyage.Heber.DTo;

import jakarta.validation.constraints.NotNull;

public record HeberUpDto(
        @NotNull
        String nom,
        @NotNull
        String ville,
        @NotNull
        String website
) {
}
