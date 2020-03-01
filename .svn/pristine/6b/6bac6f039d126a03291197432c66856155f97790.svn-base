
<%
    // MenuController menuController = new MenuController()
    List list = [params.controller, params.action]
    if (params.searchConfigurationId) {
        list.add(params.searchConfigurationId)
    }
    if ("${isDateEnabled}" == 'true') {
        list.add("${isDateEnabled}")
    }
%>
<style type="text/css">
.active-link {
    color: #E17009 !important;
    font-weight: bold !important;
}
</style>

<script type="text/javascript">
    function getCurrentMenuItemIndex(accordionId) {
        var index = $("#" + accordionId).index($(".${list.join('_')}"));
        return index;
    }

    $(document).ready(function () {
        $("#left-menu").accordion({
            collapsible: true,
            autoHeight: false,
            active: getCurrentMenuItemIndex("left-menu")
        });
        $("#agent-menu").accordion({
            collapsible: true,
            autoHeight: false,
            active: getCurrentMenuItemIndex("agent-menu")
        });
        $("#sub-agent-menu").accordion({
            collapsible: true,
            autoHeight: false,
            active: getCurrentMenuItemIndex("sub-agent-menu")
        });
        $("#common-setup-menu").accordion({
            collapsible: true,
            autoHeight: false,
            active: getCurrentMenuItemIndex("common-setup-menu")
        });
        $("#security-menu").accordion({
            collapsible: true,
            autoHeight: false,
            active: getCurrentMenuItemIndex("security-menu")
        });

        $("#customer-menu").accordion({
            collapsible: true,
            autoHeight: false,
            active: getCurrentMenuItemIndex("customer-menu")
        });
        $("#financial-year-menu").accordion({
            collapsible: true,
            autoHeight: false,
            active: getCurrentMenuItemIndex("financial-year-menu")
        });
        $("#statement-menu").accordion({
            collapsible: true,
            autoHeight: false,
            active: getCurrentMenuItemIndex("statement-menu-menu")
        });
        $("#transaction-menu").accordion({
            collapsible: true,
            autoHeight: false,
            active: getCurrentMenuItemIndex("transaction-menu")
        });
        $("#system-admin-menu").accordion({
            collapsible: true,
            autoHeight: false,
            active: getCurrentMenuItemIndex("system-admin-menu")
        });
        $("#${list.join('_')}").addClass('active-link');
        $("#${list.join('_')}").closest(".ui-accordion-content").parent().accordion('activate', 0);
    });
</script>

%{--<div id="left-menu">--}%
%{--<g:if test="${session.getAttribute('leftMenu')}">--}%
%{--<g:render template="/layouts/leftMenu/${session.getAttribute('leftMenu')}"--}%
%{--</g:if>--}%
%{--</div>--}%

<script type="text/javascript">
    function searchKeyPress(e)
    {
        if (typeof e == 'undefined' && window.event) { e = window.event; }
        if (e.keyCode == 13)
        {
            DocuAjax.json("${request.contextPath}/menu/quickSearch", {quick_search : $("#quick_search").val()}, function(result){
                if(result.actionUrl != ""){
                    location.href = '${request.contextPath}/'+result.actionUrl;
                }
            });
        }
    }
    function addLinkAsFavourite(){
        DocuAjax.html('${request.contextPath}/menu/addUserFavouriteLink', {url : document.URL}, function(result){
            $("#favouriteLink").html(result);
        });
    }
    function addAgent(){
        DocuAjax.html('${request.contextPath}/menu/addAgent', {url : document.URL}, function(result){
            $("#favouriteLink").html(result);
        });
    }

    function deleteLinkAsFavourite(link){
        DocuAjax.html('${request.contextPath}/menu/deleteFavouriteLink', {url : link, isDeleted : true}, function(result){
            $("#favouriteLink").html(result);
        });
    }

    function getReportName() {
        DocuAjax.json('${request.contextPath}/reportResource/moduleData',{modId:3},function(response){
            //iterate through JSON array
            $("#dynamicReportList").empty();
            for(var item in response) {
                //assign this object to a var
                var itemInfo = response[item];
                $("#dynamicReportList").append('<div><a href="${request.contextPath}/reportResource/report/'+ itemInfo.NameOrURI +'">'+  itemInfo.ReportTitle +'</div>');

            }
        })
    }

</script>
<div class="left_nav">
    <h3 class="header">Quick Access</h3>
    <div class="search_container">
        <!-- top search container begin -->
        <input type="text" value="" class="txt width75p" id="quick_search" onkeypress="searchKeyPress(event);" />
        <input type="button" value="" class="btn"/>
        <!-- top search container end -->
    </div>
    <div class="clear height5"></div>
    <div id="left-menu" class="width95p">
        <h3>
            <a href="javascript:void(0)">Favorites</a>
        </h3>
        <div id="favouriteLink">
            <g:render template="/layouts/favouriteLink"/>
        </div>

    </div>
    <div id="agent-menu" class="width95p">
        <h3>
            <a href="javascript:void(0)">Agent</a>
        </h3>
        <div id="agentLink">
            <g:render template="/layouts/agentLink"/>
        </div>

    </div>
    <div id="sub-agent-menu" class="width95p">
        <h3>
            <a href="javascript:void(0)">Sub Agent</a>
        </h3>
        <div id="subAgentLink">
            <g:render template="/layouts/subAgentLink"/>
        </div>

    </div>

    <div id="common-setup-menu" class="width95p">
        <h3>
            <a href="javascript:void(0)">Setup</a>
        </h3>
        <div id="commonSetupLink">

            <g:render template="/layouts/commonSetupLink"/>
        </div>

    </div>

    <div id="security-menu" class="width95p">
        <h3>
            <a href="javascript:void(0)">Security</a>
        </h3>
        <div id="securityLink">
            <g:render template="/layouts/securityLink"/>
        </div>

    </div>
    <div id="customer-menu" class="width95p">
        <h3>
            <a href="javascript:void(0)">Customer</a>
        </h3>
        <div id="customerLink">
            <g:render template="/layouts/customerLink"/>
        </div>

    </div>
    <div id="financial-year-menu" class="width95p">
        <h3>
            <a href="javascript:void(0)">Financial Year</a>
        </h3>
        <div id="financialYearLink">
            <g:render template="/layouts/financialYearLink"/>
        </div>

    </div>
    <div id="statement-menu" class="width95p">
        <h3>
            <a href="javascript:void(0)">Statement</a>
        </h3>
        <div id="statementLink">
            <g:render template="/layouts/statementLink"/>
        </div>

    </div>

    <div id="transaction-menu" class="width95p">
        <h3>
            <a href="javascript:void(0)">Transaction</a>
        </h3>
        <div id="transactionLink">

            <g:render template="/layouts/transactionLink"/>
        </div>

    </div>

    <div id="system-admin-menu" class="width95p">
        <h3>
            <a href="javascript:void(0)">System Admin</a>
        </h3>
        <div id="systemAdmin">

            <g:render template="/layouts/systemAdminLink"/>
        </div>

    </div>
</div>




<script type="text/javascript">
    //$(document).ready(function(){
    //    $('#docu-ajaxaware-menu a').each(function(index, element){
    //        var href = $(element).attr('href');
    //        $(element).attr('href', 'javascript:void(0)');
    //        $(element).click(function(){
    //            DocuAjax.loadContent(href, {});
    //        });
    //    });
    //});
</script>
<div class="clear"></div>
%{--<div id="docu-ajaxaware-menu" style="padding-left: 10px; padding-top: 10px;">--}%
%{--<br/>--}%
%{--<div><a href="${request.contextPath}/employeeChildType/show">Employee Child Type</a></div>--}%
%{--<div><a href="${request.contextPath}/mediaType/show">Media Type</a></div>--}%
%{--<div><a href="${request.contextPath}/employeeDesignation/show">Employee Designation</a></div>--}%
%{--<div><a href="${request.contextPath}/functionalDesignation/show">Functional Designation</a></div>--}%
%{--<br/>--}%
%{--<div><a href="${request.contextPath}/employeeCoreInfo/create">Create Employee</a></div>--}%
%{--<div><a href="${request.contextPath}/employeeCoreInfo/list">Employee List</a></div>--}%
%{--<div><a href="${request.contextPath}/employeeDetail/show">Employee Detail</a></div>--}%
%{--<br/>--}%
%{--<div><a href="${request.contextPath}/employeeJobDescription/approveJobDesc">Approve Job Description</a></div>--}%
%{--<div><a href="${request.contextPath}/employeeCoreInfo/employeeApproval">Employee Approval</a></div>--}%
%{--<div><a href="${request.contextPath}/employeeCoreInfo/deployNewEmployee">Employee Deployment</a></div>--}%

%{--<br/>--}%
%{--<div><a href="${request.contextPath}/countryPayStructure/define">Define Country Pay Structure</a></div>--}%
%{--<div><a href="${request.contextPath}/countryPayStructure/show">Country Pay Structure</a></div>--}%
%{--<div><a href="${request.contextPath}/countryPayStructure/list">Country Pay Structure List</a></div>--}%
%{--<div><a href="${request.contextPath}/countryPayStructure/approveCountryPayStructure">Approve Country Pay Structure</a></div>--}%
%{--<br/>--}%
%{--<div><a href="${request.contextPath}/employeePayStructure/create">Employee Pay Structure</a></div>--}%
%{--<div><a href="${request.contextPath}/employeePayStructure/list">Employee Pay Structure List</a></div>--}%
%{--<div><a href="${request.contextPath}/employeePayStructure/approveEmpPayStructure">Approve Employee Pay Structure</a></div>--}%
%{--<div><a href="${request.contextPath}/employeePayrollInfo/changePayrollStatus">Change Payroll Status</a></div>--}%

%{--<br/>--}%
%{--<div><a href="${request.contextPath}/employeePayrollInfo/list">Employee Payroll Provision</a></div>--}%
%{--<div><a href="${request.contextPath}/employeePayrollPayment/accountTransfer">Employee Payroll Account Transfer</a></div>--}%
%{--<div><a href="${request.contextPath}/employeePayrollPayment/create">Employee Payroll Cheque/Cash Payment</a></div>--}%
%{--<br/>--}%

%{--<div><a href="${request.contextPath}/employeeBonusPayment/bonusPayment">Employee Bonus Account Transfer</a></div>--}%
%{--<div><a href="${request.contextPath}/employeeBonusPayment/bonusPaymentCashOrCheque">Employee Bonus Cheque/Cash Payment</a></div>--}%
%{--<br/>--}%
%{--<div><a href="${request.contextPath}/hrReport/paySlipReport">Employee Pay Slip</a></div>--}%
%{--<div><a href="${request.contextPath}/hrReport/bankTransferAdviceReport">Payroll Bank Advice</a></div>--}%

%{--<br/>--}%
%{--<div><a href="${request.contextPath}/payCodeType/show">Pay Code Type</a></div>--}%
%{--<div><a href="${request.contextPath}/payCode/show">Pay Code</a></div>--}%
%{--<div><a href="${request.contextPath}/countryPayCode/show">Country Pay Code</a></div>--}%
%{--<div><a href="${request.contextPath}/countryIncomeTaxRule/show">Country Income Tax Rule</a></div>--}%
%{--<br/>--}%
%{--<div><a href="${request.contextPath}/employeeTransferProposal/show">Transfer Proposal</a></div>--}%
%{--<div><a href="${request.contextPath}/employeeTransferRecommendation/pendingForRecommendation">Transfer Recommendation</a></div>--}%
%{--<div><a href="${request.contextPath}/employeeTransferOrder/show">Transfer Order</a></div>--}%
%{--<div><a href="${request.contextPath}/employeeTransferInfo/show">Employee Transfer Info</a></div>--}%
%{--<div><a href="${request.contextPath}/employeeResponsibilityHandover/show">Responsibility Handover</a></div>--}%
%{--<div><a href="${request.contextPath}/employeePayCodeTransfer/show">Pay Code Transfer</a></div>--}%
%{--<div><a href="${request.contextPath}/transferredEmpJoinInfo/show">Transferred Employee Joining</a></div>--}%

%{--</div>--}%
%{--<br/>--}%

%{--<div style="padding-left: 10px; padding-top: 10px;">--}%
%{--<div><a href="${request.contextPath}/hrReport/staffPositionReport">Staff Position</a></div>--}%
%{--<div><a href="${request.contextPath}/dynamicGridView/list">Dynamic Report Query Builder</a></div>--}%
%{--<div><a href="${request.contextPath}/reportResource/showajax">Dynamic Report Resource Map</a></div>--}%
%{--<div><a href="${request.contextPath}/reportResource/hrReports">Dynamic Report Viewer</a></div>--}%
%{--<div ><a  href="${request.contextPath}/${params.controller}/report/${queryList[i].NameOrURI}">${queryList[i].ReportTitle}</a></h4><br/>--}%
%{--<div id="dynamicReportList">--}%

%{--</div>--}%
%{--</div>--}%

%{--<br/>--}%
%{--<div style="padding-left: 10px; padding-top: 10px;">--}%
%{--<div><a href="${request.contextPath}/featureInfo/create">New Feature</a></div>--}%
%{--<div><a href="${request.contextPath}/featureInfo/list">Feature List</a></div>--}%
%{--<div><a href="${request.contextPath}/featureInfo/arrange">Arrange Features</a></div>--}%
%{--<div><a href="${request.contextPath}/featureControllerMapping/create">Feature Controller Mapping</a></div>--}%
%{--<div><a href="${request.contextPath}/menuGroup/create">New Menu</a></div>--}%
%{--<div><a href="${request.contextPath}/menuGroup/sort">Arrange Menu Items</a></div>--}%
%{--<div><a href="${request.contextPath}/requestmap/create">Access Control</a></div>--}%
%{--</div>--}%

<br/>
<div style="padding-left: 10px; padding-top: 10px;">
    %{--<div><a href="${request.contextPath}/hrReport/staffPositionReport">Staff Position</a></div>--}%
</div>
%{--<ul style="padding-left: 10px; list-style: none;">--}%
%{--<br/>--}%
%{--<li><b>PMS Setup</b></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/index">Objective Type</a></li>--}%
%{--<br/>--}%
%{--<li><b>PMS Objective</b></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/yearWiseObjective">Year Wise Objective Setting</a></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/objectiveSetting">Objective Setting</a></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/createDelegationOfObjective">Objective Delegation</a></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/objectiveReview">Objective Review</a></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/objectiveLog">Objective Concern Log</a></li>--}%
%{--<br/>--}%
%{--<li><b>Mid Year Objective Review </b></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/midYearObjectiveReviewByEmployee">Employee</a></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/midYearObjectiveReviewBySupervisor">Supervisor</a></li>--}%
%{--<br/>--}%
%{--<li><b>End Year Objective Review</b></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/endYearReviewObjective">First Supervisor</a></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/marksReview">Employee</a></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/objectiveSupervisor">Second Supervisor</a></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/objectiveAnchor">Anchor</a></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/pmsList">HR Entry</a></li>--}%
%{--<br/>--}%
%{--<li><b>PMS Development Plan</b></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/developmentPlanSetting">Development Plan Setting</a></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/developmentPlanReview">Development Plan Review</a></li>--}%
%{--<li><a href="${request.contextPath}/pmsDemo/developmentPlanLog">Development Plan Concern Log</a></li>--}%
%{--</ul>--}%

%{--<ul style="padding-left: 10px; list-style: none;">--}%
%{--<br/>--}%
%{--<li><b>E-Recruitment Setup</b></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/urgencyLevel">Requisition Urgency Level</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/vacancyType">Requisition Vacancy</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/questionType">Question Segment</a></li>--}%
%{--<br/>--}%
%{--<li><b>E-Recruitment Requisition</b></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/create">Employee Requisition</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/list">Foreword Proposal</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/list">Recommendation Proposal</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/list">Approve Proposal</a></li>--}%
%{--<br/>--}%
%{--<li><b>E-Recruitment Job Posting</b></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/jobPost"> Job Posting Base on Requested </a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/jobPost"> Job Posting(Urgent) </a></li>--}%
%{--<br/>--}%
%{--<li><b>E-Recruitment Register & Apply </b></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/signUp" target="_blank"> Sign-up </a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/signIn" target="_blank"> Login </a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/forgotPassword" target="_blank"> Forgot Password </a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRecruitmentDetail/show"> Applicant Details </a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/submitApplication">Apply For Job</a></li>--}%
%{--<br/>--}%
%{--<li><b>E-Recruitment Resume Scanning & Call for Written Test </b></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/applicantSearch">Searching & Save For other view</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/writtenTestApplicantList"> Call For Written Test </a></li>--}%
%{--<br/>--}%
%{--<li><b>E-Recruitment Question & Viva Management & </b></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/markEntry">Question Mark Setting</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/revisedPassMarkEntry">Revise Pass Mark Setting</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/questionUploadManagement">Question Upload</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/approveQuestionForm"> Question Approve</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/questionReviewFormByProposedPerson"> Question Modify Base on Req.</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/attendanceSheet"> Attendance For Interview.</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/eligibleApplicantForViva"> Call For Viva.</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/vivaBoard"> Viva Board Create</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/hrVivaMarksForm"> Viva Mark Entry From HR</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/vivaCompletionList"> Viva Completion</a></li>--}%
%{--<li><a href="${request.contextPath}/employeeRequisition/recruitedForm"> Viva After Recruited</a></li>--}%
%{--<br/>--}%
%{--</ul>--}%
<ul style="padding-left: 10px; list-style: none;">
    %{--<br/>--}%
    %{--<li><b>Training Setup</b></li>--}%
    %{--<li><a href="${request.contextPath}/employeeTraining/trainingType">Training Type</a></li>--}%
    %{--<li><a href="${request.contextPath}/employeeTraining/sponsor">Sponsor</a></li>--}%
    %{--<li><a href="${request.contextPath}/employeeTraining/facilitator">Facilitator</a></li>--}%
    %{--<li><a href="${request.contextPath}/employeeTraining/venue">Venue</a></li>--}%
    %{--<li><a href="${request.contextPath}/employeeTraining/evaluationCriteria">Evaluation Criteria</a></li>--}%

    %{--<br/>--}%
    %{--<li><b>Training</b></li>--}%
    %{--<li><a href="${request.contextPath}/employeeTraining/create">Create Training</a></li>--}%
    %{--<li><a href="${request.contextPath}/employeeTraining/trainingOffering">Training Offering</a></li>--}%
    %{--<li><a href="${request.contextPath}/employeeTraining/approveTna">Training Approval</a></li>--}%
    %{--<li><a href="${request.contextPath}/employeeTraining/training">Training need and assessment</a></li>--}%
    %{--<li><a href="${request.contextPath}/employeeTraining/reviewTrainingOffer">Review Training Offering</a></li>--}%
    %{--<li><a href="${request.contextPath}/employeeTraining/trainingAttendance"> Training Attendance</a></li>--}%
    %{--<br/>--}%
    %{--<li><b>Training Evaluation</b></li>--}%
    %{--<li><a href="${request.contextPath}/employeeTraining/participentAsesment">Participant Assessment</a></li>--}%
    %{--<li><a href="${request.contextPath}/employeeTraining/courseEvaluation">Course Evaluation</a></li>--}%
</ul>