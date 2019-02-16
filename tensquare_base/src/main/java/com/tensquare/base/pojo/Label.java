package com.tensquare.base.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 标签实体类
 */
@Data
@Entity
@Table(name = "tb_label")
@NoArgsConstructor
@AllArgsConstructor
public class Label implements Serializable {
    @Id
    private String id;

    /**
     * 标签名称
     */
    private String labelname;

    /**
     * 状态
     */
    private String state;

    /**
     * 使用数量
     */
    private Long count;

    /**
     * 是否推荐
     */
    private String recommend;

    /**
     * 关注数
     */
    private Long fans;
}
