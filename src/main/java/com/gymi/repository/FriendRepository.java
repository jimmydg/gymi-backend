package com.gymi.repository;

import com.gymi.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("SELECT f FROM friends f WHERE p.userId1 = :userId1 OR p.userId2 = :userId1")
    List<Friend> findFriendsforUser(long userId1);

}
