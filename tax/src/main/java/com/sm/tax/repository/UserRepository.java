package com.sm.tax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import com.sm.tax.models.Tax;
import com.sm.tax.models.User_information;
//import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User_information,Long> {
    Optional<User_information> findByEmail(String email);
    
}
