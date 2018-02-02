package ${basePackage}.controller;

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

import cn.fjlcx.application.bean.${modelNameUpperCamel};
import cn.fjlcx.application.config.SystemControllerLog;
import cn.fjlcx.application.core.Result;
import cn.fjlcx.application.core.ResultGenerator;
import cn.fjlcx.application.service.${modelNameUpperCamel}Service;

/**
 * @author ${author} 
 * @version 1.0
 * @Description 
 * @date ${date}.
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@Controller
@RequestMapping("admin/${modelNameLowerCamel}")
public class ${modelNameUpperCamel}Controller {
	private static final Logger logger = LoggerFactory.getLogger(${modelNameUpperCamel}Controller.class);
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;
   
    @RequiresPermissions("system:${modelNameLowerCamel}:list")
	@GetMapping("list")
	public String List() {
		return "admin/${modelNameLowerCamel}/list";
	}
    
    /**
     * 获取${modelNameUpperCamel}列表
     * @param page
     * @param rows
     * @param stype
     * @param skey
     * @return
     */
    @RequiresPermissions("system:${modelNameLowerCamel}:select")
	@PostMapping("select")
	@ResponseBody
	public Result select(int page,int rows,String stype,String skey) {
    	logger.debug("stype:"+stype+"---skey:"+skey);
    	PageHelper.startPage(page, rows);//设置分页
    	Map<String,Object> params1 = new HashMap<String, Object>();
		params1.put("stype", stype);
		params1.put("skey", skey);
		List<${modelNameUpperCamel}> artList = ${modelNameLowerCamel}Service.select${modelNameUpperCamel}ByCondition(params1);
		PageInfo<${modelNameUpperCamel}> pageData=new PageInfo<${modelNameUpperCamel}>(artList);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("total", pageData.getTotal());
		params.put("rows",pageData.getList());
		return ResultGenerator.genSuccessResult(params);
	}
    
    /**
     * 新增${modelNameUpperCamel}
     * @param dict
     * @return
     */
    @RequiresPermissions("system:${modelNameLowerCamel}:insert")
	@PostMapping("insert")
	@SystemControllerLog(description = "新增${modelNameUpperCamel}")   
	@ResponseBody
	public Result insert(@ModelAttribute ${modelNameUpperCamel} model) {
    	//dict.setDictOrder(dictionaryService.selectMaxOrder()+1);
    	${modelNameLowerCamel}Service.save(model);
		return ResultGenerator.genSuccessResult().setMessage("新增成功");
	}
    
    /**
     * 查询${modelNameUpperCamel}
     * @param id
     * @return
     */
    @RequiresPermissions("system:${modelNameLowerCamel}:select")
	@PostMapping("select/{id}")
	@ResponseBody
	public Result selectById(@PathVariable int id) {
    	${modelNameUpperCamel} model = ${modelNameLowerCamel}Service.findById(id);
		return ResultGenerator.genSuccessResult(dt);
	}
    
    /**
     * 更新${modelNameUpperCamel}
     * @param type
     * @return
     */
    @RequiresPermissions("system:${modelNameLowerCamel}:update")
	@PostMapping("update")
	@SystemControllerLog(description = "更新${modelNameLowerCamel}")   
	@ResponseBody
	public Result update(@ModelAttribute ${modelNameUpperCamel} model) {
    	${modelNameLowerCamel}Service.update(model);
		return ResultGenerator.genSuccessResult().setMessage("更新成功");
	}
    
    /**
     * 删除${modelNameUpperCamel}
     * @param type
     * @return
     */
    @RequiresPermissions("system:${modelNameLowerCamel}:delete")
	@PostMapping("delete/{id}")
	@SystemControllerLog(description = "删除${modelNameLowerCamel}")   
	@ResponseBody
	public Result delete(@PathVariable int id) {
    	${modelNameLowerCamel}Service.deleteById(id);
		return ResultGenerator.genSuccessResult().setMessage("删除成功");
	}

}

