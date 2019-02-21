package com.tensquare.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Administrator
 */
@Data
@Entity
@Table(name = "tb_user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    private String id;//ID


    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private String sex;
    /**
     * 出生年月日
     */
    private java.util.Date birthday;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 注册日期
     */
    private java.util.Date regdate;
    /**
     * 修改日期
     */
    private java.util.Date updatedate;
    /**
     * 最好登录日期
     */
    private java.util.Date lastdate;
    /**
     * 在线时长(分钟)
     */
    private Long online;
    /**
     * 兴趣
     */
    private String interest;
    /**
     * 个性签名
     */
    private String personality;
    /**
     * 粉丝数
     */
    private Integer fanscount;
    /**
     * 关注数
     */
    private Integer followcount;


}
