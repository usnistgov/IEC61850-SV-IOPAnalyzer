package MU92E2InteroperabilityAnalyzer;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
//import java.io.FileNameExtensionFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class MUIoPXMLAnalyzer {
    
    //FileWriter fileWriter = null;;//new PrintWriter("filename.txt");
	public File file;
	public File dataFile[]=new File[25];
    
    
    public static String xmlDataFileName ="";
    //"C:\\Users\\ysong\\workspace\\MU92E2InteroperabilityAnalyzer\\test_004.xml";
    //"C:\\Users\\ysong\\workspace\\MU92E2InteroperabilityAnalyzer\\test_SV_Sim.xml";
    //"C:\\Users\\ysong\\workspace\\MU92E2InteroperabilityAnalyzer\\test_002.xml";
    
    public static String xmlDataFileFullName ="";
    FileWriter fileWriter = null;//new PrintWriter("filename.txt");
    FileWriter dataFileWriter[] = new FileWriter[25];
    String svStreamID[]= new String[25];
    //String dataFileName = "threePhaseCurrentAndVoltage.txt";
    
    //String globalSVIDStr[]= new String[10];
    String globalSVIDStr="";
    int globalSampleCounter=0;
    
	boolean status;	    
	boolean fieldStatus;
	
	boolean ethLanStatus;	    
	boolean ethLanFieldStatus;
	
	boolean vLanStatus;	    
	boolean vLanFieldStatus;
	 	 
	boolean dstMacAddrStatus;	    
	boolean dstMacAddrFieldStatus; 		
	
	public  MUIoPXMLAnalyzer(){
		analyzerControl();
	}
	
	public void analyzerControl()
	{
	       String testCase="";
	       //SendUSVMessage
	       //GetMSVCBValues
	       //SetMSVCBValues
	       //GetUSVCBValues
	       //SetUSVCBValues
	       //GetLogicalNodeDirectory
	       //GetLogicalNodeDirectory
	       //GetAllDataValue
	        
	        System.out.println("\nMU Interoperability Analyzing System\n");  
	        // Display file choose
			JFileChooser jfc = new JFileChooser();//FileSystemView.getFileSystemView().getHomeDirectory()
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".xml","xml");
			jfc.setFileFilter(filter); 
			 //String currentDir = System.getProperty("user.dir");
		     //System.out.println("Current dir using System:" +currentDir);		
		        
		        File workingDirectory = new File(System.getProperty("user.dir"));
		        jfc.setCurrentDirectory(workingDirectory);		 
		        System.out.println("Current dir using System:" +workingDirectory);	
		        
			//jfc.setCurrentDirectory(currentDir);
			jfc.setDialogTitle("Choose an IEC 61850-9-2 SV packet data file in XML:");
			int returnValue = jfc.showOpenDialog(null);
			// int returnValue = jfc.showSaveDialog(null);
	        
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				xmlDataFileName=selectedFile.getName();
				System.out.println("File Name: "+xmlDataFileName);
				
				xmlDataFileFullName = selectedFile.getAbsolutePath();
				System.out.println("File Full Path Name: " +xmlDataFileFullName);
				
				
				xmlDataFileName = xmlDataFileFullName.replace("\\","\\\\");
				
				System.out.println("File full name: "+ xmlDataFileName);
				
			}	        
	        
	        
	        System.out.println("\nTest Cases:");
	        System.out.println("0: Exit Testing!!!");
	        System.out.println("1: SendMSVMessageTestCase");
	        System.out.println("2: SendUSVMessageTestCase");            
	        System.out.println("3: GetMSVCBValuesTestCase");            
	        System.out.println("4: SetMSVCBValuesTestCase");
	        System.out.println("5: GetUSVCBValuesTestCase");   
	        System.out.println("6: SetUSVCBValuesTestCase");    
	        System.out.println("7: GetLogicalNodeDirectoryTestCase");   
	        System.out.println("8: GetAllDataValueTestCase"); 	        
	        
	        System.out.println("\nStaring to analyze interoperability of Test Case now!!!");              

	        
	        boolean iopAnalysisResult=false;
	        

			
			  while(true)
			  {
				  


				  
			        Scanner keyboard = new Scanner(System.in);		
			        System.out.println("\nPlease enter test case No.:");
			        int testCaseNo = keyboard.nextInt();	        
			        
			        switch(testCaseNo)
			        {                    	 
			            case 0:  testCase = "0:Exit";
			            		 {
			            			System.out.println("Exit testing!!!"); 
			            			System.exit(0);
			            			
			            		 }
			            case 1:  testCase = "1:SendMSVMessageTestCase";
					       		 {
					       			System.out.println("***** Interoperability Analysis of IEC 61850-9-2 MU SendMSVMessage Test Case ********");
				            		iopAnalysisResult=SendMSVMessageTestCaseIOPAnalysis(xmlDataFileName);
				            		System.out.println("SendMSVMessage test case IOP Analysis Result: "+iopAnalysisResult);			            		
					       			break;
					       		 }
			            case 2:  testCase = "2:SendUSVMessageTestCase";
					       		 {
						       		System.out.println("************ Interoperability Analysis of SendUSVMessage Test Case*******************");
				            		//iopAnalysisResult = SendUSVMessageTestCaseIOPAnalysis(xmlDataFileName);
				            		System.out.println("SendUSVMessage test case IOP Analysis Result: "+iopAnalysisResult);
					       			break;
					       		 }
			            case 3:  testCase = "3:GetMSVCBValuesTestCase";
					       		 {
						       		System.out.println("************ Interoperability Analysis of GetMSVCBValues Test Case*******************");
				            		//iopAnalysisResult=GetMSVCBValuesTestCaseIOPAnalysis(xmlDataFileName);
				            		System.out.println("GetMSVCBValuestest case IOP Analysis Result: "+iopAnalysisResult);			            		
					       			break;
					       		 }
			            case 4:  testCase = "4:SetMSVCBValuesTestCase";
					       		 {
						       			System.out.println("************ Interoperability Analysis of SetMSVCBValues Test Case*******************");	
				            		//iopAnalysisResult=SetMSVCBValuesTestCaseIOPAnalysis(xmlDataFileName);
				            		System.out.println("CSetMSVCBValues test case IOP Analysis Result: "+iopAnalysisResult);			            		
					       			break;
					       		 }
			            case 5:  testCase = "5:GetUSVCBValuesTestCase";
					       		 {
						       		System.out.println("************ Interoperability Analysis of GetUSVCBValues Test Case*******************");
				            		//iopAnalysisResult=GetUSVCBValuesTestCaseIOPAnalysis(xmlDataFileName);
				            		System.out.println("GetUSVCBValues test case IOP Analysis Result: "+iopAnalysisResult);			            		
					       			break;
					       		 }
			            case 6:  testCase = "6:SetUSVCBValuesTestCase";
					       		 {
						       		 System.out.println("************ Interoperability Analysis of SetUSVCBValues Test Case*******************");
				            		 //iopAnalysisResult=SetUSVCBValuesTestCaseIOPAnalysis(xmlDataFileName);
				            		 System.out.println("SetUSVCBValues test case IOP Analysis Result: "+iopAnalysisResult);			            		 
					       			 break;
					       		 }
			            default: testCase = "Default";
					       		 {
				            		 System.out.println("\nDefault"); 			       			
					       			 break;
					       		 }
			        
			        }
			  }		
	}
	
	
	//SendMSVMessageTestCaseIOPAnalysis
	public boolean SendMSVMessageTestCaseIOPAnalysis(String xmlFileName){

		 
   	  try {

   	   file = new File("C:\\Users\\ysong\\workspace\\MU92E2InteroperabilityAnalyzer\\src\\MUAnlysisResult.txt");	   
       fileWriter = new FileWriter(file);                        
        // if file doesnt exists, then create it
        if (!file.exists()) {
        	file.createNewFile();
        }  
        
        
        /* SvID:
			0: SIEMENSMU0101
			1: Vizimax4001
			2: Vizimax4002 
			3: VIZ_TxA_MU2
			4: VIZ_INCA_MU02
			5: SAM600MU0101
			6: MU320MU0101
			7: 4000	
			8: SIE_A1_MU2	
			9: RTDS_VIZMU0301	
			10: RTDS_VIZMU0501	
			11: RTDS_MU6MU0701	
			12: RTDS_MU8MU1001	
			13: RTDSMU9MU1101	
			14: RTDS_ABBMU0101	
			15: RTDS_MU7MU0901		
			16: RTDS_SIEMMU0401	
			17: RTDS_SIEMMU0601	
			18: RTDS_MU5MU0801	
			19: RTDSMU2MU1201	
			20: RTDS_GEMU0201	
			21: RTDS_MU8MU1001	
			22: TEMPLATEPISV/LLN0$SV$Smvcb
			23: TEMPLATE
        	*/
  
       // for(i=0; i<25; i++){
        	svStreamID[0] = "SIEMENSMU0101";
        	svStreamID[1]= "Vizimax4001";
        	svStreamID[2]= "Vizimax4002"; 
        	svStreamID[3]= "VIZ_TxA_MU2"; 
        	svStreamID[4]= "VIZ_INCA_MU02"; 
        	svStreamID[5]= "SAM600MU0101"; 
        	svStreamID[6]= "MU320MU0101"; 
        	svStreamID[7]= "4000"; 	
        	svStreamID[8]= "SIE_A1_MU2"; 	
        	svStreamID[9]= "RTDS_VIZMU0301"; 	
        	svStreamID[10]= "RTDS_VIZMU0501"; 	
        	svStreamID[11]= "RTDS_MU6MU0701"; 	
        	svStreamID[12]= "RTDS_MU8MU1001"; 	
        	svStreamID[13]= "RTDSMU9MU1101"; 	
        	svStreamID[14]= "RTDS_ABBMU0101"; 	
        	svStreamID[15]= "RTDS_MU7MU0901"; 		
        	svStreamID[16]= "RTDS_SIEMMU0401"; 	
        	svStreamID[17]= "RTDS_SIEMMU0601"; 	
        	svStreamID[18]= "RTDS_MU5MU0801"; 	
        	svStreamID[19]= "RTDSMU2MU1201"; 	
        	svStreamID[20]= "RTDS_GEMU0201"; 	
        	svStreamID[21]= "RTDS_MU8MU1001"; 	
        	svStreamID[22]= "TEMPLATEPISV";//"TEMPLATEPISV/LLN0$SV$Smvcb"; 
        	svStreamID[23]= "TEMPLATE";   
        	svStreamID[24]= "TEST";       	
        	
       // }
        
        for(int i=0; i<25; i++)
        {  //dataFile.length
 
        		   dataFile[i] = new File("C:\\Users\\ysong\\workspace\\MU92E2InteroperabilityAnalyzer\\src\\"+ svStreamID[i] +"-ThreePhaseCurrentAndVoltage.csv");
        		   dataFileWriter[i]= new FileWriter(dataFile[i]);
    		       // if file doesnt exists, then create it
    		       if (!dataFile[i].exists()) {
    		       	dataFile[i].createNewFile();
    		       }          		   
    	   
        }
       	   
 	   
        //System.out.println("Parseing xml file!!!!!");      


 	    		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
 	    		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
 	    		Document doc = dBuilder.parse(xmlDataFileName);
 	    		doc.getDocumentElement().normalize();
 	
 	    		//System.out.println("Root element:" + doc.getDocumentElement().getNodeName());
 	    		
 	    		NodeList packetList = doc.getElementsByTagName("packet");	
 	            System.out.println("Total No. of Packets:"+packetList.getLength());
 	            
 	    		Node packetNode = null;
			    NodeList protoList =null;
			    int svPacketCounter=0;
			    
			    fileWriter.write("***** Interoperability Analysis of SendMSVMessage Testcase******\n");

	
				
 			    for (int i = 1; i <= packetList.getLength(); i++) 
 			    {		
 			        System.out.println("================================Packet No: "+i+"========================");
 			        
 			        packetNode = packetList.item(i);		

 			        try{
	  			        if(packetNode!=null)
	  			        	protoList = packetNode.getChildNodes(); /// nullPointerException??????????	

		  			        System.out.println("Total No. of SV protos:"+protoList.getLength());
		  			        //fileWriter.write("\n Total No. of protos:"+protoList.getLength());		
		  			        
 							 ethLanStatus=true;	    
 							 ethLanFieldStatus=true;

  							 vLanStatus=true;	    
  							 vLanFieldStatus=true;
  							 
 							 dstMacAddrStatus=true;	    
 							 dstMacAddrFieldStatus=true; 							 
  							 									  			        		
		  			        		if(protoList.getLength()>0)
		  			        		{
					  			        for(int j=0; j<protoList.getLength(); j++)
					  			        {
					  				        //System.out.println("============Proto No:"+j);		
					  			            Node pNode = protoList.item(j);
							            
					  			            
					  				        //Node.ELEMENT_NODE 				     
					  				        if (pNode.getNodeType() == Node.ELEMENT_NODE) 
					  				        {
					  				            
					  						      Element eElement = (Element) pNode;//proto  attributes

					  						      
					  			                    //System.out.println("\n********************SV Message Analyzer:***************************************");
					  			                    //fileWriter.write("\n********************SV Message Analysis:***************************************\n");


					  						      // Ethernet type: Ethernet II, 					  							 	 						  							 
						  			    			if(eElement.getAttribute("name").equals("eth") && (eElement.getAttribute("showname").startsWith("Ethernet II"))) // eth, Ethernet II
						  			    			 {     
							  			                NodeList nList = eElement.getElementsByTagName("field");
							  			                String nameOfField="";
							  			                String destMACAddress="";
							  			                String etherType="";
							  			                
						  			                    System.out.println("\n********SV Message Analyzer:**********");
						  			                    fileWriter.write("\n**********SV Message Analysis:*********\n");

							  			                
								  			  			for (int temp = 0; temp < nList.getLength(); temp++) 
								  			  			{
								  						   Node nNode = nList.item(temp);
								  						   if (nNode.getNodeType() == Node.ELEMENT_NODE) 
								  						   {
								  						     Element element = (Element) nNode; 
								  						     
								  			                 nameOfField=element.getAttribute("name");
								  			                 //System.out.println("nameOfField=:"+nameOfField);
								  			                 
								  			                 switch (nameOfField) 
								  			                 {
									  	                       // case 0:  nameOfField = "sv.appid";
									  	                 		case "eth.dst":
									  	                 		{						
										  			                 destMACAddress = element.getAttribute("show");
										  			                 System.out.println("DestMACAddress=:"+destMACAddress);		
										  			                 fileWriter.write("\n DestMACAddress=:"+destMACAddress);	
										  			                 
									  	                 			if(destMACAddress.startsWith("01:0c:cd:04") )
										  			                 {
										  			                	 System.out.println("DestMACAddress=: true");
										  			                	 dstMacAddrFieldStatus=true;
										  			                	 fileWriter.write("\n dstMacAddrFieldStatus:"+dstMacAddrFieldStatus);	                	 
										  			                	 dstMacAddrStatus = dstMacAddrFieldStatus && dstMacAddrStatus ;
										  			                	 fileWriter.write("\n dstMacAddrStatus:"+dstMacAddrStatus+"\n");
										  			                 }else
										  			                 {
										  			                	System.out.println("DestMACAddress=: false");
										  			                	dstMacAddrFieldStatus = false;	
										  			                	fileWriter.write("\n dstMacAddrFieldStatus:"+dstMacAddrFieldStatus);		  			                	
										  			                	dstMacAddrStatus = dstMacAddrStatus && dstMacAddrFieldStatus;
										  			                	fileWriter.write("\n dstMacAddrStatus:"+dstMacAddrStatus+"\n");
										  			                 }
									  	                 			break;
									  	                 		}
									  	                 		
									  	                 		case "eth.type":
									  	                 		{						
									  	                 			 etherType = element.getAttribute("value");
										  			                 System.out.println("EtherType=:"+etherType);		
										  			                 fileWriter.write("\n EtherType=:"+etherType+"\n");	
										  			                 
									  	                 			if(etherType.equals("88ba") || etherType.equals("88BA") || etherType.equals("8100") )
										  			                 {
										  			                	 System.out.println("EtherType=: true");
										  			                	 ethLanFieldStatus=true;
										  			                	 fileWriter.write(" ethLanFieldStatus: "+ethLanFieldStatus);										  			                	 
										  			                	 ethLanStatus = ethLanStatus && ethLanFieldStatus;
										  			                	 fileWriter.write("\n ethLanStatus=: "+ethLanStatus+"\n\n");
										  			                 } else
										  			                 {
										  			                	System.out.println(" EtherType=: false");
										  			                	ethLanFieldStatus= false;
										  			                	fileWriter.write(" ethLanFieldStatus: "+ ethLanFieldStatus);	
										  			                	ethLanStatus = ethLanStatus && ethLanFieldStatus;
										  			                	fileWriter.write("\n ethLanStatus:" +ethLanStatus+"\n\n");
										  			                 }
									  	                 			
									  	                 			/*
									  	                 			if(etherType.equals("8100") || etherType.equals("8100") )
										  			                 {
										  			                	 System.out.println("Virtuel EtherType=: true");
										  			                	 ethLanFieldStatus=true;
										  			                	 fileWriter.write(" ethVLanFieldStatus: "+ethLanFieldStatus);										  			                	 
										  			                	 ethLanStatus = ethLanStatus && ethLanFieldStatus;
										  			                	 fileWriter.write("\n ethVLanStatus=: "+ethLanStatus+"\n");
										  			                 }else
										  			                 {
										  			                	System.out.println(" Virtual EtherType=: false");
										  			                	ethLanFieldStatus= false;
										  			                	fileWriter.write(" ethVLanFieldStatus: "+ ethLanFieldStatus);	
										  			                	ethLanStatus = ethLanStatus && ethLanFieldStatus;
										  			                	fileWriter.write("\n ethVLanStatus:" +ethLanStatus+"\n");
										  			                 }	*/								  	                 			
									  	                 			
									  	                 			break;
									  	                 		}									  	                 		
									  	                 		
									 	                       default: nameOfField = "invalid";
								                                break;									  	                 		
								  			                 }					  						     
								  						   }
								  			  			}						  			    			 
						  			    			 }

					  			                						  			    			
						  			    			// VLAN  802.1Q Virtual LAN protocol 
						  			    			if(eElement.getAttribute("name").equals("vlan") && (eElement.getAttribute("showname").startsWith("802.1Q Virtual LAN"))) // eth, Ethernet II
						  			    			 {     
							  			                NodeList nList = eElement.getElementsByTagName("field");
							  			                String nameOfField="";
							  			                
							  			                String vlanTpID="";			  			             
							  			                String vlanPriority="";
							  			                String vlanID="";
							  			                String vlanCFI="";
							  			                String vlanDEI="";          

							  							 							  			                
								  			  			for (int temp = 0; temp < nList.getLength(); temp++) 
								  			  			{
								  						   Node nNode = nList.item(temp);
								  						   if (nNode.getNodeType() == Node.ELEMENT_NODE) 
								  						   {
								  						     Element element = (Element) nNode; 
								  						     
								  			                 nameOfField=element.getAttribute("name");
								  			                 //System.out.println("nameOfField=:"+nameOfField);
							  			                	 
								  			                 //fileWriter.write("\n Status: "+ status+"\n");
							  			                	 
								  			                 switch (nameOfField) 
								  			                 {
									  	                      
							  			                	   /*case "vlan.tpid": 
									  	                 		{						
									  	                 			 vlanTpID = element.getAttribute("show");
										  			                 System.out.println("vlanTpID=:"+vlanTpID);		
										  			                 fileWriter.write("\n vlanTpID=:"+vlanTpID);	
										  			                 
									  	                 			if(vlanTpID.equals("8100") )
										  			                 {
										  			                	 System.out.println("vlanTpID=: true");
										  			                	 fileWriter.write("\n vlanTpID=: true");
										  			                	 vLanFieldStatus=true;
										  			                	 fileWriter.write("\n vLanFieldStatus: "+ vLanFieldStatus);
										  			                	 vLanStatus = vLanStatus && vLanFieldStatus;										  			                	 
										  			                	 fileWriter.write("\n vLanStatus: "+ vLanStatus+"\n");
										  			                 }else
										  			                 {
										  			                	System.out.println("vlanTpID=: false");
										  			                	fileWriter.write("\n vlanTpID=: false");
										  			                	vLanFieldStatus= false;
										  			                	fileWriter.write("\n vLanFieldStatus: "+ vLanFieldStatus);
										  			                	vLanStatus = vLanStatus && vLanFieldStatus;										  			                	
										  			                	fileWriter.write("\n vLanStatus: "+vLanStatus+"\n");
										  			                 }
									  	                 			break;
									  	                 		}*/
									  	                 		
									  	                 		case "vlan.priority": // single char: 0~7, default =4
									  	                 		{						
									  	                 			 vlanPriority = element.getAttribute("show");
									  	                 			 int vLanP = Integer.parseInt(vlanPriority);
									  	                 			 
										  			                 System.out.println("vlanPriority=:"+vlanPriority);		
										  			                 fileWriter.write("\n vLanPriority=:"+vlanPriority);	
										  			                 
										  			                 System.out.println("vLanP=:"+vLanP);		
										  			                 fileWriter.write("\n vLanP=:"+vLanP);											  			                 
										  			                 
									  	                 			//if(vlanPriority.equals("4") )
										  			               if( vLanP >= 0 && vLanP <= 7 )	
										  			                 {
										  			                	 System.out.println("vlanPriority=: true");
										  			                	 fileWriter.write("\n vlanPriority=: true");
										  			                	 vLanFieldStatus=true;
										  			                	 fileWriter.write("\n vLanFieldStatus: "+ vLanFieldStatus);
										  			                	 vLanStatus = vLanStatus && vLanFieldStatus;					  			                	 
										  			                	 fileWriter.write("\n vLanStatus: "+ vLanStatus+"\n\n");
										  			                 }else
										  			                 {
										  			                	System.out.println("vlanPriority=: false");
										  			                	fileWriter.write("\n vlanPriority=: false");										  			                	
										  			                	vLanFieldStatus= false;
										  			                	fileWriter.write("\n vLanFieldStatus: "+ vLanFieldStatus);
										  			                	vLanStatus = vLanStatus && vLanFieldStatus;										  			                	
										  			                	fileWriter.write("\n vLanStatus: "+vLanStatus+"\n\n");
										  			                 }
									  	                 			break;
									  	                 		}

									  	                 		/*
									  	                		case "vlan.cfi":
									  	                 		{						
									  	                 			vlanCFI = element.getAttribute("show");
										  			                 System.out.println("vlanCFI=:"+vlanCFI);		
										  			                 fileWriter.write("\n vlanCFI=:"+vlanCFI);	
										  			                 
									  	                 			if(vlanCFI.equals("0"))
										  			                 {
										  			                	 System.out.println("vlanCFI=: true");
										  			                	 fileWriter.write("\n vlanCFI=: true");				  			                	 
										  			                	 fieldStatus=true;
										  			                	 fileWriter.write("\n vlanCFI: "+ fieldStatus);
										  			                	 status = status && fieldStatus;										  			                	 
										  			                	 fileWriter.write("\n Status: "+ status);
										  			                 }else
										  			                 {
										  			                	System.out.println("vlanCFI=: false");
										  			                	fileWriter.write("\n vlanCFI=: false");	
										  			                	fieldStatus= false;
										  			                	fileWriter.write("\n vlanCFI: "+ fieldStatus);
										  			                	status = status && fieldStatus;										  			                	
										  			                	fileWriter.write("\n Status: "+status);
										  			                 }
									  	                 			break;
									  	                 		}	*/									  	                 		
									  	                 		
									  	                		case "vlan.dei": //single character vlan.cfi
									  	                 		{						
									  	                 			 vlanDEI = element.getAttribute("show");
									  	                 			 //int vDEI = Integer.parseInt(vlanDEI);
									  	                 			
										  			                 System.out.println("vlanDEI=:"+vlanDEI);		
										  			                 fileWriter.write("\n vlanDEI=:"+vlanDEI);	
										  			                 
										  			                 //System.out.println("vEI=:"+vDEI);		
										  			                 //fileWriter.write("\n vDEI=:"+vDEI);											  			                 
										  			                 
									  	                 			if( vlanDEI.equals("0"))
										  			                 {
										  			                	 System.out.println("vlanDEI=: true");
										  			                	 fileWriter.write("\n vlanDEI=: true");				  			                	 
										  			                	 vLanFieldStatus=true;
										  			                	 fileWriter.write("\n vLanFieldStatus: "+ vLanFieldStatus);
										  			                	 vLanStatus = vLanStatus && vLanFieldStatus;										  			                	 
										  			                	 fileWriter.write("\n vLanStatus: "+ vLanStatus+"\n\n");
										  			                 }else
										  			                 {
										  			                	System.out.println("vlanDEI=: false");
										  			                	fileWriter.write("\n vlanDEI=: false");	
										  			                	vLanFieldStatus= false;
										  			                	fileWriter.write("\n fieldStatus: "+ vLanFieldStatus);
										  			                	vLanStatus = vLanStatus && vLanFieldStatus;										  			                	
										  			                	fileWriter.write("\n vLanStatus: "+vLanStatus+"\n\n");
										  			                 }
									  	                 			break;
									  	                 		}									  	                 		
									  	                 		
									  	                		case "vlan.id": // default  is 000, range: 000~FFF (0-4095)
									  	                 		{						
									  	                			vlanID = element.getAttribute("show");
									  	                			int vID = Integer.parseInt(vlanID);
									  	                			
										  			                 System.out.println("vlanID=:"+vlanID);		
										  			                 fileWriter.write("\n vlanID=:"+vlanID);	
										  			                 
										  			                 System.out.println("vID=:"+vID);		
										  			                 fileWriter.write("\n vID=:"+vID);											  			                 
										  			                 
									  	                 			if( vID >= 0 || vID <= 4095)
										  			                 {
										  			                	 System.out.println("vlanID=: true");
										  			                	 fileWriter.write("\n vlanID=: true");	
										  			                	 vLanFieldStatus=true;
										  			                	 fileWriter.write("\n vLanFieldStatus: "+ vLanFieldStatus);
										  			                	 vLanStatus = vLanStatus && vLanFieldStatus;										  			                	 
										  			                	 fileWriter.write("\n vLanStatus: "+ vLanStatus+"\n\n");
										  			                 }else
										  			                 {
										  			                	System.out.println("vlanID=: false");
										  			                	fileWriter.write("\n vlanID=: false");
										  			                	vLanFieldStatus= false;
										  			                	fileWriter.write("\n vLanFieldStatus: "+ vLanFieldStatus);
										  			                	vLanStatus = vLanStatus && vLanFieldStatus;										  			                	
										  			                	fileWriter.write("\n vLanStatus: "+vLanStatus+"\n\n");
										  			                 }
									  	                 			break;
									  	                 		}			
									  	                 		
									  	                 		
									  	                 		case "vlan.etype":
									  	                 		{						
									  	                 			 String vLanType = element.getAttribute("value");
										  			                 System.out.println("VLanType=:"+vLanType);		
										  			                 fileWriter.write("\n VLanType=:"+vLanType+"\n");	
										  			                 
									  	                 			if(vLanType.equals("88ba") || vLanType.equals("88BA") )
										  			                 {
										  			                	 System.out.println("VLanType=: true");
										  			                	 vLanFieldStatus=true;
										  			                	 fileWriter.write("vLanFieldStatus: "+vLanFieldStatus);										  			                	 
										  			                	 ethLanStatus = ethLanStatus && vLanFieldStatus;
										  			                	 fileWriter.write("\n vLanStatus=: "+vLanStatus+"\n\n");
										  			                 }else
										  			                 {
										  			                	System.out.println(" VLanType=: false");
										  			                	ethLanFieldStatus= false;
										  			                	fileWriter.write(" vLanFieldStatus: "+ vLanFieldStatus);	
										  			                	ethLanStatus = vLanStatus && vLanFieldStatus;
										  			                	fileWriter.write("\n VLanStatus:" +vLanStatus+"\n\n");
										  			                 }
									  	                 			break;
									  	                 		}
									  	                 		
									 	                       default: nameOfField = "invalid";
								                                break;									  	                 		
								  			                 }					  						     
								  						   }
								  			  			}						  			    			 
						  			    			 }						  			    			
						  			    			
						  			                //fileWriter.write("\n vLanStatus=:"+vLanStatus+"\n");	
						  			                //fileWriter.write("\n ethLanStatus=:"+ethLanStatus+"\n");						  			    			
						  			    			// 61850-9-2 SV protocol 
						  			    			if(eElement.getAttribute("name").equals("sv") && (eElement.getAttribute("showname").equals("IEC61850 Sampled Values"))) // 61850 SV proto
						  			    			 {                                                                                                   
								  			                System.out.println("protocol name:="+eElement.getAttribute("name"));    
								  			                System.out.println("show name:="+eElement.getAttribute("showname"));   
										  			      	System.out.println(" IEC 61850-9-2 SV Packet Counter=:"+svPacketCounter);
										  			      	
										  			        //fileWriter.write("\n IEC 61850-9-2 SV Packet Counter=:"+svPacketCounter+"\n");


								  			                	System.out.println("Initial position of SV message: " + eElement.getAttribute("pos") );
								  			                	//fileWriter.write("\n Initial position of SV message: " + eElement.getAttribute("pos") +"\n");
								  			                	

								  			      	    try{
								  			 	         
								  			                System.out.println("\n==========================parserMsgFields:====================================:");
								  			                NodeList nList = eElement.getElementsByTagName("field");

								  			                int totalNumOfFileds=nList.getLength();
								  			                System.out.println("Total number of fields=:"+nList.getLength());
								  			                
								              	  			globalSVIDStr="";
								              	  			globalSampleCounter=0;
								  			                	
								  			                status = parserAndAnalysisMultipleSVMessageFields(nList);
								  			                
								  			                fileWriter.write("\n status=:"+status);		
								  			                fileWriter.write("\n vLanStatus=:"+vLanStatus);	
								  			                fileWriter.write("\n ethLanStatus=:"+ethLanStatus+"\n\n");	
								  			                
								  			                status = status && vLanStatus && ethLanStatus;
								  			                
								  			                System.out.println("MU SV Message Analysis Result:=:"+status);
								  			                fileWriter.write("\n\n MU SV Message Analysis Result:=:"+status+"\n\n");								  			                	
								  			                
		
								  				        }catch(NullPointerException e){
								  				           System.out.println("NullPointerException:"+e);
								  				        }									  			                
								  			                
//===========================================================
									  			        svPacketCounter++;
								  			              
				  			                
						  			    			}  // end if							  			    								  			    								  			    			
					  				      	}// end if				  				        
				  			            }// end for
					  			        
		  			        //		}
							}//end if		
 			        }catch(NullPointerException e){
	  			           System.out.println("NullPointerException:"+e);
	  			        }					   
		        }//end for
 			    
 	         //fileWriter.flush();
 		 	 fileWriter.close();		   
 		 	 for(int i=0; i<25;i++){
		 	         dataFileWriter[i].close();	       
 			    }
 		     System.out.println("Done");
 		     
	  } catch (Exception e) {
		e.printStackTrace();
	  }   	 
		 return status;		 
	}	
	
	public byte[] hexToByte(String hexString) {
	     HexBinaryAdapter adapter = new HexBinaryAdapter();
	     byte[] bytes = adapter.unmarshal(hexString);
	     return bytes;
	}	
	
	public static Long hexStrToLong (String hexStr)
	{
		// First convert the Hex-number into a binary number:
		String bin = Long.toString(Long.parseLong(hexStr, 16), 2);
		//System.out.println("bin=:"+ bin);		
		// Now create the invert (make 1's to 0's and vice versa)
		String binCompl = bin.replace('0', 'X').replace('1', '0').replace('X', '1');				
		//System.out.println("binCompl=:"+ binCompl);		
		// Now parse it back to an integer, add 1 and make it negative:
		long result = (Long.parseLong(binCompl, 2) + 1) * -1;		
		
		//System.out.println("result=:"+ result);	
		return result;
	}
	
	public static double[] getThreePhaseCurrents(String hexStr){
		double currentDataValue[] = new double[4];
		Long dataValue[] = new Long[16];
		String dataStr[] = new String[16];
		double currentScaleFactor= 0.001; //IEC 61850-9-2LE
		
		for (int i=0; i< 7; i++ )
       {
			
       	try{
	        	dataStr[i]= hexStr.substring(i*8, i*8+8);
	            //System.out.println("i=:"+ i);
	            //System.out.println("dataStr=:"+ dataStr[i]);
	            if (dataStr[i].startsWith("F") ||dataStr[i].startsWith("f") ){
	            	//System.out.println("Negative number!");	            	           	                        	
	            	dataValue[i]= hexStrToLong(dataStr[i]);
	            }else{
	            	dataValue[i] = Long.parseLong(dataStr[i],16);
	            }
	            //System.out.println("dataValue=:"+dataValue[i]);
	            
	            if(i==0) 
	            {
	            	currentDataValue[i]= dataValue[i]*currentScaleFactor;	
	            	//System.out.println("i=:"+i);
	            	//System.out.println("Current=:"+ currentDataValue[i]);
	            }
	            if(i==2) 
	            {
	            	currentDataValue[i-1]= dataValue[i]*currentScaleFactor;	
	            	//System.out.println("i=:"+i);
	            	//System.out.println("Current=:"+ currentDataValue[i-1]);
	            }	            
	            if(i==4) 
	            {
	            	currentDataValue[i-2]= dataValue[i]*currentScaleFactor;	
	            	//System.out.println("i=:"+i);
	            	//System.out.println("Current=:"+ currentDataValue[i-2]);
	            }	
	            if(i==6) 
	            {
	            	currentDataValue[i-3]= dataValue[i]*currentScaleFactor;	
	            	//System.out.println("i=:"+i);
	            	//System.out.println("Current=:"+ currentDataValue[i-3]);
	            }	            
	            
       	}catch (java.lang.ArrayIndexOutOfBoundsException e)
       	{
       		System.out.println(e);
       	}catch(java.lang.NumberFormatException e){
       		System.out.println(e);
       	}
       	
       }		
		return currentDataValue;
	}
	
	
	
	public void getThreePhaseCurrentQuality(String hexStr){
		
		String cQualityStr[]= new String[4];
		
		//System.out.println("hexStr:"+hexStr);
		
		//System.out.println("Three phase current quality:");		
		
		cQualityStr=getThreePhaseCurrentQualityStr(hexStr);
		
		for(int i=0; i<cQualityStr.length; i++)
		{
			//System.out.println("i=:"+i);
			//System.out.println("cQualityStr[]:"+cQualityStr[i]);			
			
			getSinglePhaseCurrentQuality(cQualityStr[i]);
		}
	}
	
	
	public static String[] getThreePhaseCurrentQualityStr(String hexStr){
		
		String cQualityStr[] = new String[4];
		
		for (int i=0; i<8; i++ )
       {
			
       	try{
            
	            if(i==1) 
	            {
	            	cQualityStr[0]= hexStr.substring(i*8, i*8+8);  //8-16
	            	//System.out.println("i=:"+i);
	            	//System.out.println("CurrentQuality=:"+ cQualityStr[0]);
	            }
	            if(i==3) 
	            {
	            	cQualityStr[1]= hexStr.substring(i*8, i*8+8);//24-32
	            	//System.out.println("i=:"+i);
	            	//System.out.println("CurrentQuality=:"+ cQualityStr[1]);
	            }	            
	            if(i==5) 
	            {
	            	cQualityStr[2]= hexStr.substring(i*8, i*8+8); //40-48
	            	//System.out.println("i=:"+i);
	            	//System.out.println("CurrentQuality=:"+ cQualityStr[2]);
	            }	
	            if(i==7) 
	            {
	            	cQualityStr[3]= hexStr.substring(i*8, i*8+8); //56-64
	            	//System.out.println("i=:"+i);
	            	//System.out.println("CurrentQuality=:"+ cQualityStr[3]);
	            }	            
	            
       	}catch (java.lang.ArrayIndexOutOfBoundsException e)
       	{
       		System.out.println(e);
       	}catch(java.lang.NumberFormatException e){
       		System.out.println(e);
       	}
       	
       }		
		return cQualityStr;
	}
	
	public String hexToBin(String hex){
	    String bin = "";
	    String binFragment = "";
	    int iHex;
	    hex = hex.trim();
	    hex = hex.replaceFirst("0x", "");

	    for(int i = 0; i < hex.length(); i++){
	        iHex = Integer.parseInt(""+hex.charAt(i),16);
	        binFragment = Integer.toBinaryString(iHex);

	        while(binFragment.length() < 4){
	            binFragment = "0" + binFragment;
	        }
	        bin += binFragment;
	    }
	    return bin;
	}	
	
	public void getSinglePhaseCurrentQuality(String str){

		System.out.println("Signle phase current quality: "+str);
		
		str = new String(hexToBin(str)); // covert into binary string

		System.out.println("binStr=:"+ str);		
	
		
		String strQuality[] = new String[13];
		
		strQuality[0] = str.substring(0, 2);
		strQuality[1] = str.substring(2,3);		
		strQuality[2] = str.substring(3,4);				
		strQuality[3] = str.substring(4,5);
		strQuality[4] = str.substring(5,6);
		strQuality[5] = str.substring(6,7);
		strQuality[6] = str.substring(7,8);
		strQuality[7] = str.substring(8,9);		
		strQuality[8] = str.substring(9,10);
		strQuality[9] = str.substring(10,11);		
		strQuality[10] = str.substring(11,12);	
		strQuality[11] = str.substring(12,13);			
		strQuality[12] = str.substring(13,14);	
		
		/*for(int i=0; i<strQuality.length; i++){
			System.out.println("i=:"+i);
			System.out.println(""+strQuality[i]);
		}*/
			
		
		try{
			System.out.println("Signle phase current quality: ");			
			fileWriter.write("Signle phase current quality: ");			
			
			if (strQuality[0].equals("00"))
			{
				System.out.println(".... .... .... ..00: " +"validity or good");
				fileWriter.write("\n\n.... .... .... ..00: "+"validity or good");
			}
			else
			{
				System.out.println(".... .... .... ..01: "+"not validity or good");
				fileWriter.write("\n\n.... ..... .... ..01: " +"not validity or good");
			}
			
			
			if (strQuality[1].equals("0"))
			{
				System.out.println(".... .... .... .0..: "+"Not Overflow");	
				fileWriter.write("\n.... .... .... .0..: "+"Not Overflow");
			}
			else
			{
				System.out.println(".... .... .... .1..: "+"Verflow");	
				fileWriter.write("\n.... .... .... .1..: "+"overflow");
			}
			
			if (strQuality[2].equals("0"))
			{
				System.out.println(".... .... .... 0...: "+"In Range");	
				fileWriter.write("\n.... .... .... 0...: "+"In Range");	
			}
			else
			{
				System.out.println(".... .... .... 1...: "+"out of Range");	
				fileWriter.write("\n.... .... .... 1...: "+"Out of Range");
			}
			
			if (strQuality[3].equals("0"))
			{
				System.out.println(".... .... ...0 ....: "+"Good Reference");
				fileWriter.write("\n.... .... ...0 ....: "+"Good Reference");
			}
			else
			{
				System.out.println(".... .... ...1 ....: "+"Bad Reference");
				fileWriter.write("\n.... .... ...1 ....: "+"Bad Reference");
			}
			
			if (strQuality[4].equals("0"))
			{
				System.out.println(".... .... ..0. ....: "+"Not Oscillatory");
				fileWriter.write("\n.... .... ..0. ....: "+"Not Oscillatory");	
			}
			else
			{
				System.out.println(".... .... ..1. ....: "+"Oscillatory");	
				fileWriter.write("\n.... .... ..1. ....: "+"Oscillatory");
			}
			
			if (strQuality[5].equals("0"))
			{
				System.out.println(".... .... .0.. ....: "+"Not Failed");
				fileWriter.write("\n.... .... .0.. ....: "+"Not Failed");
			}
			else
			{
				System.out.println(".... .... .1.. ....: "+"Failed");	
				fileWriter.write("\n.... .... .1.. .... "+"Failed");	
			}
			
			if (strQuality[6].equals("0"))
			{
				System.out.println(".... .... 0... ....: "+"not old data");	
				fileWriter.write("\n.... .... 0... ....: "+"Not Old data");	
			}
			else
			{
				System.out.println(".... .... 1... ....: "+"Old data");	
				fileWriter.write("\n.... .... 1... ....: "+"Old data");	
			}
				
			
			if (strQuality[7].equals("0"))
			{
				System.out.println(".... ...0 .... ....: "+"Not Inconsistent");
				fileWriter.write("\n.... ...0 .... ....: "+"Not Inconsistent");
			}
			else
			{
				System.out.println(".... ...1 .... ....: "+"Inconsistent");
				fileWriter.write("\n.... ...1 .... ....: "+"Inconsistent");
			}
				
			
			if (strQuality[8].equals("0"))
			{
				System.out.println(".... ..0. .... ....: "+"Not Inaccurate");	
				fileWriter.write("\n.... ..0. .... ....: "+"Not Inaccurate");	
			}
			else
			{
				System.out.println(".... ..1. .... ....: "+"Inaccurate");
				fileWriter.write("\n.... ..1. .... ....: "+"Inaccurate");
			}
				
			
			if (strQuality[9].equals("0"))
			{
				System.out.println(".... .0.. .... ....: "+"Source = Process");		
				fileWriter.write("\n.... .0.. .... ....: "+"Source = Process");		
			}
			else
			{
				System.out.println(".... .1.. .... ....: "+"Source <> Process");
				fileWriter.write("\n.... .1.. .... ....: "+"Source <> Process");
			}
				
			
			if (strQuality[10].equals("0"))
			{
				System.out.println(".... 0... .... ....: "+"Not Test");	
				fileWriter.write("\n.... 0... .... ....: "+"Not Test");	
			}
			else
			{
				System.out.println(".... 1... .... ....: "+"Test");	
				fileWriter.write("\n.... 1... .... ....: "+"Test");	
			}
				
			
			if (strQuality[11].equals("0"))
			{
				System.out.println("...0 .... .... ....: "+"Not Operator Blocked");	
				fileWriter.write("\n...0 .... .... ....: "+"Not Operator Blocked");	
			}		
			else
			{
				System.out.println("...1 .... .... ....: "+"OperatorBlocked");
				fileWriter.write("\n...1 .... .... ....: "+"OperatorBlocked");
			}
				
			
			if (strQuality[12].equals("0"))
			{
				System.out.println("..0. .... .... ....:"+" Measured");	
				fileWriter.write("\n..0. .... .... ....:"+" Measured"+"\n");	
			}
			else
			{
				System.out.println("..1. .... .... ....:"+" Not mMasured");	
				fileWriter.write("\n..1. .... .... ....:"+" Not Measured"+"\n");	
			}
				
		 }catch(NullPointerException e){
		        System.out.println("NullPointerException:"+e);
		 }catch (IOException e)	 {
		 	System.out.println("IOException:"+e);
		 }			
}
	
	public void getSinglePhaseVoltageQuality(String str){

		System.out.println("Signle phase voltage quality: "+str);
		
		str = new String(hexToBin(str)); // covert into binary string		
		System.out.println("binStr=:"+str);
		
		
		String strQuality[] = new String[13];
		
		strQuality[0] = str.substring(0, 2);
		strQuality[1] = str.substring(2,3);		
		strQuality[2] = str.substring(3,4);				
		strQuality[3] = str.substring(4,5);
		strQuality[4] = str.substring(5,6);
		strQuality[5] = str.substring(6,7);
		strQuality[6] = str.substring(7,8);
		strQuality[7] = str.substring(8,9);		
		strQuality[8] = str.substring(9,10);
		strQuality[9] = str.substring(10,11);		
		strQuality[10] = str.substring(11,12);	
		strQuality[11] = str.substring(12,13);			
		strQuality[12] = str.substring(13,14);	
		
		/*for(int i=0; i<strQuality.length; i++){
			System.out.println("i=:"+i);
			System.out.println(""+strQuality[i]);
		}*/
			
		
		try{
			//System.out.println("Signle phase voltage quality: ");			
			//fileWriter.write("Signle phase voltage quality: ");					
			
			if (strQuality[0].equals("00"))
			{
				System.out.println(".... .... .... ..00: " +"validity or good");
				fileWriter.write("\n\n.... .... .... ..00: "+"validity or good");
			}
			else
			{
				System.out.println(".... .... .... ..01: "+"not validity or good");
				fileWriter.write("\n\n.... ..... .... ..01: " +"not validity or good");
			}
			
			
			if (strQuality[1].equals("0"))
			{
				System.out.println(".... .... .... .0..: "+"Not Overflow");	
				fileWriter.write("\n.... .... .... .0..: "+"Not Overflow");
			}
			else
			{
				System.out.println(".... .... .... .1..: "+"Verflow");	
				fileWriter.write("\n.... .... .... .1..: "+"overflow");
			}
			
			if (strQuality[2].equals("0"))
			{
				System.out.println(".... .... .... 0...: "+"In Range");	
				fileWriter.write("\n.... .... .... 0...: "+"In Range");	
			}
			else
			{
				System.out.println(".... .... .... 1...: "+"out of Range");	
				fileWriter.write("\n.... .... .... 1...: "+"Out of Range");
			}
			
			if (strQuality[3].equals("0"))
			{
				System.out.println(".... .... ...0 ....: "+"Good Reference");
				fileWriter.write("\n.... .... ...0 ....: "+"Good Reference");
			}
			else
			{
				System.out.println(".... .... ...1 ....: "+"Bad Reference");
				fileWriter.write("\n.... .... ...1 ....: "+"Bad Reference");
			}
			
			if (strQuality[4].equals("0"))
			{
				System.out.println(".... .... ..0. ....: "+"Not Oscillatory");
				fileWriter.write("\n.... .... ..0. ....: "+"Not Oscillatory");	
			}
			else
			{
				System.out.println(".... .... ..1. ....: "+"Oscillatory");	
				fileWriter.write("\n.... .... ..1. ....: "+"Oscillatory");
			}
			
			if (strQuality[5].equals("0"))
			{
				System.out.println(".... .... .0.. ....: "+"Not Failed");
				fileWriter.write("\n.... .... .0.. ....: "+"Not Failed");
			}
			else
			{
				System.out.println(".... .... .1.. ....: "+"Failed");	
				fileWriter.write("\n.... .... .1.. .... "+"Failed");	
			}
			
			if (strQuality[6].equals("0"))
			{
				System.out.println(".... .... 0... ....: "+"not old data");	
				fileWriter.write("\n.... .... 0... ....: "+"Not Old data");	
			}
			else
			{
				System.out.println(".... .... 1... ....: "+"Old data");	
				fileWriter.write("\n.... .... 1... ....: "+"Old data");	
			}
				
			
			if (strQuality[7].equals("0"))
			{
				System.out.println(".... ...0 .... ....: "+"Not Inconsistent");
				fileWriter.write("\n.... ...0 .... ....: "+"Not Inconsistent");
			}
			else
			{
				System.out.println(".... ...1 .... ....: "+"Inconsistent");
				fileWriter.write("\n.... ...1 .... ....: "+"Inconsistent");
			}
				
			
			if (strQuality[8].equals("0"))
			{
				System.out.println(".... ..0. .... ....: "+"Not Inaccurate");	
				fileWriter.write("\n.... ..0. .... ....: "+"Not Inaccurate");	
			}
			else
			{
				System.out.println(".... ..1. .... ....: "+"Inaccurate");
				fileWriter.write("\n.... ..1. .... ....: "+"Inaccurate");
			}
				
			
			if (strQuality[9].equals("0"))
			{
				System.out.println(".... .0.. .... ....: "+"Source = Process");		
				fileWriter.write("\n.... .0.. .... ....: "+"Source = Process");		
			}
			else
			{
				System.out.println(".... .1.. .... ....: "+"Source <> Process");
				fileWriter.write("\n.... .1.. .... ....: "+"Source <> Process");
			}
				
			
			if (strQuality[10].equals("0"))
			{
				System.out.println(".... 0... .... ....: "+"Not Test");	
				fileWriter.write("\n.... 0... .... ....: "+"Not Test");	
			}
			else
			{
				System.out.println(".... 1... .... ....: "+"Test");	
				fileWriter.write("\n.... 1... .... ....: "+"Test");	
			}
				
			
			if (strQuality[11].equals("0"))
			{
				System.out.println("...0 .... .... ....: "+"Not Operator Blocked");	
				fileWriter.write("\n...0 .... .... ....: "+"Not Operator Blocked");	
			}		
			else
			{
				System.out.println("...1 .... .... ....: "+"OperatorBlocked");
				fileWriter.write("\n...1 .... .... ....: "+"OperatorBlocked");
			}
				
			
			if (strQuality[12].equals("0"))
			{
				System.out.println("..0. .... .... ....:"+" Measured");	
				fileWriter.write("\n..0. .... .... ....:"+" Measured"+"\n");	
			}
			else
			{
				System.out.println("..1. .... .... ....:"+" Not mMasured");	
				fileWriter.write("\n..1. .... .... ....:"+" Not Measured"+"\n");	
			}
				
		 }catch(NullPointerException e){
		        System.out.println("NullPointerException:"+e);
		 }catch (IOException e)	 {
		 	System.out.println("IOException:"+e);
		 }			
}
	
	public void getThreePhaseVoltageQuality(String hexStr){
		
		String vQualityStr[]= new String[4];
		
		System.out.println("Three phase voltage quality:");	
		

		
		vQualityStr=getThreePhaseVoltageQualityStr(hexStr);

		for(int i=0; i<4; i++)
		{
			//System.out.println("i=:"+i);	
			//System.out.println("vQualityStr[]:"+vQualityStr[i]);				
			getSinglePhaseVoltageQuality(vQualityStr[i]);
		}
	}
	

		
	
	public static double[] getThreePhaseVoltages(String hexStr){
		double voltageDataValue[] = new double[4];
		Long dataValue[] = new Long[16];
		String dataStr[] =new String[16];
		double voltageScaleFactor= 0.01; //IEC 61850-9-2LE
		
		for (int i=0; i< 15; i++ )
       {
			
       	try{
	        	dataStr[i]= hexStr.substring(i*8, i*8+8);
	            //System.out.println("i=:"+ i);
	            //System.out.println("dataStr=:"+ dataStr[i]);
	            if (dataStr[i].startsWith("F") ||dataStr[i].startsWith("f") ){
	            	//System.out.println("Negative number!");	            	           	                        	
	            	dataValue[i]= hexStrToLong(dataStr[i]);
	            }else{
	            	dataValue[i] = Long.parseLong(dataStr[i],16);
	            }
	            //System.out.println("dataValue=:"+dataValue[i]);
	            
	            if(i==8) 
	            {
	            	voltageDataValue[i-8]= dataValue[i]*voltageScaleFactor;	
	            	//System.out.println("i=:"+i);
	            	//System.out.println("Current=:"+ voltageDataValue[i-8]);
	            }
	            if(i==10) 
	            {
	            	voltageDataValue[i-9]= dataValue[i]*voltageScaleFactor;	
	            	//System.out.println("i=:"+i);
	            	//System.out.println("Current=:"+ voltageDataValue[i-9]);
	            }	            
	            if(i==12) 
	            {
	            	voltageDataValue[i-10]= dataValue[i]*voltageScaleFactor;	
	            	//System.out.println("i=:"+i);
	            	//System.out.println("Current=:"+ voltageDataValue[i-10]);
	            }	
	            if(i==14) 
	            {
	            	voltageDataValue[i-11]= dataValue[i]*voltageScaleFactor;	
	            	//System.out.println("i=:"+i);
	            	//System.out.println("Current=:"+ voltageDataValue[i-11]);
	            }	            
	            
       	}catch (java.lang.ArrayIndexOutOfBoundsException e)
       	{
       		System.out.println(e);
       	}catch(java.lang.NumberFormatException e){
       		System.out.println(e);
       	}
       	
       }		
		return voltageDataValue;
	}	
	
	public static String[] getThreePhaseVoltageQualityStr(String hexStr){
		String vQualityStr[] = new String[4];
		
		for (int i=8; i< 16; i++ )
       {
			
       	try{
            
	            if(i==9) 
	            {
	            	vQualityStr[0]= hexStr.substring(i*8, i*8+8);  //72-81
	            	//System.out.println("i=:"+i);
	            	//System.out.println("VoltageQuality=:"+ vQualityStr[0]);
	            }
	            if(i==11) 
	            {
	            	vQualityStr[1]= hexStr.substring(i*8, i*8+8);//88-96
	            	//System.out.println("i=:"+i);
	            	//System.out.println("VoltageQuality=:"+ vQualityStr[1]);
	            }	            
	            if(i==13) 
	            {
	            	vQualityStr[2]= hexStr.substring(i*8, i*8+8); //104-112
	            	//System.out.println("i=:"+i);
	            	//System.out.println("VoltageQuality=:"+ vQualityStr[2]);
	            }	
	            if(i==15) 
	            {
	            	vQualityStr[3]= hexStr.substring(i*8, i*8+8); //120-128
	            	//System.out.println("i=:"+i);
	            	//System.out.println("VoltageQuality=:"+ vQualityStr[3]);
	            }	            
	            
       	}catch (java.lang.ArrayIndexOutOfBoundsException e)
       	{
       		System.out.println(e);
       	}catch(java.lang.NumberFormatException e){
       		System.out.println(e);
       	}
       	
       }		
		return vQualityStr;
	}	

	public void getCurrentValue(String hexStr){
		double current[] = new double[4];	
		current = getThreePhaseCurrents(hexStr);// get a array of three phase current
		try{
			
			System.out.println("\nThree phase currents:");
			fileWriter.write("\nThree phase currents:");	
			
		    System.out.println("Ia=: "+current[0]);	
	        fileWriter.write("\nIa=: "+current[0]);	    
	        
		    System.out.println("Ib=: "+current[1]);		
	        fileWriter.write("\nIb=: "+current[1]);	   	
	        
		    System.out.println("Ic=: "+current[2]);	
	        fileWriter.write("\nIc=: "+current[2]);	   	
	        
		    System.out.println("In=: "+current[3]);		
	        fileWriter.write("\nIn=: "+current[3]+"\n");	
	        

	        System.out.println("globalSVIDStr=:"+globalSVIDStr);
  	  		System.out.println("globalSampleCounter=:"+globalSampleCounter);
  	  		    
	  	 if(globalSVIDStr.equals("SIEMENSMU0101")) //i=0
	  		 	dataFileWriter[0].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("Vizimax4001")) //i=1
		  	    dataFileWriter[1].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("Vizimax4002")) //i=2
		  	    dataFileWriter[2].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);	  	 
	  	 
	  	 if(globalSVIDStr.equals("VIZ_TxA_MU2")) //i=3
	  		 	dataFileWriter[3].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("VIZ_INCA_MU02")) //i=4
		  	    dataFileWriter[4].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("SAM600MU0101")) //i=5
		  	    dataFileWriter[5].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);	  	 

	  	 if(globalSVIDStr.equals("MU320MU0101")) //i=6
		  	    dataFileWriter[6].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);	  	 
	  	 
	  	 if(globalSVIDStr.equals("4000")) //i=7
	  		 	dataFileWriter[7].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("SIE_A1_MU2")) //i=8
		  	    dataFileWriter[8].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("RTDS_VIZMU0301")) //i=9
		  	    dataFileWriter[9].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);	  	 
	  	 
	  	 if(globalSVIDStr.equals("RTDS_VIZMU0501")) //i=10
	  		 	dataFileWriter[10].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("RTDS_MU6MU0701")) //i=11
		  	    dataFileWriter[11].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("RTDS_MU8MU1001")) //i=12
		  	    dataFileWriter[12].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);	  	 

	  	 if(globalSVIDStr.equals("RTDSMU9MU1101")) //i=13
		  	    dataFileWriter[13].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);	  	 

	  	 if(globalSVIDStr.equals("RTDS_ABBMU0101")) //i=14
	  		 	dataFileWriter[14].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("RTDS_MU7MU0901")) //i=15
		  	    dataFileWriter[15].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("RTDS_SIEMMU0401")) //i=16
		  	    dataFileWriter[16].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);	  	 
	  	 
	  	 if(globalSVIDStr.equals("RTDS_SIEMMU0601")) //i=17
	  		 	dataFileWriter[17].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("RTDS_MU5MU0801")) //i=18
		  	    dataFileWriter[18].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("RTDSMU2MU1201")) //i=19
		  	    dataFileWriter[19].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);	  	 

	  	 if(globalSVIDStr.equals("RTDS_GEMU0201")) //i=20
		  	    dataFileWriter[20].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);	  	 
	  	
	  	 if(globalSVIDStr.equals("RTDS_MU8MU1001")) //i=21
	  		 	dataFileWriter[21].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("TEMPLATEPISV/LLN0$SV$Smvcb")) //i=22 "TEMPLATEPISV/LLN0$SV$Smvcb"
		  	    dataFileWriter[22].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 
	  	 if(globalSVIDStr.equals("TEMPLATE")) //i=23
		  	    dataFileWriter[23].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);	  	 
	  	 
	  	 if(globalSVIDStr.equals("TEST")) //i=24
	  		 	dataFileWriter[24].write("\n"+ globalSVIDStr + "," + globalSampleCounter + "," + current[0] + "," + current[1] + "," + current[2] + "," + current[3]);
	  	 

	  	 
	    }catch(NullPointerException e){
	           System.out.println("NullPointerException:"+e);
	    }catch (IOException e)	 {
	    	System.out.println("IOException:"+e);
	    }

	}
	

	public void getVoltageValue(String hexStr){
		double voltage[]= new double[4];	
		voltage = getThreePhaseVoltages(hexStr); // get a array of three phase voltage
		try{
			 
			System.out.println("\nThree phase voltages:");				
			fileWriter.write("\nThree phase voltages:");				
			
		    System.out.println("Va=: "+voltage[0]);	
	        fileWriter.write("\nVa=: "+voltage[0]);		  
	        
		    System.out.println("Vb=: "+voltage[1]);	
	        fileWriter.write("\nVb=: "+voltage[1]);		
	        
		    System.out.println("Vc=: "+voltage[2]);	
	        fileWriter.write("\nVc=: "+voltage[2]);		
	        
		    System.out.println("Vn=: "+voltage[3]);		 
	        fileWriter.write("\nVn=: "+voltage[3]+"\n");	
	        
	        //i==0
		  	 if(globalSVIDStr.equals("SIEMENSMU0101")) //svStreamID[0]
			        dataFileWriter[0].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
			  	 
		  	 // i=1
			 if(globalSVIDStr.equals("Vizimax4001"))  //svStreamID[1]
			        dataFileWriter[1].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
			  	 
			 //i==2
			 if(globalSVIDStr.equals("Vizimax4002"))  //svStreamID[2]
			        dataFileWriter[2].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );	  	
			  	 
		     //i==3
			 if(globalSVIDStr.equals("VIZ_TxA_MU2"))  //svStreamID[3]
				    dataFileWriter[3].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
				  	 
			 // i==4
			 if(globalSVIDStr.equals("VIZ_INCA_MU02"))  //svStreamID[4]
				    dataFileWriter[4].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
				  	 
			 //i==5
			 if(globalSVIDStr.equals("SAM600MU0101"))  //svStreamID[5]
				   dataFileWriter[5].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );	  	
			 
			 //i==6
			 if(globalSVIDStr.equals("MU320MU0101")) //svStreamID[6]
				   dataFileWriter[6].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );	  
			 
		        //i==7
			  	 if(globalSVIDStr.equals("4000")) //svStreamID[7]
				        dataFileWriter[7].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
				  	 
			  	 // i=8
				 if(globalSVIDStr.equals("SIE_A1_MU2"))  //svStreamID[8]
				        dataFileWriter[8].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
				  	 
				 //i==9
				 if(globalSVIDStr.equals("RTDS_VIZMU0301"))  //svStreamID[9]
				        dataFileWriter[9].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );	  	
				  	 
			     //i==10
				 if(globalSVIDStr.equals("RTDS_VIZMU0501"))  //svStreamID[10]
					    dataFileWriter[10].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
					  	 
				 // i==11
				 if(globalSVIDStr.equals("RTDS_MU6MU0701"))  //svStreamID[11]
					    dataFileWriter[11].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
					  	 
				 //i==12
				 if(globalSVIDStr.equals("RTDS_MU8MU1001"))  //svStreamID[12]
					   dataFileWriter[12].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );	  	
				 
				 //i==13
				 if(globalSVIDStr.equals("RTDSMU9MU1101")) //svStreamID[13]
					   dataFileWriter[13].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );	
				 
			     //i==14
			  	 if(globalSVIDStr.equals("RTDS_ABBMU0101")) //svStreamID[14]
				        dataFileWriter[14].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
				  	 
			  	 // i=15
				 if(globalSVIDStr.equals("RTDS_MU7MU0901"))  //svStreamID[15]
				        dataFileWriter[15].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
				  	 
				 //i==16
				 if(globalSVIDStr.equals("RTDS_SIEMMU0401"))  //svStreamID[16]
				        dataFileWriter[16].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );	  	
				  	 
			     //i==17
				 if(globalSVIDStr.equals("RTDS_SIEMMU0601"))  //svStreamID[17]
					    dataFileWriter[17].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
					  	 
				 // i==18
				 if(globalSVIDStr.equals("RTDS_MU5MU0801"))  //svStreamID[18]
					    dataFileWriter[18].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
					  	 
				 //i==19
				 if(globalSVIDStr.equals("RTDSMU2MU1201"))  //svStreamID[19]
					   dataFileWriter[19].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );	  	
				 
				 //i==20
				 if(globalSVIDStr.equals("RTDS_GEMU0201")) //svStreamID[20]
					   dataFileWriter[20].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );					 

			     //i==21
				 if(globalSVIDStr.equals("RTDS_MU8MU1001"))  //svStreamID[21]
					    dataFileWriter[21].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
					  	 
				 // i==22
				 if(globalSVIDStr.equals("TEMPLATEPISV/LLN0$SV$Smvcb"))  //svStreamID[22]
					    dataFileWriter[22].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );
					  	 
				 //i==23
				 if(globalSVIDStr.equals("TEMPLATE"))  //svStreamID[23]
					   dataFileWriter[23].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );	  	
				 
				 //i==24
				 if(globalSVIDStr.equals("TEST")) //svStreamID[24]
					   dataFileWriter[24].write("," + voltage[0] + "," + voltage[1] + "," + voltage[2] + "," + voltage[3] );				 
				 
	    }catch(NullPointerException e){
	           System.out.println("NullPointerException:"+e);
	    }catch (IOException e)	 {
	    	System.out.println("IOException:"+e);
	    }	    
	}	

	// pattern match
public boolean patternMatch(String regexChar, String patternStr){
		  boolean result;
		  
		  Pattern pattern = Pattern.compile(regexChar);
		  Matcher matcher = pattern.matcher(patternStr);
		  result = matcher.find();
		  return result;
}	
	
public boolean parserAndAnalysisMultipleSVMessageFields(NodeList nList)
{


	 
	 try{
		         
	            //System.out.println("\n==========================parserSVMsgFields:====================================:");
	            //NodeList nList = eElement.getElementsByTagName("field");
	
	            int totalNumOfFiled=nList.getLength();
	            System.out.println("Total Num. of Field=:"+nList.getLength());
	            //fileWriter.write("Num. of Fields of Mulitple ASDUs of SV  Message=:"+nList.getLength()+"\n");     
	            
	            int NoOfField=0;
	            String nameOfField="";
	            
                int posInt=0;// dynamic change
                String posStr=""; //dynamic change
                
                int positionInt=0;
                String positionStr="";
                
                int lengthInt=0;
                String lengthStr="";	      
                
        		int numOfASDU=0;  //   numOfASDu= 8
        		int svIDlength=0; //   10<= svIDLength <=34
        		int svLength=0;   //   99<= svLength <= 973
        		int svIDCounter=0;
        		
        		
        		status=true;	    
        		fieldStatus=true;
        		
			for (int temp = 0; temp < nList.getLength(); temp++) {
			   Node nNode = nList.item(temp);
			   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
			     Element element = (Element) nNode; 
	                 //System.out.println("\n========================= Field:====================================:");  
	                 /*
	                 System.out.println("No. of Filed in Header frame: "+temp);
	                 System.out.println("Field Name=:"+element.getAttribute("name"));                           
	                 System.out.println("showname=:"+element.getAttribute("showname"));  
	                 System.out.println("length=:"+element.getAttribute("length"));      
	                 System.out.println("pos=:"+element.getAttribute("pos"));  
	                 System.out.println("show=:"+element.getAttribute("show"));      
	                 System.out.println("value=:"+element.getAttribute("value"));                      
	                 System.out.println("");                         
	
	                 System.out.println("\n=================");     
	                 */
	                 //System.out.println("temp=:"+temp);  
	                 NoOfField=temp;
	                // if(NoOfField<=11)
	                // {
	                 System.out.println("\nNo. of Field=:"+NoOfField);  
	                // fileWriter.write("\nNo. of Field=:"+NoOfField+"\n");
	                // }
	                 //fileWriter.write("\n");          
	                 nameOfField=element.getAttribute("name");
	                 System.out.println("nameOfField=:"+nameOfField);
	                 
	                 
	                  //switch (NoOfField) {
	                 switch (nameOfField) {
	                       // case 0:  nameOfField = "sv.appid";
	                 		case "sv.appid": // 0x4000 to 0x7FFF
	                 			
	                 				fileWriter.write(" Tag=:"+nameOfField+"\n");
	                 				
		             	  			posStr=element.getAttribute("pos");//"14";
		              	  			System.out.println("Pos=:"+posStr);		
		              	  			//fileWriter.write(" Pos=:"+posStr+"\n");			              	  			
		              	  		
		              	  			posInt=Integer.parseInt(posStr);	
		              	  			
		              	  			lengthStr=element.getAttribute("size");//"2";
		              	  			lengthInt=Integer.parseInt(lengthStr);
		              	  			
		              	  			System.out.println("length=:"+lengthStr);	
		              	  			fileWriter.write(" length=:"+lengthStr+"\n");			              	  			
		              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");			              	  			
		              	  			
		              	  			
		              	  			int appidDec= Integer.parseInt(element.getAttribute("value"), 16);  ;//hexStrToLong( element.getAttribute("value"));
		              	  			System.out.println("appidDec=:"+appidDec);

		              	  			//fileWriter.write(" appidDec=:"+appidDec+ "\n");				              	  			
	                 			
	                               //if(element.getAttribute("name").equals("sv.appid") && element.getAttribute("showname").startsWith("APPID:") && element.getAttribute("size").equals("2") && element.getAttribute("pos").equals("14") )
	                 			if(element.getAttribute("size").equals("2")  && (appidDec >= 16380) && (appidDec <= 32767) )	  //                              
	                 				{
	                                    fileWriter.write(" sv.appid: "+"true"+"\n");
	                                    System.out.println("sv.appid: "+"true");      
	                                    //System.out.println("sv.appid: "+element.getAttribute("name"));     
	                                    System.out.println("sv.appId: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" sv.appId: "+element.getAttribute("value")+"\n");    
	                                    fieldStatus=true;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);;
	                                    System.out.println("status=:"+status);;
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }
	                               else
	                               {
	                                    fileWriter.write(" sv.appid: "+"false"+"\n");
	                                    System.out.println("sv.appid: "+"false");     
	                                    //System.out.println("sv.appid: "+element.getAttribute("name"));         
	                                    System.out.println("sv.appId: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" sv.appId: "+element.getAttribute("value")+"\n");     
	                                    fieldStatus=false;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);;
	                                    System.out.println("status=:"+status);;	 
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }                                      

                	  			positionInt = posInt+lengthInt;
                	  			positionStr = Integer.toString(positionInt);
                	  			System.out.println("\nNext position=:"+positionStr);
                	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");  
                	  			
	                                break;                           
	                       // case 1:  nameOfField = "sv.length";
	                 		case "sv.length":    
	                 			
                 					fileWriter.write(" Tag=:"+nameOfField+"\n");	                 			
		              	  			posStr=element.getAttribute("pos");//"16";
		              	  			posInt=Integer.parseInt(posStr);	
		              	  			
		              	  			lengthStr=element.getAttribute("size");//"2";
		              	  			lengthInt=Integer.parseInt(lengthStr);
		              	  			
		              	  			System.out.println("Pos=:"+posStr);
		              	  			//fileWriter.write(" Pos=:"+posStr+"\n");			              	  			
		              	  			System.out.println("length=:"+lengthStr);	 
		              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
		              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
		              	  			int lengthDec= Integer.parseInt(element.getAttribute("value"), 16);
		              	  			
	                               // if(element.getAttribute("name").equals("sv.length") && element.getAttribute("size").equals("2") && element.getAttribute("pos").equals("16") )
	                 			if(element.getAttribute("size").equals("2") && lengthDec<= 943 ) // value= 109 - 943   && element.getAttribute("pos").equals(positionStr)
	                               {
	                                    fileWriter.write(" sv.length: "+"true"+"\n");
	                                    System.out.println("sv.length: "+"true");       
	                                    //System.out.println("sv.length: "+element.getAttribute("name"));
	                                    System.out.println("sv.length: "+element.getAttribute("show"));   
	                                    //fileWriter.write(" sv.length: "+element.getAttribute("show")+"\n");   
                                        svLength= Integer.parseInt(element.getAttribute("value"),16); // hex string to int
                                        System.out.println("svLength=:"+svLength);	                                    
	                                    fieldStatus=true;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);;
	                                    System.out.println("status=:"+status);;    
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }
	                               else
	                               {
	                                    fileWriter.write(" sv.length: "+"false"+"\n");
	                                    System.out.println("sv.length: "+"false");            
	                                    //System.out.println("sv.length: "+element.getAttribute("name"));    
	                                    System.out.println("sv.length: "+element.getAttribute("show"));   
	                                    //fileWriter.write(" sv.length: "+element.getAttribute("show")+"\n");       
	                                    fieldStatus= false;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);;
	                                    System.out.println("status=:"+status);	
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }  
	                 			
                	  			positionInt = posInt+lengthInt;
                	  			positionStr = Integer.toString(positionInt);	   
                	  			System.out.println("\nNext position=:"+positionStr);                    	  			
                	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");                  	  			
	                                break;
	                                
	                       // case 2:  nameOfField = "sv.reserve1"; //0x4000 to 0x7FFF 
	                 		case "sv.reserve1":  
             						fileWriter.write(" Tag=:"+nameOfField+"\n");	                 			
             						                 		
		              	  			posStr=element.getAttribute("pos");//"18";
		              	  			posInt=Integer.parseInt(posStr);
		              	  			
		              	  			lengthStr=element.getAttribute("size");//"2";
		              	  			lengthInt=Integer.parseInt(lengthStr);
		              	  			
		              	  			int reserved1= Integer.parseInt(element.getAttribute("value"));
		              	  			
		              	  			System.out.println("Pos=:"+posStr);
		              	  			//fileWriter.write(" Pos=:"+posStr+"\n");			              	  			
		              	  			System.out.println("length=:"+lengthStr);		                    	  
		              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
		              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
		              	  			
		              	  			
		              	  			//	((reserved1 <= 32767) && (reserved1 >= 16384))  (element.getAttribute("value").equals("0000") || element.getAttribute("value").equals("8000"))
		              	  				
	                               // if(element.getAttribute("name").equals("sv.reserve1") && element.getAttribute("size").equals("2") && element.getAttribute("pos").equals("18") )
	                 			if(element.getAttribute("size").equals("2") && ((element.getAttribute("value").equals("0000") || element.getAttribute("value").equals("8000")) || ((reserved1 <= 32767) && (reserved1 >= 16384))))  //&& element.getAttribute("pos").equals(positionStr)
	                 				{
	                                    fileWriter.write(" sv.reserve1: "+"true"+"\n");
	                                    System.out.println("sv.reserve1: "+"true");          
	                                    //System.out.println("sv.reserve1: "+element.getAttribute("name"));  
	                                    System.out.println("sv.reserve1: "+element.getAttribute("value"));   
	                                    
	                                   // fileWriter.write(" sv.reserve1: "+element.getAttribute("value")+"\n");    
	                                    //fileWriter.write(" positionStr=: "+positionStr);
	                                   // fileWriter.write(" posStr=: "+element.getAttribute("pos")+"\n");
	                                    
	                                    fieldStatus=true;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);;
	                                    System.out.println("status=:"+status);;  
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }
	                               else
	                               {
	                                    fileWriter.write(" sv.reserve1: "+"false"+"\n");
	                                    System.out.println("sv.reserve1: "+"false");     
	                                    //System.out.println("sv.reserve1: "+element.getAttribute("name"));       
	                                    System.out.println("sv.reserve1: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" sv.reserve1: "+element.getAttribute("value")+"\n");   
	                                   // fileWriter.write(" positionStr=: "+positionStr);	 
	                                   // fileWriter.write(" posStr=: "+element.getAttribute("pos")+"\n");
	                                    
	                                    fieldStatus= false;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    System.out.println("status=:"+status);  
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }   
	                 			
                	  			positionInt = posInt+lengthInt;
                	  			positionStr = Integer.toString(positionInt);
                	  			System.out.println("\nNext position=:"+positionStr);                  	  			
                	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");                  	  				                 			
	                                break;
	                      // case 3:  nameOfField = "sv.reserve2";
	                 		case "sv.reserve2":
         							fileWriter.write(" Tag=:"+nameOfField+"\n");	                 			
		                 			
	                 			
		              	  			posStr=element.getAttribute("pos");//"20";
		              	  			posInt=Integer.parseInt(posStr);	
		              	  			
		              	  			lengthStr=element.getAttribute("size");//"2";
		              	  			lengthInt=Integer.parseInt(lengthStr);
		              	  			System.out.println("Pos=:"+posStr);
		              	  			//fileWriter.write(" Pos=:"+posStr+"\n");			              	  			
		              	  			System.out.println("length=:"+lengthStr);
		              	  			fileWriter.write(" length=:"+lengthStr+"\n");		                 			
		              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
		              	  			
	                              // if(element.getAttribute("name").equals("sv.reserve2") && element.getAttribute("size").equals("2") && element.getAttribute("pos").equals("20") )
	                 			if(element.getAttribute("size").equals("2") && element.getAttribute("value").equals("0000") ) //&& element.getAttribute("pos").equals(positionStr)
	                               {
	                                    fileWriter.write(" sv.reserve2: "+"true"+"\n");
	                                    System.out.println("sv.reserve2: "+"true");    
	                                    //System.out.println("sv.reserve2: "+element.getAttribute("name"));     
	                                    System.out.println("sv.reserve2: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" sv.reserve2: "+element.getAttribute("value")+"\n");   
	                                    
	                                   // fileWriter.write(" positionStr=: "+positionStr);	 
	                                   // fileWriter.write(" posStr=: "+element.getAttribute("pos")+"\n");
	                                    
	                                    fieldStatus=true;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);;
	                                    System.out.println("status=:"+status);;  	  
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }
	                               else
	                               {
	                                    fileWriter.write(" sv.reserve2: "+"false"+"\n");
	                                    System.out.println("sv.reserve2: "+"false");     
	                                   // System.out.println("sv.reserve2: "+element.getAttribute("name"));     
	                                    System.out.println("sv.reserve2: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" sv.reserve2: "+element.getAttribute("value")+"\n");   
	                                    
	                                   // fileWriter.write(" positionStr=: "+positionStr);	 
	                                   // fileWriter.write(" posStr=: "+element.getAttribute("pos")+"\n");
	                                    
	                                    fieldStatus=false;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);;
	                                    System.out.println("status=:"+status);;  	  
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }      
	                	  			positionInt = posInt+lengthInt+2;
	                	  			positionStr = Integer.toString(positionInt);			                    	  		
	                	  			System.out.println("\nNext position=:"+positionStr);               	  			
                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");  	                	  			               			
	                                break;
	                      // case 4:  nameOfField = "sv.savPdu_element";
	                 		case "sv.savPdu_element":  
         							fileWriter.write(" Tag=:"+nameOfField+"\n");	                 			
		                 			
	                 			
		              	  			posStr=element.getAttribute("pos");//"26", not 24; ?????
		              	  			posInt=Integer.parseInt(posStr);	
		              	  			
		              	  			lengthStr=element.getAttribute("size");//"99";
		              	  			lengthInt=Integer.parseInt(lengthStr);
		              	  			System.out.println("Pos=:"+posStr);
		              	  			//fileWriter.write(" Pos=:"+posStr+"\n");			              	  			
		              	  			System.out.println("length=:"+lengthStr);	
		              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
		              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
		              	  			
	                 			//element.getAttribute("showname").equals("savPdu") &&
		              	  			System.out.println("showname:savPdu "+element.getAttribute("showname"));
	                               // if(element.getAttribute("name").equals("sv.savPdu_element") && element.getAttribute("showname").equals("savPdu") )//&& element.getAttribute("pos").equals("24") )
	                 			if( element.getAttribute("showname").equals("savPdu") ) //&& element.getAttribute("pos").equals(positionStr)
	                               {
	                                    fileWriter.write(" sv.savPdu_element: "+"true"+"\n"); 
	                                    System.out.println("sv.savPdu_element: "+"true");         
	                                    //System.out.println("sv.savPdu_element: "+element.getAttribute("name"));     
	                                    System.out.println("sv.savPdu_element: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" sv.savPdu_element: "+element.getAttribute("value")+"\n");   
	                                   //fileWriter.write(" positionStr=:"+positionStr+"\n");
	                                   // fileWriter.write(" posStr=: "+element.getAttribute("pos")+"\n");
	                                    		
	                                    fieldStatus=true;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    System.out.println("status=:"+status); 	  
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }
	                               else
	                               {
	                                    fileWriter.write(" sv.savPdu_element: "+"false"+"\n");
	                                    System.out.println("sv.savPdu_element: "+"false");         
	                                    //System.out.println("sv.savPdu_element: "+element.getAttribute("name"));   
	                                    System.out.println("sv.savPdu_element: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" sv.savPdu_element: "+element.getAttribute("value")+"\n"); 
	                                    //fileWriter.write(" positionStr=: "+positionStr+"\n");	   
	                                    //fileWriter.write(" posStr=: "+element.getAttribute("pos")+"\n");
	                                    
	                                    fieldStatus=false;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    System.out.println("status=:"+status); 	    
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               } 
	                	  			positionInt = posInt+2;
	                	  			positionStr = Integer.toString(positionInt);	
	                	  			System.out.println("\nNext position=:"+positionStr);                    	  			
                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");  	                	  			                 			
	                                break;
	                      // case 5:  nameOfField = "sv.noASDU";
	                 		case   "sv.noASDU":        
         								fileWriter.write(" Tag=:"+nameOfField+"\n");	                 			
		                 			

			              	  			posStr=element.getAttribute("pos");//"28", not 26;??????
			              	  			posInt=Integer.parseInt(posStr);	
			              	  			
			              	  			lengthStr=element.getAttribute("size");//"1";
			              	  			lengthInt=Integer.parseInt(lengthStr);
			              	  			System.out.println("Pos=:"+posStr);
			              	  			//fileWriter.write(" Pos=:"+posStr+"\n");				              	  			
			              	  			System.out.println("length=:"+lengthStr);	   
			              	  			fileWriter.write(" length=:"+lengthStr+"\n");	      	  				                 			
			              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
			              	  			
			              	  			if(element.getAttribute("show").equals("8")){
			  			                	System.out.println("MU SV Message (Muliple Data Frame) Analysis");
			  			                	fileWriter.write(" MU SV Message (Muliple Data Frame) Analysis:"+"\n");			              	  				
			              	  			}else
			              	  			{
			  			                	System.out.println("MU SV Message (Single Data Frame) Analysis");
			  			                	fileWriter.write(" MU SV Message (Single Data Frame) Analysis:"+"\n");			              	  			
			              	  			}
								  			                	
			              	  			
			              	  			
	                               // if(element.getAttribute("name").equals("sv.noASDU") && element.getAttribute("showname").startsWith("noASDU:"))// && element.getAttribute("pos").equals("26") )
	                 			if( element.getAttribute("size").equals("1") && (element.getAttribute("show").equals("8") || element.getAttribute("show").equals("1")) )// && element.getAttribute("pos").equals(positionStr)
	                               {
	                                    fileWriter.write(" sv.noASDU: "+"true"+"\n");
	                                    System.out.println(" sv.noASDU: "+"true");     
	                                    //System.out.println("sv.noASDU: "+element.getAttribute("name"));      
	                                    System.out.println("sv.noASDU: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" sv.noASDU: "+element.getAttribute("show")+"\n");   
                                        numOfASDU= Integer.parseInt(element.getAttribute("show"));
                                        System.out.println("numOfASDU=:"+numOfASDU);                      
	                                    fieldStatus=true;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    System.out.println("status=:"+status);  	
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }
	                               else
	                               {
	                                    fileWriter.write(" sv.noASDU: "+"false"+"\n");
	                                    System.out.println("sv.noASDU: "+"false");           
	                                    //System.out.println("sv.noASDU: "+element.getAttribute("name"));      
	                                    System.out.println("sv.noASDU: "+element.getAttribute("show"));   
	                                    //fileWriter.write(" sv.noASDU: "+element.getAttribute("show")+"\n");      
	                                    fieldStatus=false;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    System.out.println("status=:"+status); 	   
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }   
	                	  			positionInt = posInt+lengthInt+2; ///???? 4, not 2 
	                	  			positionStr = Integer.toString(positionInt);	
	                	  			System.out.println("\nNext position=:"+positionStr);                 	  			
                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");  	                	  			                			
	                                break;
	                      // case 6:  nameOfField = "sv.seqASDU";//sv.seqASDU
	                 		case "sv.seqASDU":   
         							fileWriter.write(" Tag=:"+nameOfField+"\n");	                 			
		                 			
	                 			
		              	  			posStr=element.getAttribute("pos");//"33"; not "29"
		              	  			posInt=Integer.parseInt(posStr);	
		              	  			
		              	  			lengthStr=element.getAttribute("size");//"94";
		              	  			lengthInt=Integer.parseInt(lengthStr);
		              	  			System.out.println("Pos=:"+posStr);
		              	  			//fileWriter.write(" Pos=:"+posStr+"\n");			              	  			
		              	  			System.out.println("length=:"+lengthStr);		                    	  
		              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
		              	  			fileWriter.write(" value=:"+element.getAttribute("show")+"\n");		
		              	  			
		              	  			
	                               // if(element.getAttribute("name").equals("sv.seqASDU") && element.getAttribute("showname").startsWith("seqASDU:"))// && element.getAttribute("pos").equals("29") )
	                 			if( element.getAttribute("showname").startsWith("seqASDU:"))  //element.getAttribute("pos").equals(positionStr)
	                               {
	                                    fileWriter.write(" sv.seqASDU: "+"true"+"\n");
	                                    System.out.println("sv.seqASDU: "+"true");      
	                                    //System.out.println("sv.seqASDU: "+element.getAttribute("name"));    
	                                    System.out.println("sv.seqASDU "+element.getAttribute("value"));   
	                                    //fileWriter.write(" sv.seqASDU: "+element.getAttribute("value")+"\n");     
	                                    fieldStatus=true;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    System.out.println("status=:"+status);	               
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }
	                               else
	                               {
	                                    fileWriter.write(" sv.seqASDU: "+"false"+"\n");
	                                    System.out.println("sv.seqASDU: "+"false");    
	                                    //System.out.println("synphasor.timeqal: "+element.getAttribute("name"));    
	                                    System.out.println("sv.seqASDU: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" sv.seqASDU: "+element.getAttribute("value")+"\n");     
	                                    fieldStatus=false;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    System.out.println("status=:"+status); 	    
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }     
                	  			positionInt = posInt+0;
                	  			positionStr = Integer.toString(positionInt);	
                	  			System.out.println("\nNext position=:"+positionStr);	                    	  			
                	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");                 	  			                			
	                                break;
	                                
	                                
	                       //case 7: case 13: case 19: case 25: case 31: case 37: case 43: case 49:
	                 		case    "sv.ASDU_element":      
	                       		{
             						fileWriter.write(" Tag=:"+nameOfField+"\n");	                 			
			                 			
	                       			
		              	  			posStr=element.getAttribute("pos");//"29";
		              	  			posInt=Integer.parseInt(posStr);	
		              	  			
		              	  			lengthStr=element.getAttribute("size");//"94";
		              	  			lengthInt=Integer.parseInt(lengthStr);
		              	  			System.out.println("Pos=:"+posStr);
		              	  			//fileWriter.write(" Pos=:"+posStr+"\n");			              	  			
		              	  			System.out.println("length=:"+lengthStr);		                    	  
		              	  			fileWriter.write(" length=:"+lengthStr+"\n");	                    	  	                       			

		               	  			System.out.println("svIDCounter(256):="+svIDCounter);
		               	  			fileWriter.write(" svIDCounter=:"+svIDCounter+"\n");
		              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
		              	  				               	  			
	
	                	  			
		               	  			svIDCounter++;	
		               	  			
                                    //fileWriter.write("\n ********** MU SV Single ASDU ********* "+"\n");	                       			
	                    	   		//nameOfField = "sv.ASDU_element";
	                               // if(element.getAttribute("name").equals("sv.ASDU_element") && element.getAttribute("showname").equals("ASDU"))// && element.getAttribute("pos").equals("29") )
                                    if(element.getAttribute("showname").equals("ASDU")  )  //&& element.getAttribute("pos").equals(posStr)
	                               {
	                                    fileWriter.write(" sv.ASDU_element: "+"true"+"\n");
	                                    System.out.println("sv.ASDU_element: "+"true");    
	                                    //System.out.println("sv.ASDU_element: "+element.getAttribute("name"));        
	                                    System.out.println("ASDU_element: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" ASDU_element: "+element.getAttribute("value")+"\n");  
	                                    
	                                   // fileWriter.write(" positionStr=: "+positionStr);	 
	                                   // fileWriter.write(" posStr=: "+element.getAttribute("pos")+"\n");
	                                    
	                                    fieldStatus=true;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    //fileWriter.write("fieldStatus=:"+fieldStatus+"\n");	 
	                                    System.out.println("status=:"+status); 	    
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               }
	                               else
	                               {
	                                    fileWriter.write(" sv.ASDU_element: "+"false"+"\n");
	                                    System.out.println("sv.ASDU_element: "+"false");             
	                                    //System.out.println("sv.ASDU_element: "+element.getAttribute("name"));      
	                                    System.out.println("ASDU_element: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" ASDU_element: "+element.getAttribute("value")+"\n");   
	                                    
	                                    //fileWriter.write("positionStr=: "+positionStr);	 
	                                    //fileWriter.write(" posStr=: "+element.getAttribute("pos")+"\n");	                                    
	                                    
	                                    fieldStatus=false;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    //fileWriter.write("fieldStatus=:"+fieldStatus+"\n");	 
	                                    System.out.println("status=:"+status);	  
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                               } 
                                    
                	  			positionInt = posInt+4;//29+4=33
                	  			positionStr = Integer.toString(positionInt);  
                	  			System.out.println("\nNext position=:"+positionStr);
                	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");                  	  			                                    
	                       		}
         		
	                                break;
	                                
	                      // case 8: case 14: case 20: case 26: case 32: case 38: case 44: case 50:
	                 		case   "sv.svID":        
	                       {
        						fileWriter.write(" Tag=:"+nameOfField+"\n");	                 			
		                 			
	                    	   
	              	  			posStr=element.getAttribute("pos");//"33";
	              	  			posInt=Integer.parseInt(posStr);	
	              	  			
	              	  			lengthStr=element.getAttribute("size");//"11 for Vizimax" or "13 for Siemens";
	              	  			lengthInt=Integer.parseInt(lengthStr);// svIDLength
	              	  			System.out.println("Pos=:"+posStr);
	              	  			//fileWriter.write(" Pos=:"+posStr+"\n");		              	  			
	              	  			System.out.println("length=:"+lengthStr);  	                    	  
	              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
	              	  			fileWriter.write(" value=:"+element.getAttribute("show")+"\n");		
	              	  			              	  			
	              	  			
	              	  			globalSVIDStr=element.getAttribute("show");
	              	  			System.out.println("globalSVIDStr=:"+globalSVIDStr);
	              	  			
	              	  		    String regexChar = "MU[0-9][0-9][0-9][0-9]"; // check if svID is match to xxxxMUnnnn

		              			
		              			//System.out.println("svID=:"+element.getAttribute("show"));
		              			//fileWriter.write("\n svID="+element.getAttribute("show"));
		              			              			
			              		boolean svIDPatternMatch= patternMatch(regexChar, element.getAttribute("show"));		            		  
		              			
		              			System.out.println("result=:"+svIDPatternMatch);		              			 
		              			fileWriter.write(" svID pattern match result=:"+svIDPatternMatch+"\n");		
		                  	  		    
	              	  			
	                    	   		//nameOfField = "sv.svID";
	                              // if(element.getAttribute("name").equals("sv.svID") && element.getAttribute("showname").startsWith("svID:"))
	                    	   System.out.println("sv.svID showname=:"+element.getAttribute("showname").startsWith("svID:"));
	                    	   if( svIDPatternMatch  && (element.getAttribute("show").length() >=10 ) && element.getAttribute("show").length() <= 34) //&& element.getAttribute("pos").equals(posStr)
	                              {
	                                   fileWriter.write(" sv.svID: "+"true"+"\n");
	                                   System.out.println("sv.svID: "+"true");    
	                                   //System.out.println("sv.svID: "+element.getAttribute("name"));       
	                                    System.out.println("svID: "+element.getAttribute("show"));   
	                                    //fileWriter.write(" svID: "+element.getAttribute("show")+"\n");                                       
	                                    
	                                    fieldStatus=true;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    //fileWriter.write("fieldStatus=:"+fieldStatus+"\n");	 
	                                    System.out.println("status=:"+status);  
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                              }
	                              else
	                              {
	                                   fileWriter.write(" sv.svID: "+"false"+"\n");
	                                   System.out.println("sv.svID: "+"false");             
	                                   //System.out.println("sv.svID: "+element.getAttribute("name"));      
	                                    System.out.println("svID: "+element.getAttribute("show"));   
	                                    //fileWriter.write(" svID: "+element.getAttribute("show")+"\n");     
	                                    fieldStatus=false;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    //fileWriter.write("fieldStatus=:"+fieldStatus+"\n");	 
	                                    System.out.println("status=:"+status); 		
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                              }
               	  			positionInt = posInt+lengthInt+2; //2-fixed
               	  			positionStr = Integer.toString(positionInt);
               	  			System.out.println("\nNext position=:"+positionStr);     	  
            	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");                 	  			

	                       }	                    	  			
           	  				                       
	                               break;
	                       
	                      // case 9: case 15: case 21: case 27: case 33: case 39: case 45: case 51:
	                 		case "sv.smpCnt":
	                       {   
        						fileWriter.write(" Tag=:"+nameOfField+"\n");	                 			
		                 			
	                    	   
	              	  			posStr=element.getAttribute("pos");//"46";
	              	  			posInt=Integer.parseInt(posStr);	
	              	  			
	              	  			lengthStr=element.getAttribute("size");//"2";
	              	  			lengthInt=Integer.parseInt(lengthStr);
	              	  			
	              	  			System.out.println("Pos=:"+posStr);
	              	  			//fileWriter.write(" Pos=:"+posStr+"\n");		              	  			
	              	  			System.out.println("length=:"+lengthStr);	
	              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
	              	  			fileWriter.write(" value=:"+element.getAttribute("show")+"\n");		
	              	  			              	  			
	              	  		    globalSampleCounter= Integer.parseInt(element.getAttribute("value"),16);
	              	  		    System.out.println("globalSampleCounter=:"+globalSampleCounter);
	              	  		    		
	                    	   		//nameOfField = "sv.smpCnt";
	                              // if(element.getAttribute("name").equals("sv.smpCnt") && element.getAttribute("showname").startsWith("smpCnt:"))
	                    	   if( element.getAttribute("size").equals("2")  && Integer.parseInt(element.getAttribute("value"),16)<=15360) //&& element.getAttribute("pos").equals(positionStr)
	                              {
	                                   fileWriter.write(" sv.smpCnt: "+"true"+"\n");
	                                   System.out.println("sv.smpCnt: "+"true");    
	                                   //System.out.println("sv.smpCnt: "+element.getAttribute("name"));        
	                                    System.out.println("smpCnt: "+element.getAttribute("show"));   
	                                    //fileWriter.write(" smpCnt: "+element.getAttribute("show")+"\n");  
	                                    
	                                    //System.out.println("smpCnt=:"+Integer.parseInt(element.getAttribute("value"),16));
	                                    //fileWriter.write(" smpCnt: "+Integer.parseInt(element.getAttribute("value"),16)+"\n");
	                                    
	                                    fieldStatus=true;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    //fileWriter.write("fieldStatus=:"+fieldStatus+"\n");	 
	                                    System.out.println("status=:"+status);; 
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                              }
	                              else
	                              {
	                                   fileWriter.write(" sv.smpCnt: "+"false"+"\n");
	                                   System.out.println("sv.smpCnt: "+"false");             
	                                   //System.out.println("sv.ASDU_element: "+element.getAttribute("name"));      
	                                    System.out.println("smpCnt: "+element.getAttribute("show"));   
	                                    //fileWriter.write(" smpCnt: "+element.getAttribute("show")+"\n");    
	                                    fieldStatus=false;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    //fileWriter.write("fieldStatus=:"+fieldStatus+"\n");	 
	                                    System.out.println("status=:"+status);  
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                              }          
               	  			positionInt = posInt+lengthInt+2; //2- fixed
               	  			positionStr = Integer.toString(positionInt);
               	  			System.out.println("\nNext position=:"+positionStr);               	  			
            	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");                 	  				                    	   
	                       }	                    	  			           	  				                       
	                               break;
	                       
	                      // case 10: case 16: case 22: case 28: case 34: case 40: case 46: case 52:
	                 		case      "sv.confRef":    
	                       {   
        						fileWriter.write(" Tag=:"+nameOfField+"\n");	                 			
		                 			
	                    	   
	              	  			posStr=element.getAttribute("pos");//"50";
	              	  			posInt=Integer.parseInt(posStr);	
	              	  			
	              	  			lengthStr=element.getAttribute("size");//"4";
	              	  			lengthInt=Integer.parseInt(lengthStr);
	                	  		System.out.println("Pos=:"+posStr);
	              	  			//fileWriter.write(" Pos=:"+posStr+"\n");		                	  		
	              	  			System.out.println("length=:"+lengthStr);  	                    	  
	              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
	              	  			//fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
	              	  		
	              	  			int confRevInt=Integer.parseInt(element.getAttribute("value"),16);       
	                	  		System.out.println("confRev=:"+confRevInt);
	              	  			fileWriter.write(" value of conRev=:"+confRevInt+"\n");
	              	  			
	                    	   		//nameOfField = "sv.confRef";
	                              // if(element.getAttribute("name").equals("sv.confRef") && element.getAttribute("showname").startsWith("confRef:"))
	                    	   if( element.getAttribute("size").equals("4") && confRevInt == 1)  //&& element.getAttribute("pos").equals(positionStr)
	                              {
	                                   fileWriter.write(" sv.confRef: "+"true"+"\n");
	                                   System.out.println("sv.confRef: "+"true");    
	                                   //System.out.println("sv.confRef: "+element.getAttribute("name"));        
	                                    System.out.println("confRef: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" confRef: "+element.getAttribute("value")+"\n");    
	                                    fieldStatus=true;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    //fileWriter.write("fieldStatus=:"+fieldStatus+"\n");	 
	                                    System.out.println("status=:"+status);  
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                              }
	                              else
	                              {
	                                   fileWriter.write(" sv.confRef: "+"false"+"\n");
	                                   System.out.println("sv.confRef: "+"false");             
	                                   //System.out.println("sv.confRef: "+element.getAttribute("name"));      
	                                    System.out.println("confRef: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" confRef: "+element.getAttribute("value")+"\n");    
	                                    fieldStatus=false;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    //fileWriter.write("fieldStatus=:"+fieldStatus+"\n");	 
	                                    System.out.println("status=:"+status);  	
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                              } 
               	  			positionInt = posInt+lengthInt+2; //2- fixed
               	  			positionStr = Integer.toString(positionInt);                    	  		
               	  			System.out.println("\nNext position=:"+positionStr);
            	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");                 	  				                    	   
	                       }	                    	  			           	  				                       
	                               break;
	                       
	                      // case 11: case 17: case 23: case 29: case 35: case 41: case 47: case 53:
	                 		case "sv.smpSynch":
	                       {   
        						fileWriter.write(" Tag=:"+nameOfField+"\n");	                 			
		                 			
	                    	   
	              	  			posStr=element.getAttribute("pos");//"56";
	              	  			posInt=Integer.parseInt(posStr);	
	              	  			
	              	  			lengthStr=element.getAttribute("size");//"1";
	              	  			lengthInt=Integer.parseInt(lengthStr);
	              	  			System.out.println("Pos=:"+posStr);
	              	  			//fileWriter.write(" Pos=:"+posStr+"\n");		              	  			
	              	  			System.out.println("length=:"+lengthStr);  	                    	  
	              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
	              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
	              	  			              	  			
	                    	   
	                    	   		//nameOfField = "sv.smpSynch";
	                              // if(element.getAttribute("name").equals("sv.smpSynch") && element.getAttribute("showname").startsWith("smpSynch:") )
	                    	   if( element.getAttribute("size").equals("1") && (element.getAttribute("show").equals("0") || element.getAttribute("show").equals("2") || element.getAttribute("show").equals("3")  || element.getAttribute("show").equals("4") || element.getAttribute("show").equals("5")))  //|| element.getAttribute("show").equals("2"  && element.getAttribute("pos").equals(positionStr)element.getAttribute("pos").equals(positionStr)
	                              {
	                                   fileWriter.write(" sv.smpSynch: "+"true"+"\n");
	                                   System.out.println("sv.smpSynch: "+"true");    
	                                   //System.out.println("sv.smpSynch: "+element.getAttribute("name"));        
	                                    System.out.println("smpSynch: "+element.getAttribute("show"));   
	                                    //fileWriter.write(" smpSynch: "+element.getAttribute("show")+"\n");     
	                                    fieldStatus=true;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    //fileWriter.write("fieldStatus=:"+fieldStatus+"\n");	 
	                                    System.out.println("status=:"+status);;  	
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                              }
	                              else
	                              {
	                                   fileWriter.write(" sv.smpSynch: "+"false"+"\n");
	                                   System.out.println("sv.smpSynch: "+"false");             
	                                   //System.out.println("sv.smpSynch: "+element.getAttribute("name"));      
	                                    System.out.println("smpSynch: "+element.getAttribute("show"));   
	                                    //fileWriter.write(" smpSynch: "+element.getAttribute("show")+"\n");             
	                                    fieldStatus=false;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    //fileWriter.write("fieldStatus=:"+fieldStatus+"\n");	 
	                                    System.out.println("status=:"+status);  
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                              }  
               	  			positionInt = posInt+lengthInt+2; //2-fixed
               	  			positionStr = Integer.toString(positionInt);	                    	  		
               	  			System.out.println("\nNext position=:"+positionStr);
            	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");                 	  				                    	   
	                       }
  
	                               break;                               
	                       
	                      // case 12: case 18: case 24: case 30: case 36: case 42: case 48: case 54:
	                 		case "sv.seqData":
	                       {   
        						fileWriter.write(" Tag=:"+nameOfField+"\n");	                 			
		                 			
	                    	   
	              	  			posStr=element.getAttribute("pos");//"59";
	              	  			posInt=Integer.parseInt(posStr);	
	              	  			
	              	  			lengthStr=element.getAttribute("size");//"64";
	              	  			lengthInt=Integer.parseInt(lengthStr);
	              	  			System.out.println("Pos=:"+posStr);
	              	  			//fileWriter.write(" Pos=:"+posStr+"\n");		              	  			
	              	  			System.out.println("length=:"+lengthStr);		                    	  
	              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
	              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
	              	  			              	  			
	                    	   		//nameOfField = "sv.seqData";
	                              // if(element.getAttribute("name").equals("sv.seqData") && element.getAttribute("showname").startsWith("seqData:"))
	                    	   if( element.getAttribute("size").equals("64") )   //&& element.getAttribute("pos").equals(positionStr)
	                              {
	                                   fileWriter.write(" sv.seqData: "+"true"+"\n");
	                                   System.out.println("sv.seqData: "+"true");    
	                                   //System.out.println("sv.seqData: "+element.getAttribute("name"));        
	                                    System.out.println("seqData: "+element.getAttribute("value"));   
	                                    //fileWriter.write(" seqData: "+element.getAttribute("value")+"\n");    
	                                    
	                                    getCurrentValue(element.getAttribute("value"));
	                                    
	                                    getThreePhaseCurrentQuality(element.getAttribute("value"));
	                                    
	                                    getVoltageValue(element.getAttribute("value"));	
	                                    
	                                    getThreePhaseVoltageQuality(element.getAttribute("value"));
	                                    
	                                    fieldStatus=true;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    //fileWriter.write("fieldStatus=:"+fieldStatus+"\n");	                                    
	                                    System.out.println("status=:"+status);;  
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                              }
	                              else
	                              {
	                                   fileWriter.write(" sv.seqData: "+"false"+"\n");
	                                   System.out.println("sv.seqData: "+"false");             
	                                   //System.out.println("sv.seqData: "+element.getAttribute("name"));      
	                                    System.out.println("seqData: "+element.getAttribute("value"));   
	                                   // fileWriter.write(" seqData: "+element.getAttribute("value")+"\n");     
	                                    
	                                    getCurrentValue(element.getAttribute("value"));
	                                    
	                                    getThreePhaseCurrentQuality(element.getAttribute("value"));
	                                    
	                                    getVoltageValue(element.getAttribute("value"));	
	                                    
	                                    getThreePhaseVoltageQuality(element.getAttribute("value"));
	                                    
	                                    fieldStatus=false;
	                                    status=status && fieldStatus;
	                                    System.out.println("fieldStatus=:"+fieldStatus);
	                                    //fileWriter.write("fieldStatus=:"+fieldStatus+"\n");	 
	                                    System.out.println("status=:"+status);;  
                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                    
	                                    
	                              }       
	                               
	                    	    fileWriter.write("***** MU SV Single ASDU analysis Result:="+status+"\n\n");		                               
                   	  			positionInt = posInt+lengthInt;
                   	  			positionStr = Integer.toString(positionInt);		                    	  			
                   	  			System.out.println("\nNext position="+positionStr);
                   	  			
                   	  				                       
	                       }	                    	  			
           	  				                       
	                               break;                               
	                       
	                          
	                       default: nameOfField = "invalid";
	                                break;
	                   }                         
	                                
	                  //System.out.println("\n========================= End Field:====================================:");       
	                          
	               }//end-if
	          }// end-for
	            
			
	
	    }catch(NullPointerException e){
	           System.out.println("NullPointerException:"+e);
	    }catch (IOException e)	 {
	    	System.out.println("IOException:"+e);
	    }
	 
							  			                	
	 
	 return status;
	//}
}	
	
public boolean parserAndAnalysisOneSVMessageFields(NodeList nList)
 {
	 //boolean status=true;	
	 //boolean fieldStatus=false;
		String dataStr[]={""};
		Integer dataValue[]={0};
		int numOfASDU=0;  //   numOfASDu=1, 8
		int svIDlength=0; //   10<= svIDLength <=34
		int svLength=0;   //   99<= svLength <= 129
		
	    try{
	         
	                System.out.println("\n==========================parserMsgFields:====================================:");
	                //NodeList nList = eElement.getElementsByTagName("field");

	                int totalNumOfFiled=nList.getLength();
	                System.out.println("Total Num. of Field=:"+nList.getLength());
	                //fileWriter.write("Num. of Field=:"+nList.getLength()+"\n");     
	                
	                int NoOfField=0;
	                String nameOfField="";
	                int lengthOfSvID=0;// length >=10
	                
	                int posInt=0;// dynamic change
	                String posStr=""; //dynamic change
	                
	                String smpSynchStr="";
	                int smpSynchInt=0;
	                
	                int positionInt=0;
	                String positionStr="";
	                
	                int lengthInt=0;
	                String lengthStr="";
	                
	                
	 		for (int temp = 0; temp < nList.getLength(); temp++) {
	 		   Node nNode = nList.item(temp);
	 		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	 		     Element element = (Element) nNode; 
	                     //System.out.println("\n========================= Field:====================================:");  
	                     /*
	                     System.out.println("No. of Filed in Header frame: "+temp);
	                     System.out.println("Field Name=:"+element.getAttribute("name"));                           
	                     System.out.println("showname=:"+element.getAttribute("showname"));  
	                     System.out.println("length=:"+element.getAttribute("size"));      
	                     System.out.println("pos=:"+element.getAttribute("pos"));  
	                     System.out.println("show=:"+element.getAttribute("show"));      
	                     System.out.println("value=:"+element.getAttribute("value"));                      
	                     System.out.println("");                         

	                     System.out.println("\n=================");     
	                     */
	                     //System.out.println("temp=:"+temp);  
	                     NoOfField=temp;
	                    // if(NoOfField<=11)
	                    // {
	                     System.out.println("\nNo. of Fields=:"+NoOfField);  
	                    // fileWriter.write("\nNo. of Fields=:"+NoOfField+"\n");
	                    // }
	                     //fileWriter.write("\n");                     
	                     
	                     nameOfField=element.getAttribute("name");
	                     System.out.println("nameOfField=:"+nameOfField);
	                      //switch (NoOfField) {
	                      switch (nameOfField) {
	                      //case 0:  nameOfField = "sv.appid"; 
	                      case "sv.appid":
	                    	  
			              	  			posStr=element.getAttribute("pos");//"14" or "18" depends implementation
			              	  			//fileWriter.write(" Pos=:"+posStr+"\n");				              	  			
			              	  			posInt=Integer.parseInt(posStr);	
			              	  			
			              	  			lengthStr=element.getAttribute("size");//"2";
			              	  			lengthInt=Integer.parseInt(lengthStr);
			              	  			
			              	  			System.out.println("Pos=:"+posStr);
			              	  			System.out.println("length=:"+lengthStr);	
			              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
			              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
			              	  					              	  			
			              	  		    int appidDec= Integer.parseInt(element.getAttribute("value"), 16); 
			              	  			System.out.println("appidDec=:"+appidDec);
			              	  		
			              	  			//fileWriter.write(" appidDec=:"+appidDec+ "\n");			              	  				              	  			
              	  			
	                                   //if(element.getAttribute("name").equals("sv.appid") && element.getAttribute("showname").startsWith("APPID:") && element.getAttribute("size").equals("2") && element.getAttribute("pos").equals("14") )
	                    	  			if(element.getAttribute("size").equals("2")  && (appidDec >= 16380) && (appidDec <= 32767) )
	                      				{
	                                        fileWriter.write(" sv.appid: "+"P"+"\n");
	                                        System.out.println("sv.appid: "+"P");      
	                                        //System.out.println("sv.appid: "+element.getAttribute("name"));     
		                                    System.out.println("appid: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" appid: "+element.getAttribute("value")+"\n");        
	                                        fieldStatus=true;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 		                                        
	                                   }
	                                   else
	                                   {
	                                        fileWriter.write(" sv.appid: "+"F"+"\n");
	                                        System.out.println("sv.appid: "+"F");     
	                                        //System.out.println("sv.appid: "+element.getAttribute("name"));         
		                                    System.out.println("appid: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" appid: "+element.getAttribute("value")+"\n");    
	                                        fieldStatus=false;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;	  
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 		                                        
	                                   }          
	                    	  			 
	                    	  			
	                    	  			positionInt = posInt+lengthInt;
	                    	  			positionStr = Integer.toString(positionInt);
	                    	  			System.out.println("\nNext position=:"+positionStr);
	                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");  	                    	  			
	                                    break;                           
	                           //case 1:  nameOfField = "sv.length";
	                      case   "sv.length":    
	                    	  
			              	  			posStr=element.getAttribute("pos");//"16";
			              	  			posInt=Integer.parseInt(posStr);	
			              	  			
			              	  			lengthStr=element.getAttribute("size");//"2";
			              	  			lengthInt=Integer.parseInt(lengthStr);
			              	  			
			              	  			System.out.println("Pos=:"+posStr);
			              	  			//fileWriter.write(" Pos=:"+posStr+"\n");				              	  			
			              	  			System.out.println("length=:"+lengthStr);	 
			              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
			              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
			              	  					              	  			
	                                   // if(element.getAttribute("name").equals("sv.length") && element.getAttribute("size").equals("2") && element.getAttribute("pos").equals("16") )
	                    	  			if(element.getAttribute("size").equals("2") && element.getAttribute("pos").equals(positionStr) )  //length= 99- 129
	                                   {
	                                        fileWriter.write(" sv.length: "+"P"+"\n");
	                                        System.out.println("sv.length: "+"P");       
	                                        //System.out.println("sv.lengthe: "+element.getAttribute("name"));
	                                        svLength= Integer.parseInt(element.getAttribute("value"),16); // hex string to int
	                                        System.out.println("svLength=:"+svLength);
		                                    System.out.println("length: "+element.getAttribute("show"));   
		                                    
		                                    //fileWriter.write(" length: "+element.getAttribute("show")+"\n");        
	                                        fieldStatus=true;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;   
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
	                                   }
	                                   else
	                                   {
	                                        fileWriter.write(" sv.length: "+"F"+"\n");
	                                        System.out.println("sv.length: "+"F");            
	                                        //System.out.println("sv.length: "+element.getAttribute("name"));    
		                                    System.out.println("length: "+element.getAttribute("show"));   
		                                    //fileWriter.write(" length: "+element.getAttribute("show")+"\n");    
	                                        fieldStatus= false;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;  
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
	                                   }    
	                    	  		    positionInt = posInt+lengthInt;
	                    	  			positionStr = Integer.toString(positionInt);	   
	                    	  			System.out.println("\nNext position=:"+positionStr);                    	  			
	                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");  	                    	  			
	                                    break;
	                         //  case 2:  nameOfField = "sv.reserve1";
	                      case "sv.reserve1":           
	                    	  
			              	  			posStr=element.getAttribute("pos");//"18";
			              	  			posInt=Integer.parseInt(posStr);
			              	  			
			              	  			lengthStr=element.getAttribute("size");//"2";
			              	  			lengthInt=Integer.parseInt(lengthStr);
			              	  			
			              	  			System.out.println("Pos=:"+posStr);
			              	  			//fileWriter.write(" Pos=:"+posStr+"\n");				              	  			
			              	  			System.out.println("length=:"+lengthStr);		                    	  
			              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
			              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
			              	  					              	  			
	                                    // if(element.getAttribute("name").equals("sv.reserve1") && element.getAttribute("size").equals("2") && element.getAttribute("pos").equals("18") )
	                    	  			if(element.getAttribute("size").equals("2") && element.getAttribute("pos").equals(positionStr)  && element.getAttribute("value").equals("0000") )
	                                   {
	                                        fileWriter.write(" sv.reserve1: "+"P"+"\n");
	                                        System.out.println("sv.reserve1: "+"P");          
	                                        //System.out.println("sv.reserve1: "+element.getAttribute("name"));  
		                                    System.out.println("reserve1: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" reserve1: "+element.getAttribute("value")+"\n");     
	                                        fieldStatus=true;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;  	
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
	                                   }
	                                   else
	                                   {
	                                        fileWriter.write(" sv.reserve1: "+"F"+"\n");
	                                        System.out.println("sv.reserve1: "+"F");     
	                                        //System.out.println("sv.reserve1: "+element.getAttribute("name"));       
		                                    System.out.println("reserve1: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" reserve1: "+element.getAttribute("value")+"\n");     
	                                        fieldStatus= false;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);; 
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
	                                   }         
	                    	  			
	                    	  			positionInt = posInt+lengthInt;
	                    	  			positionStr = Integer.toString(positionInt);
	                    	  			System.out.println("\nNext position=:"+positionStr);                  	  			
	                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");  	                    	  			
	                    	  			
	                                    break;
	                       //    case 3:  nameOfField = "sv.reserve2";
	                      case "sv.reserve2":     

			              	  			posStr=element.getAttribute("pos");//"20";
			              	  			posInt=Integer.parseInt(posStr);	
			              	  			
			              	  			lengthStr=element.getAttribute("size");//"2";
			              	  			lengthInt=Integer.parseInt(lengthStr);
			              	  			System.out.println("Pos=:"+posStr);
			              	  			//fileWriter.write(" Pos=:"+posStr+"\n");				              	  			
			              	  			System.out.println("length=:"+lengthStr);
			              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
			              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
			              	  					              	  			
	                                   //if(element.getAttribute("name").equals("sv.reserve2") && element.getAttribute("size").equals("2") && element.getAttribute("pos").equals("20") )
	                    	  			if(element.getAttribute("size").equals("2") && element.getAttribute("pos").equals(positionStr) && element.getAttribute("value").equals("0000") )
	                                   {
	                                        fileWriter.write(" sv.reserve2: "+"P"+"\n");
	                                        System.out.println("sv.reserve2: "+"P");    
	                                        //System.out.println("sv.reserve2: "+element.getAttribute("name"));     
		                                    System.out.println("reserve2: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" reserve2: "+element.getAttribute("value")+"\n");    
	                                        fieldStatus=true;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
	                                   
	                                   }
	                                   else
	                                   {
	                                        fileWriter.write(" sv.reserve2: "+"F"+"\n");
	                                        System.out.println("sv.reserve2: "+"F");     
	                                       // System.out.println("sv.reserve2: "+element.getAttribute("name"));     
		                                    System.out.println("reserve2: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" vreserve2: "+element.getAttribute("value")+"\n");    
	                                        fieldStatus=false;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
	                                   }  
	                    	  			
	                    	  			positionInt = posInt+lengthInt+2; //24
	                    	  			positionStr = Integer.toString(positionInt);			                    	  		
	                    	  			System.out.println("\nNext position=:"+positionStr);   
	                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");  	                    	  			
	                    	  			
	                                    break;
	                        //   case 4:  nameOfField = "sv.savPdu_element";
	                      case   "sv.savPdu_element":  
	                    	  
			              	  			posStr=element.getAttribute("pos");//"24";
			              	  			posInt=Integer.parseInt(posStr);	
			              	  			
			              	  			lengthStr=element.getAttribute("size");//"99";
			              	  			lengthInt=Integer.parseInt(lengthStr);
			              	  			System.out.println("Pos=:"+posStr);
			              	  			//fileWriter.write(" Pos=:"+posStr+"\n");				              	  			
			              	  			System.out.println("length=:"+lengthStr);	
			              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
			              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
			              	  					              	  			
	                                    //if(element.getAttribute("name").equals("sv.savPdu_element") && element.getAttribute("showname").equals("savPdu") && element.getAttribute("pos").equals("24") )
	                    	  			// length:751-943
	                    	  			if(element.getAttribute("pos").equals(positionStr) ) //24
	                                   {
	                                        fileWriter.write(" sv.savPdu_element: "+"P"+"\n"); 
	                                        System.out.println("sv.savPdu_element: "+"P");         
	                                        //System.out.println("sv.savPdu_element: "+element.getAttribute("name"));     
		                                    System.out.println("savPdu_element: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" savPdu_element: "+element.getAttribute("value")+"\n");    
	                                        fieldStatus=true;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
	                                   }
	                                   else
	                                   {
	                                        fileWriter.write(" sv.savPdu_element: "+"F"+"\n");
	                                        System.out.println("sv.savPdu_element: "+"F");         
	                                        //System.out.println("sv.savPdu_element: "+element.getAttribute("name"));   
		                                    System.out.println("savPdu_element: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" savPdu_element: "+element.getAttribute("value")+"\n");    
	                                        fieldStatus=false;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
	                                   }          
	                    	  				                    	  			
	                    	  			positionInt = posInt+2;
	                    	  			positionStr = Integer.toString(positionInt);	
	                    	  			System.out.println("\nNext position=:"+positionStr);                    	  			
	                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");  
	                    	  			
	                                    break;
	                           //case 5:  nameOfField = "sv.noASDU";
	                      case "sv.noASDU":      
	                    	  
			              	  			posStr=element.getAttribute("pos");//"26";
			              	  			System.out.println("Pos=:"+posStr);
			              	  			//fileWriter.write(" Pos=:"+posStr+"\n");				              	  			
			              	  			posInt=Integer.parseInt(posStr);	
			              	  			
			              	  			lengthStr=element.getAttribute("size");//"1";
			              	  			lengthInt=Integer.parseInt(lengthStr);

			              	  			System.out.println("length=:"+lengthStr);	   
			              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
			              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
			              	  					              	  			
	                                    // if(element.getAttribute("name").equals("sv.noASDU") && element.getAttribute("showname").startsWith("noASDU:") && element.getAttribute("pos").equals("26") )
	                    	  			if(element.getAttribute("size").equals("1")  && (element.getAttribute("show").equals("8") && element.getAttribute("show").equals("1")) && element.getAttribute("pos").equals(positionStr)) //
	                                   {
	                                        fileWriter.write(" sv.noASDU: "+"P"+"\n");
	                                        System.out.println("sv.noASDU: "+"P");     
	                                        //System.out.println("sv.noASDU: "+element.getAttribute("name"));      
		                                    System.out.println("value: "+element.getAttribute("value"));   
	                                        numOfASDU= Integer.parseInt(element.getAttribute("value"));
	                                        //System.out.println("noASDU=:"+numOfASDU);                    
		                                    fileWriter.write(" noASDU:= "+element.getAttribute("show")+"\n");     
	                                        fieldStatus=true;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;  	

	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
	                                   }
	                                   else
	                                   {
	                                        fileWriter.write(" sv.noASDU: "+"F"+"\n");
	                                        System.out.println("sv.noASDU: "+"F");           
	                                        //System.out.println("sv.noASDU: "+element.getAttribute("name"));      
		                                    System.out.println("noASDU: "+element.getAttribute("show"));   
		                                    //fileWriter.write(" noASDU: "+element.getAttribute("show")+"\n");    
	                                        fieldStatus=false;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;  	   
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	    
	                                        
	                                   }             
                    	  			
	                    	  			positionInt = posInt+lengthInt+2; //29
	                    	  			positionStr = Integer.toString(positionInt);	
	                    	  			System.out.println("\nNext position=:"+positionStr);                 	  			
	                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");  
	                    	  			
	                                    break;
	                      //     case 6:  nameOfField = "sv.seqASDU";//sv.seqASDU
	                      case  "sv.seqASDU": 
	                    	  
			              	  			posStr=element.getAttribute("pos");//"29";
			              	  			posInt=Integer.parseInt(posStr);	
			              	  			
			              	  			lengthStr=element.getAttribute("size");//"94";
			              	  			lengthInt=Integer.parseInt(lengthStr);
			              	  			System.out.println("Pos=:"+posStr);
			              	  			//fileWriter.write(" Pos=:"+posStr+"\n");				              	  			
			              	  			System.out.println("length=:"+lengthStr);		                    	  
			              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
			              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
			              	  					              	  			
	                                    //if(element.getAttribute("name").equals("sv.seqASDU") && element.getAttribute("showname").startsWith("seqASDU:") && element.getAttribute("pos").equals("29") )
	                    	  			// length:744-936			
	                    	  			if( element.getAttribute("showname").startsWith("seqASDU:")  && element.getAttribute("pos").equals(positionStr)) //29 
	                                   {
	                                        fileWriter.write(" sv.seqASDU: "+"P"+"\n");
	                                        System.out.println("sv.seqASDU: "+"P");      
	                                        //System.out.println("sv.seqASDU: "+element.getAttribute("name"));    
		                                    System.out.println("seqASDU: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" seqASDU: "+element.getAttribute("value")+"\n");       
	                                        fieldStatus=true;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;  	
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
	                                   }
	                                   else
	                                   {
	                                        fileWriter.write(" sv.seqASDU: "+"F"+"\n");
	                                        System.out.println("sv.seqASDU: "+"F");    
	                                        //System.out.println("synphasor.timeqal: "+element.getAttribute("name"));    
		                                    System.out.println("seqASDU: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" seqASDU: "+element.getAttribute("value")+"\n");    
	                                        fieldStatus=true;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;  	
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
	                                   }            
	                    	  			

	                    	  			
	                    	  			positionInt = posInt+0;
	                    	  			positionStr = Integer.toString(positionInt);	

	                    	  			System.out.println("\nNext position=:"+positionStr);	                    	  			
	                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");  	                    	  			
	                                    break;
	                    //       case 7: nameOfField = "sv.ASDU_element";
	                      case "sv.ASDU_element":     
	                    	  
			              	  			posStr=element.getAttribute("pos");//"29";
			              	  			posInt=Integer.parseInt(posStr);	
			              	  			
			              	  			lengthStr=element.getAttribute("size");//"94";
			              	  			lengthInt=Integer.parseInt(lengthStr);
			              	  			System.out.println("Pos=:"+posStr);
			              	  			//fileWriter.write(" Pos=:"+posStr+"\n");				              	  			
			              	  			System.out.println("length=:"+lengthStr);		                    	  
			              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
			              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
			              	  					              	  			
	                                   // if(element.getAttribute("name").equals("sv.ASDU_element") && element.getAttribute("showname").equals("ASDU") && element.getAttribute("pos").equals("29") )
	                    	  			// length = 91-115
	                    	  			if( element.getAttribute("pos").equals(positionStr) )
	                                   {
	                                        fileWriter.write(" sv.ASDU_element: "+"P"+"\n");
	                                        System.out.println("sv.ASDU_element: "+"P");    
	                                        //System.out.println("sv.ASDU_element: "+element.getAttribute("name"));        
		                                    System.out.println("ASDU_element:: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" vASDU_element:: "+element.getAttribute("value")+"\n");     
	                                        fieldStatus=true;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);; 
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
	                                   }
	                                   else
	                                   {
	                                        fileWriter.write(" sv.ASDU_element: "+"F"+"\n");
	                                        System.out.println("sv.ASDU_element: "+"F");             
	                                        //System.out.println("sv.ASDU_element: "+element.getAttribute("name"));      
		                                    System.out.println("ASDU_element:: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" ASDU_element:: "+element.getAttribute("value")+"\n");    
	                                        fieldStatus=false;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);; 
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
	                                   }      
         	  			
	                    	  			positionInt = posInt+4;//29+4=33
	                    	  			positionStr = Integer.toString(positionInt);  
	                    	  			System.out.println("\nNext position=:"+positionStr);
	                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");  	                    	  			
	                                    break;
	                                    
	                    //       case 8: nameOfField = "sv.svID";
	                      case "sv.svID":               
	                    	  
		              	  			posStr=element.getAttribute("pos");//"33";
		              	  			posInt=Integer.parseInt(posStr);	
		              	  			
		              	  			lengthStr=element.getAttribute("size");//"11 for Vizimax" or "13 for Siemens";
		              	  			lengthInt=Integer.parseInt(lengthStr);// svIDLength
		              	  			System.out.println("Pos=:"+posStr);
		              	  			//fileWriter.write(" Pos=:"+posStr+"\n");			              	  			
		              	  			System.out.println("length=:"+lengthStr);  	                    	  
		              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
		              	  			fileWriter.write(" value=:"+element.getAttribute("show")+"\n");		
		              	  				              	  			
		              	  			globalSVIDStr=element.getAttribute("show");
		              	  			System.out.println("globalSVIDStr=:"+globalSVIDStr);
		              	  			
		                              // if(element.getAttribute("name").equals("sv.svID") && element.getAttribute("showname").startsWith("svID:"))
	                    	   		//System.out.println("sv.svID showname=:"+element.getAttribute("showname").startsWith("svID:"));
	                    	  		if(element.getAttribute("pos").equals(positionStr) && (element.getAttribute("show").length() >=10 ) && (element.getAttribute("show").length() <= 34 ))
		                              {
		                                    fileWriter.write(" sv.svID: "+"P"+"\n");
		                                    System.out.println("sv.svID: "+"P");    
		                                    //System.out.println("sv.svID: "+element.getAttribute("name"));        
		                                    svIDlength= element.getAttribute("show").length();
	                                        System.out.println("svIDlength=:"+svIDlength);  
	                                        
		                                    System.out.println("svID: "+element.getAttribute("show"));   
		                                    //fileWriter.write(" svID: "+element.getAttribute("show")+"\n");
		                                      
	                                        fieldStatus=true;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;  	
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
		                              }
		                              else
		                              {
		                                    fileWriter.write(" sv.svID: "+"F"+"\n");
		                                    System.out.println("sv.svID: "+"F");             
		                                    //System.out.println("sv.svID: "+element.getAttribute("name"));      
		                                    System.out.println("svID: "+element.getAttribute("show"));   
		                                    //fileWriter.write(" svID: "+element.getAttribute("show")+"\n");    
	                                        fieldStatus=false;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;  
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n");                                         
		                              }            

                    	  			
                    	  			positionInt = posInt+lengthInt+2; //2-fixed
                    	  			positionStr = Integer.toString(positionInt);
                    	  			System.out.println("\nNext position=:"+positionStr);     	  			
                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");                      	  			
		                               break;
                               
	                      //     case 9: nameOfField = "sv.smpCnt";
	                      case   "sv.smpCnt":   
	                    	  
			              	  			posStr=element.getAttribute("pos");//"46";
			              	  			posInt=Integer.parseInt(posStr);	
			              	  			
			              	  			lengthStr=element.getAttribute("size");//"2";
			              	  			lengthInt=Integer.parseInt(lengthStr);
			              	  			
			              	  			System.out.println("Pos=:"+posStr);
			              	  			//fileWriter.write(" Pos=:"+posStr+"\n");				              	  			
			              	  			System.out.println("length=:"+lengthStr);	
			              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
			              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
			              	  					              	  			
			              	  		    globalSampleCounter= Integer.parseInt(element.getAttribute("value"),16);
			              	  		    System.out.println("globalSampleCounter=:"+globalSampleCounter);			              	  			
              	  			
		                               if(element.getAttribute("size").equals("2") && element.getAttribute("pos").equals(positionStr) && Integer.parseInt(element.getAttribute("value"),16)<=4800)
		                              {
		                                   fileWriter.write(" sv.smpCnt: "+"P"+"\n");
		                                   System.out.println("sv.smpCnt: "+"P");    
		                                   //System.out.println("sv.smpCnt: "+element.getAttribute("name"));        
		                                    System.out.println("smpCnt "+element.getAttribute("value"));  
		                                    fileWriter.write(" smpCnt: "+element.getAttribute("value")+"\n");
		                                    System.out.println("smpCnt=:"+Integer.parseInt(element.getAttribute("value"),16));
		                                    //fileWriter.write(" smpCnt: "+Integer.parseInt(element.getAttribute("value"),16)+"\n");      
	                                        fieldStatus=true;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;  
	                                        
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
		                              }
		                              else
		                              {
		                                   fileWriter.write(" sv.smpCnt: "+"F"+"\n");
		                                   System.out.println("sv.smpCnt: "+"F");             
		                                   //System.out.println("sv.ASDU_element: "+element.getAttribute("name"));      
		                                    System.out.println("smpCnt: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" smpCnt: "+element.getAttribute("value")+"\n");      
	                                        fieldStatus=false;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);
	                                        
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 
		                              }     
		                               

	                    	  			
	                    	  			positionInt = posInt+sizeInt+2; //2- fixed
	                    	  			positionStr = Integer.toString(positionInt);

	                    	  			System.out.println("\nNext position=:"+positionStr);               	  			
	                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");  	                    	  			
		                               break;
                               
	                     //      case 10: nameOfField = "sv.confRef";
	                      case   "sv.confRef":  
	                    	  
			              	  			posStr=element.getAttribute("pos");//"50";
			              	  			posInt=Integer.parseInt(posStr);	
			              	  			
			              	  			lengthStr=element.getAttribute("size");//"4";
			              	  			lengthInt=Integer.parseInt(lengthStr);
			                	  		System.out.println("Pos=:"+posStr);
			              	  			//fileWriter.write(" Pos=:"+posStr+"\n");				                	  		
			              	  			System.out.println("length=:"+lengthStr);  	                    	  
			              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
			              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
			              	  					              	  			
		                              // if(element.getAttribute("name").equals("sv.confRef") && element.getAttribute("showname").startsWith("confRef:"))
	                    	  		if(element.getAttribute("size").equals("4") && element.getAttribute("pos").equals(positionStr))
		                              {
		                                   fileWriter.write(" sv.confRef: "+"P"+"\n");
		                                   System.out.println("sv.confRef: "+"P");    
		                                   //System.out.println("sv.confRef: "+element.getAttribute("name"));        
		                                    System.out.println("value: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" value: "+element.getAttribute("value")+"\n");      
	                                        fieldStatus=true;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;  	
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
		                              }
		                              else
		                              {
		                                   fileWriter.write(" sv.confRef: "+"F"+"\n");
		                                   System.out.println("sv.confRef: "+"F");             
		                                   //System.out.println("sv.confRef: "+element.getAttribute("name"));      
		                                    System.out.println("value: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" value: "+element.getAttribute("value")+"\n");        
	                                        fieldStatus=false;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;  	
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
		                              }  
 
                    	  			
                    	  			positionInt = posInt+lengthInt+2; //2- fixed
                    	  			positionStr = Integer.toString(positionInt);                    	  		
                    	  			System.out.println("\nNext position=:"+positionStr);
                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");                      	  			
		                               break;
                               
	                       //    case 11: nameOfField = "sv.smpSynch";
	                      case   "sv.smpSynch":   
	                    	  
			              	  			posStr=element.getAttribute("pos");//"56";
			              	  			posInt=Integer.parseInt(posStr);	
			              	  			
			              	  			smpSynchStr=element.getAttribute("value"); // 0, 1,2,5,..., 255
			              	  			smpSynchInt=Integer.parseInt(smpSynchStr);				              	  			
			              	  			
			              	  			lengthStr=element.getAttribute("size");//"1";
			              	  			lengthInt=Integer.parseInt(lengthStr);
			              	  			System.out.println("Pos=:"+posStr);
			              	  			//fileWriter.write(" Pos=:"+posStr+"\n");				              	  			
			              	  			System.out.println("length=:"+lengthStr);  	   
			              	  			fileWriter.write(" length=:"+lengthStr+"\n");				              	  			
			              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
			              	  			                    	  
		                              // if(element.getAttribute("name").equals("sv.smpSynch") && element.getAttribute("showname").startsWith("smpSynch:") )
	                    	  		if(element.getAttribute("size").equals("1") && (element.getAttribute("show").equals("0") || element.getAttribute("show").equals("1") || element.getAttribute("show").equals("2") ||  (smpSynchInt >=5 && smpSynchInt <= 255) ))
		                              {
		                                   fileWriter.write(" sv.smpSynch: "+"P"+"\n");
		                                   System.out.println("sv.smpSynch: "+"P");    
		                                   //System.out.println("sv.smpSynch: "+element.getAttribute("name"));        
		                                    System.out.println(" smpSynch: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" smpSynch: "+element.getAttribute("value")+"\n");    
	                                        fieldStatus=true;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;  
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
		                              }
		                              else
		                              {
		                                   fileWriter.write(" sv.smpSynch: "+"F"+"\n");
		                                   System.out.println("sv.smpSynch: "+"F");             
		                                   //System.out.println("sv.smpSynch: "+element.getAttribute("name"));      
		                                    System.out.println("smpSynch: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" smpSynch: "+element.getAttribute("value")+"\n");            
	                                        fieldStatus=false;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status);;  	
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
		                              }        
    
                    	  			
                    	  			positionInt = posInt+lengthInt+2; //2-fixed
                    	  			positionStr = Integer.toString(positionInt);	                    	  		
                    	  			System.out.println("\nNext position=:"+positionStr);
                    	  			//fileWriter.write("\n Next position=:"+positionStr+"\n");                     	  			
		                               break;                               
                               
	                      //     case 12: nameOfField = "sv.seqData";
	                      case   "sv.seqData":     
	                    	  
		              	  			posStr=element.getAttribute("pos");//"59";
		              	  			posInt=Integer.parseInt(posStr);	
		              	  			
		              	  			lengthStr=element.getAttribute("size");//"64";
		              	  			lengthInt=Integer.parseInt(lengthStr);
		              	  			System.out.println("Pos=:"+posStr);
		              	  			//fileWriter.write(" Pos=:"+posStr+"\n");			              	  			
		              	  			System.out.println("length=:"+lengthStr);		                    	  
		              	  			fileWriter.write(" length=:"+lengthStr+"\n");	
		              	  			fileWriter.write(" value=:"+element.getAttribute("value")+"\n");		
		              	  				              	  			
		                               //if(element.getAttribute("name").equals("sv.seqData") && element.getAttribute("showname").startsWith("seqData:"))
	                    	  			if(element.getAttribute("size").equals("64") && element.getAttribute("pos").equals(positionStr))
		                              {

		                                   fileWriter.write(" sv.seqData: "+"P"+"\n");
		                                   System.out.println("sv.seqData: "+"P");    
		                                   //System.out.println("sv.seqData: "+element.getAttribute("name"));        
		                                    System.out.println("sv.seqData: "+element.getAttribute("value"));   
		                                    //fileWriter.write("sv.seqData: "+element.getAttribute("value")+"\n");      
                                       
		                                    
		                                    getCurrentValue(element.getAttribute("value"));
		                                    getThreePhaseCurrentQuality(element.getAttribute("value"));
		                                    

		                                    getVoltageValue(element.getAttribute("value"));	     
		                                    getThreePhaseVoltageQuality(element.getAttribute("value"));		                                    
		                                    
		                                    fieldStatus=true;
	                                        status=status && fieldStatus;
	                                       		
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
		                              }
		                              else
		                              {
		                                   fileWriter.write("sv.seqData: "+"F"+"\n");
		                                   System.out.println("sv.seqData: "+"F");             
		                                   //System.out.println("sv.seqData: "+element.getAttribute("name"));      
		                                    System.out.println("sv.seqData: "+element.getAttribute("value"));   
		                                    //fileWriter.write(" sv.seqData: "+element.getAttribute("value")+"\n");  
	                                        
		                                    
		                                    getCurrentValue(element.getAttribute("value"));
		                                    
		                                    getThreePhaseCurrentQuality(element.getAttribute("value"));
		                                    
		                                    getVoltageValue(element.getAttribute("value"));	
		                                    
		                                    getThreePhaseVoltageQuality(element.getAttribute("value"));	                                    
		                                    
		                                    fieldStatus=false;
	                                        status=status && fieldStatus;
	                                        System.out.println("fieldStatus=:"+fieldStatus);;
	                                        System.out.println("status=:"+status); 		     
	                                        fileWriter.write(" fieldStatus=:"+fieldStatus+"\n");
	                                        fileWriter.write(" status=:"+status+"\n\n"); 	                                        
		                              }          

	                    	  			
	                    	  			positionInt = posInt+lengthInt;
	                    	  			positionStr = Integer.toString(positionInt);		                    	  			
	                    	  			System.out.println("\nNext position="+positionStr);
	                    	  			
		                               break;                               
                               
	                              
	                           default: nameOfField = "invalid";
	                                    break;
	                       }                         
	                                    
	                      //System.out.println("\n========================= End Field:====================================:");       
	                              
	                   }//end-if
	              }// end-for
	                	 			

	        }catch(NullPointerException e){
		           System.out.println("NullPointerException:"+e);
		    }catch (IOException e)	 {
		    	System.out.println("IOException:"+e);
		    }	
	    return status;	    
 		} 
}
