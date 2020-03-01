<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
%{--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"--}%
%{--"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">--}%
%{--<html xmlns="http://www.w3.org/1999/xhtml">--}%
%{--<head>--}%
%{--<meta name='layout' content='main' />--}%
    %{--<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>--}%
    %{--<link rel="shortcut icon" href="${resource(dir: 'images/layout', file: 'favicon.gif')}" type="image/x-icon"/>--}%
    %{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'denied.css')}"/>--}%
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>
<title>Access Denied</title>
%{--</head>--}%

<body>
<div class="main_container">
    <h1>Access Denied</h1>
	<div class='denied'>

        <div class="content">Sorry, you're not authorized to view this page.</div>
    </div>
</div>
</body>
%{--</html>--}%
