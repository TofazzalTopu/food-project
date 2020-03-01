%{--<%@ page import="com.bits.bdfp.customer.CustomerMaster" %>--}%
<style>
    /*#jqgh_cb .cbox {*/
        /*margin-left: 0 !important;*/
        /*display: none;*/
    /*}*/

    table tr td .cbox {
        margin-left: 0 !important;
        margin-top: 3px;
    }
</style>
<form name='gFormSetupIncentive' id='gFormSetupIncentive'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value=""/>
    <div id="remote-content-setupIncentive"></div>
    <br>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table class="create-form-table">
            <tr>
                <td width="100%" id="tabContainerTd">
                    <div id="createMemberPanel" style="margin-right: 2px;">
                        <table class='create-internal-table'>
                            <tr>
                                <td>
                                    <div id="tabs" class="flexDiv" style="width: 100%;">
                                        <ul>
                                            <li><a href="#fragment-1"><span>Target (in value only) Based Incentive Setup</span></a></li>
                                            <li><a href="#fragment-2"><span>Sales Amount Based Incentive Setup</span></a></li>
                                            <li><a href="#fragment-3"><span>Quantity Based Incentive Setup</span></a></li>
                                            <li><a href="#fragment-4"><span>Volume Based Incemtive Setup</span></a></li>
                                        </ul>

                                        <div id="fragment-1">
                                            <g:render template='targetBased' model="[targetBasedIncentive:targetBasedIncentive]"/>
                                            <g:render template='targetBasedScript' model="[
                                                   territoryConfigurationList:territoryConfigurationList,
                                                   territoryConfigurationSize:territoryConfigurationSize,
                                                   targetBasedIncentiveSlabList:targetBasedIncentiveSlabList,
                                                   targetBasedIncentiveCustomersList:targetBasedIncentiveCustomersList
                                            ]"/>
                                        </div>

                                        <div id="fragment-2">
                                            <g:render template='amountBased' model="[salesAmountBasedIncentive:salesAmountBasedIncentive]"/>
                                            <g:render template='amountBasedScript' model="[
                                                    territoryConfigurationList:territoryConfigurationList,
                                                    territoryConfigurationSize:territoryConfigurationSize,
                                                    salesAmountBasedIncentiveSlabList:salesAmountBasedIncentiveSlabList,
                                                    salesAmountBasedIncentiveCustomersList:salesAmountBasedIncentiveCustomersList
                                            ]"/>
                                        </div>

                                        <div id="fragment-3">
                                            <g:render template='quantityBased' model="[
                                                    productList:productList,
                                                    quantityBasedIncentive:quantityBasedIncentive
                                            ]"/>
                                            <g:render template='quantityBasedScript' model="[
                                                    territoryConfigurationList:territoryConfigurationList,
                                                    territoryConfigurationSize:territoryConfigurationSize,
                                                    quantityBasedIncentiveSlabList:quantityBasedIncentiveSlabList,
                                                    quantityBasedIncentiveCustomersList:quantityBasedIncentiveCustomersList
                                            ]"/>
                                        </div>

                                        <div id="fragment-4">
                                            <g:render template='volumeBased' model="[volumeBasedIncentive:volumeBasedIncentive]"/>
                                            <g:render template='volumeBasedScript' model="[
                                                    territoryConfigurationList:territoryConfigurationList,
                                                    territoryConfigurationSize:territoryConfigurationSize,
                                                    volumeBasedIncentiveSlabList:volumeBasedIncentiveSlabList,
                                                    volumeBasedIncentiveCustomersList:volumeBasedIncentiveCustomersList
                                            ]"/>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
        </table>

    </div>
    <br/>

    %{--<div class="buttons">--}%
        %{--<span class="button"><input type="button" name="create-button" id="create-button"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all"--}%
                                    %{--value="Save"--}%
                                    %{--onclick="executeAjaxCustomerMaster();"/></span>--}%
    %{--</div>--}%
</form>
