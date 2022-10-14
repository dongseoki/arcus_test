package ArcusJavaClient.ChannelTest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ChannelWriteExam {

    public static void main(String[] args) {

        Path path = Paths.get("./exampleTestData/output.txt");

        try (FileChannel ch = FileChannel.open(path,
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE)) {

            String data = "NIO Channel을 이용해서 파일에 데이터를 써보겠습니다.";
            Charset charset = Charset.defaultCharset();
            ByteBuffer buffer = charset.encode(data);
            ch.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

