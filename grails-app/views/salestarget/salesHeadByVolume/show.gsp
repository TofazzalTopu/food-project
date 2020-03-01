<%@ page import="com.bits.bdfp.setup.salestarget.SalesHeadByVolume" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Annual Target (Volume)</title>

<div class="main_container">
    <div class="content_container">
        <h1>Setup Annual Sales Target (volume Based) for the Sales Head</h1>

        <h3>Setup Annual Sales Target (volume Based) for the Sales Head</h3>
        <g:render template='/salestarget/salesHeadByVolume/create' model="[currentYear: currentYear]"/>
        <div class="clear height5"></div>
        <g:render template="/salestarget/salesHeadByVolume/script"/>
    </div>
</div>