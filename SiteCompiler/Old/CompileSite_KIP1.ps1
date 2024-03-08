
$UserID = 'AUTOTEST@WORKFLOWS.COM'
$Password = 'TEst1234'
# Default Requests and Sites files to current folder of this script
$SitePath = $PSScriptRoot + "\" + "Sites_KIP1.txt"
$RequestsPath = $PSScriptRoot + "\" + "Requests.txt"
$LoginUrl = 'KIPWebPortal/secure/sec_login.aspx'

if(([System.IO.File]::Exists($SitePath)) -and ([System.IO.File]::Exists($RequestsPath))){
    # Site and Requets file exists
    # Loop through each Site URL in Sites.text and call each Request URL in Requests.txt
    # Attempts to login to each site in order to compile the MVC pages
    foreach($Site in [System.IO.File]::ReadLines($SitePath)){
        if (!([string]::IsNullOrEmpty($Site.Trim()))){
        
            if (!$Site.EndsWith("/")) {
                $Site = $Site + "/"
            }

            Write-Host "Logging in to site: " $Site "as " $UserID
            # Login
            $response = Invoke-WebRequest ($Site + $LoginUrl) -SessionVariable KIPSession
            $Form = $response.Forms[0]
            #$Form | Format-List
            #$Form.Fields
            $Form.Fields["txtUserID"] = $UserID
            $Form.Fields["txtPassword"] = $Password

            $response = Invoke-WebRequest -Uri ($Site + $LoginUrl) -WebSession $KIPSession -Method POST -Body $Form.Fields
            $Form = $response.Forms[0]
            # Still on the login page so the login has failed
            if ($Form.Action -eq "./sec_login.aspx") {
                Write-Host ("Failed to login to site: " + $Site + "as " + $UserID + ".")
                Write-Host "The compilation will continue but the MVC pages will not be compiled and will return a LoggedOut status. Please check the UserID and Password variables."
            }
            else{
                Write-Host ("Login Successful")
            }

            Write-Host "Begin compiling site: " $Site 

            # Loop through each request in requests.txt
            foreach($request in [System.IO.File]::ReadLines($RequestsPath)){

                if (!([string]::IsNullOrEmpty($request.Trim()))){
                    try {
                        $response = Invoke-WebRequest -Uri ($Site + $request) -WebSession $KIPSession -ErrorAction Stop
                    
                        if ($response.StatusDescription -eq "LoggedOut"){
                            Write-Host ($request + " Status:" + $response.StatusCode  + " " + $response.StatusDescription + ". Unable to compile MVC page.")
                        }
                        else{
                             Write-Host ($request + " Status:" + $response.StatusCode  + " " + $response.StatusDescription)
                        }
                    } 
                    catch {
                        $StatusCode = $_.Exception.Response.StatusCode.value__	
                        $StatusDescription = $_.Exception.Response.StatusDescription
	                    Write-Host ($request + " Status:" + $StatusCode + " " + $StatusDescription)
                    }    
                }
            }
            Write-Host "Site Compilation Completed for: " $Site 
        }
    }
} 
else{

    if(![System.IO.File]::Exists($SitePath)){
        Write-Host ("Sites.txt file does not exist in the " + $PSScriptRoot + " folder.")
    }
    if(![System.IO.File]::Exists($RequestsPath)){
        Write-Host ("Requests.txt file does not exist in the " + $PSScriptRoot + " folder.")
    }
}

#Set-ExecutionPolicy RemoteSigned