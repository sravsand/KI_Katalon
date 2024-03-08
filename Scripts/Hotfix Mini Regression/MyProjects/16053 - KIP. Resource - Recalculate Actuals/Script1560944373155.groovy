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
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testdata.reader.ExcelFactory

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import keyedInProjects.Populate as Pop
import gantt.Populate as ganttPop
import gantt.Validate as ganttVal
import gantt.Action as ganttAct
import excel.Action as eAct
import myProjects.Action as pAct
import myProjects.Validate as pVal

import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD

String today = Com.weekStartDate()

GVars.configFileName = 	"katalonConfigAuto2.xml"
Act.changeUserCredentials()


Rep.nextTestStep("Log in V6")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	
Rep.nextTestStep("Click onto [My Project] from side menu bar")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', 'Tech Crunch Web Services Deployment')
	
	
Rep.nextTestStep("Click onto [Resource] tab")
	Nav.menuItemAndValidateURL(Const.myProjects + "menu_Resource", "MVC-PRL1-STRATEGICPLAN?")
	
	
Rep.nextTestStep("set starting point")
	gObj.setEditSync(Const.resource + 'input_StartDate', today)
//	gObj.buttonClickSync(Const.myProjects + "menu_Resource")
	gObj.setEditSync(Const.resource + 'input_Periods', "4")
//	gObj.buttonClickSync(Const.myProjects + "menu_Resource")
	gObj.buttonClickSync(Const.resource + "a_Update Results")
	
	
Rep.nextTestStep("Within the [Search] filter section, change the date to 1 week before")
	def dateValue = gObj.getAttributeSync(Const.resource + 'input_StartDate','value')
	String newDate = gAct.decreaseDate(dateValue, "dd/MM/yyyy", 5, "dd/MM/yyyy")
	gObj.setEditSync(Const.resource + 'input_StartDate', newDate)
//	gObj.buttonClickSync(Const.myProjects + "menu_Resource")


Rep.nextTestStep("Enter 8 into [Periods] field")
	gObj.setEditSync(Const.resource + 'input_Periods', "8")
//	gObj.buttonClickSync(Const.myProjects + "menu_Resource")
	

Rep.nextTestStep("Select Weeks from [Periods] field")
	gObj.selectComboLabelAndVerify(Const.resource + 'select_PeriodUnits', 'value', "Weeks", "Weeks")


Rep.nextTestStep("Select Hours in the [In] field")
	gObj.selectComboLabelAndVerify(Const.resource + 'select_In', 'value', "Hours", "Hours")
//	gObj.buttonClickSync(Const.myProjects + "menu_Resource")
	
	
Rep.nextTestStep("Untick [Actuals] check box")
	boolean chk = WebUI.verifyElementChecked(findTestObject(Const.resource + 'chk_Actuals'), GVars.midWait, FailureHandling.OPTIONAL)
	if(chk == true){		
		Rep.info("Actuals state : " + chk)	
		gObj.buttonClickSync(Const.resource + "label_Actuals")
	}
	
	chk = WebUI.verifyElementChecked(findTestObject(Const.resource + 'chk_Actuals'), GVars.midWait, FailureHandling.OPTIONAL)
	
	gAct.compareBooleanAndReport(chk, false)
	
	
Rep.nextTestStep("Click [Update Results] button")
	pAct.resourceUpdateResultsAndValidate(5, 4)
	
	
Rep.nextTestStep("Click on [Recalculate Actuals] button")
	Rep.screenshot("Before Recalculate Actuals clicked.")	
	gObj.buttonClickSync(Const.resource + "a_Recalculate Actuals")
	gAct.Wait(2)
	

Rep.nextTestStep(" Post Click on [Recalculate Actuals] button screenshot")
	Rep.screenshot("After Recalculate Actuals clicked.")
