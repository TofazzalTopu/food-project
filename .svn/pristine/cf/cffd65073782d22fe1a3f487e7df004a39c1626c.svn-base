

<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
    $("#query").val('');
       $('#ui-widget-header-text').html('Enterprise')
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }

    $("#gFormEnterpriseConfiguration").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormEnterpriseConfiguration").validationEngine('attach');
    reset_form("#gFormEnterpriseConfiguration");
    $("#jqgrid-grid").jqGrid({
      url:'${request.contextPath}/${params.controller}/list',
      datatype: "json",
      colNames:[
        'SL',
        'ID',

        'Code',
        'Name',
        'Note'

      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},

        {name:'code', index:'code',width:100,align:'left'},
        {name:'name', index:'name',width:100,align:'left'},
        {name:'note', index:'note',width:100,align:'left'}

      ],
      rowNum:50,
      rowList:[10,20,30,40,50,60,70,80,90,100,-1],
      pager: '#jqgrid-pager',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"All Enterprise Information",
      autowidth: true,
      height: 350,
      scrollOffset: 0,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
        executeEditEnterpriseConfiguration();
      }
    });
    $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false})

//    $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
    $(".ui-pg-selbox").children().each(function() {
      if ($(this).val() == -1) {
        $(this).html('All')
      }

    });
  });
  function getSelectedEnterpriseConfigurationId()
  {
    var enterpriseConfigurationId = null;
    var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      enterpriseConfigurationId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
    }
    if(enterpriseConfigurationId == null){
      enterpriseConfigurationId = $('#gFormEnterpriseConfiguration input[name = id]').val();
    }
    return enterpriseConfigurationId;
  }
  function executePreConditionEnterpriseConfiguration() {
      // trim field vales before process.
      trim_form();
      if ($("#gFormEnterpriseConfiguration").validate().form({onfocusout: false}) == false) {
        return false;
      }
      return true;
  }
  function executeAjaxEnterpriseConfiguration() {
    if(!executePreConditionEnterpriseConfiguration()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormEnterpriseConfiguration input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/create";
    }
      SubmissionLoader.showTo();
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormEnterpriseConfiguration").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionEnterpriseConfiguration(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
          if(XMLHttpRequest.status = 0){
              $("#jqgrid-grid").trigger("reloadGrid");
              reset_form('#gFormEnterpriseConfiguration');
              MessageRenderer.renderErrorText("Network Problem: Time out");
          }  else{
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
  function executePostConditionEnterpriseConfiguration(result) {
      if (result.type == 1) {
        $("#jqgrid-grid").trigger("reloadGrid");
        reset_form('#gFormEnterpriseConfiguration');
      }
      MessageRenderer.render(result);
  }
  function deleteAjaxEnterpriseConfiguration() {    // Delete record
    var enterpriseConfigurationId = getSelectedEnterpriseConfigurationId();
    if(executePreConditionForDeleteEnterpriseConfiguration(enterpriseConfigurationId))
    {
      $("#dialog").dialog("destroy");
      $("#dialog-confirm-enterpriseConfiguration").dialog({
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
              data:jQuery("#gFormEnterpriseConfiguration").serialize(),
              url: "${resource(dir:'enterpriseConfiguration', file:'delete')}",
              success: function(message) {
                executePostConditionForDeleteEnterpriseConfiguration(message);
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

  function executePreConditionForEditEnterpriseConfiguration(enterpriseConfigurationId) {
    if(enterpriseConfigurationId == null)
    {
      alert("Please select a enterpriseConfiguration to edit") ;
      return false;
    }
    return true;
  }
  function executeEditEnterpriseConfiguration() {
    var enterpriseConfigurationId = getSelectedEnterpriseConfigurationId();
    if(executePreConditionForEditEnterpriseConfiguration(enterpriseConfigurationId))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'enterpriseConfiguration', file:'edit')}?id="+ enterpriseConfigurationId,
        success: function(entity) {
          executePostConditionForEditEnterpriseConfiguration(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditEnterpriseConfiguration(data) {
      if (data == null) {
        alert('Selected enterpriseConfiguration might have been deleted by someone');  //Message renderer
      } else {
        showEnterpriseConfiguration(data);
      }
  }
  function executePreConditionForDeleteEnterpriseConfiguration(enterpriseConfigurationId) {
    if(enterpriseConfigurationId == null)
    {
      alert("Please select a enterpriseConfiguration to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteEnterpriseConfiguration(data) {
      if (data.type ==1) {
        $("#jqgrid-grid").trigger("reloadGrid");
         reset_form('#gFormEnterpriseConfiguration');
      }
      MessageRenderer.render(data)
  }
  function showEnterpriseConfiguration(entity) {
    $('#gFormEnterpriseConfiguration input[name = id]').val(entity.id);
    $('#gFormEnterpriseConfiguration input[name = version]').val(entity.version);

    $('#code').val(entity.code);
    $("#code").attr('readonly', 'readonly');
    $('#name').val(entity.name);
    $('#note').val(entity.note);

    $('#create-button').attr('value', 'Update');
     $('#cancel-button').attr('value', 'Cancel');
    $('#delete-button').show();
  }
  function executeSearchEnterpriseConfiguration(fieldName, fieldValue){
    if(executePreConditionForSearchEnterpriseConfiguration(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'enterpriseConfiguration', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchEnterpriseConfiguration(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchEnterpriseConfiguration(fieldName,fieldValue) {
      // trim field vales before process.
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormEnterpriseConfiguration");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchEnterpriseConfiguration(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormEnterpriseConfiguration"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showEnterpriseConfiguration(data);
      }


  }


  function executeSearch() {
//      if (!$('#gFormEnterpriseConfiguration').validationEngine('validate')) {
//        return false;
//      }
    if ($('#query').val()) {
      $("#jqgrid-grid").setGridParam({url: '${resource(dir:'enterpriseConfiguration', file:'list')}?query=' + $("#query").val()});
      $("#jqgrid-grid").trigger("reloadGrid");
      MessageRenderer.hideHeaderMessage()
    }
    else {
      MessageRenderer.showHeaderMessage("Please type something for searching",0)
    }

  }
  function resetSearch() {
    $("#query").val('');
    $("#jqgrid-grid").setGridParam({url: '${resource(dir:'enterpriseConfiguration', file:'list')}'});
    $("#jqgrid-grid").trigger("reloadGrid");
  }
</script>