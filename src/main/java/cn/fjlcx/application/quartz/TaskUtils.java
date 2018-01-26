package cn.fjlcx.application.quartz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.fjlcx.application.bean.ScheduleJob;
import cn.fjlcx.application.utils.SpringBeanFactoryUtils;


public class TaskUtils {
	public final static Logger log = Logger.getLogger(TaskUtils.class);

	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * 
	 * @param scheduleJob
	 */
	public static void invokMethod(ScheduleJob scheduleJob) {
		Object object = null;
		Class<?> clazz = null;
		if (StringUtils.isNotBlank(scheduleJob.getJobSpringId())) {
			object = SpringBeanFactoryUtils.getBean(scheduleJob.getJobSpringId());
		} else if (StringUtils.isNotBlank(scheduleJob.getJobBeanClass())) {
			try {
				clazz = Class.forName(scheduleJob.getJobBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (object == null) {
			log.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，请检查是否配置正确！！！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getMethod(scheduleJob.getJobMethodName(),ScheduleJob.class); 
		} catch (NoSuchMethodException e) {
			log.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误！！！");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (method != null) {
			try {
				method.invoke(object,scheduleJob);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]----------启动成功");
	}
}
