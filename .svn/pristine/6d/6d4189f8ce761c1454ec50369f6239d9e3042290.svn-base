<%@ page import="com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">

    var toList;
    function setDate() {
//        var date = new Date();
//
//        var month = (date.getMonth() + 1);
//
//        if (month < 10) {
//            month = '0' + month;
//        }
//
//        var day = date.getDate();
//
//        if (day < 10) {
//            day = '0' + day;
//        }
//        var year = date.getFullYear();

//        $("#transferDate").val(day + "-" + month + "-" + year);
    }
    $(document).ready(function () {
        selectDistributionPoint();

        var idd = document.getElementById("distributionPoint").value
        if(idd){
            setInventoryByDistributionPoint(idd)
        }

       // getDistributionPointList();

        $('#ui-widget-header-text').html('SetupIncentive');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#subInventoryToSubInventoryTransferForm").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#subInventoryToSubInventoryTransferForm").validationEngine('attach');


//        Date Range
//        setDate();
//        var d = new Date();
        $("#transferDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
//                    minDate: +1
                });

        $('#transferDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        /*$("#transferDate").datepicker(
            {
                defaultDate: d,
                dateFormat: 'dd-mm-yy',
                changeMonth: true,
                changeYear: true
            });*/

        //$('#transferDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        //$("#transferDate").datepicker('setDate', new Date());
//        Product List
        $("#jqgrid-product-grid").jqGrid({
            url: '',
            datatype: "local",
            colNames: [
                'Product Name',
                'Product ID',
                'dpsId',
                'ID',
                'Batch Number',
                'Price',
                'Available Qty',
                'Transfer Qty',
                'Total Price'
            ],
            colModel: [
                {name: 'productName', index: 'productName', width: 120, align: 'left'},
                {name: 'productId', index: 'productId', width: 120, align: 'left', hidden:true},
                {name: 'dpsId', index: 'dpsId', width: 120, align: 'left', hidden:true},
                {name: 'productCode', index: 'productCode', width: 130, align: 'left'},
                {name: 'batch_no', index: 'batch_no', width: 100, align: 'left'},
                {name: 'price', index: 'price', width: 100, align: 'left'},
                {name: 'quantity', index: 'quantity', width: 90, align: 'left'},
                {name: 'transferQty', index: 'transferQty', width: 90, align: 'left', editable:true,
                    edittype:"text", editoptions:{
                        value:0, class:'gridInputMargin',
                        size: 10, maxlength: 10,
                        dataInit: function(element) {
                            $(element).keypress(function(e){
                                if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                                    return false;
                                }
                            });
                        }
                    }
                },
                {name: 'totalPrice', index: 'totalPrice', width: 100, align: 'left'}
            ],
            rowNum: 200,
            rowList: [50, 100, 150, 200, 250, 300],
            pager: '#jqgrid-product-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Available Products in Source Sub-Inventory",
            autowidth: false,
            height: true,
            scrollOffset: 0,
            multiselect: true,
            loadComplete: function () {
                    var $this = $(this), ids = $this.jqGrid('getDataIDs'), i, l = ids.length;
                    for (i = 0; i < l; i++) {
                        $this.jqGrid('editRow', ids[i], true);
                    }
            },
            onSelectRow: function (rowid, status) {

            }
        });
        $("#tb-product-grid").jqGrid('navGrid', '#jqgrid-product-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });
    });

    function getSubInventoryList(id){
        $("#subInventoryToIdText").val('');
        $('#subInventoryFromId').val('');
        $('#subInventoryToId').val('');
        $('#subInventoryFrom').html("");
        $('#subInventoryTo').html("");
        $('#subInventoryFrom').append('<option value="">Select sub-inventory from...</option>');
        $('#subInventoryTo').append('<option value="">Select sub-inventory to...</option>');
        jQuery("#jqgrid-product-grid").clearGridData();

        if(id){
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: {id:id},
                url:  "${request.contextPath}/${params.controller}/getSubInventoryListByInventoryId",
                success: function (data, textStatus) {
                    //alert("JJ"+data.length)
                    if(data[0]){
                        $.each(data, function(key, val){
                            $('#subInventoryFrom').append('<option value="'+val.id+'">'+ val.nameTypeName +'</option>');
                           // $("#subInventoryToIdText").val(val.typeName)
                            //$('#subInventoryTo').append('<option value="'+val.id+'">'+ val.name +'</option>');
//                            if(val.typeName == 'Salable FG'){
//                                refId = val.id;
//                            }
                        });
                    }
                    //toList=data[0].nameTypeName;
                    toList = data
                   // alert(toList)
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function () {
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        }
    }

    function setSubInventoryFromId(id){
        $("#subInventoryToIdText").val('');
        $('#subInventoryFromId').val(id);
        var inventoryId=$('#inventory').val();
       // $('#subInventoryTo').html("");

        var subInventoryFromTxt=$("#subInventoryFrom option:selected").text();

        var dmg = new RegExp("Damage");
        //var dmgRslt = dmg.exec(subInventoryFromTxt);
        var isDmgRslt = dmg.test(subInventoryFromTxt);

        var mrktn = new RegExp("Market Return");
       // var mrkRslt = mrktn.exec(subInventoryFromTxt);
        var isMrkRslt = mrktn.test(subInventoryFromTxt);

        var salable= new RegExp("Salable");
        var isSalableRslt = salable.test(subInventoryFromTxt);

        var size;
        var options = '';
        if (toList != '') {
            size = toList.length;

            options = '<option value="">Please Select</option>';
            for (var i = 0; i < size; i++) {
                //var dmgStr = dmg.exec(toList[i].nameTypeName);
                var isDmg = dmg.test(toList[i].nameTypeName);
                //var mrkRsltList = mrktn.exec(toList[i].nameTypeName);
                var isMrk = mrktn.test(toList[i].nameTypeName);
                var isSalable = salable.test(toList[i].nameTypeName);


                if(isSalableRslt){
                    if(!isSalable){
                        options += '<option value="' + toList[i].id + '">' + toList[i].nameTypeName + '</option>';
                    }
                }
                if(isDmgRslt){
                        if(!isDmg){
                            if(!isSalable){
                                options += '<option value="' + toList[i].id + '">' + toList[i].nameTypeName + '</option>';
                            }
                        }
                    }
                if(isMrkRslt){
                    if(!isMrk){
                        if(!isSalable){
                            options += '<option value="' + toList[i].id + '">' + toList[i].nameTypeName + '</option>';
                        }
                    }
                }
            }
            $("#subInventoryTo").html(options);
        }
        getAllProductListBySubInventoryId($('#subInventoryFromId').val());
    }

    /*
    function setSubInventoryFromId(id){
        $("#subInventoryToIdText").val('');
        $('#subInventoryFromId').val(id);
        var inventoryId=$('#inventory').val();
        $('#subInventoryTo').html("");

         var subInventoryFromTxt=$("#subInventoryFrom option:selected").text();
         var dmg = new RegExp("[Damage]");
         var dmgRslt = dmg.test(subInventoryFromTxt);

         var mrktn = new RegExp("[Market Return]");
         var mrkRslt = mrktn.test(subInventoryFromTxt);

         jQuery("#jqgrid-product-grid").clearGridData();
         SubmissionLoader.showTo();
         jQuery.ajax({
         type: 'post',
         data: {id:id,dmgRslt:dmgRslt, mrkRslt:mrkRslt,  inventoryId:inventoryId},
         url:  "${request.contextPath}/${params.controller}/getSubInventoryListByInventoryIdForTo",
         success: function (data, textStatus) {

         // alert(JSON.stringify(data[0]))
         //alert(data[0])
         $('#subInventoryTo').append('<option value="">'+"Select Sub Inventory To" +'</option>');
         if(data[0]){
         $.each(data, function(key, val){
         $('#subInventoryTo').append('<option value="'+val.id+'">'+ val.nameTypeName +'</option>');

         if(dmgRslt){
         var subInventoryToTxt=val.nameTypeName;
         var sal = new RegExp("Salable");
         var ToRes = sal.test(subInventoryToTxt);

         if(ToRes){
         $("#subInventoryTo option[value='subInventoryToTxt']").remove();
         }
         }

          if(mrkRslt){
         var subInventoryToTxt=val.nameTypeName;
         var sal = new RegExp("Salable");
         var ToRes = sal.test(subInventoryToTxt);

         if(ToRes){
         $("#subInventoryTo option[value=subInventoryToTxt]").remove();
         }

         }
         //$("#subInventoryTo").html(options);
         })
         }
         },
         error: function (XMLHttpRequest, textStatus, errorThrown) {
         if(XMLHttpRequest.status = 0){
         MessageRenderer.renderErrorText("Network Problem: Time out");
         } else{
         MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
         }
         },
         complete: function (data, textStatus) {
         SubmissionLoader.hideFrom();
         },
         dataType: 'json'
         });

        getAllProductListBySubInventoryId($('#subInventoryFromId').val());

    }
*/

    function setSubInventoryToId(id){
     //   alert($('#subInventoryFromId').val())
        $('#subInventoryToId').val(id);
    }

    /*$("#subInventoryToId").change(function () {

        var inv=$('#subInventoryToId').val();
        alert(inv)
        setSubInventoryToId(inv);
    });*/

    function setInventoryByDistributionPoint(id){
        //alert('setInventoryByDistributionPoint')
        jQuery.ajax({
            type: 'post',
            url:  "${request.contextPath}/${params.controller}/getInventoryByDistributionPointId",
            data: {distId:id},
            success: function (data) {
                var options = '<option value="">Select Inventory Name </option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
                //  alert(val.id)
                })
                $("#inventory").html(options);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            }
           // dataType: 'json'
        });

    }

    function getAllProductListBySubInventoryId(id){
        jQuery("#jqgrid-product-grid").clearGridData();
        if(id){
            %{--$("#jqgrid-product-grid").jqGrid().setGridParam({url: "${resource(dir:'subInventoryToSubInventoryTransfer', file:'getAllProductListBySubInventoryId')}?"--}%
                    %{--+ 'id=' + id + '&dpId=' + $('#distributionPoint').val(),--}%
                %{--datatype: "json"--}%
            %{--}).trigger("reloadGrid", [--}%
                        %{--{page: 1}--}%
                    %{--]);--}%
//            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: {id:id,dpId:$('#distributionPoint').val()},
                url:  "${request.contextPath}/${params.controller}/getAllProductListBySubInventoryId",
                success: function (data, textStatus) {
                    if(data[0]){
                        jQuery("#jqgrid-product-grid")
                                .jqGrid('setGridParam',
                                {
                                    datatype: 'local',
                                    data: data
                                })
                                .trigger("reloadGrid");
                    }else{
                        jQuery("#jqgrid-product-grid").clearGridData();
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function () {
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        }
    }

    function checkQuantity(){
        if(parseFloat($('#transferQty').val()) > parseFloat(transferQty)){
            return '* Transfer quantity cannot greater than available product quantity.'
        }
    }

    function transferProductPrecondition(){
        if(!$("#subInventoryToSubInventoryTransferForm").validationEngine('validate')){
            return false;
        }
        return true;
    }

    function transferProduct(){
        if(!transferProductPrecondition()){
            return false;
        }

        var allData = {};

        allData["dpId"] = $("#distributionPoint").val();
        allData["inventoryId"] = $("#inventory").val();
        allData["transferDate"] = $("#transferDate").val();
        allData["subInventoryFromId"] = $("#subInventoryFrom").val();
        allData["subInventoryToId"] = $("#subInventoryTo").val();

        var selectedProductIds = $('#jqgrid-product-grid').jqGrid('getGridParam', 'selarrrow');
        var productIds = filterListOfIds(selectedProductIds);
        var total = 0;
        var shunno = 0;

        for(var i = 0; i < productIds.length; i++){
            var transferQty = $('#'+productIds[i]+'_transferQty').val();
            var quantity = $("#jqgrid-product-grid").getRowData(productIds[i]).quantity;
            var productName = $("#jqgrid-product-grid").getRowData(productIds[i]).productName;

            if(parseInt(transferQty)  > parseInt(quantity)){
             //   if(transferQty  >quantity){
              //  alert(transferQty  +"      "+quantity)
                MessageRenderer.render({
                    "messageBody": productName + " Available Quantity Exceeded.",
                    "messageTitle": "Sub Inventory to Sub Inventory Transfer",
                    "type": "0"
                });
                return false;
            }

            allData["products.product["+ i +"].productId"] = $("#jqgrid-product-grid").getRowData(productIds[i]).productId;
            allData["products.product["+ i +"].dpsId"] = $("#jqgrid-product-grid").getRowData(productIds[i]).dpsId;
            allData["products.product["+ i +"].batchNo"] = $("#jqgrid-product-grid").getRowData(productIds[i]).batch_no;
            allData["products.product["+ i +"].unitPrice"] = $("#jqgrid-product-grid").getRowData(productIds[i]).price;
            allData["products.product["+ i +"].transferQty"] = transferQty;
            total += parseFloat(transferQty);
            if(parseFloat(transferQty) == 0){
                shunno = 1;
            }
        }

        if(shunno == 1){
            MessageRenderer.render({
                "messageBody": "Notice that the transfer quantity of ONE if not SEVERAL of the selected rows is ZERO(0).",
                "messageTitle": "Sub Inventory to Sub Inventory Transfer",
                "type": "2"
            });
            return false;
        }

        if(isNaN(total)){
            MessageRenderer.render({
                "messageBody": "Deselect the BLANK transfer quantity rows or provide a VALUE.",
                "messageTitle": "Sub Inventory to Sub Inventory Transfer",
                "type": "2"
            });
            return false;
        }

        if(productIds == ''){
            MessageRenderer.render({
                "messageBody": "Please select at least one product from the list.",
                "messageTitle": "Sub Inventory to Sub Inventory Transfer",
                "type": "0"
            });
            return false;
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url:  "${request.contextPath}/${params.controller}/transferProduct",
            success: function (data, textStatus) {
                MessageRenderer.render(data);
                reset_form_data("#subInventoryToSubInventoryTransferForm");
                jQuery("#jqgrid-product-grid").clearGridData();
                setDate();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    eset_form_data("#subInventoryToSubInventoryTransferForm");
                    jQuery("#jqgrid-product-grid").clearGridData();
                    setDate();
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function filterListOfIds(idsArray){
        var ids = [];
        $.each(idsArray, function(i, e) {
            if ($.inArray(e, ids) == -1) ids.push(e);
        });
        ids = ids.filter(function(n){ return n != undefined });
        return ids;
    }

    function reset_form_data(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                //this.value = "";
                this.value = this.defaultValue;
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName+' select').val('');
    }
    function selectDistributionPoint() {

        var size;
        var options = '';
        if ('${disList}' != '') {
            size = ${distSize};
            if (size == 1) {
                options = '<option value="' + ${disList}[0].id + '">' + ${disList}[0].dpName + '</option>';
            } else {
                options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${disList}[i].id + '">' + ${disList}[i].dpName     + '</option>';
                }
            }
            $("#distributionPoint").html(options);
        }
    }

  /*  function getDistributionPointList(){
       // jQuery("#jqgrid-product-grid").clearGridData();
        //alert('dsfsdf')

        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'branchStockReport', file:'fetchDistributionPoint')}",
            success: function (data) {
                options = '<option value="">Select Distribution Point</option>';
                $.each(data, function (key, val) {
                    // alert(JSON.stringify(val) )
                    options += '<option value="' + val.id + '">' + val.dpName + '</option>';
//                  alert(options)
                })
                $("#distributionPoint").html(options);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert('error')
            }

            dataType: 'json'
        });
    }
*/

</script>