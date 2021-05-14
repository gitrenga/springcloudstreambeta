# springcloudstreambeta

### steps followed

*1.installed the chocolately package manager from power shell using below command
	Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1')) 
*2.installed rabbit MQ using below command
	choco install rabbitmq
*3.run below command for refreshing the environment variables in windows
	refreshenv
*4.run the below command to enable the browser access of the rabbit MQ
	rabbitmq-plugins enable rabbitmq_management
*5.Rabbit MQ can be accessed in the below URL
	http://localhost:15672/
*6.Start the Spring boot APP
*7.The Spring boot will create a queue through which we can publish the messsage from browser
*8.once message is published in the queue it will invoke the function in the spring boot APP