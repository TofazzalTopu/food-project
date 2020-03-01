package com.bits.bdfp.common.unioninfo

import com.bits.bdfp.common.UnionInfo
import com.bits.bdfp.common.UnionInfoService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateUnionInfoAction")
class UpdateUnionInfoAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    UnionInfoService unionInfoService

    public Object preCondition(Object params, Object object) {
        ApplicationUser applicationUser = (ApplicationUser) object
        UnionInfo unionInfo = UnionInfo.read(Long.parseLong(params?.id?.toString()))
        unionInfo.properties = params
        unionInfo.userUpdated = applicationUser
        if (!unionInfo.validate()) {
            return null
        }
        return unionInfo
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return unionInfoService.update(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }
}
