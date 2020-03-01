/*
 * *************************

************************************
 * Copyright � 2010 Documentatm (TM) Limited. All rights reserved.
 * DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * This software is the confidential and proprietary information of
 * Documentatm (TM) Limited ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Documentatm (TM) Limited.
 * *****************************************************************
 */

package com.docu.commons

class Relationship {
    String      name
    String      description

    static constraints = {
        name(blank: false, unique: true, size: 1..50, matches: "[a-zA-Z-. _-]+")
        description(blank: true, nullable: true, maxSize: 150)
    }

    static mapping = {
        sort "name"
        cache: 'read-only'
    }

    def String toString() {
        return name;
    }
}
