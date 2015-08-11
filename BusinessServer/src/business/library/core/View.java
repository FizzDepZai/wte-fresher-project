
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.library.core;
import hapax.TemplateDictionary;
import hapax.Template;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import hapax.TemplateException;
/**
 *
 * @author tript
 */
public class View extends TemplateDictionary{
    protected String module; //package folder of views
    protected String templateFile; //trang template

    public String getModule() {
        return module;
    }

    
    public String getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }
   
    public View(String templateFile){
        this.templateFile = templateFile;
    }

    public View(String module, String templateFile) {
        this.module = module;
        this.templateFile = templateFile;
    }
    

       
    
    public String render(){
        try {
        TemplateLoader loader = TemplateResourceLoader.create("");
        Template template;

        template  = loader.getTemplate(module+"/"+templateFile);
//       template  = loader.getTemplate("connection.views/chat.xtm");
        return template.renderToString(this);
        } catch (TemplateException e){
            e.printStackTrace();
            return "Template error";
        }
    }
}
