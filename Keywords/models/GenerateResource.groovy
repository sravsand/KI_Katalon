package models

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

import internal.GlobalVariable
import models.Resource

public class GenerateResource {

	static def createNewResource(String[] newResource){
		Resource resource = new Resource()

		resource.name = newResource[0]
		resource.resourceType = newResource[1]
		resource.department = newResource[2]
		resource.role = newResource[3]
		resource.location = newResource[4]
		resource.workingTime = newResource[5]
		resource.costCentre = newResource[6]
		resource.lineManger = newResource[7]
		resource.email = newResource[8]
		resource.telephone = newResource[9]
		resource.capacity = newResource[10]
		resource.cost_std = newResource[11]
		resource.cost_ovr = newResource[12]
		resource.charge_std = newResource[13]
		resource.charge_ovr = newResource[14]
		resource.code = newResource[15]
		resource.notes = newResource[16]
		
		return resource
	}


	static def createNewResourceDD(def newResource, def row){
		Resource resource = new Resource()

		resource.name = newResource.getValue("name", row)
		resource.resourceType = newResource.getValue("resourceType", row)
		resource.department = newResource.getValue("department", row)
		resource.role = newResource.getValue("role", row)
		resource.location = newResource.getValue("location", row)
		resource.workingTime = newResource.getValue("workingTime", row)
		resource.costCentre = newResource.getValue("costCentre", row)
		resource.lineManger = newResource.getValue("lineManager", row)
		resource.email = newResource.getValue("email", row)
		resource.telephone = newResource.getValue("telephone", row)
		resource.capacity = newResource.getValue("capacity", row)
		resource.cost_std = newResource.getValue("cost_std", row)
		resource.cost_ovr = newResource.getValue("cost_ovr", row)
		resource.charge_std = newResource.getValue("charge_std", row)
		resource.charge_ovr = newResource.getValue("charge_ovr", row)

		return resource
	}
}

