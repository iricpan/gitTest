package cn.bd.service;

import cn.bd.entity.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AreaService {
    /**
     * 保存区域
     * */
    void save(List<Area> list);
/**
 * 分页查询,带条件
 *
 * @param pageable
 * @param specifications*/
    Page<Area> findPageDAtaCondition(Specification<Area> specifications, Pageable  pageable );
}
