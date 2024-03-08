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

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import keyedInProjects.Populate as Pop
import gantt.Populate as ganttPop
import gantt.Validate as ganttVal
import gantt.Action as ganttAct

import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.WebDriverMethods as WebD
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory

Rep.nextTestStep("Log in to the environment under test")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	
Rep.nextTestStep("Click onto [My Project] from side menu bar")
	WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "My Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', 'Tech Crunch Web Services Deployment')

	
Rep.nextTestStep("Click onto [Tasks] tab")
	WebUI.click(findTestObject(Const.myProjects + "menu_Tasks"))
	ganttAct.checkoutCurrentProject()

	
Rep.nextTestStep("Click on [Manage Version] icon from toolbar")
	gAct.Wait(1)
	String ganttStatus =  WebUI.getText(findTestObject(Const.gantt + 'ganttStatus'))
	WebUI.click(findTestObject(Const.ganttToolbar + 'managePlanVers'))
	gAct.Wait(1)

	
Rep.nextTestStep("Verify the [Version] , [Name] and [Status] column headers are displayed")
	 ganttVal.oneParamObjectValue(Const.ganttManageVersion + 'projectVersionCellHeader', 'col', 1, "Version")
	 ganttVal.oneParamObjectValue(Const.ganttManageVersion + 'projectVersionCellHeader',  'col', 2, "Name")
	 ganttVal.oneParamObjectValue(Const.ganttManageVersion + 'projectVersionCellHeader',  'col', 3, "Status")	 


Rep.nextTestStep("Verify the [Name] field is [Hyperlink]")
	String name
	String status
	ganttVal.twoParamObjectValue(Const.ganttManageVersion + 'projectVersionCellBody', 'row', 1, 'col', 2, '(No Description)')
	
	String val = WebUI.getText(findTestObject(Const.ganttManageVersion + 'projectVersionCellBody',[('row'): 1, ('col'): 4]))
	if(val == 'Select'){
		name = WebUI.getText(findTestObject(Const.ganttManageVersion + 'projectVersionCellBody',[('row'): 1, ('col'): 2]))
		status = WebUI.getText(findTestObject(Const.ganttManageVersion + 'projectVersionCellBody',[('row'): 1, ('col'): 3]))
		if(name == "(No Description)"){
			name = ""
		}else{
			name = name + " "
		}
	}else{
		name = WebUI.getText(findTestObject(Const.ganttManageVersion + 'projectVersionCellBody',[('row'): 2, ('col'): 2]))
		status = WebUI.getText(findTestObject(Const.ganttManageVersion + 'projectVersionCellBody',[('row'): 2, ('col'): 3]))
		if(name == "(No Description"){
			name = ""
		}else{
			name = name + " "
		}
	}
	
	boolean test = WebUI.verifyElementPresent(findTestObject(Const.ganttManageVersion + 'projectVersionSelectedUrl', [('link'): '(No Description)']), 10)
	def linkURI = WebD.getAttributeValueByPosition("versionNameLink", "baseURI", 0)
	gAct.compareStringAndReport(linkURI, "http://localhost/Projects/Dashboard/PageIndex/MVC-PRL1-TASKS?codes=PRJ007&SetBreadCrumb=True")


Rep.nextTestStep("Click on [Select] next to plan name")
	WebUI.click(findTestObject(Const.ganttManageVersion + 'versionSelectButton'))
	ganttAct.refresh()
	
	String newGanttStatus =  WebUI.getText(findTestObject(Const.gantt + 'ganttStatus'))
	gAct.compareStringAndReport(newGanttStatus, name + "(" + status + ")")

