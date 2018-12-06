package com.zq.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zq.entity.Modules;
import com.zq.entity.RoleQuery;
import com.zq.entity.Roles;
import com.zq.service.RoleService;
import com.zq.utils.Result;

@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {
	@Autowired
	RoleService roleService;
	/**
	 * 添加个新角色
	 * http://localhost:8090/role/addroles?rolename=辅导员
	 * @param rolename 角色名
	 * @return json格式｛"success":true/false,"message":添加成功/失败｝、json格式｛"remark":提示消息：角色名已存在"｝
	 */
	@RequestMapping("/addroles")
   public Object addRole(String rolename) {
	   try {
			if (roleService.addRole(rolename)>0)
				return Result.toMap(1, "添加");
			else
				return Result.toMap(0, "添加");
		} catch (Exception e) {
			return Result.toRemark("角色名已存在");
		}
   }
   
   /**
	   * 删除指定的用户
	   * http://localhost:8090/role/deleteRoleByid?roleid=1
	 * @param user 目标用户
	 * @return json格式 ｛"success":true/false,"message":删除成功/失败、json格式｛"remark":提示消息：该角色拥有管理某些模块权利"｝
	 */
	@RequestMapping("/deleteRoleByid")
	public Object deleteUser(Integer roleid) {
		try {
			roleService.deleteRoleByid(roleid);
			
		} catch (Exception e) {
			return Result.toRemark("该角色关联其他信息");
		}
		 return Result.toMap(1, "删除");
	}
	/**
	 * 更新角色名称
	 * http://localhost:8090/role/updateroles?rolename=辅导员1&roleid=1
	 * @param rolename 新角色名
	 * @param roleid 目标角色编号
	 * @return  json格式｛"success":true/false,"message":修改成功/失败｝、json格式｛"remark":提示消息：角色名已存在"｝
	 */
	@RequestMapping("/updateroles")
	   public Object updateRole(String rolename,Integer roleid) {
		   try {
				if (roleService.updateRole(rolename, roleid)>0)
					return Result.toMap(1, "修改");
				else
					return Result.toMap(0, "修改");
			} catch (Exception e) {
				return Result.toRemark("角色名已存在");
			}
	   }
	
	/**
	 * 多条件分页排序查询
	 * http://localhost:8090/role/selectRole?rolename=员&page=1&pageSize=10
	 * @param userQuery 查询条件分装的对象
	 * @param page 页码
	 * @param size 每个页面要显示的条数
	 * @return
	 */
	@RequestMapping("/selectRole")
	public Object selectRoleBywhere(RoleQuery roleQuery,int page,int pageSize) { 
		Map<String,Object> map=new HashMap<String,Object>();
		
		Page<Roles> rolelist=roleService.queryByDynamicSQLPage(roleQuery, 1, 10);
		List<Roles> uList=rolelist.getContent();
		Long count=rolelist.getTotalElements();
		map.put("rows", uList);
		map.put("total",count);
		return map;
	}
	/**
	 * 查询已经拥有的角色
	 * @param userid 用户编号
	 * @return 角色集合
	 */
	@RequestMapping("/queryMyRoles")
	public List<Roles> queryMyRoles(Integer userid){
		
		List<Roles> myroles=roleService.queryMyRoles(userid);
		System.out.println("自己已有的角色==>"+myroles);
	return myroles;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 
	
	
	
	
	
	
	
	
	
	/**
	 * 查询没有拥有的角色
	 * @param userid 用户编号
	 * @return 角色集合
	 */
	@RequestMapping("/queryMyNotRoles")
	public List<Roles> queryMyNotRoles(Integer userid){
		
		List<Roles> mynotroles=roleService.queryMyNotRoles(userid);
		System.out.println("自己没有的角色==>"+mynotroles);
	return mynotroles;
	}
	
	/**
	 * 将该角色已经具有的模块打钩
	 * @param roleId 角色编号
	 * @return 模块集合
	 */
	@RequestMapping("/getRoleModel")
	public Set<Modules> getRoleModel(Integer roleId){
		Set<Modules> models=roleService.getRoleModel(roleId);
		System.out.println("models=============>"+models);
		return models;
	}
}
