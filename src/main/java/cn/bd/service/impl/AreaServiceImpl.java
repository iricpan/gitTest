package cn.bd.service.impl;

import cn.bd.dao.AreaRepository;
import cn.bd.entity.Area;
import cn.bd.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;
    @Override
    public void save(List<Area> list) {
        areaRepository.save (list);
    }

    @Override
    public Page<Area> findPageDAtaCondition(Specification<Area> specifications, Pageable pageable) {

        return areaRepository.findAll (specifications,pageable);
    }
}
