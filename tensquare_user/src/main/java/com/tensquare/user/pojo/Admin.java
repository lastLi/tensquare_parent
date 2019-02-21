package com.tensquare.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tb_admin")
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {
    
    @Id
    private String id;
    
    /**登录名称*/
    private String loginname;
    
    /**密码*/
    private String password;
    
    /**状态*/
    private String state;
}
