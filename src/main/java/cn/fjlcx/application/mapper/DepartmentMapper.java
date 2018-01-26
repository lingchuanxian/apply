package cn.fjlcx.application.mapper;

import java.util.List;

import cn.fjlcx.application.bean.Department;
import cn.fjlcx.application.core.Mapper;

public interface DepartmentMapper extends Mapper<Department> {
	List<Department> selectDepByOrgId(int id);
	Department selectDepById(int id);
	int selectMaxOrder();
	int falseDeletion(int id);
}