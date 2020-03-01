

<%@ page import="com.bits.bdfp.inventory.demandorder.DemandOrderApprovalWorkflow" %>
<form name='gFormDemandOrderApprovalWorkflow' id='gFormDemandOrderApprovalWorkflow'>
  <g:hiddenField name="id" value="${demandOrderApprovalWorkflow?.id}" />
  <g:hiddenField name="version" value="${demandOrderApprovalWorkflow?.version}" />
  <div id="remote-content-demandOrderApprovalWorkflow"></div>
  <fieldset  class="ui-state-default ui-corner-all">
    <legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Create DemandOrderApprovalWorkflow</legend>
     <table>
      

            <tr>
              <td><g:message code="demandOrderApprovalWorkflow.workflow.label" default="Workflow" /></td>
                <td><g:select name="workflow.id" from="${com.bits.bdfp.inventory.workflow.Workflow.list()}" optionKey="id" value="${demandOrderApprovalWorkflow?.workflow?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="demandOrderApprovalWorkflow.isApprove.label" default="Is Approve" /></td>
                <td><g:checkBox name="isApprove" value="${demandOrderApprovalWorkflow?.isApprove}" /></td>
            </tr>
          

            <tr>
              <td><g:message code="demandOrderApprovalWorkflow.isReject.label" default="Is Reject" /></td>
                <td><g:checkBox name="isReject" value="${demandOrderApprovalWorkflow?.isReject}" /></td>
            </tr>
          

            <tr>
              <td><g:message code="demandOrderApprovalWorkflow.userApprovedReject.label" default="User Approved Reject" /></td>
                <td><g:select name="userApprovedReject.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${demandOrderApprovalWorkflow?.userApprovedReject?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="demandOrderApprovalWorkflow.dateApprovedReject.label" default="Date Approved Reject" /></td>
                <td><g:datePicker name="dateApprovedReject" precision="day" value="${demandOrderApprovalWorkflow?.dateApprovedReject}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="demandOrderApprovalWorkflow.userCreated.label" default="User Created" /></td>
                <td><g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${demandOrderApprovalWorkflow?.userCreated?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="demandOrderApprovalWorkflow.userUpdated.label" default="User Updated" /></td>
                <td><g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${demandOrderApprovalWorkflow?.userUpdated?.id}" noSelection="['null': '']" /></td>
            </tr>
          

            <tr>
              <td><g:message code="demandOrderApprovalWorkflow.dateCreated.label" default="Date Created" /></td>
                <td><g:datePicker name="dateCreated" precision="day" value="${demandOrderApprovalWorkflow?.dateCreated}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="demandOrderApprovalWorkflow.lastUpdated.label" default="Last Updated" /></td>
                <td><g:datePicker name="lastUpdated" precision="day" value="${demandOrderApprovalWorkflow?.lastUpdated}" default="none" noSelection="['': '']" /></td>
            </tr>
          

            <tr>
              <td><g:message code="demandOrderApprovalWorkflow.enterpriseConfiguration.label" default="Enterprise Configuration" /></td>
                <td><g:select name="enterpriseConfiguration.id" from="${com.bits.bdfp.settings.EnterpriseConfiguration.list()}" optionKey="id" value="${demandOrderApprovalWorkflow?.enterpriseConfiguration?.id}"  /></td>
            </tr>
          
         </table>
  </fieldset>
  <br/>
  <div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxDemandOrderApprovalWorkflow();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxDemandOrderApprovalWorkflow();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_form('#gFormDemandOrderApprovalWorkflow');" value="Cancel"/></span>
  </div>
</form>
