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
import gantt.Action as ganttAct

String myProject = 'Tech Crunch Web Services Deployment'
String browser = Com.getBrowserType()
String DeliverableName = "Another deliverable " + browser

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click onto [My Projects] from [side bar]")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', myProject)
	
	
Rep.nextTestStep("Click onto [Deliverables] tab")
	Nav.menuItemAndValidateURL(Const.myProjects + "menu_Deliverables", "MVC-PRL1-DELIVERABLES?")
	

Rep.nextTestStep("Click [Add]  button")
	gObj.buttonClickSync(Const.deliverables + 'a_Add')
	
	
Rep.nextTestStep("Enter Data in [Sequence] field")
	gObj.setEditSync(Const.deliverables + 'input_Sequence', "1")
	
	
Rep.nextTestStep("Enter Data in [Name] field")
	gObj.setEditSync(Const.deliverables + 'input_Name', DeliverableName)

	
Rep.nextTestStep("Select [Owner] - If applicable")
	String ownerText = gObj.getAttributeSync(Const.deliverables + 'input_Owner', 'value')
	if(ownerText == ""){
		gObj.setEditSync(Const.deliverables + 'input_Owner', GVars.user)
		gObj.buttonClickSync(Const.deliverables + 'input_Sequence')
	}
	
	
Rep.nextTestStep("Select [Project] - If applicable")
	String projText = gObj.getAttributeSync(Const.deliverables + 'input_Project', 'value')
	if(projText != myProject){
		gObj.setEditSync(Const.deliverables + 'input_Project', GVars.user)
		gObj.buttonClickSync(Const.deliverables + 'input_Sequence')
	}


Rep.nextTestStep("Select [Type]")
	gObj.setEditSync(Const.deliverables + 'input_Type', "Progress Deliverable")
	gObj.buttonClickSync(Const.deliverables + 'input_Sequence')

	
Rep.nextTestStep("Select [Publish To] - if applicable")
	gObj.selectComboByLabelSync(Const.deliverables + 'select_PublishTo', "Project")
	gObj.buttonClickSync(Const.deliverables + 'input_Sequence')
	

Rep.nextTestStep("Add some Note to [Description] field")
	gObj.setEditSync(Const.deliverables + 'textarea_Description', "Some Random Text")
	
	
Rep.nextTestStep("Select [Delivery Date]")
	String todayDate = Com.todayDate()
	String newDate = gAct.increaseDate(todayDate, "dd/MM/yyyy", 1, "dd/MM/yyyy")
	gObj.setEditSync(Const.deliverables + 'input_DeliveryDate', newDate)
	
	
Rep.nextTestStep("Click [Save ] or [Save & Close]")
	String cellVal
	int intCnt
	gObj.buttonClickSync(Const.deliverables + 'button_SaveAndClose')
	gAct.Wait(GVars.shortWait)
	gObj.selectComboByLabelSync(Const.deliverables + 'select_ViewStatus', "All")
	
	gObj.buttonClickSync(Const.deliverables + 'displayDateTo')
	
	gObj.buttonClickSync(Const.deliverables + 'DatePicker/button_Today')
	gObj.buttonClickSync(Const.deliverables + 'DatePicker/next_Month')

	gObj.buttonClickSync(Const.deliverables + 'DatePicker/selectDate')
	
	gAct.Wait(GVars.shortWait)
	
	int rowCnt = WebD.getTableRowCountOniFrame('/html[1]/body[1]/div[@class="main-container container-fluid"]/div[@class="page-container"]/div[@class="page-content"]/div[@class="page-partial"]/iframe[@class="resize"]', "//div[@id = 'edGrid1']")
	
	for(intCnt = 0; intCnt < rowCnt - 1; intCnt ++){
		cellVal = gObj.getEditTextDblSubIntPosSync(Const.deliverables + 'deliverablesTable', intCnt, "row", 9, "col")
		if(cellVal == GVars.user){
			break
		}
	}
	
	String displayed = gObj.getEditTextDblSubIntPosSync(Const.deliverables + 'deliverablesTable', intCnt, "row", 3, "col")
	gAct.compareStringAndReport(displayed, DeliverableName)
	