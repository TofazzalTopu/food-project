<%@ page import="com.docu.commons.Message; org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%--
  Created by IntelliJ IDEA.
  User: Feroz
  Date: 9/23/13
  Time: 11:46 AM
  To change this template use File | Settings | File Templates.
--%>

<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>
<title><g:message code="applicationUser.resetInternalUserPassword.label" default="Reset Password"/></title>
<script type="text/javascript">
$(document).ready(function() {
    $("#reset-password-form").validationEngine({
        isOverflown: true,
        overflownDIV: ".ui-layout-center"
    });
    $("#reset-password-form").validationEngine('attach');
    InternalUserPasswordEditor.init();
});
function validatePassword(field, rules, i, options) {
    var re = /^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%]).{6,20}$/;
    if (!re.test(field.val())) {
        return "Password should be 6 to 20 character long. \n* Password should have at least one alphabet. \n* one numeric value. \n* And one special characters.";
    }
}
var InternalUserPasswordEditor = {
    init: function(){
        InternalUserPasswordEditor.loadFlaxBox();
    },
    loadFlaxBox: function(event){
        var params = {};
        var url = "${request.contextPath}/${params.controller}/jsonInternalUserList";
        DocuAjax.json(url, params, function (json) {
            usernameFlaxBox.setData(json);
            $("#usernameDiv_input").change(function (){
                if(usernameFlaxBox.text() == "" || usernameFlaxBox.text() == 'null'){
                    InternalUserPasswordEditor.clean();
                }
            });
        });
    },
    clean: function(){
        $('#userid').val('');
        $('#username').val('');
        $('#password').val('');
        $('#confirmPassword').val('');
    },
    validate: function(){
        if (! $("#reset-password-form").validationEngine('validate')) {
            return false;
        }
        if($('#password').val() != $('#confirmPassword').val()){
            MessageRenderer.renderErrorText("Password and confirm password does not match", "<g:message code="internalUserPassword.title" default="Reset Password"/>");
            return false;
        }
        return true;
    },
    saveInternalUserPassword: function(){
        if(!InternalUserPasswordEditor.validate()){
            return false;
        }

        var url = "${request.contextPath}/${params.controller}/executeResetPassword";
        var params = $('#reset-password-form').serialize();
        DocuAjax.json(url, params, function(json){
            MessageRenderer.render(json.message);
            if(json.message.type == ${Message.SUCCESS}){
                $('#userid').val('');
                $('#username').val('');
                $('#password').val('');
                $('#confirmPassword').val('');
                $("#usernameDiv_input").val('');
            }
        });
    }
}
</script>
<form id="reset-password-form">
    <div class="main_container">
        <h1><g:message code="applicationUser.resetInternalUserPassword.label" default="Reset Password"/></h1>

        <div class=content_container>
            <h3>Reset Password</h3>


            <div class="block-title">
                <div class="element-title">User Name</div>
                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class="element-input inputContainer width200">
                    <input type="hidden" name="userid" id="userid" />
                    <docu:flaxBox id="username" name="username" var="usernameFlaxBox" optionKey="id"
                                  optionValue="label"
                                  watermark="">
                        containerClass: "ffb width200",
                        inputClass: "ffb-input validate[required] width130",
                        onSelect: function(map){
                            $('#userid').val(map.id);
                        }
                    </docu:flaxBox>
                </div>
                <div class="clear"></div>
            </div>
        </div>

        <div>
            <div class="block-title">
                <div class="element-title">Password</div>
                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class="element-input inputContainer value">
                    <g:passwordField name="password"
                                     class="validate[required,maxSize[20],funcCall[validatePassword]} width150"/>
                </div>
                <div class="clear"></div>
            </div>
        </div>

        <div>
            <div class="block-title">
                <div class="element-title">Confirm Password</div>
                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class="element-input inputContainer value">
                    <g:passwordField name="confirmPassword"
                                     class="validate[required,maxSize[20],funcCall[validatePassword]} width150"/>
                </div>
                <div class="clear"></div>
            </div>
        </div>

        <div class="buttons">
            <input type="button" id="reset-button"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="<g:message code="Reset Password" default="Reset Password"/>"
                   onclick="InternalUserPasswordEditor.saveInternalUserPassword()"/>
        </div>
    </div>
</form>