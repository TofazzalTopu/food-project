package com.docu.commons

/**
 * Created by IntelliJ IDEA.
 * User: kibria
 * Date: 9/5/11
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
class Office {
    String officeId
    String physicalOfficeId
    String officeCode
    String officeRefCode
    String officeName
    long officeTypeId
    String officeTypeName
    String officeTypeCode
    long countryId
    String countryName
    String officeHierarchyId
    String officeHierarchyName
    String officeReportingTo
    Date setupDate
    Date effectiveDate
    List<Office> subOfficeList
    String programName
    Date businessDate
    Date migrationDate

    String toString() {
        return officeName + CommonConstants.SPACE +
                CommonConstants.SQUARE_BRACKET_OPEN + officeCode + CommonConstants.SQUARE_BRACKET_CLOSE
    }

    boolean isCountryHeadOffice() {
        return (officeTypeId == CommonConstants.OFFICE_TYPE_ID_CHO)
    }

    boolean isGlobalHeadOffice() {
        return (officeTypeId == CommonConstants.OFFICE_TYPE_ID_BI || officeTypeId == CommonConstants.OFFICE_TYPE_ID_SBI)
    }

    boolean isHeadOffice() {
        return (officeTypeId == CommonConstants.OFFICE_TYPE_ID_BI || officeTypeId == CommonConstants.OFFICE_TYPE_ID_SBI || officeTypeId == CommonConstants.OFFICE_TYPE_ID_CHO)
    }

    boolean isBranchOffice() {
        return (officeTypeId == CommonConstants.OFFICE_TYPE_ID_BO)
    }

    boolean isAreaOffice() {
        return (officeTypeId == CommonConstants.OFFICE_TYPE_ID_AO)
    }

    boolean isVirtualOutpost() {
        return (officeTypeId == CommonConstants.OFFICE_TYPE_ID_VOP)
    }

    public boolean makeVisibleChildOffice() {
        if (this.officeTypeId == CommonConstants.OFFICE_TYPE_ID_SBI || this.officeTypeId == CommonConstants.OFFICE_TYPE_ID_BI) {
            return true
        }
        else {
            if (this.countryId == 4) {
                return this.officeTypeId < 6
            }
            if (this.countryId == 5) {
                return this.officeTypeId < 4
            }
            else {
                return this.officeTypeId < 5
            }
        }
    }

    boolean hasMFOperation() {
        return (officeTypeId == CommonConstants.OFFICE_TYPE_ID_BO || officeTypeId == CommonConstants.OFFICE_TYPE_ID_VOP)
    }
}
