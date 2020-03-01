<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormAuthority").validationEngine({   //    client side validation
            isOverflown:true,
            overflownDIV:".ui-layout-center"
        });
        $("#gFormAuthority").validationEngine('attach');
        reset_form("#gFormAuthority");
        $("#jqgrid-grid-authority").jqGrid({
            url:'${request.contextPath}/${params.controller}/list',
            datatype:"json",
            colNames:[
                'Id',
                'Authority Name',
                'Role',
                'Authority Dashboard Id',
                'Module Id',
                'Module Name',
                'Dashboard Url'
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'authority', index:'authority', width:200, align:'left'},
                {name:'role', index:'role', width:200, align:'left'},
                {name:'authorityDashboardId', index:'authorityDashboardId', width:0, hidden:true},
                {name:'moduleId', index:'moduleId', width:0, hidden:true},
                {name:'moduleName', index:'moduleName', width:150},
                {name:'dashboardUrl', index:'dashboardUrl', width:150}
            ],
            rowNum:10,
            rowList:[10, 20, 30],
            pager:'#jqgrid-pager-authority',
            sortname:'id',
            viewrecords:true,
            sortorder:"desc",
            caption:"All User Authority Information",
            autowidth:true,
            autoheight:true,
            scrollOffset:0,
            altRows:true,
            height:250,
            loadComplete:function () {
            },
            onSelectRow:function (rowid, status) {
                executeEditAuthority();
            }
        });
        $("#jqgrid-grid-authority").jqGrid('navGrid', '#jqgrid-pager-authority', {edit:false, add:false, del:false, search:false})
    });
    function getSelectedAuthorityId() {
        var authorityId = null;
        var rowid = $("#jqgrid-grid-authority").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            authorityId = $("#jqgrid-grid-authority").jqGrid('getCell', rowid, 'id');
        }
        if (authorityId == null) {
            authorityId = $('#gFormAuthority input[name = id]').val();
        }
        return authorityId;
    }
    function executePreConditionAuthority() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormAuthority").validate().form({onfocusout:false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxAuthority() {
        if (!executePreConditionAuthority()) {
            return false;
        }
        if (!$("#gFormAuthority").validationEngine('validate')) {
            return false
        }
        var actionUrl = null;
        if ($('#gFormAuthority input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/save";
        }
        jQuery.ajax({
            type:'post',
            data:jQuery("#gFormAuthority").serialize(),
            url:actionUrl,
            success:function (data, textStatus) {
                executePostConditionAuthority(data);
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
            },
            complete:function () {
            },
            dataType:'json'
        });
        return false;
    }
    function executePostConditionAuthority(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-authority").trigger("reloadGrid");
            reset_form('#gFormAuthority');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxAuthority() {    // Delete record
        var authorityId = getSelectedAuthorityId();
        if (executePreConditionForDeleteAuthority(authorityId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-authority").dialog({
                resizable:false,
                height:150,
                modal:true,
                title:'Delete alert',
                buttons:{
                    'Delete item(s)':function () {
                        $(this).dialog('close');
                        $.ajax({
                            type:"POST",
                            dataType:"json",
                            data:jQuery("#gFormAuthority").serialize(),
                            url:"${request.contextPath}/${params.controller}/delete",
                            success:function (message) {
                                executePostConditionForDeleteAuthority(message);
                            }
                        });
                    },
                    Cancel:function () {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
        }
    }

    function executePreConditionForEditAuthority(authorityId) {
        if (authorityId == null) {
            alert("Please select a authority to edit");
            return false;
        }
        return true;
    }
    function executeEditAuthority() {
        var authorityId = getSelectedAuthorityId();
        if (executePreConditionForEditAuthority(authorityId)) {
            $.ajax({
                type:"POST",
                url:"${request.contextPath}/${params.controller}/edit?id=" + authorityId,
                success:function (entity) {
                    executePostConditionForEditAuthority(entity);
                },
                dataType:'json'
            });
        }
    }
    function executePostConditionForEditAuthority(data) {
        if (data == null) {
            alert('Selected authority might have been deleted by someone');  //Message renderer
        } else {
            showAuthority(data);
        }
    }
    function executePreConditionForDeleteAuthority(authorityId) {
        if (authorityId == null) {
            alert("Please select a authority to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteAuthority(data) {
        if (data.type == 1) {
            $("#jqgrid-grid-authority").trigger("reloadGrid");
            reset_form('#gFormAuthority');
        }
        MessageRenderer.render(data)
    }
    function showAuthority(entity) {
        $('#gFormAuthority input[name = id]').val(entity.authority.id);
        $('#gFormAuthority input[name = version]').val(entity.authority.version);

        $('#authority').val(entity.authority.authority);
        $('#role').val(entity.authority.role);
        $('#role').attr('readonly','true');
        $('#moduleId').val(entity.authority.moduleId);
        $('#dashboardUrl').val(entity.authority.dashboardUrl);
        $('#create-button-authority').attr('value', 'Update');
        $('#delete-button-authority').show();
    }
    function executeSearchAuthority(fieldName, fieldValue) {
        if (executePreConditionForSearchAuthority(fieldName, fieldValue)) {
            $.ajax({
                type:"POST",
                url:"${resource(dir:'authority', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success:function (entity) {
                    executePostConditionForSearchAuthority(entity, fieldName, fieldValue);
                },
                dataType:'json'
            });
        }
    }
    function executePreConditionForSearchAuthority(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormAuthority");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchAuthority(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormAuthority"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showAuthority(data);
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
        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }
</script>