package com.sangxiang.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] payload;

    public MyHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            char[] chBuff = new char[512];
            int readBytes = -1;
            while ((readBytes = reader.read(chBuff)) != -1) {
                //log.info(String.format("read from instream: %d", readBytes));
                stringBuffer.append(chBuff, 0, readBytes);
            }
            payload = stringBuffer.toString().getBytes();
            //log.info(String.format("Http Request Body: \n %s", stringBuffer.toString()));
        } catch (IOException e) {
            //log.error("Read Http InputStream Error: ", e);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            private int pos = 0;

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
//                log.info(String.format("pos = %d", this.pos));
                if (pos >= payload.length) {
                    return -1;
                }
                byte ch = payload[pos];
                pos = pos + 1;
                return ch;
            }
        };

    }
}
