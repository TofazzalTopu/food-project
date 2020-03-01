package com.docu.commons

/**
 * Created by IntelliJ IDEA.
 * User: Zia
 * Date: 1/2/11
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IAction {
    Object preCondition(def object);
    Object postCondition(def object);
    Object execute(def params, def object);
}