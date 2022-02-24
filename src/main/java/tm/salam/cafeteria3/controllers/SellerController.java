package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.*;
import tm.salam.cafeteria3.Helper.ResponseTransfer;
import tm.salam.cafeteria3.dto.LoginDTO;
import tm.salam.cafeteria3.dto.UserDTO;
import tm.salam.cafeteria3.models.User;
import tm.salam.cafeteria3.security.jwt.JwtTokenProvider;
import tm.salam.cafeteria3.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/seller")
public class SellerController {

    private final UserService sellerService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SellerController(UserService sellerService, AuthenticationManager authenticationManager,
                            JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {

        this.sellerService = sellerService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping(path = "/profile", produces = "application/json")
    public ResponseEntity getProfileUser(@RequestHeader(value = "Authorization") String token) {

        JsonParser parser = JsonParserFactory.getJsonParser();
        Map<String, ?> tokenData = parser.parseMap(JwtHelper.decode(token).getClaims());

        LoginDTO userDTO = sellerService.getLoginDTOById(Integer.valueOf((String) tokenData.get("id")));
        Map<Object, Object> response = new HashMap<>();

        if (userDTO == null) {

            response.put("seller don't found ", false);

        } else {
            response.put("success", true);
            response.put("profile seller ", userDTO);
        }

        return ResponseEntity.ok(response);
    }


    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity UpdateProfileSeller(@ModelAttribute UserDTO sellerDTO,
                                              @RequestHeader("Authorization") String token,
                                              @RequestParam("oldPassword") String password) {

        JsonParser parser = JsonParserFactory.getJsonParser();
        Map<String, ?> tokenData = parser.parseMap(JwtHelper.decode(token).getClaims());
        Map<Object, Object> response = new HashMap<>();
        int id = Integer.valueOf((String) tokenData.get("id"));

        if (!passwordEncoder.matches(password, sellerService.getUserDTOById(id).getPassword())) {

            response.put("old password don't right", false);

            return ResponseEntity.ok(response);
        }
        ResponseTransfer responseTransfer = sellerService.UpdateProfileUser(sellerDTO, id);
        boolean check = responseTransfer.getStatus().booleanValue();

        if (check) {

            User seller = sellerService.getUserById(id);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(seller.getEmail(), sellerDTO.getPassword()));
            String newToken = jwtTokenProvider.CreateToken(seller.getEmail(), seller.getRoles(), id);

            response.put("success", true);
            response.put("seller", sellerDTO);
            response.put("token", newToken);

        } else {

            response.put(responseTransfer.getMessage(), false);
        }

        return ResponseEntity.ok(response);
    }

}