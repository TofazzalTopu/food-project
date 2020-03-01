import com.bits.common.BitsDateEditorRegistrarUtil
import com.bits.bdfp.common.RequestLoggingFilter

// Place your Spring DSL code here
beans = {
    bitsDatePropertyEditorRegistrar(BitsDateEditorRegistrarUtil)
    requestLoggingFilter(RequestLoggingFilter){
        sessionManagementService = ref("sessionManagementService")
        grailsUrlMappingsHolder = ref("grailsUrlMappingsHolder")
        controllerAnnotationHelper = ref("controllerAnnotationHelper")
    }
}
