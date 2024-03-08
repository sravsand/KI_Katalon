import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.junit.After
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testdata.reader.ExcelFactory

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import keyedInProjects.Populate as Pop
import gantt.Populate as ganttPop
import gantt.Validate as ganttVal
import gantt.Action as ganttAct
import excel.Action as eAct
import myProjects.Action as pAct
import myProjects.Validate as pVal

import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD

def tester = System.getProperty('user.name')
gAct.clearFolder("C:\\Downloads\\temp")
String browserFolder = Com.getBrowserType()
String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\" + browserFolder + "\\excel\\16054 - KIP_Resource"
gAct.clearFolder(downloadPath)


Rep.nextTestStep("Log in V6")
	Act.keyedInProjectsPPMLoginAndVerifyChromeDriver(GVars.usrNme, GVars.pwordEncryt, GVars.user, downloadPath)

	
Rep.nextTestStep("Click onto [My Project] from side menu bar")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	gAct.Wait(1)
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', 'Tech Crunch Web Services Deployment')
	
	
Rep.nextTestStep("Click onto [Resource] tab")
	Nav.menuItemAndValidateURL(Const.myProjects + "menu_Resource", "MVC-PRL1-STRATEGICPLAN?")
	
	boolean chk = WebUI.verifyElementChecked(findTestObject(Const.resource + 'chk_Assignment'), 5, FailureHandling.OPTIONAL)
	if(chk == true){
		Rep.info("Assignments state : " + chk)
		gObj.buttonClickSync(Const.resource + "label_Assignments")
		gObj.buttonClickSync(Const.resource + "a_Update Results")
		gAct.Wait(2)
		Rep.screenshot("Update results clicked.")
		gObj.buttonClickSync(Const.resource + "a_SearchFilter_sidePanel_minimise")
		gAct.Wait(2)
	}
		
	chk = WebUI.verifyElementChecked(findTestObject(Const.resource + 'chk_Assignment'), 5, FailureHandling.OPTIONAL)
		
	gAct.compareBooleanAndReport(chk, false)
		
			
Rep.nextTestStep("Click on [Export] button")
	gObj.buttonClickSync(Const.resource + "a_Export")
			
			
Rep.nextTestStep("Verify the browser default options on the downloaded file")
	Rep.info("unable to perform this step as Chrome browser download toolbar not recognised by Katalon")
			
			
Rep.nextTestStep("Click [Open] (or save the file into your machine)")
	Rep.info("Cannot click on the browser download menu")
	gAct.Wait(2)
				
	if(GVars.browser == "MicrosoftEdge"){
		Com.copyDirectoryFiles("C:\\Downloads\\temp", downloadPath)
	}
			
	String fileName = pAct.getFileNameInFolder(downloadPath)
	def financeExportData = eAct.openAndReadFile(downloadPath + "\\" + fileName, "Resource")
	
			
Rep.nextTestStep("Verify the data")
	pVal.exportAgainstTable_Resource(financeExportData, Const.resource + "ResourceTable", Const.resource + "tableHeader", Const.resource + "tableCell")
