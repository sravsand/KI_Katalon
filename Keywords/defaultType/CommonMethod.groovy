package defaultType

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject


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
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.testobject.ConditionType

import internal.GlobalVariable
import org.openqa.selenium.WebElement

public class CommonMethod {
	boolean value;
	@Keyword

	public void addDefaultDataTypeDetailsAndVerifyDefaultTypeIsAdded() throws InterruptedException{
		List<WebElement> defaultList = WebUI.findWebElements(findTestObject('Object Repository/DefaultPage/defaultTypeList'),10)
		for (WebElement e in defaultList) {
			String value = e.getAttribute("value")
			Thread.sleep(2000)
			String defaultTypeText = e.getText().trim();
			String dataTypeName = defaultTypeText.trim() + " " + System.nanoTime()
			String description = defaultTypeText.trim() + " " + System.nanoTime()
			value= addDefaultDataType(defaultTypeText, dataTypeName, description, value)
			if(value) {
				Thread.sleep(4000);
				WebUI.verifyElementText(getCreatedWorkflowName().trim(), dataTypeName)
				WebUI.verifyElementText(getCreatedDefaultTypeDescription().trim(), description)
			}
		}
	}


	@Keyword
	public Boolean addDefaultDataType(String defaultDataType, String dataTypeName, String description,String value1) throws InterruptedException{

		WebUI.click(findTestObject('Object Repository/DefaultPage/DefaultTypeListDropDown'))

		WebUI.selectOptionByValue(findTestObject('Object Repository/DefaultPage/DefaultTypeListDropDown'), value1, false, FailureHandling.CONTINUE_ON_FAILURE)

		Thread.sleep(3000);
		int size = WebUI.findWebElements(findTestObject('Object Repository/DefaultPage/AddButton'), 10).size()
		boolean value =false;

		if(size > 0) {
			if (!defaultDataType.contains("Project")) {
				WebUI.click(findTestObject('Object Repository/DefaultPage/AddButton'))
				addDefaultType(dataTypeName, description);
				value = true;
			} else {
				value = false;
			}
		}
	}

	@Keyword
	public void addDefaultType(String name, String description) throws InterruptedException {
		WebUI.setText(findTestObject('Object Repository/DefaultPage/nameTextBox'),name)
		WebUI.setText(findTestObject('Object Repository/DefaultPage/descriptionTextBox'), description)
		WebUI.click(findTestObject('Object Repository/DefaultPage/workflowTextBox'))
		WebUI.click(findTestObject('Object Repository/DefaultPage/selectWorkFlowButton'))
		WebUI.click(findTestObject('Object Repository/DefaultPage/saveAndCloseButton'))
		Thread.sleep(4000)
	}

	@Keyword
	public String getCreatedWorkflowName() throws Exception{
		String text =WebUI.getText(findTestObject('Object Repository/DefaultPage/createdDefaultTypeName'),10)
		return text.trim();
	}

	@Keyword
	public String getCreatedDefaultTypeDescription()  throws E{
		return WebUI.getText(findTestObject('Object Repository/DefaultPage/createdDefaultTypeDescription'),10)
	}
}
