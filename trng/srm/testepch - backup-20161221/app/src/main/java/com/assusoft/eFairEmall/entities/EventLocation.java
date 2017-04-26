/**
 * @author umesh singh kushwaha
 */
package com.assusoft.eFairEmall.entities;

public class EventLocation {
	
	   private String locationName,description,xLocation,yLocation;
	   private String creationDate,updatedBy;
	   int eventLocationId,locationId,event_loc_id,status;
	   
	   public EventLocation(){
		   super();
	   }

	    public EventLocation(int eventLocationId,int locationId,String locationName, String description, String xLocation, String yLocation, String creationDate, String updatedBy, int event_loc_id) {
	        this.eventLocationId=eventLocationId;
	        this.locationId = locationId;
	    	this.locationName = locationName;
	        this.description = description;
	        this.xLocation = xLocation;
	        this.yLocation = yLocation;
	        this.creationDate = creationDate;
	        this.updatedBy = updatedBy;
	        this.event_loc_id=event_loc_id;
	    }

	    public String getLocationName() {
	        return locationName;
	    }

	    public void setLocationName(String locationName) {
	        this.locationName = locationName;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
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

	    public int getEventLocationId() {
	        return eventLocationId;
	    }

	    public void setEventLocationId(int eventLocationId) {
	        this.eventLocationId = eventLocationId;
	    }

	    public int getLocationId() {
	        return locationId;
	    }

	    public void setLocationId(int locationId) {
	        this.locationId = locationId;
	    }
	    public int getEvent_loc_id() {
	        return event_loc_id;
	    }

	    public void setEvent_loc_id(int event_loc_id) {
	        this.event_loc_id = event_loc_id;
	    }

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}


}
