


<%@ page import="com.docu.commons.Religion" %>


<div id="dialog-confirm-religion" style="display:none">
    <p>Are sure that you want delete the record completely?</p>
</div>

<form name='gFormReligion' id='gFormReligion'>

    <div class="element-content-holder w800">
        <h1>Religion</h1>
        <g:hiddenField name="id" value="${religion?.id}"/>
        <g:hiddenField name="version" value="${religion?.version}"/>
        <g:hiddenField name="creator" value="${religion?.creator}"/>
        <g:hiddenField name="dateCreated" value="${religion?.dateCreated}"/>
        <g:hiddenField name="modifier" value="${religion?.modifier}"/>
        <g:hiddenField name="lastUpdated" value="${religion?.lastUpdated}"/>
        <div id="remote-content-religion"></div>



        

        

            
<div class="element-content-form-elements">
	<label for='religionName' class='bold txtalgnrght'><g:message code='religion.religionName.label' default='Religion Name' /></label>
	<g:textField name="religionName" id="religionName" class="w-p25" value="${religion?.religionName}" />
	</div>
<div class="element-content-form-elements">
	<label for='description' class='bold txtalgnrght dblhght'><g:message code='religion.description.label' default='Description' /></label>
	<g:textArea name="description" cols="50" rows="2" id="description" class="w-p66" value="${religion?.description}" />
	</div>

        


        <div class="clear"></div>

        <div class="buttons mar_left10">
            <span class="button"><input type="button" name="create-button" id="create-button-religion"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="${message(code: 'default.button.create.label', default: 'Create')}"
                                        onclick="executeAjaxReligion();"/></span>
            <span class="button"><input type='button' name="delete-button" id="delete-button-religion"
                                        class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                        onclick="deleteAjaxReligion();"/></span>
            <span class="button"><input name="clearFormButtonReligion"
                                        class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                        onclick=" reset_form('#gFormReligion');" value="Cancel"/></span>
        </div>
    </div>
</form>
