package com.zq.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class UserQuery {
	private Integer userid;//用户编号
	private String loginname;//登录名
	private String password;//密码
	
	private Integer islockout;//是否锁定1是0否
	
	//最后一次登陆时间区间
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME,pattern="yyyy-MM-dd HH:mm:ss")
	private Date startLastlogintime;
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME,pattern="yyyy-MM-dd HH:mm:ss")
	private Date endLastlogintime;
	
	//创建时间区间
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME,pattern="yyyy-MM-dd HH:mm:ss")
	private Date startCreatetime;
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME,pattern="yyyy-MM-dd HH:mm:ss")
	private Date endCreatetime;
	
	
	private Integer psdwrongtime;//密码错误次数
	
    //private Date locktime;
	//锁定时间区间
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME,pattern="yyyy-MM-dd HH:mm:ss")
	private Date startLocktime;
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME,pattern="yyyy-MM-dd HH:mm:ss")
	private Date endLocktime;
	
	private String protectemail;//密保邮箱
	private String protectmtel;//密保手机号
	
	/*private int page=1;//首先显示第几页
	private int startindex=0;//起始索引
	private int rows=10;//每页显示条数
*/	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getIslockout() {
		return islockout;
	}
	public void setIslockout(Integer islockout) {
		this.islockout = islockout;
	}
	public Date getStartLastlogintime() {
		return startLastlogintime;
	}
	public void setStartLastlogintime(Date startLastlogintime) {
		this.startLastlogintime = startLastlogintime;
	}
	public Date getEndLastlogintime() {
		return endLastlogintime;
	}
	public void setEndLastlogintime(Date endLastlogintime) {
		this.endLastlogintime = endLastlogintime;
	}
	public Date getStartCreatetime() {
		return startCreatetime;
	}
	public void setStartCreatetime(Date startCreatetime) {
		this.startCreatetime = startCreatetime;
	}
	public Date getEndCreatetime() {
		return endCreatetime;
	}
	public void setEndCreatetime(Date endCreatetime) {
		this.endCreatetime = endCreatetime;
	}
	public Integer getPsdwrongtime() {
		return psdwrongtime;
	}
	public void setPsdwrongtime(Integer psdwrongtime) {
		this.psdwrongtime = psdwrongtime;
	}
	
	public String getProtectemail() {
		return protectemail;
	}
	public void setProtectemail(String protectemail) {
		this.protectemail = protectemail;
	}
	public String getProtectmtel() {
		return protectmtel;
	}
	public void setProtectmtel(String protectmtel) {
		this.protectmtel = protectmtel;
	}
	/*public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getStartindex() {
		return (page-1)*rows;
	}
	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int pagesize) {
		this.rows = rows;
	}*/
	public Date getStartLocktime() {
		return startLocktime;
	}
	public void setStartLocktime(Date startLocktime) {
		this.startLocktime = startLocktime;
	}
	public Date getEndLocktime() {
		return endLocktime;
	}
	public void setEndLocktime(Date endLocktime) {
		this.endLocktime = endLocktime;
	}
	public UserQuery(Integer userid, String loginname, String password, Integer islockout, Date startLastlogintime,
			Date endLastlogintime, Date startCreatetime, Date endCreatetime, Integer psdwrongtime, Date startLocktime,
			Date endLocktime, String protectemail, String protectmtel) {
		super();
		this.userid = userid;
		this.loginname = loginname;
		this.password = password;
		this.islockout = islockout;
		this.startLastlogintime = startLastlogintime;
		this.endLastlogintime = endLastlogintime;
		this.startCreatetime = startCreatetime;
		this.endCreatetime = endCreatetime;
		this.psdwrongtime = psdwrongtime;
		this.startLocktime = startLocktime;
		this.endLocktime = endLocktime;
		this.protectemail = protectemail;
		this.protectmtel = protectmtel;
		/*this.page = page;
		this.startindex = startindex;
		this.rows = rows;*/
	}
	@Override
	public String toString() {
		return "UserQuery [userid=" + userid + ", loginname=" + loginname + ", password=" + password + ", islockout="
				+ islockout + ", startLastlogintime=" + startLastlogintime + ", endLastlogintime=" + endLastlogintime
				+ ", startCreatetime=" + startCreatetime + ", endCreatetime=" + endCreatetime + ", psdwrongtime="
				+ psdwrongtime + ", startLocktime=" + startLocktime + ", endLocktime=" + endLocktime + ", protectemail="
				+ protectemail + ", protectmtel=" + protectmtel +  "]";
	}
	public UserQuery() {
		super();
	}
	
	
    
}
