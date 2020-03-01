package com.docu.commons

/**
 * Created by IntelliJ IDEA.
 * User: Rafiq
 * Date: 1/28/11
 * Time: 6:07 PM
 * To change this template use File | Settings | File Templates.
 */
public final class CommonConstants {
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String CAPTCHA_ERROR_KEY = "sbicloud.captcha.error"
    public static final String CAPTCHA_ERROR_MESSAGE = "Incorrect captcha"

    public static final String LOGO_IMAGE_PATH = "reports/companyLogo/brac_logo.jpg"
    public static final String REPORT_DOWNLOAD_DEFAULT_FORMAT_PDF = "PDF"
    public static final String MESSAGE = "message"

    public static final COUNTRY_ID_UGANDA = 1
    public static final long COUNTRY_ID_BANGLADESH = 2
    public static final long COUNTRY_ID_NETHERLANDS = 3
    public static final long COUNTRY_ID_SRI_LANKA = 4
    public static final long COUNTRY_ID_AFGHANISTAN = 5
    public static final long COUNTRY_ID_TANZANIA = 6
    public static final COUNTRY_ID_MYANMAR = 7
    public static final long COUNTRY_ID_PAKISTAN = 8
    public static final long COUNTRY_ID_SOUTH_SUDAN = 12

    // Office Constants
    public static final Long OFFICE_TYPE_ID_CHO = 1
    public static final Long OFFICE_TYPE_ID_DO = 2
    public static final Long OFFICE_TYPE_ID_RO = 3
    public static final Long OFFICE_TYPE_ID_AO = 4
    public static final Long OFFICE_TYPE_ID_BO = 5
    public static final Long OFFICE_TYPE_ID_VOP = 6
    public static final Long OFFICE_TYPE_ID_SBI = 7
    public static final Long OFFICE_TYPE_ID_BI = 8

    public static final long BRAC_BANGLADESH_PROGRAM = 12
    public static final String BRAC_BANGLADESH_PROJECT = "BI0000000000000000000105"
    public static final String BRAC_BANGLADESH_OFFICE = "000000000000000000000001"

    public static final String ID_FORMAT = "0000000000000000"
    public static final String ID_FORMAT_EXTERNAL_USER = "00000000"
    private static final String WELCOME = "Welcome"
    public static final String EMPTY_STRING = "";
    public static final String SPACE = " ";
    public static final String PIPE = "|";
    public static final String COMMA = ",";
    public static final String EQUAL = "=";
    public static final String ID_FORMAT_COUNTRY = "00"
    //date format
    public static final String DATE_FORMAT = "dd-MM-yyyy"
    public static final String DATE_FORMAT_Y_M_D = "yyyy-MM-dd"
    public static final String DATE_FORMAT_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss"
    public static final String DATE_FORMAT_Y_M_D_0_0_0 = "yyyy-MM-dd 00:00:00"
    public static final String DATE_FORMAT_H_M_S = "dd-MM-yyyy HH:mm:ss"
    public static final String DATE_FORMAT_YMD = "yyyyMMdd"

    public static final String ID_FORMAT_MONTH = "00"
    //Date mask
    public static final String DATE_MASK_FORMAT = "99-99-9999"
    public static final String YEAR_MASK_FORMAT = "9999"
    public static final String DATE_WATERMARK_FORMAT = "DD-MM-YYYY"
    public static final String YEAR_WATERMARK_FORMAT = "YYYY"
    public static final int MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;

    public static final int DAYS_IN_YEAR = 365;
    public static final byte NUMBER_OF_DAYS_IN_A_WEEK = 7
    public static final byte NUMBER_OF_DAYS_IN_A_MONTH = 30
    public static final byte NUMBER_OF_MONTHS_IN_A_YEAR = 12

    // SQL result size
    public static final int SQL_PAGE_SIZE = 10;
    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final String COLON = ":";
    public static final String SINGLE_QUOTE = "'";
    public static final String CODE_SEPARATOR = "-"
    public static final String CODE_AUTO = "AUTO"

    public static final String DATA_COLLECTION_CONFIG_MF = "MF"
    public static final String DATA_COLLECTION_CONFIG_SEP = "SEP"
    public static final String DATA_COLLECTION_CONFIG_ELA = "ELA"
    public static final String DATA_COLLECTION_CONFIG_UNHCR = "Microfinance For Returnee Refuges"
    public static final String DATA_COLLECTION_CONFIG_ADP = "ADP"
    public static final String DATA_COLLECTION_CONFIG_AGRI_FINANCE_PROJECT = "Agri Finance Project"


    public static final byte MODULE_INFO_MICROFINANCE = 1
    public static final byte MODULE_INFO_FIXED_ASSET = 2
    public static final byte MODULE_INFO_HR = 3
    public static final byte MODULE_INFO_ACCOUNTS = 4
    public static final byte MODULE_INFO_PROCUREMENT = 5
    public static final byte MODULE_INFO_STORE = 6
    public static final byte MODULE_INFO_ADMIN = 7
    public static final byte MODULE_INFO_DONOR =8
    public static final byte MODULE_INFO_BUDGET =9
    public static final byte MODULE_INFO_PT =10   //  Proposal Tracking
    public static final String CURRENCY_FORMAT = "#0.0"

    public static final String FIRST_BRACKET_OPEN = "("
    public static final String FIRST_BRACKET_CLOSE = ")"

    public static final String SQUARE_BRACKET_OPEN = "["
    public static final String SQUARE_BRACKET_CLOSE = "]"

    public static final long DOMAIN_STATUS_ACTIVE = 1
    public static final long DOMAIN_STATUS_INACTIVE = 2

    public static final String CODE_DONOR_INDICATOR = "D"
    public static final String ID_FORMAT_DONOR = "0000"

    //Holiday Type
    public static final int HOLIDAY_TYPE_GLOBAL = 1
    public static final int HOLIDAY_TYPE_PUBLIC = 2
    public static final int HOLIDAY_TYPE_LOCAL = 3

    public static final String HOLIDAY_TYPE_NAME_GLOBAL = "Global"
    public static final String HOLIDAY_TYPE_NAME_PUBLIC = "Public"
    public static final String HOLIDAY_TYPE_NAME_LOCAL = "Local"

    //Mode of Payment
    public static final byte MODE_OF_PAYMENT_CASH = 1
    public static final byte MODE_OF_PAYMENT_CHEQUE = 2
    public static final byte MODE_OF_PAYMENT_VOUCHER = 3


    public static final String DEFAULT_HIBERNAT_EXCEPTION_CONSTRAINTVIOLATION = 'default.hibernate.exception.constraintviolation'
    public static final String DEFAULT_HIBERNAT_EXCEPTION_DATA = 'default.hibernate.exception.data'
    public static final String DEFAULT_HIBERNAT_EXCEPTION_JDBC = 'default.hibernate.exception.jdbc'
    public static final String DEFAULT_HIBERNAT_EXCEPTION_GENERIC = 'default.hibernate.exception.generic'

    public static final String LOG_GENERIC_EXCEPTION = "Generic Exception :"

    public static final String DEFAULT_LEFT_MENU = "accounts"
    public static final String LEFT_MENU_ADMIN = "admin"
    public static final byte DEFAULT_MODULE_INFO_ID = MODULE_INFO_ACCOUNTS
    public static final String DEFAULT_CONTROLLER = "myDashboard"
    public static final String DEFAULT_ACTION = "myDashboard"

    public static final String SPECIAL_CONTROLLER = "managementDashboard"
    public static final String SPECIAL_ACTION = "index"

    public static final String DEFAULT_CURRENT_THEME = "blue"

    public static final String DEFAULT_ERROR_CONTROLLER = "login"
    public static final String DEFAULT_ERROR_ACTION = "authError"

    public static final String DEFAULT_ADMIN_CONTROLLER = "administration"
    public static final String DEFAULT_ADMIN_ACTION = "index"

    public static final String ERROR_CODE_TEXT = "errorCode"
    public static final String DEFAULT_META_LAYOUT = "docuThemeRollerLayout"

    public static final int SEARCH_RESULT_PAGE_SIZE = 50

    public static final long EVENT_TYPE_MF_LOAN = 1
    public static final long EVENT_TYPE_MF_SAVINGS = 2
    public static final long EVENT_TYPE_MF_OTHERS = 3


    public static final long RECONCILIATION_INFO_MEMBER_TRANSFER = 1
    public static final long RECONCILIATION_INFO_STORE_ITEM_ISSUE=32
    public static final long RECONCILIATION_INFO_STORE_ITEM_RECEIVE=33
    public static final long RECONCILIATION_INFO_STORE_ITEM_CONSUME=34
    public static final long RECONCILIATION_INFO_ASSET = 13
    public static final long RECONCILIATION_INFO_TAKE_OVER_PORTFOLIO = 40

    public static final long LOGIN_ERROR_BUSINESS_DAY = 1
    public static final long LOGIN_ERROR_NO_DEPLOYED_OFFICE = 2

    // SubsidiaryAccounts Entity
    public static final int ENTITY_TYPE_GROUP = 1
    public static final int ENTITY_TYPE_MEMBER = 2
    public static final int ENTITY_TYPE_OFFICE = 3
    public static final int ENTITY_TYPE_BANK_ACCOUNT = 4
    public static final int ENTITY_TYPE_PROJECT = 5
    public static final long ENTITY_TYPE_DESIGNATION = 6
    public static final int ENTITY_TYPE_STORE = 8
    public static final int ENTITY_TYPE_ITEM_CATEGORY = 9
    public static final int ENTITY_TYPE_ASSET_GROUP = 10
    public static final int ENTITY_TYPE_LAND_DEVELOPMENT = 11
    public static final int ENTITY_TYPE_BUILDING = 12
    public static final int ENTITY_TYPE_EQUIPMENTS = 13
    public static final int ENTITY_TYPE_VEHICLES = 14
    public static final int ENTITY_TYPE_FURNITURE_FIXTURE = 15
    public static final int ENTITY_TYPE_ELECTRICAL_AND_ELECTRONICS_DEVICE = 16
    public static final int ENTITY_TYPE_COMPUTER_AND_PERIPHERALS = 17
    public static final int ENTITY_TYPE_INVENTORY = 18
    public static final int ENTITY_TYPE_THIRD_PARTY = 19

    public static final String ROLE_S_ADMIN = "SA"
    public static final String ROLE_HR = "HR"
    public static final String MINUS_ONE = "-1"

    public static final long ENTITY_PROGRAM = 1
    public static final long ENTITY_DEPARTMENT = 3
    public static final long ENTITY_DIVISION = 2

    public static final String CODE_PROPOSAL_INDICATOR = "P"
    public static final String ID_FORMAT_PROPOSAL = "0000"

    // Proposal Status
    public static final long PROPOSAL_STATUS_NEW = 1
    public static final long PROPOSAL_STATUS_PENDING = 2
    public static final long PROPOSAL_STATUS_APPROVED = 3
    public static final long PROPOSAL_STATUS_SUBMITTED = 4
    public static final long PROPOSAL_STATUS_ACCEPTED = 5
    public static final long PROPOSAL_STATUS_REJECTED = 6
    public static final long FORGET_PASSWORD_EMAIL_ID = 1

    public static final String CUSTOM_FIELD_NAME_N_A = 'N/A'

// MobileMoney Status
    public static String MOBILE_MONEY_PROVIDER_NAME = "Mpesa"
    public static byte MOBILE_MONEY_STATUS_PENDING = 1
    public static byte MOBILE_MONEY_STATUS_IGNORE = 2
    public static byte MOBILE_MONEY_STATUS_REJECT = 3
    public static byte MOBILE_MONEY_STATUS_SUCCESS = 4
    public static byte MOBILE_MONEY_STATUS_REVERSE = 5
    public static final long MOBILE_MONEY_COUNTRY_TANZANIA = 6

   // Multi Instance Office Code
    public static String LONG_ID = 'NONE'
    public static long ROUTER_COUNT = 0

    public static final String REPORT_NO_LOGO_IMAGE_PATH = "images/report_no_logo.jpg"

    public static final String GRAILS_DOMAIN = "Domain"
    public static final String GRAILS_CONTROLLER = "Controller"
    public static final String APPLICATION_PACKAGE = "com.docu."

    public static final String PASSWORD_PATTERN_KEY = "com.docu.security.passwordPattern"
    public static String PASSWORD_PATTERN = ""
    public static String PASSWORD_VALIDATION_MESSAGE = ""
    public static String ALREADY_UPDATED = "Already Updated"

    public static long FACTORY_MARKET_RETURN_SUB_WAREHOUSE_ID = 2
}
