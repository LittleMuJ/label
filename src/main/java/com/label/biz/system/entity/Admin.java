package com.label.biz.system.entity;
import java.io.Serializable;

import com.label.common.base.BaseEntity;

import javax.validation.constraints.NotNull;


/**
* 描述：标注平台用户模型
* @author MuRunSen
* @date 2018-11-02 11:07:14
*/

public class Admin extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
    *
    */
    @NotNull
    private String phone;

    /**
    *
    */
    private String email;

    /**
    *
    */
    @NotNull
    private String username;

    /**
    *
    */
    @NotNull
    private String password;


    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}