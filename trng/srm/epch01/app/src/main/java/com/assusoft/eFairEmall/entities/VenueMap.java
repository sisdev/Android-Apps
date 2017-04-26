package com.assusoft.eFairEmall.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class VenueMap implements Parcelable{
	private int  locationId,productId;
	private String mapName,type,floorName,hallNo,filePath,creationDate,updatedBy;
	private int year,status,isUpdated;

	   public VenueMap(){
		   super();
	   }
	   public VenueMap(int locationId,String mapName, String type, String floorName, String hallNo, 
			        String filePath, String creationDate, String updatedBy,int productId) {
		   this.locationId=locationId;
	        this.mapName = mapName;
	        this.type = type;
	        this.floorName = floorName;
	        this.hallNo = hallNo;
	        this.filePath = filePath;
	        this.creationDate = creationDate;
	        this.updatedBy = updatedBy;
	        this.productId=productId;
	    }
	   
	   public void setYear(int year){
		   this.year=year;
	   }
	   public int getYear(){
		   return this.year;
	   }

	    public int getLocationId() {
	        return locationId;
	    }

	    public void setLocationId(int locationId) {
	        this.locationId = locationId;
	    }

	    public String getMapName() {
	        return mapName;
	    }

	    public void setMapName(String mapName) {
	        this.mapName = mapName;
	    }

	    public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }

	    public String getFloorName() {
	        return floorName;
	    }

	    public void setFloorName(String floorName) {
	        this.floorName = floorName;
	    }

	    public String getHallNo() {
	        return hallNo;
	    }

	    public void setHallNo(String hallNo) {
	        this.hallNo = hallNo;
	    }

	    public String getFilePath() {
	        return filePath;
	    }

	    public void setFilePath(String filePath) {
	        this.filePath = filePath;
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
	    public int getProductId() {
	        return productId;
	    }

	    public void setProductId(int productId) {
	        this.productId = productId;
	    }
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		
		public int getIsUpdated() {
			return isUpdated;
		}
		
		public void setIsUpdated(int isUpdated) {
			this.isUpdated = isUpdated;
		}
		
		@Override
		public int describeContents() {
			return 0;
		}
		
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			
		}
}
