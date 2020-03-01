<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder; grails.plugins.springsecurity.SpringSecurityService; com.docu.commons.CommonConstants; com.docu.security.ApplicationUserType" %>
<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 6/8/11
  Time: 3:21 PM
  To change this template use File | Settings | File Templates.
--%>
<meta name="layout" content="applicationUserLayout"/>


<g:set var="entityName"
       value="${message(code: 'applicationUser.label', default: 'ApplicationUser')}"/>
<title><g:message code="default.create.label" args="[entityName]"/></title>

<script type="text/javascript">
    $(document).ready(function () {
        $("#userType").change();

        $("#application-user-form").validationEngine({
            isOverflown:true,
            overflownDIV:".ui-layout-center",
            validationEventTrigger:""
        });
        $("#application-user-form").validationEngine('attach');

        initialiseFancyBox()
    });
</script>

<script type='text/javascript'>
    $(document).ready(function () {
//    $('#dateCreated').mask('99-99-9999', {});
//    $('#lastUpdated').mask('99-99-9999', {});

        <g:if test="${applicationUserInstance?.id}">
        $('#checkProperties').show()
        $('#passwordBlock').hide()
        </g:if>
        <g:else>
        $('#checkProperties').hide()
        $('#passwordBlock').show()
        </g:else>
    });
</script>

<script type='text/javascript'>

    function isShowEmployeeDropDown() {
        if ($('#userType').attr('value') == 'INTERNAL') {
            $('#userReference').show()
            $('#employeeLabel').show()
        } else {
            $('#userReference').hide()
            $('#employeeLabel').hide()
        }
    }
</script>

<script type="text/javascript">
    function saveApplicationUserRemotely() {
        if (!$("#application-user-form").validationEngine('validate')) {
            return false;
        }
        SubmissionLoader.showTo();
    %{--$.ajax({--}%
    %{--url:"${request.contextPath}/${params.controller}/saveApplicationUserRemotely",--}%
    %{--dataType:"json",--}%
    %{--data:$("#application-user-form").serialize(),--}%
    %{--success:function (json) {--}%
    %{--SubmissionLoader.hideFrom();--}%
    %{--if (json.message.type == 1) {--}%
    %{--$("#application-user-form")[0].reset();--}%
    %{--}--}%
    %{--MessageRenderer.render(json.message);--}%
    %{--}--}%
    %{--});--}%
        var url = "${request.contextPath}/${params.controller}/saveApplicationUserRemotely";
        var data = $("#application-user-form").serialize();
        DocuAjax.json(url, data, function (json) {
            SubmissionLoader.hideFrom();
            if (json.message.type == 1) {
                $("#application-user-form")[0].reset();
                $(".remove-all").click();
            }
            MessageRenderer.render(json.message);
        });
    }

    function updateApplicationUserRemotely() {
        if (!$("#application-user-form").validationEngine('validate')) {
            return false;
        }
        SubmissionLoader.showTo();
    %{--$.ajax({--}%
    %{--url:"${request.contextPath}/${params.controller}/updateApplicationUserRemotely",--}%
    %{--dataType:"json",--}%
    %{--data:$("#application-user-form").serialize(),--}%
    %{--success:function (json) {--}%
    %{--SubmissionLoader.hideFrom();--}%
    %{--if (json.message.type == 1) {--}%
    %{--location.href = "${request.contextPath}/${params.controller}/list";--}%
    %{--} else {--}%
    %{--MessageRenderer.render(json.message)--}%
    %{--}--}%
    %{--}--}%
    %{--});--}%
        var url = "${request.contextPath}/${params.controller}/updateApplicationUserRemotely";
        var data = $("#application-user-form").serialize();
        DocuAjax.json(url, data, function (json) {
            SubmissionLoader.hideFrom();
            if (json.message.type == 1) {
                location.href = "${request.contextPath}/${params.controller}/list";
            } else {
                MessageRenderer.render(json.message)
            }
        });
    }

</script>

<script type="text/javascript">
    function checkAvailability(userName) {
    %{--$.ajax({--}%
    %{--dataType:"json",--}%
    %{--url:"${request.contextPath}/${params.controller}/checkAvailability?username=" + userName,--}%
    %{--success:function (json) {--}%
    %{--if (json.isError == 0) {--}%
    %{--$("#msg-div").html(json.message)--}%
    %{--} else {--}%
    %{--$("#msg-div").html("")--}%
    %{--}--}%
    %{--}--}%
    %{--});--}%
        var url = "${request.contextPath}/${params.controller}/checkAvailability";
        var data = {username:userName};
        DocuAjax.json(url, data, function (json) {
            if (json.isError == 0) {
                $("#msg-div").html(json.message)
            } else {
                $("#msg-div").html("")
            }
        });
    }
</script>
<script type="text/javascript">
    function validatePassword(field, rules, i, options) {
//        var re = /^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%]).{6,20}$/;
        var re = ${passwordPattern}
        if (!re.test(field.val())) {
            return "${CommonConstants.PASSWORD_VALIDATION_MESSAGE}";
        }
    }
</script>
<script type="text/javascript">
    function initialiseFancyBox() {
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
    }

</script>

<script type="text/javascript">
    function saveAuthority() {
        $.fancybox.close();
    %{--$.ajax({--}%
    %{--url:"${request.contextPath}/authority/save",--}%
    %{--data:$("#authority-form").serialize(),--}%
    %{--success:function (json) {--}%
    %{--if (json.indexOf('{"messageTitle":"Authority"') != 0) {--}%
    %{--$("#user-authority-block").html(json)--}%

    %{--}--}%
    %{--else {--}%
    %{--msg = jQuery.parseJSON(json)--}%
    %{--MessageRenderer.render(msg)--}%
    %{--}--}%
    %{--}--}%
    %{--})--}%
        var url = "${request.contextPath}/authority/save";
        var data = $("#authority-form").serialize();
        DocuAjax.html(url, data, function (json) {
            if (json.indexOf('{"messageTitle":"Authority"') != 0) {
                $("#user-authority-block").html(json)

            }
            else {
                msg = jQuery.parseJSON(json)
                MessageRenderer.render(msg)
            }
        });
    }

</script>

<script type="text/javascript">
    function deleteConfirmation() {
        if (confirm("Are you sure you want to delete User ?") == true) {
            $('#domainStatus').val('${CommonConstants.DOMAIN_STATUS_INACTIVE}');
            updateApplicationUserRemotely()
            $('#domainStatus').val('${CommonConstants.DOMAIN_STATUS_ACTIVE}');
        }
        //return true;
        else
            return false;
    }
</script>

<div class="main_container" style="margin-left: 40px;">
<form id="application-user-form">

    <g:hiddenField name="docu-ignore-security" value="1"/>
    <g:hiddenField name="id" value="${applicationUserInstance?.id}"/>
    <g:hiddenField name="version" value="${applicationUserInstance?.version}"/>
    <g:hiddenField name="domainStatus" id="domainStatus" value="${CommonConstants.DOMAIN_STATUS_ACTIVE}"/>

    <div class=content_container>
        <h3>Application User</h3>

        <div class="element_container_big">
            <div class="block-title">
                <div class="element-title input_width320">User Name</div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class="element-input inputContainer value  input_width320">
                    <g:textField name="username"
                                 class="validate[required,maxSize[20],minSize[3] width300"
                                 value="${applicationUserInstance?.username}" onchange="checkAvailability(this.value)"/>
                </div>

                <div class="element-title input_width320" style="color:#ff0000; " id="msg-div"></div>

                <div class="clear"></div>
            </div>
        </div>


        <div id="passwordBlock" class="element_container_big">
            <div class="block-title">
                <div class="element-title input_width320">Password</div>

                <div class="element-title input_width320">Confirm Password</div>

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

                <div class="element-title input_width320">Email</div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class="element-input inputContainer value input_width320">
                    <g:textField name="fullName" value="${applicationUserInstance?.full_name}" class=" width300"/>
                </div>

                <div class="element-input inputContainer value input_width320">
                    <g:textField name="email" value="${applicationUserInstance?.email_address}" class=" width300"/>
                </div>

                <div class="clear"></div>
            </div>

            <div class="clear"></div>
        </div>

    </div>

    <div class=content_container>
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

        <div class="element_container_big">
            <div class="block-title">
                <div class="element-title  input_width320">Application User Type</div>

                <div id="employeeLabel" class="element-title  input_width320">Employee</div>

                <div class="clear"></div>

            </div>

            <div class="block-input">
                <div class="element-input inputContainer value input_width320">
                    <g:select from="${ApplicationUserType.values()}" name="userType" id="userType"
                              value="${applicationUserInstance?.application_user_type}"
                              noSelection="['':'-Select user Type -']" style="width:150px;"
                              onChange="isShowEmployeeDropDown()" class="width300 validate[required]"/>

                </div>

                <div id="employeeDropDown" class="element-input inputContainer value input_width320">
                    <g:select from="${EmployeeInfo.findAllByDomainStatusIdAndApprovalStatus(CommonConstants.DOMAIN_STATUS_ACTIVE,ApprovalStatus.APPROVED) }" optionKey="id" name="userReference" id="userReference"
                              value="${applicationUserInstance?.user_ref_id}"
                              noSelection="['':'-Select user -']" class="width300"/>
                </div>

                <div class="clear"></div>

            </div>

        </div>
        %{--<div id="feature-info-html"></div>--}%
    </div>

    <div class=content_container>
        <h3 style="margin-bottom:0">Application User Role</h3>

        <div class="clear"></div>
        <b><a href="#create-user-role" style="float:right; padding: 2px 5px;" class="user-popUp">Create User Role</a>
        </b>

        <div class="clear"></div>

        <div id="user-authority-block">
            <g:render plugin="sbicloud-commons" template="userAuthority"/>
        </div>

    </div>

    <div class="buttons">
        <g:if test="${applicationUserInstance?.id}">
            <input type="button"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="Update"
                   onclick="updateApplicationUserRemotely()"/>
        </g:if>
        <g:else>
            <input type="button"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="Save"
                   onclick="saveApplicationUserRemotely()"/>
        </g:else>

        <input type="button" id="delete-button"
               class="ui-button ui-widget ui-state-default ui-corner-all"
               value="Delete"
               onclick="deleteConfirmation()"/>
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
                        <g:textField name="role" value=""/>
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
                        <g:select class="validate[funcCall[isDropdownSelected]]" name="moduleId" id="moduleId"
                                  from="${moduleInfoList}" noSelection="[0:'-Select Module-']"
                                  optionKey="id"/>
                    </div>

                    <div class="element-input inputContainer value">
                        <g:textField name="dashboardUrl" id="dashboardUrl" value=""/>
                    </div>

                    <div class="clear"></div>
                </div>

                <div class="clear" style="height:5px;"></div>

                <div class="buttons">
                    <input type="button" id="save-role"
                           class="ui-button ui-widget ui-state-default ui-corner-all"
                           value="Save"
                           onclick="saveAuthority()"/>
                </div>

                <div class="clear" style="height:5px;"></div>
            </div>
        </form>

    </div>
</div>

</div>