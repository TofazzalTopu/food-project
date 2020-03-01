package com.docu.commons.util

import com.docu.commons.Gender

/**
 * Created by IntelliJ IDEA.
 * User:    Sarwar
 * Date:    June 02, 2013
 * Time:    13:09 BST
 * To change this template use File | Settings | File Templates.
 */
@Singleton
class GenderUtil {
    private static List<Gender> genderList = []

    public void resolve() {
        genderList = Gender.list()
    }

    public List<Gender> list() {
        return genderList
    }

    public Gender get(long id) {
        return genderList.find {it.id == id}
    }

    public void destroy() {
        genderList = null
    }

}
