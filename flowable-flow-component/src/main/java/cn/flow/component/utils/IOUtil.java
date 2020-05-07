package cn.flow.component.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class IOUtil {

    public static byte[] getInputStreamBytes(InputStream inputStream){
        if (Objects.isNull(inputStream)) {
            return null;
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); InputStream is = inputStream) {
            byte[] buffer = new byte[1024];
            int n;
            while (-1 != (n = is.read(buffer))) {
                outputStream.write(buffer, 0, n);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
