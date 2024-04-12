package org.backend.voyage.Auten.Service;

import lombok.RequiredArgsConstructor;
import org.backend.voyage.Auten.Dto.AuthenticaReq;
import org.backend.voyage.Auten.Dto.AuthenticationResponse;
import org.backend.voyage.User.Dto.BaseUserRequest;
import org.backend.voyage.Auten.Model.Enum.TokenType;
import org.backend.voyage.Auten.Model.Token;
import org.backend.voyage.User.Model.Enum.Role;
import org.backend.voyage.User.Model.User;
import org.backend.voyage.Auten.Rep.InRepToken;
import org.backend.voyage.User.repo.IntRepUser;
import org.backend.voyage.Util.ImgService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IntRepUser rep;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authmana;
    private final InRepToken repToken;
    private final ImgService imgserv;
    public AuthenticationResponse register(BaseUserRequest req){
        String img="";
        if(req.getImage()!=null){
            img=imgserv.addimage(req.getImage(),"Users");
        }
        var user= User.builder().first_name(req.getFirstname())
                .Last_name(req.getLastname())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.User)
                .image(img)
                .build();
        rep.save(user);
        var jwttoken=jwtService.generateToken(user);
        SaveToken(jwttoken, user);
        return AuthenticationResponse.builder().token(jwttoken).message("success").build();
    }

    private void SaveToken(String jwttoken, User user) {
        var token= Token.builder()
                .token(jwttoken)
                .user(user).tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false).build();
        repToken.save(token);
    }

    public AuthenticationResponse authenticate(AuthenticaReq req){
        authmana.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),req.getPassword()
                )
        );
        var user=rep.findByEmail(req.getEmail()).orElseThrow();
        var nmtoken=jwtService.generateToken(user);
        SaveToken(nmtoken,user);
        return AuthenticationResponse.builder().token(nmtoken)
                .build();
    }
    private void revokeallUserToken(User user){
        var validtoken=repToken.findAllValidTokens(user.getId());
        if(validtoken.isEmpty()){
            return;
        }
        validtoken.forEach(t->{
            t.setRevoked(true);
            t.setExpired(true);
        });
        repToken.saveAll(validtoken);
    }
}
