package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    User findByMobile(String  mobile);

    /**
     *  更新粉丝数
     * @param value 数量
     * @param friendId 好友编号
     */
    @Modifying
    @Query(value = "UPDATE tensquare_user.tb_user SET fanscount=(fanscount+ :value) WHERE id= :friendId",nativeQuery = true)
    void updateFanscount(@Param("value") int value, @Param("friendId") String friendId);

    /**
     * 更新关注数
     * @param value 数量
     * @param userId 用户编号
     */
    @Modifying
    @Query(value = "UPDATE tensquare_user.tb_user SET followcount=followcount+ :value WHERE id= :userId",nativeQuery = true)
    void updateFollowcount(@Param("value") int value,@Param("userId") String userId);
}
