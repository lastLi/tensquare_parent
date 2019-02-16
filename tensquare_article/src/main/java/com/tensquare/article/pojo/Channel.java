package com.tensquare.article.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 频道实体类
 *
 * @author Administrator
 */
@Data
@Entity
@Table(name = "tb_channel")
@NoArgsConstructor
@AllArgsConstructor
public class Channel implements Serializable {

    @Id
    private String id;//ID


    /**
     * 频道名称
     */
    private String name;
    /**
     * 状态
     */
    private String state;


}
