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


Rep.nextTestStep("KIP - V6 Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Click on [Report] from the side bar ")
	Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Reports", Const.dashBoard + "subMenu_ReportList", Const.reports + 'a_ReportFilter', "Reports")
	
Rep.nextTestStep("Click on any of the [Reports] displayed")
	int rowCnt = WebD.getTableRowCount("//div[contains(@class,'editableTable tableDiv hasOperations')]")
	
	for(int intCnt = 1; intCnt < rowCnt; intCnt ++){
					
		gObj.buttonClickSubIntPosSync(Const.reports + 'reportTableMenuExpand', intCnt, "row")
		gObj.buttonClickSubIntPosSync(Const.reports + 'a_Open', intCnt, "row")
				
			
		Rep.nextTestStep("Click onto each [Tab] under the Edit Report window ")
			String reportName = gObj.getAttributeSync(Const.reports + 'reportName', 'value')
			Rep.info("Report name : " + reportName)
			
			gVal.objectAttributeValue(Const.reports + 'GeneralTab', 'class', "tabSelectedPopup")
			
			gObj.buttonClickSync(Const.reports + 'DefinitionTab')
			Com.chromeSync(1)
			gVal.objectAttributeValue(Const.reports + 'DefinitionTab', 'class', "tabSelectedPopup")
	
			gObj.buttonClickSync(Const.reports + 'ContextAreasTab')
			gVal.objectAttributeValue(Const.reports + 'ContextAreasTab', 'class', "tabSelectedPopup")
			
			gObj.buttonClickSync(Const.reports + 'LoginGroupsTab')
			gVal.objectAttributeValue(Const.reports + 'LoginGroupsTab', 'class', "tabSelectedPopup")
			
			gObj.buttonClickSync(Const.reports + 'LanguagesTab')
			gVal.objectAttributeValue(Const.reports + 'LanguagesTab', 'class', "tabSelectedPopup")
				
			
		Rep.nextTestStep("Click [Save or close]")
			gObj.buttonClickSync(Const.reports + 'button_Close')
			WebUI.refresh()
			gAct.Wait(GVars.shortWait)
		
		Rep.nextTestStep("Click on other [Reports] and follow previous steps ")
	}

