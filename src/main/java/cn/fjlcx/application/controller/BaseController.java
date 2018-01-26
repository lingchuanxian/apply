package cn.fjlcx.application.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSONArray;

import cn.fjlcx.application.bean.Menu;
import cn.fjlcx.application.bean.TreeJson;
import cn.fjlcx.application.bean.User;
import cn.fjlcx.application.config.Constant;
import cn.fjlcx.application.core.ResultGenerator;

public class BaseController {
	public static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	public User GetLoginSesseion() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Constant.LOGIN_USER);	
		return user;
	}
	
	public void MenuToTreeJson(List<TreeJson> tjs, List<Menu> menuList) {
		for (Menu menu : menuList) {  
			TreeJson tj=new TreeJson();  
			tj.setId(menu.getMuId());  
			tj.setPid(menu.getMuPid());  
			tj.setText(menu.getMuText());  
			tj.setIconCls(menu.getMuIconcls());  
			tj.setState(menu.getMuState());
			tj.setUrl(menu.getMuUrl());
			tj.setChecked(menu.getMuChecked());
			tjs.add(tj);  
		}
	}
	
	/**
	 * 统一异常处理
	 * @param request
	 * @param response
	 * @param exception
	 */
	@ExceptionHandler
	public String exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		logger.error("统一异常处理：", exception);
		request.setAttribute("ex", exception);
		response.setCharacterEncoding("utf-8");
		if (null != request.getHeader("X-Requested-With") && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String result = "";
			
			// shiro没有权限异常
			if (exception instanceof UnauthorizedException) {
				result = JSONArray.toJSONString(ResultGenerator.genNoAuthorityResult()).toString();
			}
			// shiro会话已过期异常
			if (exception instanceof InvalidSessionException) {
				result = JSONArray.toJSONString(ResultGenerator.genUnauthorizedResult()).toString();
			}
			out.write(result);
			out.flush();
			out.close();
			return null;
		}
		// shiro没有权限异常
		if (exception instanceof UnauthorizedException) {
			return "/refuse";
		}
		// shiro会话已过期异常
		if (exception instanceof InvalidSessionException) {
			return "/login";
		}
		return "/404";
	}
}
