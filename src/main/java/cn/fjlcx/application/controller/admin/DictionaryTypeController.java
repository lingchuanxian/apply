package cn.fjlcx.application.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import cn.fjlcx.application.bean.Article;
import cn.fjlcx.application.bean.DictionaryType;
import cn.fjlcx.application.config.SystemControllerLog;
import cn.fjlcx.application.controller.BaseController;
import cn.fjlcx.application.core.Result;
import cn.fjlcx.application.core.ResultGenerator;
import cn.fjlcx.application.service.DictionaryTypeService;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年2月1日 上午9:23:49
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@Controller
@RequestMapping("admin/dictionaryType")
public class DictionaryTypeController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DictionaryTypeController.class);
    @Resource
    private DictionaryTypeService dictionaryTypeService;
    
    @RequiresPermissions("system:dictionary:type:list")
	@GetMapping("list")
	public String List() {
		return "admin/dictionary/type/list";
	}
    
    /**
     * 获取字典类别列表
     * @param page
     * @param rows
     * @param stype
     * @param skey
     * @return
     */
    @RequiresPermissions("system:dictionary:type:select")
	@PostMapping("select")
	@ResponseBody
	public Result select(int page,int rows,String stype,String skey) {
    	logger.debug("stype:"+stype+"---skey:"+skey);
    	PageHelper.startPage(page, rows);//设置分页
    	Map<String,Object> params1 = new HashMap<String, Object>();
		params1.put("stype", stype);
		params1.put("skey", skey);
		List<DictionaryType> artList = dictionaryTypeService.selectDictionaryTypeByCondition(params1);
		PageInfo<DictionaryType> pageData=new PageInfo<DictionaryType>(artList);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("total", pageData.getTotal());
		params.put("rows",pageData.getList());
		return ResultGenerator.genSuccessResult(params);
	}
    
    @RequiresPermissions("system:dictionary:type:select")
   	@PostMapping("selectAll")
   	@ResponseBody
   	public Result selectAll() {
   		List<DictionaryType> list = dictionaryTypeService.findAll();
   		return ResultGenerator.genSuccessResult(list);
   	}
    
    @RequiresPermissions("system:dictionary:type:select")
   	@PostMapping("checkExist")
   	@ResponseBody
   	public Result checkExist(@RequestParam String code) {
   		DictionaryType dt = dictionaryTypeService.selectByCode(code);
   		if(dt != null) {
			return ResultGenerator.genSuccessResult(1);
		}else {
			return ResultGenerator.genSuccessResult(0);
		}
   	}
    /**
     * 新增字典类别
     * @param type
     * @return
     */
    @RequiresPermissions("system:dictionary:type:insert")
	@PostMapping("insert")
	@SystemControllerLog(description = "新增字典分类")   
	@ResponseBody
	public Result insert(@ModelAttribute DictionaryType type) {
    	dictionaryTypeService.save(type);
		return ResultGenerator.genSuccessResult().setMessage("新增成功");
	}
    
    /**
     * 查询字典类别
     * @param id
     * @return
     */
    @RequiresPermissions("system:dictionary:type:select")
	@PostMapping("select/{id}")
	@ResponseBody
	public Result selectById(@PathVariable int id) {
    	DictionaryType dt = dictionaryTypeService.findById(id);
		return ResultGenerator.genSuccessResult(dt);
	}
    
    /**
     * 更新字典类别
     * @param type
     * @return
     */
    @RequiresPermissions("system:dictionary:type:update")
	@PostMapping("update")
	@SystemControllerLog(description = "更新字典分类")   
	@ResponseBody
	public Result update(@ModelAttribute DictionaryType type) {
    	dictionaryTypeService.update(type);
		return ResultGenerator.genSuccessResult().setMessage("更新成功");
	}
    
    /**
     * 删除字典类别
     * @param type
     * @return
     */
    @RequiresPermissions("system:dictionary:type:delete")
	@PostMapping("delete/{id}")
	@SystemControllerLog(description = "删除字典分类")   
	@ResponseBody
	public Result delete(@PathVariable int id) {
    	dictionaryTypeService.deleteById(id);
		return ResultGenerator.genSuccessResult().setMessage("删除成功");
	}
    
}
