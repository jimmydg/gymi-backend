package com.gymi.repository;

import com.gymi.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUserId1OrUserId2(@NotBlank long userId1, @NotBlank long userId2);

}
