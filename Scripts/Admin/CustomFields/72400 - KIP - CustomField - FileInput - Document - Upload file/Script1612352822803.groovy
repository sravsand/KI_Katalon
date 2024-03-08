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
import models.CostCentreCustomField
import models.GenerateCostCentreCustomField
import models.CostCentre
import models.GenerateCostCentre
import customfields.Create as createCF
import customfields.Common as ComCF
import excel.Action as eAct

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads\\CustomfieldTest.docx"

	String tableName = "63f30b25-1931-4053-98a5-eec594abfc22"
	String tabName = Com.generateRandomText(5)
	String costCentreCFName = Com.generateRandomText(10)
	
	String costCentreName = Com.generateRandomText(10)
	String costCentreCode = costCentreName.toUpperCase()
	
	String[] newCostCentre = [costCentreName, costCentreCode, "Some notes"]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	
	ComCF.deleteAllCustomField(tableName, "Cost Centre")
	
	String[] newCostCentreCF = [costCentreCFName, "Document Attachment", "Normal", "", ""]
	CostCentreCustomField costCentreCustomField = GenerateCostCentreCustomField.createCostCentreCustomField(newCostCentreCF)
	createCF.costCentreCustomField_DocumentAtt(tabName, costCentreCustomField)
	
	String desc = Com.generateRandomText(70)
	String descLong = desc + Com.generateRandomText(5)
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")

	
Rep.nextTestStep("Click over [Cost Centre]")
	gObj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
	
	
Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_Add")
	
	
Rep.nextTestStep("Add a [Name]")
	gObj.setEditSync(Const.kip4Generic + "input_Name", costCentre.name)

	
Rep.nextTestStep("Add a [Code] ")
	gObj.setEditSync(Const.kip4Generic + "input_Code", costCentre.code)
	

Rep.nextTestStep("Select [Active] checkbox")

	def CCactive = WebD.getCheckBoxState("//input[@id='active']")

	if(CCactive == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}
	
	
Rep.nextTestStep("Navigate to test tab")
	gObj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")
	

Rep.nextTestStep("Click 'Upload Document' button")
	gObj.buttonClickSync(Const.kip4CostCentre + "a_Upload Document")

	
Rep.nextTestStep("Layout is as expected")


Rep.nextTestStep("Click add and Select file and click open or double click file")
	gObj.buttonClickSync(Const.kip4DocUpload + "btn_Add")
	gAct.Wait(1)
	eAct.uploadFile(downloadPath)
	gAct.Wait(2)
	

Rep.nextTestStep("Description field has a max char limit of 70")
	gObj.setEditSync(Const.kip4DocUpload + "input_Description", descLong)
	String inpText = gObj.getAttributeSync(Const.kip4DocUpload + "input_Description", 'value')
	gAct.compareStringAndReport(inpText, desc)
	

Rep.nextTestStep("Enter description of max 70 chars")
	gObj.setEditSync(Const.kip4DocUpload + "input_Description", desc)

	
Rep.nextTestStep("Optional: Check 'Keep Document Checked Out' checkbox")
	gObj.buttonClickSync(Const.kip4DocUpload + "chk_KeepDocumentCheckedOut")
	def active = WebD.getCheckBoxState("//input[@id='keepCheckedOut']")
	gAct.compareStringAndReport(active, "true")
	gObj.buttonClickSync(Const.kip4DocUpload + "chk_KeepDocumentCheckedOut")
	active = WebD.getCheckBoxState("//input[@id='keepCheckedOut']")
	gAct.compareStringAndReport(active, null)
	

Rep.nextTestStep("Click OK")
	gObj.buttonClickSync(Const.kip4DocUpload + "button_Save and Close")


Rep.nextTestStep("Ensure file has been attached to Cost Centre")
	boolean uploadExt = gObj.elementVisibleSync(Const.kip4CostCentre + "a_Upload")
	if(uploadExt) {
		String UploadDisp = gObj.getEditTextSync(Const.kip4CostCentre + "a_Upload")
		gAct.compareStringAndReport(UploadDisp, desc)
	}
	
	
Rep.nextTestStep("Click onto [Save & Close] button")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify the changes are updated accurately on the location table")
	gObj.refreshUI()
	sVal.searchTableAddedValue(2, costCentre.code)
	
	
Rep.info("clean up custom field")
	ComCF.cleanUpCostCentreCustomfields(costCentreCFName, tableName)
	