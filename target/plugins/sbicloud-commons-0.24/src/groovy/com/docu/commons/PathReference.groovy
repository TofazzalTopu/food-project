package com.docu.commons

import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Created by IntelliJ IDEA.
 * User: Zia
 * Date: 12/28/10
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public final class PathReference {

    static ConfigObject config = ConfigurationHolder.config

    public static String APP_NAME = config.application.name   //"sbicloud"
    public static final String HEADER_IMAGE_PATH = "/" + APP_NAME + "/images/layout/"
    public static final String HEADER_MENU_IMAGE_PATH = "/" + APP_NAME + "/images/header/"
    public static final String LEFT_MENU_IMAGE_PATH = "/" + APP_NAME + "/images/leftmenu/"
    public static final String JQUERY_LAYOUT = JQUERY + "layout/"
    public static final String JQUERY_UI = JQUERY + "ui/"
    public static final String JQUERY = "jquery/"
    public static final String JQUERY_MENU = JQUERY + "menu/"
//  public static final String COMMON="common.js"
    public static final String COMMON_REPORT_JS = "report-common.js"
    public static final String JQUERY_WATER_MARK = JQUERY + "watermark/jquery.watermark.js"
    public static final String JQUERY_JGROWL = JQUERY + "jgrowl-1.2.5/"
    public static final String MESSAGE = "message-renderer/"
    public static final String JQUERY_MASKEDINPUT = "jquery/maskedinput-1.2.2/"
    public static final String JQUERY_VALIDATION_ENGINE = "validation/js/jquery.validationEngine.js"
    public static final String JQUERY_VALIDATION_ENGINE_EN = "validation/js/jquery.validationEngine-en.js"
    public static final String JQUERY_VALIDATION = "validation/js/validation.js"
    public static final String JQUERY_UPLOADIFY = "jquery/uploadify/"
    public static final String GROUPINFO = "groupInfo/"
    public static final String JQUERY_JQGRID_I18N = "jquery/jqgrid/i18n/"
    public static final String JQUERY_JQGRID = "jquery/jqgrid/"
    public static final String JQUERY_JQGRID_LIB = "jquery/jqgrid/lib/"
    public static final String JQUERY_DYNATREE = "jquery/dynatree-1.0.3/"

    public static final String JQUERY_FANCY_URL = "jquery/fancybox/"

    /*Header Image path*/
    public static final String LOGO = HEADER_IMAGE_PATH + "logo.png";
    public static final String AJAX_LOADER = HEADER_IMAGE_PATH + "ajax-loader.gif";
    public static final String SUBMISSION_LOADER = HEADER_IMAGE_PATH + "submission_loader.gif";
    public static final String FACE_BOOK_AJAX_LOADER = HEADER_IMAGE_PATH + "ajax_loader_facebook.gif";

    /*for grid*/
    public static final String DONE = HEADER_IMAGE_PATH + "done.png";
    public static final String CANCEL = HEADER_IMAGE_PATH + "cancel.png";

    /*New Loan Proposal*/
    public static final String MEMBER_PROFILE = HEADER_IMAGE_PATH + "member_profile.gif";

    /*Day Close Error*/
    public static final String DAY_CLOSE_ERROR = HEADER_IMAGE_PATH + "day_close_error.gif";

    /*Header Top Icon path*/
    public static final String MY_ACCOUNT = HEADER_MENU_IMAGE_PATH + "my_account.gif";
    public static final String CHANGE_PASSWORD = HEADER_MENU_IMAGE_PATH + "change_password.gif";
    public static final String LOGOUT = HEADER_MENU_IMAGE_PATH + "logout.gif";

    /*Header Menu Icon path*/
    public static final String PROGRAM = HEADER_MENU_IMAGE_PATH + "program.gif";
    public static final String EMPLOYEE = HEADER_MENU_IMAGE_PATH + "employee.gif";
    public static final String OFFICE = HEADER_MENU_IMAGE_PATH + "office.gif";
    public static final String MEMBER = HEADER_MENU_IMAGE_PATH + "member.gif";
    public static final String GROUP = HEADER_MENU_IMAGE_PATH + "group.gif";
    public static final String MYDASHBOARD = HEADER_MENU_IMAGE_PATH + "myDashBoard.gif";

    /*Left Menu Icon path*/
    public static final String DAY_OPEN_CLOSE = LEFT_MENU_IMAGE_PATH + "day_open_close.gif";

    public static final String NEW_GROUP = LEFT_MENU_IMAGE_PATH + "new_group.gif";
    public static final String GROUP_LIST = LEFT_MENU_IMAGE_PATH + "group_list.gif";

    public static final String NEW_MEMBER = LEFT_MENU_IMAGE_PATH + "new_member.gif";
    public static final String MEMBER_LIST = LEFT_MENU_IMAGE_PATH + "member_list.gif";

    public static final String NEW_LOAN_PROPOSAL = LEFT_MENU_IMAGE_PATH + "new_loan_proposal.gif";
    public static final String LOAN_PROPOSAL_LIST = LEFT_MENU_IMAGE_PATH + "loan_proposal_list.gif";
    public static final String APPROVE_LOAN_BULK = LEFT_MENU_IMAGE_PATH + "approve_loan_bulk.gif";
    public static final String APPROVE_LOAN_LIST = LEFT_MENU_IMAGE_PATH + "approve_loan_list.gif";
    public static final String DISBURSE_LOAN = LEFT_MENU_IMAGE_PATH + "disburse_loan.gif";
    public static final String DISBURSE_LIST = LEFT_MENU_IMAGE_PATH + "disburse_list.gif";

    public static final String CREATE_GROUP_COLLECTION = LEFT_MENU_IMAGE_PATH + "create_group_collection.gif";
    public static final String CREATE_INDIVIDUAL_LOAN_COLLECTION = LEFT_MENU_IMAGE_PATH + "create_individual_loan_collection.gif";
    public static final String LIST_LOAN_COLLECTION = LEFT_MENU_IMAGE_PATH + "list_loan_collection.gif";
    public static final String GROUP_COLLECTION_RECONCILIATION = LEFT_MENU_IMAGE_PATH + "group_collection_reconciliation.gif";

    public static final String NEW_GLOBAL_HEAD_OFFICE = LEFT_MENU_IMAGE_PATH + "new_global_head_office.gif";
    public static final String GLOBAL_HEAD_OFFICE_LIST = LEFT_MENU_IMAGE_PATH + "global_head_office_list.gif";
    public static final String NEW_BRANCH_OFFICE = LEFT_MENU_IMAGE_PATH + "new_branch_office.gif";
    public static final String BRANCH_OFFICE_LIST = LEFT_MENU_IMAGE_PATH + "branch_office_list.gif";
    public static final String NEW_AREA_OFFICE = LEFT_MENU_IMAGE_PATH + "new_area_office.gif";
    public static final String AREA_OFFICE_LIST = LEFT_MENU_IMAGE_PATH + "area_office_list.gif";
    public static final String NEW_REGIONAL_OFFICE = LEFT_MENU_IMAGE_PATH + "new_regional_office.gif";
    public static final String REGIONAL_OFFICE_LIST = LEFT_MENU_IMAGE_PATH + "regional_office_list.gif";
    public static final String NEW_DIVISIONAL_OFFICE = LEFT_MENU_IMAGE_PATH + "new_divisional_office.gif";
    public static final String DIVISIONAL_OFFICE_LIST = LEFT_MENU_IMAGE_PATH + "divisional_office_list.gif";
    public static final String NEW_VIRTUAL_OUTPOST = LEFT_MENU_IMAGE_PATH + "new_virtual_outpost.gif";
    public static final String VIRTUAL_OUTPOST_LIST = LEFT_MENU_IMAGE_PATH + "virtual_outpost_list.gif";
    public static final String NEW_COUNTRY_HEAD_OFFICE = LEFT_MENU_IMAGE_PATH + "new_country_head_office.gif";
    public static final String COUNTRY_HEAD_OFFICE_LIST = LEFT_MENU_IMAGE_PATH + "country_head_office_list.gif";

    public static final String EMPLOYEE_LIST = LEFT_MENU_IMAGE_PATH + "employee_list.gif";

    public static final String PROGRAM_LIST = LEFT_MENU_IMAGE_PATH + "program_list.gif";

    public static final String NEW_PROJECT = LEFT_MENU_IMAGE_PATH + "new_project.gif";
    public static final String PROJECT_LIST = LEFT_MENU_IMAGE_PATH + "project_list.gif";

    public static final String NEW_LOAN_PRODUCT = LEFT_MENU_IMAGE_PATH + "new_loan_product.gif";
    public static final String LOAN_PRODUCT_LIST = LEFT_MENU_IMAGE_PATH + "loan_product_list.gif";

    public static final String LOAN_COLLECTION_DATE_WISE = LEFT_MENU_IMAGE_PATH + "loan_collection_date_wise.gif";
    public static final String LOAN_DISBURSEMENT_DATE_WISE = LEFT_MENU_IMAGE_PATH + "loan_disbursement_date_wise.gif";
    public static final String LOAN_OVERDUE_DATE_WISE = LEFT_MENU_IMAGE_PATH + "loan_overdue_date_wise.gif";
    public static final String LOAN_COLLECTIONS_BY_ACCOUNT = LEFT_MENU_IMAGE_PATH + "loan_collections_by_account.gif";
    public static final String LOAN_SCHEDULES_BY_ACCOUNT = LEFT_MENU_IMAGE_PATH + "loan_schedules_by_account.gif";
    public static final String LOAN_PROVISIONS_BY_ACCOUNT = LEFT_MENU_IMAGE_PATH + "loan_provisions_by_account.gif";
    public static final String LOAN_COLLECTIONS_WEEKLY = LEFT_MENU_IMAGE_PATH + "loan_collections_weekly.gif";

    public static final String CONTINENT = LEFT_MENU_IMAGE_PATH + "continent.gif";
    public static final String COUNTRY = LEFT_MENU_IMAGE_PATH + "country.gif";
    public static final String PROGRAM_INFO = LEFT_MENU_IMAGE_PATH + "programs_info.gif";
    public static final String COUNTRY_OFFICE_HIERARCHY = LEFT_MENU_IMAGE_PATH + "country_office_hierarchy.gif";
    public static final String OFFICE_TYPE = LEFT_MENU_IMAGE_PATH + "office_type.gif";
    public static final String MICROFINANCE_CONFIGURATION = LEFT_MENU_IMAGE_PATH + "city.gif";
    public static final String CITY = LEFT_MENU_IMAGE_PATH + "city.gif";
    public static final String ADDRESS_TITLE = LEFT_MENU_IMAGE_PATH + "address_title.gif";
    public static final String EMPLOYEE_DESIGNATION = LEFT_MENU_IMAGE_PATH + "employee_designation.gif";
    public static final String GROUP_CATEGORY = LEFT_MENU_IMAGE_PATH + "group_category.gif";
    public static final String GROUP_GENDER = LEFT_MENU_IMAGE_PATH + "group_gender.gif";
    public static final String MEMBER_GENDER = LEFT_MENU_IMAGE_PATH + "member_gender.gif";
    public static final String MEMBER_STATUS = LEFT_MENU_IMAGE_PATH + "member_status.gif";
    public static final String SALUTATION = LEFT_MENU_IMAGE_PATH + "salutation.gif";
    public static final String OCCUPATION = LEFT_MENU_IMAGE_PATH + "occupation.gif";
    public static final String MEMBER_ASSET = LEFT_MENU_IMAGE_PATH + "member_asset.gif";
    public static final String MARITAL_STATUS = LEFT_MENU_IMAGE_PATH + "marital_status.gif";
    public static final String RELATIONSHIP = LEFT_MENU_IMAGE_PATH + "relationship.gif";
    public static final String DOMAIN_STATUS = LEFT_MENU_IMAGE_PATH + "domain_status.gif";
    public static final String DEATH_BENEFIT_CONFIGURATION = LEFT_MENU_IMAGE_PATH + "domain_status.gif";
    public static final String ACTIVITY_STATUS = LEFT_MENU_IMAGE_PATH + "activity_status.gif";
    public static final String LOAN_APPROVAL_STATUS = LEFT_MENU_IMAGE_PATH + "loan_approval_status.gif";
    public static final String LOAN_PROPOSAL_STATUS = LEFT_MENU_IMAGE_PATH + "loan_proposal_status.gif";
    public static final String LOAN_STATUS = LEFT_MENU_IMAGE_PATH + "loan_status.gif";
    public static final String LOAN_PORTFOLIO_STATUS = LEFT_MENU_IMAGE_PATH + "loan_status.gif";
    public static final String INTEREST_RATE_TYPE = LEFT_MENU_IMAGE_PATH + "interest_rate_type.gif";
    public static final String PASSBOOK_STATUS = LEFT_MENU_IMAGE_PATH + "passbook_status.gif";
    public static final String DAY = LEFT_MENU_IMAGE_PATH + "day.gif";
    public static final String COLLECTION_FREQUENCY = LEFT_MENU_IMAGE_PATH + "collection_frequency.gif";
    public static final String SECTOR_INFO = LEFT_MENU_IMAGE_PATH + "sector_info.gif";
    public static final String SCHEME_INFO = LEFT_MENU_IMAGE_PATH + "scheme_info.gif";
    /*---*/
    public static final String FEE_INFO = LEFT_MENU_IMAGE_PATH + "fee_info.gif";
    public static final String FEE_APPLICABLE_FOR = LEFT_MENU_IMAGE_PATH + "fee_applicable_for.gif";
    public static final String FEE_CALCULATION_OPTION = LEFT_MENU_IMAGE_PATH + "fee_calculation_option.gif";
    public static final String TIME_OF_CHARGE = LEFT_MENU_IMAGE_PATH + "time_of_charge.gif";
    /*---*/
    public static final String CREATE_1 = LEFT_MENU_IMAGE_PATH + "create_1.gif";
    public static final String PROJECT_ACCOUNTS = LEFT_MENU_IMAGE_PATH + "project_accounts.gif";
    public static final String SHOW_PROJECT_CHART = LEFT_MENU_IMAGE_PATH + "show_project_chart.gif";

    /* Bank Related Links */
    public static final String BANK_INFORMATION = LEFT_MENU_IMAGE_PATH + "bank_information.gif";
    public static final String NEW_BANK_BRANCH = LEFT_MENU_IMAGE_PATH + "new_bank_branch.gif";
    public static final String BANK_BRANCH_LIST = LEFT_MENU_IMAGE_PATH + "bank_branch_list.gif";
    public static final String BANK_ACCOUNT_TYPE = LEFT_MENU_IMAGE_PATH + "show_project_chart.gif";
    public static final String NEW_BANK_ACCOUNT = LEFT_MENU_IMAGE_PATH + "show_project_chart.gif";
    public static final String BANK_ACCOUNT_LIST = LEFT_MENU_IMAGE_PATH + "show_project_chart.gif";

    public static final String OPENING_BALANCE_LIST = LEFT_MENU_IMAGE_PATH + "show_project_chart.gif";
    /* Bank Related Links */

    /* Voucher Info (Credit:Receive) Related Links */
    public static final String VOUCHER_INFORMATION_CR_RECEIVE = LEFT_MENU_IMAGE_PATH + "show_project_chart.gif";
    /* Voucher Info (Credit:Receive) Related Links */

    //JQuery UI file path (styleNew.gsp)
    public static final String JQUERY_JQUERY_MIN = JQUERY + "jquery-1.4.4.min.js"

    public static final String JQUERY_LAYOUT_MIN = JQUERY_LAYOUT + "jquery.layout.min.js"
    public static final String JQUERY_UI_WIDGET_MIN = JQUERY_UI + "jquery.ui.widget.min.js"
    public static final String JQUERY_UI_JQUERY_UI_CORE_MIN = JQUERY_UI + "jquery.ui.core.min.js"
    public static final String JQUERY_UI_JQUERY_UI_MOUSE_MIN = JQUERY_UI + "jquery.ui.mouse.min.js"
    public static final String JQUERY_UI_JQUERY_UI_POSITION_MIN = JQUERY_UI + "jquery.ui.position.min.js"
    public static final String JQUERY_UI_JQUERY_UI_SORTABLE_MIN = JQUERY_UI + "jquery.ui.sortable.min.js"
    public static final String JQUERY_UI_JQUERY_UI_ACCORDION_MIN = JQUERY_UI + "jquery.ui.accordion.min.js"
    public static final String JQUERY_UI_JQUERY_UI_TABS_MIN = JQUERY_UI + "jquery.ui.tabs.min.js"
    public static final String JQUERY_UI_JQUERY_UI_AUTOCOMPLETE_MIN = JQUERY_UI + "jquery.ui.autocomplete.min.js"
    public static final String JQUERY_UI_JQUERY_UI_DIALOG_MIN = JQUERY_UI + "jquery.ui.dialog.min.js"
    public static final String JQUERY_JQUERY_TEMPLATERENDERER = JQUERY + "jquery.templateRenderer.js"
    public static final String JQUERY_JQUERY_VALIDATE_MIN = JQUERY + "jquery.validate.min.js"
    public static final String JQUERY_BLOCK_UI = JQUERY + "jquery.blockUI.js"
    public static final String JQUERY_MENU_MENU = JQUERY_MENU + "menu.js"
    public static final String COMMON = "common.js"
    public static final String ACCOUNTS_COMMON = "accounts-common.js"
    public static final String JQUERY_JGROWL_JQUERY_JGROWL_COMPRESSED = JQUERY_JGROWL + "jquery.jgrowl_compressed.js"
    public static final String MESSAGE_MESSAGE_RENDER = MESSAGE + "message-renderer.js"
    public static final String JQUERY_UI_JQUERY_UI_BUTTON_MIN = JQUERY_UI + "jquery.ui.button.min.js"
    public static final String JQUERY_NUMBER_FORMAT ="number-format-widget/jquery.format.1.05.js"
    public static final String JQUERY_ALPHA_NUMERIC_FORMAT ="number-format-widget/jquery.alphanumeric.pack.js"

//  jquery ui file path (all gsp page)
    public static final String JQUERY_JQUERY_MASKEDINPUT_MIN = JQUERY_MASKEDINPUT + "jquery.maskedinput-1.2.2.min.js"
    public static final String JQUERY_JQUERY_TIMEPICKER = JQUERY_MASKEDINPUT + "jquery-ui-timepicker-addon.js"
//  jquery ui file path (groupInfo/createLocalHoliday.gsp)
//    public static final String JQUERY_JQUERY_UPLOADIFY_MIN = JQUERY_UPLOADIFY + "jquery.uploadify.v2.1.0.min.js"
    public static final String JQUERY_JQUERY_UPLOADIFY_MIN = JQUERY_UPLOADIFY + "jquery.uploadify.v2.1.4.min.js"
    public static final String JQUERY_UPLOADIFY_SWFOBJECT = JQUERY_UPLOADIFY + "swfobject.js"
    public static final String GROUPINFO_DOCUMENT = GROUPINFO + "document.js"
//  jquery ui file path (groupInfo/localHolidayList.gsp)
    public static final String JQUERY_GRID_LOCAL_EN = JQUERY_JQGRID_I18N + "grid.locale-en.js"
    public static final String JQUERY_JQUERY_JQGRID_MIN = JQUERY_JQGRID + "jquery.jqGrid.min.js"
    public static final String JQUERY_GRID_BASE = JQUERY_JQGRID_LIB + "grid.base.js"
    public static final String JQUERY_GRID_COMMON = JQUERY_JQGRID_LIB + "grid.common.js"
    public static final String JQUERY_GRID_FORMEDIT = JQUERY_JQGRID_LIB + "grid.formedit.js"
    public static final String JQUERY_GRID_CELLEDIT = JQUERY_JQGRID_LIB + "grid.celledit.js"
    public static final String JQUERY_GRID_INLINEEDIT = JQUERY_JQGRID_LIB + "grid.inlinedit.js"
    public static final String JQUERY_GRID_GROUPING = JQUERY_JQGRID_LIB + "grid.grouping.js"
    public static final String JQUERY_GRID_FMATTER = JQUERY_JQGRID_LIB + "jquery.fmatter.js"
    public static final String JQUERY_GRID_EXCEL_EXPORT = JQUERY_JQGRID_LIB + "grid.import.js"
    public static final String JQUERY_GRID_JSON_XML = JQUERY_JQGRID_LIB + "JsonXml.js"


//    jquery ui file path (chart of accounts summary.gsp page)
    public static final String JQUERY_JQUERY_COOKIE = JQUERY + "jquery.cookie.js"
    public static final String JQUERY_JQUERY_DYNATREE_MIN = JQUERY_DYNATREE + "jquery.dynatree.min.js"


    public static final String JQUERY_FANCY_BOX = JQUERY_FANCY_URL + "jquery.fancybox-1.3.4.pack.js"

//    png fixing for IE6
    public static final String PNG_FIX = "pngfix.js"

}
