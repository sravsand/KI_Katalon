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


import models.Location
import models.GenerateLocation

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

String locationName = Com.generateRandomText(10)
String locCode = locationName.toUpperCase()
String locationCode = locCode.take(3)

String[] newLocation = [locationName, locationCode]
Location location = GenerateLocation.createLocation(newLocation)


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Location]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Locations")

	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")

	
Rep.info("Steps to test - 60215")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.nextTestStep("Click onto [Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "button_Close")
	
	
Rep.nextTestStep("Accept alert and check location not created")
	sVal.searchTableDeletedValue(2, location.code)
	
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	

Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		
	
Rep.nextTestStep("Add a [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", location.name)


Rep.nextTestStep("Add a [Code] ")
	gObj.setEditSync(Const.kip4Generic + "input_Code", location.code)
	

Rep.nextTestStep("Select [Active] checkbox")
	def active = WebD.getCheckBoxState("//input[@id='active']")

	if(active == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}

	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	Act.verifySavePopUpText()
	
		
Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	sVal.searchTableAddedValue(2, location.code)

		
Rep.info("Steps to test - 60210")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	int rowNo = sVal.searchTableReturnRow(3, location.name)
	String ActiveStatus = gObj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 4, "col")
	gAct.compareStringAndReport(ActiveStatus, "Yes")
