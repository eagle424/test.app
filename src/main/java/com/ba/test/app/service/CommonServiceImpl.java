package com.ba.test.app.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ba.test.app.dao.CommonDAO;
import com.ba.test.app.exception.BizException;

@Service("CommonService")
public class CommonServiceImpl extends AbstractService implements CommonService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Resource(name="CommonDAO")
	private CommonDAO commonDAO;

	@Override
	public Map<String, Object> selectOne(String queryId, Map<String, Object> map) throws Exception {
		try {
			return commonDAO.selectOne(queryId, map);
		} catch (Exception e) {
			throw new BizException("获取数据时出现异常");
		}
	}

	@Override
	public List<Map<String,Object>> select(String queryId, Map<String, Object> map) throws Exception {
		return commonDAO.select(queryId, map);
	}

	@Override
	public int update(String queryId, Map<String, Object> map) throws Exception {
		return commonDAO.update(queryId, map);
	}

	@Override
	public int insert(String queryId, Map<String, Object> map) throws Exception {
		return commonDAO.insert(queryId, map);
	}

	@Override
	public int delete(String queryId, Map<String, Object> map) throws Exception {
		return commonDAO.delete(queryId, map);
	}


}
