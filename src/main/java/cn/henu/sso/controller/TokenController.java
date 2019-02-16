package cn.henu.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.henu.common.utils.EtResult;
import cn.henu.common.utils.JsonUtils;
import cn.henu.sso.service.TokenService;

/**
 * 处理用户信息
 * @author syw
 *
 */
@Controller
@CrossOrigin
public class TokenController {

	@Autowired
	private TokenService tokenService;
	
	//方法一：spring在4.1以下的跨域
	//这里指定响应的数据为application/json格式APPLICATION_JSON_UTF8_VALUE
	/*
	@RequestMapping(value="/user/token/{token}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE ) 
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback) {
		EtResult result = tokenService.getUserBytoken(token);
		//响应结果之前判断是否为jsonp请求
		if(StringUtils.isNoneBlank(callback)) {
			//说明是jsonp请求，应该把结果封装成js语句响应
			return callback+"("+JsonUtils.objectToJson(result)+");";
		}
		return JsonUtils.objectToJson(result);
	}
	*/
	@RequestMapping(value="/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback) {
		EtResult result = tokenService.getUserBytoken(token);
		//响应结果之前判断是否为jsonp请求
		if(StringUtils.isNoneBlank(callback)) {
			//说明是jsonp请求，应该把结果封装成js语句响应
			MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
			jacksonValue.setJsonpFunction(callback);
			return jacksonValue;
		}
		return result;
	}
	
	
}
