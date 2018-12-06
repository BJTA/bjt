package com.zq.entity;

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
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "p_roletb")
public class Roles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="r_roleid",columnDefinition="int unsigned NOT NULL comment '备注:角色编号自动增长主键'  ")
	private Integer roleid;
	@Column(name="r_rolename",columnDefinition="varchar(20) NOT NULL comment '备注:角色名,角色为名唯一，长度20' ",length=20,unique=true)
	private String rolename;
	@Column(name="r_exit1",columnDefinition="int unsigned NULL comment '备注:预留字段int' ")
	private Integer exit1;
	@Column(name="r_exit2",columnDefinition="varchar(50) NULL comment '备注:预留字段varchar(50)' ",length=50)
	private String exit2;
	//多对多关系实现
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER)
	@Cascade(value={CascadeType.SAVE_UPDATE})
	@JoinTable(name="role_model",//中间关系表
	  joinColumns= {@JoinColumn(name="r_roleid")},
	  inverseJoinColumns= {@JoinColumn(name="m_moduleid")}
			)
	@NotFound(action=NotFoundAction.IGNORE)
	private Set<Modules> models=new HashSet<Modules>();
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public Integer getExit1() {
		return exit1;
	}
	public void setExit1(Integer exit1) {
		this.exit1 = exit1;
	}
	public String getExit2() {
		return exit2;
	}
	public void setExit2(String exit2) {
		this.exit2 = exit2;
	}
	public Set<Modules> getModels() {
		return models;
	}
	public void setModels(Set<Modules> models) {
		this.models = models;
	}
	public Roles(Integer roleid, String rolename, Set<Modules> models) {
		super();
		this.roleid = roleid;
		this.rolename = rolename;
		this.models = models;
	}
	@Override
	public String toString() {
		return "Roles [roleid=" + roleid + ", rolename=" + rolename + ", exit1=" + exit1 + ", exit2=" + exit2
				+ "]";
	}
	public Roles(Integer roleid) {
		super();
		this.roleid = roleid;
	}
	public Roles() {
		super();
	}
	
}
