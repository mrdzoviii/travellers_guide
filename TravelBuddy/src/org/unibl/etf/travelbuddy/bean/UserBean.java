package org.unibl.etf.travelbuddy.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.unibl.etf.travelbuddy.mysql.UserDao;
import org.unibl.etf.travelbuddy.mysql.UserDto;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable{
	private static final long serialVersionUID = 1800224362852627021L;
	private UserDto user;
	private String username;
	private String password;
	private String passwordConfirmation;
	private Date today;
	public Date getToday() {
		return today;
	}
	public void setToday(Date today) {
		this.today = today;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
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
	public UserBean() {
		super();
		user=new UserDto();
		today=new Date();
	}
	public void login() {
		
	}
	public String registration() {
		System.out.println(user.getPassword()+" : "+passwordConfirmation);
		if (user.getPassword() != null && !user.getPassword().equals(passwordConfirmation)) {
			FacesMessage message = new FacesMessage("Password", "Password missmatch");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}

		UserDto tmp = UserDao.selectUsername(user.getUsername());
		if (tmp != null) {
			FacesMessage message = new FacesMessage("Username", "Username not available.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null,message);
			return null;
		}
		
		
		// check username & password
		if(!user.getUsername().matches("^[^@#/]{12,}$")) {
			// username not valid
			FacesMessage message = new FacesMessage("Username", "Username must be 12 characters long and can't contains @, # i /.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("username", message);
			return null;
		}
		if(!user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{15,}$")) {
			// password not valid
			FacesMessage message = new FacesMessage("Password", "Password must be 15 characters long and must contains digits,upper and lower letters.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("password", message);
			return null;
		}

		user.setType(1);
		user.setStatus(0);
		UserDao.insert(user);
		FacesMessage message = new FacesMessage("Success", "Registration successfully completed.");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, message);

		user = new UserDto();
		passwordConfirmation = "";
		return null;
	}
	
}
