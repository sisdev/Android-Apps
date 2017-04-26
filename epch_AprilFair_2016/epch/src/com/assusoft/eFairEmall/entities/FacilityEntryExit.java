package com.assusoft.eFairEmall.entities;

public class FacilityEntryExit {
	
	int id,facilityInfoId,status;
    String xLoc,yLoc,creationDate,updatedBy;

    public FacilityEntryExit() {
    }

    public FacilityEntryExit(int id, int facilityInfoId, String xLoc, String yLoc, String creationDate, String updatedBy) {
        this.id = id;
        this.facilityInfoId = facilityInfoId;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.creationDate = creationDate;
        this.updatedBy = updatedBy;
    }    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFacilityInfoId() {
        return facilityInfoId;
    }

    public void setFacilityInfoId(int facilityInfoId) {
        this.facilityInfoId = facilityInfoId;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}   

}
