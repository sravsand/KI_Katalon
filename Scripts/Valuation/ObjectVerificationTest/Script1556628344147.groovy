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

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import global.Report as Rep


//Define Variables
//String usrNme = 'dlees1@keyedin.com'
//String pwordEncryt = 'OWPbtHE8jFdpqHwAeGH99A=='
//String usrNmeCap = 'DLEES1@KEYEDIN.COM'

String usrNme = 'dlees@keyedin.com'
String pwordEncryt = 'wg6dpdfG/EgNl7QzLyjpjA=='  //TEst1234
String usrNmeCap = 'DLEES@KEYEDIN.COM'

String user = 'Dave Lees'
String newPasswordEncrypt = '' //populate when require using this.


//Test Step
Rep.nextTestStep("Open browser and navigate to Keyedin Project. Handling Terms & conditions and change of password if required.")
	Act.KeyedInProjectsPPMLogin(usrNme, pwordEncryt)
//	Act.changePasswordOnRequest(usrNmeCap, pwordEncryt, newPasswordEncrypt)


//Test Step
Rep.nextTestStep("Verify correct login.")
	Val.correctPPMLogin(Const.ppmUrl + "Dashboard/Index/MVC-MYWORK-HOME", user)

	
//Test Step
Rep.nextTestStep("Verify Left hand menu navigate.")
	Act.validateLeftHandMenuNavigation()
	

//Test Step
Rep.nextTestStep("Navigate My Projects top menu and verify.")
	Act.validateMyProjectsTopMenu()
	

