import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestPOI {
    public static void main(String[] args) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            System.out.println("POI cargado correctamente.");
        } catch (Exception e) {
        }
    }
}
