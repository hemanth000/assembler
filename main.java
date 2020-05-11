import java.io.*;
import java.util.*;
public class Practice1 {
       public static void main(String [] args)throws Exception {
    	  //1.read from a file
    	   
    	   File file = new File("C:\\Users\\COmputer\\OneDrive\\Desktop\\test.txt");
    	   
    	   BufferedReader br = new BufferedReader(new FileReader(file));
    	   Map<String,String>output=new LinkedHashMap<>();
    	   Map<String,String>Symtab=new LinkedHashMap<>();
    	   Map<String,String>optab=new LinkedHashMap<>();
    	   Map<String,String>obcode=new LinkedHashMap<>();
    	   ArrayList<String> objcode =new ArrayList<>();
    	   optab.put("ldx","04");
    	   optab.put("lda","00");
    	   optab.put("Add","18");
    	   optab.put("tlx","2c");
    	   optab.put("jlt","38");
    	   optab.put("sta","0c");
    	   optab.put("Rsub","4c");
    	   
    	   
    	   String st;
    	   String loc=null;
    	   int j=0;
    	   int i=0;
    	   String prevaddress=null;
    	   String address=null;
    	   String startingadd=null;
    	   String lastadd=null;
    	   String pname=null;
    	  while ((st = br.readLine()) != null) {
    		  
    		  String [] words=null;
    	       words=st.split(" ");
    		   if(words[1].equals("start")) {
        		   loc=words[2];
        		   startingadd=words[2];
        		   pname=words[0];
        		   output.put("---",st);
    		   }
    		   else {
    			   j=3*i;
    			   
    			   if(words[1].equals("resw")||words[1].equals("word")) {
    				   String operand=words[2];
    				   int opevalue=Integer.parseInt(operand)*3;
    				   String opervalue=Integer.toHexString(opevalue);
    				   int add=Integer.parseInt(prevaddress,16)+Integer.parseInt(opervalue,16);
    				   address=Integer.toHexString(add);
    				   prevaddress=address;
            		   output.put(address, st);         		   
    				   
    			   }
    			   else if(words[1].equals("end")) {
    				   int lastaddress=Integer.parseInt(prevaddress,16)+Integer.parseInt("3",16);
    				   address=Integer.toHexString(lastaddress);
    				   lastadd=address;
    				   output.put(address,st);
    			   }
    			   else {  
    			   String num1=Integer.toHexString(j);
    			   int num2=Integer.parseInt(loc,16)+Integer.parseInt(num1,16);
    	    	   address=Integer.toHexString(num2);
    	    	   prevaddress=address;
    	    	   output.put(address,st);
    	    	   
    			   }
    			   if(words[0].length()!=0) {
    	    		   Symtab.put(words[0],address);
    	    	   }
    			   
    	    	   
    			  
    		   
    			   i++;
    		   
    		   
    		  
    	  }
    	  }
    	  br.close();
    	  
    	  //pass2
    	  String s;
    	  BufferedReader fr = new BufferedReader(new FileReader(file));
    	  while((s=fr.readLine())!=null) {
    		  String[] words=null;
    		  words=s.split(" ");
    		  /*if(words[1]!="start"||words[1]!="resw"||words[1]!="word"||words[1]!="end") {
    			  String opcode=optab.get(words[1]);
    			  String opaddress=Symtab.get(words[2]);
    			  String Objectcode=opcode+opaddress;
    			  obcode.put(s,Objectcode);
    		  }
    		  */
    		  if(words[1].equals("start")||words[1].equals("resw")||words[1].equals("word")||words[1].equals("end")) {
    			  obcode.put(s,"  ");
    		  }
    		  else {
    			  String opcode=optab.get(words[1]);
    			  String opaddress=Symtab.get(words[2]);
    			  String Objectcode=opcode+opaddress;
    			  objcode.add(Objectcode);
    			  obcode.put(s,Objectcode); 
    		  }
    	  }
    	  //program with object address
    	  
    	  for(Map.Entry m:output.entrySet()){  
    		   System.out.println(m.getKey()+" "+m.getValue());  
    		  }
    	  System.out.println();
    	  //symtab table
    	  
    	  System.out.println("#######   SYMTAB    ##########");
    	  System.out.println();
    	  for(Map.Entry n:Symtab.entrySet()){  
   		   System.out.println(n.getKey()+" "+n.getValue());  
   		  } 
    	  System.out.println();
    	  //program with object code generation
    	  
    	  System.out.println("### Object code Generation #####");
    	  System.out.println();
    	  for(Map.Entry n:obcode.entrySet()){  
      		   System.out.println(n.getKey()+"           "+n.getValue());  
      		  } 
    	  System.out.println();
    	  
    	  //object program
    	  
    	  System.out.println("#### object program ##########");
    	  System.out.println();
    	  int length=Integer.parseInt(lastadd,16)-Integer.parseInt(startingadd,16);
    	  String lprogram=Integer.toHexString(length);
    	  
    	  
    	  System.out.println("H"+"  "+pname+" 00"+startingadd+" 00"+lprogram);//header record
    	  //text record
    	  double n=0;
    	  if(objcode.size()*6 <=60) {
    		 n=1; 
    	  }
    	  else {
    		  n=Math.ceil((objcode.size()*6)/(n));
    	  }
    	  for(int p=0;p<n;p++) {
    		  System.out.print("T"+" "+"00"+startingadd+" ");
    		  for(String q:objcode) {
    			  System.out.print(q+" ");
    		  }
    		 System.out.println();
    	  }
    	  
    	  System.out.println("E"+" "+"00"+startingadd);//end record
    	  
    	/*  for(String k:objcode) {
    		  System.out.println(k);
    	  }
    	  */
    	  
    	   
    	   
    	  /* String sentence="copy start 4000";
    	   String[] words=sentence.split(" ");
    	   int loc;
    	   //we got the location counter for starting address
    	   if(words[1].equals("start")) {
    		   loc=Integer.parseInt(words[2]);                              
    		   for(int i=1;i<10;i++) {
    			   int  j=3*i;
    			   loc=loc+Integer.parseInt(Integer.toHexString(j));
    		   }
    		   
    	    int loc=0x4000;
    		 for(int i=1;i<10;i++) {
    			 String j=Integer.toString(3*i);
    		     loc=0x4000+Integer.parseInt(j,16);
    		    String loc1=Integer.toHexString(loc);
    		    //System.out.println(loc1);
                String k="12";
    		 } 
    		  int num=0x4000+0Xa;
    		  System.out.println(Integer.toHexString(num));
    		  String num1=Integer.toHexString(3);
    		  int num2=0x4000+Integer.parseInt(num1,16);
    		  System.out.println(Integer.toHexString(num2));
                ;
    	   */
    	   
    	     
      

       }
}  
