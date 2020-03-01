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
    width: 180px;
}

.customInput {
    height: 18px;
    width:410px;
    margin-left:5px !important;
}
</style>
<g:form name='gFormReverseFinishGood' id='gFormReverseFinishGood'>

    <fieldset>
        <div style="float:left;">
            <table>
                <tr>
                    <td>
                        <table id="itemSpecification">

                            <tr>

                                <td>
                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label'
                                            default='Transaction/Product Reference'/></label>

                                        <input type="text" id="transactionRef" class="customInput"/>
                                        <input type="hidden" id="referenceId" value="">

                                        <span id="search-btn-customer-register-id" title="" role="button"
                                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                                            <span class="ui-button-text"></span>
                                        </span>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="buttons">
                            <span class="button"><input type="button" name="search" id="search-button"
                                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                                        value="Show"
                                                        onclick="loadGridInfo();"/></span>
                        </div>
                    </td>
                </tr>
            </table>
        </div>

        <div style="clear:both;"></div>
        <br/>
    </fieldset>
</g:form>
