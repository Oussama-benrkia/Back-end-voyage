package org.backend.voyage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.voyage.dto.user.BaseUserRequestUp;
import org.backend.voyage.dto.user.UserResp;
import org.backend.voyage.dto.user.Userpassword;
import org.backend.voyage.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mycompte")
public class CompteController {
    private final UserService userService;
    @GetMapping()
    public ResponseEntity<UserResp> myaccount(){
        UserResp userResp = userService.myCompte();
        if(userResp == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userResp);
    }
    @PutMapping
    public ResponseEntity<UserResp> update_myaccount(@Valid @ModelAttribute BaseUserRequestUp userResp){
        UserResp usser = userService.update_myaccount(userResp);
        if(userResp == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(usser);
    }
    @PutMapping("/password")
    public ResponseEntity<UserResp> Upd_modifier_myaccount(@Valid @RequestBody Userpassword userResp){
        UserResp user = userService.Upd_passwrd_myaccount(userResp);
        return ResponseEntity.ok(user);
    }
}
