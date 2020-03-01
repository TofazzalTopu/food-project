<script type="text/javascript" language="Javascript">

    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormChartOfAccountLayer").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormChartOfAccountLayer").validationEngine('attach');
//        reset_form("#gFormMarketReturn");
        %{--$("#jqgrid-grid-marketReturn").jqGrid({--}%
        %{--url: '${resource(dir:'marketReturn', file:'list')}',--}%
        %{--datatype: "json",--}%
        %{--colNames: [--}%
        %{--'Stock ID',--}%
        %{--'Product ID',--}%
        %{--'Invoice ID',--}%

        %{--'Product',--}%
        %{--'Product Code',--}%
        %{--'Invoice Code',--}%
        %{--'Batch',--}%
        %{--'MR Type',--}%
        %{--'Quantity',--}%
        %{--'Reference',--}%
        %{--'Remarks',--}%
        %{--''--}%
        %{--],--}%
        %{--colModel: [--}%
        %{--{name: 'stockId', index: 'stockId', width: 0, hidden: true},--}%
        %{--{name: 'productId', index: 'productId', width: 0, hidden: true},--}%
        %{--{name: 'invoiceId', index: 'invoiceId', width: 0, hidden: true},--}%
        %{--{name: 'product', index: 'product', width: 100, align: 'left'},--}%
        %{--{name: 'productCode', index: 'productCode', width: 120, align: 'left'},--}%
        %{--{name: 'invoiceNo', index: 'invoiceNo', width: 120, align: 'left'},--}%
        %{--{name: 'batch', index: 'batch', width: 40, align: 'left'},--}%
        %{--{name: 'mrType', index: 'mrType', width: 80, align: 'left'},--}%
        %{--{name: 'quantity', index: 'quantity', width: 55, align: 'right'},--}%
        %{--{name: 'reference', index: 'reference', width: 80, align: 'left'},--}%
        %{--{name: 'remarks', index: 'remarks', width: 80, align: 'left'},--}%
        %{--{name: 'delete', index: 'reference', width: 30, align: 'center', sortable: false}--}%
        %{--],--}%
        %{--rowNum: 10,--}%
        %{--rowList: [10, 20, 30],--}%
        %{--pager: '#jqgrid-pager-marketReturn',--}%
        %{--sortname: 'id',--}%
        %{--viewrecords: true,--}%
        %{--sortorder: "desc",--}%
        %{--caption: "All Added Product",--}%
        %{--autowidth: true,--}%
        %{--autoheight: true,--}%
        %{--scrollOffset: 0,--}%
        %{--altRows: true,--}%
        %{--loadComplete: function () {--}%
        %{--},--}%
        %{--onSelectRow: function (rowid, status) {--}%
        %{--executeEditMarketReturn();--}%
        %{--}--}%
        %{--});--}%
        %{--$("#jqgrid-grid-marketReturn").jqGrid('navGrid', '#jqgrid-pager-marketReturn', {--}%
        %{--edit: false,--}%
        %{--add: false,--}%
        %{--del: false,--}%
        %{--search: false--}%
        %{--});--}%
//                .navButtonAdd('#jqgrid-pager-marketReturn', {
//                    caption: "Edit",
//                    buttonicon: "ui-icon-edit",
//                    onClickButton: function () {
//                        executeEditMarketReturn();
//                    },
//                    position: "last"
//                })
//                .navButtonAdd('#jqgrid-pager-marketReturn', {
//                    caption: "Delete",
//                    buttonicon: "ui-icon-del",
//                    onClickButton: function () {
//                        deleteAjaxMarketReturn();
//                    },
//                    position: "last"
//                });
        if('${editable}' == 0){
            $(".button").attr('hidden', 'hidden');
            MessageRenderer.showHeaderMessage("Chart Of Account Layer can not be altered or created due to it already being in use.", 0)
        }
        if('${layerList}' == '') {
            showForm();
        }else{
            loadUpdate();
        }
    });

    function showForm() {
        if('${length}' != '0'){
            $("#maxLength").val(${length});
        }
        if ('${size}' != '0') {
            var val = '';
            var counter = 0;
            for (var i = 0; i < ${size}; i++) {
                val = val + '<tr  class="element_row_content_container lightColorbg pad_bot0">' +
                ' <td> <label class="txtright bold hight1x width1x">' +
                ' <g:message code="division.geoCode.label" default="Layer Number"/>' +
                ' <span class="mendatory_field">*</span> </label> </td> <td>' +
                ' <g:textField name="layerNumber" id="layerNumber' + counter + '" maxlength="30" class="validate[required]" value="' + (counter + 1) + '" readonly="readonly"/> </td>' +
                ' <td> <label class="txtright bold hight1x width1x">' +
                ' <g:message code="division.geoCode.label" default="Layer Name"/>' +
                ' <span class="mendatory_field">*</span> </label> </td> <td>' +
                ' <g:textField name="layerName" id="layerName' + counter + '" maxlength="30" class="validate[required]" value=""/> </td>' +
                '<td> <label class="txtright bold hight1x width1x">' +
                ' <g:message code="division.geoCode.label" default="Layer Code Length"/>' +
                ' <span class="mendatory_field">*</span> </label> </td> <td>' +
                ' <g:textField name="layerCodeLength" id="layerCodeLength' + counter + '" maxlength="30" class="validate[required]" value=""/> </td> </tr>';
                counter++;
            }
            $("#popEmpDetails").html(val);
            $("#layerName0").val('Group');
            $("#layerName0").attr('disabled', true);
        } else {
            return false;
        }
    }

    function loadUpdate(){
        if('${length}' != '0'){
            $("#maxLength").val(${length});
        }
        var list = ${layerList};
        var edit = ${editable};
        var counter = 0;
        var val = '';
        for (var i = 0; i < list.length; i++) {
            val = val + '<tr  class="element_row_content_container lightColorbg pad_bot0">' +
            ' <td> <label class="txtright bold hight1x width1x">' +
            ' <g:message code="division.geoCode.label" default="Layer Number"/>' +
            ' <span class="mendatory_field">*</span> </label> </td> <td>' +
            ' <g:hiddenField name="layerId" id="layerId' + counter + '" value=" ' + list[i].id + ' "/>' +
            ' <g:textField name="layerNumber" id="layerNumber' + counter + '" maxlength="30" class="validate[required]" value="' + list[i].layer_number + '" readonly="readonly"/> </td>' +
            ' <td> <label class="txtright bold hight1x width1x">' +
            ' <g:message code="division.geoCode.label" default="Layer Name"/>' +
            ' <span class="mendatory_field">*</span> </label> </td> <td>' +
            ' <g:textField name="layerName" id="layerName' + counter + '" maxlength="30" class="validate[required]" value="' + list[i].layer_name + '"/> </td>' +
            '<td> <label class="txtright bold hight1x width1x">' +
            ' <g:message code="division.geoCode.label" default="Layer Code Length"/>' +
            ' <span class="mendatory_field">*</span> </label> </td> <td>' +
            ' <g:textField name="layerCodeLength" id="layerCodeLength' + counter + '" maxlength="30" class="validate[required]" value="' + list[i].layer_code_length + '"/> </td> </tr>';
            counter++;
        }
        $("#popEmpDetails").html(val);
        $("#layerName0").attr('disabled', true);
        $("#create-button").val('Update');
    }

    function createLayers() {
        var accHead = 0;
        for (var i = 0; i < ${size}; i++) {
            accHead = accHead + parseInt($("#layerCodeLength" + i).val());
        }
        if (accHead > ${length} || accHead < ${length}) {
            MessageRenderer.render({
                messageTitle: 'Data Error',
                type: 2,
                messageBody: 'Total code length can not be greater than the provided code length: ' + ${length}
            });
            return false;
        }
        var data = $("#gFormChartOfAccountLayer").serializeArray();
        var length = ${size};
        for (var i = 0; i < length; i++) {
            if ($("#create-button").val() == 'Update') {
                data.push({'name': 'items.chartOfAccountLayer[' + i + '].id', 'value': $("#layerId" + i).val()});
            }
            data.push({'name': 'items.chartOfAccountLayer[' + i + '].layerNumber', 'value': $("#layerNumber" + i).val()});
            data.push({'name': 'items.chartOfAccountLayer[' + i + '].layerName', 'value': $("#layerName" + i).val()});
            data.push({'name': 'items.chartOfAccountLayer[' + i + '].layerCodeLength', 'value': $("#layerCodeLength" + i).val()});
            data.push({'name': 'items.chartOfAccountLayer[' + i + '].enterpriseConfiguration.id', 'value': $("#enterpriseConfiguration").val()});
            data.push({'name': 'items.chartOfAccountLayer[' + i + '].isActive', 'value': true});
        }
        if ($("#create-button").val() == 'Update') {
            var actionUrl = "${request.contextPath}/${params.controller}/update";
        }else {
            var actionUrl = "${request.contextPath}/${params.controller}/save";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostCondition(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom()
            },
            dataType: 'json'
        });
    }

    function executePostCondition(result) {
        if (result.type == 1) {
//            reset_form('#gFormChartOfAccountLayer');
        }
        MessageRenderer.render(result);
    }

</script>