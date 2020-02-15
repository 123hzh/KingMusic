package com.km.controller;


	import java.io.BufferedReader;
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileReader;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.RandomAccessFile;
	import java.io.Reader;
	 
	public class ReadFromFile {	    	 
		public static void main(String[] args) {
			String name="����Ѹ - ���������";
	        String fileName = "WebContent/lrc/"+name+".lrc";	       
	        String s=ReadFromFile.readFileByLines(fileName);
	        System.out.println(s);
	    }
	    /**
	     * ����Ϊ��λ��ȡ�ļ��������ڶ������еĸ�ʽ���ļ�
	     * 
	     * @param fileName
	     *            �ļ���
	     */
	    public static String readFileByLines(String fileName) {
	        File file = new File(fileName);
	        InputStreamReader reader = null;
	        try {
	            reader = new InputStreamReader(new FileInputStream(file),"utf-8");
	            BufferedReader bufferedReader = new BufferedReader(reader);
	            String tempString = null;
	            String songs = "";
	            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
	            while ((tempString = bufferedReader.readLine()) != null) {
	                // ��ʾ�к�	                
	                 songs = songs+tempString+"\\"+"\r\n";	                	                
	            }
	            /*System.out.println(songs);*/	            
	            bufferedReader.close();
	            return songs;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    }
	 
	    
	    
	    
	    
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

