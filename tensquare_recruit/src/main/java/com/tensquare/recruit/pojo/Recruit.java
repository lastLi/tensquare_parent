package com.tensquare.recruit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 职位实体
 */
@Data
@Entity
@Table(name = "tb_recruit")
@NoArgsConstructor
@AllArgsConstructor
public class Recruit implements Serializable {
    @Id
    private String id;

    /**
     * 职位名称
     */
    private String jobname;

    /**
     * 薪资范围
     */
    private String salary;
    /**
     * 经验要求
     */
    private String condition;
    /**
     * 学历要求
     */
    private String education;
    /**
     * 任职方式
     */
    private String type;
    /**
     * 办公地址
     */
    private String address;
    /**
     * 企业ID
     */
    private String eid;
    /**
     * 创建日期
     */
    private Date createtime;
    /**
     * 状态 0是下架,1是很好
     */
    private String state;
    /**
     * 网址
     */
    private String url;
    /**
     * 标签
     */
    private String label;
    /**
     * 职位描述
     */
    private String content1;
    /**
     * 职位要求
     */
    private String content2;
}
