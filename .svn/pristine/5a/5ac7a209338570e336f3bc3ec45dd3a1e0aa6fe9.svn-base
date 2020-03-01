<%--
<%--
  Created by IntelliJ IDEA.
  User: Mashuk
  Date: 1/28/13
  Time: 2:57 PM
  To change this template use File | Settings | File Templates.
--%>

<meta name="layout" content="applicationUserLayout"/>
<title><g:message code="changeSettings.title.label" default="Change Security Setting"/></title>
<script type="text/javascript">
    $(document).ready(function () {
        CursorListener.setFocusOnFirstField();
        EnterKeyListener.init();
        $("#change-settings-form").validationEngine({
            isOverflown:true,
            overflownDIV:".ui-layout-center"
        });
        $("#change-settings-form").validationEngine('attach');

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

        <g:if test="${userPreference.securityQuestionOne}">
        if ($("#systemSecurityQuestionOne").val() == "null") {
            $(".question-one-toggle").click()
        }
        else{
            $("#customSecurityQuestionOne").val("")
        }
        </g:if>
        <g:if test="${userPreference.securityQuestionOne}">
        if ($("#systemSecurityQuestionTwo").val() == "null") {
            $(".question-two-toggle").click()
        }
        else{
            $("#customSecurityQuestionTwo").val("")
        }
        </g:if>
    })

    function changeSettings() {
        if (!$("#change-settings-form").validationEngine('validate')) {
            return false;
        }
        SubmissionLoader.showTo();
        var data = {}

        data['oldPassword'] = $("#oldPassword").val()
        data['uname'] = $("#uname").val()
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

        DocuAjax.json('${request.contextPath}/${params.controller}/saveChangedSecuritySettings', data, function (response) {
            SubmissionLoader.hideFrom();
            if (response.type == 1) {
                window.location = "${request.contextPath}/login"
            }
            else {
                clearPasswordField()
                MessageRenderer.render(response)
            }
        })
    }
    function clearPasswordField(){
        $("#oldPassword").val("")
        $("#password").val("")
        $("#confirmPassword").val("")
        $("#uname").val("")
    }
    function validatePassword(field, rules, i, options) {
        var re = /^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%&*!?^]).{6,20}$/;
        if (!re.test(field.val())) {
            return "* Password should be 6 to 20 character long. * Password should have at least one alphabet, one numeric value and one special characters.";
        }
    }

</script>

<form id="change-settings-form">
    <div class="main_container mar_auto0 width800">
        <h1><g:message code="changeSettings.title.label" default="Change Security Setting"/></h1>
        <div class=content_container>
            <g:hiddenField name="uname" id="uname" value="${userName}" />
            <h3>Change Password</h3>

            <div class="element_row_content_container big_height_lightColorbg pad_bot0">
               <g:if test="${flash.message}">
                <label class="txtright bold hight1x width1x">
                    ${flash.message}
                </label>
                </g:if>
                <label class="txtright bold hight1x width1x">
                    User Name
                </label>

                <div class="element-input inputContainer width400 pad_left15">
                    ${userName}
                </div>
            </div>

            <div class="element_row_content_container big_height_lightColorbg pad_bot0">
                <label class="txtright bold hight1x width1x">
                    Old Password
                </label>

                <div class="element-input inputContainer width400 pad_left15">
                    <g:passwordField name="oldPassword" value="" class="validate[required] width300"/>
                </div>
            </div>

            <div id="passwordBlock">
                <div class="element_row_content_container big_height_lightColorbg pad_bot0">
                    <label class="txtright bold hight1x width1x">
                        New Password
                    </label>

                    <div class="element-input inputContainer width400 pad_left15">
                        <g:passwordField name="password" value=""
                                         class="validate[required,maxSize[20],funcCall[validatePassword]} width300"/>
                    </div>
                </div>

                <div class="element_row_content_container big_height_lightColorbg pad_bot0 mar_bot5">
                    <label class="txtright bold hight1x width1x">
                        Confirm Password
                    </label>

                    <div class="element-input inputContainer width400 pad_left15">
                        <g:passwordField name="confirmPassword" value="" class="validate[required] width300"/>
                    </div>
                </div>
            </div>
            <div class="clear"></div>
            %{--<div class="block-title">--}%
                %{--<div class="element-title">User Name</div>--}%

                %{--<div class="element-title">Old Password</div>--}%

                %{--<div class="clear"></div>--}%
            %{--</div>--}%

            %{--<div class="block-input">--}%
                %{--<div class="element-input">--}%
                    %{--${userName}--}%
                %{--</div>--}%

                %{--<div class="element-input inputContainer">--}%
                    %{--<g:passwordField name="oldPassword" value="" class="validate[required]"/>--}%
                %{--</div>--}%
                %{--<div class="clear"></div>--}%
            %{--</div>--}%


            %{--<div id="passwordBlock">--}%
                %{--<div class="block-title">--}%
                    %{--<div class="element-title">New Password</div>--}%

                    %{--<div class="element-title">Confirm Password</div>--}%

                    %{--<div class="clear"></div>--}%
                %{--</div>--}%

                %{--<div class="block-input">--}%
                    %{--<div class="element-input inputContainer">--}%
                        %{--<g:passwordField name="password" value=""--}%
                                         %{--class="validate[required,maxSize[20],funcCall[validatePassword]}"/>--}%
                    %{--</div>--}%

                    %{--<div class="element-input inputContainer">--}%
                        %{--<g:passwordField name="confirmPassword" value="" class="validate[required]"/>--}%
                    %{--</div>--}%

                    %{--<div class="clear"></div>--}%
                %{--</div>--}%
            %{--</div>--}%

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
                        <a class="question-one-toggle" href="javascript:void(0);">
                            <img src="${request.contextPath}/images/btn_custom.png" alt='Custom' title='Custom' class="mar_left5"/>
                        </a>

                        <div id="system-question-one-block" class="inputContainer mar_left5">
                            <g:select
                                    name="securityQuestionOne"
                                    id="systemSecurityQuestionOne"
                                    from="${securityQuestionList}" noSelection="['null':'-Select Question One-']"
                                    optionKey="question"
                                    optionValue="question"
                                    class="validate[funcCall[isDropdownSelected]] width330"
                                    value="${userPreference.securityQuestionOne}"/>
                        </div>

                        <div class="inputContainer mar_left5" id="custom-question-one-block"
                             style="display: none;">
                            <g:textField name="securityQuestionOne" id="customSecurityQuestionOne" value="${userPreference.securityQuestionOne}"
                                         class="validate[required] width324"/>
                        </div>
                    </td>
                    <td class="pad_top2 pad_bot2">
                        <div class="inputContainer mar_left5">
                            <g:textField name="answerOne" id="answerOne" value="${userPreference.answerOne}" class='validate[required] width330'/>
                        </div>
                    </td>
                </tr>
                <tr class="even">
                    <td class="txtC pad_top2 pad_bot2">2</td>
                    <td class="pad_top2 pad_bot2">
                        <a class="question-two-toggle" href="javascript:void(0);">
                            <img src="${request.contextPath}/images/btn_custom.png" alt='Custom' title='Custom' class="mar_left5"/>
                        </a>

                        <div id="system-question-two-block" class="inputContainer mar_left5">
                            <g:select
                                    name="securityQuestionTwo"
                                    id="systemSecurityQuestionTwo"
                                    from="${securityQuestionList}" noSelection="['null':'-Select Question Two-']"
                                    optionKey="question"
                                    optionValue="question"
                                    class="validate[funcCall[isDropdownSelected]] width330"
                                    value="${userPreference.securityQuestionTwo}"/>
                        </div>

                        <div id="custom-question-two-block" class="inputContainer mar_left5" style="display: none;">
                            <g:textField name="securityQuestionTwo " id="customSecurityQuestionTwo" value="${userPreference.securityQuestionTwo}"
                                         class='validate[required] width324'/>
                        </div>
                    </td>
                    <td class="pad_top2 pad_bot2">
                        <div class="inputContainer mar_left5">
                            <g:textField name="answerTwo" id="answerTwo" value="${userPreference.answerTwo}" class='validate[required] width330'/>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="buttons">

            <input type="button" id="change-setting"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="<g:message code="changeSetting.title" default="Save"/>"
                   onclick="changeSettings()"/>
        </div>
    </div>
</form>