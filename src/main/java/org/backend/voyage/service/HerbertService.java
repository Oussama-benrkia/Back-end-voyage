package org.backend.voyage.service;

import lombok.RequiredArgsConstructor;
import org.backend.voyage.dto.heber.HeberDto;
import org.backend.voyage.dto.heber.HeberResponse;
import org.backend.voyage.dto.heber.HeberUpDto;
import org.backend.voyage.model.Hebergement;
import org.backend.voyage.rep.IntRepHeberg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HerbertService implements IntHerberService {
    private final IntRepHeberg repHeber;
    @Override
    public HeberResponse find_Heber(Long id) {
        Hebergement hebergement=repHeber.findById(id).orElse(null);
        HeberResponse response = null;
        if (hebergement != null) {
            response=new HeberResponse(hebergement);
        }
        return response;
    }

    @Override
    public HeberResponse Create_Post(HeberDto p) {
        var hebergement = Hebergement.builder()
                .nom(p.nom())
                .ville(p.ville())
                .build();
        Hebergement h1 = repHeber.save(hebergement); // corrected assignment and added semicolon
        if (h1 != null) {
            return new  HeberResponse(h1);
        }
        return null;
    }


    @Override
    public HeberResponse Update_Post(HeberUpDto p, Long id) {
        Hebergement heberg=repHeber.findById(id).orElse(null);
        if(heberg!=null){
            if(!p.nom().isEmpty()){
                heberg.setVille(p.ville());
            }
            if(!p.ville().isEmpty()){
                heberg.setNom(p.nom());
            }
            Hebergement h1 = repHeber.save(heberg);
            if (h1 != null) {
                return new HeberResponse(h1);
            }
        }
        return null;
    }

    @Override
    public HeberResponse Delete_Post(Long id) {
        Hebergement heberg=repHeber.findById(id).orElse(null);
        if(heberg!=null){
            repHeber.delete(heberg);
            return new HeberResponse(heberg);

        }
        return null;
    }

    @Override
    public Page<HeberResponse> list_Heber(Pageable p,String re) {
        Page<Hebergement> posts = null;
        if(!re.isEmpty()){
            posts=repHeber.findAllByNomContainingOrVilleContaining(re,re,p);

        }else{
            posts=repHeber.findAll(p);
        }
        return  posts.map(HeberResponse::new);
    }

    @Override
    public List<HeberResponse> list_heber_by_serch(String search) {
        List<Hebergement> hebergements =null;
        if(!search.isEmpty()){
            hebergements= repHeber.findAllByNomContaining(search);

        }else {
            hebergements= repHeber.findAll();
        }
        return hebergements.stream()
                .map(hebergement -> {
                    HeberResponse response = new HeberResponse();
                    response.setId(hebergement.getId());
                    response.setNom(hebergement.getNom());
                    response.setVille(hebergement.getVille());
                    return response;
                })
                .collect(Collectors.toList());
    }
}
