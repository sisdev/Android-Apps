package com.assusoft.eFairEmall.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Exhibitors {
	private   String exhibitorName,exhibitorDescription,address1,address2,city,state,pinCode;
	private String country,nationality,website,logoPath,contactPerson,email,contactNo,mobileNo,fax,creationDate,updatedBy;
	private int exhibitorId,pkId,companyId,category, status;
	private String type;
	private String refNo;
	 
	//constructors 
	  public  Exhibitors(){
	    	super();
	    }
	  public Exhibitors(Parcel in) {
			readFromParcel(in);
		}
	  public Exhibitors(int pkId,int exhibitorId,String contactPerson,
			  String email,String contactNo,String mobileNo,String fax,String creationDate,String updatedBy,int category,int companyId) {
		    this.pkId = pkId;
	        this.exhibitorId = exhibitorId;
	        /*this.exhibitorName = exhibitorName;
	        this.exhibitorDescription = exhibitorDescription;
	        this.address1 = address1;
	        this.address2 = address2;
	        this.city = city;
	        this.state = state;
	        this.pinCode = pinCode;
	        this.country = country;
	        this.nationality = nationality;
	        this.website = website;
	        this.logoPath = logoPath;*/
	        this.contactPerson = contactPerson;
	        this.email = email;
	        this.contactNo = contactNo;
	        this.mobileNo=mobileNo;
	        this.fax = fax;
	        this.creationDate = creationDate;
	        this.updatedBy = updatedBy;
	        this.category=category;
	        this.companyId = companyId;
	       
	    }
	  
	  public String getType(){
		  return this.type;
	  }
	  
	  public void setType(String type){
		  this.type=type;
	  }
	  //getter and setter methods
	   public int getExhibitorId() {
	        return exhibitorId;
	    }

	    public void setExhibitorId(int exhibitorId) {
	        this.exhibitorId = exhibitorId;
	    }

	    public String getExhibitorName() {
	        return exhibitorName;
	    }

	    public void setExhibitorName(String exhibitorName) {
	        this.exhibitorName = exhibitorName;
	    }
	    public String getDescription() {
	        return exhibitorDescription;
	    }

	    public void setDescription(String description) {
	        this.exhibitorDescription = description;
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
	    public String getNationality() {
	        return nationality;
	    }

	    public void setNationality(String nationality) {
	        this.nationality = nationality;
	    }
	    public String getPinCode() {
	        return pinCode;
	    }

	    public void setPinCode(String pinCode) {
	        this.pinCode = pinCode;
	    }
	    public String getLogoPath() {
	        return this.logoPath;
	    }

	    public void setLogoPath(String logoPath) {
	        this.logoPath = logoPath;
	    }
	    public String getWebSite() {
	        return website;
	    }

	    public void setWebsite(String website) {
	        this.website = website;
	    }
	    public String getContactPerson() {
	        return contactPerson;
	    }

	    public void setContactPerson(String contactPerson) {
	        this.contactPerson= contactPerson;
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
	    public String getMobileNo() {
	        return mobileNo;
	    }

	    public void setMobileNo(String mobileNo) {
	        this.mobileNo = mobileNo;
	    }
	    public String getFax() {
	        return fax;
	    }

	    public void setFax(String fax) {
	        this.fax = fax;
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
	    public int getPkId() {
	        return pkId;
	    }

	    public void setPkId(int pkId) {
	        this.pkId = pkId;
	    }
	    public int getCategory() {
	        return category;
	    }

	    public void setCategory(int category) {
	        this.category = category;
	    }
	    public int getCompanyId() {
	        return companyId;
	    }

	    public void setCompanyId(int companyId) {
	        this.companyId = companyId;
	    }
	    public String getRefNo() {
			return refNo;
		}
		public void setRefNo(String refNo) {
			this.refNo = refNo;
		}
		
	  public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		//this method is used to read the serialized data
	    private void readFromParcel(Parcel in) {
	    	this.exhibitorId = in.readInt();
	        this.exhibitorName =in.readString();
	        /*this.password = in.readString();
	        this.secretQuestion =in.readString();
	        this.secretAnswer = in.readString();
	        this.status =in.readString();
	        this.lastLoggedIn = in.readString();
	        this.creationDate = in.readString();
	        this.updatedBy = in.readString();*/
		}
	    
	    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
			public Users createFromParcel(Parcel in) {
				return new Users(in);
			}

			public Users[] newArray(int size) {
				return new Users[size];
			}
		};
}
