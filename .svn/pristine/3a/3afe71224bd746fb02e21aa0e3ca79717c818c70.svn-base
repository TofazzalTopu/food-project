

<script type="text/javascript" language="Javascript">
    $(document).ready(function() {
        $('#ui-widget-header-text').html('Business Unit')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress (checkForEnter);
        } else {
            $(textboxes).keydown (checkForEnter);
        }
        $('#isActive').attr("checked",true);
        $('#divActive').hide();
        $('#delete-button').hide();

        $('#cancel-button').click(function(){
            if($('#cancel-button').val() == 'Cancel'){
                $('#divActive').hide();
            }
        });

        $("#gFormBusinessUnitConfiguration").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormBusinessUnitConfiguration").validationEngine('attach');
//    reset_form("#gFormBusinessUnitConfiguration");
        $("#jqgrid-grid").jqGrid({
            url:'${resource(dir:'businessUnitConfiguration', file:'list')}',
            datatype: "json",
            colNames:[
                'SL',
                'ID',

                'Enterprise',
                'Code',
                'Name',
                'Note',
                'Is Active'

            ],
            colModel:[
                {name:'sl',index:'sl', width:30, sortable:false, align:'left'},
                {name:'id',index:'id', width:0, hidden:true},

                {name:'ename', index:'ename',width:100,align:'left'},
                {name:'code', index:'code',width:100,align:'left'},
                {name:'name', index:'name',width:100,align:'left'},
                {name:'note', index:'note',width:100,align:'left'},
                {name:'is_active', index:'is_active',width:50,align:'left'}

            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"All Business Unit Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function() {
            },
            onSelectRow: function(rowid, status) {
                executeEditBusinessUnitConfiguration();
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
    function getSelectedBusinessUnitConfigurationId()
    {
        var businessUnitConfigurationId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if(rowid)
        {
            businessUnitConfigurationId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if(businessUnitConfigurationId == null){
            businessUnitConfigurationId = $('#gFormBusinessUnitConfiguration input[name = id]').val();
        }
        return businessUnitConfigurationId;
    }
    function executePreConditionBusinessUnitConfiguration() {
        // trim field vales before process.
//      trim_form();
        if (!$("#gFormBusinessUnitConfiguration").validationEngine('validate')) {
//      if ($("#gFormBusinessUnitConfiguration").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxBusinessUnitConfiguration() {
        if(!executePreConditionBusinessUnitConfiguration()) {
            return false;
        }

        var actionUrl = null;
        if ($('#gFormBusinessUnitConfiguration input[name = id]').val()) {

            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data:jQuery("#gFormBusinessUnitConfiguration").serialize(),
            url: actionUrl,
            success:function(data, textStatus) {
                executePostConditionBusinessUnitConfiguration(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    clear_business_form();
                    $("#divActive").hide();
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
    function executePostConditionBusinessUnitConfiguration(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            clear_business_form();
//        reset_form('#gFormBusinessUnitConfiguration');
            $("#divActive").hide();
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxBusinessUnitConfiguration() {    // Delete record
        var businessUnitConfigurationId = getSelectedBusinessUnitConfigurationId();
        if(executePreConditionForDeleteBusinessUnitConfiguration(businessUnitConfigurationId))
        {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-businessUnitConfiguration").dialog({
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
                            data:jQuery("#gFormBusinessUnitConfiguration").serialize(),
                            url: "${resource(dir:'businessUnitConfiguration', file:'delete')}",
                            success: function(message) {
                                executePostConditionForDeleteBusinessUnitConfiguration(message);
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

    function executePreConditionForEditBusinessUnitConfiguration(businessUnitConfigurationId) {
        if(businessUnitConfigurationId == null)
        {
            alert("Please select a businessUnitConfiguration to edit") ;
            return false;
        }
        return true;
    }
    function executeEditBusinessUnitConfiguration() {
        var businessUnitConfigurationId = getSelectedBusinessUnitConfigurationId();
        if(executePreConditionForEditBusinessUnitConfiguration(businessUnitConfigurationId))
        {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'businessUnitConfiguration', file:'edit')}?id="+ businessUnitConfigurationId,
                success: function(entity) {
                    executePostConditionForEditBusinessUnitConfiguration(entity);
                },
                dataType:'json'
            });
        }
    }
    function executePostConditionForEditBusinessUnitConfiguration(data) {
        if (data == null) {
            alert('Selected businessUnitConfiguration might have been deleted by someone');  //Message renderer
        } else {
            showBusinessUnitConfiguration(data);
        }
    }
    function executePreConditionForDeleteBusinessUnitConfiguration(businessUnitConfigurationId) {
        if(businessUnitConfigurationId == null)
        {
            alert("Please select a businessUnitConfiguration to delete") ;
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteBusinessUnitConfiguration(data) {
        if (data.type ==1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            clear_business_form()
//         reset_form('#gFormBusinessUnitConfiguration');
            $("#divActive").hide();
        }
        MessageRenderer.render(data)
    }
    function showBusinessUnitConfiguration(entity) {
        $('#gFormBusinessUnitConfiguration input[name = id]').val(entity[0].id);
        $('#gFormBusinessUnitConfiguration input[name = version]').val(entity[0].version);

        $('#enterpriseConfiguration').val(entity[0].eid);

        $('#enterpriseList').setValue(entity[0].ecode)

        $('#code').val(entity[0].code);
        $('#name').val(entity[0].name);
        $('#note').val(entity[0].note);

        if(entity[0].is_active){
            $('#isActive').attr('checked',true);
        }else{
            $('#isActive').attr('checked',false);
        }

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
        $('#divActive').show();
    }
    function executeSearchBusinessUnitConfiguration(fieldName, fieldValue){
        if(executePreConditionForSearchBusinessUnitConfiguration(fieldName, fieldValue))
        {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'businessUnitConfiguration', file:'search')}?fieldName="+fieldName+"&fieldValue="+fieldValue,
                success: function(entity) {
                    executePostConditionForSearchBusinessUnitConfiguration(entity, fieldName, fieldValue);
                },
                dataType:'json'
            });
        }
    }
    function executePreConditionForSearchBusinessUnitConfiguration(fieldName,fieldValue) {
        // trim field vales before process.
        $('#'+fieldName).val($.trim($('#'+fieldName).val()));
        if(fieldValue == '' || fieldValue == null){
            clear_business_form()
//          reset_form("#gFormBusinessUnitConfiguration");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchBusinessUnitConfiguration(data, fieldName, fieldValue) {
        if (data == null) {
            clear_business_form()
//          reset_form("#gFormBusinessUnitConfiguration"); // Clear Form
            $('#'+fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showBusinessUnitConfiguration(data);
        }
    }



    var BusinessUnit = {
        enterpriseInfoId: null,
        setInformation: function(enterpriseInfoId){
            $('#enterpriseConfiguration').val(enterpriseInfoId);
            BusinessUnit.enterpriseInfoId = enterpriseInfoId;
        }
    }
    function clear_business_form(){
        $('#gFormBusinessUnitConfiguration input[name = id]').val('');
        $('#gFormBusinessUnitConfiguration input[name = version]').val('');


        $('#enterpriseConfiguration').val('');
        $('#enterpriseList').setValue('');
        $('#code').val('');
        $('#name').val('');
        $('#note').val('');

        $('#create-button').attr('value', 'Create');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').hide();
    }
</script>