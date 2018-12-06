package com.zq.entity;

public class RoleQuery {
	private Integer roleid;
	private String rolename;
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
	public RoleQuery(Integer roleid, String rolename) {
		super();
		this.roleid = roleid;
		this.rolename = rolename;
	}
	@Override
	public String toString() {
		return "RoleQuery [roleid=" + roleid + ", rolename=" + rolename + "]";
	}
	public RoleQuery() {
		super();
	}

	

	
}
