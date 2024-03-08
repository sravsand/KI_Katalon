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


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click onto [Project] Icon - world sign top right corner")
	gObj.buttonClickSync(Const.projects + 'projects')
	
	
Rep.nextTestStep("Select the [Filter] option tab")
	gObj.buttonClickSync(Const.projects + 'filter')
	String selection = "Active Projects"
	String filterValue = gObj.getEditTextSync(Const.projects + 'a_CurrentFilter')
	if(filterValue != selection){
		gObj.buttonClickSync(Const.projects + 'currentFilter_Cmb')
		gAct.Wait(1)
		gObj.buttonClickSubSync(Const.projects + 'a_SelectCurrentFilter', selection, "selection")
		gAct.Wait(1)
	}
	

Rep.nextTestStep("Click the [Home] icon on the desired project that the user would like to add to Favourites")
	def titleList = WebD.listObject("//ul[@id='flyoutProjects']/li/div/div[2]")
	String projectChoice = WebD.getSpecificElementTextFromList(titleList, 0)
	
	def homeList = WebD.listObject("//a[@id='Home']/i")
	WebD.clickSpecificElementFromList(homeList, 0)

	String currentProject = gObj.getEditTextSync(Const.myProjects + 'currentProject')
	
	gAct.compareStringAndReport(currentProject, projectChoice)
	

Rep.nextTestStep("Click on [Settings Inline menu] (arrow sign) located at the top right hand side of the screen")
	gObj.buttonClickSync(Const.projects + 'Project_dashboardEdit')


Rep.nextTestStep("Click on [Add to favourites] (star icon)")
	gObj.buttonClickSync(Const.projects + 'a_AddToFavourites')
	WebUI.waitForElementNotVisible(findTestObject(Const.projects + 'AddedToFavourites'), 10, FailureHandling.OPTIONAL)


Rep.nextTestStep("Click on the [Projects] icon located at the top right hand side of the main menu bar")
	gObj.buttonClickSync(Const.projects + 'projects')


Rep.nextTestStep("Select the [Favourites] option tab")
	gObj.buttonClickSync(Const.projects + 'favourites')

	
Rep.nextTestStep("Verify the recently added project is shown in the list of [Favourites]")
	String FavValue = gObj.getEditTextSync(Const.projects + 'FavouriteList')
	
	if(GVars.browser == "MicrosoftEdge"){
		String newFavValue = FavValue.replaceAll("\n", "")
		newFavValue = newFavValue.replaceAll(" ", "")
		String newprojectChoice = projectChoice.replaceAll(" ", "")
		gAct.compareStringAndReport(newFavValue, newprojectChoice)
	}else{
		gAct.compareStringAndReport(FavValue, projectChoice)
	}
	
	
Rep.nextTestStep("Click on the [Star icon] located at the end of the project name")
	def favTitleList = WebD.listObject("//ul[@id='flyoutProjects']/li/div/div[2]")
	def removeList = WebD.listObject("//ul[@id='flyoutProjects']/li/div/div[2]/a/i")

	for(int intCnt = 0; intCnt < favTitleList.size(); intCnt ++){
		String favProjectChoice = WebD.getSpecificElementTextFromList(favTitleList, intCnt)
		if(GVars.browser == "MicrosoftEdge"){
			favProjectChoice = favProjectChoice.replace("  ", " ")
		}
		
		if(favProjectChoice == projectChoice){
			WebD.clickSpecificElementFromList(removeList, intCnt)
			WebUI.waitForElementNotVisible(findTestObject(Const.projects + 'AddedToFavourites'), 10, FailureHandling.OPTIONAL)
			break
		}		
	}


Rep.nextTestStep("Verify the project no longer exist under the [Favourites] tab")
	gObj.buttonClickSync(Const.projects + 'favourites')
	String favResult = gObj.getEditTextSync(Const.projects + 'favouriteNoResult')
	gAct.findSubstringInStringAndReport(favResult, "No Results")
	