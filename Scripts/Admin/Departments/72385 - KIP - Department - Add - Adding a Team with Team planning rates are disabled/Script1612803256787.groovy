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
import models.Department
import models.GenerateDepartment

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String departmentName = Com.generateRandomText(10)
	String departmentCode = departmentName.toUpperCase()
	
	String[] newDepartment = [departmentName, departmentCode, "PPM Solutions", "Resource Manager"]
	Department department = GenerateDepartment.createDepartment(newDepartment)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Department]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Departments")
	

Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	
	
Rep.nextTestStep("Add a [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", department.name)


Rep.nextTestStep("Add a [Code] ")
	gObj.setEditSync(Const.kip4Generic + "input_Code", department.code)
	

Rep.nextTestStep("Select [Active] checkbox")

	def active = WebD.getCheckBoxState("//input[@id='active']")

	if(active == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}
	
	
Rep.nextTestStep("Select a [Parent]")
	gObj.selectComboboxValue("parent", "parent", department.parent, department.parent)
	

Rep.nextTestStep("Select a [Manager]")
	gObj.selectComboboxValue("lineManager", "lineManager", department.manager, department.manager)


Rep.nextTestStep("Select [Available for xDemand Forecasting] checkbox")
	def dem = WebD.getCheckBoxState("//input[@id='canDemand']")

	if(dem == null){
		gObj.checkSync(Const.kip4Generic + "chk_AvailableDF")
	}

		
Rep.nextTestStep("Verify the [Financial] tab is available for selection and Click over [Financial] tab")
	WebUI.verifyElementNotVisible(findTestObject(Const.kip4Department+ "tab_Financial"), FailureHandling.OPTIONAL)


Rep.nextTestStep("Click onto [Save] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	

Rep.nextTestStep("Verify the details have been saved & retained successfully")
	sVal.searchTableAddedValue(2, department.code)

	