<%@ page import="com.bits.bdfp.common.DepositPool" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="depositPool.create.label" default="Deposit Cash To Deposit Pool"/></title>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="depositPool.create.label" default="Deposit Cash To Deposit Pool"/></h1>

        <h3><g:message code="depositPool.info.label" default="Deposit Cash To Deposit Pool"/></h3>

        <div class="content_container">
            <g:render template='/depositCashToPool/create' model="[poolList: poolList]"/>
            <br/>
            <g:render template="/depositCashToPool/script"/>
        </div>

        <div class="jqgrid-container">
            <table id="jqgrid-grid-amount"></table>
            <div id="jqgrid-amount-pager"></div>
        </div>
        <div class="height10"></div>
        <div class="jqgrid-container" id="jqgrid-container-details">
            <table id="jqgrid-grid"></table>
            <div id="jqgrid-pager"></div>
        </div>
        <div class="height10"></div>
        <table>
            <tr>
                <td>
                    <label style="margin-right: 10px;" for='countryInfo' class='customLabel'><g:message
                            code='division.countryInfo.label' default='Amount to Deposit'/><span class="mendatory_field">*</span></label>

                    <g:textField name="amountToDeposit" onblur="checkDepositAmount();"/>
                    <g:hiddenField name="amountToDepositHidden"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label style="margin-right: 10px;" for='countryInfo' class='customLabel'><g:message
                            code='division.countryInfo.label' default='Deposit To'/><span class="mendatory_field">*</span></label>
                    <g:radio name="pm" id="rdBank" value="bank" checked="checked" onclick="bankOnclick();"/> Bank <br/>
                    <g:radio name="pm" id="rdCash" value="cash" onclick="cashOnclick();"/> cash<br/>
                </td>
            </tr>
            <tr>
                <td id="tdBank">

                    <label style="margin-right: -5px;" for='countryInfo' class='customLabel'><g:message
                            code='division.countryInfo.label' default='Deposit to Bank'/><span class="mendatory_field">*</span></label>
                    <div class='element-input inputContainer'>

                        <g:select name="bankAccount.id" id="bankAccount" noSelection="['': 'Select Bank Account']"
                                  class="validate[required]"
                                  from="" optionKey="" value=""/>

                    </div>

                </td>

                <td id="tdCash" hidden="hidden">

                    <label style="padding-right: 0px;" for='countryInfo' class='customLabel'><g:message
                            code='division.countryInfo.label' default='Deposit to Cash Pool'/><span class="mendatory_field">*</span></label>
                    <div class='element-input inputContainer'>

                        <g:select name="depositPoolNameDep" style="max-width: 140px;"
                                  id="depositPoolNameDep"
                                  from="${depositPoolList}"
                                  optionKey="id"
                                  optionValue="name"
                                  noSelection="['': 'Select Pool']"/>

                    </div>

                </td>
            <tr>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="search" id="search-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Submit"
                                                    onclick="executeAjax();"/></span>

                    </div>
                </td>
            </tr>
        </tr>
        </table>

        <div id="dialog-confirm-depositPool" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>






