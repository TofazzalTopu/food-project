

<%@ page import="com.docu.commons.Message; com.docu.security.UserLockStatus" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="userLockStatus.create.label" default="User Lock Status"/></title>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="userLockStatus.create.label" default="User Lock Status"/></h1>
        <h3><g:message code="userLockStatus.Info.label" default="User Lock Status"/></h3>
        <g:render plugin="sbicloud-commons" template='create'/>
        <div class="clear height5"></div>


        <div id="dialog-confirm-userLockStatus" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>

<script type="text/javascript">

       if("${isUserLocked}" == "true"){
       $('#gFormUserLockStatus' +' input[name = create-button-lockUser]').hide();
       $('#gFormUserLockStatus' +' input[name = create-button-unLockUser]').show();
       }
       else{
       $('#gFormUserLockStatus' +' input[name = create-button-lockUser]').show();
       $('#gFormUserLockStatus' +' input[name = create-button-unLockUser]').hide();
       }

    function lockUserSave() {
    SubmissionLoader.showTo();
    var url ="${request.contextPath}/${params.controller}/lockUser";
    var data = $('#bankAccount').serialize();
    DocuAjax.json(url, data, function (json) {
    SubmissionLoader.hideFrom();
    if (json.type == 1) {
    %{--MessageRenderer.render(json);--}%
    $('#gFormUserLockStatus' +' input[name = create-button-lockUser]').hide();
    $('#gFormUserLockStatus' +' input[name = create-button-unLockUser]').show();
    %{--$("#gFormUserLockStatus" + 'input[name = create-button-unLockUser]').attr('value', 'unLockUser');--}%
    }
    MessageRenderer.render(json);
    });
    }
    function unLockUserSave() {
    SubmissionLoader.showTo();
    var url ="${request.contextPath}/${params.controller}/unlockUser";
    var data = $('#bankAccount').serialize();
    var formName =
    DocuAjax.json(url, data, function (json) {
    SubmissionLoader.hideFrom();
    if (json.type == 1) {

    $('#gFormUserLockStatus' +' input[name = create-button-unLockUser]').hide();
    $('#gFormUserLockStatus' +' input[name = create-button-lockUser]').show();
    }
    MessageRenderer.render(json);
    });
    }
</script>