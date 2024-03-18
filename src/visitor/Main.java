package visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

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

        rootdir.accept(new ListVisitor());
        System.out.println();

        System.out.println("Making user entries...");
        Directory kim = new Directory("kim");
        Directory lee = new Directory("lee");
        Directory park = new Directory("park");

        usrdir.add(kim);
        usrdir.add(lee);
        usrdir.add(park);

        kim.add(new File("diary.html", 100));
        kim.add(new File("Visitor.java", 100));
        lee.add(new File("memo.tex", 100));
        park.add(new File("game.doc", 100));
        park.add(new File("junk.mail", 100));

        rootdir.accept(new ListVisitor());
        System.out.println();

        FileFindVisitor ffv = new FileFindVisitor(".html");
        rootdir.accept(ffv);

        System.out.println("HTML files are:");
        for (File file : ffv.getFoundFiles()) {
            System.out.println(file);
        }
    }
}

class MyFileVisitor extends SimpleFileVisitor<Path> {
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("dir : " + dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("dir : " + dir);
        return FileVisitResult.CONTINUE;
    }
}
