package com.label.biz.business.service;

import com.label.biz.business.entity.Classify;
import com.label.common.model.PageModel;

/**
* 描述：标注平台用户 服务实现层接口
* @author MuRunSen
* @date 2018-11-06 16:35:31
*/
public interface ClassifyService {

    /**
    * 描述：根据Id获取DTO
    * @param id
    */
    Classify getById(Integer id)throws Exception;

    //ClassifyDTO createClassify(ClassifyDTO classifyDTO) throws Exception;
    void add(Classify classify);

    void delete(Integer id) throws Exception;

    //ClassifyDTO updateClassify(ClassifyDTO classifyDTO) throws Exception;
    void update(Classify classify);

    PageModel<Classify> findByPage(Classify classify, PageModel<Classify> page);
}