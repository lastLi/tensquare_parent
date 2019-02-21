package com.tensquare.friend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

/**
 * 非好友实体
 */
@Data
@IdClass(NoFriend.class) //表示当前这个类的主键是联合主键
@Entity(name = "tb_nofriend")
@NoArgsConstructor
@AllArgsConstructor
public class NoFriend implements Serializable {
    /**
     * 用户编号
     */
    @Id
    private String userid;
    /**
     * 好友编号
     */
    @Id
    private String friendid;

}
