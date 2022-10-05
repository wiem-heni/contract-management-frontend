package tn.ztm.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import isi.ztm.ztmcontrat.serviceinterface.ICategorieService;
import isi.ztm.ztmcontrat.serviceinterface.IDateService;
import isi.ztm.ztmcontrat.entite.Categorie;
import isi.ztm.ztmcontrat.entite.User;

@Controller
@ManagedBean
@SessionScoped
public class CategorieController implements Serializable {
	private static final long serialVersionUID = 1L;
	@Autowired
	private IDateService dateService;
	@Autowired
	private ICategorieService CategorieService;
	private boolean testExist = false;

	private List<Categorie> listAllCategorie;
	private Categorie Categorie;
	private boolean testSuppress=false;

	/**
	 * 
	 */

	public CategorieController() {

	}
	@PostConstruct
	public void init() {
		testExist = false;
		testSuppress=false;
		listAllCategorie = CategorieService.listAllCategorie();
		Categorie = new Categorie();
	}

	public String ActionCreateCategorieController() {
		
		Categorie foundu = null;

		foundu = CategorieService.rechercherCategorieByNom(Categorie.getNom());
		
		if (foundu!=null) {

		testExist=true;
	return("yes");
		}
		else {
		Categorie.setDateCreation(dateService.DateNow());
       CategorieService.ajouterCategorie(Categorie);
		return ActionGoToListAllCategorieController();}
	}

	public String ActionUpdateCategorieController() {

		CategorieService.modifierCategorie(Categorie);
		
        
		return ActionGoToListAllCategorieController();
	}

	public String ActionDeleteCategorieController() {

		if (Categorie.getListContrat()==null) {
			CategorieService.supprimerCategorie(Categorie);
			init();
		System.out.print(testSuppress);
		testSuppress=false;
			return ActionGoToListAllCategorieController();

			
			
		}else {
			testSuppress=true;
			System.out.print(testSuppress);
			return("no");
			

		}
	}

	public String ActionGoToListAllCategorieController() {

		init();

		return "listAllCat?faces-redirect=true";

	}

	public String ActionGoToCreateCategorieController() {

		init();

		return "createCategorie?faces-redirect=true";

	}

	public String ActionGoToUpdateCategorie() {

		return "updateCategorie?faces-redirect=true";
	}

	public List<Categorie> getListAllCategorie() {
		return listAllCategorie;
	}

	public void setListAllCategorie(List<Categorie> listAllCategorie) {
		this.listAllCategorie = listAllCategorie;
	}

	public Categorie getCategorie() {
		return Categorie;
	}

	public void setCategorie(Categorie Categorie) {
		this.Categorie = Categorie;
	}
	public boolean isTestExist() {
		return testExist;
	}
	public void setTestExist(boolean testExist) {
		this.testExist = testExist;
	}
	public boolean isTestSuppress() {
		return testSuppress;
	}
	public void setTestSuppress(boolean testSuppress) {
		this.testSuppress = testSuppress;
	}

}
