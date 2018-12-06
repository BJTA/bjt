package com.zq.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.zq.entity.ModuleQuery;
import com.zq.entity.Modules;

public interface ModuleService {
	
	public List<Modules> queryModelTree();
	
	
	
	
	
	
	
	/**
	 * 添加新模块
	 * @param modulename 模块名称
	 * @param modulepath 模块路径
	 * @param moduleweight 权重
	 * @param parent_id 所属模块的编号
	 * @return 受影响的行数
	 */
	public int addModule(String modulename,String modulepath,Integer moduleweight,Integer parent_id);
	/**
     * 修改模块信息
     * @param modulename 模块名字
     * @param modulepath 新路径
     * @param moduleweight 权重
     * @param moduleid 目标模块编号
     * @return 受影响的行数
     */
	public int updateModule(String modulename,String modulepath,Integer moduleweight,Integer moduleid);
	/**
	 * 删除指定的模块
	 * @param moduleid 目标模块编号
	 */
	public void deleteMoule(Integer moduleid);
	/**
	 * 多条件动态分页排序查询
	 * 
	 * @param moduleQuery 查询条件封装的对象
	 * @param page      页码
	 * @param size      页面显示条数
	 * @return
	 */
	public Page<Modules> queryByDynamicSQLPage(ModuleQuery moduleQuery, Integer page, Integer size);

}
