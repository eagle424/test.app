package com.ba.test.app.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.Assert;

public abstract class AbstractDAO extends SqlSessionDaoSupport implements RcpDAO {

	@Override
	@Resource(name = "sqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		Assert.notNull(sqlSessionFactory, "sqlSessionFactory must be not null");
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Override
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		Assert.notNull(sqlSessionTemplate, "sqlSessionTemplate must be not null");
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}


	/**
	 * 입력 처리 SQL mapping 을 실행한다.
	 * @param queryId
	 *        - 입력 처리 SQL mapping 쿼리 ID
	 * @param map
	 *        - 입력 처리 SQL mapping 입력 데이터를 세팅한 파라메터
	 *          객체(보통 VO 또는 Map)
	 * @return 입력 시 selectKey 를 사용하여 key 를 딴 경우 해당 key
	 */
	public int insert(String queryId, Map<String, Object> map) throws Exception {
		return super.getSqlSession().insert(queryId, map);
	}

	/**
	 * 수정 처리 SQL mapping 을 실행한다.
	 * @param queryId
	 *        - 수정 처리 SQL mapping 쿼리 ID
	 * @param map
	 *        - 수정 처리 SQL mapping 입력 데이터(key 조건 및 변경
	 *          데이터)를 세팅한 파라메터 객체(보통 VO 또는 Map)
	 * @return DBMS가 지원하는 경우 update 적용 결과 count
	 */
	public int update(String queryId, Map<String, Object> map) throws Exception {
		return super.getSqlSession().update(queryId, map);
	}

	/**
	 * 삭제 처리 SQL mapping 을 실행한다.
	 * @param queryId
	 *        - 삭제 처리 SQL mapping 쿼리 ID
	 * @param map
	 *        - 삭제 처리 SQL mapping 입력 데이터(일반적으로 key 조건)를
	 *          세팅한 파라메터 객체(보통 VO 또는 Map)
	 * @return DBMS가 지원하는 경우 delete 적용 결과 count
	 */
	public int delete(String queryId, Map<String, Object> map) throws Exception {
		return super.getSqlSession().delete(queryId, map);
	}

	/**
	 * pk 를 조건으로 한 단건조회 처리 SQL mapping 을 실행한다.
	 * @param queryId
	 *        - 단건 조회 처리 SQL mapping 쿼리 ID
	 * @param map
	 *        - 단건 조회 처리 SQL mapping 입력 데이터(key)를 세팅한
	 *          파라메터 객체(보통 VO 또는 Map)
	 * @return 결과 객체 - SQL mapping 파일에서 지정한
	 *         resultClass/resultMap 에 의한 단일 결과 객체(보통
	 *         VO 또는 Map)
	 */
	public Map<String, Object> selectOne(String queryId, Map<String, Object> map) throws Exception {
		return super.getSqlSession().selectOne(queryId, map);
	}

	/**
     * 리스트 조회 처리 SQL mapping 을 실행한다.
     * @param sQueryId
     *        - 리스트 조회 처리 SQL mapping 쿼리 ID
     * @param vo
     *        - 리스트 조회 처리 SQL mapping 입력 데이터(조회 조건)를
     *        세팅한 파라메터 Map 객체
     * @return 결과 List 객체 - SQL mapping 파일에서 지정한
     *         resultClass/resultMap 에 의한 결과 Map 객체 List
     */
	public List<Map<String, Object>> select(String queryId, Map<String, Object> map) throws Exception {
		return super.getSqlSession().selectList(queryId, map);
	}


	/**
	 * 부분 범위 리스트 조회 처리 SQL mapping 을 실행한다. (부분 범위 -
	 * pageIndex 와 pageSize 기반으로 현재 부분 범위 조회를 위한
	 * skipResults, maxResults 를 계산하여 ibatis 호출)
	 * @param queryId
	 *        - 리스트 조회 처리 SQL mapping 쿼리 ID
	 * @param map
	 *        - 리스트 조회 처리 SQL mapping 입력 데이터(조회 조건)를
	 *          세팅한 파라메터 객체(보통 VO 또는 Map)
	 * @param pageIndex
	 *        - 현재 페이지 번호
	 * @param pageSize
	 *        - 한 페이지 조회 수(pageSize)
	 * @return 부분 범위 결과 List 객체 - SQL mapping 파일에서 지정
	 *         resultClass/resultMap 에 의한 부분 범위 결과
	 *         객체(보통 VO 또는 Map) List
	 */
	@SuppressWarnings("rawtypes")
	public List listWithPaging(String queryId, Map<String, Object> map, int pageIndex, int pageSize) {
		int skipResults = pageIndex * pageSize;
		int maxResults = (pageIndex * pageSize) + pageSize;
		return super.getSqlSession().selectList(queryId, map, new RowBounds(skipResults, maxResults));
	}


	/**
     * 리스트 조회 처리 SQL을 실행한다.
     * @param sQuery
     *        - 완성된 Select Sql 문자열
     * @return 결과 List 객체 - SQL mapping 파일에서 지정한
     *         resultClass/resultMap 에 의한 결과 Map 객체 List
     */
	public List<Map<String, Object>> listBySql(String sQuery) throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("queryStr", sQuery);

		return super.getSqlSession().selectList("DirectSql.list", mapParam);
	}

	/**
     * pk 를 조건으로 한 단건조회 처리 SQL을 실행한다.
     * @param sQuery
     *        - 단건 종조회를 위한 완성된 Select Sql 문자열
     * @return 결과 객체 - SQL mapping 파일에서 지정한
     *         resultClass/resultMap 에 의한 단일 결과 Map 객체
     */
	@SuppressWarnings("rawtypes")
	public Map getBySql(String sQuery) throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("queryStr", sQuery);

		return (Map) super.getSqlSession().selectOne("DirectSql.get", mapParam);
	}

	/**
     * 입력 처리 SQL을 실행한다.
     * @param sQuery
     *        - 완성된 Insert Sql 문자열
     * @return void
     */
	public int insertBySql(String sQuery) throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("queryStr", sQuery);

		return super.getSqlSession().insert("DirectSql.insert", mapParam);
	}

	/**
     * 수정 처리 SQL을 실행한다.
     * @param sQuery
     *        - 완성된 Update Sql 문자열
     * @return void
     */
	public int updateBySql(String sQuery) throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("queryStr", sQuery);

		return super.getSqlSession().update("DirectSql.update", mapParam);
	}

	/**
     * 삭제 처리 SQL을 실행한다.
     * @param sQuery
     *        - 완성된 Delete Sql 문자열
     * @return void
     */
	public int deleteBySql(String sQuery) throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("queryStr", sQuery);

		return super.getSqlSession().delete("DirectSql.delete", mapParam);
	}


}
