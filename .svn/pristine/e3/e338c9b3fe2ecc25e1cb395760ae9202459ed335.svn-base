package com.bits.bdfp.common.documenttype

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.common.DocumentType
import com.bits.bdfp.common.DocumentTypeService

@Component("readDocumentTypeAction")
class ReadDocumentTypeAction implements IAction {
    public static final Log log = LogFactory.getLog(ReadDocumentTypeAction.class)

    @Autowired
    DocumentTypeService documentTypeService

    public Object preCondition(def params) {
        return null
    }

    public Object execute(def params, def object) {
        DocumentType documentType = documentTypeService.read(params)
        Map objectData = ObjectUtil.getPersistenceData(documentType)
        return objectData
    }

    public Object postCondition(def object) {
        return null
    }
}