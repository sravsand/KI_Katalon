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

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	Rep.info("set Department Planning rates enabled as true")
	gObj.buttonClickSync(Const.mainToolBar + "settings")
	gAct.Wait(1)
	WebUI.click(findTestObject(Const.settingsMenu + "StrategicPlanningSettings"))
	gAct.Wait(1)
	
	def deptPlan = WebD.getCheckBoxState("//input[@id='DepartmentPlanningRatesEnabled']")
	if(deptPlan == null){
		WebUI.click(findTestObject(Const.strategicPlanning + "chk_EnableDepartmentPlanningRates"))
		WebUI.click(findTestObject(Const.strategicPlanning + "button_SaveAndClose"))
		Act.verifySavePopUpText()
	}else{
		gObj.buttonClickSync(Const.strategicPlanning + "button_Close")
	}
	gAct.Wait(1)
	
	String departmentName = Com.generateRandomText(10)
	String departmentCode = departmentName.toUpperCase()
	String rate = Com.generateRandomNumber(2).toString()
	String ratedp = Com.generateRandomNumber(2).toString()
	String totRate = rate + "." + ratedp
	
	String[] newDepartment = [departmentName, departmentCode, "PPM Solutions", "Project Manager"]
	Department department = GenerateDepartment.createDepartment(newDepartment)
	component.createDepartmentWithFinancials(department, totRate)
	
	String departmentNameCloned = Com.generateRandomText(12)
	String departmentCodeCloned = departmentNameCloned.toUpperCase()
	
	String[] newDepartmentCloned = [departmentNameCloned, departmentCodeCloned, "PPM Solutions", "Project Manager"]
	Department departmentCloned = GenerateDepartment.createDepartment(newDepartmentCloned)
		
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Department]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Departments")
	
	
Rep.nextTestStep("Click onto [Inline Menu] against the team created in pre-req")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, department.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click over [Clone] button")
	gObj.selectInlineOption(2)


Rep.nextTestStep("Add a [Code] ")
	gObj.setEditSync(Const.kip4Generic + "input_Code", departmentCloned.code)
	
		
Rep.nextTestStep("Click over [Financial] tab")
	gObj.buttonClickSync(Const.kip4Department+ "tab_Financial")


Rep.nextTestStep("Verify details are pre-filled in planning rates table")
	String actVal = gObj.getAttributeSubSync(Const.kip4Department + "input_localRate",  "1", "pos", 'value')
	gAct.compareStringAndReport(actVal, totRate)

	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	

Rep.nextTestStep("Verify the details have been saved & retained successfully")
	sVal.searchTableAddedValue(2, departmentCloned.code)
	

Rep.info("Delete Department and Unset Department Planning rates enabled as true")

	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNumb = sVal.searchTableReturnRow(2, department.code)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNumb, "row")
	gObj.selectInlineOption(4)
	gObj.buttonClickSync(Const.kip4DeleteModal + "button_OK")
	
	Act.verifyDeletePopUpText()
	
	int rowNum = sVal.searchTableReturnRow(2, departmentCloned.code)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNum, "row")
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
	