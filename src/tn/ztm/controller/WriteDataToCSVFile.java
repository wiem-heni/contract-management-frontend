package tn.ztm.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import isi.ztm.ztmcontrat.serviceinterface.IExportService;

@Controller
@ManagedBean
@RequestScoped
public class WriteDataToCSVFile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IExportService exportService;

	public WriteDataToCSVFile() {
		// TODO Auto-generated constructor stub
	}

public void ActionExport() {
	
	exportService.WriteDataToCSVFile();
	
	
}	
	
	
	
}
