package org.rmnorbert;


public class Main {
    public static void main(String[] args) {
        if (args.length >= 1) {
            String name = args[0];
            String email = args[1] != null ? args[1] : "";
            HtmlGeneratorApplication application = new HtmlGeneratorApplication(name, email);
            application.run();
        } else {
            HtmlGeneratorApplication application = new HtmlGeneratorApplication("", "");
            application.run();
        }
    }
}