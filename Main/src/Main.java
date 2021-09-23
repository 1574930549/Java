import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] card  = new int[N];
        int sum = 0;
        int count = 0;//移动次数
        int v = 0;//平均数
        for (int i = 0; i < N; i++) {
            card[i] = sc.nextInt();
            sum += card[i];
        }
        v = sum/N;
        for (int i = 0; i < card.length; i++) {
            if (card[i] - v != 0) {
                card[i+1] = card[i+1] + card[i] - v ;
                count++;
            }
        }
        System.out.println(count);
    }
}