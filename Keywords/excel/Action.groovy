package excel

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testdata.reader.ExcelFactory
import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent
import global.Action as gAct
import internal.GlobalVariable as GVars

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*
import org.openqa.selenium.Alert
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import java.lang.String

public class Action {

	@Keyword
	static def writeToCellInExcel(String filePath, String sheetName, int rowCnt, String columnName, String value){
		FileInputStream file = new FileInputStream (new File(filePath))
		XSSFWorkbook workbook = new XSSFWorkbook(file)
		XSSFSheet sheet = workbook.getSheet(sheetName)

		def col = getColumnNumberbyHeaderValue(sheet, columnName)

		sheet.getRow(rowCnt).createCell(col).setCellValue(value)

		file.close()
		FileOutputStream outFile =new FileOutputStream(new File(filePath));
		workbook.write(outFile);
		outFile.close();

		gAct.Wait(2)
	}


	@Keyword
	static def writeToCellInExcelColNo(String filePath, String sheetName, int rowCnt, int col, String value){
		FileInputStream file = new FileInputStream (new File(filePath))
		XSSFWorkbook workbook = new XSSFWorkbook(file)
		XSSFSheet sheet = workbook.getSheet(sheetName)

		sheet.getRow(rowCnt).createCell(col).setCellValue(value)

		file.close()
		FileOutputStream outFile =new FileOutputStream(new File(filePath));
		workbook.write(outFile);
		outFile.close();

		gAct.Wait(2)
	}


	@Keyword
	static def writeToCellInExcelXLS(String filePath, String sheetName, int rowCnt, int col, String value){
		FileInputStream file = new FileInputStream (new File(filePath))

		HSSFWorkbook workbook = new HSSFWorkbook(file)
		HSSFSheet sheet = workbook.getSheet(sheetName)

		sheet.getRow(rowCnt).createCell(col).setCellValue(value)

		file.close()
		FileOutputStream outFile =new FileOutputStream(new File(filePath));
		workbook.write(outFile);
		outFile.close();

		gAct.Wait(2)
	}


	@Keyword
	static def writeToCellInExcelXLSDate(String filePath, String sheetName, int rowCnt, int col, String value){
		FileInputStream file = new FileInputStream (new File(filePath))

		HSSFWorkbook workbook = new HSSFWorkbook(file)
		CreationHelper createHelper = workbook.getCreationHelper()
		HSSFSheet sheet = workbook.getSheet(sheetName)

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(
				createHelper.createDataFormat().getFormat("dd/MM/yyyy"))

		sheet.getRow(rowCnt).createCell(col).setCellValue(value)
		sheet.getRow(rowCnt).createCell(col).setCellStyle(cellStyle)

		file.close()
		FileOutputStream outFile =new FileOutputStream(new File(filePath));
		workbook.write(outFile);
		outFile.close();

		gAct.Wait(2)
	}


	@Keyword
	static def getColumnNumberbyHeaderValue(def sheet, String value){
		Iterator<Row> it = sheet.iterator();
		int column = 0
		Row row = it.next()
		Iterator<Cell> cellIter = row.cellIterator()
		while(cellIter.hasNext()) {
			String field1 = cellIter.next().getStringCellValue();

			if(field1 == value) {
				break
			}
			column++;
		}
		return column
	}


	@Keyword
	static def openAndReadFile(String excelFilePath, String sheetName){
		gAct.Wait(3)
		def xlData = ExcelFactory.getExcelDataWithDefaultSheet(excelFilePath, sheetName, false)
		return xlData
	}



	@Keyword
	static def uploadFile (String filePath) {
		WebUI.delay(3)
		StringSelection ss = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		WebUI.delay(1)
		Robot robot = new Robot()

		robot.keyPress(KeyEvent.VK_ENTER)
		robot.keyRelease(KeyEvent.VK_ENTER)
		robot.keyPress(KeyEvent.VK_CONTROL)
		robot.keyPress(KeyEvent.VK_V)
		//		robot.keyRelease(KeyEvent.VK_V)
		robot.keyRelease(KeyEvent.VK_CONTROL)
		robot.keyPress(KeyEvent.VK_ENTER)
		//		robot.keyRelease(KeyEvent.VK_ENTER)

		gAct.Wait(1)
	}
}
