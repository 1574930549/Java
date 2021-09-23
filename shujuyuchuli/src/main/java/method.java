public class method {
    //private int f00=0,f01=0,f10=0,f11=0;
    private int A[][]=new int[][]{{0,0,0,1,1,0},{1,1,0,1,0,1}};
    private int B[][]=new int[][]{{3,5,2,6,7},{1,2,3,4,5}};
    private int C[][]=new int[][]{{4,0,3,2,7},{0,1,5,8,3}};
    //    public void sumA(){
//        int j=0;
//        for(int i=0;i<6;i++)
//        {
//            if(A[j][i]==A[j+1][i]&&A[j][i]==0){
//                f00++;
//            }
//            if(A[j][i]!=A[j+1][i]&&A[j][i]==0){
//                f01++;
//            }
//            if(A[j][i]!=A[j+1][i]&&A[j][i]==1){
//                f10++;
//            }
//            if(A[j][i]==A[j+1][i]&&A[j][i]==1){
//                f11++;
//            }
//        }
//    }
    public float Smc(int f00,int f01,int f10,int f11){
        float smc,smc1;
        smc=(f11+f00);
        smc1=smc/(f01+f10+f11+f00);
        return smc1;
    }
    public double distB(){
        double sumb=0;
        double distb;
        double sb=1;
        int xb=0;
        for (int yb=0;yb<5;yb++)
        {
            sumb=sumb+(B[xb][yb]-B[xb+1][yb])*(B[xb][yb]-B[xb+1][yb]);

        }
        distb=Math.sqrt(sumb);
        sb=sb/(1+distb);
        return sb;
    }
    public double smcC(){
        int xc=0;
        double sumc1=0;
        double sumc2=0;
        double sumc3=0;
        double sumc=0;
        double sum=0;
        for (int yc=0;yc<5;yc++)
        {
            sumc1=sumc1+C[xc][yc]*C[xc+1][yc];
        }
        xc=0;
        for (int yc=0;yc<5;yc++){
            sumc2=sumc2+C[xc][yc]*C[xc][yc];
        }
        for (int yc=0;yc<5;yc++){
            sumc3=sumc3+C[xc+1][yc]*C[xc+1][yc];
        }
        sumc=Math.sqrt(sumc2)*Math.sqrt(sumc3);
        sum=sumc1/sumc;
        return sum;
    }
    public double sim(double a,double b,double c,double wa,double wb,double wc)
    {
        double sim=wa*a+wb*b+wc*c;
        return sim;
    }
    public static void main(String[] args) {
        double sA=0;
        double sB=0;
        double sC=0;
        double sim=0;
        double wa;
        double wb;
        double wc;
        method m=new method();
        int j=0;
        int f00=0,f01=0,f10=0,f11=0;
        for(int i=0;i<6;i++)
        {
            if((m.A[j][i]==m.A[j+1][i])&&m.A[j][i]==0){
                f00++;
                //System.out.println("赋值成功");
            }
        }
        j=0;
        for(int i=0;i<6;i++)
        {
            if((m.A[j][i]!=m.A[j+1][i])&&m.A[j][i]==0&&m.A[j+1][i]==1){
                f01++;
                System.out.println("赋值成功");
            }

        }
        j=0;
        for(int i=0;i<6;i++)
        {
            if((m.A[j][i]!=m.A[j+1][i])&&m.A[j][i]==1){
                f10++;
                //System.out.println("赋值成功");
            }
        }
        j=0;
        for(int i=0;i<6;i++)
        {
            if((m.A[j][i]==m.A[j+1][i])&&m.A[j][i]==1){
                f11++;
                //System.out.println("赋值成功");
            }
        }
        System.out.println("f00="+f00);
        System.out.println("f01="+f01);
        System.out.println("f10="+f10);
        System.out.println("f11="+f11);
        sA=m.Smc(f00,f01,f10,f11);
        System.out.println("A数据集通过简单匹配系数获得smc值为"+sA);
        sB=m.distB();
        System.out.println("B数据集通过欧式距离系数获得smc值为"+sB);
        sC=m.smcC();
        System.out.println("C数据集通过余弦距离系数获得smc值为"+sC);
        sim=m.sim(sA,sB,sC,0.3,0.2,0.5);
        System.out.println("三个数据集的相似性为"+sim);
    }
}
