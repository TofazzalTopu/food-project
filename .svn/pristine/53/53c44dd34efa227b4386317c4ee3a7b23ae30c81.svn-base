<%@ page import="com.docu.commons.Relationship" %>


<div id="spinnerRelationship" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormRelationship' id='gFormRelationship'>
    <g:hiddenField name="id" value="${relationship?.id}"/>
    <g:hiddenField name="version" value="${relationship?.version}"/>
    <g:hiddenField value="true" name="isRelative" id="isRelative"/>
    <div id="remote-content-relationship"></div>

    <div>
        <div class="element_container_big">
            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='name' class='bold txtalgnrght'><g:message code='relationship.name.label' default='Relationship Name'/></label>
                <g:textField class="validate[required,funcCall[docuNameSp]]"  name="name"  maxlength="50" id="name"
                             value="${relationship?.name}"/>
            </div>
            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='description' class='bold txtalgnrght hight2x'><g:message code='relationship.description.label'
                                                                                                          default='Description'/></label>
                <g:textArea class="validate[maxSize[255]]" name="description" cols="50" rows="2" id="description"
                            value="${relationship?.description}"/>
            </div>
        </div>

    </div>

    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-relationship"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxRelationship();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button-relationship"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxRelationship();"/></span>
        <span class="button"><input name="clearFormButtonRelationship"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_form('#gFormRelationship');" value="Cancel"/></span>
    </div>
</form>
