package ArcusJavaClient.ChannelTest;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileReadExample {
    public static void main(String[] args) {

        Path path = Paths.get("./exampleTestData/temp.txt");
        // 채널 객체를 파일 읽기 모드로 생성합니다.
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.READ)) {
            // 1024 바이트 크기를 가진 Buffer 객체 생성
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            ch.read(buffer);

            buffer.flip();
            Charset charset = Charset.defaultCharset();
            String inputData = charset.decode(buffer).toString();
            System.out.println("inputData: " + inputData);

            buffer.clear();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("파일 작업 실패");
        }
    }
}
