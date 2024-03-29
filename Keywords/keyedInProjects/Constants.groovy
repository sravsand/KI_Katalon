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

public class Constants {


	//Object Repository paths
	static String genericObjs = "KeyedInProjects/Generic"
	static String loginScreen = "KeyedInProjects/LoginScreen/"
	static String logoutScreen = "KeyedInProjects/LogoutScreen/"
	static String terms = "KeyedInProjects/TermsAndCons/"
	static String chgePword = "KeyedInProjects/ChangePassword/"
	static String dashBoard = "KeyedInProjects/MyWorkDashboard/"
	static String roleMenu = "KeyedInProjects/rolesMenu/"
	static String resourceMenu = "KeyedInProjects/resourcesMenu/"
	static String customerMenu = "KeyedInProjects/customersMenu/"
	static String projectsMenu = "KeyedInProjects/projectsMenu/"
	static String costCentreMenu = "KeyedInProjects/costCentreMenu/"
	static String departmentMenu = "KeyedInProjects/departmentsMenu/"
	static String costMenu = "KeyedInProjects/costsMenu/"
	static String chargeMenu = "KeyedInProjects/chargesMenu/"
	static String webservices = "KeyedInProjects/Soap_Webservices/"
	static String public_WebServices = webservices + "publicServices/"
	static String IntacctIntegration = webservices + "IntacctIntegration/"
	static String QuickBooksIntegration = webservices + "QuickBooksIntegration/"

	static String plusMenu = dashBoard + "plusMenu/"
	static String expense = dashBoard + "Expense/"
	static String myProjects = dashBoard + "MyProjectsMenu/"
	static String reports = dashBoard + "ReportsTab/"
	static String timesheet = dashBoard + "TimesheetTab/"
	static String myWork = dashBoard + "MyWorkTab/"
	static String configureViewMenu = dashBoard + "ConfigureViewMenu/"
	static String addWidget = configureViewMenu + "addWidget/"
	static String createViewDialog = configureViewMenu + "createViewDialog/"
	static String widgetView = configureViewMenu + "widgetView/"
	static String widgetConfig = configureViewMenu + "widgetConfig/"
	static String publishConfig = dashBoard + "publishViewConfig/"
	static String manageViewsConfig = dashBoard + "manageViewsConfig/"
	static String layouts = dashBoard + "layouts/"
	static String mainToolBar = dashBoard + "TopMenuBar/"
	static String projects = dashBoard + "projects/"
	static String search = dashBoard + "searchMenu/"
	static String columnPicker = search + "ColumnChooser/"
	static String AddLocks = search + "AddLocks/"
	static String addWorkingTime = search + "AddWorkingTime/"
	static String addContract = search + "AddContract/"
	static String addPost = search + "addPost/"
	static String newTimesheet = search + "newTimesheet/"
	static String timesheetFilter = search + "TimesheetFilter/"
	static String searchFilters = dashBoard + "searchFilters/"
	static String templates = dashBoard + "Templates/"
	static String searchProject = searchFilters + "projects/"
	static String searchReport = searchFilters + "Reports/"
	static String reportRunner = reports + "reportRunner/"
	static String projectWizard = plusMenu + "ProjectWizard/"
	static String claim = expense + "Claim/"
	static String datePicker = expense + "datePicker/"
	static String approveTsheet = myWork + "ApproveTimesheet/"
	static String calendarOverride = columnPicker + "CopyCalendarOverridesModal/"
	static String dataLoad = columnPicker + "Dataload/"
	static String adjustTSheetExRate = columnPicker + "adjustTimesheetExRate/"
	static String adjustExpenseExRate = columnPicker + "adjustExpenseExRate/"
	static String adjustChargeExRate = columnPicker + "adjustChargeRate/"
	static String adjustCostExRate = columnPicker + "adjustCostRate/"
	static String addProjectSnapshot = columnPicker + "addProjectSnapshot/"
	static String LoginModal = columnPicker + "Login/"
	static String LoginProfile = columnPicker + "LoginProfile/"
	static String addAssignment = columnPicker + "AddAssignment/"

	static String adminMenu = mainToolBar + "adminMenu/"
	static String busEntMenu = adminMenu + "BusinessEntities/"
	static String financialsMenu = adminMenu + "Financials/"
	static String securityMenu = adminMenu + "Security/"
	static String skillsMenu = adminMenu + "Skills/"
	static String toolsMenu = adminMenu + "Tools/"
	static String settingsMenu = mainToolBar + "settingsMenu/"
	static String proxyLogin = mainToolBar + "Proxy/"
	static String favourites = mainToolBar + "favourites/"
	static String settingsModal = settingsMenu + "modal/"
	static String CustomFieldScreen = settingsMenu + "customFieldScreen/"
	static String generalSettings_Modal = settingsMenu + "GeneralSettingsModal/"
	static String timeExpense_Modal = settingsMenu + "TimeExpenseModal/"
	static String loginProfiles = securityMenu + "LoginProfiles/"
	static String logins  = securityMenu + "Logins/"
	static String editLogins  = logins + "EditLogins/"
	static String CustCostCentre = CustomFieldScreen + "costCentre/"
	static String DisplaySettings = settingsMenu + "DisplaySetting/"
	static String strategicPlanning = settingsMenu + "StrategicPlanning/"
	static String planningSettings = settingsMenu + "PlanningSettingsModal/"

	static String timesheetSaved = timesheet + "SuccessfullySavedPopup/"
	static String CheckOutTask = myProjects + "CheckOutPopUp/"
	static String editPopUp = myProjects + "editedPopUp/"
	static String rolebackMsg = myProjects + "RolebackMessage/"
	static String gantt = myProjects + "Gantt/"
	static String insights = myProjects + "Insights/"
	static String forecasts = myProjects + "Forecasts/"
	static String finance = myProjects + "Finance/"
	static String resource = myProjects + "Resource/"
	static String deliverables = myProjects + "Deliverables/"
	static String risks = myProjects + "Risks/"
	static String issues = myProjects + "Issues/"
	static String billing = myProjects + "Billing/"
	static String approvals = myProjects + "Approvals/"
	static String context = myProjects + "ContextReports/"
	static String documents = myProjects + "Documents/"
	static String capacity = myProjects + "Capacity/"
	static String requests = myProjects + "Requests/"
	static String resourcePlan = myProjects + "ResourcePlan/"
	static String projectAdd = myProjects + "Project_Add/"
	static String addSupply = resourcePlan + "Supply/"
	static String billingContract = billing + "Contract/"
	static String billingInvoice = billing + "Invoice/"
	static String ganttToolbar = gantt + "Toolbar/"
	static String ganttTable = gantt + "Table/"
	static String ganttTaskDetails = gantt + "TaskDetails/"
	static String ganttSaveChanges = gantt + "SaveChangesMessage/"
	static String ganttNewVersion = gantt + "NewGanttVersion/"
	static String ganttManageVersion = gantt + "versionManagement/"
	static String ganttAssignmentTable = gantt + "AssignmentTable/"
	static String projTree = myProjects + "projectTreeView/"
	static String insightsTimeLine = insights + "Timeline/"
	static String insightsDeliverables = insights + "Deliverables/"


	//kip4
	static String kip4 = dashBoard + "KIP4/"
	static String newFilter = dashBoard + "newFilter/"
	static String kip4Generic = kip4 + "Generic/"
	static String kip4Location = kip4 + "Location/"
	static String kip4Category = kip4 + "Category/"
	static String kip4Currency = kip4 + "Currency/"
	static String kip4Nominal = kip4 + "NominalCode/"
	static String kip4BenefitType = kip4 + "BenefitType/"
	static String kip4ExchRate = kip4 + "ExchangeRates/"
	static String kip4DeleteModal = kip4 + "DeleteModal/"
	static String kipCloseEdit = kip4 + "closeEditPopup/"
	static String kip4DatePicker = kip4 + "datePicker/"
	static String kip4BillingPriceList = kip4 + "BillingPriceList/"
	static String kip4DeliverableType = kip4 + "DeliverableType/"
	static String kip4MitigationPlanItemType = kip4 + "MitigationPlanItemType/"
	static String kip4Cost = kip4 + "Costs/"
	static String kip4Charge = kip4 + "Charge/"
	static String kip4CostCentre = kip4 + "CostCentre/"
	static String kip4DocUpload = kip4 + "DocumentUpload/"
	static String kip4Activities = kip4 + "Activities/"
	static String kip4ExpenseType = kip4 + "ExpenseType/"
	static String kip4Contacts = kip4 + "Contacts/"
	static String kip4BenefitActual = kip4 + "BenefitActual/"
	static String kip4Notes = kip4 + "Notes/"
	static String kip4DelNotes = kip4Notes + "DeleteModal/"
	static String kip4SecGroup = kip4 + "SecurityGroup/"
	static String kip4Department = kip4 + "Department/"
	static String kip4AdjExpExchRate = kip4 + "AdjustExpenseExchangeRate/"
	static String kip4AdjTshExchRate = kip4 + "AdjustTimesheetExchangeRate/"
	static String kip4AdjchgRate = kip4 + "AdjustChargeRate/"
	static String kip4AdjcostRate = kip4 + "AdjustCostRate/"
	static String kip4Skills = kip4 + "Skills/"
	static String kip4LoginGroup = kip4 + "LoginGroup/"
	static String kip4GenericFilter = kip4 + "GenericFilter/"
	static String kip4Roles = kip4 + "Roles/"
	static String kip4LoginProfile = kip4 + "LoginProfile/"
	static String kip4Customer = kip4 + "Customer/"
	static String kip4CodeConverter = kip4 + "CodeConverter/"
	static String kip4Resource = kip4 + "Resource/"
	static String kip4Login = kip4 + "Login/"
	static String kip4LoginAddEntity = kip4Login + "Add_EntityModal/"
	static String kip4Locks = kip4 + "Locks/"

	//new ui
	static String newUI = dashBoard + "newUI/"

}
