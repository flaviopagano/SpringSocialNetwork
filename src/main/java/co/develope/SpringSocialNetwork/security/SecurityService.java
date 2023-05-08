package co.develope.SpringSocialNetwork.security;

import co.develope.SpringSocialNetwork.entities.DTO.LoginDTO;
import co.develope.SpringSocialNetwork.entities.DTO.SignupActivationDTO;
import co.develope.SpringSocialNetwork.entities.DTO.UserDTO;
import co.develope.SpringSocialNetwork.entities.Role;
import co.develope.SpringSocialNetwork.entities.Roles;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.repositories.RoleRepository;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import co.develope.SpringSocialNetwork.services.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class SecurityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // TODO invio codice di attivazione via email
    //@Autowired
    //private MailNotificationService mailNotificationService;

    @Autowired // prendi questo bean
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Value("${crypto.key}")
    protected static String key;

    public User signup(UserDTO signupDTO) throws Exception { //SignupDTO rappresenta in Java l'oggetto body su Postman

        if (userRepository.existsByEmail(signupDTO.getEmail())) throw new Exception("Email " + signupDTO.getEmail() + " already exist");
        //User userInDB = userRepository.findByEmail(signupDTO.getEmail());
        //if(userInDB != null) throw new Exception("User already exist");

        // creo nuovo user e setto i parametri necessari
        User user = userService.createUser(signupDTO);
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setActive(false);

        //genera un codice univoco di 36 caratteri
        // UUID = Universal Unique IDentifier
        user.setActivationCode(UUID.randomUUID().toString());

        Set<Role> roles = new HashSet<>();

        Optional<Role> userRole =roleRepository.findByName(Roles.REGISTERED);
        if(!userRole.isPresent()) throw new Exception("Cannot set user role");
        roles.add(userRole.get());
        user.setRoles(roles);

        //mailNotificationService.sendActivationEmail(user); // invio mail di attivazione
        return userRepository.save(user); // ritorniamo l'user salvato
    }

    public User activate(SignupActivationDTO signupActivationDTO) throws Exception {
        User user = userRepository.findByActivationCode(signupActivationDTO.getActivationCode());
        if(user == null) throw new Exception("User Not found");
        user.setActive(true);
        user.setActivationCode(null);
        return userRepository.save(user);
    }



    public String login(LoginDTO loginDTO) throws Exception {
        if(loginDTO == null) return null;
        Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());
        if (optionalUser.isEmpty()) throw new Exception("User " + loginDTO.getEmail() + " not found");
        User userFromDB = optionalUser.get();
        boolean canLogin = this.canUserLogin(userFromDB, loginDTO.getPassword());
        if(!canLogin) return null;

        return generateJWT(userFromDB);
    }

    public boolean canUserLogin(User user, String password){
        return passwordEncoder.matches(password, user.getPassword());
    }

    //https://www.baeldung.com/java-date-to-localdate-and-localdatetime
    static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static String getJWT(User user){
        Date expiresAt = convertToDateViaInstant(LocalDateTime.now().plusDays(15));
        String[] roles = user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new);
        return JWT.create()
                .withIssuer("dh-team4")
                .withIssuedAt(new Date())
                .withExpiresAt(expiresAt)
                .withClaim("roles",String.join(",",roles)) //funziona su nuove versioni Java (17)
                .withClaim("id", user.getId())
                .sign(Algorithm.HMAC512(key));
    }

    public String generateJWT(User user) {
        String JWT = getJWT(user);

        //user.setJwtCreatedOn(LocalDateTime.now());
        userRepository.save(user);

        return JWT;
    }



}
