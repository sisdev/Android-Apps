package com.assusoft.eFairEmall.entities;

public class ExhibitorProducts {
	 String creationDate,updatedBy;
	 int productId,exhibitorsId,status; 
	 public ExhibitorProducts(){
		 super();
		 
	 }
	    public ExhibitorProducts(int productId, int exhibitorsId, String creationDate, String updatedBy) {
	        this.productId = productId;
	        this.exhibitorsId = exhibitorsId;
	        this.creationDate = creationDate;
	        this.updatedBy = updatedBy;
	    }
	    public int getProductId() {
	        return productId;
	    }

	    public void setProductId(int productId) {
	        this.productId = productId;
	    }

	    public int getExibitorsId() {
	        return exhibitorsId;
	    }

	    public void setExibitorsId(int exhibitorsId) {
	        this.exhibitorsId = exhibitorsId;
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
