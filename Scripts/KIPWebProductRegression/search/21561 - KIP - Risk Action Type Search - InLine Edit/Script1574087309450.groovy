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
import models.Risk
import models.GenerateRisk

String searchItem = "Risks"
String searchVal = "Risks"
String newDate = Com.todayDate()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String browser = Com.getBrowserType()
	String todayDate = Com.todayDate()
	String impDate = gAct.increaseDate(todayDate, "dd/MM/yyyy", 1, "dd/MM/yyyy")
	String resDate = gAct.increaseDate(todayDate, "dd/MM/yyyy", 10, "dd/MM/yyyy")
	String newImpDate = gAct.increaseDate(resDate, "dd/MM/yyyy", 7, "dd/MM/yyyy")
		
	String riskName = Com.generateRandomText(10)
	
	String medium = "background-color: orange; color: black;"
	String high = "background-color: red; color: black;"
	
	String [] newRisk = [riskName + " " + browser, 'Tech Crunch Web Services Deployment', GVars.user, "Open", todayDate, impDate, "Project", "High", "Medium", "High", resDate, "", ""]
	Risk risk = GenerateRisk.createNewRisk(newRisk)
	component.createRisk(risk)


Rep.nextTestStep("Select [Risks] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	
Rep.nextTestStep("Click onto [Inline menu]of an expense type")
	int rowNo = sVal.searchTableReturnRow(3, risk.title)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + "inlineEdit_Alt", rowNo, "row")
	
	
Rep.nextTestStep("Click onto [Edit]")
	gObj.buttonClickSync(Const.columnPicker + "a_Edit")
	
	
Rep.nextTestStep("Edit the [Impact Date]")
	gObj.setEditSync(Const.columnPicker + "input_ImpactDate", newImpDate)
	gObj.buttonClickSync(Const.columnPicker + "input_TitleValue")
	
	
Rep.nextTestStep("Click on [Floppy Icon] to save the changes")
	WebD.clickElement("//div[@id='457097a0-e807-4281-9c80-665bb53c83d3']/div[5]/table/tbody/tr[" + rowNo  + "]/td/div/a/i")
	
	
Rep.nextTestStep("Verify that the changes are saved ")
	String ActiveStatus_new = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 4, "col")
	gAct.compareStringAndReport(ActiveStatus_new, newImpDate)

