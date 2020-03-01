$(document).ready(function() {
      $("#groupInfoForm").validate({
        rules:
        {
            // basic detail part

           category: {
              required: false    // simple rule, converted to {required:false}

          },
           groupGender: {
              required: false   // simple rule, converted to {required:false}

          },
           name: {
              required:false   // simple rule, converted to {required:false}

          },
          ageGroupFrom: {
              required: false,    // simple rule, converted to {required:false}
               range:[1,99]
          },
           ageGroupTo: {
              required: false,    // simple rule, converted to {required:false}
               range:[1,99]
          },
          groupLeaderContactNo: {
              required: false,   // simple rule, converted to {required:false}
              digits: true
          },
           assignedCO: {
              required: false    // simple rule, converted to {required:false}

          },
           founderCO: {
              required: false    // simple rule, converted to {required:false}

          },
           orientationDate: {
              required: false   // simple rule, converted to {required:false}

          },
            groupCreationDate: {
              required: false    // simple rule, converted to {required:false}

          },
           groupForm: {
           required: false    // simple rule, converted to {required:false}

          },
           groupReferenceNumber: {
              required: false,    // simple rule, converted to {required:false}
              digits: true
          },
          spotAddress: {
              required: false    // simple rule, converted to {required:false}

          },

              groupStatus: {
              required: false    // simple rule, converted to {required:false}

          },
           // meeting schedule part

             day: {
              required: false    // simple rule, converted to {required:false}

          },
              time: {
              required: false    // simple rule, converted to {required:false}

          } ,
          // loan collection plan
         loanCollectionFrequency: {
           required: false    // simple rule, converted to {required:false}

          },
            loanCollectionStartDate: {
            required: false    // simple rule, converted to {required:false}

             },
           // savings collection plan
            savingsCollectionFrequency: {
            required: false    // simple rule, converted to {required:false}

             },
          savingsCollectionStartDate: {
           required: false    // simple rule, converted to {required:false}

          },
           // project & product mapping part

           project: {
           required: false    // simple rule, converted to {required:false}

          }
         

      },
        messages: {
         
         ageGroupFrom:"Age 1 to 99",
         ageGroupTo:"Age 1 to 99",
         groupLeaderContactNo:"Enter a Valid contact No.",
         orientationDate:"Enter a valid date",
         groupCreationDate: "Enter a valid date" ,
         groupReferenceNumber:"Enter a Valid Number.",
         loanCollectionStartDate:"Enter a valid date",
         savingsCollectionStartDate:"Enter a valid date"
        }
      });
    });