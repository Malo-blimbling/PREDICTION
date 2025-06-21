package com.ProjetPrediction.repositories;

import com.ProjetPrediction.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, java.util.UUID> {
    UserDetails findByEmail(String email);
}
