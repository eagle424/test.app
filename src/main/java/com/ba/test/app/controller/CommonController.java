package com.ba.test.app.controller;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ba.test.app.exception.BizException;
import com.ba.test.app.exception.RCPException;
import com.ba.test.app.vo.Json;
import com.ba.test.app.vo.JsonSaveVo;

@RestController
public class CommonController extends AbstractController {
	protected Logger log = LogManager.getLogger(this.getClass());

	@Value("#{commonProp['export.webPath']}")
	private String webPath;

	@Value(value="#{commonProp['attach.rootPath']}")
	private String attachRootPath;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/list", produces="application/json; charset=UTF-8")
//	@RequestMapping(value="/list", produces="application/json;charset=utf-8")
	public Json<List> list(@RequestBody Map<String, String> params) throws Exception {
		Json<List> json = new Json<List>();
		Object bean = null;
		Method objMethod = null;

		try{
			if(params == null || params.size() == 0) {
				throw new Exception("Empty Parameters");
			}

			String service = params.get("service");
			String method = params.get("method");
			String queryId = params.get("queryId");

			if(service == null || StringUtils.isEmpty(service))
				service = "common";
			if(method == null || StringUtils.isEmpty(method))
				method = "select";

			bean = super.getBean(service);
			objMethod = super.getMethod(bean, method);

			if(objMethod != null) {
				switch(objMethod.getParameterTypes().length) {
				case 0:
					json.setRows((List) objMethod.invoke(bean));
					break;
				case 1:
					json.setRows((List) objMethod.invoke(bean, new Object[] {super.setCommonParam(params)}));
					break;
				default:
					if(queryId == null || StringUtils.isEmpty(queryId)) {
						throw new BizException("Empty Query Id");
					}

					json.setRows((List) objMethod.invoke(bean, new Object[] {queryId, super.setCommonParam(params)}));
				}
			}

			json.setErrorCode(0);
			json.setErrorMsg("Success");
		}
		catch(Exception e){
			log.error(params, e);

			json.setErrorCode(-1);
			if(e instanceof BizException || e instanceof RCPException)
				json.setErrorMsg(e.getMessage());
			else
				json.setErrorMsg(e.getCause().getMessage());
		}

		return json;
	}

	@RequestMapping(value="/get.do", produces="application/json;charset=utf-8")
	public Json<Object> get(@RequestBody Map<String, String> params) throws Exception {
		Json<Object> json = new Json<Object>();
		Object bean = null;
		Method objMethod = null;

		try{
			if(params == null || params.size() == 0) {
				throw new BizException("Empty Parameters");
			}

			String service = params.get("service");
			String method = params.get("method");
			String sQueryId = params.get("queryId");

			if(service == null || StringUtils.isEmpty(service))
				service = "common";

			if(method == null || StringUtils.isEmpty(method))
				method = "get";

			bean = super.getBean(service);
			objMethod = super.getMethod(bean, method);

			if(objMethod != null) {
				switch(objMethod.getParameterTypes().length) {
				case 0:
					json.setRows(objMethod.invoke(bean));
					break;
				case 1:
					json.setRows(objMethod.invoke(bean, new Object[] {super.setCommonParam(params)}));
					break;
				default:
					if(sQueryId == null || StringUtils.isEmpty(sQueryId)) {
						throw new BizException("Empty Query Id");
					}

					json.setRows(objMethod.invoke(bean, new Object[] {sQueryId, super.setCommonParam(params)}));
				}
			}

			json.setErrorCode(0);
			json.setErrorMsg("Success");
		}
		catch(Exception e){
			log.error(params, e);

			json.setErrorCode(-1);
			if(e instanceof BizException || e instanceof RCPException)
				json.setErrorMsg(e.getMessage());
			else
				json.setErrorMsg(e.getCause().getMessage());
		}

		return json;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/select.do", produces="application/json;charset=utf-8")
	public Json<HashMap> select(@RequestBody Map<String, String> params) throws Exception {
		HashMap mapData = new HashMap();
		Json<HashMap> json = new Json<HashMap>();
		Object bean = null;
		Method objMethod = null;

		try{
			if(params == null || params.size() == 0) {
				throw new BizException("Empty Parameters");
			}

			String service = params.get("service");
			String method = params.get("method");
			String queryId = params.get("queryId");

			if(service == null || StringUtils.isEmpty(service))
				service = "common";
			if(method == null || StringUtils.isEmpty(method))
				method = "list";

			String[] sQueryIds = null;
			if(queryId != null && !StringUtils.isEmpty(queryId))
				sQueryIds = queryId.split(",");

			String[] sServices = service.split(",");
			String[] sMethods = method.split(",");
			int length = 0;

			if(sQueryIds != null)
				length = sQueryIds.length;

			if(sServices != null && length <= 1)
				length = sServices.length;

			if(sMethods != null && length <= 1)
				length = sMethods.length;

			for(int i=0; i<length; i++) {
				if(sServices.length == 1)
					bean = super.getBean(sServices[0]);
				else
					bean = super.getBean(sServices[i]);

				if(sMethods.length == 1)
					objMethod = super.getMethod(bean, sMethods[0]);
				else
					objMethod = super.getMethod(bean, sMethods[i]);

				if(objMethod != null) {
					switch(objMethod.getParameterTypes().length) {
					case 0:
						mapData.put("dataset"+(i+1), objMethod.invoke(bean));
						break;
					case 1:
						mapData.put("dataset"+(i+1), objMethod.invoke(bean, new Object[] {super.setCommonParam(params)}));
						break;
					default:
						if(sQueryIds == null || sQueryIds[i] == null || StringUtils.isEmpty(sQueryIds[i])) {
							throw new BizException("Empty Query Id");
						}

						super.setCommonParam(params);

						if(sQueryIds.length == 1)
							mapData.put("dataset"+(i+1), objMethod.invoke(bean, new Object[] {sQueryIds[0], params}));
						else
							mapData.put("dataset"+(i+1), objMethod.invoke(bean, new Object[] {sQueryIds[i], params}));
					}
				}
			}

			json.setRows(mapData);
			json.setErrorCode(0);
			json.setErrorMsg("Success");
        }
        catch(Exception e) {
        	log.error(params, e);

        	json.setErrorCode(-1);
        	if(e instanceof BizException || e instanceof RCPException)
				json.setErrorMsg(e.getMessage());
			else
				json.setErrorMsg(e.getCause().getMessage());
        }

		return json;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/paging.do", produces="application/json;charset=utf-8")
	public Json<List> paging(@RequestBody Map<String, String> params) throws Exception {
		Json<List> json = new Json<List>();
		Object bean = null;
		Method objMethod = null;
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		try{
			if(params == null || params.size() == 0) {
				throw new BizException("Empty Parameters");
			}

			String service = params.get("service");
			String method = params.get("method");
			String queryId = params.get("queryId");

			if(queryId == null || StringUtils.isEmpty(queryId))
				throw new BizException("Empty Query Id");

			if(service == null || StringUtils.isEmpty(service))
				service = "common";

			if(method == null || StringUtils.isEmpty(method))
				method = "paging";

			bean = super.getBean(service);
			objMethod = super.getMethod(bean, method);

			if(objMethod != null) {
				if(objMethod.getParameterTypes().length == 1) {
					mapReturn = (Map<String, Object>) objMethod.invoke(bean, new Object[] {super.setCommonParam(params)});
				}
				else {
					mapReturn = (Map<String, Object>) objMethod.invoke(bean, new Object[] {queryId, super.setCommonParam(params)});
				}
			}

			if(mapReturn != null) {
				json.setPage((Integer) mapReturn.get("page"));
				json.setRecords((Integer) mapReturn.get("records"));
				json.setTotal((Integer) mapReturn.get("total"));
				json.setRows((List) mapReturn.get("dataset"));
			}
			else {
				json.setPage((Integer) 1);
				json.setRecords(Integer.parseInt(params.get("rows")));
				json.setTotal(0);
				json.setRows((List) null);
			}

			json.setErrorCode(0);
			json.setErrorMsg("Success");
		}
		catch(Exception e){
			log.error(params, e);

			json.setErrorCode(-1);
			if(e instanceof BizException || e instanceof RCPException)
				json.setErrorMsg(e.getMessage());
			else
				json.setErrorMsg(e.getCause().getMessage());
		}

		return json;
	}

	@RequestMapping(value="/create.do", produces="application/json;charset=utf-8")
	public Json<Integer> create(@RequestBody Map<String, String> params) throws Exception {
		Json<Integer> json = new Json<Integer>();
		Object bean = null;
		Method oMethod = null;

		try{
			if(params == null || params.size() == 0) {
				throw new BizException("Empty Parameters");
			}

			String service = params.get("service");
			String method = params.get("method");
			String queryId = params.get("queryId");

			if(service == null || StringUtils.isEmpty(service))
				service = "common";

			if(method == null || StringUtils.isEmpty(method))
				method = "insert";

			bean = super.getBean(service);
			oMethod = super.getMethod(bean, method);

			if(oMethod != null) {
				if(oMethod.getParameterTypes().length == 1)
					json.setRows((Integer) oMethod.invoke(bean, new Object[] {super.setCommonParam(params)}));
				else {
					if(queryId == null || StringUtils.isEmpty(queryId))
						throw new BizException("Empty Query Id");

					json.setRows((Integer) oMethod.invoke(bean, new Object[] {queryId, super.setCommonParam(params)}));
				}
			}

			json.setErrorCode(0);
			json.setErrorMsg("Success");
		}
		catch(Exception e){
			log.error(params, e);

			json.setErrorCode(-1);
			if(e instanceof BizException || e instanceof RCPException)
				json.setErrorMsg(e.getMessage());
			else
				json.setErrorMsg(e.getCause().getMessage());
		}

		return json;
	}

	@RequestMapping(value="/modify.do", produces="application/json;charset=utf-8")
	public Json<Integer> modify(@RequestBody Map<String, String> params) throws Exception {
		Json<Integer> json = new Json<Integer>();
		Object bean = null;
		Method oMethod = null;

		try{
			if(params == null || params.size() == 0)
				throw new BizException("Empty Parameters");

			String service = params.get("service");
			String method = params.get("method");
			String queryId = params.get("queryId");

			if(service == null || StringUtils.isEmpty(service))
				service = "common";

			if(method == null || StringUtils.isEmpty(method))
				method = "update";

			bean = super.getBean(service);
			oMethod = super.getMethod(bean, method);

			if(oMethod != null) {
				if(oMethod.getParameterTypes().length == 1)
					json.setRows((Integer) oMethod.invoke(bean, new Object[] {super.setCommonParam(params)}));
				else {
					if(queryId == null || StringUtils.isEmpty(queryId))
						throw new BizException("Empty Query Id");

					json.setRows((Integer) oMethod.invoke(bean, new Object[] {queryId, super.setCommonParam(params)}));
				}
			}

			json.setErrorCode(0);
			json.setErrorMsg("Success");
		}
		catch(Exception e){
			log.error(params, e);

			json.setErrorCode(-1);
			if(e instanceof BizException || e instanceof RCPException)
				json.setErrorMsg(e.getMessage());
			else
				json.setErrorMsg(e.getCause().getMessage());
		}

		return json;
	}

	@RequestMapping(value="/remove.do", produces="application/json;charset=utf-8")
	public Json<Integer> remove(@RequestBody Map<String, String> params) throws Exception {
		Json<Integer> json = new Json<Integer>();
		Object bean = null;
		Method oMethod = null;

		try{
			if(params == null || params.size() == 0) {
				throw new BizException("Empty Parameters");
			}

			String service = params.get("service");
			String method = params.get("method");
			String queryId = params.get("queryId");

			if(service == null || StringUtils.isEmpty(service))
				service = "common";

			if(method == null || StringUtils.isEmpty(method))
				method = "delete";

			bean = super.getBean(service);
			oMethod = super.getMethod(bean, method);

			if(oMethod != null) {
				if(oMethod.getParameterTypes().length == 1)
					json.setRows((Integer) oMethod.invoke(bean, new Object[] {super.setCommonParam(params)}));
				else {
					if(queryId == null || StringUtils.isEmpty(queryId))
						throw new BizException("Empty Query Id");

					json.setRows((Integer) oMethod.invoke(bean, new Object[] {queryId, super.setCommonParam(params)}));
				}
			}

			json.setErrorCode(0);
			json.setErrorMsg("Success");
		}
		catch(Exception e){
			log.error(params, e);

			json.setErrorCode(-1);
			if(e instanceof BizException || e instanceof RCPException)
				json.setErrorMsg(e.getMessage());
			else
				json.setErrorMsg(e.getCause().getMessage());
		}

		return json;
	}

	@RequestMapping(value="/call.do", produces="application/json;charset=utf-8")
	public Json<Object> call(@RequestBody Map<String, String> params) throws Exception {
		Json<Object> json = new Json<Object>();
		Object bean = null;
		Method oMethod = null;

		try{
			if(params == null) {
				throw new BizException("Empty Parameters");
			}

			String service = params.get("service");
			String method = params.get("method");
			String queryId = params.get("queryId");

			if(service == null || StringUtils.isEmpty(service))
				service = "common";

			if(method == null || StringUtils.isEmpty(method))
				method = "call";

			bean = super.getBean(service);
			oMethod = super.getMethod(bean, method);

			if(oMethod != null) {
				switch(oMethod.getParameterTypes().length) {
				case 0:
					json.setRows(oMethod.invoke(bean));
					break;
				case 1:
					json.setRows(oMethod.invoke(bean, new Object[] {super.setCommonParam(params)}));
					break;
				default:
					if(queryId == null || StringUtils.isEmpty(queryId)) {
						throw new BizException("Empty Query Id");
					}

					json.setRows(oMethod.invoke(bean, new Object[] {queryId, super.setCommonParam(params)}));
				}
			}

			json.setErrorCode(0);
			json.setErrorMsg("Success");
		}
		catch(Exception e){
			log.error(params, e);

			json.setErrorCode(-1);
			if(e instanceof BizException || e instanceof RCPException)
				json.setErrorMsg(e.getMessage());
			else
				json.setErrorMsg(e.getCause().getMessage());
		}

		return json;
	}

	@RequestMapping(value="/callRows.do", produces="application/json;charset=utf-8")
	public Json<Object> callRows(@RequestBody JsonSaveVo params) throws Exception {
		Json<Object> json = new Json<Object>();
		Object bean = null;
		Method oMethod = null;

		try{
			if(params == null) {
				throw new BizException("Empty Parameters");
			}

			String service = params.getService();
			String method = params.getMethod();
			String queryId = params.getQueryId();

			if(service == null || StringUtils.isEmpty(service))
				service = "common";

			if(method == null || StringUtils.isEmpty(method))
				method = "callRows";

			bean = super.getBean(service);
			oMethod = super.getMethod(bean, method);

			if(oMethod != null) {
				switch(oMethod.getParameterTypes().length) {
				case 0:
					json.setRows(oMethod.invoke(bean));
					break;
				case 1:
					json.setRows(oMethod.invoke(bean, new Object[] {super.setCommonParam(params)}));
					break;
				default:
					if(queryId == null || StringUtils.isEmpty(queryId)) {
						throw new BizException("Empty Query Id");
					}

					json.setRows(oMethod.invoke(bean, new Object[] {queryId, super.setCommonParam(params)}));
				}
			}

			json.setErrorCode(0);
			json.setErrorMsg("Success");
		}
		catch(Exception e){
			log.error(params, e);

			json.setErrorCode(-1);
			if(e instanceof BizException || e instanceof RCPException)
				json.setErrorMsg(e.getMessage());
			else
				json.setErrorMsg(e.getCause().getMessage());
		}

		return json;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/checkExport.do")
	public Json<Map> checkExport(HttpServletRequest req, HttpServletResponse res, @RequestBody Map<String, Object> params) throws Exception {
		Json<Map> json = new Json<Map>();
		HashMap<String, String> mapRtn = new HashMap<String, String>();
		File file = null;
		Object bean = null;
		Method objMethod = null;

		try{
			if(params == null || params.size() == 0) {
				throw new Exception("Empty Parameters");
			}

			String service = (String) params.get("service");
			String method = (String) params.get("method");
			String queryId = (String) params.get("queryId");
			String fileName = (String) params.get("fileName");

			if(service == null || StringUtils.isEmpty(service))
				service = "excel";
			if(method == null || StringUtils.isEmpty(method))
				method = "download";
			if(fileName == null || StringUtils.isEmpty(fileName))
				fileName = "export";

			bean = super.getBean(service);
			objMethod = super.getMethod(bean, method);

			if(objMethod != null) {
				switch(objMethod.getParameterTypes().length) {
				case 0:
					file = (File) objMethod.invoke(bean);
					break;
				case 1:
					file = (File) objMethod.invoke(bean, new Object[] {req});
					break;
				case 2:
					file = (File) objMethod.invoke(bean, new Object[] {req, fileName});
					break;
				case 3:
					file = (File) objMethod.invoke(bean, new Object[] {req, fileName, super.setCommonParam(params)});
					break;
				default:
					if(queryId == null || StringUtils.isEmpty(queryId)) {
						throw new BizException("Empty Query Id");
					}

					file = (File) objMethod.invoke(bean, new Object[] {req, fileName, queryId, super.setCommonParam(params)});
				}
			}

			if(file.exists()) {
				mapRtn.put("fileName", file.getName());

				json.setErrorCode(0);
				json.setErrorMsg("Success");
				json.setRows(mapRtn);
			}
			else {
				json.setErrorCode(-1);
				json.setErrorMsg("Export Fail");
			}
		}
		catch(Exception e) {
			log.error(params, e);

			json.setErrorCode(-1);
			if(e instanceof BizException || e instanceof RCPException)
				json.setErrorMsg(e.getMessage());
			else
				json.setErrorMsg(e.getCause().getMessage());
		}

		return json;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/exportExl.do")
	public Json<Map> exportExl(HttpServletRequest req, HttpServletResponse res, @RequestBody Map<String, Object> params) throws Exception {
		Json<Map> json = new Json<Map>();
		HashMap<String, String> mapRtn = new HashMap<String, String>();
		File file = null;
		Object bean = null;
		Method objMethod = null;

		try{
			if(params == null || params.size() == 0) {
				throw new Exception("Empty Parameters");
			}

			String service = (String) params.get("service");
			String method = (String) params.get("method");
			String queryId = (String) params.get("queryId");
			String fileName = (String) params.get("fileName");

			if(service == null || StringUtils.isEmpty(service))
				service = "excel";
			if(method == null || StringUtils.isEmpty(method))
				method = "exportExl";
			if(fileName == null || StringUtils.isEmpty(fileName))
				fileName = "export";

			bean = super.getBean(service);
			objMethod = super.getMethod(bean, method);

			if(objMethod != null) {
				switch(objMethod.getParameterTypes().length) {
				case 0:
					file = (File) objMethod.invoke(bean);
					break;
				case 1:
					file = (File) objMethod.invoke(bean, new Object[] {req});
					break;
				case 2:
					file = (File) objMethod.invoke(bean, new Object[] {req, fileName});
					break;
				case 3:
					file = (File) objMethod.invoke(bean, new Object[] {req, fileName, super.setCommonParam(params)});
					break;
				default:
					if(queryId == null || StringUtils.isEmpty(queryId)) {
						throw new BizException("Empty Query Id");
					}

					file = (File) objMethod.invoke(bean, new Object[] {req, fileName, queryId, super.setCommonParam(params)});
				}
			}

			if(file.exists()) {
				mapRtn.put("fileName", file.getName());

				json.setErrorCode(0);
				json.setErrorMsg("Success");
				json.setRows(mapRtn);
			}
			else {
				json.setErrorCode(-1);
				json.setErrorMsg("Export Fail");
			}
		}
		catch(Exception e) {
			log.error(params, e);

			json.setErrorCode(-1);
			if(e instanceof BizException || e instanceof RCPException)
				json.setErrorMsg(e.getMessage());
			else
				json.setErrorMsg(e.getCause().getMessage());
		}

		return json;
	}


	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/uploadAttach.do", method=RequestMethod.POST)
	public Json<Map> uploadAttach(MultipartHttpServletRequest multi) {
		Json<Map> json = new Json<Map>();
		Object bean = null;
		Method objMethod = null;

		try {
			String service = "attach";
			String method = "upload";

			if(multi.getParameter("service") != null)
				service = multi.getParameter("service");

			if(multi.getParameter("method") != null)
				method = multi.getParameter("method");

			bean = super.getBean(service);
			objMethod = super.getMethod(bean, method);

			HashMap<String, Object> params = new HashMap<String, Object>();
			Enumeration<String> parameterNames = multi.getParameterNames();
			String paramName = null;

			while(parameterNames.hasMoreElements()) {
				paramName = parameterNames.nextElement();
				params.put(paramName, multi.getParameter(paramName));
			}

			json.setRows((Map) objMethod.invoke(bean, new Object[] {multi, super.setCommonParam(params)}));
			json.setErrorCode(0);
			json.setErrorMsg("Success");
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(e);

			json.setErrorCode(-1);
			if(e instanceof BizException || e instanceof RCPException)
				json.setErrorMsg(e.getMessage());
			else
				json.setErrorMsg(e.getCause().getMessage());
        }

		return json;
	}
}
