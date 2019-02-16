package com.tensquare.base.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 城市
 */
@Data
@Table(name="tb_city")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable {
    @Id
    private String id;
    private String name;
    private String ishot;
}
