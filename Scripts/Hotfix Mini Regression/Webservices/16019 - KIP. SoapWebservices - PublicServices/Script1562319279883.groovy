import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import global.Action as Act
import internal.GlobalVariable as GVars
import global.Report as Rep
import myProjects.Action as mAct
import global.Action as gAct
import keyedInProjects.Constants as Const
import webservices.Action as wAct

String filePath = GVars.objRep + "KeyedInProjects\\Soap_Webservices\\publicServices\\"

Rep.nextTestStep("Launch [Web Browser] and navigate to https:" + GVars.ppmUrl + "/KIPWebPortal/PublicServices/KIPWebService.asmx")

	Act.openBrowserOnUrlAndMaximise(GVars.ppmUrl + '/KIPWebPortal/PublicServices/KIPWebService.asmx')
	
	
Rep.nextTestStep("Click on each [Web Service] and verify the page is correctly displayed and no exception is found")

	wAct.navigateAndValidateLink(Const.public_WebServices, filePath)

