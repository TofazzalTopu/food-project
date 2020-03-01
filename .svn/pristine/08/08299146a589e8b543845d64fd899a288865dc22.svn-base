

<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }

    $("#gFormDivision").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormDivision").validationEngine('attach');
//    reset_form("#gFormDivision");
    $("#jqgrid-grid-division").jqGrid({
      url:'${resource(dir:'financialYear', file:'getBusinessDayTimeList')}',
      datatype: "json",
      colNames:[
        'SL',
        'ID',
        
        'Financial Year',
        'Month',
        'Business Date',
        'Start Time',
        'End Time'
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:true, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},
        
                
                    {name:'name', index:'name',width:100,align:'left'}
                
        ,
                
                    {name:'month_string', index:'month_string',width:100,align:'left'}
                
        ,
                
                    {name:'business_date', index:'business_date',width:100,align:'left'}
                
        ,
                
                    {name:'start_time', index:'start_time',width:100,align:'left'}
                
        ,
                
                    {name:'end_time', index:'end_time',width:100,align:'left'}
                

        
      ],
      rowNum:10,
      rowList:[10,20,30],
      pager: '#jqgrid-pager-division',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"All Business Day Time Information",
      autowidth: true,
      autoheight: true,
      scrollOffset: 0,
      altRows: true,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
        //executeEditDivision();
      }
    });
    $("#jqgrid-grid-division").jqGrid('navGrid', '#jqgrid-pager-division', {edit:false,add:false,del:false,search:false})
    .navButtonAdd('#jqgrid-pager-division',{
       caption:"Edit",
       buttonicon:"ui-icon-edit",
       onClickButton: function(){
          executeEditDivision();
       },
       position:"last"
    })
    .navButtonAdd('#jqgrid-pager-division',{
       caption:"Delete",
       buttonicon:"ui-icon-del",
       onClickButton: function(){
          deleteAjaxDivision();
       },
       position:"last"
    });
  });

  function reset_business_day_time_form(formName) {
           $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
          if (this.type == 'hidden') {
              if(this.name!="financialYearId"){
                  this.value = "";
              }

          } else {
              this.value = this.defaultValue;
          }
      });
      $('input[type=checkbox]').attr('checked',false);
      $(formName +' input[name = create-button]').attr('value', 'Open');
      $(formName +' input[name = delete-button]').hide();

  }

  function reset_form(formName) {
      $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
          if (this.type == 'hidden') {
              if(this.name!="financialYearId"){
                  this.value = "";
              }

          } else {
              this.value = this.defaultValue;
          }
      });
      $('input[type=checkbox]').attr('checked',false);
      $(formName +' input[name = create-button]').attr('value', 'Create');
      $(formName +' input[name = delete-button]').hide();

  }
  function getSelectedDivisionId()
  {
    var divisionId = null;
    var rowid = $("#jqgrid-grid-division").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      divisionId = $("#jqgrid-grid-division").jqGrid('getCell', rowid, 'id');
    }
    if(divisionId == null){
      divisionId = $('#gFormDivision input[name = id]').val();
    }
    return divisionId;
  }
  function executePreConditionDivision() {
      // trim field vales before process.
      trim_form();
      if ($("#gFormBusinessDayTime").validate().form({onfocusout: false}) == false) {
        return false;
      }
      return true;
  }
  function executeAjaxBusinessDayTime() {
    if(!executePreConditionDivision()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormBusinessDayTime input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/updateBusinessDayTime";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/setBusinessDayTime";
    }
      SubmissionLoader.showTo();
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormBusinessDayTime").serialize(),
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionDivision(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
          if(XMLHttpRequest.status = 0){
              $("#jqgrid-grid-division").trigger("reloadGrid");
              reset_business_day_time_form('#gFormBusinessDayTime');
              MessageRenderer.renderErrorText("Network Problem: Time out");
          }  else{
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
  function executePostConditionDivision(result) {
      if (result.type == 1) {
        $("#jqgrid-grid-division").trigger("reloadGrid");
          reset_business_day_time_form('#gFormBusinessDayTime');
      }
      MessageRenderer.render(result);
  }
  function deleteAjaxDivision() {    // Delete record
    var divisionId = getSelectedDivisionId();
    if(executePreConditionForDeleteDivision(divisionId))
    {
      $("#dialog").dialog("destroy");
      $("#dialog-confirm-division").dialog({
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
              data:jQuery("#gFormDivision").serialize(),
              url: "${resource(dir:'division', file:'delete')}",
              success: function(message) {
                executePostConditionForDeleteDivision(message);
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

  function executePreConditionForEditDivision(divisionId) {
    if(divisionId == null)
    {
      alert("Please select a division to edit") ;
      return false;
    }
    return true;
  }
  function executeEditDivision() {
    var divisionId = getSelectedDivisionId();
    if(executePreConditionForEditDivision(divisionId))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'division', file:'edit')}?id="+ divisionId,
        success: function(entity) {
          executePostConditionForEditDivision(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditDivision(data) {
      if (data == null) {
        alert('Selected division might have been deleted by someone');  //Message renderer
      } else {
        showDivision(data);
      }
  }
  function executePreConditionForDeleteDivision(divisionId) {
    if(divisionId == null)
    {
      alert("Please select a division to delete") ;
      return false;
    }
    return true;
  }
  function executePostConditionForDeleteDivision(data) {
      if (data.type ==1) {
        $("#jqgrid-grid-division").trigger("reloadGrid");
         reset_form('#gFormDivision');
      }
      MessageRenderer.render(data)
  }
  function showDivision(entity) {
    $('#gFormDivision input[name = id]').val(entity.id);
    $('#gFormDivision input[name = version]').val(entity.version);
    
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
    $('#dateUpdated').val(entity.dateUpdated);
    if(entity.countryInfo){
       $('#countryInfo').val(entity.countryInfo.id).attr("selected","selected");
    }
    else{
        $('#countryInfo').val(entity.countryInfo);
    }
    $('#geoCode').val(entity.geoCode);
    $('#name').val(entity.name);
    $('#create-button-division').attr('value', 'Update');
    $('#delete-button-division').show();
  }
  function executeSearchDivision(fieldName, fieldValue){
    if(executePreConditionForSearchDivision(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'division', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchDivision(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchDivision(fieldName,fieldValue) {
      // trim field vales before process.
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormDivision");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchDivision(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormDivision"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showDivision(data);
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