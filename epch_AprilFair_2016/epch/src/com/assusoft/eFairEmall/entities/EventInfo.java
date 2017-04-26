/**
 * @author umesh singh kushwaha
 */

package com.assusoft.eFairEmall.entities;

public class EventInfo {
	
	private int eventId,eventLocationId,status;
    private String eventName,eventDate,startTime,endTime,description,creationDate,updatedBy,moderator,panelists;
    private String eventType;
    
    public EventInfo(){
    	super();
    }

    public EventInfo(int eventId,int eventLocationId, String eventName, String eventDate, String startTime, String endTime, String description, String creationDate, String updatedBy,String moderator,String panelists) {
        this.eventId=eventId;
    	this.eventLocationId = eventLocationId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.creationDate = creationDate;
        this.updatedBy = updatedBy;
        this.moderator=moderator;
        this.panelists=panelists;
    }
    
    public String getEventType(){
    	return this.eventType;
    }
    public void setEventType(String eventType){
    	this.eventType=eventType;
    }
    
    

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getEventLocationId() {
        return eventLocationId;
    }

    public void setEventLocationId(int eventLocationId) {
        this.eventLocationId = eventLocationId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public String getModerater() {
        return moderator;
    }

    public void setModerater(String moderater) {
        this.moderator = moderater;
    }
    public String getPanelLists() {
        return panelists;
    }

    public void setPanelLists(String panelLists) {
        this.panelists = panelLists;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    

}
