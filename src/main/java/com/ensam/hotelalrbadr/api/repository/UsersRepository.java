package com.ensam.hotelalrbadr.api.repository;

import com.ensam.hotelalrbadr.api.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<users, Integer> {
    // Custom query methods can be added here if needed
}
