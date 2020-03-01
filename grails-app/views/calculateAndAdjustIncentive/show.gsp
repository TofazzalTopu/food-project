<%--
  Created by IntelliJ IDEA.
  User: liton.miah
  Date: 2/28/2016
  Time: 11:40 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="calculateAndAdjustIncentive.create.label" default="Calculate and Adjust Incentive"/></title>

<div class="main_container" style="width: 950px;">
    <div class="content_container">
        <h1 style="width: 930px;"><g:message code="calculateAndAdjustIncentive.create.label" default="Calculate and Adjust Incentive"/></h1>

        <h3><g:message code="calculateAndAdjustIncentive.info.label" default="Calculate and Adjust Incentive against Receivable"/></h3>

        <div style="width:100%;">
            <g:render template='create'/>
            <br/>
            <g:render template="script" />
        </div>

    </div>
</div>

