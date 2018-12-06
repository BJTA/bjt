package com.zq.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zq.entity.Modules;

public interface ModuleRepository extends JpaRepository<Modules, Integer>,JpaSpecificationExecutor<Modules>{
	
	/**
	 * 根据登录名获取登录者所管理的模块
	 * @param loginName 登录名
	 * @return 所有模块
	 */
	@Query(value="SELECT * FROM p_moduletb WHERE  m_moduleid IN(SELECT m_moduleid FROM role_model WHERE r_roleid IN (SELECT r_roleid FROM user_role WHERE u_userid IN(SELECT u_userid FROM p_usertb WHERE u_loginname=:loginName)))",nativeQuery=true)
	public List<Modules> queryModuleByloginName(@Param(value="loginName")String loginName);
	
	
	
	
	/**
	 * 添加新模块
	 * @param modulename 模块名称
	 * @param modulepath 模块路径
	 * @param moduleweight 权重
	 * @param parent_id 所属模块的编号
	 * @return 受影响的行数
	 */
	@Transactional
	@Modifying
	@Query(value="INSERT INTO p_moduletb(m_modulename,m_modulepath,m_moduleweight,parent_id)VALUES(:modulename,:modulepath,:moduleweight,:parent_id)",nativeQuery=true)
	public int addModule(@Param(value ="modulename")String modulename,@Param(value ="modulepath")String modulepath,@Param(value="moduleweight")Integer moduleweight,@Param(value="parent_id")Integer parent_id);

    /**
     * 修改模块信息
     * @param modulename 模块名字
     * @param modulepath 新路径
     * @param moduleweight 权重
     * @param moduleid 目标模块编号
     * @return 受影响的行数
     */
	@Transactional
	@Modifying
	@Query(value="update p_moduletb set m_modulename=:modulename,m_modulepath=:modulepath,m_moduleweight=:moduleweight where m_moduleid=:moduleid",nativeQuery=true)
    public int updateModule(@Param(value ="modulename")String modulename,@Param(value ="modulepath")String modulepath,@Param(value="moduleweight")Integer moduleweight,@Param(value="moduleid")Integer moduleid);
     
	/**
	 * 根据父模块编号查询他的子模块
	 * @param mid 父模块编号
	 * @return 模块集合
	 */
	@Query(value = "SELECT * FROM p_moduletb WHERE parent_id=:mid AND m_moduleid != 1 ", nativeQuery = true)
	public List<Modules> queryChildrenById(@Param(value="mid")Integer mid);
   

}
