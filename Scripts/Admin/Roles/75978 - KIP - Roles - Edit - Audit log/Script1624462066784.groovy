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
//import com.katalon.plugin.keyword.angularjs.DropdownKeywords as angObj
import com.katalon.plugin.keyword.angularjs.DropdownKeywords

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
import models.Role
import models.GenerateRole
import models.CostCentre
import models.GenerateCostCentre

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	def pattern = "MMMM yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String newDate = gAct.increaseDate(todaysDate, pattern, 31, pattern)
	
	String costCentreName = Com.generateRandomText(6)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	String costCentreNew = component.costCentre(costCentre)
	
	String roleName = Com.generateRandomText(8)
	String roleCode = roleName.toUpperCase()
	String[] newRole = [roleName, roleCode, "KIP Dev Team 1", "Cleckheaton", "Full Time Calendar (My Region)", costCentre.name, "Developer", "Free of Charge", ""]
	Role role = GenerateRole.createNewRole(newRole)
	component.createRole(role)
	
	String roleNameNew = Com.generateRandomText(8)
	String roleCodeNew = roleNameNew.toUpperCase()
	String[] newRoleNew = [roleNameNew, roleCodeNew, "KIP Dev Team 2", "Letterkenny", "Half Time Calendar (My Region)", costCentre.name, "No Cost", "Developer", "Some Notes"]
	Role roleNew = GenerateRole.createNewRole(newRoleNew)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Role]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Roles")
	

Rep.nextTestStep("Click onto [Inline menu] against an role")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, role.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
		
		
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)
	
	
Rep.nextTestStep("Verify the [Code] checkbox is locked and user unable to amend the value")
	String actCode = gObj.getAttributeSync(Const.kip4Generic + "input_Code", 'value')
	gObj.verifyAttributeSync(Const.kip4Generic + "input_Code", 'readonly')

	
Rep.nextTestStep("Verify the [Template] checkbox is locked and user unable to amend the value")
	gObj.verifyAttributeSync(Const.kip4Roles + "chk_Template_dis", 'disabled')


Rep.nextTestStep("Amend the [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", roleNew.name)

	
Rep.nextTestStep("Select [Team]")
	gObj.selectComboboxValue("department", "department", roleNew.department, roleNew.department)

	
Rep.nextTestStep("Amend [Working Time]")
//	gObj.scrollToElementSync(Const.kip4Roles + "label_WorkingTime")
	gObj.buttonClickSync(Const.kip4Roles + "dropDown_WorkingTime")
	gObj.buttonClickSync(Const.kip4Roles + "delete_WorkingTime")
	gObj.buttonClickSubSync(Const.kip4Roles + "select_WorkingTime", roleNew.workingTime, "wt")


Rep.nextTestStep("Add text in [Note]")
	gObj.scrollToElementSync(Const.kip4Roles + "textarea_Notes")
	gObj.setEditSync(Const.kip4Roles + "textarea_Notes",roleNew.notes)
	

Rep.nextTestStep("Click over [Financial] tab")
	gObj.buttonClickSync(Const.kip4Roles + "tab_Financial")

	
Rep.nextTestStep("Click over [Add Planning Rate] button")
	gObj.buttonClickSync(Const.kip4Roles + "btn_AddPlanningRate")

	
Rep.nextTestStep("Selct [Currency] if different from Default Currency")
	String num = "1"
	gObj.buttonClickSubSync(Const.kip4Roles + "dropDown_EffectiveFrom", num, "pos")
//	WebUI.setText(findTestObject(Const.kip4Roles + "input_EffectiveFrom", [('pos') : num]), todaysDate)
	gObj.buttonClickSubSync(Const.kip4Roles + "select_EffectiveFrom", todaysDate, "mth")
	
	gObj.buttonClickSubSync(Const.kip4Roles + "dropDown_Currency", num, "pos")
	gObj.setEditSubSync(Const.kip4Roles + "input_Currency", num, "pos", "DEFAULT")
	gObj.buttonClickSubSync(Const.kip4Roles + "select_Currency", "Default Currency", "curr")


Rep.nextTestStep("Enter [Cost] value")
	gObj.setEditSubSync(Const.kip4Roles + "input_localCostRate", num, "pos", "7")
	

Rep.nextTestStep("Enter [Charge] value")
	gObj.setEditSubSync(Const.kip4Roles + "input_localChargeRate", num, "pos", "6.5")
	

Rep.nextTestStep("Add couple of [Planning Rates] by repeating steps 14-16")
	gObj.buttonClickSync(Const.kip4Roles + "btn_AddPlanningRate")
	num = "2"
	gObj.buttonClickSubSync(Const.kip4Roles + "dropDown_EffectiveFrom",  num, "pos")
//	WebUI.setText(findTestObject(Const.kip4Roles + "input_EffectiveFrom", [('pos') : num]), newDate)
	gObj.buttonClickSubSync(Const.kip4Roles + "select_EffectiveFrom", newDate, "mth")
	
	gObj.buttonClickSubSync(Const.kip4Roles + "dropDown_Currency",  num, "pos")
	gObj.setEditSubSync(Const.kip4Roles + "input_Currency", num, "pos", "DEFAULT")
	gObj.buttonClickSubSync(Const.kip4Roles + "select_Currency", "Default Currency", "curr")
	
	gObj.setEditSubSync(Const.kip4Roles + "input_localCostRate", num, "pos", "4")
	gObj.setEditSubSync(Const.kip4Roles + "input_localChargeRate", num, "pos", "3.5")
	

Rep.nextTestStep("Click over [Security Group] tab")
	gObj.buttonClickSync(Const.kip4Roles + "tab_SecurityGroups")

	
Rep.nextTestStep("Click over [Add] button, Select couple of Security Group via checkbox & Click over [Select] button")
	component.createFilters("Security Group Name", "xSG1")	


Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	

Rep.nextTestStep("Verify the toaster message text to read as [Saved Successfully]")
	Act.verifySavePopUpText()

	
Rep.nextTestStep("Verify the changes are updated accurately on the Role table")
Rep.nextTestStep("Click onto [Inline Menu] against an active role")
	gObj.refreshUI()
	int rowNum = sVal.searchTableReturnRow(3, roleNew.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt',  rowNum, "row")
		
	
Rep.nextTestStep("Click onto [Open]")
	gObj.selectInlineOption(1)

	
Rep.nextTestStep("Verify the changes are retained accurately in all tabs")
	String actVal1 = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actVal1, roleNew.name)
	String actVal2 = WebD.getElementText("//ng-select[@id='department']")
	gAct.compareStringAndReport(actVal2, roleNew.department)
	gObj.scrollToElementSync(Const.kip4Roles + "textarea_Notes")
	String actVal3 = WebD.getElementText("//ng-select[@id='workingTime']")
	gAct.compareStringAndReport(actVal3, roleNew.workingTime)
	String actVal4 = gObj.getAttributeSync(Const.kip4Roles + "textarea_Notes", 'value')
	gAct.compareStringAndReport(actVal4, roleNew.notes)
	
	gObj.buttonClickSync(Const.kip4Roles + "tab_Financial")
	gAct.Wait(1)
	
	String actVal5 = WebD.getElementText("(//ng-select[@id='currency'])[1]")
	
	gAct.compareStringAndReport(actVal5, "DEFAULT")
	
	String actVal51 = WebD.getElementText("(//ng-select[@id='currency'])[2]")
	
	gAct.compareStringAndReport(actVal51, "DEFAULT")
	
	String actVal6 = gObj.getAttributeSubSync(Const.kip4Roles + "input_localCostRate", "1", "pos", 'value')
	gAct.compareStringAndReport(actVal6, "7")
	String actVal7 = gObj.getAttributeSubSync(Const.kip4Roles + "input_localChargeRate", "1", "pos", 'value')
	gAct.compareStringAndReport(actVal7, "6.5")
	String actVal8 = gObj.getAttributeSubSync(Const.kip4Roles + "input_localCostRate", "2", "pos", 'value')
	gAct.compareStringAndReport(actVal8, "4")
	String actVal9 = gObj.getAttributeSubSync(Const.kip4Roles + "input_localChargeRate", "2", "pos", 'value')
	gAct.compareStringAndReport(actVal9, "3.5")
	
	
Rep.nextTestStep("Click over [Audit] tab")
	gObj.buttonClickSync(Const.kip4Roles + "tab_Audit")
	

Rep.nextTestStep("Verify all the changes are recorded for all fields accurately ")
	gObj.buttonClickSync(Const.kip4Roles + "a_Today")
	gAct.Wait(2)
	String auditText = WebD.getElementText("//div[@id='audit0']")
	
	gAct.findSubstringInStringAndReport(auditText, "Department changed from KIPDEV1 to KIPDEV2")
	gAct.findSubstringInStringAndReport(auditText, "Forecast Planning Rates Amended")
	gAct.findSubstringInStringAndReport(auditText, "Security Group Restrictions Amended")
	
	gAct.findSubstringInStringAndReport(auditText, "Name changed from " + role.name + " to " + roleNew.name)
	gAct.findSubstringInStringAndReport(auditText, "Notes changed from <Empty> to Some Notes")
	gAct.findSubstringInStringAndReport(auditText, "Working Time changed from FULLTIME to HALF")
	gAct.findSubstringInStringAndReport(auditText, role.code + " inserted.")
	
	
	