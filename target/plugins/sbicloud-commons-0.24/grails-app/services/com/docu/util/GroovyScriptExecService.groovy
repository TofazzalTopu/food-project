package com.docu.util

import org.springframework.transaction.annotation.Transactional

class GroovyScriptExecService {

    static transactional = false

    @Transactional
    def execGroovyScript(Map object, String groovyScript, String returnObject) {
        try {
            def script = groovyScript
            if(script.substring(0,1) == "'") {
                groovyScript = groovyScript[1..-1]
                groovyScript = groovyScript[0..-2]
            }
            Binding bind = new Binding()
            object.each {
                bind.setVariable(it.key.toString(), it.value)
            }

            GroovyShell shell = new GroovyShell(bind)
            shell.evaluate(groovyScript)

            def result = bind.getVariable(returnObject)
            return result
        }
        catch (Exception ex) {
            println(ex)
        }
    }
}
