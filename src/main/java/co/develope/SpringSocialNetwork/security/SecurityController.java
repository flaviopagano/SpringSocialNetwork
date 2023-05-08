package co.develope.SpringSocialNetwork.security;

import co.develope.SpringSocialNetwork.entities.DTO.LoginDTO;
import co.develope.SpringSocialNetwork.entities.DTO.SignupActivationDTO;
import co.develope.SpringSocialNetwork.entities.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    @Autowired
    SecurityService securityService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserDTO signupDTO) throws Exception {
        return ResponseEntity.ok(securityService.signup(signupDTO));
    }

    @PostMapping("/signup/activation")
    public ResponseEntity signup(@RequestBody SignupActivationDTO signupActivationDTO) throws Exception {
        return ResponseEntity.ok(securityService.activate(signupActivationDTO));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO){
        try {
            String token = securityService.login(loginDTO);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
