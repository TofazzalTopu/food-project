<%@ page import="com.docu.app.ApplicationConfigUtil; com.docu.commons.CommonConstants" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><g:layoutTitle default="Agent Banking"/></title>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>

    <!-- Define Javascript global variables for server side values //-->
    <script type="text/javascript">
        var CONTEXT_PATH = "${request.contextPath}";
        var AJAX_LOADER_ICON_URL = "${request.contextPath}/images/layout/ajax-loader.gif";                      %{--${PathReference.AJAX_LOADER}--}%
        var FACE_BOOK_AJAX_LOADER_ICON_URL = "${request.contextPath}/images/layout/ajax_loader_facebook.gif";   %{--${PathReference.FACE_BOOK_AJAX_LOADER}--}%
        %{--var SUBMISSION_LOADER = "${PathReference.SUBMISSION_LOADER}";--}%
        SUBMISSION_LOADER = new Image();
        SUBMISSION_LOADER.src = "${request.contextPath}/images/layout/submission_loader.gif";                   %{--${PathReference.SUBMISSION_LOADER}--}%

        //This variable is for ajax call reference
        var REF_XHR = null;
        var TMP_REF_XHR = null;
    </script>


    %{--StyleNew--}%



    <link rel="stylesheet" href="${resource(dir: 'css', file: 'docuThemeRollerLayoutStyle.css')}"/>
%{--<!--[if IE 6]>--}%
%{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'docuThemeRollerLayoutStyleIE6.css')}">--}%
%{--<![endif]-->--}%
<!--[if lt IE 7]>
      <link rel="stylesheet" href="${resource(dir: 'css', file: 'docuThemeRollerLayoutStyleIE6.css')}"/>
    <![endif]-->
<!--[if IE 7]>
      <link rel="stylesheet" href="${resource(dir: 'css', file: 'docuThemeRollerLayoutStyleIE7.css')}"/>
    <![endif]-->
<!--[if IE 8]>
      <link rel="stylesheet" href="${resource(dir: 'css', file: 'docuThemeRollerLayoutStyleIE8.css')}"/>
    <![endif]-->
<!--[if IE 9]>
      <link rel="stylesheet" href="${resource(dir: 'css', file: 'docuThemeRollerLayoutStyleIE9.css')}"/>

    <![endif]-->
%{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'layout.css')}"/>--}%
%{--color theme begin--}%

    <g:if test="${session?.preferredTheme}" >
        <link id="currentThemeCss" rel="stylesheet" href="${request.contextPath}/themes/${session.getAttribute("preferredTheme")}/docuThemeRollerColor.css"/>
        <link id="currentThemeJs" rel="stylesheet" href="${request.contextPath}/themes/${session.getAttribute("preferredTheme")}/jquery-ui-custom.css"/>
    </g:if>
    <g:else>
        <link id="currentThemeCss" rel="stylesheet" href="${request.contextPath}/themes/${CommonConstants.DEFAULT_CURRENT_THEME}/docuThemeRollerColor.css"/>
        <link id="currentThemeJs" rel="stylesheet" href="${request.contextPath}/themes/${CommonConstants.DEFAULT_CURRENT_THEME}/jquery-ui-custom.css"/>
    </g:else>


%{--color theme end--}%
    <link rel="stylesheet" href="${resource(dir: 'js/jquery/jgrowl-1.2.5/', file: 'jquery.jgrowl.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'js/message-renderer/', file: 'message-renderer.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'data-table.css')}"/>


    %{--HP/Office Info Start--}%

    %{--globalHeadOffice/list--countryHeadOffice/list--CO Group Mapping--Unposted Voucher List--Opening Balance Information--AddressTitle--}%
    <link rel="stylesheet" href="${resource(dir: 'js/jquery/jqgrid/css', file: 'ui.jqgrid.css')}"/>


    %{--Balance Sheet--Chart of Accounts Report--Income and Expenditure Statement--Ledger Account Head Wise--Opening Balance Information--Receipts and Payment Report--Trial Balance Report--Voucher Report--AddressTitle--}%
    <link rel="stylesheet" href="${resource(dir: 'js/validation/css', file: 'validationEngine.jquery.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'js/validation/css', file: 'template.css')}"/>

    %{--Chart of Accounts Report--Receipts and Payment Report--Trial Balance Report--Voucher Report--}%
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'member-info-tab.css')}"/>

    %{--DayClose--}%
    <link rel="stylesheet" href="${resource(dir: 'js/jquery/fancybox', file: 'jquery.fancybox-1.3.4.css')}"/>

    %{--Chart Of Accounts--}%
    <link rel="stylesheet" href="${resource(dir: 'js/jquery/dynatree-1.0.3/skin', file: 'ui.dynatree.css')}"/>

    %{--Donor Loan Account Mapping--}%
    <link rel="stylesheet" href="${resource(dir: 'js/jquery/multiselect/', file: 'ui.multiselect.css')}"/>

    %{--Fund Receive--}%
    <link rel="stylesheet" href="${resource(dir: 'js/jquery/uploadify/', file: 'uploadify.css')}"/>

    <link rel="stylesheet" href="${resource(dir: 'js/jquery/jquery-pagination', file: 'pagination.css')}"/>
    %{--mb menu css--}%
    <link rel="stylesheet" href="${resource(dir: 'js/jquery/jquery.mb.menu.2.8.5/css', file: 'menu_black.css')}"/>


    %{--js files--}%

    %{--<g:javascript library="jquery" plugin="jquery" src="jquery/jquery-1.6.3.js"/>--}%
    <g:javascript library="jquery" plugin="jquery" src="jquery/jquery-1.4.4.min.js"/>
    %{--<g:javascript  src="http://code.jquery.com/jquery-1.6.4.js"/>--}%
    <g:javascript src="jquery/layout/jquery.layout.min.js"/>                                %{--${PathReference.JQUERY_LAYOUT_MIN}--}%
    <g:javascript src="jquery/ui/jquery.ui.widget.min.js"/>                                 %{--${PathReference.JQUERY_UI_WIDGET_MIN}--}%
    <g:javascript src="jquery/ui/jquery.ui.core.min.js"/>                                   %{--${PathReference.JQUERY_UI_JQUERY_UI_CORE_MIN}--}%
    <g:javascript src="jquery/ui/jquery.ui.datepicker.js"/>                             %{--${PathReference.JQUERY_UI_JQUERY_UI_DATEPICKER_MIN}--}%
    <g:javascript src="jquery/ui/jquery.ui.mouse.min.js"/>                                  %{--${PathReference.JQUERY_UI_JQUERY_UI_MOUSE_MIN}--}%
    <g:javascript src="jquery/ui/jquery.ui.position.min.js"/>                               %{--${PathReference.JQUERY_UI_JQUERY_UI_POSITION_MIN}--}%
    <g:javascript src="jquery/ui/jquery.ui.sortable.min.js"/>                               %{--${PathReference.JQUERY_UI_JQUERY_UI_SORTABLE_MIN}--}%
    <g:javascript src="jquery/ui/jquery.ui.accordion.min.js"/>                              %{--${PathReference.JQUERY_UI_JQUERY_UI_ACCORDION_MIN}--}%
    <g:javascript src="jquery/ui/jquery.ui.autocomplete.min.js"/>                           %{--${PathReference.JQUERY_UI_JQUERY_UI_AUTOCOMPLETE_MIN}--}%
    <g:javascript src="jquery/ui/jquery.ui.dialog.min.js"/>                                 %{--${PathReference.JQUERY_UI_JQUERY_UI_DIALOG_MIN}--}%
    <g:javascript src="jquery/jquery.templateRenderer.js"/>                                 %{--${PathReference.JQUERY_JQUERY_TEMPLATERENDERER}--}%
    <g:javascript src="jquery/jquery.validate.min.js"/>                                     %{--${PathReference.JQUERY_JQUERY_VALIDATE_MIN}--}%
    <g:javascript src="jquery/jquery.blockUI.js"/>                                          %{--${PathReference.JQUERY_BLOCK_UI}--}%
    %{--<g:javascript src="jquery/menu/menu.js"/>--}%
    %{--<g:javascript src="common.js"/>--}%
    <g:javascript src = "validation/js/validation.js"/>                                     %{--${PathReference.JQUERY_VALIDATION}--}%
    <g:javascript src="accounts-common.js"/>                                                %{--${PathReference.ACCOUNTS_COMMON}--}%
    <g:javascript src="jquery/jgrowl-1.2.5/jquery.jgrowl_compressed.js"/>                   %{--${PathReference.JQUERY_JGROWL_JQUERY_JGROWL_COMPRESSED}--}%
    <g:javascript src="message-renderer/message-renderer.js"/>                              %{--${PathReference.MESSAGE_MESSAGE_RENDER}--}%
    <g:javascript src="jquery/ui/jquery.ui.button.min.js"/>                                 %{--${PathReference.JQUERY_UI_JQUERY_UI_BUTTON_MIN}--}%
    <g:javascript src="number-format-widget/jquery.format.1.05.js"/>                        %{--${PathReference.JQUERY_NUMBER_FORMAT}--}%
    <g:javascript src="number-format-widget/jquery.alphanumeric.pack.js"/>                  %{--${PathReference.JQUERY_ALPHA_NUMERIC_FORMAT}--}%
    <g:javascript src="jquery/DataTables-1.8.2/media/js/jquery.dataTables.min.js"/>
    %{--Start HR CSS & JS Contents--}%
    <g:javascript src="jstorage.js"/>
    <g:javascript src="jstoragelib.js"/>
    <g:javascript src="hr.js"/>
    <link rel="stylesheet" href="${resource(dir: 'js/datepicker', file: 'datePicker.css')}"/>
    <g:javascript src="datepicker/jquery.datePicker.js"/>
    <g:javascript src="datepicker/date.js"/>
    %{--End HR CSS & JS Contents--}%


    %{--HP/Office Info Start--}%

    %{--globalHeadOffice/create/edit--countryHeadOffice/create/edit--Balance Sheet--Income and Expenditure Statement--Ledger Account Head Wise--Opening Balance Information--Receipts and Payment Report--Trial Balance Report--Voucher Report--AddressTitle--}%
    <g:javascript src="jquery/maskedinput-1.2.2/jquery.maskedinput-1.2.2.min.js"/>          %{--${PathReference.JQUERY_JQUERY_MASKEDINPUT_MIN}--}%

    %{--globalHeadOffice/list--countryHeadOffice/list--CO Group Mapping--Unposted Voucher List--AddressTitle--}%
    <g:javascript src="jquery/jqgrid/i18n/grid.locale-en.js"/>                              %{--${PathReference.JQUERY_GRID_LOCAL_EN}--}%
    <g:javascript src="jquery/jqgrid/jquery.jqGrid.min.js"/>                                %{--${PathReference.JQUERY_JQUERY_JQGRID_MIN}--}%
    <g:javascript src="jquery/jqgrid/lib/grid.base.js"/>                                    %{--${PathReference.JQUERY_GRID_BASE}--}%
    <g:javascript src="jquery/jqgrid/lib/grid.common.js"/>                                  %{--${PathReference.JQUERY_GRID_COMMON}--}%
    <g:javascript src="jquery/jqgrid/lib/grid.custom.js"/>
    <g:javascript src="jquery/jqgrid/lib/grid.celledit.js"/>                                %{--${PathReference.JQUERY_GRID_CELLEDIT}--}%
    <g:javascript src="jquery/jqgrid/lib/grid.formedit.js"/>                                %{--${PathReference.JQUERY_GRID_FORMEDIT}--}%
    <g:javascript src="jquery/jqgrid/lib/grid.inlinedit.js"/>                               %{--${PathReference.JQUERY_GRID_INLINEEDIT}--}%
    <g:javascript src="jquery/jqgrid/lib/grid.grouping.js"/>                                %{--${PathReference.JQUERY_GRID_GROUPING}--}%
    <g:javascript src="jquery/jqgrid/lib/grid.subgrid.js"/>                                %{--${PathReference.JQUERY_GRID_GROUPING}--}%
    <g:javascript src="jquery/jqgrid/lib/jquery.fmatter.js"/>                               %{--${PathReference.JQUERY_GRID_FMATTER}--}%

    %{--Balance Sheet--Chart of Accounts Report--Income and Expenditure Statement--Ledger Account Head Wise--Opening Balance Information--Receipts and Payment Report--Trial Balance Report--Voucher Report--AddressTitle--}%
    <g:javascript src="validation/js/jquery.validationEngine-en.js"/>                       %{--${PathReference.JQUERY_VALIDATION_ENGINE_EN}--}%
    <g:javascript src="validation/js/jquery.validationEngine.js"/>                          %{--${PathReference.JQUERY_VALIDATION_ENGINE}--}%
    <g:javascript src="highcharts/js/highcharts.js"/>
    %{--<g:javascript src="highcharts/js/modules/exporting.js"/>--}%

    %{--Balance Sheet--Chart of Accounts Report--Income and Expenditure Statement--Ledger Account Head Wise--Opening Balance Information--Receipts and Payment Report--Trial Balance Report--Voucher Report--}%
    <g:javascript src="report-common.js"/>                                                  %{--${PathReference.COMMON_REPORT_JS}--}%

    %{--DayClose--}%
    <g:javascript src="jquery/fancybox/jquery.fancybox-1.3.4.pack.js"/>                     %{--${PathReference.JQUERY_FANCY_BOX}--}%

    %{--Chart Of Accounts--}%
    <g:javascript src="jquery/jquery.cookie.js"/>                                            %{--${PathReference.JQUERY_JQUERY_COOKIE}--}%
    <g:javascript src="jquery/dynatree-1.0.3/jquery.dynatree.min.js"/>                      %{--${PathReference.JQUERY_JQUERY_DYNATREE_MIN}--}%

    %{--Donor Loan Account Mapping--}%
    <g:javascript src="jquery/ui/jquery.ui.draggable.min.js"/>
    <g:javascript src="jquery/multiselect/jquery.scrollTo-min.js"/>
    <g:javascript src="jquery/multiselect/ui.multiselect.js"/>

    %{--Fund Receive--}%
    <g:javascript src="jquery/uploadify/jquery.uploadify.v2.1.4.min.js"/>                   %{--${PathReference.JQUERY_JQUERY_UPLOADIFY_MIN}--}%
    <g:javascript src="jquery/uploadify/swfobject.js"/>                                     %{--${PathReference.JQUERY_UPLOADIFY_SWFOBJECT}--}%

    %{--Group Info--}%
    <g:javascript src="groupInfo/document.js"/>                                             %{--${PathReference.GROUPINFO_DOCUMENT}--}%

    %{--individualLoanCollection/list--}%
    <g:javascript src="jquery/jqgrid/lib/grid.common.js"/>

    %{--loanAccount/previewLoanAccount.gsp--}%
    <g:javascript src="jquery/ui/jquery.ui.tabs.min.js"/>                                   %{--${PathReference.JQUERY_UI_JQUERY_UI_TABS_MIN}--}%

    %{--Loan Rebate Provision List--}%
    <g:javascript src="jquery/jqgrid/lib/jquery.searchFilter.js"/>

    %{--PassbookInfo--}%
    %{--<g:javascript library="jquery"/>--}%
    %{--mb menu js--}%

    <g:javascript src="jquery/jquery.mb.menu.2.8.5/inc/jquery.hoverIntent.js"/>
    <g:javascript src="jquery/jquery.mb.menu.2.8.5/inc/jquery.metadata.js"/>
    <g:javascript src="jquery/jquery.mb.menu.2.8.5/inc/mbMenu.js"/>
    <g:javascript src="jquery/jquery.mb.menu.2.8.5/inc/styleswitch.js"/>

    <script type="text/javascript" src="${request.contextPath}/js/jquery/jquery.history.js"></script>
    <script type="text/javascript" src="${request.contextPath}/js/jquery.smartWizard-2.0.min.js"></script>
    <script type="text/javascript" src="${request.contextPath}/js/jquery.cookies.2.2.0.min.js"></script>

    <script type="text/javascript" src="${request.contextPath}/js/jquery.ui.slider.js"></script>
    <script type="text/javascript" src="${request.contextPath}/js/jquery-ui-timepicker-addon.js"></script>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui-timepicker-addon.css')}"/>


    <link rel="stylesheet" href="${resource(dir: 'js/flexBox/css', file: 'jquery.flexbox.css')}"/>


    <link rel="stylesheet" href="${resource(dir: 'css', file: 'newtemp.css')}"/>

    <g:javascript src="flexBox/js/jquery.flexbox.min.js"/>

    %{--jquery datepicker--}%

    %{--<g:javascript src="jquery/jquery.datePicker.js"/>--}%

    %{--IE6 png fixing--}%
    <!--[if lt IE 7]>
    <g:javascript src="pngfix.js"/>
    <![endif]-->

    <g:javascript src="jquery/jquery-pagination/jquery.pagination.js"/>
    <!--[if lt IE 7]>
    <script>
      DD_belatedPNG.fix('img, .button-delete, .login, .color_theme_container, .color_theme, .label_container, .close, .header, h1, h2,  a, #progress-bar, .txt, .btn, .leftmenu_mf_accountsLedgerMapping, .leftmenu_mf_loanRebatePayment, .leftmenu_mf_povertyScoreCard, .leftmenu_mf_claimedDeathBenefitList, .leftmenu_mf_loanPortfolioStatus, .leftmenu_mf_UndoDisbursedLoan, .leftmenu_mf_savingsProductType, .leftmenu_mf_feeChartOfAccountsMapping, .leftmenu_mf_projectSummary, .leftmenu_mf_relationship, .leftmenu_mf_closeMemberList, .leftmenu_mf_coGroupMapping, .leftmenu_mf_loanApprove, .leftmenu_mf_addressTitle, .leftmenu_mf_unapprovedGlobalHolidayList, .leftmenu_mf_securityMoneySavingsInformation, .leftmenu_mf_groupStatus, .leftmenu_mf_approvedDeathBenefitList, .leftmenu_mf_unapprovedLocalHolidayList, .leftmenu_mf_loanAccount, .leftmenu_mf_savingsAccountStatus, .leftmenu_mf_transferLoan, .leftmenu_mf_masterRollSavingsWithdrawal, .leftmenu_mf_maritalStatus, .leftmenu_mf_rectifyDailyCollectionRegister, .leftmenu_mf_feeApplicableFor, .leftmenu_mf_approvedWithdrawalList, .leftmenu_mf_payDeathBenefitList, .leftmenu_mf_projectProductMapping, .leftmenu_mf_masterRollSavingsWithdrawalList, .leftmenu_mf_loanCollectionScheduleAsOnNextCollectionDate, .leftmenu_mf_createApplicableGender, .leftmenu_mf_localHoliday, .leftmenu_mf_recieveGroup, .leftmenu_mf_projectFeeList, .leftmenu_mf_transferMemberBranch, .leftmenu_mf_newLoanProposal, .leftmenu_mf_feeInfo, .leftmenu_mf_rectifyLoanCollection, .leftmenu_mf_borrowersRepaymentBehavioralStatus, .leftmenu_mf_approveWithdrawalProposalBulk, .leftmenu_mf_payDeathBenefit, .leftmenu_mf_newProject, .leftmenu_mf_memberInfoReports, .leftmenu_mf_interestProvisionFrequency, .leftmenu_mf_approveLoanBulk, .leftmenu_mf_transferedGroupList, .leftmenu_mf_donorList, .leftmenu_mf_rebateProvisionPayment, .leftmenu_mf_groupLoanCollection, .leftmenu_mf_closeMember, .leftmenu_mf_loanStatus, .leftmenu_mf_newWithdrawalProposal, .leftmenu_mf_city, .leftmenu_mf_withdrawalProposalList, .leftmenu_mf_newDonor, .leftmenu_mf_settleLoan, .leftmenu_mf_loanSecurityDeposit, .leftmenu_mf_approvedLocalHolidayList, .leftmenu_mf_groupSavingsCollection, .leftmenu_mf_memberAccount, .leftmenu_mf_day, .leftmenu_mf_interestCalculationMethod, .leftmenu_mf_employeeDesignation, .leftmenu_mf_loanApprovalStatus, .leftmenu_mf_portfolioAtRisk, .leftmenu_mf_loanCollectionList, .leftmenu_mf_approvedGlobalHolidayList, .leftmenu_mf_loanAccountVoucherSearch, .leftmenu_mf_editGroup, .leftmenu_mf_writtenOffLoanList, .leftmenu_mf_officeType, .leftmenu_mf_disbursedList, .leftmenu_mf_renewMemberList, .leftmenu_mf_donorLoanAccountMapping, .leftmenu_mf_interestCalculationFrequency, .leftmenu_mf_memberStatus, .leftmenu_mf_recievedGroupList, .leftmenu_mf_cancelClaimedDeathBenefit, .leftmenu_mf_recievedMemberList, .leftmenu_mf_expiredMember, .leftmenu_mf_reviewDeathBenefitClaim, .leftmenu_mf_groupAccount, .leftmenu_mf_passbookManagement, .leftmenu_mf_memberCloseReason, .leftmenu_mf_overdueAnalysis, .leftmenu_mf_countryOfficeHierarchy, .leftmenu_mf_workingDay, .leftmenu_mf_loanDisbursementReports, .leftmenu_mf_disburseLoan, .leftmenu_mf_loanProduct, .leftmenu_mf_schemeInfo, .leftmenu_mf_loanProposalStatus, .leftmenu_mf_transferedMemberList, .leftmenu_mf_badLoanCollectionList, .leftmenu_mf_newProductFee, .leftmenu_mf_projectList, .leftmenu_mf_renewMember, .leftmenu_mf_microfinanceConfiguration, .leftmenu_mf_transactionSummaryReport, .leftmenu_mf_repaymentRule, .leftmenu_mf_groupCloseReason, .leftmenu_mf_groupMemberSecDepLoanInfo, .leftmenu_mf_newLoanProduct, .leftmenu_mf_programsInfo, .leftmenu_mf_savingsCollectionList, .leftmenu_mf_individualLoanCollection, .leftmenu_mf_loanSearch, .leftmenu_mf_continent, .leftmenu_mf_frequency, .leftmenu_mf_overdueInformation, .leftmenu_mf_loanWriteOff, .leftmenu_mf_localHolidayList, .leftmenu_mf_savingsWithdrawalList, .leftmenu_mf_individualSavingsCollection, .leftmenu_mf_approveWithdrawalProposal, .leftmenu_mf_subsidiaryAccountsList, .leftmenu_mf_occupation, .leftmenu_mf_collectionFrequency, .leftmenu_mf_ageingOfPrincipalOutstanding, .leftmenu_mf_globalHolidayList, .leftmenu_mf_countryProgramInfo, .leftmenu_mf_globalHoliday, .leftmenu_mf_subsidiaryAccountManagement, .leftmenu_mf_chartAccountsLedgerMapping, .leftmenu_mf_advanceLoanCollection, .leftmenu_mf_savingsProductList, .leftmenu_mf_transferMemberGroup, .leftmenu_mf_memberWiseSavingLoanSecurityDepositTransaction, .leftmenu_mf_probableExpiredMembers, .leftmenu_mf_trendReport, .leftmenu_mf_passbookStatus, .leftmenu_mf_dailyCollectionRegisterList, .leftmenu_mf_transferGroup, .leftmenu_mf_interestCreditFrequency, .leftmenu_mf_newSavingsProduct, .leftmenu_mf_savingsAccountList, .leftmenu_mf_reconciliationInfo, .leftmenu_mf_savingsPortfolioStatus, .leftmenu_mf_memberAsset, .leftmenu_mf_uptoDateLoanAnalysisStatement, .leftmenu_mf_memberList, .leftmenu_mf_dailyCollectionRegister, .leftmenu_mf_event, .leftmenu_mf_memberProfile, .leftmenu_mf_modifyLoan, .leftmenu_mf_mfChartOfAccountsMapping, .leftmenu_mf_memberGender, .leftmenu_mf_salutation, .leftmenu_mf_country, .leftmenu_mf_sectorInfo, .leftmenu_mf_advanceLoanCollectionList, .leftmenu_mf_memberLoanCollection, .leftmenu_mf_interestRateType, .leftmenu_mf_newSavingsAccount, .leftmenu_mf_officeCategory, .leftmenu_mf_groupList, .leftmenu_mf_ratioAnalysis, .leftmenu_mf_claimDeathBenefit, .leftmenu_mf_basicBranchInformation, .leftmenu_mf_collectionsSheet, .leftmenu_mf_securityMoneySavingsWithdrawalInformation, .leftmenu_mf_domainStatus, .leftmenu_mf_groupCategory, .leftmenu_mf_loanCollectionReports, .leftmenu_mf_savingsWithdrawalStatus, .leftmenu_mf_loanProposalList, .leftmenu_mf_feeCalculationOption, .leftmenu_mf_recieveMember, .leftmenu_mf_newProjectFee, .leftmenu_mf_badLoanCollection, .leftmenu_mf_savingsWithdrawalApprovalStatus, .leftmenu_mf_createOperationType, .leftmenu_mf_amountType, .leftmenu_mf_rebateOn, .leftmenu_mf_groupGender, .leftmenu_mf_loanRebatePaymentList, .leftmenu_mf_savingsAccountType, .leftmenu_mf_collectionDayWiseGroupList, .leftmenu_mf_timeOfCharge, .leftmenu_mf_groupWiseLoanSecurityTransaction, .leftmenu_mf_newGroup, .leftmenu_mf_loanProductList, .leftmenu_mf_deathBenefitStatus, .leftmenu_mf_newMember, .leftmenu_mf_search, .leftmenu_mf_approvedLoanList, .leftmenu_accounts_accountCodeGenerationRule, .leftmenu_accounts_advanceLedgerBalance, .leftmenu_accounts_advanceTrialBalance, .leftmenu_accounts_balanceSheet, .leftmenu_accounts_bankAccountLedger, .leftmenu_accounts_bankAccountList, .leftmenu_accounts_bankAccountType, .leftmenu_accounts_bankBranchList, .leftmenu_accounts_bankInfo, .leftmenu_accounts_billLedgerMapping, .leftmenu_accounts_businessDayRestrictionConfig, .leftmenu_accounts_chartOfAccounts, .leftmenu_accounts_chequeLeafInfo, .leftmenu_accounts_chequeLeafList, .leftmenu_accounts_chequeLeafStatus, .leftmenu_accounts_chequeRegister, .leftmenu_accounts_chequeRegisterList, .leftmenu_accounts_costCenterConfiguration, .leftmenu_accounts_createAccountDomainStatus, .leftmenu_accounts_createAccountLevel, .leftmenu_accounts_createEntityInfo, .leftmenu_accounts_createFinancialYear, .leftmenu_accounts_createGroup, .leftmenu_accounts_createMainSubGroup, .leftmenu_accounts_createReconciliation, .leftmenu_accounts_createSubGroup, .leftmenu_accounts_createTitle, .leftmenu_accounts_createTransferFrequency, .leftmenu_accounts_createVoucherNumberSequence, .leftmenu_accounts_createVoucherStatus, .leftmenu_accounts_createVoucherType, .leftmenu_accounts_currencyExchangeRateSetup, .leftmenu_accounts_dayCloseAccountsRestrictionRule, .leftmenu_accounts_dayOpenClose, .leftmenu_accounts_incomeAndExpenditureStatement, .leftmenu_accounts_interCostCenterCashBankTransferLedgerMapping, .leftmenu_accounts_interCostCenterNonCashBankTransferLedgerMapping, .leftmenu_accounts_interOfficeCashBankMapping, .leftmenu_accounts_interOfficeNonCashBankMapping, .leftmenu_accounts_journalVoucherList, .leftmenu_accounts_ledgerBalance, .leftmenu_accounts_ledgerByAccountHead, .leftmenu_accounts_ledgerStatement, .leftmenu_accounts_masterChartOfAccounts, .leftmenu_accounts_multiBillPayment, .leftmenu_accounts_newBankAccount, .leftmenu_accounts_newBankBranch, .leftmenu_accounts_newBillType, .leftmenu_accounts_newFundType, .leftmenu_accounts_newJournalVoucher, .leftmenu_accounts_newPaymentVoucher, .leftmenu_accounts_newReceiveVoucher, .leftmenu_accounts_newTransferVoucher, .leftmenu_accounts_openingBalance, .leftmenu_accounts_paidBillList, .leftmenu_accounts_payBill, .leftmenu_accounts_paymentVoucherList, .leftmenu_accounts_postVoucher, .leftmenu_accounts_postVoucherList, .leftmenu_accounts_receiptsAndPayments, .leftmenu_accounts_receiveFund, .leftmenu_accounts_receiveFundList, .leftmenu_accounts_receiveVoucherList, .leftmenu_accounts_reconciledFundTransferList, .leftmenu_accounts_rectifyTransferredFund, .leftmenu_accounts_subsidiaryLedgerBalance, .leftmenu_accounts_titleAndAccountHeadMapping, .leftmenu_accounts_transferFund, .leftmenu_accounts_transferFundList, .leftmenu_accounts_transferVoucherList, .leftmenu_accounts_trialBalance, .leftmenu_accounts_unApproveBankAccountList, .leftmenu_accounts_unReconciledFundTransferList, .leftmenu_accounts_vendor, .leftmenu_accounts_vouchers');
    </script>
    <![endif]-->
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'uploader.css')}"/>
    <g:javascript src="fileuploader.js"/>
    <g:javascript src="common.js"/>
    <g:layoutHead/>
    <script type="text/javascript">
        $(document).ready(function () {
            $('.layout-loader').html('');
            $('.ui-layout-center, .ui-layout-north, .ui-layout-south, .ui-layout-west').removeClass('layout-node');
            $('body').layout({ applyDefaultStyles: true
                ,south__applyDefaultStyles: false
                , north__applyDefaultStyles:false});

        });

    </script>
    <script type="text/javascript">
        $(document).ready(function(){
            CursorListener.setFocusOnFirstField();
        });
    </script>

</head>

<body>
<div id="layout-body-ajax-loader" style="display:none;">
    <img src="${request.contextPath}/images/spinner.gif" alt="Spinner"/> Please wait while system is loading your request...
</div>

<div class="layout-loader"><img src="${request.contextPath}/images/layout/ajax-loader.gif" alt="Ajax-loader"/></div>
<div id="hr-ajax-musk" style="display:none;"></div>
<g:if test="${true}">
    <div class="ui-layout-center bg_map layout-node">
</g:if>
<g:else>
    <div class="ui-layout-center layout-node">
</g:else>
<div class="sbi-layout-body-container" id="layout-body-ajax"><g:layoutBody/></div>
</div>

<div class="ui-layout-north layout-top-panel layout-node" style="padding-bottom:0px !important;"><g:render template='/layouts/header'/></div>

<div class="ui-layout-south  layout-node"><g:render template='/layouts/footer'/></div>
%{--<DIV class="ui-layout-east"><g:render template='/layouts/rightpanel'/></DIV>--}%
%{--<div class="ui-layout-west  layout-node">--}%
    %{--<g:include controller="menu" action="leftMenu" params="[controllerName: params.controller, actionName: params.action]"/>--}%
    %{--<g:render template='/layouts/dynamicLeftMenu' model="[controllerName: params.controller, actionName: params.action]"/>--}%
    %{--<g:render template='/layouts/leftmenu'/>--}%
%{--</div>--}%
</body>
</html>