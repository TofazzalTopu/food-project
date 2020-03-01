<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.docu.accesscontrol.ModuleInfo; com.bits.bdfp.inventory.workflow.Workflow" %>
<script type="text/javascript">
    var jqGridApplicationUserList = null;
    var jqGridCustomerList = null;
    $(document).ready(function(){
        $('#employeeSearchKey').blur(function () {
            if ($('#employeeSearchKey').val() == '') {
                clearEmployeeDataField();
            }
        });
        jQuery('#employeeSearchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term, enterpriseId: $("#enterpriseConfiguration").val()};
                var url = '${resource(dir:'workflow', file:'listEmployee')}?query=' + $('#employeeSearchKey').val();
                DocuAutoComplete.setSpinnerSelector('employeeSearchKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                loadEmployeeDataInField(ui.item.id, ui.item.username, ui.item.enterprise, ui.item.designation);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + "PIN: " + item.code + "; Name: " + item.name + "; " + "Enterprise: " + item.enterprise + "; " + "User Name: " + item.username + "; " + "Designation: " + item.designation + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };
        $('#search-btn-employee-id').click(function () {
            var enterpriseId = $("#enterpriseConfiguration").val();
            if(enterpriseId){
                var url = '${resource(dir:'workflow', file:'popupEmployeeListPanel')}';
                var params = {};
                DocuAjax.html(url, params, function (html) {
                    $.fancybox(html);
                });
            } else{
                MessageRenderer.renderErrorText("Select Enterprise");
            }
        });
        jqGridApplicationUserList = $("#jqgrid-grid-applicationUser").jqGrid({
            url:'',
            datatype: "local",
            colNames:[
                'ID',
                'workflowUserMappingId',
                'User Name',
                'Designation',
                'Priority Sequence',
                ''
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'workflowUserMappingId', index:'workflowUserMappingId', hidden: true},
                {name:'username', index:'username', width:150, align:'left'},
                {name:'designation', index:'designation', width:200, align:'left'},
                {name:'prioritySequence', index:'prioritySequence', width:150, align:'center',sorttype:'number', formatter:"number",formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}, editable: true, editrules:{number:true}},
                {name:'delete', index:'delete', width:20, align:'center', sortable:false}
            ],
            rowNum: -1,
            viewrecords:true,
            caption:"Assigned Application User List",
            autowidth:true,
            height:true,
            sortname: 'prioritySequence',
            sortorder: "asc",
            scrollOffset:0,
            gridview:true,
//            multiselect: true,
            rownumbers: true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            loadComplete: function (data) {
                $.each(data.rows, function (i, item) {
                    var rowData = $("#jqgrid-grid-applicationUser").jqGrid('getRowData', data.rows[i].id);
                    rowData.delete = '<a  href="javascript:deAllocateApplicationUser(' + data.rows[i].id + ')" class="ui-icon ui-icon-trash" title="Delete"></a>';
                    $('#jqgrid-grid-applicationUser').jqGrid('setRowData', data.rows[i].id, rowData);
                });
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText,'');
            }
        });
        $('#customerSearchKey').blur(function () {
            if ($('#customerSearchKey').val() == '') {
                clearCustomerData();
            }
        });
        jQuery('#customerSearchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var url = '${resource(dir:'workflow', file:'listCustomerByEnterprise')}?query=' + $('#customerSearchKey').val() + "&enterpriseId=" + $("#enterpriseConfiguration").val();
                DocuAutoComplete.setSpinnerSelector('customerSearchKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                loadCustomerData(ui.item.id, ui.item.name, ui.item.code, ui.item.enterprise, ui.item.present_address);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + "ID: " + item.code + "; Name: " + item.name + "; "+ "; Category: " + item.category + "; " + "Enterprise: " + item.enterprise + "; " + "Address: " + item.present_address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };
        $('#search-btn-customer-id').click(function () {
            var url = '${resource(dir:'workflow', file:'popupCustomerListPanel')}';
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        });
        jqGridCustomerList = $("#jqgrid-grid-customerMaster").jqGrid({
            url:'',
            datatype: "local",
            colNames:[
                'ID',
                'workflowCustomerMappingId',
                'Customer ID',
                'Customer Name',
                'Customer Address',
                ''
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'workflowCustomerMappingId', index:'workflowCustomerMappingId', hidden: true},
                {name:'customerCode', index:'customerCode', width:80, align:'center'},
                {name:'customerName', index:'customerName', width:200, align:'left'},
                {name:'customerAddress', index:'customerAddress', width:280, align:'left'},
                {name:'delete', index:'delete', width:20, align:'center', sortable:false}
            ],
            rowNum: -1,
            viewrecords:true,
            caption:"Assigned Customer List",
            autowidth:true,
            height:true,
            sortname: 'id',
            sortorder: "asc",
            scrollOffset:0,
            gridview:true,
//            multiselect: true,
            rownumbers: true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            loadComplete: function (data) {
                $.each(data.rows, function (i, item) {
                    var rowData = $("#jqgrid-grid-customerMaster").jqGrid('getRowData', data.rows[i].id);
                    rowData.delete = '<a  href="javascript:deAllocateCustomer(' + data.rows[i].id + ')" class="ui-icon ui-icon-trash" title="Delete"></a>';
                    $('#jqgrid-grid-customerMaster').jqGrid('setRowData', data.rows[i].id, rowData);
                });
                $("#prioritySequence").val($("#jqgrid-grid-customerMaster").getGridParam("reccount") + 1);
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText,'');
            }
        });
    });
    function loadEmployeeDataInField(applicationUserId, username, enterprise, designation) {
        $("#applicationUserId").val(applicationUserId);
        $("#username").val(username);
        $("#designation").val(designation);
    }
    function clearEmployeeDataField() {
        $("#applicationUserId").val("");
        $("#username").val("");
        $("#designation").val("");
    }
    function loadFeatureInfo() {
        $('#feature-info-html').html('');
        $('#featureList').children().remove().end();
        $('#actionList').children().remove().end();

        $.ajax({
            url: "${request.contextPath}/featureControllerMapping/getFeatureListByModule",
            dataType: "json",
            data: {moduleId:$('#moduleList').val()},
            success: function(json) {
                $('#featureList').append('<option value="">Select Feature</option>');
                for (key in json) {
                    $('#featureList').append('<option value="' + json[key].id + '">' + json[key].featureName + '</option>')
                }
            }
        });
    }

    function loadActionInfo() {
        $('#feature-info-html').html('');
        $('#actionList').children().remove().end();

        $.ajax({
            url: "${request.contextPath}/featureControllerMapping/getActionListByFeature",
            dataType: "json",
            data: {featureId:$('#featureList').val()},
            success: function(json) {
                $('#actionList').append('<option value="">Select Action</option>');
                for (key in json) {
                    $('#actionList').append('<option value="' + json[key].id + '">' + json[key].actionName + '</option>')
                }
            }
        });
    }

    function loadMenuUrl(featureActionId) {
        if(featureActionId){
            $.ajax({
                url: "${request.contextPath}/${params.controller}/getMenuUrl",
                dataType: "string",
                data: {featureActionId: featureActionId},
                success: function(menuUrl) {
                    $('#menuName').val(menuUrl);
                }
            });
        } else{
            $('#menuName').val("");
        }
    }

    function addCustomerToGrid(){
        var customerId = $("#customerId").val();
        if(!customerId){
            MessageRenderer.renderErrorText("Customer is not selected");
            return
        }
        var customerCode = $("#customerCode").val();
        var customerName = $("#customerName").val();
        var customerAddress = $("#customerAddress").val();

        var rowTo = jqGridCustomerList.getRowData(customerId.toString());
        if (!rowTo.id) {
            var newRowData = [
                {
                    'id':customerId.toString(), 'workflowCustomerMappingId': '', 'customerCode':customerCode, 'customerName':customerName, 'customerAddress': customerAddress,
                    'delete': '<a  href="javascript:deAllocateCustomer(' + customerId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                }
            ];
            jqGridCustomerList.addRowData(customerId.toString(), newRowData[0]);
            clearCustomerData();
        }else{
            MessageRenderer.renderErrorText("Selected Customer is already added to grid");
        }
    }
    function addApplicationUserToGrid(){
        var applicationUserId = $("#applicationUserId").val();
        if(!applicationUserId){
            MessageRenderer.renderErrorText("Application User is not selected");
            return
        }
        var username = $("#username").val();
        var designation = $("#designation").val();
        var prioritySequence = Number($("#prioritySequence").val());

        var rowTo = jqGridApplicationUserList.getRowData(applicationUserId.toString());
        if (!rowTo.id) {
            var newRowData = [
                {
                    'id':applicationUserId.toString(), 'workflowUserMappingId': '', 'username':username, 'designation':designation, 'prioritySequence': prioritySequence,
                    'delete': '<a  href="javascript:deAllocateApplicationUser(' + applicationUserId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                }
            ];
            jqGridApplicationUserList.addRowData(applicationUserId.toString(), newRowData[0]);
            $("#applicationUserId").val("");
            $("#username").val("");
            $("#designation").val("");
            $("#prioritySequence").val(jqGridApplicationUserList.getGridParam("reccount") + 1)
        }else{
            MessageRenderer.renderErrorText("Selected Application User is already added to grid");
        }
    }
    function deAllocateApplicationUser(applicationUserId){
        var applicationUser = jqGridApplicationUserList.getRowData(applicationUserId.toString());
        if(Number(applicationUser.workflowUserMappingId) <= 0){
            $('#jqgrid-grid-applicationUser').jqGrid('delRowData', applicationUserId);
        }else{
            deleteAjaxWorkflowApplicationUserFromDB(applicationUser.workflowUserMappingId, applicationUserId);
        }
    }
    function deleteAjaxWorkflowApplicationUserFromDB(workflowUserMappingId, applicationUserId) {    // Delete record
        if(workflowUserMappingId) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-applicationUser").dialog({
                resizable: false,
                height:'auto',
                width: 450,
                modal: true,
                title: 'Delete alert',
                buttons: {
                    'Delete item(s)': function() {
                        if(!version){
                            version = 0
                        }
                        $(this).dialog('close');
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            data:"id=" + workflowUserMappingId,
                            url: "${resource(dir:'workflowUserMapping', file:'delete')}",
                            success: function(message) {
                                if(message.type == 1){
                                    $('#jqgrid-grid-applicationUser').jqGrid('delRowData', applicationUserId);
                                    $("#jqgrid-grid-applicationUser").trigger("reloadGrid");
                                }
                                MessageRenderer.render(message);
                            }
                        });
                    },
                    Cancel: function() {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
        }
    }
    function deAllocateCustomer(customerId){
        var customer = jqGridCustomerList.getRowData(customerId.toString());
        if(Number(customer.workflowCustomerMappingId) <= 0){
            $('#jqgrid-grid-customerMaster').jqGrid('delRowData', customerId);
        }else{
            deleteAjaxWorkflowCustomerFromDB(customer.workflowCustomerMappingId, customerId);
        }
    }
    function deleteAjaxWorkflowCustomerFromDB(workflowCustomerMappingId, customerId) {    // Delete record
        if(workflowCustomerMappingId) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-applicationUser").dialog({
                resizable: false,
                height:'auto',
                width: 450,
                modal: true,
                title: 'Delete alert',
                buttons: {
                    'Delete item(s)': function() {
                        if(!version){
                            version = 0
                        }
                        $(this).dialog('close');
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            data:"id=" + workflowCustomerMappingId,
                            url: "${resource(dir:'workflow', file:'deleteWorkflowCustomer')}",
                            success: function(message) {
                                if(message.type == 1){
                                    $('#jqgrid-grid-customerMaster').jqGrid('delRowData', customerId);
                                    $("#jqgrid-grid-customerMaster").trigger("reloadGrid");
                                }
                                MessageRenderer.render(message);
                            }
                        });
                    },
                    Cancel: function() {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
        }
    }
    function loadCustomerData(customerId, cusName, code, enterprise, address) {
        $("#customerId").val(customerId);
        $("#customerCode").val(code);
        $("#customerName").val(cusName);
        $("#customerEnterprise").val(enterprise);
        $("#customerAddress").val(address);
    }
    function clearCustomerData() {
        $("#customerId").val("");
        $("#customerCode").val("");
        $("#customerName").val("");
        $("#customerEnterprise").val("");
        $("#customerAddress").val("");
    }
</script>
<form name='gFormWorkflow' id='gFormWorkflow'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value="0"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-workflow"></div>

    %{--<div class="element_container_big">--}%
        %{--<div class="block-title">--}%
            %{--<div class="element-title">Module<span class="mendatory_field"> * </span></div>--}%

            %{--<div class="element-title">Feature<span class="mendatory_field"> * </span></div>--}%

            %{--<div class="element-title">Action<span class="mendatory_field"> * </span></div>--}%

            %{--<div class="clear"></div>--}%
        %{--</div>--}%

        %{--<div class="block-input">--}%
            %{--<div class="element-input inputContainer"><g:select name="moduleList" id="moduleList" from="${ModuleInfo.list()}"--}%
                                                 %{--optionKey="id" noSelection="['':'Select Module']"--}%
                                                 %{--onChange="loadFeatureInfo(this.value)"--}%
                                                 %{--class="width180" disabled="disabled"/></div>--}%

            %{--<div class="element-input inputContainer"><g:select name="featureList" id="featureList" optionKey="id"--}%
                                                 %{--noSelection="['':'Select Feature']"--}%
                                                 %{--onChange="loadActionInfo(this.value)"--}%
                                                 %{--class="width180" disabled="disabled"/></div>--}%

            %{--<div class="element-input inputContainer"><g:select name="actionList" id="actionList" optionKey="id"--}%
                                                 %{--noSelection="['':'Select Action']" onchange="loadMenuUrl(this.value)"--}%
                                                 %{--class="width300" disabled="disabled"/></div>--}%

            %{--<div class="clear"></div>--}%
        %{--</div>--}%
    %{--</div>--}%

    <div>
        <div class="block-title">
            <div class="element-title">Enterprise<span class="mendatory_field"> * </span></div>
            <div class="clear"></div>
        </div>

        <div class="block-input">
            <div class="element-input inputContainer'">
                <g:if test="${list}">
                    <g:if test="${list.size() == 1}">
                        <g:textField name="enterPriseName" disabled="disabled" value="${list[0].name}"/>
                        <script type="text/javascript">
                            $(document).ready(function () {
                                $("#enterpriseConfiguration").val("${list[0].id}");
                            })
                        </script>
                    </g:if>
                    <g:else>
                        <div id="enterpriseList" style="width: 350px;"></div>
                        <script type="text/javascript">
                            jQuery(document).ready(function () {
                                var data = ${result};
                                $("#enterpriseList").empty();
                                $("#enterpriseList").flexbox(data, {
                                    watermark: "Select Enterprise",
                                    width: 260,
                                    onSelect: function () {
                                        $("#enterpriseConfiguration").val($('#enterpriseList_hidden').val());
                                    }
                                });
                                $('#enterpriseList_input').blur(function () {
                                    if ($('#enterpriseList_hidden').val() == '') {
                                        $("#enterpriseList").val("");
                                        $("#enterpriseConfiguration").val("");
                                    }
                                });
                            });
                        </script>
                    </g:else>
                </g:if>
                <g:else>
                    <g:textField name="enterPriseName" readonly="readonly" value=""/>
                    <script type="text/javascript">
                        jQuery(document).ready(function () {
                            MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.", 0)
                        });
                    </script>
                </g:else>
            </div>
            <div class="clear"></div>
        </div>
    </div>

    <div class="element_container_big">
        <div class="block-title">
            <div class="element-title width300">Workflow Name<span class="mendatory_field"> * </span></div>

            <div class="element-title width400">Form Name<span class="mendatory_field"> * </span></div>

            <div class="clear"></div>
        </div>

        <div class="block-input">
            <div class='element-input inputContainer width300'>
                <g:textField class="validate[required] width280" name="name" value="" readonly="readonly"/>
            </div>

            <div class='element-input inputContainer width400'>
                <g:textField class="validate[required] width400" name="menuName" id="menuName" value="${ApplicationConstants.PRIMARY_DEMAND_ORDER_APPROVAL_FORM}" readonly="readonly"/>
            </div>
            <div class="clear"></div>
        </div>
    </div>

    <div class="element_container_big">
        <div class="block-title">
            <div class="element-title width400">Description<span class="mendatory_field"> * </span></div>

            <div class="clear"></div>
        </div>

        <div class="block-input">
            <div class='element-input inputContainer width700'>
                <g:textArea class="validate[required] width620" name="description" value="" rows="2" cols="50"/>
            </div>
            <div class="clear"></div>
        </div>
    </div>
<h3>Approver Assignment</h3>
    <div class="element_container_big">
        <div class="block-title">
            <div class="element-title width290">Employee <span class="mendatory_field"> * </span></div>
            <div class="element-title width200">User Name <span class="mendatory_field"> * </span></div>
            <div class="element-title width200">Designation <span class="mendatory_field"> * </span></div>
            <div class="clear"></div>
        </div>

        <div class="block-input">
            <div class='element-input-td inputContainer width320'>
                <input type="text" id="employeeSearchKey" name="employeeSearchKey" class="width290"/>
                <span id="search-btn-employee-id" title="" role="button"
                      class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                    <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                    <span class="ui-button-text"></span>
                </span>
            </div>
            <div class="element-input inputContainer value width200">
                <g:hiddenField name="applicationUserId" id="applicationUserId" value=""/>
                <g:textField name="username" class="width200" disabled="disabled" value="" />
            </div>
            <div class="element-input inputContainer value width200">
                <g:textField name="designation" class="width200" disabled="disabled" value="" />
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <div class="clear"></div>
    <div class="element_container_big">
        <div class="block-title">
            <div class="element-title width300">Priority Sequence<span class="mendatory_field"> * </span></div>
            <div class="clear"></div>
        </div>

        <div class="block-input">
            <div class='element-input inputContainer width300'>
                <g:textField class="validate[required]" name="prioritySequence" id="prioritySequence" value="1"/>
            </div>

            <div class="buttons" style="padding-left: 450px;">
                <span class="button"><input type="button" name="add-button" id="add-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                            value="Add"
                                            onclick="addApplicationUserToGrid();"/></span>
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <div class="jqgrid-container">
            <table id="jqgrid-grid-applicationUser"></table>
        </div>
    </div>
<h3 class="customerDiv">Customer Assignment</h3>
<div class="element_container_big customerDiv">
    <div class="block-title">
        <div class="element-title input_width320">Customer <span class="mendatory_field"> * </span></div>

        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class='element-input-td inputContainer width320'>
            <input type="text" id="customerSearchKey" name="customerSearchKey" class="width290"/>
            <span id="search-btn-customer-id" title="" role="button"
                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                <span class="ui-button-text"></span>
            </span>
        </div>
        <div class="clear"></div>
    </div>
</div>
<div class="element_container_big customerDiv">
    <div class="block-title">
        <div class="element-title width190">Customer ID <span class="mendatory_field"> * </span></div>
        <div class="element-title input_width320">Customer Name <span class="mendatory_field"> * </span></div>
        <div class="element-title input_width220">Enterprise <span class="mendatory_field"> * </span></div>
        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class="element-input inputContainer value width190">
            <g:hiddenField name="customerId" id="customerId"/>
            <g:textField class="width180" name="customerCode" id="customerCode" value="" disabled="disabled" />
        </div>
        <div class="element-input inputContainer value input_width320">
            <g:textField name="customerName" class="width300" readonly="readonly"
                         value="" />
        </div>
        <div class="element-input inputContainer value input_width200">
            <g:textField name="customerEnterprise" class="width200" disabled="disabled"
                         value="" />
        </div>

        <div class="clear"></div>
    </div>
</div>
<div class="element_container_big customerDiv">
    <div class="block-title">
        <div class="element-title input_width320">Customer Address <span class="mendatory_field"> * </span></div>
        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class="element-input inputContainer value input_width320">
            <g:textField name="customerAddress" class="width300" disabled="disabled"
                         value="" />
        </div>
        <div class="buttons" style="padding-left: 450px;">
            <span class="button"><input type="button" name="add-customer-button" id="add-customer-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Add"
                                        onclick="addCustomerToGrid();"/></span>
        </div>
        <div class="clear"></div>
    </div>
</div>
<div class="element_row_content_container lightColorbg pad_bot0">
    <div class="jqgrid-container">
        <table id="jqgrid-grid-customerMaster"></table>
    </div>
</div>
    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxWorkflow();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxWorkflow();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_Workflow_form('#gFormWorkflow');" value="Cancel"/></span>
    </div>
</form>
<div id="dialog-confirm-applicationUser" title="Delete alert" style="display: none">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
</div>
