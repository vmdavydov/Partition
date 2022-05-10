Simplify Web App for split the file on sections. 

Need change {upload.path} in application.properties by yourself. It's temp folder for files. 
P.S. (not important, cause folder deleted after send response)

App work with UTF-8 without BOM char.

Example to use via Postman: 
POST http://localhost:8080/api/upload 
Body form-data 
key file - "value select file"

Example input/output:  
`#` Football  
Football teams  
`##` EPL  
`###` Man United  
`###` Chelsea  
`##` La Liga  
`###` Real Madrid  
`###` Barcelona  
`#` Basketball  
Basketball teams  
`##` Russian Superleage  
`###` CSKA  

Football 1  
EPL 1.1  
Man United 1.1.1  
Chelsea 1.1.2  
La Liga 1.2  
Real Madrid 1.2.1  
Barcelona 1.2.2  
Basketball 2  
Russian Superleage 2.1  
CSKA 2.1.1  

Football 1  
Football teams  
EPL 1.1  
Man United 1.1.1  
Chelsea 1.1.2  
La Liga 1.2  
Real Madrid 1.2.1  
Barcelona 1.2.2  
Basketball 2  
Basketball teams  
Russian Superleage 2.1  
CSKA 2.1.1  