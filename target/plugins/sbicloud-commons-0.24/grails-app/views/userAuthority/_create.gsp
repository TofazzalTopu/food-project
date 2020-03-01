<%@ page import="com.docu.security.UserAuthority" %>


<div id="spinnerAuthority" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormAuthority' id='gFormAuthority'>
    <g:hiddenField name="id" value="${authority?.id}"/>
    <g:hiddenField name="version" value="${authority?.version}"/>
    <div id="remote-content-authority"></div>

    <div>

        <div class="element_container_big width1000">
            <div class="block-title">
                <div class='element-title' style="display: none;">User Authority</div>

                <div class='element-title'>Role</div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input inputContainer' style="display: none;"><g:textField name="authority" value="${authority?.authority}"/></div>

                <div class='element-input inputContainer '><g:textField  name="role" value="${authority?.role}" class="validate[required] width400"/></div>

                <div class="clear"></div>
            </div>
        </div>

    </div>
    <div class="clear"></div>
    <div class="content_container">
        <h3> User Authority Dashboard Mapping</h3>
        <g:hiddenField name="docu-ignore-security" value="1"/>
        <div class="element_container_big width1000">

            <div class="block-title">
                <div class="element-title width200">Module</div>

                <div class="element-title width200">Dashboard Url</div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class="element-input inputContainer value width200" >
                    <g:select class="validate[required,funcCall[isDropdownSelected]] width200" name="moduleId" id="moduleId"
                              from="${moduleInfoList}" noSelection="['null':'-Select Module-']"
                              optionKey="id"/>
                </div>

                <div class="element-input inputContainer value width200">
                    <g:textField class="width200" name="dashboardUrl" id="dashboardUrl" value=""/>
                </div>

                <div class="clear"></div>
            </div>

            <div class="clear" style="height:5px;"></div>
        </div>
    </div>

    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-authority"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxAuthority();"/></span>
        %{--<span class="button"><input type='button' name="delete-button" id="delete-button-authority"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'--}%
                                    %{--onclick="deleteAjaxAuthority();"/></span>--}%
        <span class="button"><input name="clearFormButtonAuthority"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_form('#gFormAuthority');" value="Cancel"/></span>
    </div>
</form>
