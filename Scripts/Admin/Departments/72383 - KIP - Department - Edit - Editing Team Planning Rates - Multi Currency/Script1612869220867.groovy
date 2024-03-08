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
import search.Validate as sVal
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Department
import models.GenerateDepartment
import models.Currency
import models.GenerateCurrency

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String currencyName = Com.generateRandomText(8)
	String currencyCode = currencyName.toUpperCase()
	currencyCode = currencyCode.take(3)
	
	String[] newCurrency = [currencyName, currencyCode, "€", "true"]
	Currency currency = GenerateCurrency.createCurrency(newCurrency)
	component.createCurrency(currency)
	
	
	Rep.info("set Department Planning rates enabled as true")
	gObj.buttonClickSync(Const.mainToolBar + "settings")
	gObj.buttonClickSync(Const.settingsMenu + "StrategicPlanningSettings")
	gAct.Wait(1)
	def deptPlan = WebD.getCheckBoxState("//input[@id='DepartmentPlanningRatesEnabled']")
	if(deptPlan == null){
		gObj.buttonClickSync(Const.strategicPlanning + "chk_EnableDepartmentPlanningRates")
		gObj.buttonClickSync(Const.strategicPlanning + "button_SaveAndClose")
		Act.verifySavePopUpText()
	}else{
		gObj.buttonClickSync(Const.strategicPlanning + "button_Close")
	}
	
	String departmentName = Com.generateRandomText(10)
	String departmentCode = departmentName.toUpperCase()
	String rate = Com.generateRandomNumber(2).toString()
	String ratedp = Com.generateRandomNumber(2).toString()
	String totRate = rate + "." + ratedp
	
	String rate2 = Com.generateRandomNumber(2).toString()
	String ratedp2 = Com.generateRandomNumber(2).toString()
	String totRate2 = rate2 + "." + ratedp2
	
	String[] newDepartment = [departmentName, departmentCode, "PPM Solutions", "Resource Manager"]
	Department department = GenerateDepartment.createDepartment(newDepartment)
	component.createDepartmentWithFinancials(department, totRate)
	
	String newManager = "Systems Engineer"
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Department]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Departments")
	
	
Rep.nextTestStep("Click onto [Inline Menu] against the team created in pre-req")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, department.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	

Rep.nextTestStep("Click over [open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Verify the [Name] of the Team is appearing at the top of the modal")
	String deptHeader = gObj.getEditTextSync(Const.kip4Department + "header_DeptDesc")
	gAct.findSubstringInStringAndReport(deptHeader, department.code + " | " + department.name)
	
	
Rep.nextTestStep("Amend [Manager]")
	gObj.scrollToElementSync(Const.kip4Department + "dropDown_ManagerHasValue")
	gObj.selectComboboxValue("lineManager", "lineManager", newManager, newManager)


Rep.nextTestStep("Click onto [Financal] Tab")
	gObj.buttonClickSync(Const.kip4Department + "tab_Financial")

	
Rep.nextTestStep("Edit couple of Planning rates and change the currency ")
	gObj.buttonClickSync(Const.kip4Department + "dropDownCurrency")
	gObj.setEditSync(Const.kip4Department + "input_Currency", currency.name)
	gObj.buttonClickSubSync(Const.kip4Department + "select_CurrencyHasValue", currency.name, "cur")
	
	gObj.setEditSubSync(Const.kip4Department + "input_localRate", "1", "pos", totRate2)


Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	Act.verifySavePopUpText()
	gObj.refreshUI()
	

Rep.nextTestStep("Click onto [Inline Menu] against an active Team ")
	int rowNum = sVal.searchTableReturnRow(3, department.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNum, "row")

	
Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)

	
Rep.nextTestStep("Verify the changes are saved accurately ")
	gObj.scrollToElementSync(Const.kip4Department + "dropDown_ManagerHasValue")
	String newMan = gObj.getEditTextSync(Const.kip4Department + "div_Manager")
	gAct.compareStringAndReport(newMan, newManager)
	
	gObj.buttonClickSync(Const.kip4Department + "tab_Financial")
	String newCurr = gObj.getEditTextSync(Const.kip4Department + "div_Currency")	
	gAct.compareStringAndReport(newCurr, currency.name)
	
	String newRate = gObj.getAttributeSubSync(Const.kip4Department + "input_localRate", "1", "pos", 'value')
	gAct.compareStringAndReport(newRate, totRate2)
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	
Rep.info("Delete Department and Unset Department Planning rates enabled as true")

	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNumb = sVal.searchTableReturnRow(3, department.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNumb, "row")
	gObj.selectInlineOption(4)
	gObj.buttonClickSync(Const.kip4DeleteModal + "button_OK")
	
	Act.verifyDeletePopUpText()


	gObj.buttonClickSync(Const.mainToolBar + "settings")
	gObj.buttonClickSync(Const.settingsMenu + "StrategicPlanningSettings")
	gAct.Wait(GVars.shortWait)
	String deptPlannew = WebD.getCheckBoxState("//input[@id='DepartmentPlanningRatesEnabled']")
	if(deptPlannew == "true"){
		gObj.buttonClickSync(Const.strategicPlanning + "chk_EnableDepartmentPlanningRates")
		gObj.buttonClickSync(Const.strategicPlanning + "button_SaveAndClose")
		Act.verifySavePopUpText()
	}else{
		gObj.buttonClickSync(Const.strategicPlanning + "button_Close")
	}
	