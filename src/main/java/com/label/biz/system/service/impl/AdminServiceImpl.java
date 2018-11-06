package com.label.biz.system.service.impl;
import com.label.biz.system.entity.Admin;
import com.label.biz.system.service.AdminService;
import com.label.biz.system.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
* 描述：标注平台用户 服务实现层
* @author MuRunSen
* @date 2018-11-02 11:07:14
*/
@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;


    @Override
    public Admin getById(Integer id) throws Exception {
        Admin admin = adminDao.getById(id);
        return admin;
    }

    @Override
    public void add(Admin admin) {
       adminDao.add(admin);
    }

    @Override
    public void update(Admin admin){
       adminDao.updateNotNull(admin);
    }

    @Override
	public void delete(Integer id) {

		adminDao.delete(id);
	}

	//插入数据并过去该id
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public int insertAndGetId(Admin admin) {
        try {
            admin.setCreatedAt(new Date());
            admin.setUpdatedAt(new Date());
            return adminDao.insertAndGetId(admin);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("注册失败");
        }
    }

    @Override
    public Admin getByParam(Admin admin) {
        return adminDao.getByParam(admin);
    }
}