import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Report2PDF {
	// Atributos
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String CONECTOR_BBDD = "jdbc:mysql://localhost/Jardineria?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String ARCHIVO_JRXML = "./src/Informes/facturaJardineria.jrxml";
	private static final String ARCHIVO_DESTINO = "./src/Informes/facturaJardineria.pdf";	
	
	public static void main(String[] args) {
		try {
			// Compilamos el archivo JRXML
			JasperReport jasperReport = JasperCompileManager.compileReport(ARCHIVO_JRXML);
			
			// Conectamos con la BBDD
			Class.forName(DRIVER);
			Connection conexion = DriverManager.getConnection(CONECTOR_BBDD, "usuario", "usuario");
			
			// Establecemos los parametros para el informe
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("CodigoPedido", 2);
			
			// Creamos la impresora para el informe
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conexion);
			
			// Establecemos toda la configuracion para que lo imprima
			JasperExportManager.exportReportToPdfFile(print, ARCHIVO_DESTINO);
			
			// Previsualiza el informe generado
	        JasperViewer.viewReport(print);
			
			// Cerramos la conexion
			conexion.close();
			
		} catch (JRException jrE) {
			System.err.println("Error de JasperReport.");
			jrE.printStackTrace();
		} catch (SQLException sqlE) {
			System.err.println("\nHubo problemas con la base de datos:");
			System.err.println("Mensaje:\t"+sqlE.getMessage());
			System.err.println("Estado SQL:\t"+sqlE.getSQLState());
			System.err.println("Codigo Error:\t"+sqlE.getErrorCode());
		} catch (ClassNotFoundException cnfE) {
			System.err.println("Error. Clase no encontrada.");
			cnfE.printStackTrace();
		} catch (Exception e) {
			System.err.println("ERROR inesperado:");
			e.printStackTrace();
		}
	}

}
