<%@ page import="com.docu.security.UserType; com.docu.commons.Message; org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%--
  Created by IntelliJ IDEA.
  User: Feroz
  Date: 9/23/13
  Time: 11:46 AM
  To change this template use File | Settings | File Templates.
--%>

<meta name="layout" content="docuThemeRollerLayout"/>
<title>Reset Password</title>
<script type="text/javascript">
    $(document).ready(function () {
        $("#reset-password-form").validationEngine({
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#reset-password-form").validationEngine('attach');
//        InternalUserPasswordEditor.init();
        initializeGrid();
    });

    function initializeGrid() {
        $("#user-grid").jqGrid({
            %{--url: '${resource(dir:'territorySubArea', file:'list')}',--}%
            datatype: "json",
            colNames: [
                'ID',
                'User Name',
                'PIN',
                'Designation',
                'Department',
                'Name',
                ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 30, align: 'left', hidden: true},
                {name: 'userName', index: 'userName', width: 100, align: 'left'},
                {name: 'pin', index: 'pin', width: 100, align: 'left'},
                {name: 'designation', index: 'designation', width: 200, align: 'left'},
                {name: 'department', index: 'department', width: 200, align: 'left'},
                {name: 'name', index: 'name', width: 200, align: 'left'},
                {name: 'reset', index: 'reset', width: 100, align: 'center', formatter: resetLink}

            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#user-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "User List",
            autowidth: false,
//            width: 410,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            }
        });
        $("#user-grid").jqGrid('navGrid', '#user-grid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
    }

    function loadGrid() {
        jQuery("#user-grid").jqGrid().setGridParam({
            url: '${resource(dir:'userAccount', file:'listUser')}?userName='
            + $('#userName').val() + '&entId=' + $('#idEnterprise').val() +
            '&userType=' + $('#userType').val()
        }).trigger("reloadGrid");
    }

    function resetLink(cellValue, options, rowdata, action) {
        return "<input type='button' name='reset-enable-button' " +
                "id='reset-enable-button' class='ui-button ui-widget ui-state-default ui-corner-all' " +
                "value='Reset' onclick='javascript:resetEnable(" + rowdata[0] + ");'/>";
    }

    function resetEnable(id) {
        $("#resetUser").val(id);
        $("#resetTable").attr('hidden', false);
        $("#user").val($('#user-grid').jqGrid("getCell", id, 'userName'));
        $("#name").val($('#user-grid').jqGrid("getCell", id, 'name'));
        $("#password").val('');
        $("#confirmPassword").val('');
    }

    function resetPassword() {
        if (!$("#reset-password-form").validationEngine('validate')) {
            MessageRenderer.render({
                messageTitle: 'Password Format',
                type: 2,
                messageBody: "Password should be 6 to 20 character long. \n* Password should have at least one alphabet. \n* one numeric value. \n* And one special characters."
            });

            return false;
        }
        var pass = $("#password").val();
        var con = $("#confirmPassword").val();
        if (pass != con) {
            MessageRenderer.render({
                messageTitle: 'Password Mismatch',
                type: 2,
                messageBody: 'Confirm Password does not match with password.'
            });
            return false;
        }
//        var msg = validatePassword($("#password"));
//        if (msg) {
//            MessageRenderer.render({messageTitle: 'Password Format Error', type: 2, messageBody: msg});
//            return false;
//        }
        var data = jQuery("#reset-password-form").serialize();
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: '${resource(dir:'userAccount', file:'passwordReset')}',
            success: function (data, textStatus) {
                MessageRenderer.render(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){

                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
                $("#resetTable").attr('hidden', 'hidden');
            },
            dataType: 'json'
        });
    }

    function validatePassword(field, rules, i, options) {
        var re = /^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%]).{6,20}$/;
        if (!re.test(field.val())) {
            return "Password should be 6 to 20 character long. \n* Password should have at least one alphabet. \n* one numeric value. \n* And one special characters.";
        }
    }

</script>

<form id="reset-password-form">
    <g:hiddenField name="resetUser" id="resetUser" value=""/>
    <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
    <div class="main_container width900">
        <h1 class="width880">Reset Password</h1>

        <div class=content_container>
            <h3>Reset Password</h3>

            <table>
                <g:if test="${size == 1}">
                    <script type="text/javascript">
                        $(document).ready(function () {
                            %{--setId(${enterpriseList}[0].id);--}%
                            $('#idEnterprise').val(${enterpriseList}[0].id);
                        });
                    </script>
                </g:if>
                <g:else>
                    <tr class="element_row_content_container lightColorbg pad_bot0">
                        <td>
                            <label for="enterpriseConfiguration" class="txtright bold hight1x width1x"
                                   style="width: 160px;">Enterprise:
                                <span class="mendatory_field">*</span>
                            </label>
                        </td>
                        <td>
                            <g:select name="enterpriseConfiguration.id"
                                      class="validate[required]"
                                      style="width: 350px; height: 20px;" id="enterpriseConfiguration"
                                      optionKey="id" value="" noSelection="['': 'Please Select']"
                                      onchange="setId(this.value);"/>
                        </td>
                    </tr>
                    <script type="text/javascript">
                        $(document).ready(function () {
                            $('#enterpriseConfiguration').val('');
                            var options = '<option value="">Please Select</option>';
                            for (var i = 0; i < ${size}; i++) {
                                options += '<option value="' + ${enterpriseList}[i].id + '">' + ${enterpriseList}[i].name + '</option>';
                            }
                            $("#enterpriseConfiguration").html(options);
                        });
                    </script>
                </g:else>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label for="userName" class="txtright bold hight1x width1x">
                            User Name:
                        </label>
                    </td>
                    <td>
                        <g:textField name="userName" class="width160" value="" />
                    </td>
                    <td>
                        <label for="userType" class="txtright bold hight1x width1x">
                            User Type:
                        </label>
                    </td>
                    <td>
                        <g:select name="userType.id" id="userType" from="${com.docu.security.UserType.list()}"
                                  style="width: 165px; height: 20px;"
                                  optionKey="id" value="" noSelection="['': 'Please Select']"
                                  onchange=""/>
                    </td>
                    <td>
                        <span id="search-span" class="button"><input type='button' name="search-button"
                                                                     id="search-button"
                                                                     class="ui-button ui-widget ui-state-default ui-corner-all"
                                                                     value='Search'
                                                                     onclick="loadGrid();"/></span>
                    </td>
                </tr>
            </table>
            <div style="float: left">
                <table>
                    <tr>
                        <td>
                            <table id="user-grid"></table>
                            <div id="user-grid-pager"></div>
                        </td>
                    </tr>
                </table>
            </div>
            <div style="float: right">
                <table>
                    <tr>
                        <td>
                            <table hidden="hidden" id="resetTable">
                                <tr class="element_row_content_container lightColorbg pad_bot0">
                                    <td>
                                        <label for="userName" class="txtright bold hight1x width1x">
                                            Full Name:
                                        </label>
                                    </td>
                                    <td>
                                        <g:textField name="name" id="name"
                                                     value="" style="width: 160px;" readonly="readonly"/>
                                    </td>
                                </tr>
                                <tr class="element_row_content_container lightColorbg pad_bot0">
                                    <td>
                                        <label for="userName" class="txtright bold hight1x width1x">
                                            User Name:
                                        </label>
                                    </td>
                                    <td>
                                        <g:textField name="user" id="user"
                                                     value="" style="width: 160px;" readonly="readonly"/>
                                    </td>
                                </tr>
                                <tr class="element_row_content_container lightColorbg pad_bot0">
                                    <td>
                                        <label for="password" class="txtright bold hight1x width1x">New Password:
                                            <span class="mendatory_field">*</span>
                                        </label>
                                    </td>
                                    <td>
                                        <g:passwordField name="password" id="password"
                                                     class="validate[required,maxSize[20],funcCall[validatePassword]}"
                                                     value="" style="width: 160px;"/>
                                    </td>
                                </tr>
                                <tr class="element_row_content_container lightColorbg pad_bot0">
                                    <td>
                                        <label for="confirmPassword" class="txtright bold hight1x width1x">
                                            Confirm Password:
                                            <span class="mendatory_field">*</span>
                                        </label>
                                    </td>
                                    <td>
                                        <g:passwordField name="confirmPassword" id="confirmPassword"
                                                     class="validate[maxSize[20]"
                                                     value="" style="width: 160px;"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span id="reset-span" class="button">
                                            <input type="button" id="reset-password-button"
                                                   class="ui-button ui-widget ui-state-default ui-corner-all"
                                                   value="Reset"
                                                   onclick="resetPassword();"/>
                                        </span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</form>