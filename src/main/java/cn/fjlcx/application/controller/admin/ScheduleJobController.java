package cn.fjlcx.application.controller.admin;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.fjlcx.application.bean.ScheduleJob;
import cn.fjlcx.application.core.Result;
import cn.fjlcx.application.core.ResultGenerator;
import cn.fjlcx.application.service.ScheduleJobService;

/**
 * @author ling_cx
 * @version 1.0
 * @Description 
 * @date 2018年1月30日 上午10:54:27
 * @Copyright: 2018 www.lingcx.cn Inc. All rights reserved.
 */
@RestController
@RequestMapping("/schedule/job")
public class ScheduleJobController {
	private static final Logger logger = LoggerFactory.getLogger(ScheduleJobController.class);
    @Resource
    private ScheduleJobService scheduleJobService;
    
    /**
     * 新增
     * @param model
     * @return
     */
    @PostMapping("AddScheduleJob")
	public Result AddScheduleJob(@ModelAttribute ScheduleJob model) {
    	logger.info("model:"+model.toString());
    	scheduleJobService.save(model);
		return ResultGenerator.genSuccessResult().setMessage("新增成功");
	}
    
    /**
	 * 根据id删除记录
	 * @param ids
	 * @return
	 */
    @PostMapping("DeleteScheduleJobById")
	public Result DeleteScheduleJobById(@RequestParam String ids) {
    	String[] idString = ids.split(",");
    	for(int i = 0;i < idString.length;i++ ) {
    		scheduleJobService.deleteById(Integer.parseInt(idString[i]));
    	}
		return ResultGenerator.genSuccessResult().setMessage("删除成功");
	}
	
    /**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	@GetMapping("SelectScheduleJobById")
	public Result SelectScheduleJobById(@RequestParam int id) {
		ScheduleJob model = scheduleJobService.findById(id);
		return ResultGenerator.genSuccessResult(model);
	}

}
