package com.example.devoir_corentin_legall;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;


public class Coder {
    private File file;

    public Coder(String path){
        this.file = new File(path);
    }

    public boolean encode(String text) throws IOException {

        int size = text.length();
        byte[] temp_byte = new byte[(int)file.length()];
        byte[] text_bytes = text.getBytes();

        FileInputStream file_in =new FileInputStream(file);
        BufferedInputStream buf_in = new BufferedInputStream(file_in);
        int read = buf_in.read(temp_byte, 0, temp_byte.length);
        if(read < 0) {
            return false;
        }

        buf_in.close();
        file_in.close();

        int counter;

        if(temp_byte[0] == (byte)0x89 && temp_byte[1] == (byte)0x50 && temp_byte[2] == (byte)0x4E && temp_byte[3] == (byte)0x47)
            counter = 12 + 1;
        else
            counter = 2 + 1;


        for(int i=0; i<size; i++){
            for(int y=0; y<4; y++){

                temp_byte[temp_byte.length - counter] = (byte)((temp_byte[temp_byte.length - counter] & (byte)0xFC) + ((text_bytes[i] >> 2*y) & (byte)0x3));
                counter ++;
            }
        }
        FileOutputStream file_out = new FileOutputStream(file, false);
        BufferedOutputStream buf_out = new BufferedOutputStream(file_out);
        buf_out.write(temp_byte);
        buf_out.flush();
        buf_out.close();
        file_out.close();
        return true;
    }

    public String decode() throws IOException {
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        byte[] text = new byte[size];
        FileInputStream file_in =new FileInputStream(file);
        BufferedInputStream buf = new BufferedInputStream(file_in);
        int counter = -1;
        buf.read(bytes, 0, bytes.length);
        buf.close();
        file_in.close();

        int offset;

        if(bytes[0] == (byte)0x89 && bytes[1] == (byte)0x50 && bytes[2] == (byte)0x4E && bytes[3] == (byte)0x47)
            offset = 12+1;
        else
            offset = 2 + 1;

        for(int i=offset; i<bytes.length; i++){
            int letterCounter = (i-offset)%4;
            if(letterCounter == 0)
                counter ++;
            text[counter] |= ((bytes[ bytes.length - i] & 0x3) << letterCounter*2);
        }
        if(text[0]<32 || text[0]>126)
            return null;
        byte[] endtext = new byte[1024];
        int i=0;
        for(byte b : text){
            if(b<32 || b>126)
                break;
            endtext[i] = b;
            i++;
        }
        return new String(endtext, "windows-1252");
    }
}
