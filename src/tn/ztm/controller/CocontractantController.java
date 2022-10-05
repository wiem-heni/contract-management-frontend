package tn.ztm.controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import isi.ztm.ztmcontrat.entite.Cocontractant;
import isi.ztm.ztmcontrat.entite.User;
import isi.ztm.ztmcontrat.serviceinterface.ICocontractantService;
import isi.ztm.ztmcontrat.serviceinterface.IDateService;

@Controller
@ManagedBean
@RequestScoped
public class CocontractantController implements Serializable {

	/**
	 * 
	 */
	@Autowired
	private ICocontractantService cocontractantservice;
	@Autowired
	private IDateService dateService;
	private static final long serialVersionUID = 1L;

	private boolean testExist = false;
	private boolean testSuppression = false;

	private Cocontractant cocontractant;
	private List<Cocontractant> listAllCocontractants;

	public CocontractantController() {
	
	}

	@PostConstruct
	public void init() {
		listAllCocontractants = cocontractantservice.listAllCocontractant();
		cocontractant = new Cocontractant();
		testExist = false;
        testSuppression=false;
	}

	public String ActionGoToListAllCocontractantsController() {
init();
		return "listAllCocontractant?faces-redirect=true";
	}

	public String ActionGoToCreateCocontractantController() {
		init();
cocontractant=new Cocontractant();
		return "createCocontractant?faces-redirect=true";
	}

	public String ActionGoToUpdateCocontractantController() {

		return "updateCocontractant?faces-redirect=true";
	}

	public String ActionCreateCocontractantController() {
		Cocontractant foundu = null;

		foundu = cocontractantservice.rechercherCocontractant(cocontractant.getNom());
		
		if (foundu!=null) {

		testExist=true;
		return("yes");
		}
		else {
		
       cocontractant.setDateCreation(dateService.DateNow());
		cocontractantservice.ajouterCocontractant(cocontractant);

		return ActionGoToListAllCocontractantsController();}
	}
	public String ActionUpdateCocontractantController() {

		cocontractantservice.modifierCocontractant(cocontractant);

		return ActionGoToListAllCocontractantsController();
	}
	public String ActionDeleteCocontractantController() {
if (cocontractant.getListContratCo()==null) {
	cocontractantservice.supprimerCocontractant(cocontractant);
	init();
System.out.print(testSuppression);
	return ActionGoToListAllCocontractantsController();

	
	
}else {
	testSuppression=true;
	System.out.print(testSuppression);
	return("no");
	

}
		
	}

	public Cocontractant getCocontractant() {
		return cocontractant;
	}

	public void setCocontractant(Cocontractant cocontractant) {
		this.cocontractant = cocontractant;
	}

	public List<Cocontractant> getListAllCocontractants() {
		return listAllCocontractants;
	}

	public void setListAllCocontractants(List<Cocontractant> listAllCocontractants) {
		this.listAllCocontractants = listAllCocontractants;
	}

	public boolean isTestExist() {
		return testExist;
	}

	public void setTestExist(boolean testExist) {
		this.testExist = testExist;
	}

	public boolean isTestSuppression() {
		return testSuppression;
	}

	public void setTestSuppression(boolean testSuppression) {
		this.testSuppression = testSuppression;
	}
	

	
}