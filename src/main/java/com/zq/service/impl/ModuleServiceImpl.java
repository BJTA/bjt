package com.zq.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zq.entity.ModuleQuery;
import com.zq.entity.Modules;
import com.zq.repository.ModuleRepository;
import com.zq.service.ModuleService;
@Service
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	ModuleRepository moduleRepository;
	
	
	
	
	@Override
	public List<Modules> queryModelTree(){
		//查询出所有根菜单为0的模块集合
		List<Modules> pList=moduleRepository.queryChildrenById(0);
		
		this.setModelChildrens(pList);
		return pList;
	};
	public void setModelChildrens(List<Modules> parentList) {
		for (Modules model : parentList) {
			// 根据模块id查询出所有子孩子
			List<Modules> childrens = moduleRepository.queryChildrenById(model.getModuleid());
			Set<Modules> modset=new HashSet<Modules>();
			
			for (int i = 0; i < childrens.size(); i++) {
				modset.add(childrens.get(i));	
			}
			
			// 如果没有子菜单则递归结束
			if (childrens != null && !childrens.isEmpty()) {// 有子菜单
				// 设置子菜单
				model.setChildren(modset);
				// 如果有子菜单则继续递归设置子菜单
				this.setModelChildrens(childrens);
				
			}

		}

	}
	
	
	
	
	
	
	@Override
	public int addModule(String modulename, String modulepath, Integer moduleweight, Integer parent_id) {
		return moduleRepository.addModule(modulename, modulepath, moduleweight, parent_id);
	}

	@Override
	public int updateModule(String modulename, String modulepath, Integer moduleweight, Integer moduleid) {
		return moduleRepository.updateModule(modulename, modulepath, moduleweight, moduleid);
	}
	@Override
	public void deleteMoule(Integer moduleid) {
		moduleRepository.delete(moduleid);
	}
	@Override
	public Page<Modules> queryByDynamicSQLPage(ModuleQuery moduleQuery, Integer page, Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC, "moduleweight");
		Pageable pageable = new PageRequest(page-1, size, sort);
		System.out.println("动态条件SQL语句"+this.getWhereClause(moduleQuery));
		return moduleRepository.findAll(this.getWhereClause(moduleQuery),pageable);
	}

	
	/**
	 * 动态生成where语句 匿名内部类形式
	 * @param roleQuery 用户查询条件封装的一个类对象
	 * @return
	 */
	private Specification<Modules> getWhereClause(final ModuleQuery moduleQuery) {
		return new Specification<Modules>() {
			@Override
			public Predicate toPredicate(Root<Modules> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				// 动态SQL表达式
				List<Expression<Boolean>> exList = predicate.getExpressions();
				// 动态SQL表达式集合

				// 模块名模糊查询
				if (moduleQuery.getModulename() != null && !" ".equals(moduleQuery.getModulename())) {
					exList.add(cb.like(root.<String>get("modulename"), "%" + moduleQuery.getModulename() + "%"));
				}
				
				if ( true) {
					exList.add(cb.notEqual(root.<Integer>get("moduleid"), 1));
				}
				return predicate;
			}

		};

	}

	

}
