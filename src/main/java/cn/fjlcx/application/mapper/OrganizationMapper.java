package cn.fjlcx.application.mapper;

import java.util.List;
import java.util.Map;

import cn.fjlcx.application.bean.Organization;
import cn.fjlcx.application.core.Mapper;

public interface OrganizationMapper extends Mapper<Organization> {
	List<Organization> selectOrganizationOfAll();
	int selectMaxOrder();
	Organization selectOrganizationById(int id);
	int updateOrganizationOrder(Map<String,Integer> params);
	int falseDeletion(int id);
}