package com.assusoft.eFairEmall.entities;


public class Company {
	private   String companyName,companyDescription,address1,address2,city,state,pinCode;
	private String country,website,email,contactNo,creationDate,updatedBy,panNumber,number_of_persons_accompanying;
	private int companyId,status; 
	 
	  public Company(int companyId,String companyName,String companyDescription,
			  String address1,String address2,String city,String state,String pinCode,String country,String website,
			  String email,String contactNo,String creationDate,String updatedBy,String panNumber) {
	        this.companyId = companyId;
	        this.companyName = companyName;
	        this.companyDescription = companyDescription;
	        this.address1 = address1;
	        this.address2 = address2;
	        this.city = city;
	        this.state = state;
	        this.pinCode = pinCode;
	        this.country = country;
	        this.website = website;
	        this.email = email;
	        this.contactNo = contactNo;
	        this.creationDate = creationDate;
	        this.updatedBy = updatedBy;
	        this.panNumber=panNumber;
	    }
	  //getter and setter methods
	   public int getCompanyId() {
	        return companyId;
	    }

	    public void setCompanyId(int companyId) {
	        this.companyId = companyId;
	    }

	    public String getCompanyName() {
	        return companyName;
	    }

	    public void setCompanyName(String companyName) {
	        this.companyName = companyName;
	    }
	    public String getCompanyDescription() {
	        return companyDescription;
	    }

	    public void setCompanyDescription(String companyDescription) {
	        this.companyDescription = companyDescription;
	    }
	    public String getAddress1() {
	        return address1;
	    }

	    public void setAddress1(String address1) {
	        this.address1 = address1;
	    }
	    public String getAddress2() {
	        return address2;
	    }

	    public void setAddress2(String address2) {
	        this.address2 = address2;
	    }
	    public String getCity() {
	        return city;
	    }

	    public void setCity(String city) {
	        this.city = city;
	    }
	    public String getState() {
	        return state;
	    }

	    public void setState(String state) {
	        this.state = state;
	    }
	    public String getCountry() {
	        return country;
	    }

	    public void setCountry(String country) {
	        this.country = country;
	    }
	    public String getPinCode() {
	        return pinCode;
	    }

	    public void setPinCode(String pinCode) {
	        this.pinCode = pinCode;
	    }
	    public String getWebSite() {
	        return website;
	    }

	    public void setWebsite(String website) {
	        this.website = website;
	    }
	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }
	    public String getContactNo() {
	        return this.contactNo;
	    }

	    public void setContactNo(String contactNo) {
	        this.contactNo =contactNo;
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
	    public String getPanNumber() {
	        return panNumber;
	    }

	    public void setPanNumber(String panNumber) {
	        this.panNumber = panNumber;
	    }
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getNumber_of_persons_accompanying() {
			return number_of_persons_accompanying;
		}
		public void setNumber_of_persons_accompanying(
				String number_of_persons_accompanying) {
			this.number_of_persons_accompanying = number_of_persons_accompanying;
		}
		public String getWebsite() {
			return website;
		}
}
