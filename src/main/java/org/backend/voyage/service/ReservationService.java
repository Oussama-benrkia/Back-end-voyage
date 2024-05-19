package org.backend.voyage.service;

import lombok.RequiredArgsConstructor;
import org.backend.voyage.Util.Generator;
import org.backend.voyage.dto.Rese.ResevationRequest;
import org.backend.voyage.dto.Rese.ResevationResponse;
import org.backend.voyage.model.Reservation;
import org.backend.voyage.model.User;
import org.backend.voyage.model.Voyage;
import org.backend.voyage.rep.IntRepRese;
import org.backend.voyage.rep.IntRepUser;
import org.backend.voyage.rep.IntRepVoyage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final IntRepVoyage repVoyage;
    private final IntRepUser repUser;
    private final IntRepRese repRese;
    private final Generator generator;
    public List<ResevationResponse> affiche_reservation(String dt1, String dt2){
        List<Reservation> ls;
        if(!dt1.isEmpty() && !dt2.isEmpty()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss");
            LocalDateTime date1 = LocalDateTime.parse(dt1+"T12:00:00", formatter);
            LocalDateTime date2 = LocalDateTime.parse(dt2+"T12:00:00", formatter);
            ls = repRese.findAllByCreatedAtBetween(date1, date2);

        }else {
            ls=repRese.findAll();
        }
        return ls.stream()
                .map(r -> {
                    ResevationResponse res = ResevationResponse.builder().id(r.getId())
                            .date(r.getCreatedAt())
                            .User(r.getUser().getId())
                            .nom_voyage(r.getVoyage().getName())
                            .prix(r.getVoyage().getPrix())
                            .voyage(r.getVoyage().getId()).build();
                    return res;
                })
                .collect(Collectors.toList());
    }
    public Page<ResevationResponse> affiche_reservation_pagination(Pageable P){
        Page<Reservation> ls=repRese.findAll(P);
        return ls.map(ResevationResponse::new);
    }

    public List<ResevationResponse> affiche_reservation_User(User user){
        List<Reservation> ls=repRese.findAllByUser(user);
        return ls.stream()
                .map(r -> {
                    ResevationResponse res = ResevationResponse.builder().id(r.getId())
                            .date(r.getCreatedAt())
                            .User(r.getUser().getId())
                            .nom_voyage(r.getVoyage().getName())
                            .prix(r.getVoyage().getPrix())
                            .voyage(r.getVoyage().getId()).build();
                    return res;
                })
                .collect(Collectors.toList());
    }
    public Page<ResevationResponse> affiche_reservation_User_pagination(User user,Pageable pa){
        Page<Reservation> ls=repRese.findAllByUser(user,pa);
        return ls.map(ResevationResponse::new);
    }

    public ResevationResponse ajouter_reservation(ResevationRequest r){
        User user=repUser.findById(r.User()).orElse(null);
        Voyage voyage=repVoyage.findById(r.voyage()).orElse(null);
        if(voyage!=null && user!=null){
            String gen=generator.genrator(user.getId(),voyage.getId());
            Reservation t=Reservation.builder().generator(gen).user(user).voyage(voyage).build();
            Reservation res=repRese.save(t);
            return new ResevationResponse(res);
        }
        return null;
    }
    public ResevationResponse delete_reservation(Long l){
        Reservation res=repRese.findById(l).orElse(null);
        if(repRese!=null){
            repRese.delete(res);
            return new ResevationResponse(res);
        }
        return null;
    }
    public ResevationResponse find_reservation(Long l){
        Reservation res=repRese.findById(l).orElse(null);
        if(repRese!=null){
            return new ResevationResponse(res);
        }
        return null;
    }
}
