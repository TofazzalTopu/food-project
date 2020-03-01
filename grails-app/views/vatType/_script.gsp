

<script type="text/javascript" language="Javascript">
    $(document).ready(function() {
        $('#ui-widget-header-text').html('VatType')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress (checkForEnter);
        } else {
            $(textboxes).keydown (checkForEnter);
        }

        $("#gFormVatType").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormVatType").validationEngine('attach');
        reset_vatType_form("#gFormVatType");
        $("#jqgrid-grid").jqGrid({
            url:'${resource(dir:'vatType', file:'list')}',
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
            caption:"All VatType Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function() {
            },
            onSelectRow: function(rowid, status) {
                executeEditVatType();
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
    function getSelectedVatTypeId()
    {
        var vatTypeId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if(rowid)
        {
            vatTypeId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if(vatTypeId == null){
            vatTypeId = $('#gFormVatType input[name = id]').val();
        }
        return vatTypeId;
    }
    function executePreConditionVatType() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormVatType").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxVatType() {
        if(!executePreConditionVatType()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormVatType input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data:jQuery("#gFormVatType").serialize(),
            url: actionUrl,
            success:function(data, textStatus) {
                executePostConditionVatType(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_vatType_form('#gFormVatType');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
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
    function executePostConditionVatType(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_vatType_form('#gFormVatType');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxVatType() {    // Delete record
        var vatTypeId = getSelectedVatTypeId();
        if(executePreConditionForDeleteVatType(vatTypeId))
        {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-vatType").dialog({
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
                            data:jQuery("#gFormVatType").serialize(),
                            url: "${resource(dir:'vatType', file:'delete')}",
                            success: function(message) {
                                executePostConditionForDeleteVatType(message);
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

    function executePreConditionForEditVatType(vatTypeId) {
        if(vatTypeId == null)
        {
            alert("Please select a vatType to edit") ;
            return false;
        }
        return true;
    }
    function executeEditVatType() {
        var vatTypeId = getSelectedVatTypeId();
        if(executePreConditionForEditVatType(vatTypeId))
        {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'vatType', file:'edit')}?id="+ vatTypeId,
                success: function(entity) {
                    executePostConditionForEditVatType(entity);
                },
                dataType:'json'
            });
        }
    }
    function executePostConditionForEditVatType(data) {
        if (data == null) {
            alert('Selected vatType might have been deleted by someone');  //Message renderer
        } else {
            showVatType(data);
        }
    }
    function executePreConditionForDeleteVatType(vatTypeId) {
        if(vatTypeId == null)
        {
            alert("Please select a vatType to delete") ;
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteVatType(data) {
        if (data.type ==1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_vatType_form('#gFormVatType');
        }
        MessageRenderer.render(data)
    }
    function showVatType(entity) {
        var vatType=entity.vatType
        var enterpriseConfiguration=entity.enterpriseConfiguration

        $('#gFormVatType input[name = id]').val(vatType.id);
        $('#gFormVatType input[name = version]').val(vatType.version);


        if (enterpriseConfiguration){
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        $('#name').val(vatType.name);
        $('#accountCode').val(vatType.accountCode);
        $('#note').val(vatType.note);
        if(vatType.userCreated){
            $('#userCreated').val(vatType.userCreated.id).attr("selected","selected");
        }
        else{
            $('#userCreated').val(vatType.userCreated);
        }
        if(vatType.userUpdated){
            $('#userUpdated').val(vatType.userUpdated.id).attr("selected","selected");
        }
        else{
            $('#userUpdated').val(vatType.userUpdated);
        }
        $('#dateCreated').val(vatType.dateCreated);
        $('#dateUpdated').val(vatType.dateUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchVatType(fieldName, fieldValue){
        if(executePreConditionForSearchVatType(fieldName, fieldValue))
        {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'vatType', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
                success: function(entity) {
                    executePostConditionForSearchVatType(entity, fieldName, fieldValue);
                },
                dataType:'json'
            });
        }
    }
    function executePreConditionForSearchVatType(fieldName,fieldValue) {
        // trim field vales before process.
        $('#'+fieldName).val($.trim($('#'+fieldName).val()));
        if(fieldValue == '' || fieldValue == null){
            reset_vatType_form("#gFormVatType");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchVatType(data, fieldName, fieldValue) {
        if (data == null) {
            reset_vatType_form("#gFormVatType"); // Clear Form
            $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showVatType(data);
        }
    }
    function reset_vatType_form(formName) {
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