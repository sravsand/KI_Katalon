# ************************************************************************************************
# Script Name:	CompileSite.ps1
# Version:		2.0 
# Date:			24/01/2022
# Author:		Martin Philips
# ************************************************************************************************
# Script Purpose
#   Script will compile one or more KeyedIn Enterprise Sites
#   Requires 2 files: Sites.txt and Requests.txt
#   For each site specified in Sites.txt the script:
#       1. Login to the site - a successful login is required to compile the MVC pages
#       2. Make a request to each items specified in the Requests.txt file
# ******************************************************************************************************
# 			                     Version History
# ******************************************************************************************************
# Version 1.0
# 	MP1 20/09/2019 - Created 
# Version 2.0
# 	MP1 24/01/2022 - Updated script to work with Powershell 7 
# ******************************************************************************************************
$UserID = 'AUTOTEST@WORKFLOWS.COM'
$Password = 'TEst1234'
# Default Requests and Sites files to current folder of this script
$SitePath = $PSScriptRoot + "\" + "Sites.txt"
$RequestsPath = $PSScriptRoot + "\" + "Requests.txt"
$LoginUrl = 'KIPWebPortal/secure/sec_login.aspx'

if(([System.IO.File]::Exists($SitePath)) -and ([System.IO.File]::Exists($RequestsPath))){
    # Site and Requests file exists
    # Loop through each Site URL in Sites.text and call each Request URL in Requests.txt
    # Attempts to login to each site in order to compile the MVC pages
    foreach($Site in [System.IO.File]::ReadLines($SitePath))
    {
        if (!([string]::IsNullOrEmpty($Site.Trim())))
        {       
            if (!$Site.EndsWith("/")) {
                $Site = $Site + "/"
            }

            Write-Host "Logging in to site: " $Site "as " $UserID
            # Login
            $response = Invoke-WebRequest ($Site + $LoginUrl) -SessionVariable KIPSession

            # Loop through the input fields on the html form
            # Need to get the values so they can submiited as part of the form
            ForEach($field in $response.InputFields)
            {
                if ($field.type -eq 'hidden')
                {
                   if ($field.name -eq '__VIEWSTATEGENERATOR')
                   {
                    $hdnViewStateGen = $field.value
                   }
                   elseif ($field.name -eq '__VIEWSTATE')
                   {
                    $hdnViewState = $field.value
                   }
                   elseif ($field.name -eq '__EVENTVALIDATION')
                   {
                    $hdnEventValidation = $field.value
                   }
                   elseif ($field.name -eq 'hdnLogonID')
                   {
                    $hdnLogonID = $field.value
                   }
                }
            }
            
            # create a form object that can be posted on the login request
            $form = @{
                __VIEWSTATEGENERATOR = $hdnViewStateGen
                __VIEWSTATE = $hdnViewState
                __EVENTVALIDATION = $hdnEventValidation
                hdnLogonID = $hdnLogonID
                txtUserID = $UserID
                txtPassword = $password
                btnLogin = 'Sign In'            
            }
            
            $loginSuccess = $true
            # Submit the login form
            $response = Invoke-WebRequest -Uri ($Site + $LoginUrl) -WebSession $KIPSession -Body $form -Method POST
            
            if ($response.Content.Contains('divErrors'))
            {
                # Login failed if there's a divErrors in the returned markup
                # divErrors is where an login error is displayed
                $loginSuccess = $false
                Write-Host ("Failed to login to site: " + $Site + "as " + $UserID + ".")
                Write-Host "The compilation will continue but the MVC pages will not be compiled. Please check the UserID and Password variables."    
            }
            else
            {
                Write-Host ("Login Successful")
            }
            
            Write-Host "Begin compiling site: " $Site 

            # Loop through each request in requests.txt
            foreach($request in [System.IO.File]::ReadLines($RequestsPath)){

                if (!([string]::IsNullOrEmpty($request.Trim())))
                {              
                    if ($loginSuccess -eq $true -or $request.Contains('.aspx') -or $request.Contains('.asmx'))
                    {
                        #if login has failed. Only make requests for web forms pages (.aspx & .asmx)
                        # Need to be logged in to compile MVC pages
                        try 
                        {
                            $response = Invoke-WebRequest -Uri ($Site + $request) -WebSession $KIPSession -ErrorAction Stop 
                            #$html = $response.Content

                            Write-Host ($request + " Status:" + $response.StatusCode  + " " + $response.StatusDescription)    
                        }
                        catch 
                        {
                            $StatusCode = $_.Exception.Response.StatusCode.value__	
                            $StatusDescription = $_.Exception.Response.StatusDescription
                            Write-Host ($request + " Status:" + $StatusCode + " " + $StatusDescription)
                        }
                                                    
                    }
                    else 
                    {
                        Write-Host ($request + " Status: Not logged in. Unable to compile MVC page.") 
                    }                      
                }
            }
            Write-Host "Site Compilation Completed for: " $Site 
        }
    }
} 
else
{
    if(![System.IO.File]::Exists($SitePath))
    {
        Write-Host ("Sites.txt file does not exist in the " + $PSScriptRoot + " folder.")
    }
    if(![System.IO.File]::Exists($RequestsPath))
    {
        Write-Host ("Requests.txt file does not exist in the " + $PSScriptRoot + " folder.")
    }
}

#Set-ExecutionPolicy RemoteSigned