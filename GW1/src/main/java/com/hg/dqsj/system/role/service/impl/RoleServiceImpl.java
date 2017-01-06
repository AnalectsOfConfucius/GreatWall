package com.hg.dqsj.system.role.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hg.core.util.DateUtil;
import com.hg.dqsj.system.role.dao.RoleDao;
import com.hg.dqsj.system.role.entity.Operate;
import com.hg.dqsj.system.role.entity.Role;
import com.hg.dqsj.system.role.entity.RoleAuth;
import com.hg.dqsj.system.role.entity.RoleMenu;
import com.hg.dqsj.system.role.entity.RoleOperate;
import com.hg.dqsj.system.role.service.RoleService;

/**
 * 功能：角色管理SERVICE实现类
 * 
 * @author Administrator
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> selectAllRole() {
		return this.roleDao.selectAllRole();
	}

	@Override
	public void insertRole(Role role) {
		this.roleDao.insertRole(role);
	}

	@Override
	public void updateRole(Role role) {
		this.roleDao.updateRole(role);
	}

	@Override
	public void deleteRole(String roleId, String updateUserId) {
		Map<String, String> m = new HashMap<>();
		m.put("id", roleId);
		m.put("updateDate", DateUtil.getFullTime());
		m.put("updateUserId", updateUserId);
		this.roleDao.deleteRole(m);
	}

	@Override
	public List<Role> selectRoleByIdAndName(Map<String, String> m) {
		return this.roleDao.selectRoleByIdAndName(m);
	}

	@Override
	public List<RoleAuth> selectRoleAuth(Map<String, String> param) {
		return this.roleDao.selectRoleAuth(param);
	}

	@Override
	@Transactional
	public void insertRoleOperate(Map<String, String> param, List<RoleOperate> rolist) {
		this.roleDao.deleteRoleOperate(param);
		if (null != rolist && rolist.size() > 0) {
			for (int i = 0; i < rolist.size(); i++) {
				this.roleDao.insertRoleOperate(rolist.get(i));
			}
		}
	}

	@Override
	public List<Operate> selectOperate(Map<String, String> param) {
		return this.roleDao.selectOperate(param);
	}

	@Override
	public List<RoleMenu> selectMenu(Map<String, String> param) {
		return this.roleDao.selectMenu(param);
	}

}
