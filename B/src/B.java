import java.util.LinkedList;
import java.util.Scanner;
class graph{
    static int sour, dest;//sour是图形的初始整数，dest是图形的目的整数
    static int ans[]=new int[1<<16];//静态变量（即全局变量），用于存放图形变换的路径
    int m=4,n=4,x;
    int row[]=new int[4];
    int col[]=new int[4];
    void setx(int x){
        this.x=x;
    }
    int getx(){
        return this.x;
    }
    void rowx(){//将一个整数划分成四行二进制
        int y;
        for(int i=0;i<m;i++){
            y=1;
            row[i]=0;
            for(int j=0;j<n;j++){
                if((x&1)!=0) //如果x的最低位是1
                    row[i]|=y;
                y<<=1;
                x>>=1;
            }
        }
    }
    void colx(){//将一个整数划分成四列二进制
        int y;
        for(int j=0;j<n;j++) col[j]=0;
        y=1;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if((x&1)!=0) //如果x的最低位是1
                    col[j]|=y;
                x>>=1;
            }
            y<<=1;
        }
    }
    void rowy(){//将四行二进制转换成一个整数
        int  z=1, x=0, y;
        for(int i=0;i<m;i++){
            y=row[i];
            for(int j=0;j<n;j++){
                if((y&1)!=0) //如果y的最低位是1
                    x|=z;
                z<<=1;
                y>>=1;
            }
        }
        this.x=x;
    }
    void coly(){//将四列二进制转换成一个整数
        int  z=1, x=0, y;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if((col[j]&1)!=0) //如果y的最低位是1
                    x|=z;
                z<<=1;
                col[j]>>=1;
            }
        }
        this.x=x;
    }
    void Swaprow(int i, int j){//将二进数进行行互换
        int o;
        o=row[i];
        row[i]=row[j];
        row[j]=o;
    }
    void Swapcol(int i, int j){//将二进数进行列互换
        int o;
        o=col[i];
        col[i]=col[j];
        col[j]=o;
    }
    void reveR(int k){//将二进数进行行颠倒
        int y=0, z=1<<(4-1);
        for(int j=0;j<4;j++){
            if((row[k]&1)!=0) //如果y的最低位是1
                y|=z;
            z>>=1;
            row[k]>>=1;
        }
        row[k]=y;
    }
    void reveC(int k){//将二进数进行列颠倒
        int y=0, z=1<<(4-1);
        for(int j=0;j<4;j++){
            if((col[k]&1)!=0) //如果y的最低位是1
                y|=z;
            z>>=1;
            col[k]>>=1;
        }
        col[k]=y;
    }
    int rowswap(int x, int i, int j){//将二进制数的第i行与第j行互换
        this.x=x;
        rowx();
        Swaprow(i,j);
        rowy();
        return this.x;
    }
    int colswap(int x, int i, int j){//将二进制数的第i列与第j列互换
        this.x=x;
        colx();
        Swapcol(i,j);
        coly();
        return this.x;
    }
    int revrow(int x, int k){//将二进制数的第K行颠倒
        this.x=x;
        rowx();
        reveR(k);
        rowy();
        return this.x;
    }
    int revcol(int x, int k){//将二进制数的第K列颠倒
        this.x=x;
        colx();
        reveC(k);
        coly();
        return this.x;
    }
}
public class B {
    public static void main(String[] args){
        final int Maxsize=1<<16;
        graph  gN;//用于进行行变换、列变换、行颠倒、列颠倒
        int E,N;//变换前的初始值，变换前的结果值
        gN=new graph();
        int hash[]=new int[1<<16];
        int i,j,h=0;char c;
        graph G1=new graph();
//初始化，输入初始值和目标值,即1010010000101010和1010000001100101
        Scanner scanner = new Scanner(System.in);
        String ss=scanner.nextLine();
        char[]chArrs=ss.toCharArray();
        for(graph.sour=i=0;i<16;i++){
            c=chArrs[i];
            graph.sour|=(int)(c-'0')<<i;
        }
        String sd=scanner.nextLine();
        char[]chArrd=sd.toCharArray();
        for(graph.dest=i=0;i<16;i++){
            c=chArrd[i];
            graph.dest|=(int)(c-'0')<<i;
        }
        LinkedList  queue=new LinkedList();//初始化先进先出队列
        for(int k=0; k<Maxsize;k++)hash[k]=-1;
        G1.x=graph.sour;
        hash[G1.x]=0;
        queue.add(G1.x);
        while(!queue.isEmpty()){//以先进先出队列式实现分支限界法
            E=(int)queue.removeFirst();
            for(i=0;i<4-1;i++){//行变换
                for(j=i+1;j<4;j++){
                    gN.x=gN.rowswap(E,i,j);
                    N=gN.x;
                    if(hash[N]==-1){
                        hash[N]=hash[E]+1;
                        graph.ans[N]=E;
                        queue.add(N);
                    }
                }
            }
            for(i=0;i<4-1;i++){//列变换
                for(j=i+1;j<4;j++){
                    gN.x=gN.colswap(E,i,j);
                    N=gN.x;
                    if(hash[N]==-1){
                        hash[N]=hash[E]+1;
                        graph.ans[N]=E;
                        queue.add(N);
                    }
                }
            }
            for(i=0;i<4;i++){//行颠倒
                gN.x=gN.revrow(E,i);
                N=gN.x;
                if(hash[N]==-1){
                    hash[N]=hash[E]+1;
                    graph.ans[N]=E;
                    queue.add(N);
                }
            }
            for(i=0;i<4;i++){//列颠倒
                gN.x=gN.revcol(E,i);
                N=gN.x;
                if(hash[N]==-1){
                    hash[N]=hash[E]+1;
                    graph.ans[N]=E;
                    queue.add(N);
                }
            }
            if(hash[graph.dest]!=-1){//如果目的值被遍历到，则退出循环
                System.out.println("OK");break;
            }
        }
        System.out.println(hash[graph.dest]);
        output(graph.dest);//输出变换的路径
    }
    public static void outb(int x){//将一个整数以四行二进制的形式显示
        for(int i=0; i<4;i++){
            for(int j=0;j<4;j++){
                if((x&1)!=0)System.out.print(1);
                else System.out.print(0);
                x/=2;
            }
            System.out.println();
        }
    }
    public static void output(int N){
        if(N==graph.sour){
            System.out.println();
            outb(N);
            return;
        }
        output(graph.ans[N]);//graph.ans[N]存放着从初始值到目的值的遍历路径
        System.out.println();
        outb(N);
    }
}
//1010010000101010
//1010000001100101