package com.docu.commons

/**
 * Created by IntelliJ IDEA.
 * User: Zia
 * Date: 1/2/11
 * Time: 5:47 PM
 * To change this template use File | Settings | File Templates.
 */
class Message {
  static final int SUCCESS = 1
  static final int ERROR = 0
  static final int WARNING = 2
  static final int CONFIRMATION = 3
  static final int INFORMATION = 4
  static final int APPROVALCOMPLETE = 5
  public static final String MESSAGE = "message"
  public static final String OBJECT = "object"


  int type
  def messageBody = []
  String messageTitle

  @Override
  String toString() {
    String json = '{"messageTitle":"' + messageTitle + '", "type":"'+type+'", "messageBody":"'+messageBody.join('<br/>')+'"}'
    return json
  }

  String toMessageAsString() {
    String json = '"messageTitle":"' + messageTitle + '", "type":"'+type+'", "messageBody":"'+messageBody.join('<br/>')+'"'
    return json
  }
}
