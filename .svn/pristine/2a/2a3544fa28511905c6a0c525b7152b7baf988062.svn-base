<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder; com.docu.commons.CommonConstants" %>
%{--<meta name="layout" content="applicationUserLayout"/>--}%
<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>

<g:set var="entityName" value="Show Application User Info"/>
<title>Application User Details</title>

<g:form name="showapplicationuser"
        method="post">
    <div class="main_container">
        <h1>Application User Details</h1>

        <div class=content_container>
            <h3>Application User Name</h3>

            <div class="element_container_big">
                <div class="block-title">
                    <div class="element-title">User Name</div>

                    <div class="element-title">Full Name</div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <g:textField name="id" id="id" value="${applicationUserInstance?.id}" style="display: none;"/>
                    <div class="element-input">${applicationUserInstance?.username}</div>
                    <div class="element-input">${applicationUserInstance?.full_name}</div>

                    <div class="clear"></div>
                </div>

            </div>

        </div>

        <div class=content_container>
            <h3>User Check Property</h3>

            <div class="element_container_big">
                <div class="block-title">

                    <div class="element-title">Enabled</div>

                    <div class="element-title">Account Expired</div>

                    <div class="element-title">Account Locked</div>

                    <div class="element-title">Password Expired</div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <div class="element-input">${applicationUserInstance?.enabled}</div>

                    <div class="element-input">${applicationUserInstance?.account_expired}</div>

                    <div class="element-input">${applicationUserInstance?.account_locked}</div>

                    <div class="element-input">${applicationUserInstance?.password_expired}</div>

                    <div class="clear"></div>
                </div>

            </div>

        </div>

        <div class=content_container>
            <h3>Application User Roles</h3>

            <div class="element_container_big">
                <div class="block-title">

                    <div class="element-title">User Roles</div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">

                    <div class="element-input">${applicationUserAuthorityInstance?.authority}</div>

                    <div class="clear"></div>
                </div>

            </div>

        </div>

        <div class="buttons">
            <input onClick="location.href = '${request.contextPath}/${params.controller}/edit/${applicationUserInstance?.id}'"
                   type="button" name="edit" value="Edit" class="ui-button ui-widget ui-state-default ui-corner-all"/>
        </div>
    </div>

</g:form>