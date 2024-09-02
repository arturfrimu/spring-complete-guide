# How to Kill a Process on a Specific Port in Windows

Sometimes, you may need to free up a port that is being used by a process. Here's how you can kill a process using a specific port on Windows using the Command Prompt or PowerShell.

## Using Command Prompt

1. **Open Command Prompt as Administrator**:
    - Right-click on the Command Prompt in the Start menu and select "Run as administrator".

2. **Find the Process ID (PID) Using the Port**:
    - To find out which process is using a specific port (e.g., 8080), run the following command:
      ```cmd
      netstat -aon | findstr :8080
      ```
    - Look for the PID in the output. This is typically the number in the last column.

3. **Kill the Process**:
    - Use the following command to kill the process by its PID (replace `<PID>` with the actual PID):
      ```cmd
      taskkill /F /PID <PID>
      ```
    - Replace `<PID>` with the PID of the process you found in the previous step.

## Using PowerShell

1. **Open PowerShell as Administrator**:
    - Right-click on the PowerShell icon in the Start menu and select "Run as administrator".

2. **Find and Kill the Process Using a Specific Port**:
    - You can find and kill the process using a specific port (e.g., 8080) with a single command:
      ```powershell
      Get-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess | Stop-Process -Force
      ```

This command retrieves the process using the specified port and immediately stops it. Ensure you replace `8080` with the port number you are interested in.