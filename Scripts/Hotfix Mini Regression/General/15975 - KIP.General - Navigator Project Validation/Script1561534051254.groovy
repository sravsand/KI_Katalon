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

String parentName = 'All Company'
String customerName  = 'Customer Projects'
String companyName = 'PSA Solutions Inc'
String ProjectName = 'Project Innovation'


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click onto [Project] Icon - world sign top right corner")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	Act.changeProject(parentName, customerName, companyName, ProjectName)
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyWork", Const.dashBoard + 'currentPageHeader', "My Work")
	gObj.buttonClickSync(Const.projects + 'projects')
		
	
Rep.nextTestStep("Verify the options displayed under [Projects] navigator (Note: Hovering the mouse over the world icon displays the name as 'Projects')")
	gObj.verifyVisibleAndReport(Const.projects + 'favourites')
	gObj.verifyVisibleAndReport(Const.projects + 'filter')
	gObj.verifyVisibleAndReport(Const.projects + 'history')
	gObj.verifyVisibleAndReport(Const.projects + 'projectStructure')


Rep.nextTestStep("Click onto the [Favourites] icon marked as a star within the navigator")
	gObj.buttonClickSync(Const.projects + 'favourites')
	String favResult = gObj.getEditTextSync(Const.projects + 'favouriteNoResult')
	gAct.findSubstringInStringAndReport(favResult, "No Results")
	
	
Rep.nextTestStep("Click onto [Filter] Icon")
	gObj.buttonClickSync(Const.projects + 'filter')
	gAct.Wait(1)
	

Rep.nextTestStep("Verify that a dropdown field is visible to select the required filter")
	gObj.verifyVisibleAndReport(Const.projects + 'a_CurrentFilter')


Rep.nextTestStep("Verify the [Active Projects] filter by selecting it (if not already selected by default)")
	String selection = "Active Projects"
	String filterValue = gObj.getEditTextSync(Const.projects + 'a_CurrentFilter')
	if(filterValue != selection){
		gObj.buttonClickSync(Const.projects + 'currentFilter_Cmb')
		gObj.buttonClickSubSync(Const.projects + 'a_SelectCurrentFilter', selection, "selection")
	}
	gAct.Wait(GVars.shortWait)
	String filterResults = gObj.getEditTextSync(Const.projects + 'filterResults')
	
	gAct.findSubstringInStringAndReport(filterResults, "Arch Now Web Services Implementation")
	gAct.findSubstringInStringAndReport(filterResults, "Barnsley FC")
	gAct.findSubstringInStringAndReport(filterResults, "DCJ Tech Service Delivery")
	gAct.findSubstringInStringAndReport(filterResults, "EDEfficiency Solution")
	gAct.findSubstringInStringAndReport(filterResults, "Efficiency Solution")

	
Rep.nextTestStep("Click on the [Filter] dropdown and select [All Projects]")
	gObj.buttonClickSync(Const.projects + 'currentFilter_Cmb')
	gObj.buttonClickSubSync(Const.projects + 'a_SelectCurrentFilter', "All Projects", "selection")
	gAct.Wait(GVars.shortWait)
	filterResults = gObj.getEditTextSync(Const.projects + 'filterResults')
	
	gAct.findSubstringInStringAndReport(filterResults, "Administrative")
	gAct.findSubstringInStringAndReport(filterResults, "All Company")
	gAct.findSubstringInStringAndReport(filterResults, "Arch Now Web Services Implementation")
	gAct.findSubstringInStringAndReport(filterResults, "Barnsley FC")
	gAct.findSubstringInStringAndReport(filterResults, "Championship")
	
	
Rep.nextTestStep("Click on the [History] Icon")
	gObj.buttonClickSync(Const.projects + 'history')
	gAct.Wait(GVars.shortWait)
	filterResults = gObj.getEditTextSync(Const.projects + 'filterResults')
	gAct.findSubstringInStringAndReport(filterResults, ProjectName)
		
	
Rep.nextTestStep("Click on the [Heirarchy] Icon")
	gObj.buttonClickSync(Const.projects + 'projectStructure')
	gAct.Wait(GVars.shortWait)
	String hierarchyValue = gObj.getEditTextSync(Const.projects + 'currentProjectHierachy')
	
	if(GVars.browser == "MicrosoftEdge"){
		gAct.cleanUpCompareStringAndReport(hierarchyValue, parentName + "\n" + customerName + "\n" + companyName + "\n" + ProjectName)
	}else{
		gAct.findSubstringInStringAndReport(hierarchyValue, parentName + "\n" + customerName + "\n" + companyName)
		gAct.findSubstringInStringAndReport(hierarchyValue, ProjectName)
//		gAct.compareStringAndReport(hierarchyValue, parentName + "\n" + customerName + "\n" + companyName + "\n" + ProjectName)
	}
