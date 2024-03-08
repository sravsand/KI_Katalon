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

//String location_Short = "UK"
String location = "United Kingdom"
String pm_Code = "RES002"
String pm_Name = "Mary Allstar"
String desc = "some random text."


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	String suffix = Act.getBrowserSuffix()
	String project = suffix + 'Efficiency Solution'

Rep.nextTestStep("Click onto [My Projects] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	gAct.Wait(1)
	Act.changeProject('All Company', 'Customer Projects', 'PPM Solutions Inc', project)
	
	
Rep.nextTestStep("Click onto [Edit] inline button (top right corner) and select [open]")
	gObj.buttonClickSync(Const.projects + 'Project_dashboardEdit')
	
	gObj.buttonClickSync(Const.projectsMenu + 'a_Open')
	gAct.Wait(GVars.shortWait)

	
Rep.nextTestStep("Edit some details")
	gObj.buttonClickSync(Const.projectsMenu + 'locationDropdown')
	gObj.buttonClickSubSync(Const.projectsMenu + 'selectLocation', location, "location")	
	gAct.Wait(GVars.shortWait)
	
	WebD.clickElement("//a[contains(text(),'General')]")
	Com.edgeSync()
	
	if(GVars.browser != "Chrome"){
			
		if((GVars.browser == "Firefox") || (GVars.browser == "IE")){
			gObj.buttonClickSync(Const.projectsMenu + 'clear_PM')
		}
		
		gObj.buttonClickSync(Const.projectsMenu + 'pmDropDown')
		gAct.Wait(1)
	
		WebD.setEditText("//input[@id='s2id_autogen14_search']", pm_Code)
		
		WebUI.click(findTestObject(Const.projectsMenu + 'selectPM', [('pm'): pm_Name]))
	
	}
	gObj.setEditSync(Const.projectsMenu + 'textarea_Description', desc)
	gAct.Wait(1)
	

Rep.nextTestStep("Click through all the tabs on the popup window to verify")
	mVal.projectEditWindowTabNavigation_WorkflowsOn()

	
Rep.nextTestStep("Click [Save and Close ] (if applicable)")
	gObj.buttonClickSync(Const.projectsMenu + 'button_SaveAndClose')
	gAct.Wait(GVars.shortWait)
	gObj.buttonClickSync(Const.projects + 'Project_dashboardEdit')
	
	gObj.buttonClickSync(Const.projectsMenu + 'a_Open')
	
	
	if(GVars.browser != "Chrome"){
		if(GVars.browser == "MicrosoftEdge"){		
			mVal.edgeCellValue(Const.projectsMenu + 'a_PM', pm_Name)
		}else{
			gVal.objectText(Const.projectsMenu + 'a_PM', pm_Name)
		}
	}	
	
	if(GVars.browser == "MicrosoftEdge"){
		mVal.edgeCellValue(Const.projectsMenu + 'a_Location', location)
	}else{
		gVal.objectText(Const.projectsMenu + 'a_Location', location)
	}
	
	gVal.objectAttributeValue(Const.projectsMenu + 'textarea_Description', "value", desc)
		