<%@ page import="com.bits.bdfp.setup.salestarget.DailySalesTargetFinishProduct" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Update Daily Sales Target (Volume Based)</title>

<div class="main_container">
    <div class="content_container">
        <h1>Update Daily Sales Target (Volume Based)</h1>

        <h3>Daily Sales Target Details (Volume Based)</h3>
        <g:render template='/salestarget/dailySalesTargetFinishProduct/create' model="[yearlySalesTargetByVolumeList: yearlySalesTargetByVolumeList]"/>
        <div class="clear height5"></div>
        <g:render template="/salestarget/dailySalesTargetFinishProduct/script"/>
    </div>
</div>
