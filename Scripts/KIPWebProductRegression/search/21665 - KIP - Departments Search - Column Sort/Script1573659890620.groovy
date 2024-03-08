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

//change from default login
GVars.configFileName = 	"katalonConfigAuto20.xml"
Act.changeUserCredentials()

String searchItem = "Departments"
String searchVal = "Department"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [Departments] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	boolean pageLengthVis = WebUI.verifyElementPresent(findTestObject(Const.columnPicker + "pageCount"), 5, FailureHandling.OPTIONAL)
	
	if(pageLengthVis){
		gObj.selectComboByLabelSync(Const.columnPicker + "pageCount", "10")
	}
	
	gObj.buttonClickSync(Const.columnPicker + "header_Code")
	
	
Rep.nextTestStep("Click on [Description] from menu bar")
	gObj.buttonClickSync(Const.columnPicker + "header_Description")

	def preSortCode = sAct.searchTableGetColumnValues(3)

	gObj.buttonClickSync(Const.columnPicker + "header_Description")

	def postSortCode = sAct.searchTableGetColumnValues(3)
	boolean matchCode = gVal.matchingReverseArrays(preSortCode, postSortCode)
	
	if(matchCode){
		Rep.pass("Description has been sorted correctly.")
	}else{
		Rep.fail("Description has not been sorted correctly.")
	}

	
Rep.nextTestStep("Click on [Code] from menu bar")
	gObj.buttonClickSync(Const.columnPicker + "header_Code")
	def preSort = sAct.searchTableGetColumnValues(2)
	gAct.Wait(2)
	WebD.clickElement("//th[@class='sorting_asc']")

	def postSort = sAct.searchTableGetColumnValues(2)
	boolean match = gVal.matchingReverseArrays(preSort, postSort)
	
	if(match){
		Rep.pass("Code has been sorted correctly.")
	}else{
		Rep.fail("Code has not been sorted correctly.")
	}
	
