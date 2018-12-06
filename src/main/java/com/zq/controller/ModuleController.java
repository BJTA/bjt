package com.zq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zq.entity.Modules;
import com.zq.service.ModuleService;
import com.zq.utils.Result;

@RestController
@RequestMapping("/module")
public class ModuleController {
	@Autowired
	ModuleService moduleService;
	
	@RequestMapping("/queryModelTree")
	public List<Modules> queryModelTree(){
		
		List<Modules> models=moduleService.queryModelTree();
		System.out.println("模块及和啊···········"+models);
		return models;
	}
	
	
	
	
	
	
	
	/**
	 * 添加个新模块
	 * http://localhost:8090/module/addModule?modulename=单词管理&modulepath=danci&moduleweight=20&parent_id=1
	 *
     * @param modulename 模块名字
     * @param modulepath 路径
     * @param moduleweight 权重
	 * @param parent_id 父模块编号
	 * @return json格式｛"success":true/false,"message":添加成功/失败｝
	 */
	
	@RequestMapping("/addModule")
   public Object addModule(String modulename,String modulepath,Integer moduleweight,Integer parent_id) {
	   	if (moduleService.addModule(modulename, modulepath, moduleweight, parent_id)>0)
				return Result.toMap(1, "添加");
			else
				return Result.toMap(0, "添加");
		
   }
   
   /**
	   * 删除指定的模块
	   * http://localhost:8090/module/deleteModuleByid?moduleid=1
	 * @param user 目标用户
	 * @return json格式 ｛"success":true/false,"message":删除成功/失败、json格式｛"remark":提示消息：该角色拥有管理某些模块权利"｝
	 */
/*	@RequestMapping("/deleteModuleByid")
	public Object deleteUser(Integer moduleid) {
		try {
			roleService.deleteRoleByid(moduleid);
			
		} catch (Exception e) {
			return Result.toRemark("该角色关联其他信息");
		}
		 return Result.toMap(1, "删除");
	}*/
	/**
	 * 更新角色名称
	 * http://localhost:8090/module/updateroles?rolename=辅导员1&roleid=1
	 * @param rolename 新角色名
	 * @param roleid 目标角色编号
	 * @return  json格式｛"success":true/false,"message":修改成功/失败｝、json格式｛"remark":提示消息：角色名已存在"｝
	 */
	/*@RequestMapping("/updateroles")
	   public Object updateRole(String rolename,Integer roleid) {
		   try {
				if (roleService.updateRole(rolename, roleid)>0)
					return Result.toMap(1, "修改");
				else
					return Result.toMap(0, "修改");
			} catch (Exception e) {
				return Result.toRemark("角色名已存在");
			}
	   }*/
	
	/**
	 * 多条件分页排序查询
	 * http://localhost:8090/module/selectModule?modulename=管理&page=1&size=10
	 * @param moduleQuery 查询条件分装的对象
	 * @param page 页码
	 * @param size 每个页面要显示的条数
	 * @return
	 */
	/*@RequestMapping("/selectModule")
	public Object selectRoleBywhere(ModuleQuery moduleQuery,int page,int size) { 
		Map<String,Object> map=new HashMap<String,Object>();	
		Page<Modules> moduleslist=moduleService.queryByDynamicSQLPage(moduleQuery, page, size);
		List<Modules> mlist=moduleslist.getContent();
		Long count=moduleslist.getTotalElements();
		map.put("rows", mlist);
		map.put("total",count);		
		return map;
	}*/
}
