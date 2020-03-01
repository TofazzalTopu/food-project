<%@ page import="com.docu.commons.CommonConstants; org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%--
<%--
  Created by IntelliJ IDEA.
  User: Mashuk
  Date: 1/28/13
  Time: 2:57 PM
  To change this template use File | Settings | File Templates.
--%>

<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>
<title><g:message code="changePassword.title.label" default="Change Password"/></title>
<script type="text/javascript">
    $(document).ready(function () {
        CursorListener.setFocusOnFirstField();
        EnterKeyListener.init();
        $("#change-password-form").validationEngine({
            isOverflown:true,
            overflownDIV:".ui-layout-center"
        });
        $("#change-password-form").validationEngine('attach');
    });

    function changePassword() {
        if (!$("#change-password-form").validationEngine('validate')) {
            return false;
        }
        SubmissionLoader.showTo();
        var data = {};

        data['oldPassword'] = $("#oldPassword").val();
        data['password'] = $("#password").val();
        data['confirmPassword'] = $("#confirmPassword").val();

        DocuAjax.json('${request.contextPath}/${params.controller}/executeChangePassword', data, function (response) {
            SubmissionLoader.hideFrom();
            if (response.type == 1) {
                window.location = "${request.contextPath}/logout"
            }
            else {
                MessageRenderer.render(response)
            }
        })
    }

    function validatePassword(field, rules, i, options) {
//        var re = /^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%&*!?^]).{6,20}$/;
        var re = ${passwordPattern}
        if (!re.test(field.val())) {
            return "${CommonConstants.PASSWORD_VALIDATION_MESSAGE}";
        }
    }

</script>

<form id="change-password-form">
    <div class="main_container width800">
        <h1><g:message code="changePassword.title.label" default="Change Password"/></h1>
        <div class=content_container>
            <h3>Change Password</h3>

            <div class="element_row_content_container big_height_lightColorbg pad_bot0">
                <label class="txtright bold hight1x width1x">
                    User Name
                </label>

                <div class="element-input inputContainer width400 pad_left15">
                    <b><sec:ifLoggedIn> <sec:loggedInUserInfo field="username"/></sec:ifLoggedIn></b>
                </div>
            </div>

            <div class="element_row_content_container big_height_lightColorbg pad_bot0">
                <label class="txtright bold hight1x width1x">
                    Old Password <span class="red">*</span>
                </label>

                <div class="element-input inputContainer width400 pad_left15">
                    <g:passwordField name="oldPassword" value="" class="validate[required] width300"/>
                </div>
            </div>

            <div class="element_row_content_container big_height_lightColorbg pad_bot0">
                <label class="txtright bold hight2x width1x">

                </label>

                <div class="element-input inputContainer width600 pad_left15">
                    <span class="red"> Password should be 6 to 20 character long with at least one alphabet, one numeric value and one special characters. Example: abc@123</span>
                </div>
            </div>

            <div id="passwordBlock">
                <div class="element_row_content_container big_height_lightColorbg pad_bot0">
                    <label class="txtright bold hight1x width1x">
                        New Password <span class="red">*</span>
                    </label>

                    <div class="element-input inputContainer width400 pad_left15">
                        <g:passwordField name="password" value=""
                                         class="validate[required,maxSize[20],funcCall[validatePassword]} width300"/>
                    </div>
                </div>

                <div class="element_row_content_container big_height_lightColorbg pad_bot0 mar_bot5">
                    <label class="txtright bold hight1x width1x">
                        Confirm Password <span class="red">*</span>
                    </label>

                    <div class="element-input inputContainer width400 pad_left15">
                        <g:passwordField name="confirmPassword" value="" class="validate[required] width300"/>
                    </div>
                </div>
            </div>
            <div class="clear"></div>

        </div>

        <div class="buttons">
            <input type="button" id="change-setting"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="<g:message code="changePassword.title" default="Save"/>"
                   onclick="changePassword()"/>
        </div>
    </div>
</form>

