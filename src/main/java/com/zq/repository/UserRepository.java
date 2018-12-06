package com.zq.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zq.entity.Modules;
import com.zq.entity.Users;


public interface UserRepository extends JpaRepository<Users, Integer>,JpaSpecificationExecutor<Users>{
	/**
	   * 添加新用户
	 * @param name 登录名
	 * @param pwd 用户输入加盐之后的密码 
	 * @param lastLoginTime 最后一次登录时间
	 * @return
	 */
	@Transactional
	@Modifying
	@Query(value="INSERT INTO p_usertb(u_loginname,u_password,u_lastlogintime)VALUES(:name,:pwd,now())",nativeQuery=true)
	public int addUser(@Param(value ="name") String name,@Param(value ="pwd")String pwd);
	/**
	   * 登录检查
	 * @param name 用户输入登录名
	 * @param pwd  用户输入加盐之后的密码
	 * @return  匹配后的对象，不存在则为null
	 */
	public Users findByLoginnameAndPassword(String loginname,String password);
	/**
	   * 更新某用户最后登陆时间
	 * @param userid 用户编号
	 * @return 受影响的行数
	 */
	@Transactional
	@Modifying
	@Query(value="UPDATE p_usertb SET u_lastlogintime=NOW() WHERE u_userid=:userid",nativeQuery=true)
	public int updateUserLastloginTime(@Param(value="userid")Integer userid);
	/**
	   * 重置密码
	 * @param userid 用户编号
	 * @param pwd 新密码
	 * @return 受影响的行数
	 */
	@Transactional
	@Modifying
	@Query(value="UPDATE p_usertb SET u_password=:pwd WHERE u_userid=:userid",nativeQuery=true)
	public int resetPwd(@Param(value="pwd")String pwd,@Param(value="userid")Integer userid);
	/**
	   * 锁定解锁
	 * @param userid 用户编号
	 * @param userid idlockout 1解锁0解锁
	 * @return 受影响的行数
	 */
	@Transactional
	@Modifying
	@Query(value="UPDATE p_usertb SET u_islockout=:islockout WHERE u_userid=:userid",nativeQuery=true)
	public int resetUserState(@Param(value="islockout")Integer islockout,@Param(value="userid")Integer userid);

	/**
	 * 更新某用户信息
	 * @param email 有效的邮箱
	 * @param phone 手机号
	 * @param userid 用户编号
	 * @return 受影响的行数
	 */
	@Transactional
	@Modifying
	@Query(value="UPDATE p_usertb SET u_protectemail=:email,u_protectmtel=:phone WHERE u_userid=:userid",nativeQuery=true)
	public int updateUser(@Param(value="email")String email,@Param(value="phone")String phone,@Param(value="userid")Integer userid);
	
	/**
	 * 根据用户编号查询对应的角色编号
	 * @param uid 用户编号
	 * @return  角色编号集合
	 */
	@Query(value="SELECT r_roleid FROM user_role WHERE u_userid=:uid",nativeQuery=true)
	public List<Integer> selectRoleidsByUid(@Param(value="uid")Integer uid);
	/**
	 * 查询出角色下的[根]模块
	 * @param roleId 角色编号集合
	 * @param parentid 父亲模块编号
	 * @return
	 */
	@Query(value="SELECT m_modulename, m_moduleid,m_modulepath,m_moduleweight,parent_id FROM p_moduletb WHERE m_moduleid IN(SELECT m_moduleid FROM role_model WHERE r_roleid IN(:roleids)) AND parent_id=:parentid ORDER BY m_moduleweight desc",nativeQuery=true)
	public List<Modules> selectModuleByRoleids(@Param("roleids")List<Integer> roleids,@Param("parentid")Integer parentid);
	/**
	 * 关键字抽象方法
	 *根据用户编号查询出该用户
	 * @param userid 用户编号
	 * @return 对相应用户对象
	 */
	public Users findByUserid(Integer userid);
	
	/**
	 * 设置完角色之后，更新用户信息
	 * @param userId
	 * @param userName
	 * @param userPassword
	 * @param usrEmail
	 * @param userTelphone
	 * @return
	 */
	@Transactional
	@Query(value="UPDATE p_usertb SET u_loginname=?2,u_password=?3,u_protectemail=?4,u_protectmtel=?5 WHERE u_userid=?1",nativeQuery=true)
	@Modifying
	public Integer updUser(Integer userid,String loginname,String password,String protectemail,String protectmtel);
	
}
