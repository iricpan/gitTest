package cn.bd.service;

import cn.bd.entity.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface FixedAreaService {
    /**
     * 添加定区
     * */
    void save(FixedArea model);
/**
 * 分页查询
 * */
    Page<FixedArea> findPageDataCondition(Specification<FixedArea> specification, Pageable pageable);

}
