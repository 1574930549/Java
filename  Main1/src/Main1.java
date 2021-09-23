import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        List<Integer> list=new ArrayList<>();
        Scanner sc=new Scanner(System.in);
        int total=sc.nextInt();//有1-N个学生
        int countNum=sc.nextInt();//从1数到countNum时，指定的同学出局
        int arr[]=new int[total];
        for(int i=0;i<arr.length;i++) {
            arr[i]=i+1;
        }
        int left=total;
        int index=0;
        int count=0;
        while(left>0) {//有N个学生，一定是需要出局N次
            if(arr[index]!=0) {
                count++;
                if(count==countNum) {
                    list.add(arr[index]);
                    arr[index]=0;//当有学生出局时指定的值赋为0；
                    count=0;//报数重新开始
                    left--;//剩余left个学生出局
                }
            }
            index++;
            if(index==total) {//当最后一个学生报完数，从第一个学生重新开始报
                index=0;
            }

        }

        System.out.println(list);
    }
}