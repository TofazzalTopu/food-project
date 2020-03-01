<%@ page import="com.docu.commons.MaritalStatus" %>


<div id="spinnerMaritalStatus" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormMaritalStatus' id='gFormMaritalStatus'>
    <g:hiddenField name="id" value="${maritalStatus?.id}"/>
    <g:hiddenField name="version" value="${maritalStatus?.version}"/>
    <div id="remote-content-maritalStatus"></div>

    <div>

        <div class="element_container_big">
            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='name' class='bold txtalgnrght'><g:message code='maritalStatus.name.label' default='Status Name'/></label>
                <g:textField class="validate[required,funcCall[docuNameSp],maxSize[50],funcCall[uniqueValue]]" name="name"
                             value="${maritalStatus?.name}"/>
            </div>
            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='note' class='bold txtalgnrght hight2x'><g:message code='maritalStatus.description.label'
                                                                                                          default='Description'/></label>
                <g:textArea class="validate[maxSize[255]]" name="description"
                             value="${maritalStatus?.description}"/>
            </div>
        </div>

    </div>

    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-maritalStatus"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxMaritalStatus();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button-maritalStatus"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxMaritalStatus();"/></span>
        <span class="button"><input name="clearFormButtonMaritalStatus"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_form('#gFormMaritalStatus');" value="Cancel"/></span>
    </div>
</form>
