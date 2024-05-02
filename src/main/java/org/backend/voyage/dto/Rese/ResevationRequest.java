package org.backend.voyage.dto.Rese;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ResevationRequest(
        @NotNull
        Long User,
         @NotNull
         Long voyage
) {
}
