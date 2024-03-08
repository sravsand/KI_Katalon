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
import global.Validate as gVal
import global.Object as gObj
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import gantt.Action as ganttAct
import capacity.Validate as cVal
import capacity.Populate as cPop


String today = Com.todayDate()
int periodCount = 4

Rep.nextTestStep("Pre-requisite: Ensure Strategic Planning mode is selected through Login Groups->Open User's Login group->Views tab->Resourcing section->Select Strategic Planning")
	Rep.info("Pre-req already setup in test database")
	

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Hover over and select [Resourcing] > [Requests] from the side bar")
	Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Resourcing", Const.dashBoard + "subMenu_Requests", Const.dashBoard + 'currentPageHeader', "Requests")
	

Rep.nextTestStep("Select [Date]")
	gVal.objectAttributeValue(Const.requests + 'input_StartDate', 'value', today)

	
Rep.nextTestStep("Select [Period]")
	gObj.setEditSync(Const.requests + 'input_Periods_Number', periodCount.toString())
	gObj.selectComboByLabelSync(Const.requests + 'select_Units', "Weeks")
	
	
Rep.nextTestStep("Select [Project]")
	String projSel = "All Company"
	WebD.clickElement("//div[@id='s2id_FilterProjects']//ul[@class='select2-choices']")
	gObj.buttonClickSubSync(Const.capacity + 'select_ResourceProject', projSel, "proj")
	
	
Rep.nextTestStep("Select [Department]")
	WebD.clickElement("//div[@id='s2id_FilterDepartments']//ul[@class='select2-choices']")
	gObj.buttonClickSubSync(Const.capacity + 'dropdownSelect', "PPM Solutions", "select")
	
	
Rep.nextTestStep("Select [Roles]")
	WebD.clickElement("//div[@id='s2id_Roles']//ul[@class='select2-choices']")
	gObj.buttonClickSubSync(Const.capacity + 'dropdownSelect', "Project Manager", "select")
	
	
Rep.nextTestStep("Select [Days] from the In section")
	gObj.selectComboByLabelSync(Const.requests + 'select_InUnits', "Days")


Rep.nextTestStep("Click on [Update results] and verify ")
	gObj.buttonClickSync(Const.capacity + 'a_UpdateResults')
	gAct.Wait(1)
	
	boolean tableExists = WebD.elementPresent("//div[@id='splitterContainer']")
	gAct.compareBooleanAndReport(tableExists, true)
	
