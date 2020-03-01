

<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
       $('#ui-widget-header-text').html('WorkflowUserMapping')
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }

    $("#gFormWorkflowUserMapping").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormWorkflowUserMapping").validationEngine('attach');
    reset_form("#gFormWorkflowUserMapping");
    $("#jqgrid-grid").jqGrid({
      url:'${resource(dir:'workflowUserMapping', file:'list')}',
      datatype: "json",
      colNames:[
        'SL',
        'ID',
        
        'Workflow',
        'Application User',
        'Is Active'
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},
        
        {name:'workflow', index:'workflow',width:100,align:'left'},
        {name:'applicationUser', index:'applicationUser',width:100,align:'left'},
        {name:'isActive', index:'isActive',width:100,align:'left'}
      ],
      rowNum:50,
      rowList:[10,20,30,40,50,60,70,80,90,100,-1],
      pager: '#jqgrid-pager',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"All WorkflowUserMapping Information",
      autowidth: true,
      height: 350,
      scrollOffset: 0,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
        executeEditWorkflowUserMapping();
      }
    });
    $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
//      $(".ui-pg-selbox").children().each(function() {
//          if ($(this).val() == -1) {
//              $(this).html('All')
//          }
//
//      });
  });
  function getSelectedWorkflowUserMappingId()
  {
    var workflowUserMappingId = null;
    var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      workflowUserMappingId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
    }
    if(workflowUserMappingId == null){
      workflowUserMappingId = $('#gFormWorkflowUserMapping input[name = id]').val();
    }
    return workflowUserMappingId;
  }
  function executePreConditionWorkflowUserMapping() {
      // trim field vales before process.
      trim_form();
      if ($("#gFormWorkflowUserMapping").validate().form({onfocusout: false}) == false) {
        return false;
      }
      return true;
  }
  function executeAjaxWorkflowUserMapping() {
    if(!executePreConditionWorkflowUserMapping()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormWorkflowUserMapping input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/create";
    }
      SubmissionLoader.showTo();
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormWorkflowUserMapping").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionWorkflowUserMapping(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
          if(XMLHttpRequest.status = 0){
              $("#jqgrid-grid").trigger("reloadGrid");
              reset_form('#gFormWorkflowUserMapping');
              MessageRenderer.renderErrorText("Network Problem: Time out");
          } else{
              MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
          }
      },
      complete:function(){
          SubmissionLoader.hideFrom();
      },
      dataType:'json'
    });
    return false;
  }
  function executePostConditionWorkflowUserMapping(result) {
      if (result.type == 1) {
        $("#jqgrid-grid").trigger("reloadGrid");
        reset_form('#gFormWorkflowUserMapping');
      }
      MessageRenderer.render(result);
  }
  function deleteAjaxWorkflowUserMapping() {    // Delete record
    var workflowUserMappingId = getSelectedWorkflowUserMappingId();
    if(executePreConditionForDeleteWorkflowUserMapping(workflowUserMappingId))
    {
      $("#dialog").dialog("destroy");
      $("#dialog-confirm-workflowUserMapping").dialog({
        resizable: false,
        height:150,
        modal: true,
        title: 'Delete alert',
        buttons: {
          'Delete item(s)': function() {
            $(this).dialog('close');
            $.ajax({
              type: "POST",
              dataType: "json",
              data:jQuery("#gFormWorkflowUserMapping").serialize(),
              url: "${resource(dir:'workflowUserMapping', file:'delete')}",
              success: function(message) {
                executePostConditionForDeleteWorkflowUserMapping(message);
              }
            });
          },
          Cancel: function() {
            $(this).dialog('close');
          }
        }
      }); //end of dialog
    }
  }

  function executePreConditionForEditWorkflowUserMapping(workflowUserMappingId) {
    if(workflowUserMappingId == null)
    {
      alert("Please select a workflowUserMapping to edit") ;
      return false;
    }
    return true;
  }
  function executeEditWorkflowUserMapping() {
    var workflowUserMappingId = getSelectedWorkflowUserMappingId();
    if(executePreConditionForEditWorkflowUserMapping(workflowUserMappingId))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'workflowUserMapping', file:'edit')}?id="+ workflowUserMappingId,
        success: function(entity) {
          executePostConditionForEditWorkflowUserMapping(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditWorkflowUserMapping(data) {
      if (data == null) {
        alert('Selected workflowUserMapping might have been deleted by someone');  //Message renderer
      } else {
        showWorkflowUserMapping(data);
      }
  }
  function executePreConditionForDeleteWorkflowUserMapping(workflowUserMappingId) {
    if(workflowUserMappingId == null)
    {
      alert("Please select a workflowUserMapping to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteWorkflowUserMapping(data) {
      if (data.type ==1) {
        $("#jqgrid-grid").trigger("reloadGrid");
         reset_form('#gFormWorkflowUserMapping');
      }
      MessageRenderer.render(data)
  }
  function showWorkflowUserMapping(entity) {
    $('#gFormWorkflowUserMapping input[name = id]').val(entity.id);
    $('#gFormWorkflowUserMapping input[name = version]').val(entity.version);
    
    if(entity.workflow){
       $('#workflow').val(entity.workflow.id).attr("selected","selected");
    }
    else{
        $('#workflow').val(entity.workflow);
    }
    if(entity.applicationUser){
       $('#applicationUser').val(entity.applicationUser.id).attr("selected","selected");
    }
    else{
        $('#applicationUser').val(entity.applicationUser);
    }
    $('#isActive').attr('checked',entity.isActive);
    if(entity.userCreated){
       $('#userCreated').val(entity.userCreated.id).attr("selected","selected");
    }
    else{
        $('#userCreated').val(entity.userCreated);
    }
    if(entity.userUpdated){
       $('#userUpdated').val(entity.userUpdated.id).attr("selected","selected");
    }
    else{
        $('#userUpdated').val(entity.userUpdated);
    }
    $('#dateCreated').val(entity.dateCreated);
    $('#dateUpdated').val(entity.dateUpdated);
    $('#create-button').attr('value', 'Update');
     $('#cancel-button').attr('value', 'Cancel');
    $('#delete-button').show();
  }
  function executeSearchWorkflowUserMapping(fieldName, fieldValue){
    if(executePreConditionForSearchWorkflowUserMapping(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'workflowUserMapping', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchWorkflowUserMapping(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchWorkflowUserMapping(fieldName,fieldValue) {
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormWorkflowUserMapping");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchWorkflowUserMapping(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormWorkflowUserMapping"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showWorkflowUserMapping(data);
      }
  }
</script>