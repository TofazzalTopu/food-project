package com.bits.bdfp.common.documenttype

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.common.DocumentType
import com.bits.bdfp.common.DocumentTypeService


@Component("deleteDocumentTypeAction")
class DeleteDocumentTypeAction implements IAction {

    public static final Log log = LogFactory.getLog(DeleteDocumentTypeAction.class)
    private final String MESSAGE_HEADER = 'Document Type.'
    private final String MESSAGE_SUCCESS = 'Document Type deleted successfully'
    private final String MESSAGE_FAILURE = 'Document Type delete failed'

    @Autowired
    DocumentTypeService documentTypeService

    public Object preCondition(def params) {
        Map mapInstance = [:]
        DocumentType documentType = null
        try {
            documentType = documentTypeService.read(params)
            if (!documentType) {
                return UserMessageBuilder.createMessage(documentTypeService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, documentTypeService.getUserMessage(MESSAGE_FAILURE, null))
            }

            mapInstance = (Map) UserMessageBuilder.createMessage(documentTypeService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
            mapInstance.put(DocumentType.class.simpleName, documentType)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(documentTypeService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, documentType, ex)
        }

        return mapInstance
    }

    public Object execute(def params, def object) {
        DocumentType documentType = null
        try {
            documentType = object.get(DocumentType.class.simpleName)
            documentTypeService.delete(documentType)

        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(documentTypeService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, documentType, ex)
        }

        return UserMessageBuilder.createMessage(documentTypeService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, documentTypeService.getUserMessage(MESSAGE_SUCCESS, null))
    }

    public Object postCondition(def object) {
        return null
    }
}