import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipException;

public class ZipFile
{
	private ArrayList<String> fileList;
	private String outputFile;
	private String srcPath;
	private File file;
   
	public ZipFile(File file) 
	{
		fileList = new ArrayList<String>();
      	// TODO
         this.fileList = fileList;
         String f = file.getName();
         this.outputFile = f + ".zip";
         File path = file.getAbsoluteFile();
         String src = path.getAbsolutePath();
         this.srcPath = src;
         this.file = file;
         
   	}
   
   	public void fillFileList(File f)
   	{
      	if (f.isFile())
      	{
         	String currFilePath = f.getAbsoluteFile().toString();
         	String relativeFilePath;
         	if (this.srcPath.length() < currFilePath.length())
            	relativeFilePath = currFilePath.substring(this.srcPath.length() + 1, currFilePath.length());
         	else
            	relativeFilePath = currFilePath.substring(this.srcPath.length(), currFilePath.length());
         
         	fileList.add(relativeFilePath);
      	}
                
      	if (f.isDirectory())
      	{
         	// TODO
            String [] fileList = f.list();
            for(String s : fileList)
               fillFileList(new File(f, s));
      	}
   	}
   
   	public void makeZip(File dir)
   	{
    	// TODO  
         byte [] buffer = new byte[1024];
          
         try
         {
            FileOutputStream output = new FileOutputStream(this.outputFile);
            ZipOutputStream zoutput = new ZipOutputStream(output);
            
            for(String s: this.fileList)
            {
               String path;
               FileInputStream fis;
               ZipEntry ze;
               
               if(s.equals(""))
               {
                  path = this.srcPath;
                  fis = new FileInputStream(path);
                  System.out.println("Adding... " + this.file.getName());
                  ze = new ZipEntry(this.file.getName());
               }
               else
               {
                  path = this.srcPath + File.separator + s;
                  fis = new FileInputStream(path);
                  System.out.println("Adding... " + s);
                  ze = new ZipEntry(s);
               }
               zoutput.putNextEntry(ze);
               
               int l;
               while((l = fis.read(buffer)) > 0)
               {
                  zoutput.write(buffer, 0, l);
               }
               fis.close();
               zoutput.closeEntry();

            }
            zoutput.close();
            System.out.println("Zip File Has Been Completed " + this.outputFile);

          }
          catch(FileNotFoundException e)
          {
            System.out.println("File Not Found");        
          }
          catch(ZipException ex)
          {
            System.out.println("Zip Format Error");
          }
          catch(IOException exp)
          {
            System.out.println("Input/Output Error");
          }
          
   	}
}