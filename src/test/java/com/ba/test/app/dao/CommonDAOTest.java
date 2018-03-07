package com.ba.test.app.dao;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.ba.test.app.BaseTest;
import com.ba.test.app.vo.RcpMap;
import com.github.pagehelper.PageHelper;

public class CommonDAOTest extends BaseTest {

	public static final String TEST_FAIL = "非异常测试出现异常!";

	@Resource(name="CommonDAO")
	private CommonDAO commonDao;

	/**
	 * method: list(queryId, map)
	 * 1. 不出异常
	 *
	 */
	@Test
	public void Test01_R001(){
		String queryId = "msUsr.list";
		String cpnId = "CPN0000001";
		String usrId = "USR00000001";
		Map<String, Object> pMap = new RcpMap<String, Object>();
		pMap.put("cpnId", cpnId);
		pMap.put("usrId", usrId);
		try {
			List<Map<String, Object>> list = commonDao.select(queryId, pMap);
			for(Map<String, Object> map : list){
				for(Map.Entry<String,Object> entry : map.entrySet()){
					System.out.println(entry);
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
			fail(TEST_FAIL);
		}

	}

	/**
	 * 分页查询
	 */
	@Test
	public void Test02_R002(){
		String queryId = "msUsr.list";
		String cpnId = "CPN0000001";
		String usrId = "USR00000001";
		String bzoId = "BZO000000122";
		Map<String, Object> pMap = new RcpMap<String, Object>();
		pMap.put("cpnId", cpnId);
		pMap.put("bzoId", bzoId);
		try {
			PageHelper.startPage(1, 10);
			List<Map<String, Object>> list = commonDao.select(queryId, pMap);
			System.out.println(list.toString());

//			PageRowBounds pageBounds = new PageRowBounds(0, 10);
//			List<Map<String, Object>> list = commonDao.select(queryId, pMap, pageBounds);
//			System.out.println(list.toString());
//			System.out.println(pageBounds.getTotal());

		} catch (Exception e) {
			e.getStackTrace();
			fail(TEST_FAIL);
		}

	}
}
