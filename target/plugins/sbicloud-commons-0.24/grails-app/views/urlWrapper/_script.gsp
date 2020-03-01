

<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }

    $("#gFormUrlWrapper").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormUrlWrapper").validationEngine('attach');
    reset_form("#gFormUrlWrapper");
    $("#jqgrid-grid-urlWrapper").jqGrid({
      url:'${request.contextPath}/${params.controller}/list',
      datatype: "json",
      colNames:[
        'SL',
        'ID',
        
        'Url',
        'Url Wrapper Name'
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:true, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},
        
                
                    {name:'url', index:'url',width:100,align:'left'}
                
        ,
                
                    {name:'urlWrapperName', index:'urlWrapperName',width:100,align:'left'}
                
        
      ],
      rowNum:10,
      rowList:[10,20,30],
      pager: '#jqgrid-pager-urlWrapper',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"All UrlWrapper Information",
      autowidth: true,
      autoheight: true,
      scrollOffset: 0,
      altRows: true,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
        executeEditUrlWrapper();
      }
    });
    $("#jqgrid-grid-urlWrapper").jqGrid('navGrid', '#jqgrid-pager-urlWrapper', {edit:false,add:false,del:false,search:false})
    .navButtonAdd('#jqgrid-pager-urlWrapper',{
       caption:"Edit",
       buttonicon:"ui-icon-edit",
       onClickButton: function(){
          executeEditUrlWrapper();
       },
       position:"last"
    })
    .navButtonAdd('#jqgrid-pager-urlWrapper',{
       caption:"Delete",
       buttonicon:"ui-icon-del",
       onClickButton: function(){
          deleteAjaxUrlWrapper();
       },
       position:"last"
    });
  });
  function getSelectedUrlWrapperId()
  {
    var urlWrapperId = null;
    var rowid = $("#jqgrid-grid-urlWrapper").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      urlWrapperId = $("#jqgrid-grid-urlWrapper").jqGrid('getCell', rowid, 'id');
    }
    if(urlWrapperId == null){
      urlWrapperId = $('#gFormUrlWrapper input[name = id]').val();
    }
    return urlWrapperId;
  }
  function executePreConditionUrlWrapper() {
      // trim field vales before process.
      trim_form();
      if ($("#gFormUrlWrapper").validate().form({onfocusout: false}) == false) {
        return false;
      }
      return true;
  }
  function executeAjaxUrlWrapper() {
    if(!executePreConditionUrlWrapper()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormUrlWrapper input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/save";
    }
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormUrlWrapper").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionUrlWrapper(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
      },
      complete:function(){
      },
      dataType:'json'
    });
    return false;
  }
  function executePostConditionUrlWrapper(result) {
      if (result.type == 1) {
        $("#jqgrid-grid-urlWrapper").trigger("reloadGrid");
        reset_form('#gFormUrlWrapper');
      }
      MessageRenderer.render(result);
  }
  function deleteAjaxUrlWrapper() {    // Delete record
    var urlWrapperId = getSelectedUrlWrapperId();
    if(executePreConditionForDeleteUrlWrapper(urlWrapperId))
    {
      $("#dialog").dialog("destroy");
      $("#dialog-confirm-urlWrapper").dialog({
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
              data:jQuery("#gFormUrlWrapper").serialize(),
              url: "${request.contextPath}/${params.controller}/delete",
              success: function(message) {
                executePostConditionForDeleteUrlWrapper(message);
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

  function executePreConditionForEditUrlWrapper(urlWrapperId) {
    if(urlWrapperId == null)
    {
      alert("Please select a urlWrapper to edit") ;
      return false;
    }
    return true;
  }
  function executeEditUrlWrapper() {
    var urlWrapperId = getSelectedUrlWrapperId();
    if(executePreConditionForEditUrlWrapper(urlWrapperId))
    {
      $.ajax({
        type: "POST",
        url: "${request.contextPath}/${params.controller}/edit?id="+ urlWrapperId,
        success: function(entity) {
          executePostConditionForEditUrlWrapper(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditUrlWrapper(data) {
      if (data == null) {
        alert('Selected urlWrapper might have been deleted by someone');  //Message renderer
      } else {
        showUrlWrapper(data);
      }
  }
  function executePreConditionForDeleteUrlWrapper(urlWrapperId) {
    if(urlWrapperId == null)
    {
      alert("Please select a urlWrapper to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteUrlWrapper(data) {
      if (data.type ==1) {
        $("#jqgrid-grid-urlWrapper").trigger("reloadGrid");
         reset_form('#gFormUrlWrapper');
      }
      MessageRenderer.render(data)
  }
  function showUrlWrapper(entity) {
    $('#gFormUrlWrapper input[name = id]').val(entity.id);
    $('#gFormUrlWrapper input[name = version]').val(entity.version);
    
    $('#url').val(entity.url);
    $('#urlWrapperName').val(entity.urlWrapperName);
    $('#create-button-urlWrapper').attr('value', 'Update');
    $('#delete-button-urlWrapper').show();
  }
  function executeSearchUrlWrapper(fieldName, fieldValue){
    if(executePreConditionForSearchUrlWrapper(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'urlWrapper', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchUrlWrapper(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchUrlWrapper(fieldName,fieldValue) {
      // trim field vales before process.
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormUrlWrapper");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchUrlWrapper(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormUrlWrapper"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showUrlWrapper(data);
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