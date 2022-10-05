package tn.ztm.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import isi.ztm.ztmcontrat.serviceinterface.IEmailService;

@Controller
@ManagedBean
@RequestScoped
public class EmailController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IEmailService emailService;

	public EmailController() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void actionSendEmailController() {
		
		emailService.sendEmail("elheniwiem20@gmail.com", "Test email  contrat");
		
	}

}
