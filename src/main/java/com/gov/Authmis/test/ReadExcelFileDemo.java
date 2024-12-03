package com.gov.Authmis.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
public class ReadExcelFileDemo  
{  
	public static void main(String args[]) throws IOException  
	{  
		//obtaining input bytes from a file  
		FileInputStream fis=new FileInputStream(new File("C:\\Users\\w10\\Downloads\\DoIT001.xlsx"));  
		
		excelToTutorials(fis);
	}  
	
	public static void excelToTutorials(FileInputStream is) {
	    try {
	      Workbook workbook = new XSSFWorkbook(is);

	      Sheet sheet = workbook.getSheetAt(0);
	      Iterator<Row> rows = sheet.iterator();

	      //List<Tutorial> tutorials = new ArrayList<Tutorial>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        //Tutorial tutorial = new Tutorial();

	        int cellIdx = 0;
	        while (cellsInRow.hasNext()) {
	          Cell currentCell = cellsInRow.next();

	          switch (cellIdx) {
	          
	          
	          default:
	        	  System.out.println(currentCell.getStringCellValue());
	            break;
	          }

	          cellIdx++;
	        }

	        //tutorials.add(tutorial);
	      }

	      workbook.close();

	      //return tutorials;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
}  