

<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
      changePageTitle("<g:message code="gender.create.title"/>");
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }

    $("#gFormGender").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormGender").validationEngine('attach');
    reset_form("#gFormGender");

    $('.date').mask('99-99-9999',{});
    $('.date').datePicker({startDate:'01/01/1900'});
  });
  function getSelectedGenderId()
  {
    var genderId = null;
    var rowid = $("#jqgrid-grid-gender").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      genderId = $("#jqgrid-grid-gender").jqGrid('getCell', rowid, 'id');
    }
    if(genderId == null){
      genderId = $('#gFormGender input[name = id]').val();
    }
    return genderId;
  }
  function executePreConditionGender() {
      // trim field vales before process.
      //trim_form();
      //if ($("#gFormGender").validate().form({onfocusout: false}) == false) {
      //  return false;
      //}
      return true;
  }
  function executeAjaxGender() {
    if(!executePreConditionGender()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormGender input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/save";
    }
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormGender").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionGender(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
      },
      complete:function(){
      },
      dataType:'json'
    });
    return false;
  }
  function executePostConditionGender(result) {
      if (result.type == 1) {
        $("#jqgrid-grid-gender").trigger("reloadGrid");
        reset_form('#gFormGender');
      }
      MessageRenderer.render(result);
  }
  function deleteAjaxGender() {    // Delete record
    var genderId = getSelectedGenderId();
    if(executePreConditionForDeleteGender(genderId))
    {
      $( "#dialog:ui-dialog" ).dialog( "destroy" );
      $("#dialog-confirm-gender").dialog({
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
              data:jQuery("#gFormGender").serialize(),
              url: "${resource(dir:'gender', file:'delete')}",
              success: function(message) {
                executePostConditionForDeleteGender(message);
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

  function executePreConditionForEditGender(genderId) {
    if(genderId == null)
    {
      alert("Please select a gender to edit") ;
      return false;
    }
    return true;
  }
  function executeEditGender(id) {
    //var genderId = getSelectedGenderId();
    var genderId = id;

    if(executePreConditionForEditGender(genderId))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'gender', file:'editJSON')}?id="+ genderId,
        success: function(entity) {
          executePostConditionForEditGender(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditGender(data) {
      if (data == null) {
        alert('Selected gender might have been deleted by someone');  //Message renderer
      } else {
        showGender(data);
      }
  }
  function executePreConditionForDeleteGender(genderId) {
    if(genderId == null)
    {
      alert("Please select a gender to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteGender(data) {
      if (data.type ==1) {
        $("#jqgrid-grid-gender").trigger("reloadGrid");
         reset_form('#gFormGender');
      }
      MessageRenderer.render(data)
  }
  function showGender(entity) {
    $('#gFormGender input[name = id]').val(entity.id);
    $('#gFormGender input[name = version]').val(entity.version);
    $('#gFormGender input[name = creator]').val(entity.creator);
    $('#gFormGender input[name = dateCreated]').val(entity.dateCreated);
    $('#gFormGender input[name = modifier]').val(entity.modifier);
    $('#gFormGender input[name = lastUpdated]').val(entity.lastUpdated);

    
    $('#genderType').val(entity.genderType);
    $('#description').val(entity.description);
    $('#create-button-gender').attr('value', 'Update');
    $('#delete-button-gender').show();
  }
  function executeSearchGender(fieldName, fieldValue){
    if(executePreConditionForSearchGender(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'gender', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchGender(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchGender(fieldName,fieldValue) {
      // trim field vales before process.
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormGender");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchGender(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormGender"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showGender(data);
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