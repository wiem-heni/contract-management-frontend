package tn.ztm.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import isi.ztm.ztmcontrat.serviceinterface.ICategorieService;
import isi.ztm.ztmcontrat.serviceinterface.ICocontractantService;
@Controller
@ManagedBean
@RequestScoped
public class Statistique implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	    private PieChartModel pieModel2;
	    private PieChartModel pieModel3;
	    private PieChartModel pieModel4;
	    private PieChartModel pieModel5;

	@Autowired
	private ICategorieService CategorieService;
	@Autowired
	private ICocontractantService cocontractantService;
	private List<Object[]>	list;
	private List<Object[]>	listPreavisCategoire;
	private List<Object[]>	listParCo;
	private List<Object[]>	listPreavisCo;

	
	  public Statistique() {
	
	}
	@PostConstruct
	    public void init() {
		  list= CategorieService.getNbContratParCategorie();
		  listPreavisCategoire=CategorieService.getNbContratPreavisParCategorie();
		  listParCo=cocontractantService.getNbContratParCo();
		  listPreavisCo=cocontractantService.getNbContratPreavisParCo();
	        pieModel2 = new PieChartModel();
	        pieModel3 = new PieChartModel();
	        pieModel4 = new PieChartModel();
	        pieModel5 = new PieChartModel();
	        createPieModel2();
	        createPieModel3();
	        createPieModel4();
	        createPieModel5();
	        

	  }
	  public String ActionGoToDashboard() {
	    	
	    	return "dashboard1?faces-redirect=true";
	   }
	 
	    public void createPieModel2() {
	list= CategorieService.getNbContratParCategorie();


	        for (Object[] obj : list) {
	        String att = String.valueOf(obj[1]);
	        Integer attINT = Integer.parseInt(att);
	        String x = String.valueOf(obj[0]);

		        pieModel2.set(x, attINT);

	        	
	        }
	        pieModel2.setTitle("Nombre contrat par catégorie");
	        pieModel2.setLegendPosition("e");
	        pieModel2.setFill(true);
	        pieModel2.setShowDataLabels(true);
	        pieModel2.setDiameter(150);

	    }
		 
	    public void createPieModel3() {
	   listPreavisCategoire=CategorieService.getNbContratPreavisParCategorie();

	        for (Object[] obj : listPreavisCategoire) {
	        String att = String.valueOf(obj[1]);
	        Integer attINT = Integer.parseInt(att);
	        String x = String.valueOf(obj[0]);

		        pieModel3.set(x, attINT);

	        	
	        }
	        pieModel3.setTitle("Nombre contrat atteint préavis par catégorie");
	        pieModel3.setLegendPosition("e");
	        pieModel3.setFill(true);
	        pieModel3.setShowDataLabels(true);
	        pieModel3.setDiameter(150);

	    }
	    public void createPieModel4() {
	    	 listParCo=cocontractantService.getNbContratParCo();
	        for (Object[] obj : listParCo) {
	        String att = String.valueOf(obj[1]);
	        Integer attINT = Integer.parseInt(att);
	        String x = String.valueOf(obj[0]);

		        pieModel4.set(x, attINT);

	        	
	        }
	        pieModel4.setTitle("Nombre contrat par cocontractant");
	        pieModel4.setLegendPosition("e");
	        pieModel4.setFill(true);
	        pieModel4.setShowDataLabels(true);
	        pieModel4.setDiameter(150);

	    }
	    public void createPieModel5() {
	    	listPreavisCo=cocontractantService.getNbContratPreavisParCo();
	    	for (Object[] obj : listPreavisCo) {
	        String att = String.valueOf(obj[1]);
	        Integer attINT = Integer.parseInt(att);
	        String x = String.valueOf(obj[0]);

		        pieModel5.set(x, attINT);

	        	
	        }
	        pieModel5.setTitle("Nombre contrat atteint préavis par cocontractant");
	        pieModel5.setLegendPosition("e");
	        pieModel5.setFill(true);
	        pieModel5.setShowDataLabels(true);
	        pieModel5.setDiameter(150);

	    }
	  
		public PieChartModel getPieModel2() {
			return pieModel2;
		}
		public void setPieModel2(PieChartModel pieModel2) {
			this.pieModel2 = pieModel2;
		}
		public List<Object[]> getList() {
			return list;
		}
		public void setList(List<Object[]> list) {
			this.list = list;
		}
		public List<Object[]> getListPreavisCategoire() {
			return listPreavisCategoire;
		}
		public void setListPreavisCategoire(List<Object[]> listPreavisCategoire) {
			this.listPreavisCategoire = listPreavisCategoire;
		}
		public List<Object[]> getListParCo() {
			return listParCo;
		}
		public void setListParCo(List<Object[]> listParCo) {
			this.listParCo = listParCo;
		}
		public List<Object[]> getListPreavisCo() {
			return listPreavisCo;
		}
		public void setListPreavisCo(List<Object[]> listPreavisCo) {
			this.listPreavisCo = listPreavisCo;
		}
		public PieChartModel getPieModel3() {
			return pieModel3;
		}
		public void setPieModel3(PieChartModel pieModel3) {
			this.pieModel3 = pieModel3;
		}
		public PieChartModel getPieModel4() {
			return pieModel4;
		}
		public void setPieModel4(PieChartModel pieModel4) {
			this.pieModel4 = pieModel4;
		}
		public PieChartModel getPieModel5() {
			return pieModel5;
		}
		public void setPieModel5(PieChartModel pieModel5) {
			this.pieModel5 = pieModel5;
		} 
	        
	        
	        
	        
	        
	        
	        




	
	     
	
	

}
