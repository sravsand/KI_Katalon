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
import global.WebDriverMethods as WebD


//change from default login
GVars.configFileName = 	"katalonConfigAuto10.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	
Rep.nextTestStep("Click on [Dashboard] from the side bar ")
	Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Dashboards", Const.dashBoard + "subMenu_Dashboard", Const.dashBoard + 'currentPageHeader', "Dashboards")


Rep.nextTestStep("Click on a [Project Name] within [Resource Budget vs Actual] section")
	gAct.Wait(GVars.shortWait)
	boolean elePresent = WebD.elementPresent("//span[contains(text(),'Resource Budget vs. Actual')]")
	gAct.compareBooleanAndReport(elePresent, true)
	Rep.info("Cannot interact with elements within the widget")


Rep.nextTestStep("Click on a [Project Name] within [Pie chart of demand] section")
	elePresent = WebD.elementPresent("//span[contains(text(),'Pie Chart of Demand By Project, By Role')]")
	gAct.compareBooleanAndReport(elePresent, true)
	Rep.info("Cannot interact with elements within the widget")


Rep.nextTestStep("Click on various [Projects] and verify ")
	Rep.info("Cannot interact with elements within the widget")
	
	Rep.warning("incomplete automated test, as Katalon/Selenium cannot interact with elements within the widget.")
