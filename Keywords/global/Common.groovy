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
import org.apache.commons.io.FileUtils
import internal.GlobalVariable as GVars
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.Capabilities
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.RemoteWebDriver
import com.kms.katalon.core.webui.driver.DriverFactory
import org.apache.commons.lang.RandomStringUtils as RandomString

import com.kms.katalon.core.webui.driver.SmartWaitWebDriver //ver 7
import java.awt.*
import groovy.time.TimeCategory
import java.text.SimpleDateFormat
import global.Action as gAct
import global.Report as Rep

public class Common {

	@Keyword
	static def getOperatingSystem () {
		String osName = System.getProperty('os.name')
		Rep.info('osName : ' + osName)
	}


	@Keyword
	static def getBrowserAndVersion() {
		Capabilities caps
		String katalonVer = RunConfiguration.appVersion
		WebDriver driver = DriverFactory.getWebDriver()

		//		if(katalonVer[0] == "6"){
		//caps = ((RemoteWebDriver) driver).getCapabilities()
		//		}else{
		caps = ((SmartWaitWebDriver) driver).getCapabilities() //for Katalon v7
		//		}
		String browserName = caps.getBrowserName().capitalize()
		String browserVersion = caps.getVersion()
		Rep.info('Browser name : ' + browserName)
		Rep.info('Browser version : ' + browserVersion)
		GVars.browser = browserName
		return browserName + '/' + browserVersion
	}


	@Keyword
	static def getScreenResolution() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
		Integer screenHeight = screenSize.height
		Integer screenWidth = screenSize.width
		Rep.info('Screen Size : ' + screenWidth + 'x' + screenHeight)
		return screenWidth + 'x' + screenHeight
	}


	@Keyword
	static def firefoxSync(int waitTime = 1){
		if(GVars.browser == "Firefox"){
			gAct.Wait(waitTime)
		}
	}


	@Keyword
	static def edgeSync(int waitTime = 1){
		if(GVars.browser == "MicrosoftEdge"){
			gAct.Wait(waitTime)
		}
	}


	@Keyword
	static def ieSync(int waitTime = 1){
		if(GVars.browser == "Internet explorer"){
			gAct.Wait(waitTime)
		}
	}


	@Keyword
	static def chromeSync(int waitTime = 1){
		if(GVars.browser == "Chrome"){
			gAct.Wait(waitTime)
		}
	}


	@Keyword
	static def removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}


	@Keyword
	static def todayDate(){
		def today = new Date()
		String date = today.format('dd/MM/yyyy')
		return date
	}


	@Keyword
	static def todayDateFormat(String dtFormat){
		def today = new Date()
		String date = today.format(dtFormat)
		return date
	}


	@Keyword
	static def currentDay(){
		def today = new Date()
		String day = today.format('EEEE')
		return day
	}


	@Keyword
	static def dateDay(Date date){
		String day = date.format('EEEE')
		return day
	}

	@Keyword
	static def weekDates(){
		def today = new Date()
		String curDay = currentDay()
		int weekdayCount = 0


		switch(curDay){
			case 'Monday':
				weekdayCount = 0
				break
			case 'Tuesday':
				weekdayCount = 1
				break
			case 'Wednesday':
				weekdayCount = 2
				break
			case 'Thursday':
				weekdayCount = 3
				break
			case 'Friday':
				weekdayCount = 4
				break
			case 'Saturday':
				weekdayCount = 5
				break
			case 'Sunday':
				weekdayCount = 6
				break
		}

		def weekStart = today - weekdayCount
		weekStart = weekStart.format("d MMMM")
		def weekEnd = today + (6 - weekdayCount)
		weekEnd = weekEnd.format("d MMMM")
		return weekStart + " - " + weekEnd
	}



	@Keyword
	static def weekStartDate(){
		def today = new Date()
		String curDay = currentDay()
		int weekdayCount = 0


		switch(curDay){
			case 'Monday':
				weekdayCount = 0
				break
			case 'Tuesday':
				weekdayCount = 1
				break
			case 'Wednesday':
				weekdayCount = 2
				break
			case 'Thursday':
				weekdayCount = 3
				break
			case 'Friday':
				weekdayCount = 4
				break
			case 'Saturday':
				weekdayCount = 5
				break
			case 'Sunday':
				weekdayCount = 6
				break
		}

		def weekStart = today - weekdayCount
		weekStart = weekStart.format("dd/MM/yyyy")
		return weekStart
	}


	@Keyword
	static def weekStartDateFormat(String dateFormat){
		def today = new Date()
		String curDay = currentDay()
		int weekdayCount = 0


		switch(curDay){
			case 'Monday':
				weekdayCount = 0
				break
			case 'Tuesday':
				weekdayCount = 1
				break
			case 'Wednesday':
				weekdayCount = 2
				break
			case 'Thursday':
				weekdayCount = 3
				break
			case 'Friday':
				weekdayCount = 4
				break
			case 'Saturday':
				weekdayCount = 5
				break
			case 'Sunday':
				weekdayCount = 6
				break
		}

		def weekStart = today - weekdayCount
		weekStart = weekStart.format(dateFormat)
		return weekStart
	}


	@Keyword
	static def returnTodaysDayDateInGivenWeek(def newDate, def dateFormat){
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		def Date = new SimpleDateFormat(dateFormat).parse(newDate)

		String curDay = currentDay()
		int weekdayCount = 0


		switch(curDay){
			case 'Monday':
				weekdayCount = 0
				break
			case 'Tuesday':
				weekdayCount = 1
				break
			case 'Wednesday':
				weekdayCount = 2
				break
			case 'Thursday':
				weekdayCount = 3
				break
			case 'Friday':
				weekdayCount = 4
				break
			case 'Saturday':
				weekdayCount = 5
				break
			case 'Sunday':
				weekdayCount = 6
				break
		}

		def count = 0;
		while (count < weekdayCount) {
			Date.setDate(Date.getDate() + 1);
			if (Date.getDay() != 0 && Date.getDay() != 6) // Skip weekends
				count++;
		}

		def newDateFormat = formatter.format(Date)
		return newDateFormat
	}



	@Keyword
	static def weekDatesDifferent(int weekDifference, String direction){
		//direction can be forward and back
		def weekNewStart, weekNewEnd
		def today = new Date()
		String curDay = currentDay()
		int weekdayCount = 0


		switch(curDay){
			case 'Monday':
				weekdayCount = 0
				break
			case 'Tuesday':
				weekdayCount = 1
				break
			case 'Wednesday':
				weekdayCount = 2
				break
			case 'Thursday':
				weekdayCount = 3
				break
			case 'Friday':
				weekdayCount = 4
				break
			case 'Saturday':
				weekdayCount = 5
				break
			case 'Sunday':
				weekdayCount = 6
				break
		}

		def weekStart = today - weekdayCount
		def weekEnd = today + (6 - weekdayCount)

		if(direction == "forward"){
			weekNewStart = weekStart + (weekDifference * 7)
			weekNewEnd = weekEnd + (weekDifference * 7)
		}else{
			weekNewStart = weekStart - (weekDifference * 7)
			weekNewEnd = weekEnd - (weekDifference * 7)
		}

		weekNewStart = weekNewStart.format("dd/MM/yyyy")
		return weekNewStart
	}


	@Keyword
	static def weekDatesDifferentFormat(int weekDifference, String direction, String format){
		//direction can be forward and back
		def weekNewStart, weekNewEnd
		def today = new Date()
		String curDay = currentDay()
		int weekdayCount = 0


		switch(curDay){
			case 'Monday':
				weekdayCount = 0
				break
			case 'Tuesday':
				weekdayCount = 1
				break
			case 'Wednesday':
				weekdayCount = 2
				break
			case 'Thursday':
				weekdayCount = 3
				break
			case 'Friday':
				weekdayCount = 4
				break
			case 'Saturday':
				weekdayCount = 5
				break
			case 'Sunday':
				weekdayCount = 6
				break
		}

		def weekStart = today - weekdayCount
		def weekEnd = today + (6 - weekdayCount)

		if(direction == "forward"){
			weekNewStart = weekStart + (weekDifference * 7)
			weekNewEnd = weekEnd + (weekDifference * 7)
		}else{
			weekNewStart = weekStart - (weekDifference * 7)
			weekNewEnd = weekEnd - (weekDifference * 7)
		}

		weekNewStart = weekNewStart.format(format)
		return weekNewStart
	}


	@Keyword
	static def weekDatesDifferent_StartDate(int weekDifference, String direction){
		//direction can be forward and back
		def weekNewStart, weekNewEnd
		def today = new Date()
		String curDay = currentDay()
		int weekdayCount = 0


		switch(curDay){
			case 'Monday':
				weekdayCount = 0
				break
			case 'Tuesday':
				weekdayCount = 1
				break
			case 'Wednesday':
				weekdayCount = 2
				break
			case 'Thursday':
				weekdayCount = 3
				break
			case 'Friday':
				weekdayCount = 4
				break
			case 'Saturday':
				weekdayCount = 5
				break
			case 'Sunday':
				weekdayCount = 6
				break
		}

		def weekStart = today - weekdayCount
		def weekEnd = today + (6 - weekdayCount)

		if(direction == "forward"){

			weekNewStart = weekStart + (weekDifference * 7)
			weekNewEnd = weekEnd + (weekDifference * 7)
		}else{
			weekNewStart = weekStart - (weekDifference * 7)
			weekNewEnd = weekEnd - (weekDifference * 7)
		}

		weekNewStart = weekNewStart.format("d MMMM")
		weekNewEnd = weekNewEnd.format("d MMMM")
		return weekNewStart + " - " + weekNewEnd
	}


	@Keyword
	static def weekDatesDifferent_WorkingStartDate(int weekDifference, String direction){
		//direction can be forward and back
		def weekNewStart, weekNewEnd
		//		def today = new Date()
		String newDate = workingDate()
		def pattern = "dd/MM/yyyy"
		Date today = new SimpleDateFormat(pattern).parse(newDate)

		String curDay = dateDay(today)
		int weekdayCount = 0


		switch(curDay){
			case 'Monday':
				weekdayCount = 0
				break
			case 'Tuesday':
				weekdayCount = 1
				break
			case 'Wednesday':
				weekdayCount = 2
				break
			case 'Thursday':
				weekdayCount = 3
				break
			case 'Friday':
				weekdayCount = 4
				break
			case 'Saturday':
				weekdayCount = 5
				break
			case 'Sunday':
				weekdayCount = 6
				break
		}

		def weekStart = today - weekdayCount
		def weekEnd = today + (6 - weekdayCount)

		if(direction == "forward"){

			weekNewStart = weekStart + (weekDifference * 7)
			weekNewEnd = weekEnd + (weekDifference * 7)
		}else{
			weekNewStart = weekStart - (weekDifference * 7)
			weekNewEnd = weekEnd - (weekDifference * 7)
		}

		weekNewStart = weekNewStart.format("d MMMM")
		weekNewEnd = weekNewEnd.format("d MMMM")
		return weekNewStart + " - " + weekNewEnd
	}


	@Keyword
	static def getTimeDateStamp(){
		Date today = new Date()
		String todaysDate = today.format('dd_mm_yyyy')
		String nowTime = today.format('hh_mm')
		String tdStamp = todaysDate + "__" + nowTime

		return tdStamp
	}


	@Keyword
	static def increaseWorkingCalendar(def Date, def days){
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");

		def count = 0;
		while (count < days) {
			Date.setDate(Date.getDate() + 1);
			if (Date.getDay() != 0 && Date.getDay() != 6) // Skip weekends
				count++;
		}

		def newDateFormat = formatter.format(Date)
		return newDateFormat
	}


	@Keyword
	static def decreaseWorkingCalendar(def Date, def days){
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");

		def count = 0;
		while (count < days) {
			Date.setDate(Date.getDate() - 1);
			if (Date.getDay() != 0 && Date.getDay() != 6) // Skip weekends
				count++;
		}

		def newDateFormat = formatter.format(Date)
		return newDateFormat
	}


	@Keyword
	static def increaseWorkingCalendarWithFormat(def Date, def days, String dateFormat){
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		def count = 0;
		while (count < days) {
			Date.setDate(Date.getDate() + 1);
			if (Date.getDay() != 0 && Date.getDay() != 6) // Skip weekends
				count++;
		}

		def newDateFormat = formatter.format(Date)
		return newDateFormat
	}


	@Keyword
	static def decreaseWorkingCalendarWithFormat(def Date, def days, String dateFormat){
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		def count = 0;
		while (count < days) {
			Date.setDate(Date.getDate() - 1);
			if (Date.getDay() != 0 && Date.getDay() != 6) // Skip weekends
				count++;
		}

		def newDateFormat = formatter.format(Date)
		return newDateFormat
	}


	@Keyword
	static def increaseWorkingCalendarTypeDate(def Date, def days){

		def count = 0;
		while (count < days) {
			Date.setDate(Date.getDate() + 1);
			count++;
		}

		return Date
	}


	@Keyword
	static def decreaseWorkingCalendarTypeDate(def Date, def days){

		def count = 0;
		while (count < days) {
			Date.setDate(Date.getDate() - 1);
			count++;
		}

		return Date
	}


	@Keyword
	static def changeDateFormat(def input, String dateFormat, String newFormat){
		def date = new SimpleDateFormat(dateFormat).parse(input)
		String newDate = date.format(newFormat)
		return newDate
	}


	@Keyword
	static def getNextMonth(){
		def months
		use (groovy.time.TimeCategory) {
			Date date = new Date()

			months = (0..11).collect {
				(date + it.months).format("MMM/yyyy")
			}
		}

		return months[1]
	}


	@Keyword
	static def getNextMonth_Numeric(){
		def months
		use (groovy.time.TimeCategory) {
			Date date = new Date()

			months = (0..11).collect {
				(date + it.months).format("M/yyyy")
			}
		}

		return months[1]
	}


	@Keyword
	static def getPreviousMonth_Numeric(){
		def months
		use (groovy.time.TimeCategory) {
			Date date = new Date()

			months = (0..11).collect {
				(date - it.months).format("M/yyyy")
			}
		}

		return months[1]
	}


	@Keyword
	static def workingDate(){
		def today = new Date()
		boolean wd = isBankHoliday(today)
		while(wd) {
			today = increaseWorkingCalendarTypeDate(today, 1)
			wd = isBankHoliday(today)
		}

		String date = today.format('dd/MM/yyyy')
		return date
	}


	@Keyword
	static def workingDateBack(){
		def today = new Date()
		boolean wd = isBankHoliday(today)
		while(wd) {
			today = decreaseWorkingCalendarTypeDate(today, 1)
			wd = isBankHoliday(today)
		}

		String date = today.format('dd/MM/yyyy')
		return date
	}


	@Keyword
	static def nextWorkingDate(def dt){
		String dateFormat = 'dd/MM/yyyy'
		def today = new SimpleDateFormat(dateFormat).parse(dt)
		boolean wd = isBankHoliday(today)
		while(wd) {
			today = increaseWorkingCalendarTypeDate(today, 1)
			wd = isBankHoliday(today)
		}

		String date = today.format(dateFormat)
		return date
	}


	@Keyword
	static def previousWorkingDate(def dt){
		String dateFormat = 'dd/MM/yyyy'
		def today = new SimpleDateFormat(dateFormat).parse(dt)
		boolean wd = isBankHoliday(today)
		while(wd) {
			today = decreaseWorkingCalendarTypeDate(today, 1)
			wd = isBankHoliday(today)
		}

		String date = today.format(dateFormat)
		return date
	}


	@Keyword
	static def isBankHoliday(Date d) {
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		if((Calendar.SATURDAY == c.get(c.DAY_OF_WEEK)) || (Calendar.SUNDAY == c.get(c.DAY_OF_WEEK))) {
			return (true);
		} else {
			return false;
		}
	}


	@Keyword
	static def copyDirectoryFiles(String sourcePath, String destPath){
		File source = new File(sourcePath);
		File dest = new File(destPath);
		FileUtils.copyDirectory(source, dest);
	}


	@Keyword
	static def getBrowserType(){
		String browserVer = DriverFactory.getExecutedBrowser()
		String selectedBrowser = DriverFactory.getExecutedBrowser().getName()

		if (browserVer == "Chrome (headless)"){
			selectedBrowser = "CHROME_DRIVER"
		}else if(browserVer == "Firefox (headless)"){
			selectedBrowser = "FIREFOX_DRIVER"
		}

		return selectedBrowser
	}


	@Keyword
	static String generateRandomText(int stringLength){
		String RandomString = RandomString.randomAlphabetic(stringLength)
		String newText = RandomString.toLowerCase()
		return newText
	}


	@Keyword
	static def generateRandomNumber(int upperLimit){
		int RandomNum = Math.abs(new Random().nextInt() % upperLimit) + 1
		return RandomNum
	}
}
