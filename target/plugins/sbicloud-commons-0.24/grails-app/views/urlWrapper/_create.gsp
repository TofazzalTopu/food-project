


<%@ page import="com.docu.security.UrlWrapper" %>


<div id="spinnerUrlWrapper" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>
<form name='gFormUrlWrapper' id='gFormUrlWrapper'>
  <g:hiddenField name="id" value="${urlWrapper?.id}" />
  <g:hiddenField name="version" value="${urlWrapper?.version}" />
  <div id="remote-content-urlWrapper"></div>

  
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='urlWrapper.url.label' default='Url' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:textField name="url" value="${urlWrapper?.url}" />
    </div>
  
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='urlWrapper.urlWrapperName.label' default='Url Wrapper Name' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:textField name="urlWrapperName" value="${urlWrapper?.urlWrapperName}" />
    </div>
  
  </div>
    

  <div class="clear"></div>

    <div class="buttons mar_left10">
        <span class="button">
            <input type="button" name="create-button" id="create-button-urlWrapper"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="${message(code: 'default.button.create.label', default: 'Create')}"
                   onclick="executeAjaxUrlWrapper();"/>
        </span>
        <span class="button">
            <input type='button' name="delete-button" id="delete-button-urlWrapper"
                   class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                   onclick="deleteAjaxUrlWrapper();"/>
        </span>
        <span class="button">
            <input name="clearFormButtonUrlWrapper" class="ui-button ui-widget ui-state-default ui-corner-all"
                   type="button" onclick=" reset_form('#gFormUrlWrapper');" value="Cancel"/>
        </span>
    </div>
</form>
