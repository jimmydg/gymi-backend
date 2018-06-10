package com.gymi.repository;

import com.gymi.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUserId1OrUserId2(@NotBlank long userId1, @NotBlank long userId2);

    Friend findByUserId1AndUserId2(@NotBlank long userId1, @NotBlank long userId2);

    @Query("SELECT u FROM Friend u WHERE (u.userId1 = ?1 AND u.userId2 = ?2) OR (u.userId1 = ?2 AND u.userId2 = ?1)")
    Friend findFriendshipByUserIds(long userId1, long userId2);

}
