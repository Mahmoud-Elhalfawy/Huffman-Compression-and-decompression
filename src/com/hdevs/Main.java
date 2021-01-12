package com.hdevs;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static int zeros=0;
    private static final String CODES_FILE_NAME = "newcodes.txt";
    public  static String INPUT_FILE_NAME ="original.txt";
    public  static String OUTPUT_ENCODED_FILE_NAME ="encoded.bin";
    public  final static String OUTPUT_DECODED_FILE_NAME ="decoded.txt";
    public static HashMap<Character,Integer> frequencies;
    public static String file=null;
    public static StringBuilder builder;
    public static HuffmanTree huffmanTree;

    public static void main(String[] args) throws IOException {
        int choice=0;
        Scanner scanner=new Scanner(System.in);
do {
huffmanTree=new HuffmanTree();
frequencies=new HashMap<>();
    System.out.println("1.Compress & decompress\t 2.Decompress\t3.Compress\t4.exit");
    System.out.println("\n*................*\n");
    System.out.print("Please enter your choice: ");
    try {
         choice = Integer.parseInt(scanner.nextLine());
        System.out.println("\n*................*\n");

        switch (choice) {
            case 1: {
                System.out.print("Enter name of desired input file or leave the default test( default = original.txt ): ");
                String input = scanner.nextLine();
                if (!input.trim().equals(""))
                    INPUT_FILE_NAME = input;
                System.out.println("\n*................*\n");
                readFile();


                long stE = System.nanoTime();
                HashMap<Character, String> codes = huffmanTree.buildTree();
                writeCodesFile(codes);
                long fiE = System.nanoTime();

                String encodedText = huffmanTree.printEncoded(file);
                String temp=encodedText;
                writeBinaryFile(temp,OUTPUT_ENCODED_FILE_NAME);
                long stD = System.nanoTime();
                String decodedText = huffmanTree.printDecoded();
                long fiD = System.nanoTime();


                System.out.println("\n*................*\n");
                System.out.println("File Encoded Successfully!");
                System.out.println("\n*................*\n");
                System.out.println("Execution time of encoding = " + ((fiE - stE) / 1000000F) + " millisecs");
                System.out.println("\n*................*\n");
                writeFile(decodedText, OUTPUT_DECODED_FILE_NAME);
                System.out.println("File Decoded Successfully!");
                System.out.println("\n*................*\n");
                System.out.println("Execution time of decoding = " + ((fiD - stD) / 1000000F) + " millisecs");
                System.out.println("\n******************\n");
                double compRatio = (((float) getFileSizeBytes(OUTPUT_ENCODED_FILE_NAME) / getFileSizeBytes(INPUT_FILE_NAME)) * 100.0);
                System.out.println("Compression Ratio = " + String.format("%.3f", compRatio) + " %");
            }
            break;
            case 2: {
                System.out.print("Enter name of desired input file to decompress: ");
                String input = scanner.nextLine();
                if (!input.trim().equals(""))
                    OUTPUT_ENCODED_FILE_NAME = input;
                System.out.println("\n*................*\n");

                System.out.print("Enter name of original file: ");
                String input2 = scanner.nextLine();
                if (!input2.trim().equals(""))
                    INPUT_FILE_NAME = input2;
                System.out.println("\n*................*\n");

                readFile();
                HashMap<Character,String> codes= readCodesFile();
                huffmanTree.buildTree();

                String encodedText = huffmanTree.printEncoded(file);
                String temp=encodedText;
                writeBinaryFile(temp,OUTPUT_ENCODED_FILE_NAME);
                long stD = System.nanoTime();
                String decodedText = huffmanTree.printDecoded();
                long fiD = System.nanoTime();
                writeFile(decodedText, OUTPUT_DECODED_FILE_NAME);

                System.out.println("File Decoded Successfully!");
                System.out.println("\n*................*\n");
                System.out.println("Execution time of decoding = " + ((fiD - stD) / 1000000F) + " millisecs");
                System.out.println("\n******************\n");
            }
            break;
            case 3:{
                System.out.print("Enter name of desired input file or leave the default test( default = original.txt ): ");
                String input = scanner.nextLine();
                if (!input.trim().equals(""))
                    INPUT_FILE_NAME = input;
                System.out.println("\n*................*\n");
                readFile();


                long stE = System.nanoTime();
                HashMap<Character, String> codes = huffmanTree.buildTree();
                writeCodesFile(codes);
                long fiE = System.nanoTime();

                String encodedText = huffmanTree.printEncoded(file);
                String temp=encodedText;
                writeBinaryFile(temp,OUTPUT_ENCODED_FILE_NAME);
                System.out.println("\n*................*\n");
                System.out.println("File Encoded Successfully!");
                System.out.println("\n*................*\n");
                System.out.println("Execution time of encoding = " + ((fiE - stE) / 1000000F) + " millisecs");
                System.out.println("\n*................*\n");
            }
            break;
            case 4:
                return;
            default:
        }

    } catch (Exception e) {
        System.out.println("Invalid Input");
        System.out.println("\n******************\n");

    }
}while (choice!=0);
    }

    private static String readEncoded() throws IOException {

        FileReader fileReader=new FileReader(OUTPUT_ENCODED_FILE_NAME);
        BufferedReader bufferedReader=new BufferedReader(fileReader);
        String line=null;
        builder=new StringBuilder();
        while((line=bufferedReader.readLine())!=null){
            builder.append(line);

            }

        return builder.toString();
    }

    private static void writeCodesFile(HashMap<Character, String> codes) throws IOException {

        Node root= huffmanTree.root;
        FileWriter fileWriter=new FileWriter(CODES_FILE_NAME,StandardCharsets.UTF_8,false);
        PrintWriter printWriter=new PrintWriter(fileWriter);
        printWriter.println(zeros);
        printWriter.print(root);
        for (Map.Entry entry:codes.entrySet()) {
            if ((char) entry.getKey()=='\n' )
                printWriter.println("nline" + "," + entry.getValue());
            if ((char) entry.getKey()==' ')
                printWriter.println("space" + "," + entry.getValue());
            else
                printWriter.println(entry.getKey() + "," + entry.getValue());


        }
        fileWriter.close();

    }


    public static HashMap<Character,String> readCodesFile ()throws IOException{
        HashMap<Character, String> encodedMap=new HashMap<>();
        FileReader fileReader=new FileReader(CODES_FILE_NAME);
        BufferedReader bufferedReader=new BufferedReader(fileReader);
        String line=null;
        builder=new StringBuilder();
        zeros=Integer.parseInt(bufferedReader.readLine());
        while((line=bufferedReader.readLine())!=null){
            if (line.equals("") || line.charAt(0)==',')
                continue;

            String[] entry=line.split(",");
            if (entry[0].equals("space"))
                entry[0]=" ";
            else if (entry[0].equals("nline"))
                entry[0]="\n";
            encodedMap.put(entry[0].charAt(0),entry[1]);
        }

    return encodedMap;
    }

    public static void  readFile() throws IOException {
//        BufferedReader bufferedReader  = new BufferedReader(
//                new InputStreamReader(new FileInputStream(new File(INPUT_FILE_NAME)),"ISO-8859-1"));
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

    public static String writeBinaryFile(String encodedText,String fileName) throws IOException {
        FileWriter fileWriter=new FileWriter(fileName,false);
        PrintWriter printWriter=new PrintWriter(fileWriter);
        encodedText=normalizeBinary(encodedText);
        printWriter.println(convertToText(encodedText));
        fileWriter.close();
        return convertToText(encodedText);

    }
    public static void writeFile(String text,String fileName)throws IOException {
        FileWriter fileWriter=new FileWriter(fileName,false);
        PrintWriter printWriter=new PrintWriter(fileWriter);
        printWriter.println(text);
        fileWriter.close();
    }

    private static long getFileSizeBytes(String filename) {
        File file=new File(filename);
        return file.length();
    }

    private static String convertToText(String text){
        StringBuilder output= new StringBuilder();
        for(int i = 0; i <= text.length() - 8; i+=8)
        {
            int k = Integer.parseInt(text.substring(i, i+8), 2);
            output.append((char) k);
        }
    return output.toString();
    }


    private static String convertToBinary(String asciiText) {
        StringBuilder output = new StringBuilder();
        for (Character c : asciiText.toCharArray()) {
            String s=Integer.toBinaryString((int) c);
            while (s.length()<8){
                s="0"+s;
            }
            output.append(s);
        }

        return output.toString();
    }


private static String normalizeBinary(String encodedText){

        while (encodedText.length()%8!=0){
            zeros=zeros+1;
            encodedText="0"+encodedText;
        }
        return encodedText;

}
public static void retrieveTree() throws IOException {




}

}
