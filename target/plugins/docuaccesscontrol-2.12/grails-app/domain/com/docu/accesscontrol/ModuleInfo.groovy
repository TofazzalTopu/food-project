package com.docu.accesscontrol

public class ModuleInfo {
    String moduleName
    String moduleShortName
    String description
    String moduleCode
    String fileName
    String url

    static constraints = {
        moduleName(nullable: false, blank: false)
        moduleShortName(nullable: true,blank: true)
        description(nullable: true, blank: true)
        moduleCode(nullable: true, blank: true)
        fileName(nullable: true, blank: true)
        url(nullable: true,blank: true)
    }

    @Override
    String toString() {
        return moduleName
    }
}
