package cn.bd.service;

import cn.bd.entity.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStandardService {
/*
 * 保存取派标准
 * **/
	void save(Standard standard);
/**
 * 根据取派名称查询
 * */
	List<Standard> findByName(String name);
	/**
	 *根据名称查询
	 * */
	List<Standard> findByNameLike(String string);
	/**
	 * 自定义查询名称
	 * */
	List<Standard> queryName(String string);
	/**
	 * 实体类上的自定义查询
	 * @param string2 
	 * */
//	List<Standard> queryName2(String string, String string2);
	/**
	 * 更新
	 * */
	void updateMinLength(int i, int j);
	/**
	 * 分页查询
	 * */
	Page<Standard> findPagedata(Pageable pageable);
	/**
	 * 删除派送标准
	 * */
	void delete(String ids);
	/**
	 * 查询所有
	 * */
	List<Standard> findAll();

}
