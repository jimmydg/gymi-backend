package com.gymi.repository;

import com.gymi.model.Activity;
import com.gymi.model.Session;
import com.gymi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Set<Session> findByUserId(long userId);

    Set<Session> findAllByUserIsInOrderByDateTime(Collection<User> user);
}
