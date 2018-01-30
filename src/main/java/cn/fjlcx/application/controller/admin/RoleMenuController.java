package cn.fjlcx.application.controller.admin;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.fjlcx.application.controller.BaseController;
import cn.fjlcx.application.service.RoleMenuService;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年1月30日 上午10:54:32
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@RestController
@RequestMapping("admin/roleMenu")
public class RoleMenuController extends BaseController{
	
    @Resource
    private RoleMenuService roleMenuService;

    
    
   
    
}
