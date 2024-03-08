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


String ProjectName = 'Project Innovation'

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Click onto [My Projects] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', ProjectName)
	
	
Rep.nextTestStep("Click onto [Project] Icon - world sign top right corner")
	gObj.buttonClickSync(Const.projects + 'projects')
	
	
Rep.nextTestStep("Click on [History] Icon ")
	gObj.buttonClickSync(Const.projects + 'history')
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Click on a [Home] icon of the project that need adding to favourites")
	gObj.buttonClickSync(Const.projects + 'home')
	gAct.Wait(GVars.midWait)
	
	
Rep.nextTestStep("Click on to the [Edit] inline button ( Arrow --> sign top right corner )")
	gObj.buttonClickSync(Const.projects + 'Project_dashboardEdit')
	gAct.Wait(GVars.midWait)
	

Rep.nextTestStep("Click [Add to favourites]")
	gObj.buttonClickSync(Const.projects + 'a_AddToFavourites')
	WebUI.waitForElementNotVisible(findTestObject(Const.projects + 'AddedToFavourites'), GVars.longWait, FailureHandling.OPTIONAL)


Rep.nextTestStep("Click onto [Project] icon (world sign top right corner)")
	gObj.buttonClickSync(Const.projects + 'projects')
	gAct.Wait(GVars.shortWait)
	

Rep.nextTestStep("Click on to [Favourites] (star icon) and verify ")
	gObj.buttonClickSync(Const.projects + 'favourites')
	String FavValue = gObj.getEditTextSync(Const.projects + 'FavouriteList')
	gAct.compareStringAndReport(FavValue, ProjectName)
