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
import models.Login
import models.GenerateLogin
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre

//change from default login
GVars.configFileName = 	"katalonConfigAuto6.xml"
Act.changeUserCredentials()

String searchItem = "Logins"
String searchVal = "Login"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)

	String code = Com.generateRandomText(10)
	code = code.toUpperCase()
	String firstName = Com.generateRandomText(5)
	String surname = Com.generateRandomText(8)
	String name = firstName + " " + surname
	String userNme = firstName.take(1) + surname + "@workflows.com"
	
	String costCentreName = Com.generateRandomText(6)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	String costCentreNew = component.costCentre(costCentre)
	
	String ResourceName = Com.generateRandomText(10)
	String newDate = Com.todayDate()
	String[] newResource = [name, "Employee", "KIP Dev Team 1", "KIP 1 Team Lead", "Cleckheaton", "Full Time Calendar (My Region)", costCentreNew, "Martin Phillips", userNme, "", "100", "", "", "", "", "", ""]
	Resource resource = GenerateResource.createNewResource(newResource)
	component.createResource(resource)
	
	String[] newLogin = [name, code, userNme, resource.code, "TEst1234"]
	Login login = GenerateLogin.createLogin(newLogin)
	component.createLogin(login, "Administration")
	
	String clonedCode = Com.generateRandomText(10)
	clonedCode = clonedCode.toUpperCase()
	String clonedFirstName = Com.generateRandomText(5)
	String clonedSurname = Com.generateRandomText(8)
	String clonedName = clonedFirstName + " " + clonedSurname
	String clonedUserNme = clonedFirstName.take(1) + clonedSurname + "@workflows.com"
	
	String[] newClonedLogin = [clonedName, clonedCode, clonedUserNme, resource.code, "TEst1234"]
	Login clonedLogin = GenerateLogin.createLogin(newClonedLogin)


Rep.nextTestStep("Select [Logins] from search filter")
	Nav.selectSearchFilter(searchItem)


Rep.nextTestStep("Click onto [Inline menu] of a Logins")
	gObj.createNewFilter("Login Name", "=", login.name)
	int rowNo = sVal.searchTableReturnRow(3, login.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	
Rep.nextTestStep("Click onto [clone]")
	gObj.selectInlineOption(2)
	

Rep.nextTestStep("Amend text in [Name] field")
	gObj.scrollToElementSync(Const.kip4Login + "label_Name")
	gObj.setEditSync(Const.kip4Login + "input_Name", clonedLogin.name)
	
	
Rep.nextTestStep("Add text in [Code] field")
	gObj.setEditSync(Const.kip4Login + "input_Code", clonedLogin.code)

	
Rep.nextTestStep("Select [Active] through checkbox")
	def active = WebD.getCheckBoxState("//input[@id='active']")

	if(active == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}
	
	
Rep.nextTestStep("Add text in [User ID] field")
	gObj.scrollToElementSync(Const.kip4Login + "label_UserID")
	gObj.setEditSync(Const.kip4Login + "input_UserID", clonedLogin.userId)
		
	gObj.scrollToElementSync(Const.kip4Login + "label_Password")
	gObj.setEditSync(Const.kip4Login + "input_Password", clonedLogin.password)
	gObj.setEditSync(Const.kip4Login + "input_PasswordConfirm", clonedLogin.password)
				
	def expire = WebD.getCheckBoxState("//input[@id='general.passwordNeverExpires']")
		
	if(expire == null){
		gObj.buttonClickSync(Const.kip4Login + "chk_PasswordNeverExpires")
	}
		
	def change = WebD.getCheckBoxState("//input[@id='general.passwordMustBeChangedAtNextLogin']")
		
	if(change == true){
		gObj.buttonClickSync(Const.kip4Login + "chk_PasswordMustBeChanged")
	}
	
	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the cloned [Activity] is available for selection in the table")
	gObj.createNewFilter("Login Name", "=", clonedLogin.name)
	sVal.searchTableAddedValue(3, clonedLogin.name)

