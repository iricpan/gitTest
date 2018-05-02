package cn.bd.service.impl;

import cn.bd.dao.CourierRepository;
import cn.bd.entity.Courier;
import cn.bd.service.CourierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourierServiceImpl implements CourierService {
	@Autowired
	private CourierRepository courierRepository;
	@Override
	public void save(Courier courier) {
		courierRepository.save(courier);
	}
	@Override
	public Page<Courier> pageQuery(Pageable pageable) {
		
		return courierRepository.findAll(pageable);
	}
	@Override
	public Page<Courier> findPageQueryCourierByCondition(
			Specification<Courier> spec, Pageable pageable) {
		return courierRepository.findAll(spec, pageable);
	}
	@Override
	public void delete(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] split = ids.split(",");
			for (String id : split) {
				courierRepository.updateById(Integer.valueOf(id));
			}
		}
	}
	@Override
	public void restore(String ids) {
		if(StringUtils.isNoneBlank(ids)){
			String[] split = ids.split(",");
			for (String id : split) {
				courierRepository.updateCourierById(Integer.valueOf(id));
			}
		}
	}

}
