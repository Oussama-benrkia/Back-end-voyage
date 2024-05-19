package org.backend.voyage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.backend.voyage.Util.ImgService;
import org.backend.voyage.dto.voyage.VoyageResponse;
import org.backend.voyage.dto.voyage.Voyagerequest;
import org.backend.voyage.dto.voyage.VoyagerequestUp;
import org.backend.voyage.model.Activity;
import org.backend.voyage.model.Enum.Transport;
import org.backend.voyage.model.Hebergement;
import org.backend.voyage.model.Voyage;
import org.backend.voyage.rep.IntRepActivi;
import org.backend.voyage.rep.IntRepHeberg;
import org.backend.voyage.rep.IntRepVoyage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoyageService {
    private final IntRepVoyage RepVoyage;
    private final ImgService imgService;
    private final IntRepHeberg intRepHeberg;
    private final IntRepActivi intRepActivi;

    public List<String> getalltransp(){
        return enumToList(Transport.values());
    }
    private   <T extends Enum<T>> List<String> enumToList(T[] enumValues) {
        List<String> list = new ArrayList<>();
        for (T enumValue : enumValues) {
            list.add(enumValue.name());
        }
        return list;
    }
    public VoyageResponse find_transp(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Voyage act=RepVoyage.findById(id).orElse(null);
        if(act!=null) {
            if(act.isEtat()){
                if (authentication != null && authentication.isAuthenticated()) {
                    return new VoyageResponse(act);
                }
            }else{
                return new VoyageResponse(act);
            }
        }
        return null;
    }
    private Transport getTypetrans(String input) {
        for (Transport type : Transport.values()) {
            if (type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant found for input: " + input);
    }
    @SneakyThrows
    public VoyageResponse Delete_voyage(Long id) {
        Voyage act=RepVoyage.findById(id).orElse(null);
        if(act!=null){
            if(!act.getImage().isEmpty()){
                ObjectMapper mapper = new ObjectMapper();
                List<String> image=List.of(mapper.readValue(act.getImage(), String[].class));
                for(String img:image){
                    imgService.deleteimage(img);
                }
            }
            RepVoyage.delete(act);
            return new VoyageResponse(act);
        }
        return null;
    }
    @SneakyThrows
    public VoyageResponse Create_voyage(Voyagerequest voyager) throws ParseException {
        String images="";
        ObjectMapper obj=new ObjectMapper();
        List<Activity> ls=null;
        if(voyager.activity()!=null && !voyager.activity().isEmpty()){
                ls=new ArrayList<>();
                for(Long i : voyager.activity()){
                    Activity act =intRepActivi.findById(i).orElse(null);
                    if(act!=null){
                        ls.add(act);
                    }
                }
        }
        Hebergement h=null;
        if(voyager.hebergement()!=null){
            h=intRepHeberg.findById(voyager.hebergement()).orElse(null);
        }
        if(voyager.image()!=null && !voyager.image().isEmpty()){

            List<String> all=new ArrayList<>();
            for (MultipartFile i : voyager.image()){
                all.add(imgService.addimage(i,"Voyages"));
            }
            images=obj.writeValueAsString(all);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date =(voyager.date_debut()!=null)? formatter.parse(voyager.date_debut()):new Date();

        var activity =Voyage.builder()
                .Name(voyager.Name())
                .Description(voyager.Description())
                .transport(getTypetrans(voyager.transport()))
                .nbr_per(voyager.nbr_per())
                .date_debut(date)
                .date_fin(formatter.parse(voyager.date_fin()))
                .prix(voyager.prix())
                .hebergement(h)
                .ville(voyager.ville())
                .image(images)
                .activity(ls)
                .etat(false)
                .build();
        Voyage ac = RepVoyage.save(activity); // corrected assignment and added semicolon
        return new VoyageResponse(ac);
    }
    @SneakyThrows
    public VoyageResponse Update_voyage(VoyagerequestUp voyager,Long id) throws ParseException {
        Voyage voy=RepVoyage.findById(id).orElse(null);
        if(voy!=null){
            ObjectMapper obj=new ObjectMapper();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            if(voyager.Name()!=null && !voyager.Name().isEmpty()){
                voy.setName(voyager.Name());
            }
            if(voyager.etat()!=null){
                voy.setEtat(voyager.etat());
            }
            if(voyager.Description()!=null && !voyager.Description().isEmpty()){
                voy.setDescription(voyager.Description());
            }
            if(voyager.transport()!=null && !voyager.transport().isEmpty()){
                voy.setTransport(getTypetrans(voyager.transport()));
            }
            if(voyager.nbr_per()!=null){
                voy.setNbr_per(voyager.nbr_per());
            }
            if(voyager.date_debut()!=null&& !voyager.date_debut().isEmpty()){
                voy.setDate_debut(formatter.parse(voyager.date_debut()));
            }
            if(voyager.date_fin()!=null && !voyager.date_fin().isEmpty()){
                voy.setDate_fin(formatter.parse(voyager.date_fin()));
            }
            if (voyager.prix()!= null) { // Accessing the prix field directly
                voy.setPrix(voyager.prix());
            }
            if(voyager.hebergement()!=null){
                Hebergement h=intRepHeberg.findById(voyager.hebergement()).orElse(null);
                voy.setHebergement(h);
            }
            if(voyager.image()!=null && !voyager.image().isEmpty()){
                if(voyager.exist()!=null&&voyager.exist()){
                    String images="";
                    if(!voy.getImage().isEmpty()){

                        ObjectMapper mapper = new ObjectMapper();
                        List<String> image=List.of(mapper.readValue(voy.getImage(), String[].class));
                        for(String img:image){
                            imgService.deleteimage(img);
                        }
                    }
                    List<String> all=new ArrayList<>();
                    for (MultipartFile i : voyager.image()){
                        all.add(imgService.addimage(i,"Voyages"));
                    }
                    images=obj.writeValueAsString(all);
                    voy.setImage(images);
                }
            }
            if(voyager.activity()!=null && !voyager.activity().isEmpty()){
                List<Activity> ls=new ArrayList<>();
                for(Long i : voyager.activity()){
                    Activity act =intRepActivi.findById(i).orElse(null);
                    if(act!=null){
                        ls.add(act);
                    }
                }
                voy.setActivity(ls);
            }
            if(voyager.ville()!=null && !voyager.ville().isEmpty()){
                voy.setVille(voyager.ville());
            }
            Voyage ac = RepVoyage.save(voy);
            if (ac != null) {
                return new VoyageResponse(ac);
            }
        }
        return null;
    }
    public List<VoyageResponse> voyages(String ser,String ville,String date_debut,String date_fin){
        List<Voyage> ls;

        if (!ser.isEmpty() || !ville.isEmpty() || !date_debut.isEmpty() || !date_fin.isEmpty()) {
            ls = RepVoyage.findAll((Specification<Voyage>) (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (!ser.isEmpty()) {
                    Predicate serPredicate = criteriaBuilder.or(
                            criteriaBuilder.like(root.get("Name"), "%" + ser.toLowerCase() + "%"),
                            criteriaBuilder.like(root.get("Description"), "%" + ser.toLowerCase() + "%")
                    );
                    predicates.add(serPredicate);
                }
                if (!ville.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("ville"), ville));
                }
                if (!date_debut.isEmpty()) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date_debut"), new Date(date_debut)));
                }

                if (!date_fin.isEmpty()) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date_fin"), new Date(date_fin)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            });
        } else {
            ls = RepVoyage.findAll();
        }
        return ls.stream()
                .map(voy -> {
                    return new VoyageResponse(voy);
                })
                .collect(Collectors.toList());
    }

    public Page<VoyageResponse> voyagesallpagination(String ser,String ville,String date_debut,String date_fin,Pageable pageable){
        Page<Voyage> ls=null;
        if (!ser.isEmpty() || !ville.isEmpty() || !date_debut.isEmpty() || !date_fin.isEmpty()) {
            ls = RepVoyage.findAll((Specification<Voyage>) (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (!ser.isEmpty()) {
                    Predicate serPredicate = criteriaBuilder.or(
                            criteriaBuilder.like(root.get("Name"), "%" + ser.toLowerCase() + "%"),
                            criteriaBuilder.like(root.get("Description"), "%" + ser.toLowerCase() + "%")
                    );
                    predicates.add(serPredicate);
                }
                if (!ville.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("ville"), ville));
                }

                if (!date_debut.isEmpty()) {
                    // Search by Date Debut (Greater than or equal)
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date_debut"), new Date(date_debut)));
                }

                if (!date_fin.isEmpty()) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date_fin"), new Date(date_fin)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            },pageable);
        } else {
            ls = RepVoyage.findAll(pageable);
        }
        return  ls.map(VoyageResponse::new);
    }
}
