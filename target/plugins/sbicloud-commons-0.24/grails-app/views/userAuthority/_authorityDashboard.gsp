<%--
  Created by IntelliJ IDEA.
  User: nasrin
  Date: 9/20/12
  Time: 5:14 PM
  To change this template use File | Settings | File Templates.
--%>
<meta name="layout" content="applicationUserLayout"/>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'login.css')}"/>
<style>
body .ui-layout-pane-center{
    background-image: none!important;
}
#stylized input[type='button']{
    width: 30%;
}
</style>
<script type="text/javascript">
    function setUserRole(){
        $("#authority-role-form").submit();
    }
</script>
<div class="clearfix" id="header" style="border-bottom: 1px solid #AACCEE; margin-bottom: 30px;">
    <a href="#"><img src="/sbicloud/images/layout/logo.jpg" alt="logo" id="logo_img">
    </a>
</div>

<div id="stylized" class="container-ajax-login myform ui-corner-all" style="width:400px; margin: 0 auto;">
    <h1>Choose User Role</h1>

    <div style="padding: 20px 10px 10px;">

            <form id="authority-role-form" method="post" action="${request.contextPath}/officeDashboard/index">
                <div class="element_container_big">
                    <g:hiddenField name="isRoleChecked" value="true"/>
                    <div class="block-title">
                        <div class="element-title">Role</div>

                        <div class="clear"></div>
                    </div>

                    <div class="block-input">
                        <div class="element-input inputContainer value" >
                            <g:select class="validate[funcCall[isDropdownSelected]]" name="userRole" id="userRole"
                                      from="${authorityList}" noSelection="['null':'-Select Role-']"
                                      optionKey="id" style="width: 200px;"/>
                        </div>

                        <div class="clear"></div>
                    </div>

                    <div class="clear" style="height:5px;"></div>

                    <div class="buttons">
                        <input type="button" id="save-role"
                               class="ui-button ui-widget ui-state-default ui-corner-all floatR"
                               value="Save"
                               onclick="setUserRole()"/>
                    </div>

                    <div class="clear" style="height:5px;"></div>
                </div>
            </form>
    </div>
</div>