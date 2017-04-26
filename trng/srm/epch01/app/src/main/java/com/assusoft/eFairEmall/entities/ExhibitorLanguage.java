/**
 * @author umesh singh kushwaha
 */
package com.assusoft.eFairEmall.entities;

public class ExhibitorLanguage {

	int langId,exhibitorId,status; 
	String creationDate,updatedBy;
	public ExhibitorLanguage(){
		super();
	}

    public ExhibitorLanguage(int langId, int exhibitorId,String creationDate,String updatedBy) {
        this.langId = langId;
        this.exhibitorId = exhibitorId;
        this.creationDate=creationDate;
        this.updatedBy=updatedBy;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public int getExhibitorId() {
        return exhibitorId;
    }

    public void setExhibitorId(int exhibitorId) {
        this.exhibitorId = exhibitorId;
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
