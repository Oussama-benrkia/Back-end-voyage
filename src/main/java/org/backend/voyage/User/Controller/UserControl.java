package org.backend.voyage.User.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.voyage.User.Dto.UserRequestUp;
import org.backend.voyage.User.Dto.UserResp;
import org.backend.voyage.User.Dto.Userpassword;
import org.backend.voyage.User.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserControl {
    private final UserService userService;
    @GetMapping("/my")
    public ResponseEntity<UserResp> myaccount(){
        UserResp userResp = userService.myCompte();
        if(userResp == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userResp);
    }
    @PutMapping("/my")
    public ResponseEntity<UserResp> update_myaccount(@Valid @ModelAttribute UserRequestUp userResp){
        UserResp usser = userService.update_myaccount(userResp);
        if(userResp == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(usser);
    }
    @PutMapping("/my/password")
    public ResponseEntity<UserResp> Upd_modifier_myaccount(@Valid @RequestBody Userpassword userResp){
        UserResp user = userService.Upd_passwrd_myaccount(userResp);
        return ResponseEntity.ok(user);
    }

}
