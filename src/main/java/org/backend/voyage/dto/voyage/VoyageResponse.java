package org.backend.voyage.dto.voyage;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.backend.voyage.model.Activity;
import org.backend.voyage.model.Enum.Transport;
import org.backend.voyage.model.Voyage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoyageResponse {
    @SneakyThrows
    public VoyageResponse(Voyage voyage){
        ObjectMapper mapper = new ObjectMapper();
        this.id = voyage.getId();
        this.Name=voyage.getName();
        this.Description=voyage.getDescription();
        this.transport = voyage.getTransport();
        this.nbr_per=voyage.getNbr_per();
        this.prix=voyage.getPrix();
        this.hebergement=(voyage.getHebergement()!=null)?voyage.getHebergement().getId():null;
        this.ville=voyage.getVille();
        this.date_debut=voyage.getDate_debut();
        this.date_fin=voyage.getDate_fin();
        List<Long> lis=null;
        if (voyage.getActivity() != null) {
            lis = new ArrayList<>();
            for (Activity i : voyage.getActivity()) {
                lis.add(i.getId());
            }
        }

        this.activity=lis;
        List<String> img = new ArrayList<>();
        if (voyage.getImage() != null && !voyage.getImage().isEmpty()) {
            img = List.of(mapper.readValue(voyage.getImage(), String[].class));
        }
        this.image=img;
        this.etat=voyage.isEtat();
        nbr_reseve=(voyage.getReservations().isEmpty())?0:voyage.getReservations().size();
    }
    private Long id;
    private String Name;
    private String Description;
    private Transport transport;
    private Long nbr_per;
    private Date date_debut;
    private Date date_fin;
    private double prix;
    private Long hebergement;
    private String ville;
    private boolean etat;
    private int nbr_reseve;
    private List<Long> activity;
    private List<String> image;
}
