


<%@ page import="com.docu.commons.Gender" %>


<div id="dialog-confirm-gender" style="display:none">
    <p>Are sure that you want delete the record completely?</p>
</div>

<form name='gFormGender' id='gFormGender'>

    <div class="element-content-holder w800">
        <g:hiddenField name="id" value="${gender?.id}"/>
        <g:hiddenField name="version" value="${gender?.version}"/>
        <g:hiddenField name="creator" value="${gender?.creator}"/>
        <g:hiddenField name="dateCreated" value="${gender?.dateCreated}"/>
        <g:hiddenField name="modifier" value="${gender?.modifier}"/>
        <g:hiddenField name="lastUpdated" value="${gender?.lastUpdated}"/>
        <div id="remote-content-gender"></div>



        

        

            
	<div class="element-content-form-elements">
	<label style="padding-right: 5px;" for='genderType' class='bold txtalgnrght'><g:message code='gender.genderType.label' default='Gender Type' /></label>
	<g:textField name="genderType" id="genderType" class="w-p25" value="${gender?.genderType}" />
	</div>
<div class="element-content-form-elements">
	<label style="padding-right: 5px;" for='description' class='bold txtalgnrght hight2x'><g:message code='gender.description.label' default='Description' /></label>
	<g:textArea name="description" cols="50" rows="2" id="description" class="w-p66" value="${gender?.description}" />
	</div>

        


        <div class="clear"></div>

        <div class="buttons mar_left10">
            <span class="button"><input type="button" name="create-button" id="create-button-gender"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="${message(code: 'default.button.create.label', default: 'Create')}"
                                        onclick="executeAjaxGender();"/></span>
            <span class="button"><input type='button' name="delete-button" id="delete-button-gender"
                                        class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                        onclick="deleteAjaxGender();"/></span>
            <span class="button"><input name="clearFormButtonGender"
                                        class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                        onclick=" reset_form('#gFormGender');" value="Cancel"/></span>
        </div>
    </div>
</form>
