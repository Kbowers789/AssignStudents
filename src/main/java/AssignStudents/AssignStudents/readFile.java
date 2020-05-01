package AssignStudents.AssignStudents;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class readFile {
	
	private XSSFWorkbook wb;
	
	
	public void openFile(File f) throws IOException, InvalidFormatException {
		wb = new XSSFWorkbook(f);
	}
	
	public String[][] read() {
		// creating a sheet object to grab sheet data
		XSSFSheet sheet = wb.getSheetAt(0);
		
		int n = sheet.getLastRowNum();
		String[][] data = new String[n+1][5];
		
		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();

		// Traversing over each row of XLSX file
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			// For each row, iterate through each columns
			Iterator<Cell> cellIterator = row.cellIterator();

			String[] tempData = new String[row.getLastCellNum()];
			//if (tempData[0].contains("Student")) {
				//continue;
			//}
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				//will skip the columns (if any) after the initial 5 (Student Name + 4 Ranks)
				if (cell.getColumnIndex() < 5) {			
					// all cells should be strings, but just in case, willl cast any other types to a string before adding to temoData
					switch(cell.getCellType()) {
					case STRING:
						tempData[cell.getColumnIndex()] = cell.getStringCellValue();
						break;
					case NUMERIC:
						tempData[cell.getColumnIndex()] = String.valueOf(cell.getNumericCellValue());
						break;
					case BOOLEAN:
						tempData[cell.getColumnIndex()] = String.valueOf(cell.getBooleanCellValue());
						break;
					default :
					}
				}
			}
			// adding our row data to our full data matrix
			data[row.getRowNum()] = tempData;
			
		}
		return data;
	}
	
	public void close() throws IOException {
		wb.close();
	}
}