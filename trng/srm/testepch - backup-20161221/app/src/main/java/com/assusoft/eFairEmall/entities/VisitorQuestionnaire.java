package com.assusoft.eFairEmall.entities;

public class VisitorQuestionnaire {

	private int questionId,orderId,status;
	private String questionType,question,userType,creationDate,updatedBy;
	
	public VisitorQuestionnaire() {
		super();
	}

	public VisitorQuestionnaire(int questionId, int orderId, int status,
			String questionType, String question, String userType,
			String creationDate, String updatedBy) {
		super();
		this.questionId = questionId;
		this.orderId = orderId;
		this.status = status;
		this.questionType = questionType;
		this.question = question;
		this.userType = userType;
		this.creationDate = creationDate;
		this.updatedBy = updatedBy;
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

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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
