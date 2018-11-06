package com.label.biz.system.service;

import com.label.biz.system.entity.Admin;
/**
* 描述：标注平台用户 服务实现层接口
* @author MuRunSen
* @date 2018-11-02 11:07:14
*/
public interface AdminService {

    /**
    * 描述：根据Id获取DTO
    * @param id
    */
    Admin getById(Integer id)throws Exception;

    //AdminDTO createAdmin(AdminDTO adminDTO) throws Exception;
    void add(Admin admin);

    void delete(Integer id) throws Exception;

    //AdminDTO updateAdmin(AdminDTO adminDTO) throws Exception;
    void update(Admin admin);

    int insertAndGetId(Admin admin);

    Admin getByParam(Admin admin);
}