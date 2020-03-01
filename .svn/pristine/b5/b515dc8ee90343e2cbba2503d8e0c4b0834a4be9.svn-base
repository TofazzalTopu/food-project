package com.bits.bdfp.setup.salestarget.salesheadbyvolume

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.setup.salestarget.SalesHeadByVolume
import com.bits.bdfp.setup.salestarget.SalesHeadByVolumeService

@Component("readSalesHeadByVolumeAction")
class ReadSalesHeadByVolumeAction extends Action {
    public static final Log log = LogFactory.getLog(ReadSalesHeadByVolumeAction.class)

    @Autowired
    SalesHeadByVolumeService salesHeadByVolumeService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        SalesHeadByVolume salesHeadByVolume = salesHeadByVolumeService.read(Long.parseLong(params.id))
//        Map objectData = ObjectUtil.getPersistenceData(salesHeadByVolume)
        return salesHeadByVolume
    }

    public Object postCondition(def params, def object) {
        return null
    }
}