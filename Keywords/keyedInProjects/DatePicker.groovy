package keyedInProjects

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

import keyedInProjects.Constants as Const
import keyedInProjects.Navigate as Nav
import keyedInProjects.Validate as Val

import global.Object as Obj
import global.Action as Act
import global.Common as Com
import global.Report as Rep
import global.WebDriverMethods as WebD

import internal.GlobalVariable as GVars

public class DatePicker {

	@Keyword
	static def getCurrentMthYr() {
		String actMth = Obj.getAttributeSync(Const.kip4DatePicker + "select_Month", 'value')
		String actYr = Obj.getAttributeSync(Const.kip4DatePicker + "select_Year", 'value')
		return actMth + "/" + actYr
	}

	@Keyword
	static def moveMonth_Fwd(String mth, String yr) {
		String newDate = getCurrentMthYr()

		if(newDate == mth + "/" + yr) {
			return
		}

		while(newDate != mth + "/" + yr) {
			Obj.buttonClickSync(Const.kip4DatePicker + "btn_NextMonth")
			newDate = getCurrentMthYr()
		}
	}


	@Keyword
	static def moveYear_Fwd(String yr) {
		String newDate = getCurrentMthYr()
		def currDate = newDate.split("/")

		while(currDate[1] != yr) {
			Obj.buttonClickSync(Const.kip4DatePicker + "btn_NextMonth")
			newDate = getCurrentMthYr()
			currDate = newDate.split("/")
		}
	}


	@Keyword
	static def moveMonth_Bck(String mth, String yr) {
		String newDate = getCurrentMthYr()

		if(newDate == mth + "/" + yr) {
			return
		}

		while(newDate != mth + "/" + yr) {
			Obj.buttonClickSync(Const.kip4DatePicker + "btn_PrevMonth")
			newDate = getCurrentMthYr()
		}
	}


	@Keyword
	static def moveYear_Bck(String yr) {
		String newDate = getCurrentMthYr()
		def currDate = newDate.split("/")

		while(currDate[1] != yr) {
			Obj.buttonClickSync(Const.kip4DatePicker + "btn_PrevMonth")
			newDate = getCurrentMthYr()
			currDate = newDate.split("/")
		}
	}


	@Keyword
	static def setCorrectYr(String yr) {
		String newDate = getCurrentMthYr()
		def currDate = newDate.split("/")
		if(currDate[1] == yr) {
			return
		}

		int yrDiff = currDate[1].toInteger() - yr.toInteger()

		if(yrDiff < 0) {
			moveYear_Fwd(yr)
		}else if (yrDiff > 0) {
			moveYear_Bck(yr)
		}
	}


	@Keyword
	static def setCorrectMth(String mth, String yr) {
		String newDate = getCurrentMthYr()
		def currDate = newDate.split("/")
		if(currDate[1] == yr) {
			int dateDiff = currDate[0].toInteger() - mth.toInteger()

			if(dateDiff < 0) {
				moveMonth_Fwd(mth, yr)
			}else if (dateDiff > 0) {
				moveMonth_Bck(mth, yr)
			}
		}
	}

	
	@Keyword
	static def setDateCF(String day, String mth, String yr) {
		setCorrectYr(yr)
		setCorrectMth(mth, yr)
		String sub = day.substring(0, 1)
		if(sub == "0") {
			day = day.substring(1)
		}
		Act.Wait(2)
		String altDate = day + "-" + mth + "-" + yr
		String currDate = Com.todayDateFormat("d/M/yyyy")
		if(currDate == day + "/" + mth + "/" + yr) {
			Obj.buttonClickSubSync(Const.kip4DatePicker + "select_DayToday", day, "dy")
		}else {
			Obj.buttonClickSubSync(Const.kip4DatePicker + "select_Day", day, "dy")
		}
	}
	

	@Keyword
	static def setDate(String day, String mth, String yr) {
		setCorrectYr(yr)
		setCorrectMth(mth, yr)
		String sub = day.substring(0, 1)
		if(sub == "0") {
			day = day.substring(1)
		}
		String sub2 = mth.substring(0, 1)
		if(sub2 == "0") {
			mth = mth.substring(1)
		}
		
		Act.Wait(2)
		String altDate = day + "-" + mth + "-" + yr
		String currDate = Com.todayDateFormat("d/M/yyyy")
		if(currDate == day + "/" + mth + "/" + yr) {
			Obj.buttonClickSubSync(Const.kip4DatePicker + "select_DayToday", day, "dy")
		}else {
//			Obj.buttonClickSubSync(Const.kip4DatePicker + "select_DayNewAlt", day, "dy")
			Obj.buttonClickSubSync(Const.kip4DatePicker + "select_DayNewAlt_fullDate", altDate, "date")
		}
	}


	@Keyword
	static def setDateAlt(String day, String mth, String yr) {
		setCorrectYr(yr)
		setCorrectMth(mth, yr)
		String sub = day.substring(0, 1)
		if(sub == "0") {
			day = day.substring(1)
		}

		String currDate = Com.todayDateFormat("d/M/yyyy")
		if(currDate == day + "/" + mth + "/" + yr) {
			Obj.buttonClickSubSync(Const.kip4DatePicker + "select_DayToday", day, "dy")
		}else {
			Obj.buttonClickSubSync(Const.kip4DatePicker + "select_Day_CustF", day, "dy")
		}
	}
}
