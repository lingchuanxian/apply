package cn.fjlcx.application.config;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import cn.fjlcx.application.bean.Menu;
import cn.fjlcx.application.bean.RoleMenu;
import cn.fjlcx.application.bean.User;
import cn.fjlcx.application.bean.UserRole;
import cn.fjlcx.application.service.MenuService;
import cn.fjlcx.application.service.RoleMenuService;
import cn.fjlcx.application.service.UserRoleService;
import cn.fjlcx.application.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年1月22日 上午9:12:21
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
public class ShiroRealm extends AuthorizingRealm {

	private static final Logger logger = Logger.getLogger(ShiroRealm.class);
	@Resource
	UserService mUserService;
	@Resource
	UserRoleService mUserRoleService;
	@Resource
	RoleMenuService mRoleMenuService;
	@Resource
	MenuService mMenuService;
	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		//UsernamePasswordToken用于存放提交的登录信息
		UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
		logger.info("登录认证!");
		User user = mUserService.selectUserWithRole(token.getUsername());
		if (user != null){
			logger.info("用户: " + user.getUsLoginname());
			if(user.getUsState() == 1){
				throw new DisabledAccountException();
			}
			// 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
			logger.info("password:"+user.getUsPwd());
			return new SimpleAuthenticationInfo(user.getUsLoginname(),user.getUsPwd(), ByteSource.Util.bytes(user.getUsLoginname()), getName());
		}else{
			throw new AccountException("帐号不存在！");
		}

	}

	/**
	 * 权限认证（为当前登录的Subject授予角色和权限）
	 *
	 * 该方法的调用时机为需授权资源被访问时，并且每次访问需授权资源都会执行该方法中的逻辑，这表明本例中并未启用AuthorizationCache，
	 * 如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），
	 * 超过这个时间间隔再刷新页面，该方法会被执行
	 *
	 * doGetAuthorizationInfo()是权限控制，
	 * 当访问到页面的时候，使用了相应的注解或者shiro标签才会执行此方法否则不会执行，
	 * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) super.getAvailablePrincipal(principals);
		User user = mUserService.selectUserWithRole(username);
		logger.info("权限认证!");
		if (user != null){
			// 权限信息对象info，用来存放查出的用户的所有的角色及权限
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			//用户的角色集合
			List<UserRole> roles = mUserRoleService.selectUserRoleByUserId(user.getUsId());
			//Set<String> roleCodes = new HashSet<String>();
			Set<String> permissions = new HashSet<String>();
			for(UserRole role : roles) {
				//roleCodes.add(role.getUrRole().getRlCode());
				//暂时用角色控制
				List<RoleMenu> rolemenu = mRoleMenuService.selectMenuByRole(role.getUrRole().getRlId());
				for(RoleMenu rm :rolemenu) {
					permissions.add(rm.getMenu().getMuCode());
				}
			}
			//info.setRoles(roleCodes);
			info.setStringPermissions(permissions);
			return info;
		}
		// 返回null将会导致用户访问任何被拦截的请求时都会自动跳转到unauthorizedUrl指定的地址
		return null;
	}
	
	@Override
	protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
	    Cache<Object, AuthenticationInfo> c = getAuthenticationCache();
	    logger.info("清除【认证】缓存之前");
	    for(Object o : c.keys()){
	        logger.info( o + " , " + c.get(o));
	    }
	    super.clearCachedAuthenticationInfo(principals);
	    logger.info("调用父类清除【认证】缓存之后");
	    for(Object o : c.keys()){
	        logger.info( o + " , " + c.get(o));
	    }

	    // 添加下面的代码清空【认证】的缓存
	    SimplePrincipalCollection spc = new SimplePrincipalCollection(principals.getPrimaryPrincipal(),getName());
	    super.clearCachedAuthenticationInfo(spc);
	    logger.info("添加了代码清除【认证】缓存之后");
	    int cacheSize = c.keys().size();
	    logger.info("【认证】缓存的大小:" + c.keys().size());
	    if (cacheSize == 0){
	        logger.info("说明【认证】缓存被清空了。");
	    }
	}

	@Override
	protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
	    logger.info("清除【授权】缓存之前");
	    Cache c = getAuthorizationCache();
	    for(Object o : c.keys()){
	        logger.info( o + " , " + c.get(o));
	    }
	    super.clearCachedAuthorizationInfo(principals);
	    logger.info("清除【授权】缓存之后");
	    int cacheSize = c.keys().size();
	    logger.info("【授权】缓存的大小:" + cacheSize);

	    for(Object o : c.keys()){
	        logger.info( o + " , " + c.get(o));
	    }
	    if(cacheSize == 0){
	        logger.info("说明【授权】缓存被清空了。");
	    }

	}
}