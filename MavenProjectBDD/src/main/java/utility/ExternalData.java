package utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExternalData {

	String scenarioName;
	private static final String FILE_NAME = "D:\\CucumberBDD\\MavenProjectBDD\\Temp\\ExcelData.xlsx";
	private static Map<String,Object> currentSheetData = new HashMap<String,Object>();
	
	public ExternalData(String string){
		this.scenarioName = string;
	}
	
	public static  Map<String,Object> readData(String scenarioName,String runningSheetName){
		try {
			XSSFSheet sheet;
			int count = 0;
			File file = new File(FILE_NAME);
			FileInputStream excelFile = new FileInputStream(file);
			Workbook workbook  = new XSSFWorkbook(excelFile);

			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				String tableName = workbook.getSheetName(sheetIndex);
				if(runningSheetName.equalsIgnoreCase(tableName)) {
					sheet = (XSSFSheet) workbook.getSheet(tableName);
					for (int i = 0; i < sheet.getLastRowNum()+1; i++) {
						if(sheet.getRow(i).getCell(0).getStringCellValue().toString().equalsIgnoreCase(scenarioName) ) {
							for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
								if(sheet.getRow(i).getCell(0).getStringCellValue().toString().equalsIgnoreCase(scenarioName) ) {
									if (sheet.getRow(i).getCell(j).getCellTypeEnum() == CellType.STRING) {
										currentSheetData.put(sheet.getRow(0).getCell(j).getStringCellValue(), (String)sheet.getRow(i).getCell(j).getStringCellValue());
									} else if (sheet.getRow(i).getCell(j).getCellTypeEnum() == CellType.NUMERIC) {
										currentSheetData.put(sheet.getRow(0).getCell(j).getStringCellValue(), sheet.getRow(i).getCell(j).getNumericCellValue());
									}
								}
							}
						}
					}
				}
			}
			workbook.close();
			excelFile.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
		return currentSheetData;
	}
	
	
	public String currnData(String sheetName,String coloumName) {
		readData(scenarioName,sheetName);
		return (String)currentSheetData.get(coloumName);
		
		
	}
}
