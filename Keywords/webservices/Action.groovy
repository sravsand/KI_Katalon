package webservices

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
import myProjects.Action as mAct
import global.Action as gAct
import global.Object as gObj
import global.WebDriverMethods as WebD
import keyedInProjects.Constants as Const

import internal.GlobalVariable

public class Action {
	
	@Keyword
	static def navigateAndValidateLink(String oRFolder, String filePath){
		ArrayList<String> secFileNames = mAct.getAllFilesNamesInFolder(filePath)
		for(String item : secFileNames){
			String menuItem = item.replace(".rs", "")
			if(menuItem == "a_UpdateDepartment"){				
				gObj.scrollToElementSync(oRFolder + menuItem)
			}
			gObj.buttonClickSync(oRFolder + menuItem)


			String heading = gObj.getEditTextSync(Const.webservices + 'url_Heading')
			String menuHeading = menuItem.replace("a_", "")
			gAct.Wait(1)
			gAct.findSubstringInStringAndReport(heading, menuHeading)
					
			WebUI.back()
		}
	}
}
