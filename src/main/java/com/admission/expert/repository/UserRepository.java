package com.admission.expert.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.admission.expert.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
	User findByEmail(String email);
    
    /*
     * Will return encrypted password using mysql function()
     */
    @Query(value = "SELECT password(?1)", nativeQuery = true)
    String encryptPass(String password);

	Optional<User> findByResetToken(String resetToken);

	@Override
	Page<User> findAll(Pageable pageable);

}
