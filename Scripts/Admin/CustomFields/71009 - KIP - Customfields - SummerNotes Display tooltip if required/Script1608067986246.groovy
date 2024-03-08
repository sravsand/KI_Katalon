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
import models.CostCentreCustomField
import models.GenerateCostCentreCustomField
import customfields.Create as createCF
import customfields.Common as ComCF

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String tableName = "63f30b25-1931-4053-98a5-eec594abfc22"
	String tabName = Com.generateRandomText(5)
	String costCentreCFName = Com.generateRandomText(10)
	String GuidenceNotes = Com.generateRandomText(4) + " " + Com.generateRandomText(7) + " " + Com.generateRandomText(5)
	
	ComCF.deleteAllCustomField(tableName, "Cost Centre")
	
	String[] newCostCentreCF = [costCentreCFName, "Formatted Text", "Normal", GuidenceNotes, ""]
	CostCentreCustomField costCentreCustomField = GenerateCostCentreCustomField.createCostCentreCustomField(newCostCentreCF)

	createCF.costCentreCustomField_FormattedText(tabName, costCentreCustomField)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Cost Centre]")
	gObj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
	
	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	

Rep.nextTestStep("Navigate to test tab")
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")

	
Rep.nextTestStep("Ensure 'SummerNotes' text editor is displayed")
	gObj.elementPresentSubSync(Const.kip4CostCentre + "label_Customfield_InTab", costCentreCustomField.name, "obj")
	gObj.elementPresentSync(Const.kip4CostCentre + "FormattedTextField")
	
	
Rep.nextTestStep("Ensure 'SummerNotes' have the Info icon to the left of the label")
	if(GVars.browser == "Firefox"){
		WebD.clickElement("//i[@class='fa fa-info-circle tooltip-info']")
	}else {
		gObj.buttonClickSubSync(Const.kip4CostCentre + "costCentreGuidanceNotes_InTab", costCentreCustomField.guidanceNotes, "tip")
	}
	

Rep.nextTestStep("Ensure the 'Guidance Notes' text matches that set in the [Custom Field]")
	WebUI.verifyTextPresent(costCentreCustomField.guidanceNotes, false)
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")

	
Rep.info("clean up custom field")
	ComCF.cleanUpCostCentreCustomfields(costCentreCFName, tableName)
	