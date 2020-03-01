

<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }

    $("#gFormBloodGroup").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormBloodGroup").validationEngine('attach');
    reset_form("#gFormBloodGroup");
    $("#jqgrid-grid-bloodGroup").jqGrid({
      url:'${request.contextPath}/${params.controller}/list',
      datatype: "json",
      colNames:[
        'SL',
        'ID',
        
        'Group Name',
        'Description'
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:true, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},
        
                
                    {name:'groupName', index:'groupName',width:100,align:'left'}
                
        ,
                
                    {name:'description', index:'description',width:100,align:'left'}
                
        
      ],
      rowNum:10,
      rowList:[10,20,30],
      pager: '#jqgrid-pager-bloodGroup',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"All BloodGroup Information",
      autowidth: true,
      autoheight: true,
      scrollOffset: 0,
      altRows: true,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
        executeEditBloodGroup();
      }
    });
    $("#jqgrid-grid-bloodGroup").jqGrid('navGrid', '#jqgrid-pager-bloodGroup', {edit:false,add:false,del:false,search:false})
    .navButtonAdd('#jqgrid-pager-bloodGroup',{
       caption:"Edit",
       buttonicon:"ui-icon-edit",
       onClickButton: function(){
          executeEditBloodGroup();
       },
       position:"last"
    })
    .navButtonAdd('#jqgrid-pager-bloodGroup',{
       caption:"Delete",
       buttonicon:"ui-icon-del",
       onClickButton: function(){
          deleteAjaxBloodGroup();
       },
       position:"last"
    });
  });
  function getSelectedBloodGroupId()
  {
    var bloodGroupId = null;
    var rowid = $("#jqgrid-grid-bloodGroup").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      bloodGroupId = $("#jqgrid-grid-bloodGroup").jqGrid('getCell', rowid, 'id');
    }
    if(bloodGroupId == null){
      bloodGroupId = $('#gFormBloodGroup input[name = id]').val();
    }
    return bloodGroupId;
  }
  function executePreConditionBloodGroup() {
      // trim field vales before process.
      trim_form();
      if ($("#gFormBloodGroup").validate().form({onfocusout: false}) == false) {
        return false;
      }
      return true;
  }
  function executeAjaxBloodGroup() {
    if(!executePreConditionBloodGroup()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormBloodGroup input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/save";
    }
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormBloodGroup").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionBloodGroup(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
      },
      complete:function(){
      },
      dataType:'json'
    });
    return false;
  }
  function executePostConditionBloodGroup(result) {
      if (result.type == 1) {
        $("#jqgrid-grid-bloodGroup").trigger("reloadGrid");
        reset_form('#gFormBloodGroup');
      }
      MessageRenderer.render(result);
  }
  function deleteAjaxBloodGroup() {    // Delete record
    var bloodGroupId = getSelectedBloodGroupId();
    if(executePreConditionForDeleteBloodGroup(bloodGroupId))
    {
      $("#dialog").dialog("destroy");
      $("#dialog-confirm-bloodGroup").dialog({
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
              data:jQuery("#gFormBloodGroup").serialize(),
              url: "${request.contextPath}/${params.controller}/delete",
              success: function(message) {
                executePostConditionForDeleteBloodGroup(message);
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

  function executePreConditionForEditBloodGroup(bloodGroupId) {
    if(bloodGroupId == null)
    {
      alert("Please select a bloodGroup to edit") ;
      return false;
    }
    return true;
  }
  function executeEditBloodGroup() {
    var bloodGroupId = getSelectedBloodGroupId();
    if(executePreConditionForEditBloodGroup(bloodGroupId))
    {
      $.ajax({
        type: "POST",
        url: "${request.contextPath}/${params.controller}/edit?id="+ bloodGroupId,
        success: function(entity) {
          executePostConditionForEditBloodGroup(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditBloodGroup(data) {
      if (data == null) {
        alert('Selected bloodGroup might have been deleted by someone');  //Message renderer
      } else {
        showBloodGroup(data);
      }
  }
  function executePreConditionForDeleteBloodGroup(bloodGroupId) {
    if(bloodGroupId == null)
    {
      alert("Please select a bloodGroup to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteBloodGroup(data) {
      if (data.type ==1) {
        $("#jqgrid-grid-bloodGroup").trigger("reloadGrid");
         reset_form('#gFormBloodGroup');
      }
      MessageRenderer.render(data)
  }
  function showBloodGroup(entity) {
    $('#gFormBloodGroup input[name = id]').val(entity.id);
    $('#gFormBloodGroup input[name = version]').val(entity.version);
    
    $('#groupName').val(entity.groupName);
    $('#description').val(entity.description);
    $('#create-button-bloodGroup').attr('value', 'Update');
    $('#delete-button-bloodGroup').show();
  }
  function executeSearchBloodGroup(fieldName, fieldValue){
    if(executePreConditionForSearchBloodGroup(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'bloodGroup', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchBloodGroup(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchBloodGroup(fieldName,fieldValue) {
      // trim field vales before process.
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormBloodGroup");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchBloodGroup(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormBloodGroup"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showBloodGroup(data);
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