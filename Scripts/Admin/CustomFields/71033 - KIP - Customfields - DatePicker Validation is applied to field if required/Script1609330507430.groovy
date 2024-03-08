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
import com.katalon.plugin.keyword.calendar.SetDateCalendarKeyword as SetDateCalendar
import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import keyedInProjects.DatePicker as dpck
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
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.CostCentreCustomField
import models.GenerateCostCentreCustomField
import models.CostCentre
import models.GenerateCostCentre
import customfields.Create as createCF
import customfields.Common as ComCF

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	def today = new Date()
	def newDate = Com.increaseWorkingCalendarWithFormat(today, 4, "d/M/yyyy")
	def newDateFmt = Com.changeDateFormat(newDate, "d/M/yyyy", "dd/MM/yyyy")
	
	String[] splitDate = newDate.split("/")
		
	String tableName = "63f30b25-1931-4053-98a5-eec594abfc22"
	String tabName = Com.generateRandomText(5)
	String costCentreCFName = Com.generateRandomText(10)
	String errorDateValue = Com.generateRandomText(10)
	
	ComCF.deleteAllCustomField(tableName, "Cost Centre")
	
	String[] newCostCentreCF = [costCentreCFName, "Date", "Normal", "", ""]
	CostCentreCustomField costCentreCustomField = GenerateCostCentreCustomField.createCostCentreCustomField(newCostCentreCF)

	createCF.costCentreCustomField_DatePicker(tabName, costCentreCustomField)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Cost Centre]")
	gObj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
	
	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	
	
Rep.nextTestStep("Navigate to test tab")
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")
		
		
Rep.nextTestStep("Select DatePicker textfield OR Calendar icon")
	def height = WebUI.getElementHeight(findTestObject(Const.kip4CostCentre + "input_DatePicker"))
	def width = WebUI.getElementWidth(findTestObject(Const.kip4CostCentre + "input_DatePicker"))
	gObj.buttonClickSync(Const.kip4CostCentre + "input_DatePicker")
	gAct.Wait(1)
	int newWid = (width/2) - 15
	WebUI.clickOffset(findTestObject(Const.kip4CostCentre + "input_DatePicker"), newWid, 0)
	gAct.Wait(1)


Rep.nextTestStep("Use left and right arrows to navigate though months")
	String actMth = gObj.getAttributeSync(Const.kip4DatePicker + "select_Month", 'value')
	String actYr = gObj.getAttributeSync(Const.kip4DatePicker + "select_Year", 'value')
	def nxtMth = Com.getNextMonth_Numeric()
	def preMth = Com.getPreviousMonth_Numeric()
	gObj.buttonClickSync(Const.kip4DatePicker + "btn_PrevMonth")
	String actPreMth = gObj.getAttributeSync(Const.kip4DatePicker + "select_Month", 'value')
	String actPreYr = gObj.getAttributeSync(Const.kip4DatePicker + "select_Year", 'value')
	gAct.compareStringAndReport(actPreMth + "/" + actPreYr, preMth)
	gObj.buttonClickSync(Const.kip4DatePicker + "btn_NextMonth")
	gObj.buttonClickSync(Const.kip4DatePicker + "btn_NextMonth")
	String actNextMth = gObj.getAttributeSync(Const.kip4DatePicker + "select_Month", 'value')
	String actNextYr = gObj.getAttributeSync(Const.kip4DatePicker + "select_Year", 'value')
	gAct.compareStringAndReport(actNextMth + "/" + actNextYr, nxtMth)
	
		
Rep.nextTestStep("Attempt entry of alphanumeric characters and remove focus from textfield")
	WebUI.clickOffset(findTestObject(Const.kip4CostCentre + "input_DatePicker"), 290, 0)
	gObj.clearSetEditSync(Const.kip4CostCentre + "input_DatePicker", errorDateValue)
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")
	String actError = gObj.getEditTextSync(Const.kip4CostCentre + "errorDate_InTab")
//	gAct.compareStringAndReport(actError, errorDateValue + " Is Invalid")
	String[] actErrArr = actError.split(" ")
	gAct.findSubstringInStringAndReport(errorDateValue, actErrArr[0])
	

Rep.nextTestStep("Select date from DatePicker")
	WebUI.clickOffset(findTestObject(Const.kip4CostCentre + "input_DatePicker"), newWid, 0)
	dpck.setDateCF(splitDate[0], splitDate[1], splitDate[2])

	String disVal = gObj.getAttributeSync(Const.kip4CostCentre + "input_DatePicker", 'value')
	gAct.compareStringAndReport(disVal, newDateFmt)
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_ContinueWithoutSaving")
	
		
Rep.info("clean up custom field")
	ComCF.cleanUpCostCentreCustomfields(costCentreCFName, tableName)

