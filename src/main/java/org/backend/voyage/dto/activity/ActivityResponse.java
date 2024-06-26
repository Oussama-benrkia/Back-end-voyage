package org.backend.voyage.dto.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.backend.voyage.model.Activity;
import org.backend.voyage.model.Enum.Type_Acti;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityResponse {
    private Long id;
    private String nom;
    private String ville;
    private Type_Acti type;
    public ActivityResponse(Activity a) {
        this.id = a.getId();
        this.nom = a.getNom();
        this.ville = a.getVille();
        this.type = a.getType();
    }
}
