package com.assusoft.eFairEmall.entities;

public class Products {
	String productName,productType,productBrand,description;
	String creationDate,updatedBy;
    int productId,parentId,status;
    
	public Products(){
		super();
	}
    public Products(int productId, String productName, String productType, String productBrand,String description,
    		String creationDate, String updatedBy) {
    	this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productBrand = productBrand;
        this.description = description;
        this.creationDate = creationDate;
        this.updatedBy = updatedBy;
    }
    
    public int getParentId(){
    	return this.parentId;
    }
    public void setParentId(int parentId){
    	this.parentId=parentId;
    }

    public int getProductId() {
        return productId;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    
    public void setProductId(int productId){
    	this.productId =productId;
    }
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
