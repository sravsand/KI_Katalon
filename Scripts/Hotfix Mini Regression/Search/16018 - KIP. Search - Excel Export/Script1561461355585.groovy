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

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myProjects.Validate as mVal
import myProjects.Action as mAct
import excel.Action as eAct
import myProjects.Action as pAct
import search.Validate as sVal
import models.Timesheet
import models.GenerateTimesheet
import buildingBlocks.createComponents

//setup pre-requisites
def thisWeek = Com.weekDates()
String[] newTimesheet = ["Project Innovation", "Project Management", "Testing Phase"]
String[] newHours = ["7", "Notes", "7", "", "7", "", "7", "Notes", "7", "", "", "", "", ""]
Timesheet timesheet = GenerateTimesheet.createTimesheet(thisWeek, newTimesheet, newHours)
createComponents.submitTimesheet(timesheet)

String searchItem = "Timesheet Submissions"
String searchVal = "All Submissions"

def tester = System.getProperty('user.name')
String browserFolder = Com.getBrowserType()
String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\" + browserFolder + "\\excel\\16018 - KIP_Search"
gAct.clearFolder(downloadPath)

Rep.nextTestStep("Log in V6")
	Act.keyedInProjectsPPMLoginAndVerifyChromeDriver(GVars.usrNme, GVars.pwordEncryt, GVars.user, downloadPath)
	

Rep.nextTestStep("Click onto [Search] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")

	
Rep.nextTestStep("Click on [Filter] dropdown")
	gObj.buttonClickSync(Const.search + "search_DropDown")
	
	
Rep.nextTestStep("Select an item from the [Drop-down] list within filter section i.e Timesheet Submissions t")
	gObj.buttonClickSubSync(Const.search + "filterType", searchItem, "label")
	
	String filterName = gObj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')
	gAct.findSubstringInStringAndReport(filterName, searchVal)

							
Rep.nextTestStep("Click on [Export] button ")
	gObj.buttonClickSync(Const.search + "a_Export")
			
		
Rep.nextTestStep("Open the downloaded excel file and verify the data ")
	gAct.Wait(2)
	if(GVars.browser == "MicrosoftEdge"){
		Com.copyDirectoryFiles("C:\\Downloads\\temp\\" + browserFolder, downloadPath)
		gAct.Wait(GVars.shortWait)
	}
			
	String fileName = pAct.getFileNameInFolder(downloadPath)
	def ExportData = eAct.openAndReadFile(downloadPath + "\\" + fileName, "Timesheet Submissions")
		
	sVal.exportAgainstSearchTable(ExportData, Const.search + "searchResults", Const.search + "resultsHeader", Const.search + "resultsBody")
