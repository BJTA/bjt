package com.zq.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.zq.entity.Modules;
import com.zq.entity.UserQuery;
import com.zq.entity.Users;

public interface UserService {
	
	/**
	   * 添加新用户
	 * @param name 登录名
	 * @param pwd 用户输入加盐之后的密码 
	 * @return 受影响的行数
	 */
	public int addUser(String name,String pwd);
	
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
	public int updateUserLastloginTime(Integer userid);
	/**
               * 删除指定对象
     * @param user 目标对象
     */
    public void deleteUser(Users users);
    /**
	 * 更新某用户信息
	 * @param pwd 用户输入加盐之后的密码 
	 * @param email 有效的邮箱
	 * @param phone 手机号
	 * @param userid 用户编号
	 * @return 受影响的行数
	 */
    public int updateUser(String email,String phone,Integer userid);
    /**
	   * 重置密码
	 * @param userid 用户编号
	 * @param pwd 新密码
	 * @return 受影响的行数
	 */
    public int resetPwd(String pwd,Integer userid);
    /**
	   * 锁定解锁
	 * @param userid 用户编号
	 * @param userid idlockout 1解锁0解锁
	 * @return 受影响的行数
	 */
	public int resetUserState(Integer islockout,Integer userid);
    /**
	 * 多条件动态分页排序查询
	 * @param userQuery   查询条件封装的对象
	 * @param page 页码
	 * @param size  页面显示条数
	 * @return
	 */
	public Page<Users> queryByDynamicSQLPage(UserQuery userQuery,Integer page,Integer size);
	/**
	 * 根据登录名获取登录者所管理的模块
	 * @param loginName 登录名
	 * @return 所有模块
	 */
	public List<Modules> queryModuleByloginName(String loginName);
	/**
	 * 根据用户编号查询对应的角色编号集合
	 * @param uid 用户编号
	 * @return  角色id集合
	 */
	public List<Integer> selectRoleidsByUid(Integer uid);
	/**
	 * 根据角色编号查询这些角色管理的模块
	 * @param roleid 角色id集合
	 * @return 模块集合
	 */
	public List<Modules> queryModuleByroleid(List<Integer> roleid);
	
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
	public Integer updUser(Integer userid,String loginname,String password,String protectemail,String protectmtel);
}
