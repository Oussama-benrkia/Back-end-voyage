package org.backend.voyage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.voyage.dto.heber.HeberDto;
import org.backend.voyage.dto.heber.HeberResponse;
import org.backend.voyage.dto.heber.HeberUpDto;
import org.backend.voyage.service.HerbertService;
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
@RequestMapping("/api/hebergements")
@RequiredArgsConstructor
public class HeberController {
    private final HerbertService herberService;
    @PostMapping
    public ResponseEntity<HeberResponse> Create_Heb(@Valid @RequestBody HeberDto heberDto){
        HeberResponse response = herberService.Create_Post(heberDto);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HeberResponse> Delete_Heb(@PathVariable("id") Long id){
        HeberResponse response = herberService.Delete_Post(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<HeberResponse> Update_Heb(@PathVariable("id") Long id,@Valid @RequestBody HeberUpDto heberDto){
        HeberUpDto HeberUpDto;
        HeberResponse response = herberService.Update_Post(heberDto,id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<HeberResponse>>list_all_Hebergemt(
            @RequestParam(defaultValue = "") String search
    ){
        List<HeberResponse> posts = herberService.list_heber_by_serch(search);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/{id}")
    public ResponseEntity<HeberResponse>Show_heber(
            @PathVariable("id") Long id
    ){
        HeberResponse response=herberService.find_Heber(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/pagination")
    public ResponseEntity<Page<HeberResponse>>paginate_all_Post(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "")String search
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<HeberResponse> posts = herberService.list_Heber(pageable,search);
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
