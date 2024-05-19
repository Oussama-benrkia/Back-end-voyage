package org.backend.voyage.dto.heber;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.backend.voyage.model.Hebergement;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeberResponse {
    private Long id;
    private String nom;
    private String ville;
    private String website;

    public HeberResponse(Hebergement h){
        this.id=h.getId();
        this.ville=h.getVille();
        this.nom=h.getNom();
        this.website=h.getWebsite();
    }
}
