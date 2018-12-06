package com.zq.service.impl;

import java.util.Date;
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
import com.zq.entity.UserQuery;
import com.zq.entity.Users;
import com.zq.repository.ModuleRepository;
import com.zq.repository.UserRepository;
import com.zq.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModuleRepository moduleRepository;

	@Override
	public int addUser(String name, String pwd) {
		return userRepository.addUser(name, pwd);
	}
	
	
	@Override
	public Users findByLoginnameAndPassword(String loginname, String password) {
		return userRepository.findByLoginnameAndPassword(loginname, password);
	}

	@Override
	public int updateUserLastloginTime(Integer userid) {
		return userRepository.updateUserLastloginTime(userid);
	}

	@Override
	public int resetPwd(String pwd, Integer userid) {
		// TODO Auto-generated method stub
		return userRepository.resetPwd(pwd, userid);
	}
	@Override
	public int resetUserState(Integer islockout, Integer userid) {
		// TODO Auto-generated method stub
		return userRepository.resetUserState(islockout, userid);
	}
	@Override
	public List<Integer> selectRoleidsByUid(Integer uid) {
		return userRepository.selectRoleidsByUid(uid);
	}
	@Override
	public void deleteUser(Users users) {
		userRepository.delete(users);
	}


	@Override
	public int updateUser( String email, String phone, Integer userid) {
	
		return userRepository.updateUser(email, phone, userid);
	}


	@Override
	public Page<Users> queryByDynamicSQLPage(UserQuery userQuery, Integer page, Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC, "createtime");
		Pageable pageable = new PageRequest(page-1, size, sort);
		return userRepository.findAll(this.getWhereClause(userQuery),pageable);
	}

	
	/**
	 * 动态生成where语句 匿名内部类形式
	 * @param userQuery 用户查询条件封装的一个类对象
	 * @return
	 */
	private Specification<Users> getWhereClause(final UserQuery userQuery) {
		return new Specification<Users>() {
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				// 动态SQL表达式
				List<Expression<Boolean>> exList = predicate.getExpressions();
				// 动态SQL表达式集合

				// 登陆名模糊查询
				if (userQuery.getLoginname() != null && !" ".equals(userQuery.getLoginname())) {
					exList.add(cb.like(root.<String>get("loginname"), "%" + userQuery.getLoginname() + "%"));
				}
				// 是否锁定查询
				if (userQuery.getIslockout() != null) {
					exList.add(cb.equal(root.<Integer>get("islockout"), userQuery.getIslockout()));
				}
				// 错误次数查询
				if (userQuery.getPsdwrongtime() != null) {
					exList.add(cb.equal(root.<Integer>get("psdwrongtime"), userQuery.getPsdwrongtime()));
				}
				// 密保邮箱查询
				if (userQuery.getProtectemail() != null && !"".equals(userQuery.getProtectemail())) {
					exList.add(cb.equal(root.get("protectemail").as(String.class), userQuery.getProtectemail()));
				}
				// 密保手机号查询
				if (userQuery.getProtectmtel() != null && !"".equals(userQuery.getProtectmtel())) {
					
					exList.add(cb.like(root.<String>get("protectmtel"), "%" + userQuery.getProtectmtel() + "%"));
				}

				// 大于等于起始创建时间
				if (userQuery.getStartCreatetime() != null) {
					exList.add(cb.greaterThanOrEqualTo(root.<Date>get("createtime"), userQuery.getStartCreatetime()));
				}
				// 小于等于结束创建时间
				if (userQuery.getEndCreatetime() != null) {
					exList.add(cb.lessThanOrEqualTo(root.get("createtime").as(Date.class), userQuery.getEndCreatetime()));// 小于等于截止日期
				}
				// 大于等于起始最后一次登录时间
				if (userQuery.getStartLastlogintime() != null) {
					exList.add(cb.greaterThanOrEqualTo(root.<Date>get("lastlogintime"),userQuery.getStartLastlogintime()));

				}
				// 小于等于结束最后一次登录时间
				if (userQuery.getEndLastlogintime() != null) {
					exList.add(cb.lessThanOrEqualTo(root.get("lastlogintime").as(Date.class),userQuery.getEndLastlogintime()));// 小于等于截止日期
				}
				// 大于等于起始锁定时间
				if (userQuery.getStartLocktime() != null) {
					exList.add(cb.greaterThanOrEqualTo(root.<Date>get("locktime"), userQuery.getStartLocktime()));
				}
				// 小于等于结束锁定时间
				if (userQuery.getEndLocktime() != null) {
					exList.add(cb.lessThanOrEqualTo(root.get("locktime").as(Date.class), userQuery.getEndLocktime()));// 小于等于截止日期
				}
				return predicate;
			}

		};

	}


	@Override
	public List<Modules> queryModuleByloginName(String loginName) {
		return moduleRepository.queryModuleByloginName(loginName);
	}


	@Override
	public List<Modules> queryModuleByroleid(List<Integer> roleid) {
		System.out.println("这里是userserviceImpl==roleid"+roleid);
		// 先查询出角色下的根模块（我的数据库根编号为1）
		List<Modules> parentModulelist = userRepository.selectModuleByRoleids(roleid,1);
		
		// 再查询根模块下的子模块
		this.setChildrens(parentModulelist, roleid);
		return parentModulelist;
	}
	
	
	private void setChildrens(List<Modules> parentList, List<Integer> roleId) {

		for (Modules m : parentList) {// 循环根模块
			// 查询出子菜单
			Set<Modules> childrenList = (Set<Modules>) userRepository.selectModuleByRoleids(roleId,m.getModuleid());

			if (childrenList != null && !childrenList.isEmpty()) {// 有子菜单
				
				// 设置子菜单
				m.setChildren(childrenList);
				
				// 如果子菜单中还有子菜单则继续递归再次设置子菜单
				this.setChildrens((List<Modules>)childrenList, roleId);
			}
			// 如果没有子菜单则递归结束
		}
	}


	@Override
	public Users findByUserid(Integer userid) {
		return userRepository.findByUserid(userid);
	}


	@Override
	public Integer updUser(Integer userid, String loginname, String password, String protectemail, String protectmtel) {
		// TODO Auto-generated method stub
		return userRepository.updUser(userid, loginname, password, protectemail, protectmtel);
	}


	








}
