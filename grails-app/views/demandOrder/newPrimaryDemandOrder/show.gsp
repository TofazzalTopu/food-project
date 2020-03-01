<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 9/17/15
  Time: 10:08 AM
--%>

<%@ page import="com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Create New Primary Demand Order</title>

<div class="main_container">
    <div class="content_container">
        <h1>Create New Primary Demand Order</h1>
        <h3>New Primary Demand Order Details</h3>

        <div style="width:100%;">
            <g:render template='/demandOrder/newPrimaryDemandOrder/create' model="[userType:  userType, customerMaster:customerMaster,
                                                                                   distributionPointList:distributionPointList, customerShippingAddressList : customerShippingAddressList]"/>
            <br/>
            <g:render template="/demandOrder/newPrimaryDemandOrder/script"/>
        </div>
    </div>
</div>