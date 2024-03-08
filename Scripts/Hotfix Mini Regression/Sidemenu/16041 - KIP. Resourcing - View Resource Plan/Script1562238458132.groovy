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
String weekStartDate = Com.weekStartDate()
String weekStart = Com.changeDateFormat(weekStartDate, 'dd/MM/yyyy', 'dd MMM')

int periodCount = 4

Rep.nextTestStep("Pre-requisite: Ensure Strategic Planning mode is selected through Login Groups->Open User's Login group->Views tab->Resourcing section->Select Strategic Planning")
	Rep.info("Pre-req already setup in test database")
	

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Hover over and select [Resourcing] > [Resource Plan] from the side bar")
	Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Resourcing", Const.dashBoard + "subMenu_ResourcePlan", Const.dashBoard + 'currentPageHeader', "Resource Plan")
	gAct.Wait(GVars.shortWait)
	
	
Rep.nextTestStep("Select [Date]")
	gObj.setEditSync(Const.resourcePlan + 'input_StartDate', weekStartDate)

	
Rep.nextTestStep("Select [Period]")
	gObj.buttonClickSync(Const.resourcePlan + 'input_Periods_Number')
	gObj.setEditSync(Const.resourcePlan + 'input_Periods_Number', periodCount.toString())
	gObj.selectComboByLabelSync(Const.resourcePlan + 'select_Units', "Weeks")
	gAct.Wait(2)
	
	
Rep.nextTestStep("Select [Department]")
	def vis = gObj.elementVisibleSync(Const.resourcePlan + 'closeDeptVal')

	if(vis){
		gObj.buttonClickSync(Const.resourcePlan + 'closeDeptVal')
	}
	
	WebD.clickElement("//div[@id='s2id_FilterDepartments']//ul[@class='select2-choices']")
	gObj.buttonClickSubSync(Const.capacity + 'dropdownSelect', "PPM Solutions", "select")
	
	
Rep.nextTestStep("Select [Roles]")
	def val = gObj.elementVisibleSync(Const.resourcePlan + 'btn_DeleteRole')

	if(val) {
		gObj.buttonClickSync(Const.resourcePlan + 'btn_DeleteRole')
	}
	
	gObj.buttonClickSync(Const.resourcePlan + 'input_Periods_Number')
	WebD.clickElement("//input[@id='s2id_autogen12']")
//		gObj.buttonClickSync(Const.resourcePlan + 'edt_InputRoles')
	
	gObj.buttonClickSubSync(Const.capacity + 'dropdownSelect', "Project Manager", "select")
	gObj.buttonClickSync(Const.resourcePlan + 'input_Periods_Number')
	

Rep.nextTestStep("Select [Project]")
	Rep.info("Leave Project blank")
	
	
Rep.nextTestStep("Select [Skills]")
	Rep.info("Leave Skills blank")
	

Rep.nextTestStep("Select [Locations]")
	Rep.info("Leave Locations blank")


Rep.nextTestStep("Click on [Update results] and verify ")
	gObj.scrollToElementSync(Const.resourcePlan + 'a_Update Results')
	gObj.buttonClickSync(Const.resourcePlan + 'a_Update Results')
//	WebD.clickElement("//a[@id='updateOptions']")
	gAct.Wait(2)
	
	String colDate = gObj.getEditTextSubIntPosSync(Const.resourcePlan + 'cellHeader', 1, "col")
	gAct.compareStringAndReport(colDate, weekStart)
	
	int colCount = WebD.getTableColumnCount("//div[@id='resourceTable']/div[2]/div[1]/table/thead/tr")
	gAct.compareIntAndReport(colCount, periodCount + 1)
	
	