package facade;

import facade.pagemaker.PageMaker;

public class Main {
    public static void main(String[] args) {
        PageMaker.makeWelcomePage("test@example.com", "welcome.html");
        PageMaker.makeLinkPage("linkpage.html");
    }
}
