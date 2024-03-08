package customfields

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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import customfields.Common as custCom
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import global.Action as gAct
import global.Object as gObj
import models.CostCentreCustomField
import search.Validate as sVal
import global.WebDriverMethods as WebD

public class Create {

	@Keyword
	static def costCentreCustomField_FormattedText(String tab, CostCentreCustomField ccCustomField){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_NewSection"), tab)
		WebUI.click(findTestObject(Const.CustCostCentre + "a_Update"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_Name"), ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		WebUI.setText(findTestObject(Const.CustCostCentre + "textarea_GuidanceNotes"),ccCustomField.guidanceNotes)

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def costCentreCustomField_ShortText(String tab, CostCentreCustomField ccCustomField){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_NewSection"), tab)
		WebUI.click(findTestObject(Const.CustCostCentre + "a_Update"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_Name"), ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_DefaultValue"),ccCustomField.defaultValue)
		WebUI.setText(findTestObject(Const.CustCostCentre + "textarea_GuidanceNotes"),ccCustomField.guidanceNotes)

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		Act.verifySavePopUpText()

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def costCentreCustomField_CheckBox(String tab, CostCentreCustomField ccCustomField, boolean defVal){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")
		gObj.setEditSync(Const.CustCostCentre + "input_NewSection", tab)
		gObj.buttonClickSync(Const.CustCostCentre + "a_Update")
		gObj.setEditSync(Const.CustCostCentre + "input_Name", ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		gObj.setEditSync(Const.CustCostCentre + "textarea_GuidanceNotes",ccCustomField.guidanceNotes)

		if(defVal){
			WebUI.click(findTestObject(Const.CustCostCentre + "chk_DefaultValue_boolean"))
		}

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		Act.verifySavePopUpText()

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def costCentreCustomField_float(String tab, CostCentreCustomField ccCustomField){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")
		gObj.setEditSync(Const.CustCostCentre + "input_NewSection", tab)
		gObj.buttonClickSync(Const.CustCostCentre + "a_Update")
		gObj.setEditSync(Const.CustCostCentre + "input_Name", ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		gObj.setEditSync(Const.CustCostCentre + "textarea_GuidanceNotes",ccCustomField.guidanceNotes)

		gObj.setEditSync(Const.CustCostCentre + "input_DefaultValue",ccCustomField.defaultValue)

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		Act.verifySavePopUpText()

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def costCentreCustomFieldExistingTab_Int(String tab, CostCentreCustomField ccCustomField){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")

		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_Section"), tab, false)

		gObj.setEditSync(Const.CustCostCentre + "input_Name", ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		gObj.setEditSync(Const.CustCostCentre + "textarea_GuidanceNotes",ccCustomField.guidanceNotes)

		gObj.setEditSync(Const.CustCostCentre + "input_DefaultValue",ccCustomField.defaultValue)

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		Act.verifySavePopUpText()

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def costCentreCustomField_EmailURL(String tab, CostCentreCustomField ccCustomField){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_NewSection"), tab)
		WebUI.click(findTestObject(Const.CustCostCentre + "a_Update"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_Name"), ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_DefaultValue"),ccCustomField.defaultValue)
		WebUI.setText(findTestObject(Const.CustCostCentre + "textarea_GuidanceNotes"),ccCustomField.guidanceNotes)

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		Act.verifySavePopUpText()

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def costCentreCustomField_ShortTextDefault(String tab, CostCentreCustomField ccCustomField){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_NewSection"), tab)
		WebUI.click(findTestObject(Const.CustCostCentre + "a_Update"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_Name"), ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_DefaultValue"),ccCustomField.defaultValue)
		WebUI.click(findTestObject(Const.CustCostCentre + "chk_UseDefaultValueCloned"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "textarea_GuidanceNotes"),ccCustomField.guidanceNotes)


		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		Act.verifySavePopUpText()

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def costCentreCustomField_Keywords(String tab, CostCentreCustomField ccCustomField, String allowVal, String colour){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_NewSection"), tab)
		WebUI.click(findTestObject(Const.CustCostCentre + "a_Update"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_Name"), ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		gAct.Wait(2)
		gObj.setEditSync(Const.CustCostCentre + "input_DefaultValue",ccCustomField.defaultValue)
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.CustCostCentre + "textarea_GuidanceNotes"),ccCustomField.guidanceNotes)

		WebUI.click(findTestObject(Const.CustCostCentre + "tab_AllowedValues"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_AllowedValue"), allowVal)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DisplayColour"), colour, false)
		WebUI.click(findTestObject(Const.CustCostCentre + "btn_AddAllowedValue"))

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		Act.verifySavePopUpText()

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def costCentreCustomFieldExistingTab_Keyword(String tableName, String tab, CostCentreCustomField ccCustomField, String allowVal, String colour){
		custCom.createCustomField("Cost Centre")
		gAct.Wait(1)

		WebD.clickElement("//div[@id='63f30b25-1931-4053-98a5-eec594abfc22']/div[5]/table/tbody/tr/td/div/a/i")
		//		WebUI.click(findTestObject(Const.columnPicker + 'inlineEdit_specTable', [('table'): tableName, ('row'): 1]))
		gObj.selectInlineOption(1)

		WebUI.click(findTestObject(Const.CustCostCentre + "tab_AllowedValues"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_AllowedValue"), allowVal)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DisplayColour"), colour, false)
		WebUI.click(findTestObject(Const.CustCostCentre + "btn_AddAllowedValue"))

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		Act.verifySavePopUpText()

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def costCentreCustomField_ExistingValue(String tab, CostCentreCustomField ccCustomField){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_NewSection"), tab)
		WebUI.click(findTestObject(Const.CustCostCentre + "a_Update"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_Name"), ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_DefaultValue"),ccCustomField.defaultValue)

		WebUI.setText(findTestObject(Const.CustCostCentre + "textarea_GuidanceNotes"),ccCustomField.guidanceNotes)

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def costCentreCustomField_DatePicker(String tab, CostCentreCustomField ccCustomField){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_NewSection"), tab)
		WebUI.click(findTestObject(Const.CustCostCentre + "a_Update"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_Name"), ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_DefaultValue"),ccCustomField.defaultValue)

		WebUI.setText(findTestObject(Const.CustCostCentre + "textarea_GuidanceNotes"),ccCustomField.guidanceNotes)

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}

	@Keyword
	static def costCentreCustomField_DropDown(String tab, CostCentreCustomField ccCustomField, String allowVal, String colour){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_NewSection"), tab)
		WebUI.click(findTestObject(Const.CustCostCentre + "a_Update"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_Name"), ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_DefaultValue"),ccCustomField.defaultValue)
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.CustCostCentre + "textarea_GuidanceNotes"),ccCustomField.guidanceNotes)

		WebUI.click(findTestObject(Const.CustCostCentre + "tab_AllowedValues"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_AllowedValue"), allowVal)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DisplayColour"), colour, false)
		WebUI.click(findTestObject(Const.CustCostCentre + "btn_AddAllowedValue"))

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		Act.verifySavePopUpText()

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}

	@Keyword
	static def costCentreCustomField_DropDownDefault(String tab, CostCentreCustomField ccCustomField, String allowVal, String colour){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_NewSection"), tab)
		WebUI.click(findTestObject(Const.CustCostCentre + "a_Update"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_Name"), ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		gAct.Wait(1)
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_DefaultValue"),ccCustomField.defaultValue)
		gAct.Wait(1)
		WebUI.click(findTestObject(Const.CustCostCentre + "chk_UseDefaultValueCloned"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "textarea_GuidanceNotes"),ccCustomField.guidanceNotes)

		WebUI.click(findTestObject(Const.CustCostCentre + "tab_AllowedValues"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_AllowedValue"), allowVal)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DisplayColour"), colour, false)
		WebUI.click(findTestObject(Const.CustCostCentre + "btn_AddAllowedValue"))

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		Act.verifySavePopUpText()

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def costCentreCustomField_EntityPicker(String tab, CostCentreCustomField ccCustomField, boolean required){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")
		gObj.setEditSync(Const.CustCostCentre + "input_NewSection", tab)
		gObj.buttonClickSync(Const.CustCostCentre + "a_Update")
		gObj.setEditSync(Const.CustCostCentre + "input_Name", ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		gObj.setEditSync(Const.CustCostCentre + "textarea_GuidanceNotes",ccCustomField.guidanceNotes)

		if(required){
			WebUI.click(findTestObject(Const.CustCostCentre + "chk_Required"))
		}

		WebUI.setText(findTestObject(Const.CustCostCentre + "input_DefaultValue"),ccCustomField.defaultValue)
		gAct.Wait(1)

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		Act.verifySavePopUpText()

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}


	@Keyword
	static def costCentreCustomField_DocumentAtt(String tab, CostCentreCustomField ccCustomField){
		custCom.createCustomField("Cost Centre")
		gObj.buttonClickSync(Const.CustomFieldScreen + "a_Add")
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_NewSection"), tab)
		WebUI.click(findTestObject(Const.CustCostCentre + "a_Update"))
		WebUI.setText(findTestObject(Const.CustCostCentre + "input_Name"), ccCustomField.name)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_DataType"), ccCustomField.dataType, false)
		gAct.Wait(1)
		WebUI.selectOptionByLabel(findTestObject(Const.CustCostCentre + "select_FieldType"), ccCustomField.fieldType, false)
		WebUI.setText(findTestObject(Const.CustCostCentre + "textarea_GuidanceNotes"),ccCustomField.guidanceNotes)

		WebUI.click(findTestObject(Const.CustCostCentre + "button_SaveAndClose"))

		gAct.Wait(3)
		WebUI.click(findTestObject(Const.dashBoard + "img_Logo"))
		gAct.Wait(3)
	}
}
