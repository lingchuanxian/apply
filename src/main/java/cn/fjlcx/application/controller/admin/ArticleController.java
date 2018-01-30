package cn.fjlcx.application.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import cn.fjlcx.application.config.SystemControllerLog;
import cn.fjlcx.application.controller.BaseController;
import cn.fjlcx.application.core.Result;
import cn.fjlcx.application.core.ResultGenerator;
import cn.fjlcx.application.service.ArticleService;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年1月22日 下午2:18:38
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@Controller
@RequestMapping("admin/article")
public class ArticleController extends BaseController{
	
    @Resource
    private ArticleService articleService;
    
    @RequiresPermissions("system:article:list")
    @GetMapping("list")
	public String articleList() {
		return "admin/article/list";
	}
    
    /**
     * 获取文章列表
     * @param page 当前页码
     * @param rows 单页显示条数
     * @param stype 搜索类型
     * @param skey 搜索关键词
     * @return
     */
    @RequiresPermissions("system:article:select")
	@GetMapping("select")
	@ResponseBody
    public Result select(int page,int rows,String stype,String skey) {
    	logger.debug("stype:"+stype+"---skey:"+skey);
    	PageHelper.startPage(page, rows);//设置分页
    	Map<String,Object> params1 = new HashMap<String, Object>();
		params1.put("stype", stype);
		params1.put("skey", skey);
		List<Article> artList = articleService.selectArticleByCondition(params1);
		PageInfo<Article> pageData=new PageInfo<Article>(artList);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("total", pageData.getTotal());
		params.put("rows",pageData.getList());
		return ResultGenerator.genSuccessResult(params);
	}
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
    @RequiresPermissions("system:article:select")
	@PostMapping("select/{id}")
    @ResponseBody
	public Result selectArticleById(@PathVariable int id) {
		Article article = articleService.selectArticlebyId(id);
		return ResultGenerator.genSuccessResult(article);
	}
	
	/**
	 * 根据id删除记录
	 * @param ids
	 * @return
	 */
    @RequiresPermissions("system:article:delete")
    @PostMapping("delete")
    @SystemControllerLog(description = "根据ids删除文章")   
    @ResponseBody
	public Result delete(@RequestParam String ids) {
    	String[] idString = ids.split(",");
    	for(int i = 0;i < idString.length;i++ ) {
    		articleService.deleteById(Integer.parseInt(idString[i]));
    	}
		return ResultGenerator.genSuccessResult().setMessage("删除成功");
	}
    
    /**
     * 新增
     * @param article
     * @return
     */
    @RequiresPermissions("system:article:insert")
    @PostMapping("insert")
    @SystemControllerLog(description = "新增文章")  
    @ResponseBody
	public Result insert(@ModelAttribute Article article) {
    	logger.info("article:"+article.toString());
    	article.setArtDate(new Date());
    	article.setArtTimes(0);
    	article.setArtUser(GetLoginSesseion().getUsId());
    	articleService.save(article);
		return ResultGenerator.genSuccessResult().setMessage("新增成功");
	}
    
    @RequiresPermissions("system:article:update")
    @PostMapping("update")
    @SystemControllerLog(description = "更新文章")  
    @ResponseBody
    public Result update(@ModelAttribute Article article,String artContents) {
    	logger.info("article:"+article.toString());
    	article.setArtContent(artContents);
    	articleService.update(article);
    	return ResultGenerator.genSuccessResult().setMessage("更新成功");
    }
}
