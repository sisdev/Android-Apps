package com.assusoft.eFairEmall.entities;

public class AdService {
	private int adId, status;
	private String placementId, hashCode, url, creationDate, updatedBy;
	
	public AdService() {
		super();
	}

	public AdService(int adId, int status, String placementId, String hashCode,
			String url, String creationDate, String updatedBy) {
		super();
		this.adId = adId;
		this.status = status;
		this.placementId = placementId;
		this.hashCode = hashCode;
		this.url = url;
		this.creationDate = creationDate;
		this.updatedBy = updatedBy;
	}

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPlacementId() {
		return placementId;
	}

	public void setPlacementId(String placementId) {
		this.placementId = placementId;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
