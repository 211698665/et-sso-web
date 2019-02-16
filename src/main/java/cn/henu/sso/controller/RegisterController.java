package cn.henu.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.henu.common.utils.EtResult;
import cn.henu.pojo.TbUser;
import cn.henu.sso.service.RegisterService;

/**
 * 注册功能
 * @author syw
 *
 */
@Controller
public class RegisterController {
	
	@Autowired
	private RegisterService registerService;

	@RequestMapping("/page/register")
	public String showRegister() {
		
		return "register";
	}
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public EtResult checkData(@PathVariable String param,@PathVariable Integer type) {
		EtResult result = registerService.checkData(param, type);
		return result;
	}
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public EtResult register(TbUser user) {
		EtResult register = registerService.register(user);
		return register;
	}
}
