package com.hdevs;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public  static String INPUT_FILE_NAME ="original.txt";
    public  final static String OUTPUT_ENCODED_FILE_NAME ="encoded.bin";
    public  final static String OUTPUT_DECODED_FILE_NAME ="decoded.txt";
    public static HashMap<Character,Integer> frequencies=new HashMap<>();
    public static String file=null;
    public static StringBuilder builder;

    public static void main(String[] args) throws IOException {

        System.out.print("Enter name of desired input file or leave the default test( default = original.txt ): ");
        Scanner scanner=new Scanner(System.in);
        String input=scanner.nextLine();
        if (!input.trim().equals(""))
            INPUT_FILE_NAME=input;
        System.out.println("\n*................*\n");
        readFile();
        HuffmanTree huffmanTree=new HuffmanTree();
        huffmanTree.buildTree();
        String encodedText=huffmanTree.printEncoded(file);
        String decodedText=huffmanTree.printDecoded();
        writeFile(encodedText,OUTPUT_ENCODED_FILE_NAME);
        System.out.println("File Encoded Successfully!");
        System.out.println("\n*................*\n");

        writeFile(decodedText,OUTPUT_DECODED_FILE_NAME);
        System.out.println("File Decoded Successfully!");
        System.out.println("\n******************\n");

    }

    public static void  readFile() throws IOException {
        FileReader fileReader=new FileReader(INPUT_FILE_NAME);
        BufferedReader bufferedReader=new BufferedReader(fileReader);
        String line=null;
         builder=new StringBuilder();
        while((line=bufferedReader.readLine())!=null){
            line=line+"\n";
            builder.append(line);
            char[] characters =line.toCharArray();
            for (Character c:characters){
                frequencies.put(c,(frequencies.getOrDefault(c,0)+1));
            }
        }
        file=builder.toString();


    }

    public static void writeFile(String text,String fileName)throws IOException {
        FileWriter fileWriter=new FileWriter(fileName,false);
        PrintWriter printWriter=new PrintWriter(fileWriter);
        printWriter.println(text);
        fileWriter.close();


    }


}
