package tm.salam.cafeteria3.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import tm.salam.cafeteria3.models.Role;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@PropertySource("classpath:value.properties")
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init(){

        secret=Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String CreateToken(String username, List<Role>roles,int id){

        Claims claims= Jwts.claims().setSubject(username);
        claims.put("roles",getRoleNames(roles));
        claims.put("id",String.valueOf(id));
        Date now=new Date();
        Date validity=new Date(now.getTime()+validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();

    }

    public Authentication getAuthentication(String token){

        UserDetails userDetails=this.userDetailsService.loadUserByUsername(getUsername(token));

        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUsername(String token){

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String ResolveToken(HttpServletRequest request){

        String bearerToken=request.getHeader("Authorization");

        if(bearerToken!=null){

            return bearerToken;
        }
        return null;
    }

    public boolean ValidateToken(String token){

        try {

            Jws<Claims> claimsJwts=Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if(claimsJwts.getBody().getExpiration().before(new Date())){

                return false;
            }

            return true;

        }catch (JwtException | IllegalArgumentException exception){

            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    private List<String>getRoleNames(List<Role>roles){

        List<String>result=new ArrayList<>();

        roles.forEach(role -> {
            result.add(role.getName());
        });

        return result;
    }
}
