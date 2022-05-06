package com.ketul.repo;

import com.ketul.module.TokenConfirmation;
import com.ketul.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenConfirmationRepository extends JpaRepository<TokenConfirmation, Long> {
    TokenConfirmation findByToken(String token);

    @Query(value = "select * from token_confirmation t where t.user_id=?", nativeQuery = true)
    TokenConfirmation existsByUser(User user);

    TokenConfirmation findByUser(User user);


}