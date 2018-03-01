package com.ba.test.app.service;

import java.util.List;
import java.util.Map;

public interface CommonService {
	Map<String, Object> selectOne(String queryId, Map<String, Object> map) throws Exception;

	List<Map<String, Object>> select(String queryId, Map<String, Object> map) throws Exception;

	int update(String queryId, Map<String, Object> map) throws Exception;

	int insert(String queryId, Map<String, Object> map) throws Exception;

	int delete(String queryId, Map<String, Object> map) throws Exception;
}
