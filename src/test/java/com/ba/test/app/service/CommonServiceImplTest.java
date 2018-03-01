package com.ba.test.app.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.ba.test.app.BaseTest;
import com.ba.test.app.vo.RcpMap;

public class CommonServiceImplTest extends BaseTest {

	public static final String TEST_FAIL = "非异常测试出现异常!";

	@Resource(name="CommonService")
	private CommonService commonService;


	/**
	 *
	 */
	@Test
	public void testC01_001(){
		String queryId  = "msUsr.list";
		Map<String, Object> map = new RcpMap<String, Object>();
		map.put("cpnId","CPN0000001");
		map.put("usrId","USR00000001");
		try {
			List<Map<String, Object>> list = commonService.select(queryId, map);
			System.out.println(list.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(TEST_FAIL);
		}
	}


}
