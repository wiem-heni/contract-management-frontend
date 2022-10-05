package tn.ztm.controller;
import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import isi.ztm.ztmcontrat.entite.Categorie;
import isi.ztm.ztmcontrat.entite.Cocontractant;
import isi.ztm.ztmcontrat.entite.Contrat;
import isi.ztm.ztmcontrat.entite.File;
import isi.ztm.ztmcontrat.entite.Historique;
import isi.ztm.ztmcontrat.serviceinterface.ICategorieService;
import isi.ztm.ztmcontrat.serviceinterface.ICocontractantService;
import isi.ztm.ztmcontrat.serviceinterface.IContratService;
import isi.ztm.ztmcontrat.serviceinterface.IDateService;
import isi.ztm.ztmcontrat.serviceinterface.IFileService;
import isi.ztm.ztmcontrat.serviceinterface.IHistoriqueService;

@Controller
@ManagedBean
@RequestScoped
public class ContratController implements Serializable{
	private static final long serialVersionUID = 1L;
	@Autowired
	private IContratService contratService;
	@Autowired
	private ICocontractantService cocontractantService;
	@Autowired
	private ICategorieService categorieService;
	@Autowired
	private IHistoriqueService historiqueService;
	@Autowired
	private IDateService dateService;
	@Autowired
	private IFileService fileService;
	@Autowired
	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;
	
	
	private Part fileUp;
	
	
	private boolean testExist = false;
    private String now ;
    private Contrat contrat;
	private String selectItemCategorie;
	private String selectItemType;
	private String selectItemUnite;
	private String selectItemPeriode;
	private String selectItemRecherche;
	private String selectItemCat;

	private String selectItemCategorieUp;
	private String selectItemTypeUp;
	private String selectItemUniteUp;
	private String selectItemPeriodeUp;
	private String selectItemCoco;
	private String  selectItemCocoUp;
	private String datee;
	private Cocontractant con;
	private Date dateD;
	private Date dateF;
	private String prix;
	private String ann;
	private String typeRecherche;
	private String selectCo;

	private String ob;
	private File file;
    private Categorie cc;
    private Cocontractant cocontractant;
    private Categorie categorie;
	private List<Categorie> listCategorie;
	private List<Historique> listHistorique;
	private Historique historique;
	private List<Contrat> listAllContrats;
	private List<Contrat> listRecherche;
    private int nbNotif;
    private List<Contrat> listContratPourNotif;
    private List<String> notification;
	private List<Contrat> listContratFermeEnCours;
	private List<Contrat> listContratAnnuler;
	private List<Contrat> listContratFermeExpirer;
	private List<Contrat> listContratFermeAvantExpiration;//-31 jours avant leur expiration
	private List<Contrat> listContratRenouvelable;//tous les contrats renouvelables
	private List<Contrat> listContratRenouvelableEnCours;
	private List<Contrat> listContratRenouvelableAvantExpiration;//-31 jours avant leurs delais de preavis(contrat renouvelable en attente)
	private List<Contrat> listContratRenouvelableExpirer;//les contrats qui ont atteint le delai de preavis
	private List<Contrat> listContratRenouvelableAtteintPreavis;
	private List<Contrat> listContratRenouvelableAvantPreavis;
	private List<Cocontractant> listAllCocon;
	private int nbContratAnnuler;
	private int nbContratEnCours;//fermes et renouvelables
	private int nbContratFermeEnCours;
	private int nbContratRenouvelableEnCours;
	private int nbContratRenouvelableAvantExpiration;//renouvelables en attente de decision
	private int nbContratRenouvelableExpirer;//contrats expirés(renouvelables )(delai preavis dépassé)
	private int nbContratFermeExpirer;//contrats expirés(fermes )
	private int nbContratFermeAvantExpiration;//30 jours avant leur fermeture(ne concerne que les contrats fermes)
	private int nbContratExpirer;//fermes et renouvelables
	private int nbContratRenouvelableAtteintPreavis;
	private int nbContratRenouvelableAvantPreavis;
    private Date d = new Date();

    public ContratController() {
		
	}
    @PostConstruct
	public void init() {
    	testExist = false;
		listCategorie = categorieService.listAllCategorie();
		listAllCocon=cocontractantService.listAllCocontractant();
		listRecherche=contratService.listAllContrats();
		listAllContrats = contratService.listAllContrats();
		nbContratEnCours= contratService.nbContratEnCours();
		nbContratFermeEnCours= contratService.nbContratFermeEnCours();
		nbContratRenouvelableEnCours= contratService.nbContratRenouvelableEnCours();
		nbContratRenouvelableAvantExpiration= contratService.nbContratRenouvelableAvantExpiration();
		nbContratRenouvelableExpirer= contratService.nbContratRenouvelableExpirer();
		nbContratFermeExpirer = contratService.nbContratFermeExpirer();
		nbContratFermeAvantExpiration = contratService.nbContratFermeAvantExpiration();
		nbContratExpirer = contratService.nbContratExpirer();
		nbContratRenouvelableAtteintPreavis= contratService.nbContratRenouvelableAtteintPreavis();
		nbContratRenouvelableAvantPreavis= contratService.nbContratRenouvelableAvantPreavis();
		nbContratAnnuler= contratService.nbContratAnnuler();
		listContratFermeEnCours = contratService.listContratFermeEnCours();
		listContratPourNotif = contratService.listPourNotif();
		listContratFermeExpirer = contratService.listContratFermeExpirer();
		listContratFermeAvantExpiration = contratService.listContratFermeAvantExpiration();
		listContratRenouvelable = contratService.listContratRenouvelable();
		listContratRenouvelableEnCours = contratService.listContratRenouvelableEnCours();
		listContratRenouvelableAvantExpiration = contratService.listContratRenouvelableAvantExpiration();
		listContratRenouvelableExpirer = contratService.listContratRenouvelableExpirer();
		listContratRenouvelableAtteintPreavis = contratService.listContratRenouvelableAtteintPreavis();
		listContratRenouvelableExpirer = contratService.listContratRenouvelableExpirer();
		listContratRenouvelableAvantPreavis= contratService.listContratRenouvelableAvantPreavis();
		listContratFerme = contratService.listContratFerme();
        listContratAnnuler= contratService.listContratAnnuler();
        nbNotif= contratService.nbNotif();
        notification= contratService.listNotif();
		contrat = new Contrat();
		historique = new Historique();
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM yyyy");
	   datee = sdf.format(currentDate);
	  file = new File();
	


	}


	public String ActionCreateContratController() {
		Contrat foundu = null;


		contrat.setTypeContrat(selectItemType);
		categorie = categorieService.rechercherCategorieById(Integer.parseInt(selectItemCategorie));
		contrat.setCategorie(categorie);
		cocontractant = cocontractantService.rechercherCocontractantById(Integer.parseInt(selectItemCoco));
		contrat.setCocontractant(cocontractant);
		contrat.setUnite(selectItemUnite);
		contrat.setPeriode(selectItemPeriode);
		String mm= contrat.getNombre()+contrat.getPeriode();
		contrat.setPeriodeContrat(mm);
		String duree = contrat.getNb()+contrat.getUnite();
		contrat.setDelaiPreavis(duree);
		contrat.setTypeContrat(selectItemType);
		Date dt = contratService.DateRenouveler(contrat);
		contrat.setDateRenouvellement(dt);
		if (contrat.getTypeContrat().equals("Ferme")) {
			 contrat.setEtatContrat(contratService.testEtatFerme(contrat));
		} else if (contrat.getTypeContrat().equals("Renouvelable")) {
			contrat.setEtatContrat(contratService.testEtatRenouvelable(contrat));
		}
		
		Calendar st = Calendar.getInstance();
		st.setTime(contrat.getDateDebut());
		int year = st.get(Calendar.YEAR);
		contrat.setAnneeSignature(year);
		String c=contrat.getObjetContrat().substring(0,4)+contrat.getAnneeSignature()+contrat.getCategorie().getNom().substring(0,4);
		contrat.setCodeContrat(c);
		
		foundu = contratService.rechercherContratByCode(contrat.getCodeContrat());
		
		if (foundu!=null) {

		testExist=true;
	return("yes");
		}
		else {
		
		historique.setDateModif(dateService.DateNow());
		historique.setUtilisateur(loginController.getUser().getPrenomUser()+" "+loginController.getUser().getNomUser());
		historique.setTypeTransaction("Ajout");
		historique.setAnneeSignature(contrat.getAnneeSignature());
		historique.setCat(contrat.getCategorie().getNom());
		historique.setCocontractant(contrat.getCocontractant().getNom());
		historique.setCodeContrat(contrat.getCodeContrat());
		historique.setCout(contrat.getCout());
		historique.setDateAugmentation(contrat.getDateAugmentation());
		historique.setDateDebut(contrat.getDateDebut());
		historique.setDateFin(contrat.getDateFin());
		historique.setDateRenouvellement(contrat.getDateRenouvellement());
		historique.setPeriodeContrat(contrat.getPeriodeContrat());
		historique.setDelaiPreavis(contrat.getDelaiPreavis());
	    historique.setEtatContrat(contrat.getEtatContrat());
	    historique.setTypeContrat(contrat.getTypeContrat());
	    historique.setTaux(contrat.getTaux());
	    historique.setObservation(contrat.getObservation());
	    historique.setObjetContrat(contrat.getObjetContrat());
	    contratService.ajouterContrat(contrat);
        historique.setContrat(contrat);

	    historiqueService.ajouterHistorique(historique);
	    try {
			uploadExcelFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    file.setNom("D:\\contratDoc\\" + getFilename(fileUp));
        fileService.ajouterFichier(file);
        file.setContrat(contrat);
        fileService.modifierFichier(file);
	   
    	return ActionGoToListAllContratsController();}
	
    }

    public String ActionUpdateContratController() {
    	
		categorie = categorieService.rechercherCategorieById(Integer.parseInt(selectItemCategorieUp));
		contrat.setCategorie(categorie);
		cocontractant = cocontractantService.rechercherCocontractantById(Integer.parseInt(selectItemCocoUp));
		contrat.setCocontractant(cocontractant);
		contrat.setUnite(selectItemUniteUp);
		contrat.setPeriode(selectItemPeriodeUp);
		String mm= contrat.getNombre()+contrat.getPeriode();
		contrat.setPeriodeContrat(mm);
		String duree = contrat.getNb()+contrat.getUnite();
		contrat.setDelaiPreavis(duree);
		contrat.setTypeContrat(selectItemType);
		Date dt = contratService.DateRenouveler(contrat);
		contrat.setDateRenouvellement(dt);
		if (contrat.getTypeContrat().equals("Ferme")) {
			 contrat.setEtatContrat(contratService.testEtatFerme(contrat));
		} else if (contrat.getTypeContrat().equals("Renouvelable")) {
			contrat.setEtatContrat(contratService.testEtatRenouvelable(contrat));
		}
		Calendar st = Calendar.getInstance();
		st.setTime(contrat.getDateDebut());
		int year = st.get(Calendar.YEAR);
		contrat.setAnneeSignature(year);
		String c=categorie.getNom()+contrat.getAnneeSignature();
		contrat.setCodeContrat(c);
		contratService.modifierContrat(contrat);

		historique.setDateModif(dateService.DateNow());
		historique.setUtilisateur(loginController.getUser().getPrenomUser()+" "+loginController.getUser().getNomUser());
		historique.setTypeTransaction("Modification");
		historique.setAnneeSignature(contrat.getAnneeSignature());
		historique.setCocontractant(contrat.getCocontractant().getNom());
		historique.setCodeContrat(contrat.getCodeContrat());
		historique.setCat(contrat.getCategorie().getNom());
		historique.setCout(contrat.getCout());
		historique.setDateAugmentation(contrat.getDateAugmentation());
		historique.setDateDebut(contrat.getDateDebut());
		historique.setDateFin(contrat.getDateFin());
		historique.setDateRenouvellement(contrat.getDateRenouvellement());
		historique.setPeriodeContrat(contrat.getPeriodeContrat());
		historique.setDelaiPreavis(contrat.getDelaiPreavis());
	    historique.setEtatContrat(contrat.getEtatContrat());
	    historique.setTypeContrat(contrat.getTypeContrat());
	    historique.setTaux(contrat.getTaux());
	    historique.setObservation(contrat.getObservation());
	    historique.setObjetContrat(contrat.getObjetContrat());
        historique.setContrat(contrat);
		historiqueService.ajouterHistorique(historique);

		return ActionGoToListAllContratsController();
	}
    public String ActionRenouvelerContratController() {
        
       
		contratService.renouveler(contrat);
		contratService.modifierContrat(contrat);

		historique.setDateModif(dateService.DateNow());
		historique.setUtilisateur(loginController.getUser().getPrenomUser()+" "+loginController.getUser().getNomUser());
		historique.setTypeTransaction("Modification");
		historique.setAnneeSignature(contrat.getAnneeSignature());
		historique.setCocontractant(contrat.getCocontractant().getNom());
		historique.setCodeContrat(contrat.getCodeContrat());
		historique.setCat(contrat.getCategorie().getNom());
		historique.setCout(contrat.getCout());
		historique.setDateAugmentation(contrat.getDateAugmentation());
		historique.setDateDebut(contrat.getDateDebut());
		historique.setDateFin(contrat.getDateFin());
		historique.setDateRenouvellement(contrat.getDateRenouvellement());
		historique.setPeriodeContrat(contrat.getPeriodeContrat());
		historique.setDelaiPreavis(contrat.getDelaiPreavis());
	    historique.setEtatContrat(contrat.getEtatContrat());
	    historique.setTypeContrat(contrat.getTypeContrat());
	    historique.setTaux(contrat.getTaux());
	    historique.setObservation(contrat.getObservation());
	    historique.setObjetContrat(contrat.getObjetContrat());
        historique.setContrat(contrat);
		historiqueService.ajouterHistorique(historique);



		init();
		return ActionGoToListRenouvelableAtteintPreavisController();
	}
    
    public String ActionAnnulerContratController() {
        
        
		contratService.fermer(contrat);
		contratService.modifierContrat(contrat);

		historique.setDateModif(dateService.DateNow());
		historique.setUtilisateur(loginController.getUser().getPrenomUser()+" "+loginController.getUser().getNomUser());
		historique.setTypeTransaction("Modification");
		historique.setAnneeSignature(contrat.getAnneeSignature());
		historique.setCocontractant(contrat.getCocontractant().getNom());
		historique.setCodeContrat(contrat.getCodeContrat());
		historique.setCat(contrat.getCategorie().getNom());
		historique.setCout(contrat.getCout());
		historique.setDateAugmentation(contrat.getDateAugmentation());
		historique.setDateDebut(contrat.getDateDebut());
		historique.setDateFin(contrat.getDateFin());
		historique.setDateRenouvellement(contrat.getDateRenouvellement());
		historique.setPeriodeContrat(contrat.getPeriodeContrat());
		historique.setDelaiPreavis(contrat.getDelaiPreavis());
	    historique.setEtatContrat(contrat.getEtatContrat());
	    historique.setTypeContrat(contrat.getTypeContrat());
	    historique.setTaux(contrat.getTaux());
	    historique.setObservation(contrat.getObservation());
	    historique.setObjetContrat(contrat.getObjetContrat());
        historique.setContrat(contrat);
		historiqueService.ajouterHistorique(historique);


		init();
		return ActionGoToListRenouvelableAtteintPreavisController();
	}
    public void ActionRechercherParCritereController() {


    	if (selectItemCat=="") {
			cc=null;
			System.out.println("categorie null");
		}else {
			cc = categorieService.rechercherCategorieById(Integer.parseInt(selectItemCat));
		}

    	if (selectCo=="") {
			con=null;
			System.out.println("cocontractant null");
		}else {
			con = cocontractantService.rechercherCocontractantById(Integer.parseInt(selectCo));
		}
    	listRecherche = contratService.rechercheParCritere(con,ob,typeRecherche,cc,ann,prix,dateD,dateF);
    	
           
	}

public void afficheFile()
{
	
	



}
	public String ActionGoToListAllContratsController() {

		init();

		return "listAll?faces-redirect=true";

	}

	public String ActionGoToListActionController() {

		init();

		return "prendreAction?faces-redirect=true";

	}

	public String ActionGoToCreateContratController() {

		init();
	

		return "createContrat?faces-redirect=true";

	}
	public String ActionGoToAfficherContratController() {
		String ch="";
	if (contrat.getTypeContrat().contentEquals("Ferme")) {
	ch="afficherFerme?faces-redirect=true";
	}
	else if (contrat.getTypeContrat().contentEquals("Renouvelable")) {
		ch="afficherRenouvelable?faces-redirect=true";
	}
return ch;
	}

	public String ActionGoToUpdateContrat() {

		return "updateContrat?faces-redirect=true";
	}
	 public String ActionGoToListRenouvelableAvantExpirationController() {
		 return "listContratRenouvelableAvantExpiration?faces-redirect=true";
	 }
	 public String ActionGoToListRenouvelableController() {
		 return "listContratRenouvelable?faces-redirect=true";
	 }
	 public String ActionGoToListRenouvelableExpirerController() {
		 return "listContratRenouvelableExpirer?faces-redirect=true";
	 }
	 public String ActionGoToListFermeController() {
		 return "listContratFerme?faces-redirect=true";
	 }
	 public String ActionGoToSuiviContratController() {
		 return "suiviContrat?faces-redirect=true";
	 }
	
	 public String ActionGoToListFermeAvantExpirationController() {
		 return "listContratFermeAvantExpiration?faces-redirect=true";
	 }
	 public String ActionGoToListFermeExpirerController() {
		 return "listContratFermeExpirer?faces-redirect=true";
	 }
	 public String ActionGoToListFermeEnCoursController() {
		 return "listContratFermeEnCours?faces-redirect=true";
	 }
	 public String ActionGoToListRenouvelableEnCoursController() {
		 return "listContratRenouvelableEnCours?faces-redirect=true";
	 }
	 public String ActionGoToListRenouvelableAvantPreavisController() {
		 return "listContratRenouvelableAvantPreavis?faces-redirect=true";
	 }
	 public String ActionGoToListRenouvelableAtteintPreavisController() {
		 return "listContratRenouvelableAtteintPreavis?faces-redirect=true";
	 }
	 public String ActionGoToListContratAnnuler() {
		 return "listContratAnnuler?faces-redirect=true";
	 }
	
	 public String ActionGoToHistorique() {
		 return "historique?faces-redirect=true";
	 }
	public Contrat getContrat() {
		return contrat;
	}
	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public List<Categorie> getListCategorie() {
		return listCategorie;
	}
	public void setListCategorie(List<Categorie> listCategorie) {
		this.listCategorie = listCategorie;
	}
	public List<Contrat> getListAllContrats() {
		return listAllContrats;
	}
	public void setListAllContrats(List<Contrat> listAllContrats) {
		this.listAllContrats = listAllContrats;
	}
	public String getSelectItemCategorie() {
		return selectItemCategorie;
	}
	public void setSelectItemCategorie(String selectItemCategorie) {
		this.selectItemCategorie = selectItemCategorie;
	}
	public String getSelectItemType() {
		return selectItemType;
	}
	public void setSelectItemType(String selectItemType) {
		this.selectItemType = selectItemType;
	}

    public int getNbContratEnCours() {
		return nbContratEnCours;
	}
	public void setNbContratEnCours(int nbContratEnCours) {
		this.nbContratEnCours = nbContratEnCours;
	}
	public int getNbContratFermeEnCours() {
		return nbContratFermeEnCours;
	}
	public void setNbContratFermeEnCours(int nbContratFermeEnCours) {
		this.nbContratFermeEnCours = nbContratFermeEnCours;
	}
	public int getNbContratRenouvelableEnCours() {
		return nbContratRenouvelableEnCours;
	}
	public void setNbContratRenouvelableEnCours(int nbContratRenouvelableEnCours) {
		this.nbContratRenouvelableEnCours = nbContratRenouvelableEnCours;
	}
	public int getNbContratRenouvelableAvantExpiration() {
		return nbContratRenouvelableAvantExpiration;
	}
	public void setNbContratRenouvelableAvantExpiration(int nbContratRenouvelableAvantExpiration) {
		this.nbContratRenouvelableAvantExpiration = nbContratRenouvelableAvantExpiration;
	}
	public int getNbContratRenouvelableExpirer() {
		return nbContratRenouvelableExpirer;
	}
	public void setNbContratRenouvelableExpirer(int nbContratRenouvelableExpirer) {
		this.nbContratRenouvelableExpirer = nbContratRenouvelableExpirer;
	}
	public int getNbContratFermeExpirer() {
		return nbContratFermeExpirer;
	}
	public void setNbContratFermeExpirer(int nbContratFermeExpirer) {
		this.nbContratFermeExpirer = nbContratFermeExpirer;
	}
	public int getNbContratFermeAvantExpiration() {
		return nbContratFermeAvantExpiration;
	}
	public void setNbContratFermeAvantExpiration(int nbContratFermeAvantExpiration) {
		this.nbContratFermeAvantExpiration = nbContratFermeAvantExpiration;
	}
	private List<Contrat> listContratFerme;//tous les contrats fermes
	public List<Contrat> getListContratFerme() {
		return listContratFerme;
	}
	public void setListContratFerme(List<Contrat> listContratFerme) {
		this.listContratFerme = listContratFerme;
	}
	public List<Contrat> getListContratFermeEnCours() {
		return listContratFermeEnCours;
	}
	public void setListContratFermeEnCours(List<Contrat> listContratFermeEnCours) {
		this.listContratFermeEnCours = listContratFermeEnCours;
	}
	public List<Contrat> getListContratFermeExpirer() {
		return listContratFermeExpirer;
	}
	public void setListContratFermeExpirer(List<Contrat> listContratFermeExpirer) {
		this.listContratFermeExpirer = listContratFermeExpirer;
	}
	public List<Contrat> getListContratFermeAvantExpiration() {
		return listContratFermeAvantExpiration;
	}
	public void setListContratFermeAvantExpiration(List<Contrat> listContratFermeAvantExpiration) {
		this.listContratFermeAvantExpiration = listContratFermeAvantExpiration;
	}
	public List<Contrat> getListContratRenouvelable() {
		return listContratRenouvelable;
	}
	public void setListContratRenouvelable(List<Contrat> listContratRenouvelable) {
		this.listContratRenouvelable = listContratRenouvelable;
	}
	public List<Contrat> getListContratRenouvelableEnCours() {
		return listContratRenouvelableEnCours;
	}
	public void setListContratRenouvelableEnCours(List<Contrat> listContratRenouvelableEnCours) {
		this.listContratRenouvelableEnCours = listContratRenouvelableEnCours;
	}
	public List<Contrat> getListContratRenouvelableAvantExpiration() {
		return listContratRenouvelableAvantExpiration;
	}
	public void setListContratRenouvelableAvantExpiration(List<Contrat> listContratRenouvelableAvantExpiration) {
		this.listContratRenouvelableAvantExpiration = listContratRenouvelableAvantExpiration;
	}
	public List<Contrat> getListContratRenouvelableExpirer() {
		return listContratRenouvelableExpirer;
	}
	public void setListContratRenouvelableExpirer(List<Contrat> listContratRenouvelableExpirer) {
		this.listContratRenouvelableExpirer = listContratRenouvelableExpirer;
	}
	public String getSelectItemUnite() {
		return selectItemUnite;
	}
	public void setSelectItemUnite(String selectItemUnite) {
		this.selectItemUnite = selectItemUnite;
	}
	public int getNbContratExpirer() {
		return nbContratExpirer;
	}
	public void setNbContratExpirer(int nbContratExpirer) {
		this.nbContratExpirer = nbContratExpirer;
	}
	public String getSelectItemPeriode() {
		return selectItemPeriode;
	}
	public void setSelectItemPeriode(String selectItemPeriode) {
		this.selectItemPeriode = selectItemPeriode;
	}
	public List<Contrat> getListContratRenouvelableAtteintPreavis() {
		return listContratRenouvelableAtteintPreavis;
	}
	public void setListContratRenouvelableAtteintPreavis(List<Contrat> listContratRenouvelableAtteintPreavis) {
		this.listContratRenouvelableAtteintPreavis = listContratRenouvelableAtteintPreavis;
	}
	public List<Contrat> getListContratRenouvelableAvantPreavis() {
		return listContratRenouvelableAvantPreavis;
	}
	public void setListContratRenouvelableAvantPreavis(List<Contrat> listContratRenouvelableAvantPreavis) {
		this.listContratRenouvelableAvantPreavis = listContratRenouvelableAvantPreavis;
	}
	public int getNbContratRenouvelableAtteintPreavis() {
		return nbContratRenouvelableAtteintPreavis;
	}
	public void setNbContratRenouvelableAtteintPreavis(int nbContratRenouvelableAtteintPreavis) {
		this.nbContratRenouvelableAtteintPreavis = nbContratRenouvelableAtteintPreavis;
	}
	public int getNbContratRenouvelableAvantPreavis() {
		return nbContratRenouvelableAvantPreavis;
	}
	public void setNbContratRenouvelableAvantPreavis(int nbContratRenouvelableAvantPreavis) {
		this.nbContratRenouvelableAvantPreavis = nbContratRenouvelableAvantPreavis;
	}
	public List<Contrat> getListContratAnnuler() {
		return listContratAnnuler;
	}
	public void setListContratAnnuler(List<Contrat> listContratAnnuler) {
		this.listContratAnnuler = listContratAnnuler;
	}
	public int getNbContratAnnuler() {
		return nbContratAnnuler;
	}
	public void setNbContratAnnuler(int nbContratAnnuler) {
		this.nbContratAnnuler = nbContratAnnuler;
	}
	public Historique getHistorique() {
		return historique;
	}
	public void setHistorique(Historique historique) {
		this.historique = historique;
	}
	public List<Historique> getListHistorique() {
		return listHistorique;
	}
	public void setListHistorique(List<Historique> listHistorique) {
		this.listHistorique = listHistorique;
	}
	public String getSelectItemRecherche() {
		return selectItemRecherche;
	}
	public void setSelectItemRecherche(String selectItemRecherche) {
		this.selectItemRecherche = selectItemRecherche;
	}

	public Date getDateD() {
		return dateD;
	}
	public void setDateD(Date dateD) {
		this.dateD = dateD;
	}
	public Date getDateF() {
		return dateF;
	}
	public void setDateF(Date dateF) {
		this.dateF = dateF;
	}
	public String getPrix() {
		return prix;
	}
	public void setPrix(String prix) {
		this.prix = prix;
	}

	public String getTypeRecherche() {
		return typeRecherche;
	}
	public void setTypeRecherche(String typeRecherche) {
		this.typeRecherche = typeRecherche;
	}

	public String getAnn() {
		return ann;
	}
	public void setAnn(String ann) {
		this.ann = ann;
	}
	public String getOb() {
		return ob;
	}
	public void setOb(String ob) {
		this.ob = ob;
	}

	public List<Contrat> getListRecherche() {
		return listRecherche;
	}
	public void setListRecherche(List<Contrat> listRecherche) {
		this.listRecherche = listRecherche;
	}
	public Categorie getCc() {
		return cc;
	}
	public void setCc(Categorie cc) {
		this.cc = cc;
	}
	public String getSelectItemCat() {
		return selectItemCat;
	}
	public void setSelectItemCat(String selectItemCat) {
		this.selectItemCat = selectItemCat;
	}
	public String getSelectItemCategorieUp() {
		return selectItemCategorieUp;
	}
	public void setSelectItemCategorieUp(String selectItemCategorieUp) {
		this.selectItemCategorieUp = selectItemCategorieUp;
	}
	public String getSelectItemTypeUp() {
		return selectItemTypeUp;
	}
	public void setSelectItemTypeUp(String selectItemTypeUp) {
		this.selectItemTypeUp = selectItemTypeUp;
	}
	public String getSelectItemUniteUp() {
		return selectItemUniteUp;
	}
	public void setSelectItemUniteUp(String selectItemUniteUp) {
		this.selectItemUniteUp = selectItemUniteUp;
	}
	public String getSelectItemPeriodeUp() {
		return selectItemPeriodeUp;
	}
	public void setSelectItemPeriodeUp(String selectItemPeriodeUp) {
		this.selectItemPeriodeUp = selectItemPeriodeUp;
	}
	public boolean isTestExist() {
		return testExist;
	}
	public void setTestExist(boolean testExist) {
		this.testExist = testExist;
	}
	public Cocontractant getCocontractant() {
		return cocontractant;
	}
	public void setCocontractant(Cocontractant cocontractant) {
		this.cocontractant = cocontractant;
	}
	public String getSelectItemCoco() {
		return selectItemCoco;
	}
	public void setSelectItemCoco(String selectItemCoco) {
		this.selectItemCoco = selectItemCoco;
	}
	public List<Cocontractant> getListAllCocon() {
		return listAllCocon;
	}
	public void setListAllCocon(List<Cocontractant> listAllCocon) {
		this.listAllCocon = listAllCocon;
	}
	public String getSelectItemCocoUp() {
		return selectItemCocoUp;
	}
	public void setSelectItemCocoUp(String selectItemCocoUp) {
		this.selectItemCocoUp = selectItemCocoUp;
	}
	public String getSelectCo() {
		return selectCo;
	}
	public void setSelectCo(String selectCo) {
		this.selectCo = selectCo;
	}
	public Cocontractant getCon() {
		return con;
	}
	public void setCon(Cocontractant con) {
		this.con = con;
	}
	public int getNbNotif() {
		return nbNotif;
	}
	public void setNbNotif(int nbNotif) {
		this.nbNotif = nbNotif;
	}
	public List<String> getNotification() {
		return notification;
	}
	public void setNotification(List<String> notification) {
		this.notification = notification;
	}
	public String getNow() {
		return now;
	}
	public void setNow(String now) {
		this.now = now;
	}

	public void setD(Date d) {
		this.d = d;
	}
	public String getDatee() {
		return datee;
	}
	public void setDatee(String datee) {
		this.datee = datee;
	}
	public List<Contrat> getListContratPourNotif() {
		return listContratPourNotif;
	}
	public void setListContratPourNotif(List<Contrat> listContratPourNotif) {
		this.listContratPourNotif = listContratPourNotif;
	}

public String ActionGoToUploadFileontroller() {
		
		init();
		
		return "index?faces-redirect=true";
		
	}

	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE
																													// fix.
			}
		}
		return null;
	}

	public void uploadExcelFile() throws IOException {

		int a = getFilename(fileUp).indexOf(".pdf");

		System.out.println("testttttttttttttttttttttt======");

		try {

			InputStream inputStream = fileUp.getInputStream();
			FileOutputStream outputStream = new FileOutputStream(getFilename(fileUp));
			System.out.println("file name ======" + getFilename(fileUp));

			if (a != -1) {

				byte[] buffer = new byte[4096];
				int bytesRead = 0;
				while (true) {
					bytesRead = inputStream.read(buffer);
					if (bytesRead > 0) {
						outputStream.write(buffer, 0, bytesRead);
					} else {
						break;
					}
				}
				outputStream.close();
				inputStream.close();
				//				fileUp.write("E:\\imf\\upload-imf\\ZTM\\" + getFilename(fileUp));
				fileUp.write("D:\\contratDoc\\" + getFilename(fileUp));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	public Part getFileUp() {
		return fileUp;
	}

	public void setFileUp(Part fileUp) {
		this.fileUp = fileUp;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}


	
	
	
	

}
