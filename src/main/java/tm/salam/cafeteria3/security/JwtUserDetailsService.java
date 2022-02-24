package tm.salam.cafeteria3.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tm.salam.cafeteria3.models.User;
import tm.salam.cafeteria3.security.jwt.JwtUserFactory;
import tm.salam.cafeteria3.security.jwt.JwtUser;
import tm.salam.cafeteria3.service.UserService;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getUserByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        JwtUser jwtUser= JwtUserFactory.create(user);

        return jwtUser;
    }

}