package com.tensquare.friend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

/**
 * 好友实体
 */
@Data
@IdClass(Friend.class) //表示当前这个类的主键是联合主键
@Entity(name = "tb_friend")
@NoArgsConstructor
@AllArgsConstructor
public class Friend implements Serializable {
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
    /**
     * 是否喜欢
     */
    private String islike;
}
