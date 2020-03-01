package com.docu.security

import com.docu.commons.CommonConstants
import com.docu.commons.InternationalizationService
import org.springframework.transaction.annotation.Transactional

class UserLockStatusService extends InternationalizationService {

    static transactional = true
    ApplicationUserService applicationUserService


    @Transactional
    public boolean lockUser(UserLockStatus userLockStatus){
            try {
                applicationUserService.backupApplicationUser()
                applicationUserService.lockApplicationUser()
                userLockStatus.isLocked=true
                userLockStatus.save()
            }
            catch(Exception ex){
                log.error(ex.message)
                throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
            }
        return true
    }

    @Transactional
    public boolean unLockUser(UserLockStatus userLockStatus){
            try {
                applicationUserService.restoreApplicationUser()
                userLockStatus.isLocked=false
                userLockStatus.save()
            }
            catch(Exception ex){
                log.error(ex.message)
                throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
            }
        return true
    }



    @Transactional(readOnly = true)
    public UserLockStatus getUserLockStatus(){
        List<UserLockStatus> userLockStatusList = UserLockStatus.list()
        if(userLockStatusList.size()>0){
            return userLockStatusList[0]
        }
        return null
    }

    @Transactional(readOnly = true)
    public boolean isUserLocked(){
        List<UserLockStatus> userLockStatusList = UserLockStatus.list()
        if(userLockStatusList.size()==0){
            return false
        }
        return  userLockStatusList[0].isLocked
    }

    @Transactional(readOnly = true)
    public boolean isServerBusy(){
        List<UserLockStatus> userLockStatusList = UserLockStatus.list()
        if(userLockStatusList.size()==0){
            return false
        }
        return  userLockStatusList[0].isServerBusy
    }

    @Transactional
    public void changeServerBusyStatus(boolean busyStatus){
        List<UserLockStatus> userLockStatusList = UserLockStatus.list()
        userLockStatusList[0].isServerBusy = busyStatus
        userLockStatusList[0].save()
    }
}
