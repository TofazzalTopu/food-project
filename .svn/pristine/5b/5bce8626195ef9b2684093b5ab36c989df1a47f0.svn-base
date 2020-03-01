package com.bits.bdfp.setup.salestarget.monthlysalestargetbyvolume

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByVolume
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByVolumeService

@Component("readMonthlySalesTargetByVolumeAction")
class ReadMonthlySalesTargetByVolumeAction extends Action {
    public static final Log log = LogFactory.getLog(ReadMonthlySalesTargetByVolumeAction.class)

    @Autowired
    MonthlySalesTargetByVolumeService monthlySalesTargetByVolumeService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        MonthlySalesTargetByVolume monthlySalesTargetByVolume = monthlySalesTargetByVolumeService.read(Long.parseLong(params.id))
        Map objectData = ObjectUtil.getPersistenceData(monthlySalesTargetByVolume)
        return objectData
    }

    public Object postCondition(def params, def object) {
        return null
    }
}