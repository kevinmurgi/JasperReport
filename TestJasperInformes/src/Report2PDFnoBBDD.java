import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

public class Report2PDFnoBBDD {
	// Atributos
	private static final String ARCHIVO_JRXML = "./src/Informes/Leaf_Violet.jrxml";
	private static final String ARCHIVO_DESTINO = "./src/Informes/Leaf_Violet.pdf";
	
	// Main
	public static void main(String[] args) {
		try {
			// Primero se compila el archivo JRXML
			String reportSrcFile = ARCHIVO_JRXML;
	        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
	        
	        // Parametros para el informe
	        Map<String, Object> parameters = new HashMap<String, Object>();
	        
	        // Fuente de datos
	        JRDataSource dataSource = new JREmptyDataSource();
	        
	        // Print
	        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
	        
	        // Previsualiza el dise�o del informe
	        JasperDesignViewer.viewReportDesign(jasperReport);
	        
	        // Previsualiza el informe generado
	        JasperViewer.viewReport(print);
	        
	        // Esportador a PDF
	        JasperExportManager.exportReportToPdfFile(print, ARCHIVO_DESTINO);
			
		} catch (JRException jrE) {
			System.err.println("Error de JasperReport.");
			jrE.printStackTrace();
		} catch (Exception e) {
			System.err.println("ERROR inesperado:");
			e.printStackTrace();
		}
		
	}
}
