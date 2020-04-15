package com.example.devoir_corentin_legall;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Coder {
    private File file;

    public Coder(String path){
        this.file = new File(path);
    }

    public boolean encode(String text) throws IOException {

        int size = text.length();
        byte[] temp_byte = new byte[(int)file.length()];
        byte[] text_bytes = text.getBytes();

        BufferedInputStream buf_in = new BufferedInputStream(new FileInputStream(file));
        int read = buf_in.read(temp_byte, 0, temp_byte.length);
        if(read < 0) {
            return false;
        }
        
        buf_in.close();

        int counter;
        if(temp_byte[0] == (byte)0x89 && temp_byte[1] == (byte)0x50 && temp_byte[2] == (byte)0x4E && temp_byte[3] == (byte)0x47)
            counter = 33;
        else
            counter = (temp_byte[4]<<8 | temp_byte[5] & 0xFF) & 0xFFFF;

        for(int i=0; i<size; i++){
            for(int y=0; y<4; y++){

                temp_byte[counter] = (byte)((temp_byte[counter] & (byte)0xFC) + ((text_bytes[i] >> 2*y) & (byte)0x3));
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

        int offset;

        if(bytes[0] == (byte)0x89 && bytes[1] == (byte)0x50 && bytes[2] == (byte)0x4E && bytes[3] == (byte)0x47)
            offset = 33;
        else
            offset =( bytes[4]<<8 | bytes[5] & 0xFF) & 0xFFFF;

        for(int i=offset; i<bytes.length; i++){
            int letterCounter = (i-offset)%4;
            if(letterCounter == 0)
                counter ++;
            text[counter] |= ((bytes[i] & 0x3) << letterCounter*2);
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
