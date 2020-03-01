<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 9/21/15
  Time: 10:32 AM
--%>

<%@ page import="com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Update New Primary Demand Order</title>

<div class="main_container">
    <div class="content_container">
        <h1>Update New Primary Demand Order</h1>
        <h3>New Primary Demand Order Info</h3>

        <div style="width:100%;">
            <g:render template='/demandOrder/newPrimaryDemandOrder/update' model="[primaryDemandOrder: primaryDemandOrder, distributionPointList:distributionPointList]"/>
            <br/>
            <g:render template="/demandOrder/newPrimaryDemandOrder/updateScript" model="[primaryDemandOrder: primaryDemandOrder, distributionPointList:distributionPointList]"/>
        </div>
    </div>
</div>