import java.io.*;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Provide the folder path to compressed folder");
        String sourcePath = s.nextLine();
        System.out.println("Provide the folder path for the uncompressed file");
        String destinationPath = s.nextLine();
        sourcePath = sourcePath.replace("\\", "\\\\");
        destinationPath = destinationPath.replace("\\", "\\\\");
        unzip(sourcePath,destinationPath);
    }

    private static void unzip(String sourcePath, String destinationPath){

        File destFile = new File(destinationPath);
        File sourceFile = new File(sourcePath);
        if(!destFile.exists())destFile.mkdirs();
        byte[] buffer = new byte[1024];
        try(FileInputStream fileInputStream = new FileInputStream(sourceFile)){
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while(zipEntry != null){
                String fileName = zipEntry.getName();
                File newFile = new File(destinationPath + File.separator + fileName );
                System.out.println("Unzipping to " + newFile.getAbsolutePath());
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                zipInputStream.closeEntry();
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.closeEntry();
            zipInputStream.close();
        }catch (IOException ioe){
            System.out.println("Opps something went wrong");
        }
    }
}
