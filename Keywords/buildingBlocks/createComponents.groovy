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

public class createComponents {

	static def role(String[] roleDetails){
		Role roleNew = genRole.createNewRole(roleDetails)

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def resource(String[] resourceDetails){
		Resource resourceNew = genRes.createNewResource(resourceDetails)

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return resourceNew.code
	}


	static def roleDD(def roleDetails, def row){
		Role roleNew = genRole.createNewRoleDD(roleDetails, row)

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return roleNew.code
	}


	static def resourceDD(def resourceDetails, def row){
		Resource resourceNew = genRes.createNewResourceDD(resourceDetails, row)

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return resourceNew.code
	}


	static def customerDD(def customerDetails, def row){
		Customer customerNew = genCust.createCustomerDD(customerDetails, row)

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Customers"))
		WebUI.click(findTestObject(Const.customerMenu + "a_Add"))

		Pop.newCustomerForm(customerNew)

		WebUI.click(findTestObject(Const.customerMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		customerNew.code = Act.customerSearch("Customer Name", customerNew.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return customerNew.code
	}


	static def customer(Customer customer){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Customers"))
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		Pop.newCustomerForm(customer)

		WebUI.click(findTestObject(Const.customerMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		customer.code = Act.customerSearch("Customer Name", customer.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return customer.code
	}


	static def projectDD(def projectDetails, def row){
		Project projectNew = genProj.createProjectDD(projectDetails, row)

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))
		WebUI.click(findTestObject(Const.projectsMenu + "a_Add"))

		WebUI.click(findTestObject(Const.projectsMenu + "radio_AddEmptyProject"))
		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newProjectForm(projectNew)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		projectNew.code = Act.projectSearch("Project Name", projectNew.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return projectNew.code
	}


	static def project(Project project){

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return project.code
	}


	static def costCentreDD(def costCentreDetails, def row){
		CostCentre costCentreNew = genCCentre.createCostCentreDD(costCentreDetails, row)

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_CostCentres"))
		WebUI.click(findTestObject(Const.costCentreMenu + "a_Add"))

		Pop.newCostCentreForm(costCentreNew)

		WebUI.click(findTestObject(Const.costCentreMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		Act.costCentreSearch("Cost Centre Name", costCentreNew.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return costCentreNew.code

	}


	static def costCentre(CostCentre costCentre){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_CostCentres"))
		gAct.Wait(3)
		WebUI.click(findTestObject(Const.costCentreMenu + "a_Add"))
		gAct.Wait(1)

		Pop.newCostCentreForm(costCentre)

		WebUI.click(findTestObject(Const.costCentreMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		//		Act.costCentreSearch("Cost Centre Name", costCentre.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return costCentre.code

	}


	static def departmentDD(def departmentDetails, def row){
		Department departmentNew = genDep.createDepartmentDD(departmentDetails, row)

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Departments"))
		WebUI.click(findTestObject(Const.departmentMenu + "a_Add"))

		Pop.newDepartmentForm(departmentNew)

		WebUI.click(findTestObject(Const.departmentMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		Act.departmentSearch("Department Name", departmentNew.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return departmentNew.code
	}


	static def department(Department department){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Departments"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.departmentMenu + "a_Add"))

		Pop.newDepartmentForm(department)

		WebUI.click(findTestObject(Const.departmentMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		//		Act.departmentSearch("Department Name", department.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return department.code
	}


	static def companyDD(def companyDetails, def row){
		Company companyNew = genComp.createCompanyDD(companyDetails, row)

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return companyNew.code
	}


	static def company(Company company){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))

		WebUI.click(findTestObject(Const.projects + "a_Add"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.projectAdd + "radio_AddEmptyProject"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.projectAdd + "select_ProjectEntityType"))

		WebD.clickElement("//div[@id='select2-drop']//div[@class='select2-search']")
		//		WebD.setEditText("//div[@id='select2-drop']//div[@class='select2-search']", company.projectLevel)


		//		WebUI.click(findTestObject(Const.projectAdd + "input_EntityType"))

		WebUI.selectOptionByValue(findTestObject(Const.projectAdd + "select_ProjectEntityType"), company.projectLevel, false)

		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newCompanyForm(company)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		company.code = Act.projectSearch("Project Name", company.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return company.code
	}


	static def programmeDD(def programmeDetails, def row){
		Programme programmeNew = genProg.createProgrammeDD(programmeDetails, row)

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))
		WebUI.click(findTestObject(Const.projectsMenu + "a_Add"))

		WebUI.click(findTestObject(Const.projectsMenu + "radio_AddEmptyProject"))
		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newProgrammeForm(programmeNew)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		programmeNew.code = Act.projectSearch("Project Name", programmeNew.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return programmeNew.code
	}


	static def programme(Programme programme){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))
		WebUI.click(findTestObject(Const.projectsMenu + "a_Add"))

		WebUI.click(findTestObject(Const.projectsMenu + "radio_AddEmptyProject"))
		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newProgrammeForm(programme)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		programme.code = Act.projectSearch("Project Name", programme.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return programme.code
	}


	static def parentDD(def parentDetails, def row){
		Parent parentNew = genPar.createParentDD(parentDetails, row)

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))
		WebUI.click(findTestObject(Const.projectsMenu + "a_Add"))

		WebUI.click(findTestObject(Const.projectsMenu + "radio_AddEmptyProject"))
		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newParentForm(parentNew)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		parentNew.code = Act.projectSearch("Project Name", parentNew.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return parentNew.code
	}


	static def parent(Parent parent){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Projects"))
		WebUI.click(findTestObject(Const.projectsMenu + "a_Add"))

		WebUI.click(findTestObject(Const.projectsMenu + "radio_AddEmptyProject"))
		WebUI.click(findTestObject(Const.projectsMenu + "button_Finish"))

		Pop.newParentForm(parent)

		WebUI.click(findTestObject(Const.projectsMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		parent.code = Act.projectSearch("Project Name", parent.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return parent.code
	}


	static def costDD(def costDetails, def row){
		Cost costNew = genCost.createCostDD(costDetails, row)

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.financialsMenu + "a_Costs"))
		WebUI.click(findTestObject(Const.costMenu + "a_Add"))

		Pop.newCostForm(costNew)

		WebUI.click(findTestObject(Const.costMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		costNew.code = Act.costSearch("Cost Name", costNew.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return costNew.code
	}


	static def chargeDD(def chargeDetails, def row){
		Charge chargeNew = genChg.createChargeDD(chargeDetails, row)

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.financialsMenu + "a_Charges"))
		WebUI.click(findTestObject(Const.chargeMenu + "a_Add"))

		Pop.newChargeForm(chargeNew)

		WebUI.click(findTestObject(Const.chargeMenu + "button_SaveAndClose"))
		gAct.Wait(3)
		chargeNew.code = Act.chargeSearch("Charge Name", chargeNew.name)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		return chargeNew.code
	}


	@Keyword
	static def customTaskField(String taskName){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def addNewViewAndWidget_MyProjects(String widgetViewName, int widget, String widgetFilter){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		Com.edgeSync(3)
		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "My Projects")
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
		WebUI.click(findTestObject(Const.widgetView + 'widgetConfig', [('widget'): widgetFilter]))
		gAct.Wait(1)
		Com.ieSync()
		WebUI.click(findTestObject(Const.widgetConfig + 'DefaultFilterList'))
		WebUI.setText(findTestObject(Const.widgetConfig + 'DefaultFilterSearch'), "All Actions")
		Com.ieSync()
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.widgetConfig + 'DefaultFilterChoice', [('choice'): 'All Actions']))
		Com.ieSync()
		WebUI.click(findTestObject(Const.widgetConfig + 'button_Save'))
		gAct.Wait(1)
		String saveMessage =  WebUI.getText(findTestObject(Const.widgetConfig + 'widgetConfigSaveMessage'))

		if((GVars.browser == "MicrosoftEdge")||(GVars.browser == "Internet explorer")){
			gAct.findSubstringInStringAndReport(saveMessage, "Configuration saved successfully")
		}else{
			gAct.compareStringAndReport(saveMessage, "×\nConfiguration saved successfully")
		}

		WebUI.click(findTestObject(Const.widgetConfig + 'button_Close'))
		gAct.Wait(2)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def submitTimesheet(Timesheet timesheet){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		Com.edgeSync()
		Com.firefoxSync()
		Com.chromeSync()
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
		gAct.Wait(2)
		String statusText = WebUI.getText(findTestObject(Const.timesheet + 'txt_SubmitStatus'))
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		gAct.Wait(3)
		//Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def submitTimesheet_SpecificUser(Timesheet timesheet, Login login){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme,  GVars.pwordEncryt)
		gAct.Wait(3)
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
		gAct.Wait(2)
		String statusText = WebUI.getText(findTestObject(Const.timesheet + 'txt_SubmitStatus'))
		if(GVars.browser == "MicrosoftEdge"){
			gAct.findSubstringInStringAndReport(statusText, "Submitted")
		}else{
			gAct.compareStringAndReport(statusText, " Submitted")
		}

		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}



	@Keyword
	static def saveTimesheet_SpecificUser(Timesheet timesheet, Login login){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme,  GVars.pwordEncryt)
		gAct.Wait(3)
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		if(GVars.browser == "Internet explorer"){
			WebUI.click(findTestObject(Const.dashBoard + 'currentPageHeader'))
		}
		Com.firefoxSync()

		WebUI.click(findTestObject(Const.timesheet + 'timesheetEntry_DropDown'))
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.timesheet + 'input_TimesheetEntryUser'), login.name)
		WebUI.click(findTestObject(Const.timesheet + 'Select_TimesheetEntryUser', [('usr'): login.name]))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.timesheet + 'TimesheetAddLine'))
		gAct.Wait(5)
		tPop.CreateAdditionalRow("2", timesheet.row[0].project, timesheet.row[0].activity, timesheet.row[0].task)
		tPop.StandardHoursAdditionalRowModel(timesheet, "0", 0)
		tAct.timesheetSave()
		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def submitPreviousTimesheet(Timesheet timesheet, int backCnt){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		gAct.Wait(3)
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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def submitPreviousTimesheet_SpecificUser(Timesheet timesheet, int backCnt, Login login){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		gAct.Wait(3)
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

		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def saveTimesheet(Timesheet timesheet){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		gAct.Wait(1)
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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def submitOldTimesheetAndApprove(Timesheet timesheet, int weekRemoved, String weekCommence){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		gAct.Wait(1)
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		if(GVars.browser == "Internet explorer"){
			WebUI.click(findTestObject(Const.dashBoard + 'currentPageHeader'))
		}
		Com.firefoxSync()

		for(int intCnt = 0; intCnt < weekRemoved; intCnt ++){
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

		gAct.Wait(4)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")

		WebUI.click(findTestObject(Const.search + "search_DropDown"))

		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Timesheet Submissions"]))
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.columnPicker + "a_Pending Approval"))
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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}



	@Keyword
	static def submitOldTimesheetAndApprove_SpecificUser(Timesheet timesheet, int weekRemoved, String weekCommence, Login login){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		gAct.Wait(3)
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

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		Com.edgeSync()
		Com.firefoxSync()
		Com.chromeSync()

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
		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createExpense(Expense expense){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		gAct.Wait(1)
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
		//Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createExpenseType(ExpenseType expenseType){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		gAct.Wait(1)

		String searchItem = "Expense Types"
		String searchVal = "Expense Type"

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))

		WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
		gAct.Wait(1)

		String filterName = WebUI.getAttribute(findTestObject(Const.searchFilters + "input_SearchFilterName"), 'value')
		gAct.findSubstringInStringAndReport(filterName, searchVal)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "pageCount"), "10", false)

		Rep.nextTestStep("Click On [+ Add] ")
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), expenseType.name)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), expenseType.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.setText(findTestObject(Const.columnPicker + "input_MultplyExpenseCharge"), expenseType.multiplyExpChg)

		gAct.Wait(1)
		if(expenseType.Receipt == "true"){
			WebUI.check(findTestObject(Const.columnPicker + "chk_AllowReceipt"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createActionType(ActionType actionType){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		gAct.Wait(1)

		String searchItem = "Action Types"
		String searchVal = "Action Type"

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))

		WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
		gAct.Wait(1)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createCategory(Category category){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		Com.edgeSync()
		Com.firefoxSync()

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")

		WebUI.click(findTestObject(Const.search + "search_DropDown"))

		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Categories"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(1)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), category.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), category.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createContract(Contract contract){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		Com.edgeSync()
		Com.firefoxSync()

		String searchItem = "Contract"
		String searchVal = "Contract"

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")

		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): searchItem]))
		gAct.Wait(1)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createForecast(Forecast forecast){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))

		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Forecasts"]))
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
		WebUI.click(findTestObject(Const.forecasts + 'button_Save and Close'))

		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createForecastAndApprove(Forecast forecast){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))

		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Forecasts"]))
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

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createRisk(Risk risk){
		String medium = "background-color: orange; color: black;"
		String high = "background-color: red; color: black;"

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))

		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Risks"]))
		gAct.Wait(1)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createIssue(Issue issue){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createActionView(ActionView actionView){

		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createActivity(Activity activity){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createBenefitType(BenefitType benefitType){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createClientGroup(ClientGroup clientGroup){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Customer Groups"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), clientGroup.name)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), clientGroup.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createClient(Client client){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Customers"]))
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

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createContact(Contact contact){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Contacts"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		WebUI.setText(findTestObject(Const.columnPicker + "input_firstName"), contact.firstName)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Surname"), contact.surname)
		gAct.Wait(3)
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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createDeliverableType(DeliverableType deliverableType){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Deliverables"]))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.columnPicker + 'input_DelName'), deliverableType.name)

		String projectText = WebUI.getAttribute(findTestObject(Const.columnPicker + 'input_Project'), 'value')
		if(projectText == ""){
			WebUI.setText(findTestObject(Const.columnPicker + 'input_Project'), deliverableType.project)
			WebUI.click(findTestObject(Const.columnPicker + 'input_DelName'))
		}
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.columnPicker + 'input_Type'), deliverableType.type)
		WebUI.click(findTestObject(Const.columnPicker + 'input_DelName'))
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + 'select_PublishTo'), deliverableType.publishTo, false)
		WebUI.click(findTestObject(Const.columnPicker + 'input_DelName'))

		WebUI.setText(findTestObject(Const.columnPicker + 'input_DeliveryDate'), deliverableType.DeliveryDate)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))

		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createLocation(Location location){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Locations"]))
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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createProject(Project project){   //use template
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Projects"]))
		gAct.Wait(1)
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

		WebUI.setText(findTestObject(Const.projectWizard + 'input_ExecutionStartDate'), project.startDate)

		WebUI.click(findTestObject(Const.projectWizard + 'button_Next'))

		Pop.addProject_Wizard_page2(project)

		WebUI.click(findTestObject(Const.projectWizard + "button_SaveAndClose"))

		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def createEmptyProject(Project project){   //use template
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createResource(Resource resource){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.busEntMenu + "a_Resources"))
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.roleMenu + "a_Add"))

		WebUI.click(findTestObject(Const.resourceMenu + "radio_AddEmptyResource"))
		WebUI.click(findTestObject(Const.resourceMenu + "button_OK"))

		Pop.newResourceForm(resource)

		WebUI.click(findTestObject(Const.resourceMenu + "button_Save"))
		gAct.Wait(1)
		resource.code = WebUI.getAttribute(findTestObject(Const.resourceMenu + "resourceCode"), 'value')

		WebUI.click(findTestObject(Const.resourceMenu + "button_SaveAndClose"))
		gAct.Wait(3)

		boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.resourceMenu + "saveResourceError"), 5, FailureHandling.CONTINUE_ON_FAILURE)

		if(!saveError){
			Rep.fail("Resource : " + resource.name + " was not created.")
			Rep.screenshot(resource.name )
			WebUI.click(findTestObject(Const.resourceMenu + "button_Close"))
		}

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createRole(Role role){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.busEntMenu + "a_Roles"))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.roleMenu + "a_Add"))

		Pop.newRolesForm(role)
		WebUI.click(findTestObject(Const.roleMenu + "button_SaveAndClose"))
		gAct.Wait(3)

		boolean saveError = WebUI.verifyElementNotPresent(findTestObject(Const.roleMenu + "saveRoleError"), 5, FailureHandling.CONTINUE_ON_FAILURE)

		if(!saveError){
			Rep.fail("Role : " + role.name + " was not created.")
			Rep.screenshot(role.name )
			WebUI.click(findTestObject(Const.roleMenu + "button_Close"))
		}

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createCurrency(Currency currency){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Currencies"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), currency.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), currency.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.setText(findTestObject(Const.columnPicker + "input_Symbol"), currency.symbol)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createCharge(Charge charge){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Charges"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_Name"), charge.name)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Code"), charge.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_Active"), 5, FailureHandling.OPTIONAL)
		if(!active){
			WebUI.check(findTestObject(Const.columnPicker + "chk_Active"))
		}

		WebUI.setText(findTestObject(Const.columnPicker + "input_Currency"), charge.currency)


		WebUI.setText(findTestObject(Const.columnPicker + "input_Rate"), charge.rate)

		WebUI.click(findTestObject(Const.issues + 'button_Save'))
		gAct.Wait(1)

		charge.code = WebUI.getAttribute(findTestObject(Const.issues + 'input_Code'), 'value')

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createCost(Cost cost){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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

		WebUI.setText(findTestObject(Const.columnPicker + "input_Currency"), cost.currency)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Rate"), cost.rate)

		WebUI.check(findTestObject(Const.columnPicker + "chk_Standard"))
		WebUI.check(findTestObject(Const.columnPicker + "chk_Overtime"))

		WebUI.click(findTestObject(Const.issues + 'button_Save'))
		gAct.Wait(1)

		cost.code = WebUI.getAttribute(findTestObject(Const.issues + 'input_Code'), 'value')

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createNominalCode(NominalCode nominalCode){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createSkill(Skill skill){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Skills"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createLogin(Login login){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createLoginGroup(LoginGroup loginGroup){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createLoginProfile(LoginProfile loginProfile){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createSecurityGroup(SecurityGroup securityGroup){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createClaim(Claim claim){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		gAct.Wait(1)

		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}



	static def acceptClaim(Claim claim, String expenseVal, String claimNewNotes){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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


		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createLock(Lock lock){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()

	}


	static def createInvoice(Invoice invoice){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createDeliverable(Deliverable deliverable){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Deliverables"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.columnPicker + 'input_DelName'), deliverable.name)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Resource"), deliverable.owner)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Project"), deliverable.project)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Type"), deliverable.type)
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.columnPicker + "input_DeliveryDate"), deliverable.deliveryDate)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createBenefitActual(BenefitActual benefitActual){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "Benefit Actuals"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Date"), benefitActual.date)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Project"), benefitActual.project)
		WebUI.click(findTestObject(Const.columnPicker + "input_BenefitAmount"))
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.columnPicker + "select_Benefit"))
		WebUI.selectOptionByLabel(findTestObject(Const.columnPicker + "select_Benefit"), benefitActual.benefit, false)
		WebUI.click(findTestObject(Const.columnPicker + "input_Date"))

		WebUI.setText(findTestObject(Const.columnPicker + "input_Currency"), "Default Currency")
		WebUI.setText(findTestObject(Const.columnPicker + "input_BenefitAmount"), benefitActual.amount)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createAction(Action action){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		WebUI.click(findTestObject(Const.search + "search_DropDown"))
		WebUI.click(findTestObject(Const.search + "filterType", [('label'): "All Actions"]))
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.columnPicker + "a_Add"))
		gAct.Wait(4)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createRiskAction(RiskAction riskAction){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		gAct.Wait(1)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createIssueAction(IssueAction issueAction){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}



	static def createWorkingTimes(WorkingTimes workingTimes){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createAssignment(Assignment assignment){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

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
		gAct.Wait(2)

		WebUI.setText(findTestObject(Const.columnPicker + "input_Resource"), assignment.resource)
		gAct.Wait(2)
		WebUI.setText(findTestObject(Const.columnPicker + "input_StartDate"), assignment.startDate)
		WebUI.setText(findTestObject(Const.columnPicker + "input_FinishDate"), assignment.endDate)
		WebUI.setText(findTestObject(Const.columnPicker + "input_Effort"), assignment.effort)

		WebUI.click(findTestObject(Const.columnPicker + "button_SaveAndClose"))
		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	static def createSupply(Supply supply){
		Act.KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		WebUI.click(findTestObject(Const.dashBoard + "menuExpand"))
		gAct.Wait(1)
		Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Resourcing", Const.dashBoard + "subMenu_ResourcePlan", Const.dashBoard + 'currentPageHeader', "Resource Plan")
		gAct.Wait(3)

		WebUI.click(findTestObject(Const.addSupply + "a_AddSupply"))
		gAct.Wait(1)

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
		gAct.Wait(2)
		WebUI.setText(findTestObject(Const.addSupply + "input_Role"), supply.role)
		gAct.Wait(2)
		WebUI.click(findTestObject(Const.addSupply + "EditProjectSupplyLineModalHeader"))
		WebUI.setText(findTestObject(Const.addSupply + "input_SupplyWk1"), supply.supplywk1)
		WebUI.setText(findTestObject(Const.addSupply + "input_SupplyWk2"), supply.supplywk2)
		WebUI.setText(findTestObject(Const.addSupply + "input_SupplyWk3"), supply.supplywk3)
		WebUI.setText(findTestObject(Const.addSupply + "input_SupplyWk4"), supply.supplywk4)
		gAct.Wait(2)

		WebUI.click(findTestObject(Const.addSupply + "button_SaveAndClose"))
		gAct.Wait(3)
		Act.KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}

}
