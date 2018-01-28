package cn.fjlcx.application.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fjlcx.application.bean.ArticleType;
import cn.fjlcx.application.config.SystemControllerLog;
import cn.fjlcx.application.core.Result;
import cn.fjlcx.application.core.ResultGenerator;
import cn.fjlcx.application.service.ArticleTypeService;


/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年1月22日 下午3:10:10
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@Controller
@RequestMapping("admin/articleType")
public class ArticleTypeController {

	@Resource
	private ArticleTypeService articleTypeService;

	@RequiresPermissions("system:article:type:list")
	@GetMapping("list")
	public String articletypeList() {
		return "admin/article/type/list";
	}

	/**
	 * 查询文章类别
	 * @return
	 */
	@RequiresPermissions("system:article:type:select")
	@GetMapping("select")
	@ResponseBody
	public Result select() {
		List<ArticleType> artList = articleTypeService.findAll();
		return ResultGenerator.genSuccessResult(artList);
	}

	/**
	 * 新增文章分类
	 * @param type
	 * @return
	 */
	@RequiresPermissions("system:article:type:insert")
	@PostMapping("insert")
	@SystemControllerLog(description = "新增文章分类")   
	@ResponseBody
	public Result insert(@ModelAttribute ArticleType type) {
		articleTypeService.save(type);
		return ResultGenerator.genSuccessResult().setMessage("新增成功");
	}

	/**
	 * 更新文章分类
	 * @param type
	 * @return
	 */
	@RequiresPermissions("system:article:type:update")
	@PostMapping("update")
	@SystemControllerLog(description = "更新文章分类")   
	@ResponseBody
	public Result update(@ModelAttribute ArticleType type) {
		articleTypeService.update(type);
		return ResultGenerator.genSuccessResult().setMessage("更新成功");
	}

	/**
	 * 根据id删除指定文章分类
	 * @param id
	 * @return
	 */
	@RequiresPermissions("system:article:type:delete")
	@PostMapping("delete")
	@SystemControllerLog(description = "根据id删除指定文章分类")  
	@ResponseBody
	public Result delete(@RequestParam int id) {
		articleTypeService.deleteById(id);
		return ResultGenerator.genSuccessResult().setMessage("删除成功");
	}

}
