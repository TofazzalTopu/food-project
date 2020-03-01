<%@ page import="com.bits.bdfp.inventory.warehouse.ReplacementMiscellaneousTransactions" %>


<div id="spinnerMiscellaneousTransactions" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormMiscellaneousTransactions' id='gFormMiscellaneousTransactions'>
    <g:hiddenField name="id" value="${miscellaneousTransactions?.id}"/>
    <g:hiddenField name="version" value="${miscellaneousTransactions?.version}"/>
    <div id="remote-content-miscellaneousTransactions"></div>

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
                                            <li><a href="#fragment-1"><span>Replacement</span></a></li>
                                            <li><a href="#fragment-2"><span>Entertainment</span></a></li>
                                            <li><a href="#fragment-3"><span>Sample</span></a></li>
                                            <li><a href="#fragment-4"><span>Damage</span></a></li>
                                            <li><a href="#fragment-5"><span>Return to Production</span></a></li>
                                        </ul>

                                        <div id="fragment-1">
                                            <g:render template='replacement' />
                                            <g:render template='replacementScript' model="[dpList: dpList, dpSize: dpSize]"/>
                                        </div>

                                        <div id="fragment-2">
                                            <g:render template='entertainment' />
                                            <g:render template='entertainmentScript' model="[dpList: dpList, dpSize: dpSize]"/>
                                        </div>

                                        <div id="fragment-3">
                                            <g:render template='sample' />
                                            <g:render template='sampleScript' model="[customers: customers, dpList: dpList, dpSize: dpSize]" />
                                        </div>

                                        <div id="fragment-4">
                                            <g:render template='damage' />
                                            <g:render template='damageScript' model="[dpList: dpList, dpSize: dpSize]"/>
                                        </div>

                                        <div id="fragment-5">
                                            <g:render template='returnToProduction' />
                                            <g:render template='returnToProductionScript' model="[factoryDpList: factoryDpList,factoryDpSize:factoryDpSize]"/>
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

    <div class="clear"></div>
</form>
