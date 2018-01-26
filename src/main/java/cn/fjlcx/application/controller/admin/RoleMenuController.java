package cn.fjlcx.application.controller.admin;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.fjlcx.application.controller.BaseController;
import cn.fjlcx.application.service.RoleMenuService;

/**
 *  @author ling_cx 
 *  @date   2017/09/28.
 */
@RestController
@RequestMapping("admin/roleMenu")
public class RoleMenuController extends BaseController{
	
    @Resource
    private RoleMenuService roleMenuService;

    
    
   
    
}
