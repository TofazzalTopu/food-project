
<script type="text/javascript" language="Javascript">

  $(document).ready(function() {

      changePageTitle("<g:message code="addressType.create.title"/>");
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");

    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }

    $("#gFormAddressType").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });

    $("#gFormAddressType").validationEngine('attach');
    reset_form("#gFormAddressType");

      $("#jqgrid-grid-addressType").jqGrid({
          url:'${resource(dir:'addressType', file:'listJSON')}',
          datatype: "json",
          colNames:[
              'SL',
              'ID',
              'Address Type',
              'Description'
          ],
          colModel:[
              {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
              {name:'id',index:'id', width:0, hidden:true},
              {name:'addressTypeName', index:'addressTypeName',width:100,align:'left'},
              {name:'description', index:'description',width:100,align:'left'}
          ],
          rowNum:10,
          rowList:[10,20,30],
          pager: '#jqgrid-pager-addressType',
          sortname: 'id',
          viewrecords: true,
          sortorder: "desc",
          caption:"Address Type",
          autowidth: true,
          autoheight: true,
          scrollOffset: 0,
          altRows: true,
          loadComplete: function() {
          },
          onSelectRow: function(rowid, status) {
              executeEditAddressType(rowid);
          }
      });
      $("#jqgrid-grid-addressType").jqGrid('navGrid', '#jqgrid-pager-addressType', { add:false, edit:false, del:false, search:true, refresh:true });


  });

  function getSelectedAddressTypeId()
  {
    var addressTypeId = null;
    var rowid = $("#jqgrid-grid-addressType").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      addressTypeId = $("#jqgrid-grid-addressType").jqGrid('getCell', rowid, 'id');
    }
    if(addressTypeId == null){
      addressTypeId = $('#gFormAddressType input[name = id]').val();
    }
    return addressTypeId;
  }
  function executePreConditionAddressType() {
      // trim field vales before process.
      //trim_form();
      //if ($("#gFormAddressType").validate().form({onfocusout: false}) == false) {
      //  return false;
      //}
      return true;
  }
  function executeAjaxAddressType() {
    if(!executePreConditionAddressType()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormAddressType input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/save";
    }
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormAddressType").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionAddressType(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
      },
      complete:function(){
      },
      dataType:'json'
    });
    return false;
  }
  function executePostConditionAddressType(result) {
      if (result.type == 1) {
        $("#jqgrid-grid-addressType").trigger("reloadGrid");
        reset_form('#gFormAddressType');
      }
      MessageRenderer.render(result);
  }
  function deleteAjaxAddressType() {    // Delete record
    var addressTypeId = getSelectedAddressTypeId();
    if(executePreConditionForDeleteAddressType(addressTypeId))
    {
      $( "#dialog:ui-dialog" ).dialog( "destroy" );
      $("#dialog-confirm-addressType").dialog({
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
              data:jQuery("#gFormAddressType").serialize(),
              url: "${resource(dir:'addressType', file:'delete')}",
              success: function(message) {
                executePostConditionForDeleteAddressType(message);
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

  function executePreConditionForEditAddressType(addressTypeId) {
    if(addressTypeId == null)
    {
      alert("Please select a addressType to edit") ;
      return false;
    }
    return true;
  }
  function executeEditAddressType(id) {
    //var addressTypeId = getSelectedAddressTypeId();
    var addressTypeId = id;

    if(executePreConditionForEditAddressType(addressTypeId))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'addressType', file:'editJSON')}?id="+ addressTypeId,
        success: function(entity) {
          executePostConditionForEditAddressType(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditAddressType(data) {
      if (data == null) {
        alert('Selected addressType might have been deleted by someone');  //Message renderer
      } else {
        showAddressType(data);
      }
  }
  function executePreConditionForDeleteAddressType(addressTypeId) {
    if(addressTypeId == null)
    {
      alert("Please select a addressType to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteAddressType(data) {
      if (data.type ==1) {
        $("#jqgrid-grid-addressType").trigger("reloadGrid");
         reset_form('#gFormAddressType');
      }
      MessageRenderer.render(data)
  }
  function showAddressType(entity) {
    $('#gFormAddressType input[name = id]').val(entity.id);
    $('#gFormAddressType input[name = version]').val(entity.version);
    $('#gFormAddressType input[name = creator]').val(entity.creator);
    $('#gFormAddressType input[name = dateCreated]').val(entity.dateCreated);
    $('#gFormAddressType input[name = modifier]').val(entity.modifier);
    $('#gFormAddressType input[name = lastUpdated]').val(entity.lastUpdated);

    
    $('#addressTypeName').val(entity.addressTypeName);
    $('#description').val(entity.description);
    $('#create-button-addressType').attr('value', 'Update');
    $('#delete-button-addressType').show();
  }
  function executeSearchAddressType(fieldName, fieldValue){
    if(executePreConditionForSearchAddressType(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'addressType', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchAddressType(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchAddressType(fieldName,fieldValue) {
      // trim field vales before process.
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormAddressType");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchAddressType(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormAddressType"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showAddressType(data);
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