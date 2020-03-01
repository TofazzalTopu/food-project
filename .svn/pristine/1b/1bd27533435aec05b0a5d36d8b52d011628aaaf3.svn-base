<script type="text/javascript" language="Javascript">
    $(document).ready(function() {
        $('#ui-widget-header-text').html('ProductCategory')
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

        $("#gFormProductCategory").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormProductCategory").validationEngine('attach');
        reset_productCategory_form("#gFormProductCategory");
        $("#jqgrid-grid").jqGrid({
            url:'${resource(dir:'productCategory', file:'list')}',
            datatype: "json",
            colNames:[
                'SL',
                'ID',

                'Enterprise',
                'Code',
                'Category Name',
                'Note',
                'Is Active'
            ],
            colModel:[
                {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
                {name:'id',index:'id', width:0, hidden:true},

                {name:'enterpriseConfiguration', index:'enterpriseConfiguration',width:100,align:'left'},
                {name:'code', index:'code',width:100,align:'left'},
                {name:'name', index:'name',width:100,align:'left'},
                {name:'note', index:'note',width:100,align:'left'},
                {name:'isActive', index:'isActive',width:50,align:'left'}
            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"All Product Category Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function() {
            },
            onSelectRow: function(rowid, status) {
                executeEditProductCategory();
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
    function getSelectedProductCategoryId()
    {
        var productCategoryId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if(rowid)
        {
            productCategoryId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if(productCategoryId == null){
            productCategoryId = $('#gFormProductCategory input[name = id]').val();
        }
        return productCategoryId;
    }
    function executePreConditionProductCategory() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormProductCategory").validationEngine('validate')) {
//        if ($("#gFormProductCategory").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxProductCategory() {
        if(!executePreConditionProductCategory()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormProductCategory input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        jQuery.ajax({
            type:'post',
            data:jQuery("#gFormProductCategory").serialize(),
            url: actionUrl,
            success:function(data, textStatus) {
                executePostConditionProductCategory(data);
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
        return false;
    }
    function executePostConditionProductCategory(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_productCategory_form('#gFormProductCategory');
            $('#divActive').hide();
        }
        MessageRenderer.render(result);
        console.log(result);
    }
    function deleteAjaxProductCategory() {    // Delete record
        var productCategoryId = getSelectedProductCategoryId();
        if(executePreConditionForDeleteProductCategory(productCategoryId))
        {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-productCategory").dialog({
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
                            data:jQuery("#gFormProductCategory").serialize(),
                            url: "${resource(dir:'productCategory', file:'delete')}",
                            success: function(message) {
                                executePostConditionForDeleteProductCategory(message);
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

    function executePreConditionForEditProductCategory(productCategoryId) {
        if(productCategoryId == null)
        {
            alert("Please select a productCategory to edit") ;
            return false;
        }
        return true;
    }
    function executeEditProductCategory() {
        var productCategoryId = getSelectedProductCategoryId();
        if(executePreConditionForEditProductCategory(productCategoryId))
        {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'productCategory', file:'edit')}?id="+ productCategoryId,
                success: function(entity) {
                    executePostConditionForEditProductCategory(entity);
                },
                dataType:'json'
            });
        }
    }
    function executePostConditionForEditProductCategory(data) {
        if (data == null) {
            alert('Selected productCategory might have been deleted by someone');  //Message renderer
        } else {
            showProductCategory(data);
        }
    }
    function executePreConditionForDeleteProductCategory(productCategoryId) {
        if(productCategoryId == null)
        {
            alert("Please select a productCategory to delete") ;
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteProductCategory(data) {
        if (data.type ==1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_productCategory_form('#gFormProductCategory');
            $('#divActive').hide();
        }
        MessageRenderer.render(data)
    }
    function showProductCategory(entity) {
        var productCategory=entity.productCategory
        var enterpriseConfiguration=entity.enterpriseConfiguration

        $('#gFormProductCategory input[name = id]').val(productCategory.id);
        $('#gFormProductCategory input[name = version]').val(productCategory.version);

        if (enterpriseConfiguration){
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        $('#code').val(productCategory.code);
        $('#code').attr('readonly','readonly');
        $('#name').val(productCategory.name);
        $('#note').val(productCategory.note);

        if(productCategory.isActive){
            $('#isActive').attr('checked',true);
        }else{
            $('#isActive').attr('checked',false);
        }

        if(productCategory.userCreated){
            $('#userCreated').val(productCategory.userCreated.id).attr("selected","selected");
        }
        else{
            $('#userCreated').val(productCategory.userCreated);
        }
        if(productCategory.userUpdated){
            $('#userUpdated').val(productCategory.userUpdated.id).attr("selected","selected");
        }
        else{
            $('#userUpdated').val(productCategory.userUpdated);
        }
        $('#dateCreated').val(productCategory.dateCreated);
        $('#dateUpdated').val(productCategory.dateUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();

        $('#divActive').show();
    }
    function executeSearchProductCategory(fieldName, fieldValue){
        if(executePreConditionForSearchProductCategory(fieldName, fieldValue))
        {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'productCategory', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
                success: function(entity) {
                    executePostConditionForSearchProductCategory(entity, fieldName, fieldValue);
                },
                dataType:'json'
            });
        }
    }
    function executePreConditionForSearchProductCategory(fieldName,fieldValue) {
        // trim field vales before process.
        $('#'+fieldName).val($.trim($('#'+fieldName).val()));
        if(fieldValue == '' || fieldValue == null){
            reset_productCategory_form("#gFormProductCategory");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchProductCategory(data, fieldName, fieldValue) {
        if (data == null) {
            reset_productCategory_form("#gFormProductCategory"); // Clear Form
            $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showProductCategory(data);
        }
    }
    function reset_productCategory_form(formName) {
        var enterpriseId = $('#enterpriseConfiguration').val();
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
        $('#enterpriseConfiguration').val(enterpriseId);
    }
</script>