<%@ page import="com.bits.bdfp.setup.salestarget.MonthlySalesTargetByVolume" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Update Sales Target (Volume Based)</title>

<div class="main_container">
    <div class="content_container">
        <h1>Update Sales Target (Volume Based)</h1>
        <h3>Update Sales Target Details (Volume Based)</h3>
        <g:render template='/salestarget/monthlySalesTargetByVolume/edit' model="[yearlySalesTargetByVolumeList: yearlySalesTargetByVolumeList]"/>
        <div class="clear height5"></div>
        <g:render template="/salestarget/monthlySalesTargetByVolume/editScript"/>
    </div>
</div>
