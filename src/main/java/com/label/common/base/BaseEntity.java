package com.label.common.base;



import com.label.common.util.Globals;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;


/**
 * Entity基础类
 * 
 * @author Mmmmm
 *
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键ID */
	private Integer id;
	/** 创建人 */
	private String createUser;
	/** 创建日期 */
	private Timestamp createDate;
	/** 修改人 */
	private Date createdAt;
	/** 修改日期 */
	private Date updatedAt;
	/** 删除标识(0:正常；1：删除)默认正常 */
	private String isDelete = Globals.USER_TYPE_NORMAL;
	
	/** 创建人名称 */
	private String createUserName;
	/** 修改人名称 */
	private String updateUserName;

    /**
     * 验证码
     * @return
     */
    @NotNull
    private String vCode;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getvCode() {
        return vCode;
    }

    public void setvCode(String vCode) {
        this.vCode = vCode;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
