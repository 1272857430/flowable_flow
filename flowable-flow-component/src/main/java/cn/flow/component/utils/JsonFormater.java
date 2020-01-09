package cn.flow.component.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JsonFormater {
    public static String format(String jsonStr){
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(jsonStr.getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            char ch;
            int read;
            int space=0;
            while((read = in.read()) > 0){
                ch = (char)read;
                switch (ch){
                    case '{': {
                        space = outputAndRightMove(space, ch, out);
                        break;
                    }
                    case '[': {
                        out.write(ch);
                        space += 2;
                        break;
                    }
                    case '}': {
                        space = outputAndLeftMove(space, ch, out);
                        break;
                    }
                    case ']': {
                        space = outputAndLeftMove(space, ch, out);
                        break;
                    }
                    case ',': {
                        out.write(ch);
                        outputNewline(out);
                        out.write(getBlankingStringBytes(space));
                        break;
                    }
                    default: {
                        out.write(ch);
                        break;
                    }
                }
            }
            return out.toString();
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    private static int outputAndRightMove(int space, char ch, ByteArrayOutputStream out) throws IOException {
        //换行
        outputNewline(out);
        //向右缩进
        out.write(getBlankingStringBytes(space));
        out.write(ch);
        outputNewline(out);
        space += 2;
        //再向右缩进多两个字符
        out.write(getBlankingStringBytes(space));
        return space;
    }
    private static int outputAndLeftMove(int space, char ch, ByteArrayOutputStream out) throws IOException{
        outputNewline(out);
        space -= 2;
        out.write(getBlankingStringBytes(space));
        out.write(ch);
        return space;
    }
    private static byte[] getBlankingStringBytes(int space){
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < space; i++) {
            sb.append(" ");
        }
        return sb.toString().getBytes();
    }

    public static void outputNewline(ByteArrayOutputStream out){
        out.write('\n');
    }
}
