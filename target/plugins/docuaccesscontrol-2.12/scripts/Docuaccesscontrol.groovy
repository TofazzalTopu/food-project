
//includeTargets << grailsScript("Init")


includeTargets << new File("$docuaccesscontrolPluginDir/scripts/_Prerequisit.groovy")

USAGE = """
Usage: grails docuaccesscontrol <domain-class-package>

Creates Access Control Plugin in the specified package

Example: grails docuaccesscontrol com.yourapp
"""

target(Docuaccessplugin: "The description of the script goes here!") {
    // TODO: Implement script here
  configure()
  createDomains()
  copyImages()
  updateConfig()
  generateModifiedGsp()
}

private void configure() {
	def argValues = parseArgs()
    packageName=argValues

  templateAttributes = [packageName: packageName]
}

private void createDomains() {
  String dir = packageToDir(packageName)
  generateFile "$templateDir/Requestmap.groovy.template", "$appDir/domain/${dir}/Requestmap.groovy"
  generateFile "$templateDir/UserAuthority.groovy.template", "$appDir/domain/${dir}/UserAuthority.groovy"
}

private void copyImages(){
   copyFile "$templateDir/pluginajax-loader.gif.template",  "$rootDir/web-app/images/pluginajax-loader.gif"
   copyFile "$templateDir/plugincross.png.template",  "$rootDir/web-app/images/plugincross.png"
   copyFile "$templateDir/plugin_submission_loader.gif.template",  "$rootDir/web-app/images/plugin_submission_loader.gif"
}

private void updateConfig() {

	def configFile = new File(appDir, 'conf/Config.groovy')
	if (configFile.exists()) {
		configFile.withWriterAppend {
			it.writeLine '\n// Added by docu accesscontrol plugin:'
			it.writeLine "pluginStyle = 'pluginStyle'"
            it.writeLine "pluginCss = 'pluginStyle.css'"
		}
	}
}

private void generateModifiedGsp(){
//  copyFile "$templateDir/requestmap/create.gsp.template", "$appDir/views/requestmap/create.gsp"
  String sourceFileName=  "$templateDir/create.gsp.template"
  String targetFileName= "$docuaccesscontrolPluginDir/grails-app/views/requestmap/create.gsp"
  File file = new File(sourceFileName);

    BufferedReader reader = null;
    BufferedWriter writer=null;

    try {
      reader = new BufferedReader(new FileReader(file));
      file=new File(targetFileName)
      writer=new BufferedWriter(new FileWriter(file))
      String text = null;

      writer.write("<%@ page import=\"${packageName}.UserAuthority\"%>")
      writer.write(System.getProperty("line.separator"))
      while ((text = reader.readLine()) != null) {

        writer.write(text)
        writer.write(System.getProperty("line.separator"))
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (reader != null) {
          reader.close();
          writer.close()
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // show file contents here
    //System.out.println(contents.toString());
}

private parseArgs() {
   if(args.size() >0){
      return args
   }else{
     ant.echo message: USAGE
     System.exit(1)
   }
}
setDefaultTarget('Docuaccessplugin')