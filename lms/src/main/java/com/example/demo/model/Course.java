package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long courseID;
	private String courseName;
	
	//private CourseCategory courseCategory;
	private long fkCourseCategoryID;
	private boolean isPublished;
	private boolean isActive;

	/*
	 * private CourseCategory courseCategory; private StudyMaterial studyMaterial;
	 */


	

	public boolean setIsPublished() {
		return isPublished;
	}

	public void setIsPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public long getCourseID() {
		return courseID;
	}

	public void setCourseID(long courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public long getFkCourseCategoryID() {
		return fkCourseCategoryID;
	}

	public void setFkCourseCategoryID(long fkCourseCategoryID) {
		this.fkCourseCategoryID = fkCourseCategoryID;
	}



}
