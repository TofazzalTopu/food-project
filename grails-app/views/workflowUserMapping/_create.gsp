

<%@ page import="com.bits.bdfp.inventory.workflow.WorkflowUserMapping" %>
<form name='gFormWorkflowUserMapping' id='gFormWorkflowUserMapping'>
  <g:hiddenField name="id" value="${workflowUserMapping?.id}" />
  <g:hiddenField name="version" value="${workflowUserMapping?.version}" />
  <div id="remote-content-workflowUserMapping"></div>
  <fieldset  class="ui-state-default ui-corner-all">
    <legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Create WorkflowUserMapping</legend>
     <table>
      

            <tr>
              <td><g:message code="workflowUserMapping.workflow.label" default="Workflow" /></td>
                <td><g:select name="workflow.id" from="${com.bits.bdfp.inventory.workflow.Workflow.list()}" optionKey="id" value="${workflowUserMapping?.workflow?.id}"  /></td>
            </tr>


            <tr>
              <td><g:message code="workflowUserMapping.applicationUser.label" default="Application User" /></td>
                <td><g:select name="applicationUser.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" optionValue="username" value="${workflowUserMapping?.applicationUser?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="workflowUserMapping.isActive.label" default="Is Active" /></td>
                <td><g:checkBox name="isActive" value="${workflowUserMapping?.isActive}" /></td>
            </tr>
          


         </table>
  </fieldset>
  <br/>
  <div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxWorkflowUserMapping();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxWorkflowUserMapping();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_form('#gFormWorkflowUserMapping');" value="Cancel"/></span>
  </div>
</form>
