package com.hg.dqsj.system.auth.entity;

/**
 * 操作权限
 * 
 */
public class Operate {
	private boolean search = false; // 查询
	private boolean view = false;   // 查看
	private boolean add = false;    // 新增
	private boolean update = false; // 修改
	private boolean delete = false; // 删除
	private boolean print = false;  // 打印
	private boolean imp = false;    // 导入
	private boolean exp = false;    // 导出
	private boolean audit = false;  // 审核

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public boolean isImp() {
		return imp;
	}

	public void setImp(boolean imp) {
		this.imp = imp;
	}

	public boolean isExp() {
		return exp;
	}

	public void setExp(boolean exp) {
		this.exp = exp;
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public boolean isAudit() {
		return audit;
	}

	public void setAudit(boolean audit) {
		this.audit = audit;
	}

}