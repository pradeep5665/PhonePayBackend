package org.uhc.util;

import java.util.Map;

public class EmailTemplate {
    private String template;

    public EmailTemplate(String template){
        this.template = template;
    }

    public String getEmailMessage(Map<String, String> replacements){
        String message = this.template;
        if(message != null){
            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                message = message.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }
        }
        return message;
    }

}
