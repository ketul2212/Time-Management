package com.ketul.service;

import com.ketul.module.TokenConfirmation;
import com.ketul.module.User;
import com.ketul.repo.TokenConfirmationRepository;
import com.ketul.repo.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {

    private final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenConfirmationRepository tokenConfirmationRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // section Save Token
    public String registerToken(@NotNull User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            User alreadyUser = userRepository.findByEmail(user.getEmail());
            if(!alreadyUser.isEnabled()) {
                TokenConfirmation tokenConfirmation = tokenConfirmationRepository.findByUser(alreadyUser);

                if(LocalDateTime.now().isEqual(tokenConfirmation.getExpiresAt()) || LocalDateTime.now().isAfter(tokenConfirmation.getExpiresAt())) {
                    tokenConfirmation.setExpiresAt(tokenConfirmation.getExpiresAt().plusMinutes(1));
                    tokenConfirmationRepository.save(tokenConfirmation);
                }
                mailService.sendSimpleMail(user.getEmail(), tokenConfirmation.getToken());
                return "Your Data is saved SuccessFully....Please Confirm token send into your email.: " + user.getEmail();
            }
            else {
                logger.info("You are Already Registered");
                return "You are Already Registered with this email: " + user.getEmail();
            }

        }
        else {
            TokenConfirmation tokenConfirmation = new TokenConfirmation();

            String token = UUID.randomUUID().toString();
            token = bCryptPasswordEncoder.encode(token).replaceAll("/", "");

            if (token.charAt(token.length() - 1) == '.')
                token += "a";

            logger.debug("Your Token is -> {}", token);

            user.setPass(bCryptPasswordEncoder.encode(user.getPass()));

            tokenConfirmation.setToken(token);
            tokenConfirmation.setCreatedAt(LocalDateTime.now());
            tokenConfirmation.setExpiresAt(LocalDateTime.now().plusMinutes(1));
            tokenConfirmation.setUser(user);
            tokenConfirmationRepository.save(tokenConfirmation);

            mailService.sendSimpleMail(user.getEmail(), tokenConfirmation.getToken());
            return "Your Data is saved SuccessFully....Please Confirm token send into your email.: " + user.getEmail();
        }
    }

    // section Get Token
    public TokenConfirmation getTokenConfirmation(String token) {
        return tokenConfirmationRepository.findByToken(token);
    }

    // section Set Enable user
    public TokenConfirmation setEnable(long id) {
        TokenConfirmation tokenConfirmation = tokenConfirmationRepository.findById(id).orElse(null);

        if(tokenConfirmation == null)
            throw new UsernameNotFoundException("Token is not found");

        tokenConfirmation.setConfirmedAt(LocalDateTime.now());
        tokenConfirmation.getUser().setEnabled(true);
        userRepository.save(tokenConfirmation.getUser());

        return tokenConfirmation;
    }

}
