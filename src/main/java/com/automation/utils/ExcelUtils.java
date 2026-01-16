package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	private static Workbook workbook;
	private static Sheet sheet;

	public ExcelUtils() {
		// Private constructor to prevent instantiation
	}

	public static void loadExcel(String filePath, String sheetName) throws IOException {
		FileInputStream file = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(file);
		sheet = workbook.getSheet(sheetName);
	}

	public static String getCellData(int rowNum, int colNum) {
		Cell cell = sheet.getRow(rowNum).getCell(colNum);

		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else if (cell.getCellType() == CellType.BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else {
			return "";
		}
	}
	
	public static int getRowCount() {
		return sheet.getPhysicalNumberOfRows();
	}

	public static int getColumnCount() {
		return sheet.getRow(0).getPhysicalNumberOfCells();
	}

	public static void closeWorkbook() {
		try {
			if (workbook != null) {
				workbook.close();
			}
		} catch (IOException e) {
			Log.error("IOException while closing workbook: " + e.getMessage());
		}
	}
	
}
