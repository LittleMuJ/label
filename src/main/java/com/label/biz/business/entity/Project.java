package com.label.biz.business.entity;
import java.io.Serializable;

import com.label.common.base.BaseEntity;

/**
* 描述：标注平台用户模型
* @author MuRunSen
* @date 2018-11-06 16:30:50
*/

public class Project extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
    /**
    *
    */
    /**
    *项目名称
    */
    private String name;

    /**
    *项目描述
    */
    private String description;

    /**
    *归属甲方id
    */
    private Integer customerId;

    /**
    *
    */
    /**
    *
    */
    /**
    *是否已经删除
    1.未删除
    0.已删除
    */
    /**
    *标注类型
    1.视频
    2.音频
    3.图片
    4.文字标注类（也是图片）
    */
    private Integer type;

    /**
    *是否是多期
    */
    private boolean multi;
    /**
    *分类id，从classify查询
    */
    private Integer classifyId;

    /**
    *试题取题的数量(可以不在初始化的时候设置)
    */
    private Integer examNum;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean getMulti() {
        return this.multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }
    public Integer getClassifyId() {
        return this.classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    public Integer getExamNum() {
        return this.examNum;
    }

    public void setExamNum(Integer examNum) {
        this.examNum = examNum;
    }


}