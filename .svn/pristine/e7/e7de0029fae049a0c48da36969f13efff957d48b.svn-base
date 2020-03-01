<%@ page import="com.docu.security.ApplicationUserType; com.docu.commons.CommonConstants; org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%--
  Created by IntelliJ IDEA.
  User: kibria
  Date: 11/27/12
  Time: 11:52 AM
  To change this template use File | Settings | File Templates.
--%>
<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>
<script type="text/javascript">
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
            var data = $("#gFormNewApplicationUser").serialize();
            DocuAjax.json(url, data, function (json) {
                SubmissionLoader.hideFrom();
                if (json.message.type == 1) {
                    $("#gFormNewApplicationUser")[0].reset();
                    $(".remove-all").click();
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
            var url = "${request.contextPath}/authority/save";
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
        <g:if test="${applicationUserInstance?.id}">
            $('#username').attr('readonly', true);
            $('#fullName').attr('readonly', true);
            $('#checkProperties').show();
            $('#passwordBlock').hide();
        </g:if>
        <g:else>
            $('#username').focus();
            $('#username').removeAttr('readonly');
            $('#fullName').removeAttr('readonly');
            $('#checkProperties').hide();
            $('#passwordBlock').show();
        </g:else>
    });
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
</script>

<div class="main_container" style="margin-left: 40px;">
    <form id="gFormNewApplicationUser" name="gFormNewApplicationUser">
        <g:hiddenField name="docu-ignore-security" value="1"/>
        <g:hiddenField name="id" value="${applicationUserInstance?.id}"/>
        <g:hiddenField name="version" value="${applicationUserInstance?.version}"/>
        <g:hiddenField name="domainStatus" id="domainStatus" value="${CommonConstants.DOMAIN_STATUS_ACTIVE}"/>

        <div class=content_container>
            <h3>Application User</h3>

            <div class="element_container_big">
                <div class="block-title">
                    <div class="element-title input_width320">User Type *</div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <div class="element-input inputContainer value  input_width320">
                        <g:select name="userType.id" id="userType" class="validate[required] width300"
                                     value="" from="${userTypeList}" optionKey="id" noSelection="['':'Select User Type']"/>
                    </div>

                    <div class="element-title input_width320" style="color:#ff0000; "></div>

                    <div class="clear"></div>
                </div>
            </div>

            <div class="element_container_big">
                <div class="block-title">
                    <div class="element-title input_width320">User Name *</div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <div class="element-input inputContainer value  input_width320">
                        <g:textField name="username" class="validate[required,maxSize[20],minSize[3] width300" readonly="true"
                                     value="${applicationUserInstance?.username}" onchange="ApplicationUserEditor.checkAvailability(this.value)" />
                    </div>

                    <div class="element-title input_width320" style="color:#ff0000; " id="msg-div"></div>

                    <div class="clear"></div>
                </div>
            </div>


            <div id="passwordBlock" class="element_container_big">
                <div class="block-title">
                    <div class="element-title input_width320">Password *</div>

                    <div class="element-title input_width320">Confirm Password *</div>

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

                    <div class="element-title input_width320">Email *</div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <div class="element-input inputContainer value input_width320">
                        <g:textField name="fullName" value="${applicationUserInstance?.full_name}" class="width300" readonly="true"/>
                    </div>

                    <div class="element-input inputContainer value input_width320">
                        <g:textField name="email" value="${applicationUserInstance?.email_address}" class="validate[required,funcCall[validateEmail]} width300" />
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
        <div class=content_container>
            <h3 style="margin-bottom:0">User Role Mapping</h3>

            <div class="clear"></div>
            <b><a href="#create-user-role" style="float:right; padding: 2px 5px;" class="user-popUp">Create User Role</a>
            </b>

            <div class="clear" style="height: 2px;"></div>

            <div id="user-authority-block">
                <g:render plugin="sbicloud-commons" template="userAuthority"/>
            </div>

        </div>
        <div class="buttons">
            <g:if test="${applicationUserInstance?.id}">
                <input type="button"
                       class="ui-button ui-widget ui-state-default ui-corner-all"
                       value="Update"
                       onclick="ApplicationUserEditor.updateApplicationUserAuthority()"/>
            </g:if>
            <g:else>
                <input type="button"
                       class="ui-button ui-widget ui-state-default ui-corner-all"
                       value="Save"
                       onclick="ApplicationUserEditor.saveApplicationUserRemotely()"/>
            </g:else>
        </div>

    </form>
    <div style="display:none">
        <div class=content_container id="create-user-role">
            <h3>User Role</h3>

            <form id="authority-form">
                <g:hiddenField name="docu-ignore-security" value="1"/>
                <div class="element_container_big">

                    <div class="block-title">
                        <div class="element-title">Role</div>

                        <div class="clear"></div>
                    </div>

                    <div class="block-input">
                        <div class="element-input inputContainer value">
                            <g:textField name="role" value="" class="validate[required]"/>
                        </div>

                        <div class="clear"></div>
                    </div>

                    <div class="block-title">
                        <div class="element-title">Module</div>

                        <div class="element-title">Dashboard Url</div>

                        <div class="clear"></div>
                    </div>

                    <div class="block-input">
                        <div class="element-input inputContainer value">
                            <g:select class="validate[required,funcCall[isDropdownSelected]]" name="moduleId" id="moduleId"
                                      from="${moduleInfoList}" noSelection="['null':'-Select Module-']"
                                      optionKey="id"/>
                        </div>

                        <div class="element-input inputContainer value">
                            <g:textField name="dashboardUrl" id="dashboardUrl" value="" class="validate[required]"/>
                        </div>

                        <div class="clear"></div>
                    </div>

                    <div class="clear" style="height:5px;"></div>

                    <div class="buttons">
                        <input type="button" id="save-role"
                               class="ui-button ui-widget ui-state-default ui-corner-all"
                               value="Save"
                               onclick="ApplicationUserEditor.saveAuthority()"/>
                    </div>

                    <div class="clear" style="height:5px;"></div>
                </div>
            </form>

        </div>
    </div>
</div>
