<%@ page import="com.bits.bdfp.customer.CustomerMaster" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Create Employee</title>
<div class="main_container width1000">
    <div class="content_container width1000">
        <h1>Create New Employee</h1>
        <h3>Employee Info</h3>
        <div style="width:100%;">
            <g:render template='/customer/customerMaster/employee/create' model="[list: list, result: result, supervisorList: supervisorList, territoryIds: territoryIds]"/>
            <br/>
            <g:render template="/customer/customerMaster/employee/script" model="[result: result, supervisorList: supervisorList, territoryIds: territoryIds]"/>
        </div>
    </div>
</div>

