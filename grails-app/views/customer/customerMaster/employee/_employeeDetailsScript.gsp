<script>
    $(document).ready(function(){
        $('#search-btn-employee-register-id').click(function(){
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
    });
    var EmployeeRegister = {
        employeeCoreInfoId: null,
        popupEmployeeListPanel: function(){
            var url = "${request.contextPath}/${params.controller}/popupEmployeeListPanel";
            var params = {unqId:"employeeRegisterId"};
            DocuAjax.html(url, params, function(html){
                $.fancybox(html);
            });
        },

        showEmployeeDetailsById: function(employeeCoreInfoId){
            EmployeeRegister.employeeCoreInfoId = employeeCoreInfoId;
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
        }
    };
</script>