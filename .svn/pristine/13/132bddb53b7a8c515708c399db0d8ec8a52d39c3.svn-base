/*
 * ****************************************************************
 * Copyright � 2010 Documentatm (TM) Limited. All rights reserved.
 * DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * This software is the confidential and proprietary information of
 * Documentatm (TM) Limited ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Documentatm (TM) Limited.
 * *****************************************************************
 */

package com.docu.security

import org.apache.commons.lang.builder.HashCodeBuilder

class ApplicationUserAuthority implements Serializable {
    ApplicationUser applicationUser
    UserAuthority userAuthority

    boolean equals(other) {
        if (!(other instanceof ApplicationUserAuthority)) {
            return false
        }

        other.applicationUser?.id == applicationUser?.id &&
                other.userAuthority?.id == userAuthority?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (applicationUser) builder.append(applicationUser.id)
        if (userAuthority) builder.append(userAuthority.id)
        builder.toHashCode()
    }

    static ApplicationUserAuthority get(String userId, String authorityId) {
        find 'from ApplicationUserAuthority where applicationUser.id=:userId and userAuthority.id=:authorityId',
                [userId: userId, authorityId: authorityId]
    }

    static ApplicationUserAuthority create(ApplicationUser applicationUser, UserAuthority userAuthority, boolean flush = false) {
        new ApplicationUserAuthority(applicationUser: applicationUser, userAuthority: userAuthority).save(flush: flush, insert: true)
    }

    static boolean remove(ApplicationUser applicationUser, UserAuthority userAuthority, boolean flush = false) {
        ApplicationUserAuthority instance = ApplicationUserAuthority.findByApplicationUserAndUserAuthority(applicationUser, userAuthority)
        instance ? instance.delete(flush: flush) : false
    }

    static void removeAll(ApplicationUser applicationUser) {
        executeUpdate 'DELETE FROM ApplicationUserAuthority WHERE applicationUser=:user', [user: applicationUser]
    }

    static void removeAll(UserAuthority userAuthority) {
        executeUpdate 'DELETE FROM ApplicationUserAuthority WHERE userAuthority=:userAuthority', [userAuthority: userAuthority]
    }

    static mapping = {
        version false
    }
}
