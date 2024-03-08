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
import com.kms.katalon.core.webui.driver.DriverFactory

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
import timesheets.Navigate as tNav

String browserVer = DriverFactory.getExecutedBrowser()
String browser = Com.getBrowserType()
String newReportName = "16001 - Report 8" + browser
String newRepTitle = "16001 - Report New Title 8" + browser

Rep.nextTestStep("Shared steps 12112: KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click on to [Reports] located left hand side menu ")
	Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Reports", Const.dashBoard + "subMenu_ReportList", Const.reports + 'a_ReportFilter', "Reports")
	
	
Rep.nextTestStep("Click [+Add]")
	gObj.buttonClickSync(Const.reports + 'button_Add')


Rep.nextTestStep("Input some data for the report in the [Name] field")
	gObj.setEditSync(Const.reports + 'newReportName', newReportName)


Rep.nextTestStep("Enter some data in [Title] field")
	gObj.setEditSync(Const.reports + 'newReportTitle', newRepTitle)


Rep.nextTestStep("Tick the check box for [Log Report Usage]")
	gObj.checkSync(Const.reports + 'chkLogUsage')


Rep.nextTestStep("Click [Definition] tab")
	gObj.buttonClickSync(Const.reports + 'DefinitionTab')
	
	
Rep.nextTestStep("Select a data view as > Action View Data (v6.0.0)")
	gObj.selectComboByIndexSync(Const.reports + 'select_DataView', 3)

	
Rep.nextTestStep("Verify [Report Type] and [Usage Type] are auto populated as default ( can be changed)")
	String type = gObj.getAttributeSync(Const.reports + 'select_ReportType', 'value')
	gAct.compareStringAndReport(type, "1") //1 is index option - Tabular

	String usage = gObj.getAttributeSync(Const.reports + 'select_ReportUsage', 'value')
	gAct.compareStringAndReport(usage, "1") //1 is index option - Report

	
Rep.nextTestStep("Select / Tick the following options from various sections within that window")
	gObj.checkSync(Const.reports + 'chkActionViewName')
	gObj.checkSync(Const.reports + 'chkActionViewActive')
	gObj.checkSync(Const.reports + 'chkActionViewDescr')
	gObj.checkSync(Const.reports + 'chkViewType')
	gObj.checkSync(Const.reports + 'chkActionViewLastEdit')
	gObj.checkSync(Const.reports + 'chkDefaultActionTypeCode')
	gObj.checkSync(Const.reports + 'chkDefaultActionTypeName')
	
	
Rep.nextTestStep("Click [Save and Run]")
	gObj.buttonClickSync(Const.reports + 'button_SaveAndRun')

	
Rep.nextTestStep("Verify the report details i.e name of the report , Title etc")
	gAct.Wait(3)
	String repName = gObj.getAttributeSync(Const.reports + 'newReportName', 'value')
	String repTitle = gObj.getAttributeSync(Const.reportRunner + 'reportTitle', 'value')	
	gAct.compareStringAndReport(repName, newReportName)		
	gAct.compareStringAndReport(repTitle, newRepTitle)

	
Rep.nextTestStep("Select PDF from [Output] dropdown within [Report Options]")
	gObj.selectComboByLabelSync(Const.reportRunner + "reportOutputNew", "PDF")

	
	if(browserVer != "Chrome (headless)"){
	
		Rep.nextTestStep("Click on [Run Report]")
			gObj.buttonClickSync(Const.reportRunner + "button_RunReport")
			gAct.Wait(GVars.midWait)
			
			
		Rep.nextTestStep("Verify the browser default options are available for the downloaded file")
			Rep.info("unable to perform this step as Chrome browser download toolbar not recognised by Katalon")
			WebUI.switchToWindowIndex(1)
			gAct.Wait(GVars.shortWait)
			String pdfUrl = WebUI.getUrl()
			gAct.compareStringAndReport(pdfUrl, GVars.ppmUrl + "KIPWebPortal/reporting/rep_results.aspx?Output=PDF")
			Rep.screenshot("Verify the browser default options are available for the downloaded file")
			WebUI.closeWindowIndex(1)
			WebUI.switchToWindowIndex(0)
	

		Rep.nextTestStep("Click [Save]  - if applicable")
			Rep.info("unable to perform this step as Chrome browser download toolbar not recognised by Katalon")
			Rep.screenshot("Click [Save]  - if applicable")
	}else{
		Rep.info("Chrome headless browser doesn't recognise report popup")
	}
	
	
Rep.nextTestStep("Navigate back to [Report] screen and verify")
	gObj.buttonClickSync(Const.reportRunner + "button_Cancel")
	gObj.buttonClickSync(Const.reports + 'button_Close')
	gObj.buttonClickSync(Const.reports + "a_Add Row")
	gObj.buttonClickSync(Const.reports + "a_Field")

	gObj.buttonClickSubSync(Const.reports + "selectFieldValueGeneric", "Report Name", "value")
	gObj.buttonClickSync(Const.reports + "a_Type")
	gObj.buttonClickSync(Const.reports + "comparisonType")
	gObj.setEditSync(Const.reports + "searchFieldValue", newReportName)
	gObj.buttonClickSync(Const.reports + 'a_Search')
	gAct.Wait(2)
	
	String actReportName = WebD.getElementText("//div[@id='8062f4cf-83ce-48d3-b96c-5047313b7357']/div[5]/table/tbody/tr/td[3]")
	gAct.compareStringAndReport(actReportName, newReportName)
	
	
Rep.nextTestStep("Verify if the report Action data is correct")
	

Rep.nextTestStep("Verify if the report Project data is correct")

