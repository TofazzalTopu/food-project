<%@ page import="com.bits.bdfp.inventory.sales.DistributionPoint" %>
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

.customLabel4 {
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
<script type="text/javascript">
    $(document).ready(function () {
        $("#depositPoolDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });

        $('#depositPoolDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
    });
</script>
<g:form id="frmDepositCashToPool">
    <fieldset>
        <div style="float:left;">
            <table>
                <tr>
                    <td>
                        <label style="padding-right: 5px; text-align: right;" for='countryInfo' class='customLabel width80'><g:message
                                code='division.countryInfo.label' default='Date'/></label>
                        <div class='element-input inputContainer'>
                            <g:textField class="cutomInput"
                                         id="depositPoolDate" name="depositPoolDate" value=""/>
                            <g:hiddenField name="depositPoolDateHidden" value=""/>
                        </div>
                    </td>
                    <td>
                        <label style="padding-right: 5px; text-align: right;" for='countryInfo' class='customLabel width100'><g:message
                                code='division.countryInfo.label' default='Security Deposit'/></label>
                        <div class='inputContainer' style="margin: 5px 10px 0 5px;">
                            <g:checkBox name="securityDeposit"
                                        onclick=""
                                        value=""/>
                        </div>
                    </td>
                    <td>
                        <label style="padding-right: 5px; text-align: right;" for='countryInfo' class='customLabel width80'><g:message
                                code='division.countryInfo.label' default='Pool Name'/></label>

                        <div class='element-input inputContainer'>
                            <g:select name="depositPoolName" style="max-width: 140px; margin-left: 0px !important;"
                                      id="depositPoolName"
                                      from="${poolList}"
                                      optionKey="id"
                                      optionValue="name"
                                      noSelection="['': 'Select Pool']"/>

                            <g:hiddenField name="depositPoolNameHidden" value=""/>
                        </div>
                    </td>
                    <td>
                        <div class="buttons">
                            <span class="button">
                                <input type="button" name="search" id="search-button"
                                       class="ui-button ui-widget ui-state-default ui-corner-all"
                                       value="Search"
                                       onclick="paymentDetailsInfo();"/></span>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </fieldset>
</g:form>