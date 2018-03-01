package com.ba.test.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-*.xml")
@Transactional(transactionManager="transactionManager")
@Rollback(true)
public class BaseTest {

	/**
	 * 测试
	 */
	@Test
	public void test(){
		// 基础模板.
	}
}
