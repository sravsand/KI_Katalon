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
import models.LoginGroup
import models.GenerateLoginGroup


//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String loginGrpName = Com.generateRandomText(8)
	String loginGrpCode = loginGrpName.toUpperCase()
	
	String[] newLoginGrp = [loginGrpName, loginGrpCode, "All Actions"]
	LoginGroup loginGroup = GenerateLoginGroup.createLoginGroup(newLoginGrp)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Login Groups]")
	gObj.buttonClickSync(Const.securityMenu + "a_LoginGroups")
	
	
Rep.nextTestStep("Click Add")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	
	
Rep.nextTestStep("Ensure [Name] field is mandatory")
	String nameLab = gObj.getEditTextSync(Const.kip4LoginGroup + "label_Name")
	gAct.findSubstringInStringAndReport(nameLab, "*")

	
Rep.nextTestStep("If nothing is entered and focus is removed from the name field, a red message is displayed under the [Name] field")
	gObj.buttonClickSync(Const.kip4LoginGroup + "label_Name")
	gObj.buttonClickSync(Const.kip4LoginGroup + "label_Code")	
	String nmeErr = gObj.elementVisibleSync(Const.kip4LoginGroup + "errNameField")
	

Rep.nextTestStep("If nothing is entered and focus is removed from the name field, a red message is displayed under the [Code] field")
	gObj.buttonClickSync(Const.kip4LoginGroup + "label_Code")
	gObj.setEditSync(Const.kip4Generic + "input_Name", "gsdgrnm")
	
	String cdeErr = gObj.elementVisibleSync(Const.kip4LoginGroup + "errCodeField")
	
	
Rep.nextTestStep("Ensure no more than 70 characters can be entered in the [Name] field")
	String longText = "aqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuihaqwertyuih"
	gObj.setEditSync(Const.kip4Generic + "input_Name", longText + "nm")
	
	String actVal1 = gObj.getAttributeSync(Const.kip4Generic + "input_Name", 'value')
	gAct.compareStringAndReport(actVal1, longText)


Rep.nextTestStep("Add [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", loginGroup.name)

	
Rep.nextTestStep("Ensure [Code] field is mandatory")
	String codeLab = gObj.getEditTextSync(Const.kip4LoginGroup + "label_Code")
	gAct.findSubstringInStringAndReport(codeLab, "*")

	
Rep.nextTestStep("Ensure no more than 20 characters can be entered in the [Code] field")
	String longCodeText = "aqwertyuihaqwertyuih"
	gObj.setEditSync(Const.kip4Generic + "input_Code", longCodeText + "nm")
	
	String actVal2 = gObj.getAttributeSync(Const.kip4Generic + "input_Code", 'value')
	gAct.compareStringAndReport(actVal2, longCodeText)
	

Rep.nextTestStep("Add a [Code]")
	gObj.setEditSync(Const.kip4Generic + "input_Code", loginGroup.code)
	
	
//Rep.nextTestStep("Ensure [Default Action View] dropdown is mandatory")
//Rep.nextTestStep("If nothing is entered and focus is removed from the name field, a red message is displayed under the [Default Action View] dropdown ")
//Rep.nextTestStep("Select option from [Default Action View] dropdown")


Rep.nextTestStep("Click Save and Close")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	Act.verifySavePopUpText()



