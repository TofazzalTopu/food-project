

<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
       $('#ui-widget-header-text').html('SecondaryDemandOrderDetails')
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }

    $("#gFormSecondaryDemandOrderDetails").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormSecondaryDemandOrderDetails").validationEngine('attach');
    reset_form("#gFormSecondaryDemandOrderDetails");
    $("#jqgrid-grid").jqGrid({
      url:'${resource(dir:'secondaryDemandOrderDetails', file:'list')}',
      datatype: "json",
      colNames:[
        'SL',
        'ID',
        
        'Secondary Demand Order',
        'Finish Product',
        'Rate',
        'Quantity',
        'Amount',
        'User Created',
        'User Updated',
        'Date Created',
        'Last Updated'
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},
        
        {name:'secondaryDemandOrder', index:'secondaryDemandOrder',width:100,align:'left'},
        {name:'finishProduct', index:'finishProduct',width:100,align:'left'},
        {name:'rate', index:'rate',width:100,align:'left'},
        {name:'quantity', index:'quantity',width:100,align:'left'},
        {name:'amount', index:'amount',width:100,align:'left'},
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
      caption:"All SecondaryDemandOrderDetails Information",
      autowidth: true,
      height: 350,
      scrollOffset: 0,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
        executeEditSecondaryDemandOrderDetails();
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
  function getSelectedSecondaryDemandOrderDetailsId()
  {
    var secondaryDemandOrderDetailsId = null;
    var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      secondaryDemandOrderDetailsId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
    }
    if(secondaryDemandOrderDetailsId == null){
      secondaryDemandOrderDetailsId = $('#gFormSecondaryDemandOrderDetails input[name = id]').val();
    }
    return secondaryDemandOrderDetailsId;
  }
  function executePreConditionSecondaryDemandOrderDetails() {
      // trim field vales before process.
      trim_form();
      if ($("#gFormSecondaryDemandOrderDetails").validate().form({onfocusout: false}) == false) {
        return false;
      }
      return true;
  }
  function executeAjaxSecondaryDemandOrderDetails() {
    if(!executePreConditionSecondaryDemandOrderDetails()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormSecondaryDemandOrderDetails input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/create";
    }
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormSecondaryDemandOrderDetails").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionSecondaryDemandOrderDetails(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
          if(XMLHttpRequest.status = 0){
              $("#jqgrid-grid").trigger("reloadGrid");
              reset_form('#gFormSecondaryDemandOrderDetails');
              MessageRenderer.renderErrorText("Network Problem: Time out");
          } else{
              MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
          }
      },
      complete:function(){
      },
      dataType:'json'
    });
    return false;
  }
  function executePostConditionSecondaryDemandOrderDetails(result) {
      if (result.type == 1) {
        $("#jqgrid-grid").trigger("reloadGrid");
        reset_form('#gFormSecondaryDemandOrderDetails');
      }
      MessageRenderer.render(result);
  }
  function deleteAjaxSecondaryDemandOrderDetails() {    // Delete record
    var secondaryDemandOrderDetailsId = getSelectedSecondaryDemandOrderDetailsId();
    if(executePreConditionForDeleteSecondaryDemandOrderDetails(secondaryDemandOrderDetailsId))
    {
      $("#dialog").dialog("destroy");
      $("#dialog-confirm-secondaryDemandOrderDetails").dialog({
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
              data:jQuery("#gFormSecondaryDemandOrderDetails").serialize(),
              url: "${resource(dir:'secondaryDemandOrderDetails', file:'delete')}",
              success: function(message) {
                executePostConditionForDeleteSecondaryDemandOrderDetails(message);
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

  function executePreConditionForEditSecondaryDemandOrderDetails(secondaryDemandOrderDetailsId) {
    if(secondaryDemandOrderDetailsId == null)
    {
      alert("Please select a secondaryDemandOrderDetails to edit") ;
      return false;
    }
    return true;
  }
  function executeEditSecondaryDemandOrderDetails() {
    var secondaryDemandOrderDetailsId = getSelectedSecondaryDemandOrderDetailsId();
    if(executePreConditionForEditSecondaryDemandOrderDetails(secondaryDemandOrderDetailsId))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'secondaryDemandOrderDetails', file:'edit')}?id="+ secondaryDemandOrderDetailsId,
        success: function(entity) {
          executePostConditionForEditSecondaryDemandOrderDetails(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditSecondaryDemandOrderDetails(data) {
      if (data == null) {
        alert('Selected secondaryDemandOrderDetails might have been deleted by someone');  //Message renderer
      } else {
        showSecondaryDemandOrderDetails(data);
      }
  }
  function executePreConditionForDeleteSecondaryDemandOrderDetails(secondaryDemandOrderDetailsId) {
    if(secondaryDemandOrderDetailsId == null)
    {
      alert("Please select a secondaryDemandOrderDetails to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteSecondaryDemandOrderDetails(data) {
      if (data.type ==1) {
        $("#jqgrid-grid").trigger("reloadGrid");
         reset_form('#gFormSecondaryDemandOrderDetails');
      }
      MessageRenderer.render(data)
  }
  function showSecondaryDemandOrderDetails(entity) {
    $('#gFormSecondaryDemandOrderDetails input[name = id]').val(entity.id);
    $('#gFormSecondaryDemandOrderDetails input[name = version]').val(entity.version);
    
    if(entity.secondaryDemandOrder){
       $('#secondaryDemandOrder').val(entity.secondaryDemandOrder.id).attr("selected","selected");
    }
    else{
        $('#secondaryDemandOrder').val(entity.secondaryDemandOrder);
    }
    if(entity.finishProduct){
       $('#finishProduct').val(entity.finishProduct.id).attr("selected","selected");
    }
    else{
        $('#finishProduct').val(entity.finishProduct);
    }
    $('#rate').val(entity.rate);
    $('#quantity').val(entity.quantity);
    $('#amount').val(entity.amount);
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
  function executeSearchSecondaryDemandOrderDetails(fieldName, fieldValue){
    if(executePreConditionForSearchSecondaryDemandOrderDetails(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'secondaryDemandOrderDetails', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchSecondaryDemandOrderDetails(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchSecondaryDemandOrderDetails(fieldName,fieldValue) {
      // trim field vales before process.
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormSecondaryDemandOrderDetails");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchSecondaryDemandOrderDetails(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormSecondaryDemandOrderDetails"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showSecondaryDemandOrderDetails(data);
      }
  }
</script>