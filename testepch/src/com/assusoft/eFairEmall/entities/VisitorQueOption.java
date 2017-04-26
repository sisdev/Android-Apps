package com.assusoft.eFairEmall.entities;

public class VisitorQueOption {
	
	private int optionId,questionId,orderId,status;
	private String option,creationDate,updatedBy;
	
	public VisitorQueOption() {
		super();
	}

	public VisitorQueOption(int optionId, int questionId, int orderId,
			int status, String option, String creationDate, String updatedBy) {
		super();
		this.optionId = optionId;
		this.questionId = questionId;
		this.orderId = orderId;
		this.status = status;
		this.option = option;
		this.creationDate = creationDate;
		this.updatedBy = updatedBy;
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
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
