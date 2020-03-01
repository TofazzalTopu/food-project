<%@ page import="com.docu.security.ApplicationUserType; org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%--
  Created by IntelliJ IDEA.
  User: nasrin
  Date: 7/17/12
  Time: 12:44 PM
  To change this template use File | Settings | File Templates.
--%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>
<title>Login History</title>


<script type="text/javascript">
    var oTable;
    var user ;
    var data = {};

    $.fn.dataTableExt.oApi.fnReloadAjax = function (oSettings, data) {
        this.fnClearTable(this);
        this.oApi._fnProcessingDisplay(oSettings, true);
        var that = this;
        $.getJSON(oSettings.sAjaxSource, data, function (json) {
            /* Got the data - add it to the table */
            for (var i = 0; i < json.aaData.length; i++) {
                that.oApi._fnAddData(oSettings, json.aaData[i]);
            }

            oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
            that.fnDraw(that);
            that.oApi._fnProcessingDisplay(oSettings, false);
        });
    };


    $(document).ready(function () {
        userInformation.loadUserFlexBox();
        initTable();

    });

    function initTable() {
        oTable = $("#loginHistory").dataTable({
            "sPaginationType":"full_numbers",
            "bJQueryUI":true,
            "bLengthChange":true,
            "bSort":true,
            "bInfo":true,
            "bAutoWidth":false,
            "bProcessing":true,
            "iDisplayLength":10,
            "bFilter":true,
            "oSearch":{
                "sSearch":"",
                "bRegex":false,
                "bSmart":true },
            "sAjaxSource":'${request.contextPath}/${params.controller}/getLoginInformation',
            "aoColumns":[
                { "mDataProp":"id", "sClass":"left", "sWidth":'100px', "bVisible":false}
                ,
                { "mDataProp":"userPin", "sClass":"left", "sWidth":'70px', "bVisible":false}
                ,
                { "mDataProp":"userName", "sClass":"left", "sWidth":'70px', "bVisible":false}
                ,
                { "mDataProp":"user", "sClass":"left", "sWidth":'70px'}
                ,
                { "mDataProp":"userType", "sClass":"left", "sWidth":'70px'}
                ,
                { "mDataProp":"loginIP", "sClass":"left", "sWidth":'20px'}
                ,
                { "mDataProp":"logoutIP", "sClass":"left", "sWidth":'70px'}
                ,
                { "mDataProp":"loginTime", "sClass":"left", "sWidth":'70px'}
                ,
                { "mDataProp":"logoutTime", "sClass":"left", "sWidth":'70px'}
            ]
        });
    }

    function RefreshTable(){
        user = $("#user").val();
        data = {user: user};
        oTable.fnReloadAjax(data);
    }

    var userInformation = {
        clearUserInformation: function () {
            userFlaxBox.clear();
            oTable.fnClearTable();
        },
        loadUserFlexBox: function () {
            if (userFlaxBox != null) {
                userInformation.clearUserInformation();
            }

            var url = "${request.getContextPath()}/${params.controller}/getUserList";
            var params = {};
            DocuAjax.json(url, params, function (mapList) {
                userFlaxBox.setData(mapList);
                $('#userDiv_input').blur(function () {
                    if (!$.trim($('#userDiv_input').val())) {
                        userFlaxBox.val('');
                        oTable.fnClearTable();
                    }
                });
            });
        }
    }

</script>

<div class="main_container">
    <form name="loginHistoryForm" id="loginHistoryFrom">
        <h1>Login History</h1>

        <div class="content_container">
            <div class="element_container_big">
                <div class="block-title">
                    <div class="element-title">User</div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <div class="element-input">
                        <docu:flaxBox id="user" name="user" var="userFlaxBox" optionKey="id"
                                      optionValue="userInfo" watermark="Select User" >
                            containerClass: "ffb width200",
                            inputClass: "ffb-input width130 validate[required]",
                            onSelect: function(map){
                                RefreshTable();
                            }
                        </docu:flaxBox>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </form>
    <h3 class="mar_top0">Show Login History</h3>


    <table id="loginHistory" border="0" cellpadding="0" cellspacing="0"
           class="pretty borderTop borderBottom bodColDark">
        <thead>
        <tr>
            <th>id</th>
            <th>User Pin</th>
            <th>User Name</th>
            <th>User</th>
            <th>User Type</th>
            <th>Login IP</th>
            <th>Logout IP</th>
            <th>Login Time</th>
            <th>Logout Time</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>

</div>

