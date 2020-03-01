

<script type="text/javascript" language="Javascript">
    $(document).ready(function() {
        $('#ui-widget-header-text').html('DiscountType')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress (checkForEnter);
        } else {
            $(textboxes).keydown (checkForEnter);
        }

        $("#gFormDiscountType").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormDiscountType").validationEngine('attach');
        reset_discountType_form("#gFormDiscountType");
        $("#jqgrid-grid").jqGrid({
            url:'${resource(dir:'discountType', file:'list')}',
            datatype: "json",
            colNames:[
                'SL',
                'ID',

                'Enterprise',
                'Name',
                'Account Code',
                'Note'
            ],
            colModel:[
                {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
                {name:'id',index:'id', width:0, hidden:true},

                {name:'enterpriseConfiguration', index:'enterpriseConfiguration',width:100,align:'left'},
                {name:'name', index:'name',width:100,align:'left'},
                {name:'accountCode', index:'accountCode',width:100,align:'left'},
                {name:'note', index:'note',width:100,align:'left'}
            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"All DiscountType Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function() {
            },
            onSelectRow: function(rowid, status) {
                executeEditDiscountType();
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
    function getSelectedDiscountTypeId()
    {
        var discountTypeId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if(rowid)
        {
            discountTypeId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if(discountTypeId == null){
            discountTypeId = $('#gFormDiscountType input[name = id]').val();
        }
        return discountTypeId;
    }
    function executePreConditionDiscountType() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormDiscountType").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxDiscountType() {
        if(!executePreConditionDiscountType()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormDiscountType input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data:jQuery("#gFormDiscountType").serialize(),
            url: actionUrl,
            success:function(data, textStatus) {
                executePostConditionDiscountType(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_discountType_form('#gFormDiscountType');
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
    function executePostConditionDiscountType(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_discountType_form('#gFormDiscountType');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxDiscountType() {    // Delete record
        var discountTypeId = getSelectedDiscountTypeId();
        if(executePreConditionForDeleteDiscountType(discountTypeId))
        {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-discountType").dialog({
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
                            data:jQuery("#gFormDiscountType").serialize(),
                            url: "${resource(dir:'discountType', file:'delete')}",
                            success: function(message) {
                                executePostConditionForDeleteDiscountType(message);
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

    function executePreConditionForEditDiscountType(discountTypeId) {
        if(discountTypeId == null)
        {
            alert("Please select a discountType to edit") ;
            return false;
        }
        return true;
    }
    function executeEditDiscountType() {
        var discountTypeId = getSelectedDiscountTypeId();
        if(executePreConditionForEditDiscountType(discountTypeId))
        {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'discountType', file:'edit')}?id="+ discountTypeId,
                success: function(entity) {
                    executePostConditionForEditDiscountType(entity);
                },
                dataType:'json'
            });
        }
    }
    function executePostConditionForEditDiscountType(data) {
        if (data == null) {
            alert('Selected discountType might have been deleted by someone');  //Message renderer
        } else {
            showDiscountType(data);
        }
    }
    function executePreConditionForDeleteDiscountType(discountTypeId) {
        if(discountTypeId == null)
        {
            alert("Please select a discountType to delete") ;
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteDiscountType(data) {
        if (data.type ==1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_discountType_form('#gFormDiscountType');
        }
        MessageRenderer.render(data)
    }
    function showDiscountType(entity) {
        var discountType=entity.discountType
        var enterpriseConfiguration=entity.enterpriseConfiguration
        $('#gFormDiscountType input[name = id]').val(discountType.id);
        $('#gFormDiscountType input[name = version]').val(discountType.version);

        if (enterpriseConfiguration){
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        $('#name').val(discountType.name);
        $('#accountCode').val(discountType.accountCode);
        $('#note').val(discountType.note);
        if(discountType.userCreated){
            $('#userCreated').val(discountType.userCreated.id).attr("selected","selected");
        }
        else{
            $('#userCreated').val(discountType.userCreated);
        }
        if(discountType.userUpdated){
            $('#userUpdated').val(discountType.userUpdated.id).attr("selected","selected");
        }
        else{
            $('#userUpdated').val(discountType.userUpdated);
        }
        $('#dateCreated').val(discountType.dateCreated);
        $('#dateUpdated').val(discountType.dateUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchDiscountType(fieldName, fieldValue){
        if(executePreConditionForSearchDiscountType(fieldName, fieldValue))
        {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'discountType', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
                success: function(entity) {
                    executePostConditionForSearchDiscountType(entity, fieldName, fieldValue);
                },
                dataType:'json'
            });
        }
    }
    function executePreConditionForSearchDiscountType(fieldName,fieldValue) {
        // trim field vales before process.
        $('#'+fieldName).val($.trim($('#'+fieldName).val()));
        if(fieldValue == '' || fieldValue == null){
            reset_discountType_form("#gFormDiscountType");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchDiscountType(data, fieldName, fieldValue) {
        if (data == null) {
            reset_discountType_form("#gFormDiscountType"); // Clear Form
            $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showDiscountType(data);
        }
    }
    function reset_discountType_form(formName) {
        var enterpriseId = $('#enterpriseConfiguration').val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio, :selected").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
//    $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $(formName+' select').val('');
//        $("#packageType").html('');
//        $("#measureUnitConfiguration").html('');
        $('#enterpriseConfiguration').val(enterpriseId);
    }
</script>