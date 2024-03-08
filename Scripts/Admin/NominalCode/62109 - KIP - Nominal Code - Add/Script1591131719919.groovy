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
import buildingBlocks.createComponentsLogedIn as component
import models.NominalCode
import models.GenerateNominalCode

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String NominalCodeName = Com.generateRandomText(10)
	String NominalCode_Code = NominalCodeName.toUpperCase()
	
	String[] newNominalCode = [NominalCodeName, NominalCode_Code]
	NominalCode nominalCode = GenerateNominalCode.createNominalCode(newNominalCode)
	
		
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")


Rep.nextTestStep("Click over [NominalCodes]")
	gObj.buttonClickSync(Const.financialsMenu + "a_NominalCodes")


Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")


Rep.info("Steps to test - 54297")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	
Rep.info("~~~~~~~~~~~~~~~~~~~~~")


Rep.nextTestStep("Enter text in [Name] field (Required)")
	gObj.setEditSync(Const.kip4Generic + "input_Name", nominalCode.name)

		
Rep.nextTestStep("Enter text in [Code] field (Required)")
	gObj.setEditSync(Const.kip4Generic + "input_Code", nominalCode.code)
	
	
Rep.nextTestStep("Check/Uncheck [Active] checkbox")
	def active = WebD.getCheckBoxState("//input[@id='active']")

	if(active == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}
	

Rep.info("Steps to test - 54301")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	gObj.buttonClickSync(Const.kipCloseEdit + "button_ContinueWithoutSaving")

	sVal.searchTableDeletedValue(2, nominalCode.code)
Rep.info("~~~~~~~~~~~~~~~~~~~~~")

		
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	

Rep.nextTestStep("Enter text in [Name] field (Required)")
	gObj.setEditSync(Const.kip4Generic + "input_Name", nominalCode.name)

		
Rep.nextTestStep("Enter text in [Code] field (Required)")
	gObj.setEditSync(Const.kip4Generic + "input_Code", nominalCode.code)
	
	
Rep.nextTestStep("Check/Uncheck [Active] checkbox")
	def stillActive = WebD.getCheckBoxState("//input[@id='active']")

	if(stillActive == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}
	
		
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
		
Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	sVal.searchTableAddedValue(2, nominalCode.code)
	
	int rowNo = sVal.searchTableReturnRow(3, nominalCode.name)
	String ActiveStatus = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 4, "col")
	gAct.compareStringAndReport(ActiveStatus, "Yes")

	