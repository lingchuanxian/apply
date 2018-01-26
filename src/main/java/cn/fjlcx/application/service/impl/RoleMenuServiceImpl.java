package cn.fjlcx.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fjlcx.application.bean.Menu;
import cn.fjlcx.application.bean.RoleMenu;
import cn.fjlcx.application.bean.UrlFilter;
import cn.fjlcx.application.config.ShiroFilerChainManager;
import cn.fjlcx.application.core.AbstractService;
import cn.fjlcx.application.mapper.MenuMapper;
import cn.fjlcx.application.mapper.RoleMenuMapper;
import cn.fjlcx.application.service.RoleMenuService;

/**
 *  @author ling_cx 
 *  @date   2017/09/28.
 */
@Service
@Transactional
public class RoleMenuServiceImpl extends AbstractService<RoleMenu> implements RoleMenuService {
	private static final Logger logger = LoggerFactory.getLogger(RoleMenuServiceImpl.class);
	@Resource
	private RoleMenuMapper oaRoleMenuMapper;
	@Resource
	private MenuMapper menuMapper;
	@Resource  
	private ShiroFilerChainManager shiroFilterChainManager;  

	@Override
	public List<RoleMenu> selectMenuByRole(int id) {
		return oaRoleMenuMapper.selectMenuByRole(id);
	}

	@Override
	public int deleteRoleMenuByRole(int id) {
		return oaRoleMenuMapper.deleteRoleMenuByRole(id);
	}

	@Override
	public int deleteRoleMenuByMenu(int id) {
		return oaRoleMenuMapper.deleteRoleMenuByMenu(id);
	}

	@Override
	public List<RoleMenu> selectRoleMenuAll() {
		return oaRoleMenuMapper.selectRoleMenuAll();
	}  

	@Override
	public List<RoleMenu> selectRoleMenuByMenu(int id) {
		return oaRoleMenuMapper.selectRoleMenuByMenu(id);
	}
	
	@Override
	public void refreshFilterChain() {
		//initFilterChain();
	}

	/*@PostConstruct  
	public void initFilterChain() {  
		List<UrlFilter> filters = new ArrayList<UrlFilter>();
		//查询所有菜单，为每个菜单绑定角色
		List<Menu> listMenu = menuMapper.selectAllOfMenu();
		for(Menu menu : listMenu) {
			if(menu.getMuPid() != 0) {
				UrlFilter filter = new UrlFilter();
				filter.setId(Long.parseLong(String.valueOf(menu.getMuId())));
				filter.setName(menu.getMuText());
				filter.setUrl("/"+menu.getMuUrl());
				StringBuilder roles = new StringBuilder();
				List<RoleMenu> listRoleMenu = selectRoleMenuByMenu(menu.getMuId());
				for(RoleMenu rm: listRoleMenu) {
					roles.append(rm.getRole().getRlCode() + StringUtils.DEFAULT_DELIMITER_CHAR);
				}
				filter.setRoles(roles.toString().substring(0, roles.toString().length() - 1));
				filters.add(filter);
				if(org.apache.commons.lang3.StringUtils.isNotEmpty(menu.getMuRequestUrl())) {
					filter.setUrl("/"+menu.getMuRequestUrl());
					filters.add(filter);
				}
			}
		}
		shiroFilterChainManager.initFilterChains(filters);  
	}
*/
	@Override
	public int falseDeletion(int id) {
		return oaRoleMenuMapper.falseDeletion(id);
	}
}
