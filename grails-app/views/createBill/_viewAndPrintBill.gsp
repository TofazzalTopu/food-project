<%--
  Created by IntelliJ IDEA.
  User: mdtofazzal.hossain
  Date: 5/25/2016
  Time: 1:30 PM
--%>




<%@ page import="com.bits.bdfp.bill.CreateBill" %>


<div id="spinnerCreateBill" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>
<form name='gFormCreateBill' id='gFormCreateBill'>
<g:hiddenField name="id" value="${createBill?.id}" />
<g:hiddenField name="version" value="${createBill?.version}" />
<g:hiddenField name="customerMasterId" id="customerMasterId" />

<div id="remote-content-createBill"></div>
<div>

    <div class="element_container_big" style="width: 990px;">
        <div class="block-title">

            <div class='element-title'>
                <g:message code='createBill.billNumber.label' default='Bill Number' />
            </div>

            <div class="clear"></div>
        </div>
        <div class="block-input">
            <div class='element-input inputContainer '><g:textField id="billNumber" name="billNumber"  value="${createBill?.billNumber}" /></div>
            <div class="clear"></div>
        </div>
    </div>


<div class="element_container_big" style="width: 990px;">
    <div class="block-title">

        <div class='element-title'>
            <g:message code='createBill.purchaseOrderDate.label' default='Bill Generated Date From' />

        </div>

        <div class='element-title'>
            <g:message code='createBill.vatChallanDate.label' default='Bill Generated Date To' />

        </div>

        <div class="clear"></div>
    </div>
    <div class="block-input">

        <div class='element-input inputContainer'><g:textField name="billGeneratedFrom" id="billGeneratedFrom" value="${createBill?.purchaseOrderDate}" /></div>
        <script type='text/javascript'>
            $(document).ready(function(){ $('#billGeneratedFrom').datepicker({dateFormat:'yy-mm-dd',
                changeMonth:true,
                changeYear:true
            });
                $('#billGeneratedFrom').mask('${com.docu.commons.CommonConstants.DATE_FORMAT_Y_M_D}',{});});
        </script>


        <div class='element-input inputContainer'><g:textField name="billGeneratedTo" id="billGeneratedTo" value="${createBill?.vatChallanDate}" /></div>
        <script type='text/javascript'>
            $(document).ready(function(){ $('#billGeneratedTo').datepicker({dateFormat:'yy-mm-dd',
                changeMonth:true,
                changeYear:true
            });
                $('#billGeneratedTo').mask('${com.docu.commons.CommonConstants.DATE_FORMAT_Y_M_D}',{});});
        </script>

        <div class="clear"></div>
    </div>
</div>

    <div class="element_container_big" style="width: 990px;">
        <div class="block-title">

            <div class='element-title'>
                <g:message code='createBill.customerName.label' default='Customer Name' />
            </div>
            <div class='element-title'>
                <g:message code='createBill.customerId.label' default='Customer Id' />

            </div>

            <div class="clear"></div>
        </div>
        <div class="block-input">
            <div class='element-input inputContainer '><g:textField name="customerName.id" id="customerName"   optionKey="id" value="${createBill?.customerName?.id}" onchange="selectCustomerId(this.value);" noSelection="['null': '']" /></div>
            <div class='element-input inputContainer setup-css-numeric-currency'><g:textField name="customerId" value="${fieldValue(bean: createBill, field: 'customerId')}" /></div>

            <div class="clear"></div>
        </div>
    </div>

</div>

    %{-- from="${com.bits.bdfp.customer.CustomerMaster.list()}"--}%

<table id="invoice-detail-grid"></table>

<div id="invoice-detail-grid-pager"></div>
<div class="clear"></div>

<div class="clear"></div>
<div class="buttons" style="margin-left:10px;">

    <span class="button"><input type="button" name="search-button" id="search-button-createBill" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search" onclick="searchBill();"/></span>
   %{-- <span class="button"><input type='button' name="delete-button" id="delete-button-createBill" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxCreateBill();"/></span>--}%
    <span class="button"><input type="button" name="viewBill-button" id="viewBill-button-createBill" class="ui-button ui-widget ui-state-default ui-corner-all" value="Print Bill" onclick="generateBill();"/></span>
    <span class="button"><input name="clearFormButtonCreateBill" class="ui-button ui-widget ui-state-default ui-corner-all" type="button" onclick=" reset_form('#gFormCreateBill');" value="Cancel"/></span>
    <a target="_blank" href="${resource(dir:'createBill', file:'show')}"> <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Create New Bill"></a>
</div>
</form>
