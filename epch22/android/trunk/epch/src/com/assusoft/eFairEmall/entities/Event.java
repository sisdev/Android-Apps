package com.assusoft.eFairEmall.entities;

public class Event {
	int eventLocationId,status;
	String eventLocationName,eventLocationDiscription,creationDate,updatedBy;
	public Event(int eventLocationId, String eventLocationName, String eventLocationDiscription,String creationDate, String updatedBy) {
        this.eventLocationId = eventLocationId;
        this.eventLocationName= eventLocationName;
        this.eventLocationDiscription = eventLocationDiscription;
        this.creationDate = creationDate;
        this.updatedBy = updatedBy;
    }
    
    

    public int getEventLocationId() {
        return eventLocationId;
    }

    public void setEventLocationId(int eventLocationId) {
        this.eventLocationId = eventLocationId;
    }

    public String getEventLocationName() {
        return eventLocationName;
    }

    public void setEventLocationName(String eventLocationName) {
        this.eventLocationName = eventLocationName;
    }

    public String getEventLocationDescription() {
        return eventLocationDiscription;
    }

    public void setEventLocationDescription(String eventLocationDiscription) {
        this.eventLocationDiscription = eventLocationDiscription;
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
