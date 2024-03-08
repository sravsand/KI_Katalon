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
import com.kms.katalon.core.webui.driver.DriverFactory

import keyedInProjects.Constants as Const
import keyedInProjects.Navigate as Nav
import keyedInProjects.Validate as Val

import global.Object as Obj
import global.Action as Act
import global.Common as Com
import global.Report as Rep
import global.WebDriverMethods as WebD

import internal.GlobalVariable as GVars
import groovy.inspect.swingui.ObjectBrowser
import groovy.util.XmlParser

public class Action {

	@Keyword
	static def getBrowserSuffix(){
		String suffix
		String browserVer = DriverFactory.getExecutedBrowser()
		String selectedBrowser = DriverFactory.getExecutedBrowser().getName()

		if (browserVer == "Chrome (headless)"){
			selectedBrowser = "CHROME_DRIVER"
		}else if(browserVer == "Firefox (headless)"){
			selectedBrowser = "FIREFOX_DRIVER"
		}

		switch (selectedBrowser){
			case "CHROME_DRIVER":
				suffix = ""
				break

			case "EDGE_DRIVER":
				suffix = "ED"
				break

			case "FIREFOX_DRIVER":
				suffix = "FF"
				break

			case "IE_DRIVER":
				suffix = "IE"
				break
		}

		return suffix
	}

	@Keyword
	static def KeyedInProjectsPPMLoginTnC(String userName, String password){
		String selectedBrowser = DriverFactory.getExecutedBrowser().getName()
		Com.firefoxSync()
		Com.edgeSync(3)
		Act.openBrowserOnUrlAndMaximise(GVars.ppmUrl)
		Com.getBrowserAndVersion()

		Obj.setEditSync(Const.loginScreen + 'txtUserID', userName)
		WebUI.setEncryptedText(findTestObject(Const.loginScreen + 'txtPassword'), password)
		Obj.buttonClickSync(Const.loginScreen + 'btnLogin')

		if(GVars.browser == 'Firefox' || GVars.browser == 'MicrosoftEdge'|| GVars.browser == 'Internet explorer'){
			GVars.shortWait = 5
			Rep.info("Short wait has been changed to : " + GVars.shortWait)
		}

		handleTermsAndConditions()
	}


	@Keyword
	static def KeyedInProjectsPPMLogin(String userName, String password){
		String selectedBrowser = DriverFactory.getExecutedBrowser().getName()
		Com.firefoxSync()
		Com.edgeSync(3)
		Act.openBrowserOnUrlAndMaximise(GVars.ppmUrl)
		Com.getBrowserAndVersion()

		Obj.setEditSync(Const.loginScreen + 'txtUserID', userName)
		WebUI.setEncryptedText(findTestObject(Const.loginScreen + 'txtPassword'), password)
		Obj.buttonClickSync(Const.loginScreen + 'btnLogin')

		if(GVars.browser == 'Firefox' || GVars.browser == 'MicrosoftEdge'|| GVars.browser == 'Internet explorer'){
			Rep.info("Short wait has been changed to : " + GVars.midWait)
		}

		//handleTermsAndConditions()
	}


	@Keyword
	static def KeyedInProjectsPPMLoginChromeDriver(String userName, String password, String reportDownloadPath){
		String browserVer = DriverFactory.getExecutedBrowser()
		String selectedBrowser = DriverFactory.getExecutedBrowser().getName()

		if (browserVer == "Chrome (headless)"){
			selectedBrowser = "CHROME_DRIVER"
		}else if(browserVer == "Firefox (headless)"){
			selectedBrowser = "FIREFOX_DRIVER"
		}

		switch (selectedBrowser){
			case "CHROME_DRIVER":
				Act.openBrowserAtUrlMaximiseChromedriver(GVars.ppmUrl, reportDownloadPath)
				break

			case "EDGE_DRIVER":
				Act.openBrowserAtUrlMaximiseEdgedriver(GVars.ppmUrl, reportDownloadPath)
				break

			case "FIREFOX_DRIVER":
				Act.openBrowserAtUrlMaximiseFirefoxdriver(GVars.ppmUrl, reportDownloadPath)
				break

			case "IE_DRIVER":
				Act.openBrowserAtUrlMaximiseIEdriver(GVars.ppmUrl, reportDownloadPath)
				break
		}

		Com.getBrowserAndVersion()

		Obj.setEditSync(Const.loginScreen + 'txtUserID', userName)
		WebUI.setEncryptedText(findTestObject(Const.loginScreen + 'txtPassword'), password)
		Obj.buttonClickSync(Const.loginScreen + 'btnLogin')

		if(GVars.browser == 'Firefox' || GVars.browser == 'MicrosoftEdge' ||  GVars.browser == 'Internet explorer'){
			Rep.info("Short wait has been changed to : " + GVars.midWait)
		}

		//handleTermsAndConditions()
	}


	@Keyword
	static def KeyedInProjectsPPMLogout(){
		Obj.buttonClickSync(Const.newUI + "a_ProfileSettings")
		Obj.buttonClickSync(Const.mainToolBar + "a_SignOut")
		Act.Wait(2)
		String logOutText = WebD.getElementText("//td[@class='formDetail']")
		Act.compareStringAndReport(logOutText, "Thank you for using KeyedIn.\nYou have now been logged out of the system.\nLog In")
		//		WebUI.verifyElementPresent(findTestObject(Const.logoutScreen + "logOutText"), 10)
	}


	@Keyword
	static def KeyedInProjectsLoginToCurrentBrowserSession(String userName, String password){
		String selectedBrowser = DriverFactory.getExecutedBrowser().getName()

		WebUI.navigateToUrl(GVars.ppmUrl)
		Obj.setEditSync(Const.loginScreen + 'txtUserID', userName)
		WebUI.setEncryptedText(findTestObject(Const.loginScreen + 'txtPassword'), password)
		Obj.buttonClickSync(Const.loginScreen + 'btnLogin')
	}


	@Keyword
	static def handleTermsAndConditions(){
		boolean elementVisible = WebUI.verifyElementPresent(findTestObject(Const.terms + 'TermsAndConditions'), GVars.shortWait, FailureHandling.OPTIONAL)

		if(elementVisible){
			String TermsText = Obj.getEditTextSync(Const.terms + 'TermsAndConditions')

			Obj.checkSync(Const.terms + 'chkAccept')
			Obj.buttonClickSync(Const.terms + 'btnAccept')
		}
	}


	@Keyword
	static def changePasswordOnRequest(String userCaps, String pWord, String newPWord){

		boolean alertVisible = WebUI.verifyAlertPresent(GVars.longWait, FailureHandling.OPTIONAL)

		if(alertVisible){
			WebUI.acceptAlert()
		}

		boolean elementVisible = WebUI.verifyElementPresent(findTestObject(Const.chgePword + 'userID', [('User'): userCaps]), GVars.longWait, FailureHandling.OPTIONAL)

		if (elementVisible){
			WebUI.setEncryptedText(findTestObject(Const.chgePword + 'txtCurrentPassword'), pWord)
			WebUI.setEncryptedText(findTestObject(Const.chgePword + 'txtNewPassword'), newPWord)
			WebUI.setEncryptedText(findTestObject(Const.chgePword + 'txtConfirmPassword'), newPWord)

			Obj.buttonClickSync(Const.chgePword + 'btnChangePassword')
		}
	}


	@Keyword
	static def KeyedInProjectsPPMLoginAndVerify(String userName, String password, String user){
		KeyedInProjectsPPMLogin(userName, password)
//		Act.Wait(GVars.shortWait)
		Val.correctPPMLogin(GVars.ppmUrl + "Dashboard/Index/MVC-MYWORK-HOME", user)
	}


	@Keyword
	static def KeyedInProjectsPPMLoginAndVerifyTnC(String userName, String password, String user){
		KeyedInProjectsPPMLoginTnC(userName, password)
		Act.Wait()
		Val.correctPPMLogin(GVars.ppmUrl + "Dashboard/Index/MVC-MYWORK-HOME", user)
	}


	@Keyword
	static def keyedInProjectsPPMLoginAndVerifyChromeDriver(String userName, String password, String user, String downloadPath){
		KeyedInProjectsPPMLoginChromeDriver(userName, password, downloadPath)
		Val.correctPPMLogin(GVars.ppmUrl + "Dashboard/Index/MVC-MYWORK-HOME", user)
		Act.Wait(1)
	}


	@Keyword
	static def validateLeftHandMenuNavigation(){
		Obj.buttonClickSync(Const.dashBoard + "menuExpand")
		Act.Wait(GVars.shortWait)

		Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "My Projects")
		Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Resourcing", Const.dashBoard + "subMenu_Capacity", Const.dashBoard + 'currentPageHeader', "Capacity")
		Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Resourcing", Const.dashBoard + "subMenu_Requests", Const.dashBoard + 'currentPageHeader', "Requests")
		Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Resourcing", Const.dashBoard + "subMenu_ResourcePlan", Const.dashBoard + 'currentPageHeader', "Resource Plan")
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Timesheets", Const.dashBoard + 'currentPageHeader', "Timesheet Entry")
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Expenses", Const.dashBoard + 'currentPageHeader', "Expenses")
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Portfolio Analysis", Const.dashBoard + 'labelPortfolio', "Portfolio")
		Nav.subMenuItemAndValidate(Const.dashBoard + "menu_Dashboards", Const.dashBoard + "subMenu_Dashboard", Const.dashBoard + 'currentPageHeader', "Dashboards")
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Reports", Const.reports + 'a_ReportFilter', "Reports")
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		Nav.menuItemAndValidate(Const.dashBoard + "menu_MyWork", Const.dashBoard + 'currentPageHeader', "My Work")
		Obj.buttonClickSync(Const.dashBoard + "menuExpand")
	}


	@Keyword
	static def validateMyProjectsTopMenu(){
		Obj.buttonClickSync(Const.dashBoard + "menuExpand")
		Act.Wait(GVars.shortWait)

		Nav.menuItemAndValidate(Const.dashBoard + "menu_MyProjects", Const.dashBoard + 'currentPageHeader', "My Projects")

		Obj.buttonClickSync(Const.myProjects + "menu_Tasks")

		boolean menuItemExists = WebUI.waitForElementClickable(findTestObject(Const.CheckOutTask + 'button_Open'), GVars.longWait, FailureHandling.CONTINUE_ON_FAILURE)
		if(menuItemExists){
			Act.Wait(4)
			checkOutOrViewTask(Const.CheckOutTask + 'button_Open')
			String Url = WebUI.getUrl()
			Act.findSubstringInStringAndReport(Url, "MVC-PRL1-TASKS?")

			if(GVars.browser == "MicrosoftEdge"){
				Act.Wait(2)
			}
		}

		Nav.menuItemAndValidateURL(Const.myProjects + "menu_Forecasts", "MVC-PRL1-FORECASTS?")
		Nav.menuItemAndValidateURL(Const.myProjects + "menu_Finance", "MVC-PRL1-FINANCE?")
		Nav.menuItemAndValidateURL(Const.myProjects + "menu_Resource", "MVC-PRL1-STRATEGICPLAN?")
		Nav.menuItemAndValidateURL(Const.myProjects + "menu_Deliverables", "MVC-PRL1-DELIVERABLES?")
		Nav.menuItemAndValidateURL(Const.myProjects + "menu_Risks", "MVC-PRL1-RISKS?")
		Nav.menuItemAndValidateURL(Const.myProjects + "menu_Issues", "MVC-PRL1-ISSUES?")
		Nav.menuItemAndValidateURL(Const.myProjects + "menu_Approvals", "MVC-PRL1-APPROVALS?")
		//		Nav.menuItemAndValidateURL(Const.myProjects + "menu_Documents", "BITACTION-4?")
		Nav.menuItemAndValidateURL(Const.myProjects + "menu_Billing", "MVC-PRL1-BILLING?")
	}


	@Keyword
	static def checkOutOrViewTask(String buttonOption){
		WebUI.waitForElementClickable(findTestObject(buttonOption), GVars.longWait)
		boolean checkOutPresent = WebUI.verifyElementPresent(findTestObject(Const.CheckOutTask + 'headingCheckOutProjectPlan'), 15)

		if(checkOutPresent){
			Obj.buttonClickSync(buttonOption)
		}
	}


	@Keyword
	static def roleSearch(String searchEntity, String searchValue){
		Obj.buttonClickSync(Const.roleMenu + "a_Field")
		Obj.setEditSync(Const.roleMenu + "searchField", searchEntity)
		Obj.buttonClickSync(Const.roleMenu + "selectFieldValue")

		Obj.buttonClickSync(Const.roleMenu + "a_Type")
		Obj.buttonClickSync(Const.roleMenu + "comparisonType")
		Obj.setEditSync(Const.roleMenu + "input__searchField", searchValue)

		Obj.buttonClickSync(Const.roleMenu + "a_Search")

		Boolean searchResults = WebUI.verifyElementPresent(findTestObject(Const.roleMenu + "searchField"), 5)

		if(searchResults){
			Rep.pass("Role "+ searchValue + " search was successful")
		}else{
			Rep.fail("Role "+ searchValue + " search was not successful")
		}
	}


	@Keyword
	static def resourceSearch(String searchEntity, String searchValue){
		Obj.buttonClickSync(Const.resourceMenu + "a_Field")
		Obj.setEditSync(Const.resourceMenu + "searchField", searchEntity)
		Obj.buttonClickSync(Const.resourceMenu + "selectFieldValue")

		Obj.buttonClickSync(Const.resourceMenu + "a_Type")
		Obj.buttonClickSync(Const.resourceMenu + "comparisonType")
		Obj.setEditSync(Const.resourceMenu + "searchFieldValue", searchValue)

		Obj.buttonClickSync(Const.resourceMenu + "buttonSearch")

		Obj.buttonClickSync(Const.resourceMenu + "a_ExpandResults")
		Obj.buttonClickSync(Const.resourceMenu + "a_Edit")

		def resourceCode = Obj.getEditTextSync(Const.resourceMenu + "codeResult")
		Obj.buttonClickSync(Const.resourceMenu + "nameResult")

		String resourceNme = Obj.getAttributeSync(Const.resourceMenu + "nameResult", "value")

		Act.compareStringAndReport(resourceNme, searchValue)

		return resourceCode
	}


	@Keyword
	static def customerSearch(String searchEntity, String searchValue){
		Obj.buttonClickSync(Const.customerMenu + "a_Field")
		Obj.setEditSync(Const.customerMenu + "searchField", searchEntity)

		Obj.buttonClickSubSync(Const.timesheetFilter + 'select_FieldSelection', searchEntity, "choice")

		Obj.buttonClickSync(Const.customerMenu + "a_Type")
		Obj.buttonClickSync(Const.customerMenu + "comparisonType")
		Obj.setEditSync(Const.customerMenu + "searchFieldValue", searchValue)

		Obj.buttonClickSync(Const.customerMenu + "buttonSearch")
		Obj.buttonClickSync(Const.customerMenu + "a_ExpandResults")
		Obj.buttonClickSync(Const.customerMenu + "a_Edit")

		def customerCode = Obj.getEditTextSync(Const.customerMenu + "codeResult")
		Obj.buttonClickSync(Const.customerMenu + "nameResult")

		String customerNme = Obj.getAttributeSync(Const.customerMenu + "nameResult", "value")

		Act.compareStringAndReport(customerNme, searchValue)

		return customerCode
	}


	@Keyword
	static def projectSearch(String searchEntity, String searchValue){
		Obj.buttonClickSync(Const.projectsMenu + "a_AddRow")
		Obj.buttonClickSync(Const.projectsMenu + "a_Field")
		Obj.setEditSync(Const.projectsMenu + "searchField", searchEntity)
		Obj.buttonClickSync(Const.projectsMenu + "selectFieldValue")

		Obj.buttonClickSync(Const.projectsMenu + "filterTypeDropDown")
		Obj.buttonClickSync(Const.projectsMenu + "filterEquals")
		Obj.setEditSync(Const.projectsMenu + "searchValue", searchValue)

		Obj.buttonClickSync(Const.projectsMenu + "a_Search")
		Obj.buttonClickSync(Const.projectsMenu + "a_ExpandResults")
		Obj.buttonClickSync(Const.projectsMenu + "a_Edit")

		def projectCode = Obj.getEditTextSync(Const.projectsMenu + "codeResult")
		Obj.buttonClickSync(Const.projectsMenu + "nameResult")

		String projectNme = Obj.getAttributeSync(Const.projectsMenu + "nameResult", "value")

		Act.compareStringAndReport(projectNme, searchValue)

		return projectCode
	}


	@Keyword
	static def costCentreSearch(String searchEntity, String searchValue){
		Obj.buttonClickSync(Const.costCentreMenu + "a_Field")
		Obj.setEditSync(Const.costCentreMenu + "searchField", searchEntity)
		Obj.buttonClickSync(Const.costCentreMenu + "selectFieldValue")

		Obj.buttonClickSync(Const.costCentreMenu + "a_Type")
		Obj.buttonClickSync(Const.costCentreMenu + "comparisonType")
		Obj.setEditSync(Const.costCentreMenu + "searchFieldValue", searchValue)

		Obj.buttonClickSync(Const.costCentreMenu + "buttonSearch")

		Obj.buttonClickSync(Const.costCentreMenu + "a_ExpandResults")
		Obj.buttonClickSync(Const.costCentreMenu + "a_Edit")
		Obj.buttonClickSync(Const.costCentreMenu + "nameResult")

		String costCentreNme = Obj.getAttributeSync(Const.costCentreMenu + "nameResult", "value")

		Act.compareStringAndReport(costCentreNme, searchValue)
	}


	@Keyword
	static def departmentSearch(String searchEntity, String searchValue){
		Obj.buttonClickSync(Const.timesheetFilter + "select_Field")
		Obj.setEditSync(Const.timesheetFilter + "input_FieldValue","Department Name")
		Obj.buttonClickSubSync(Const.timesheetFilter + 'select_FieldSelection', "Department Name", "choice")

		Obj.buttonClickSync(Const.departmentMenu + "a_Type")
		Obj.buttonClickSync(Const.departmentMenu + "comparisonType")
		Obj.setEditSync(Const.departmentMenu + "searchFieldValue", searchValue)

		Obj.buttonClickSync(Const.departmentMenu + "buttonSearch")
		Obj.buttonClickSync(Const.departmentMenu + "a_ExpandResults")
		Obj.buttonClickSync(Const.departmentMenu + "a_Edit")

		Obj.buttonClickSync(Const.departmentMenu + "nameResult")

		String departmentNme = Obj.getAttributeSync(Const.departmentMenu + "nameResult", "value")

		Act.compareStringAndReport(departmentNme, searchValue)
	}


	@Keyword
	static def costSearch(String searchEntity, String searchValue){
		Obj.buttonClickSync(Const.costMenu + "a_Field")
		Obj.setEditSync(Const.costMenu + "searchField", searchEntity)
		Obj.buttonClickSync(Const.costMenu + "selectFieldValue")

		Obj.buttonClickSync(Const.costMenu + "a_Type")
		Obj.buttonClickSync(Const.costMenu + "comparisonType")
		Obj.setEditSync(Const.costMenu + "searchFieldValue", searchValue)

		Obj.buttonClickSync(Const.costMenu + "buttonSearch")
		Obj.buttonClickSync(Const.costMenu + "a_ExpandResults")
		Obj.buttonClickSync(Const.costMenu + "a_Edit")

		def costCode = Obj.getEditTextSync(Const.costMenu + "codeResult")
		Obj.buttonClickSync(Const.costMenu + "nameResult")

		String costNme = Obj.getAttributeSync(Const.costMenu + "nameResult", "value")

		Act.compareStringAndReport(costNme, searchValue)

		return costCode
	}


	@Keyword
	static def chargeSearch(String searchEntity, String searchValue){
		Obj.buttonClickSync(Const.chargeMenu + "a_Field")
		Obj.setEditSync(Const.chargeMenu + "searchField", searchEntity)
		Obj.buttonClickSync(Const.chargeMenu + "selectFieldValue")

		Obj.buttonClickSync(Const.chargeMenu + "a_Type")
		Obj.buttonClickSync(Const.chargeMenu + "comparisonType")
		Obj.setEditSync(Const.chargeMenu + "searchFieldValue", searchValue)

		Obj.buttonClickSync(Const.chargeMenu + "buttonSearch")
		Obj.buttonClickSync(Const.chargeMenu + "a_ExpandResults")
		Obj.buttonClickSync(Const.chargeMenu + "a_Edit")

		def chargeCode = Obj.getEditTextSync(Const.chargeMenu + "codeResult")
		Obj.buttonClickSync(Const.chargeMenu + "nameResult")

		String chargeNme = Obj.getAttributeSync(Const.chargeMenu + "nameResult", "value")

		Act.compareStringAndReport(chargeNme, searchValue)

		return chargeCode
	}


	@Keyword
	static def changeUserCredentials(){
		InetAddress addr;
		addr = InetAddress.getLocalHost();
		String hostname = addr.getHostName();
		hostname = hostname.toLowerCase()

		GVars.buildVerFilePath = GVars.configFilePath + GVars.buildVerFile

		String test = GVars.configFilePath + GVars.buildVerFile
		def configBuild = new XmlParser().parse(GVars.buildVerFilePath)
		GVars.buildVer = configBuild.ver.text()
		String buildStr = GVars.buildVer.toString()


		GVars.configFile = GVars.configFilePath + GVars.configFileName

		def config = new XmlParser().parse(GVars.configFile)
		GVars.server = config.url.text()
		GVars.ppmUrl = config.url.text() + GVars.buildVer + "/"
		GVars.user = config.user.text()
		GVars.usrNme = config.usrNme.text()
		GVars.pwordEncryt = config.pwordEncryt.text()
		GVars.altUser = config.altUser.text()

		//		GVars.ppmUrl = GVars.ppmUrl.replace("localhost", hostname)

		GVars.ppmUrl = GVars.ppmUrl.replace("Projects", "KIPAutomation")

	}


	@Keyword
	static def removeCustomTaskField(String taskName){
		KeyedInProjectsPPMLogin(GVars.usrNme, GVars.pwordEncryt)

		Obj.buttonClickSync(Const.mainToolBar + 'settings')
		Obj.buttonClickSync(Const.settingsMenu + 'CustomFields')
		WebUI.selectOptionByLabel(findTestObject(Const.CustomFieldScreen + 'select_CustomFieldType'), "Task", false)
		Act.Wait(1)

		def rowCnt = WebD.getTableRowCount("//div[@id='63f30b25-1931-4053-98a5-eec594abfc22']/div[4]/table")
		if(rowCnt == 2){
			Obj.buttonClickSync(Const.CustomFieldScreen + 'expandRowMenu')
			Obj.buttonClickSync(Const.CustomFieldScreen + 'a_Delete')
			Obj.buttonClickSync(Const.CustomFieldScreen + 'button_OK')
		}else{
			String taskNme = Obj.getEditTextSync(Const.CustomFieldScreen + 'customTaskName')
			if(taskNme == taskName){
				Obj.buttonClickSync(Const.CustomFieldScreen + 'expandRowMenu')
				Obj.buttonClickSync(Const.CustomFieldScreen + 'a_Delete')
				Obj.buttonClickSync(Const.CustomFieldScreen + 'button_OK')
			}else
				for(int intCnt = 2; intCnt < rowCnt; intCnt ++){
					String extraTaskNme = Obj.getEditTextSubIntPosSync(Const.CustomFieldScreen + 'customTaskNameExtraRow', intCnt, "row")
					if(extraTaskNme == taskName){
						Obj.buttonClickSubIntPosSync(Const.CustomFieldScreen + 'expandRowMenuExtraRows', intCnt, "row")
						Obj.buttonClickSubIntPosSync(Const.CustomFieldScreen + 'a_DeleteExtraRows', intCnt, "row")
						Obj.buttonClickSync(Const.CustomFieldScreen + 'button_OK')
						break
					}
				}
		}

		KeyedInProjectsPPMLogout()
		WebUI.closeBrowser()
	}


	@Keyword
	static def changeProject(String topLevel, String custProj, String company, String project){
		String currentProject = Obj.getEditTextSync(Const.myProjects + 'currentProject')
		if(currentProject != project){
			Obj.buttonClickSync(Const.projTree + 'projectTree')
			Obj.buttonClickSubSync(Const.projTree + 'tree_Level1', topLevel, "topLevel")
			Obj.buttonClickSync(Const.projTree + 'projectTree')
			Obj.buttonClickSubSync(Const.projTree + 'tree_Level2', custProj, "custProj")
			Obj.buttonClickSync(Const.projTree + 'projectTree')
			Obj.buttonClickSubSync(Const.projTree + 'tree_Level3', company, "company")
			Obj.buttonClickSync(Const.projTree + 'projectTree')
			Obj.buttonClickSubSync(Const.projTree + 'tree_Level4', project, "project")
			Act.Wait(GVars.shortWait)
		}
	}


	@Keyword
	static def altChangeProject(String topLevel, String custProj, String company, String project){
		String currentProject = Obj.getEditTextSync(Const.myProjects + 'currentProject')
		if(currentProject != project){
			Obj.buttonClickSync(Const.projTree + 'projectTree')
			Obj.buttonClickSubSync(Const.projTree + 'a_Top_Level', topLevel, "topLevel")
			Obj.buttonClickSync(Const.projTree + 'projectTree')
			Obj.buttonClickSubSync(Const.projTree + 'a_Level2', custProj, "custProj")
			Obj.buttonClickSync(Const.projTree + 'projectTree')
			Obj.buttonClickSubSync(Const.projTree + 'a_Level3', company, "company")
			Obj.buttonClickSync(Const.projTree + 'projectTree')
			Obj.buttonClickSubSync(Const.projTree + 'a_Level4', project, "project")
			Com.edgeSync(2)
			Act.Wait(2)
		}
	}


	@Keyword
	static def changeProjectLoop(String topLevel, String custProj, String company, String project){
		String currentProject = Obj.getEditTextSync(Const.myProjects + 'currentProject')
		if(currentProject != project){
			String[] menuItems = [topLevel, custProj, company, project]
			for(int intCnt = 0; intCnt < 4; intCnt ++){
				Obj.buttonClickSync(Const.projTree + 'projectTree')
				Obj.buttonClickSubSync(Const.projTree + 'a_MenuOption', menuItems[intCnt], "menuItem")
			}
		}
	}


	@Keyword
	static def verifySavePopUp(){
		Act.Wait(1)
		boolean elementVisible = WebUI.verifyElementPresent(findTestObject(Const.kip4 + 'Popup_Saved Successfully'), GVars.longWait, FailureHandling.OPTIONAL)
		if (elementVisible){
			WebUI.waitForElementNotPresent(findTestObject(Const.kip4 + 'Popup_Saved Successfully'), GVars.longWait, FailureHandling.OPTIONAL)
		}
	}


	@Keyword
	static def verifySavePopUpText(){
		Act.Wait(1)
		boolean elementVisible = WebUI.verifyElementPresent(findTestObject(Const.kip4 + 'Popup_Saved Successfully'), GVars.longWait, FailureHandling.OPTIONAL)
		if (elementVisible){
			String popupMsg = Obj.getEditTextSync(Const.kip4 + 'Popup_Saved Successfully')
			Act.findSubstringInStringAndReport(popupMsg, "Saved Successfully")
			WebUI.waitForElementNotPresent(findTestObject(Const.kip4 + 'Popup_Saved Successfully'), GVars.longWait, FailureHandling.OPTIONAL)
		}
	}


	@Keyword
	static def verifyDeletePopUpText(){
		boolean elementVisible = WebUI.verifyElementPresent(findTestObject(Const.kip4 + 'Popup_Saved Successfully'), GVars.longWait, FailureHandling.OPTIONAL)
		if (elementVisible){
			String popupMsg = Obj.getEditTextSync(Const.kip4 + 'Popup_Saved Successfully')
			Act.findSubstringInStringAndReport(popupMsg, "Deleted Successfully")
			WebUI.waitForElementNotPresent(findTestObject(Const.kip4 + 'Popup_Saved Successfully'), GVars.longWait, FailureHandling.OPTIONAL)
		}
	}

	@Keyword
	static def verifyPopUpText(String textValue){
		boolean elementVisible = WebUI.verifyElementPresent(findTestObject(Const.kip4 + 'Popup_Saved Successfully'), GVars.longWait, FailureHandling.OPTIONAL)
		if (elementVisible){
			String popupMsg = Obj.getEditTextSync(Const.kip4 + 'Popup_Saved Successfully')
			Act.findSubstringInStringAndReport(popupMsg, textValue)
			WebUI.waitForElementNotPresent(findTestObject(Const.kip4 + 'Popup_Saved Successfully'), GVars.longWait, FailureHandling.OPTIONAL)
		}
	}


	@Keyword
	static def selectEntityViaSearch(String entityVal){
		Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")

		boolean filterExist = WebUI.verifyElementNotPresent(findTestObject(Const.kip4Generic + "filterCollapsed"), GVars.midWait, FailureHandling.OPTIONAL)

		if(!filterExist){
			Obj.buttonClickSync(Const.kip4Generic + "expandFilters")
		}

		Obj.buttonClickSync(Const.search + "search_DropDown")
		Obj.buttonClickSubSync(Const.search + "filterType", entityVal, "label")
		Act.Wait(2)
	}


	@Keyword
	static def expandFilter(){
		boolean filterExist = WebUI.verifyElementPresent(findTestObject(Const.kip4Generic + "filterCollapsed"), GVars.midWait, FailureHandling.OPTIONAL)

		if(filterExist){
			Obj.buttonClickSync(Const.kip4Generic + "expandFilters")
			Obj.buttonClickSync(Const.kip4Generic + "expandFilters")
		}
		Act.Wait(2)
	}
}
