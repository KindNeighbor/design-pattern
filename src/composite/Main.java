package composite;

public class Main {

    public static void main(String[] args) {
        System.out.println("Making root entries...");
        Directory rootdir = new Directory("root");
        Directory bindir = new Directory("bin");
        Directory tmpdir = new Directory("tmp");
        Directory usrdir = new Directory("usr");

        rootdir.add(bindir);
        rootdir.add(tmpdir);
        rootdir.add(usrdir);
        bindir.add(new File("vi", 10000));
        bindir.add(new File("latex", 20000));
        rootdir.printList();
        System.out.println();

        System.out.println("Making user entries...");
        Directory kim = new Directory("kim");
        Directory lee = new Directory("lee");
        Directory choi = new Directory("choi");

        usrdir.add(kim);
        usrdir.add(lee);
        usrdir.add(choi);
        kim.add(new File("diary.html", 100));
        kim.add(new File("Composite.java", 200));
        lee.add(new File("memo.tex", 300));
        choi.add(new File("game.doc", 400));
        File file = new File("junk.mail", 500);
        choi.add(file);
        rootdir.printList();

        System.out.println();
        System.out.println("file = " + file.getFullName());
        System.out.println("choi = " + choi.getFullName());
    }
}
