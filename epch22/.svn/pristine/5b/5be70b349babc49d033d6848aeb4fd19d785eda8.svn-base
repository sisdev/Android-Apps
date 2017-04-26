package com.assusoft.eFairEmall.entities;



import android.os.Parcel;
import android.os.Parcelable;

public class Users {
	private   String loginEmail,password,secretQuestion,secretAnswer,lastLoggedIn,creationDate,updatedBy;
	private int roleId,userId,status; 
	
	 //constructors 
	  public  Users(){
	    	super();
	    }
	  public Users(Parcel in) {
			readFromParcel(in);
		}
	  public Users(int roleId,String loginEmail,String password,String secretQuestion,String secretAnswer,int status,String lastLoggedIn,String creationDate,String updatedBy) {
	        this.roleId = roleId;
	        this.loginEmail = loginEmail;
	        this.password = password;
	        this.secretQuestion = secretQuestion;
	        this.secretAnswer = secretAnswer;
	        this.status = status;
	        this.lastLoggedIn = lastLoggedIn;
	        this.creationDate = creationDate;
	        this.updatedBy = updatedBy;
	       
	    }
	  //getter and setter methods
	  public int getUserId() {
	        return userId;
	    }
      public void setUserId(int userId)
      {
    	  this.userId=userId;
      }
	   public int getRoleId() {
	        return roleId;
	    }

	    public void setRoleId(int roleId) {
	        this.roleId = roleId;
	    }

	    public String getLoginEmail() {
	        return loginEmail;
	    }

	    public void setLoginEmail(String loginEmail) {
	        this.loginEmail = loginEmail;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getSecretQuestion() {
	        return secretQuestion;
	    }

	    public void setSecretQuestion(String secretQuestion) {
	        this.secretQuestion = secretQuestion;
	    }

	    public String getSecretAnswer() {
	        return secretAnswer;
	    }

	    public void setSecretAnswer(String secretAnswer) {
	        this.secretAnswer = secretAnswer;
	    }

	    public String getLastLoggedIn() {
	        return lastLoggedIn;
	    }

	    public void setLastLoggedIn(String lastLoggedIn) {
	        this.lastLoggedIn = lastLoggedIn;
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
		//this method is used to read the serialized data
	    private void readFromParcel(Parcel in) {
	    	this.roleId = in.readInt();
	        this.loginEmail =in.readString();
	        this.password = in.readString();
	        this.secretQuestion =in.readString();
	        this.secretAnswer = in.readString();
	        this.status =in.readInt();
	        this.lastLoggedIn = in.readString();
	        this.creationDate = in.readString();
	        this.updatedBy = in.readString();
		}
	    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
			public Users createFromParcel(Parcel in) {
				return new Users(in);
			}

			public Users[] newArray(int size) {
				return new Users[size];
			}
		};
}
