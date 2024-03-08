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

	String DeliverableName = Com.generateRandomText(10)
	String browser = Com.getBrowserType()
	String todayDate = Com.todayDate()
	String delDate = gAct.increaseDate(todayDate, "dd/MM/yyyy", 20, "dd/MM/yyyy")
	
	String [] newDel = [DeliverableName, GVars.user, "Project Innovation_" + browser, "Billing", delDate]
	Deliverable deliverable = GenerateDeliverable.createDeliverable(newDel)
	component.createDeliverable(deliverable)
	

Rep.nextTestStep("Select [Deliverables] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click onto [Inline menu]")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	gAct.Wait(1)
	int rowNo = sVal.searchTableReturnRow(5, deliverable.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click onto [Add Post] button")
	WebD.clickElement("//div[contains(@class,'btn-group btn-group-xs shiny open')]//li[4]//a[1]")
	gAct.Wait(1)
	
	WebD.switchToActive()
	
	
Rep.nextTestStep("Add some text in [Enter Post Text] Section")
	gObj.buttonClickSync(Const.addPost + "textarea_Post")
	WebD.setEditText("//textarea[@id='Text']", "Some post text 21923")
	WebUI.switchToDefaultContent()
	

Rep.nextTestStep("Click on [Resources] section")
	gObj.buttonClickSync(Const.addPost + "input_Resource")


Rep.nextTestStep("Select a [Resource] from dropdown list")
	gObj.buttonClickSubSync(Const.addPost + "select_Resources", GVars.user, "name")
	
	
Rep.nextTestStep("Click on [Add]")
	gObj.buttonClickSync(Const.addPost + "button_Add")

	boolean elementVisible = WebUI.verifyElementPresent(findTestObject(Const.timesheetSaved + 'TimeSavedSuccessfully'), GVars.longWait, FailureHandling.OPTIONAL)
	if (elementVisible){
		WebUI.waitForElementNotPresent(findTestObject(Const.timesheetSaved + 'TimeSavedSuccessfully'), 10, FailureHandling.OPTIONAL)
	}

	