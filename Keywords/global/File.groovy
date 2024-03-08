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
import com.kms.katalon.core.util.KeywordUtil
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.VerRsrc.VS_FIXEDFILEINFO;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import internal.GlobalVariable
import org.testng.Assert
import myProjects.Action as mAct

public class File {
	public static int MAJOR = 0;
	public static int MINOR = 1;
	public static int BUILD = 2;
	public static int REVISION = 3;

	public static int getMajorVersionOfProgram(String path) {
		return getVersionInfo(path)[MAJOR];
	}

	public static int getMinorVersionOfProgram(String path) {
		return getVersionInfo(path)[MINOR];
	}

	public static int getBuildOfProgram(String path) {
		return getVersionInfo(path)[BUILD];
	}

	public static int getRevisionOfProgram(String path) {
		return getVersionInfo(path)[REVISION];
	}

	@Keyword
	public static String getVersionInfo(String path) {
		IntByReference dwDummy = new IntByReference();
		dwDummy.setValue(0);

		int versionlength = com.sun.jna.platform.win32.Version.INSTANCE.GetFileVersionInfoSize(path, dwDummy);

		byte[] bufferarray = new byte[versionlength];
		Pointer lpData = new Memory(bufferarray.length);
		PointerByReference lplpBuffer = new PointerByReference();
		IntByReference puLen = new IntByReference();
		boolean fileInfoResult = com.sun.jna.platform.win32.Version.INSTANCE.GetFileVersionInfo(path, 0, versionlength, lpData);
		boolean verQueryVal = com.sun.jna.platform.win32.Version.INSTANCE.VerQueryValue(lpData, "\\", lplpBuffer, puLen);

		VS_FIXEDFILEINFO lplpBufStructure = new VS_FIXEDFILEINFO(lplpBuffer.getValue());
		lplpBufStructure.read();

		int v1 = (lplpBufStructure.dwFileVersionMS).intValue() >> 16;
		int v2 = (lplpBufStructure.dwFileVersionMS).intValue() & 0xffff;
		int v3 = (lplpBufStructure.dwFileVersionLS).intValue() >> 16;
		int v4 = (lplpBufStructure.dwFileVersionLS).intValue() & 0xffff;
		return v1 + "." + v2 + "." + v3 + "." + v4
	}


	@Keyword
	static def checkFileExits(String downloadPath, String fileName) {

		File dir = new File(downloadPath);

		File[] dirContents = dir.listFiles();

		String lastAttempt = "";

		if (dirContents.length > 0) {

			for (int i = 0; i < dirContents.length; i++) {

				if (dirContents[i].getName().equals(fileName)) {

					// File has been found, it can now be deleted:

					dirContents[i].delete();

					KeywordUtil.markPassed(fileName + ' exist in ' + downloadPath + ' and was deleted')

					return true;

				}

				lastAttempt = dirContents[i].getName().equals(fileName);
			}

			if (lastAttempt != fileName) {

				KeywordUtil.markFailed(fileName + 'does not exist in' + downloadPath)

				return false;
			}
		}

		return false;
	}


	@Keyword
	static def checkFile(String downloadPath, String fileName) {

		ArrayList<String> dataLoadNames = mAct.getAllFilesNamesInFolder(downloadPath)
		boolean match = false
		for(String item : dataLoadNames){
			if(item == fileName){
				match = true
				break
			}
		}

		return match
	}
}
