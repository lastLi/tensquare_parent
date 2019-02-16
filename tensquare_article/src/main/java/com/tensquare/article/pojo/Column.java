package com.tensquare.article.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 专栏实体类
 *
 * @author Administrator
 */
@Data
@Entity
@Table(name = "tb_column")
@NoArgsConstructor
@AllArgsConstructor
public class Column implements Serializable {

    @Id
    private String id;


    /**
     * 专栏名称
     */
    private String name;
    /**
     * 专栏简介
     */
    private String summary;
    /**
     * 用户ID
     */
    private String userid;
    /**
     * 申请日期
     */
    private java.util.Date createtime;
    /**
     * 审核日期
     */
    private java.util.Date checktime;
    /**
     * 状态
     */
    private String state;





}
