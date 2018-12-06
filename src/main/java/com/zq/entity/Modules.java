package com.zq.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Table(name = "p_moduletb")
public class Modules {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="m_moduleid",columnDefinition="int unsigned NOT NULL comment '备注:模块编号自动增长主键' ")
	@JsonProperty("id")
	private Integer moduleid;

	@Column(name="m_modulename",columnDefinition="varchar(20) NOT NULL comment '备注:模块名字'  ",length=20)	
	@JsonProperty("text")
	private String modulename;
/*	@Column(name="m_moduleparentid",columnDefinition="NOT NULL comment '备注:父模块编号，自引用'  ")
	private Integer moduleparentid;*/
	@Column(name="m_modulepath",columnDefinition="varchar(100) NULL comment '备注:模块路径'  ")	
	private String modulepath;
	@Column(name="m_moduleweight",columnDefinition="int unsigned NOT NULL comment '备注:模块权重'  ")
	@OrderBy
	private Integer moduleweight;
	@Column(name = "m_exit1", columnDefinition = "int unsigned NULL comment '备注:预留字段int' ")
	private Integer exit1;
	@Column(name = "m_exit2", columnDefinition = "varchar(100) NULL comment '备注:预留字段varchar(50)' ", length = 50)
	private String exit2;
	
	//一对多关联实现（自引用）
	@JsonIgnore
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
	@JoinColumn(name="parent_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private Set<Modules> children=new HashSet<Modules>();
    //多对一
	/*@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="parent_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private Modules parent;*/
	
	
	//多对多关联关系
	/*@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(name="models")
	private Set<Roles> Roles=new HashSet<Roles>();*/
	@Transient
	public Boolean checked;
	
	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Integer getModuleid() {
		return moduleid;
	}

	public void setModuleid(Integer moduleid) {
		this.moduleid = moduleid;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getModulepath() {
		return modulepath;
	}

	public void setModulepath(String modulepath) {
		this.modulepath = modulepath;
	}

	public Integer getModuleweight() {
		return moduleweight;
	}

	public void setModuleweight(Integer moduleweight) {
		this.moduleweight = moduleweight;
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

	public Set<Modules> getChildren() {
		return children;
	}

	public void setChildren(Set<Modules> children) {
		this.children = children;
	}

	public Modules(Integer moduleid, String modulename, String modulepath, Integer moduleweight, Set<Modules> children) {
		super();
		this.moduleid = moduleid;
		this.modulename = modulename;
		this.modulepath = modulepath;
		this.moduleweight = moduleweight;
		this.children = children;
	}

	public Modules(Integer moduleid, String modulename, String modulepath, Integer moduleweight, Set<Modules> children,
			Boolean checked) {
		super();
		this.moduleid = moduleid;
		this.modulename = modulename;
		this.modulepath = modulepath;
		this.moduleweight = moduleweight;
		this.children = children;
		this.checked = checked;
	}

	public Modules() {
		super();
	}

	@Override
	public String toString() {
		return "Modules [moduleid=" + moduleid + ", modulename=" + modulename + ", modulepath=" + modulepath
				+ ", moduleweight=" + moduleweight + ", exit1=" + exit1 + ", exit2=" + exit2 + ", children=" + children
				+ ", checked=" + checked + "]";
	}

	
	
	
}
