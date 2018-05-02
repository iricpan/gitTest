package cn.bd.service.impl;

import cn.bd.dao.IStandardRepository;
import cn.bd.entity.Standard;
import cn.bd.service.IStandardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StandardServiceImpl implements IStandardService {
	@Autowired
	private IStandardRepository standardRepository;
	@Override
	public void save(Standard standard) {
		standardRepository.save(standard);

	}
	@Override
	public List<Standard> findByName(String name) {
		return standardRepository.findByName(name);
	}
	@Override
	public List<Standard> findByNameLike(String string) {
		return standardRepository.findByNameLike(string);
	}
	@Override
	public List<Standard> queryName(String string) {
		
		return standardRepository.queryName(string);
	}
	@Override
	public void updateMinLength(int i, int j) {
		standardRepository.updateMinLength(i,j);
		
	}
	@Override
	public Page<Standard> findPagedata(Pageable pageable) {
		return standardRepository.findAll(pageable);
	}
	
	@Override
	public void delete(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] split = ids.split(",");
			for (String id : split) {
				standardRepository.delete(Integer.valueOf(id));
			}
		}
		
	}
	@Override
	public List<Standard> findAll() {
		
		return standardRepository.findAll();
	}

}
