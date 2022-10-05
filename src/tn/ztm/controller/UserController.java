package tn.ztm.controller;

import java.io.Serializable;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import isi.ztm.ztmcontrat.entite.Profil;
import isi.ztm.ztmcontrat.entite.Role;
import isi.ztm.ztmcontrat.entite.User;
import isi.ztm.ztmcontrat.serviceinterface.IDateService;
import isi.ztm.ztmcontrat.serviceinterface.IUserService;



@Controller
@ManagedBean
@RequestScoped
public class UserController implements Serializable{
	@Autowired
	private IUserService userService;
	@Autowired
	private IDateService dateService;
	

    private static final long serialVersionUID = 1L;
    private boolean active = true;
	private boolean testExist = false;
	private boolean testLoginExist = false;

	private User user;
	private List<User> listUsers;
	private String selectItemContrat;
	private String selectItemCat;
	private String selectItemCo;
	private String selectItemCharts;
	private Role role;	
	private Profil profil;
	private String SelectItemRole;
	private String selectItemSuivi;
	public UserController() {
	}

	@PostConstruct
	public void init()
	{
		listUsers = userService.listAllUsers();
		user = new User();
		role=new Role();
		profil= new Profil();
		testExist = false;
		
		
	}
public String ActionCreateUserController() {
		
	
	
	User foundu = null;
	User us= null;

	foundu = userService.findUserByCin(user.getCin());
	
	if (foundu!=null) {
	testExist=true;
return("yes");
	}
	
	
	else {
		testExist=false;

		us = userService.findUserByLogin(user.getLogin());
		if (us!=null) {
			setTestLoginExist(true);
			return("login exist");
		} else {
			
		testLoginExist=false;
		user.setEnable(true);
		user.setEtat("Active");
		user.setDateCreation(dateService.DateNow());
		if(SelectItemRole.equals("responsable")) {
			profil.setResponsableJuridique(true);
			profil.setCollaborateur(false);
			}
		
		
		
		else if (SelectItemRole.equals("collab")) {
			profil.setCollaborateur(true);
			profil.setResponsableJuridique(false);

		}
		user.setProfil(profil);
		
		
		if(selectItemCat.equals("true")) {
			role.setRoleGestionCategorie(true);}
		
			else  {role.setRoleGestionCategorie(false);}
		
		if(selectItemCo.equals("true")) {
			role.setRoleGestionContractant(true);}
		
			else  {role.setRoleGestionContractant(false);}
		
		if(selectItemContrat.equals("true")) {
			role.setRoleGestionContrat(true);}
		
			else  {role.setRoleGestionContrat(false);}
		
		
		if(selectItemCharts.equals("true")) {
			role.setRoleStatistique(true);}
		
			else  {role.setRoleStatistique(false);}
		if(selectItemSuivi.equals("true")) {
			role.setRoleSuiviContrat(true);}
		
			else  {role.setRoleSuiviContrat(false);}
		
		
		role.setRoleGestionUser(false);
		role.setRoleRapport(false);
		user.setRole(role);
	
		userService.ajouterUser(user);
		
		return ActionGoToListAllUsersController();
		}
	}
	}
public String ActionGoToListAllUsersController() {
	
	init();
	return "listAllUsers?faces-redirect=true";
}
public String ActionGoToProfil() {
	
	return "profil?faces-redirect=true";
}

public String ActionGoToCreateUserController() {
	
	init();
	user = new User();
		
	return "createUser?faces-redirect=true";
}

public String ActionUpdateUserController()
{
	
	
	if(SelectItemRole.equals("responsable")) {
		profil.setResponsableJuridique(true);
		profil.setCollaborateur(false);
		}
	
	
	
	else if (SelectItemRole.equals("collab")) {
		profil.setCollaborateur(true);
		profil.setResponsableJuridique(false);

	}
	user.setProfil(profil);
	
	if(selectItemCo.equals("true")) {
		role.setRoleGestionContractant(true);}
	
		else  {role.setRoleGestionContractant(false);}
	
	if(selectItemContrat.equals("true")) {
		role.setRoleGestionContrat(true);}
	
		else  {role.setRoleGestionContrat(false);}
	
	
	if(selectItemCharts.equals("true")) {
		role.setRoleStatistique(true);}
	
		else  {role.setRoleStatistique(false);}
	user.setRole(role);
	userService.modifierUser(user);
	
	return ActionGoToListAllUsersController();
}

public String ActionGoToUpdateUserController()
{
	
	return "updateUser?faces-redirect=true";
}
public String ActionGoToAfficherUserController()
{
	
	return "afficherUser?faces-redirect=true";
}

public String ActionSupprimerUserController() {
	
	userService.SupprimerUser(user);
	init();
	
	return ActionGoToListAllUsersController();
}
public String ActionDesactiverUserController() {
	
	setActive(false);
	user.setEnable(false);
	user.setEtat("Désactivé");
	System.out.println("utilisateur"+user.getLogin()+user.getEtat()+user.isEnable());
	userService.modifierUser(user);
	init();
	return ActionGoToListAllUsersController();
	
}

public String ActionActiverUserController() {
	
	setActive(true);
	user.setEnable(true);
	user.setEtat("Activé");	
	userService.modifierUser(user);
	init();
	return ActionGoToListAllUsersController();
}



public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public List<User> getListUsers() {
	return listUsers;
}

public void setListUsers(List<User> listUsers) {
	this.listUsers = listUsers;
}

public boolean isActive() {
	return active;
}

public void setActive(boolean active) {
	this.active = active;
}

public String getSelectItemContrat() {
	return selectItemContrat;
}

public void setSelectItemContrat(String selectItemContrat) {
	this.selectItemContrat = selectItemContrat;
}

public String getSelectItemCat() {
	return selectItemCat;
}

public void setSelectItemCat(String selectItemCat) {
	this.selectItemCat = selectItemCat;
}

public String getSelectItemCo() {
	return selectItemCo;
}

public void setSelectItemCo(String selectItemCo) {
	this.selectItemCo = selectItemCo;
}

public String getSelectItemCharts() {
	return selectItemCharts;
}

public void setSelectItemCharts(String selectItemCharts) {
	this.selectItemCharts = selectItemCharts;
}

public Role getRole() {
	return role;
}

public void setRole(Role role) {
	this.role = role;
}

public boolean isTestExist() {
	return testExist;
}

public void setTestExist(boolean testExist) {
	this.testExist = testExist;
}

public String getSelectItemRole() {
	return SelectItemRole;
}

public void setSelectItemRole(String selectItemRole) {
	SelectItemRole = selectItemRole;
}

public Profil getProfil() {
	return profil;
}

public void setProfil(Profil profil) {
	this.profil = profil;
}

public String getSelectItemSuivi() {
	return selectItemSuivi;
}

public void setSelectItemSuivi(String selectItemSuivi) {
	this.selectItemSuivi = selectItemSuivi;
}

public boolean isTestLoginExist() {
	return testLoginExist;
}

public void setTestLoginExist(boolean testLoginExist) {
	this.testLoginExist = testLoginExist;
}



}
