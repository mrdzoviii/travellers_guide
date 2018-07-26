<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	  xmlns:f="http://java.sun.com/jsf/core"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:p="http://primefaces.org/ui"
>
<h:head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Travel Advertiser</title>
</h:head>
<h:body>
<h:form enctype="multipart/form-data">
    <p:growl id="messages" showDetail="true" />
 
    <p:fileUpload value="#{fileUploadView.file}" mode="simple" skinSimple="true"/>
 
    <p:commandButton value="Submit" ajax="false" actionListener="#{fileUploadView.upload(fileUploadView.file)}" disabled="false" />
</h:form>
</h:body>
</html>