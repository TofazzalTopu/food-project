<%@ page import="com.bits.bdfp.customer.CustomerMaster" %>
<form name='gFormCustomerMaster' id='gFormCustomerMaster'>
    <g:hiddenField name="id" value="${customerMaster?.id}"/>
    <g:hiddenField name="version" value="${customerMaster?.version}"/>
    <div id="remote-content-customerMaster"></div>
    <br>

    <div class="element_row_content_container lightColorbg pad_bot0 width1000">
        <table class="create-form-table">
            <tr>
                <td width="100%" id="tabContainerTd">
                    <div id="createMemberPanel" style="margin-right: 2px;">
                        <table class='create-internal-table'>
                            <tr>
                                <td>
                                    <div id="tabs" class="flexDiv width1000">
                                        <ul style="height:29px">
                                            <li><a href="#fragment-1"><span>Basic Info</span></a></li>
                                            <li><a href="#fragment-2"><span>Address</span></a></li>
                                            <li><a href="#fragment-3"><span>Financial Info</span></a></li>
                                            <li><a href="#fragment-4"><span>Assignment</span></a></li>
                                            <li><a href="#fragment-5"><span>Ship to Address</span></a></li>
                                        </ul>

                                        <div id="fragment-1">
                                            <g:render template='/customer/customerMaster/customer/basicInformation' />
                                        </div>

                                        <div id="fragment-2">
                                            <g:render template='/customer/customerMaster/customer/address'/>
                                        </div>

                                        <div id="fragment-3">
                                            <g:render template='/customer/customerMaster/customer/financialInformation' />
                                        </div>

                                        <div id="fragment-4">
                                            <g:render template='/customer/customerMaster/customer/assignment' model="[list:list]"/>
                                        </div>

                                        <div id="fragment-5">
                                            <g:render template='/customer/customerMaster/customer/shipToAddress'/>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>

                        <div></div>

                    </div>
                </td>
            </tr>
        </table>

    </div>
    <br/>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Create"
                                    onclick="executeAjaxCustomerMaster();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxCustomerMaster();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_form('#gFormCustomerMaster');" value="Cancel"/></span>
    </div>
</form>
