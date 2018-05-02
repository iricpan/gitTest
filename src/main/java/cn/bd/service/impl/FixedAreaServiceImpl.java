package cn.bd.service.impl;


import cn.bd.dao.FixedAreaRepository;
import cn.bd.entity.FixedArea;
import cn.bd.service.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaRepository fixedAreaRepository;

	@Override
	public void save(FixedArea model) {
		fixedAreaRepository.save (model);
	}

	@Override
	public Page<FixedArea> findPageDataCondition(Specification<FixedArea> specification, Pageable pageable) {
		return fixedAreaRepository.findAll (specification,pageable);
	}
}
