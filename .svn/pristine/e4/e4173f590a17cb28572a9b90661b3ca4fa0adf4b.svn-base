

<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
      changePageTitle("<g:message code="religion.create.title"/>");
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }

    $("#gFormReligion").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormReligion").validationEngine('attach');
    reset_form("#gFormReligion");

    $('.date').mask('99-99-9999',{});
    $('.date').datePicker({startDate:'01/01/1900'});
  });
  function getSelectedReligionId()
  {
    var religionId = null;
    var rowid = $("#jqgrid-grid-religion").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      religionId = $("#jqgrid-grid-religion").jqGrid('getCell', rowid, 'id');
    }
    if(religionId == null){
      religionId = $('#gFormReligion input[name = id]').val();
    }
    return religionId;
  }
  function executePreConditionReligion() {
      // trim field vales before process.
      //trim_form();
      //if ($("#gFormReligion").validate().form({onfocusout: false}) == false) {
      //  return false;
      //}
      return true;
  }
  function executeAjaxReligion() {
    if(!executePreConditionReligion()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormReligion input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/save";
    }
      SubmissionLoader.showTo();
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormReligion").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionReligion(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
          if(XMLHttpRequest.status = 0){
              $("#jqgrid-grid-religion").trigger("reloadGrid");
              reset_form('#gFormReligion');
              MessageRenderer.renderErrorText("Network Problem: Time out");
          }else{
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
  function executePostConditionReligion(result) {
      if (result.type == 1) {
        $("#jqgrid-grid-religion").trigger("reloadGrid");
        reset_form('#gFormReligion');
      }
      MessageRenderer.render(result);
  }
  function deleteAjaxReligion() {    // Delete record
    var religionId = getSelectedReligionId();
    if(executePreConditionForDeleteReligion(religionId))
    {
      $( "#dialog:ui-dialog" ).dialog( "destroy" );
      $("#dialog-confirm-religion").dialog({
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
              data:jQuery("#gFormReligion").serialize(),
              url: "${resource(dir:'religion', file:'delete')}",
              success: function(message) {
                executePostConditionForDeleteReligion(message);
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

  function executePreConditionForEditReligion(religionId) {
    if(religionId == null)
    {
      alert("Please select a religion to edit") ;
      return false;
    }
    return true;
  }
  function executeEditReligion(id) {
    //var religionId = getSelectedReligionId();
    var religionId = id;

    if(executePreConditionForEditReligion(religionId))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'religion', file:'editJSON')}?id="+ religionId,
        success: function(entity) {
          executePostConditionForEditReligion(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditReligion(data) {
      if (data == null) {
        alert('Selected religion might have been deleted by someone');  //Message renderer
      } else {
        showReligion(data);
      }
  }
  function executePreConditionForDeleteReligion(religionId) {
    if(religionId == null)
    {
      alert("Please select a religion to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteReligion(data) {
      if (data.type ==1) {
        $("#jqgrid-grid-religion").trigger("reloadGrid");
         reset_form('#gFormReligion');
      }
      MessageRenderer.render(data)
  }
  function showReligion(entity) {
    $('#gFormReligion input[name = id]').val(entity.id);
    $('#gFormReligion input[name = version]').val(entity.version);
    $('#gFormReligion input[name = creator]').val(entity.creator);
    $('#gFormReligion input[name = dateCreated]').val(entity.dateCreated);
    $('#gFormReligion input[name = modifier]').val(entity.modifier);
    $('#gFormReligion input[name = lastUpdated]').val(entity.lastUpdated);

    
    $('#religionName').val(entity.religionName);
    $('#description').val(entity.description);
    $('#create-button-religion').attr('value', 'Update');
    $('#delete-button-religion').show();
  }
  function executeSearchReligion(fieldName, fieldValue){
    if(executePreConditionForSearchReligion(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'religion', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchReligion(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchReligion(fieldName,fieldValue) {
      // trim field vales before process.
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormReligion");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchReligion(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormReligion"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showReligion(data);
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