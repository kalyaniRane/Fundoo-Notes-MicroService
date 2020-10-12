package com.bridgelabz.userservice.repository;

import com.bridgelabz.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String emilID);

    @Query(value = "select * from user where is_varified= :varify",nativeQuery = true)
    List<User> findAllByVarified(@Param("varify") boolean varify);
}
