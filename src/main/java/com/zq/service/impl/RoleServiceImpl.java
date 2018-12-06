package com.zq.service.impl;

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

import com.zq.entity.Modules;
import com.zq.entity.RoleQuery;
import com.zq.entity.Roles;
import com.zq.repository.RolesRepository;
import com.zq.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RolesRepository rolesRepository;
	@Override
	public int addRole(String rolename) {
		return rolesRepository.addRole(rolename);
	}

	@Override
	public int updateRole(String rolename, Integer roleid) {
		return rolesRepository.updateRole(rolename, roleid);
	}
	@Override
	public int deleteRoleByid(Integer roleid) {
		return rolesRepository.deleteRoleByid(roleid);
	}

	@Override
	public Page<Roles> queryByDynamicSQLPage(RoleQuery roleQuery, Integer page, Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC, "roleid");
		Pageable pageable = new PageRequest(page-1, size, sort);
		return rolesRepository.findAll(this.getWhereClause(roleQuery),pageable);
	}

	
	/**
	 * 动态生成where语句 匿名内部类形式
	 * @param roleQuery 用户查询条件封装的一个类对象
	 * @return
	 */
	private Specification<Roles> getWhereClause(final RoleQuery roleQuery) {
		return new Specification<Roles>() {
			@Override
			public Predicate toPredicate(Root<Roles> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				// 动态SQL表达式
				List<Expression<Boolean>> exList = predicate.getExpressions();
				// 动态SQL表达式集合

				// 角色名模糊查询
				if (roleQuery.getRolename() != null && !" ".equals(roleQuery.getRolename())) {
					exList.add(cb.like(root.<String>get("rolename"), "%" + roleQuery.getRolename() + "%"));
				}
				
				return predicate;
			}

		};

	}

	@Override
	public List<Roles> queryMyRoles(Integer userid) {
		// TODO Auto-generated method stub
		return rolesRepository.queryMyRoles(userid);
	}

	@Override
	public List<Roles> queryMyNotRoles(Integer userid) {
		// TODO Auto-generated method stub
		return rolesRepository.queryMyNotRoles(userid);
	}
	@Override
	public Roles findByRoleid(Integer roleid) {
		return rolesRepository.findByRoleid(roleid);
	}
	
	@Override
	public Set<Modules> getRoleModel(Integer roleId) {
		
         List<Integer> mids=rolesRepository.setRoleModelByRoleId(roleId);
		//我的父编号为1
		Set<Modules> parentList=rolesRepository.queryChildrenById(1);
		this.setChildrens(parentList, mids);
		return parentList;
	};
	//递归核心
	public void setChildrens(Set<Modules> parentList, List<Integer> mids) {
		for (Modules model : parentList) {
			
			Set<Modules> childrens =rolesRepository.queryChildrenById(model.getModuleid());
			
			if (childrens != null && !childrens.isEmpty()) {
			
				model.setChildren(childrens);
			
				this.setChildrens(childrens, mids);
				
			}else{
				if (mids.contains(model.getModuleid())) {
					model.setChecked(true);
				}
			}

		}

	}
	
	
	
}
