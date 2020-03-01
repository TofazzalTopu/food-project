<%@ page import="com.bits.bdfp.setup.salestarget.MonthlySalesTargetByAmount" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Update Sales Target</title>

<div class="main_container">
    <div class="content_container">
        <h1>Update Sales Target By Amount</h1>
        <h3>Update Sales Target By Amount Details</h3>
        <g:render template='/salestarget/monthlySalesTargetByAmount/edit' model="[yearlySalesTargetByAmountList: yearlySalesTargetByAmountList]"/>
        <div class="clear height5"></div>
        <g:render template="/salestarget/monthlySalesTargetByAmount/editScript"/>
    </div>
</div>
