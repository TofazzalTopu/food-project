<%--
  Created by IntelliJ IDEA.
  User: Samia
  Date: 6/7/2015
  Time: 11:12 PM
--%>

<%@ page import="com.docu.security.SecurityQuestion; java.text.SimpleDateFormat; org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<head>
    <meta name="layout" content="changePassLayout"/>
    <title>Change Password</title>
</head>
<script type="text/javascript">
    var migrationDate = "${new SimpleDateFormat("dd-MM-yyyy").format(new Date())}"
    var previousDate = "";
    var isDisplayed;

    var ApplicationUserEditor = {

        saveApplicationUserChangePassword:function () {
            if (!$("#gFormNewApplicationUser").validationEngine('validate')) {
                return false;
            }
            SubmissionLoader.showTo();
            var url = "${request.contextPath}/${params.controller}/saveApplicationUserChangePassword";
            var data = $("#gFormNewApplicationUser").serialize();
            DocuAjax.json(url, data, function (data) {
                SubmissionLoader.hideFrom();
                if (data){

                    if (data.message.type==1){
                        window.open("${resource(dir:'home', file:'index')}",'_self',false)
                    }
                    else{
                        MessageRenderer.render(data.message)

                    }
                }


            });
        }

    };




</script>

<div id="dialog-user-notification" style="display:none">
    <h2>Please check the followings:</h2>
    <br/><div id="addMessage1"></div>
    <div id="addMessage2"></div>
    <div id="addMessage3"></div>
    <br/><h2>Do you want to continue?</h2>
</div>


<h1>
    <span class="headerMain">Change Password on</span>
    <span class="mar_top8 pad_left5">  &nbsp;<g:textField readonly="readonly" name="dashBoardBusinessDate" id="dashBoardBusinessDate" value="${new java.util.Date().format('dd-MM-yyyy hh:mm aa')}" class="inputBgBlank" style="width:122px;"/>&nbsp;
    </span>
</h1>


<div class="main_container">
    <form id="gFormNewApplicationUser" name="gFormNewApplicationUser" >
        <g:hiddenField name="userId" value="${applicationUser.id}"/>
        <g:if test="${userFirstLoginPasswordVerification.isVerified='false'}">
            <g:hiddenField name="verifyId" value="${userFirstLoginPasswordVerification.id}"/>
        </g:if>


        <div class=content_container>
            <h3>Application User</h3>



            <div id="passwordBlock" class="element_container_big">
                <div class="block-title">
                    <div class="element-title input_width320">Password</div>

                    <div class="element-title input_width320">Confirm Password</div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <div class="element-input inputContainer value input_width320">
                        <g:passwordField name="password" value=""
                                         class="validate[required,maxSize[20],funcCall[validatePassword]} width300"/>
                    </div>

                    <div class="element-input inputContainer value input_width320">
                        <g:passwordField name="confirmPassword" value=""
                                         class=" width300"/>
                    </div>

                    <div class="clear"></div>
                </div>
            </div>


        </div>


        <div>
            <h3>Security Questions and Answers</h3>
            <div style="clear:both;"></div>
<g:if test="${securityQuestionList}">
    <g:each in="${(1..questionLimit).toList()}" var="question" status="i">
        <div id="assmentTbl" >
            <div class="groupTr" >
                <div >

                    <div class="content_container">
                        <div style="min-height: 25px;">
                            <h3><g:select name="securityQuestion.${i}" id="securityQuestion" style="width: 400px"
                                          from="${com.docu.security.SecurityQuestion.list()}"
                                          optionKey="id" class="validate[required]" optionValue="question"
                            /></h3>

                        </div>
                    </div>

                </div>


            </div>


            <div style="background-color: #D7DFE7;" >

                <div>
                    <div style="float:left;padding-left:4px ;min-height:25px;">

                        <input type="text"  name="answer.${i}" style="width: 700px"  >

                    </div>

                </div>
                <div style="clear:both;"></div>
            </div>


        </div>

    </g:each>
</g:if>

        </div>
        <div class="buttons">
            %{--<button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" style="width: 50px;height: 25px">Save</button>--}%
            <input type="button"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="Save"
                   onclick="ApplicationUserEditor.saveApplicationUserChangePassword()"/>



        </div>

    </form>

</div>