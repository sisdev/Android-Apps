package com.assusoft.eFairEmall.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class FacilityInformation implements Parcelable{
	
	int facilityId,locationId,status;
    String locationName,xLoc,yLoc,creationDate,updatedBy,type;

    public FacilityInformation() {
    }

    public FacilityInformation(int facilityId, int locationId, String locationName, String xLoc, String yLoc, String creationDate, String updatedBy) {
        this.facilityId = facilityId;
        this.locationId = locationId;
        this.locationName = locationName;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.creationDate = creationDate;
        this.updatedBy = updatedBy;
    }
 
    
    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getxLoc() {
        return xLoc;
    }

    public void setxLoc(String xLoc) {
        this.xLoc = xLoc;
    }

    public String getyLoc() {
        return yLoc;
    }

    public void setyLoc(String yLoc) {
        this.yLoc = yLoc;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}    

}
