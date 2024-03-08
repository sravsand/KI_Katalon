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
import myProjects.Validate as mVal
import myProjects.Action as mAct
import search.Validate as sVal


String addFavourite = "Activity"

//change from default login
GVars.configFileName = 	"katalonConfigAuto10.xml"
Act.changeUserCredentials()

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	
Rep.nextTestStep("Click on [Favourites] (star icon) located at the top right hand side of the menu bar")
	gObj.buttonClickSync(Const.favourites + 'Favourite')


Rep.nextTestStep("Verify [Favourites] lists are displayed (n.b. these favourites will be unique for each user based on their logon credentials)")
	WebUI.verifyElementPresent(findTestObject(Const.favourites + 'a_MenuVal', [('item') : "Action View"]), GVars.longWait)
	WebUI.verifyElementPresent(findTestObject(Const.favourites + 'a_MenuVal', [('item') : "Project"]), GVars.longWait)


Rep.nextTestStep("If displayed click on any of the list from [Favourites] ")
	String menuItem = "Project"	
	gObj.buttonClickSubSync(Const.favourites + 'favouriteMenuItem', menuItem, "item")
	gVal.objectText(Const.favourites + 'pageHeader', menuItem)
	gAct.Wait(2)

	
Rep.nextTestStep("Click on [Search] from the left hand side menu")
	Nav.menuItemAndValidate(Const.dashBoard + "menu_Search", Const.dashBoard + 'currentPageHeader', "Search")


Rep.nextTestStep("Select [Reports] from the dropdown")
	gObj.buttonClickSync(Const.search + "search_DropDown")
	gObj.buttonClickSubSync(Const.searchFilters + 'selectSearchItemByValue', "Reports", "value")
	gAct.Wait(1)


Rep.nextTestStep("Click the [Inline menu] (arrow icon) next to the required report and click on the star icon (Favourite)")
	sVal.reportAddFavourite(addFavourite)
	WebUI.waitForElementNotVisible(findTestObject(Const.searchFilters + 'Message_Added'), GVars.longWait, FailureHandling.OPTIONAL)
	gObj.buttonClickSync(Const.favourites + 'Favourite')
	WebUI.verifyElementPresent(findTestObject(Const.favourites + 'a_MenuVal', [('item') : addFavourite]), GVars.longWait)

