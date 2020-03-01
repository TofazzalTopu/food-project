package com.docu.security.util

import com.docu.security.SecurityQuestion

/**
 * Created by rafiqul.islam on 11/28/2014.
 */
@Singleton
class SecurityQuestionUtil {
    List<SecurityQuestion> questionList = null

    public void resolve() {
        questionList = SecurityQuestion.listOrderById()
    }

    public void destroy() {
        questionList = null
    }

    public List<SecurityQuestion> getAll(){
        return questionList
    }

    public SecurityQuestion get(long id) {
        return questionList.find { it.id == id }
    }
}
