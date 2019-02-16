package com.tensquare.qa.pojo;

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
@Table(name = "tb_problem")
@NoArgsConstructor
@AllArgsConstructor
public class Problem implements Serializable {

    @Id
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建日期
     */
    private java.util.Date createtime;
    /**
     * 修改日期
     */
    private java.util.Date updatetime;
    /**
     * 用户ID
     */
    private String userid;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 浏览量
     */
    private Long visits;
    /**
     * 点赞数
     */
    private Long thumbup;
    /**
     * 回复数
     */
    private Long reply;
    /**
     * 是否解决
     */
    private String solve;
    /**
     * 回复人昵称
     */
    private String replyname;
    /**
     * 回复日期
     */
    private java.util.Date replytime;


}
