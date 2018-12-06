package com.zq.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="p_usertb")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="u_userid",columnDefinition="int unsigned NOT NULL comment '备注:用户编号自动增长主键'  ")
	private Integer userid;
	@Column(name="u_loginname",columnDefinition="varchar(20) not null comment '备注:用户登录名,登录名唯一,不能修改，长度20' ",unique=true,length=20,updatable=false)
	private String loginname;
	@Column(name="u_password",columnDefinition="varchar(100) not null comment '备注:用户密码,长度50' ",length=50)
	private String password;
	@Column(name="u_islockout",columnDefinition="int default 0 NOT NULL comment '备注:是否锁定，默认否，1：是 0：否'  ",insertable=false)
	private Integer islockout;
	
	@Column(name="u_lastlogintime",columnDefinition="Date not null comment '备注:最后一次登陆时间'  ")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastlogintime;
	@OrderBy
	@Column(name="u_createtime",columnDefinition="Timestamp NOT NULL comment '备注:创建时间'  ",nullable=false,updatable=false,insertable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp createtime;
	@Column(name="psdwrongtime",columnDefinition="int default 0 NULL comment '备注:密码错误次数'  ")
	private Integer psdwrongtime;
	
	@Column(name="u_locktime",columnDefinition="Date null comment '备注:锁定时间'  ")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date locktime;
	@Column(name="u_protectemail",columnDefinition="varchar(20) null comment '备注:密保邮箱' ")	
	private String protectemail;
	@Column(name="u_protectmtel",columnDefinition="varchar(30) null comment '备注:密保手机号' ")	
	private String protectmtel;
	
	
	
	@Column(name="u_exit1",columnDefinition="int unsigned NULL comment '备注:预留字段int' ")
	private Integer exit1;
	@Column(name="u_exit2",columnDefinition="varchar(50) null comment '备注:预留字段varchar(50)' ",length=50)
	private String exit2;
	
	//多对多实现
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER)
	@Cascade(value={CascadeType.SAVE_UPDATE})//级联
	@JoinTable(name="user_role",//指定中间表
			joinColumns={@JoinColumn(name="u_userid")},//user_id外键列，引用用户编号,（joinColumns）u_userid当前类的主键
			inverseJoinColumns= {@JoinColumn(name="r_roleid")}//role_id外键列，引用角色编号，（inverseJoinColumns）r_roleid与本类关联的类的主键
			)
	@NotFound(action=NotFoundAction.IGNORE)
	private Set<Roles> roles=new HashSet<Roles>();

	public Integer getUserid() {
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

	public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Integer getPsdwrongtime() {
		return psdwrongtime;
	}

	public void setPsdwrongtime(Integer psdwrongtime) {
		this.psdwrongtime = psdwrongtime;
	}

	public Date getLocktime() {
		return locktime;
	}

	public void setLocktime(Date locktime) {
		this.locktime = locktime;
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

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public Users(Integer userid, String loginname, String password, Integer islockout, Date lastlogintime,
			Timestamp createtime, Integer psdwrongtime, Date locktime, String protectemail, String protectmtel,
			Set<Roles> roles) {
		super();
		this.userid = userid;
		this.loginname = loginname;
		this.password = password;
		this.islockout = islockout;
		this.lastlogintime = lastlogintime;
		this.createtime = createtime;
		this.psdwrongtime = psdwrongtime;
		this.locktime = locktime;
		this.protectemail = protectemail;
		this.protectmtel = protectmtel;
		this.roles = roles;
	}

	public Users() {
		super();
	}

	@Override
	public String toString() {
		return "Users [userid=" + userid + ", loginname=" + loginname + ", password=" + password + ", islockout="
				+ islockout + ", lastlogintime=" + lastlogintime + ", createtime=" + createtime + ", psdwrongtime="
				+ psdwrongtime + ", locktime=" + locktime + ", protectemail=" + protectemail + ", protectmtel="
				+ protectmtel + ", exit1=" + exit1 + ", exit2=" + exit2 +  "]";
	}
	
	
}
