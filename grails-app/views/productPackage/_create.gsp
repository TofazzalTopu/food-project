<%@ page import="com.bits.bdfp.inventory.product.ProductPackage" %>
<form name='gFormProductPackage' id='gFormProductPackage'>
  <g:hiddenField name="id" value="${productPackage?.id}" />
  <g:hiddenField name="version" value="${productPackage?.version}" />
  <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value="" />
  <g:hiddenField name="measureUnitConfiguration.id"  id="measureUnitConfigurationId" value="" />

  <div id="remote-content-productPackage"></div>

         <div class="element_row_content_container lightColorbg pad_bot0">
             <label class="txtright bold hight1x width1x">
                 <g:message code='productPackage.enterprise.label' default='Enterprise' />
                 <span class="mendatory_field"> * </span>
             </label>
             <g:if test="${list}">
                 <g:if test="${list.size()==1}">
                     <div  class='element-input inputContainer'>
                         <g:textField name="enterPriseName" readonly="readonly" value="${list[0].name}" />
                         <script type="text/javascript">
                             $(document).ready(function(){
                                 $("#enterpriseConfiguration").val("${list[0].id}");
                                 loadProduct();
                                 loadPackageType();
//                                 loadMeasurementUnit();
                             })
                         </script>
                     </div>
                 </g:if>
                 <g:else>
                     <div  class='element-input inputContainer width350'>
                         <div id="enterpriseList "></div>
                         <script type="text/javascript">
                             jQuery(document).ready(function () {
                                 var data=${result}
                                 $("#enterpriseList").empty();
                                 $("#enterpriseList").flexbox(data , {
                                     watermark: "Select Enterprise",
                                     width: 260,
                                     onSelect: function() {
                                         $("#enterpriseConfiguration").val($('#enterpriseList_hidden').val());
                                         if ($('#enterpriseList_hidden').val()){
                                             loadProduct();
                                             loadPackageType();
                                             $("#measureUnitConfigurationId").val('');
                                         }
                                     }
                                 });
                                 $('#enterpriseList_input').blur(function() {
                                     if($('#enterpriseList_hidden').val() == ''){
                                         $("#enterpriseList").val("");
                                         $('#searchKey').val('');
                                         $("#finishProduct").val('');
                                         $("#enterpriseConfiguration").val('');
                                         $("#measureUnitConfigurationId").val('');
                                     }
                                 });
                             });
                         </script>
                     </div>
                 </g:else>
             </g:if>
             <g:else>
                 <div  class='element-input inputContainer width350'>
                     <g:textField name="enterPriseName" readonly="readonly" value="" />
                     <script type="text/javascript">
                         jQuery(document).ready(function () {
                             MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.",0)
                         });
                     </script>
                 </div>
             </g:else>
         </div>


<div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code="productPackage.finishProduct.label" default="Product" />
        <span class="mendatory_field"> * </span>
    </label>

    <div class='element-input  inputContainer'>
        <input type="text" id="searchKey" name="searchKey" class="width350" />

    </div>
    <div style="padding-right: 32%;padding-top: 0.6%">
        <span id="search-btn-product-register-id" title="" role="button"
              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
            <span class="ui-button-text"></span>
        </span>
    </div>

    <input type="hidden" id="finishProduct" name="finishProduct.id"/>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code="productPackage.packageType.label" default="Package Type" />
        <span class="mendatory_field"> * </span>
    </label>

    <div class='element-input inputContainer width400'>
        <g:select name="packageType.id" class="validate[required] width155 " id="packageType" from="" optionKey="id" value=""  />

    </div>

</div>
<div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code="productPackage.measureUnitConfiguration.label" default="Measure Unit" />
        <span class="mendatory_field"> * </span>
    </label>

    <div class='element-input inputContainer width350'>
        %{--<g:select name="measureUnitConfiguration.id" id="measureUnitConfiguration" from="" optionKey="id" value=""  />--}%
        <g:textField name="measureUnitConfigurationName" readonly="true" id="measureUnitConfigurationName" value="" />

    </div>

</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code="productPackage.conversionFactor.label" default="Conversion Factor" />
        <span class="mendatory_field"> * </span>

    </label>

    <div class='element-input inputContainer' >
        <g:textField name="conversionFactor"  class="validate[required] width150" value="" />
    </div>

</div>
<div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code="productPackage.packSize.label" default="Pack Size" />
        <span class="mendatory_field"> * </span>
    </label>

    <div class='element-input inputContainer width350'>
        <g:textField name="packSize" class="validate[required]" readonly="true" value="" />
    </div>

</div>

  <div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxProductPackage();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxProductPackage();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_productPackage_form('#gFormProductPackage');" value="Cancel"/></span>
  </div>
</form>
