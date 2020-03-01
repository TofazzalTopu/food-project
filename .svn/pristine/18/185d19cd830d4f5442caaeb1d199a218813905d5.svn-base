/*
 * ****************************************************************
 * Copyright © 2010 Documentatm (TM) Limited. All rights reserved.
 * DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * This software is the confidential and proprietary information of
 * Documentatm (TM) Limited ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Documentatm (TM) Limited.
 * *****************************************************************
 */

$(document).ready(function() {
    jQuery.validator.addMethod("alphaNumeric", function (value, element) {
                 return this.optional(element) || /^[0-9a-zA-Z]+$/.test(value);
                }, "Enter a valid Alphanumeric Number.");
    $("#member-form").validate({
        rules: {
            // Member Information  rules
      
          'memberInfoInstance.applicationDate':{
              required:false
          },
          'memberInfoInstance.membershipDate':{
              required:false
          },
           'memberInfoInstance.referenceNo':{
               required:true,
               digits:true
           },
            'memberInfoInstance.surveyReportNo':{
               required:true,
               digits:true

           },
            'memberInfoInstance.MemberStatusId':{
               required:false
           },
            //Tabs Personal rules
           'personalInfoInstance.salutation.id':{
               required:false
           },
            'personalInfoInstance.name':{
               required:false
           },
            'personalInfoInstance.nationalId':{
                required:false,
                digits:true
           },
            'personalInfoInstance.passportNo':{
               required:false,
                alphaNumeric: true

           },


            'personalInfoInstance.drivingLicenseNo':{
                required:false,
                digits:true
           },
           'personalInfoInstance.photoIdNo':{
                required:false,
                digits:true
           },
           'personalInfoInstance.gender.id':{
                required:false
           },
           'personalInfoInstance.maritalStatus.id':{
                required:false
           },
           'personalInfoInstance.age':{
                required:false,
                range:[1,99]
           },
           'personalInfoInstance.occupation.id':{
                required:false
           },
           'personalInfoInstance.dateOfBirth':{
                required:false
           },
           'personalInfoInstance.fatherName':{
                required:false
            },
           'personalInfoInstance.motherName':{
                required:false
           },
            //Tabs Family rules
           'familyInfoInstance.houseHoldHeadName':{
                required:false
           },
           'familyInfoInstance.sonNo':{
                 required:false,
                 digits:true
           },
           'familyInfoInstance.daughterNo':{
                 required:false,
                 digits:true
           },
           'familyInfoInstance.maleNo':{
                 required:false,
                 digits:true
           },
           'familyInfoInstance.femaleNo':{
                 required:false,
                 digits:true
           },
           'familyInfoInstance.earningMemberNo':{
                 required:false,
                 digits:true
           },
           'familyInfoInstance.otherOrgMemberNo':{
                 required:false,
                 digits:true
           },
            organizationName:{
                 required:false
            },
           loanAmount:{
                 required:false,
                 number:true
           },
            //Tabs Contact rules
           'contactInfoInstance.contactNo':{
                 required:false,
                 digits:true
           },
           addressId:{
                 required:false
           },
           address:{
                 required:false
           },
           cityId:{
                required:false
           },
           zipCode:{
                required:false,
                digits:true
           },
          //Tabs Asset rules
           assetId:{
                required:false
           },
           assetNumber:{
               required:false,
               digits:true
           },
           assetValue:{
               required:false,
               number:true
           },
          //Tabs Nominee rules
           nomineeName:{
               required:false
          },
           relationshipId:{
               required:false
           },
           nomineeShare:{
               required:false,
               number:true
           },
            //Tabs Passbook rules
           passbookNo:{
               required:false,
               digits:true
           },
           issueDate:{
               required:false
           },
           passbookStatus:{
               required:false
           }
        },
        
        messages: {

            'memberInfoInstance.referenceNo':"Enter a valid number.",
            'memberInfoInstance.surveyReportNo':"Enter a valid number.",
            'personalInfoInstance.nationalId':"Enter a valid number.",
            'personalInfoInstance.drivingLicenseNo':"Enter a valid number.",
            'personalInfoInstance.photoIdNo':"Enter a valid number.",
            'personalInfoInstance.age':"Enter a age 1 to 99.",
            'familyInfoInstance.sonNo':"Enter  son's number.",
            'familyInfoInstance.daughterNo':"Enter  daughter's number.",
            'familyInfoInstance.maleNo':"Enter male's number.",
            'familyInfoInstance.femaleNo':"Enter female's number.",
            'familyInfoInstance.earningMemberNo':"Enter earning membe.",
            'familyInfoInstance.otherOrgMemberNo':"Enter a valid number.",
            loanAmount:"Enter a valid Amount.",
            'contactInfoInstance.contactNo':"Enter a valid number.",
            zipCode:"Enter a valid Zip code number.",
            assetNumber:"Enter a valid asset number.",
            assetValue:"Enter a valid asset value.",
            nomineeShare:"Enter a valid share no.",
            passbookNo: "Entera valid passbook no."
        }
    });
});


