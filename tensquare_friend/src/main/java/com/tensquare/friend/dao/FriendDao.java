package com.tensquare.friend.dao;


import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendDao extends JpaRepository<Friend, String> {
    Friend findByUseridAndFriendid(String userId, String friendId);

    @Modifying
    @Query(value = "update tensquare_friend.tb_friend set islike= :isLike where userid= :userId and friendid= :friendId", nativeQuery = true)
    void updateIsLike(@Param("isLike") String isLike,@Param("userId") String userId,@Param("friendId") String friendId);

    @Modifying
    @Query(value = "delete from tensquare_friend.tb_friend  where userid= :userId and friendid= :friendId", nativeQuery = true)
    void deleteFriend(@Param("userId") String userId, @Param("friendId") String friendId);
}
