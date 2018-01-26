package cn.fjlcx.application.service.impl;

import cn.fjlcx.application.mapper.DepartmentMapper;
import cn.fjlcx.application.bean.Department;
import cn.fjlcx.application.service.DepartmentService;
import cn.fjlcx.application.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

/**
 *  @author ling_cx 
 *  @date   2017/09/26.
 */
@Service
@Transactional
public class DepartmentServiceImpl extends AbstractService<Department> implements DepartmentService {
    @Resource
    private DepartmentMapper oaDepartmentMapper;

	@Override
	public List<Department> selectDepByOrgId(int id) {
		return oaDepartmentMapper.selectDepByOrgId(id);
	}

	@Override
	public Department selectDepById(int id) {
		return oaDepartmentMapper.selectDepById(id);
	}

	@Override
	public int selectMaxOrder() {
		return oaDepartmentMapper.selectMaxOrder();
	}

	@Override
	public int falseDeletion(int id) {
		return oaDepartmentMapper.falseDeletion(id);
	}

}
