package com.assusoft.eFairEmall.entities;

public class ExhibitorLocation {
	int exhibitorLocationId,locationId,exhibitorId,status;
    String standNo,xLocation,yLocation,creationDate,updatedBy;
    public ExhibitorLocation(){
        super();
    }
    public ExhibitorLocation( int locationId, int exhibitorId, String standNo, String xLocation, String yLocation, String creationDate, String updatedBy) {
        
        this.locationId = locationId;
        this.exhibitorId = exhibitorId;
        this.standNo = standNo;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.creationDate = creationDate;
        this.updatedBy = updatedBy;
    }

    public int getExhibitorLocationId() {
        return exhibitorLocationId;
    }

    public void setExhibitorLocationId(int exhibitorLocationId) {
        this.exhibitorLocationId = exhibitorLocationId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getExhibitorId() {
        return exhibitorId;
    }

    public void setExhibitorId(int exhibitorId) {
        this.exhibitorId = exhibitorId;
    }

    public String getStandNo() {
        return standNo;
    }

    public void setStandNo(String standNo) {
        this.standNo = standNo;
    }

    public String getxLocation() {
        return xLocation;
    }

    public void setxLocation(String xLocation) {
        this.xLocation = xLocation;
    }

    public String getyLocation() {
        return yLocation;
    }

    public void setyLocation(String yLocation) {
        this.yLocation = yLocation;
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
