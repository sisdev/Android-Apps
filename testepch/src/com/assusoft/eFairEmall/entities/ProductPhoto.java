package com.assusoft.eFairEmall.entities;

public class ProductPhoto {
	String filePath,fileName,creationDate,updatedBy;
	int productId,exhibitorId,status;
	byte photoProducts[]=null;
	private int productPhotoId;
	public ProductPhoto(){
		super();
	}

    public  ProductPhoto(int productId, int exhibitorId,String filePath,String fileName,String creationDate, String updatedBy) {
        this.productId = productId;
        this.exhibitorId = exhibitorId;
        this.filePath=filePath;
        this.fileName=fileName;
        this.creationDate = creationDate;
        this.updatedBy = updatedBy;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getExhibitorId() {
        return exhibitorId;
    }

    public void setExhibitorId(Integer exibitorId) {
        this.exhibitorId = exibitorId;
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

	public void setProductPhotoId(int productPhotoId) {
		// TODO Auto-generated method stub
		this.productPhotoId=productPhotoId;
	}
	public int getProductPhotoId() {
		// TODO Auto-generated method stub
		return this.productPhotoId;
	}

    /*public byte[] getPhotoProducts() {
        return photoProducts;
    }

    public void setPhotoProducts(byte[] photoProducts) {
        this.photoProducts = photoProducts;
    }*/
    

}
