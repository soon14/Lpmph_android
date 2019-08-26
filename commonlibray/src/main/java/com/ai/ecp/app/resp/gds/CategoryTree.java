package com.ai.ecp.app.resp.gds;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.treeview.TreeNodeId;
import com.ailk.treeview.TreeNodeLabel;
import com.ailk.treeview.TreeNodePid;

public class CategoryTree extends AppBody{

	private static final long serialVersionUID = -7036373984413541896L;

	private int catgLevel;
	/**
	 * 是否目录.
	 */
	private Boolean isRoot;
	/**
	 *节点ID 
	 * */
	@TreeNodeId
	private String id;
	/**
	 * 父节点ID
	 * */
	@TreeNodePid
	private String pid;
	/** 
	 * 节点名称
	 * */
	@TreeNodeLabel
	private String name;

	public int getCatgLevel() {
		return catgLevel;
	}

	public void setCatgLevel(int catgLevel) {
		this.catgLevel = catgLevel;
	}

	public Boolean getRoot() {
		return isRoot;
	}

	public void setRoot(Boolean root) {
		isRoot = root;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

