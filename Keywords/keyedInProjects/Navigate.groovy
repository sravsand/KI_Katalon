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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import global.Action as Act
import global.Common as Com
import global.Object as Obj

public class Navigate {

	static def menuItemAndValidate(String menuItem, String textObject, String expectedText){
		boolean menuItemExists = WebUI.verifyElementPresent(findTestObject(menuItem), GVars.longWait, FailureHandling.CONTINUE_ON_FAILURE)
		if(menuItemExists){
			Obj.buttonClickSync(menuItem)
			def objExt = Obj.elementPresentSubSync(textObject, expectedText, "header")
		}
	}


	static def menuItemAndValidateURL(String menuItem, String expectedText){
		boolean menuItemExists = WebUI.verifyElementPresent(findTestObject(menuItem), 10, FailureHandling.CONTINUE_ON_FAILURE)
		if(menuItemExists){
			WebUI.waitForElementClickable(findTestObject(menuItem), GVars.longWait)
			WebUI.click(findTestObject(menuItem))
			Act.Wait(GVars.shortWait)
			Com.edgeSync(2)
			String Url = WebUI.getUrl()
			Act.findSubstringInStringAndReport(Url, expectedText)
		}
	}


	static def subMenuItemAndValidate(String menuItem, String subMenuItem, String textObject, String expectedText){
		boolean menuItemExists = WebUI.verifyElementPresent(findTestObject(menuItem), GVars.longWait, FailureHandling.CONTINUE_ON_FAILURE)
		if(menuItemExists){
			WebUI.focus(findTestObject(menuItem))
			Obj.buttonClickSync(menuItem)
			Act.Wait(GVars.shortWait)
			menuItemAndValidate(subMenuItem, textObject, expectedText)
		}
	}


	@Keyword
	static def selectSearchFilter(String filterName) {
		menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")
		Obj.buttonClickSync(Const.search + "search_DropDown")
		Obj.buttonClickSubSync(Const.search + "filterType", filterName, "label")
		WebUI.waitForElementPresent(findTestObject(Const.columnPicker + "a_Search"), GVars.longWait, FailureHandling.CONTINUE_ON_FAILURE)
	}
}
