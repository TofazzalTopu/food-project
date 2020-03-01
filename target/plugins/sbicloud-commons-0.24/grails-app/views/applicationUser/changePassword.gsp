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

        $(".question-one-toggle").toggle(
                function () {
                    $("#system-question-one-block").slideUp();
                    $("#custom-question-one-block").slideDown();
                    $("#systemSecurityQuestionOne").val("null")
                    $(this).html("<img src='${request.contextPath}/images/btn_system.png' alt='Custom' title='System' class='mar_left5'/>")
                },
                function () {
                    $("#custom-question-one-block").slideUp();
                    $("#system-question-one-block").slideDown();
                    $("#customSecurityQuestionOne").val("")
                    $(this).html("<img src='${request.contextPath}/images/btn_custom.png' alt='Custom' title='Custom' class='mar_left5'/>")
                }
        )

        $(".question-two-toggle").toggle(function () {
                    $("#system-question-two-block").slideUp();
                    $("#custom-question-two-block").slideDown();
                    $("#systemSecurityQuestionTwo").val("null")
                    $(this).html("<img src='${request.contextPath}/images/btn_system.png' alt='Custom' title='System' class='mar_left5'/>")
                },
                function () {
                    $("#custom-question-two-block").slideUp();
                    $("#system-question-two-block").slideDown();
                    $("#customSecurityQuestionTwo").val("")
                    $(this).html("<img src='${request.contextPath}/images/btn_custom.png' alt='Custom' title='Custom' class='mar_left5'/>")
                }
        )

        <g:if test="${securitySettingObject?.userPreference?.securityQuestionOne}">
        if ($("#systemSecurityQuestionOne").val() == "null") {
            $(".question-one-toggle").click()
        }
        else{
            $("#customSecurityQuestionOne").val("")
        }
        </g:if>
        <g:if test="${securitySettingObject?.userPreference?.securityQuestionOne}">
        if ($("#systemSecurityQuestionTwo").val() == "null") {
            $(".question-two-toggle").click()
        }
        else{
            $("#customSecurityQuestionTwo").val("")
        }
        </g:if>
    })

    function changePassword() {
        if (!$("#change-password-form").validationEngine('validate')) {
            return false;
        }
        SubmissionLoader.showTo();
        var data = {}

        data['oldPassword'] = $("#oldPassword").val()
        data['password'] = $("#password").val()
        data['confirmPassword'] = $("#confirmPassword").val()
        if($("#systemSecurityQuestionOne").val() == "null"){
            data['securityQuestionOne'] = $("#customSecurityQuestionOne").val()
        }
        else{
            data['securityQuestionOne'] = $("#systemSecurityQuestionOne").val()
        }

        if($("#systemSecurityQuestionTwo").val() == "null"){
            data['securityQuestionTwo'] = $("#customSecurityQuestionTwo").val()
        }
        else{
            data['securityQuestionTwo'] = $("#systemSecurityQuestionTwo").val()
        }
        data['answerOne'] = $("#answerOne").val()
        data['answerTwo'] = $("#answerTwo").val()

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
                    ${userName}
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

        <h3>Security Question</h3>

        <div>
            <table id="security-question-table" border="0" cellpadding="0" cellspacing="0" width="100%"
                   class="pretty">
                <thead>
                <tr>
                    <th rowspan="2" class="width10p pad_left5">SL</th>
                    <th rowspan="2" class="width45p pad_left5">Question</th>
                    <th rowspan="2" class="width45p pad_left5">Answer</th>
                </tr>
                </thead>
                <tbody>
                <tr class="odd">
                    <td class="txtC pad_top2 pad_bot2">1</td>
                    <td class="pad_top2 pad_bot2">


                        <div id="system-question-one-block" class="inputContainer">
                            <g:select
                                    name="securityQuestionOne"
                                    id="systemSecurityQuestionOne"
                                    from="${securityQuestionList}" noSelection="['null':'-Select Question One-']"
                                    optionKey="question"
                                    optionValue="question"
                                    class="validate[funcCall[isDropdownSelected]] width310"
                                    value="${securitySettingObject?.userPreference?.securityQuestionOne}"/>
                        </div>

                        <div class="inputContainer mar_left10" id="custom-question-one-block"
                             style="display: none;">
                            <g:textField name="securityQuestionOne" id="customSecurityQuestionOne" value="${securitySettingObject?.userPreference?.securityQuestionOne}"
                                         class="validate[required] width300"/>
                        </div>
                        <a class="question-one-toggle" href="javascript:void(0);">
                            <img src="${request.contextPath}/images/btn_custom.png" alt='Custom' title='Custom' class="mar_left5"/>
                        </a>
                    </td>
                    <td class="pad_top2 pad_bot2">
                        <div class="inputContainer mar_left5">
                            <g:textField name="answerOne" id="answerOne" value="${securitySettingObject?.userPreference?.answerOne}" class='validate[required] width330'/>
                        </div>
                    </td>
                </tr>
                <tr class="even">
                    <td class="txtC pad_top2 pad_bot2">2</td>
                    <td class="pad_top2 pad_bot2">
                        <div id="system-question-two-block" class="inputContainer">
                            <g:select
                                    name="securityQuestionTwo"
                                    id="systemSecurityQuestionTwo"
                                    from="${securityQuestionList}" noSelection="['null':'-Select Question Two-']"
                                    optionKey="question"
                                    optionValue="question"
                                    class="validate[funcCall[isDropdownSelected]] width310"
                                    value="${securitySettingObject?.userPreference?.securityQuestionTwo}"/>
                        </div>

                        <div id="custom-question-two-block" class="inputContainer mar_left10" style="display: none;">
                            <g:textField name="securityQuestionTwo " id="customSecurityQuestionTwo" value="${securitySettingObject?.userPreference?.securityQuestionTwo}"
                                         class='validate[required] width300'/>
                        </div>
                        <a class="question-two-toggle" href="javascript:void(0);">
                            <img src="${request.contextPath}/images/btn_custom.png" alt='Custom' title='Custom' class="mar_left5"/>
                        </a>
                    </td>
                    <td class="pad_top2 pad_bot2">
                        <div class="inputContainer mar_left5">
                            <g:textField name="answerTwo" id="answerTwo" value="${securitySettingObject?.userPreference?.answerTwo}" class='validate[required] width330'/>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="buttons">

            <input type="button" id="change-setting"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="<g:message code="changePassword.title" default="Save"/>"
                   onclick="changePassword()"/>
        </div>
    </div>
</form>

