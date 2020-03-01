

<%@ page import="com.bits.bdfp.settings.EnterpriseConfiguration" %>

<div id="spinnerBloodGroup" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>


<form name='gFormEnterpriseConfiguration' id='gFormEnterpriseConfiguration'>
  <g:hiddenField name="id" value="${enterpriseConfiguration?.id}" />
  <g:hiddenField name="version" value="${enterpriseConfiguration?.version}" />
  <div id="remote-content-enterpriseConfiguration"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='enterpriseConfiguration.code.label' default='Code' />
            <span class="mendatory_field"> * </span>
        </label>

        <div  class='element-input inputContainer'>
            <g:textField name="code" value="${enterpriseConfiguration?.code}" class="validate[required]"/>

        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='enterpriseConfiguration.name.label' default='Name' />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="name" value="${enterpriseConfiguration?.name}" class="validate[required]"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='enterpriseConfiguration.name.label' default='No. Of Layers' />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textArea name="noOfLayers" value="${enterpriseConfiguration?.noOfLayers}" class="validate[required]"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='enterpriseConfiguration.name.label' default='Account Code Length' />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textArea name="codeLength" value="${enterpriseConfiguration?.codeLength}" class="validate[required]"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='enterpriseConfiguration.name.label' default='Note' />
        </label>

        <div class='element-input inputContainer'>
            <g:textArea name="note" value="${enterpriseConfiguration?.note}" />
        </div>

    </div>
    <div class="clear"></div>

    <div class="buttons mar_left10">
        <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxEnterpriseConfiguration();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxEnterpriseConfiguration();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_form('#gFormEnterpriseConfiguration');" value="Cancel"/></span>

    </div>
    %{--<div class="element-input inputContainer width215">--}%
        %{--<input type="text" id="query" class="width173"/>--}%

        %{--<span id="search-btn-employee-register-id" title="" role="button"--}%
              %{--class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR" onclick="executeSearch()">--}%
            %{--<span class="ui-button-icon-primary ui-icon ui-icon-search"></span>--}%
            %{--<span class="ui-button-text"></span>--}%
        %{--</span>--}%
    %{--</div>--}%
    %{--<div class="clear"></div>--}%

</form>
