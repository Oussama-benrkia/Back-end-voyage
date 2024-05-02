package org.backend.voyage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.voyage.dto.activity.ActivityResponse;
import org.backend.voyage.dto.activity.Activityrequest;
import org.backend.voyage.dto.activity.ActivityrequestUp;
import org.backend.voyage.service.ActivityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    @GetMapping("/types")
    public ResponseEntity<List<String>> getalltype(){
        return ResponseEntity.ok(activityService.getalltype());
    }
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<ActivityResponse> Create_Activity(@Valid @RequestBody Activityrequest activityrequest){
        ActivityResponse response = activityService.Create_acti(activityrequest);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ActivityResponse> Delete_Acti(@PathVariable("id") Long id){
        ActivityResponse response = activityService.Delete_acti(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponse> Update_acti(@PathVariable("id") Long id,@Valid @RequestBody ActivityrequestUp activityrequest){
        ActivityResponse response = activityService.Update_acti(activityrequest,id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<ActivityResponse>>list_all_acti(
            @RequestParam(defaultValue = "") String search
    ){
        List<ActivityResponse> posts = activityService.list_acti_by_serch(search);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse>Show_activ(
            @PathVariable("id") Long id
    ){
        ActivityResponse response=activityService.find_activi(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/pagination")
    public ResponseEntity<Page<ActivityResponse>>paginate_all_acti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "")String search
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<ActivityResponse> posts = activityService.list_activi(pageable,search);
        return ResponseEntity.ok(posts);
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

}
