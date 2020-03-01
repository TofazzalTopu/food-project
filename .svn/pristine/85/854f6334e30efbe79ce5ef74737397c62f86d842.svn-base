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

%{----}%

<g:set var="entityName" value="${com.docu.common.Tools.formatCamelCase(message(code: 'applicationUserAuthority.label', default: 'ApplicationUserAuthority'))}" />

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
                              url: "${resource(dir:'applicationUserAuthority', file:'deleteApplicationUserAuthorityAjax')}?id="
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

          function onSaveApplicationUserAuthority(data) {
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
                  var message = "One ApplicationUserAuthority "+option+" successfully";
                  var messageType = "Success";
                  showMessage(divId, message, messageType)

                  $('#id').val(result.entity.id);
                  $('#version').val(result.version);
                  $('#create').attr('value', 'Update ApplicationUserAuthority');                

                  clearErrors();
                  clearForm();
                  $("#jqgrid-grid").trigger("reloadGrid");
                  $('#delete-button').hide();
              }
          }

          function clearForm(){
              $('#id').val('');
              $('#version').val('');

              
              $('#applicationUser').val('');
              $('#authority').val('');

              $('#create').attr('value', 'Create');
              $('#applicationUserAuthorityForm').validate().resetForm();
              $("#jqgrid-grid").trigger("reloadGrid");
              $('#delete-button').hide();
          }
          function clearErrors() {
             $('.error').each(function(){
                   $(this).removeClass('error').attr('title', '');
              });
          }
      </script>

    <g:form onsubmit="return onSubmitApplicationUserAuthority()"
        name='applicationUserAuthorityForm'
        action="saveApplicationUserAuthorityRemotely"
        method="post">

    	<g:hiddenField name="id" value="${applicationUserAuthorityInstance?.id}" />
        <g:hiddenField name="version" value="${applicationUserAuthorityInstance?.version}" />
      
		<fieldset class='create-form-fieldlist'>
			<legend class='create-form-header'><g:message code="default.create.label" args="[entityName]" /></legend>
			<table class='create-form-table'>
				
	                 <tr>
	                     <td><label class='create-form-label' for="applicationUser"><g:message code="applicationUserAuthority.applicationUser.label" default="Application User" /></label></td>
	                     <td class='create-form-field'>
                           
                           <g:select name="applicationUser.id" id="applicationUser" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${applicationUserAuthorityInstance?.applicationUser?.id}"  />
                         </td>
	                 </tr>
	             
	                 <tr>
	                     <td><label class='create-form-label' for="authority"><g:message code="applicationUserAuthority.authority.label" default="Authority" /></label></td>
	                     <td class='create-form-field'>
                           
                           <g:select name="authority.id" id="authority" from="${com.docu.security.UserAuthority.list()}" optionKey="id" value="${applicationUserAuthorityInstance?.authority?.id}"  />
                         </td>
	                 </tr>
	             
             </table>
		 </fieldset>      
         <div class="buttons">
             <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Update ApplicationUserAuthority')}" /></span>
             <span class="button"><input name="delete" id="delete-button" class="delete" type='button' value='Delete ApplicationUserAuthority'/></span>
             <span class="button"><input name="clearFormButton" class="clear-form" type='button' onclick='clearForm();' value='Cancel'/></span>
             <span id="successMessage">&nbsp;</span>
         </div>
      </g:form>