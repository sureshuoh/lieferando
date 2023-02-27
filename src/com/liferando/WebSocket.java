package com.liferando;
import static com.sun.nio.file.ExtendedWatchEventModifier.FILE_TREE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSocket {
	 public static void main(String[] args) throws Exception
	    {
		 System.out.println("websockets called here ");
	        FileSystem fs = FileSystems.getDefault();
	        WatchService ws = fs.newWatchService();
	        Path pTemp = Paths.get("C:/Lieferando/pdf");
	        pTemp.register(ws, new WatchEvent.Kind[] {ENTRY_MODIFY, ENTRY_CREATE, ENTRY_DELETE});
	        while(true)
	        {
	            WatchKey k = ws.take();
	            for (WatchEvent<?> e : k.pollEvents())
	            {	            	 
	                Object c = e.context();
	                System.out.printf("%s %d %s\n", e.kind(), e.count(), c);
	                
	                if(e.kind()==ENTRY_CREATE) {
		                Pushers pushrs=	new Pushers();
		                pushrs.Pdf_();
	                }
	                
	            }
	            k.reset();
	        }
	    }
}
  
