

<script type="text/javascript" language="Javascript">
  $(document).ready(function() {
      textboxes = $("input:visible:not([disabled],[readonly]),select,button");
    if ($.browser.mozilla) {
        $(textboxes).keypress (checkForEnter);
    } else {
        $(textboxes).keydown (checkForEnter);
    }
      getBusinessMonth();
    $("#gFormBusinessDay").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormBusinessDay").validationEngine('attach');
    //reset_form("#gFormBusinessDay");
    $("#jqgrid-grid-division").jqGrid({
      url:'${resource(dir:'financialYear', file:'getBusinessDayList')}',
      datatype: "json",
      colNames:[
        'SL',
        'ID',
        
        'Business Date',
        'Opened From',
        'Is Open',
        'User Created',
        'User Updated',
        'Date Created',
        'Date Updated'
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:true, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},
        
                
                    {name:'business_date', index:'business_date',width:100,align:'left'}
                
        ,
                
                    {name:'opened_from', index:'opened_from',width:100,align:'left'}
                
        ,
                
                    {name:'is_open', index:'is_open',width:100,align:'left'}
                
        ,
                
                    {name:'username', index:'username',width:100,align:'left'}
                
        ,
                
                    {name:'user_updated', index:'user_updated',width:100,align:'left'}
                
        ,
                
                    {name:'date_created', index:'date_created',width:100,align:'left'}
                
        ,
                
                    {name:'date_updated', index:'date_updated',width:100,align:'left'}
                
        
      ],
      rowNum:10,
      rowList:[10,20,30],
      pager: '#jqgrid-pager-division',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"All Business Day Information",
      autowidth: true,
      autoheight: true,
      scrollOffset: 0,
      altRows: true,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
        executeEditDivision();
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
  function getSelectedDivisionId()
  {
    var divisionId = null;
    var rowid = $("#jqgrid-grid-division").jqGrid('getGridParam', 'selrow');
    if(rowid)
    {
      divisionId = $("#jqgrid-grid-division").jqGrid('getCell', rowid, 'id');
    }
    if(divisionId == null){
      divisionId = $('#gFormBusinessDay input[name = id]').val();
    }
    return divisionId;
  }
  function executePreConditionDivision() {
      // trim field vales before process.
      trim_form();
      if ($("#gFormBusinessDay").validate().form({onfocusout: false}) == false) {
        return false;
      }
      return true;
  }
  function executeAjaxBusinessDay() {
    if(!executePreConditionDivision()) {
      return false;
    }
    var actionUrl = null;
    if ($('#gFormBusinessDay input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/updateBusinessDay";
      $('#day').attr("disabled", false);
      $('#month').attr("disabled", false);
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/saveBusinessDay";
    }
    SubmissionLoader.showTo();
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormBusinessDay").serialize(),
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
        reset_form('#gFormBusinessDay');
      }
      $("#financialYearId option:text=" + "Select One" +"").attr("selected", "selected");
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
              data:jQuery("#gFormBusinessDay").serialize(),
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
        url: "${resource(dir:'financialYear', file:'editBusinessDay')}?id="+ divisionId,
        success: function(entity) {
          executePostConditionForEditDivision(entity);
        },
        dataType:'json'
      });
    }
  }
  function executePostConditionForEditDivision(data) {
      if (data == null) {
        alert('Selected Business Day might have been deleted by someone');  //Message renderer
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
         reset_form('#gFormBusinessDay');
      }
      MessageRenderer.render(data)
  }
  function showDivision(entity) {
    $('#gFormBusinessDay input[name = id]').val(entity[0].id);
    $('#gFormBusinessDay input[name = version]').val(entity[0].version);
    

    if(entity[0].month){
       $('#month').val(entity[0].month).attr("selected","selected");
        $('#month').attr("disabled", true);
    }
      getBusinessDay(entity[0].day);

    $('#isOpen').attr('checked',entity[0].is_open);
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
          reset_form("#gFormBusinessDay");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchDivision(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormBusinessDay"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showDivision(data);
      }
  }

   function reset_form(formName) {
       $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
           if (this.type == 'hidden') {
               if(this.name!="financialYearId" && this.name!="yearVal" && this.name!="month"){
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

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }

  function getBusinessMonth() {
      var yearId = $('#financialYearId').val();
      if(yearId){
          $.ajax({
              type: "POST",
              url: "${resource(dir:'financialYear', file:'getOpenedBusinessMonth')}?id="+ yearId,
              beforeSend: function (jqXHR, settings) {
                  $("#loader_icon").show();
              },
              success: function (data) {
//                  var options = '<option value="">Select Month</option>';
//                  $.each(data, function (key, val) {
//                      options += '<option value="' + val.month_val + '">' + val.month_string + '</option>';
////                    console.log(val)
//                  })
                  if(data.length >0 ){
                      $("#monthName").text(data[0].month_string);
                      $("#month").val(data[0].month_val);
                      $("#yearVal").val(data[0].year);
                      getBusinessDay(0)
                  }
                  else{
                      MessageRenderer.showHeaderMessage("Please open a business month first",1);
                  }

              },
              complete: function (data) {
                  $("#loader_icon").hide();
              },
              dataType: 'json'
          });
      }
      else{
          MessageRenderer.showHeaderMessage("Please Select Financial Year",1)
      }

  }

  function getBusinessDay(day) {
      var monthId = $('#month').val();
      var yearId = $('#financialYearId').val();
      if(yearId && monthId){
//          var monthText=$("#month option:selected").text().split("-");
//          $('#yearVal').val(monthText[1]);

          $.ajax({
              type: "POST",
              url: "${resource(dir:'financialYear', file:'getBusinessDay')}?monthId="+ monthId+"&yearId="+yearId+"&yearVal="+$('#yearVal').val(),
              beforeSend: function (jqXHR, settings) {
                  $("#loader_icon").show();
              },
              success: function (data) {
                  var options = '<option value="">Select Day</option>';
                  $.each(data, function (key, val) {
                      options += '<option value="' + val.date + '">' + val.date + '</option>';
//                    console.log(val)
                  })
                  $("#day").html(options);
                  if(day!=0){
                      $('#day').val(day).attr("selected","selected");
                      $('#day').attr("disabled", true);
                  }
              },
              complete: function (data) {
                  $("#loader_icon").hide();
              },
              dataType: 'json'
          });
      }
      else{
          MessageRenderer.showHeaderMessage("Please Select Financial Year and Month",1)
      }

  }
</script>