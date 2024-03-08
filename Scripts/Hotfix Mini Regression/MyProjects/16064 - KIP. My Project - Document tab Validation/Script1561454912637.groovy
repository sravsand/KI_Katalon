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
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myProjects.Validate as mVal
import myProjects.Action as mAct

//change from default login
GVars.configFileName = 	"katalonConfigAuto8.xml"
Act.changeUserCredentials()

String docNme = "NewTestDocumentation.docx (Draft)"
String ProjectName = 'Project Innovation'

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)


Rep.nextTestStep("Click onto [My Projects] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', ProjectName)
	

	Rep.nextTestStep("Click onto [Document] tab ")
	Rep.info("To add [Document] button within [More] tab > Admin button > Login Groups > Project Manager STR > Views > Show Document checkbox")
	gObj.buttonClickSync(Const.myProjects + "a_Documents")
	
	
Rep.nextTestStep("Click into [Inline] option")
	gObj.buttonClickSync(Const.documents + "inlineEdit")
	gAct.Wait(1)
	

Rep.nextTestStep("Select [Open] ")
	WebD.clickElement("//div[contains(@class,'editableTable tableDiv hasOperations')]//li[1]//a[1]")

	
Rep.nextTestStep("Verify details")
	String projectNme = gObj.getAttributeSync(Const.documents + 'input_Project', 'value')
	gAct.compareStringAndReport(projectNme, ProjectName)
	gObj.verifySelectedComboBoxValueInFrame(Const.documents + 'select_DocumentType', 'Project Plan', 'xpath', 0)	
	String documentName = gObj.getEditTextSync(Const.documents + 'a_DocumentName')
	gAct.compareStringAndReport(documentName, docNme)
	
	
Rep.nextTestStep("Click onto [Save and Close] button at the bottom left of the page")
	gObj.buttonClickSync(Const.documents + 'button_SaveAndClose')
