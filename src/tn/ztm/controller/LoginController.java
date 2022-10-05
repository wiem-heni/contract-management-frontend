package tn.ztm.controller;

import java.io.IOException;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.http.Cookie;

import org.exolab.castor.types.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import isi.ztm.ztmcontrat.serviceinterface.IDateService;
import isi.ztm.ztmcontrat.serviceinterface.IUserService;
import isi.ztm.ztmcontrat.daointerface.IEmailDao;
import isi.ztm.ztmcontrat.entite.Role;
import isi.ztm.ztmcontrat.entite.User;

@Controller
@ManagedBean
@SessionScoped
public class LoginController implements Serializable {
	@Autowired
	private IUserService userService;
	@Autowired
	private IDateService dateService;
	@Autowired
	private IEmailDao emailService;
	
	
	private String email = "";

	private String receiverEmail;

	private String objectEmail = "";
	private User user;
	private String password;
	private String login;
	private boolean access = false;
	private String shemanPage = "";
	private Role role;
	private String name;


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginController() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {

		user = new User();
		login = "";
		password = "";
		role = new Role();
		setName("");
		
	}

	@PostConstruct
	public void createAdminUser() {
		
		User userFound = null;

		userFound = userService.rechercherUserByLoginAndPassword("admin", "admin");

		if (userFound == null) {
		
			role.setRoleGestionCategorie(true);
			role.setRoleGestionContractant(true);
			role.setRoleGestionUser(true);
			role.setRoleStatistique(true);
			role.setRoleSuiviContrat(true);
			role.setRoleRapport(true);
			role.setRoleGestionContrat(true);
			
			userService.ajouterUser(new User("ElHeni",dateService.DateNow(), "Wiem", "elheniwiem20@gmail.com", "admin", "admin", "Active",true, role));
		}
	}

	public void actionLoginController(ActionEvent event) {

		access = userService.authentificationUser(login, password);
		System.out.println("access ="+access);

		FacesContext context = FacesContext.getCurrentInstance();
		if (access == true) {
			context.getExternalContext().getSessionMap().put("user", login);
			user = userService.rechercherUserByLoginAndPassword(login, password);

			try {
				context.getExternalContext().redirect("pages/listAll.xhtml");
				login = null;
				password = null;
				setName(user.getNomUser()+" "+user.getPrenomUser());
			} catch (IOException e) {

				e.printStackTrace();
			}
		} else {
			context.addMessage(null,
					new FacesMessage("Authentication Failed. Please check your login or your password."));
		}

	}

	public void actionLogoutController(ActionEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();

		context.getExternalContext().invalidateSession();

		try {

			context.getExternalContext().redirect("../login.xhtml");
			Cookie ck = new Cookie("name", "");
			ck.setMaxAge(0);

		} catch (IOException e) {

			e.printStackTrace();
		}

		login = "";
		password = "";
		user = new User();

	}
	public void sendEmail() {
		FacesContext context = FacesContext.getCurrentInstance();
		user = null;
		receiverEmail = email;
		user = userService.findUserByEmail(receiverEmail);
		System.out.println("utilisateur = " + user);
		if (user != null) {
			objectEmail = "Dear User,"
					+ "\n\n You're receiving this e-mail because you requested a password recovery for your user account"
					+ "\n\n Your password : " + user.getPassword();
			emailService.sendEmail(receiverEmail, objectEmail);
		
		email = "";
		user = new User();
		}
		else {
			context.addMessage(null,
					new FacesMessage("Failed ! Please check your email."));
		}
		
	}




	public String ActionGoToForgetPasswordController()
	{
		return "forgetPassword.xhtml?faces-redirect=true";
		

	}
	public String ActionGoToLoginController()
	{
		return "login.xhtml?faces-redirect=true";
		

	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isAccess() {
		return access;
	}

	public void setAccess(boolean access) {
		this.access = access;
	}

	public String getShemanPage() {
		return shemanPage;
	}

	public void setShemanPage(String shemanPage) {
		this.shemanPage = shemanPage;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public String getObjectEmail() {
		return objectEmail;
	}

	public void setObjectEmail(String objectEmail) {
		this.objectEmail = objectEmail;
	}



}
