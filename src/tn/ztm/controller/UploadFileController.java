package tn.ztm.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.servlet.http.Part;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import isi.ztm.ztmcontrat.entite.Contrat;
import isi.ztm.ztmcontrat.serviceinterface.IContratService;


@Controller
@ManagedBean
@RequestScoped
public class UploadFileController implements Serializable {
	
	/**
	 * 
	 */
	@Autowired
	private IContratService contratService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Part fileUp;
	private Contrat contrat;

	/**
	 * 
	 */

	public void init() {

		contrat = new Contrat();
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


}
