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
	component.createNominalCodeDefaults(nominalCode)
	
	String newNominalCodeName = Com.generateRandomText(10)
	String newNominalCode_Code = newNominalCodeName.toUpperCase()
	
	String[] nextNominalCode = [newNominalCodeName, newNominalCode_Code]
	NominalCode nominalCodeNew = GenerateNominalCode.createNominalCode(nextNominalCode)
	component.createNominalCode(nominalCodeNew)
		
	String updateNominalCodeName = Com.generateRandomText(8) + "1" + "!"
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")


Rep.nextTestStep("Click over [NominalCodes]")
	gObj.buttonClickSync(Const.financialsMenu + "a_NominalCodes")
	

Rep.nextTestStep("Click onto [Inline menu] against an NominalCodes")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, nominalCodeNew.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)


Rep.info("Steps to test - 54309")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.nextTestStep("Enter text in [Name] field (Required)")
	gObj.setEditSync(Const.kip4Generic + "input_Name", updateNominalCodeName)

	
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	gObj.buttonClickSync(Const.kipCloseEdit + "button_ContinueWithoutSaving")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")

	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")


Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)

		
Rep.info("Steps to test - 54294")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	gObj.elementPresentSync(Const.kip4Nominal + 'label_Name')
	
	String inputLongText = "byedhtssefbyedhtssefbyedhtssefbyedhtssefbyedhtssefbyedhtssefbyedhtssefbyedhtssef"
	gObj.setEditSync(Const.kip4Generic + "input_Name", inputLongText)
	adminAct.verifyMaximumEditLength(Const.kip4Generic + "input_Name", inputLongText, 70)
			
Rep.info("~~~~~~~~~~~~~~~~~~~~~")

	
Rep.nextTestStep("Enter text in [Name] field (Required)")
	gObj.setEditSync(Const.kip4Generic + "input_Name", updateNominalCodeName)

		
Rep.nextTestStep("Attempt to enter text in [Code] field (Required)")
	gObj.verifyAttributeSync(Const.kip4Generic + "input_Code", 'readonly')
	
	
Rep.nextTestStep("Check/Uncheck [Active] checkbox")
	def active = WebD.getCheckBoxState("//input[@id='active']")

	if(active == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}
	
	
Rep.nextTestStep("Check/Uncheck [Default Timesheet Nominal Code]")
	gObj.checkSync(Const.kip4Nominal + "chk_DefaultExpenseNominalCode")


Rep.nextTestStep("Check/Uncheck  [Default Expense Nominal Code]")
	gObj.checkSync(Const.kip4Nominal + "chk_DefaultTimesheetNominalCode")

	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	gAct.Wait(2)
	String[] svrNme = GVars.server.split("/")
	String errorMsg = gObj.getEditTextSubSync(Const.kip4Nominal + "label_NominalCodeChangeDef", svrNme[2], "serv")
	gAct.findSubstringInStringAndReport(errorMsg, "You are about to change the default to Nominal Code '" + updateNominalCodeName + "'")
	
	gObj.buttonClickSync(Const.kip4Nominal + "button_OK")
	
	Act.verifySavePopUpText()
		
	
Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	sVal.searchTableAddedValue(2, nominalCode.code)
	
	int rowNum = sVal.searchTableReturnRow(3, updateNominalCodeName)
	String ActiveStatus = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNum, "row", 4, "col")
	gAct.compareStringAndReport(ActiveStatus, "Yes")
	