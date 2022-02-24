package tm.salam.cafeteria3.controllers;

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
import tm.salam.cafeteria3.dto.UserDTO;
import tm.salam.cafeteria3.models.User;
import tm.salam.cafeteria3.security.jwt.JwtTokenProvider;
import tm.salam.cafeteria3.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }


//    @PostMapping(path = "/createNewAdmin", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
//            produces = "application/json")
//    public ResponseTransfer CreateNewAdmin(@ModelAttribute UserDTO adminDTO) {
//
//        return userService.CreateNewAdmin(adminDTO);
//    }


    @GetMapping(path = "/profile", produces = "application/json")
    public ResponseEntity ShowProfileAdmin(@RequestHeader("Authorization") String token) {

        JsonParser parser = JsonParserFactory.getJsonParser();
        Map<String, ?> tokenData = parser.parseMap(JwtHelper.decode(token).getClaims());

        Map<Object, Object> response = new HashMap<>();

        response.put("profile admin", userService.getLoginDTOById(Integer.valueOf((String) tokenData.get("id"))));

        return ResponseEntity.ok(response);

    }


    @PutMapping(path = "/updateProfile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    @ResponseBody
    public ResponseEntity UpdateProfileAdmin(@ModelAttribute UserDTO userDTO,
                                             @RequestHeader("Authorization") String token,
                                             @RequestParam("oldPassword") String password) {


        JsonParser parser = JsonParserFactory.getJsonParser();
        Map<String, ?> tokenData = parser.parseMap(JwtHelper.decode(token).getClaims());
        Map<Object, Object> response = new HashMap<>();
        int id = Integer.valueOf((String) tokenData.get("id"));

        if (!passwordEncoder.matches(password, userService.getUserById(id).getPassword())) {

            response.put("old password don't right", false);

            return ResponseEntity.ok(response);
        }
        ResponseTransfer responseTransfer = userService.UpdateProfileUser(userDTO, id);

        boolean check = responseTransfer.getStatus().booleanValue();
        if (check) {

            User user = userService.getUserById(id);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), userDTO.getPassword()));
            String newToken = jwtTokenProvider.CreateToken(user.getEmail(), user.getRoles(), id);

            response.put("profile admin successful updated", true);
            response.put("admin", userDTO);
            response.put("token", newToken);

        } else {

            response.put(responseTransfer.getMessage(), false);
        }

        return ResponseEntity.ok(response);
    }


//    @DeleteMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
//    @ResponseBody
//    public ResponseTransfer RemoveAdmin(@RequestParam("id") int id) {
//
//        if (userService.RemoveUserById(id)) {
//            return new ResponseTransfer("Admin Successful removed", true);
//        } else {
//            return new ResponseTransfer("Admin don't removed", false);
//        }
//    }


    @PostMapping(path = "/createNewSeller", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    @ResponseBody
    public ResponseTransfer CreateNewSeller(@ModelAttribute UserDTO sellerDTO) {

        return userService.CreateNewSeller(sellerDTO);
    }


    @DeleteMapping(path = "/removeSeller", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseTransfer RemoveSeller(int id) {

        if (userService.RemoveUserById(id)) {

            return new ResponseTransfer("Seller removed", true);
        } else {
            return new ResponseTransfer("Seller don't removed", false);
        }
    }

}