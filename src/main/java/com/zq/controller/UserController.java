package com.zq.controller;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zq.entity.Modules;
import com.zq.entity.Roles;
import com.zq.entity.UserQuery;
import com.zq.entity.Users;
import com.zq.service.RoleService;
import com.zq.service.UserService;
import com.zq.utils.PasswordEncoder;
import com.zq.utils.Result;


@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	/**
	    * 添加一个新用户
	 * http://localhost:8090/user/adduser?loginname=admin
	 * 
	 * @param users 目标对象
	 * @return json格式｛"success":true/false,"message":添加成功/失败｝、json格式｛"remark":提示消息｝
	 */
	@RequestMapping("/adduser")
	public Object addUser(Users users) {
		PasswordEncoder yanzhi = new PasswordEncoder(users.getLoginname(), "Md5");
		String pwd = yanzhi.encode("123456", 5);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~根据用户名获取盐值~~~~~~~~~~参照" + users.getLoginname() + "~~~~~~~~~~~~~~盐值" + pwd);
		users.setPassword(pwd);
		try {
			if (userService.addUser(users.getLoginname(), pwd)>0)
				return Result.toMap(1, "添加");
			else
				return Result.toMap(0, "添加");
		} catch (Exception e) {
			// TODO: handle exception
			return Result.toRemark("登录名已存在");
		}	
		
	}
	/**
	 * 用户登录
	 * http://localhost:8090/user/userlogin?loginname=admin&password=123456
	 * @param users 登录对象
	 * @param response
	 * @return
	 */
	@RequestMapping("/userlogin")
	public Object UserLogin(Users users,HttpServletResponse response) {
		// 将用户输入的密码加密
		PasswordEncoder yanzhi = new PasswordEncoder(users.getLoginname(), "Md5");
		String pwd = yanzhi.encode(users.getPassword(), 5);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~根据用户名获取盐值~~~~~~~~~~参照" + users.getLoginname() + "~~~~~~~~~~~~~~盐值" + pwd);
		users.setPassword(pwd);
		Users user2=userService.findByLoginnameAndPassword(users.getLoginname(), pwd) ;
		if(user2 == null) 
			return Result.toRemark("用户不存在，请检查登录名和密码");
		
		else if(user2.getIslockout()==1) 
			return Result.toRemark("用户已被锁定");
		
			//更新登录时间
			userService.updateUserLastloginTime(user2.getUserid());
			
			//获取登录人的信息,并存到Cookie中
			Cookie userCookie=new Cookie("loginName", user2.getLoginname());//存登录名
			//List<Modules> modulelist=userService.queryModuleByloginName(user2.getLoginname());
			
			Cookie userCookie1=new Cookie("loginId",user2.getUserid()+"");//存登录名queryModuleByloginName
			userCookie.setMaxAge(1*24*60*60);//在cookie中存一天
			userCookie.setPath("/");
			response.addCookie(userCookie);
			response.addCookie(userCookie1);
			
			return Result.toMap(1, "登录");
	}
	/**
	 * 登陆之后查询自己管理模块
	 * http://localhost:8090/user/queryOwnerAllModule
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryOwnerAllModule")
	public Object queryOwnerAllModule(HttpServletRequest request) {
		Cookie[] cookies=request.getCookies();
		String loginname="";
		for (Cookie cookie : cookies) {
			if("loginName".equals(cookie.getName())) {
				String loginName=cookie.getValue();
				System.out.println("loginName"+loginName);
				loginname=loginName.split(",")[0];
				System.out.println("登录名"+loginname);
				
			}
		}
		List<Modules> modulelist=new ArrayList<Modules>();
		try {
			modulelist = userService.queryModuleByloginName(loginname);
			System.out.println("modulelist:"+modulelist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String[] toStringlist=modulelist.toArray(new String([modulelist.size()]);
		/*for (int i=1;i<modulelist.size();i++) {
			modulelist+=modulelist.substring
		}*/
		
		return modulelist;
	}
	
	
	@RequestMapping("/queryMyModule")
	@ResponseBody
	public List<Modules> queryMyModule(HttpServletRequest request) {
		
		Cookie[] cookies=request.getCookies();
		String loginId="";
		for (Cookie cookie : cookies) {
			if("loginId".equals(cookie.getName())) {
				 loginId=cookie.getValue();
				System.out.println("loginId"+loginId);
				loginId=loginId.split(",")[0];
				System.out.println("登录人编号"+loginId);
				
			}
		}
		// 现根据用户编号查询出该用户所拥有的角色编号
		List<Integer> roleids = userService.selectRoleidsByUid(Integer.parseInt(loginId));
		
		
		
		// 声明创建模块集合，用于返回给客户端
		List<Modules> modulelist = new ArrayList<Modules>();
		if (roleids.size() != 0) {// 如果登录的用户有角色
			// 查询出角色所管理的模块信息
			modulelist = userService.queryModuleByroleid(roleids);

		}
		System.out.println("自己管理的模块呀====="+modulelist);
		return modulelist;
	}
	
	
	
	
	
	/**
	   * 删除指定的用户
	   * http://localhost:8090/user/deleteuserByid?userid=1
	 * @param user 目标用户
	 * @return json格式
	 *                            ｛"success":true/false,"message":删除成功/失败
	 */
	@RequestMapping("/deleteuserByid")
	public Object deleteUser(Users user) {
		try {
			userService.deleteUser(user);
			 return Result.toMap(1, "删除");
		} catch (Exception e) {
			return Result.toRemark("用户属于在职状态");
		}
	}
	/**
	   * 修改指定的用户信息(页面用户名要为只读状态)
	   * http://localhost:8090/user/updateuser?userid=2&protectemail=186365956@163.com&protectmtel=13256526358
	 * @param user 目标用户（用户编号，email，phone）
	 * @return json格式
	 *    ｛"success":true/false,"message":信息修改成功/失败
	 */
	@RequestMapping("/updateuser")
	public Object updateUser(Users user,HttpServletRequest request) {
		
		/*//取cookie中的值
		Cookie[] cookies=request.getCookies();
		String loginname="";
		for (Cookie cookie : cookies) {
			if("loginName".equals(cookie.getName())) {
				String loginName=cookie.getValue();
				System.out.println("loginName"+loginName);
				loginname=loginName.split(",")[0];
				System.out.println("登录名"+loginName);
				
			}
		}
		// 将用户输入的密码加密
		PasswordEncoder yanzhi = new PasswordEncoder(loginname, "Md5");
		String pwd = yanzhi.encode(user.getPassword(), 5);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~根据用户名获取盐值~~~~~~~~~~参照" + user.getLoginname() + "~~~~~~~~~~~~~~盐值" + pwd);
		user.setPassword(pwd);*/
		//调用修改方法
		int n = userService.updateUser(user.getProtectemail(), user.getProtectmtel(), user.getUserid());
		
		if (n > 0)
			return Result.toMap(1, "修改");
		else
			return Result.toMap(0, "修改");
	}
	/**
	 * 重置密码
	 * http://localhost:8090/user/resetPwd?uname=admin&uid=1
	 * @param uname 用户名
	 * @param uid 编号
	 * @return ｛"success":true/false,"message":重置修改成功/失败}
	 */
	@RequestMapping("/resetPwd")
	public Object resetPwd(String uname,Integer uid,HttpServletRequest request) {
		PasswordEncoder yanzhi = new PasswordEncoder(uname, "Md5");
		String pwd = yanzhi.encode("123456", 5);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~根据用户名获取盐值~~~~~~~~~~参照" + uname + "~~~~~~~~~~~~~~盐值" + pwd);
		
		Cookie[] cookies=request.getCookies();
		String loginId="";
		for (Cookie cookie : cookies) {
			if("loginId".equals(cookie.getName())) {
				 loginId=cookie.getValue();
				System.out.println("loginId"+loginId);
				loginId=loginId.split(",")[0];
				System.out.println("登录人编号"+loginId);
				
			}
		}
		int n=userService.resetPwd(pwd, uid);
		if (n > 0)
			return Result.toMap(1, loginId);
		else {
			return Result.toMap(0, "重置");
			
		}
	}
	
	
	@RequestMapping("/updateUserState")
	public Object UpdateUserState(Integer islockout,Integer userid,HttpServletRequest request) {
		Cookie[] cookies=request.getCookies();
		String loginId="";
		for (Cookie cookie : cookies) {
			if("loginId".equals(cookie.getName())) {
				 loginId=cookie.getValue();
				System.out.println("loginId"+loginId);
				loginId=loginId.split(",")[0];
				System.out.println("登录人编号"+loginId);
				
			}
		}
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa"+loginId.equals(userid+""));
		if(loginId.equals(userid+"")) {
			return Result.toMap(0, "不能操作自己的状态,操作");	
		}else {
			
		int n=userService.resetUserState(islockout, userid);
		if (n > 0)
			return Result.toMap(1, "锁定");
		else {
			return Result.toMap(0, "锁定");
			
		}
		
		}
		
	}
	/**
	 * 多条件分页排序查询
	 * http://localhost:8090/user/selectUser?loginname=adm&page=1&size=10
	 * @param userQuery 查询条件分装的对象
	 * @param page 页码
	 * @param size 每个页面要显示的条数
	 * @return
	 */
	@RequestMapping("/selectUser")
	public Object selectUserBywhere(UserQuery userQuery,int page,int size) { 
		Map<String,Object> map=new HashMap<String,Object>();
		Page<Users> userlist=userService.queryByDynamicSQLPage(userQuery, page, size);
		List<Users> uList=userlist.getContent();
		Long count=userlist.getTotalElements();
		map.put("rows", uList);
		map.put("total",count);
		System.out.println(uList);
		return map;
	}
	/**
	 * 给用户设置角色 
	 * @param userid 用户编号
	 * @param roleid 角色编号
	 * @param cz add要添加某个角色/remove要删除某角色
	 * @return
	 */
	@RequestMapping("/czUser")
	public Object addRoleUserByRidAndUid(Integer userid,Integer roleid,String cz) {				
		
		Users user=userService.findByUserid(userid);//靶子
		
	    Set<Roles> rSet=user.getRoles();//操作之前该用户拥有的角色集合
	   
	    Roles role=roleService.findByRoleid(roleid);//弓箭
	    
	    
	   if("add".equals(cz)) {//射箭
		rSet.add(role);			
		user.setRoles(rSet);
		
	   }else if("remove".equals(cz)){//拔箭
		   rSet.remove(role);
		   user.setRoles(rSet);
	   }
	  
	   if(userService.updUser(userid, user.getLoginname(), user.getPassword(), user.getProtectemail(), user.getProtectmtel())>0){
		return Result.toMap(1, "设置");
	   } else {
		   return Result.toMap(0, "设置");
	   }
	  
	
	};
}
