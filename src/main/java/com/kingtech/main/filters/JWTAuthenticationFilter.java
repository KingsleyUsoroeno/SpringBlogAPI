package com.kingtech.main.filters;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingtech.main.data.model.CacheUserModel;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.kingtech.main.constants.SecurityConstants.LOGIN_URL;
import static com.kingtech.main.constants.SecurityConstants.SECRET;

/*So having an Authentication filter is giving spring boot the full control of authorizing our requests as soon as
 * it been made by the user , so thats why we override functions such as [attemptAuthentication] and
 * [successfulAuthentication]*/
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final Environment environment;

    //@Autowired
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, Environment environment) {
        this.authenticationManager = authenticationManager;
        this.environment = environment;
        setFilterProcessesUrl(LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            CacheUserModel creds = new ObjectMapper()
                    .readValue(req.getInputStream(), CacheUserModel.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        final long expirationTime = TimeUnit.MINUTES.toMillis(15);
        final String secret = environment.getProperty("token.secret");

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(HMAC512(SECRET.getBytes()));

        res.getWriter().write(token);
        res.getWriter().flush();
    }
}
