package org.backend.voyage.Activity.Service;

import lombok.RequiredArgsConstructor;
import org.backend.voyage.Activity.DTo.ActivityResponse;
import org.backend.voyage.Activity.DTo.Activityrequest;
import org.backend.voyage.Activity.DTo.ActivityrequestUp;
import org.backend.voyage.Activity.Model.Activity;
import org.backend.voyage.Activity.Model.Enum.Type_Acti;
import org.backend.voyage.Activity.Rep.IntRepActivi;
import org.backend.voyage.Heber.DTo.HeberResponse;
import org.backend.voyage.Heber.Model.Hebergement;
import org.backend.voyage.User.Model.Enum.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final IntRepActivi repActivi;
    public List<String> getalltype(){
        return enumToList(Type_Acti.values());
    }
    private   <T extends Enum<T>> List<String> enumToList(T[] enumValues) {
        List<String> list = new ArrayList<>();
        for (T enumValue : enumValues) {
            list.add(enumValue.name());
        }
        return list;
    }
    public ActivityResponse find_activi(Long id) {
        Activity act=repActivi.findById(id).orElse(null);
        if(act!=null) {
            return new ActivityResponse(act);
        }
        return null;
    }
    public Page<ActivityResponse> list_activi(Pageable pageable, String search) {
        Page<Activity> list = null;
        if(!search.isEmpty()){
            list=repActivi.findAllByNomContaining(search,pageable);

        }else{
            list=repActivi.findAll(pageable);
        }
        return  list.map(ActivityResponse::new);
    }



    public List<ActivityResponse> list_acti_by_serch(String search) {
        List<Activity> list =null;
        if(!search.isEmpty()){
            list=repActivi.findAllByNomContaining(search);
        }else {
            list= repActivi.findAll();
        }
        return list.stream()
                .map(acti -> {
                    ActivityResponse response = new ActivityResponse();
                    response.setId(acti.getId());
                    response.setNom(acti.getNom());
                    response.setVille(acti.getVille());
                    response.setType(acti.getType());
                    return response;
                })
                .collect(Collectors.toList());    }

    public ActivityResponse Update_acti(ActivityrequestUp activityrequest, Long id) {
        Activity act=repActivi.findById(id).orElse(null);
        if(act!=null){
            if(!activityrequest.nom().isEmpty()){
                act.setVille(activityrequest.ville());
            }
            if(!activityrequest.ville().isEmpty()){
                act.setNom(activityrequest.nom());
            }
            if(!activityrequest.type().isEmpty()){
                act.setType(getTyperole(activityrequest.type()));
            }
            Activity ac = repActivi.save(act);
            if (ac != null) {
                return new ActivityResponse(ac);
            }
        }
        return null;
    }

    public ActivityResponse Delete_acti(Long id) {
        Activity act=repActivi.findById(id).orElse(null);
        if(act!=null){
            repActivi.delete(act);
            return new ActivityResponse(act);
        }
        return null;
    }

    public ActivityResponse Create_acti(Activityrequest activityrequest) {
        var activity =Activity.builder()
                .nom(activityrequest.nom())
                .ville(activityrequest.ville())
                .type(getTyperole(activityrequest.type())).build();
        Activity ac = repActivi.save(activity); // corrected assignment and added semicolon
        if (ac != null) {
            return  new ActivityResponse(ac);
        }
        return null;
    }
    private Type_Acti getTyperole(String input) {
        for (Type_Acti type : Type_Acti.values()) {
            if (type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant found for input: " + input);
    }
}
