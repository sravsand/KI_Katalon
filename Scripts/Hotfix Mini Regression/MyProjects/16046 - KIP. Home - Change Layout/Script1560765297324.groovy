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

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myWork.Action as mAct
import myWork.Validate as mVal

//change from default login
GVars.configFileName = 	"katalonConfigAuto4.xml"
Act.changeUserCredentials()

String myWorkView = "Project Layout View"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click onto [My projects] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	
	
Rep.nextTestStep("Click onto [Spanner] (Settings) button at the top right ")
	gObj.buttonClickSync(Const.myProjects + 'spannerSettings')
	
	
Rep.nextTestStep("Click on [Configure Current View]")
	gObj.buttonClickSync(Const.configureViewMenu + 'a_Configure Current View')

	
Rep.nextTestStep("Click on [Layout] (Top Right)")
	gObj.buttonClickSync(Const.layouts + 'button_Layouts')
	
	
Rep.nextTestStep("Select a [Layout]")
	gObj.buttonClickSync(Const.layouts + 'layout_2equalSplit')
	gAct.Wait(1)
	mVal.layout_2equalSplit()
	

Rep.nextTestStep("Click [Save] ( next to layout)")
	gObj.buttonClickSync(Const.widgetView + 'widgetSave')
	gAct.Wait(1)

	
Rep.nextTestStep("Select another [Layout]")
	gObj.buttonClickSync(Const.myProjects + 'spannerSettings')
	mAct.changeLayoutAndValidate_TSplit()
	
	
Rep.nextTestStep("Click [Save] ( next to layout)")
	gObj.buttonClickSync(Const.widgetView + 'widgetSave')
	