package cn.bd.dao;

import cn.bd.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Integer>,JpaSpecificationExecutor<Courier> {
	/**
	 * 删除快递员
	 * */
	@Query(value="update Courier set deltag = '1' where id = ?1")
	@Modifying
	void updateById(Integer id);
	
	/**
	 * 恢复快递员
	 * */
	@Query(value="update Courier set deltag = '' where id = ?1")
	@Modifying
	void updateCourierById(Integer id);

}
