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

String searchItem = "Risks"
String searchVal = "Risks"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Select [Risks] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	WebD.clickElement("//th[@class='text-align-center sorting_disabled resizing_disabled editableTableButtonsHeader']")
	gObj.buttonClickSync(Const.columnPicker + "a_Reset to Default")
	gAct.Wait(1)
	
	WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "pageCount"), "10", false)
	gObj.buttonClickSync(Const.columnPicker + "header_Owner")
	gAct.Wait(1)
	
	
Rep.nextTestStep("Click on [Title] from menu bar")
	WebD.clickElement("//th[@class='sorting'][contains(text(),'Title')]")
	gAct.Wait(1)
	def preSort = sAct.searchTableGetColumnValues_Alt(3)
	gAct.Wait(2)

	WebD.clickElement("//th[@class='sorting_asc']")
	
	gAct.Wait(1)
	def postSort = sAct.searchTableGetColumnValues_Alt(3)
	boolean match = gVal.matchingReverseArrays(preSort, postSort)
	
	if(match){
		Rep.pass("Title has been sorted correctly.")
	}else{
		Rep.fail("Title has not been sorted correcly.")
	}
	
	
Rep.nextTestStep("Click on [Owner] from menu bar")
	gObj.buttonClickSync(Const.columnPicker + "header_Owner")
	gAct.Wait(1)
	def preSortCode = sAct.searchTableGetColumnValues_Alt(7)
	gObj.buttonClickSync(Const.columnPicker + "header_Owner")
	gAct.Wait(2)
	def postSortCode = sAct.searchTableGetColumnValues_Alt(7)
	boolean matchCode = gVal.matchingReverseArrays(preSortCode, postSortCode)
	
	if(matchCode){
		Rep.pass("Owner has been sorted correctly.")
	}else{
		Rep.fail("Owner has not been sorted correcly.")
	}
	