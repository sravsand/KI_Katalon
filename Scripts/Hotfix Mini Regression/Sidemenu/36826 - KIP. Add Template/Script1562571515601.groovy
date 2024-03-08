import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.junit.After
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import keyedInProjects.Populate as Pop
import keyedInProjects.Object as Obj
import templates.Validate as tVal
import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.WebDriverMethods as WebD
import administration.Action as aAct

String browser = Com.getBrowserType()
String loginProfile = "PROJECT_MANAGER_STR"
String newTemplate = "Test Project Mgm LC " + browser
String project = "TestProject_Auto"

//change from default login
GVars.configFileName = 	"katalonConfigAuto9.xml"
Act.changeUserCredentials()

Rep.nextTestStep("Log in to the environment under test")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
	Act.changeProject('All Company', 'Customer Projects', 'PSA Solutions Inc', 'Project Innovation')
	

Rep.nextTestStep("Click onto [Admin] button top right hand side corner")
	gObj.buttonClickSync(Const.mainToolBar + "administration")
	
	
Rep.nextTestStep("Click [Login Profile] ")
	gObj.buttonClickSync(Const.securityMenu + 'a_LoginProfiles')
	
	
Rep.nextTestStep("Click onto [Inline] menu of the login profile under test i.e PROJECT_MANAGER_STR and click [Open]")
	aAct.openLoginProfile(loginProfile)


Rep.nextTestStep("Click onto [Setup] tab")
	gObj.buttonClickSync(Const.kip4LoginProfile + 'Tab_Setup')
	
	
Rep.nextTestStep("Tick the box for [Project Template]")

	def active = WebD.getCheckBoxState("//input[@id='administration.setupProjectTemplate']")

	if(active == null){
		gObj.checkSync(Const.kip4Generic + "chk_ProjectTemplate")
		gObj.checkSync(Const.kip4Generic + "button_SaveAndClose")
	}else {
		gObj.checkSync(Const.kip4Generic + "button_Close")
	}


Rep.nextTestStep("Click on [Search] from the side bar menu")
	WebD.clickElement("//div[@id='s2id_filterType']")


Rep.nextTestStep("Search the project type under test using the filter options")
	gObj.buttonClickSubSync(Const.searchFilters + 'selectSearchItemByValue', "Projects", "value")
	gObj.buttonClickSync(Const.searchFilters + 'a_Project Templates Filter')
	

Rep.nextTestStep("Click onto [Add Template] ")
	gObj.buttonClickSync(Const.templates + 'a_Add Template')


Rep.nextTestStep("Select [Project Type] ")
	gObj.buttonClickSync(Const.templates + 'ProjectType_DropDown')
	gObj.buttonClickSubSync(Const.templates + 'select_ProjectType', project, "value")

	
Rep.nextTestStep("Select [Add an Empty Project] Radio button")
	gObj.buttonClickSync(Const.templates + 'radio_EmptyProject')
	

Rep.nextTestStep("Click [Next] ")
	gObj.buttonClickSync(Const.templates + 'button_Next')


Rep.nextTestStep("Verify the [Option] tab is present and click")
	gObj.setEditSync(Const.templates + 'input_Description', newTemplate)
		
	gObj.buttonClickSync(Const.templates + 'customerDropDown')
	gObj.buttonClickSubSync(Const.templates + 'selectCustomer', 'PPM Solutions Inc', "cust")
	
	gObj.buttonClickSync(Const.templates + 'workingTimeDropDown')
	gObj.buttonClickSubSync(Const.templates + 'selectWorkingTime', 'Full Time Calendar (My Region)', "wtime")
	
	gObj.buttonClickSync(Const.templates + 'ParentDropDown')
	gObj.buttonClickSubSync(Const.templates + 'selectParent', 'PSA Solutions Inc', "parent")

	gObj.buttonClickSync(Const.templates + 'a_Template Options')


Rep.nextTestStep("Tick all the checkboxes under [Options] tab")
	Obj.clickCheckboxWith2Identifiers(Const.templates + 'chk_CopyTasks', true, "//div[@id='ProjectTemplateOptions']/div/div/div/div/div/label/span")
	Obj.clickCheckboxWith2Identifiers(Const.templates + 'chk_CopyForecast', true, "//div[@id='ProjectTemplateOptions']/div/div/div[2]/div/div/label/span")
	Obj.clickCheckboxWith2Identifiers(Const.templates + 'chk_CopyRisks', true, "//div[@id='ProjectTemplateOptions']/div/div/div[3]/div/div/label/span")
	Obj.clickCheckboxWith2Identifiers(Const.templates + 'chk_CopyDeliverables', true, "//div[@id='ProjectTemplateOptions']/div/div/div[4]/div/div/label/span")
	

Rep.nextTestStep("Click [Save] and Close")
	gObj.buttonClickSync(Const.templates + 'button_SaveAndClose')
	gAct.Wait(GVars.shortWait)


Rep.nextTestStep("On The Search screen Click onto [Add ]")
	WebD.clickElement("//div[@id='editableViewButtons']//a[1]")

	
Rep.nextTestStep("Select [Project Type] that the recently project was added into")
	gObj.buttonClickSync(Const.templates + 'ProjectType_DropDown')
	gObj.buttonClickSubSync(Const.templates + 'select_ProjectType', project, "value")
	

Rep.nextTestStep("Select [Create Project from Template] radio button")
	gObj.buttonClickSync(Const.templates + 'radio_CreateProjectFromTemplate')

	
Rep.nextTestStep("Click onto [Template] dropdown")
	gObj.buttonClickSync(Const.templates + 'template_DropDown')

	
Rep.nextTestStep("Verify the recently added project is present in the template dropdown")
	WebUI.verifyElementPresent(findTestObject(Const.templates + 'SelectTemplate', [('template'): newTemplate]), GVars.longWait)
	gObj.buttonClickSubSync(Const.templates + 'SelectTemplate', newTemplate, "template")
	
	
Rep.nextTestStep("[close]")
	gObj.buttonClickSync(Const.templates + 'button_Close')
	gAct.Wait(1)
		

Rep.nextTestStep("GO to Search screen and verify the [Template] column for the recently added template is populated as [Yes]")
	tVal.templateColumnSetToYes(newTemplate, 4, 9)
