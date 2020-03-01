


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
  <g:hiddenField name="custId" id="custId" value="" />

    <div id="remote-content-createBill"></div>
    <div>
      
          <div class="element_container_big width820" >
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
                <div class='element-input inputContainer '><g:textField id="billNumber" name="billNumber" readonly="true" value="${createBill?.billNumber}" /></div>
                %{--<div class='element-input inputContainer'><g:textField name="billGenerationDate" id="billGenerationDate" format="dd-mm-yy" date="${new java.util.Date()}" value="${createBill?.billGenerationDate}" /></div>--}%
                <div class='element-input inputContainer'><g:formatDate format="dd-MM-yyyy" date="${new java.util.Date()}" id="billGenerationDate" value="${createBill?.billGenerationDate}"/></div>
                %{--<p> <input type="text" id="billGeneration"> </p>--}%
                <script type='text/javascript'>
                    $(document).ready(function(){ $('#billGenerationDate').datepicker({dateFormat:'dd-mm-yy',
                        changeMonth:true,
                        changeYear:true
                    });
                        $('#billGenerationDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}',{});});
                </script>

                <div class="clear"></div>
            </div>
          </div>

          <div class="element_container_big width820">
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

                <div class="clear"></div>
            </div>
            <div class="block-input">
                <div class='element-input inputContainer '><g:select name="territory.id" id="territory" from="${com.bits.bdfp.geolocation.TerritoryConfiguration.list()}" optionKey="id" value="" onchange="selectGeoLocation(this.value);" noSelection="['null': 'Select Territory']" /></div>
                <div class='element-input inputContainer '><g:select name="territoryGeoLocation.id" id="territoryGeoLocation" from="${com.bits.bdfp.geolocation.TerritorySubArea.list()}" optionKey="id" value="" onchange="selectCustomerCategory(this.value);" noSelection="['null': 'Select Territory GeoLocation']" /></div>
                <div class='element-input inputContainer '><g:select name="customerCategory.id" id="customerCategory" from="" optionKey="id" value="${createBill?.customerCategory?.id}" onchange="selectCustomerName(this.value);"  noSelection="['null': 'Select Customer Category']"   /></div>

                <div class="clear"></div>
            </div>
          </div>

        <div class="element_container_big width820">
            <div class="block-title">

                <div class='element-title width360'>
                    <g:message code='createBill.customerName.label' default='Customer Name' />
                </div>

                <div class='element-title'>
                    <g:message code='createBill.customerId.label' default='Customer Id' />
                </div>
                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input inputContainer width360 alin_left'>
                    <input type="text" name="customerName" id="customerName" value="${fieldValue(bean: createBill, field: 'customerName')}" class="width330"/>
                    %{--<div class="element-input inputContainer width50">--}%

                        <span id="search-btn-customer-register-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    %{--</div>--}%
                </div>
                <div class='element-input inputContainer  alin_left'><g:textField name="customerId" readonly="true" value="${fieldValue(bean: createBill, field: 'customerId')}" /></div>

                <div class="clear"></div>
            </div>
        </div>

        <div class="element_container_big width820"   >
            <div class="block-title"></div>
            <div class="clear"></div>
        </div>
        <div class="block-input"></div>
    </div>
<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="search-button" id="search-button-createBill" class="ui-button ui-widget ui-state-default ui-corner-all" value="Show Unadjusted Invoices" onclick="showUnadjustedInvoices();"/></span>

    <div class="clear"></div>
</div>

        <div class="element_container_big width820" >
            <div class="block-title"></div>
            <div class="clear"></div>
        </div>
        <div class="block-input"></div>
            </div>
        <div class="element_container_big ">

            <div class="jqgrid-container">
                <table id="jqgrid-grid-unadjusted-invoice"></table>
                <div id="jqgrid-pager-unadjusted-invoice"></div>
            </div>

        </div>
          <div class="element_container_big width820"  >
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

          <div class="element_container_big width820">
            <div class="block-title">
                <div class="clear"></div>
            </div>
            <div class="block-input">
                <div class="clear"></div>
            </div>
          </div>

  </div>

<div class="buttons">
    <span class="button"><input type="button" name="add-button" id="add-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Add"
                                onclick="addNewItemToCollectionGrid();"/></span>
</div>

    <table id="invoice-detail-grid"></table>

    <div id="invoice-detail-grid-pager"></div>
    <div class="clear"></div>

<div class="element_container_big width820">
    <div class="block-title">

        <div class='element-title'>
            <g:message code='createBill.TotalAmountInTaka.label' default='Total Amount In Taka' />
        </div>
        </div>
<div class="block-input">
    <div class='element-input inputContainer setup-css-numeric-currency'><g:textField name="a" hidden="true"  width="600px;" value="" /></div>
    <div class='element-input inputContainer setup-css-numeric-currency'><g:textField name="b" hidden="true"   width="600px;" value="" /></div>
    <div class='element-input inputContainer setup-css-numeric-currency'><g:textField name="c" hidden="true"  width="600px;" value="" /></div>
    <div  class='element-input inputContainer setup-css-numeric-currency'><g:textField name="TotalAmountInTaka" width="800px;" readonly="true"  value="${fieldValue(bean: createBill, field: 'TotalAmountInTaka')}" /></div>
</div>
</div>

  <div class="clear"></div>
  <div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="create-button" id="create-button-createBill" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxCreateBill();"/></span>
      <span class="button"><input type="button" name="viewBill-button" id="viewBill-button-createBill" class="ui-button ui-widget ui-state-default ui-corner-all" value="Preview Bill" onclick="generateBillForCreateBill();"/></span>
    <span class="button"><input name="clearFormButtonCreateBill" class="ui-button ui-widget ui-state-default ui-corner-all" type="button" onclick=" reset_form('#gFormCreateBill');" value="Cancel"/></span>
  </div>
</form>
