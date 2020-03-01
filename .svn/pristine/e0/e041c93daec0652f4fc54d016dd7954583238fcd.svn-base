<%@ page import="com.bits.bdfp.util.ApplicationConstants" %>
<style type="text/css">
html .ui-multiselect{
    width: 750px!important;
}
</style>
<script type="text/javascript" language="Javascript">
    var widgetTerritory = null;
    var supervisorFlexbox = null;
    $(document).ready(function() {
        $('#ui-widget-header-text').html('CustomerMaster');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress (checkForEnter);
        } else {
            $(textboxes).keydown (checkForEnter);
        }

        $("#gFormCustomerMaster").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCustomerMaster").validationEngine('attach');

        $(".ui-pg-selbox").children().each(function() {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });
        $("#territorySubAreaList").multiselect(
                $.extend($.ui.multiselect, {
                    locale: {
                        addAll:'Add All',
                        removeAll:'Remove All',
                        itemsCount:'Geographic Location selected'
                    }
                })
        );
        if($('#gFormCustomerMaster input[name = id]').val()){
            $("#create-button").val("Update");
            $("#code").attr('disabled', 'disabled');
            $("#cancel-button").hide();
            $("#enterpriselist").setValue("${enterPriseName}");
            getTerritoryByEnterprise($("#enterpriseConfigurationId").val());
            loadGeoLocationByTerritory();
//            getTerritorySubAreaMappingByEnterprise($("#enterpriseConfigurationId").val());
//            $("#enterpriseConfigurationId").change();
        }else{
            $(".customerStatus").hide();
        }
        getFlexBoxSupervisorList();

        $("#mobile").format({precision: 0, allow_negative: false, autofix: true});
    });
    function getSelectedCustomerMasterId() {
        return $('#gFormCustomerMaster input[name = id]').val();
    }
    function executePreConditionCustomerMaster() {
        // trim field vales before process.
//        trim_form();
        if (!$("#gFormCustomerMaster").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxCustomerMaster() {
        if(!executePreConditionCustomerMaster()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCustomerMaster input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        var data = $("#gFormCustomerMaster").serializeArray();
        var department = $("#department").val();
        var selectedGeolocation = $('#territorySubAreaList').val();
        if(department == ${ApplicationConstants.SALES_DEPARTMENT_ID}){
            if (!selectedGeolocation || selectedGeolocation.length <= 0){
                MessageRenderer.renderErrorText("Please Select Geographic Location");
                return false
            }
            if(!$("#customerMasterId").val()){
                MessageRenderer.renderErrorText("Please Select Supervisor");
                return false
            }
        }
        if(selectedGeolocation && selectedGeolocation.length > 0){
            data.push({'name':'territorySubAreaIdList', 'value': selectedGeolocation.toString()});
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data: data,
            url: actionUrl,
            success:function(data, textStatus) {
                executePostConditionCustomerMaster(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    if (result.type == 1) {
                        if ($('#gFormCustomerMaster input[name = id]').val()) {
                            $('#gFormCustomerMaster input').attr('readonly', 'readonly');
                        }else{
                            clean_form('#gFormCustomerMaster');
                        }
                    }
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
    function executePostConditionCustomerMaster(result) {
        if (result.type == 1) {
            if ($('#gFormCustomerMaster input[name = id]').val()) {
                $('#gFormCustomerMaster input').attr('readonly', 'readonly');
            }else{
                clean_form('#gFormCustomerMaster');
            }
        }
        MessageRenderer.render(result);
    }

    function getTerritoryListByEnterprise(enterpriseId) {
        if(enterpriseId){
            $.ajax({
                type:'POST',
                data:'enterpriseId=' + enterpriseId,
                url:'${request.contextPath}/territoryConfiguration/searchTerritoryByEnterprise',
                success:function (data) {
                    $("#td-territory").html(data);
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        clean_form('#gFormCustomerMaster');
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                },
                dataType:'html'
            });
        }else{
            $("#td-territory").html("");
        }
    }

    function getTerritoryByEnterprise(enterpriseId){
        var options = "<option value=''>Select Territory</option>";
        if(enterpriseId){
            $.ajax({
                type:'POST',
                data:'enterpriseId=' + enterpriseId,
                url:'${request.contextPath}/territoryConfiguration/searchTerritoryByEnterprise',
                success:function (data) {
                    var territoryCount = data.length;
                    for(var i=0; i < territoryCount; i++){
                        options += "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
                    }
                    $("#territory").html(options);
                    if($('#gFormCustomerMaster input[name = id]').val()){
                        if(${territoryIds}){
                            var territoryIdTemp = '${territoryIds}';
                            var territoryIds = territoryIdTemp.toString().split(',');
                            $('#territory').val(territoryIds).attr('selected', 'selected');
                            $('#territory').change();
                        }
                    }
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        clean_form('#gFormCustomerMaster');
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                },
                dataType:'json'
            });
        }else{
            $("#territory").html(options);
        }
    }

    function loadGeoLocationByTerritory(){
        var territoryIds = $("#territory").val();
        if(territoryIds != ''){
            $.ajax({
                type:'POST',
                data:'territoryIds=' + territoryIds + '&employeeId=' + $('#gFormCustomerMaster input[name = id]').val(),
                url:'${request.contextPath}/territorySubArea/searchTerritorySubAreaByTerritory',
                success:function (data) {
                    $("#td-geoLocation").html(data);
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        $("#territorySubAreaList").html("");
                        $("#territorySubAreaList").multiselect();
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                },
                dataType:'html'
            });
        }else {
            $("#territorySubAreaList").html("");
            $(".remove-all").click();
        }
    }

    function getTerritorySubAreaMappingByEnterprise(enterpriseId) {
        if(enterpriseId){
            $.ajax({
                type:'POST',
                data:'enterpriseId=' + enterpriseId + '&employeeId=' + $('#gFormCustomerMaster input[name = id]').val(),
                url:'${request.contextPath}/territorySubArea/searchTerritorySubAreaMappingByCustomer',
                success:function (data) {
                    $("#td-geoLocation").html(data);
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        $("#td-geoLocation").html("");
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                },
                dataType:'html'
            });
        }else{
            $("#td-geoLocation").val("");
            $("#territorySubAreaList").html("");
            $(".remove-all").click();
//            $("#territorySubAreaList").html("");
//            $("#territorySubAreaList").multiselect();
//            $("#td-geoLocation").html("");
        }
    }

    function getGeographicLocationByTerritory() {
        var territoryIds = $("#territoryConfiguration option:selected").val();
        var options = '';
        if(territoryIds){
            $.ajax({
                type:'POST',
                data:'territoryIds=' + territoryIds,
                url:'${request.contextPath}/territorySubArea/searchTerritorySubAreaByTerritory',
                success:function (j) {
                    if (j.length > 0) {
                        for (var i = 0; i < j.length; i++) {
                            options += '<option value="' + j[i].id + '">' + j[i].name + '</option>';
                        }
                    }
                    $("#territorySubAreaList").html(options);
                    $("#territorySubAreaList").multiselect(
                            $.extend($.ui.multiselect, {
                                locale: {
                                    addAll:'Add All',
                                    removeAll:'Remove All',
                                    itemsCount:'Geographic Location selected'
                                }
                            })
                    );
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        $("#td-geoLocation").html("");
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                },
                dataType:'json'
            });
        }else{
            $("#territorySubAreaList").html(options);
            $("#territorySubAreaList").multiselect(
                    $.extend($.ui.multiselect, {
                        locale: {
                            addAll:'Add All',
                            removeAll:'Remove All',
                            itemsCount:'Geographic Location selected'
                        }
                    })
            );
        }
    }

    function getFlexBoxSupervisorList() {
        $("#supervisor").empty();
        supervisorFlexbox = $("#supervisor").flexbox(${supervisorList}, {
            watermark: "Select Supervisor",
            width: 380,
            onSelect: function() {
                $("#customerMasterId").val($('#supervisor_hidden').val());
            }
        });
        $('#supervisor_input').blur(function() {
            if($('#supervisor_input').val() == ''){
                $("#customerMasterId").val("");
            }
        });
        var customerMasterId = $("#customerMasterId").val();
        if(customerMasterId) {
            supervisorFlexbox.setValue('${supervisor}');
        }
    }

    function clean_form(formName) {
        var enterprise = $("#enterpriseConfigurationId").val();
        var enterpriseName = $("#enterPriseName").val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = "";
            }
        });
//    $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $("#enterpriseConfigurationId").val(enterprise);
        $("#enterpriseConfigurationId").change();
        $("#enterPriseName").val(enterpriseName);
        $("#territorySubAreaList").html("");
        $(".remove-all").click();
    }

</script>