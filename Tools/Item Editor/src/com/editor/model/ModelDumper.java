package com.editor.model;

import com.alex.store.Index;
import com.alex.store.Store;

import java.io.FileOutputStream;
import java.io.IOException;

public class ModelDumper {
   private static Store STORE;

   public static void main(String[] args) throws IOException {
      try {
         STORE = new Store("C:/Users/szumaster/Documents/GitLab/2009scape/Server/data/cache");
      } catch (Exception var4) {
         ;
      }

      Index index = STORE.getIndexes()[7];
      System.out.println(index.getLastArchiveId());

      for(int i = 0; i < index.getLastArchiveId(); ++i) {
         byte[] data = index.getFile(i);
         if(data != null) {
            writeFile(data, "/Dumps/Models/" + i + ".dat");
         }
      }

   }

   public static void writeFile(byte[] data, String fileName) throws IOException {
      FileOutputStream out = new FileOutputStream(fileName);
      out.write(data);
      out.close();
   }
}
