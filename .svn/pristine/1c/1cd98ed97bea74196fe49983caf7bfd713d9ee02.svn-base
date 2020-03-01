<%@ page import="com.bits.bdfp.inventory.setup.SalesCommission;com.bits.bdfp.geolocation.TerritoryConfiguration" %>
<form name="gFormSalesCommission" id="gFormSalesCommission">
<g:hiddenField name="id" value="${salesCommission?.id}"/>
<g:hiddenField name="version" value="${salesCommission?.version}"/>
<div id="remote-content-salesCommission"></div>
<style>
#jqgh_cb .cbox {
    margin-left: 0 !important;
    display: none;
}

#jqgrid-grid tr td .cbox {
    margin-left: 0 !important;
    margin-top: 3px;
}
</style>

<div class="element_row_content_container lightColorbg pad_bot0">
    <table>
        <tr>
            <td>
                <label class="txtright bold hight1x width1x">
                    Select Territory
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='element-input inputContainer'>
                    <g:select class="validate[required]" name="territory"
                              from="" optionKey="id"

                              id="territory" onchange="selectTerritory(this.value);"

                    />
                </div>
                %{--<div class='element-input inputContainer'>
                    <g:select class="validate[required]" name="territory"
                              from="${TerritoryConfiguration.list()}" optionKey="id"
                              noSelection="['': '- Select Territory -']"
                              id="territory" onchange="selectTerritory(this.value);"
                              value=""/>
                </div>--}%
            </td>

            <td>
                <div style="padding-left: 100px">
                    <label class="txtright bold hight1x width1x">
                        Select As of Date
                        <span class="mendatory_field">*</span>
                    </label>

                </div>
            </td>
            <td>
                <div class='element-input inputContainer'>
                    <g:textField name="asOfDate" class="validate[required]" value=""/>
                </div>
            </td>
        </tr>

        <tr>
            <td>
                <label class="txtright bold hight1x width1x">
                    Select DP
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='element-input inputContainer'>
                    <g:select class="validate[required]" name="distributionPoint" optionKey="id"
                              noSelection="['': '- Select DP -']"
                              id="dp" onchange="selectDP(this.value);"
                              value=""/>

                </div>
            </td>
        </tr>

        <tr>
            <td>
                <label class="txtright bold hight1x width1x">
                    Customer Name
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='element-input inputContainer'>
                    <g:textField name="customerName" value="" class="width140 validate[required]" readonly="true"/>
                </div>
            </td>
            <td>
                <div style="padding-left: 100px">
                    <label class="txtright bold hight1x width1x">
                        Customer ID
                        <span class="mendatory_field">*</span>
                    </label>

                </div>
            </td>
            <td>
                <div class='element-input inputContainer'>
                    <g:textField name="customerId" value="" readonly="true" class="width140 validate[required]"/>
                </div>
            </td>
        </tr>

        <tr>
            <td>
                <label class="txtright bold hight1x width1x">
                    Branch Commission
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='element-input inputContainer'>
                    <g:textField name="branchCommission" value="" class="width140 validate[required]" readonly="true"/>
                </div>
            </td>
        </tr>
    </table>
</div>

<div>
    <label class="txtright bold hight1x width370">
        Customer (Sales Man) Commission
    </label>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <div class="jqgrid-container">
        <table id="jqgrid-grid"></table>

        <div id="jqgrid-pager"></div>
    </div>
</div>
</form>