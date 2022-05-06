package com.ketul.service;

import com.ketul.module.TimeTable;
import com.ketul.module.TokenConfirmation;
import com.ketul.module.User;
import com.ketul.repo.TokenConfirmationRepository;
import com.ketul.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenConfirmationRepository tokenConfirmationRepository;

    @Autowired
    private TokenService tokenService;

    // section Conform token
    public ResponseEntity<Object> conform(@PathVariable String token) {

        System.out.println("HELLO TOKEN CONFORM");
        logger.debug("Your Token is -> {}",token);

        TokenConfirmation tokenConfirmation = tokenService.getTokenConfirmation(token);
        System.out.println(tokenConfirmation);

        if(tokenConfirmation == null)
            return new ResponseEntity<>("Please Login again with provided mail id", HttpStatus.NOT_FOUND);

        if(LocalDateTime.now().isEqual(tokenConfirmation.getExpiresAt()) || LocalDateTime.now().isAfter(tokenConfirmation.getExpiresAt())) {
            tokenConfirmation.setExpiresAt(LocalDateTime.now().plusMinutes(15));
            tokenConfirmationRepository.save(tokenConfirmation);
            return new ResponseEntity<>("Your Token is Expire try it after 6 minutes", HttpStatus.REQUEST_TIMEOUT);
        }

        tokenService.setEnable(tokenConfirmation.getId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/login").build().toUri();

        System.out.println(location);
        return ResponseEntity.created(location).build();
    }

    // section Update Info
    public String updateInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(auth.getPrincipal());
        return auth.getPrincipal().toString();
    }

    // section Save Customer
    @Transactional
    public String saveUser(User user) {
        return tokenService.registerToken(user);
    }

    // section login
    public User login(String email, String pass) {
        System.out.println(email + " " + pass);
        User user = userRepository.findByEmail(email);
        System.out.println("login" + user);
//        if(user != null && bCryptPasswordEncoder.matches(pass, user.getPass())) {
        if(user != null) {
            System.out.println("LOgin Sucess");
            return user;
        }
        return null;
    }

    //section authenticate
/*    public String authenticate(String email, String pass) throws URISyntaxException {

        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

        bodyValues.add("email", email);
        bodyValues.add("pass", pass);

        String response = WebClient.create().post()
                .uri(new URI("http://localhost:8081/anotherLogin"))
//                .header("Authorization", "Bearer MY_SECRET_TOKEN")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(bodyValues))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(response);
        System.out.println("response");


        return response;
    }
*/

    //section Scheduler
/*
    @Scheduled(cron = "* /3000 * * * * ?")
    public void deleteUserWithoutConformation() {
        List<TokenConfirmation> tokenConfirmationsList = tokenConfirmationRepository.findAll();

        for(TokenConfirmation tokenConfirmation : tokenConfirmationsList) {
            if(LocalDateTime.now().isAfter(tokenConfirmation.getExpiresAt())) {
                User user = userRepository.findById(tokenConfirmation.getUser().getId()).orElse(null);
                if(user != null && !user.isEnabled()) {
                    System.out.println("Bye");
                    tokenConfirmationRepository.deleteById(tokenConfirmation.getId());
                }
            }
        }
        System.out.println("Hello");
    }
*/
}
