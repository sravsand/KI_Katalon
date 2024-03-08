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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.Keys as Keys
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
import global.Validate as gVal
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myProjects.Validate as mVal
import myProjects.Action as mAct
import search.Action as sAct
import search.Validate as sVal

String searchItem = "Tasks"
String searchVal = "Task"

Rep.nextTestStep("KIP - V6 Stage Login page")
Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String postText = "Some post text 21900"
	Nav.selectSearchFilter(searchItem)
		gAct.Wait(1)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "inlineEdit_Alt", 2, "row")
	WebD.clickElement("//div[contains(@class,'btn-group btn-group-xs shiny open')]//li[2]//a[1]")
	gAct.Wait(1)
	WebD.switchToActive()
	gObj.buttonClickSync(Const.addPost + "textarea_Post")
	WebD.setEditText("//textarea[@id='Text']", postText)
	gAct.Wait(1)
	WebUI.switchToDefaultContent()
	gObj.buttonClickSync(Const.addPost + "input_Resource")
	gObj.buttonClickSubSync(Const.addPost + "select_Resources", GVars.user, "name")
	gObj.buttonClickSync(Const.addPost + "button_Add")
	
	boolean elementVisible = WebUI.verifyElementPresent(findTestObject(Const.timesheetSaved + 'TimeSavedSuccessfully'), GVars.longWait, FailureHandling.OPTIONAL)
	if (elementVisible){
		WebUI.waitForElementNotPresent(findTestObject(Const.timesheetSaved + 'TimeSavedSuccessfully'), 10, FailureHandling.OPTIONAL)
	}

	gAct.Wait(GVars.shortWait)
	gObj.buttonClickSync(Const.dashBoard + "img_Logo")
	gAct.Wait(GVars.shortWait)
	

Rep.nextTestStep("Select [Tasks] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click onto [Inline menu]")
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "inlineEdit_Alt", 2, "row")
	
	
Rep.nextTestStep("Click onto [View Post] button")
	WebD.clickElement("//div[contains(@class,'btn-group btn-group-xs shiny open')]//li[3]//a[1]")
	gAct.Wait(1)
	
	WebD.switchToActive()
	
	String text = gObj.getEditTextSync(Const.addPost + "ExistingPost")
	gAct.Wait(1)
	gAct.findSubstringInStringAndReport(text, GVars.user)
	gAct.findSubstringInStringAndReport(text, postText)
	