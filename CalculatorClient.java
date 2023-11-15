import java.io.*;
import java.net.Socket;

public class CalculatorClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.print("원하는 계산을 입력하세요 (예: ADD/SUB/MUL/DIV 10 20) : ");
            String expression = userInput.readLine();

            // 서버로 계산식 전송
            writer.println(expression);

            // 서버로부터 결과 수신
            String result = serverInput.readLine();
            System.out.println(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
