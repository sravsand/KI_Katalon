import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetupTestCase
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.WebDriverMethods as WebD
import search.Validate as sVal
import timesheets.Navigate as tNav
import java.awt.Robot;
import java.awt.event.KeyEvent

def tester = System.getProperty('user.name')
gAct.clearFolder("C:\\Downloads\\temp")

String browserFolder = Com.getBrowserType()
String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\" + browserFolder + "\\excel\\15991 - KIP_Report"

gAct.clearFolder(downloadPath)

Rep.nextTestStep("Shared steps 12112: KIP - V6 Stage Login page")
	Act.keyedInProjectsPPMLoginAndVerifyChromeDriver(GVars.usrNme, GVars.pwordEncryt, GVars.user, downloadPath)
	
	
Rep.nextTestStep("Click on to [Reports] located left hand side menu ")
	Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Reports", Const.dashBoard + "subMenu_ReportList", Const.reports + 'a_ReportFilter', "Reports")

	
Rep.nextTestStep("Click [Inline Menu] and select [Open] on to any displayed report i.e Action View")
	int rowNo = sVal.searchTableReturnRow(3, "Action View")
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	gObj.selectInlineOption(1)

	
	
Rep.nextTestStep("Click [Save and Run]")
	gObj.buttonClickSync(Const.reports + 'button_SaveAndRun')
	gAct.Wait(2)


Rep.nextTestStep("Verify the report details i.e name of the report , Title etc")
	String repName = gObj.getEditTextSync(Const.reportRunner + 'reportName')
	gAct.compareStringAndReport(repName, "Action View")
	
	String repTitle = gObj.getAttributeSync(Const.reportRunner + 'reportTitle', 'value')
	gAct.compareStringAndReport(repTitle, "Action View")

	
Rep.nextTestStep("Goto [Output] drop-down report type as Excel from [Report Options]")
	gObj.selectComboByLabelSync(Const.reportRunner + "reportOutput", "Excel")

Rep.nextTestStep("Click on [Run Report]")
	gObj.buttonClickSync(Const.reportRunner + "button_RunReport")	
	gAct.Wait(GVars.shortWait)
			
			
Rep.nextTestStep("Verify the browser default options are available for the downloaded file")
	Rep.info("unable to perform this step as Chrome browser download toolbar not recognised by Katalon")
		
		
Rep.nextTestStep("Click [Open]")
	Rep.info("File is not opened, but there is validation so see if it has been downloaded.")
	gAct.Wait(2)
				
	if(GVars.browser == "MicrosoftEdge" || GVars.browser == "Internet explorer"){
		Com.copyDirectoryFiles("C:\\Downloads\\temp", downloadPath)
	}
			
	File f = new File(downloadPath);
	ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
	if(names.size == 1){
		Rep.pass("File : " + names[0] + " has been downloaded.")
	}else{
		Rep.fail("File has not been downloaded.")
	}

