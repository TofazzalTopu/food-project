

<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }

    $("#gFormDocumentType").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormDocumentType").validationEngine('attach');
    reset_form("#gFormDocumentType");
    $("#jqgrid-grid-documentType").jqGrid({
      url:'${resource(dir:'documentType', file:'list')}',
      datatype: "json",
      colNames:[
        'SL',
        'ID',
        
        'Name',
        'Note'
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:true, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},
        
                
                    {name:'name', index:'name',width:100,align:'left'}
                
        ,
                
                    {name:'note', index:'note',width:100,align:'left'}
                
        
      ],
      rowNum:10,
      rowList:[10,20,30],
      pager: '#jqgrid-pager-documentType',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"All DocumentType Information",
      autowidth: true,
      autoheight: true,
      scrollOffset: 0,
      altRows: true,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
        executeEditDocumentType();
      }
    });
    $("#jqgrid-grid-documentType").jqGrid('navGrid', '#jqgrid-pager-documentType', {edit:false,add:false,del:false,search:false})
    .navButtonAdd('#jqgrid-pager-documentType',{
       caption:"Edit",
       buttonicon:"ui-icon-edit",
       onClickButton: function(){
          executeEditDocumentType();
       },
       position:"last"
    })
    .navButtonAdd('#jqgrid-pager-documentType',{
       caption:"Delete",
       buttonicon:"ui-icon-del",
       onClickButton: function(){
          deleteAjaxDocumentType();
       },
       position:"last"
    });
  });
  function getSelectedDocumentTypeId()
  {
    var documentTypeId = null;
    var rowid = $("#jqgrid-grid-documentType").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      documentTypeId = $("#jqgrid-grid-documentType").jqGrid('getCell', rowid, 'id');
    }
    if(documentTypeId == null){
      documentTypeId = $('#gFormDocumentType input[name = id]').val();
    }
    return documentTypeId;
  }
  function executePreConditionDocumentType() {
      // trim field vales before process.
      trim_form();
      if ($("#gFormDocumentType").validate().form({onfocusout: false}) == false) {
        return false;
      }
      return true;
  }
  function executeAjaxDocumentType() {
    if(!executePreConditionDocumentType()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormDocumentType input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/save";
    }
      SubmissionLoader.showTo();
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormDocumentType").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionDocumentType(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
          if(XMLHttpRequest.status = 0){
              $("#jqgrid-grid-documentType").trigger("reloadGrid");
              reset_form('#gFormDocumentType');
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
  function executePostConditionDocumentType(result) {
      if (result.type == 1) {
        $("#jqgrid-grid-documentType").trigger("reloadGrid");
        reset_form('#gFormDocumentType');
      }
      MessageRenderer.render(result);
  }
  function deleteAjaxDocumentType() {    // Delete record
    var documentTypeId = getSelectedDocumentTypeId();
    if(executePreConditionForDeleteDocumentType(documentTypeId))
    {
      $("#dialog").dialog("destroy");
      $("#dialog-confirm-documentType").dialog({
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
              data:jQuery("#gFormDocumentType").serialize(),
              url: "${resource(dir:'documentType', file:'delete')}",
              success: function(message) {
                executePostConditionForDeleteDocumentType(message);
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

  function executePreConditionForEditDocumentType(documentTypeId) {
    if(documentTypeId == null)
    {
      alert("Please select a documentType to edit") ;
      return false;
    }
    return true;
  }
  function executeEditDocumentType() {
    var documentTypeId = getSelectedDocumentTypeId();
    if(executePreConditionForEditDocumentType(documentTypeId))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'documentType', file:'edit')}?id="+ documentTypeId,
        success: function(entity) {
          executePostConditionForEditDocumentType(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditDocumentType(data) {
      if (data == null) {
        alert('Selected documentType might have been deleted by someone');  //Message renderer
      } else {
        showDocumentType(data);
      }
  }
  function executePreConditionForDeleteDocumentType(documentTypeId) {
    if(documentTypeId == null)
    {
      alert("Please select a documentType to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteDocumentType(data) {
      if (data.type ==1) {
        $("#jqgrid-grid-documentType").trigger("reloadGrid");
         reset_form('#gFormDocumentType');
      }
      MessageRenderer.render(data)
  }
  function showDocumentType(entity) {
    $('#gFormDocumentType input[name = id]').val(entity.id);
    $('#gFormDocumentType input[name = version]').val(entity.version);
    
    $('#name').val(entity.name);
    $('#note').val(entity.note);
    $('#create-button-documentType').attr('value', 'Update');
    $('#delete-button-documentType').show();
  }
  function executeSearchDocumentType(fieldName, fieldValue){
    if(executePreConditionForSearchDocumentType(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'documentType', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchDocumentType(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchDocumentType(fieldName,fieldValue) {
      // trim field vales before process.
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormDocumentType");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchDocumentType(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormDocumentType"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showDocumentType(data);
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