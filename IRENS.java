/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irens;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Eltsarion
 */
public class IRENS {

    public static void main(String[] args) {
        
    Customize CM = new Customize();
    scrollDir SD = new scrollDir();

    SD.Catalog();
    CM.Config();
    
    String basicDir = CM.Config();
    

    if (basicDir == null){
    do {
        basicDir = CM.Config();
        if (basicDir != null) break;
    } while (basicDir != null); 
    }
    
    SD.listDir(basicDir);
    
    if (SD.sb.toString().length() > 0) {
    SD.treatment_catalog();
    }
    SD.sort_catalog();
    System.exit(0);
    
        
    }
    
}

class Customize{
    static File F;
    public String Config(){
        boolean Verification;
        String GetDir = null;
        
        F = new File (CurrentDir() + "Configuration.cfg");
        if (F.exists() && F.isFile()) {
            
        try (FileReader fr = new FileReader(F)){
            
            BufferedReader reader = new BufferedReader(
            new InputStreamReader (new FileInputStream(F), "windows-1251"));
            String line = reader.readLine();
            int number_i = 0;
                 while (line != null) {
                 number_i++;
                 if (number_i == 1 && line.length() > 10){
                   int check_int = line.substring(0, 10).compareTo("DIR_Basic:");
                   
                   if (check_int == 0){ 
                     GetDir = line.substring(10, line.length()).trim();
                     } else {
                     DeleteCfg();
                     Config();
                     }

                 line = reader.readLine();
                 } else {
                   DeleteCfg();
                   Config();
                 }
                 }
                 
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        } else {
            CreateCfg();
            Config();
        }
        
        return GetDir;
    }
    
    public static String CurrentDir(){
	String path = System.getProperty("java.class.path");
	String FileSeparator = (String)System.getProperty("file.separator");
        String Dir = path.substring(0, path.lastIndexOf(FileSeparator)+1); 
	return Dir;
 }
    
    public void DeleteCfg(){
        if (F.exists() && F.isFile()){
            F.delete();
            CreateCfg();
            } else if (!F.exists() && !F.isFile()){
            CreateCfg();
        }
}
    
    public void CreateCfg(){
        try {
                F.createNewFile();
                    try ( FileWriter fw = new FileWriter(F) ) {
                        fw.write("DIR_Basic: " + "C:\\TestData");
                        fw.close();
                    }
                    } catch (IOException ex) {
                System.out.println("Error!");
            }
    }
    
    
}

//Класс работающий непосредственно с номерами телефонов

class scrollDir {
    
    Customize CM = new Customize();
    File F = new File (CM.CurrentDir() + "Catalog.txt");
    StringBuilder sb = new StringBuilder();
    int n_count = 0;
    
    public void listDir(String dir){
    File F_Dir = new File(dir);
    if(F_Dir.isDirectory())
        {
        for(File item : F_Dir.listFiles()){
                 if(item.isDirectory()){  
                     
                  listDir(item.getAbsolutePath()); 
                 }
                 else if (item.isFile()){
                     
                  treatment_F(item);

                 }
        }
    
}
}
    
    public void Catalog(){
        if (!F.exists()){
	try {
                F.createNewFile();
                    } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }
    
    public void treatment_F(File F){
      if (F.exists() && F.isFile()){
      String file_extension = F.getAbsolutePath();
      if (file_extension.lastIndexOf(".") != -1 && file_extension.lastIndexOf(".") != 0 && file_extension.substring(file_extension.lastIndexOf(".")+1).equalsIgnoreCase("txt")){

            BufferedReader reader; 
          try {
              reader = new BufferedReader(
                      new InputStreamReader (new FileInputStream(F), "windows-1251"));
                String line = reader.readLine();
                while (line != null) {
                int n_char = 0;
                char [] line_char = line.toCharArray();
                 for(char c : line_char) {
			if(Character.isDigit(c)) {
                        sb.append(c);
                        }
                    }
                sb.append(System.getProperty( "line.separator" ));
                line = reader.readLine(); 
                }
                 
        } catch(IOException ex) {
                     System.out.println(ex.getMessage());
                 }         
      }
      }
    }
    
    public void treatment_catalog(){
    String[] catalog_arr = new String[n_count];
    try (FileWriter fw = new FileWriter(F, true)){
        if (F.exists() && F.isFile()){
  
        String[] lines = sb.toString().split(System.getProperty( "line.separator" ));
            for(String line: lines){
                 if (line.length() <= 12){
                     
                     if(line.indexOf("0") == 0 && line.length() == 10){
                     line = "+38 (" + line.substring(0, 3) + ") " + line.substring(3, line.length());
                     line = line.substring(0, 13) + "-" + line.substring(13, line.length());
                     fw.write(line);
                     fw.write(System.getProperty( "line.separator" ));
                     continue;
                     } else if (line.indexOf("80") == 0 && line.length() == 11){
                     line = "+3" + line.substring(0, 1) + " (" + line.substring(1, 4) + ") " + line.substring(4, line.length());
                     line = line.substring(0, 13) + "-" + line.substring(13, line.length());
                     fw.write(line);
                     fw.write(System.getProperty( "line.separator" ));
                     continue;
                     }
                     else if (line.indexOf("380") == 0 && line.length() == 12){
                     line = "+" + line.substring(0, 2) + " (" + line.substring(2, 5) + ") " + line.substring(5, line.length());
                     line = line.substring(0, 13) + "-" + line.substring(13, line.length());
                     fw.write(line);
                     fw.write(System.getProperty( "line.separator" ));
                     continue;
                     }
                     
                     line= "+" + line;
                     if (line.length() <= 11){
                     line = line.substring(0, 1) + "7" + line.substring(1, line.length());
                     System.out.println(line);
                     }
                     
                 if (line.substring(2, line.length()).length() == 7){
                 line = line.substring(0, 2) + " (812) " + line.substring(2, line.length());
                 line = line.substring(0, 12) + "-" + line.substring(12, line.length());
                 
                 } else if(line.substring(2, line.length()).length() == 10){
                 line = line.substring(0, 2) + " (" + line.substring(2, 5)
                         + ") " + line.substring(5, line.length());
                 
                 line = line.substring(0, 12) + "-" + line.substring(12, line.length());
                 }
                 
                 }
                 
                 if (line.length() == 17){
                    fw.write(line);
                    fw.write(System.getProperty( "line.separator" ));
                    }
                 }
        } else {
            Catalog();
            treatment_catalog();
        }
       fw.close();
    } catch(IOException ex) {
        System.out.println(ex.getMessage());
                 }        

}
    
    public void sort_catalog(){
        int i = 0;
        if (F.exists() && F.isFile()) {
        String arr_line[] = new String[F_lineCount(F)];
           
        try (FileReader fr = new FileReader(F)){
            
            BufferedReader reader = new BufferedReader(
            new InputStreamReader (new FileInputStream(F), "windows-1251"));
            String line = reader.readLine();
            
                 while (line != null) {
                 arr_line[i] = line;    
                 i++;
                 line = reader.readLine();
                     
                 }
                 
                try {
                 FileWriter fw = new FileWriter(F); 
                 BufferedWriter writer  = new BufferedWriter(fw);
                 
                 Arrays.sort(arr_line); 
                 
                 reader.close();
                 fr.close();
                 
                 if (F.exists())
                 writer.write("");
                 
                 HashSet<String> set = new HashSet<>(Arrays.asList(arr_line));
                 Collections.addAll(set, arr_line);
                 List sortList = new ArrayList(set);
                 Collections.sort(sortList);
                 for (Object s : sortList)
                    {
                    fw.write((String) s);
                    fw.write(System.getProperty( "line.separator" ));
                    }
                writer.close();
                } catch (Exception e) 
                {System.err.println("Error in file cleaning: " + e.getMessage());}
                 
                 
        } catch(IOException ex) {
        System.out.println(ex.getMessage());
                 }
        }
    }
    
    public int F_lineCount(File F){
        int count = 0;
    if (F.exists() && F.isFile()) {
            
        try (FileReader fr = new FileReader(F)){
            
            BufferedReader reader = new BufferedReader(
            
            new InputStreamReader (new FileInputStream(F), "windows-1251"));
            
            String line = reader.readLine();
            
                 while (line != null) {
                     
                 count++; 
                 
                 line = reader.readLine();
                     
                 }
                 
                 reader.close();
                 fr.close();
        } catch(IOException ex) {
        System.out.println(ex.getMessage());
                 }
        }
    return count;
    }
    
}
