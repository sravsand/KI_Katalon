package global

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.pdmodel.PDDocument
//import org.apache.pdfbox.text.PDFTextStripper
import com.kms.katalon.core.annotation.Keyword
import org.apache.pdfbox.util.PDFTextStripper
import org.apache.pdfbox.util.PDFTextStripperByArea;
import java.io.File
import java.net.URL as URL
import java.io.IOException
import internal.GlobalVariable

public class Pdf {

	@Keyword
	static def ReadPDF(String PDFURL) throws IOException{
		URL TestURL = new URL(PDFURL)
		BufferedInputStream bis = new BufferedInputStream(TestURL.openStream());
		PDDocument doc = PDDocument.load(bis);
		String pdfText = new PDFTextStripper().getText(doc);
		doc.close();
		bis.close();
		return pdfText
	}


	@Keyword
	static def ReadPDFFile(String fileName){
		PDDocument document = PDDocument.load(new File(fileName))

		document.getClass()

		PDFTextStripperByArea stripper = new PDFTextStripperByArea()
		stripper.setSortByPosition(true);

		PDFTextStripper tStripper = new PDFTextStripper()

		String pdfFileInText = tStripper.getText(document)

		return pdfFileInText
	}
}
