package global

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
import org.openqa.selenium.Keys

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import global.Report as Rep
import global.Validate as Val
import global.Action as Act
import global.Object as Obj
import global.Common as Com
import global.WebDriverMethods as WebD

public class Object {

	@Keyword
	static def getObjectRepositoryIdentifierValue(String objectPath, String identifier){
		TestObject tObj = findTestObject(objectPath)
		String identifierValue =  "${tObj.findPropertyValue(identifier)}"   //e.g. xpath
		return identifierValue
	}


	@Keyword
	static def verifyPresentAndReport(String objectPath){
		boolean elementPresent = WebUI.verifyElementPresent(findTestObject(objectPath), 5)
		if(elementPresent){
			Rep.pass("Object : " + objectPath + " present as expected.")
		}else{
			Rep.fail("Object : " + objectPath + " not present.")
		}
	}


	@Keyword
	static def verifyVisibleAndReport(String objectPath){
		boolean elementVisible = WebUI.verifyElementVisible(findTestObject(objectPath))
		if(elementVisible){
			Rep.pass("Object : " + objectPath + " is visible as expected.")
		}else{
			Rep.fail("Object : " + objectPath + " not visible.")
		}
	}


	@Keyword
	static def verifyNotPresentAndReport(String objectPath){
		boolean elementNotPresent = WebUI.verifyElementNotPresent(findTestObject(objectPath), 5)
		if(elementNotPresent){
			Rep.pass("Object : " + objectPath + " not present as expected.")
		}else{
			Rep.fail("Object : " + objectPath + " present.")
		}
	}


	@Keyword
	static def clearAndSetText(String objectName, String inputValue){
		WebUI.waitForElementPresent(findTestObject(objectName), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectName), GVars.midWait, FailureHandling.OPTIONAL)
		Act.milliWait(500)
		WebUI.sendKeys(findTestObject(objectName), Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(findTestObject(objectName), Keys.chord(Keys.BACK_SPACE))
		WebUI.setText(findTestObject(objectName), inputValue)
	}


	@Keyword
	static def clearText(String objectName){
		WebUI.waitForElementPresent(findTestObject(objectName), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectName), GVars.midWait, FailureHandling.OPTIONAL)
		Act.milliWait(500)
		WebUI.sendKeys(findTestObject(objectName), Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(findTestObject(objectName), Keys.chord(Keys.DELETE))
	}


	@Keyword
	static def clearAndSetTextKat(String objectName, String inputValue){
		WebUI.waitForElementPresent(findTestObject(objectName), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectName), GVars.midWait, FailureHandling.OPTIONAL)
		Act.milliWait(500)
		WebUI.clearText(findTestObject(objectName))
		WebUI.setText(findTestObject(objectName), inputValue)
	}


	@Keyword
	static def selectComboLabelAndVerify(String objectName, String attribute, String value, String expValue){
		WebUI.selectOptionByLabel(findTestObject(objectName), value, false)
		Act.Wait(1)
		Val.objectAttributeValue(objectName, attribute, expValue)
	}


	@Keyword
	static def verifySelectedComboBoxValueInFrame(String objectName, String expValue, String identifier, int frameIndex){
		String publishToXpath = Obj.getObjectRepositoryIdentifierValue(objectName, identifier)
		String publish = WebD.getComboBoxLabelInFrame(publishToXpath, frameIndex)
		Act.compareStringAndReport(publish, expValue)
	}


	@Keyword
	static def clickAndValidateAttribute(String objectName, String attribute, String expValue){
		Act.Wait(1)
		WebUI.click(findTestObject(objectName))
		Com.edgeSync()
		Com.firefoxSync()
		Act.Wait(1)
		String actValue = WebUI.getAttribute(findTestObject(objectName), attribute)
		Act.compareStringAndReport(actValue, expValue)
	}


	@Keyword
	static def getCheckBoxState(String objectName){
		boolean checked = WebUI.verifyElementChecked(findTestObject(objectName), 10, FailureHandling.OPTIONAL)
		return checked
	}


	@Keyword
	static def setCheckBoxState(String objectName, boolean state){
		boolean chkState = getCheckBoxState(objectName)
		if(chkState != state){
			WebUI.check(findTestObject(objectName))
		}
	}


	@Keyword
	static def getObjectAttributeValue(String xPathVal, String attributeVal, int position){
		def childNodes = WebD.listObjectChildObjects(xPathVal)
		def checkValue = childNodes[position].getAttribute(attributeVal)
		return checkValue
	}


	@Keyword
	static def selectInlineOption(int OptionPosition){
		boolean objExist = Obj.elementPresentSubSync(Const.columnPicker + 'a_InlineMenuPositionSelection', OptionPosition, "pos")
		if(objExist){
			buttonClickSubIntPosSync(Const.columnPicker + 'a_InlineMenuPositionSelection', OptionPosition, "pos")
		}else{
			buttonClickSubIntPosSync(Const.columnPicker + 'a_InlineMenuPositionSelectionUpmenu', OptionPosition, "pos")
		}
	}


	//Wrapper methods
	@Keyword
	static def buttonDblClickSync(String objectLocator){
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.longWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator), GVars.midWait, FailureHandling.OPTIONAL)
		Act.milliWait(500)
		WebUI.doubleClick(findTestObject(objectLocator))
	}


	@Keyword
	static def buttonClickSync(String objectLocator){
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.longWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator), GVars.longWait, FailureHandling.OPTIONAL)
		Act.milliWait(500)
		WebUI.click(findTestObject(objectLocator))
	}


	@Keyword
	static def buttonClickSubSync(String objectLocator, String value, String label) {
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait, FailureHandling.OPTIONAL)
		Act.milliWait(500)
		WebUI.click(findTestObject(objectLocator, [('' + label + ''): value]))
	}


	@Keyword
	static def buttonClickSubGenSync(String objectLocator, def value, def label) {
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait, FailureHandling.OPTIONAL)
		Act.milliWait(500)
		WebUI.click(findTestObject(objectLocator, [('' + label + ''): value]))
	}


	@Keyword
	static def buttonClickDblSubGenSync(String objectLocator, def value, def label, def value2, def label2) {
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value, ('' + label2 + ''): value2]), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator, [('' + label + ''): value, ('' + label2 + ''): value2]), GVars.midWait, FailureHandling.OPTIONAL)
		Act.milliWait(500)
		String edtText = WebUI.click(findTestObject(objectLocator, [('' + label + ''): value, ('' + label2 + ''): value2]))
		return edtText
	}


	@Keyword
	static def setEditSync(String objectLocator, String value){
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator), GVars.midWait, FailureHandling.OPTIONAL)
		WebUI.setText(findTestObject(objectLocator), value)
	}


	@Keyword
	static def setEditSubSync(String objectLocator, String value, String label, String text){
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait, FailureHandling.OPTIONAL)
		WebUI.setText(findTestObject(objectLocator, [('' + label + ''): value]), text)
	}


	@Keyword
	static def setEditSubIntPosSync(String objectLocator, int value, String label, String text){
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait, FailureHandling.OPTIONAL)
		WebUI.setText(findTestObject(objectLocator, [('' + label + ''): value]), text)
	}


	@Keyword
	static def clearSetEditSync(String objectLocator, String value){
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator), GVars.midWait, FailureHandling.OPTIONAL)
		WebUI.clearText(findTestObject(objectLocator))
		Act.Wait(1)
		WebUI.setText(findTestObject(objectLocator), value)
	}


	@Keyword
	static def getEditTextSync(String objectLocator){
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		String editText = WebUI.getText(findTestObject(objectLocator))
		return editText
	}


	@Keyword
	static def getEditTextSubSync(String objectLocator, String value, String label){
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait)
		String editText = WebUI.getText(findTestObject(objectLocator, [('' + label + ''): value]))
		return editText
	}


	@Keyword
	static def getEditTextDblSubGenPosSync(String objectLocator, def value, def label, def value2, def label2){
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value, ('' + label2 + ''): value2]), GVars.midWait)
		String edtText = WebUI.getText(findTestObject(objectLocator, [('' + label + ''): value, ('' + label2 + ''): value2]))
		return edtText
	}


	@Keyword
	static def getEditTextDblSubIntPosSync(String objectLocator, int value, String label, int value2, String label2){
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value, ('' + label2 + ''): value2]), GVars.midWait)
		String edtText = WebUI.getText(findTestObject(objectLocator, [('' + label + ''): value, ('' + label2 + ''): value2]))
		return edtText
	}


	@Keyword
	static def getEditTextSubIntPosSync(String objectLocator, int value, String label){
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait)
		String editText = WebUI.getText(findTestObject(objectLocator, [('' + label + ''): value]))
		return editText
	}


	@Keyword
	static def getEditTextTriSubIntPosSync(String objectLocator, def value, def label, def value2, def label2, def value3, def label3){
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value, ('' + label2 + ''): value2, ('' + label3 + ''): value3]), GVars.midWait)
		String editText = WebUI.getText(findTestObject(Const.columnPicker + "searchSpecifiedTable", [('' + label + ''): value, ('' + label2 + ''): value2, ('' + label3 + ''): value3]))
		return editText
	}


	@Keyword
	static def verifyAttributeSync(String objectLocator, String attr){
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		WebUI.verifyElementHasAttribute(findTestObject(objectLocator), attr, GVars.midWait)
	}


	@Keyword
	static def verifyAttributeSubSync(String objectLocator, def value, def label, String attr){
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait)
		WebUI.verifyElementHasAttribute(findTestObject(objectLocator, [('' + label + ''): value]), attr, GVars.midWait)
	}


	@Keyword
	static def getAttributeSync(String objectLocator, String attr){
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		String editText = WebUI.getAttribute(findTestObject(objectLocator), attr)
		return editText
	}


	@Keyword
	static def getAttributeSubSync(String objectLocator, def value, def label, String attr){
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait)
		String editText = WebUI.getAttribute(findTestObject(objectLocator, [('' + label + ''): value]), attr)
		return editText
	}


	@Keyword
	static def checkSync(String objectLocator){
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator), GVars.midWait, FailureHandling.OPTIONAL)
		WebUI.check(findTestObject(objectLocator))
	}


	@Keyword
	static def uncheckSync(String objectLocator){
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator), GVars.midWait, FailureHandling.OPTIONAL)
		WebUI.uncheck(findTestObject(objectLocator))
	}


	@Keyword
	static def verifyCheckedSync(String objectLocator) {
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		boolean active = WebUI.verifyElementChecked(findTestObject(Const.columnPicker + "chk_ActiveContact"), GVars.midWait, FailureHandling.OPTIONAL)
		return active
	}


	@Keyword
	static def checkSubSync(String objectLocator, int value, String label) {
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait, FailureHandling.OPTIONAL)
		WebUI.check(findTestObject(objectLocator, [('' + label + ''): value]))
	}


	@Keyword
	static def selectComboByLabelSync(String objectLocator, String value){
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator), GVars.midWait, FailureHandling.OPTIONAL)
		WebUI.selectOptionByLabel(findTestObject(objectLocator), value, false)
	}


	@Keyword
	static def selectComboByValueSync(String objectLocator, String value){
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator), GVars.midWait, FailureHandling.OPTIONAL)
		WebUI.selectOptionByValue(findTestObject(objectLocator), value, false)
	}


	@Keyword
	static def selectComboByIndexSync(String objectLocator, String value){
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator), GVars.midWait, FailureHandling.OPTIONAL)
		WebUI.selectOptionByIndex(findTestObject(objectLocator), value)
	}


	@Keyword
	static def scrollToElementSync(String objectLocator){
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		WebUI.scrollToElement(findTestObject(objectLocator), GVars.midWait)
	}


	@Keyword
	static def scrollToElementSubSync(String objectLocator, def value, def label){
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait)
		WebUI.scrollToElement(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait)
	}


	@Keyword
	static def buttonClickSubIntPosSync(String objectLocator, int value, String label) {
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait)
		WebUI.waitForElementClickable(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait, FailureHandling.OPTIONAL)
		WebUI.click(findTestObject(objectLocator, [('' + label + ''): value]))
	}


	@Keyword
	static def elementVisibleSync(String objectLocator) {
		WebUI.waitForElementVisible(findTestObject(objectLocator), GVars.longWait)
		def dateExist = WebUI.verifyElementVisible(findTestObject(objectLocator), FailureHandling.OPTIONAL)
		return dateExist
	}


	@Keyword
	static def elementVisibleSubSync(String objectLocator, def value, def label) {
		WebUI.waitForElementVisible(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait)
		def dateExist = WebUI.verifyElementVisible(findTestObject(objectLocator, [('' + label + ''): value]), FailureHandling.OPTIONAL)
		return dateExist
	}


	@Keyword
	static def elementPresentSubSync(String objectLocator, def value, def label) {
		WebUI.waitForElementPresent(findTestObject(objectLocator, [('' + label + ''): value]), GVars.longWait)
		def objExist = WebUI.verifyElementPresent(findTestObject(objectLocator, [('' + label + ''): value]), GVars.midWait, FailureHandling.OPTIONAL)
		return objExist
	}


	@Keyword
	static def elementPresentSync(String objectLocator) {
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		def objExist = WebUI.verifyElementPresent(findTestObject(objectLocator), GVars.midWait, FailureHandling.OPTIONAL)
		return objExist
	}


	@Keyword
	static def verifyLabelSelectedSync(String objectLocator, String Value) {
		WebUI.waitForElementPresent(findTestObject(objectLocator), GVars.midWait)
		def selected = WebUI.verifyOptionSelectedByLabel(findTestObject(objectLocator), Value, false, GVars.midWait)
		return selected
	}


	@Keyword
	static def acceptAlertSync() {
		WebUI.waitForAlert(GVars.midWait, FailureHandling.OPTIONAL)
		WebUI.acceptAlert()
	}


	@Keyword
	static def clickLogoSync() {
		Obj.buttonClickSync(Const.dashBoard + "img_Logo")
		Act.Wait(GVars.shortWait)
	}


	@Keyword
	static def selectComboboxValue(String comboSelector, String comboInputSelector, String inputValue, String selectValue) {
		String textVal = getEditTextSubSync(Const.kip4Generic + 'dropDown_GenericCombo', comboSelector, "val")
		if(textVal != inputValue) {
			buttonClickSubSync(Const.kip4Generic + 'dropDown_GenericCombo', comboSelector, "val")

			boolean objExt = WebUI.verifyElementNotPresent(findTestObject(Const.kip4Generic + 'delete_GenericComboValue', [('val'): comboSelector]), GVars.shortWait, FailureHandling.OPTIONAL)
			if(objExt == false) {
				WebUI.mouseOver(findTestObject(Const.kip4Generic + 'dropDown_GenericCombo', [('val'): comboSelector]))
				buttonClickSubSync(Const.kip4Generic +'delete_GenericComboValue', comboSelector, "val")
				buttonClickSubSync(Const.kip4Generic +'dropDown_GenericCombo', comboSelector, "val")
			}

			setEditSubSync(Const.kip4Generic + "input_GenericCombo", comboInputSelector, "pos", inputValue)
			Act.milliWait(500)
			buttonClickSubSync(Const.kip4Generic + "select_GenericCombo", selectValue, "val")
		}
	}


	@Keyword
	static def selectComboValueOldModal(String dropDownVal, String selectValue) {
		Act.Wait(1)
		Obj.buttonClickSync(Const.addAssignment + dropDownVal)
		Obj.setEditSync(Const.addAssignment + "input_ComboVal", selectValue)
		buttonClickSubSync(Const.addAssignment + "select_ComboVal", selectValue, "proj")
	}


	@Keyword
	static def populateEditBox(String editBox, String edtValue, String dropDown) {
		String edtVal = getAttributeSync(editBox, 'value')
		if(edtVal != "") {
			WebUI.clearText(findTestObject(editBox))
		}

		setEditSync(editBox, edtValue)

		String edtTextVal = getAttributeSync(editBox, 'value')
		if(edtTextVal == edtValue) {
			Rep.pass("Editbox populated correctly")
		}else {
			Rep.fail("Editbox not populated")
		}
		buttonClickSync(editBox)
	}


	@Keyword
	static def createNewFilter(String fieldVal, String compVal, String val) {
		Obj.buttonClickSync(Const.columnPicker + "a_New Filter")
		def elVis = Obj.elementVisibleSync(Const.newFilter + "UnsavedFilter_PopUp")
		if(elVis) {
			Obj.buttonClickSync(Const.newFilter + "SaveFilter_button_OK")
		}

		Obj.buttonClickSync(Const.newFilter + "a_Select")
		Obj.setEditSync(Const.newFilter + "input_FilterField", fieldVal)
		Obj.buttonClickSubSync(Const.newFilter + "select_FilterVal", fieldVal, "val")

		Obj.buttonClickSync(Const.newFilter + "a_selectComparisonType")
		Obj.buttonClickSubSync(Const.newFilter + "select_FilterVal", compVal, "val")

		Obj.setEditSync(Const.newFilter + "input_FilterValue", val)
		Act.Wait(1)
		Obj.buttonClickSync(Const.columnPicker + "a_Search")
		Act.Wait(1)
	}


	@Keyword
	static def refreshUI() {
		WebUI.refresh()
		Act.Wait(GVars.shortWait)
	}

}
