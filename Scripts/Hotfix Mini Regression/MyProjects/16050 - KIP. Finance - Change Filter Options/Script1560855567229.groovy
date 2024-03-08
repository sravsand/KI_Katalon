import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.junit.After
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import keyedInProjects.Populate as Pop
import gantt.Populate as ganttPop
import gantt.Validate as ganttVal
import gantt.Action as ganttAct

import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD


Rep.nextTestStep("Log in V6")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	
Rep.nextTestStep("Click onto [My Project] from side menu bar")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")

	
Rep.nextTestStep("Click onto [Finance] tab")
	Nav.menuItemAndValidateURL(Const.myProjects + "menu_Finance", "MVC-PRL1-FINANCE?")
	gAct.Wait(GVars.shortWait)
	gObj.selectComboLabelAndVerify(Const.finance + 'select_PeriodType', 'value', "Weeks", "Weeks")
	
	
Rep.nextTestStep("Within the [Search] filter section, change the start date to 1 week before")
	Rep.screenshot("Pre date update screenshot.")
	def dateValue = gObj.getAttributeSync(Const.finance + 'input_StartDate','value')
	String newDate = gAct.decreaseDate(dateValue, "dd/MM/yyyy", 5, "dd/MM/yyyy")
	gObj.setEditSync(Const.finance + 'input_StartDate', newDate)
	gObj.buttonClickSync(Const.myProjects + "menu_Finance")
	
	
Rep.nextTestStep("Enter 8 into [Periods] field")
	gObj.setEditSync(Const.finance + 'input_Periods', "8")
	

Rep.nextTestStep("Change [Period Type] to Week mode")
	gObj.selectComboLabelAndVerify(Const.finance + 'select_PeriodType', 'value', "Weeks", "Weeks")
	
	
Rep.nextTestStep("Select Quarters from [Period Type] field")
	gObj.selectComboLabelAndVerify(Const.finance + 'select_PeriodType', 'value', "Quarters", "Quarters")
	
	
Rep.nextTestStep("Select Efforts (Days) from [Show] field")
	gObj.selectComboLabelAndVerify(Const.finance + 'select_Show', 'value', "Effort (Days)", "DemandEffort")
	

Rep.nextTestStep("Select Comparison from [Show as] field")
	gObj.selectComboLabelAndVerify(Const.finance + 'select_ShowAs', 'value', "Comparison", "ProfileComparison")
	
	
Rep.nextTestStep("Select Manager from [Role] field")
	gObj.buttonClickSync(Const.finance + 'role_DropDown')
	gObj.setEditSync(Const.finance + 'input_RoleSearch', "Admin")
	gObj.buttonClickSync(Const.finance + 'selectRole')

	
Rep.nextTestStep("Click [Update Results]")
	gObj.buttonClickSync(Const.finance + 'a_Update Results')
	gAct.Wait(2)
	boolean resultsTable = WebUI.verifyElementPresent(findTestObject(Const.finance + 'financeResultTable'), GVars.longWait, FailureHandling.CONTINUE_ON_FAILURE)
	if(resultsTable){
		Rep.pass("Results updated")
		Rep.screenshot("Finance search results table")
	}else{
		Rep.fail("Results not updated")
	}
