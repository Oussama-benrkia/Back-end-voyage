package org.backend.voyage.dto.voyage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record VoyagerequestUp(
        String Name,
        String Description,
        String transport,
        Long nbr_per,
        String date_fin,
        Double prix,
        String ville,
        Long hebergement,
        List<Long> activity,
        List<MultipartFile> image,
        String date_debut,
        Boolean etat,
        Boolean exist
) {

}
