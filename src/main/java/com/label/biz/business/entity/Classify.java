package com.label.biz.business.entity;
import java.io.Serializable;

import com.label.common.base.BaseEntity;

/**
* 描述：标注平台用户模型
* @author MuRunSen
* @date 2018-11-06 16:35:31
*/

public class Classify extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
    /**
    *
    */
    /**
    *
    */
    private String name;

    /**
    *
    */
    /**
    *
    */

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


}