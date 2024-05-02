package org.backend.voyage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.voyage.dto.Rese.ResevationRequest;
import org.backend.voyage.dto.Rese.ResevationResponse;
import org.backend.voyage.model.User;
import org.backend.voyage.rep.IntRepUser;
import org.backend.voyage.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservaController {
    public final ReservationService reservationService;
    public final IntRepUser repUser;
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping
    public ResponseEntity<List<ResevationResponse>> affiche_reservation(
            @RequestParam(defaultValue = "") String date_debut,
            @RequestParam(defaultValue = "") String date_fin
    ){
        List<ResevationResponse> pageResponse = reservationService.affiche_reservation(date_debut,date_fin);
        return ResponseEntity.ok(pageResponse);
    }
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/pagination")
    public ResponseEntity<Page<ResevationResponse>> affiche_reservation_pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<ResevationResponse> pageResponse = reservationService.affiche_reservation_pagination(pageable);
        return ResponseEntity.ok(pageResponse);
    }
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/user/{id}/pagination")
    public ResponseEntity<Page<ResevationResponse>> affiche_user_reservation_pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @PathVariable("id") Long id
    ){
        User us= repUser.findById(id).orElse(null);
        if(us==null){
            return ResponseEntity.notFound().build();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<ResevationResponse> pageResponse = reservationService.affiche_reservation_User_pagination(us,pageable);
        return ResponseEntity.ok(pageResponse);
    }
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ResevationResponse>> affiche_user_reservation( @PathVariable("id") Long id){
        User us= repUser.findById(id).orElse(null);
        if(us==null){
            return ResponseEntity.notFound().build();
        }
        List<ResevationResponse> pageResponse = reservationService.affiche_reservation_User(us);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/my/pagination")
    public ResponseEntity<Page<ResevationResponse>> affiche_my_reservation_pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ){
        UserDetails user= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User us= repUser.findByEmail(user.getUsername()).orElse(null);
        if(us==null){
            return ResponseEntity.notFound().build();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<ResevationResponse> pageResponse = reservationService.affiche_reservation_User_pagination(us,pageable);
        return ResponseEntity.ok(pageResponse);
    }
    @GetMapping("/my")
    public ResponseEntity<List<ResevationResponse>> affiche_my_reservation(){
        UserDetails user= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User us= repUser.findByEmail(user.getUsername()).orElse(null);
        if(us==null){
            return ResponseEntity.notFound().build();
        }
        List<ResevationResponse> pageResponse = reservationService.affiche_reservation_User(us);
        return ResponseEntity.ok(pageResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodAr(
            MethodArgumentNotValidException exp
    ){
        var errors=new HashMap<String , String>();
        exp.getBindingResult().getAllErrors()
                .forEach(objectError ->
                {
                    var fieldName=((FieldError)objectError).getField();
                    var errorMes=objectError.getDefaultMessage();
                    errors.put(fieldName,errorMes);

                });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResevationResponse> delete_reservation(
            @PathVariable("id") Long id){
        ResevationResponse response = reservationService.delete_reservation(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<ResevationResponse> Ajout_reservation(
           @Valid@RequestBody ResevationRequest resevationRequest
    ){
        ResevationResponse response = reservationService.ajouter_reservation(resevationRequest);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResevationResponse> find_reservation(
            @PathVariable("id") Long id){
        ResevationResponse response = reservationService.find_reservation(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
