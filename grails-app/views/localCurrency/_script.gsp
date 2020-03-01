

<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }

    $("#gFormLocalCurrency").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormLocalCurrency").validationEngine('attach');
    reset_form("#gFormLocalCurrency");
    $("#jqgrid-grid-localCurrency").jqGrid({
      url:'${resource(dir:'localCurrency', file:'list')}',
      datatype: "json",
      colNames:[
        'SL',
        'ID',
        
        'User Created',
        'User Updated',
        'Date Created',
        'Date Updated',

        'Name',
          'Symbol',
          'Note',
          'Is Active'
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:true, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},
        
                
                    {name:'userCreated', index:'userCreated',width:100,align:'left',hidden:true}
                
        ,
                
                    {name:'userUpdated', index:'userUpdated',width:100,align:'left',hidden:true}
                
        ,
                
                    {name:'dateCreated', index:'dateCreated',width:100,align:'left', formatter: 'date', formatoptions: {newformat: 'd-m-Y'},hidden:true}
                
        ,
                
                    {name:'dateUpdated', index:'dateUpdated',width:100,align:'left', formatter: 'date', formatoptions: {newformat: 'd-m-Y'},hidden:true}

        ,

                    {name:'name', index:'name',width:100,align:'left'}
          ,

          {name:'symbol', index:'symbol',width:100,align:'left'}

          ,

                    {name:'note', index:'note',width:100,align:'left'}
                

          ,

          {name:'isActive', index:'isActive',width:100,align:'left'}


      ],
      rowNum:10,
      rowList:[10,20,30],
      pager: '#jqgrid-pager-localCurrency',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"All Local Currency Information",
      autowidth: true,
      autoheight: true,
      scrollOffset: 0,
      altRows: true,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
        executeEditLocalCurrency();
      }
    });
    $("#jqgrid-grid-localCurrency").jqGrid('navGrid', '#jqgrid-pager-localCurrency', {edit:false,add:false,del:false,search:false})
    .navButtonAdd('#jqgrid-pager-localCurrency',{
       caption:"Edit",
       buttonicon:"ui-icon-edit",
       onClickButton: function(){
          executeEditLocalCurrency();
       },
       position:"last"
    })
    .navButtonAdd('#jqgrid-pager-localCurrency',{
       caption:"Delete",
       buttonicon:"ui-icon-del",
       onClickButton: function(){
          deleteAjaxLocalCurrency();
       },
       position:"last"
    });
  });
  function getSelectedLocalCurrencyId()
  {
    var localCurrencyId = null;
    var rowid = $("#jqgrid-grid-localCurrency").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      localCurrencyId = $("#jqgrid-grid-localCurrency").jqGrid('getCell', rowid, 'id');
    }
    if(localCurrencyId == null){
      localCurrencyId = $('#gFormLocalCurrency input[name = id]').val();
    }
    return localCurrencyId;
  }
  function executePreConditionLocalCurrency() {
      // trim field vales before process.
      trim_form();
      if ($("#gFormLocalCurrency").validate().form({onfocusout: false}) == false) {
        return false;
      }
      return true;
  }
  function executeAjaxLocalCurrency() {
    if(!executePreConditionLocalCurrency()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormLocalCurrency input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/save";
    }
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormLocalCurrency").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionLocalCurrency(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
          if(XMLHttpRequest.status = 0){
              MessageRenderer.renderErrorText("Network Problem: Time out");
          }  else{
              MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
          }
      },
      complete:function(){
      },
      dataType:'json'
    });
    return false;
  }
  function executePostConditionLocalCurrency(result) {
      if (result.type == 1) {
        $("#jqgrid-grid-localCurrency").trigger("reloadGrid");
        reset_form('#gFormLocalCurrency');
      }
      MessageRenderer.render(result);
  }
  function deleteAjaxLocalCurrency() {    // Delete record
    var localCurrencyId = getSelectedLocalCurrencyId();
    if(executePreConditionForDeleteLocalCurrency(localCurrencyId))
    {
      $("#dialog").dialog("destroy");
      $("#dialog-confirm-localCurrency").dialog({
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
              data:jQuery("#gFormLocalCurrency").serialize(),
              url: "${resource(dir:'localCurrency', file:'delete')}",
              success: function(message) {
                executePostConditionForDeleteLocalCurrency(message);
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

  function executePreConditionForEditLocalCurrency(localCurrencyId) {
    if(localCurrencyId == null)
    {
      alert("Please select a localCurrency to edit") ;
      return false;
    }
    return true;
  }
  function executeEditLocalCurrency() {
    var localCurrencyId = getSelectedLocalCurrencyId();
    if(executePreConditionForEditLocalCurrency(localCurrencyId))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'localCurrency', file:'edit')}?id="+ localCurrencyId,
        success: function(entity) {
          executePostConditionForEditLocalCurrency(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditLocalCurrency(data) {
      if (data == null) {
        alert('Selected localCurrency might have been deleted by someone');  //Message renderer
      } else {
        showLocalCurrency(data);
      }
  }
  function executePreConditionForDeleteLocalCurrency(localCurrencyId) {
    if(localCurrencyId == null)
    {
      alert("Please select a localCurrency to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteLocalCurrency(data) {
      if (data.type ==1) {
        $("#jqgrid-grid-localCurrency").trigger("reloadGrid");
         reset_form('#gFormLocalCurrency');
      }
      MessageRenderer.render(data)
  }
  function showLocalCurrency(entity) {
    $('#gFormLocalCurrency input[name = id]').val(entity.id);
    $('#gFormLocalCurrency input[name = version]').val(entity.version);
    
    $('#dateUpdated').val(entity.dateUpdated);
    $('#isActive').attr('checked',entity.isActive);
    $('#name').val(entity.name);
    $('#note').val(entity.note);
    $('#symbol').val(entity.symbol);
    $('#create-button-localCurrency').attr('value', 'Update');
    $('#delete-button-localCurrency').show();
  }
  function executeSearchLocalCurrency(fieldName, fieldValue){
    if(executePreConditionForSearchLocalCurrency(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'localCurrency', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchLocalCurrency(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchLocalCurrency(fieldName,fieldValue) {
      // trim field vales before process.
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormLocalCurrency");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchLocalCurrency(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormLocalCurrency"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showLocalCurrency(data);
      }
  }

   function reset_form(formName) {
       $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
           if (this.type == 'hidden') {
               this.value = "";
           } else {
               this.value = this.defaultValue;
           }
       });
       $('input[type=checkbox]').attr('checked',false);
       $(formName +' input[name = create-button]').attr('value', 'Create');
       $(formName +' input[name = delete-button]').hide();
   }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }
</script>