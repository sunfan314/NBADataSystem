package net.nba.controller;

import java.util.List;

import javax.annotation.Resource;

import net.nba.service.TestService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	@Resource
	private TestService testService;
	

	@RequestMapping("/test")
	public @ResponseBody List<Object> getTestData(){
		//测试接口
		return testService.getTestData();
	}

}
