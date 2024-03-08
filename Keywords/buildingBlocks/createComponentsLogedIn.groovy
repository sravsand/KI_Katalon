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

import internal.GlobalVariable as GVars

import global.Report as Rep
import global.Action as gAct
import global.WebDriverMethods as WebD
import global.Common as Com
import global.Object as Obj
import global.Validate as gVal

import keyedInProjects.Action as Act
import keyedInProjects.Constants as Const
import keyedInProjects.Populate as Pop
import keyedInProjects.Navigate as Nav
import timesheets.Populate as tPop
import timesheets.Action as tAct
import search.Validate as sVal

import models.GenerateRole as genRole
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
import models.LoginGroup
import models.GenerateLoginGroup
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

public class createComponentsLogedIn {

	static def role(String[] roleDetails){
		Role roleNew = genRole.createNewRole(roleDetails)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Roles"))
		WebUI.click(findTestObject(Const.roleMenu + "a_Add"))

		Pop.newRolesForm(roleNew)
		WebUI.click(findTestObject(Const.roleMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.roleMenu + "saveRoleError"), 5, FailureHandling.CONTINUE_ON_FAILURE)

		if(saveError){
			Act.roleSearch("Role Name", roleNew.name)
		}else{
			Rep.fail("Role : " + roleNew.name + " was not created.")
			Rep.screenshot(roleNew.name )
			WebUI.click(findTestObject(Const.roleMenu + "button_Close"))
		}
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def resource(String[] resourceDetails){
		Resource resourceNew = genRes.createNewResource(resourceDetails)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Resources"))
		WebUI.click(findTestObject(Const.roleMenu + "a_Add"))

		WebUI.click(findTestObject(Const.resourceMenu + "radio_AddEmptyResource"))
		WebUI.click(findTestObject(Const.resourceMenu + "button_OK"))

		Pop.newResourceForm(resourceNew)

		WebUI.click(findTestObject(Const.resourceMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.resourceMenu + "saveResourceError"), 5, FailureHandling.CONTINUE_ON_FAILURE)

		if(saveError){
			resourceNew.code = Act.resourceSearch("Resource Name", resourceNew.name)
		}else{
			Rep.fail("Resource : " + resourceNew.name + " was not created.")
			Rep.screenshot(resourceNew.name )
			WebUI.click(findTestObject(Const.resourceMenu + "button_Close"))
		}

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)

		return resourceNew.code
	}


	static def roleDD(def roleDetails, def row){
		Role roleNew = genRole.createNewRoleDD(roleDetails, row)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Roles"))
		//		WebUI.click(findTestObject(Const.adminMenu + "subMenuRoles"))
		WebUI.click(findTestObject(Const.roleMenu + "a_Add"))

		Pop.newRolesForm(roleNew)
		WebUI.click(findTestObject(Const.roleMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.roleMenu + "saveRoleError"), 5, FailureHandling.CONTINUE_ON_FAILURE)

		if(saveError){
			Act.roleSearch("Role Name", roleNew.name)
		}else{
			Rep.fail("Role : " + roleNew.name + " was not created.")
			Rep.screenshot(roleNew.name)
			String errorMessage = WebUI.getText(findTestObject(Const.roleMenu + "saveRoleError"))
			String errorSummary = WebUI.getText(findTestObject(Const.roleMenu + "errorMessageSummary"))
			String errorReason = WebUI.getText(findTestObject(Const.roleMenu + "errorMessageReason"))
			Rep.fail("Error : '" + errorMessage + "',  summary : '" + errorSummary + "' , reason : '" + errorReason)

			WebUI.click(findTestObject(Const.roleMenu + "button_Close"))
		}
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)

		return roleNew.code
	}


	static def resourceDD(def resourceDetails, def row){
		Resource resourceNew = genRes.createNewResourceDD(resourceDetails, row)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Resources"))
		WebUI.click(findTestObject(Const.roleMenu + "a_Add"))

		WebUI.click(findTestObject(Const.resourceMenu + "radio_AddEmptyResource"))
		WebUI.click(findTestObject(Const.resourceMenu + "button_OK"))

		Pop.newResourceForm(resourceNew)

		WebUI.click(findTestObject(Const.resourceMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.resourceMenu + "saveResourceError"), 5, FailureHandling.CONTINUE_ON_FAILURE)

		if(saveError){
			resourceNew.code = Act.resourceSearch("Resource Name", resourceNew.name)
		}else{
			Rep.fail("Resource : " + resourceNew.name + " was not created.")
			Rep.screenshot(resourceNew.name )
			WebUI.click(findTestObject(Const.resourceMenu + "button_Close"))
		}

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)

		return resourceNew.code
	}


	static def customerDD(def customerDetails, def row){
		Customer customerNew = genCust.createCustomerDD(customerDetails, row)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Customers"))
		WebUI.click(findTestObject(Const.customerMenu + "a_Add"))

		Pop.newCustomerForm(customerNew)

		WebUI.click(findTestObject(Const.customerMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		customerNew.code = Act.customerSearch("Customer Name", customerNew.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return customerNew.code
	}


	static def customer(Customer customer){
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Customers"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		Pop.newCustomerForm(customer)

		WebUI.click(findTestObject(Const.customerMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		customer.code = Act.customerSearch("Customer Name", customer.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return customer.code
	}


	static def projectDD(def projectDetails, def row){
		Project projectNew = genProj.createProjectDD(projectDetails, row)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))
		WebUI.click(findTestObject(Const.projectsMenu + "a_Add"))

		WebUI.click(findTestObject(Const.projectsMenu + "radio_AddEmptyProject"))
		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newProjectForm(projectNew)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		projectNew.code = Act.projectSearch("Project Name", projectNew.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return projectNew.code
	}


	static def project(Project project){
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_AddProject"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.projectsMenu + "radio_AddEmptyProject"))
		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newProjectForm(project)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		project.code = Act.projectSearch("Project Name", project.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return project.code
	}


	static def costCentreDD(def costCentreDetails, def row){
		CostCentre costCentreNew = genCCentre.createCostCentreDD(costCentreDetails, row)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_CostCentres"))
		WebUI.click(findTestObject(Const.costCentreMenu + "a_Add"))

		Pop.newCostCentreForm(costCentreNew)

		WebUI.click(findTestObject(Const.costCentreMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		Act.costCentreSearch("Cost Centre Name", costCentreNew.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return costCentreNew.code
	}


	static def costCentre(CostCentre costCentre){
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_CostCentres"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.costCentreMenu + "a_Add"))
		gAct.Wait(1)

		Pop.newCostCentreForm(costCentre)

		WebUI.click(findTestObject(Const.costCentreMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return costCentre.code
	}


	static def departmentDD(def departmentDetails, def row){
		Department departmentNew = genDep.createDepartmentDD(departmentDetails, row)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Departments"))
		WebUI.click(findTestObject(Const.departmentMenu + "a_Add"))

		Pop.newDepartmentForm(departmentNew)

		WebUI.click(findTestObject(Const.departmentMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		Act.departmentSearch("Department Name", departmentNew.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return departmentNew.code
	}


	static def department(Department department){
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Departments"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.departmentMenu + "a_Add"))

		Pop.newDepartmentForm(department)

		WebUI.click(findTestObject(Const.departmentMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return department.code
	}


	static def companyDD(def companyDetails, def row){
		Company companyNew = genComp.createCompanyDD(companyDetails, row)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.projectsMenu + "a_Add"))

		WebUI.click(findTestObject(Const.projectsMenu + "radio_AddEmptyProject"))
		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newCompanyForm(companyNew)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		companyNew.code = Act.projectSearch("Project Name", companyNew.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return companyNew.code
	}


	static def company(Company company){
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))

		WebUI.click(findTestObject(Const.projects + "a_Add"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.projectAdd + "radio_AddEmptyProject"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.projectAdd + "select_ProjectEntityType"))

		WebD.clickElement("//div[@id='select2-drop']//div[@class='select2-search']")

		WebUI.selectOptionByValue(findTestObject(Const.projectAdd + "select_ProjectEntityType"), company.projectLevel, false)

		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newCompanyForm(company)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		company.code = Act.projectSearch("Project Name", company.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return company.code
	}


	static def programmeDD(def programmeDetails, def row){
		Programme programmeNew = genProg.createProgrammeDD(programmeDetails, row)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))
		WebUI.click(findTestObject(Const.projectsMenu + "a_Add"))

		WebUI.click(findTestObject(Const.projectsMenu + "radio_AddEmptyProject"))
		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newProgrammeForm(programmeNew)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		programmeNew.code = Act.projectSearch("Project Name", programmeNew.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return programmeNew.code
	}


	static def programme(Programme programme){
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))
		WebUI.click(findTestObject(Const.projectsMenu + "a_Add"))

		WebUI.click(findTestObject(Const.projectsMenu + "radio_AddEmptyProject"))
		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newProgrammeForm(programme)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		programme.code = Act.projectSearch("Project Name", programme.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return programme.code
	}


	static def parentDD(def parentDetails, def row){
		Parent parentNew = genPar.createParentDD(parentDetails, row)
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))
		WebUI.click(findTestObject(Const.projectsMenu + "a_Add"))

		WebUI.click(findTestObject(Const.projectsMenu + "radio_AddEmptyProject"))
		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newParentForm(parentNew)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		parentNew.code = Act.projectSearch("Project Name", parentNew.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return parentNew.code
	}


	static def parent(Parent parent){
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))
		WebUI.click(findTestObject(Const.projectsMenu + "a_Add"))

		WebUI.click(findTestObject(Const.projectsMenu + "radio_AddEmptyProject"))
		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newParentForm(parent)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		parent.code = Act.projectSearch("Project Name", parent.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return parent.code
	}


	static def costDD(def costDetails, def row){
		Cost costNew = genCost.createCostDD(costDetails, row)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.financialsMenu + "a_Costs"))
		WebUI.click(findTestObject(Const.costMenu + "a_Add"))

		Pop.newCostForm(costNew)

		WebUI.click(findTestObject(Const.costMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		costNew.code = Act.costSearch("Cost Name", costNew.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return costNew.code
	}


	static def chargeDD(def chargeDetails, def row){
		Charge chargeNew = genChg.createChargeDD(chargeDetails, row)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.financialsMenu + "a_Charges"))
		WebUI.click(findTestObject(Const.chargeMenu + "a_Add"))

		Pop.newChargeForm(chargeNew)

		WebUI.click(findTestObject(Const.chargeMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		chargeNew.code = Act.chargeSearch("Charge Name", chargeNew.name)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
		return chargeNew.code
	}


	@Keyword
	static def customTaskField(String taskName){
		WebUI.click(findTestObject(Const.mainToolBar + 'settings'))
		WebUI.click(findTestObject(Const.settingsMenu + 'CustomFields'))
		WebUI.selectOptionByLabel(findTestObject(Const.CustomFieldScreen + 'select_CustomFieldType'), "Task", false)
		WebUI.click(findTestObject(Const.CustomFieldScreen + 'a_Add'))

		WebUI.check(findTestObject(Const.CustomFieldScreen + 'chk_AllSections'))
		WebUI.setText(findTestObject(Const.CustomFieldScreen + 'input__Name'), taskName)
		WebUI.check(findTestObject(Const.CustomFieldScreen + 'chk_Required'))
		WebUI.click(findTestObject(Const.CustomFieldScreen + 'button_SaveAndClose'))
		gAct.Wait(3)
		def rowCnt = WebD.getTableRowCount("//div[@id='63f30b25-1931-4053-98a5-eec594abfc22']/div[4]/table")

		if(rowCnt == 2){
			String taskVal = WebUI.getText(findTestObject(Const.CustomFieldScreen + 'customTaskName'))
			gAct.compareStringAndReport(taskVal, taskName)
		}else{
			String taskNme = WebUI.getText(findTestObject(Const.customFieldScreen + 'customTaskName'))
			if(taskNme == taskName){
				Rep.pass(taskNme + " matches as expected.")
			}else{
				for(int intCnt = 2; intCnt < rowCnt; intCnt ++){
					String extraTaskNme = WebUI.getText(findTestObject(Const.customFieldScreen + 'customTaskNameExtraRow', [('row'): intCnt]))
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
		Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "Projects")
		WebUI.click(findTestObject(Const.myProjects + 'spannerSettings'))
		WebUI.click(findTestObject(Const.configureViewMenu + 'a_Add New View'))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.createViewDialog + 'createViewName'), widgetViewName)
		WebUI.click(findTestObject(Const.createViewDialog + 'createViewSave'))
		gAct.Wait(3)
		Com.edgeSync()
		WebUI.click(findTestObject(Const.myProjects + 'spannerSettings'))
		Com.ieSync()
		WebUI.click(findTestObject(Const.configureViewMenu + 'a_Configure Current View'))
		WebUI.click(findTestObject(Const.configureViewMenu + 'plusSymbol'))
		gAct.Wait(1)
		Com.edgeSync()
		WebUI.click(findTestObject(Const.addWidget + 'a_Action Views'))
		WebUI.click(findTestObject(Const.addWidget + 'actionViewsButton', [('button'): widget]))
		String widgetName_new = WebUI.getText(findTestObject(Const.widgetView + 'widgetViewName'))

		gAct.compareStringAndReport(widgetName_new, widgetViewName)

		boolean widgetPresent = WebUI.verifyElementPresent(findTestObject(Const.widgetView + 'WidgetName',[('widget'): widgetFilter]), 10, FailureHandling.CONTINUE_ON_FAILURE)

		if(widgetPresent){
			Rep.pass("Widget has been created and added to the correct View")
		}else{
			Rep.fail("Widget has been not been created or added to the correct View")
			Rep.screenshot("Widget has been not been created or added to the correct View")
		}

		gAct.Wait(1)
		WebUI.click(findTestObject(Const.widgetView + 'widgetSave'))
		gAct.Wait(1)
		Obj.buttonClickDblSubGenSync(Const.widgetView + 'a_newWidgetConfig', widgetFilter, "widge", widget, "pos")
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.widgetConfig + 'DefaultFilterList'))
		WebUI.setText(findTestObject(Const.widgetConfig + 'DefaultFilterSearch'), "All Actions")
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.widgetConfig + 'DefaultFilterChoice', [('choice'): 'All Actions']))
		WebUI.click(findTestObject(Const.widgetConfig + 'button_Save'))
		gAct.Wait(1)
		String saveMessage =  WebUI.getText(findTestObject(Const.widgetConfig + 'widgetConfigSaveMessage'))

		if((GVars.browser == "MicrosoftEdge")||(GVars.browser == "Internet explorer")){
			gAct.findSubstringInStringAndReport(saveMessage, "Configuration saved successfully")
		}else{
			gAct.compareStringAndReport(saveMessage, "×\nConfiguration saved successfully")
		}

		WebUI.click(findTestObject(Const.widgetConfig + 'button_Close'))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def submitTimesheet(Timesheet timesheet){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		if(GVars.browser == "Internet explorer"){
			WebUI.click(findTestObject(Const.dashBoard + 'currentPageHeader'))
		}
		Com.firefoxSync()
		WebUI.click(findTestObject(Const.timesheet + 'TimesheetAddLine'))
		gAct.Wait(5)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.timesheet + 'button_Submit'))
		gAct.Wait(5)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		gAct.Wait(4)
		String statusText = WebUI.getText(findTestObject(Const.timesheet + 'txt_SubmitStatus'))
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		gAct.Wait(4)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def submitTimesheetWithCharge_SpecificUser(Timesheet timesheet, String Charge, Login login){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		if(GVars.browser == "Internet explorer"){
			WebUI.click(findTestObject(Const.dashBoard + 'currentPageHeader'))
		}

		WebUI.click(findTestObject(Const.timesheet + 'timesheetEntry_DropDown'))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.timesheet + 'input_TimesheetEntryUser'), login.name)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.timesheet + 'Select_TimesheetEntryUser', [('usr'): login.name]))
		gAct.Wait(2)

		Com.firefoxSync()
		WebUI.click(findTestObject(Const.timesheet + 'TimesheetAddLine'))
		gAct.Wait(5)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.timesheet + 'dropdown_Charge'))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.timesheet + 'input_Charge'), Charge)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.timesheet + 'select_Charge',[('charge'): Charge]))

		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.timesheet + 'button_Submit'))
		gAct.Wait(5)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		gAct.Wait(4)
		String statusText = WebUI.getText(findTestObject(Const.timesheet + 'txt_SubmitStatus'))
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		gAct.Wait(4)
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		//		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def submitTimesheet_SpecificUser(Timesheet timesheet, Login login){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		if(GVars.browser == "Internet explorer"){
			WebUI.click(findTestObject(Const.dashBoard + 'currentPageHeader'))
		}
		Com.firefoxSync()

		WebUI.click(findTestObject(Const.timesheet + 'timesheetEntry_DropDown'))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.timesheet + 'input_TimesheetEntryUser'), login.name)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.timesheet + 'Select_TimesheetEntryUser', [('usr'): login.name]))
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.timesheet + 'TimesheetAddLine'))
		gAct.Wait(5)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.timesheet + 'button_Submit'))
		gAct.Wait(5)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		gAct.Wait(4)
		String statusText = WebUI.getText(findTestObject(Const.timesheet + 'txt_SubmitStatus'))
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		gAct.Wait(4)
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		//		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}



	@Keyword
	static def saveTimesheet_SpecificUser(Timesheet timesheet, Login login){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		if(GVars.browser == "Internet explorer"){
			WebUI.click(findTestObject(Const.dashBoard + 'currentPageHeader'))
		}
		Com.firefoxSync()

		WebUI.click(findTestObject(Const.timesheet + 'timesheetEntry_DropDown'))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.timesheet + 'input_TimesheetEntryUser'), login.name)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.timesheet + 'Select_TimesheetEntryUser', [('usr'): login.name]))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.timesheet + 'TimesheetAddLine'))
		gAct.Wait(5)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def submitPreviousTimesheet(Timesheet timesheet, int backCnt){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		if(GVars.browser == "Internet explorer"){
			WebUI.click(findTestObject(Const.dashBoard + 'currentPageHeader'))
		}
		Com.firefoxSync()

		for(int intCnt = 0; intCnt < backCnt; intCnt ++){
			WebUI.click(findTestObject(Const.timesheet + 'timesheetNavigateBack'))
		}

		WebUI.click(findTestObject(Const.timesheet + 'TimesheetAddLine'))
		gAct.Wait(5)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.timesheet + 'button_Submit'))
		gAct.Wait(5)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		gAct.Wait(2)
		String statusText = WebUI.getText(findTestObject(Const.timesheet + 'txt_SubmitStatus'))
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def submitPreviousTimesheet_SpecificUser(Timesheet timesheet, int backCnt, Login login){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		if(GVars.browser == "Internet explorer"){
			WebUI.click(findTestObject(Const.dashBoard + 'currentPageHeader'))
		}
		Com.firefoxSync()

		WebUI.click(findTestObject(Const.timesheet + 'timesheetEntry_DropDown'))
		gAct.Wait(2)
		WebUI.setText(findTestObject(Const.timesheet + 'input_TimesheetEntryUser'), login.name)
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.timesheet + 'Select_TimesheetEntryUser', [('usr'): login.name]))
		gAct.Wait(2)

		for(int intCnt = 0; intCnt < backCnt; intCnt ++){
			WebUI.click(findTestObject(Const.timesheet + 'timesheetNavigateBack'))
		}
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.timesheet + 'TimesheetAddLine'))
		gAct.Wait(5)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.timesheet + 'button_Submit'))
		gAct.Wait(5)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		gAct.Wait(2)
		String statusText = WebUI.getText(findTestObject(Const.timesheet + 'txt_SubmitStatus'))
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		gAct.Wait(4)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def saveTimesheet(Timesheet timesheet){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		if(GVars.browser == "Internet explorer"){
			WebUI.click(findTestObject(Const.dashBoard + 'currentPageHeader'))
		}
		Com.firefoxSync()
		WebUI.click(findTestObject(Const.timesheet + 'TimesheetAddLine'))
		gAct.Wait(5)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def submitOldTimesheetAndApprove(Timesheet timesheet, int weekRemoved, String weekCommence){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		if(GVars.browser == "Internet explorer"){
			WebUI.click(findTestObject(Const.dashBoard + 'currentPageHeader'))
		}
		gAct.Wait(1)

		for(int intCnt = 0; intCnt < weekRemoved; intCnt ++){
			WebUI.click(findTestObject(Const.timesheet + 'timesheetNavigateBack'))
			gAct.Wait(1)
		}

		WebUI.click(findTestObject(Const.timesheet + 'TimesheetAddLine'))
		gAct.Wait(5)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.timesheet + 'button_Submit'))
		gAct.Wait(5)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		gAct.Wait(2)

		Act.verifyPopUpText("Time Submitted Successfully")
		/*		
		 String statusText = WebUI.getText(findTestObject(Const.timesheet + 'txt_SubmitStatus'))
		 if(GVars.browser == "MicrosoftEdge"){
		 gAct.findSubstringInStringAndReport(statusText, "Submitted")
		 }else{
		 gAct.compareStringAndReport(statusText, " Submitted")
		 }
		 */
		gAct.Wait(4)

		Obj.buttonClickSync(Const.dashBoard + "menuExpand")
		//		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")

		WebUI.click(findTestObject(Const.search + "search_DropDown"))

		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Timesheet Submissions"]))
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.columnPicker + "a_Pending Approval"))
		gAct.Wait(2)
		int rowNo = sVal.searchTableReturnRow(4, weekCommence)
		WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_Alt', [('row'): rowNo]))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.columnPicker + "a_Open_ActionView"))

		WebUI.click(findTestObject(Const.columnPicker + "btn_SubmittedForApproval"))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.columnPicker + "button_AcceptSubmission"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.columnPicker + "button_CloseSubmission"))
		gAct.Wait(3)
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		gAct.Wait(3)
	}



	@Keyword
	static def submitOldTimesheetAndApprove_SpecificUser(Timesheet timesheet, int weekRemoved, String weekCommence, Login login){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		if(GVars.browser == "Internet explorer"){
			WebUI.click(findTestObject(Const.dashBoard + 'currentPageHeader'))
		}
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.timesheet + 'timesheetEntry_DropDown'))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.timesheet + 'input_TimesheetEntryUser'), login.name)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.timesheet + 'Select_TimesheetEntryUser', [('usr'): login.name]))
		gAct.Wait(1)
		for(int intCnt = 0; intCnt < weekRemoved; intCnt ++){
			WebUI.click(findTestObject(Const.timesheet + 'timesheetNavigateBack'))
			gAct.Wait(1)
		}
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.timesheet + 'TimesheetAddLine'))
		gAct.Wait(5)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.timesheet + 'button_Submit'))
		gAct.Wait(5)
		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		gAct.Wait(2)
		String statusText = WebUI.getText(findTestObject(Const.timesheet + 'txt_SubmitStatus'))
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		gAct.Wait(4)
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		//		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")

		WebUI.click(findTestObject(Const.search + "search_DropDown"))

		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Timesheet Submissions"]))
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.columnPicker + "a_Pending Approval"))
		int rowNo = sVal.searchTableReturnRow(5, login.name)
		WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_Alt', [('row'): rowNo]))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.columnPicker + "a_Open_ActionView"))

		WebUI.click(findTestObject(Const.columnPicker + "btn_SubmittedForApproval"))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.columnPicker + "button_AcceptSubmission"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.columnPicker + "button_CloseSubmission"))
		gAct.Wait(4)
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		gAct.Wait(3)
	}


	@Keyword
	static def createExpense(Expense expense){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Expenses", Const.dashBoard + 'currentPageHeader', "Expenses")
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.claim + 'a_Add'))
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.expense + 'input_Date'))
		gVal.objectAttributeValue(Const.expense + 'input_Date', 'value', expense.date)
		gAct.Wait(1)

		String projectText = Obj.getAttributeSync(Const.expense + 'input_Project', 'value')
		if(projectText == ""){
			Obj.setEditSync(Const.expense + 'input_Project', expense.project)
			gAct.Wait(GVars.shortWait)
			//Obj.buttonClickSync("//a[@id='ui-active-menuitem']")
	}
		gAct.Wait(1)

		WebUI.selectOptionByValue(findTestObject(Const.expense + 'select_Expense'), expense.expenseType, false)
		gAct.Wait(1)

		if(expense.category != "DEFAULT"){
			WebUI.selectOptionByValue(findTestObject(Const.expense + 'select_Category'), expense.category, false)
			gAct.Wait(1)
		}

		WebUI.check(findTestObject(Const.expense + 'chk_Reimbursable'))
		WebUI.setText(findTestObject(Const.expense + 'input_Gross'), expense.gross)
		WebUI.click(findTestObject(Const.expense + 'input_Vat'))

		Com.edgeSync()
		gVal.objectAttributeValue(Const.expense + 'input_Net', 'value', expense.net)

		WebUI.check(findTestObject(Const.expense + 'chk_Chargeable'))

		WebUI.click(findTestObject(Const.expense + 'button_SaveAndClose'))

		gAct.Wait(3)

		WebUI.waitForElementNotVisible(findTestObject(Const.expense + 'a_ExpenseSavedSuccessfully'), 10, FailureHandling.OPTIONAL)

		gAct.Wait(2)
		WebUI.click(findTestObject(Const.expense + 'filter_DropDown'))
		WebUI.click(findTestObject(Const.expense + 'a_UnclaimedExpenses'))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createExpenseWithCurrency(ExpenseWithCurrency expense){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Expenses", Const.dashBoard + 'currentPageHeader', "Expenses")
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.claim + 'a_Add'))
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.expense + 'input_Date'))
		gVal.objectAttributeValue(Const.expense + 'input_Date', 'value', expense.date)
		gAct.Wait(1)

		String projectText = Obj.getAttributeSync(Const.expense + 'input_Project', 'value')
		if(projectText == ""){
			Obj.setEditSync(Const.expense + 'input_Project', expense.project)
			gAct.Wait(GVars.shortWait)
			//Obj.buttonClickSync("//a[@id='ui-active-menuitem']")
	}
		gAct.Wait(1)

		WebUI.selectOptionByValue(findTestObject(Const.expense + 'select_Expense'), expense.expenseType, false)
		gAct.Wait(1)

		if(expense.category != "DEFAULT"){
			WebUI.selectOptionByValue(findTestObject(Const.expense + 'select_Category'), expense.category, false)
			gAct.Wait(1)
		}

		if(expense.currency != "DEFAULT"){
			WebUI.selectOptionByValue(findTestObject(Const.expense + 'select_Currency'), expense.currency, false)
			gAct.Wait(1)
		}

		WebUI.check(findTestObject(Const.expense + 'chk_Reimbursable'))
		WebUI.setText(findTestObject(Const.expense + 'input_Gross'), expense.gross)
		WebUI.click(findTestObject(Const.expense + 'input_Vat'))

		Com.edgeSync()
		gVal.objectAttributeValue(Const.expense + 'input_Net', 'value', expense.net)

		WebUI.check(findTestObject(Const.expense + 'chk_Chargeable'))

		WebUI.click(findTestObject(Const.expense + 'button_SaveAndClose'))

		gAct.Wait(3)

		WebUI.waitForElementNotVisible(findTestObject(Const.expense + 'a_ExpenseSavedSuccessfully'), 10, FailureHandling.OPTIONAL)

		gAct.Wait(2)
		WebUI.click(findTestObject(Const.expense + 'filter_DropDown'))
		WebUI.click(findTestObject(Const.expense + 'a_UnclaimedExpenses'))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}



	@Keyword
	static def createMultiExpense(Expense expense, int count){
		int newGross, newNet
		int Gross = 10
		int Net = 10

		Nav.menuItemAndValidate(Const.dashBoard + "menu_Expenses", Const.dashBoard + 'currentPageHeader', "Expenses")
		gAct.Wait(1)

		for(int intCnt = 0; intCnt < count; intCnt ++){

			WebUI.click(findTestObject(Const.claim + 'a_Add'))
			gAct.Wait(2)

			WebUI.click(findTestObject(Const.expense + 'input_Date'))
			gVal.objectAttributeValue(Const.expense + 'input_Date', 'value', expense.date)
			gAct.Wait(1)

			WebUI.selectOptionByValue(findTestObject(Const.expense + 'select_Project'), expense.project, false)
			gAct.Wait(1)

			WebUI.selectOptionByValue(findTestObject(Const.expense + 'select_Expense'), expense.expenseType, false)
			gAct.Wait(1)

			if(expense.category != "DEFAULT"){
				WebUI.selectOptionByValue(findTestObject(Const.expense + 'select_Category'), expense.category, false)
				gAct.Wait(1)
			}

			WebUI.check(findTestObject(Const.expense + 'chk_Reimbursable'))

			newGross = Gross + intCnt
			String strGross = newGross.toString() + ".00"

			WebUI.setText(findTestObject(Const.expense + 'input_Gross'), strGross)
			WebUI.click(findTestObject(Const.expense + 'input_Vat'))

			newNet = Net + intCnt
			String strNet = newNet.toString() + ".00"

			Com.edgeSync()
			gVal.objectAttributeValue(Const.expense + 'input_Net', 'value', strNet)

			WebUI.check(findTestObject(Const.expense + 'chk_Chargeable'))

			WebUI.click(findTestObject(Const.expense + 'button_SaveAndClose'))

			gAct.Wait(3)

			WebUI.waitForElementNotVisible(findTestObject(Const.expense + 'a_ExpenseSavedSuccessfully'), 10, FailureHandling.OPTIONAL)

			WebUI.refresh()
			gAct.Wait(3)

		}

		gAct.Wait(2)
		WebUI.click(findTestObject(Const.expense + 'filter_DropDown'))
		WebUI.click(findTestObject(Const.expense + 'a_UnclaimedExpenses'))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createExpenseType(ExpenseType expenseType){
		String searchItem = "Expense Types"
		String searchVal = "Expense Type"
		Nav.selectSearchFilter(searchItem)

		String filterName = WebUI.getAttribute(findTestObject(Const.searchFilters + "input_SearchFilterName"), 'value')
		gAct.findSubstringInStringAndReport(filterName, searchVal)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "pageCount"), "10", false)
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), expenseType.name)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), expenseType.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		if(expenseType.currency != ""){
			WebUI.setText(findTestObject(Const.columnPicker + "input_CurrencyExp"), expenseType.currency)
		}

		WebUI.setText(findTestObject(Const.columnPicker + "input_MultplyExpenseCharge"), expenseType.multiplyExpChg)

		gAct.Wait(1)
		if(expenseType.Receipt == "true"){
			WebUI.check(findTestObject(Const.columnPicker + "chk_AllowReceipt"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}



	@Keyword
	static def createMitigationPlanItemType(MitigationPlanItemType mitigationPlanItemType){
		String searchItem = "Mitigation Plan Item Types"
		String searchVal = "Mitigation Plan Item Type"
		Nav.selectSearchFilter(searchItem)

		String filterName = WebUI.getAttribute(findTestObject(Const.searchFilters + "input_SearchFilterName"), 'value')
		gAct.findSubstringInStringAndReport(filterName, searchVal)
		gAct.Wait(1)

		boolean pageLengthVis = WebUI.verifyElementPresent(findTestObject(Const.columnPicker + "pageCount"), 2, FailureHandling.OPTIONAL)
		if(pageLengthVis){
			WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "pageCount"), "10", false)
			gAct.Wait(1)
		}

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), mitigationPlanItemType.name)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), mitigationPlanItemType.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createExpenseTypeWithNominalCode(ExpenseType expenseType, String nominal){
		String searchItem = "Expense Types"
		String searchVal = "Expense Type"
		Nav.selectSearchFilter(searchItem)

		String filterName = WebUI.getAttribute(findTestObject(Const.searchFilters + "input_SearchFilterName"), 'value')
		gAct.findSubstringInStringAndReport(filterName, searchVal)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "pageCount"), "10", false)
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), expenseType.name)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), expenseType.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		if(expenseType.currency != ""){
			WebUI.setText(findTestObject(Const.columnPicker + "input_CurrencyExp"), expenseType.currency)
		}

		WebUI.setText(findTestObject(Const.columnPicker + "input_MultplyExpenseCharge"), expenseType.multiplyExpChg)

		gAct.Wait(1)
		if(expenseType.Receipt == "true"){
			WebUI.check(findTestObject(Const.columnPicker + "chk_AllowReceipt"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "tab_Defaults"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_NominalCode"), nominal)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createActionType(ActionType actionType){
		String searchItem = "Action Types"
		String searchVal = "Action Type"
		Nav.selectSearchFilter(searchItem)

		String filterName = WebUI.getAttribute(findTestObject(Const.searchFilters + "input_SearchFilterName"), 'value')
		gAct.findSubstringInStringAndReport(filterName, searchVal)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "pageCount"), "10", false)

		Rep.nextTestStep("Click On [+ Add] ")
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), actionType.name)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), actionType.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createCategory(Category category){
		Nav.selectSearchFilter("Categories")

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(1)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), category.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), category.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)

		if(category.active == "true"){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}else{
			WebUI.uncheck(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createCategoryDefault(Category category){
		Nav.selectSearchFilter("Categories")

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(1)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), category.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), category.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)

		if(category.active == "true"){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}else{
			WebUI.uncheck(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.check(findTestObject(Const.columnPicker + "chk_DefaultCategory"))

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(1)

		WebUI.acceptAlert()

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createContract(Contract contract){
		String searchItem = "Contract"
		String searchVal = "Contract"

		Nav.selectSearchFilter(searchItem)

		String filterName = WebUI.getAttribute(findTestObject(Const.searchFilters + "input_SearchFilterName"), 'value')
		gAct.findSubstringInStringAndReport(filterName, searchVal)
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		WebUI.selectOptionByLabel(findTestObject(Const.addContract + "select_Status"), contract.status, false)

		if(contract.status != "Draft"){
			WebUI.acceptAlert()
			gAct.Wait(1)
			WebUI.switchToDefaultContent()
		}

		WebUI.setText(findTestObject(Const.addContract + "input_Project"), contract.project)
		WebUI.setText(findTestObject(Const.addContract + "input_Name"), contract.name)
		gAct.Wait(1)
		if(contract.status == "Draft"){
			WebUI.selectOptionByLabel(findTestObject(Const.addContract + "select_TimeUnit"), contract.billTimeIn, false)
			gAct.Wait(1)
		}
		WebUI.setText(findTestObject(Const.addContract + "input_DayLength"), contract.dayLength)
		WebUI.click(findTestObject(Const.addContract + "button_Save and Close"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createForecast(Forecast forecast){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))

		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Forecasts"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_AllForecasts"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.forecasts + 'projectName'), forecast.project)

		WebUI.setText(findTestObject(Const.forecasts + 'forecastName'), forecast.name)
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.forecasts + 'BenefitsTab'))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + 'btn_AddBenefitLine'))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + 'input_BenefitName'), forecast.benefitName)
		WebUI.setText(findTestObject(Const.columnPicker + "input_BenefitType"), forecast.benefitType)
		WebUI.click(findTestObject(Const.forecasts + 'BenefitsTab'))
		WebUI.click(findTestObject(Const.columnPicker + 'btn_AddBenefit'))

		WebUI.setText(findTestObject(Const.columnPicker + 'input_BenefitDate'), forecast.date)

		for(int intCnt = 1; intCnt < 7; intCnt ++){
			String dateVal = WebUI.getText(findTestObject(Const.columnPicker + 'to_BenefitHeaderDates',[('col'): intCnt]))
			String[] newDateVal = dateVal.split('/')
			String revDate = newDateVal[2] + newDateVal[1] + newDateVal[0]

			gAct.Wait(1)
			WebUI.click(findTestObject(Const.columnPicker + 'input_BenefitCellOuter', [('cell'): intCnt + 7]))

			WebUI.setText(findTestObject(Const.columnPicker + 'input_BenefitsCell', [('cell'): revDate]), "5")
		}

		gAct.Wait(1)
		Obj.buttonClickSync(Const.forecasts + 'button_Save and Close')


		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createForecastAndApprove(Forecast forecast){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))

		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Forecasts"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_AllForecasts"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.forecasts + 'projectName'), forecast.project)
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.forecasts + 'forecastName'), forecast.name)
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.forecasts + 'BenefitsTab'))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + 'btn_AddBenefitLine'))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + 'input_BenefitName'), forecast.benefitName)
		WebUI.setText(findTestObject(Const.columnPicker + "input_BenefitType"), forecast.benefitType)
		WebUI.click(findTestObject(Const.forecasts + 'BenefitsTab'))
		WebUI.click(findTestObject(Const.columnPicker + 'btn_AddBenefit'))

		WebUI.setText(findTestObject(Const.columnPicker + 'input_BenefitDate'), forecast.date)

		for(int intCnt = 1; intCnt < 7; intCnt ++){
			String dateVal = WebUI.getText(findTestObject(Const.columnPicker + 'to_BenefitHeaderDates',[('col'): intCnt]))
			String[] newDateVal = dateVal.split('/')
			String revDate = newDateVal[2] + newDateVal[1] + newDateVal[0]

			gAct.Wait(1)
			WebUI.click(findTestObject(Const.columnPicker + 'input_BenefitCellOuter', [('cell'): intCnt + 7]))

			WebUI.setText(findTestObject(Const.columnPicker + 'input_BenefitsCell', [('cell'): revDate]), "5")
		}

		gAct.Wait(1)
		WebUI.click(findTestObject(Const.forecasts + 'button_Save and Close'))

		gAct.Wait(3)

		WebUI.click(findTestObject(Const.columnPicker + "a_New Filter"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))

		gAct.Wait(1)
		int rowNo = sVal.searchTableReturnRow(2, forecast.name)
		WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_Alt', [('row'): rowNo]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_InlineMenuPositionSelection", [('pos'): 2]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "btn_ApproveForecast"))
		gAct.Wait(1)
		WebUI.refresh()
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createRisk(Risk risk){
		String medium = "background-color: orange; color: black;"
		String high = "background-color: red; color: black;"


		Nav.selectSearchFilter("Risks")

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(4)

		WebUI.setText(findTestObject(Const.risks + 'input_Title'), risk.title)

		String projectText = WebUI.getAttribute(findTestObject(Const.risks + 'input_Project'), 'value')
		if(projectText == ""){
			WebUI.setText(findTestObject(Const.risks + 'input_Project'), risk.project)
			WebUI.click(findTestObject(Const.risks + 'input_Title'))
		}

		String ownerText = WebUI.getAttribute(findTestObject(Const.risks + 'input_Owner'), 'value')
		if(ownerText == ""){
			WebUI.setText(findTestObject(Const.risks + 'input_Owner'), risk.owner)
			WebUI.click(findTestObject(Const.risks + 'input_Title'))
		}
		gAct.Wait(1)

		WebUI.verifyOptionSelectedByLabel(findTestObject(Const.risks + 'select_Status'), risk.status, false, 5)

		String dateRaised = WebUI.getAttribute(findTestObject(Const.risks + 'input_DateIdentified'), 'value')
		gAct.compareStringAndReport(dateRaised, risk.dateIdentified)

		WebUI.setText(findTestObject(Const.risks + 'input_ImpactDate'), risk.impactDate)

		WebUI.selectOptionByLabel(findTestObject(Const.risks + 'select_PublishTo'), risk.publishTo, false)
		WebUI.click(findTestObject(Const.risks + 'input_Title'))

		WebUI.selectOptionByLabel(findTestObject(Const.risks + 'select_Probability'), risk.probability, false)
		WebUI.click(findTestObject(Const.risks + 'input_Title'))
		String colourNewTask = WebUI.getAttribute(findTestObject(Const.risks + 'select_Probability'), 'style')
		gAct.compareStringAndReport(colourNewTask, high)

		WebUI.selectOptionByLabel(findTestObject(Const.risks + 'select_Impact'), risk.impact, false)
		WebUI.click(findTestObject(Const.risks + 'input_Title'))
		colourNewTask = WebUI.getAttribute(findTestObject(Const.risks + 'select_Impact'), 'style')
		gAct.compareStringAndReport(colourNewTask, medium)

		WebUI.selectOptionByLabel(findTestObject(Const.risks + 'select_Severity'), risk.severity, false)
		WebUI.click(findTestObject(Const.risks + 'input_Title'))
		colourNewTask = WebUI.getAttribute(findTestObject(Const.risks + 'select_Severity'), 'style')
		gAct.compareStringAndReport(colourNewTask, high)

		WebUI.setText(findTestObject(Const.risks + 'input_ResolutionDate'), risk.resolutionDate)

		String riskValue = WebUI.getText(findTestObject(Const.risks + 'riskValue'))
		gAct.findSubstringInStringAndReport(riskValue, "RED")
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.risks + 'button_Save'))
		gAct.Wait(1)

		risk.code = WebUI.getAttribute(findTestObject(Const.risks + 'input_Code'), 'value')

		WebUI.click(findTestObject(Const.risks + 'button_SaveAndClose'))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createIssue(Issue issue){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Issues"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(4)

		WebUI.setText(findTestObject(Const.issues + 'input_Title'), issue.title)
		WebUI.setText(findTestObject(Const.issues + 'input_Project'), issue.project)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "td_General"))
		gAct.Wait(1)

		String ownerText = WebUI.getAttribute(findTestObject(Const.issues + 'input_Owner'), 'value')
		if(ownerText == ""){
			WebUI.setText(findTestObject(Const.issues + 'input_Owner'), issue.owner)
			WebUI.click(findTestObject(Const.issues + 'input_Title'))
		}

		WebUI.verifyOptionSelectedByLabel(findTestObject(Const.issues + 'select_Status'), issue.status, false, 5)

		WebUI.selectOptionByLabel(findTestObject(Const.issues + 'select_Impact'), issue.impact, false)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "td_General"))
		gAct.Wait(1)

		WebUI.selectOptionByLabel(findTestObject(Const.issues + 'select_ResolutionProgress'), issue.resolutionProgress, false)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "td_General"))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.issues + 'button_Save'))
		gAct.Wait(1)

		issue.code = WebUI.getAttribute(findTestObject(Const.issues + 'input_Code'), 'value')

		WebUI.click(findTestObject(Const.issues + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createActionView(ActionView actionView){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Action Views"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_ActionName"), actionView.name)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.setText(findTestObject(Const.columnPicker + "input_Description"), actionView.description)

		WebUI.setText(findTestObject(Const.columnPicker + "input_DefaultActionType"), actionView.defaultActionType)
		WebUI.click(findTestObject(Const.columnPicker + "input_ActionName"))

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createActivity(Activity activity){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Activities"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), activity.name)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), activity.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createBillingPriceList(BillingPriceList billingPriceList){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Billing Price List"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), billingPriceList.name)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), billingPriceList.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		if(billingPriceList.currency != "DEFAULT"){
			WebUI.selectOptionByValue(findTestObject(Const.expense + 'select_Currency'), billingPriceList.currency, false)
			gAct.Wait(1)
		}
		//		WebUI.setText(findTestObject(Const.columnPicker + "input_Currency"), billingPriceList.currency)
		WebUI.setText(findTestObject(Const.columnPicker + "input_UnitPrice"), billingPriceList.unitPrice)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createBenefitType(BenefitType benefitType){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Benefit Types"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), benefitType.name)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), benefitType.code)
		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "select_Type"), benefitType.type, false)
		WebUI.click(findTestObject(Const.columnPicker + "input_Name"))

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createBenefitTypeInactive(BenefitType benefitType){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Benefit Types"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), benefitType.name)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), benefitType.code)
		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(active){
			WebUI.uncheck(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "select_Type"), benefitType.type, false)
		WebUI.click(findTestObject(Const.columnPicker + "input_Name"))

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}



	@Keyword
	static def createClientGroup(ClientGroup clientGroup){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Customer Groups"]))
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), clientGroup.name)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), clientGroup.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createClient(Client client){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Customers"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Default Client Filter"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(2)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), client.name)
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), client.code)
		gAct.Wait(1)
		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createClientWithClientGroup(Client client, String clientGroup){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Customers"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Default Client Filter"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), client.name)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), client.code)
		gAct.Wait(1)
		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "select_ClientGroup"), clientGroup, false)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}



	@Keyword
	static def createContact(Contact contact){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Contacts"]))
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_firstName"), contact.firstName)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Surname"), contact.surname)
		gAct.Wait(3)
		WebUI.clearText(findTestObject(Const.columnPicker + "input_Customer"))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Customer"), contact.customer)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "input_firstName"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_Department"), contact.department)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Position"), contact.position)
		WebUI.setText(findTestObject(Const.columnPicker + "input_telephone"), contact.telephone)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def addContactToClient(Contact contact, Client client){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Customers"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_New Filter"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(1)
		int rowNo = sVal.searchTableReturnRow(3, client.name)
		WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_Alt', [('row'): rowNo]))
		gAct.Wait(1)

		Obj.selectInlineOption(6)
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "Tab_Contacts"))
		WebUI.click(findTestObject(Const.columnPicker + "btnAddContact"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_ContactFirstname"), contact.firstName)
		WebUI.setText(findTestObject(Const.columnPicker + "input_ContactSurname"), contact.surname)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_ActiveContact"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_ActiveContact"))
		}

		String expCust = WebUI.getText(findTestObject(Const.columnPicker + "customerName"))
		gAct.compareStringAndReport(expCust, client.name)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndCloseAddContact"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createDeliverableType(DeliverableType deliverableType){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Deliverable Types"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_DefaultDeliverableTypeFilter"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + 'input_DelTypeName'), deliverableType.name)
		WebUI.setText(findTestObject(Const.columnPicker + 'input_Code'), deliverableType.code)
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createPost(String deliverableName, String postText, String userName){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Deliverables"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_New Filter"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(1)
		int rowNo = sVal.searchTableReturnRow(5, deliverableName)
		WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_Alt', [('row'): rowNo]))
		gAct.Wait(1)

		WebD.clickElement("//div[contains(@class,'btn-group btn-group-xs shiny open')]//li[4]//a[1]")
		gAct.Wait(1)

		WebD.switchToActive()
		WebUI.click(findTestObject(Const.addPost + "textarea_Post"))
		WebD.setEditText("//textarea[@id='Text']", postText)
		WebUI.switchToDefaultContent()
		WebUI.click(findTestObject(Const.addPost + "input_Resource"))

		WebUI.click(findTestObject(Const.addPost + "select_Resources", [('name'): userName]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.addPost + "button_Add"))

		boolean elementVisible = WebUI.verifyElementPresent(findTestObject(Const.timesheetSaved + 'TimeSavedSuccessfully'), GVars.longWait, FailureHandling.OPTIONAL)
		if (elementVisible){
			WebUI.waitForElementNotPresent(findTestObject(Const.timesheetSaved + 'TimeSavedSuccessfully'), 10, FailureHandling.OPTIONAL)
		}

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)

	}


	@Keyword
	static def createLocation(Location location){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Locations"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_DefaultLocationFilter"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), location.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), location.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createProject(Project project){   //use template
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Projects"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_ActiveProjects"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.columnPicker + "a_AddProject"))
		WebUI.click(findTestObject(Const.columnPicker + 'template_Radio'))

		WebUI.click(findTestObject(Const.projectWizard + 'projectTypeDropDown'))
		WebUI.click(findTestObject(Const.projectWizard + 'selectProjectType', [('type'): project.projectType]))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.projectWizard + 'a_Select'))
		WebUI.click(findTestObject(Const.projectWizard + 'selectTemplate', [('template'): project.template]))

		WebUI.click(findTestObject(Const.projectWizard + 'chk_CopyDeliverables_Wizard'))
		WebUI.click(findTestObject(Const.projectWizard + 'chk_CopyForecast_Wizard'))
		WebUI.click(findTestObject(Const.projectWizard + 'chk_CopyRisks_Wizard'))
		WebUI.click(findTestObject(Const.projectWizard + 'chk_CopyTasks_Wizard'))

		//		WebUI.setText(findTestObject(Const.projectWizard + 'input_ExecutionStartDate'), project.startDate)
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.projectWizard + 'button_Next'))

		Pop.addProject_Wizard_page2(project)

		WebUI.click(findTestObject(Const.projectWizard + "button_SaveAndClose"))
		gAct.Wait(3)

		WebUI.click(findTestObject(Const.columnPicker + "a_New Filter"))
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		gAct.Wait(1)

		int rowNo = sVal.searchTableReturnRow(4, project.name)
		project.code = WebUI.getText(findTestObject(Const.columnPicker + "searchTable", [('row'): rowNo, ('col'): 3]))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createProjectCopyTasks(Project project){   //use template
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Projects"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_ActiveProjects"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.columnPicker + "a_AddProject"))
		WebUI.click(findTestObject(Const.columnPicker + 'template_Radio'))

		WebUI.click(findTestObject(Const.projectWizard + 'projectTypeDropDown'))
		WebUI.click(findTestObject(Const.projectWizard + 'selectProjectType', [('type'): project.projectType]))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.projectWizard + 'a_Select'))
		WebUI.click(findTestObject(Const.projectWizard + 'selectTemplate', [('template'): project.template]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.projectWizard + 'chk_CopyDeliverables_Wizard'))
		WebUI.click(findTestObject(Const.projectWizard + 'chk_CopyForecast_Wizard'))
		WebUI.click(findTestObject(Const.projectWizard + 'chk_CopyRisks_Wizard'))

		WebUI.setText(findTestObject(Const.projectWizard + 'input_ExecutionStartDate'), project.startDate)
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.projectWizard + 'button_Next'))

		Pop.addProject_Wizard_page2(project)

		WebUI.click(findTestObject(Const.projectWizard + "button_SaveAndClose"))
		gAct.Wait(3)


		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(1)

		int rowNo = sVal.searchTableReturnRow(4, project.name)
		project.code = WebUI.getText(findTestObject(Const.columnPicker + "searchTable", [('row'): rowNo, ('col'): 3]))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def createEmptyProject(Project project){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Projects"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_AddProject"))

		WebUI.click(findTestObject(Const.projectWizard + 'projectTypeDropDown'))
		WebUI.click(findTestObject(Const.projectWizard + 'selectProjectType', [('type'): project.projectType]))
		WebUI.click(findTestObject(Const.projectWizard + 'radioEmptyProject'))
		WebUI.click(findTestObject(Const.projectWizard + 'button_Next'))

		Pop.addProject_Wizard_page2(project)

		WebUI.click(findTestObject(Const.projectWizard + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createResource(Resource resource){
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.busEntMenu + "a_Resources"))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.roleMenu + "a_Add"))
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.resourceMenu + "radio_AddEmptyResource"))
		WebUI.click(findTestObject(Const.resourceMenu + "button_OK"))
		gAct.Wait(2)
		Pop.newResourceForm(resource)

		WebUI.click(findTestObject(Const.resourceMenu + "button_Save"))
		gAct.Wait(2)
		resource.code = WebUI.getAttribute(findTestObject(Const.resourceMenu + "resourceCode"), 'value')

		WebUI.click(findTestObject(Const.resourceMenu + "button_SaveAndClose"))
		gAct.Wait(3)

		boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.resourceMenu + "saveResourceError"), 5, FailureHandling.CONTINUE_ON_FAILURE)

		if(!saveError){
			Rep.fail("Resource : " + resource.name + " was not created.")
			Rep.screenshot(resource.name )
			WebUI.click(findTestObject(Const.resourceMenu + "button_Close"))
		}

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)

		return resource.code
	}


	static def createResourceWithFinancials(Resource resource){
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.busEntMenu + "a_Resources"))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.roleMenu + "a_Add"))
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.resourceMenu + "radio_AddEmptyResource"))
		WebUI.click(findTestObject(Const.resourceMenu + "button_OK"))
		gAct.Wait(2)
		Pop.newResourceFormWithFinancials(resource)

		WebUI.click(findTestObject(Const.resourceMenu + "generalTab"))
		WebUI.click(findTestObject(Const.resourceMenu + "button_Save"))
		gAct.Wait(2)
		resource.code = WebUI.getAttribute(findTestObject(Const.resourceMenu + "resourceCode"), 'value')

		WebUI.click(findTestObject(Const.resourceMenu + "button_SaveAndClose"))
		gAct.Wait(3)

		boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.resourceMenu + "saveResourceError"), 5, FailureHandling.CONTINUE_ON_FAILURE)

		if(!saveError){
			Rep.fail("Resource : " + resource.name + " was not created.")
			Rep.screenshot(resource.name )
			WebUI.click(findTestObject(Const.resourceMenu + "button_Close"))
		}

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)

		return resource.code
	}

	@Keyword
	static def addSkillsToResource(Resource resource, String skillCode){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Resources"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_New Filter"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "previousPage"))
		int rowNo = sVal.searchTableReturnRow(3, resource.name)
		WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_Alt', [('row'): rowNo]))
		gAct.Wait(1)
		Obj.selectInlineOption(1)
		WebUI.click(findTestObject(Const.resourceMenu + "skillsTab"))
		WebUI.click(findTestObject(Const.resourceMenu + "btn_AddSkills"))
		WebUI.setText(findTestObject(Const.resourceMenu + "input_SkillCode"), skillCode)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.resourceMenu + "btnSearch"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.resourceMenu + "chkSelect"))
		WebUI.click(findTestObject(Const.resourceMenu + "btnSelect"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.resourceMenu + "button_SaveAndClose"))
		gAct.Wait(3)

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}

	@Keyword
	static def addSecurityGroupToResource(Resource resource, String securityCode){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Resources"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_New Filter"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "previousPage"))
		int rowNo = sVal.searchTableReturnRow(3, resource.name)
		WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_Alt', [('row'): rowNo]))
		gAct.Wait(1)
		Obj.selectInlineOption(1)
		WebUI.click(findTestObject(Const.resourceMenu + "SecurityGroupsTab"))

		WebUI.click(findTestObject(Const.resourceMenu + "btn_AddSecurityGroup"))
		WebUI.setText(findTestObject(Const.resourceMenu + "input_SkillCode"), securityCode)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.resourceMenu + "btnSearch"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.resourceMenu + "chkSelect"))
		WebUI.click(findTestObject(Const.resourceMenu + "btnSelect"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.resourceMenu + "button_SaveAndClose"))
		gAct.Wait(3)

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}



	static def createResourceWithDefaults(Resource resource, String project, String activity){
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.busEntMenu + "a_Resources"))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.roleMenu + "a_Add"))
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.resourceMenu + "radio_AddEmptyResource"))
		WebUI.click(findTestObject(Const.resourceMenu + "button_OK"))
		gAct.Wait(2)
		Pop.newResourceForm(resource)

		WebUI.click(findTestObject(Const.resourceMenu + "button_Save"))
		gAct.Wait(2)
		resource.code = WebUI.getAttribute(findTestObject(Const.resourceMenu + "resourceCode"), 'value')

		WebUI.click(findTestObject(Const.resourceMenu + "defaultsTab"))

		WebUI.setText(findTestObject(Const.resourceMenu + "defaultProject"), project)
		WebUI.setText(findTestObject(Const.resourceMenu + "defaultActivity"), activity)

		WebUI.click(findTestObject(Const.resourceMenu + "button_SaveAndClose"))
		gAct.Wait(3)

		boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.resourceMenu + "saveResourceError"), 5, FailureHandling.CONTINUE_ON_FAILURE)

		if(!saveError){
			Rep.fail("Resource : " + resource.name + " was not created.")
			Rep.screenshot(resource.name )
			WebUI.click(findTestObject(Const.resourceMenu + "button_Close"))
		}

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)

		return resource.code
	}


	static def createRole(Role role){
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Roles"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.roleMenu + "a_Add"))
		gAct.Wait(1)
		Pop.newRolesForm(role)
		WebUI.click(findTestObject(Const.roleMenu + "button_SaveAndClose"))
		gAct.Wait(3)

		boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.roleMenu + "saveRoleError"), 5, FailureHandling.CONTINUE_ON_FAILURE)

		if(!saveError){
			Rep.fail("Role : " + role.name + " was not created.")
			Rep.screenshot(role.name )
			WebUI.click(findTestObject(Const.roleMenu + "button_Close"))
		}
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createCurrency(Currency currency){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Currencies"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), currency.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), currency.code)

		boolean activeState = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)

		if(currency.active == "false"){
			if(activeState){
				WebUI.uncheck(findTestObject(Const.columnPicker + "chk_Active"))
			}
		}else{
			if(!activeState){
				WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
			}
		}

		WebUI.setText(findTestObject(Const.columnPicker + "input_Symbol"), currency.symbol)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createCharge(Charge charge){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Charges"]))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.columnPicker + "a_New Filter"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(2)
		Obj.setEditSync(Const.columnPicker + "input_Name", charge.name)
		gAct.Wait(1)
		Obj.setEditSync(Const.columnPicker + "input_Code", charge.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}
		gAct.Wait(1)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Currency"), charge.currency)


		WebUI.setText(findTestObject(Const.columnPicker + "input_Rate"), charge.rate)

		WebUI.click(findTestObject(Const.issues + 'button_Save'))
		gAct.Wait(1)

		charge.code = WebUI.getAttribute(findTestObject(Const.issues + 'input_Code'), 'value')

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createCost(Cost cost){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Costs"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), cost.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), cost.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.clearText(findTestObject(Const.columnPicker + "input_Currency"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_Currency"), cost.currency)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Rate"), cost.rate)

		WebUI.check(findTestObject(Const.columnPicker + "chk_Standard"))
		WebUI.check(findTestObject(Const.columnPicker + "chk_Overtime"))

		WebUI.click(findTestObject(Const.issues + 'button_Save'))
		gAct.Wait(1)

		cost.code = WebUI.getAttribute(findTestObject(Const.issues + 'input_Code'), 'value')

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createNominalCode(NominalCode nominalCode){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Nominal Codes"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), nominalCode.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), nominalCode.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createNominalCodeDefaults(NominalCode nominalCode){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Nominal Codes"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), nominalCode.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), nominalCode.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.check(findTestObject(Const.columnPicker + "chk_DefaultExpenseNominalCode"))

		WebUI.check(findTestObject(Const.columnPicker + "chk_DefaultTimesheetNominalCode"))

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)

		WebUI.acceptAlert()
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createSkill(Skill skill){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Skills"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(2)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), skill.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), skill.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.setText(findTestObject(Const.columnPicker + "input_MinLevel"), skill.minLevel)
		WebUI.setText(findTestObject(Const.columnPicker + "input_MaxLevel"), skill.maxLevel)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createLogin(Login login){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Logins"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(5)
		WebUI.click(findTestObject(Const.resourceMenu + 'radio_AddTemplateResource'))

		WebUI.click(findTestObject(Const.resourceMenu + 'select_Template'))

		WebUI.selectOptionByLabel(findTestObject(Const.resourceMenu + 'select_Template'), "ADMINISTRATION | Administration" , false)
		WebUI.click(findTestObject(Const.resourceMenu + 'select_Template'))

		WebUI.click(findTestObject(Const.resourceMenu + 'button_OK'))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_LoginName"), login.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), login.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.setText(findTestObject(Const.columnPicker + "input_UserID"), login.userId)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Password"), login.password)
		WebUI.setText(findTestObject(Const.columnPicker + "input_ConfirmPassword"), login.password)
		WebUI.check(findTestObject(Const.columnPicker + "chk_PasswordNeverExpires"))
		WebUI.uncheck(findTestObject(Const.columnPicker + "chk_PasswordChanged"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_OwnResource"), login.ownResource)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createLoginWithLoginProfile(Login login, String loginProfile){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Logins"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(5)
		WebUI.click(findTestObject(Const.resourceMenu + 'radio_AddTemplateResource'))

		WebUI.click(findTestObject(Const.resourceMenu + 'select_Template'))

		WebUI.selectOptionByLabel(findTestObject(Const.resourceMenu + 'select_Template'), "ADMINISTRATION | Administration" , false)
		WebUI.click(findTestObject(Const.resourceMenu + 'select_Template'))

		WebUI.click(findTestObject(Const.resourceMenu + 'button_OK'))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_LoginName"), login.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), login.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.setText(findTestObject(Const.columnPicker + "input_UserID"), login.userId)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Password"), login.password)
		WebUI.setText(findTestObject(Const.columnPicker + "input_ConfirmPassword"), login.password)
		WebUI.check(findTestObject(Const.columnPicker + "chk_PasswordNeverExpires"))
		WebUI.uncheck(findTestObject(Const.columnPicker + "chk_PasswordChanged"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_LoginProfile"), loginProfile)

		WebUI.setText(findTestObject(Const.columnPicker + "input_OwnResource"), login.ownResource)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createLoginTemplate(Login login){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Logins"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(5)
		WebUI.click(findTestObject(Const.resourceMenu + 'radio_AddTemplateResource'))

		WebUI.click(findTestObject(Const.resourceMenu + 'select_Template'))

		WebUI.selectOptionByLabel(findTestObject(Const.resourceMenu + 'select_Template'), "ADMINISTRATION | Administration" , false)
		WebUI.click(findTestObject(Const.resourceMenu + 'select_Template'))

		WebUI.click(findTestObject(Const.resourceMenu + 'button_OK'))
		gAct.Wait(1)

		WebUI.check(findTestObject(Const.columnPicker + "chk_Template"))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_LoginName"), login.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), login.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.setText(findTestObject(Const.columnPicker + "input_OwnResource"), login.ownResource)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createLoginGroup(LoginGroup loginGroup){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Login Groups"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.click(findTestObject(Const.columnPicker + "td_General"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), loginGroup.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), loginGroup.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "td_Action Views"))
		gAct.Wait(1)

		Rep.nextTestStep("Click on [Add]")
		WebUI.click(findTestObject(Const.columnPicker + "btn_AddActionViews"))
		gAct.Wait(1)

		WebUI.check(findTestObject(Const.columnPicker + "a_All Actions"))

		WebUI.click(findTestObject(Const.columnPicker + "td_General"))
		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "select_DefaultActionView"), loginGroup.loginGrp, false)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createLoginProfile(LoginProfile loginProfile){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Login Profiles"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		WebUI.click(findTestObject(Const.columnPicker + "td_General"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), loginProfile.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), loginProfile.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createSecurityGroup(SecurityGroup securityGroup){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Security Groups"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		WebUI.click(findTestObject(Const.columnPicker + "td_General"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), securityGroup.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), securityGroup.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createClaim(Claim claim){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Claims"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.claim + "input_CategoryCodeFilter"), claim.category)
		WebUI.click(findTestObject(Const.claim + "input_SearchFilter"))

		WebUI.check(findTestObject(Const.claim +'input_Default Category_chkSelect'))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.claim +'button_Select'))

		String resourceName = WebUI.getAttribute(findTestObject(Const.claim + 'input_Resource'), 'value')
		if(resourceName != claim.resource){
			WebUI.setText(findTestObject(Const.claim + 'input_Resource'), claim.resource)
		}

		String date = WebUI.getText(findTestObject(Const.claim + 'date'))
		gAct.compareStringAndReport(date, claim.date)

		WebUI.setText(findTestObject(Const.claim + 'textarea_Notes'), claim.notes)

		WebUI.click(findTestObject(Const.claim + 'dateFrom'))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.datePicker + 'button_Today'))

		def today = new Date()
		def todayDate = today.format('MMM')
		String nextMonth = Com.getNextMonth()
		String[] nextMnthYear = nextMonth.split("/")
		WebUI.click(findTestObject(Const.datePicker + 'button_Close'))

		WebUI.click(findTestObject(Const.claim + 'dateTo'))
		gAct.Wait(1)
		Com.chromeSync()
		WebUI.selectOptionByLabel(findTestObject(Const.datePicker + 'select_Month_To'), nextMnthYear[0], false)
		WebUI.selectOptionByLabel(findTestObject(Const.datePicker + 'select_Year_To'), nextMnthYear[1], false)
		WebUI.click(findTestObject(Const.datePicker + "a_Date_To", [('row'): 3, ('col'): 1]))
		gAct.Wait(1)


		Com.edgeSync()
		WebUI.check(findTestObject(Const.claim +'chkSelectExpense', [('row'): 1]))


		WebUI.click(findTestObject(Const.claim + "button_Save"))
		gAct.Wait(1)

		WebUI.acceptAlert()
		gAct.Wait(1)
		WebUI.switchToWindowIndex(1)
		WebUI.closeWindowIndex(1)
		gAct.Wait(1)
		WebUI.switchToDefaultContent()
		WebUI.click(findTestObject(Const.columnPicker + 'button_Close'))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}



	static def acceptClaim(Claim claim, String expenseVal, String claimNewNotes){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Claims"]))
		gAct.Wait(1)

		Rep.nextTestStep("Click onto [Inline menu] of Claim")
		int rowNo = sVal.searchTableReturnRow(7, "£" + expenseVal)
		WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_Alt', [('row'): rowNo]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_ViewInline", [('pos'): 1]))
		gAct.Wait(1)

		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "select_ClaimAcces"), "Accepted", false)

		WebUI.setText(findTestObject(Const.columnPicker + "AcceptClaimNotes"), claimNewNotes)

		WebD.clickElement("//button[@class='btn btn-primary'][contains(text(),'OK')]")
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createLock(Lock lock){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Locks"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.AddLocks + "input_StartDate"), lock.startDate)
		WebUI.setText(findTestObject(Const.AddLocks + "input_EndDate"), lock.endDate)
		WebUI.check(findTestObject(Const.AddLocks + "chk_IncludeTimesheets"))

		WebUI.click(findTestObject(Const.AddLocks + "button_EstimateLockCounts"))

		String timesheetsIncluded = WebUI.getText(findTestObject(Const.AddLocks + "TimesheetCount"))
		int timesheetInt = timesheetsIncluded as Integer

		if(timesheetInt >= 5){
			Rep.pass("Timesheets included as expected")
		}else{
			Rep.fail("Timesheets not included")
		}
		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)

	}


	static def createInvoice(Invoice invoice){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Invoices"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(1)
		WebD.switchToiFrameAndEditObject("//div[@id='ui-id-1']/iframe", "//input[@id='ctl00_phFormContent_ucInvoiceDate_txtDate']", invoice.date)
		gAct.Wait(1)
		WebUI.switchToDefaultContent()

		WebUI.setText(findTestObject(Const.columnPicker + "input_Project"), invoice.project)
		gAct.Wait(3)

		WebUI.click(findTestObject(Const.addContract + 'textarea_Notes'))
		gAct.Wait(3)
		WebUI.setText(findTestObject(Const.addContract + 'textarea_Notes'), invoice.notes)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "tab_AccountInfo"))
		gAct.Wait(2)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Reference"), invoice.reference)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAmpClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createDeliverable(Deliverable deliverable){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Deliverables"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_All Deliverables"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.columnPicker + 'input_DelName'), deliverable.name)
		gAct.Wait(2)
		WebUI.clearText(findTestObject(Const.columnPicker + "input_Resource"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_Resource"), deliverable.owner)
		gAct.Wait(2)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Project"), deliverable.project)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Type"), deliverable.type)
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_DeliveryDate"), deliverable.deliveryDate)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAmpClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createDeliverableAndConfirm(Deliverable deliverable){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Deliverables"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.columnPicker + 'input_DelName'), deliverable.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "select_ConfirmedProvisional"), "Confirmed", false)
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Resource"), deliverable.owner)
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Project"), deliverable.project)
		gAct.Wait(2)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Type"), deliverable.type)
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_DeliveryDate"), deliverable.deliveryDate)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAmpClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createBenefitActual(BenefitActual benefitActual){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Benefit Actuals"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_DefaultBenefitActualFilter"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Date"), benefitActual.date)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Project"), benefitActual.project)
		WebUI.click(findTestObject(Const.columnPicker + "input_BenefitAmount"))
		gAct.Wait(3)

		WebUI.click(findTestObject(Const.columnPicker + "select_Benefit"))
		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "select_Benefit"), benefitActual.benefit, false)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "input_Date"))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Currency"), "Default Currency")
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_BenefitAmount"), benefitActual.amount)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createAction(Action action){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "All Actions"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(4)

		WebUI.clearText(findTestObject(Const.columnPicker + "input_Resource"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_Resource"), action.resource)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "tab_ToDo"))

		WebUI.click(findTestObject(Const.columnPicker + "select_ActionType"))
		WebUI.selectOptionByValue(findTestObject(Const.columnPicker + "select_ActionType"), action.actionType, false)
		WebUI.click(findTestObject(Const.columnPicker + "tab_ToDo"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_RequiredDate"), action.date)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Project"), action.project)
		WebUI.click(findTestObject(Const.columnPicker + "tab_ToDo"))

		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "action_Details"), action.description)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createRiskAction(RiskAction riskAction){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Mitigation Plan Items"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.columnPicker + 'input_Project'), riskAction.project)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.columnPicker + "td_General"))
		gAct.Wait(3)

		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + 'select_Risk'), riskAction.risk, false)
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.columnPicker + 'input_RiskActionType'), riskAction.type)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "td_General"))
		gAct.Wait(1)

		String ownerText = WebUI.getAttribute(findTestObject(Const.risks + 'input_Owner'), 'value')
		if(ownerText == ""){
			WebUI.setText(findTestObject(Const.risks + 'input_Owner'), riskAction.owner)
			WebUI.click(findTestObject(Const.risks + 'input_Title'))
		}

		WebUI.verifyOptionSelectedByLabel(findTestObject(Const.risks + 'select_Status'), riskAction.status, false, 5)
		WebUI.setText(findTestObject(Const.columnPicker + 'input_DueDate'), riskAction.dateDue)
		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + 'select_Priority'), riskAction.priority, false)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "td_General"))

		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "action_Description"), riskAction.description)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createIssueAction(IssueAction issueAction){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Resolution Plan Items"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.issues + 'input_Project'), issueAction.project)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "td_General"))
		gAct.Wait(1)

		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + 'select_Issue'), issueAction.issue, false)
		gAct.Wait(1)

		WebUI.setText(findTestObject(Const.columnPicker + 'input_RiskActionType'), issueAction.type)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "td_General"))
		gAct.Wait(1)

		String ownerText = WebUI.getAttribute(findTestObject(Const.issues + 'input_Owner'), 'value')
		if(ownerText == ""){
			WebUI.setText(findTestObject(Const.issues + 'input_Owner'), issueAction.owner)
			WebUI.click(findTestObject(Const.issues + 'input_Title'))
		}

		WebUI.setText(findTestObject(Const.columnPicker + 'input_DueDate'), issueAction.dueDate)

		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + 'select_Priority'), issueAction.priority, false)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "td_General"))
		gAct.Wait(1)

		WebUI.setText(findTestObject(Const.columnPicker + "action_Description"), issueAction.description)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)

	}



	static def createWorkingTimes(WorkingTimes workingTimes){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Working Times"]))
		gAct.Wait(1)

		WebD.clickElement("//a[@class='tooltip-primary btn btn-default shiny btn-sm viewaction']")
		gAct.Wait(4)

		WebUI.setText(findTestObject(Const.addWorkingTime + "input_Name"), workingTimes.title)
		WebUI.click(findTestObject(Const.addWorkingTime + "a_General"))
		gAct.Wait(2)
		WebUI.setText(findTestObject(Const.addWorkingTime + "input_Code"), workingTimes.code)

		String fThours = WebUI.getAttribute(findTestObject(Const.addWorkingTime + 'input_FullTimeHoursPerDay'), 'value')
		gAct.compareStringAndReport(fThours, "8")

		String FTE = WebUI.getAttribute(findTestObject(Const.addWorkingTime + 'input_FTE'), 'value')
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

		WebUI.click(findTestObject(Const.addWorkingTime + 'button_Save and Close'))
		gAct.Wait(3)
		Act.verifySavePopUpText()

		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		gAct.Wait(3)
	}


	static def createAssignment(Assignment assignment){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Assignments"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Project"), assignment.project)
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.columnPicker + "Select_Task"))
		gAct.Wait(2)
		WebUI.selectOptionByIndex(findTestObject(Const.columnPicker + "Select_Task"), assignment.task)
		gAct.Wait(1)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Description"), assignment.description)
		gAct.Wait(3)

		WebUI.clearText(findTestObject(Const.columnPicker + "input_Resource"))
		gAct.Wait(2)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Resource"), assignment.resource)
		gAct.Wait(2)
		WebUI.setText(findTestObject(Const.columnPicker + "input_StartDate"), assignment.startDate)
		WebUI.setText(findTestObject(Const.columnPicker + "input_FinishDate"), assignment.endDate)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Effort"), assignment.effort)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAmpClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)

	}


	static def createSupply(Supply supply){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		gAct.Wait(2)
		Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Resourcing", Const.dashBoard + "subMenu_ResourcePlan", Const.dashBoard + 'currentPageHeader', "Resource Plan")
		gAct.Wait(3)

		WebUI.click(findTestObject(Const.addSupply + "a_AddSupply"))
		gAct.Wait(3)

		WebUI.setText(findTestObject(Const.addSupply + "input_Project"), supply.project)
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.addSupply + "td_ResourceLabel"))
		gAct.Wait(2)
		WebUI.setText(findTestObject(Const.addSupply + "input_Resource"), supply.resource)
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.addSupply + "EditProjectSupplyLineModalHeader"))
		WebUI.setText(findTestObject(Const.addSupply + "input_Department"), supply.department)
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.addSupply + "EditProjectSupplyLineModalHeader"))
		gAct.Wait(3)
		WebUI.clearText(findTestObject(Const.addSupply + "input_Role"))
		Obj.setEditSync(Const.addSupply + "input_Role", supply.role)
		//		WebUI.setText(findTestObject(Const.addSupply + "input_Role"), supply.role)
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.addSupply + "EditProjectSupplyLineModalHeader"))
		WebUI.setText(findTestObject(Const.addSupply + "input_SupplyWk1"), supply.supplywk1)
		WebUI.setText(findTestObject(Const.addSupply + "input_SupplyWk2"), supply.supplywk2)
		WebUI.setText(findTestObject(Const.addSupply + "input_SupplyWk3"), supply.supplywk3)
		WebUI.setText(findTestObject(Const.addSupply + "input_SupplyWk4"), supply.supplywk4)
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.addSupply + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)

	}


	static def createExchangeRate(ExchangeRate exchangeRate){
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.financialsMenu + "a_ExchangeRates"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_AddExchangeRate"))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Currency"), exchangeRate.currency)
		WebUI.click(findTestObject(Const.columnPicker + "input_Rate"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_ExRateStartDate"), exchangeRate.startDate)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Rate"), exchangeRate.rate)
		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndCloseExchangeRate"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createProjectSnapshot(ProjectSnapshot projectSnapshot){
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Project Snapshots"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.addProjectSnapshot + "a_AddSnapshot"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.addProjectSnapshot + 'dropDown_ProjSnapshot'))
		WebUI.click(findTestObject(Const.addProjectSnapshot + 'select_Project_Snapshot', [('proj'): projectSnapshot.project]))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.addProjectSnapshot + 'input__Description'), projectSnapshot.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.addProjectSnapshot + 'select_Type'), projectSnapshot.type, false)
		WebUI.click(findTestObject(Const.addProjectSnapshot + 'button_Save and Close'))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createMultiProjectSnapshot(ProjectSnapshot projectSnapshot, int snapshotNo){
		String snapshotName
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Project Snapshots"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Search"))
		gAct.Wait(1)


		for(int intCnt = 0; intCnt < snapshotNo; intCnt ++){

			WebUI.click(findTestObject(Const.addProjectSnapshot + "a_AddSnapshot"))
			gAct.Wait(1)
			WebUI.click(findTestObject(Const.addProjectSnapshot + 'dropDown_ProjSnapshot'))
			WebUI.click(findTestObject(Const.addProjectSnapshot + 'select_Project_Snapshot', [('proj'): projectSnapshot.project]))
			gAct.Wait(1)
			WebUI.setText(findTestObject(Const.addProjectSnapshot + 'input__Description'), projectSnapshot.name)
			gAct.Wait(1)
			WebUI.selectOptionByLabel(findTestObject(Const.addProjectSnapshot + 'select_Type'), projectSnapshot.type, false)
			WebUI.click(findTestObject(Const.addProjectSnapshot + 'button_Save and Close'))
			gAct.Wait(3)

			snapshotName = Com.generateRandomText(10)
			projectSnapshot.name = snapshotName
		}

		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	static def createProjectTemplate(Project project){
		Rep.nextTestStep("Click On [Search] from the side bar")
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Projects"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_AddTemplate"))
		WebUI.click(findTestObject(Const.projectWizard + 'projectTypeDropDown'))
		WebUI.click(findTestObject(Const.projectWizard + 'selectProjectType', [('type'): project.projectType]))
		WebUI.click(findTestObject(Const.projectWizard + 'radioEmptyProject'))
		WebUI.click(findTestObject(Const.projectWizard + 'button_Next'))
		Pop.addProject_Wizard_page2(project)

		WebUI.click(findTestObject(Const.projectWizard + "button_SaveAndClose"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


}
