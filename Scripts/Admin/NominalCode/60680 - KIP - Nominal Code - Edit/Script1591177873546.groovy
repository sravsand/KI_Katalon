import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.katalon.plugin.keyword.waitforangular.WaitForAngularKeywords
import com.katalon.plugin.keyword.angularjs.DropdownKeywords

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
import models.NominalCode
import models.GenerateNominalCode
import administration.Action as adminAct

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String NominalCodeName = Com.generateRandomText(10)
	String NominalCode_Code = NominalCodeName.toUpperCase()
	
	String[] newNominalCode = [NominalCodeName, NominalCode_Code]
	NominalCode nominalCode = GenerateNominalCode.createNominalCode(newNominalCode)
	component.createNominalCode(nominalCode)
	
	String newNominalCodeName = Com.generateRandomText(8)
	
		
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")


Rep.nextTestStep("Click over [NominalCodes]")
	gObj.buttonClickSync(Const.financialsMenu + "a_NominalCodes")


Rep.nextTestStep("Click onto [Inline menu] against an NominalCodes")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, nominalCode.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)

	
Rep.info("Steps to test - 54306")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)
		
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	

Rep.info("Steps to test - 54303")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	gObj.elementPresentSync(Const.kip4Nominal + 'label_Name')
	String inputLongText = "byedhtssefbyedhtssefbyedhtssefbyedhtssefbyedhtssefbyedhtssefbyedhtssefbyedhtssef"
	gObj.setEditSync(Const.kip4Generic + "input_Name", inputLongText)
	adminAct.verifyMaximumEditLength(Const.kip4Generic + "input_Name", inputLongText, 70)

Rep.info("~~~~~~~~~~~~~~~~~~~~~")


Rep.nextTestStep("Change text in [Name] field (Required)")
	gObj.setEditSync(Const.kip4Generic + "input_Name", newNominalCodeName)


Rep.nextTestStep("Attempt to enter text in [Code] field (Required)")
	gObj.verifyAttributeSync(Const.kip4Generic + "input_Code", 'readonly')
	
	
Rep.nextTestStep("Check/Uncheck [Active] checkbox")
	WebD.clickElement("//span[contains(text(),'Active')]")

	def active = WebD.getCheckBoxState("//input[@id='active']")
	gAct.compareStringAndReport(active, null) 
	
	WebD.clickElement("//span[contains(text(),'Active')]")

	active = WebD.getCheckBoxState("//input[@id='active']")
	gAct.compareStringAndReport(active, "true")
		

Rep.nextTestStep("DO NOT check [Default Timesheet Nominal Code]")


Rep.nextTestStep("DO NOT check [Default Expense Nominal Code]")


Rep.info("Steps to test - 54306")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	gObj.buttonClickSync(Const.kipCloseEdit + "button_Cancel")

Rep.info("~~~~~~~~~~~~~~~~~~~~~")


Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()



Rep.nextTestStep("Verify the changes are updated accurately on the Currency table")
	sVal.searchTableAddedValue(3, newNominalCodeName)


Rep.info("Steps to test - 54310")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	int rowNum = sVal.searchTableReturnRow(3, newNominalCodeName)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNum, "row")
	