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
//import buildingBlocks.createComponentsLogedIn as component
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.BenefitType
import models.GenerateBenefitType
import models.Forecast
import models.GenerateForecast

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String benefitTypeName = Com.generateRandomText(10)
	String benefitTypeCode = benefitTypeName.toUpperCase()
	
	String[] newBenefitType = [benefitTypeName, benefitTypeCode, "Units"]
	BenefitType benefitType = GenerateBenefitType.createBenefitType(newBenefitType)
	component.createBenefitType(benefitType)
	
	def pattern = "dd/MM/yyyy"
	Date today = new Date()
	String todaysDate = today.format(pattern)
	
	String forecastName = Com.generateRandomText(10)
	String benefitName = Com.generateRandomText(8)
	
	String[] newForecast = [forecastName, "Project Innovation_CHROME_DRIVER", "KIPDEV", "KIP1TEST", benefitName, benefitType.code, todaysDate]
	Forecast forecast = GenerateForecast.createForecast(newForecast)
	component.createForecast(forecast)
	

Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Location]")
	gObj.buttonClickSync(Const.busEntMenu + "a_BenefitTypes")

		
Rep.nextTestStep("Click onto [Inline menu] against an currency")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	int rowNo = sVal.searchTableReturnRow(3, benefitType.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [delete]")
	gObj.selectInlineOption(3)

	
Rep.nextTestStep("Verify and click cancel")
	boolean errMess = gObj.elementPresentSync(Const.kip4DeleteModal + "text_DeleteMessage")
	
	if(errMess){
		String errMessage = gObj.getEditTextSync(Const.kip4DeleteModal + "text_DeleteMessage")
		gAct.findSubstringInStringAndReport(errMessage, benefitTypeName + " cannot be deleted:")
		gAct.findSubstringInStringAndReport(errMessage, "At least one Benefit refers to it")
	}
	
	
Rep.nextTestStep("Click [Close]")
	gObj.buttonClickSync(Const.kip4DeleteModal + "button_Cancel")
	sVal.searchTableAddedValue(2, benefitType.code)
