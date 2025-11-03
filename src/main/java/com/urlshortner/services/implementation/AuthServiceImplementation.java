package com.urlshortner.services.implementation;

import com.urlshortner.JWTPayload.JWTRequest;
import com.urlshortner.JWTPayload.JWTResponse;
import com.urlshortner.configuration.JWT.JWTHelper;
import com.urlshortner.entity.User;
import com.urlshortner.exception.DuplicateUserException;
import com.urlshortner.repository.UserRepository;
import com.urlshortner.services.AuthService;
import com.urlshortner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImplementation implements AuthService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> login(JWTRequest jwtRequest) {
        this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = jwtHelper.generateToken(userDetails);
        JWTResponse jwtResponse = new JWTResponse(userDetails.getUsername(), token);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username Or Password");
        }
    }

    @Override
    public ResponseEntity<?> register(User user) {
        boolean exist = userService.checkExist(user.getEmail());
        if (exist) {
            throw new DuplicateUserException("User Already Exist With this Email");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = this.userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> validate(String jwtToken, String email) {
        boolean status = this.jwtHelper.validateToken(jwtToken, userDetailsService.loadUserByUsername(email));
        if(status)
            return new ResponseEntity<>(true,HttpStatus.OK);

        return new ResponseEntity<>(true,HttpStatus.UNAUTHORIZED);
    }
}
