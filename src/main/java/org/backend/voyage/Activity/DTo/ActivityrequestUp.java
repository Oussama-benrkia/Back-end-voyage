package org.backend.voyage.Activity.DTo;

import jakarta.validation.constraints.NotNull;

public record ActivityrequestUp(
        @NotNull
        String nom,
        @NotNull
        String ville,
        @NotNull
        String type
) {
}
