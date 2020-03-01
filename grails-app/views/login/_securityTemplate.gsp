<%@ page import="com.docu.security.ApplicationUserType" %>
<%--
  Created by IntelliJ IDEA.
  User: Mashuk
  Date: 3/13/13
  Time: 4:18 PM
  To change this template use File | Settings | File Templates.
--%>
<script type="text/javascript">
    $(document).ready(function () {
        EnterKeyListener.init();
        passControlBetweenFields();
        $("#security-form").validationEngine({
            isOverflown:true,
            overflownDIV:".ui-layout-center"
        });

        $("#security-form").validationEngine('attach');

    });
    function doCancel(){
//        $("#security-block").hide();
        $("#security-block").html("")
//        $("#user-block").slideDown();
    }

    function executeForgotPassword() {
        if (!$("#security-form").validationEngine('validate')) {
            return false;
        }
        trimForm()
        SubmissionLoader.showTo();
        $.ajax({
            url:"${request.contextPath}/${params.controller}/executeForgotPassword?docu-ignore-security=1",
            dataType:"json",
            data:$("#security-form").serialize(),
            success:function (json) {
                if (json.isError != 1) {
                    $("#username").val("")
                    doCancel()
                }
                SubmissionLoader.hideFrom();
                MessageRenderer.render(json.message);

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }  else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            }
        });
    }
</script>
<form id="security-form">
    <g:hiddenField name="userid" id="userid" value="${applicationUser.id}"/>
    %{--<div class="element_row_content_container big_height_lightColorbg pad_bot0">--}%
        %{--<div class="disTable">--}%
            %{--<label class="txtright bold hight1x width1x">--}%
                %{--User Name--}%
            %{--</label>--}%

            %{--<div class="element-input inputContainer width200">--}%
                %{--${applicationUser.username}--}%
            %{--</div>--}%
        %{--</div>--}%
    %{--</div>--}%

    %{--<div class="clear"></div>--}%

    <h3>Security Question</h3>

    <div id="security-ques-div">

        <div class="block-title">
            <div class="element-title width580">  ${userPreference.securityQuestionOne}</div>

            <div class="clear"></div>
        </div>

        <div class="block-input">
            <div class="element-input inputContainer value width580">
                <g:textField name="ques1"
                             class="validate[required] width500"
                             value=""/>
            </div>
            <div class="clear"></div>
        </div>
        %{--<div class="element_row_content_container big_height_lightColorbg pad_bot0">--}%
            %{--<div class="disTable">--}%
                %{--<label class="txtright bold hight1x width1x" id="ques1-div">--}%
                      %{--${userPreference.securityQuestionOne}--}%
                %{--</label>--}%

                %{--<div class="element-input inputContainer width200">--}%
                    %{--<g:textField name="ques1"--}%
                                 %{--class="validate[required] width180"--}%
                                 %{--value=""/>--}%

                %{--</div>--}%
            %{--</div>--}%
        %{--</div>--}%

        <div class="block-title">
            <div class="element-title width580"> ${userPreference.securityQuestionTwo}</div>

            <div class="clear"></div>
        </div>

        <div class="block-input">
            <div class="element-input inputContainer value width580">
                <g:textField name="ques2"
                             class="validate[required] width500"
                             value=""/>
            </div>
            <div class="clear"></div>
        </div>
        %{--<div class="element_row_content_container big_height_lightColorbg pad_bot0">--}%
            %{--<div class="disTable">--}%
                %{--<label class="txtright bold hight1x width1x" id="ques2-div">--}%
                    %{--${userPreference.securityQuestionTwo}--}%
                %{--</label>--}%

                %{--<div class="element-input inputContainer width200">--}%
                    %{--<g:textField name="ques2"--}%
                                 %{--class="validate[required] width180"--}%
                                 %{--value=""/>--}%

                %{--</div>--}%
            %{--</div>--}%
        %{--</div>--}%

    </div>
    <div class="clear"></div>

    <div class="buttons" style="padding-left: 155px;">
        <input type="button" id="reset-button"
               class="ui-button ui-widget ui-state-default ui-corner-all"
               value="<g:message code="password" default="Reset Password"/>"
               onclick="executeForgotPassword()"/>
        <input type="button" id="cancel"
               class="ui-button ui-widget ui-state-default ui-corner-all"
               value="<g:message code="Forgot Password" default="Cancel"/>"
               onclick="doCancel();"/>
    </div>

    </form>