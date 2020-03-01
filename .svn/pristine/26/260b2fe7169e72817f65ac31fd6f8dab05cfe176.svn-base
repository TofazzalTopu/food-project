package com.bits.bdfp.inventory.sales.loadingslip

import com.bits.bdfp.inventory.sales.LoadingSlip
import com.bits.bdfp.inventory.sales.LoadingSlipService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateLoadingSlipAction")
class UpdateLoadingSlipAction extends Action {
 private static final Log log = LogFactory.getLog(this)
  @Autowired
  LoadingSlipService loadingSlipService

   public Object preCondition(Object params, Object object) {
    LoadingSlip loadingSlip = LoadingSlip.read(Long.parseLong(params?.id?.toString()))
    loadingSlip.properties=params
    if (!loadingSlip.validate()) {
      return null
    }
    return loadingSlip
  }

   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {
    try {
      return loadingSlipService.update(object)
    } catch (Exception ex) {
      log.error(ex.message)
      return null
    }
  }
}
