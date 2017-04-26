package com.assusoft.eFairEmall.entities;
public class FileSettings {
    int id,status;
    String filePath,fileName,creationDate,updatedBy;

    public FileSettings() {
    }

    public FileSettings(int id, String filePath, String fileName) {
        this.id = id;
        this.filePath = filePath;
        this.fileName = fileName;
        this.creationDate=creationDate;
        this.updatedBy=updatedBy;
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    
}
