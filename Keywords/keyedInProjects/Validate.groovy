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
import global.Action as gAct
import global.Object as gObj
import global.Validate as gVal
import global.Common as gCom
import global.Report as Rep
import global.WebDriverMethods as WebD
import keyedInProjects.Constants as Const
import myProjects.Action as mAct
import models.Expense


public class Validate {

	@Keyword
	static def correctPPMLogin(String expectedUrl, String expectedUser){
		gObj.elementVisibleSync(Const.newUI + "a_ProfileSettings")
		String Url = WebUI.getUrl()
		gAct.compareStringAndReport(expectedUrl, Url)

		gObj.buttonClickSync(Const.newUI + "a_ProfileSettings")
		String currentUserName = gObj.getEditTextSync(Const.newUI + "txt_CurrentUser")

		if(GVars.browser == "MicrosoftEdge"){
			currentUserName = currentUserName.replace(" \n", "")
		}

		gAct.compareStringAndReport(expectedUser, currentUserName)
		gObj.buttonClickSync(Const.newUI + "a_ProfileSettings")
	}


	@Keyword
	static def businessEntitiesMenuItems(String folderUrl){
		ArrayList<String> busFileNames = mAct.getAllFilesNamesInFolder(folderUrl)
		for(String item : busFileNames){
			String menuItem = item.replace(".rs", "")
			WebUI.click(findTestObject(Const.mainToolBar + "administration"))
			gAct.Wait(2)
			if(menuItem == "CustomerGroups"){
				gAct.Wait(2)
			}
			WebUI.click(findTestObject(Const.busEntMenu + menuItem))
			gAct.Wait(1)
			String urlVal = WebUI.getUrl()
			String menuItemUrl = menuItem.replace("a_", "")

			switch (menuItemUrl){

				case "Activities":
					menuItemUrl = "Activity"
					break

				case "Categories":
					menuItemUrl = "Category"
					break

				case "CustomerGroups":
					menuItemUrl = "ClientGroup"
					break

				case "Customers":
					menuItemUrl = "Client"
					break

				case "DeliverableTypes":
					menuItemUrl = "MilestoneType"
					break

				case "MitigationPlanItemTypes":
					menuItemUrl = "RiskActionType"
					break

				default:
					menuItemUrl =  menuItemUrl.substring(0, menuItemUrl.length() - 1)
					break
			}
			gAct.findSubstringInStringAndReport(urlVal, menuItemUrl)
			gAct.Wait(1)
		}
	}


	@Keyword
	static def financialsMenuItems(String folderUrl){
		ArrayList<String> finFileNames = mAct.getAllFilesNamesInFolder(folderUrl)
		for(String item : finFileNames){
			String menuItem = item.replace(".rs", "")
			WebUI.click(findTestObject(Const.mainToolBar + "administration"))
			WebUI.click(findTestObject(Const.financialsMenu + menuItem))
			gAct.Wait(2)
			String urlVal = WebUI.getUrl()
			String menuItemUrl = menuItem.replace("a_", "")

			if(menuItemUrl == "NominalCodes"){
				menuItemUrl = "Nominal"
			} else{
				menuItemUrl =  menuItemUrl.substring(0, menuItemUrl.length() - 1)
			}

			if(menuItemUrl == "ExchangeRate"){
				WebUI.verifyTextPresent("Currency", false)
			}else if(menuItemUrl == "Currencie"){
				WebUI.verifyTextPresent("Currency", false)
			}else if (menuItemUrl == "AdjustExpenseExchangeRate"){
				String modalheaderText = WebUI.getText(findTestObject(Const.kip4Generic + "header_ModalTitle"))
				gAct.compareStringAndReport(modalheaderText, "Adjust Expense Exchange Rate")
			}else if (menuItemUrl == "AdjustTimesheetExchangeRate"){
				String modalheaderText = WebUI.getText(findTestObject(Const.kip4Generic + "header_ModalTitle"))
				gAct.compareStringAndReport(modalheaderText, "Adjust Timesheet Exchange Rate")
			}else if (menuItemUrl == "AdjustCharge") {
				gAct.Wait(2)
				String modalheaderText = WebUI.getText(findTestObject(Const.kip4Generic + "header_ModalTitle"))
				gAct.compareStringAndReport(modalheaderText, "Adjust Charge Rates")
			}else if (menuItemUrl == "AdjustCost") {
				String modalheaderText = WebUI.getText(findTestObject(Const.kip4Generic + "header_ModalTitle"))
				gAct.compareStringAndReport(modalheaderText, "Adjust Cost Rates")
			}else{
				gAct.findSubstringInStringAndReport(urlVal, menuItemUrl)
				gAct.Wait(1)
			}
		}
	}


	@Keyword
	static def securityMenuItems(String folderUrl){
		ArrayList<String> secFileNames = mAct.getAllFilesNamesInFolder(folderUrl)
		for(String item : secFileNames){
			if(!item.contains("a_")){
				continue
			}
			String menuItem = item.replace(".rs", "")
			WebUI.click(findTestObject(Const.mainToolBar + "administration"))
			WebUI.click(findTestObject(Const.securityMenu + menuItem))
			String urlVal = WebUI.getUrl()
			String menuItemUrl = menuItem.replace("a_", "")

			if(menuItemUrl == "LoginProfiles"){
				menuItemUrl = "LoginTemplate"
			}else{
				menuItemUrl =  menuItemUrl.substring(0, menuItemUrl.length() - 1)
			}

			gAct.findSubstringInStringAndReport(urlVal, menuItemUrl)
			gAct.Wait(1)
		}
	}


	@Keyword
	static def skillsMenuItems(String folderUrl){
		ArrayList<String> skiFileNames = mAct.getAllFilesNamesInFolder(folderUrl)
		for(String item : skiFileNames){
			String menuItem = item.replace(".rs", "")
			WebUI.click(findTestObject(Const.mainToolBar + "administration"))
			WebUI.click(findTestObject(Const.skillsMenu + menuItem))
			String urlVal = WebUI.getUrl()
			String menuItemUrl = menuItem.replace("a_", "")
			menuItemUrl =  menuItemUrl.substring(0, menuItemUrl.length() - 1)
			gAct.findSubstringInStringAndReport(urlVal, menuItemUrl)
			gAct.Wait(1)
		}
	}


	@Keyword
	static def toolsMenuItems(String folderUrl){
		ArrayList<String> tooFileNames = mAct.getAllFilesNamesInFolder(folderUrl)
		String menuItem = tooFileNames[0].replace(".rs", "")
		WebUI.click(findTestObject(Const.mainToolBar + "administration"))
		WebUI.click(findTestObject(Const.toolsMenu + menuItem))
		gAct.Wait(2)
		String modalheaderText = WebUI.getText(findTestObject(Const.kip4Generic + "header_ModalTitle"))
		gAct.compareStringAndReport(modalheaderText, "Code Converter")

		WebUI.click(findTestObject(Const.kip4Generic + "button_Close"))
	}


	@Keyword
	static def settingsMenuItems(String folderUrl){
		String urlVal
		String heading
		ArrayList<String> fileNames = mAct.getAllFilesNamesInFolder(folderUrl)
		for(String item : fileNames){
			Rep.info("Menu item : " + item)
			if(item.contains(".rs")){
				String menuItem = item.replace(".rs", "")
				gAct.Wait(1)
				gObj.buttonClickSync(Const.mainToolBar + "settings")
				gAct.Wait(1)
				WebUI.click(findTestObject(Const.settingsMenu + menuItem))

				gAct.Wait(1)

				if(menuItem == "WorkingTimes"){
					WebUI.verifyTextPresent("Working Times", false)
				}else if((menuItem == "CustomFields")||(menuItem == "InvoiceTemplates")||(menuItem == "ProjectTypes")||(menuItem == "ScheduledReports")||(menuItem == "WebHooks")||(menuItem == "WorkflowNotifications")){
					urlVal = WebUI.getUrl()
					String menuItemUrl =  menuItem.substring(0, menuItem.length() - 1)
					gAct.findSubstringInStringAndReport(urlVal, menuItemUrl)

					if(menuItem == "CustomFields"){
						WebUI.click(findTestObject(Const.mainToolBar + "settings"))
					}
				}else if(menuItem == "ProjectWorkflows"){
					urlVal = WebUI.getUrl()
					gAct.findSubstringInStringAndReport(urlVal, "Workflows")
				}else{
					WebUI.focus(findTestObject(Const.settingsModal + 'configModalHeading'))
					heading = WebUI.getText(findTestObject(Const.settingsModal + 'configModalHeading'))

					heading = heading.replaceAll(" ", "")

					if(menuItem == "TimeExpenseSettings"){
						heading = heading.replaceAll("&", "")
					}

					gAct.findSubstringInStringAndReport(heading, menuItem)
					gAct.Wait(1)

					if(menuItem == "PlannningPeriods"){
						gAct.Wait(1)
					}

//					if(menuItem == "ScheduledJobs"){
//						WebD.clickElement("//div[text()='Close']")
//					}
					if(menuItem == "ProjectLevelSettings"){
						WebD.clickElement("(//button[text()='Close'])[1]")
					}
					
					else{
						WebUI.click(findTestObject(Const.settingsModal + 'button_Close'))
					}
				}
			}
		}


		WebUI.refresh() //refresh required otherwise modal close and heading aren't recognised on next loop
		gAct.Wait(2)
	}


	@Keyword
	static def settingsMenuItems_Old(String folderUrl){
		String urlVal
		String heading
		ArrayList<String> fileNames = mAct.getAllFilesNamesInFolder(folderUrl)
		for(String item : fileNames){
			Rep.info("Menu item : " + item)
			if(item.contains(".rs")){
				String menuItem = item.replace(".rs", "")
				WebUI.click(findTestObject(Const.mainToolBar + "settings"))
				gAct.Wait(1)
				WebUI.click(findTestObject(Const.settingsMenu + menuItem))

				gCom.ieSync()
				gCom.edgeSync()
				gCom.firefoxSync()

				if(menuItem == "WorkingTimes"){
					WebUI.verifyTextPresent("Working Times", false)
				}else if((menuItem == "CustomFields")||(menuItem == "InvoiceTemplates")||(menuItem == "ProjectLifecycles")||(menuItem == "ScheduledReports")||(menuItem == "ProjectTypes")||(menuItem == "WebHooks")||(menuItem == "WorkflowNotifications")){
					urlVal = WebUI.getUrl()
					String menuItemUrl =  menuItem.substring(0, menuItem.length() - 1)
					gAct.findSubstringInStringAndReport(urlVal, menuItemUrl)
				}else if(menuItem == "ProjectWorkflows"){
					urlVal = WebUI.getUrl()
					gAct.findSubstringInStringAndReport(urlVal, "Workflows")
				}else{

					if(menuItem == "ProjectWorkflowSettings"){
						heading = WebUI.getText(findTestObject(Const.settingsModal + 'Workflow_Heading'))
					}else{
						heading = WebUI.getText(findTestObject(Const.settingsModal + 'modalHeading'))
					}

					heading = heading.replaceAll(" ", "")

					if(menuItem == "ProjectLevelSettings"){
						menuItem = "ProjectLevels"
					}

					if(menuItem == "ProjectSettings"){
						menuItem = "ProjectConfiguration"
					}

					gAct.findSubstringInStringAndReport(heading, menuItem)

					if(menuItem == "ProjectWorkflowSettings"){
						WebUI.click(findTestObject(Const.settingsModal + 'WorkFlow_button_Close'))
					}else{

						WebUI.click(findTestObject(Const.settingsModal + 'button_Close'))
					}
					WebUI.refresh() //refresh required otherwise modal close and heading aren't recognised on next loop
					gAct.Wait(2)
				}
				gAct.Wait(2)
			}
		}
	}


	@Keyword
	static def expense(Expense expense, int row){
		gAct.Wait(1)
		gObj.buttonClickSubIntPosSync(Const.expense + 'inlineMenu', row, "row")
//		WebUI.click(findTestObject(Const.expense + 'inlineMenu', [('row'): row]))
		gAct.Wait(1)
		WebD.clickElement("//div[@class='btn-group btn-group-xs shiny open']//li[1]//a[1]")
		gAct.Wait(1)
		
		gVal.objectAttributeValue(Const.expense + 'input_Date', 'value', expense.date)
		WebUI.verifyOptionSelectedByValue(findTestObject(Const.expense + 'select_Department'), expense.department, false, 5)
		gVal.objectAttributeValue(Const.expense + 'input_CostCentre', 'value', expense.costCentre)
		//WebUI.verifyOptionSelectedByValue(findTestObject(Const.expense + 'select_Project'), expense.project, false, 5)
		WebUI.verifyOptionSelectedByValue(findTestObject(Const.expense + 'select_Expense'), expense.expenseType, false, 5)
		String catVal = WebUI.getAttribute(findTestObject(Const.expense + 'select_Category'), 'value')
		gAct.compareStringAndReport(catVal, expense.category)
		gVal.objectAttributeValue(Const.expense + 'input_Gross', 'value', expense.gross)
		gVal.objectAttributeValue(Const.expense + 'input_Net', 'value', expense.net)
//		gObj.buttonClickSync(Const.expense + 'button_Close')
	}


	@Keyword
	static def modalTitle(String expModalTitle) {
		String actText = gObj.getEditTextSync(Const.kip4Generic + "header_ModalTitle")
		int counter = 0
		while(actText == "Loading"  && counter < 5) {
			gAct.Wait(1)
			actText = gObj.getEditTextSync(Const.kip4Generic + "header_ModalTitle")
			counter ++
		}

		gAct.compareStringAndReport(actText, expModalTitle)
	}
}
