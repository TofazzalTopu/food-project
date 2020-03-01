
<script type="text/javascript" language="Javascript">


  $(document).ready(function() {

     // setBillNumber();

      var dt= new Date();

      $("#billGeneration").val(dt);

      //alert(dt)
      $("#billGenerationDate").val(dt);

    $("#jqgrid-grid-createBill").jqGrid({
      url:'${resource(dir:'createBill', file:'listByCriteria')}',
      datatype: "json",
      colNames:[
          "Select",
       // 'SL',
        'ID',

        'Bill Number',
        'Bill Generation Date',
        'Total Amount In Taka',
        'Edit',
        'Delete'
      ],
      colModel:[
          {name: 'select', index: 'select', width: 30},
         /* {
              name: 'chk', index: 'chk', width: 25, align: "right", edittype: 'radio',
              formatter: function (cellValue, option) {
                  return '<input type="radio" name="radio_' + option.id + '"  />';
                  //return '<input id="t2" type="radio" name="radio_' + option.gid + '" checked="'+cellvalue+'"/>';
              }
          },*/
       // {name:'sl',index:'sl', width:30, sortable:true, align:'left'},
       {name:'id',index:'id', width:0, hidden:true}

       ,{name:'billNumber', index:'billNumber',width:100,align:'right'}

        ,{name:'billGenerationDate', index:'billGenerationDate',width:100,align:'right', formatter: 'date', formatoptions: {newformat: 'd-m-Y'}}

       ,{name:'receivableAmount', index:'receivableAmount',width:100,align:'right', formatter:'number', formatoptions :{thousandsSeparator:","}}
         ,{name:'edit', index:'edit',width:30,align:'right'}
          ,{name:'delete', index:'delete',width:30,align:'right'}

      ],
      rowNum:10,
      rowList:[10,20,30],
      pager: '#jqgrid-pager-createBill',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"View Bill List",
      autowidth: true,
      autoheight: true,
      scrollOffset: 0,
      altRows: true,
      loadComplete: function() {
          ids = $("#jqgrid-grid-createBill").jqGrid('getDataIDs');
          var myData = $('#jqgrid-grid-createBill').jqGrid('getRowData');


        /*  for (key in ids) {
              //alert(ids[key])
              var bill= "'"+ myData[key].billNumber+"'";
              var id=myData[key].id;
              //alert(bill)
              $("#jqgrid-grid-createBill").jqGrid('setRowData', ids[key], {edit: '<a  href="javascript:editBillForm('+bill+')">' + 'Edit' + '</a>'});
              // $("#jqgrid-grid-createBill").jqGrid('setRowData', ids[key], {delete: '<a  href="javascript:deleteBill('+ id +',' +bill +')">' + 'Delete' + '</a>'});
              $("#jqgrid-grid-insuranceSetup").jqGrid('setRowData', ids[key], {assetInsurance: '<a  href="javascript:getAssetInsuranceForm('+status+','+myData[key].id+')">' + 'Add Asset Insurance' + '</a>'});
          }*/

          for(key in ids){
              var bill= "'"+ myData[key].billNumber+"'";
              var billN= myData[key].billNumber;
              var id = $("#jqgrid-grid-createBill").getCell(ids[key], 'id');
              var billNumber = $("#jqgrid-grid-createBill").getCell(ids[key], 'billNumber');
            //  alert('billNumber'+ bill)
              $("#jqgrid-grid-createBill").jqGrid('setRowData', ids[key],{
                  edit: '<a target="_blank" href="${request.contextPath}/createBill/showEditBillForm?billNumber='+bill+'"title="U">Update</a>'
              });
             //$("#jqgrid-grid-createBill").jqGrid('setRowData', ids[key], {edit: '<a  target="_blank" href="javascript:editBillForm('+bill+')">' + 'Edit' + '</a>'});
              $("#jqgrid-grid-createBill").jqGrid('setRowData', ids[key], {delete: '<a  href="javascript:deleteBill('+bill+')">' + 'Delete' + '</a>'});

              $("#jqgrid-grid-createBill").jqGrid('setRowData', ids[key], {
                  si:ids[key],
                  select: '<input name="tp-select" type="radio" id="tp-pool-radio-' + id + '" class="tp-pool-radio" val="' +
                  bill + '" onclick="setBillNumber('+id+','+bill+');" />'});

          }

          },
      onSelectRow: function(rowid, status) {
          var billNumber = "'"+$("#jqgrid-grid-createBill").getCell(ids[key], 'billNumber')+"'";
          $('#tp-pool-radio-'+id).attr('checked',true);
          setBillNumber(id,billNumber);
          executeEditCreateBill();
      }
    });

  });
  function setBillNumber(id,billNumber){
      if($('#tp-pool-radio-' + id).is(':checked')){
          $('#billNumber').val(billNumber);
      }
  }
  function editBillForm(billNumber)
  {
      //alert(billNumber)
       //alert('insuranceClaimAmountId '+' rowID '+rowID+ " billNumber "+billNumber);
      $.ajax({
          type: "POST",
          //data: { 'billNumber' : billNumber},
          url: "${resource(dir:'createBill', file:'showEditBillForm')}?billNumber="+billNumber,
          beforeSend: function (jqXHR, settings) {
              $("#loader_icon").show();
          },
          success: function (entity) {
              $("#admission-form-block").html(entity);
              $("#admission-form-slip").fancybox({
                  autoDimensions: false,
                  height: 1000,
                  width: 1550,
                  'transitionIn': 'elastic',
                  'transitionOut': 'elastic',
                  'speedIn': 600,
                  'speedOut': 400,
                  'overlayShow': true

              }).trigger('click')
          },
          complete: function () {
          },
          dataType: 'html'
      });


  }
  function deleteBill(billNumber) {    // Delete record
      var createBillId = billNumber;

      if(executePreConditionForDeleteCreateBill(createBillId))
      {
          $("#dialog").dialog("destroy");
          $("#dialog-confirm-createBill").dialog({
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
                          //data:jQuery("#gFormCreateBill").serialize(),
                          url: "${resource(dir:'createBill', file:'deleteBill')}?billNumber="+billNumber,
                          //url: "${resource(dir:'createBill', file:'deleteBill')}",
                          success: function(message) {
                              executePostConditionForDeleteCreateBill(message);
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

  /*function deleteBill(billNumber)
  {
      //alert(billNumber)
      //alert('insuranceClaimAmountId '+' rowID '+rowID+ " billNumber "+billNumber);
      $.ajax({
       type: "POST",
       //data: { 'billNumber' : billNumber},
       url: "${resource(dir:'createBill', file:'deleteBill')}?billNumber="+billNumber,
       beforeSend: function (jqXHR, settings) {
       $("#loader_icon").show();
       },
       success: function (entity) {

       $("#admission-form-block").html(entity)
       $("#admission-form-slip").fancybox({
       autoDimensions: false,
       height: 1000,
       width: 1550,
       'transitionIn': 'elastic',
       'transitionOut': 'elastic',
       'speedIn': 600,
       'speedOut': 400,
       'overlayShow': true

       }).trigger('click')
       },
       complete: function () {


       },
       dataType: 'html'
       });


  }*/


  function executePreConditionForEditCreateBill(createBillId) {
      if(createBillId == null)
      {
          alert("Please select a createBill to edit") ;
          return false;
      }
      return true;
  }
  function executeEditCreateBill() {
      var createBillId = getSelectedCreateBillId();
      if(executePreConditionForEditCreateBill(createBillId))
      {
          $.ajax({
              type: "POST",
              url: "${resource(dir:'createBill', file:'edit')}?id="+ createBillId,
              success: function(entity) {
                  executePostConditionForEditCreateBill(entity);
              },
              dataType:'json'
          });
      }
  }
  function executePostConditionForEditCreateBill(data) {
      if (data == null) {
          alert('Selected createBill might have been deleted by someone');  //Message renderer
      } else {
          showCreateBill(data);
      }
  }
  function showCreateBill(entity) {
      $('#gFormCreateBill input[name = id]').val(entity.id);
      $('#gFormCreateBill input[name = version]').val(entity.version);

      $('#invoiceNumber').val(entity.invoiceNumber);
      $('#deliveryDate').val(entity.deliveryDate);
      $('#receivableAmount').val(entity.receivableAmount);

      /*if(entity.customerName){
          $('#customerName').val(entity.customerName.id).attr("selected","selected");
      }
      else{
          $('#customerName').val(entity.customerName);
      }*/
      if(entity.territory){
          $('#territory').val(entity.territory.id).attr("selected","selected");
      }
      else{
          $('#territory').val(entity.territory);
      }
      if(entity.territoryGeoLocation){
          $('#territoryGeoLocation').val(entity.territoryGeoLocation.id).attr("selected","selected");
      }
      else{
          $('#territoryGeoLocation').val(entity.territoryGeoLocation);
      }
      $('#customerId').val(entity.customerId);
      $('#billNumber').val(entity.billNumber);
      $('#billGenerationDate').val(entity.billGenerationDate);
      $('#purchaseOrderNumber').val(entity.purchaseOrderNumber);
      $('#purchaseOrderDate').val(entity.purchaseOrderDate);
      $('#vatChallanNumber').val(entity.vatChallanNumber);
      $('#vatChallanDate').val(entity.vatChallanDate);
      if(entity.customerCategory){
          $('#customerCategory').val(entity.customerCategory.id).attr("selected","selected");
      }
      else{
          $('#customerCategory').val(entity.customerCategory);
      }
      $('#receivableAmount').val(entity.receivableAmount);
      $('#create-button-createBill').attr('value', 'Update');
      $('#delete-button-createBill').show();
  }

  $('#customerId').blur(function () {

      if ($('#customerId').val() == '') {
          //alert('sdfs')
          clearCustomerData();

      }
  });
  jQuery('#customerId').autocomplete({
      minLength: '1',
      source: function (request, response) {
          var data = {searchKey: request.term};
          var url = '${resource(dir:'createBill', file:'listCustomerCodeByCriteria')}?customerCode=' + $('#customerId').val() + "&customerName=" + $("#customerName").val();
          DocuAutoComplete.setSpinnerSelector('customerId').execute(response, url, data, function (item) {

              item['label'] = item['code'];
              item['value'] = item['label'];
              return item;
          });
      },
      select: function (event, ui) {
          loadCustomerData(ui.item.id, ui.item.name, ui.item.code, ui.item.enterprise, ui.item.present_address);
      }
  }).data("autocomplete")._renderItem = function (ul, item) {
      var accountstype = "";
      if (item) {
          accountstype = '<div style="font-size: 9px; color:#326E93;">' + "ID: " + item.code + "; Name: " + item.name + "; "+ "; Category: " + item.category + "; " + "Enterprise: " + item.enterprise + "; " + "Address: " + item.present_address + '</div>';
      }
      return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
  };
  function loadCustomerData(customerId, cusName, code, enterprise, address) {
      $("#customerMasterId").val(customerId);
      $("#customerId").val(code);
      $("#customerName").val(cusName);
  }

  $('#customerName').blur(function () {

      if ($('#customerName').val() == '') {
          clearCustomerData();

      }
  });
  jQuery('#customerName').autocomplete({
      minLength: '1',
      source: function (request, response) {
          var data = {searchKey: request.term};
          var url = '${resource(dir:'createBill', file:'listCustomerCodeByCriteria')}?customerCode=' + $('#customerId').val() + "&customerName=" + $("#customerName").val();
          DocuAutoComplete.setSpinnerSelector('customerName').execute(response, url, data, function (item) {

              item['label'] = item['name'];
              item['value'] = item['label'];
              return item;
          });
      },
      select: function (event, ui) {
          loadCustomerData(ui.item.id, ui.item.name, ui.item.code, ui.item.enterprise, ui.item.present_address);
      }
  }).data("autocomplete")._renderItem = function (ul, item) {
      var accountstype = "";
      if (item) {
          accountstype = '<div style="font-size: 9px; color:#326E93;">' + "ID: " + item.code + "; Name: " + item.name + "; "+ "; Category: " + item.category + "; " + "Enterprise: " + item.enterprise + "; " + "Address: " + item.present_address + '</div>';
      }
      return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
  };
  function loadCustomerData(customerId, cusName, code, enterprise, address) {
      $("#customerMasterId").val(customerId);
      $("#customerId").val(code);
      $("#customerName").val(cusName);
  }

  function searchBill(){
      var customerName = $("#customerName").val();
      /*if(!customerName){
          MessageRenderer.renderErrorText("Please select customer name.");
      }*/
      var billNum= $("#billNumber").val()
      var frmDate= $("#billGeneratedFrom").val()
      var toDate= $("#billGeneratedTo").val()
      var cusId= $("#customerMasterId").val()
     // alert('df'+ cusId)
      $("#jqgrid-grid-createBill").setGridParam({
         // url: '${resource(dir:'createBill', file:'listByCriteria')}?&customerId=' + $("#customerName").val()
          url: '${resource(dir:'createBill', file:'listByCriteria')}',
          postData: {
              billNum: billNum,
              frmDate: frmDate,
              toDate: toDate,
              cusId: cusId
          }
      });
      $("#jqgrid-grid-createBill").trigger("reloadGrid");
  }

  /*function deleteBill() {    // Delete record
      var createBillId = getSelectedCreateBillId();
      if(executePreConditionForDeleteCreateBill(createBillId))
      {
          $("#dialog").dialog("destroy");
          $("#dialog-confirm-createBill").dialog({
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
                          data:jQuery("#gFormCreateBill").serialize(),
                          url: "${resource(dir:'createBill', file:'deleteBill')}",
                          success: function(message) {
                              executePostConditionForDeleteCreateBill(message);
                          }
                      });
                  },
                  Cancel: function() {
                      $(this).dialog('close');
                  }
              }
          }); //end of dialog
      }
  }*/
  function deleteAjaxCreateBill() {    // Delete record
      var createBillId = getSelectedCreateBillId();
      if(executePreConditionForDeleteCreateBill(createBillId))
      {
          $("#dialog").dialog("destroy");
          $("#dialog-confirm-createBill").dialog({
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
                          data:jQuery("#gFormCreateBill").serialize(),
                          url: "${resource(dir:'createBill', file:'deleteBill')}",
                          success: function(message) {
                              executePostConditionForDeleteCreateBill(message);
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
  function getSelectedCreateBillId()
  {
      var createBillId = null;
      var rowid = $("#jqgrid-grid-createBill").jqGrid('getGridParam', 'selrow');
      if(rowid)
      {
          createBillId = $("#jqgrid-grid-createBill").jqGrid('getCell', rowid, 'id');
      }
      if(createBillId == null){
          createBillId = $('#gFormCreateBill input[name = id]').val();
      }
      return createBillId;
  }

  function executePreConditionForDeleteCreateBill(createBillId) {
      if(createBillId == null)
      {
          alert("Please select a createBill to delete") ;
          return false;
      }
      return true;
  }
  function executePostConditionForDeleteCreateBill(data) {
      if (data.type ==1) {
          $("#jqgrid-grid-createBill").trigger("reloadGrid");
          reset_form('#gFormCreateBill');
      }
      MessageRenderer.render(data)
  }

  function generateBill() {
      var billNumber = $("#billNumber").val();
      //$("#jqgrid-grid-createBill").
      // alert(po)
      SubmissionLoader.showTo();
     // var url = '${resource(dir:'createBill', file:'listCustomerCodeByCriteria')}?customerCode=' + $('#customerId').val() + "&customerName=" + $("#customerName").val();
      window.open("${resource(dir:'createBill', file:'viewReportBeforeCreateBill')}?format=PDF"+ "&billNumber="+billNumber);
      SubmissionLoader.hideFrom();
  }

</script>