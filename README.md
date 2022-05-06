Simplify Web App for split the file on sections. 

Need change {upload.path} in application.properties by yourself. It's temp folder for input.

App work only with UTF-8 without BOM char.

Example to use via Postman: 
POST http://localhost:8080/api/upload 
Body form-data 
key file - "value select file"