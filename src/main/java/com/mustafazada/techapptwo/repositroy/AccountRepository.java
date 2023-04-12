package com.mustafazada.techapptwo.repositroy;

import com.mustafazada.techapptwo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
