package com.tensquare.spit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Spit implements Serializable {
    /**
     * 吐槽编号
     */
    @Id
    private String _id;

    /**
     * 吐槽内容
     */
    private String content;
    /**
     * 发布时间
     */
    private Date publishtime;
    /**
     * 发布人id
     */
    private String userid;
    /**
     * 发布人昵称
     */
    private String nickname;
    /**
     * 浏览量
     */
    private Integer visits;
    /**
     * 点赞数
     */
    private Integer thumbup;
    /**
     * 分享数
     */
    private Integer share;

    /**
     * 回复数
     */
    private Integer comment;

    /**
     * 是否可见,0是不可见 1是可见
     */
    private String state;
    /**
     * 上级ID
     */
    private String parentid;
}
