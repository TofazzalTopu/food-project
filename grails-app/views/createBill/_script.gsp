

<script type="text/javascript" language="Javascript">

      var newBills = -1;
      $(document).ready(function() {
      var date = new Date();

      var month = (date.getMonth()+1);

      var day = date.getDate();

      var year = date.getFullYear();

      $("#bilDate").val(day+"-"+month+"-"+year);

      //alert(day+"-"+month+"-"+year)

      //setBillNumber();

    /*
    $("#gFormCreateBill").validationEngine({   //    client side validation
      isOverflown: true,
      overflownDIV: ".ui-layout-center"
    });
    $("#gFormCreateBill").validationEngine('attach');
    reset_form("#gFormCreateBill");
    $("#jqgrid-grid-createBill").jqGrid({
      url:'${resource(dir:'createBill', file:'list')}',
      datatype: "json",
      colNames:[
        'SL',
        'ID',
        
        'Invoice Number',
        */
        /*'Customer Name',
        'Territory',
        'Territory Geo Location',
        'Customer Id',*//*

        'Bill Number',
        'Bill Generation Date',
        'Purchase Order Number',
        'Purchase Order Date',
        'Vat Challan Number',
        'Vat Challan Date',
        */
        /*'Customer Category',
        'Receivable Amount',*//*

          'Delivery Date',
          'Total Amount'
      ],
      colModel:[
        {name:'sl',index:'sl', width:30, sortable:true, align:'left'},
        {name:'id',index:'id', width:0, hidden:true},
        
          {name:'invoiceNumber', index:'invoiceNumber',width:100,align:'right'}
                
       */
        /* , {name:'customerName', index:'customerName',width:100,align:'left'}
                
        , {name:'territory', index:'territory',width:100,align:'left'}
                
        , {name:'territoryGeoLocation', index:'territoryGeoLocation',width:100,align:'left'}
                
        , {name:'customerId', index:'customerId',width:100,align:'right'}*//*

                
        ,{name:'billNumber', index:'billNumber',width:100,align:'right'}
                
        ,{name:'billGenerationDate', index:'billGenerationDate',width:100,align:'right', formatter: 'date', formatoptions: {newformat: 'd-m-Y'}}
                
        , {name:'purchaseOrderNumber', index:'purchaseOrderNumber',width:100,align:'right'}
                
        , {name:'purchaseOrderDate', index:'purchaseOrderDate',width:100,align:'right', formatter: 'date', formatoptions: {newformat: 'd-m-Y'}}
                
        , {name:'vatChallanNumber', index:'vatChallanNumber',width:100,align:'right'}
                
        , {name:'vatChallanDate', index:'vatChallanDate',width:100,align:'right', formatter: 'date', formatoptions: {newformat: 'd-m-Y'}}
        */
    /*, {name:'customerCategory', index:'customerCategory',width:100,align:'left'}
        ,{name:'receivableAmount', index:'receivableAmount',width:100,align:'right', formatter:'number', formatoptions :{thousandsSeparator:","}}*//*

         , {name:'deliveryDate', index:'deliveryDate',width:100,align:'right', formatter: 'date', formatoptions: {newformat: 'd-m-Y'}}
          ,{name:'receivableAmount', index:'receivableAmount',width:100,align:'right', formatter:'number', formatoptions :{thousandsSeparator:","}}
      ],
      rowNum:10,
      rowList:[10,20,30],
      pager: '#jqgrid-pager-createBill',
      sortname: 'id',
      viewrecords: true,
      sortorder: "desc",
      caption:"Create Bill List",
      autowidth: true,
      autoheight: true,
      scrollOffset: 0,
      altRows: true,
      loadComplete: function() {
      },
      onSelectRow: function(rowid, status) {
        executeEditCreateBill();
      }
    });
    */
  });


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
  function executePreConditionCreateBill() {
      // trim field vales before process.
      trim_form();
      if (!$("#gFormCreateBill").validationEngine('validate')) {
          return false;
      }
      return true;
  }
  /*function executeAjaxCreateBill() {
    //  setBillNumber();
      unAdjustedInvoiceList();


    if(!executePreConditionCreateBill()) {
      return false;
    }
     // var datas= jQuery("#gFormCreateBill").serialize();
     // var allIds= [];
    var actionUrl = null;
    if ($('#gFormCreateBill input[name = id]').val()) {
      actionUrl = "${request.contextPath}/${params.controller}/update";
    } else {
      actionUrl = "${request.contextPath}/${params.controller}/create";
    }
    jQuery.ajax({
      type:'post',
      data:jQuery("#gFormCreateBill").serialize(),
      //  data:{dataId: datas,allID: allIds},
      url: actionUrl,
      success:function(data, textStatus) {
          executePostConditionCreateBill(data);
      },
      error:function(XMLHttpRequest, textStatus, errorThrown) {
      },
      complete:function(){
      },
      dataType:'json'
    });
    return false;

  }
    */
  function executePostConditionCreateBill(result) {
      if (result.type == 1) {
        $("#invoice-detail-grid").trigger("reloadGrid");
        reset_form('#gFormCreateBill');
         // $('#invoice-detail-grid').jqGrid('GridUnload');
          $('#invoice-detail-grid').jqGrid('clearGridData');
      }
      MessageRenderer.render(result);
  }
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
              url: "${resource(dir:'createBill', file:'delete')}",
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
  function showCreateBill(entity) {
    $('#gFormCreateBill input[name = id]').val(entity.id);
    $('#gFormCreateBill input[name = version]').val(entity.version);
    
    $('#invoiceNumber').val(entity.invoiceNumber);
    $('#deliveryDate').val(entity.deliveryDate);
    $('#receivableAmount').val(entity.receivableAmount);
    if(entity.customerName){
       $('#customerName').val(entity.customerName.id).attr("selected","selected");
    }
    else{
        $('#customerName').val(entity.customerName);
    }
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
  function executeSearchCreateBill(fieldName, fieldValue){
    if(executePreConditionForSearchCreateBill(fieldName, fieldValue))
    {
      $.ajax({
        type: "POST",
        url: "${resource(dir:'createBill', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
        success: function(entity) {
          executePostConditionForSearchCreateBill(entity, fieldName, fieldValue);
        },
        dataType:'json'
      });
    }
  }
  function executePreConditionForSearchCreateBill(fieldName,fieldValue) {
      // trim field vales before process.
      $('#'+fieldName).val($.trim($('#'+fieldName).val()));
      if(fieldValue == '' || fieldValue == null){
          reset_form("#gFormCreateBill");
          return false;
      }
      return true;
  }
  function executePostConditionForSearchCreateBill(data, fieldName, fieldValue) {
      if (data == null) {
          reset_form("#gFormCreateBill"); // Clear Form
          $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
      } else {
        showCreateBill(data);
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
       $('#create-button-createBill').show();

       $('#invoice-detail-grid').jqGrid('clearGridData');
       $("#jqgrid-grid-unadjusted-invoice").jqGrid('clearGridData');

       $("#TotalAmountInTaka").val('');
       $("#purchaseOrderNumber").val('');
       $("#purchaseOrderDate").val('');
       $("#vatChallanNumber").val('');
       $("#vatChallanDate").val('');
   }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }

  function showUnadjustedInvoices(){
     // setBillNumber();
      var customerName = $("#custId").val();
      if(!customerName){
          MessageRenderer.renderErrorText("Please select customer name.");
      }

      $("#jqgrid-grid-unadjusted-invoice").setGridParam({
          url: '${resource(dir:'invoice', file:'listInvoiceByCustomerId')}?&customerId=' + $("#custId").val()
      });
      $("#jqgrid-grid-unadjusted-invoice").trigger("reloadGrid");
  }

  function buttonStatusChange() {
     // alert('checked')
      var isSelected = true;
      $('.disChck').each(function () {
          if (this.checked) {
              $('#select-button').attr('value', 'Deselect All');
              isSelected = false
          }
      });

      if (isSelected) {
          $('#select-button').attr('value', 'Select All')
      }
  }

  function checkUnCheck() {
      //alert('Unchecked')

          $('.chk').each(function () {
              if(this.checked) {
                  this.disable = true;
              }
             // $('#select-button').attr('value', 'Deselect All')
          })
  }

  function executeAjaxCreateBill() {
      //setBillNumber();
      ids = $("#invoice-detail-grid").jqGrid('getDataIDs');
      var myData = $('#invoice-detail-grid').jqGrid('getRowData');

      var gridCollection = $('#invoice-detail-grid').jqGrid('getRowData');
      var dataLength = gridCollection.length;
      if(dataLength==0){
          MessageRenderer.renderErrorText("At first add invoice.");
          return;
      }
      var totalAmountInTk=0;
    //alert(dataLength)
      var data= jQuery("#gFormCreateBill").serialize();
      for (var i = 0; i < dataLength; i++) {
//                debugger
//            data = data + '&items.costCenter[' + i + '].id=' + gridCollection[i].id;

          data = data + '&items.bill[' + i + '].invoiceNo=' + gridCollection[i].InvoiceNo;
          data = data + '&items.bill[' + i + '].po=' + gridCollection[i].purchaseOrderNumber;
          data = data + '&items.bill[' + i + '].pod=' + gridCollection[i].purchaseOrderDate;

          data = data + '&items.bill[' + i + '].vatChallanNo=' + gridCollection[i].vatChallanNumber;
          data = data + '&items.bill[' + i + '].vatChallanDate=' + gridCollection[i].vatChallanDate;
          data = data + '&items.bill[' + i + '].deliveryDate=' + gridCollection[i].deliveryDate;

          data = data + '&items.bill[' + i + '].receivableAmount=' + gridCollection[i].receivableAmount;

          totalAmountInTk+=gridCollection[i].receivableAmount;
      }
      data = data + '&quantity=' + dataLength;
      //data = data + '&deleted=' + deletedIds;

      if(!executePreConditionCreateBill()){
          return false;
      }
     // alert('5th')
      var actionUrl = null;

      actionUrl = "${request.contextPath}/${params.controller}/create";
      //actionUrl = "createBill/create";
      SubmissionLoader.showTo();
      jQuery.ajax({
          type: 'post',
          data: data,
          url: actionUrl,
          success: function (data, textStatus) {
              executePostConditionCreateBill(data);
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
              if(XMLHttpRequest.status = 0){
                  $("#invoice-detail-grid").trigger("reloadGrid");
                  reset_form('#gFormCreateBill');
                  $('#invoice-detail-grid').jqGrid('clearGridData');
                  MessageRenderer.renderErrorText("Network Problem: Time out");
              }else{
                  MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
              }
          },
          complete: function () {
              SubmissionLoader.hideFrom();
          },
          dataType: 'json'
      });
      return false;
  }

  function gridForBill() {
      $("#invoice-detail-grid").jqGrid({
          datatype: "json",
          colNames:[
              'ID',
              'Invoice Number',
              'PO Number',
              'PO Date',
              'VAT Challan Number',
              'VAT Challan Date',
              'Delivery Date',
              'Total Amount In Taka'
             // ,'Remove'
          ],
          colModel:[
              {name: 'id', index: 'id', width: 0, align: 'left', hidden: true},
              {name:'InvoiceNo',index:'InvoiceNo', width:135},
              {name:'purchaseOrderNumber', index:'purchaseOrderNumber',width:110,align:'right'},
              {name:'purchaseOrderDate', index:'purchaseOrderDate',width:110,align:'right'},
              {name:'vatChallanNumber',index:'vatChallanNumber', width:120,align:'right'},
              {name:'vatChallanDate', index:'vatChallanDate',width:110,align:'right'},
              {name:'deliveryDate', index:'deliveryDate',width:110,align:'right',  formatter: 'date', formatoptions: {newformat: 'd-m-Y'}},
              {name:'receivableAmount', index:'receivableAmount',formatter: 'number', formatoptions: { decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 4, defaultValue: '0.0000' },width:120,align:'right'}
             //, {name: 'remove', index: 'remove', width: 70, align: 'center'}

          ],
          rowNum:10,
          rowList:[10,20,30],
          pager: '#invoice-detail-grid-pager',
          sortname: 'purchaseOrderNumber',
          viewrecords: true,
          sortorder: "desc",
          caption: "New Bills" + '<span class="mendatory_field"> *</span>',
          autowidth: false,
          height: 100,
          scrollOffset: 0,
          loadComplete: function () {
          },
          onSelectRow: function (rowid, status) {

          }
      });
      $("#invoice-detail-grid").jqGrid('navGrid', '#invoice-detail-grid-pager', {
          edit: false,
          add: false,
          del: false,
          search: false
      });
  }
  var rowId=0;

  function addNewItemToCollectionGrid() {

          $("#invoice-detail-grid").show();
          //ids = $("#jqgrid-grid-issueMaintenance").jqGrid('getDataIDs');
          var myData = $('#jqgrid-grid-unadjusted-invoice').jqGrid('getRowData');
          //alert(JSON.stringify(myData));

          var PO = $("#purchaseOrderNumber").val();
          var POD = $("#purchaseOrderDate").val();
          var VN = $("#vatChallanNumber").val();
          var VND = $("#vatChallanDate").val();

         /* if (PO == "") {
              MessageRenderer.renderErrorText("Enter Purchase Order Number.");
              return;
          } else if (POD == "") {
              MessageRenderer.renderErrorText("Enter Purchase Order Date.");
              return;
          } else if (VN == "") {
              MessageRenderer.renderErrorText("Enter VAT Challan Number.");
              return;
          } else if (VND == "") {
              MessageRenderer.renderErrorText("Enter VAT Challan Date.");
              return;
          }*/


          var total = 0.00;
          var id = 0;
          var count=0;
          var dt = '';
          var inv = '';
          var receivable = '';
          for (var j = 0; j < myData.length; j++) {

              /*if (myData[j].chk == 'True') {
                  alert(myData[j].id)
                  $("#".myData[j].id).attr('disabled',true);
                   // return;
                 }*/

          if (myData[j].chk == 'True') {
              dt=myData[j].deliveryDate;
              inv=myData[j].invoiceNo;
              receivable= myData[j].receivableAmount;
              id=myData[j].id;
              total=total+ parseFloat(receivable);

              count++;
          var myGrid =  $("#invoice-detail-grid");

          //alert('dsfdsfs')
          var dataItem = {
              id: id,
              InvoiceNo: inv,
              purchaseOrderNumber: PO,
              purchaseOrderDate: POD,
              vatChallanNumber: VN,
              vatChallanDate: VND,
              deliveryDate: dt,
              receivableAmount:receivable,
              select: true,
              remove: '<a style="float:right;margin-right:20px;" class="element-input button-delete" title="Remove"  onclick="removeBill(\'' + newBills + '\')"></a>'
          }

          myGrid.addRowData(newBills, dataItem, "last");
          newBills--;
          gridCollection = myGrid.jqGrid('getRowData');
           //   checkUnCheck();
             // $('input[type=checkbox]').attr('disabled',true);
          }
              jQuery('#jqgrid-grid-unadjusted-invoice').delRowData(id)
      }

      if(count<1){
              MessageRenderer.renderErrorText("At first select Unadjusted Invoice.");
      }
      setBillNumber();
      var ttamnt= $("#TotalAmountInTaka").val();
      if(ttamnt){
          $("#TotalAmountInTaka").val(parseFloat(ttamnt)+total);
      } else{
          $("#TotalAmountInTaka").val(total);
      }
  }
   // jQuery('#gridPreSeleccion').delRowData(ids[i])

  function removeBill(rowId) {
      $("#invoice-detail-grid").jqGrid("delRowData", rowId);
  }
    $(document).ready(function () {
        gridForBill();

        $("#gFormUnadjustedInvoice").validationEngine('attach');
//        reset_form("#gFormUnadjustedInvoice");
        $("#jqgrid-grid-unadjusted-invoice").jqGrid({
            %{--url: '${resource(dir:'mushak', file:'list')}',--}%
            datatype: "json",
            colNames: [
                // 'SL',
                'ID',
                'Check',
                'Invoice No',
                'Delivery Date',
                //'Invoice Amount',
                'Receivable Amount'
            ],
            colModel: [
                // {name:'sl',index:'sl', width:30, sortable:true, align:'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {
                    name: 'chk', index: 'chk', width: 50, align: "right", edittype: 'checkbox',
                    formatter: "checkbox", editoptions: {value: "True:False"},
                    editable: true, formatoptions: {disabled: false}
                },
                //  {name: 'checkGood', index: 'checkGood', width: 20},
                {name: 'invoiceNo', index: 'invoiceNo', width: 250, align: 'left'},
                {name: 'deliveryDate', index: 'deliveryDate', width: 260, align: 'center',  formatter: 'date', formatoptions: {newformat: 'd-m-Y'}},
               // {name: 'invoiceAmount', index: 'invoiceAmount', width: 100, align: 'right'},
                {name: 'receivableAmount', index: 'receivableAmount', formatter: 'number', formatoptions: { decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 4, defaultValue: '0.0000' }, width: 260, align: 'right'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-unadjusted-invoice',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Unadjusted Invoice List",
            autowidth: false,
            //autoheight: true,
            height: 100,
            scrollOffset: 0,
            altRows: true,
            afterSaveCell: function (id, name, val, iRow, iCol) {
            },
            loadComplete: function () {
                /*var ids = $("#jqgrid-grid-unadjusted-invoice").jqGrid('getDataIDs');
                 for (key in ids) {

                 var rowData = $("#jqgrid-grid-unadjusted-invoice").getRowData(ids[key]);
                 alert(ids)
                 $("#jqgrid-grid-unadjusted-invoice").jqGrid('setRowData', ids[key], {checkGood: '<input type="checkbox"  onchange="buttonStatusChange();"  class="disChck" value="' + ids[key] + '" />'});
                 }*/
            },
            onSelectRow: function (rowid, status) {
            }
        });
        //populateCustomer();
    });


    function selectGeoLocation(id){
        if (id == '') {
            var options = '<option value="">--Select--</option>';
            $("#territoryGeoLocation").html(options);
            return false;
        }
        else {
          //   alert(id)
            var options = '<option value="">Please Select</option>';
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'territorySubArea', file:'fetchTerritorySubAreaList')}?id=" + id,
            success: function (data) {
                options = '<option value="">Select Territory Geo Location</option>';
                $.each(data, function (key, val) {
                     // alert(JSON.stringify(val) )
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
//                  alert(options)
                })
                $("#territoryGeoLocation").html(options);
            },

            dataType: 'json'
        });

    }


    function selectCustomerCategory(id){
        if (id == '') {
            var options = '<option value="">--Select--</option>';
            $("#customerCategory").html(options);
            return false;
        }
        else {
           // alert(id)
            var options = '<option value="">Please Customer Category</option>';
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'territorySubArea', file:'fetchCustomerCategoryByTerritorySubArea')}?id=" + id,
            success: function (data) {
                options = '<option value="">Select Customer Category</option>';
                $.each(data, function (key, val) {
                    //alert(JSON.stringify(val) )
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
//                  alert(options)
                })
                $("#customerCategory").html(options);
            },

            dataType: 'json'
        });

    }
    function selectCustomerByCategory(id){
        if (id == '') {
            var options = '<option value="">--Select--</option>';
            $("#customerName").html(options);
            return false;
        }
        else {
            //alert(id)
            var options = '<option value="">Please Select</option>';
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'customerMaster', file:'fetchCustomerInfoByCategory')}?id=" + id,
            success: function (data) {
                options = '<option value="">Select SubType</option>';
                $.each(data, function (key, val) {
                    //alert(JSON.stringify(val) )
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
//                  alert(options)
                })
                $("#customerName").html(options);
            },

            dataType: 'json'
        });

    }

    function setBillNumber(){
        //alert("fds")
        var actionUrl = "${request.contextPath}/${params.controller}/searchBill";
        $.ajax({
            type: "POST",
            //url: "${resource(dir:'createBill', file:'searchMaxBill')}",
            url:actionUrl,
            success: function (bill) {
            //    alert(JSON.stringify(bill));
           // alert('6th')
                if(!bill){
                   // alert('6th')
                    $('#billNumber').val("Bill"+1);
                }
                var patrn= bill.billNumber;
                var result = patrn.substring(4);
                var bil= ++result;
                $('#billNumber').val("Bill"+bil);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete:function(){
            },
            dataType:'json'
        });
    }


    function selectCustomerName(id){
        if(id) {
            $("#customerName").val('');
            $("#customerId").val('');
        }
    }
    function selectCustomerId(id){

        $.ajax({
            type: "POST",
            url: "${resource(dir:'customerMaster', file:'fetchCustomerCodeInfo')}?cusId="+ id,
            //url:actionUrl,
            success: function (customer) {
                //alert(JSON.stringify(bill));

                var code= customer.code;

                $("#customerId").val(code);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete:function(){
            },
            dataType:'json'
        });

    }

    function generateBillForCreateBill() {
        ids = $("#invoice-detail-grid").jqGrid('getDataIDs');
        var myData = $('#invoice-detail-grid').jqGrid('getRowData');

        // alert(ids)
        var gridCollection = $('#invoice-detail-grid').jqGrid('getRowData');
        var dataLength = gridCollection.length;

        var totalAmountInTk=0;
        // alert(dataLength)
        var data= jQuery("#gFormCreateBill").serialize();
        for (var i = 0; i < dataLength; i++) {
//                debugger
//            data = data + '&items.costCenter[' + i + '].id=' + gridCollection[i].id;

            data = data + '&items.bill[' + i + '].invoiceNo=' + gridCollection[i].InvoiceNo;
            data = data + '&items.bill[' + i + '].po=' + gridCollection[i].purchaseOrderNumber;
            data = data + '&items.bill[' + i + '].pod=' + gridCollection[i].purchaseOrderDate;

            data = data + '&items.bill[' + i + '].vatChallanNo=' + gridCollection[i].vatChallanNumber;
            data = data + '&items.bill[' + i + '].vatChallanDate=' + gridCollection[i].vatChallanDate;
            data = data + '&items.bill[' + i + '].deliveryDate=' + gridCollection[i].deliveryDate;

            data = data + '&items.bill[' + i + '].receivableAmount=' + gridCollection[i].receivableAmount;

            totalAmountInTk+=gridCollection[i].receivableAmount;
        }
        data = data + '&quantity=' + dataLength;

       // var actionUrl = actionUrl = "${request.contextPath}/${params.controller}/create";
        var actionUrl = "${resource(dir:'createBillTemp', file:'create')}";
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                generateBill();
            },
            dataType: 'json'
        });
        return false;
    }



    function generateBill() {
        //var po = $("#purchaseOrderNumber").val();
        // alert(po)
        SubmissionLoader.showTo();
        window.open("${resource(dir:'createBillTemp', file:'viewReportBeforeCreateBill')}?format=PDF");
        SubmissionLoader.hideFrom();
    }


/*
    $('#customerId').blur(function () {
        if (!$('#customerCategory').val()){
            MessageRenderer.renderErrorText("Customer Category Can Not Be Blank.");
        }
        if ($('#customerId').val() == '') {
            clearCustomerData();

        }
    });
    jQuery('#customerId').autocomplete({
        minLength: '1',
        source: function (request, response) {
            var data = {searchKey: request.term};
            var url = '${resource(dir:'createBill', file:'listCustomerByCode')}?query=' + $('#customerId').val() + "&customerCategory=" + $('#customerCategory').val();
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
        $("#customerId").val(code);
        $("#customerName").val(cusName);
    }*/

    $('#customerName').blur(function () {
        if (!$('#territoryGeoLocation').val()){
            MessageRenderer.renderErrorText("Territory Geo Location Can Not Be Blank.");
        }
        if (!$('#customerCategory').val()){
            MessageRenderer.renderErrorText("Customer Category Can Not Be Blank.");
        }
        if ($('#customerName').val() == '') {
            clearCustomerData();

        }
    });
    jQuery('#customerName').autocomplete({
        minLength: '1',
        source: function (request, response) {
            var data = {searchKey: request.term};
         // alert($('#territoryGeoLocation').val())
            var url = '${resource(dir:'createBill', file:'listCustomerByCode')}?query=' + $('#customerName').val() + "&customerCategory=" + $('#customerCategory').val() + "&territorySubAreaId=" + $('#territoryGeoLocation').val();
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
        var nm=cusName+"-"+code;
       // alert(customerId)
        $("#custId").val(customerId);
        $("#customerId").val(code);
        $("#customerName").val(nm);
    }

    $('#search-btn-customer-register-id').click(function(){
        EmployeeRegister.popupEmployeeListPanel();
    });
    $("#tabs span.ui-icon-close").live("click", function () {

        var index = $("li", $tabs).index($(this).parent());
        if (index > 0) {
            $("#dialog-confirm").dialog({
                resizable: false,
                height: 140,
                modal: true,
                autoOpen: false,
                buttons: {
                    "Yes": function () {
                        $(this).dialog("close");
                        $tabs.tabs("remove", index);
                    },
                    Cancel: function () {
                        $(this).dialog("close");
                    }
                }
            });
            $("#dialog-confirm").dialog('open');
        }
    });
    var EmployeeRegister = {

        employeeCoreInfoId: null,
        popupEmployeeListPanel: function(){
            var territorySubAreaId= $("#territoryGeoLocation").val();
            var customerCategory= $("#customerCategory").val();
            //alert(customerCategory)
            var url = "${request.contextPath}/${params.controller}/popupCustomerListPanel";
            var params = {territorySubAreaId:territorySubAreaId,customerCategory:customerCategory};
            DocuAjax.html(url, params, function(html){
                $.fancybox(html);
            });


        }
        /*,

        showEmployeeDetailsById: function(employeeCoreInfoId){
            //alert(employeeCoreInfoId)
            EmployeeRegister.employeeCoreInfoId = employeeCoreInfoId
            EmployeeRegister.showEmployeeDetail();
            EmployeeRegister.employeeCoreInfoId = null
        }
        ,
        showEmployeeDetail: function(){
            if(!EmployeeRegister.employeeCoreInfoId){
                MessageRenderer.renderErrorText("Select an employee.", "Employee Detail Information");
                return false;
            }
            var url = "${request.contextPath}/${params.controller}/edit";
            var params = {id:EmployeeRegister.employeeCoreInfoId};
            AjaxLoader.showTo('popEmpDetails');
            DocuAjax.html(url, params, function(html){
                $('#popEmpDetails').html(html);
            });
        },
        setEmployeeInformation: function(employeeCoreInfoId, employeeCoreInfo){
            $("#employeeCoreInfo").val(employeeCoreInfo);
            EmployeeRegister.employeeCoreInfoId = employeeCoreInfoId;
        }*/
    };

    //CustomerInfo.setCustomerInformation(customerCodeInfoId, name);

  /*  function setCustomerInformation(customerCodeInfoId, name, code){
        $('#customerName').val(name);
        $('#customerId').val(code);
    }*/
</script>