package com.mustafazada.techapptwo.repositroy;

import com.mustafazada.techapptwo.entity.TechUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<TechUser, Long> {
    @Query(value = "SELECT p FROM TechUser p JOIN FETCH p.accountList WHERE p.pin = :pin")
    Optional<TechUser> findByPin(String pin);
}
