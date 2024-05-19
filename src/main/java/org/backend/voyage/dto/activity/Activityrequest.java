package org.backend.voyage.dto.activity;

import jakarta.validation.constraints.NotEmpty;

public record Activityrequest(
        @NotEmpty
        String nom,
        @NotEmpty
        String ville,
        @NotEmpty
        String type
) {
}
