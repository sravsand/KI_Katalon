import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint

import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys


WebUI.openBrowser('')

WebUI.maximizeWindow();

WebUI.navigateToMaskedUrl("https://kipstaging.keyedinuat.com/Workflows/Web/KIPWebPortal/secure/sec_login.aspx")

WebUI.verifyElementPresent(findTestObject('Object Repository/LoginPage/LoginButton'), 15)

WebUI.setText(findTestObject('Object Repository/LoginPage/UserNameTextBox'), "SANDEEP@WORKFLOWS.KI")

WebUI.setText(findTestObject('Object Repository/LoginPage/PasswordTextBox'), "KatalonSelenium@123")

WebUI.click(findTestObject('Object Repository/LoginPage/LoginButton'))

WebUI.verifyElementText(findTestObject('Object Repository/HomePage/HomePageTitle'), "Home")

WebUI.click(findTestObject('Object Repository/HomePage/SettingButton'))

//WebUI.verifyElementText(findTestObject('Object Repository/HomePage/headerTextOfConfigurationPage'), "Configuration")

WebUI.click(findTestObject('Object Repository/HomePage/DefaultTypeOption'))

//WebUI.verifyElementText(findTestObject('Object Repository/HomePage/headerTextOfConfigurationPage'), "Default Types")

CustomKeywords.'defaultType.CommonMethod.addDefaultDataTypeDetailsAndVerifyDefaultTypeIsAdded'()

WebUI.closeBrowser()
