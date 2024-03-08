import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetupTestCase
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.Keys as Keys

import internal.GlobalVariable as GVars
import keyedInProjects.Constants as Const
import keyedInProjects.Action as Act
import keyedInProjects.Validate as Val
import keyedInProjects.Navigate as Nav
import global.Report as Rep
import global.Common as Com
import global.Action as gAct
import global.Object as gObj
import global.Validate as gVal
import global.WebDriverMethods as WebD
import timesheets.Navigate as tNav
import myProjects.Validate as mVal
import myProjects.Action as mAct
import search.Action as sAct
import search.Validate as sVal
import risks.Validate as rVal
import excel.Action as eAct
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.Project
import models.GenerateProject
import com.kms.katalon.core.testdata.reader.ExcelFactory

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads"
String fileType = "xlsx"
def downLoadFileNames = mAct.getAllFileOfFileTypeInFolder(downloadPath, fileType)

String searchItem = "Projects"
String searchVal = "Projects"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String ProjectName = Com.generateRandomText(10)
	String newDate = Com.todayDate()
	String[] newProject = [ProjectName, "customer_oreert", "Full Time Calendar (My Region)" , "Project", "PPM Solutions Inc", "", "", "RES006", newDate, "", "TestProject_Auto", "TestAuto_Proj", ""]	
	Project project = GenerateProject.createProject(newProject)
	component.createProject(project)


Rep.nextTestStep("Select [Projects] from search filter")
	Nav.selectSearchFilter(searchItem)
	
	gObj.buttonClickSync(Const.columnPicker + "a_New Filter")
	gObj.buttonClickSync(Const.columnPicker + "a_Search")

	
Rep.nextTestStep("Click onto [Export] button")
	gObj.buttonClickSync(Const.search + "a_Export")
	gAct.Wait(GVars.shortWait)
	

Rep.nextTestStep("Open Downloaded spreadsheet")
	def ExportData
	def newDownLoadFileNames = mAct.getAllFileOfFileTypeInFolder(downloadPath, fileType)
	gAct.Wait(1)
	int length = newDownLoadFileNames.size()
	
	String fileDate = newDate.replace("/", "_")
	
	for(int intCt = length -1 ; intCt >= 0; intCt --){
		if(newDownLoadFileNames[intCt].contains('Snapshot')){
			newDownLoadFileNames.remove(intCt)
			continue
		}
		
		if(!newDownLoadFileNames[intCt].contains(fileDate)){
			newDownLoadFileNames.remove(intCt)
			continue
		}
	}
	length = newDownLoadFileNames.size()

	ExportData = eAct.openAndReadFile(downloadPath + "\\" + newDownLoadFileNames[length-1], searchVal)


Rep.nextTestStep("Verify the data within the spreadsheet matches the data within the project table")
	String value
	boolean prjLocated = false
	
	int rowNo = ExportData.getRowNumbers()
	
	for(int intCnt = 0; intCnt < rowNo; intCnt ++){
		value = ExportData.getValue(2, intCnt + 1)

		if(value == ProjectName){
			Rep.pass("Project exists in export.")
			prjLocated = true
			break
		}
	}
	
	if (!prjLocated){
		Rep.fail("Project does not exist in export.")
	}
	
	gAct.Wait(1)
	
	