package cn.bd.dao;

import cn.bd.entity.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStandardRepository extends JpaRepository<Standard, Integer>,JpaSpecificationExecutor<Standard> {
/**
 * 根据取派名称查询
 * */
	List<Standard> findByName(String name);
/**
 * 根据从名称查询
 * */
	List<Standard> findByNameLike(String string);
	/**自定义query
	 *此时的value不能省略
	 * */
	@Query(value="from Standard where name = ?",nativeQuery=false)
    List<Standard> queryName(String string);
	/**
	 * 实体类上的自定义查询
	 * @param
	 * */
//	List<Standard> queryName2(String string, String string2);
	@Query(value="update Standard set minLength = ?2 where id =?1")
	@Modifying
	void updateMinLength(int i, int j);

}
