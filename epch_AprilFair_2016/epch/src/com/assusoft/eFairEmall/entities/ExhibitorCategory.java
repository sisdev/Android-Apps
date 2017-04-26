package com.assusoft.eFairEmall.entities;

public class ExhibitorCategory {
	int categoryId,status;
	String categoryName,creationDate,updatedBy;
	
	public ExhibitorCategory() {
	}
	
	public ExhibitorCategory(int categoryId, String categoryName, String creationDate, String updatedBy) {
	    this.categoryId = categoryId;
	    this.categoryName = categoryName;
	    this.creationDate = creationDate;
	    this.updatedBy = updatedBy;
	}
	
	public int getCategoryId() {
	    return categoryId;
	}
	
	public void setCategoryId(int categoryId) {
	    this.categoryId = categoryId;
	}
	
	public String getCategoryName() {
	    return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
	    this.categoryName = categoryName;
	}
	
	public String getCreationDate() {
	    return creationDate;
	}
	
	public void setCreationDate(String creationDate) {
	    this.creationDate = creationDate;
	}
	
	public String getUpdatedBy() {
	    return updatedBy;
	}
	
	public void setUpdatedBy(String updatedBy) {
	    this.updatedBy = updatedBy;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
