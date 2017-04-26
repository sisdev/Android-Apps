package com.assusoft.eFairEmall.entities;

public class Appointment {
	

	public  String date,startTime,endTime,creationDate,updatedBy,exhibitorName,eventName;
	public  int visitorId,exhibitorId,appointmentId,eventLocationId,eventId;
	public  int type,status;
	
        public Appointment(){
        	
        	super();
        }
	    public Appointment(int visitorId,int exhibitorId,String date, String startTime, String endTime, 
	    		int status, String creationDate, String updatedBy,String exhibitorName) {
	        this.visitorId = visitorId;
	        this.exhibitorId = exhibitorId;
	        this.date = date;
	        this.startTime = startTime;
	        this.endTime = endTime;
	        this.status = status;
	        this.creationDate = creationDate;
	        this.updatedBy = updatedBy;
	        this.exhibitorName=exhibitorName;
	    }
	    
	    public int getType(){
	    	return this.type;
	    }
	    public void setType(int i){
	    	this.type=i;
	    }
	    
	    public int geteventLocationId(){
	    	return this.eventLocationId;
	    }
	    
	    public void seteventLocationId(int eventId){
	    	this.eventLocationId=eventId;
	    }
	    
	    public void setEventId(int eventId){
	    	this.eventId=eventId;
	    }
	    
	    public int getEventId(){
	    	return this.eventId;
	    }

	    public int getVisitorId() {
	        return visitorId;
	    }

	    public void setVisitorId(int visitorId) {
	        this.visitorId = visitorId;
	    }
	    public int getEhxibitorId() {
	        return exhibitorId;
	    }

	    public void setExhibitorId(int exhibitorId) {
	        this.exhibitorId = exhibitorId;
	    }

	    public String getDate() {
	        return date;
	    }

	    public void setDate(String date) {
	        this.date = date;
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
	    public void setExhibitorName(String exhibitorName) {
	        this.exhibitorName = exhibitorName;
	    }
	    public String getExhibitorName() {
	       return this.exhibitorName ;
	    }
		public void setAppointmentId(int appointmentId) {
			// TODO Auto-generated method stub
			this.appointmentId=appointmentId;
		}
		public int getAppointmentId(){
			return this.appointmentId;
		}
		
		public void setEventName(String eventName){
			this.eventName=eventName;
		}
		
		public String getEventName(){
			return this.eventName;
		}
	 

}
