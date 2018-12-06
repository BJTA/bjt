package com.zq.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zq.entity.Modules;
import com.zq.entity.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer>,JpaSpecificationExecutor<Roles>{
	
	/**
	 * 添加新角色
	 * @param rolename 角色名
	 * @return 受影响的行数
	 */
	@Transactional
	@Modifying
	@Query(value="INSERT INTO p_roletb(r_rolename) VALUES(:rolename)",nativeQuery=true)
	public int addRole(@Param(value ="rolename")String rolename);
	/**
	 * 修改角色名称
	 * @param rolename 新角色名
	 * @param roleid 角色编号
	 * @return 受影响的行数
	 */
	@Transactional
	@Modifying
	@Query(value="UPDATE p_roletb SET r_rolename=:rolename WHERE r_roleid=:roleid",nativeQuery=true)
	public int updateRole(@Param(value ="rolename")String rolename,@Param(value="roleid")Integer roleid);
	
	/**
	 * 删除某个角色
	 * @param roleid  目标对象编号
	 * @return 受影响的行数
	 */
	@Transactional
	@Modifying
	@Query(value="DELETE FROM p_roletb  WHERE r_roleid=:roleid",nativeQuery=true)
	public int deleteRoleByid(@Param(value="roleid")Integer roleid);
	/**
	 * 查询自己拥有的角色
	 * @param userid 用户编号
	 * @return 角色集合
	 */
	@Query(value="SELECT * FROM p_roletb WHERE r_roleid IN (SELECT r_roleid FROM user_role WHERE u_userid=:userid)",nativeQuery=true)
	public List<Roles> queryMyRoles(@Param(value="userid")Integer userid);
	/**
	 * 查询自己没有拥有的角色
	 * @param userid 用户编号
	 * @return 角色集合
	 */
	@Query(value="SELECT * FROM p_roletb WHERE r_roleid NOT IN (SELECT r_roleid FROM user_role WHERE u_userid=:userid)",nativeQuery=true)
	public List<Roles> queryMyNotRoles(@Param(value="userid")Integer userid);
	/**
	 * 关键字抽象方法
	 *根据角色编号查询出该角色对象
	 * @param roleid 角色编号
	 * @return 对相应角色对象
	 */
	public Roles findByRoleid(Integer roleid);
	/**
	 * 根据角色id查询出该角色下的模块id集合
	 * @param roleId 角色编号
	 * @return
	 */
	@Query(value = "SELECT m_moduleid FROM role_model WHERE r_roleid=:roleId", nativeQuery = true)
	public List<Integer> setRoleModelByRoleId(@Param(value="roleId")Integer roleId);
	/**
	 * 根据父模块编号查询他的子模块
	 * @param mid 父模块编号
	 * @return 模块集合
	 */
	@Query(value = "SELECT * FROM p_moduletb WHERE parent_id=:mid AND m_moduleid != 1 ", nativeQuery = true)
	public Set<Modules> queryChildrenById(@Param(value="mid")Integer mid);
}
