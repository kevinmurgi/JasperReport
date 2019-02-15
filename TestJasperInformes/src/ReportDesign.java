import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JRTextElement;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReportDesign {
	// Atributos
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String CONECTOR_BBDD = "jdbc:mysql://localhost/Jardineria?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String ARCHIVO_JRXML = "./src/Informes/facturaJardineria.jrxml";
	private static final String ARCHIVO_DESTINO = "./src/Informes/facturaJardineria.pdf";	
	
	public static void main(String[] args) {
		try {
			// Compilamos el archivo JRXML
			JasperDesign jasperDesign = JRXmlLoader.load(ARCHIVO_JRXML);
			JRBand[] bands = jasperDesign.getAllBands();
			JRQuery que = jasperDesign.getQuery();
			System.out.println(que.getText());
			
			for(JRBand banda : bands) {
				
				JRElement el = banda.getElementByKey("arbolcito");
				JRElement[] elementos = banda.getElements();
				for(JRElement elemento : elementos) {
					if(elemento.getUUID().toString().equals("f49d68fd-a2c7-44ec-a5fc-9300d10c85a1")) {
						JRTextElement txt = (JRTextElement) elemento;
						txt.setItalic(true);
						txt.setUnderline(true);
						txt.setFontSize(30);
						txt.setForecolor(Color.BLUE);
						txt.setBackcolor(Color.BLUE);
					}
					if(elemento.getUUID().toString().equals("d47168ce-aae1-4efc-8d24-e15195025a5a")) {
						JRImage img = (JRImage) elemento;
						System.out.println(img.getScaleImageValue().toString());
						img.setLazy(true);
						img.setWidth(400);
					}
				}
			}
			
			
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			
			
			// Conectamos con la BBDD
			Class.forName(DRIVER);
			Connection conexion = DriverManager.getConnection(CONECTOR_BBDD, "usuario", "usuario");
			
			// Establecemos los parametros para el informe
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("CodigoPedido", 2);
			
			// Creamos la impresora para el informe
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conexion);
			
			// Establecemos toda la configuracion para que lo imprima
//			JasperExportManager.exportReportToPdfFile(print, ARCHIVO_DESTINO);
			
			// Previsualiza el informe generado
	        JasperViewer.viewReport(print);
			
			// Cerramos la conexion
			conexion.close();
			
		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
