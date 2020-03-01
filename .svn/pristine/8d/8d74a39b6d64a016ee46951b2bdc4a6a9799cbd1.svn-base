<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.docu.security.ApplicationUserType; com.docu.commons.CommonConstants; org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%--
  Created by IntelliJ IDEA.
  User: kibria
  Date: 11/27/12
  Time: 11:52 AM
  To change this template use File | Settings | File Templates.
--%>
<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>
<script type="text/javascript">
    var jqGridDPList = null;
    var ApplicationUserEditor = {
        hidePrompt:function () {
            $("#gFormNewApplicationUser").validationEngine('hideAll');
        },
        saveApplicationUserRemotely:function () {
            if (!$("#gFormNewApplic" +
                    "ationUser").validationEngine('validate')) {
                return false;
            }
            SubmissionLoader.showTo();
            var url = "${request.contextPath}/${params.controller}/saveApplicationUserRemotely";
            var data = $("#gFormNewApplicationUser").serializeArray();
            var userType = $("#userType").val();
            if(userType == '${ApplicationConstants.USER_TYPE_OTHER}') {
                var employeeId = $("#employeeId").val();
                if(!employeeId){
                    MessageRenderer.renderErrorText("Please Select Employee");
                    return
                }
                var gd = $("#jqgrid-grid-distributionPoint").jqGrid('getRowData');
                var length = gd.length;
                for (var i=0; i < length; ++i) {
                    data.push({'name':'items.applicationUserDistributionPoint['+i+'].id', 'value': ''});
                    data.push({'name':'items.applicationUserDistributionPoint['+i+'].distributionPoint.id', 'value': gd[i].id});
                    data.push({'name':'items.applicationUserDistributionPoint['+i+'].applicationUser.id', 'value': ''});
                }
            }else if(userType == '${ApplicationConstants.USER_TYPE_CUSTOMER}'){
                var customerId = $("#customerId").val();
                if(!customerId){
                    MessageRenderer.renderErrorText("Please Select Customer");
                    return
                }
            }
            DocuAjax.json(url, data, function (json) {
                SubmissionLoader.hideFrom();
                if (json.message.type == 1) {
                    var enterprise = $("#enterpriseConfiguration").val();
                    $("#gFormNewApplicationUser")[0].reset();
                    reset_form("#gFormNewApplicationUser");
                    $("#enterpriseConfiguration").val(enterprise);
                    $(".remove-all").click();
                    $('#jqgrid-grid-distributionPoint').jqGrid('clearGridData');
                }
                MessageRenderer.render(json.message);
            });
        },
        updateApplicationUserRemotely:function () {
            if (!$("#gFormNewApplic" +
                    "ationUser").validationEngine('validate')) {
                return false;
            }
            SubmissionLoader.showTo();
            var url = "${request.contextPath}/${params.controller}/updateApplicationUserRemotely";
            var data = $("#gFormNewApplicationUser").serializeArray();
            var userType = $("#userType").val();
            if(userType == '${ApplicationConstants.USER_TYPE_OTHER}') {
                var gd = $("#jqgrid-grid-distributionPoint").jqGrid('getRowData');
                var length = gd.length;
                for (var i=0; i < length; ++i) {
                    data.push({'name':'items.applicationUserDistributionPoint['+i+'].id', 'value': ''});
                    data.push({'name':'items.applicationUserDistributionPoint['+i+'].distributionPoint.id', 'value': gd[i].id});
                    data.push({'name':'items.applicationUserDistributionPoint['+i+'].applicationUser.id', 'value': ''});
                }
            }
            DocuAjax.json(url, data, function (json) {
                SubmissionLoader.hideFrom();
                if (json.message.type == 1) {
                    $("#gFormNewApplicationUser")[0].reset();
                    $(".remove-all").click();
                    $('#jqgrid-grid-distributionPoint').jqGrid('clearGridData');
                }
                MessageRenderer.render(json.message);
            });
        },
        updateApplicationUserAuthority: function () {
            if (!$("#gFormNewApplicationUser").validationEngine('validate')) {
                return false;
            }
            SubmissionLoader.showTo();
            var url = "${request.contextPath}/${params.controller}/updateApplicationUserAuthority";
            var data = $("#gFormNewApplicationUser").serialize();
            DocuAjax.json(url, data, function (json) {
                SubmissionLoader.hideFrom();
                if (json.message.type == 1) {
                    location.href = "${request.contextPath}/${params.controller}/list";
                } else {
                    MessageRenderer.render(json.message)
                }
            });
        },
        deleteConfirmation:function () {
            if (confirm("Are you sure you want to delete User ?") == true) {
                $('#domainStatus').val('${CommonConstants.DOMAIN_STATUS_INACTIVE}');
                ApplicationUserEditor.updateApplicationUserRemotely();
                $('#domainStatus').val('${CommonConstants.DOMAIN_STATUS_ACTIVE}');
            } else{
                return false;
            }
        },
        checkAvailability:function (userName) {
            var url = "${request.contextPath}/${params.controller}/checkAvailability";
            var data = {username:userName};
            DocuAjax.json(url, data, function (json) {
                if (json.isError == 0) {
                    $("#msg-div").html(json.message)
                } else {
                    $("#msg-div").html("")
                }
            });
        },
        initialiseFancyBox:function () {
            $(".user-popUp").fancybox({
                'scrolling':'no',
                'titleShow':false,
                'width':400,
                'height':200,
                'autoDimensions':false,
                'onClosed':function () {
                    $("#role").val("")
                },
                'onComplete':function () {

                }
            });
        },
        saveAuthority:function () {
            if (!$("#authority-form").validationEngine('validate')) {
                return false
            }
            $.fancybox.close();
            var url = "${request.contextPath}/userAuthority/save";
            var data = $("#authority-form").serialize();
            DocuAjax.html(url, data, function (json) {
                if (json.indexOf('{"messageTitle":"Authority"') != 0) {
                    $("#user-authority-block").html(json)
                }
                else {
                    msg = jQuery.parseJSON(json);
                    MessageRenderer.render(msg)
                }
            });
        }
    };
    $(document).ready(function(){
        $('#checkProperties').hide();
        $("#gFormNewApplicationUser").validationEngine({
            isOverflown: true,
            overflownDIV: ".ui-layout-center",
            validationEventTrigger:""
        });
        $("#gFormNewApplicationUser").validationEngine('attach');
        ApplicationUserEditor.initialiseFancyBox();
        $("#distributionPointList").flexbox('Select Distribution Point', {
            watermark: "Select Distribution Point",
            width: 260,
            onSelect: function () {
                $("#distributionPoint").val($('#distributionPointList_hidden').val());
            }
        });
        jqGridDPList = $("#jqgrid-grid-distributionPoint").jqGrid({
            url:'',
            datatype: "local",
            colNames:[
                'ID',
                'applicationUserDistributionPointId',
                'Enterprise',
                'Distribution Point',
                'Address',
                ''
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'applicationUserDistributionPointId', index:'applicationUserDistributionPointId', hidden: true},
                {name:'enterprise', index:'enterprise', width:150, align:'left'},
                {name:'distributionPoint', index:'distributionPoint', width:200, align:'left'},
                {name:'address', index:'address', width:350, align:'left'},
                {name:'delete', index:'delete', width:20, align:'center', sortable:false}
            ],
            rowNum: -1,
            viewrecords:true,
            caption:"Distribution Point List",
            autowidth:true,
            height:true,
            scrollOffset:0,
            gridview:true,
//            multiselect: true,
            rownumbers: true,
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText,'');
            },
            loadComplete: function(data) {
                $.each(data.rows, function (i, item) {
                        var rowData = $("#jqgrid-grid-distributionPoint").jqGrid('getRowData', data.rows[i].id);
                        rowData.delete = '<a  href="javascript:deAllocateDP(' + data.rows[i].id + ')" class="ui-icon ui-icon-trash" title="Delete"></a>';
                        $('#jqgrid-grid-distributionPoint').jqGrid('setRowData', data.rows[i].id, rowData);
                });
            }
        });
        $(".employeeDiv").hide();
        $(".customerDiv").hide();
        <g:if test="${applicationUserInstance?.id}">
            $('#username').attr('readonly', true);
            $('#fullName').attr('readonly', true);
            $('#checkProperties').show();
            $('#passwordBlock').hide();
            var userType = Number($("#userType").val());
            changeUserType($("#userType").val());
            if(userType > 0){
                $("#userType").attr('disabled', true);
            }
            if(userType == ${ApplicationConstants.USER_TYPE_OTHER}){
                $("#employeeSearchKey").val("[${applicationUserInstance?.customerCode}] ${applicationUserInstance?.customerName}");
                $("#employeeId").val('${applicationUserInstance?.customer_master_id}');
                $("#employeePin").val('${applicationUserInstance?.customerCode}');
                $("#employeeName").val('${applicationUserInstance?.customerName}');
                $("#employeeEnterprise").val('${applicationUserInstance?.customerEnterprise}');
                loadUserDistributionPointList('${applicationUserInstance?.id}');
            } else if(userType == ${ApplicationConstants.USER_TYPE_CUSTOMER}){
                $("#customerSearchKey").val("[${applicationUserInstance?.customerCode}] ${applicationUserInstance?.customerName}");
                $("#customerId").val("${applicationUserInstance?.customer_master_id}");
                $("#customerCode").val("${applicationUserInstance?.customerCode}");
                $("#customerName").val("${applicationUserInstance?.customerName}");
                $("#customerEnterprise").val("${applicationUserInstance?.customerEnterprise}");
                $("#customerAddress").val("${applicationUserInstance?.customerAddress}");
            }
        </g:if>
        <g:else>
            $('#username').focus();
            $('#username').removeAttr('readonly');
            $('#fullName').removeAttr('readonly');
            $('#checkProperties').hide();
            $('#passwordBlock').show();
        </g:else>
        $('#employeeSearchKey').blur(function () {
            if ($('#employeeSearchKey').val() == '') {
                clearEmployeeDataField();
            }
        });
        jQuery('#employeeSearchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var url = '${resource(dir:'userAccount', file:'listEmployee')}?query=' + $('#employeeSearchKey').val();
                DocuAutoComplete.setSpinnerSelector('employeeSearchKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                loadEmployeeDataInField(ui.item.id, ui.item.name, ui.item.code, ui.item.enterprise);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + "PIN: " + item.code + "; Name: " + item.name + "; " + "Enterprise: " + item.enterprise + "; " + "Address: " + item.present_address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };
        $('#search-btn-employee-id').click(function () {
            var url = '${resource(dir:'userAccount', file:'popupEmployeeListPanel')}';
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        });
        $('#customerSearchKey').blur(function () {
            if ($('#customerSearchKey').val() == '') {
                clearDataField();
            }
        });
        jQuery('#customerSearchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var url = '${resource(dir:'userAccount', file:'listCustomer')}?query=' + $('#customerSearchKey').val();
                DocuAutoComplete.setSpinnerSelector('customerSearchKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                loadDataInField(ui.item.id, ui.item.name, ui.item.code, ui.item.enterprise, ui.item.present_address);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + "ID: " + item.code + "; Name: " + item.name + "; "+ "; Category: " + item.category + "; " + "Enterprise: " + item.enterprise + "; " + "Address: " + item.present_address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };
        $('#search-btn-customer-id').click(function () {
            var url = '${resource(dir:'userAccount', file:'popupCustomerListPanel')}';
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        });
    });
    function loadEmployeeDataInField(employeeId, cusName, code, enterprise) {
        $("#employeeId").val(employeeId);
        $("#employeePin").val(code);
        $("#employeeName").val(cusName);
        $("#username").val(code);
        $("#fullName").val(cusName);
        $("#employeeEnterprise").val(enterprise);
    }
    function clearEmployeeDataField() {
        $("#employeeId").val("");
        $("#employeePin").val("");
        $("#employeeName").val("");
        $("#employeeEnterprise").val("");
        $("#username").val("");
        $("#fullName").val("");
    }
    function loadDataInField(customerId, cusName, code, enterprise, address) {
        $("#customerId").val(customerId);
        $("#customerCode").val(code);
        $("#customerName").val(cusName);
        $("#customerEnterprise").val(enterprise);
        $("#customerAddress").val(address);
        $("#username").val(code);
        $("#fullName").val(cusName);
    }
    function clearDataField() {
        $("#customerId").val("");
        $("#customerCode").val("");
        $("#customerName").val("");
        $("#customerEnterprise").val("");
        $("#customerAddress").val("");
        $("#username").val("");
        $("#fullName").val("");
    }
    function validatePassword(field, rules, i, options) {
        var re = /^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%!&*^]).{6,20}$/;
        if (!re.test(field.val())) {
            return "Password should be 6 to 20 character long. \n* Password should have at least one alphabet. \n* one numeric value. \n* And one special characters.";
        }
    }

    function validateEmail(field, rules, i, options) {
        var pattern = /^([A-Za-z0-9_\-\.\'])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,6})$/;
        if (field.val() == '') {
            return ;
        }else if (!pattern.test(field.val())) {
            return "* Invalid email address";
        }
    }

    function changeUserType(userTypeId){
        if(userTypeId){
            if(userTypeId == ${ApplicationConstants.USER_TYPE_SUPER_ADMIN}){
                $(".employeeDiv").hide();
                $(".customerDiv").hide();
            }else if(userTypeId == ${ApplicationConstants.USER_TYPE_ADMIN}){
                $(".employeeDiv").hide();
                $(".customerDiv").hide();
            }else if(userTypeId == ${ApplicationConstants.USER_TYPE_CUSTOMER}){
                $(".employeeDiv").hide();
                $(".customerDiv").show();
            }else if(userTypeId == ${ApplicationConstants.USER_TYPE_OTHER}){
                $(".employeeDiv").show();
                $(".customerDiv").hide();
            }
        }else{
            $(".employeeDiv").hide();
            $(".customerDiv").hide();
        }
    }
    function loadDistributionPointByEnterprise(enterpriseId) {
        $("#distributionPointList").empty();
        $("#distributionPoint").val("");
        if(enterpriseId){
            jQuery.ajax({
                type: "POST",
                url: '${resource(dir:'distributionPoint', file:'loadDistributionPoint')}?enterpriseId=' + enterpriseId,
                dataType: 'json',
                success: function (data, textStatus) {
                    $("#distributionPointList").flexbox(data, {
                        watermark: "Select Distribution Point",
                        width: 260,
                        onSelect: function () {
                            $("#distributionPoint").val($('#distributionPointList_hidden').val());
                        }
                    });
                    $('#distributionPointList_input').blur(function () {
                        if ($('#distributionPointList_input').val() == '') {
                            $("#distributionPoint").val("");
                        }
                    });

                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                },
                complete: function () {
                }
            });
        }
    }
    function addDistributionPointToGrid(){
        var enterpriseId = $("#enterpriseConfiguration").val();

        if(!enterpriseId){
            MessageRenderer.renderErrorText("Enterprise is not selected");
            return
        }
        var distributionPointId = $("#distributionPoint").val();
        if(!distributionPointId){
            MessageRenderer.renderErrorText("Distribution Point is not selected");
            return
        }

        var enterprise =  $("#enterPriseName").val();
//        var enterprise =  $("#enterpriseList_input").val();

        var distributionPoint =  $("#distributionPointList_input").val();

        var rowTo = jqGridDPList.getRowData(distributionPointId.toString());
        if (!rowTo.id) {
            var newRowData = [
                {
                    'id':distributionPointId.toString(), 'applicationUserDistributionPointId': '', 'enterprise':enterprise, 'distributionPoint':distributionPoint,
                    'address':'', 'delete': '<a  href="javascript:deAllocateDP(' + distributionPointId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                }
            ];
            jqGridDPList.addRowData(distributionPointId.toString(), newRowData[0]);
            $("#distributionPointList_input").val("");
            $("#distributionPoint").val("");
        }else{
            MessageRenderer.renderErrorText("Selected DP is already added to grid");
        }

    }
    function loadUserDistributionPointList(userId){
        jQuery("#jqgrid-grid-distributionPoint").jqGrid().setGridParam({url:"${resource(dir:'userAccount', file:'listUserAccountDistributionPoint')}?"
                + "id=" + userId,
            mtype: "POST",
            datatype:"json"
        }).trigger("reloadGrid");
    }
    function deAllocateDP(distributionPointId){
        $('#jqgrid-grid-distributionPoint').jqGrid('delRowData', distributionPointId);
    }
</script>

<div class="main_container">
<form id="gFormNewApplicationUser" name="gFormNewApplicationUser">
<g:hiddenField name="docu-ignore-security" value="1"/>
<g:hiddenField name="id" value="${applicationUserInstance?.id}"/>
<g:hiddenField name="version" value="${applicationUserInstance?.version}"/>
<g:hiddenField name="domainStatus" id="domainStatus" value="${CommonConstants.DOMAIN_STATUS_ACTIVE}"/>

<div class=content_container>
<h3>Application User</h3>

<div class="element_container_big">
    <div class="block-title">
        <div class="element-title input_width320">User Type <span class="mendatory_field"> * </span></div>

        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class="element-input inputContainer value  input_width320">
            <g:select name="userType" id="userType" class="validate[required] width300"
                      value="${applicationUserInstance?.user_type_id}" from="${userTypeList}" optionKey="id" noSelection="['':'Select User Type']"
                      onchange="changeUserType(this.value)"/>
        </div>

        <div class="element-title input_width320" style="color:#ff0000; "></div>

        <div class="clear"></div>
    </div>
</div>
<h3 class="employeeDiv">Employee Assignment</h3>
<div class="element_container_big employeeDiv">
    <div class="block-title">
        <div class="element-title input_width320">Employee <span class="mendatory_field"> * </span></div>

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
        <div class="clear"></div>
    </div>
</div>
<div class="element_container_big employeeDiv">
    <div class="block-title">
        <div class="element-title width190">Employee PIN <span class="mendatory_field"> * </span></div>
        <div class="element-title input_width320">Employee Name <span class="mendatory_field"> * </span></div>
        <div class="element-title input_width200">Enterprise <span class="mendatory_field"> * </span></div>
        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class="element-input inputContainer value width190">
            <g:hiddenField name="employeeId" id="employeeId" value=""/>
            <g:textField class="width100" name="employeePin" id="employeePin" value="" readonly="readonly" />
        </div>
        <div class="element-input inputContainer value input_width320">
            <g:textField name="employeeName" class="width300" readonly="readonly" value="" />
        </div>
        <div class="element-input inputContainer value input_width200">
            <g:textField name="employeeEnterprise" class="width200" readonly="readonly" value="" />
        </div>
        <div class="clear"></div>
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
            <g:textField class="width180" name="customerCode" id="customerCode" value="" readonly="readonly" />
        </div>
        <div class="element-input inputContainer value input_width320">
            <g:textField name="customerName" class="width300" readonly="readonly"
                         value="" />
        </div>
        <div class="element-input inputContainer value input_width200">
            <g:textField name="customerEnterprise" class="width200" readonly="readonly"
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
            <g:textField name="customerAddress" class="width300" readonly="readonly"
                         value="" />
        </div>
        <div class="clear"></div>
    </div>
</div>
<h3>User Account Information</h3>
<div class="element_container_big">
    <div class="block-title">
        <div class="element-title input_width320">User Name <span class="mendatory_field"> * </span></div>
        %{--Added on 14-02-2017--}%
        <div class="element-title input_width320">Device ID</div>
        %{--End--}%
        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class="element-input inputContainer value  input_width320">
            <g:textField name="username" class="validate[required,maxSize[20],minSize[3] width300" readonly="readonly"
                         value="${applicationUserInstance?.username}" onchange="ApplicationUserEditor.checkAvailability(this.value)" />
        </div>

        %{--Added on 14-02-2017--}%
        <div class="element-input inputContainer value  input_width320">
            <g:textField name="deviceId" class="validate[maxSize[20],minSize[3] width300"
                         value="${applicationUserInstance?.device_id}" onchange="" />
        </div>
        %{--End--}%

        <div class="element-title input_width320" style="color:#ff0000; " id="msg-div"></div>

        <div class="clear"></div>
    </div>
</div>


<div id="passwordBlock" class="element_container_big">
    <div class="block-title">
        <div class="element-title input_width320">Password <span class="mendatory_field"> * </span></div>

        <div class="element-title input_width320">Confirm Password <span class="mendatory_field"> * </span></div>

        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class="element-input inputContainer value input_width320">
            <g:passwordField name="password" value="${applicationUserInstance?.password}"
                             class="validate[required,maxSize[20],funcCall[validatePassword]} width300"/>
        </div>

        <div class="element-input inputContainer value input_width320">
            <g:passwordField name="confirmPassword" value="${applicationUserInstance?.password}"
                             class=" width300"/>
        </div>

        <div class="clear"></div>
    </div>
</div>

<div class="element_container_big">
    <div class="block-title">
        <div class="element-title input_width320">Full Name</div>

        <div class="element-title input_width320">Email </div>

        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class="element-input inputContainer value input_width320">
            <g:textField name="fullName" value="${applicationUserInstance?.full_name}" class="width300" readonly="readonly"/>
        </div>

        <div class="element-input inputContainer value input_width320">
            <g:textField name="email" value="${applicationUserInstance?.email_address}" class="validate[funcCall[validateEmail]} width300" />
        </div>

        <div class="clear"></div>
    </div>

    <div class="clear"></div>
</div>

<div id="checkProperties" class="element_container_big">
    <div class="block-title">
        <div class="element-title">Enabled</div>

        <div class="element-title">Account Expired</div>

        <div class="element-title">Account Locked</div>

        <div class="element-title">Password Expired</div>

        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class="element-input inputContainer value ">
            <g:checkBox name="enabled" value="${applicationUserInstance?.enabled}"/>
        </div>

        <div class="element-input inputContainer value ">
            <g:checkBox name="accountExpired" value="${applicationUserInstance?.account_expired}"/>
        </div>

        <div class="element-input inputContainer value ">
            <g:checkBox name="accountLocked" value="${applicationUserInstance?.account_locked}"/>
        </div>

        <div class="element-input inputContainer value ">
            <g:checkBox name="passwordExpired" value="${applicationUserInstance?.password_expired}"/>
        </div>

        <div class="clear"></div>
    </div>
</div>
</div>
<div class="content_container">
    <h3 style="margin-bottom:0">User Role Mapping</h3>
    <div class="clear" style="height: 2px;"></div>

    <div id="user-authority-block">
        <g:render template="userAuthority"/>
    </div>
</div>
<div class="content_container">
    <h3 style="margin-bottom:0">User Enterprise Mapping</h3>
    <div class="clear" style="height: 2px;"></div>

    <div id="user-enterprise-block">
        <g:render template="userEnterprise"/>
    </div>

</div>
<div class="content_container employeeDiv">
    <h3 style="margin-bottom:0">Distribution Point Allocation</h3>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>

    %{--<div class="clear"></div>--}%
    %{--<b><a href="#create-user-role" style="float:right; padding: 2px 5px;" class="user-popUp">Distribution Point Allocation</a>--}%
    %{--</b>--}%

    <div class="clear" style="height: 2px;"></div>

    <div id="user-distributionPoint-block">
        <div class="element_row_content_container lightColorbg pad_bot0">
            <label class="txtright bold hight1x width1x">
                Enterprise
                <span class="mendatory_field"> * </span>
            </label>
            <g:if test="${enterpriseList}">
                <g:if test="${enterpriseList.size() == 1}">
                    <div class='element-input inputContainer'>

                        <g:textField name="enterPriseName" readonly="readonly" value="${enterpriseList[0].name}"/>
                        <script type="text/javascript">
                            $(document).ready(function () {
                                $("#enterpriseConfiguration").val("${enterpriseList[0].id}");
                                loadDistributionPointByEnterprise("${enterpriseList[0].id}");
                            })
                        </script>
                    </div>
                </g:if>
                <g:else>
                    <div class='element-input inputContainer'>
                        <div id="enterpriseList" style="width: 350px;"></div>
                        <script type="text/javascript">

                            jQuery(document).ready(function () {
                                var data = ${enterpriseFlexData};
                                $("#enterpriseList").empty();
                                $("#enterpriseList").flexbox(data, {
                                    watermark: "Select Enterprise",
                                    width: 260,
                                    onSelect: function () {
                                        $("#enterpriseConfiguration").val($('#enterpriseList_hidden').val());
                                        loadDistributionPointByEnterprise($('#enterpriseList_hidden').val());
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
                    </div>
                </g:else>

            </g:if>
            <g:else>
                <div class='element-input inputContainer'>
                    <g:textField name="enterPriseName" readonly="readonly" value=""/>
                    <script type="text/javascript">
                        jQuery(document).ready(function () {
                            MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.", 0)
                        });
                    </script>
                </div>
            </g:else>
        </div>

        <div class="element_row_content_container lightColorbg pad_bot0">
            <label class="txtright bold hight1x width1x">
                Distribution Point
            </label>

            <div class='element-input inputContainer'>

                <div id="distributionPointList" class="width350"></div>

            </div>
            <g:hiddenField name="distributionPoint.id" id="distributionPoint" value=""/>
            <div class="buttons" style="padding-left: 450px;">
                <span class="button"><input type="button" name="add-button" id="add-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                            value="Add"
                                            onclick="addDistributionPointToGrid();"/></span>
            </div>
        </div>
        <div class="element_row_content_container lightColorbg pad_bot0">
            <div class="jqgrid-container">
                <table id="jqgrid-grid-distributionPoint"></table>
            </div>
        </div>

    </div>
</div>
<div class="buttons">
    <g:if test="${applicationUserInstance?.id}">
        <input type="button"
               class="ui-button ui-widget ui-state-default ui-corner-all"
               value="Update"
               onclick="ApplicationUserEditor.updateApplicationUserRemotely()"/>
    </g:if>
    <g:else>
        <input type="button"
               class="ui-button ui-widget ui-state-default ui-corner-all"
               value="Save"
               onclick="ApplicationUserEditor.saveApplicationUserRemotely()"/>
    </g:else>
</div>

</form>
</div>
