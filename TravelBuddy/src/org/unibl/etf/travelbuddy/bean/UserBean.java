package org.unibl.etf.travelbuddy.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
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
	private String messages;
	private boolean loggedIn;
	private boolean typeUser;
	private boolean typeAdmin;
	
	 
	public boolean isTypeUser() {
		return typeUser;
	}
	public void setTypeUser(boolean typeUser) {
		this.typeUser = typeUser;
	}
	public boolean isTypeAdmin() {
		return typeAdmin;
	}
	public void setTypeAdmin(boolean typeAdmin) {
		this.typeAdmin = typeAdmin;
	}
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
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
		loggedIn=false;
		typeUser=false;
		typeAdmin=false;
		messages="5";
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
	
	public void login(ActionEvent event) {
		System.out.println("LOGINNNNN "+username+" :"+password);
		boolean logged = false;
		user = UserDao.selectActive(username);
		if (user != null && user.getPassword().equals(password)) {
			logged = true;
			PrimeFaces.current().ajax().addCallbackParam("loggedIn", logged);
			if (user.getType()==1) {
				typeUser=true;
				FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(
						FacesContext.getCurrentInstance(), null, "/user/user_home.xhtml?faces-redirect=true");
				/* messages not implemented yet
				int count = MessageDAO.countNotSeenMessagesAll(user.getId());
				notSeenMessages = count==0?"":""+count;
				*/
			} else {
				typeAdmin=false;
				FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(
						FacesContext.getCurrentInstance(), null, "/admin/admin_home.xhtml?faces-redirect=true");
			}
			
			loggedIn = logged;
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("user", this);
		} else {
			logged = false;
			user = null;
			PrimeFaces.current().ajax().addCallbackParam("loggedIn", logged);
		}
		username = "";
		password = "";
		passwordConfirmation="";
	}
	
	public void logout(ActionEvent event) {
		if(loggedIn) {
			loggedIn = false;
			if(typeUser) {
				typeUser=false;
			}
			if(typeAdmin) {
				typeAdmin=false;
			}
			user = new UserDto();
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(
					FacesContext.getCurrentInstance(), null, "/index.xhtml?faces-redirect=true");
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.removeAttribute("user");
			messages = "";
		}
	}

	
}
