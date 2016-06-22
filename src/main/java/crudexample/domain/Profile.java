package crudexample.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Profile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idprofile;
	
	private String FName;
	private String LName;
	private String Title;
	private String Gender;
	private String Address;
	private String Phone;
	private String Active;
	private Date CreateDate;
	private Date UpdatedDate;
	
	
	public String getFName() {
		return FName;
	}

	public void setFName(String firstName) {
		this.FName = firstName;
	}

	public String getLName() {
		return LName;
	}

	public void setLName(String lastName) {
		this.LName = lastName;
	}
	
	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}
	
	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		this.Gender = gender;
	}
	
	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		this.Address = address;
	}
	
	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		this.Phone = phone;
	}
	
	public String getActive() {
		return Active;
	}

	public void setActive(String active) {
		this.Active = active;
	}
	
	public Date getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
	}
    
	public Date getUpdatedDate() {
		return UpdatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.UpdatedDate = updatedDate;
	}
	public long getidprofile() {
		return idprofile;
	}

	public void setidprofile(long idprofile) {
		this.idprofile = idprofile;
	}
}
