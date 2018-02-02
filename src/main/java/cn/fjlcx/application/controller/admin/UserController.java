package cn.fjlcx.application.controller.admin;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.fjlcx.application.bean.User;
import cn.fjlcx.application.bean.UserRole;
import cn.fjlcx.application.config.Constant;
import cn.fjlcx.application.config.SystemControllerLog;
import cn.fjlcx.application.controller.BaseController;
import cn.fjlcx.application.core.Result;
import cn.fjlcx.application.core.ResultGenerator;
import cn.fjlcx.application.service.UserRoleService;
import cn.fjlcx.application.service.UserService;
import cn.fjlcx.application.utils.ListPageUtil;
import tk.mybatis.mapper.util.StringUtil;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年1月22日 下午3:28:35
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@Controller
@RequestMapping("admin/user")
public class UserController extends BaseController{
	
	@Resource
	UserService mUserService;
	@Resource
	private UserRoleService userRoleService;
	
	@RequiresPermissions("system:user:list")
	@GetMapping("list")
	public String userLists() {
		return "admin/user/list";
	}
	
	/**
	 * 获取用户列表
	 * @param page
	 * @param rows
	 * @param name
	 * @param loginName
	 * @return
	 */
	@RequiresPermissions("system:user:select")
	@GetMapping("GetUserList")
	@ResponseBody
	public Result GetUserList(int page,int rows,String name,String loginName) {
		PageHelper.startPage(page, rows);//设置分页
		Map<String,Object> params1 = new HashMap<String, Object>();
		params1.put("name", name);
		params1.put("loginName", loginName);
		List<User> list = mUserService.selectUserByConditions(params1);
		PageInfo<User> pageData=new PageInfo<User>(list);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("total", pageData.getTotal());
		params.put("rows", pageData.getList());
		return ResultGenerator.genSuccessResult().setData(params);
	}
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	@RequiresPermissions("system:user:insert")
	@PostMapping("AddUser")
	@SystemControllerLog(description = "新增用户信息")
	@ResponseBody
	public Result AddUser(@ModelAttribute User user) {
		logger.info("user-form:"+user.toString());
		SimpleHash sh = new SimpleHash("MD5",user.getUsPwd(), ByteSource.Util.bytes(user.getUsLoginname()),1024);
		user.setUsPwd(sh.toString());
		user.setUsRegistdate(new Date());
		mUserService.save(user);
		return ResultGenerator.genSuccessResult().setMessage("新增成功");
	}
	
	/**
	 * 校验用户帐号是否已经存在
	 * @param name
	 * @return
	 */
	@RequiresPermissions("system:user:select")
	@PostMapping("checkExist")
	@ResponseBody
	public Result checkExist(@RequestParam String name) {
		User user = mUserService.selectUserWithRole(name);
		if(user != null) {
			return ResultGenerator.genSuccessResult(1);
		}else {
			return ResultGenerator.genSuccessResult(0);
		}
	}

	/**
	 * 删除指定用户(假删除)
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:user:delete")
	@PostMapping("DeleteUserById")
	@SystemControllerLog(description = "根据id删除置指定用户")
	@ResponseBody
	public Result DeleteUserById(@RequestParam int id) {
		mUserService.falseDeletion(id);
		return ResultGenerator.genSuccessResult().setMessage("删除成功");
	}
	
	/**
	 * 更新用户的状态
	 * @param id
	 * @param state
	 * @return
	 */
	@RequiresPermissions("system:user:updatestate")
	@PostMapping("UpdateUserState")
	@SystemControllerLog(description = "更新用户状态")
	@ResponseBody
	public Result UpdateUserState(@RequestParam int id,@RequestParam int state) {
		User user = new User();
		user.setUsId(id);
		user.setUsState(state);
		mUserService.update(user);
		return ResultGenerator.genSuccessResult().setMessage("修改成功");
	}
	
	/**
	 * 获取指定用户信息
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:user:select")
	@PostMapping("SelectUserById/{id}")
	@ResponseBody
	public Result SelectUserById(@PathVariable int id) {
		User user = mUserService.selectUserById(id);
		return ResultGenerator.genSuccessResult(user);
	}
	
	/**
	 * 获取没有当前角色的用户列表
	 * @param page
	 * @param rows
	 * @param rid
	 * @return
	 */
	@RequiresPermissions("system:user:select")
	@GetMapping("GetUserListExpectRoleExist")
	@ResponseBody
	public Result GetUserListExpectRoleExist(int page,int rows,int rid) {
		logger.info("page:"+page);
		List<UserRole> listUserRole = userRoleService.selectUserRoleByRoleId(rid);
		List<User> list = mUserService.selectUserByConditions(null);
		List<User> templist = new ArrayList<User>();
		for(UserRole ur:listUserRole) {
			for(User user : list) {
				if(user.getUsId() == ur.getUrUser().getUsId()) {
					templist.add(user);
				}
			}
		}
		list.removeAll(templist);
		ListPageUtil<User> listPageUtil =new ListPageUtil<User>(list,page, rows);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("total", listPageUtil.getTotalCount());
		params.put("rows", listPageUtil.getPagedList());
		return ResultGenerator.genSuccessResult().setData(params);
	}
	
	/**
	 * 重置用户密码为123456
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:user:resetpwd")
	@PostMapping("ResetUserPassword")
	@SystemControllerLog(description = "重置指定用户的密码")
	@ResponseBody
	public Result ResetUserPassword(@RequestParam int id) {
		User user = new User();
		user.setUsId(id);
		User initUser = mUserService.selectUserById(id);
		SimpleHash sh = new SimpleHash(Constant.ENCRYPTION_TYPE,Constant.RESET_PWD, ByteSource.Util.bytes(initUser.getUsLoginname()),1024);
		user.setUsPwd(sh.toString());
		mUserService.update(user);
		return ResultGenerator.genSuccessResult().setMessage("密码重置成功！新密码为123456");
	}
	
	/**
	 * 修改密码
	 * @param id
	 * @return
	 */
	@RequiresAuthentication
	@PostMapping("ModifyUserPassword")
	@SystemControllerLog(description = "修改密码信息")
	@ResponseBody
	public Result ModifyUserPassword(@RequestParam String password,@RequestParam String newpassword,@RequestParam String repassword) {
		User currentUser = GetLoginSesseion();
		if(StringUtil.isNotEmpty(password)&&StringUtil.isNotEmpty(repassword)&&StringUtil.isNotEmpty(newpassword)) {
			SimpleHash sh1 = new SimpleHash(Constant.ENCRYPTION_TYPE,password, ByteSource.Util.bytes(currentUser.getUsLoginname()),1024);
			if(currentUser.getUsPwd().equals(sh1.toString())) {
				if(newpassword.equals(repassword)) {
					SimpleHash sh2 = new SimpleHash(Constant.ENCRYPTION_TYPE,newpassword, ByteSource.Util.bytes(currentUser.getUsLoginname()),1024);
					currentUser.setUsPwd(sh2.toString());
					mUserService.update(currentUser);
					return ResultGenerator.genSuccessResult().setMessage("重置成功");
				}else {
					return ResultGenerator.genFailResult("密码修改失败，密码不一致。");
				}
			}else {
				return ResultGenerator.genFailResult("密码修改失败，原密码错误。");
			}
		}else {
			return ResultGenerator.genFailResult("密码修改失败，密码不能为空。");
		}
		
	}


}
