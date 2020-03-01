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


<g:set var="entityName" value="Module Info"/>

<script language="Javascript">
    $(document).ready(function () {
        $('#delete-button').hide();
        $('#delete-button').click(function () {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Delete alert',
                buttons: {
                    'Delete item(s)': function () {
                        $(this).dialog('close');
                        if (!$('#id').val()) {
                            return false;
                        }
                        $.ajax({
                            type: "POST",
                            url: "${resource(dir:'moduleInfo', file:'deleteModuleInfoAjax')}?id="
                                    + $('#id').val(),
                            success: function (data) {
                                if (data.deleted) {
                                    $("#jqgrid-grid").trigger("reloadGrid");
                                    clearForm();
                                }
                            },
                            dataType: 'json'
                        });
                    },
                    Cancel: function () {
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

    function onSaveModuleInfo(data) {
        $("#errorList").html("");
        var result = eval("(" + data + ")");
        if (result.isError) {
            $("#errorList").html("");
            clearErrors()
            var errors = $(result.errors);
            errors.each(function (i) {
                var err = $(this);
                try {
                    if (err.length == 2) {
                        $('#' + err[0]).addClass('error').attr('title', err[1]);
                    } else if (err.length == 1) {
                        $("#errorList").append($("<li></li>").html(err[0]));
                    }
                } catch (e) { /** ignored */
                }
            });
        } else if (result.entity != null) {
            var option = "updated";
            if ($('#create').attr('value') == 'Create') {
                option = "created";
            }
            var divId = "successMessage";
            var message = "One ModuleInfo " + option + " successfully";
            var messageType = "Success";
            showMessage(divId, message, messageType)

            $('#id').val(result.entity.id);
            $('#version').val(result.version);
            $('#create').attr('value', 'Update ModuleInfo');

            clearErrors();
            clearForm();
            $("#jqgrid-grid").trigger("reloadGrid");
            $('#delete-button').hide();
        }
    }

    function clearForm() {
        $('#id').val('');
        $('#version').val('');


        $('#moduleName').val('');
        $('#moduleCode').val('');
        $('#url').val('');
        $('#fileName').val('');
        $('#description').val('');

        $('#create').attr('value', 'Create');
        // $('#moduleInfoForm').validate().resetForm();
        $("#jqgrid-grid").trigger("reloadGrid");
        $('#delete-button').hide();
    }
    function clearErrors() {
        $('.error').each(function () {
            $(this).removeClass('error').attr('title', '');
        });
    }
</script>

<div style="width:800px;">
    <form id="moduleInfoForm">
        <g:hiddenField name="id" value="${moduleInfoInstance?.id}"/>
        <g:hiddenField name="version" value="${moduleInfoInstance?.version}"/>

        <fieldset class='create-form-fieldlist'>
            <legend class='create-form-header'><g:message code="default.create.label" args="[entityName]"/></legend>
            <table class='create-form-table'>

                <tr>
                    <td><label class='create-form-label' for="moduleName"><g:message code="moduleInfo.moduleName.label"
                                                                                     default="Module Name"/></label>
                    </td>
                    <td class='create-form-field'>

                        <g:textField name="moduleName" value="${moduleInfoInstance?.moduleName}"/>
                    </td>
                </tr>

                <tr>
                    <td><label class='create-form-label' for="moduleCode"><g:message code="moduleInfo.moduleCode.label"
                                                                                     default="Module Code"/></label>
                    </td>
                    <td class='create-form-field'>

                        <g:textField name="moduleCode" value="${moduleInfoInstance?.moduleCode}"/>
                    </td>
                </tr>

                <tr>
                    <td><label class='create-form-label' for="url"><g:message code="moduleInfo.url.label"
                                                                              default="URL"/></label></td>
                    <td class='create-form-field'>

                        <g:textField name="url" value="${moduleInfoInstance?.url}"/>
                    </td>
                </tr>

                <tr>
                    <td><label class='create-form-label' for="fileName"><g:message code="moduleInfo.fileName.label"
                                                                                   default="File Name"/></label></td>
                    <td class='create-form-field'>

                        <g:textField name="fileName" value="${moduleInfoInstance?.fileName}"/>
                    </td>
                </tr>

                <tr>
                    <td><label class='create-form-label' for="description"><g:message
                            code="moduleInfo.description.label" default="Description"/></label></td>
                    <td class='create-form-field'>

                        <g:textField name="description" value="${moduleInfoInstance?.description}"/>
                    </td>
                </tr>

            </table>
        </fieldset>

        <div class="buttons">
            <span class="button"><input type="button" name="create" class="save" value="Create"
                                        onclick="onSubmitModuleInfo()"/></span>
            <span class="button"><input name="delete" id="delete-button" class="delete" type='button'
                                        value='Delete ModuleInfo'/></span>
            <span class="button"><input name="clearFormButton" class="clear-form" type='button' onclick='clearForm();'
                                        value='Cancel'/></span>
            <span id="successMessage">&nbsp;</span>
        </div>
    </form>
</div>