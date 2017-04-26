package com.assusoft.eFairEmall.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class PhotoShoot implements Parcelable{
    int id,exhibitorId,visitorId,productId,status;
    String photopath,notes,creationDate,updatedBy;

    public PhotoShoot() {
    }

    public PhotoShoot(int id, int exhibitorId, int visitorId, int productId, String photopath, String notes, String creationDate, String updatedBy) {
        this.id = id;
        this.exhibitorId = exhibitorId;
        this.visitorId = visitorId;
        this.productId = productId;
        this.photopath = photopath;
        this.notes = notes;
        this.creationDate = creationDate;
        this.updatedBy = updatedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExhibitorId() {
        return exhibitorId;
    }

    public void setExhibitorId(int exhibitorId) {
        this.exhibitorId = exhibitorId;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getPhotopath() {
        return photopath;
    }

    public void setPhotopath(String photopath) {
        this.photopath = photopath;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
