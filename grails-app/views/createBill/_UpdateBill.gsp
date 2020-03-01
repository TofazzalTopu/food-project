<%--
  Created by IntelliJ IDEA.
  User: mdtofazzal.hossain
  Date: 5/29/2016
  Time: 4:06 PM
--%>




<%@ page import="com.bits.bdfp.bill.CreateBill" %>


<div id="spinnerCreateBill" class="spinner" style="display:none;" align="left" xmlns="http://www.w3.org/1999/html"
     xmlns="http://www.w3.org/1999/html">
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
%{--<g:hiddenField name="version" value="${createBill?.version}" />--}%
<div id="remote-content-createBill"></div>
<div>

    <div class="element_container_big" style="width: 990px;">
        <div class="block-title">

            <div class='element-title'>
                <g:message code='createBill.billNumber.label' default='Bill Number' />

            </div>
            <div class='element-title'>
                <g:message code='createBill.billGenerationDate.label' default='Bill Generation Date' />

            </div>

            <div class="clear"></div>
        </div>
        <div class="block-input">
            <div class='element-input inputContainer '><g:textField id="billNumber" readonly="true" name="billNumber"  value="${createBill?.billNumber}" /></div>
            %{--<div class='element-input inputContainer'><g:textField name="billGenerationDate" id="billGenerationDate" format="dd-mm-yy" date="${new java.util.Date()}" value="${createBill?.billGenerationDate}" /></div>--}%
            <div class='element-input inputContainer'><g:textField name="billGenerationDate" disabled="disabled" id="billGenerationDate" value="${createBill?.billGenerationDate}"/></div>

        %{--    <script type='text/javascript'>
                $(document).ready(function(){ $('#billGenerationDate').datepicker({dateFormat:'dd-mm-yy',
                    changeMonth:true,
                    changeYear:true
                });
                    $('#billGenerationDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}',{});});
            </script>--}%

            <div class="clear"></div>
        </div>
    </div>

</div>
%{--

    <div class="element_container_big" style="width: 990px;">
        <div class="block-title">
            <div class='element-title'>
                <g:message code='createBill.territory.label' default='Territory' />

            </div>
            <div class='element-title'>
                <g:message code='createBill.territoryGeoLocation.label' default='Territory Geo Location' />

            </div>

            <div class='element-title'>
                <g:message code='createBill.customerCategory.label' default='Customer Category' />
                <span class="mendatory_field"> * </span>
            </div>
            <div class='element-title'>
                <g:message code='createBill.customerName.label' default='Customer Name' />
            </div>
            <div class='element-title'>
                <g:message code='createBill.customerId.label' default='Customer Id' />

            </div>

            <div class="clear"></div>
        </div>
        <div class="block-input">
            <div class='element-input inputContainer '><g:select name="territory.id" id="territory" from="${com.bits.bdfp.geolocation.TerritoryConfiguration.list()}" optionKey="id" value="" onchange="selectGeoLocation(this.value);" noSelection="['null': '']" /></div>
            <div class='element-input inputContainer '><g:select name="territoryGeoLocation.id" id="territoryGeoLocation" from="${com.bits.bdfp.geolocation.TerritorySubArea.list()}" optionKey="id" value="" onchange="selectCustomerCategory(this.value);" noSelection="['null': '']" /></div>
            <div class='element-input inputContainer '><g:select name="customerCategory.id" id="customerCategory" from="${com.bits.bdfp.customer.CustomerCategory.list()}" optionKey="id" value="${createBill?.customerCategory?.id}" onchange="selectCustomerByCategory(this.value);" noSelection="['null': '']"   /></div>
            <div class='element-input inputContainer '><g:select name="customerName.id" id="customerName" from="" optionKey="id" value="${createBill?.customerName?.id}" onchange="selectCustomerId(this.value);" noSelection="['null': '']" /></div>
            <div class='element-input inputContainer setup-css-numeric-currency'><g:textField name="customerId" value="${fieldValue(bean: createBill, field: 'customerId')}" /></div>

            <div class="clear"></div>
        </div>
    </div>
--}%

<div class="element_container_big">
    <div class="block-title">

        <div class='element-title'>
            <g:message code='createBill.purchaseOrderNumber.label' default='Purchase Order Number' />

        </div>
        <div class='element-title'>
            <g:message code='createBill.purchaseOrderDate.label' default='Purchase Order Date' />

        </div>
        <div class='element-title'>
            <g:message code='createBill.vatChallanNumber.label' default='Vat Challan Number' />

        </div>

        <div class='element-title'>
            <g:message code='createBill.vatChallanDate.label' default='Vat Challan Date' />

        </div>

        <div class="clear"></div>
    </div>
    <div class="block-input">

        <div class='element-input inputContainer '><g:textField name="purchaseOrderNumber" value="${createBill?.purchaseOrderNumber}" /></div><div class='element-input inputContainer'><g:textField name="purchaseOrderDate" id="purchaseOrderDate" value="${createBill?.purchaseOrderDate}" /></div>
        <script type='text/javascript'>
            $(document).ready(function(){ $('#purchaseOrderDate').datepicker({dateFormat:'dd-mm-yy',
                changeMonth:true,
                changeYear:true
            });
                $('#purchaseOrderDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}',{});});
        </script>
        <div class='element-input inputContainer '><g:textField name="vatChallanNumber" value="${createBill?.vatChallanNumber}" /></div>

        <div class='element-input inputContainer'><g:textField name="vatChallanDate" id="vatChallanDate" value="${createBill?.vatChallanDate}" /></div>
        <script type='text/javascript'>
            $(document).ready(function(){ $('#vatChallanDate').datepicker({dateFormat:'dd-mm-yy',
                changeMonth:true,
                changeYear:true
            });
                $('#vatChallanDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}',{});});
        </script>

        <div class="clear"></div>
    </div>
</div>


<div class="clear"></div>
<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="update-button" id="update-button-createBill" class="ui-button ui-widget ui-state-default ui-corner-all" value="Update" onclick="executeAjaxUpdateBill();"/></span>
    %{--<span class="button"><input type='button' name="delete-button" id="delete-button-createBill" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxCreateBill();"/></span>--}%
    %{--<span class="button"><input type="button" name="new-button" id="new-button-createBill" class="ui-button ui-widget ui-state-default ui-corner-all" value="Create New Bill" > <a target="_blank" href="${resource(dir:'createBill', file:'show')}"></a></input></span>--}%
    <span class="button"><input name="clearFormButtonCreateBill" class="ui-button ui-widget ui-state-default ui-corner-all" type="button" onclick=" reset_form('#gFormCreateBill');" value="Cancel"/></span>

    %{--<a target="_blank" href="${resource(dir:'createBill', file:'show')}"> <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Create New Bill"></a>--}%

</div>
</form>
