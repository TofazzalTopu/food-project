<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 6/13/11
  Time: 6:26 PM
  To change this template use File | Settings | File Templates.
--%>
<meta name="layout" content="docuThemeRollerLayout"/>
%{--<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>--}%
<title><g:message code="ForgotPassword" default="Forgot Password"/></title>
<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder; com.docu.commons.CommonConstants" contentType="text/html;charset=UTF-8" %>
<g:render template='/common/message'/>
<script type="text/javascript">
    $(document).ready(function () {
        EnterKeyListener.init();
        passControlBetweenFields();
        $("#forgot-password-form").validationEngine({
            isOverflown:true,
            overflownDIV:".ui-layout-center"
        });

        $("#forgot-password-form").validationEngine('attach');
    });

    function checkUserName() {
        if (!$("#forgot-password-form").validationEngine('validate')) {
            return false;
        }

        var data = {};
        data['username'] = $("#username").val();
        data['docu-ignore-security'] = 1;
        var actionUrl = "${request.contextPath}/${params.controller}/getSecurityQuestion";
        AjaxLoader.showTo("security-block")
        DocuAjax.html(actionUrl, data, function (response) {
            var msg = null
            try {
                AjaxLoader.hideFrom("security-block")
                var msg = JSON.parse(response);
                MessageRenderer.render(msg)
            }
            catch (e) {
                $("#security-block").html(response)
//                $("#user-block").slideUp();
            }
        });

    }

</script>

<div class="main_container mar_auto0 width600">
    <h1 class="width580">Forgot Password</h1>

    <div id="user-block">
        <form id="forgot-password-form">
            <div class=content_container>
                <div class="element_row_content_container big_height_lightColorbg pad_bot0">
                    <div class="disTable">
                        <label class="txtright bold hight1x width1x">
                            User Name
                        </label>

                        <div class="element-input inputContainer width200">
                            <g:textField name="username"
                                         class="validate[required] width180"
                                         value=""/>

                        </div>
                    </div>
                </div>

                <div class="clear"></div>

                <div class="buttons" style="padding-left: 155px;">
                    <input type="button" id="reset-button"
                           class="ui-button ui-widget ui-state-default ui-corner-all"
                           value="<g:message code="continuew" default="Continue"/>"
                           onclick="checkUserName()"/>
                </div>

                <div class="clear"></div>
            </div>
        </form>
    </div>

    <div class="clear"></div>

    <div id="security-block">

    </div>
</div>