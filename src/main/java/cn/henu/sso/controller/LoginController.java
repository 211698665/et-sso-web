package cn.henu.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.henu.common.utils.CookieUtils;
import cn.henu.common.utils.EtResult;
import cn.henu.sso.service.LoginService;

/**
 *用户登录处理 
 * @author syw
 *
 */
@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	@RequestMapping("/page/login")
	public String showLogin(String redirect,Model model) {
		model.addAttribute("redirect", redirect);
		return "login";
	}
	
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody        //注意这里的名字要和表单中的name属性的名字对应才能引入进来,如果不对应则需要使用@RequestParam
	public EtResult login(String username,@RequestParam(value="password")String password,
			HttpServletRequest request,HttpServletResponse response) {
		EtResult result = loginService.userLogin(username, password);
		//判断是否登录成功
		if(result.getStatus()==200) {
			String token = result.getData().toString();
			//把token写入cookie
			CookieUtils.setCookie(request,response,TOKEN_KEY,token);
		}
		
		//返回结果
		return result;
	}
}
