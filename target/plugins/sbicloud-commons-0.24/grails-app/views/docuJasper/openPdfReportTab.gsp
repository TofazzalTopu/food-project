<%@ page import="com.docu.commons.CommonConstants" %>
<%--
  Created by IntelliJ IDEA.
  User: feroz
  Date: 6/19/12
  Time: 11:54 AM
  To change this template use File | Settings | File Templates.
--%>
<g:render template="/layouts/reportIframeLayout"/>
<%
Map<String, String> data = params
String reportUrl = params.DocuReportUrl
String gspTitle = params.GspTitle
params.remove('action')
params.remove('controller')
params.remove('DocuReportUrl')
params.remove('GspTitle')
String queryString = params.collect {key, val -> "${key}=${val}"}.join("&")
%>
<title>${gspTitle}</title>
<iframe src="${reportUrl}?${queryString}" width="100%" height="100%"></iframe>