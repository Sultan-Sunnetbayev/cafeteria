package tm.salam.cafeteria3.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.*;
import tm.salam.cafeteria3.Helper.ResponseTransfer;
import tm.salam.cafeteria3.dto.LoginDTO;
import tm.salam.cafeteria3.dto.UserDTO;
import tm.salam.cafeteria3.models.Role;
import tm.salam.cafeteria3.models.User;
import tm.salam.cafeteria3.security.jwt.JwtTokenProvider;
import tm.salam.cafeteria3.service.UserService;
import java.awt.image.ImageProducer;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public LoginController(AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider,
                           UserService userService) {

        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},produces = "application/json")
    public ResponseEntity login(@RequestParam("email")String email,
                                @RequestParam("password")String password) {

        try {
            User user=userService.getUserByEmail(email);

            if(user==null){
                throw new UsernameNotFoundException("User with email "+email+" not found");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),password));

            String token=jwtTokenProvider.CreateToken(email,user.getRoles(),user.getId());
            Map<Object,Object>response=new HashMap<>();
            response.put("token",token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }


    @GetMapping(produces = "application/json")
    public ResponseEntity getUserByToken(@RequestHeader(value = "Authorization")String token){

//        String[] chunks=token.split("\\.");
//        Base64.Decoder decoder = Base64.getUrlDecoder();
//        Map<Object,Object>response=new HashMap<>();
//
//        String header = new String(decoder.decode(chunks[0]));
//        String payload = new String(decoder.decode(chunks[1]));
//
//        response.put("username",header);
//        response.put("data",payload);
//
//        return ResponseEntity.ok(response);

        JsonParser parser = JsonParserFactory.getJsonParser();
        Map<String, ?> tokenData = parser.parseMap(JwtHelper.decode(token).getClaims());
        Map<Object,Object>response=new HashMap<>();
        LoginDTO userDTO=userService.getLoginDTOById(Integer.valueOf((String)tokenData.get("id")));

        if(userDTO==null){
            response.put("this user not found",false);
        }else{
            response.put("user successful founded",userDTO);
        }

        return ResponseEntity.ok(response);
    }
//    @GetMapping("/login-error")
//    public ResponseTransfer loginError(){
//
//        return new ResponseTransfer("email or password user invalid ",false);
//    }
}
