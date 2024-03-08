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
import models.Location
import models.GenerateLocation

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String locationName = Com.generateRandomText(10)
	String locCode = locationName.toUpperCase()
	String locationCode = locCode.take(3)
	
	String[] newLocation = [locationName, locationCode]
	Location location = GenerateLocation.createLocation(newLocation)
	component.createLocation(location)
	
	String newLocationName = Com.generateRandomText(10)
	String newLocCodeLwr = newLocationName.take(3)  //test case 60205
	String newLocCode = newLocationName.toUpperCase()
	String NewLocationCode = newLocCode.take(3)
	
	String[] secondLocation = [newLocationName, NewLocationCode]
	Location LocationTwo = GenerateLocation.createLocation(secondLocation)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Location]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Locations")
	
	
Rep.info("Steps to test - 60214")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")


Rep.nextTestStep("Add a [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", LocationTwo.name)
	
	
Rep.nextTestStep("Add a new [Code]")
	gObj.setEditSync(Const.kip4Generic + "input_Code", LocationTwo.code)
	
	
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	
Rep.nextTestStep("Accept alert and check location not created")
	gObj.buttonClickSync(Const.kipCloseEdit + "button_ContinueWithoutSaving")
	sVal.searchTableDeletedValue(2, LocationTwo.code)
Rep.info("~~~~~~~~~~~~~~~~~~~~~")

	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	
	
Rep.nextTestStep("Add a [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", LocationTwo.name)

	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	
	boolean codeError = gObj.elementPresentSync(Const.kip4Location + 'code_message')
	if(codeError){
		String errtext = gObj.getEditTextSync(Const.kip4Location + "code_message")
		gAct.compareStringAndReport(errtext, "Code is required")
	}
	
	
Rep.nextTestStep("Add an existing [Code]")
	gObj.setEditSync(Const.kip4Generic + "input_Code", location.code)
	
	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	boolean modalError = gObj.elementPresentSync(Const.kip4 + 'error_ModalMsg')
	if(modalError){
		String errtext = gObj.getEditTextSync(Const.kip4 + "error_ModalMsg")
		gAct.findSubstringInStringAndReport(errtext, "The code already exists")
	}

		
Rep.nextTestStep("Add a new [Code]")
	gObj.setEditSync(Const.kip4Generic + "input_Code", newLocCodeLwr) //test case 60205
		
	
Rep.nextTestStep("Select [Active] checkbox")

	def active = WebD.getCheckBoxState("//input[@id='active']")

	if(active == "true"){
		WebD.clickElement("//span[(text() = ' Active ' or . = ' Active ')]")
	}

	
Rep.info("Steps to test - 60213")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")	
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	gObj.buttonClickSync(Const.kipCloseEdit + "button_Cancel")
	
Rep.info("~~~~~~~~~~~~~~~~~~~~~")


Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	Act.verifySavePopUpText()
	
		
Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")
	sVal.searchTableAddedValue(2, LocationTwo.code)
	
	int rowNo = sVal.searchTableReturnRow(3, LocationTwo.name)
	String ActiveStatus = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable",  rowNo, "row", 4, "col")
	gAct.compareStringAndReport(ActiveStatus, "No")
