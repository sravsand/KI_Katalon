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
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre

//change from default login
GVars.configFileName = 	"kip4ConfigDefault.xml"
Act.changeUserCredentials()


Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
		
	String costCentreName = Com.generateRandomText(6)
	String costCentreCode = costCentreName.toUpperCase()
	String[] newCostCentre = [costCentreName, costCentreCode, ""]
	CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
	String costCentreNew = component.costCentre(costCentre)
	
	String resourceNotes = Com.generateRandomText(17)
	String ResourceName = Com.generateRandomText(10)
	String newDate = Com.todayDate()
	String[] newResource = ["Dave_" + ResourceName, "Employee", "KIP Dev Team 1", "KIP 1 Team Lead", "Cleckheaton", "Full Time Calendar (My Region)", costCentreNew, "Martin Phillips", "AutoTest@Workflows.com", "", "100", "", "", "", "", "", resourceNotes]
	Resource resource = GenerateResource.createNewResource(newResource)
	
	String templateName = "Project Manager"
	
	
Rep.nextTestStep("Click over [Adminstration] icon (page top  right)")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click over [Department]")
	gObj.buttonClickSync(Const.busEntMenu + "a_Resources")
	

Rep.nextTestStep("Click onto [Add] button")
	gObj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
	

Rep.nextTestStep("Select [Add from a template]")
	gObj.buttonClickSync(Const.kip4Resource + 'radio_AddTemplateResource')
		
		
Rep.nextTestStep("Click on [Template] dropdown menu")
	gObj.buttonClickSync(Const.kip4Resource + 'dropDown_Template')
		
		
Rep.nextTestStep("Select a template from dropdown list")
	gObj.setEditSync(Const.kip4Resource + 'input_Template', templateName)
	gObj.buttonClickSubSync(Const.kip4Resource + 'select_Template', templateName, "temp")
		
		
Rep.nextTestStep("Click [Continue]")
	gObj.buttonClickSync(Const.kip4Resource + 'button_Continue')
		
		
Rep.nextTestStep("Verify the pre-populated [Name] field")
	gObj.setEditSync(Const.kip4Resource + "input_ResourceName", resource.name)
	
	def active = WebD.getCheckBoxState("//input[@id='active']")

	if(active == null){
		gObj.checkSync(Const.kip4Generic + "chk_Active")
	}
	
		
Rep.nextTestStep("Search & add [Department]")
	gObj.scrollToElementSync(Const.kip4Resource + 'label_Department')
	gObj.selectComboboxValue("general.department", "general.department", resource.department, resource.department)
		
		
Rep.nextTestStep("Search & add [Primary Role]")
	gObj.selectComboboxValue("general.primaryRole", "general.primaryRole", resource.role, resource.role)
	
		
Rep.nextTestStep("Search & add [Location]")
//	gObj.scrollToElementSync(Const.kip4Resource + 'label_Location')
	gObj.selectComboboxValue("general.location", "general.location", resource.location, resource.location)
	
		
Rep.nextTestStep("Search & add [Working Time]")
//	gObj.scrollToElementSync(Const.kip4Resource + 'label_WorkingTime')
	gObj.selectComboboxValue("general.workingTime", "general.workingTime", resource.workingTime, resource.workingTime)
	
		
Rep.nextTestStep("Search & add [Cost Centre]")
//	gObj.scrollToElementSync(Const.kip4Resource + 'label_CostCentre')
	String cCentre = resource.costCentre.toString().toLowerCase()
	gObj.selectComboboxValue("general.costCentre", "general.costCentre", resource.costCentre, cCentre)
	
	
Rep.nextTestStep("Search & add [LineManager]")
//	gObj.scrollToElementSync(Const.kip4Resource + 'label_LineManager')
	gObj.selectComboboxValue("general.lineManager", "general.lineManager", resource.lineManger, resource.lineManger)
	
	
Rep.nextTestStep("Set email]")
//	gObj.scrollToElementSync(Const.kip4Resource + 'label_Email')
	gObj.setEditSync(Const.kip4Resource + "input_Email", resource.email)
	
	
Rep.nextTestStep("Set telephone]")
//	gObj.scrollToElementSync(Const.kip4Resource + 'label_Telephone')
	gObj.setEditSync(Const.kip4Resource + "input_phoneNumber", resource.telephone)
		
		
Rep.nextTestStep("Set notes]")
//	gObj.scrollToElementSync(Const.kip4Resource + 'label_Notes')
	gObj.setEditSync(Const.kip4Resource + "textarea_Notes", resource.notes)
	
	
Rep.nextTestStep("Click [Save and Close]")
	gObj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
	Act.verifySavePopUpText()
	
	
Rep.nextTestStep("Verify recently added risk within [deliverable Type] table")
	gObj.createNewFilter("Resource Name", "=", resource.name)
	sVal.searchTableAddedValue(3, resource.name)
	
