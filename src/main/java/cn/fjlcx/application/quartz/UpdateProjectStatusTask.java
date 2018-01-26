package cn.fjlcx.application.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.fjlcx.application.bean.ScheduleJob;
import cn.fjlcx.application.controller.BaseController;
@Component
public class UpdateProjectStatusTask {
	public static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 修改项目报名的状态为“报名中”
	 * @param scheduleJob
	 * @throws Exception
	 */
	public void ChangeToStart(ScheduleJob scheduleJob)throws Exception{
		
	}
}
