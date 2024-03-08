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
import models.BenefitType
import models.GenerateBenefitType

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String benefitTypeName = Com.generateRandomText(10)
	String benefitTypeCode = benefitTypeName.toUpperCase()
	
	String[] newBenefitType = [benefitTypeName, benefitTypeCode, "Financial"]
	BenefitType benefitType = GenerateBenefitType.createBenefitType(newBenefitType)
	component.createBenefitTypeInactive(benefitType)
	
	String benefitTypeNameNew = Com.generateRandomText(10)

		
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Location]")
	gObj.buttonClickSync(Const.busEntMenu + "a_BenefitTypes")

		
Rep.nextTestStep("Click onto [Inline menu] against an currency")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, benefitType.name)
	String ActiveStatus = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 4, "col")
	gAct.compareStringAndReport(ActiveStatus, "No")
	
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [open]")
	gObj.selectInlineOption(1)

	
Rep.info("Steps to test - 61843")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	gObj.selectInlineOption(1)
	
Rep.info("~~~~~~~~~~~~~~~~~~~~~")


	
Rep.info("Steps to test - 61842")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")

	gObj.setEditSync(Const.kip4Generic + "input_Name", benefitTypeNameNew)
	
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_Cancel")
	
	gObj.setEditSync(Const.kip4Generic + "input_Name", benefitType.name)

Rep.info("~~~~~~~~~~~~~~~~~~~~~")
		

Rep.nextTestStep("Select [Active] checkbox")
	def activeRem = WebD.getCheckBoxState("//input[@id='active']")

	if(activeRem == null){
		WebD.clickElement("//span[contains(text(),'Active')]")
	}
	
	
Rep.nextTestStep("Click [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")	
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the chagnes are updated accurately and the selected Benefit Type is marked as active on the Benefit Type table")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNum = sVal.searchTableReturnRow(3, benefitType.name)
	String newActiveStatus = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNum, "row", 4, "col")
	gAct.compareStringAndReport(newActiveStatus, "Yes")
	