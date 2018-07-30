package MU92E2InteroperabilityAnalyzer;



import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;

public class MUXmlPacketDataParser {
    //FileWriter fileWriter = null;;//new PrintWriter("filename.txt");
   // public static String xmlDataFileName = "C:\\Users\\ysong\\workspace\\MU92E2InteroperabilityAnalyzer\\src\\VizmaxMUPackets.xml";
    
    MUXmlPacketDataParser(){
    }
    
    
    public void parserXMLFile(String xmlFileName){
    	  try {

    		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		Document doc = dBuilder.parse(xmlFileName);
    		doc.getDocumentElement().normalize();

    		//System.out.println("Root element:" + doc.getDocumentElement().getNodeName());
    		NodeList nList = doc.getElementsByTagName("pdml");

                   System.out.println("Root Length=:"+nList.getLength());

		      		for (int temp = 0; temp < nList.getLength(); temp++) {
		
		
		      		   Node nNode = nList.item(temp);
		      		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		
		      		      Element eElement = (Element) nNode;
		                            System.out.println("Node Name=:"+eElement.getNodeName());
		                            System.out.println("Root Element Done:================");
		                            System.out.println("\n\n\n");
		      		   }
		      		}
    	  } catch (Exception e) {
    		e.printStackTrace();
    	  }
}

private static String getTagValue(String sTag, Element eElement) {

          //NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
            NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

            Node nValue = (Node) nlList.item(0);

            return nValue.getNodeValue();
}



public String getElementTag(String xmlFileName, String elementTag){

     	  //Element eElement;
     	  String elementTagStr="";

     	  try {

     		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
     		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
     		Document doc = dBuilder.parse(xmlFileName);
     		doc.getDocumentElement().normalize();

     		System.out.println("Element Name:" + doc.getDocumentElement().getNodeName());
     		NodeList nList = doc.getElementsByTagName(elementTag);
     	        System.out.println("Node No. of Element=:"+nList.getLength());

     		for (int temp = 0; temp < nList.getLength(); temp++) {

     		   Node nNode = nList.item(temp);
     		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

     		      Element eElement = (Element) nNode;
     	  		  //elementTagStr= getTagValue(elementTag, eElement);
                              //System.out.println("elementTagStr=:"+elementTagStr);
                          System.out.println("No.=:"+temp);
                          System.out.println("Name=:"+eElement.getNodeName());
                          System.out.println("Attr Name 0=:"+eElement.getAttributes().item(0));
                          System.out.println("Attr Name 1=:"+eElement.getAttributes().item(1));
                          System.out.println("Attr Name 2=:"+eElement.getAttributes().item(2));
                          System.out.println("Attr Name 3=:"+eElement.getAttributes().item(3));

                          System.out.println("Value=:"+eElement.getNodeValue());
                          //System.out.println("Node Name=:"+eElement.getNodeName());
     		   }
                      // System.out.println(doc.getDocumentElement().getNodeName()+"   Element done:=========================");
                       System.out.println("\n");

     		}
     	  } catch (Exception e) {
     		e.printStackTrace();
     	  }

          // System.out.println("Element=:"+elementTagStr);
           return elementTagStr;
	}    

//get MU meta data, it will call getMUdata()
public void getMUSVProtocolName(String xmlFileName)
{
   		  try {
   			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
   			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
   			Document doc = dBuilder.parse(xmlFileName);
   			doc.getDocumentElement().normalize();

            // fileWriter.write("=================MUSVProtocolName=================\n");
             System.out.println("=================MUSVProtocolName=================\n");
   			NodeList nList = doc.getElementsByTagName("proto");

            int noOfPhasorFrames=0;
   			for (int temp = 0; temp < nList.getLength(); temp++) {
   			   Node nNode = nList.item(temp);
   			   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

   			     Element eElement = (Element) nNode;
   			     if(eElement.getAttribute("name").equals("sv"))
   			      {
	      				if(eElement.getAttribute("showname").indexOf("Configuration Frame 1")!= -1)
	      				{
	                             getMUSVProtocolMetadata(eElement);
	                             break;
	      				}
	                    noOfPhasorFrames++;
   			      }

   			   }

   			}

   		  } catch (Exception e) {
   			e.printStackTrace();
   		  }

	}

public void getMUSVProtocolMetadata(Element eElement)
{

                      System.out.println("\n==========================GetMUData:====================================:");
       		NodeList nList = eElement.getElementsByTagName("field");

                      int totalNumOfFiled=nList.getLength();

                      int NoOfField=0;
                      String nameOfField="";

       		for (int temp = 0; temp < nList.getLength(); temp++) {
       		   Node nNode = nList.item(temp);
       		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

       		     Element element = (Element) nNode;

                           NoOfField=temp;

                            switch (NoOfField) {

                                 case 1: nameOfField = "sv.appid";
                                          //this.numOfPMUs=Integer.parseInt(element.getAttribute("value"),16);
                                          System.out.println("name:="+element.getAttribute("name"));
                                          System.out.println("showname:="+element.getAttribute("showname"));
                                          System.out.println("size:="+element.getAttribute("size"));                                          
                                          System.out.println("pos:="+element.getAttribute("pos"));
                                          System.out.println("show:="+element.getAttribute("show"));                                          
                                          System.out.println("value:="+element.getAttribute("value"));
                                          break;
                                          
                                 case 2: nameOfField = "sv.length";
		                                 System.out.println("name:="+element.getAttribute("name"));
		                                 System.out.println("showname:="+element.getAttribute("showname"));
		                                 System.out.println("size:="+element.getAttribute("size"));                                          
		                                 System.out.println("pos:="+element.getAttribute("pos"));
		                                 System.out.println("show:="+element.getAttribute("show"));                                          
		                                 System.out.println("value:="+element.getAttribute("value"));
                                         break;
                                          
                                 case 3: nameOfField = "sv.reserve1";
		                                 System.out.println("name:="+element.getAttribute("name"));
		                                 System.out.println("showname:="+element.getAttribute("showname"));
		                                 System.out.println("size:="+element.getAttribute("size"));                                          
		                                 System.out.println("pos:="+element.getAttribute("pos"));
		                                 System.out.println("show:="+element.getAttribute("show"));                                          
		                                 System.out.println("value:="+element.getAttribute("value"));
                                         break;
                                          
                                 case 4: nameOfField = "sv.reserve2";
		                                 System.out.println("name:="+element.getAttribute("name"));
		                                 System.out.println("showname:="+element.getAttribute("showname"));
		                                 System.out.println("size:="+element.getAttribute("size"));                                          
		                                 System.out.println("pos:="+element.getAttribute("pos"));
		                                 System.out.println("show:="+element.getAttribute("show"));                                          
		                                 System.out.println("value:="+element.getAttribute("value"));
		                                 break;                                          
                                          
                                 case 5: nameOfField = "sv.savPdu_element";
		                                 System.out.println("name:="+element.getAttribute("name"));
		                                 System.out.println("showname:="+element.getAttribute("showname"));
		                                 System.out.println("size:="+element.getAttribute("size"));                                          
		                                 System.out.println("pos:="+element.getAttribute("pos"));
		                                 System.out.println("show:="+element.getAttribute("show"));                                          
		                                 System.out.println("value:="+element.getAttribute("value"));
                                         break;

                                 case 6: nameOfField = "sv.noASDU";
		                                 System.out.println("name:="+element.getAttribute("name"));
		                                 System.out.println("showname:="+element.getAttribute("showname"));
		                                 System.out.println("size:="+element.getAttribute("size"));                                          
		                                 System.out.println("pos:="+element.getAttribute("pos"));
		                                 System.out.println("show:="+element.getAttribute("show"));                                          
		                                 System.out.println("value:="+element.getAttribute("value"));
                                         break;

                                 case 7: nameOfField = "sv.seqASDU";
		                                 System.out.println("name:="+element.getAttribute("name"));
		                                 System.out.println("showname:="+element.getAttribute("showname"));
		                                 System.out.println("size:="+element.getAttribute("size"));                                          
		                                 System.out.println("pos:="+element.getAttribute("pos"));
		                                 System.out.println("show:="+element.getAttribute("show"));                                          
		                                 System.out.println("value:="+element.getAttribute("value"));
		                                 break;

                                 default: nameOfField = "invalid";
                                          break;
                             }

                         }//end-if
                      }// end-for
	}

public boolean analyzeSVASDU(Element eElement)
{
	 boolean status=false;
	 boolean srcIpStatus=false;
	 boolean dstIpStatus=false;
	 try{
	
	         System.out.println("\n==========================parserIP proto:====================================:");
	         NodeList nList = eElement.getElementsByTagName("field");
	
	         int totalNumOfFiled=nList.getLength();
	         System.out.println("Total Num. of Field=:"+nList.getLength());
	         //fileWriter.write("Num. of Field=:"+nList.getLength()+"\n");
	
	         int NoOfField=0;
	         String nameOfField="";
	
				for (int temp = 0; temp < nList.getLength(); temp++) {
				   Node nNode = nList.item(temp);
				   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			
				     Element element = (Element) nNode;
			              //System.out.println("\n========================= Field:====================================:");
			/*
			              if(element.getAttribute("name").equals("ip.src") && element.getAttribute("show").equals(this.cmdSourceIp) )
                          {
			            	  srcIpStatus=true;
                          }else
                          {
                        	  srcIpStatus=false;
                          }
			              
			              if(element.getAttribute("name").equals("ip.dst") && element.getAttribute("show").equals(this.cmdDestinationIp) )
                          {
			            	  dstIpStatus=true;
                          }else
                          {
                        	  dstIpStatus=false;
                          }
                          */
			              		              
			            }//end-if
			         }// end-for
		
     }catch(NullPointerException e){
         System.out.println("NullPointerException:"+e);
      }		 
	 
	 if (srcIpStatus && dstIpStatus)
		 status=true;
	 else
		 status=false;
	 
	 return status;
	}
}

