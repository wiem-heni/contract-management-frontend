package tn.ztm.controller;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import isi.ztm.ztmcontrat.serviceinterface.IDateService;
import isi.ztm.ztmcontrat.serviceinterface.IHistoriqueService;
import isi.ztm.ztmcontrat.entite.Historique;
@Controller
@ManagedBean
@RequestScoped
public class HistoriqueController {

	
	@Autowired
	private IDateService dateService;
	@Autowired
	private IHistoriqueService HistoriqueService;
	private List<Historique> listAllHistorique;
	private Historique historique;
	
	public HistoriqueController() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void init() {

		listAllHistorique = HistoriqueService.listAllHistorique();
		historique = new Historique();
	}
	public void ActionCreateHistoriqueController() {

       HistoriqueService.ajouterHistorique(historique);
	
	}
	public String ActionGoToListAllHistoriqueController() {

		init();

		return "historique?faces-redirect=true";}

	public List<Historique> getListAllHistorique() {
		return listAllHistorique;
	}
	public void setListAllHistorique(List<Historique> listAllHistorique) {
		this.listAllHistorique = listAllHistorique;
	}
	public Historique getHistorique() {
		return historique;
	}
	public void setHistorique(Historique historique) {
		this.historique = historique;
	}
	
	
	
	
	
	
	
}
