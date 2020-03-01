<%@ page import="com.bits.bdfp.common.BankAccount;org.codehaus.groovy.grails.commons.ApplicationHolder; com.bits.bdfp.inventory.sales.DistributionPoint" %>
<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>
<style>
.customLabel {
    background-color: #d7dfe7;
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 17px;
    margin: 0;
    padding: 5px 10px 0;
    width: 160px;
}

.customLabel1 {
    background-color: #d7dfe7;
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 17px;
    margin-left: 4px;
    padding: 5px 10px 0;
    width: 160px;
}

.cutomInput {
    height: 18px;
    width: 160px;
}
</style>
<g:form name='gFormDistributionPoint' id='gFormDistributionPoint'>
    <g:hiddenField name="id" value="${distributionPoint?.id}"/>
    <g:hiddenField name="version" value="${distributionPoint?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-distributionPoint"></div>
%{--<fieldset>--}%
%{--<div style="float:left;">--}%
    <div class="main_container">
          <div class=content_container>
              <div class="element_container_big">

                  <div class="block-title">
                        <div class="element-title "><g:message code='division.countryInfo.label' default='Factory DP'/>
                         <span class="mendatory_field">*</span>
                        </div>

                      <div class="element-title  "><g:message code='division.countryInfo.label' default='Distribution Point Name'/>
                        <span class="mendatory_field">*</span>
                    </div>

                 <div class="element-title "><g:message code='division.countryInfo.label' default='Legacy ID'/>
                     <span class="mendatory_field">*</span>
                     </div>

                          <div class="clear"></div>
             </div>

      <div class="block-input">
                  <div class='element-input inputContainer'>
                   <input id="Yes" type="radio" name="isFactory" checked="checked" value="true"/>Yes
                     <input id="No" type="radio" name="isFactory" value="false"/>No
                  </div>

                 <div class='element-input inputContainer'>
                       <g:textField class="cutomInput validate[required] " name="name" value=""/>
                  </div>

                 <div class='element-input inputContainer '>
                      <g:textField class="cutomInput validate[required] " name="code" value=""/>
                 </div>

                                 <div class="clear"></div>
      </div>
              </div>
          </div>

        <div class=content_container>
            <div class="element_container_big">

                <div class="block-title">

                    <div class="element-title ">
                        <g:message code='division.countryInfo.label' default='Contact Email'/><span
                    class="mendatory_field">*</span>
                    </div>

                    <div class="element-title ">
                        <g:message code='division.countryInfo.label' default='Contact Number'/><span
                    class="mendatory_field">*</span>
                    </div>

                    <div class="element-title width320"><g:message code='division.countryInfo.label' default='Contact Address'/><span
                            class="mendatory_field">*</span>
                    </div>

                <div class="clear"></div>
        </div>

            <div class="block-input">

                    <div class='element-input inputContainer'>
                          <g:textField class="cutomInput validate[required]" name="email" value=""/>
                    </div>

                    <div class='element-input inputContainer '>
                         <g:textField class="cutomInput validate[required]" name="mobile" value=""/>
                    </div>

                    <div class='element-input inputContainer input_width320'>
                    <g:textField contenteditable="false" class="cutomInput validate[required] width300"
                                 name="address" value=""/>
                     </div>

            <div class="clear"></div>
            </div>
            </div>
        </div>


        <div class=content_container>
           <h3>Assign Other Information</h3>
            <div class="element_container_big">

                <div class="block-title">

                    <div class="element-title width340">
                        <g:message code='division.countryInfo.label' default='Assign Enterprise'/><span
                            class="mendatory_field">*</span>
                    </div>

                    <div class="element-title ">
                        <g:message code='division.countryInfo.label' default='Assign Territory'/><span
                            class="mendatory_field">*</span>
                    </div>

                    %{--<div class="element-title ">--}%
                        %{--<g:message code='division.bankAccount.label' default='Bank Account'/>--}%
                    %{--</div>--}%

                    <div class="clear"></div>
                </div>

                <div class="block-input">

                    <div class='element-input inputContainer input_width340'>
                        <g:if test="${result}">

                            <div id="enterpriselist"></div>
                            <script type="text/javascript">
                                jQuery(document).ready(function () {
                                    $("#enterpriselist").empty();
                                    $("#enterpriselist").flexbox(${result}, {
                                        watermark: "Select Enterprise",
                                        width: 300,
                                        onSelect: function () {
                                            $("#enterpriseConfiguration").val($('#enterpriselist_hidden').val());
                                            selectTerritoryAndInventory($('#enterpriselist_hidden').val());
                                        }

                                    });
                                    $('#enterpriselist_input').val("");
                                    $('#enterpriselist_input').addClass("validate[required]");

                                    $('#enterpriselist_input').blur(function () {
                                        if ($('#enterpriselist_input').val() == '') {
                                            $("#enterpriseConfiguration").val("");
                                        }
                                    });
                                });
                            </script>
                        </g:if>
                    </div>

                    <div class='element-input inputContainer input_width320'>
                        <div id="territoryConfigurationList"></div>
                        <g:hiddenField name="territoryConfiguration.id" id="territoryConfiguration" value="" class="width300"/>
                    </div>

                    %{--<div class='element-input inputContainer input_width320'>--}%
                        %{--<g:select class="width250" name="bankAccount.id" id="bankAccount" value=""--}%
                                  %{--from="${BankAccount.list()}" optionKey="id"--}%
                                  %{--noSelection="['': 'Select Bank Account']"/>--}%
                    %{--</div>--}%

                    <div class="clear"></div>
                </div>
            </div>
        </div>

    <div class=content_container>
        <div class="element_container_big">
            <div class="block-title">

            <div class="element-title width340">
                <g:message code='division.countryInfo.label' default='Assign Inventory'/><span
                    class="mendatory_field">*</span>
                </div>

                <div class="element-title ">
                    <g:message code='division.countryInfo.label' default='Assign In-Charge'/><span
                        class="mendatory_field">*</span>
               </div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input inputContainer input_width340'>
                    <div id="inventoryList"></div>
                    <g:hiddenField name="warehouse.id" id="warehouse" value="" class="validate[required]"/>
                    </div>

                <div class='element-input inputContainer input_width320'>
                    <div id="assignInchargeList"></div>
                    <g:hiddenField name="inCharge.id" id="inCharge" value="" class="validate[required]"/>
                </div>

                <div class="clear"></div>
                </div>

            </div>
        </div>

    <div class=content_container>
            <div class="element_container_big">

      <div class="block-title">
          <div class="element-title style:a">
              <g:message code='division.countryInfo.label' default='Default Customer'/>
              </div>
          <div class="clear"></div>
      </div>

                <div class="block-input">
                    <div class="element-input inputContainer input_width320">
                        <input type="text" id="defaultCustomer" class="cutomInput width280"/>
                        <input type="hidden" name="defaultCustomer.id" id="defaultCustomerId" value="">

                        <span id="search-btn-customer-register-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    </div>
                    <div class="clear"></div>

                    </div>

             </div>
        </div>


<table>
<tr>
<td>
<table>
</table>


        <tr>
            <td>
                <div id="geoLocationInfo">
                    <div class="jqgrid-container width800">
                        <table id="jqgrid-grid-geolocation"></table>

                    </div>
                </div>
            </td>
        </tr>
    </table>

    </div>

    <div style="clear:both;"></div>
    <br/>
%{--</fieldset>--}%
    <div class="buttons">
        <span class="button"><input type="button" name="select-button" id="select-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value="Select All"
                                    onclick="checkUnCheck();"/></span>
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxDistributionPoint();"/></span>
        <span class="button" style="display: none !important;"><input type='button' name="delete-button"
                                                                      id="delete-button"
                                                                      class="ui-button ui-widget ui-state-default ui-corner-all"
                                                                      value='Delete'
                                                                      onclick="deleteAjaxDistributionPoint();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_form('#gFormDistributionPoint');" value="Cancel"/></span>
    </div>

</g:form>
