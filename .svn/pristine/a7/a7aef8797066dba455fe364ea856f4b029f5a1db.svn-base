package com.docu.accesscontrol.commons

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 5/15/11
 * Time: 6:58 PM
 * To change this template use File | Settings | File Templates.
 */
class PluginMessage {
  static final int SUCCESS = 1
  static final int ERROR = 0
  static final int WARNING = 2
  static final int CONFIRMATION = 3
  static final int INFORMATION = 4
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
}
