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
        byte[] temp_byte = new byte[1];
        byte[] text_bytes = text.getBytes();
        BufferedOutputStream buf_out = new BufferedOutputStream(new FileOutputStream(file));
        BufferedInputStream buf_in = new BufferedInputStream(new FileInputStream(file));

        int counter = 0;
        for(int i=0; i<size; i++){
            for(int y=0; y<4; y++){
                buf_in.read(temp_byte, counter, 1);
                temp_byte[0] = (byte)((temp_byte[0] & 0xFC) | ((text_bytes[i] >> 2*y) & 0x3));
                buf_out.write(temp_byte, counter, 1);
                counter ++;
            }
        }
        return true;
    }

    public String decode() throws IOException {
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        byte[] text = new byte[size];
        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
        int counter = 0;

        buf.read(bytes, 0, bytes.length);

        for(int i=0; i<bytes.length; i++){
            int letterCounter = i%4;
            if(letterCounter == 0)
                counter ++;
            text[counter] |= (bytes[i] & 0x3) << letterCounter;
        }
        return text.toString();
    }
}
