package com.assusoft.eFairEmall.entities;

public class Links {
	
	private int linkId,	linkTypeId, status;
	private String linkUrl,linkName,creationDate,updatedBy;
	
	public Links() {
		super();
	}

	public Links(int linkId, int linkTypeId, int status, String linkUrl,
			String linkName, String creationDate, String updatedBy) {
		super();
		this.linkId = linkId;
		this.linkTypeId = linkTypeId;
		this.status = status;
		this.linkUrl = linkUrl;
		this.linkName = linkName;
		this.creationDate = creationDate;
		this.updatedBy = updatedBy;
	}

	public int getLinkId() {
		return linkId;
	}

	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}

	public int getLinkTypeId() {
		return linkTypeId;
	}

	public void setLinkTypeId(int linkTypeId) {
		this.linkTypeId = linkTypeId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
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
}
