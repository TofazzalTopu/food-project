<%@ page import="com.bits.bdfp.setup.salestarget.MonthlySalesTargetByVolume" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Setup Sales Target (Volume Based)</title>

<div class="main_container">
    <div class="content_container">
        <h1>Setup Sales Target (Volume Based)</h1>

        <h3>Setup Sales Target Details (Volume Based)</h3>
        <g:render template='/salestarget/monthlySalesTargetByVolume/create' model="[yearlySalesTargetByVolumeList: yearlySalesTargetByVolumeList, salesHeadByVolumeList: salesHeadByVolumeList]"/>
        <div class="clear height5"></div>
        <g:render template="/salestarget/monthlySalesTargetByVolume/script"/>
    </div>
</div>
