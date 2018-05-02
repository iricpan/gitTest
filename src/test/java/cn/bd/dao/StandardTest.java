package cn.bd.dao;


import cn.bd.entity.Standard;
import cn.bd.service.IStandardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class StandardTest {
	@Autowired
	private IStandardService standardService;
	@Test
	public void standardTest(){
		//List<Standard> byName = standardService.findByName("77-88kg");
		List<Standard> byName = standardService.findByNameLike("%kg%");
		System.out.println(byName);
	}
	@Test
	public void standardQueryTest2(){
		List<Standard> byName = standardService.queryName("77-88kg");
		System.out.println(byName);
	}
	@Test
	public void standardQueryTest3(){
		standardService.updateMinLength(2,15);
	}

}
