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
import com.kms.katalon.core.testdata.reader.ExcelFactory
import buildingBlocks.createComponentsLogedIn_AdminNew as component
import models.ProjectSnapshot
import models.GenerateProjectSnapshot

String downloadPath = "C:\\Katalon Studio\\KeyedInProjects\\downloads"
String fileType = "xlsx"
def downLoadFileNames = mAct.getAllFileOfFileTypeInFolder(downloadPath, fileType)

String searchItem = "Project Snapshots"
String searchVal = "Snapshot"

Rep.nextTestStep("KIP - V6 Stage Login page")
	Act.KeyedInProjectsPPMLoginAndVerify(GVars.usrNme, GVars.pwordEncryt, GVars.user)
	
	String snapshotName = Com.generateRandomText(10)
	String[] newProjectSnapshot = ["Project Innovation_CHROME_DRIVER", snapshotName, "Interim"]
	ProjectSnapshot projectSnapshot = GenerateProjectSnapshot.createProjectSnapshot(newProjectSnapshot)
	component.createProjectSnapshot(projectSnapshot)

	String snapshotName2 = Com.generateRandomText(10)
	String[] newProjectSnapshot2 = ["Project Innovation_CHROME_DRIVER", snapshotName2, "Interim"]
	ProjectSnapshot projectSnapshot2 = GenerateProjectSnapshot.createProjectSnapshot(newProjectSnapshot2)
	component.createProjectSnapshot(projectSnapshot2)
	
	
Rep.nextTestStep("Select [Projects Snapshots] from search filter")
	Nav.selectSearchFilter(searchItem)

	
Rep.nextTestStep("Click onto [Export] button")
	gObj.buttonClickSync(Const.search + "a_Export")
	gAct.Wait(GVars.shortWait)
	

Rep.nextTestStep("Open Downloaded spreadsheet")
	def newDownLoadFileNames = mAct.getAllFileOfFileTypeInFolder(downloadPath, fileType)
	gAct.Wait(1)
	int length = newDownLoadFileNames.size()
	def ExportData = eAct.openAndReadFile(downloadPath + "\\" + newDownLoadFileNames[length-1], searchVal)
	

Rep.nextTestStep("Verify the data within the spreadsheet matches the data within the project table")
	String value
	boolean prjLocated = false
	
	int rowNo = ExportData.getRowNumbers()
	
	for(int intCnt = 0; intCnt < rowNo; intCnt ++){
		value = ExportData.getValue(1, intCnt + 1)

		if(value == snapshotName){
			Rep.pass("Project Snapshot exists in export.")
			prjLocated = true
			break
		}
	}
	
	if (!prjLocated){
		Rep.fail("Project snapshot does not exist in export.")
	}
	
	
	