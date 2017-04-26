/**
 * @author umesh singh kushwaha
 * 
 */
package com.assusoft.eFairEmall.entities;

public class Language {
     
	private int langId,status;
    String langName,creationDate,updatedBy;
    
    public Language(int langId,String langName,String creationDate,String updatedBy){
    	this.langId=langId;
    	this.langName=langName;
    	this.creationDate=creationDate;
    	this.updatedBy=updatedBy;
    }
    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
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
