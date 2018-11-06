package com.label.biz.business.dao;
import org.apache.ibatis.annotations.Mapper;

import com.label.common.base.BaseDao;
import com.label.biz.business.entity.Classify;

/**
* 描述：标注平台用户DTO
* @author MuRunSen
* @date 2018-11-06 16:35:31
*/
@Mapper
public interface ClassifyDao extends BaseDao<Classify, Integer>{

}