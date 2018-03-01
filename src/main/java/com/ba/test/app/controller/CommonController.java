package com.ba.test.app.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ba.test.app.service.CommonService;

@Controller
public class CommonController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name="CommonService")
	private CommonService commonService;

	@RequestMapping(value="/get.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public void get(HttpServletRequest req, String aaa) throws Exception{
		String queryId = "msUsr.get";
		try {
//			commonService.selectOne(queryId, vo);
			System.out.println("111");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@RequestMapping(value="get2.do")
	public void get2(String aaa) throws Exception{
		System.out.println("111");
	}

}
