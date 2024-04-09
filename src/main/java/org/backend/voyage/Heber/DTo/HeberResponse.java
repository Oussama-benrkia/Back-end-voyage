package org.backend.voyage.Heber.DTo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.backend.voyage.Heber.Model.Hebergement;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeberResponse {
    private Long id;
    private String nom;
    private String ville;
    public HeberResponse(Hebergement h){
        this.id=h.getId();
        this.ville=h.getVille();
        this.nom=h.getNom();
    }
}
