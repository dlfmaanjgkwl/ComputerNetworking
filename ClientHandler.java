import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String expression = reader.readLine();
            System.out.println("계산식: " + expression);

            try {
                double result = evaluateExpression(expression);
                writer.println("결과: " + result);
            } catch (ArithmeticException e) {
                writer.println("오류: " + e.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private double evaluateExpression(String expression) {
        String[] tokens = expression.split(" ");
        String operator = tokens[0];
        double operand1 = Double.parseDouble(tokens[1]);
        double operand2 = Double.parseDouble(tokens[2]);

        switch (operator) {
            case "ADD":
                return operand1 + operand2;
            case "SUB":
                return operand1 - operand2;
            case "MUL":
                return operand1 * operand2;
            case "DIV":
                if (operand2 == 0) {
                    throw new ArithmeticException("0으로 나눌 수 없습니다.");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("지원하지 않는 연산자입니다.");
        }
    }
}
