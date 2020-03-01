<%@ page import="com.bits.bdfp.setup.salestarget.SalesHead" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Sales Head Setup</title>

<div class="main_container">
    <div class="content_container">
        <h1>Declare Sales Head for New Year</h1>

        <h3>Sales Head Details Information</h3>
        <g:render template='/salestarget/salesHead/create' model="[currentYear: currentYear]"/>
        <div class="clear height5"></div>
        <g:render template="/salestarget/salesHead/script"/>
    </div>
</div>
