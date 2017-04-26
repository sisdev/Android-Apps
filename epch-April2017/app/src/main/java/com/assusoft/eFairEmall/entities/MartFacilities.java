package com.assusoft.eFairEmall.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class MartFacilities implements Parcelable{
	int facilityId, status;
	String facilityName, imgName, creationDate, updatedBy;
	
	public MartFacilities(){
		
	}
	
	public MartFacilities(int facilityId, String facilityName, String imgName, String creationDate, 
			String updatedBy, int status){
		this.facilityId 	=	facilityId;
		this.facilityName	=	facilityName;
		this.imgName		=	imgName;
		this.creationDate	=	creationDate;
		this.updatedBy		=	updatedBy;
		this.status			=	status;
	}
	
	public MartFacilities(int facilityId, String facilityName, String imgName){
		this.facilityId 	=	facilityId;
		this.facilityName	=	facilityName;
		this.imgName		=	imgName;
	}

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
