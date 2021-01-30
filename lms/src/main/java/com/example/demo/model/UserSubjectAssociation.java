package com.example.demo.model;

public class UserSubjectAssociation {
	private long userSubjAssociationID;
	private long fkUSerID;
	private long fkSubjectID;

	public long getUserSubjAssociationID() {
		return userSubjAssociationID;
	}

	public void setUserSubjAssociationID(long userSubjAssociationID) {
		this.userSubjAssociationID = userSubjAssociationID;
	}

	public long getFkUSerID() {
		return fkUSerID;
	}

	public void setFkUSerID(long fkUSerID) {
		this.fkUSerID = fkUSerID;
	}

	public long getFkSubjectID() {
		return fkSubjectID;
	}

	public void setFkSubjectID(long fkSubjectID) {
		this.fkSubjectID = fkSubjectID;
	}

}
