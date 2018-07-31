package org.unibl.etf.travelbuddy.mysql;

import java.io.Serializable;
import java.util.Date;

public class UserDto implements Serializable {
	private static final long serialVersionUID = -4171674734020296532L;
	private int id;
	private String username;
	private String password;
	private String name;
	private String surname;
	private Date birthDate;
	private String email;
	private Date createTime;
	private int type;
	private int status;
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public UserDto(int id, String username, String password, String name, String surname, Date birthDate, String email,
			int type, int status,Date createTime) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.email = email;
		this.type = type;
		this.status = status;
		this.createTime=createTime;
	}
	public UserDto() {
		super();
	}
	
	
}
