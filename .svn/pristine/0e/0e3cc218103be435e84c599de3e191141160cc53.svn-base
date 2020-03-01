

<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
       $('#ui-widget-header-text').html('TerritorySubArea')
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }

    $("#gFormTerritorySubArea").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormTerritorySubArea").validationEngine('attach');
    reset_form("#gFormTerritorySubArea");
    $("#jqgrid-grid").jqGrid({
      url:'${resource(dir:'territorySubArea', file:'list')}',
      datatype: "json",
      colNames:[
        'SL',
        'ID',
        
        'Territory Configuration',
        'Para Or Locality',
        'Road',
        'User Created',
        'User Updated',
        'Date Created',
        'Last Updated'
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},
        
        {name:'territoryConfiguration', index:'territoryConfiguration',width:100,align:'left'},
        {name:'paraOrLocality', index:'paraOrLocality',width:100,align:'left'},
        {name:'road', index:'road',width:100,align:'left'},
        {name:'userCreated', index:'userCreated',width:100,align:'left'},
        {name:'userUpdated', index:'userUpdated',width:100,align:'left'},
        {name:'dateCreated', index:'dateCreated',width:100,align:'left'},
        {name:'lastUpdated', index:'lastUpdated',width:100,align:'left'}
      ],
      rowNum:50,
      rowList:[10,20,30,40,50,60,70,80,90,100,-1],
      pager: '#jqgrid-pager',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"All TerritorySubArea Information",
      autowidth: true,
      height: 350,
      scrollOffset: 0,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
        executeEditTerritorySubArea();
      }
    });
    $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false});
     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
      $(".ui-pg-selbox").children().each(function() {
          if ($(this).val() == -1) {
              $(this).html('All')
          }

      });
  });
  function getSelectedTerritorySubAreaId()
  {
    var territorySubAreaId = null;
    var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      territorySubAreaId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
    }
    if(territorySubAreaId == null){
      territorySubAreaId = $('#gFormTerritorySubArea input[name = id]').val();
    }
    return territorySubAreaId;
  }
  function executePreConditionTerritorySubArea() {
      // trim field vales before process.
      trim_form();
      if ($("#gFormTerritorySubArea").validate().form({onfocusout: false}) == false) {
        return false;
      }
      return true;
  }
  function executeAjaxTerritorySubArea() {
    if(!executePreConditionTerritorySubArea()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormTerritorySubArea input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/create";
    }
    SubmissionLoader.showTo();
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormTerritorySubArea").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionTerritorySubArea(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
          if(XMLHttpRequest.status = 0){
              $("#jqgrid-grid").trigger("reloadGrid");
              reset_form('#gFormTerritorySubArea');
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
  function executePostConditionTerritorySubArea(result) {
      if (result.type == 1) {
        $("#jqgrid-grid").trigger("reloadGrid");
        reset_form('#gFormTerritorySubArea');
      }
      MessageRenderer.render(result);
  }
  function deleteAjaxTerritorySubArea() {    // Delete record
    var territorySubAreaId = getSelectedTerritorySubAreaId();
    if(executePreConditionForDeleteTerritorySubArea(territorySubAreaId))
    {
      $("#dialog").dialog("destroy");
      $("#dialog-confirm-territorySubArea").dialog({
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
              data:jQuery("#gFormTerritorySubArea").serialize(),
              url: "${resource(dir:'territorySubArea', file:'delete')}",
              success: function(message) {
                executePostConditionForDeleteTerritorySubArea(message);
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

  function executePreConditionForEditTerritorySubArea(territorySubAreaId) {
    if(territorySubAreaId == null)
    {
      alert("Please select a territorySubArea to edit") ;
      return false;
    }
    return true;
  }
  function executeEditTerritorySubArea() {
    var territorySubAreaId = getSelectedTerritorySubAreaId();
    if(executePreConditionForEditTerritorySubArea(territorySubAreaId))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'territorySubArea', file:'edit')}?id="+ territorySubAreaId,
        success: function(entity) {
          executePostConditionForEditTerritorySubArea(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditTerritorySubArea(data) {
      if (data == null) {
        alert('Selected territorySubArea might have been deleted by someone');  //Message renderer
      } else {
        showTerritorySubArea(data);
      }
  }
  function executePreConditionForDeleteTerritorySubArea(territorySubAreaId) {
    if(territorySubAreaId == null)
    {
      alert("Please select a territorySubArea to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteTerritorySubArea(data) {
      if (data.type ==1) {
        $("#jqgrid-grid").trigger("reloadGrid");
         reset_form('#gFormTerritorySubArea');
      }
      MessageRenderer.render(data)
  }
  function showTerritorySubArea(entity) {
    $('#gFormTerritorySubArea input[name = id]').val(entity.id);
    $('#gFormTerritorySubArea input[name = version]').val(entity.version);
    
    if(entity.territoryConfiguration){
       $('#territoryConfiguration').val(entity.territoryConfiguration.id).attr("selected","selected");
    }
    else{
        $('#territoryConfiguration').val(entity.territoryConfiguration);
    }
    $('#paraOrLocality').val(entity.paraOrLocality);
    $('#road').val(entity.road);
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
    $('#lastUpdated').val(entity.lastUpdated);
    $('#create-button').attr('value', 'Update');
     $('#cancel-button').attr('value', 'Cancel');
    $('#delete-button').show();
  }
  function executeSearchTerritorySubArea(fieldName, fieldValue){
    if(executePreConditionForSearchTerritorySubArea(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'territorySubArea', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchTerritorySubArea(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchTerritorySubArea(fieldName,fieldValue) {
      // trim field vales before process.
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormTerritorySubArea");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchTerritorySubArea(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormTerritorySubArea"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showTerritorySubArea(data);
      }
  }
</script>