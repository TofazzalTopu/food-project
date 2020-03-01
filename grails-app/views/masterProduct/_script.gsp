<script type="text/javascript" language="Javascript">
    $(document).ready(function() {
        $('#ui-widget-header-text').html('MasterProduct')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress (checkForEnter);
        } else {
            $(textboxes).keydown (checkForEnter);
        }

        $('#isActive').attr("checked",true);
        $('#divActive').hide();

        $('#cancel-button').click(function(){
            if($('#cancel-button').val() == 'Cancel'){
                $('#divActive').hide();
            }
        });

        $("#gFormMasterProduct").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormMasterProduct").validationEngine('attach');
        reset_masterProduct_form("#gFormMasterProduct");
        $("#jqgrid-grid").jqGrid({
            url:'${resource(dir:'masterProduct', file:'list')}',
            datatype: "json",
            colNames:[
                'SL',
                'ID',

                'Enterprise',
                'Master Product Code',
                'Master Product Name',
                'Note',
                'Is Active',
                'Sequence Number'
            ],
            colModel:[
                {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
                {name:'id',index:'id', width:0, hidden:true},

                {name:'enterpriseConfiguration', index:'enterpriseConfiguration',width:100,align:'left'},
                {name:'code', index:'code',width:100,align:'left'},
                {name:'name', index:'name',width:100,align:'left'},
                {name:'note', index:'note',width:100,align:'left', hidden: true},
                {name:'isActive', index:'isActive',width:50, align:'left', align: 'center'},
                {name:'sequenceNumber', index:'sequenceNumber',width:65,align:'center'}
            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"Master Product List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function() {
            },
            onSelectRow: function(rowid, status) {
                executeEditMasterProduct();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function() {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });
    });
    function getSelectedMasterProductId()
    {
        var masterProductId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if(rowid)
        {
            masterProductId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if(masterProductId == null){
            masterProductId = $('#gFormMasterProduct input[name = id]').val();
        }
        return masterProductId;
    }
    function executePreConditionMasterProduct() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormMasterProduct").validationEngine('validate')) {
//        if ($("#gFormMasterProduct").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxMasterProduct() {
        if(!executePreConditionMasterProduct()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormMasterProduct input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data:jQuery("#gFormMasterProduct").serialize(),
            url: actionUrl,
            success:function(data, textStatus) {
                executePostConditionMasterProduct(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_masterProduct_form('#gFormMasterProduct');
                    $('#divActive').hide();
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else{
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
    function executePostConditionMasterProduct(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_masterProduct_form('#gFormMasterProduct');
            $('#divActive').hide();
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxMasterProduct() {    // Delete record
        var masterProductId = getSelectedMasterProductId();
        if(executePreConditionForDeleteMasterProduct(masterProductId))
        {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-masterProduct").dialog({
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
                            data:jQuery("#gFormMasterProduct").serialize(),
                            url: "${resource(dir:'masterProduct', file:'delete')}",
                            success: function(message) {
                                executePostConditionForDeleteMasterProduct(message);
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

    function executePreConditionForEditMasterProduct(masterProductId) {
        if(masterProductId == null)
        {
            alert("Please select a masterProduct to edit") ;
            return false;
        }
        return true;
    }
    function executeEditMasterProduct() {
        var masterProductId = getSelectedMasterProductId();
        if(executePreConditionForEditMasterProduct(masterProductId))
        {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'masterProduct', file:'edit')}?id="+ masterProductId,
                success: function(entity) {
                    executePostConditionForEditMasterProduct(entity);
                },
                dataType:'json'
            });
        }
    }
    function executePostConditionForEditMasterProduct(data) {
        if (data == null) {
            alert('Selected masterProduct might have been deleted by someone');  //Message renderer
        } else {
            showMasterProduct(data);
        }
    }
    function executePreConditionForDeleteMasterProduct(masterProductId) {
        if(masterProductId == null)
        {
            alert("Please select a masterProduct to delete") ;
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteMasterProduct(data) {
        if (data.type ==1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_masterProduct_form('#gFormMasterProduct');
            $('#divActive').hide();
        }
        MessageRenderer.render(data)
    }
    function showMasterProduct(entity) {

        var masterProduct=entity.masterProduct
        var enterpriseConfiguration=entity.enterpriseConfiguration
        $('#gFormMasterProduct input[name = id]').val(masterProduct.id);
        $('#gFormMasterProduct input[name = version]').val(masterProduct.version);


        if (enterpriseConfiguration){
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }
        $('#code').val(masterProduct.code);
        $('#code').attr('readonly','readonly');
        $('#name').val(masterProduct.name);
        $('#note').val(masterProduct.note);
        $('#sequenceNumber').val(masterProduct.sequenceNumber);

        if(masterProduct.isActive){
            $('#isActive').attr('checked',true);
        }else{
            $('#isActive').attr('checked',false);
        }

        if(masterProduct.userCreated){
            $('#userCreated').val(masterProduct.userCreated.id).attr("selected","selected");
        }
        else{
            $('#userCreated').val(masterProduct.userCreated);
        }
        if(masterProduct.userUpdated){
            $('#userUpdated').val(masterProduct.userUpdated.id).attr("selected","selected");
        }
        else{
            $('#userUpdated').val(masterProduct.userUpdated);
        }
        $('#dateCreated').val(masterProduct.dateCreated);
        $('#dateUpdated').val(masterProduct.dateUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();

        $('#divActive').show();
    }
    function executeSearchMasterProduct(fieldName, fieldValue){
        if(executePreConditionForSearchMasterProduct(fieldName, fieldValue))
        {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'masterProduct', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
                success: function(entity) {
                    executePostConditionForSearchMasterProduct(entity, fieldName, fieldValue);
                },
                dataType:'json'
            });
        }
    }
    function executePreConditionForSearchMasterProduct(fieldName,fieldValue) {
        // trim field vales before process.
        $('#'+fieldName).val($.trim($('#'+fieldName).val()));
        if(fieldValue == '' || fieldValue == null){
            reset_masterProduct_form("#gFormMasterProduct");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchMasterProduct(data, fieldName, fieldValue) {
        if (data == null) {
            reset_masterProduct_form("#gFormMasterProduct"); // Clear Form
            $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showMasterProduct(data);
        }
    }
    function reset_masterProduct_form(formName) {
        var enterprise = $("#enterpriseConfiguration").val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName+' select').val('');
        $(formName+' input').attr('readonly',false);
//    $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $("#enterpriseConfiguration").val(enterprise);
    }
</script>