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
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre
import buildingBlocks.createComponents
import search.Populate as sPop

//change from default login
GVars.configFileName = 	"katalonConfigAuto20.xml"
Act.changeUserCredentials()

String searchItem = "Costs"
String searchVal = "Cost"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [Costs] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	String filterName = gObj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')
	gAct.findSubstringInStringAndReport(filterName, searchVal)
	gAct.Wait(1)
	Boolean valueBefore = WebUI.verifyElementNotPresent(findTestObject(Const.columnPicker + "header_Code"), GVars.midWait)
	
	
Rep.nextTestStep("Click on [...] from menu bar ")
	WebD.clickElement("//a[@class='menuChooser']")
	gObj.buttonClickSync(Const.columnPicker + "a_Column Chooser")
	gAct.Wait(2)
	WebUI.dragAndDropToObject(findTestObject(Const.columnPicker + "col_Code"), findTestObject(Const.columnPicker + "col_Active"))
	gObj.buttonClickSync(Const.columnPicker + "button_Save")
	
	Boolean value2After = WebUI.verifyElementPresent(findTestObject(Const.columnPicker + "header_Code"), GVars.midWait)
	
