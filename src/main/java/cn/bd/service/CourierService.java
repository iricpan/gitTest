package cn.bd.service;

import cn.bd.entity.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CourierService {
/**
 * 添加取派员
 * */
	void save(Courier courier);
/**
 * 分页查询
 * */
Page<Courier> pageQuery(Pageable pageable);
/**
 * 有条件的分页查询
 * */
Page<Courier> findPageQueryCourierByCondition(Specification<Courier> spec,
                                              Pageable pageable);
/**
 * 删除快递员
 * */
void delete(String ids);
/**
 * 恢复快递员
 * */
void restore(String ids);


}
