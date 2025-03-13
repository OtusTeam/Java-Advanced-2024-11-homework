package org.example.task12.repository;

import org.example.task12.persistence.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    @Query("SELECT email FROM task12_users WHERE email IS NOT NULL AND email != ''")
    Flux<String> findNoEmptyEmails();
}
