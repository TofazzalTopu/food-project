package com.docu.commons.util

import com.docu.commons.MaritalStatus

/**
 * Created by IntelliJ IDEA.
 * User:
 * Date:
 * Time:
 * To change this template use File | Settings | File Templates.
 */
@Singleton
class MaritalStatusUtil {
    private static List<MaritalStatus> list = []

    public void resolve() {
        list = MaritalStatus.list(sort: "id")
    }
    public void destroy() {
        list = null
    }

    public List<MaritalStatus> list() {
        //return list = MaritalStatus.list()
        return list
    }
    public MaritalStatus get(long id) {
        return list.find {it.id == id}
    }

    public boolean isExists(String name, long id) {
        MaritalStatus maritalStatus = list().find {
            it.name.toLowerCase() == name.toLowerCase()
        }
        if ((maritalStatus == null) || (maritalStatus.id == id)) {
            return false
        }
        else {
            return true
        }
    }
}
