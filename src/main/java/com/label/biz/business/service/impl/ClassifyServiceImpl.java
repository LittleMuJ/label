package com.label.biz.business.service.impl;
import com.github.pagehelper.PageHelper;
import com.label.biz.business.entity.Classify;
import com.label.biz.business.service.ClassifyService;
import com.label.biz.business.dao.ClassifyDao;
import com.label.common.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
* 描述：标注平台用户 服务实现层
* @author MuRunSen
* @date 2018-11-06 16:35:31
*/
@Service("classifyService")
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    private ClassifyDao classifyDao;


    @Override
    public Classify getById(Integer id) throws Exception {
        Classify classify = classifyDao.getById(id);
        return classify;
    }

    @Override
    public void add(Classify classify) {
       classifyDao.add(classify);
    }

    @Override
    public void update(Classify classify){
       classifyDao.updateNotNull(classify);
    }

    @Override
	public void delete(Integer id) {

		classifyDao.delete(id);
	}

    @Override
    public PageModel<Classify> findByPage(Classify classify, PageModel<Classify> page) {
        PageHelper.offsetPage(page.getStart(),page.getLength());
        page.initData(classifyDao.findByPage(classify));
        return page;
    }
}