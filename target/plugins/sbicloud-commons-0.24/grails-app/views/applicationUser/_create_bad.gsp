<%@ page import="com.docu.security.ApplicationUserType" %>
%{--
  - ****************************************************************
  - Copyright © 2010 Documentatm (TM) Limited. All rights reserved.
  - DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
  - This software is the confidential and proprietary information of
  - Documentatm (TM) Limited ("Confidential Information").
  - You shall not disclose such Confidential Information and shall use
  - it only in accordance with the terms of the license agreement
  - you entered into with Documentatm (TM) Limited.
  - *****************************************************************
  --}%

<meta name="layout" content="applicationUserLayout"/>

<g:set var="entityName" value="${com.docu.common.Tools.formatCamelCase(message(code: 'applicationUser.label', default: 'ApplicationUser'))}" />

      <script language="Javascript">
          $(document).ready(function(){
            $('#delete-button').hide();
            $('#delete-button').click(function(){
                $("#dialog").dialog("destroy");
                $("#dialog-confirm").dialog({
                  resizable: false,
                  height:150,
                  modal: true,
                  title: 'Delete alert',
                  buttons: {
                      'Delete item(s)': function() {
                          $(this).dialog('close');
                          if(!$('#id').val()){
                            return false;
                          }
                          $.ajax({
                              type: "POST",
                              url: "${resource(dir:'applicationUser', file:'deleteApplicationUserAjax')}?id="
                                      + $('#id').val(),
                              success: function(data) {
                                  if (data.deleted) {
                                      $("#jqgrid-grid").trigger("reloadGrid");
                                      clearForm();
                                  }
                              },
                              dataType:'json'
                          });
                        },
                        Cancel: function() {
                            $(this).dialog('close');
                        }
                    }
                }); //end of dialog
            })
          });
          function showHideSpinner(show) {
              if (show) {
                  $('#spinner').show();
              } else {
                  $('#spinner').hide();
              }
          }

          function onSaveApplicationUser(data) {
              $("#errorList").html("");
              var result = eval("(" + data + ")");
              if (result.isError) {
                  $("#errorList").html("");
                  clearErrors()
                  var errors = $(result.errors);
                  errors.each(function (i){
                      var err = $(this);
                      try {
                        if (err.length == 2) {
                          $('#' + err[0]).addClass('error').attr('title', err[1]);
                        } else if (err.length == 1) {
                          $("#errorList").append( $("<li></li>").html(err[0]) );
                        }
                      } catch (e) { /** ignored */ }
                  });
              } else if (result.entity != null) {
                  var option = "updated";
                  if($('#create').attr('value')=='Create'){
                      option = "created";
                  }                  
                  var divId = "successMessage";
                  var message = "One ApplicationUser "+option+" successfully";
                  var messageType = "Success";
                  showMessage(divId, message, messageType)

                  $('#id').val(result.entity.id);
                  $('#version').val(result.version);
                  $('#create').attr('value', 'Update ApplicationUser');                

                  clearErrors();
                  clearForm();
                  $("#jqgrid-grid").trigger("reloadGrid");
                  $('#delete-button').hide();
              }
          }

          function clearForm(){
              $('#id').val('');
              $('#version').val('');

              
              $('#username').val('');
              $('#password').val('');
              $('#fullName').val('');
              $('#enabled').val('');
              $('#accountExpired').val('');
              $('#accountLocked').val('');
              $('#passwordExpired').val('');
              $('#dateCreated').val('');
              $('#lastUpdated').val('');

              $('#create').attr('value', 'Create');
              $('#applicationUserForm').validate().resetForm();
              $("#jqgrid-grid").trigger("reloadGrid");
              $('#delete-button').hide();
          }
          function clearErrors() {
             $('.error').each(function(){
                   $(this).removeClass('error').attr('title', '');
              });
          }
      </script>

<script>
    function isShowEmployeeDropDown() {
        if ($('#userType').attr('value') == 'INTERNAL') {
            alert($('#userType').attr('value'))
        }
    }
</script>


    <g:form onsubmit="return onSubmitApplicationUser()"
        name='applicationUserForm'
        action="saveApplicationUserRemotely"
        method="post">

    	<g:hiddenField name="id" value="${applicationUserInstance?.id}" />
        <g:hiddenField name="version" value="${applicationUserInstance?.version}" />
      
		<fieldset class='create-form-fieldlist'>
			<legend class='create-form-header'><g:message code="default.create.label" args="[entityName]" /></legend>
			<table class='create-form-table'>
				
	                 <tr>
	                     <td><label class='create-form-label' for="username"><g:message code="applicationUser.username.label" default="Username" /></label></td>
	                     <td class='create-form-field'>
                           
                           <g:textField name="username" maxlength="15" value="${applicationUserInstance?.username}" />
                         </td>
	                 </tr>
	             
	                 <tr>
	                     <td><label class='create-form-label' for="password"><g:message code="applicationUser.password.label" default="Password" /></label></td>
	                     <td class='create-form-field'>
                           
                           <g:textField name="password" value="${applicationUserInstance?.password}" />
                         </td>
	                 </tr>
	             
	                 <tr>
	                     <td><label class='create-form-label' for="fullName"><g:message code="applicationUser.fullName.label" default="Full Name" /></label></td>
	                     <td class='create-form-field'>
                           
                           <g:textField name="fullName" value="${applicationUserInstance?.fullName}" />
                         </td>
	                 </tr>
	             
	                 <tr>
	                     <td><label class='create-form-label' for="enabled"><g:message code="applicationUser.enabled.label" default="Enabled" /></label></td>
	                     <td class='create-form-field'>
                           
                           <g:checkBox name="enabled" value="${applicationUserInstance?.enabled}" />
                         </td>
	                 </tr>
	             
	                 <tr>
	                     <td><label class='create-form-label' for="accountExpired"><g:message code="applicationUser.accountExpired.label" default="Account Expired" /></label></td>
	                     <td class='create-form-field'>
                           
                           <g:checkBox name="accountExpired" value="${applicationUserInstance?.accountExpired}" />
                         </td>
	                 </tr>
	             
	                 <tr>
	                     <td><label class='create-form-label' for="accountLocked"><g:message code="applicationUser.accountLocked.label" default="Account Locked" /></label></td>
	                     <td class='create-form-field'>
                           
                           <g:checkBox name="accountLocked" value="${applicationUserInstance?.accountLocked}"  />
                         </td>
	                 </tr>
	             
	                 <tr>
	                     <td><label class='create-form-label' for="passwordExpired"><g:message code="applicationUser.passwordExpired.label" default="Password Expired" /></label></td>
	                     <td class='create-form-field'>
                           
                           <g:checkBox name="passwordExpired" value="${applicationUserInstance?.passwordExpired}" />
                         </td>
	                 </tr>
	             
	                 <tr>
	                     <td><label class='create-form-label' for="dateCreated"><g:message code="applicationUser.dateCreated.label" default="Date Created" /></label></td>
	                     <td class='create-form-field'>
                           <script type='text/javascript'>$(document).ready(function(){$('#dateCreated').mask('99-99-9999',{});});</script>
                           <g:textField name="dateCreated" id="dateCreated" value="${fieldValue(bean: applicationUserInstance, field: 'dateCreated')}" />
                         </td>
	                 </tr>
	             
	                 <tr>
	                     <td><label class='create-form-label' for="lastUpdated"><g:message code="applicationUser.lastUpdated.label" default="Last Updated" /></label></td>
	                     <td class='create-form-field'>
                           <script type='text/javascript'>$(document).ready(function(){$('#lastUpdated').mask('99-99-9999',{});});</script>
                           <g:textField name="lastUpdated" id="lastUpdated" value="${fieldValue(bean: applicationUserInstance, field: 'lastUpdated')}" />
                         </td>
	                 </tr>

                    <tr>
	                     <td><label class='create-form-label' for="userType"><g:message code="User Type" default="User Type" /></label></td>
	                     <td class='create-form-field'>
                           <g:select from="${ApplicationUserType.values()}" name="userType" id="userType"  noSelection="['':'-Select user Type -']" style="width:150px;" onChange= "isShowEmployeeDropDown()" />
                         </td>
	                </tr>


	             
             </table>
		 </fieldset>      
         <div class="buttons">
             <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Update ApplicationUser')}" /></span>
             <span class="button"><input name="delete" id="delete-button" class="delete" type='button' value='Delete ApplicationUser'/></span>
             <span class="button"><input name="clearFormButton" class="clear-form" type='button' onclick='clearForm();' value='Cancel'/></span>
             <span id="successMessage">&nbsp;</span>
         </div>
      </g:form>