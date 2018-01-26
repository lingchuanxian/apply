package ${basePackage}.controller;

import ${basePackage}.core.Result;
import ${basePackage}.core.ResultGenerator;
import ${basePackage}.bean.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import cn.fjlcx.application.bean.${modelNameUpperCamel};

import javax.annotation.Resource;
import java.util.List;

/**
 *  @author ${author} 
 *  @date   ${date}.
 */
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller {
	private static final Logger logger = LoggerFactory.getLogger(${modelNameUpperCamel}Controller.class);
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;
    
    /**
     * 新增
     * @param model
     * @return
     */
    @PostMapping("Add${modelNameLowerCamel}")
	public Result Add${modelNameLowerCamel}(@ModelAttribute ${modelNameUpperCamel} model) {
    	logger.info("model:"+model.toString());
    	${modelNameLowerCamel}Service.save(model);
		return ResultGenerator.genSuccessResult().setMessage("新增成功");
	}
    
    /**
	 * 根据id删除记录
	 * @param ids
	 * @return
	 */
    @PostMapping("Delete${modelNameUpperCamel}ById")
	public Result Delete${modelNameUpperCamel}ById(@RequestParam String ids) {
    	String[] idString = ids.split(",");
    	for(int i = 0;i < idString.length;i++ ) {
    		${modelNameLowerCamel}Service.deleteById(Integer.parseInt(idString[i]));
    	}
		return ResultGenerator.genSuccessResult().setMessage("删除成功");
	}
	
    /**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	@GetMapping("Select${modelNameUpperCamel}ById")
	public Result Select${modelNameUpperCamel}ById(@RequestParam int id) {
		${modelNameLowerCamel} model = ${modelNameUpperCamel}Service.findById(id);
		return ResultGenerator.genSuccessResult(model);
	}

}
