package com.bits.bdfp.bonus

import com.bits.bdfp.bonus.bonuscriteriasetup.CreateBonusCriteriaSetupAction
import com.bits.bdfp.bonus.bonuscriteriasetup.DeleteBonusCriteriaSetupAction
import com.bits.bdfp.bonus.bonuscriteriasetup.ListBonusCriteriaSetupAction
import com.bits.bdfp.bonus.bonuscriteriasetup.UpdateBonusCriteriaSetupAction
import com.bits.bdfp.bonus.bonuscriteriasetup.ReadBonusCriteriaSetupAction
import com.bits.bdfp.bonus.bonuscriteriasetup.SearchBonusCriteriaSetupAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class BonusCriteriaSetupController {

    @Autowired
    private CreateBonusCriteriaSetupAction createBonusCriteriaSetupAction
    @Autowired
    private UpdateBonusCriteriaSetupAction updateBonusCriteriaSetupAction
    @Autowired
    private ListBonusCriteriaSetupAction listBonusCriteriaSetupAction
    @Autowired
    private DeleteBonusCriteriaSetupAction deleteBonusCriteriaSetupAction
    @Autowired
    private ReadBonusCriteriaSetupAction readBonusCriteriaSetupAction
    @Autowired
    private SearchBonusCriteriaSetupAction searchBonusCriteriaSetupAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
       List list = listBonusCriteriaSetupAction.execute(params, null)
       render listBonusCriteriaSetupAction.postCondition(null, list) as JSON
    }

    def show = {
      BonusCriteriaSetup bonusCriteriaSetup = new BonusCriteriaSetup()
      render(template: "show", model:[bonusCriteriaSetup:bonusCriteriaSetup])
    }

    def create = {
      BonusCriteriaSetup bonusCriteriaSetup = new BonusCriteriaSetup(params)
        ApplicationUser applicationUser=session?.applicationUser
      BonusCriteriaSetup bonusCriteriaSetupInstance = createBonusCriteriaSetupAction.preCondition(applicationUser, bonusCriteriaSetup)
      Message message = null
      if (bonusCriteriaSetupInstance == null) {
        message = createBonusCriteriaSetupAction.getValidationErrorMessage(bonusCriteriaSetup)
      } else {
        bonusCriteriaSetupInstance = createBonusCriteriaSetupAction.execute(null, bonusCriteriaSetupInstance)
        if (bonusCriteriaSetupInstance) {
          message = createBonusCriteriaSetupAction.getMessage("Bonus Criteria Setup",Message.SUCCESS, createBonusCriteriaSetupAction.SUCCESS_SAVE)
        } else {
          message = createBonusCriteriaSetupAction.getMessage("Bonus Criteria Setup",Message.ERROR, createBonusCriteriaSetupAction.FAIL_SAVE)
        }
      }
      render message as JSON
    }

    def edit = {
      render readBonusCriteriaSetupAction.execute(params, null) as JSON
    }

    def update = {
      BonusCriteriaSetup bonusCriteriaSetup = new BonusCriteriaSetup(params)
      Object object = updateBonusCriteriaSetupAction.preCondition(params,null)
      Message message = null
      if (object == false) {
          message = updateBonusCriteriaSetupAction.getValidationErrorMessage(bonusCriteriaSetup)
      } else {
          int noOfRows = (int) updateBonusCriteriaSetupAction.execute(null,object)
          if (noOfRows > 0) {
            message = updateBonusCriteriaSetupAction.getMessage("Bonus Criteria Setup",Message.SUCCESS, updateBonusCriteriaSetupAction.SUCCESS_UPDATE)
          } else {
            message = updateBonusCriteriaSetupAction.getMessage("Bonus Criteria Setup",Message.ERROR, updateBonusCriteriaSetupAction.FAIL_UPDATE)
          }
      }
      render message as JSON
    }

    def delete = {
        BonusCriteriaSetup bonusCriteriaSetup = deleteBonusCriteriaSetupAction.preCondition(params, null);
        Message message = null
        if (bonusCriteriaSetup) {
          int rowCount = (int) deleteBonusCriteriaSetupAction.execute(null, bonusCriteriaSetup);
          if (rowCount > 0) {
            message = deleteBonusCriteriaSetupAction.getMessage("Bonus Criteria Setup",Message.SUCCESS, deleteBonusCriteriaSetupAction.SUCCESS_DELETE);
          } else {
            message = deleteBonusCriteriaSetupAction.getMessage("Bonus Criteria Setup",Message.ERROR, deleteBonusCriteriaSetupAction.FAIL_DELETE);
          }
        }
        else {
          message = deleteBonusCriteriaSetupAction.getMessage("Bonus Criteria Setup", Message.ERROR,deleteBonusCriteriaSetupAction.ALREADY_DELETED);
        }
        render message as JSON;
    }


}
