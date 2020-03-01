package com.docu.sqlgen.common

import com.docu.commons.CommonConstants
import com.docu.commons.DateUtil
import com.docu.commons.sqlgen.AbstractQueryGenerator

/**
 * Created by rafiqul.islam on 12/27/2014.
 */
@Singleton
class HolidayQueryGenerator extends AbstractQueryGenerator {
    @Override
    String toInsertSql(Object object) {
        return null
    }

    String toSelect(Date fromDate = null) {
        String whereClause = ""
        if (fromDate) {
            whereClause = "AND from_date > '${DateUtil.getFlexibleDateFormatAsString(fromDate, CommonConstants.DATE_FORMAT_Y_M_D)}'"
        }
        String strSelect = """
            SELECT
              id,
              version,
              approval_type as "approvalType",
              country_id as "countryId",
              from_date as "fromDate",
              holiday_name as "holidayName",
              holiday_type_id as "holidayTypeId",
              holiday_year as "holidayYear",
              office_code as "officeCode",
              office_hierarchy_id as "officeHierarchyId",
              office_id as "officeId",
              to_date as "toDate"
            FROM holiday WHERE status_id = ${CommonConstants.DOMAIN_STATUS_ACTIVE} ${whereClause}"""
        return strSelect
    }
}
