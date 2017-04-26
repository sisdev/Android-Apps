package com.assusoft.eFairEmall.entities;

public class Roles {
	
    String roleName,roleDetails,creationDate,updatedBy;
    int roleId,status;
    public Roles(){
    	super();
    }
    public Roles(String roleName, String roleDetails, String creationDate,String updatedBy) {
        this.roleName = roleName;
        this.roleDetails = roleDetails;
        this.creationDate = creationDate;
        this.updatedBy=updatedBy;
    }

   
    public int getRoleId(){
    	return this.roleId;
    }
    public void setRoleId(int roleId){
    	this.roleId=roleId;
    }
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDetails() {
        return roleDetails;
    }

    public void setRoleDetails(String roleDetails) {
        this.roleDetails = roleDetails;
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
