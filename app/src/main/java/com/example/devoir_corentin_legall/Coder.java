package com.example.devoir_corentin_legall;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Coder {
    private File file;

    public Coder(String path){
        this.file = new File(path);
    }

    public boolean encode(String text) throws IOException {

        int size = text.length();
        int file_size = (int) file.length();
        byte[] temp_byte = new byte[file_size];
        byte[] text_bytes = text.getBytes();
        BufferedInputStream buf_in = new BufferedInputStream(new FileInputStream(file));

        buf_in.read(temp_byte, 0, temp_byte.length);
        buf_in.close();
        int counter = temp_byte[4]<<8 | temp_byte[5] & 0xFF;
        for(int i=0; i<size; i++){
            for(int y=0; y<4; y++){

                temp_byte[counter] = (byte)((temp_byte[counter] & 0xFC) | ((text_bytes[i] >> 2*y) & 0x3));
                counter ++;
            }
        }
        BufferedOutputStream buf_out = new BufferedOutputStream(new FileOutputStream(file));
        buf_out.write(temp_byte, 0, temp_byte.length);
        buf_out.flush();
        buf_out.close();
        return true;
    }

    public String decode() throws IOException {
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        byte[] text = new byte[size];
        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
        int counter = -1;
        buf.read(bytes, 0, bytes.length);
        buf.close();

        int offset = bytes[4]<<8 | bytes[5] & 0xFF;

        for(int i=offset; i<bytes.length; i++){
            int letterCounter = (i-offset)%4;
            if(letterCounter == 0)
                counter ++;
            text[counter] |= ((bytes[i] & 0x3) << letterCounter*2);
        }
        String test = new String(text,"UTF-8");
        return test;
    }
}
