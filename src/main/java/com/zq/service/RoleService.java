package com.zq.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.zq.entity.Modules;
import com.zq.entity.RoleQuery;
import com.zq.entity.Roles;

public interface RoleService {
	/**
	 * 添加新角色
	 * 
	 * @param rolename 角色名
	 * @return 受影响的行数
	 */
	public int addRole(String rolename);

	/**
	 * 修改角色名称
	 * 
	 * @param rolename 新角色名
	 * @param roleid   角色编号
	 * @return 受影响的行数
	 */
	public int updateRole(String rolename, Integer roleid);

	/**
	 * 删除某个角色
	 * @param roles 目标对象
	 * @return 受影响的行数
	 */
	public int deleteRoleByid(Integer roleid);

	/**
	 * 多条件动态分页排序查询
	 * 
	 * @param roleQuery 查询条件封装的对象
	 * @param page      页码
	 * @param size      页面显示条数
	 * @return
	 */
	public Page<Roles> queryByDynamicSQLPage(RoleQuery roleQuery, Integer page, Integer size);
	
	/**
	 * 查询自己拥有的角色
	 * @param userid 用户编号
	 * @return 角色集合
	 */
	public List<Roles> queryMyRoles(Integer userid);
	/**
	 * 查询自己没有拥有的角色
	 * @param userid 用户编号
	 * @return 角色集合
	 */
	public List<Roles> queryMyNotRoles(Integer userid);
	/**
	 * 关键字抽象方法
	 *根据角色编号查询出该角色对象
	 * @param roleid 角色编号
	 * @return 对相应角色对象
	 */
	public Roles findByRoleid(Integer roleid);
	/**
	 * 将该角色已经具有的模块打钩
	 * @param roleId 角色编号
	 * @return 模块集合
	 */
	public Set<Modules> getRoleModel(Integer roleId);
}
