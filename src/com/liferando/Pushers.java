package com.liferando;

import java.awt.Dimension;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.print.PrintServiceLookup;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.text.PDFTextStripper;

import com.fasterxml.jackson.databind.JsonNode;
import com.floreantpos.main.Application;
import com.lowagie.text.pdf.PdfDocument;

import io.github.jonathanlink.PDFLayoutTextStripper;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.standard.MediaTray;
import javax.print.attribute.standard.SheetCollate;
import javax.print.attribute.standard.Sides;
 

public class Pushers {

	public Pushers() {
		// TODO Auto-generated constructor stub
	}
	
	private boolean printa4;
	 
	String fileName;
	File folder = new File("C:\\Lieferando\\pdf");
	File destination = new File("C:\\Lieferando\\proceesed");
	public boolean Pdf_() throws InvalidPasswordException, IOException {
		
		File[] listOfFiles = folder.listFiles();
 
		boolean success=false;
		for (int j = 0; j < listOfFiles.length; j++) {
		  if (listOfFiles[j].isFile()) {
		    System.out.println("File " + listOfFiles[j].getName());		
		    fileName = listOfFiles[j].getName();
		    String[] chkFile= listOfFiles[j].getName().split("Bestellung-");
		    String orderNumber = chkFile[1].replaceAll(".pdf", "");
		    orderNumber = orderNumber.replaceAll(" ","").trim();
		    
		    
		    if (! destination.exists()){
		    	destination.mkdir();		          
		    }
		    
		    File f = new File(destination+"/"+new File(fileName));
            boolean print=false;
		    if(!f.exists()) {		     
		    	 System.out.println("orderNumber exist "+orderNumber);

		    	  String filePath = folder+"/"+listOfFiles[j].getName(); 
		    	  
		    	  PrintService service = PrintServiceLookup.lookupDefaultPrintService();
		    	  
		          String printerName=service.getName(); 
		          try {
					  print=printer(filePath,printerName);
		                   System.out.println("Auto printed of pdf file"+print);
				} catch (PrinterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//pass the all parameters.

		    	 File directory = destination;	    	     	 
	    			if (!directory.exists()) {
	    	  			directory.mkdirs();
	    	  		}
	    			
	    		if(print) {
	    			
		    	    File sourcedirectory = new File(folder+"/"+new File(fileName));
	    			File pdfCopy = new File(directory+"/"+fileName);
	    			 
	    			try {
						copyFile(sourcedirectory,pdfCopy);
						success =true;
					} catch (IOException e) {
						e.printStackTrace();
					}

	    		}//createOrder(orderNumber);
		  }  else {  
			  File sourcedirectory = new File(folder+"/"+new File(fileName));
			  sourcedirectory.delete();
				System.out.println("Ticket already exist");
			}
		  } 
	   }
		return true;
	}
	
	public static boolean printer(String filePath, String printerName)
			throws InvalidPasswordException, IOException, PrinterException {

		boolean flag = false;
		PDDocument document = PDDocument.load(new File(filePath));
		PrintService myPrintService = findPrintService(printerName);
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPageable(new PDFPageable(document));
		job.setPrintService(myPrintService);
		job.print();
		System.out.println("printed!!");
			flag = true;
		job = null;
		myPrintService = null;
		document.close(); 
		return flag;
	}

	private static PrintService findPrintService(String printerName) {
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(
				null, null);
		for (PrintService printService : printServices) {
			if (printService.getName().trim().equals(printerName)) {
				return printService;
			}
		}
		return null;
	}
	
	
	private void createOrder(String orderId) {

	}
	
	private static void copyFile(File sourceFile, File destinationFile) 
  		  throws IOException {
  		    try (InputStream in = new FileInputStream(sourceFile); 
  		      OutputStream out = new FileOutputStream(destinationFile)) {
  		        byte[] buf = new byte[1024];
  		        int length;
  		        while ((length = in.read(buf)) > 0) {
  		            out.write(buf, 0, length);
  		        }
  		    }
  		}

}
