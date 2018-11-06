package com.label.biz.business.dao;
import com.label.biz.business.entity.Project;
import com.label.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
* 描述：标注平台用户DTO
* @author MuRunSen
* @date 2018-11-06 14:54:01
*/
@Mapper
public interface ProjectDao extends BaseDao<Project, Integer> {

}