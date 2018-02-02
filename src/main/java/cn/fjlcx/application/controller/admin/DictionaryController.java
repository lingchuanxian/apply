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
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.fjlcx.application.bean.Dictionary;
import cn.fjlcx.application.config.SystemControllerLog;
import cn.fjlcx.application.core.Result;
import cn.fjlcx.application.core.ResultGenerator;
import cn.fjlcx.application.service.DictionaryService;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年2月1日 下午3:20:57
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@Controller
@RequestMapping("admin/dictionary")
public class DictionaryController {
	private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
    @Resource
    private DictionaryService dictionaryService;
   
    @RequiresPermissions("system:dictionary:list")
	@GetMapping("list")
	public String List() {
		return "admin/dictionary/list";
	}
    
    /**
     * 获取字典列表
     * @param page
     * @param rows
     * @param stype
     * @param skey
     * @return
     */
    @RequiresPermissions("system:dictionary:select")
	@PostMapping("select")
	@ResponseBody
	public Result select(int page,int rows,String stype,String skey) {
    	logger.debug("stype:"+stype+"---skey:"+skey);
    	PageHelper.startPage(page, rows);//设置分页
    	Map<String,Object> params1 = new HashMap<String, Object>();
		params1.put("stype", stype);
		params1.put("skey", skey);
		List<Dictionary> artList = dictionaryService.selectDictionaryByCondition(params1);
		PageInfo<Dictionary> pageData=new PageInfo<Dictionary>(artList);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("total", pageData.getTotal());
		params.put("rows",pageData.getList());
		return ResultGenerator.genSuccessResult(params);
	}
    
    /**
     * 新增字典
     * @param dict
     * @return
     */
    @RequiresPermissions("system:dictionary:insert")
	@PostMapping("insert")
	@SystemControllerLog(description = "新增字典")   
	@ResponseBody
	public Result insert(@ModelAttribute Dictionary dict) {
    	dict.setDictOrder(dictionaryService.selectMaxOrder()+1);
    	dictionaryService.save(dict);
		return ResultGenerator.genSuccessResult().setMessage("新增成功");
	}
    
    /**
     * 查询字典类别
     * @param id
     * @return
     */
    @RequiresPermissions("system:dictionary:select")
	@PostMapping("select/{id}")
	@ResponseBody
	public Result selectById(@PathVariable int id) {
    	Dictionary dt = dictionaryService.findById(id);
		return ResultGenerator.genSuccessResult(dt);
	}
    
    /**
     * 更新字典类别
     * @param type
     * @return
     */
    @RequiresPermissions("system:dictionary:update")
	@PostMapping("update")
	@SystemControllerLog(description = "更新字典")   
	@ResponseBody
	public Result update(@ModelAttribute Dictionary dict) {
    	dictionaryService.update(dict);
		return ResultGenerator.genSuccessResult().setMessage("更新成功");
	}
    
    /**
     * 删除字典类别
     * @param type
     * @return
     */
    @RequiresPermissions("system:dictionary:delete")
	@PostMapping("delete/{id}")
	@SystemControllerLog(description = "删除字典")   
	@ResponseBody
	public Result delete(@PathVariable int id) {
    	dictionaryService.deleteById(id);
		return ResultGenerator.genSuccessResult().setMessage("删除成功");
	}

}
