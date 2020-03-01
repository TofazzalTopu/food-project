<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 6/13/11
  Time: 6:26 PM
  To change this template use File | Settings | File Templates.
--%>
<meta name="layout" content="applicationUserLayout"/>
%{--<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>--}%
<title><g:message code="ResetPassword" default="Reset Password"/></title>
<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder; com.docu.commons.CommonConstants" contentType="text/html;charset=UTF-8" %>
<g:render template='/common/message'/>
<script type="text/javascript">
    $(document).ready(function() {
        $("#reset-password-form").validationEngine({
                    isOverflown: true,
                    overflownDIV: ".ui-layout-center"
                });
        $("#reset-password-form").validationEngine('attach');

    });

   function checkUserName(userName) {
    $.ajax({
      dataType:"json",
      url: "${request.contextPath}/${params.controller}/checkUserName?username=" + userName,
      success: function(json) {
        if (json.isError == 1) {
          $("#msg-div").html(json.message)
        } else {
          $("#userid").val(json.userId)
          $("#msg-div").html("")


        }
      }
    });
  }

  function executeResetPassword() {
    if (! $("#reset-password-form").validationEngine('validate')) {
      return false;
    }
    trimForm()
    SubmissionLoader.showTo();
    $.ajax({
      url: "${request.contextPath}/${params.controller}/executeResetPassword",
      dataType: "json",
      data: $("#reset-password-form").serialize() ,
      success: function(json) {
      SubmissionLoader.hideFrom();
      clearForm()
      MessageRenderer.render(json.message)
      }
    });
  }

    function clearForm() {
        $('#userid').val('');
        $('#username').val('');
        $('#password').val('');
        $('#confirmPassword').val('');

    }

</script>

<script type="text/javascript">
  function validatePassword(field, rules, i, options) {
    var re = /^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%]).{6,20}$/;
    if (!re.test(field.val())) {
      return "Password should be 6 to 20 character long. \n* Password should have at least one alphabet. \n* one numeric value. \n* And one special characters.";
    }
  }
</script>

<form id="reset-password-form">
    <div class="main_container">

        <g:hiddenField name="userid" id="userid" value=""/>

        <div class=content_container>
            <h3>Reset Password</h3>


            <div class="block-title">
                <div class="element-title">User Name</div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class="element-input inputContainer value">
                    <g:textField name="username"
                                 class="validate[required,maxSize[20],minSize[5]"
                                 value=" " onchange="checkUserName(this.value)"/>
                </div>

                <div class="element-title" style="width: 300px; color:#ff0000; " id="msg-div"></div>

                <div class="clear"></div>
            </div>


            <div id="passwordBlock">
                <div class="block-title">
                    <div class="element-title">Password</div>

                    <div class="element-title">Confirm Password</div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <div class="element-input inputContainer value">
                        <g:passwordField name="password" value=""
                                         class="validate[required,maxSize[20],funcCall[validatePassword]}"/>
                    </div>

                    <div class="element-input inputContainer value">
                        <g:passwordField name="confirmPassword" value=""/>
                    </div>

                    <div class="clear"></div>
                </div>
            </div>

        </div>

        <div class="buttons">

            <input type="button" id="reset-button"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="<g:message code="Reset Password" default="Reset Password"/>"
                   onclick="executeResetPassword()"/>
        </div>
    </div>
</form>
