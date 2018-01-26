package cn.fjlcx.application.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fjlcx.application.bean.Menu;
import cn.fjlcx.application.bean.Role;
import cn.fjlcx.application.bean.RoleMenu;
import cn.fjlcx.application.bean.TreeJson;
import cn.fjlcx.application.bean.User;
import cn.fjlcx.application.bean.UserRole;
import cn.fjlcx.application.config.Constant;
import cn.fjlcx.application.core.Result;
import cn.fjlcx.application.core.ResultGenerator;
import cn.fjlcx.application.generator.CodeGenerator;
import cn.fjlcx.application.service.MenuService;
import cn.fjlcx.application.service.RoleMenuService;
import cn.fjlcx.application.service.RoleService;
import cn.fjlcx.application.service.UserRoleService;
import cn.fjlcx.application.service.UserService;
import cn.fjlcx.application.utils.JwtUtil;
import cn.fjlcx.application.utils.SortListUtil;
import io.jsonwebtoken.Claims;
import tk.mybatis.mapper.util.StringUtil;

/**
 * @author ling_cx
 * @date 2017/09/26.
 */
@Controller
public class CommonController extends BaseController{

	@Resource
	UserService mUserService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private RoleService roleService;
	@Resource
	private MenuService menuService;
	@Resource
	private RoleMenuService roleMenuService;


	@GetMapping("/")
	public String tlogin() {
		return "redirect:login";
	}

	@GetMapping("login")
	public String login() {
		return "login";
	}

	@GetMapping("404")
	public String errotPage() {
		return "404";
	}

	@GetMapping("refuse")
	public String refuse() {
		return "refuse";
	}

	@GetMapping("admin/index")
	public String index() {
		return "admin/index";
	}

	@GetMapping("admin/rolemenu/list")
	public String rolemenuList() {
		return "admin/rolemenu/list";
	}

	@GetMapping("admin/gencode/list")
	public String gencodeList() {
		return "admin/gencode/list";
	}

	/**
	 * 登录操作
	 * @param username
	 * @param password
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("loginAction")
	@ResponseBody
	public Result loginAction(@RequestParam String username, @RequestParam String password){
		logger.info("登陆操作");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject currentUser = SecurityUtils.getSubject();
		try {
			logger.info("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.login(token);
			logger.info("对用户[" + username + "]进行登录验证..验证通过");
			if(currentUser.isAuthenticated()) {
				logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
				Session session = currentUser.getSession();
				User user = mUserService.selectUserWithRole(username);
				List<Role> roles = new ArrayList<>();
				List<UserRole> roleList = userRoleService.selectUserRoleByUserId(user.getUsId());
				for(UserRole urole:roleList) {
					Role role = urole.getUrRole();
					roles.add(role);
				}
				user.setRoles((List<Role>) SortListUtil.sort(roles,"rlOrder","asc"));
				session.setAttribute(Constant.LOGIN_USER,user);
				return ResultGenerator.genSuccessResult().setMessage("登录成功");
			}else {
				token.clear();
				return ResultGenerator.genSuccessResult().setMessage("登录失败");
			}
		}catch(UnknownAccountException uae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
			return ResultGenerator.genFailResult("未知账户");
		}catch(IncorrectCredentialsException ice){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
			return ResultGenerator.genFailResult("密码不正确");
		}catch(LockedAccountException lae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
			return ResultGenerator.genFailResult("账户已锁定");
		}catch(ExcessiveAttemptsException eae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数大于5次,账户已锁定");
			return ResultGenerator.genFailResult("用户名或密码错误次数大于5次,账户已锁定");
		}catch (DisabledAccountException sae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,帐号已经禁止登录");
			return ResultGenerator.genFailResult("帐号已被禁用，请联系管理员。");
		}catch(AuthenticationException ae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			return ResultGenerator.genFailResult("用户名或密码不正确");
		}  
	}

	/**
	 * 用户登录后---主页面获取菜单一级菜单
	 * @param id
	 * @return
	 */
	@GetMapping("admin/GetMenuParent")
	@RequiresAuthentication
	@ResponseBody
	public Result GetMenuParent(@RequestParam int id) {
		User user = GetLoginSesseion();

		List<Role> roles = user.getRoles();
		List<Menu> menuList = new ArrayList<>();
		for(Role role : roles) {
			List<RoleMenu> rolemenu = roleMenuService.selectMenuByRole(role.getRlId());
			for(RoleMenu rm :rolemenu) {
				Menu menu = rm.getMenu();
				if(menu.getMuPid() == id) {
					if(!menuList.contains(menu)){
						menuList.add(menu);
					}
				}
			}
		}
		@SuppressWarnings("unchecked")
		List<Menu> menuListOrder = (List<Menu>)SortListUtil.sort(menuList,"muOrder",SortListUtil.ASC);
		return ResultGenerator.genSuccessResult(menuListOrder);
	}

	/**
	 * 用户登录后---根据id获取子菜单
	 * @param id
	 * @return
	 */
	@GetMapping("admin/GetMenuChildren")
	@ResponseBody
	public Result GetMenuChildren(@RequestParam int id) {
		List<TreeJson> tjs=new ArrayList<TreeJson>();  
		User user = GetLoginSesseion();
		List<Role> roles = user.getRoles();
		List<Menu> menuList = new ArrayList<>();
		for(Role role : roles) {
			List<RoleMenu> rolemenu = roleMenuService.selectMenuByRole(role.getRlId());
			for(RoleMenu rm :rolemenu) {
				Menu menu = rm.getMenu();
				if(menu.getMuType() == 0) {
					if(!menuList.contains(menu)){
						menuList.add(menu);
					}
				}
			}
		}
		@SuppressWarnings("unchecked")
		List<Menu> menuListOrder = (List<Menu>)SortListUtil.sort(menuList,"muOrder",SortListUtil.ASC);
		MenuToTreeJson(tjs, menuListOrder);  
		TreeJson root = new TreeJson(); 
		List<TreeJson> treelist = new ArrayList<TreeJson>();//拼凑好的json格式的数据       
		if (tjs != null && tjs.size() > 0) {  
			for(int i= 0; i < tjs.size(); i++){  
				//如果该节点没有父节点那么它就是根（root）节点  
				if(tjs.get(i).getPid() == id){  
					root = tjs.get(i) ;  
					//获取该根节点的子节点  
					TreeJson.getChildrenNodes(tjs,root);  
					treelist.add(root) ;  
				}  
			}  
		}        
		return ResultGenerator.genSuccessResult(treelist);
	}

	/**
	 * 注销登录，通过配置文件交给shiro处理
	 * @return
	 */
	@GetMapping("logout")
	@ResponseBody
	public Result logout() {
		return ResultGenerator.genSuccessResult("退出成功");
	}
	
	/*****************************************JSON WEN TOKEN TEST*******************************************/
	@GetMapping("JWTTest")
	public Result JWTTest() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Constant.LOGIN_USER);
		try {
			String token = JwtUtil.createJWT(Constant.JWT_ID, JwtUtil.generalSubject(user), Constant.JWT_TTL);
			System.out.println("Token:"+token);
			return ResultGenerator.genFailResult(token);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("JWTParse")
	public Result JWTParse(String token) {
		Claims claims;
		try {
			claims = JwtUtil.parseJWT(token);
			System.out.println("Subject:"+claims.getSubject());
			System.out.println("Id:"+claims.getId());
			System.out.println("Expiration:"+claims.getExpiration());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResultGenerator.genFailResult("123");
	}

	@PostMapping("GenCode")
	public Result GenCode(@RequestParam String tablename,@RequestParam String classname) {
		if(StringUtil.isEmpty(classname)) {
			CodeGenerator.genCode(tablename);
		}else {
			CodeGenerator.genCode(tablename,classname);
		}
		return ResultGenerator.genSuccessResult().setMessage("生成成功");
	}
}
