package com.label.biz.system.dao;
import com.label.biz.system.entity.Admin;
import com.label.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;


/**
* 描述：标注平台用户DTO
* @author MuRunSen
* @date 2018-11-02 11:07:14
*/
@Mapper
public interface AdminDao extends BaseDao<Admin, Integer> {

    int insertAndGetId(Admin admin);

    Admin getByParam(Admin admin);
}