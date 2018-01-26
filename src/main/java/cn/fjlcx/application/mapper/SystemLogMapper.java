package cn.fjlcx.application.mapper;

import java.util.List;
import java.util.Map;

import cn.fjlcx.application.bean.SystemLog;
import cn.fjlcx.application.core.Mapper;

public interface SystemLogMapper extends Mapper<SystemLog> {
	List<SystemLog>  selectSystemLogByCondition(Map<String,Object> params);
	SystemLog selectSystemLogById(int id);
}