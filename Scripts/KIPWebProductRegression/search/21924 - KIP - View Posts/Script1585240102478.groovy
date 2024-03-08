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
import models.Deliverable
import models.GenerateDeliverable
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.DeliverableType
import models.GenerateDeliverableType

String searchItem = "Deliverables"
String searchVal = "Assignment"

Rep.nextTestStep("KIP - V6 Stage Login page")
Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String postText = "Pre added text - 21924"
	String DeliverableName = Com.generateRandomText(10)
	String browser = Com.getBrowserType()
	String todayDate = Com.todayDate()
	String delDate = gAct.increaseDate(todayDate, "dd/MM/yyyy", 20, "dd/MM/yyyy")
	
	String [] newDelType = [DeliverableName, GVars.user, "Project Innovation_" + browser, "Billing", delDate]
	Deliverable deliverable = GenerateDeliverable.createDeliverable(newDelType)
	component.createDeliverable(deliverable)
	
	component.createPost(deliverable.name, postText, GVars.user)
	
	
Rep.nextTestStep("Select [Deliverables] from search filter")
	Nav.selectSearchFilter(searchItem)
	
		
Rep.nextTestStep("Click onto [Inline menu]")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	gAct.Wait(1)
	int rowNo = sVal.searchTableReturnRow(5, deliverable.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	

Rep.nextTestStep("Click onto [View Post] button")
	WebD.clickElement("//div[contains(@class,'btn-group btn-group-xs shiny open')]//li[5]//a[1]")
	gAct.Wait(1)
	
	WebD.switchToActive()
	
	String text = gObj.getEditTextSync(Const.addPost + "ExistingPost")
	gAct.Wait(1)
	gAct.findSubstringInStringAndReport(text, GVars.user)
	gAct.findSubstringInStringAndReport(text, deliverable.name)
	gAct.findSubstringInStringAndReport(text, postText)
	