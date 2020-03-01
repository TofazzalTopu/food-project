

<%@ page import="com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Secondary Demand Order</title>

<div class="main_container">
  <div class="content_container">
    <h1>Secondary Demand Order</h1>

    <h3>Secondary Demand Order Details</h3>

    <div style="width:100%;">
      <g:render template='create'  model="[result:result, list:list, applicationUser:applicationUser, tentativeDeliveryManList: tentativeDeliveryManList, userType:  userType, customerMaster: customerMaster, deliveryManList: deliveryManList]"/>
      <br/>
      <g:render template="script"/>
    </div>


    <div id="dialog-confirm-territoryConfiguration" title="Delete alert" style="display: none">
      <p><span class="ui-icon ui-icon-alert"
               style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
      </p>
    </div>
  </div>
</div>
