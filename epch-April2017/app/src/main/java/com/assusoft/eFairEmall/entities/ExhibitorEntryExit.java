package com.assusoft.eFairEmall.entities;

public class ExhibitorEntryExit {
	
	

	int exhibitorLocationId,status,id;
    String xLoc,yLoc,creationDate,updatedBy,type;

    public ExhibitorEntryExit() {
    }

    public ExhibitorEntryExit(int exhibitorLocationId, String xLoc, String yLoc, int status, String creationDate, String updatedBy) {

        this.exhibitorLocationId = exhibitorLocationId;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.status = status;
        this.creationDate = creationDate;
        this.updatedBy = updatedBy;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
    public int getExhibitorLocationId() {
        return exhibitorLocationId;
    }

    public void setExhibitorLocationId(int exhibitorLocationId) {
        this.exhibitorLocationId = exhibitorLocationId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
