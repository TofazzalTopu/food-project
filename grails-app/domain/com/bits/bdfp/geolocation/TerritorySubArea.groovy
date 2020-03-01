package com.bits.bdfp.geolocation

import com.bits.bdfp.common.CountryInfo
import com.bits.bdfp.common.District
import com.bits.bdfp.common.Division
import com.bits.bdfp.common.ThanaUpazilaPouroshova
import com.bits.bdfp.common.UnionInfo
import com.docu.security.ApplicationUser

class TerritorySubArea {

    TerritoryConfiguration  territoryConfiguration

    CountryInfo             countryInfo
    Division                division
    District                district
    ThanaUpazilaPouroshova  thanaUpazilaPouroshova
    UnionInfo               unionInfo

    String                  geoLocation
    String                  paraOrLocality
    String                  road
    boolean                 isActive

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static mapping = {
        isActive defaultValue: '1'
    }

    static constraints = {
        territoryConfiguration(nullable: false)
        geoLocation(blank: false, nullable: false, unique: ['territoryConfiguration', 'countryInfo', 'division', 'district', 'thanaUpazilaPouroshova', 'unionInfo', 'paraOrLocality', 'road'], maxSize: 100)
        countryInfo(nullable: false)
        division(nullable: false)
        district(nullable: false)
        thanaUpazilaPouroshova(nullable: true)
        unionInfo(nullable: true)
        paraOrLocality(blank: false, nullable: false)
        road(blank: false, nullable: false)
        isActive(nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }


    @Override
    public String toString() {
        return geoLocation + '[' + territoryConfiguration +']';
    }
}
