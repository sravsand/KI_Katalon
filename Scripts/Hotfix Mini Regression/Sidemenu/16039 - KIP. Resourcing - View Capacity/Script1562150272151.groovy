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


String today = Com.weekStartDate()
int periodCount = 4

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Hover over and select [Resourcing] > [Capacity] from the side bar")
	Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Resourcing", Const.dashBoard + "subMenu_Capacity", Const.dashBoard + 'currentPageHeader', "Capacity")
	gAct.Wait(2)
	
	
Rep.nextTestStep("Select [Date]")
	gVal.objectAttributeValue(Const.capacity + 'input_StartDate', 'value', today)

	
Rep.nextTestStep("Select [Period]")
	gObj.setEditSync(Const.capacity + 'input_Periods_Number', periodCount.toString())
	gObj.selectComboByLabelSync(Const.capacity + 'select_Units', "Weeks")

	
Rep.nextTestStep("Select [Effort] (Days / Hrs / FTEs)")
	gObj.selectComboByLabelSync(Const.capacity + 'select_Effort', "Days")

	
Rep.nextTestStep("Select [Department]")
	gAct.Wait(2)
	
	WebD.clickElement("//div[@id='s2id_Departments']//ul[@class='select2-choices']")
	gObj.buttonClickSubSync(Const.capacity + 'dropdownSelect', "PPM Solutions", "select")
	
	
Rep.nextTestStep("Select [Roles]")
	WebD.clickElement("//div[@id='s2id_Roles']//ul[@class='select2-choices']")
	gObj.buttonClickSubSync(Const.capacity + 'dropdownSelect', "Manager", "select")
	
	
Rep.nextTestStep("Select the available [Entities]")
	cPop.capacityReportCheckboxArray()

	
Rep.nextTestStep("Click on [Update results] and verify ")
	gObj.buttonClickSync(Const.capacity + 'a_UpdateResults')
	gAct.Wait(1) 
	
	cVal.capacityReportTable(today, 2, periodCount)

