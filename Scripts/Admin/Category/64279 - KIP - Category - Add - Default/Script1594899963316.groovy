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
import buildingBlocks.createComponentsLogedIn as component
import models.Category
import models.GenerateCategory
import administration.Action as admAct

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String categoryName = Com.generateRandomText(10)
	String categoryCode = categoryName.toUpperCase()
	
	String[] newCategory = [categoryName, categoryCode, "true"]
	Category category = GenerateCategory.createCategory(newCategory)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [categories]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Categories")

	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")

	
Rep.info("Steps to test - 52637")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
Rep.info("save and close button disabled")	

	gObj.buttonClickSync(Const.kip4Generic + "button_Close")	
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	
Rep.info("~~~~~~~~~~~~~~~~~~~~~")

	
Rep.nextTestStep("Add a [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", category.name)
	

Rep.info("Steps to test - 53742")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")

	String longValue = "1111111111222222222233333"
	admAct.verifyMaximumEditLength(Const.kip4Generic + "input_Code", longValue, 20)
	
Rep.info("~~~~~~~~~~~~~~~~~~~~~")


Rep.nextTestStep("Add a [Code] ")
	gObj.setEditSync(Const.kip4Generic + "input_Code", category.code)


Rep.nextTestStep("Select [Active] checkbox")
	def active = WebD.getCheckBoxState("//input[@id='active']")

	if(active == null){
		WebD.clickElement("//span[contains(text(),'Active')]")
	}
	
	
Rep.nextTestStep("Check 'Default'")
   gObj.checkSync(Const.kip4Category + "chk_DefaultCategory")

	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
		
	gObj.elementPresentSync(Const.kip4Category + "text_CategoryDefaultMsg")
	gObj.buttonClickSync(Const.kip4Category + "button_OK")
		
	Act.verifySavePopUpText()

	
Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	sVal.searchTableAddedValue(2, category.code)
	
	
Rep.info("Steps to test - 60964")
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
	
	int rowNo = sVal.searchTableReturnRow(3, category.name)
	gObj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
	
	gObj.selectInlineOption(1)
		
	gObj.elementPresentSync(Const.kip4BenefitType + 'edit_LastEditInfo')
Rep.info("~~~~~~~~~~~~~~~~~~~~~")
