package org.backend.voyage.dto.voyage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.backend.voyage.model.Enum.Transport;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public record Voyagerequest(
        @NotEmpty
        String Name,
        @NotEmpty
        String Description,
        @NotEmpty
        String transport,
        @Positive
        Long nbr_per,
        @NotEmpty
        String date_fin,
        @Positive
        Double prix,
        @NotEmpty
        String ville,
        Long hebergement,
        List<Long> activity,
        List<MultipartFile> image,
        String date_debut
) {

}
