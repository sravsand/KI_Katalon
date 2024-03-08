package keyedInProjects

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

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import global.Action as gAct
import global.Common as Com
import global.Object as gObj
import global.WebDriverMethods as WebD
import models.Role
import models.Resource
import models.Customer
import models.Project
import models.CostCentre
import models.Department
import models.Company
import models.Programme
import models.Parent
import models.Cost
import models.Charge


public class Populate {

	@Keyword
	static def newRolesForm(Role role){
		gObj.buttonClickSync(Const.roleMenu + "generalTab")
		gObj.setEditSync(Const.roleMenu + "roleName", role.name)
		gObj.setEditSync(Const.roleMenu + "roleCode", role.code)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.roleMenu + "roleActive"), GVars.longWait)
		if(!active){
			gObj.checkSync(Const.roleMenu + "roleActive")
		}

		gObj.setEditSync(Const.roleMenu + "roleDept", role.department)
		gObj.setEditSync(Const.roleMenu + "roleLocation", role.location)
		gObj.buttonClickSync(Const.roleMenu + "generalTab")

		gObj.buttonClickSync(Const.roleMenu + "roleWorkingTimeDropdown")
		gObj.clearSetEditSync(Const.roleMenu + "roleWorkingTime", role.workingTime)

		gObj.buttonClickSync(Const.roleMenu + "generalTab")
		gObj.setEditSync(Const.roleMenu + "roleCostCentre", role.costCentre)
		gObj.setEditSync(Const.roleMenu + "roleNotes", role.notes)

		gObj.buttonClickSync(Const.roleMenu + "financialTab")

		gObj.setEditSync(Const.roleMenu + "roleCost", role.cost)
		gObj.setEditSync(Const.roleMenu + "roleCharge", role.charge)
	}


	@Keyword
	static def newResourceForm(Resource resource){
		gObj.setEditSync(Const.kip4Resource + "input_ResourceName", resource.name)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			gObj.checkSync(Const.kip4Generic + "chk_Active")
		}

		gObj.scrollToElementSync(Const.kip4Resource + 'label_Department')

		gObj.selectComboboxValue("general.department", "general.department", resource.department, resource.department)
		gObj.selectComboboxValue("general.primaryRole", "general.primaryRole", resource.role, resource.role)

		//		gObj.scrollToElementSync(Const.kip4Resource + 'label_Location')
		gObj.selectComboboxValue("general.location", "general.location", resource.location, resource.location)

		gObj.scrollToElementSync(Const.kip4Resource + 'label_WorkingTime')
		gObj.selectComboboxValue("general.workingTime", "general.workingTime", resource.workingTime, resource.workingTime)

		gObj.scrollToElementSync(Const.kip4Resource + 'label_CostCentre')
		String cCentre = resource.costCentre.toString().toLowerCase()
		gObj.selectComboboxValue("general.costCentre", "general.costCentre", resource.costCentre, cCentre)

		gObj.scrollToElementSync(Const.kip4Resource + 'label_LineManager')
		gObj.selectComboboxValue("general.lineManager", "general.lineManager", resource.lineManger, resource.lineManger)

		gObj.scrollToElementSync(Const.kip4Resource + 'label_Email')
		gObj.setEditSync(Const.kip4Resource + "input_Email", resource.email)

		gObj.scrollToElementSync(Const.kip4Resource + 'label_Telephone')
		gObj.setEditSync(Const.kip4Resource + "input_phoneNumber", resource.telephone)

		gObj.scrollToElementSync(Const.kip4Resource + 'label_Notes')
		gObj.setEditSync(Const.kip4Resource + "textarea_Notes", resource.notes)
	}


	@Keyword
	static def newResourceFormWithFinancials(Resource resource){
		gObj.setEditSync(Const.kip4Resource + "input_ResourceName", resource.name)

		def active = WebD.getCheckBoxState("//input[@id='active']")

		if(active == null){
			gObj.checkSync(Const.kip4Generic + "chk_Active")
		}

		gObj.scrollToElementSync(Const.kip4Resource + 'label_Department')
		gObj.selectComboboxValue("general.department", "general.department", resource.department, resource.department)
		gObj.selectComboboxValue("general.primaryRole", "general.primaryRole", resource.role, resource.role)

		//		gObj.scrollToElementSync(Const.kip4Resource + 'label_Location')
		gObj.selectComboboxValue("general.location", "general.location", resource.location, resource.location)

		gObj.scrollToElementSync(Const.kip4Resource + 'label_WorkingTime')
		gObj.selectComboboxValue("general.workingTime", "general.workingTime", resource.workingTime, resource.workingTime)

		gObj.scrollToElementSync(Const.kip4Resource + 'label_CostCentre')
		String cCentre = resource.costCentre.toString().toLowerCase()
		gObj.selectComboboxValue("general.costCentre", "general.costCentre", resource.costCentre, cCentre)

		gObj.scrollToElementSync(Const.kip4Resource + 'label_LineManager')
		gObj.selectComboboxValue("general.lineManager", "general.lineManager", resource.lineManger, resource.lineManger)

		gObj.scrollToElementSync(Const.kip4Resource + 'label_Email')
		gObj.setEditSync(Const.kip4Resource + "input_Email", resource.email)

		gObj.scrollToElementSync(Const.kip4Resource + 'label_Telephone')
		gObj.setEditSync(Const.kip4Resource + "input_phoneNumber", resource.telephone)

		gObj.scrollToElementSync(Const.kip4Resource + 'label_Notes')
		gObj.setEditSync(Const.kip4Resource + "textarea_Notes", resource.notes)


		WebUI.click(findTestObject(Const.kip4Resource + "tab_Financial"))
		//		gObj.scrollToElementSync(Const.kip4Resource + 'label_CostStd')
		if(resource.cost_std != "") {
			String cost_lc = resource.cost_std.toString().toLowerCase()
			gObj.selectComboboxValue("financial.defaultStandardCost", "financial.defaultStandardCost", resource.cost_std, cost_lc)
		}

		//		gObj.scrollToElementSync(Const.kip4Resource + 'label_CostOvr')
		if(resource.cost_ovr != "") {
			gObj.selectComboboxValue("financial.defaultOvertimeCost", "financial.defaultOvertimeCost", resource.cost_ovr, resource.cost_ovr)
		}

		//		gObj.scrollToElementSync(Const.kip4Resource + 'label_ChargeStd')
		if(resource.charge_std != "") {
			gObj.selectComboboxValue("financial.defaultStandardCharge", "financial.defaultStandardCharge", resource.charge_std, resource.charge_std)
		}

		//		gObj.scrollToElementSync(Const.kip4Resource + 'label_ChargeOvr')
		if(resource.charge_ovr != "") {
			gObj.selectComboboxValue("financial.defaultOvertimeCharge", "financial.defaultOvertimeCharge", resource.charge_ovr, resource.charge_ovr)
		}
	}


	@Keyword
	static def newCustomerForm(Customer customer){
		gObj.buttonClickSync(Const.customerMenu + "generalTab")
		gObj.setEditSync(Const.customerMenu + "customerName", customer.name)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.customerMenu + "customerActive"), GVars.longWait)
		if(!active){
			gObj.checkSync(Const.customerMenu + "customerActive")
		}

		gObj.setEditSync(Const.customerMenu + "customerAddressLine1", customer.address.address1)
		gObj.setEditSync(Const.customerMenu + "customerAddressLine2", customer.address.address2)
		gObj.setEditSync(Const.customerMenu + "customerCity", customer.address.city)
		gObj.setEditSync(Const.customerMenu + "customerState", customer.address.state)
		gObj.setEditSync(Const.customerMenu + "customerCountry", customer.address.country)
		gObj.setEditSync(Const.customerMenu + "customerZipCode", customer.address.postCode)
		gObj.setEditSync(Const.customerMenu + "customerTelephone", customer.telephone)
		gObj.setEditSync(Const.customerMenu + "customerFax", customer.fax)
		gObj.setEditSync(Const.customerMenu + "customerWebsite", customer.website)
		gObj.setEditSync(Const.customerMenu + "customerEmail", customer.email)

		if(customer.customerGroup != ""){
			gObj.setEditSync(Const.customerMenu + "select_CustomerGroup", customer.customerGroup)
		}
	}


	@Keyword
	static def newProjectForm(Project project){
		gObj.buttonClickSync(Const.projectsMenu + "generalTab")
		gObj.setEditSync(Const.projectsMenu + "projectName", project.name)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.projectsMenu + "projectActive"), GVars.longWait)
		if(!active){
			gObj.checkSync(Const.projectsMenu + "projectActive")
		}

		gObj.setEditSync(Const.projectsMenu + "projectCustomer", project.customer)
		gObj.buttonClickSync(Const.projectsMenu + "projectWorkingTimeDropdown")
		gObj.setEditSync(Const.projectsMenu + "projectWorkingTime", project.workingTime)
		gObj.selectComboByLabelSync(Const.projectsMenu + "select_ProjectLevel", project.projectLevel)
		gObj.setEditSync(Const.projectsMenu + "projectParent", project.parent)
		gObj.setEditSync(Const.projectsMenu + "projectLocation", project.location)
		gObj.setEditSync(Const.projectsMenu + "projectDepartment", project.department)
		gObj.setEditSync(Const.projectsMenu + "projectProjectManager", project.projectManager)
		gObj.setEditSync(Const.projectsMenu + "projectStartDate", project.startDate)
		gObj.setEditSync(Const.projectsMenu + "projectEndDate", project.endDate)
	}


	@Keyword
	static def newCostCentreForm(CostCentre costCentre){
		gObj.setEditSync(Const.costCentreMenu + "costCentreName", costCentre.name)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.costCentreMenu + "costCentreActive"), GVars.longWait)
		if(!active){
			gObj.checkSync(Const.costCentreMenu + "costCentreActive")
		}

		gObj.setEditSync(Const.costCentreMenu + "costCentreCode", costCentre.code)
		gObj.setEditSync(Const.costCentreMenu + "costCentreNotes", costCentre.notes)
	}


	@Keyword
	static def newDepartmentForm(Department department){
		gObj.setEditSync(Const.departmentMenu + "departmentName", department.name)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.departmentMenu + "departmentActive"), GVars.longWait)
		if(!active){
			gObj.checkSync(Const.departmentMenu + "departmentActive")
		}

		gObj.setEditSync(Const.departmentMenu + "departmentCode", department.code)
		gObj.setEditSync(Const.departmentMenu + "departmentParent", department.parent)
		gObj.setEditSync(Const.departmentMenu + "departmentManager", department.manager)
	}


	@Keyword
	static def newCompanyForm(Company company){
		gObj.buttonClickSync(Const.projectsMenu + "generalTab")
		gObj.setEditSync(Const.projectsMenu + "projectName", company.name)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.projectsMenu + "projectActive"), GVars.longWait)
		if(!active){
			gObj.checkSync(Const.projectsMenu + "projectActive")
		}

		gObj.setEditSync(Const.projectsMenu + "projectCustomer", company.customer)

		gObj.selectComboByLabelSync(Const.projectsMenu + "select_ProjectLevel", company.projectLevel)

		boolean alertVisible = WebUI.verifyAlertPresent(GVars.longWait, FailureHandling.OPTIONAL)

		if(alertVisible){
			WebUI.acceptAlert()
		}
		WebUI.switchToDefaultContent()

		gObj.buttonClickSync(Const.projectsMenu + "projectWorkingTimeDropdown")
		gObj.setEditSync(Const.projectsMenu + "projectWorkingTime", company.workingTime)

		gObj.setEditSync(Const.projectsMenu + "projectLocation", company.location)
		gObj.setEditSync(Const.projectsMenu + "projectDepartment", company.department)

		gObj.setEditSync(Const.projectsMenu + "projectStartDate", company.startDate)
		gObj.setEditSync(Const.projectsMenu + "projectEndDate", company.endDate)
	}


	@Keyword
	static def newProgrammeForm(Programme programme){
		gObj.buttonClickSync(Const.projectsMenu + "generalTab")
		gObj.setEditSync(Const.projectsMenu + "projectName", programme.name)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.projectsMenu + "projectActive"), GVars.longWait)
		if(!active){
			gObj.checkSync(Const.projectsMenu + "projectActive")
		}

		gObj.selectComboByLabelSync(Const.projectsMenu + "select_ProjectLevel", programme.projectLevel)
		boolean alertVisible = WebUI.verifyAlertPresent(GVars.longWait, FailureHandling.OPTIONAL)

		if(alertVisible){
			WebUI.acceptAlert()
		}
		WebUI.switchToDefaultContent()

		gObj.buttonClickSync(Const.projectsMenu + "projectWorkingTimeDropdown")
		gObj.setEditSync(Const.projectsMenu + "projectWorkingTime", programme.workingTime)

		gObj.setEditSync(Const.projectsMenu + "projectParent", programme.parent)
		gObj.setEditSync(Const.projectsMenu + "projectLocation", programme.location)
		gObj.setEditSync(Const.projectsMenu + "projectDepartment", programme.department)

		gObj.setEditSync(Const.projectsMenu + "projectStartDate", programme.startDate)
		gObj.setEditSync(Const.projectsMenu + "projectEndDate", programme.endDate)
	}


	@Keyword
	static def newParentForm(Parent parent){
		gObj.buttonClickSync(Const.projectsMenu + "generalTab")
		gObj.setEditSync(Const.projectsMenu + "projectName", parent.name)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.projectsMenu + "projectActive"), GVars.longWait)
		if(!active){
			gObj.checkSync(Const.projectsMenu + "projectActive")
		}

		gObj.selectComboByLabelSync(Const.projectsMenu + "select_ProjectLevel", parent.projectLevel)
		boolean alertVisible = WebUI.verifyAlertPresent(GVars.longWait, FailureHandling.OPTIONAL)

		if(alertVisible){
			WebUI.acceptAlert()
		}
		WebUI.switchToDefaultContent()

		gObj.buttonClickSync(Const.projectsMenu + "projectWorkingTimeDropdown")
		gObj.setEditSync(Const.projectsMenu + "projectWorkingTime", parent.workingTime)

		gObj.setEditSync(Const.projectsMenu + "projectParent", parent.parent)
		gObj.setEditSync(Const.projectsMenu + "projectLocation", parent.location)
		gObj.setEditSync(Const.projectsMenu + "projectDepartment", parent.department)

		gObj.setEditSync(Const.projectsMenu + "projectStartDate", parent.startDate)
		gObj.setEditSync(Const.projectsMenu + "projectEndDate", parent.endDate)
	}


	@Keyword
	static def newCostForm(Cost cost){
		gObj.setEditSync(Const.costMenu + "costName", cost.name)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.costMenu + "costActive"), GVars.longWait)
		if(!active){
			gObj.checkSync(Const.costMenu + "costActive")
		}

		gObj.setEditSync(Const.costMenu + "costRate", cost.rate)

		if(cost.appliesTo == "Standard"){
			gObj.checkSync(Const.costMenu + "costStandardchkSelect")
		}else{
			gObj.checkSync(Const.costMenu + "costOvertimechkSelect")
		}
	}


	@Keyword
	static def newChargeForm(Charge charge){
		gObj.setEditSync(Const.chargeMenu + "chargeName", charge.name)

		boolean active = WebUI.verifyElementChecked(findTestObject(Const.chargeMenu + "chargeActive"), GVars.longWait)
		if(!active){
			gObj.checkSync(Const.chargeMenu + "chargeActive")
		}

		gObj.setEditSync(Const.chargeMenu + "chargeRate", charge.rate)
	}


	@Keyword
	static def addProject_Wizard_page1(boolean template, String templateName, boolean forecast, boolean deliverables, boolean risks, boolean tasks, String date){
		if(template){
			gObj.buttonClickSync(Const.projectWizard + 'radioTemplate')
			gObj.selectComboByLabelSync(Const.projectWizard + 'select_Template', templateName)

			if(!forecast){
				gObj.uncheckSync(Const.projectWizard + 'chk_CopyForecasts')
			}

			if(!deliverables){
				gObj.uncheckSync(Const.projectWizard + 'chk_CopyDeliverables')
			}

			if(!risks){
				gObj.uncheckSync(Const.projectWizard + 'chk_CopyRisks')
			}

			if(!tasks){
				gObj.uncheckSync(Const.projectWizard + 'chk_CopyTasks')
			}

			gObj.setEditSync(Const.projectWizard + 'input_startDate', date)
		}else{
			gObj.buttonClickSync(Const.projectWizard + 'radioEmptyProject')
		}
		gObj.buttonClickSync(Const.projectWizard + 'button_Next')
	}


	@Keyword
	static def addProject_Wizard_page2(Project project){
		gObj.clearAndSetText(Const.projectWizard + 'input_ProjectName', project.name)

		gObj.buttonClickSync(Const.projectWizard + 'customerDropDown')
		gObj.buttonClickSubSync(Const.projectWizard + 'selectCustomer', project.customer, "cust")


		gObj.buttonClickSync(Const.projectWizard + 'WorkingTimeDropdown')
		gObj.buttonClickSubSync(Const.projectWizard + 'selectWorkingTime', project.workingTime, "wrkTime")

		gObj.buttonClickSync(Const.projectWizard + 'ParentDropDown')
		gObj.buttonClickSubSync(Const.projectWizard + 'selectParent', project.parent, "parent")

		if(project.location != ""){
			gObj.buttonClickSync(Const.projectWizard + 'input_ProjectName')
			gObj.buttonClickSync(Const.projectWizard + 'LocationDropDown')
			gObj.buttonClickSubSync(Const.projectWizard + 'selectLocation', project.location, "location")
		}
		gObj.setEditSync(Const.projectWizard + 'input_StartDateAddProject', project.startDate)
	}
}
