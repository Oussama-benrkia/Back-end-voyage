package org.backend.voyage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.voyage.dto.voyage.VoyageResponse;
import org.backend.voyage.dto.voyage.Voyagerequest;
import org.backend.voyage.dto.voyage.VoyagerequestUp;
import org.backend.voyage.service.VoyageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/Voyage")
@RequiredArgsConstructor
public class VoyageController {
    private final VoyageService voyageService;
    @GetMapping("/types")
    public ResponseEntity<List<String>> getalltype(){
        return ResponseEntity.ok(voyageService.getalltransp());
    }
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<VoyageResponse> Create_Activity(@Valid @ModelAttribute Voyagerequest req) throws ParseException {
        VoyageResponse response = voyageService.Create_voyage(req);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<VoyageResponse> Delete_Acti(@PathVariable("id") Long id){
        VoyageResponse response = voyageService.Delete_voyage(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
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
    @GetMapping("/{id}")
    public ResponseEntity<VoyageResponse> findvoyage(
            @PathVariable("id") Long id
    ) {

        VoyageResponse response = voyageService.find_transp(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/{id}")
    public ResponseEntity<VoyageResponse> Updatevoyage(
            @PathVariable("id") Long id,
            @Valid@ModelAttribute VoyagerequestUp rep
    ) throws ParseException {

        VoyageResponse response = voyageService.Update_voyage(rep,id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<VoyageResponse>>list_voy(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "")String ville,
            @RequestParam(defaultValue = "")String Date_debut,
            @RequestParam(defaultValue = "")String Date_fin
    ){
        List<VoyageResponse> posts = voyageService.voyages(search,ville,Date_debut,Date_fin);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/pagination")
    public ResponseEntity<Page<VoyageResponse>>paginate_voy(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String ville,
            @RequestParam(defaultValue = "") String date_debut,
            @RequestParam(defaultValue = "") String date_fin) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VoyageResponse> pageResponse = voyageService.voyagesallpagination(search, ville, date_debut, date_fin, pageable);
        return ResponseEntity.ok(pageResponse);
    }

}
