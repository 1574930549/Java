package tianping;
public class tianping {
    public static void main(String[] args) {
        int m = 27; //全部钩码的重量之和的二分之一
        int n = 9; //钩码的数量
        int a[] = {10,9,8,7,6,5,4,3,2};
        int h[] =new int[100];
        h[0]=1;
        for (int i = 1; i <=n; i++) {
            for (int j = m; j >=1; j--) {
                if(j>=a[i-1]){
                    h[j]=h[j]+h[j-a[i-1]];
                }
            }
        }
        for (int j = 0; j <=m; j++)
            System.out.print(h[j]+" ");
    }
}