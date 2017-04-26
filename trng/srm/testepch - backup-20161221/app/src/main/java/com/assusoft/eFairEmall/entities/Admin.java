package com.assusoft.eFairEmall.entities;

public class Admin {
	
	 String adminName,employeeId,contactNo,creationDate,updatedBy;
	 int adminId,status;
	 	public Admin(){
	 		super();
	 	}
	    public Admin(String adminName, String employeeId, String contactNo,String creationDate, String updatedBy) {
	        this.adminName = adminName;
	        this.employeeId = employeeId;
	        this.contactNo = contactNo;
	        this.creationDate = creationDate;
	        this.updatedBy = updatedBy;
	    }
	    
	    public int getAdminId(){
	    	return this.adminId;
	    }
	    public void setAdminId(int adminId){
	    	this.adminId=adminId;
	    }

	    public String getAdminName() {
	        return adminName;
	    }

	    public void setAdminName(String adminName) {
	        this.adminName = adminName;
	    }

	    public String getEmployeeId() {
	        return employeeId;
	    }

	    public void setEmployeeId(String employeeId) {
	        this.employeeId = employeeId;
	    }

	    public String getContactNo() {
	        return contactNo;
	    }

	    public void setContactNo(String contactNo) {
	        this.contactNo = contactNo;
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
