package cn.fjlcx.application.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.fjlcx.application.bean.UserRole;
import cn.fjlcx.application.config.SystemControllerLog;
import cn.fjlcx.application.controller.BaseController;
import cn.fjlcx.application.core.Result;
import cn.fjlcx.application.core.ResultGenerator;
import cn.fjlcx.application.service.UserRoleService;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年1月30日 上午10:54:17
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@Controller
@RequestMapping("admin/userRole")
public class UserRoleController  extends BaseController{
	
    @Resource
    private UserRoleService userRoleService;
    
    /**
     * 根据角色ID查询 用户-角色 关系
     * @param id
     * @param page
     * @param rows
     * @return
     */
    @RequiresPermissions("system:userRole:select")
    @GetMapping("SelectUserByRid")
    @ResponseBody
    public Result SelectUserByRid(@RequestParam int id,int page,int rows) {
		PageHelper.startPage(page, rows);//设置分页
		List<UserRole> listUserRole = userRoleService.selectUserRoleByRoleId(id);
		PageInfo<UserRole> pageData=new PageInfo<UserRole>(listUserRole);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("total", pageData.getTotal());
		params.put("rows", pageData.getList());
		return ResultGenerator.genSuccessResult().setData(params);
	}
    
    /**
     * 新增用户-角色（给用户赋予新的角色）
     * @param rid
     * @param uids
     * @return
     */
    @RequiresPermissions("system:userRole:insert")
    @PostMapping("AddUserToRole")
    @ResponseBody
    @SystemControllerLog(description = "给用户赋予新的角色")
    public Result AddUserToRole(@RequestParam int rid,@RequestParam String uids) {
    	logger.info("rid:"+rid+"--uids:"+uids);
    	String id[] = uids.split(",");
    	for(int i = 0;i < id.length; i++) {
    		UserRole ur = new UserRole();
    		ur.setUrUid(Integer.parseInt(id[i]));
    		ur.setUrRid(rid);
    		userRoleService.save(ur);
    	}
    	return ResultGenerator.genSuccessResult().setMessage("新增成功");
    }

    /**
     * 删除用户-角色（移除用户的某个角色）
     * @param ids
     * @return
     */
    @RequiresPermissions("system:userRole:delete")
    @PostMapping("RemoveUserOfRole")
    @ResponseBody
    @SystemControllerLog(description = "移除用户的指定角色")
    public Result RemoveUserOfRole(@RequestParam String ids) {
    	logger.info("ids:"+ids);
    	String id[] = ids.split(",");
    	for(int i = 0;i < id.length; i++) {
    		userRoleService.falseDeletion(Integer.parseInt(id[i]));
    	}
    	return ResultGenerator.genSuccessResult().setMessage("移除成功");
    }
    
    /**
     * 查询指定用户的角色
     * @param id
     * @return
     */
    @RequiresPermissions("system:userRole:selectbyuser")
    @PostMapping("SelectUserRoleByUid")
    @ResponseBody
    public Result SelectUserRoleByUid(@RequestParam int id) {
    	List<UserRole> listRole = userRoleService.selectUserRoleByUserId(id);
    	return ResultGenerator.genSuccessResult(listRole);
    }
}
