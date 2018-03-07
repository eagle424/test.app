package com.ba.test.app.controller;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ba.test.app.exception.RCPException;
import com.ba.test.app.vo.JsonSaveVo;
import com.ba.test.app.vo.RcpSessionVo;

public class AbstractController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private HttpSession httpSession;

	private Map<String, Object> userInfo;

	protected Object getBean(String sServiceName) throws Exception{
		return WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(sServiceName);
	}

	protected Method getMethod(Object bean, String methodName) throws Exception{
		Method[] methods = bean.getClass().getMethods();

		for(int i = 0 ; i < methods.length ; i++) {
			if(methods[i].getName().equals(methodName)) {
				return methods[i];
			}
		}
		throw new RCPException("Cann't find " + methodName + ".");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Map<String, Object> setCommonParam(Map param) {

//		RcpSessionVo vo = (RcpSessionVo) httpSession.getAttribute("_sessionData_");
//		userInfo = vo.getUserInfo();

//		param.put("cpUsrLngDv", (String) userInfo.get("usrLngDv"));
//		param.put("cpIp", (String) userInfo.get("cpIp"));
//		param.put("cpUid", (String) userInfo.get("usrId"));
//		param.put("cpnId", (String) userInfo.get("cpnId"));
		param.put("cpUsrLngDv", "CN");
//		param.put("cpIp", "");
		param.put("cpUid", "");
		param.put("cpnId", "CPN0000001");

		return param;
	}

	protected JsonSaveVo setCommonParam(JsonSaveVo param) {
		RcpSessionVo vo = (RcpSessionVo) httpSession.getAttribute("_sessionData_");
		userInfo = vo.getUserInfo();

		param.setCpUsrLngDv((String) userInfo.get("usrLngDv"));
		param.setCpIp((String) userInfo.get("cpIp"));
		param.setCpUid((String) userInfo.get("usrId"));
		param.setCpnId((String) userInfo.get("cpnId"));

		return param;
	}
}
