package org.backend.voyage.User.Service;

import lombok.RequiredArgsConstructor;
import org.backend.voyage.User.Dto.UserRequestUp;
import org.backend.voyage.User.Dto.UserResp;
import org.backend.voyage.User.Dto.Userpassword;
import org.backend.voyage.User.Model.User;
import org.backend.voyage.User.repo.IntRepUser;
import org.backend.voyage.Util.ImgService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IntRepUser repUser;
    private final ImgService imgService;
    private final PasswordEncoder passwordEncoder;

    public UserResp myCompte(){
        UserDetails us= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=repUser.findByEmail(us.getUsername()).orElse(null);
        if(us!=null){
            return new UserResp(user);
        }return null;
    }
    public UserResp update_myaccount(UserRequestUp userRequestUp) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = repUser.findByEmail(userDetails.getUsername()).orElse(null);
        if (user != null) {
            if (!userRequestUp.getFirstname().isEmpty()) {
                user.setFirst_name(userRequestUp.getFirstname());
            }
            if (!userRequestUp.getLastname().isEmpty()) {
                user.setLast_name(userRequestUp.getLastname());
            }
            if (!userRequestUp.getEmail().isEmpty()) {
                user.setEmail(userRequestUp.getEmail());
            }
            if (userRequestUp.getImage() != null) {
                if (!user.getImage().isEmpty()) {
                    imgService.deleteimage(user.getImage());
                }
                String newImageFileName = imgService.addimage(userRequestUp.getImage(), "Users");
                user.setImage(newImageFileName);
            }
            User s=repUser.save(user);
            return new UserResp(s);
        }
        return null;
    }
    public UserResp Upd_passwrd_myaccount(Userpassword userpassword) {
        if(userpassword.getPassword().equals(userpassword.getPassword_comfirme())){
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = repUser.findByEmail(userDetails.getUsername()).orElse(null);
                user.setPassword(passwordEncoder.encode(userpassword.getPassword()));
                return new UserResp(repUser.save(user));
        }
            return null;
    }

}
