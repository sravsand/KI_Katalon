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
import risks.Validate as rVal
import search.Validate as sVal
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.ActionView
import models.GenerateActionView

String searchItem = "Action Views"
String searchVal = "Action View"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String actionName = Com.generateRandomText(10)
	
	String[] newActionView = [actionName, "This is a test for script 21571", "Meeting Notes"]
	ActionView actionView = GenerateActionView.createActionView(newActionView)
	component.createActionView(actionView)


Rep.nextTestStep("Select [Action Views] from search filter")
	Nav.selectSearchFilter(searchItem)

	int rowNo = sVal.searchTableReturnRow(2, actionView.name)
	String ActiveStatus = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 4, "col")
	gAct.compareStringAndReport(ActiveStatus, "Yes")
	
	
Rep.nextTestStep("Click onto [Inline menu]of an expense type")
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "inlineEdit_Alt", rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Edit]")
	gObj.buttonClickSync(Const.columnPicker + "a_Edit")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Tick the checkbox from [Action] column")
	WebD.clickElement("//div[@id='861a4a51-99fe-4dfe-8681-5c044870dad7']/div[5]/table/tbody/tr[" + rowNo + "]/td[4]/div/label/span")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click on [Floppy Icon] to save the changes")
	WebD.clickElement("//div[@id='861a4a51-99fe-4dfe-8681-5c044870dad7']/div[5]/table/tbody/tr[" + rowNo + "]/td/div/a/i")
	
	
Rep.nextTestStep("Verify that the changes are saved ")
	String ActiveStatus_new = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 4, "col")
	gAct.compareStringAndReport(ActiveStatus_new, "No")

