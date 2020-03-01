

<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
       $('#ui-widget-header-text').html('ProductPricingType')
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }

    $("#gFormProductPricingType").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormProductPricingType").validationEngine('attach');
    reset_form("#gFormProductPricingType");
    $("#jqgrid-grid").jqGrid({
      url:'${resource(dir:'productPricingType', file:'list')}',
      datatype: "json",
      colNames:[
        'SL',
        'ID',
        'Enterprise',
        'Name',
        'Priority',
        'Note',
        'Is Active'

      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},
        
        {name:'enterpriseConfiguration', index:'enterpriseConfiguration',width:100,align:'left'},
        {name:'name', index:'name',width:100,align:'left'},
        {name:'priority', index:'priority',width:100,align:'left'},
        {name:'note', index:'note',width:100,align:'left'},
        {name:'isActive', index:'isActive',width:100,align:'left'}

      ],
      rowNum:50,
      rowList:[10,20,30,40,50,60,70,80,90,100,-1],
      pager: '#jqgrid-pager',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"All Product Pricing Type Information",
      autowidth: true,
      height: 350,
      scrollOffset: 0,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
        executeEditProductPricingType();
      }
    });
    $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
      $(".ui-pg-selbox").children().each(function() {
          if ($(this).val() == -1) {
              $(this).html('All')
          }

      });
  });
  function getSelectedProductPricingTypeId()
  {
    var productPricingTypeId = null;
    var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      productPricingTypeId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
    }
    if(productPricingTypeId == null){
      productPricingTypeId = $('#gFormProductPricingType input[name = id]').val();
    }
    return productPricingTypeId;
  }
  function executePreConditionProductPricingType() {
      // trim field vales before process.
      trim_form();
      if (!$("#gFormProductPricingType").validationEngine('validate')) {
//      if ($("#gFormProductPricingType").validate().form({onfocusout: false}) == false) {
        return false;
      }
      return true;
  }
  function executeAjaxProductPricingType() {
    if(!executePreConditionProductPricingType()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormProductPricingType input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/create";
    }
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormProductPricingType").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionProductPricingType(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
          if(XMLHttpRequest.status = 0){
              MessageRenderer.renderErrorText("Network Problem: Time out");
          }else{
              MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
          }
      },
      complete:function(){
      },
      dataType:'json'
    });
    return false;
  }
  function executePostConditionProductPricingType(result) {
      if (result.type == 1) {
        $("#jqgrid-grid").trigger("reloadGrid");
        reset_form('#gFormProductPricingType');
      }
      MessageRenderer.render(result);
  }
  function deleteAjaxProductPricingType() {    // Delete record
    var productPricingTypeId = getSelectedProductPricingTypeId();
    if(executePreConditionForDeleteProductPricingType(productPricingTypeId))
    {
      $("#dialog").dialog("destroy");
      $("#dialog-confirm-productPricingType").dialog({
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
              data:jQuery("#gFormProductPricingType").serialize(),
              url: "${resource(dir:'productPricingType', file:'delete')}",
              success: function(message) {
                executePostConditionForDeleteProductPricingType(message);
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

  function executePreConditionForEditProductPricingType(productPricingTypeId) {
    if(productPricingTypeId == null)
    {
      alert("Please select a productPricingType to edit") ;
      return false;
    }
    return true;
  }
  function executeEditProductPricingType() {
    var productPricingTypeId = getSelectedProductPricingTypeId();
    if(executePreConditionForEditProductPricingType(productPricingTypeId))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'productPricingType', file:'edit')}?id="+ productPricingTypeId,
        success: function(entity) {
          executePostConditionForEditProductPricingType(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditProductPricingType(data) {
      if (data == null) {
        alert('Selected productPricingType might have been deleted by someone');  //Message renderer
      } else {
        showProductPricingType(data);
      }
  }
  function executePreConditionForDeleteProductPricingType(productPricingTypeId) {
    if(productPricingTypeId == null)
    {
      alert("Please select a productPricingType to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteProductPricingType(data) {
      if (data.type ==1) {
        $("#jqgrid-grid").trigger("reloadGrid");
         reset_form('#gFormProductPricingType');
      }
      MessageRenderer.render(data)
  }
  function showProductPricingType(entity) {
    var productPricingType=entity.productPricingType
    var enterpriseConfiguration=entity.enterpriseConfiguration

    $('#gFormProductPricingType input[name = id]').val(productPricingType.id);
    $('#gFormProductPricingType input[name = version]').val(productPricingType.version);

    if (enterpriseConfiguration){
      $('#enterpriseList').setValue(enterpriseConfiguration.name);
      $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
    }

    $('#name').val(productPricingType.name);
    $('#note').val(productPricingType.note);
    $('#isActive').attr('checked',productPricingType.isActive);
    $('#priority').val(productPricingType.priority);
    if(productPricingType.userCreated){
       $('#userCreated').val(productPricingType.userCreated.id).attr("selected","selected");
    }
    else{
        $('#userCreated').val(productPricingType.userCreated);
    }
    if(productPricingType.userUpdated){
       $('#userUpdated').val(productPricingType.userUpdated.id).attr("selected","selected");
    }
    else{
        $('#userUpdated').val(productPricingType.userUpdated);
    }
    $('#dateCreated').val(productPricingType.dateCreated);
    $('#dateUpdated').val(productPricingType.dateUpdated);
    $('#create-button').attr('value', 'Update');
     $('#cancel-button').attr('value', 'Cancel');
    $('#delete-button').show();
  }
  function executeSearchProductPricingType(fieldName, fieldValue){
    if(executePreConditionForSearchProductPricingType(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'productPricingType', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchProductPricingType(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchProductPricingType(fieldName,fieldValue) {
      // trim field vales before process.
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormProductPricingType");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchProductPricingType(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormProductPricingType"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showProductPricingType(data);
      }
  }
</script>