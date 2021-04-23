package com.inl.rest.userMgmt;

import com.inl.rest.userMgmt.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByAccountName(String accountName);
    Optional<User> findByEmailAddress(String emailAddress);
}
