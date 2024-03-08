package search

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
import buildingBlocks.createComponents
import models.Assignment
import models.GenerateAssignment
import models.Login
import models.GenerateLogin
import models.Resource
import models.GenerateResource
import models.CostCentre
import models.GenerateCostCentre
import internal.GlobalVariable
import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj

public class Populate {

	static def newUser(String costCentreNew){
		String code = Com.generateRandomText(10)
		code = code.toUpperCase()
		String firstName = Com.generateRandomText(5)
		String surname = Com.generateRandomText(8)
		String name = firstName + " " + surname
		String userNme = firstName.take(1) + surname + "@workflows.com"

		String ResourceName = Com.generateRandomText(10)
		String newDate = Com.todayDate()
		String[] newResource = [
			name,
			"Employee",
			"KIP Dev Team 1",
			"KIP 1 Team Lead",
			"Cleckheaton",
			"Full Time Calendar (My Region)",
			costCentreNew,
			"Martin Phillips",
			userNme,
			"",
			"5",
			"",
			"",
			"",
			"",
			"",
			""
		]
		Resource resource = GenerateResource.createNewResource(newResource)
		createComponents.createResource(resource)

		String[] newLogin = [name, code, userNme, resource.code, "TEst1234"]
		Login login = GenerateLogin.createLogin(newLogin)
		createComponents.createLogin(login)

		return resource
	}


	static def newCostCentre(){
		String costCentreName = Com.generateRandomText(6)
		String costCentreCode = costCentreName.toUpperCase()
		String[] newCostCentre = [costCentreName, costCentreCode, ""]
		CostCentre costCentre = GenerateCostCentre.createCostCentre(newCostCentre)
		String costCentreNew = createComponents.costCentre(costCentre)
		return costCentreNew
	}
}
