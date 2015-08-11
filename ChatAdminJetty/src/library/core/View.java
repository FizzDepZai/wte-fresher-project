
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.core;
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
    protected String module; //child folder of view
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
        if (module!=null){
            template = loader.getTemplate("application/views/"+module+"/"+templateFile);
        } else {
            template = loader.getTemplate("application/views/layout/"+templateFile);
        }
        return template.renderToString(this);
        } catch (TemplateException e){
            e.printStackTrace();
            return "Template error";
        }
    }
}
