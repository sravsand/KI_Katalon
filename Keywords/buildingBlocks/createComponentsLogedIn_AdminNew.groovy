package buildingBlocks

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.katalon.plugin.keyword.calendar.SetDateCalendarKeyword as SetDateCalendarKeyword
import org.openqa.selenium.Keys as Keys
import internal.GlobalVariable as GVars

import global.Report as Rep
import global.Action as gAct
import global.WebDriverMethods as WebD
import global.Common as Com
import global.Object as Obj
import global.Validate as gVal

import keyedInProjects.DatePicker as dpck
import keyedInProjects.Action as Act
import keyedInProjects.Constants as Const
import keyedInProjects.Populate as Pop
import keyedInProjects.Navigate as Nav
import timesheets.Populate as tPop
import timesheets.Action as tAct
import search.Validate as sVal

import models.GenerateRole
import models.Role
import models.GenerateResource as genRes
import models.Resource
import models.Customer
import models.GenerateCustomer as genCust
import models.Company
import models.GenerateCompany as genComp
import models.Project
import models.GenerateProject as genProj
import models.CostCentre
import models.GenerateCostCentre as genCCentre
import models.Department
import models.GenerateDepartment as genDep
import models.Programme
import models.GenerateProgramme as genProg
import models.Parent
import models.GenerateParent as genPar
import models.Cost
import models.GenerateCost as genCost
import models.Charge
import models.GenerateCharge as genChg
import models.Timesheet
import models.Expense
import models.ExpenseType
import models.Forecast
import models.GenerateForecast
import models.GenerateExpenseType
import models.ActionType
import models.GenerateActionType
import models.Category
import models.GenerateCategory
import models.Contract
import models.GenerateContract
import models.Risk
import models.GenerateRisk
import models.Issue
import models.GenerateIssue
import models.ActionView
import models.GenerateActionView
import models.Activity
import models.GenerateActivity
import models.BenefitType
import models.GenerateBenefitType
import models.ClientGroup
import models.GenerateClientGroup
import models.Client
import models.GenerateClient
import models.Contact
import models.GenerateContact
import models.DeliverableType
import models.GenerateDeliverableType
import models.Location
import models.GenerateLocation
import models.Currency
import models.GenerateCurrency
import models.NominalCode
import models.GenerateNominalCode
import models.Skill
import models.GenerateSkill
import models.Login
import models.GenerateLogin
import models.LoginPG
import models.GenerateLoginPG
import models.LoginGroup
import models.GenerateLoginGroup
import models.LoginGroupWithTabs
import models.GenerateLoginGroupWithTabs
import models.LoginProfile
import models.GenerateLoginProfile
import models.SecurityGroup
import models.GenerateSecurityGroup
import models.Claim
import models.GenerateClaim
import models.Lock
import models.GenerateLock
import models.Invoice
import models.GenerateInvoice
import models.Deliverable
import models.GenerateDeliverable
import models.BenefitActual
import models.GenerateBenefitActual
import models.Action
import models.GenerateAction
import models.RiskAction
import models.GenerateRiskAction
import models.IssueAction
import models.GenerateIssueAction
import models.WorkingTimes
import models.GenerateWorkingTimes
import models.Assignment
import models.GenerateAssignment
import models.Supply
import models.GenerateSupply
import models.ExchangeRate
import models.GenerateExchangeRate
import models.ProjectSnapshot
import models.GenerateProjectSnapshot
import models.ExpenseWithCurrency
import models.GenerateExpenseWithCurrency
import models.MitigationPlanItemType
import models.GenerateMitigationPlanItemType
import models.BillingPriceList
import models.GenerateBillingPriceList
import models.NewContact
import models.GenerateNewContact
import models.Notes
import models.GenerateNotes
import models.SkillWithProjRes
import models.GenerateSkillWithProjRes

public class createComponentsLogedIn_AdminNew{

	static def resource(String[] resourceDetails){
		Resource resourceNew = genRes.createNewResource(resourceDetails)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Resources"))
		WebUI.click(findTestObject(Const.roleMenu + "a_Add"))

		WebUI.click(findTestObject(Const.resourceMenu + "radio_AddEmptyResource"))
		WebUI.click(findTestObject(Const.resourceMenu + "button_OK"))

		Pop.newResourceForm(resourceNew)

		WebUI.click(findTestObject(Const.resourceMenu + "button_SaveAndClose"))
		gAct.Wait(GVars.shortWait)
		boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.resourceMenu + "saveResourceError"), GVars.midWait, FailureHandling.CONTINUE_ON_FAILURE)

		if(saveError){
			resourceNew.code = Act.resourceSearch("Resource Name", resourceNew.name)
		}else{
			Rep.fail("Resource : " + resourceNew.name + " was not created.")
			Rep.screenshot(resourceNew.name )
			WebUI.click(findTestObject(Const.resourceMenu + "button_Close"))
		}

		gAct.Wait(GVars.shortWait)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))

		return resourceNew.code
	}


	static def customer(Customer customer){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_Customers")
		Obj.buttonClickSync(Const.columnPicker + "a_Add")

		Pop.newCustomerForm(customer)

		Obj.buttonClickSync(Const.customerMenu + "button_SaveAndClose")
		gAct.Wait(GVars.shortWait)
		customer.code = Act.customerSearch("Customer Name", customer.name)

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		return customer.code
	}


	static def project(Project project){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_Projects")
		Obj.buttonClickSync(Const.columnPicker + "a_AddProject")
		Obj.buttonClickSync(Const.projectsMenu + "radio_AddEmptyProject")
		Obj.buttonClickSync(Const.projectsMenu + "button_Finish")

		Pop.newProjectForm(project)

		Obj.buttonClickSync(Const.projectsMenu + "button_SaveAndClose")
		gAct.Wait(GVars.shortWait)
		project.code = Act.projectSearch("Project Name", project.name)

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		return project.code
	}


	static def costCentre(CostCentre costCentre){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", costCentre.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", costCentre.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.setEditSync(Const.kip4CostCentre + "CostCentre_notes", costCentre.notes)
		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		return costCentre.code
	}


	static def costCentreWithCustomField(CostCentre costCentre, String tabName, String CustVal){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", costCentre.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", costCentre.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.setEditSync(Const.kip4CostCentre + "CostCentre_notes", costCentre.notes)
		Obj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")

		Obj.setEditSync(Const.kip4CostCentre + "input_Customfield", CustVal)
		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		return costCentre.code
	}


	static def costCentreWithEntityPicker(CostCentre costCentre, String tabName, String CustVal){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", costCentre.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", costCentre.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.setEditSync(Const.kip4CostCentre + "CostCentre_notes", costCentre.notes)

		Obj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")

		Obj.buttonClickSync(Const.kip4CostCentre + "dropDown_CFKeyword")
		Obj.setEditSync(Const.kip4CostCentre + "input_CustomfieldExistingVal", CustVal)

		Obj.buttonClickSubSync(Const.kip4CostCentre + "select_EntityPicker", CustVal, "value")

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		return costCentre.code
	}


	static def costCentreWithDynamicCustomField(CostCentre costCentre, String tabName, String CustVal){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", costCentre.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", costCentre.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.setEditSync(Const.kip4CostCentre + "CostCentre_notes", costCentre.notes)


		Obj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")

		Obj.buttonClickSync(Const.kip4CostCentre + "a_btnAddDynKey")
		Obj.setEditSync(Const.kip4CostCentre + "input_newDynKeyword", CustVal)
		Obj.buttonClickSync(Const.kip4CostCentre + "btn_AddDynKeyModal")
		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		return costCentre.code
	}


	static def costCentreWithExistingCustomField_AddValueOnTab(CostCentre costCentre, String tabName, String CustVal){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
		Obj.buttonClickSync(Const.columnPicker + "a_New Filter")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		int rowNo = sVal.searchTableReturnRow(3, costCentre.name)

		Obj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

		Obj.selectInlineOption(1)

		Obj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")
		gAct.Wait(1)
		WebUI.mouseOver(findTestObject(Const.kip4CostCentre + "dropdownSelector"))
		Obj.buttonClickSync(Const.kip4CostCentre + "dropdownSelector")

		Obj.buttonClickSync(Const.kip4CostCentre + "blankCell")


		Obj.setEditSync(Const.kip4CostCentre + "input_ExistingVal_extra", CustVal)
		gAct.Wait(1)

		boolean eleVis = Obj.elementVisibleSync(Const.kip4CostCentre + "dropDown_AddItem")

		if(eleVis){
			Obj.buttonClickSync(Const.kip4CostCentre + "dropDown_AddItem")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		return costCentre.code
	}


	@Keyword
	static def costCentreWithExistingCustomField(CostCentre costCentre, String tabName, String CustVal){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", costCentre.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", costCentre.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.setEditSync(Const.kip4CostCentre + "CostCentre_notes", costCentre.notes)

		Obj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")
		Obj.setEditSync(Const.kip4CostCentre + "input_CustomfieldExistingVal", CustVal)
		gAct.Wait(1)

		boolean eleVis = Obj.elementVisibleSync(Const.kip4CostCentre + "dropDown_AddItem")

		if(eleVis){
			Obj.buttonClickSync(Const.kip4CostCentre + "dropDown_AddItem")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		return costCentre.code
	}


	@Keyword
	static def costCentreWithExistingCustomFieldTab2(CostCentre costCentre, String tabName, String CustVal){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_CostCentres")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", costCentre.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", costCentre.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.setEditSync(Const.kip4CostCentre + "CostCentre_notes", costCentre.notes)

		Obj.buttonClickSubSync(Const.kip4CostCentre + "newTab", tabName, "tab")

		Obj.setEditSync(Const.kip4CostCentre + "input_CustomfieldExistingVal_Tab2", CustVal)
		gAct.Wait(1)

		boolean eleVis = Obj.elementVisibleSync(Const.kip4CostCentre + "dropDown_AddItem")

		if(eleVis){
			Obj.buttonClickSync(Const.kip4CostCentre + "dropDown_AddItem")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		return costCentre.code
	}


	static def createDepartment(Department department){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_Departments")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", department.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", department.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		if(department.parent != "") {
			Obj.selectComboboxValue("parent", "parent", department.parent, department.parent)
		}

		if(department.manager != "") {
			Obj.selectComboboxValue("lineManager", "lineManager", department.manager, department.manager)
		}

		def dem = WebD.getCheckBoxState("//input[@id='canDemand']")

		if(dem == null){
			Obj.checkSync(Const.kip4Generic + "chk_AvailableDF")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createDepartmentWithFinancials(Department department, String rate){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_Departments")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", department.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", department.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.selectComboboxValue("parent", "parent", department.parent, department.parent)
		Obj.selectComboboxValue("lineManager", "lineManager", department.manager, department.manager)

		def dem = WebD.getCheckBoxState("//input[@id='canDemand']")
		if(dem == null){
			Obj.checkSync(Const.kip4Generic + "chk_AvailableDF")
		}

		Obj.buttonClickSync(Const.kip4Department+ "tab_Financial")
		Obj.buttonClickSync(Const.kip4Department+ "button_AddPlanningRate")
		Obj.setEditSubSync(Const.kip4Department + "input_localRate", "1", "pos", rate)

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def company(Company company){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_Projects")
		Obj.buttonClickSync(Const.projects + "a_Add")

		Obj.buttonClickSync(Const.projectAdd + "radio_AddEmptyProject")
		Obj.buttonClickSync(Const.projectAdd + "select_ProjectEntityType")
		WebD.clickElement("//div[@id='select2-drop']//div[@class='select2-search']")

		Obj.selectComboByValueSync(Const.projectAdd + "select_ProjectEntityType", company.projectLevel)
		Obj.buttonClickSync(Const.projectsMenu + "button_Finish")

		Pop.newCompanyForm(company)

		Obj.buttonClickSync(Const.projectsMenu + "button_SaveAndClose")

		company.code = Act.projectSearch("Project Name", company.name)

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		return company.code
	}


	static def programme(Programme programme){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_Projects")
		Obj.buttonClickSync(Const.projects + "a_Add")

		Obj.buttonClickSync(Const.projectsMenu + "radio_AddEmptyProject")
		Obj.buttonClickSync(Const.projectsMenu + "button_Finish")

		Pop.newProgrammeForm(programme)

		Obj.buttonClickSync(Const.projectsMenu + "button_SaveAndClose")
		gAct.Wait(GVars.shortWait)
		programme.code = Act.projectSearch("Project Name", programme.name)

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		return programme.code
	}


	static def parent(Parent parent){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_Projects")
		Obj.buttonClickSync(Const.projects + "a_Add")

		Obj.buttonClickSync(Const.projectsMenu + "radio_AddEmptyProject")
		Obj.buttonClickSync(Const.projectsMenu + "button_Finish")

		Pop.newParentForm(parent)

		Obj.buttonClickSync(Const.projectsMenu + "button_SaveAndClose")
		gAct.Wait(GVars.shortWait)
		parent.code = Act.projectSearch("Project Name", parent.name)

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		return parent.code
	}


	@Keyword
	static def customTaskField(String taskName){
		Obj.buttonClickSync(Const.mainToolBar + 'settings')
		Obj.buttonClickSync(Const.settingsMenu + 'CustomFields')

		Obj.selectComboByLabelSync(Const.CustomFieldScreen + 'select_CustomFieldType', "Task")
		Obj.buttonClickSync(Const.CustomFieldScreen + 'a_Add')

		Obj.checkSync(Const.CustomFieldScreen + 'chk_AllSections')
		Obj.setEditSync(Const.CustomFieldScreen + 'input__Name', taskName)
		Obj.checkSync(Const.CustomFieldScreen + 'chk_Required')
		Obj.buttonClickSync(Const.CustomFieldScreen + 'button_SaveAndClose')
		gAct.Wait(GVars.shortWait)
		def rowCnt = WebD.getTableRowCount("//div[@id='63f30b25-1931-4053-98a5-eec594abfc22']/div[4]/table")

		if(rowCnt == 2){
			String taskVal = Obj.getEditTextSync(Const.CustomFieldScreen + 'customTaskName')
			gAct.compareStringAndReport(taskVal, taskName)
		}else{
			String taskNme = Obj.getEditTextSync(Const.CustomFieldScreen + 'customTaskName')
			if(taskNme == taskName){
				Rep.pass(taskNme + " matches as expected.")
			}else{
				for(int intCnt = 2; intCnt < rowCnt; intCnt ++){

					String extraTaskNme = Obj.getEditTextSubIntPosSync(Const.CustomFieldScreen + 'customTaskNameExtraRow', intCnt, "row")
					if(extraTaskNme == taskName){
						Rep.pass(taskNme + " matches as expected.")
						break
					}
				}
			}
		}
	}


	@Keyword
	static def addNewViewAndWidget_MyProjects(String widgetViewName, int widget, String widgetFilter){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "My Projects")
		Obj.buttonClickSync(Const.myProjects + 'spannerSettings')
		Obj.buttonClickSync(Const.configureViewMenu + 'a_Add New View')
		Obj.setEditSync(Const.createViewDialog + 'createViewName', widgetViewName)
		Obj.buttonClickSync(Const.createViewDialog + 'createViewSave')

		Obj.buttonClickSync(Const.myProjects + 'spannerSettings')
		Obj.buttonClickSync(Const.configureViewMenu + 'a_Configure Current View')
		Obj.buttonClickSync(Const.configureViewMenu + 'plusSymbol')

		Obj.buttonClickSync(Const.addWidget + 'a_Action Views')

		Obj.buttonClickSubSync(Const.addWidget + 'actionViewsButton', widget, "button")
		String widgetName_new = Obj.getEditTextSync(Const.widgetView + 'widgetViewName')

		gAct.compareStringAndReport(widgetName_new, widgetViewName)

		boolean widgetPresent = Obj.elementPresentSubSync(Const.widgetView + 'WidgetName', widgetFilter, 'widget')

		if(widgetPresent){
			Rep.pass("Widget has been created and added to the correct View")
		}else{
			Rep.fail("Widget has been not been created or added to the correct View")
			Rep.screenshot("Widget has been not been created or added to the correct View")
		}

		Obj.buttonClickSync(Const.widgetView + 'widgetSave')
		Obj.buttonClickSubSync(Const.widgetView + 'widgetConfig', widgetFilter, "widget")

		Obj.buttonClickSync(Const.widgetConfig + 'DefaultFilterList')
		Obj.setEditSync(Const.widgetConfig + 'DefaultFilterSearch', "All Actions")

		Obj.buttonClickSubSync(Const.widgetConfig + 'DefaultFilterChoice', "All Actions", "choice")
		Obj.buttonClickSync(Const.widgetConfig + 'button_Save')

		String saveMessage =  Obj.getEditTextSync(Const.widgetConfig + 'widgetConfigSaveMessage')

		if((GVars.browser == "MicrosoftEdge")||(GVars.browser == "Internet explorer")){
			gAct.findSubstringInStringAndReport(saveMessage, "Configuration saved successfully")
		}else{
			gAct.compareStringAndReport(saveMessage, "×\nConfiguration saved successfully")
		}

		Obj.buttonClickSync(Const.widgetConfig + 'button_Close')
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def submitTimesheet(Timesheet timesheet){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		Obj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
		gAct.Wait(GVars.shortWait)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()
		Obj.buttonClickSync(Const.timesheet + 'button_Submit')
		gAct.Wait(GVars.midWait)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		gAct.Wait(GVars.shortWait)
		String statusText = Obj.getEditTextSync(Const.timesheet + 'txt_SubmitStatus')
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def submitTimesheetWithCharge_SpecificUser(Timesheet timesheet, String Charge, Login login){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")

		Obj.buttonClickSync(Const.timesheet + 'timesheetEntry_DropDown')
		Obj.setEditSync(Const.timesheet + 'input_TimesheetEntryUser', login.name)
		Obj.buttonClickSubSync(Const.timesheet + 'Select_TimesheetEntryUser', login.name, "usr")

		Obj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
		gAct.Wait(GVars.midWait)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)

		Obj.buttonClickSync(Const.timesheet + 'dropdown_Charge')

		Obj.setEditSync(Const.timesheet + 'input_Charge', Charge)
		Obj.buttonClickSubSync(Const.timesheet + 'select_Charge', Charge, "charge")

		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()
		Obj.buttonClickSync(Const.timesheet + 'button_Submit')
		gAct.Wait(GVars.midWait)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		String statusText = Obj.getEditTextSync(Const.timesheet + 'txt_SubmitStatus')
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def submitTimesheet_SpecificUser(Timesheet timesheet, Resource resource){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")

		Obj.buttonClickSync(Const.timesheet + 'timesheetEntry_DropDown')
		Obj.setEditSync(Const.timesheet + 'input_TimesheetEntryUser', resource.name)

		Obj.buttonClickSubSync(Const.timesheet + 'Select_TimesheetEntryUser', resource.name, "usr")

		Obj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
		gAct.Wait(GVars.midWait)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()

		Obj.buttonClickSync(Const.timesheet + 'button_Submit')
		gAct.Wait(GVars.shortWait)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		String statusText = Obj.getEditTextSync(Const.timesheet + 'txt_SubmitStatus')

		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def submitTimesheet_SpecificUserNoCharge(Timesheet timesheet, Resource resource){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		Obj.buttonClickSync(Const.timesheet + 'timesheetEntry_DropDown')

		Obj.setEditSync(Const.timesheet + 'input_TimesheetEntryUser', resource.name)
		Obj.buttonClickSubSync(Const.timesheet + 'Select_TimesheetEntryUser', resource.name, "usr")

		Obj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
		gAct.Wait(GVars.midWait)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)

		Obj.buttonClickSync(Const.timesheet + 'chk_ChargeableRow1')

		tAct.timesheetSave()
		Obj.buttonClickSync(Const.timesheet + 'button_Submit')
		gAct.Wait(GVars.midWait)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		gAct.Wait(GVars.shortWait)
		String statusText = Obj.getEditTextSync(Const.timesheet + 'txt_SubmitStatus')
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def saveTimesheet_SpecificUser(Timesheet timesheet, Resource resource){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")

		Obj.buttonClickSync(Const.timesheet + 'timesheetEntry_DropDown')
		Obj.setEditSync(Const.timesheet + 'input_TimesheetEntryUser', resource.name)

		Obj.buttonClickSubSync(Const.timesheet + 'Select_TimesheetEntryUser', resource.name, "usr")
		Obj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
		gAct.Wait(GVars.midWait)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def submitPreviousTimesheet(Timesheet timesheet, int backCnt){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")

		for(int intCnt = 0; intCnt < backCnt; intCnt ++){
			Obj.buttonClickSync(Const.timesheet + 'timesheetNavigateBack')
		}

		Obj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
		gAct.Wait(GVars.midWait)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()

		Obj.buttonClickSync(Const.timesheet + 'button_Submit')
		gAct.Wait(GVars.midWait)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		gAct.Wait(GVars.shortWait)
		String statusText = Obj.getEditTextSync(Const.timesheet + 'txt_SubmitStatus')
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def submitPreviousTimesheet_SpecificUser(Timesheet timesheet, int backCnt, Resource resource){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")

		Obj.buttonClickSync(Const.timesheet + 'timesheetEntry_DropDown')

		Obj.setEditSync(Const.timesheet + 'input_TimesheetEntryUser', resource.name)

		Obj.buttonClickSubSync(Const.timesheet + 'Select_TimesheetEntryUser', resource.name, "usr")

		for(int intCnt = 0; intCnt < backCnt; intCnt ++){
			Obj.buttonClickSync(Const.timesheet + 'timesheetNavigateBack')
		}

		Obj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
		gAct.Wait(5)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()

		Obj.buttonClickSync(Const.timesheet + 'button_Submit')
		gAct.Wait(GVars.midWait)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		gAct.Wait(2)
		String statusText = Obj.getEditTextSync(Const.timesheet + 'txt_SubmitStatus')
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def saveTimesheet(Timesheet timesheet){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")

		Obj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
		gAct.Wait(GVars.midWait)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def submitOldTimesheetAndApprove(Timesheet timesheet, int weekRemoved, String weekCommence){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")

		for(int intCnt = 0; intCnt < weekRemoved; intCnt ++){
			Obj.buttonClickSync(Const.timesheet + 'timesheetNavigateBack')
		}

		Obj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
		gAct.Wait(GVars.midWait)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()

		Obj.buttonClickSync(Const.timesheet + 'button_Submit')
		gAct.Wait(GVars.midWait)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")

		String statusText = Obj.getEditTextSync(Const.timesheet + 'txt_SubmitStatus')
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")

		Obj.buttonClickSync(Const.search + "search_DropDown")

		Obj.buttonClickSubSync(Const.search + "filterType", "Timesheet Submissions", "label")

		Obj.buttonClickSync(Const.columnPicker + "a_Pending Approval")
		int rowNo = sVal.searchTableReturnRow(4, weekCommence)

		Obj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
		Obj.buttonClickSync(Const.columnPicker + "a_Open_ActionView")
		Obj.buttonClickSync(Const.columnPicker + "btn_SubmittedForApproval")
		Obj.buttonClickSync(Const.columnPicker + "button_AcceptSubmission")
		Obj.buttonClickSync(Const.columnPicker + "button_CloseSubmission")
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}



	@Keyword
	static def submitOldTimesheetAndApprove_SpecificUser(Timesheet timesheet, int weekRemoved, String weekCommence, Resource resource){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")

		Obj.buttonClickSync(Const.timesheet + 'timesheetEntry_DropDown')
		Obj.setEditSync(Const.timesheet + 'input_TimesheetEntryUser', resource.name)
		Obj.buttonClickSubSync(Const.timesheet + 'Select_TimesheetEntryUser', resource.name, "usr")
		gAct.Wait(1)

		for(int intCnt = 0; intCnt < weekRemoved; intCnt ++){
			Obj.buttonClickSync(Const.timesheet + 'timesheetNavigateBack')
		}

		Obj.buttonClickSync(Const.timesheet + 'TimesheetAddLine')
		gAct.Wait(GVars.midWait)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()
		Obj.buttonClickSync(Const.timesheet + 'button_Submit')
		gAct.Wait(GVars.midWait)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		String statusText = Obj.getEditTextSync(Const.timesheet + 'txt_SubmitStatus')
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")

		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")

		Obj.buttonClickSync(Const.search + "search_DropDown")
		Obj.buttonClickSubSync(Const.search + "filterType", "Timesheet Submissions", "label")
		Obj.buttonClickSync(Const.columnPicker + "a_Pending Approval")
		int rowNo = sVal.searchTableReturnRow(5, resource.name)

		Obj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

		Obj.buttonClickSync(Const.columnPicker + "a_Open_ActionView")
		Obj.buttonClickSync(Const.columnPicker + "btn_SubmittedForApproval")
		Obj.buttonClickSync(Const.columnPicker + "button_AcceptSubmission")
		gAct.Wait(1)
		Obj.buttonClickSync(Const.columnPicker + "button_CloseSubmission")
		gAct.Wait(1)
		WebUI.verifyElementNotPresent(findTestObject(Const.columnPicker + "button_CloseSubmission"), GVars.longWait, FailureHandling.OPTIONAL)
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createExpense(Expense expense){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Expenses", Const.dashBoard + 'currentPageHeader', "Expenses")
		Obj.buttonClickSync(Const.claim + 'a_Add')
		Obj.buttonClickSync(Const.expense + 'input_Date')
		gVal.objectAttributeValue(Const.expense + 'input_Date', 'value', expense.date)

		//Obj.selectComboByValueSync(Const.expense + 'select_Project', expense.project)
		
		String projectText = Obj.getAttributeSync(Const.expense + 'input_Project', 'value')
		if(projectText == ""){
			Obj.setEditSync(Const.expense + 'input_Project', expense.project)
			gAct.Wait(GVars.shortWait)
			//Obj.buttonClickSync("//a[@id='ui-active-menuitem']")
	}
	
		Obj.selectComboByValueSync(Const.expense + 'select_Expense', expense.expenseType)

		if(expense.category != "DEFAULT"){
			Obj.selectComboByValueSync(Const.expense + 'select_Category', expense.category)
		}

		Obj.checkSync(Const.expense + 'chk_Reimbursable')
		Obj.setEditSync(Const.expense + 'input_Gross', expense.gross)
		Obj.buttonClickSync(Const.expense + 'input_Vat')

		gVal.objectAttributeValue(Const.expense + 'input_Net', 'value', expense.net)

		Obj.checkSync(Const.expense + 'chk_Chargeable')

		Obj.buttonClickSync(Const.expense + 'button_SaveAndClose')

		gAct.Wait(GVars.shortWait)

		WebUI.waitForElementNotVisible(findTestObject(Const.expense + 'a_ExpenseSavedSuccessfully'), 10, FailureHandling.OPTIONAL)

		Obj.buttonClickSync(Const.expense + 'filter_DropDown')
		Obj.buttonClickSync(Const.expense + 'a_UnclaimedExpenses')

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createExpenseWithCurrency(ExpenseWithCurrency expense){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Expenses", Const.dashBoard + 'currentPageHeader', "Expenses")

		Obj.buttonClickSync(Const.claim + 'a_Add')
		Obj.buttonClickSync(Const.expense + 'input_Date')
		gVal.objectAttributeValue(Const.expense + 'input_Date', 'value', expense.date)
		String projectText = Obj.getAttributeSync(Const.expense + 'input_Project', 'value')
		if(projectText == ""){
			Obj.setEditSync(Const.expense + 'input_Project', expense.project)
			gAct.Wait(GVars.shortWait)
			//Obj.buttonClickSync("//a[@id='ui-active-menuitem']")
	}

		WebUI.selectOptionByValue(findTestObject(Const.expense + 'select_Expense'), expense.expenseType, false)
		gAct.Wait(1)

		if(expense.category != "DEFAULT"){
			Obj.selectComboByValueSync(Const.expense + 'select_Category', expense.category)
		}

		if(expense.currency != "DEFAULT"){
			Obj.selectComboByValueSync(Const.expense + 'select_Currency', expense.currency)
		}

		Obj.checkSync(Const.expense + 'chk_Reimbursable')
		Obj.setEditSync(Const.expense + 'input_Gross', expense.gross)
		Obj.buttonClickSync(Const.expense + 'input_Vat')

		gVal.objectAttributeValue(Const.expense + 'input_Net', 'value', expense.net)

		if(expense.chargeable == "true") {
			Obj.checkSync(Const.expense + 'chk_Chargeable')
		}

		Obj.buttonClickSync(Const.expense + 'button_SaveAndClose')
		gAct.Wait(GVars.shortWait)
		WebUI.waitForElementNotVisible(findTestObject(Const.expense + 'a_ExpenseSavedSuccessfully'), 10, FailureHandling.OPTIONAL)

		Obj.buttonClickSync(Const.expense + 'filter_DropDown')
		Obj.buttonClickSync(Const.expense + 'a_UnclaimedExpenses')

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}



	@Keyword
	static def createMultiExpense(Expense expense, int count){
		int newGross, newNet
		int Gross = 10
		int Net = 10

		Nav.menuItemAndValidate(Const.dashBoard + "menu_Expenses", Const.dashBoard + 'currentPageHeader', "Expenses")
		gAct.Wait(1)

		for(int intCnt = 0; intCnt < count; intCnt ++){

			Obj.buttonClickSync(Const.claim + 'a_Add')
			Obj.buttonClickSync(Const.expense + 'input_Date')
			gVal.objectAttributeValue(Const.expense + 'input_Date', 'value', expense.date)
			
			Rep.nextTestStep("select a project from [Project] field ")
			String projectText = Obj.getAttributeSync(Const.expense + 'input_Project', 'value')
			if(projectText == ""){
				Obj.setEditSync(Const.expense + 'input_Project', expense.project)
				
			}	
				gAct.Wait(GVars.shortWait)
			
			Obj.selectComboByValueSync(Const.expense + 'select_Expense', expense.expenseType)

			if(expense.category != "DEFAULT"){
				Obj.selectComboByValueSync(Const.expense + 'select_Category', expense.category)
			}

			Obj.checkSync(Const.expense + 'chk_Reimbursable')

			newGross = Gross + intCnt
			String strGross = newGross.toString() + ".00"

			Obj.setEditSync(Const.expense + 'input_Gross', strGross)
			Obj.buttonClickSync(Const.expense + 'input_Vat')

			newNet = Net + intCnt
			String strNet = newNet.toString() + ".00"

			gVal.objectAttributeValue(Const.expense + 'input_Net', 'value', strNet)

			Obj.checkSync(Const.expense + 'chk_Chargeable')
			Obj.buttonClickSync(Const.expense + 'button_SaveAndClose')
			gAct.Wait(2)

			WebUI.waitForElementNotVisible(findTestObject(Const.expense + 'a_ExpenseSavedSuccessfully'), GVars.longWait, FailureHandling.OPTIONAL)

			Obj.refreshUI()
		}

		Obj.buttonClickSync(Const.expense + 'filter_DropDown')
		Obj.buttonClickSync(Const.expense + 'a_UnclaimedExpenses')

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createExpenseType(ExpenseType expenseType){
		String searchItem = "Expense Types"
		String searchVal = "Expense Type"

		Act.selectEntityViaSearch(searchItem)

		String filterName = Obj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')
		gAct.findSubstringInStringAndReport(filterName, searchVal)

		Obj.selectComboByLabelSync(Const.columnPicker + "pageCount", "10")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")
		Obj.setEditSync(Const.kip4Generic + "input_Name", expenseType.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", expenseType.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4ExpenseType + "select_SystemTaxRateOverride")
		Obj.setEditSync(Const.kip4ExpenseType + "input__multipleExpense", expenseType.multiplyExpChg)

		if(expenseType.Receipt == "true"){
			Obj.checkSync(Const.kip4ExpenseType + "chk_AllowReceipt")
		}

		if(expenseType.currency != "") {
			Obj.buttonClickSync(Const.kip4ExpenseType + "chk_FixedCurrency")
			Obj.selectComboboxValue("currency", "currency", expenseType.currency, expenseType.currency)
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createMitigationPlanItemType(MitigationPlanItemType mitigationPlanItemType){
		String searchItem = "Mitigation Plan Item Types"
		String searchVal = "Mitigation Plan Item Type"

		Act.selectEntityViaSearch(searchItem)

		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		Obj.setEditSync(Const.kip4Generic + "input_Name", mitigationPlanItemType.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", mitigationPlanItemType.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createMitigationPlanItemTypeDefault(MitigationPlanItemType mitigationPlanItemType){
		String searchItem = "Mitigation Plan Item Types"
		String searchVal = "Mitigation Plan Item Type"

		Act.selectEntityViaSearch(searchItem)

		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")

		Obj.setEditSync(Const.kip4Generic + "input_Name", mitigationPlanItemType.name)

		Obj.setEditSync(Const.kip4Generic + "input_Code", mitigationPlanItemType.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.checkSync(Const.kip4MitigationPlanItemType + "chk_DefaultMitigationPlanItemType")

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
		gAct.Wait(1)

		Obj.buttonClickSync(Const.kip4MitigationPlanItemType + "button_OK")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createExpenseTypeWithNominalCode(ExpenseType expenseType, String nominal){
		String searchItem = "Expense Types"
		String searchVal = "Expense Type"

		Act.selectEntityViaSearch(searchItem)

		String filterName = Obj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')
		gAct.findSubstringInStringAndReport(filterName, searchVal)

		Obj.selectComboByLabelSync(Const.columnPicker + "pageCount", "10")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", expenseType.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", expenseType.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4ExpenseType + "select_SystemTaxRateOverride")
		Obj.setEditSync(Const.kip4ExpenseType + "input__multipleExpense", expenseType.multiplyExpChg)
		Obj.checkSync(Const.kip4ExpenseType + "chk_AllowReceipt")

		if(expenseType.currency != "") {
			Obj.buttonClickSync(Const.kip4ExpenseType + "chk_FixedCurrency")
			Obj.buttonClickSync(Const.kip4ExpenseType + "select_NUCurrencyControl")
			Obj.setEditSync(Const.kip4ExpenseType + "input_NUCurrencyExp", expenseType.currency)

			Obj.buttonClickSubSync(Const.kip4ExpenseType + "select_Currency", " " + expenseType.currency + " ", "curr")
		}

		Obj.buttonClickSync(Const.kip4ExpenseType + "tab_Defaults")

		Obj.buttonClickSync(Const.kip4ExpenseType + "dropDown_Nominal Code")
		Obj.setEditSync(Const.kip4ExpenseType + "inputNominalCode", nominal)

		Obj.buttonClickSubSync(Const.kip4ExpenseType + "selectNominalCode", " " + nominal + " ", "nomCode")

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
		gAct.Wait(1)
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createActionType(ActionType actionType){
		String searchItem = "Action Types"
		String searchVal = "Action Type"

		Act.selectEntityViaSearch(searchItem)

		String filterName = Obj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')
		gAct.findSubstringInStringAndReport(filterName, searchVal)

		Obj.selectComboByLabelSync(Const.columnPicker + "pageCount", "10")

		Obj.buttonClickSync(Const.columnPicker + "a_Add")

		Obj.setEditSync(Const.columnPicker + "input_Name", actionType.name)
		Obj.setEditSync(Const.columnPicker + "input_Code", actionType.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			Obj.checkSync(Const.columnPicker + "chk_Active")
		}

		Obj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
		gAct.Wait(1)
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createCategory(Category category){
		Act.selectEntityViaSearch("Categories")

		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		Obj.setEditSync(Const.kip4Generic + "input_Name", category.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", category.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active != category.active){
			WebD.clickElement("//span[contains(text(),'Active')]")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createCategoryDefault(Category category){
		Act.selectEntityViaSearch("Categories")

		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		Obj.setEditSync(Const.kip4Generic + "input_Name", category.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", category.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active != category.active){
			WebD.clickElement("//span[contains(text(),'Active')]")
		}

		Obj.checkSync(Const.kip4Category + "chk_DefaultCategory")

		Obj.buttonClickSync(Const.kip4Generic + "button_SaveClose")
		Obj.buttonClickSync(Const.kip4Category + "button_OK")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createContract(Contract contract){
		String searchItem = "Contract"
		String searchVal = "Contract"

		Act.selectEntityViaSearch("Contract")

		String filterName = Obj.getAttributeSync(Const.searchFilters + "input_SearchFilterName", 'value')
		gAct.findSubstringInStringAndReport(filterName, searchVal)

		Obj.buttonClickSync(Const.columnPicker + "a_Add")

		Obj.selectComboByLabelSync(Const.addContract + "select_Status", contract.status)

		if(contract.status != "Draft"){
			WebUI.acceptAlert()
			gAct.Wait(1)
			WebUI.switchToDefaultContent()
		}

		Obj.setEditSync(Const.addContract + "input_Project", contract.project)
		Obj.setEditSync(Const.addContract + "input_Name", contract.name)

		if(contract.status == "Draft"){
			Obj.selectComboByLabelSync(Const.addContract + "select_TimeUnit", contract.billTimeIn)
		}
		Obj.setEditSync(Const.addContract + "input_DayLength", contract.dayLength)
		Obj.buttonClickSync(Const.addContract + "button_Save and Close")
		gAct.Wait(1)
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createForecast(Forecast forecast){
		Act.selectEntityViaSearch("Forecasts")

		Obj.buttonClickSync(Const.columnPicker + "a_AllForecasts")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Obj.buttonClickSync(Const.columnPicker + "a_Add")
		Obj.setEditSync(Const.forecasts + 'projectName', forecast.project)

		Obj.setEditSync(Const.forecasts + 'forecastName', forecast.name)

		Obj.buttonClickSync(Const.forecasts + 'BenefitsTab')
		Obj.buttonClickSync(Const.columnPicker + 'btn_AddBenefitLine')

		Obj.setEditSync(Const.columnPicker + 'input_BenefitName', forecast.benefitName)
		Obj.setEditSync(Const.columnPicker + "input_BenefitType", forecast.benefitType)
		Obj.buttonClickSync(Const.forecasts + 'BenefitsTab')
		Obj.buttonClickSync(Const.columnPicker + 'btn_AddBenefit')

		Obj.setEditSync(Const.columnPicker + 'input_BenefitDate', forecast.date)

		for(int intCnt = 1; intCnt < 7; intCnt ++){
			String dateVal = Obj.getEditTextSubIntPosSync(Const.columnPicker + 'to_BenefitHeaderDates', intCnt, "col")
			String[] newDateVal = dateVal.split('/')
			String revDate = newDateVal[2] + newDateVal[1] + newDateVal[0]

			Obj.buttonClickSubIntPosSync(Const.columnPicker + 'input_BenefitCellOuter', intCnt + 7, "cell")

			Obj.setEditSubSync(Const.columnPicker + 'input_BenefitsCell', revDate, "cell", "5")
		}
		Obj.buttonClickSync(Const.forecasts + 'button_Save and Close')
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createForecastAndApprove(Forecast forecast){
		Act.selectEntityViaSearch("Forecasts")

		Obj.buttonClickSync(Const.columnPicker + "a_AllForecasts")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Obj.buttonClickSync(Const.columnPicker + "a_Add")

		Obj.setEditSync(Const.forecasts + 'projectName', forecast.project)
		Obj.setEditSync(Const.forecasts + 'forecastName', forecast.name)

		Obj.buttonClickSync(Const.forecasts + 'BenefitsTab')
		Obj.buttonClickSync(Const.columnPicker + 'btn_AddBenefitLine')

		Obj.setEditSync(Const.columnPicker + 'input_BenefitName', forecast.benefitName)
		Obj.setEditSync(Const.columnPicker + "input_BenefitType", forecast.benefitType)
		Obj.buttonClickSync(Const.forecasts + 'BenefitsTab')
		Obj.buttonClickSync(Const.columnPicker + 'btn_AddBenefit')

		Obj.setEditSync(Const.columnPicker + 'input_BenefitDate', forecast.date)

		for(int intCnt = 1; intCnt < 7; intCnt ++){
			String dateVal = Obj.getEditTextSubIntPosSync(Const.columnPicker + 'to_BenefitHeaderDates', intCnt, "col")
			String[] newDateVal = dateVal.split('/')
			String revDate = newDateVal[2] + newDateVal[1] + newDateVal[0]

			Obj.buttonClickSubIntPosSync(Const.columnPicker + 'input_BenefitCellOuter', intCnt + 7, "cell")
			Obj.setEditSubSync(Const.columnPicker + 'input_BenefitsCell', revDate, "cell", "5")
		}

		Obj.buttonClickSync(Const.forecasts + 'button_Save and Close')
		Obj.buttonClickSync(Const.columnPicker + "a_New Filter")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")

		int rowNo = sVal.searchTableReturnRow(2, forecast.name)
		Obj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

		Obj.buttonClickSubIntPosSync(Const.columnPicker + "a_InlineMenuPositionSelection", 2, "pos")

		Obj.buttonClickSync(Const.columnPicker + "btn_ApproveForecast")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createRisk(Risk risk){
		String medium = "background-color: orange; color: black;"
		String high = "background-color: red; color: black;"

		Act.selectEntityViaSearch("Risks")
		Obj.buttonClickSync(Const.columnPicker + "a_Add")
		Obj.setEditSync(Const.risks + 'input_Title', risk.title)

		String projectText = Obj.getAttributeSync(Const.risks + 'input_Project', 'value')
		if(projectText == ""){
			Obj.setEditSync(Const.risks + 'input_Project', risk.project)
			Obj.buttonClickSync(Const.risks + 'input_Title')
		}

		String ownerText = Obj.getAttributeSync(Const.risks + 'input_Owner', 'value')
		if(ownerText == ""){
			Obj.setEditSync(Const.risks + 'input_Owner', risk.owner)
			Obj.buttonClickSync(Const.risks + 'input_Title')
		}
		gAct.Wait(1)

		WebUI.verifyOptionSelectedByLabel(findTestObject(Const.risks + 'select_Status'), risk.status, false, 5)

		String dateRaised = Obj.getAttributeSync(Const.risks + 'input_DateIdentified', 'value')
		gAct.compareStringAndReport(dateRaised, risk.dateIdentified)

		Obj.setEditSync(Const.risks + 'input_ImpactDate', risk.impactDate)

		Obj.selectComboByLabelSync(Const.risks + 'select_PublishTo', risk.publishTo)
		Obj.buttonClickSync(Const.risks + 'input_Title')

		Obj.selectComboByLabelSync(Const.risks + 'select_Probability', risk.probability)
		Obj.buttonClickSync(Const.risks + 'input_Title')
		String colourNewTask = Obj.getAttributeSync(Const.risks + 'select_Probability', 'style')
		gAct.compareStringAndReport(colourNewTask, high)

		Obj.selectComboByLabelSync(Const.risks + 'select_Impact', risk.impact)
		Obj.buttonClickSync(Const.risks + 'input_Title')
		colourNewTask = Obj.getAttributeSync(Const.risks + 'select_Impact', 'style')
		gAct.compareStringAndReport(colourNewTask, medium)

		Obj.selectComboByLabelSync(Const.risks + 'select_Severity', risk.severity)
		Obj.buttonClickSync(Const.risks + 'input_Title')
		colourNewTask = Obj.getAttributeSync(Const.risks + 'select_Severity', 'style')
		gAct.compareStringAndReport(colourNewTask, high)

		Obj.setEditSync(Const.risks + 'input_ResolutionDate', risk.resolutionDate)

		String riskValue = Obj.getEditTextSync(Const.risks + 'riskValue')
		gAct.findSubstringInStringAndReport(riskValue, "RED")

		Obj.buttonClickSync(Const.risks + 'button_Save')
		gAct.Wait(1)

		risk.code = Obj.getAttributeSync(Const.risks + 'input_Code', 'value')

		Obj.buttonClickSync(Const.risks + 'button_SaveAndClose')
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createIssue(Issue issue){
		Act.selectEntityViaSearch("Issues")
		Obj.buttonClickSync(Const.columnPicker + "a_Add")

		Obj.setEditSync(Const.issues + 'input_Title', issue.title)
		Obj.setEditSync(Const.issues + 'input_Project', issue.project)
		Obj.buttonClickSync(Const.columnPicker + "td_General")

		String ownerText = Obj.getAttributeSync(Const.issues + 'input_Owner', 'value')
		if(ownerText == ""){
			Obj.setEditSync(Const.issues + 'input_Owner', issue.owner)
			Obj.buttonClickSync(Const.issues + 'input_Title')
		}

		WebUI.verifyOptionSelectedByLabel(findTestObject(Const.issues + 'select_Status'), issue.status, false, 5)

		Obj.selectComboByLabelSync(Const.issues + 'select_Impact', issue.impact)
		Obj.buttonClickSync(Const.columnPicker + "td_General")

		Obj.selectComboByLabelSync(Const.issues + 'select_ResolutionProgress', issue.resolutionProgress)

		Obj.buttonClickSync(Const.columnPicker + "td_General")
		Obj.buttonClickSync(Const.issues + 'button_Save')

		issue.code = Obj.getAttributeSync(Const.issues + 'input_Code', 'value')

		Obj.buttonClickSync(Const.issues + "button_SaveAndClose")
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createActionView(ActionView actionView){
		Act.selectEntityViaSearch("Action Views")

		Obj.buttonClickSync(Const.columnPicker + "a_Add")
		Obj.setEditSync(Const.columnPicker + "input_ActionName", actionView.name)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			Obj.checkSync(Const.columnPicker + "chk_Active")
		}

		Obj.setEditSync(Const.columnPicker + "input_Description", actionView.description)

		Obj.setEditSync(Const.columnPicker + "input_DefaultActionType", actionView.defaultActionType)
		Obj.buttonClickSync(Const.columnPicker + "input_ActionName")
		Obj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createActivity(Activity activity){
		Act.selectEntityViaSearch("Activities")

		Obj.buttonClickSync(Const.kip4Generic + "a_Add")
		Obj.setEditSync(Const.kip4Generic + "input_Name", activity.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", activity.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createBillingPriceList(BillingPriceList billingPriceList){
		Nav.selectSearchFilter("Billing Price List")
		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		Obj.setEditSync(Const.kip4Generic + "input_Name", billingPriceList.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", billingPriceList.code)

		def activeRem = WebD.getCheckBoxState("//input[@id='active']")

		if(activeRem == null){
			WebD.clickElement("//span[contains(text(),'Active')]")
		}

		if(billingPriceList.currency != "DEFAULT"){
			Obj.selectComboboxValue("currency", "currency", billingPriceList.name, billingPriceList.name)
		}

		Obj.setEditSync(Const.kip4BillingPriceList + "input_UnitPrice", billingPriceList.unitPrice)
		Obj.setEditSync(Const.kip4BillingPriceList + "textarea__notes", billingPriceList.notes)

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createBenefitType(BenefitType benefitType){
		Nav.selectSearchFilter("Benefit Types")

		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		Obj.setEditSync(Const.kip4Generic + "input_Name", benefitType.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", benefitType.code)

		def activeRem = WebD.getCheckBoxState("//input[@id='active']")

		if(activeRem == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4BenefitType + "dropDown_Type")
		Obj.buttonClickSubSync(Const.kip4BenefitType + "select_Type", benefitType.type, "choice")

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createBenefitTypeInactive(BenefitType benefitType){
		Act.selectEntityViaSearch("Benefit Types")
		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		Obj.setEditSync(Const.kip4Generic + "input_Name", benefitType.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", benefitType.code)

		def activeRem = WebD.getCheckBoxState("//input[@id='active']")
		if(activeRem == "true"){
			WebD.clickElement("//span[contains(text(),'Active')]")
		}

		Obj.buttonClickSync(Const.kip4BenefitType + "dropDown_Type")
		Obj.buttonClickSubSync(Const.kip4BenefitType + "select_Type", benefitType.type, "choice")

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}



	@Keyword
	static def createClientGroup(ClientGroup clientGroup){
		Act.selectEntityViaSearch("Customer Groups")

		Obj.buttonClickSync(Const.kip4Generic + "a_Add")
		Obj.setEditSync(Const.kip4Generic + "input_Name", clientGroup.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", clientGroup.code)

		def activeRem = WebD.getCheckBoxState("//input[@id='active']")

		if(activeRem == null){
			Obj.buttonClickSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createClient(Client client){
		Act.selectEntityViaSearch("Customers")
		Obj.buttonClickSync(Const.columnPicker + "a_Default Client Filter")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", client.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", client.code)
		gAct.Wait(1)
		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.buttonClickSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createClientWithNotes(Client client, Notes notes){
		Nav.selectSearchFilter("Customers")
		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")

		Obj.setEditSync(Const.columnPicker + "input_Name", client.name)
		Obj.setEditSync(Const.columnPicker + "input_Code", client.code)
		gAct.Wait(1)
		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), GVars.midWait, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		Obj.buttonClickSync(Const.kip4Contacts + "tab_Notes")

		String headerVal = Obj.getEditTextSync(Const.kip4Contacts + "contacts_Header")
		gAct.findSubstringInStringAndReport(headerVal, "Title\nNotes\nName\nDate")
		Obj.buttonClickSync(Const.kip4Contacts + "btn_AddNote")

		Obj.setEditSync(Const.kip4Notes + "input_title", notes.title)
		Obj.setEditSync(Const.kip4Notes + "notes_Details", notes.details)

		Obj.buttonClickSync(Const.kip4Notes + "button_Add")
		Obj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createClientWithClientGroup(Client client, String clientGroup){
		Act.selectEntityViaSearch("Customers")
		Obj.buttonClickSync(Const.columnPicker + "a_Default Client Filter")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")
		Obj.setEditSync(Const.kip4Generic + "input_Name", client.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", client.code)
		gAct.Wait(1)
		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4Customer + "dropDown_CustomerGroup")
		Obj.setEditSync(Const.kip4Customer + "input_CustomerGroup", clientGroup)
		Obj.buttonClickSubSync(Const.kip4Customer + "select_CustomerGroup", clientGroup, "val")

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createClientKIP4(Customer client){
		Act.selectEntityViaSearch("Customers")
		Obj.buttonClickSync(Const.columnPicker + "a_Default Client Filter")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", client.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", client.code)
		gAct.Wait(1)
		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.selectComboboxValue("clientGroup", "clientGroup", client.customerGroup, client.customerGroup)

		Obj.scrollToElementSync(Const.kip4Customer + "input_addressLine1")
		Obj.setEditSync(Const.kip4Customer + "input_addressLine1", client.address.address1)
		Obj.setEditSync(Const.kip4Customer + "input_addressLine2", client.address.address2)
		Obj.setEditSync(Const.kip4Customer + "input_addressLine3", client.address.city)
		Obj.setEditSync(Const.kip4Customer + "input_addressLine4", client.address.state)
		Obj.setEditSync(Const.kip4Customer + "input_addressLine5", client.address.country)
		Obj.setEditSync(Const.kip4Customer + "input_addressLine6", client.address.postCode)

		Obj.scrollToElementSync(Const.kip4Customer + "input_phone")
		Obj.setEditSync(Const.kip4Customer + "input_phone", client.telephone)
		Obj.setEditSync(Const.kip4Customer + "input_fax", client.fax)
		Obj.setEditSync(Const.kip4Customer + "input_website", client.website)
		Obj.setEditSync(Const.kip4Customer + "input_email", client.email)

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createContact(NewContact contact){
		Act.selectEntityViaSearch("Contacts")

		Obj.buttonClickSync(Const.columnPicker + "a_New Filter")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")
		Obj.buttonClickSync(Const.kip4Contacts + "dropDown_Title")
		Obj.buttonClickSubSync(Const.kip4Contacts + "select_Title", contact.title, "title")

		Obj.setEditSync(Const.kip4Contacts + "input_firstName", contact.firstName)
		Obj.setEditSync(Const.kip4Contacts + "input_middleName", contact.middleName)
		Obj.setEditSync(Const.kip4Contacts + "input_lastName", contact.surname)
		gAct.Wait(GVars.shortWait)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.scrollToElementSync(Const.kip4Contacts + "input_lastName")

		Obj.selectComboboxValue("client", "client", contact.customer, contact.customer)

		Obj.setEditSync(Const.kip4Contacts + "input_Position", contact.position)
		Obj.setEditSync(Const.kip4Contacts + "input_department", contact.department)

		Obj.setEditSync(Const.kip4Contacts + "input_addressLine1", contact.address.address1)
		Obj.setEditSync(Const.kip4Contacts + "input_addressLine2", contact.address.address2)
		Obj.scrollToElementSync(Const.kip4Contacts + "input_addressLine3")
		Obj.setEditSync(Const.kip4Contacts + "input_addressLine3", contact.address.city)
		Obj.setEditSync(Const.kip4Contacts + "input_addressLine4", contact.address.state)
		Obj.setEditSync(Const.kip4Contacts + "input_addressLine5", contact.address.country)
		Obj.scrollToElementSync(Const.kip4Contacts + "input_addressLine6")
		Obj.setEditSync(Const.kip4Contacts + "input_addressLine6", contact.address.postCode)
		Obj.setEditSync(Const.kip4Contacts + "input_phone", contact.telephone)
		Obj.setEditSync(Const.kip4Contacts + "input_mobile", contact.mobile)
		Obj.setEditSync(Const.kip4Contacts + "input_fax", contact.fax)
		Obj.scrollToElementSync(Const.kip4Contacts + "input_Email")

		Obj.setEditSync(Const.kip4Contacts + "input_Email", contact.email)
		Obj.setEditSync(Const.kip4Contacts + "input_purpose",contact.purpose)

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createContactWithNotes(NewContact contact, Notes notes){
		Act.selectEntityViaSearch("Contacts")

		Obj.buttonClickSync(Const.columnPicker + "a_New Filter")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.buttonClickSync(Const.kip4Contacts + "dropDown_Title")
		Obj.buttonClickSubSync(Const.kip4Contacts + "select_Title", contact.title, "title")

		Obj.setEditSync(Const.kip4Contacts + "input_firstName", contact.firstName)
		Obj.setEditSync(Const.kip4Contacts + "input_middleName", contact.middleName)
		Obj.setEditSync(Const.kip4Contacts + "input_lastName", contact.surname)
		gAct.Wait(GVars.shortWait)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}
		Obj.scrollToElementSync(Const.kip4Contacts + "input_lastName")

		Obj.buttonClickSync(Const.kip4Contacts + "dropDown_Customer")
		Obj.buttonClickSubSync(Const.kip4Contacts + "select_Customer", contact.customer, "cust")

		Obj.setEditSync(Const.kip4Contacts + "input_Position", contact.position)
		Obj.setEditSync(Const.kip4Contacts + "input_department", contact.department)

		Obj.setEditSync(Const.kip4Contacts + "input_addressLine1", contact.address.address1)
		Obj.setEditSync(Const.kip4Contacts + "input_addressLine2", contact.address.address2)
		Obj.scrollToElementSync(Const.kip4Contacts + "input_addressLine3")

		Obj.setEditSync(Const.kip4Contacts + "input_addressLine3", contact.address.city)
		Obj.setEditSync(Const.kip4Contacts + "input_addressLine4", contact.address.state)
		Obj.setEditSync(Const.kip4Contacts + "input_addressLine5", contact.address.country)
		Obj.scrollToElementSync(Const.kip4Contacts + "input_addressLine6")
		Obj.setEditSync(Const.kip4Contacts + "input_addressLine6", contact.address.postCode)

		Obj.setEditSync(Const.kip4Contacts + "input_phone", contact.telephone)
		Obj.setEditSync(Const.kip4Contacts + "input_mobile", contact.mobile)
		Obj.setEditSync(Const.kip4Contacts + "input_fax", contact.fax)
		Obj.scrollToElementSync(Const.kip4Contacts + "input_purpose")

		Obj.setEditSync(Const.kip4Contacts + "input_purpose",contact.purpose)

		Obj.buttonClickSync(Const.kip4Contacts + "tab_Notes")
		Obj.buttonClickSync(Const.kip4Contacts + "btn_AddNote")
		Obj.setEditSync(Const.kip4Notes + "input_title", notes.title)

		if(GVars.browser == "Firefox") {
			WebD.clickElement("//div[@id='main-container']/div/formly-form/formly-field/formly-field-tabset/formly-tabs-tabset/div/formly-field[2]/formly-field-tab/formly-tabs-tab/formly-field/notes-table/div/app-add-note/div/formly-form/formly-field/formly-field-tabset/formly-tabs-tabset/div/formly-field/formly-field-tab/formly-tabs-tab/formly-field[4]/ng-component/div/div/formatted-text/div")
			WebD.setEditText("//div[@class='note-editable panel-body']", notes.details)
			WebD.dblClickElement('//p[contains(text(),"' + notes.details + '")]')
		}else {
			Obj.setEditSync(Const.kip4Notes + "notes_Details", notes.details)
			//			WebUI.sendKeys(findTestObject(Const.kip4Notes + "notes_Details"), Keys.chord(Keys.CONTROL + "a"))
			Obj.buttonDblClickSync(Const.kip4Notes + "notes_Details")
		}

		Obj.buttonClickSync(Const.kip4Notes + "btn_italic")

		if(notes.controlled) {
			Obj.buttonClickSync(Const.kip4Notes + 'chk_Controlled')
		}

		Obj.buttonClickSync(Const.kip4Notes + "button_Add")

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def addContactToClient(Contact contact, Client client){
		Act.selectEntityViaSearch("Customers")

		Obj.buttonClickSync(Const.columnPicker + "a_New Filter")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		gAct.Wait(1)
		int rowNo = sVal.searchTableReturnRow(3, client.name)
		Obj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

		Obj.selectInlineOption(6)

		Obj.buttonClickSync(Const.columnPicker + "Tab_Contacts")
		Obj.buttonClickSync(Const.columnPicker + "btnAddContact")
		Obj.setEditSync(Const.columnPicker + "input_ContactFirstname", contact.firstName)
		Obj.setEditSync(Const.columnPicker + "input_ContactSurname", contact.surname)

		boolean active = Obj.verifyCheckedSync(Const.columnPicker + "chk_ActiveContact")
		if(!active){
			Obj.checkSync(Const.columnPicker + "chk_ActiveContact")
		}

		String expCust = Obj.getEditTextSync(Const.columnPicker + "customerName")
		gAct.compareStringAndReport(expCust, client.name)

		Obj.buttonClickSync(Const.columnPicker + "button_SaveAndCloseAddContact")
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createDeliverableTypeUnclicked(DeliverableType deliverableType){
		Act.selectEntityViaSearch("Deliverable Types")

		Obj.buttonClickSync(Const.columnPicker + "a_DefaultDeliverableTypeFilter")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")

		Obj.setEditSync(Const.kip4Generic + "input_Name", deliverableType.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", deliverableType.code)

		def activeRem = WebD.getCheckBoxState("//input[@id='active']")

		if(activeRem == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createDeliverableType(DeliverableType deliverableType){
		Act.selectEntityViaSearch("Deliverable Types")
		Obj.buttonClickSync(Const.columnPicker + "a_DefaultDeliverableTypeFilter")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")

		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")

		Obj.setEditSync(Const.kip4Generic + "input_Name", deliverableType.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", deliverableType.code)

		def activeRem = WebD.getCheckBoxState("//input[@id='active']")
		if(activeRem == null){
			WebD.clickElement("//span[contains(text(),'Active')]")
		}

		def allowAtt = WebD.getCheckBoxState("//input[@id='allowAttachments']")
		if(allowAtt == null){
			Obj.checkSync(Const.kip4DeliverableType + "chk_AllowAttachments")
		}

		def audit = WebD.getCheckBoxState("//input[@id='auditCustomFields']")
		if(audit == null){
			Obj.checkSync(Const.kip4DeliverableType + "chk_AuditCustomFields")
		}
		gAct.milliWait(600)
		Obj.buttonClickSync(Const.kip4DeliverableType + "tab_DeliverableCustomFields")

		Obj.buttonClickSync(Const.kip4DeliverableType + "custField_DropDown")
		Obj.setEditSync(Const.kip4DeliverableType + "deliverableCustomField", "General")
		Obj.buttonClickSync(Const.kip4DeliverableType + "select_deliverableCustomField")

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createPost(String deliverableName, String postText, String userName){
		Act.selectEntityViaSearch("Deliverables")

		Obj.buttonClickSync(Const.columnPicker + "a_New Filter")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		gAct.Wait(1)
		int rowNo = sVal.searchTableReturnRow(5, deliverableName)
		Obj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
		gAct.Wait(1)

		WebD.clickElement("//div[contains(@class,'btn-group btn-group-xs shiny open')]//li[4]//a[1]")
		gAct.Wait(1)

		WebD.switchToActive()
		Obj.buttonClickSync(Const.addPost + "textarea_Post")
		WebD.setEditText("//textarea[@id='Text']", postText)
		WebUI.switchToDefaultContent()
		Obj.buttonClickSync(Const.addPost + "input_Resource")

		Obj.buttonClickSubSync(Const.addPost + "select_Resources", userName, "name")

		Obj.buttonClickSync(Const.addPost + "button_Add")

		boolean elementVisible = Obj.elementPresentSync(Const.timesheetSaved + 'TimeSavedSuccessfully')
		if (elementVisible){
			WebUI.waitForElementNotPresent(findTestObject(Const.timesheetSaved + 'TimeSavedSuccessfully'), 10, FailureHandling.OPTIONAL)
		}

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createLocation(Location location){
		Act.selectEntityViaSearch("Locations")

		boolean filterExist = Obj.elementVisibleSync(Const.columnPicker + "a_DefaultLocationFilter")

		if(!filterExist){
			Obj.buttonClickSync(Const.kip4Generic + "expand_Global Filters")
		}

		Obj.buttonClickSync(Const.columnPicker + "a_DefaultLocationFilter")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		Obj.setEditSync(Const.kip4Generic + "input_Name", location.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", location.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createProject(Project project){
		//use template
		Act.selectEntityViaSearch("Projects")
		Obj.buttonClickSync(Const.columnPicker + "a_ActiveProjects")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Obj.buttonClickSync(Const.columnPicker + "a_AddProject")
		Obj.buttonClickSync(Const.columnPicker + 'template_Radio')

		Obj.buttonClickSync(Const.projectWizard + 'projectTypeDropDown')
		Obj.buttonClickSubSync(Const.projectWizard + 'selectProjectType', project.projectType, "type")
		Obj.buttonClickSync(Const.projectWizard + 'a_Select')
		Obj.buttonClickSubSync(Const.projectWizard + 'selectTemplate', project.template, "template")

		def dateExist = Obj.elementVisibleSync(Const.projectWizard + 'input_ExecutionStartDate')
		if(dateExist) {
			Obj.clearSetEditSync(Const.projectWizard + 'input_ExecutionStartDate', project.startDate)
		}

		Obj.buttonClickSync(Const.projectWizard + 'button_Next')

		Pop.addProject_Wizard_page2(project)

		Obj.buttonClickSync(Const.projectWizard + "button_SaveAndClose")
		gAct.Wait(GVars.shortWait)

		Obj.createNewFilter("Project Name", "=", project.name)
		int rowNo = sVal.searchTableReturnRow(4, project.name)
		project.code = Obj.getEditTextDblSubIntPosSync(Const.columnPicker + "searchTable", rowNo, "row", 3, "col")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createProjectCopyTasks(Project project){   //use template
		Act.selectEntityViaSearch("Projects")

		boolean filterExist = Obj.elementVisibleSync(Const.columnPicker + "a_ActiveProjects")

		if(!filterExist){
			Obj.buttonClickSync(Const.kip4Generic + "expand_Global Filters")
		}

		Obj.buttonClickSync(Const.columnPicker + "a_ActiveProjects")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")

		Obj.buttonClickSync(Const.columnPicker + "a_AddProject")
		Obj.buttonClickSync(Const.columnPicker + 'template_Radio')

		Obj.buttonClickSync(Const.projectWizard + 'projectTypeDropDown')
		Obj.buttonClickSubSync(Const.projectWizard + 'selectProjectType', project.projectType, "type")

		Obj.buttonClickSync(Const.projectWizard + 'a_Select')
		Obj.buttonClickSubSync(Const.projectWizard + 'selectTemplate', project.template, "template")

		Obj.buttonClickSync(Const.projectWizard + 'chk_CopyDeliverables_Wizard')
		Obj.buttonClickSync(Const.projectWizard + 'chk_CopyForecast_Wizard')
		Obj.buttonClickSync(Const.projectWizard + 'chk_CopyRisks_Wizard')

		Obj.setEditSync(Const.projectWizard + 'input_ExecutionStartDate', project.startDate)

		Obj.buttonClickSync(Const.projectWizard + 'button_Next')

		Pop.addProject_Wizard_page2(project)

		Obj.buttonClickSync(Const.projectWizard + "button_SaveAndClose")

		Obj.createNewFilter("Project Name", "=", project.name)
		int rowNo = sVal.searchTableReturnRow(4, project.name)

		project.code = Obj.getEditTextDblSubGenPosSync(Const.columnPicker + "searchTable", rowNo, "row", 3, "col")
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def createEmptyProject(Project project){
		Act.selectEntityViaSearch("Projects")
		Obj.buttonClickSync(Const.columnPicker + "a_AddProject")

		Obj.buttonClickSync(Const.projectWizard + 'projectTypeDropDown')
		Obj.buttonClickSubSync(Const.projectWizard + 'selectProjectType', project.projectType, "type")
		Obj.buttonClickSync(Const.projectWizard + 'radioEmptyProject')
		Obj.buttonClickSync(Const.projectWizard + 'button_Next')

		Pop.addProject_Wizard_page2(project)

		Obj.buttonClickSync(Const.projectWizard + "button_SaveAndClose")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createResource(Resource resource){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_Resources")
		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		Obj.buttonClickSync(Const.kip4Resource + 'radio_AddEmptyResource')
		Obj.buttonClickSync(Const.kip4Resource + 'button_Continue')

		Pop.newResourceForm(resource)

		Obj.buttonClickSync(Const.kip4Resource + "btn_Save")

		Act.verifySavePopUp()

		resource.code = Obj.getAttributeSync(Const.kip4Resource + "input_Code", 'value')

		Obj.buttonClickSync(Const.kip4Generic + "button_Close")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")

		return resource.code
	}


	static def createResourceWithFinancials(Resource resource){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_Resources")
		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")

		Obj.buttonClickSync(Const.kip4Resource + 'radio_AddEmptyResource')
		Obj.buttonClickSync(Const.kip4Resource + 'button_Continue')

		Pop.newResourceFormWithFinancials(resource)

		Obj.buttonClickSync(Const.kip4Resource + "tab_General")
		Obj.buttonClickSync(Const.kip4Resource + "btn_Save")

		resource.code = Obj.getAttributeSync(Const.kip4Resource + "input_Code", 'value')
		if(resource.code == "Auto") {
			gAct.Wait(GVars.shortWait)
			resource.code = Obj.getAttributeSync(Const.kip4Resource + "input_Code", 'value')
		}

		Obj.buttonClickSync(Const.kip4Generic + "button_Close")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")

		return resource.code
	}


	static def createResourceWithDefaults(Resource resource, String project, String activity){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_Resources")
		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		Obj.buttonClickSync(Const.kip4Resource + 'radio_AddEmptyResource')
		Obj.buttonClickSync(Const.kip4Resource + 'button_Continue')

		Pop.newResourceForm(resource)

		Obj.buttonClickSync(Const.kip4Resource + "tab_Defaults")

		Obj.buttonClickSync(Const.kip4Resource + 'dropDown_DefaultProject')
		Obj.setEditSync(Const.kip4Resource + 'input_DefaultProject', project)

		Obj.buttonClickSubSync(Const.kip4Resource + 'select_DefaultProject', project, "proj")

		Obj.buttonClickSync(Const.kip4Resource + 'dropDown_DefaultActivity')
		Obj.setEditSync(Const.kip4Resource + 'input_DefaultActivity', activity)
		Obj.buttonClickSubSync(Const.kip4Resource + 'select_DefaultActivity', activity, "act")

		Obj.buttonClickSync(Const.kip4Resource + "tab_General")

		Obj.buttonClickSync(Const.kip4Resource + "btn_Save")

		Act.verifySavePopUp()

		resource.code = Obj.getAttributeSync(Const.kip4Resource + "input_Code", 'value')

		Obj.buttonClickSync(Const.kip4Generic + "button_Close")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")

		return resource.code
	}


	@Keyword
	static def addSkillsToResource(Resource resource, String skillCode){
		Act.selectEntityViaSearch("Resources")

		Obj.buttonClickSync(Const.columnPicker + "a_New Filter")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Obj.buttonClickSync(Const.columnPicker + "previousPage")
		int rowNo = sVal.searchTableReturnRow(3, resource.name)
		Obj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
		Obj.selectInlineOption(1)
		Obj.buttonClickSync(Const.kip4Resource + "tab_Skills")
		Obj.buttonClickSync(Const.kip4Resource + "btn_AddSkills")
		Obj.setEditSync(Const.kip4Resource + "input_ContainsValue_Skills", skillCode)
		Obj.buttonClickSync(Const.kip4Resource + "btn_Search")
		Obj.buttonClickSync(Const.kip4Resource + "chk_Skills")
		Obj.buttonClickSync(Const.kip4Resource + "btn_Select")
		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def addSecurityGroupToResource(Resource resource, String securityCode){
		Act.selectEntityViaSearch("Resources")

		Obj.buttonClickSync(Const.columnPicker + "a_New Filter")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Obj.buttonClickSync(Const.columnPicker + "previousPage")
		int rowNo = sVal.searchTableReturnRow(3, resource.name)
		Obj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
		Obj.selectInlineOption(1)
		Obj.buttonClickSync(Const.resourceMenu + "SecurityGroupsTab")

		Obj.buttonClickSync(Const.resourceMenu + "btn_AddSecurityGroup")
		Obj.setEditSync(Const.resourceMenu + "input_SkillCode", securityCode)
		Obj.buttonClickSync(Const.resourceMenu + "btnSearch")

		Obj.buttonClickSync(Const.resourceMenu + "chkSelect")
		Obj.buttonClickSync(Const.resourceMenu + "btnSelect")

		Obj.buttonClickSync(Const.resourceMenu + "button_SaveAndClose")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def addSecurityGroupToLogin(Login login, String securityCode){
		Nav.selectSearchFilter("Logins")

		int rowNo = sVal.searchTableReturnRow(3, login.name)
		Obj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
		Obj.selectInlineOption(1)

		Obj.buttonClickSync(Const.kip4Login + 'tab_Administration')
		Obj.buttonClickSync(Const.kip4Login + 'chk_MaintainResources')

		Obj.buttonClickSubSync(Const.kip4Generic + 'dropDown_GenericCombo', "permissions.setupResourceMaintain.resources", "val")
		Obj.buttonClickSubSync(Const.kip4Generic + "select_GenericCombo", "Security Group Resources", "val")

		Obj.buttonClickSync(Const.kip4LoginAddEntity + "btn_SelGrp")

		Obj.buttonClickSync(Const.kip4LoginAddEntity + "button_Add")
		Obj.setEditSync(Const.kip4LoginAddEntity + "input_BeginsWith_value", securityCode)
		Obj.buttonClickSync(Const.kip4LoginAddEntity + "button_Search")
		Obj.buttonClickSync(Const.kip4LoginAddEntity + "chk_SecGrp")
		Obj.buttonClickSync(Const.kip4LoginAddEntity + "button_Select_filterScreen")

		Obj.buttonClickSync(Const.kip4LoginAddEntity + "button_SelectFinalAdd")
		Obj.buttonClickSync(Const.kip4Generic + "button_SaveAndClose")
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	@Keyword
	static def editControlledNotesInLoginProfile(String loginName, String edt, String readOnly, String restore){
		Nav.selectSearchFilter("Login Profiles")

		int rowNo = sVal.searchTableReturnRow(3, loginName)

		Obj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")
		Obj.selectInlineOption(1)

		Obj.buttonClickSync(Const.kip4LoginProfile + 'Tab_Customer Accounts')

		Obj.buttonClickSync(Const.kip4LoginProfile + "dropDown_ControlledNotesAccess")
		Obj.buttonClickSubSync(Const.kip4LoginProfile + "select_ControlledNotesAccess", edt, "val")

		def val3 = WebD.getCheckBoxState("//input[@id='crm.editReadonlyActions']")

		if(val3 != readOnly){
			Obj.buttonClickSync(Const.kip4LoginProfile + 'chk_EditReadonlyActions')
		}

		def val4 = WebD.getCheckBoxState("//input[@id='crm.restoreFinishedProcesses']")

		if(val4 != restore){
			Obj.buttonClickSync(Const.kip4LoginProfile + 'chk_RestoreFinishedProcesses')
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()
		//		gAct.Wait(GVars.shortWait)

		//		Obj.refreshUI()
	}


	static def createRole(Role role){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_Roles")

		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		if(Obj.elementVisibleSync(Const.kip4Roles + "radio_AddEmpty")){
			Obj.buttonClickSync(Const.kip4Roles + "radio_AddEmpty")
			Obj.buttonClickSync(Const.kip4Roles + "btn_Continue")
		}

		Obj.setEditSync(Const.kip4Generic + "input_Name", role.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", role.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		if(role.department != "") {
			Obj.selectComboboxValue("department", "department", role.department, role.department)
		}

		if(role.location != "") {
			Obj.selectComboboxValue("location", "location", role.location, role.location)
		}

		Obj.scrollToElementSync(Const.kip4Roles + "textarea_Notes")

		if(role.workingTime != "") {
			//			Obj.selectComboboxValue("workingTime", "workingTime", role.workingTime, role.workingTime)

			Obj.buttonClickSync(Const.kip4Roles + "dropDown_WorkingTime")
			Obj.buttonClickSync(Const.kip4Roles + "delete_WorkingTime")
			Obj.buttonClickSubSync(Const.kip4Roles + "select_WorkingTime", role.workingTime, "wt")
		}

		if(role.costCentre != "") {
			Obj.selectComboboxValue("costCentre", "costCentre", role.costCentre, role.costCentre)
		}

		Obj.setEditSync(Const.kip4Roles + "textarea_Notes",role.notes)

		Obj.buttonClickSync(Const.kip4Roles + "tab_Financial")

		if(role.charge != "") {
			Obj.selectComboboxValue("defaultStandardCharge","defaultStandardCharge", role.charge, role.charge)
		}

		if(role.cost != "") {
			Obj.selectComboboxValue("defaultStandardCost","defaultStandardCost", role.cost, role.cost)
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createRoleTemplate(Role role){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.busEntMenu + "a_Roles")

		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		if(Obj.elementVisibleSync(Const.kip4Roles + "radio_AddEmpty")) {
			Obj.buttonClickSync(Const.kip4Roles + "radio_AddEmpty")
			Obj.buttonClickSync(Const.kip4Roles + "btn_Continue")
		}

		Obj.setEditSync(Const.kip4Generic + "input_Name", role.name)


		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4Roles + "chk_Template")

		Obj.setEditSync(Const.kip4Generic + "input_Code", role.code)

		if(role.department != "") {
			Obj.selectComboboxValue("department", "department", role.department, role.department)
		}

		if(role.location != "") {
			Obj.selectComboboxValue("location", "location", role.location, role.location)
		}

		Obj.scrollToElementSync(Const.kip4Roles + "textarea_Notes")

		if(role.workingTime != "") {
			Obj.buttonClickSync(Const.kip4Roles + "dropDown_WorkingTime")
			Obj.setEditSync(Const.kip4Roles + "input_WorkingTime", role.workingTime)
			Obj.buttonClickSubSync(Const.kip4Roles + "select_WorkingTime", role.workingTime, "wt")
		}

		if(role.costCentre != "") {
			Obj.selectComboboxValue("costCentre", "costCentre", role.costCentre, role.costCentre)
		}

		Obj.setEditSync(Const.kip4Roles + "textarea_Notes",role.notes)

		Obj.buttonClickSync(Const.kip4Roles + "tab_Financial")

		if(role.charge != "") {
			Obj.selectComboboxValue("defaultStandardCharge","defaultStandardCharge", role.charge, role.charge)
		}

		if(role.cost != "") {
			Obj.selectComboboxValue("defaultStandardCost","defaultStandardCost", role.cost, role.cost)
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createCurrency(Currency currency){
		Act.selectEntityViaSearch("Currencies")

		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		Obj.setEditSync(Const.kip4Generic + "input_Name", currency.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", currency.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active != currency.active){
			WebD.clickElement("//span[(contains(text(), ' Active ') or contains(., ' Active '))]")
		}

		Obj.setEditSync(Const.kip4Currency + "input_symbol", currency.symbol)

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createCharge(Charge charge){
		Act.selectEntityViaSearch("Charges")

		Obj.buttonClickSync(Const.columnPicker + "a_New Filter")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", charge.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", charge.code)
		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.selectComboboxValue("currency", "currency", charge.currency, charge.currency)

		Obj.setEditSync(Const.kip4Charge + "input_rate", charge.rate)
		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createCost(Cost cost){
		Act.selectEntityViaSearch("Costs")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", cost.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", cost.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.selectComboboxValue("currency", "currency", cost.currency, cost.currency)

		Obj.setEditSync(Const.kip4Cost + "input_rate", cost.rate)

		Obj.scrollToElementSync(Const.kip4Cost + "radio_StandardTime")
		gAct.Wait(1)

		if(cost.appliesTo == "") {
			WebD.clickElement("//span[contains(text(),'Standard')]")
		}
		else
		{
			WebD.clickElement("//span[contains(text(),'" + cost.appliesTo + "')]")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createCostWithResource(Cost cost, String firstName){
		Act.selectEntityViaSearch("Costs")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")
		Obj.setEditSync(Const.kip4Generic + "input_Name", cost.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", cost.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4Cost + "select_currencyControl")
		Obj.setEditSync(Const.kip4Cost + 'input_currency', cost.currency)
		gAct.Wait(1)

		Obj.buttonClickSubSync(Const.kip4Cost + "select_Currency", " " + cost.currency + " ", "curr")

		Obj.setEditSync(Const.kip4Cost + "input_rate", cost.rate)

		Obj.scrollToElementSync(Const.kip4Cost + "radio_StandardTime")
		gAct.Wait(1)

		if(cost.appliesTo == "") {
			WebD.clickElement("//span[contains(text(),'Standard')]")
		}
		else
		{
			WebD.clickElement("//span[contains(text(),'" + cost.appliesTo + "')]")
		}

		Obj.buttonClickSync(Const.kip4Cost + "tab_Restrictions")
		Obj.buttonClickSync(Const.kip4Cost + "chk_CostAvailableAllResources")
		Obj.buttonClickSync(Const.kip4Cost + "btn_Add")
		Obj.setEditSync(Const.kip4Cost + "input_Contains_value", firstName)
		Obj.buttonClickSync(Const.kip4Cost + "btn_Search")
		Obj.buttonClickSync(Const.kip4Cost + "chk_selResource")
		Obj.buttonClickSync(Const.kip4Cost + "btn_Select")


		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}



	static def createNominalCode(NominalCode nominalCode){
		Act.selectEntityViaSearch("Nominal Codes")

		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		Obj.setEditSync(Const.kip4Generic + "input_Name", nominalCode.name)

		Obj.setEditSync(Const.kip4Generic + "input_Code", nominalCode.code)

		def stillActive = WebD.getCheckBoxState("//input[@id='active']")

		if(stillActive == null){
			WebD.clickElement("//span[contains(text(),'Active')]")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createNominalCodeDefaults(NominalCode nominalCode){
		Act.selectEntityViaSearch("Nominal Codes")
		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")

		Obj.setEditSync(Const.kip4Generic + "input_Name", nominalCode.name)

		Obj.setEditSync(Const.kip4Generic + "input_Code", nominalCode.code)

		def stillActive = WebD.getCheckBoxState("//input[@id='active']")

		if(stillActive == null){
			WebD.clickElement("//span[contains(text(),'Active')]")
		}

		gAct.Wait(1)
		Obj.checkSync(Const.kip4Nominal + "chk_DefaultExpenseNominalCode")

		Obj.checkSync(Const.kip4Nominal + "chk_DefaultTimesheetNominalCode")

		Obj.buttonClickSync(Const.kip4Generic + "button_SaveAndClose")

		Obj.buttonClickSync(Const.kip4Nominal + "button_OK")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createSkill(Skill skill){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.skillsMenu + "a_Skills")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", skill.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", skill.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.setEditSync(Const.kip4Skills + "input_minLevel", skill.minLevel)
		Obj.setEditSync(Const.kip4Skills + "input_maxLevel", skill.maxLevel)
		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createSkillWithProjectAndResource(SkillWithProjRes skill){
		String fieldValue = "Project Name"
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.skillsMenu + "a_Skills")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name", skill.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", skill.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.setEditSync(Const.kip4Skills + "input_minLevel", skill.minLevel)
		Obj.setEditSync(Const.kip4Skills + "input_maxLevel", skill.maxLevel)

		Obj.buttonClickSync(Const.kip4Skills + "tab_Resource")
		Obj.buttonClickSync(Const.kip4Skills + "btn_AddResources")
		Obj.setEditSync(Const.kip4Skills + "input_Contains_value", skill.resource)
		Obj.buttonClickSync(Const.kip4Skills + "btn_Search")
		Obj.buttonClickSync(Const.kip4Skills + "chk_Resource")
		Obj.buttonClickSync(Const.kip4Skills + "button_Select")
		Obj.buttonClickSync(Const.kip4Skills + "dropDown_Level")

		Obj.buttonClickSubSync(Const.kip4Skills + "select_Level", skill.resourceLevel, "val")

		Obj.buttonClickSync(Const.kip4Skills + "tab_Project")
		Obj.buttonClickSync(Const.kip4Skills + "btn_AddProjects")
		Obj.buttonClickSync(Const.kip4Skills + "btn_NewFilter")

		Obj.buttonClickSync(Const.kip4Skills + "dropDownField")
		Obj.setEditSync(Const.kip4Skills + "input_Field", fieldValue)

		Obj.buttonClickSubSync(Const.kip4Skills + "selectField", fieldValue, "field")

		Obj.setEditSync(Const.kip4Skills + "input_value", skill.project)
		Obj.buttonClickSync(Const.kip4Skills + "btn_Search")

		Obj.buttonClickSync(Const.kip4Skills + "chk_Project")

		Obj.buttonClickSync(Const.kip4Skills + "button_Select")
		gAct.Wait(1)
		Obj.buttonClickSync(Const.kip4Skills + "dropDown_Level")
		Obj.buttonClickSubSync(Const.kip4Skills + "select_Level", skill.projectLevel, "val")

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createLogin(Login login, String tmplate){
		Nav.selectSearchFilter("Logins")

		Obj.buttonClickSync(Const.kip4Generic + "a_Add")
		Obj.buttonClickSync(Const.kip4Login + 'radio_AddLoginfromTemplate')
		Obj.buttonClickSync(Const.kip4Login + 'dropDown_LoginTemplate')
		Obj.setEditSync(Const.kip4Login + 'input_Template', tmplate)

		Obj.buttonClickSubSync(Const.kip4Login + 'select_Template', tmplate, "log")

		Obj.buttonClickSync(Const.kip4Login + 'btn_Continue')

		Obj.scrollToElementSync(Const.kip4Login + "label_Name")
		Obj.setEditSync(Const.kip4Login + "input_Name", login.name)
		Obj.setEditSync(Const.kip4Login + "input_Code", login.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.scrollToElementSync(Const.kip4Login + "label_UserID")
		Obj.setEditSync(Const.kip4Login + "input_UserID", login.userId)

		Obj.scrollToElementSync(Const.kip4Login + "label_Password")
		Obj.setEditSync(Const.kip4Login + "input_Password", login.password)
		Obj.setEditSync(Const.kip4Login + "input_PasswordConfirm", login.password)


		def expire = WebD.getCheckBoxState("//input[@id='general.passwordNeverExpires']")

		if(expire == null){
			Obj.buttonClickSync(Const.kip4Login + "chk_PasswordNeverExpires")
		}

		def change = WebD.getCheckBoxState("//input[@id='general.passwordMustBeChangedAtNextLogin']")

		if(change == true){
			Obj.buttonClickSync(Const.kip4Login + "chk_PasswordMustBeChanged")
		}

		Obj.scrollToElementSync(Const.kip4Login + "label_Resource")
		Obj.buttonClickSync(Const.kip4Login + 'dropDown_Resource')
		Obj.setEditSync(Const.kip4Login + 'input_Resource', login.ownResource)
		Obj.buttonClickSubSync(Const.kip4Login + 'select_Resource', login.name, "res")
		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createLoginWithLoginProfile(LoginPG login, String tmplate){
		Nav.selectSearchFilter("Logins")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")
		Obj.buttonClickSync(Const.kip4Login + 'radio_AddLoginfromTemplate')
		Obj.buttonClickSync(Const.kip4Login + 'dropDown_LoginTemplate')
		Obj.setEditSync(Const.kip4Login + 'input_Template', tmplate)
		Obj.buttonClickSubSync(Const.kip4Login + 'select_Template', tmplate, "log")

		Obj.buttonClickSync(Const.kip4Login + 'btn_Continue')
		Obj.scrollToElementSync(Const.kip4Login + "label_Name")

		Obj.setEditSync(Const.kip4Login + "input_Name", login.name)
		Obj.setEditSync(Const.kip4Login + "input_Code", login.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.scrollToElementSync(Const.kip4Login + "label_UserID")
		Obj.setEditSync(Const.kip4Login + "input_UserID", login.userId)

		Obj.scrollToElementSync(Const.kip4Login + "label_Password")
		Obj.setEditSync(Const.kip4Login + "input_Password", login.password)
		Obj.setEditSync(Const.kip4Login + "input_PasswordConfirm", login.password)

		def expire = WebD.getCheckBoxState("//input[@id='general.passwordNeverExpires']")

		if(expire == null){
			Obj.buttonClickSync(Const.kip4Login + "chk_PasswordNeverExpires")
		}

		def change = WebD.getCheckBoxState("//input[@id='general.passwordMustBeChangedAtNextLogin']")

		if(change == true){
			Obj.buttonClickSync(Const.kip4Login + "chk_PasswordMustBeChanged")
		}

		String lcProf = login.profile.toString().toLowerCase()
		Obj.selectComboboxValue("general.profile", "general.profile", login.profile, lcProf)

		Obj.selectComboboxValue("general.resource", "general.resource", login.ownResource, login.name)
		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createLoginWithLoginGroup(LoginPG login, String tmplate){
		Nav.selectSearchFilter("Logins")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")
		Obj.buttonClickSync(Const.kip4Login + 'radio_AddLoginfromTemplate')

		Obj.buttonClickSync(Const.kip4Login + 'dropDown_LoginTemplate')
		Obj.setEditSync(Const.kip4Login + 'input_Template', tmplate)
		Obj.buttonClickSubSync(Const.kip4Login + 'select_Template', tmplate, "log")
		Obj.buttonClickSync(Const.kip4Login + 'btn_Continue')


		Obj.scrollToElementSync(Const.kip4Login + "label_Name")
		Obj.setEditSync(Const.kip4Login + "input_Name", login.name)
		Obj.setEditSync(Const.kip4Login + "input_Code", login.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.scrollToElementSync(Const.kip4Login + "label_UserID")
		Obj.setEditSync(Const.kip4Login + "input_UserID", login.userId)

		Obj.scrollToElementSync(Const.kip4Login + "label_Password")
		Obj.setEditSync(Const.kip4Login + "input_Password", login.password)
		Obj.setEditSync(Const.kip4Login + "input_PasswordConfirm", login.password)

		def expire = WebD.getCheckBoxState("//input[@id='general.passwordNeverExpires']")

		if(expire == null){
			Obj.buttonClickSync(Const.kip4Login + "chk_PasswordNeverExpires")
		}

		def change = WebD.getCheckBoxState("//input[@id='general.passwordMustBeChangedAtNextLogin']")

		if(change == true){
			Obj.buttonClickSync(Const.kip4Login + "chk_PasswordMustBeChanged")
		}

		String lcProf = login.profile.toString().toLowerCase()
		Obj.selectComboboxValue("general.profile", "general.profile", login.profile, lcProf)

		Obj.selectComboboxValue("general.group", "general.group", login.group, login.group)

		Obj.selectComboboxValue("general.resource", "general.resource", login.ownResource, login.name)

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createLoginTemplate(Login login, String tmplate){
		Nav.selectSearchFilter("Logins")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")
		Obj.buttonClickSync(Const.kip4Login + 'radio_AddLoginfromTemplate')

		Obj.buttonClickSync(Const.kip4Login + 'dropDown_LoginTemplate')
		Obj.setEditSync(Const.kip4Login + 'input_Template', tmplate)
		Obj.buttonClickSubSync(Const.kip4Login + 'select_Template', tmplate, "log")

		Obj.buttonClickSync(Const.kip4Login + 'btn_Continue')

		Obj.checkSync(Const.kip4Login + "chk_Template")

		Obj.scrollToElementSync(Const.kip4Login + "label_Name")
		Obj.setEditSync(Const.kip4Login + "input_Name", login.name)
		Obj.setEditSync(Const.kip4Login + "input_Code", login.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		if(login.ownResource != "") {
			Obj.scrollToElementSync(Const.kip4Login + "label_Resource_Templ")
			Obj.selectComboboxValue("general.resource", "general.resource", login.ownResource, login.name)
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createLoginGroup(LoginGroup loginGroup){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.securityMenu + "a_LoginGroups")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")
		Obj.setEditSync(Const.kip4Generic + "input_Name", loginGroup.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", loginGroup.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createLoginGroupWithFilters(LoginGroupWithTabs loginGroup){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.securityMenu + "a_LoginGroups")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")
		Obj.setEditSync(Const.kip4Generic + "input_Name", loginGroup.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", loginGroup.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		//logins
		Obj.buttonClickSync(Const.kip4LoginGroup + "tab_Logins")
		createFilters("Login Name", loginGroup.login)

		//reports
		Obj.buttonClickSync(Const.kip4LoginGroup + "tab_Reports")
		createFilters("Name", loginGroup.report)

		//filters
		Obj.buttonClickSync(Const.kip4LoginGroup + "tab_Filters")
		Obj.buttonClickSync(Const.kip4GenericFilter + "btn_Add")
		Obj.buttonClickSync(Const.kip4LoginGroup + "chk_filters")

		Obj.buttonClickSubSync(Const.kip4LoginGroup + "chk_filtersByPos", "4", "pos")
		Obj.buttonClickSync(Const.kip4GenericFilter + "btn_Select")

		//action views
		Obj.buttonClickSync(Const.kip4LoginGroup + "tab_ActionViews")
		Obj.buttonClickSync(Const.kip4GenericFilter + "btn_Add")

		Obj.selectComboboxValue("add", "add", loginGroup.actionView, loginGroup.actionView)

		Obj.buttonClickSync(Const.kip4LoginGroup + "btn_AddActionView")

		//projects
		Obj.buttonClickSync(Const.kip4LoginGroup + "tab_ProjectLevels")

		String singName
		for(int cnt = 1; cnt < 5; cnt++) {
			singName = WebD.getObjectAttribute("//table[@id='projectLevelsTable']/tbody/tr[" + cnt +"]/td[2]/formly-field/ng-component/div/div/formly-field-text/div/input", "value")
			if(singName == loginGroup.projectLevels) {
				Obj.buttonClickSubIntPosSync(Const.kip4LoginGroup + "ProjLevelTableCell", cnt, "row")
				break
			}
		}

		//notifications
		Obj.buttonClickSync(Const.kip4LoginGroup + "tab_Notifications")
		Obj.buttonClickSync(Const.kip4GenericFilter + "btn_Add")

		def trucString = loginGroup.notification.size()
		String newStr = (loginGroup.notification.toString())//.substring(trucString - 20)
		Obj.selectComboboxValue("add", "add", newStr, loginGroup.notification)

		Obj.buttonClickSync(Const.kip4LoginGroup + "btn_AddActionView")
		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createFilters(String fieldValue, String Value){
		if(Value != "") {
			Obj.buttonClickSync(Const.kip4GenericFilter + "btn_Add")
			Obj.buttonClickSync(Const.kip4GenericFilter + "btn_New")
			Obj.buttonClickSync(Const.kip4GenericFilter + "dropDownField")
			Obj.setEditSync(Const.kip4GenericFilter + "input_Field", fieldValue)
			Obj.buttonClickSubSync(Const.kip4GenericFilter + "selectField", fieldValue, "field")

			Obj.setEditSync(Const.kip4GenericFilter + "input_value", Value)
			Obj.buttonClickSync(Const.kip4GenericFilter + "btn_Search")
			Obj.buttonClickSync(Const.kip4GenericFilter + "chk_Value")
			Obj.buttonClickSync(Const.kip4GenericFilter + "btn_Select")
		}
	}


	static def createLoginProfile(LoginProfile loginProfile){
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.securityMenu + "a_LoginProfiles")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Generic + "input_Name",loginProfile.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code",loginProfile.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createSecurityGroup(SecurityGroup securityGroup){
		Nav.selectSearchFilter("Security Groups")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")
		Obj.setEditSync(Const.kip4Generic + "input_Name", securityGroup.name)
		Obj.setEditSync(Const.kip4Generic + "input_Code", securityGroup.code)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			Obj.checkSync(Const.kip4Generic + "chk_Active")
		}

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createClaim(Claim claim){
		Nav.selectSearchFilter("Claims")

		Obj.buttonClickSync(Const.columnPicker + "a_Add")
		Obj.setEditSync(Const.claim + "input_CategoryCodeFilter", claim.category)
		Obj.buttonClickSync(Const.claim + "input_SearchFilter")

		Obj.checkSync(Const.claim +'input_Default Category_chkSelect')
		Obj.buttonClickSync(Const.claim +'button_Select')

		String resourceName = Obj.getAttributeSync(Const.claim + 'input_Resource', 'value')
		if(resourceName != claim.resource){
			Obj.setEditSync(Const.claim + 'input_Resource', claim.resource)
		}

		String date = Obj.getEditTextSync(Const.claim + 'date')
		gAct.compareStringAndReport(date, claim.date)

		Obj.setEditSync(Const.claim + 'textarea_Notes', claim.notes)
		Obj.buttonClickSync(Const.claim + 'dateFrom')
		Obj.buttonClickSync(Const.datePicker + 'button_Today')

		def today = new Date()
		def todayDate = today.format('MMM')
		String nextMonth = Com.getNextMonth()
		String[] nextMnthYear = nextMonth.split("/")
		Obj.buttonClickSync(Const.datePicker + 'button_Close')

		Obj.buttonClickSync(Const.claim + 'dateTo')
		gAct.Wait(1)
		Obj.selectComboByLabelSync(Const.datePicker + 'select_Month_To', nextMnthYear[0])
		Obj.selectComboByLabelSync(Const.datePicker + 'select_Year_To', nextMnthYear[1])
		Obj.buttonClickDblSubGenSync(Const.datePicker + "a_Date_To", 3, "row", 1, "col")
		gAct.Wait(1)

		Obj.checkSubSync(Const.claim +'chkSelectExpense', 1, "row")
		Obj.buttonClickSync(Const.claim + "button_Save")

		def AlertExist = WebUI.verifyAlertPresent(GVars.midWait, FailureHandling.OPTIONAL)
		if(AlertExist) {
			Obj.acceptAlertSync()
			gAct.Wait(GVars.shortWait)
			WebUI.switchToWindowIndex(1)
			WebUI.closeWindowIndex(1)
			gAct.Wait(1)
			WebUI.switchToDefaultContent()
		}

		String refNo = Obj.getEditTextSync(Const.claim + "ClaimReference")

		Obj.buttonClickSync(Const.claim + 'button_Close')
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")

		return refNo
	}


	static def acceptClaimByRef(Claim claim, String refNo, String claimNewNotes){
		Nav.selectSearchFilter("Claims")

		int rowNo = sVal.searchTableReturnRow(3, refNo)
		Obj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

		Obj.buttonClickSubIntPosSync(Const.columnPicker + "a_ViewInline", 1, "pos")
		Obj.selectComboByLabelSync(Const.columnPicker + "select_ClaimAcces", "Accepted")
		Obj.setEditSync(Const.columnPicker + "AcceptClaimNotes", claimNewNotes)

		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def acceptClaim(Claim claim, String expenseVal, String claimNewNotes){
		Nav.selectSearchFilter("Claims")

		int rowNo = sVal.searchTableReturnRow(7, "£" + expenseVal)
		Obj.buttonClickSubIntPosSync(Const.columnPicker + 'inlineEdit_Alt', rowNo, "row")

		Obj.buttonClickSubIntPosSync(Const.columnPicker + "a_ViewInline", 1, "pos")
		Obj.selectComboByLabelSync(Const.columnPicker + "select_ClaimAcces", "Accepted")
		Obj.setEditSync(Const.columnPicker + "AcceptClaimNotes", claimNewNotes)

		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createLock(Lock lock){
		Nav.selectSearchFilter("Locks")
		Obj.buttonClickSync(Const.kip4Generic + "a_Add")

		Obj.setEditSync(Const.kip4Locks + "input_StartDate", lock.startDate)
		Obj.setEditSync(Const.kip4Locks + "input_EndDate", lock.endDate)

		Obj.checkSync(Const.kip4Locks + "chk_IncludeTimesheets")

		Obj.buttonClickSync(Const.kip4Locks + "btn_EstimateLocks")

		String timesheetsIncluded = Obj.getAttributeSync(Const.kip4Locks + "TimesheetCount", "value")
		int timesheetInt = timesheetsIncluded as Integer

		if(timesheetInt >= 5){
			Rep.pass("Timesheets included as expected")
		}else{
			Rep.fail("Timesheets not included")
		}
		Obj.buttonClickSync(Const.kip4Locks + "btn_Saveclosebutton")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createInvoice(Invoice invoice){
		Nav.selectSearchFilter("Invoices")

		Obj.buttonClickSync(Const.columnPicker + "a_Add")
		gAct.Wait(1)
		WebD.switchToiFrameAndEditObject("//div[@id='ui-id-1']/iframe", "//input[@id='ctl00_phFormContent_ucInvoiceDate_txtDate']", invoice.date)
		gAct.Wait(1)
		WebUI.switchToDefaultContent()

		Obj.setEditSync(Const.columnPicker + "input_Project", invoice.project)
		Obj.buttonClickSync(Const.addContract + 'textarea_Notes')
		Obj.setEditSync(Const.addContract + 'textarea_Notes', invoice.notes)
		Obj.buttonClickSync(Const.columnPicker + "tab_AccountInfo")
		Obj.setEditSync(Const.columnPicker + "input_Reference", invoice.reference)
		Obj.buttonClickSync(Const.columnPicker + "button_SaveAmpClose")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createDeliverable(Deliverable deliverable){
		Nav.selectSearchFilter("Deliverables")

		Obj.buttonClickSync(Const.columnPicker + "a_All Deliverables")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Obj.buttonClickSync(Const.columnPicker + "a_Add")

		Obj.setEditSync(Const.columnPicker + 'input_DelName', deliverable.name)
		Obj.clearSetEditSync(Const.columnPicker + "input_Resource", deliverable.owner)
		Obj.setEditSync(Const.columnPicker + "input_Project", deliverable.project)
		Obj.setEditSync(Const.columnPicker + "input_Type", deliverable.type)

		Obj.setEditSync(Const.columnPicker + "input_DeliveryDate", deliverable.deliveryDate)

		Obj.buttonClickSync(Const.columnPicker + "button_SaveAmpClose")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createDeliverableAndConfirm(Deliverable deliverable){
		Nav.selectSearchFilter("Deliverables")

		Obj.buttonClickSync(Const.columnPicker + "a_Add")
		Obj.setEditSync(Const.columnPicker + 'input_DelName', deliverable.name)
		Obj.selectComboByLabelSync(Const.columnPicker + "select_ConfirmedProvisional", "Confirmed")

		String itemTextOwn = deliverable.owner.toString()
		Obj.populateEditBox(Const.columnPicker + 'input_Resource', itemTextOwn, Const.columnPicker + 'edt_DropSelect')

		Obj.setEditSync(Const.columnPicker + "input_Project", deliverable.project)

		String itemTextType = deliverable.type.toString()
		Obj.populateEditBox(Const.columnPicker + 'input_Type', itemTextType, Const.columnPicker + 'edt_DropSelect')

		Obj.setEditSync(Const.columnPicker + "input_DeliveryDate", deliverable.deliveryDate)

		Obj.buttonClickSync(Const.columnPicker + "button_SaveAmpClose")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createBenefitActual(BenefitActual benefitActual, Currency currency) {
		String[] arrDateNew = benefitActual.date.split('/')
		Nav.selectSearchFilter("Benefit Actuals")

		Obj.buttonClickSync(Const.columnPicker + "a_DefaultBenefitActualFilter")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")

		Obj.selectComboboxValue("project", "project", benefitActual.project, benefitActual.project)

		WebD.clickUsingJavaScript("//span[@class = 'calendar-button']")
		dpck.setDate(arrDateNew[0], arrDateNew[1], arrDateNew[2])

		Obj.selectComboboxValue("benefit", "benefit", benefitActual.benefit, benefitActual.benefit)
		Obj.selectComboboxValue("currency", "currency", currency.name, currency.name)

		Obj.setEditSync(Const.kip4BenefitActual + "input_Amount", benefitActual.amount)

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createAction(Action action){
		Nav.selectSearchFilter("All Actions")

		Obj.buttonClickSync(Const.columnPicker + "a_Add")
		Obj.clearSetEditSync(Const.columnPicker + "input_Resource", action.resource)

		Obj.buttonClickSync(Const.columnPicker + "tab_ToDo")

		Obj.buttonClickSync(Const.columnPicker + "select_ActionType")

		Obj.selectComboByValueSync(Const.columnPicker + "select_ActionType", action.actionType)
		Obj.buttonClickSync(Const.columnPicker + "tab_ToDo")

		Obj.setEditSync(Const.columnPicker + "input_RequiredDate", action.date)

		Obj.setEditSync(Const.columnPicker + "input_Project", action.project)
		Obj.buttonClickSync(Const.columnPicker + "tab_ToDo")
		Obj.setEditSync(Const.columnPicker + "action_Details", action.description)

		Obj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createRiskAction(RiskAction riskAction){
		Nav.selectSearchFilter("Mitigation Plan Items")

		Obj.buttonClickSync(Const.columnPicker + "a_Add")

		Obj.setEditSync(Const.columnPicker + 'input_Project', riskAction.project)
		Obj.buttonClickSync(Const.columnPicker + "td_General")

		Obj.selectComboByLabelSync(Const.columnPicker + 'select_Risk', riskAction.risk)

		String itemText = riskAction.type.toString()
		Obj.populateEditBox(Const.columnPicker + 'input_RiskActionType', itemText, Const.columnPicker + 'edt_DropSelect')

		String ownerText = Obj.getAttributeSync(Const.risks + 'input_Owner', 'value')
		if(ownerText == ""){
			Obj.setEditSync(Const.risks + 'input_Owner', riskAction.owner)
			Obj.buttonClickSync(Const.risks + 'input_Title')
		}

		WebUI.verifyOptionSelectedByLabel(findTestObject(Const.risks + 'select_Status'), riskAction.status, false, GVars.midWait)
		Obj.setEditSync(Const.columnPicker + 'input_DueDate', riskAction.dateDue)

		Obj.selectComboByLabelSync(Const.columnPicker + 'select_Priority', riskAction.priority)
		Obj.buttonClickSync(Const.columnPicker + "td_General")
		Obj.setEditSync(Const.columnPicker + "action_Description", riskAction.description)

		Obj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		//		gAct.Wait(GVars.shortWait)
	}


	static def createIssueAction(IssueAction issueAction){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		Obj.buttonClickSync(Const.search + "search_DropDown")
		Obj.buttonClickSubSync(Const.search + "filterType", "Resolution Plan Items", "label")

		Obj.buttonClickSync(Const.columnPicker + "a_Add")
		Obj.setEditSync(Const.issues + 'input_Project', issueAction.project)
		Obj.buttonClickSync(Const.columnPicker + "td_General")

		Obj.selectComboByLabelSync(Const.columnPicker + 'select_Issue', issueAction.issue)
		Obj.clearSetEditSync(Const.columnPicker + 'input_RiskActionType', issueAction.type)
		Obj.buttonClickSync(Const.columnPicker + "td_General")

		String ownerText = Obj.getAttributeSync(Const.issues + 'input_Owner', 'value')
		if(ownerText == ""){
			Obj.setEditSync(Const.issues + 'input_Owner', issueAction.owner)
			Obj.buttonClickSync(Const.issues + 'input_Title')
		}

		Obj.setEditSync(Const.columnPicker + 'input_DueDate', issueAction.dueDate)

		Obj.selectComboByLabelSync(Const.columnPicker + 'select_Priority', issueAction.priority)

		Obj.buttonClickSync(Const.columnPicker + "td_General")
		Obj.setEditSync(Const.columnPicker + "action_Description", issueAction.description)

		Obj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createWorkingTimes(WorkingTimes workingTimes){
		Nav.selectSearchFilter("Working Times")

		WebD.clickElement("//a[@class='tooltip-primary btn btn-default shiny btn-sm viewaction']")

		Obj.setEditSync(Const.addWorkingTime + "input_Name", workingTimes.title)
		Obj.buttonClickSync(Const.addWorkingTime + "a_General")
		Obj.setEditSync(Const.addWorkingTime + "input_Code", workingTimes.code)

		String fThours = Obj.getAttributeSync(Const.addWorkingTime + 'input_FullTimeHoursPerDay', 'value')
		gAct.compareStringAndReport(fThours, "8")

		String FTE = Obj.getAttributeSync(Const.addWorkingTime + 'input_FTE', 'value')
		gAct.compareStringAndReport(FTE, "1")

		String sun = WebD.getCheckBoxState("//input[@id='SundayWorkingEnabled']")
		gAct.compareStringAndReport(sun, null)

		String mon = WebD.getCheckBoxState("//input[@id='MondayWorkingEnabled']")
		gAct.compareStringAndReport(mon, "true")

		String tues = WebD.getCheckBoxState("//input[@id='TuesdayWorkingEnabled']")
		gAct.compareStringAndReport(tues, "true")

		String weds = WebD.getCheckBoxState("//input[@id='WednesdayWorkingEnabled']")
		gAct.compareStringAndReport(weds, "true")

		String thurs = WebD.getCheckBoxState("//input[@id='ThursdayWorkingEnabled']")
		gAct.compareStringAndReport(thurs, "true")

		String fri = WebD.getCheckBoxState("//input[@id='FridayWorkingEnabled']")
		gAct.compareStringAndReport(fri, "true")

		String sat = WebD.getCheckBoxState("//input[@id='SaturdayWorkingEnabled']")
		gAct.compareStringAndReport(sat, null)

		Obj.buttonClickSync(Const.addWorkingTime + 'button_Save and Close')
		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createAssignment(Assignment assignment){
		Nav.selectSearchFilter("Assignments")

		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		Obj.selectComboValueOldModal("dropDown_Project", assignment.project)
		Obj.selectComboValueOldModal("dropDown_Task", assignment.task)

		Obj.setEditSync(Const.addAssignment + "input_Description", assignment.description)

		Obj.selectComboValueOldModal("dropDown_Resource", assignment.resource)

		Obj.setEditSync(Const.addAssignment + "input_StartDate", assignment.startDate)
		Obj.setEditSync(Const.addAssignment + "input_EndDate", assignment.endDate)
		Obj.setEditSync(Const.addAssignment + "input_Effort", assignment.effort)
		Obj.buttonClickSync(Const.columnPicker + "button_SaveAndClose")
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createSupply(Supply supply){
		Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Resourcing", Const.dashBoard + "subMenu_ResourcePlan", Const.dashBoard + 'currentPageHeader', "Resource Plan")

		Obj.buttonClickSync(Const.addSupply + "a_AddSupply")
		Obj.setEditSync(Const.addSupply + "input_Project", supply.project)

		Obj.buttonClickSync(Const.addSupply + "td_ResourceLabel")
		Obj.setEditSync(Const.addSupply + "input_Resource", supply.resource)

		Obj.buttonClickSync(Const.addSupply + "EditProjectSupplyLineModalHeader")
		Obj.setEditSync(Const.addSupply + "input_Department", supply.department)

		Obj.buttonClickSync(Const.addSupply + "EditProjectSupplyLineModalHeader")

		Obj.clearSetEditSync(Const.addSupply + "input_Role", supply.role)

		Obj.buttonClickSync(Const.addSupply + "EditProjectSupplyLineModalHeader")
		Obj.setEditSync(Const.addSupply + "input_SupplyWk1", supply.supplywk1)
		Obj.setEditSync(Const.addSupply + "input_SupplyWk2", supply.supplywk2)
		Obj.setEditSync(Const.addSupply + "input_SupplyWk3", supply.supplywk3)
		Obj.setEditSync(Const.addSupply + "input_SupplyWk4", supply.supplywk4)

		Obj.buttonClickSync(Const.addSupply + "button_SaveAndClose")

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createExchangeRate(ExchangeRate exchangeRate, Currency currency){
		String[] arrDate = exchangeRate.startDate.split('/')
		int day = arrDate[0].toInteger()
		int month = arrDate[1].toInteger()
		int year = arrDate[2].toInteger()

		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.financialsMenu + "a_ExchangeRates")
		Obj.buttonClickSync(Const.kip4Generic + "a_NEWAdd")
		Obj.selectComboboxValue("currency", "currency", currency.name, currency.name)
		gAct.Wait(GVars.shortWait)
		WebD.clickUsingJavaScript("//span[@class = 'calendar-button']")
		dpck.setDate(arrDate[0], arrDate[1], arrDate[2])

		Obj.setEditSync(Const.kip4ExchRate + "input_rate", exchangeRate.rate)

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def updateExchangeRate(String exchangeRate, String tableName, String newExRate) {
		Obj.buttonClickSync(Const.mainToolBar + "administration")
		Obj.buttonClickSync(Const.financialsMenu + "a_ExchangeRates")

		int rowNo = sVal.searchSpecificTableReturnRow(2, exchangeRate, tableName)
		Obj.buttonClickDblSubGenSync(Const.columnPicker + 'inlineEdit_ExchgRate', tableName, "table", rowNo, "row")

		Obj.selectInlineOption(1)
		Obj.setEditSync(Const.kip4ExchRate + "input_rate", newExRate)

		Obj.buttonClickSync(Const.kip4Generic + "input_Close_btn btn-primary saveclosebutton")

		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createProjectSnapshot(ProjectSnapshot projectSnapshot){
		Act.selectEntityViaSearch("Project Snapshots")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Obj.buttonClickSync(Const.addProjectSnapshot + "a_AddSnapshot")

		Obj.buttonClickSync(Const.addProjectSnapshot + 'dropDown_ProjSnapshot')
		Obj.setEditSync(Const.addProjectSnapshot + 'input_dropdownSnap',  projectSnapshot.project)

		Obj.buttonClickSubSync(Const.addProjectSnapshot + 'select_Project_Snapshot', projectSnapshot.project, "proj")

		Obj.setEditSync(Const.addProjectSnapshot + 'input__Description', projectSnapshot.name)

		Obj.selectComboByLabelSync(Const.addProjectSnapshot + 'select_Type', projectSnapshot.type)
		Obj.buttonClickSync(Const.addProjectSnapshot + 'button_Save and Close')
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createMultiProjectSnapshot(ProjectSnapshot projectSnapshot, int snapshotNo){
		String snapshotName
		Act.selectEntityViaSearch("Project Snapshots")
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		gAct.Wait(1)

		for(int intCnt = 0; intCnt < snapshotNo; intCnt ++){
			Obj.buttonClickSync(Const.addProjectSnapshot + "a_AddSnapshot")
			Obj.buttonClickSync(Const.addProjectSnapshot + 'dropDown_ProjSnapshot')
			Obj.setEditSync(Const.addProjectSnapshot + 'input_dropdownSnap',  projectSnapshot.project)
			Obj.buttonClickSubSync(Const.addProjectSnapshot + 'select_Project_Snapshot', projectSnapshot.project, "proj")

			Obj.setEditSync(Const.addProjectSnapshot + 'input__Description', projectSnapshot.name)
			Obj.selectComboByLabelSync(Const.addProjectSnapshot + 'select_Type', projectSnapshot.type)
			Obj.buttonClickSync(Const.addProjectSnapshot + 'button_Save and Close')

			snapshotName = Com.generateRandomText(10)
			projectSnapshot.name = snapshotName
		}

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}


	static def createProjectTemplate(Project project){
		Act.selectEntityViaSearch("Projects")

		Obj.buttonClickSync(Const.columnPicker + "a_AddTemplate")
		Obj.buttonClickSync(Const.projectWizard + 'projectTypeDropDown')

		Obj.buttonClickSubSync(Const.projectWizard + 'selectProjectType', project.projectType, "type")
		Obj.buttonClickSync(Const.projectWizard + 'radioEmptyProject')
		Obj.buttonClickSync(Const.projectWizard + 'button_Next')
		Pop.addProject_Wizard_page2(project)

		Obj.buttonClickSync(Const.projectWizard + "button_SaveAndClose")
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
	}

}

