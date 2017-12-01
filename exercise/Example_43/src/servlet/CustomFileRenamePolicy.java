package servlet;

import java.io.File;

import java.text.SimpleDateFormat;

import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class CustomFileRenamePolicy implements FileRenamePolicy {

 // Here we implement the renameFile(File file) method from FileRenamePolicy
 // interface

 public File rename(File file) {

  // Here we get the parent directory path as in U:/documents/reports/

  String parentDir = file.getParent();

  // Here we get the filename without its path location, such as view.gif

  String fileName = file.getName();

  // Here we get the extension if the file has one

  String fileExt = "";

  int i;

  if ((i = fileName.indexOf(".")) != -1) {

   /*
    *
    * Here we read s substring of filename starting from ".", which
    *
    * will be the file extension
    */

   fileExt = fileName.substring(i);

   // Here we read the filename without its extension

   fileName = fileName.substring(0, i);

  }

  // Here we get the date

  Date date = new Date();

  // Here we choose the date presentation format

  SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");

  // Here we add the date time stamp to the file name

  fileName = fileName + "_" + sdf.format(date);

  // Here we put the file name parts together and create a new filename
  // with the

  // directory path and the extension

  fileName = parentDir + System.getProperty("file.separator") + fileName
    + fileExt;
  

  return new File(fileName);

 }

}