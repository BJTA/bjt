package com.zq.entity;

import java.util.List;

public class ModuleQuery {
	private Integer moduleid;
	private String modulename;
	 private List<Modules> children;
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
	public List<Modules> getChildren() {
		return children;
	}
	public void setChildren(List<Modules> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return "ModuleQuery [moduleid=" + moduleid + ", modulename=" + modulename + ", children=" + children + "]";
	}
	public ModuleQuery(Integer moduleid, String modulename, List<Modules> children) {
		super();
		this.moduleid = moduleid;
		this.modulename = modulename;
		this.children = children;
	}
	public ModuleQuery() {
		super();
	}
	 
}
