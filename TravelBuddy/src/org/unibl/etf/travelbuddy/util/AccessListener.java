package org.unibl.etf.travelbuddy.util;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.unibl.etf.travelbuddy.bean.UserBean;


public class AccessListener implements PhaseListener {

	private static final long serialVersionUID = 3971334959325942875L;

	@Override
	public void afterPhase(PhaseEvent arg0) {
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		FacesContext cxt = arg0.getFacesContext();
		HttpServletRequest req = (HttpServletRequest) cxt.getExternalContext().getRequest();
		HttpServletResponse resp = (HttpServletResponse) cxt.getExternalContext().getResponse();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		
		UserBean user = (UserBean) session.getAttribute("user");
		String address = "error.xhtml?faces-redirect=true";
		
		if (req.getRequestURI().endsWith("/") || req.getRequestURI().endsWith("index.xhtml")) {
			if(user != null) {
				if(user.getUser().getType()==1)
					address = "/user/user_home.xhtml?faces-redirect=true";
				else if(user.getUser().getType()==0)
					address = "/admin/admin_home.xhtml?faces-redirect=true";
				else
					address = "error.xhtml?faces-redirect=true";
				
				if (!resp.isCommitted()) {
					cxt.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,
							address);
					cxt.responseComplete();
				}
			}
		} 
		else if(req.getRequestURI().contains("guest")) {
			if(user != null) {
				address = "error.xhtml?faces-redirect=true";
				if (!resp.isCommitted()) {
					cxt.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,
							address);
					cxt.responseComplete();
				}
			}
		} 
		else if(req.getRequestURI().contains("user")) {
			if(user == null || !(user.getUser().getType()==1)) {
				address = "error.xhtml?faces-redirect=true";
				if (!resp.isCommitted()) {
					cxt.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,
							address);
					cxt.responseComplete();
				}
			}
		}
		else if(req.getRequestURI().contains("admin")) {
			if(user == null || !(user.getUser().getType()==0)) {
				address = "error.xhtml?faces-redirect=true";
				if (!resp.isCommitted()) {
					cxt.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,
							address);
					cxt.responseComplete();
				}
			}
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
