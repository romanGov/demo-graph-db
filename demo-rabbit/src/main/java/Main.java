import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        hse(365);
    }
    public static void hse(int value){
        int x = value;
        int l = 0;
        int m = 1;
        while(x>0){
            l++;
            if(x%2 > 0){
                int resultMod = x % 8;
                m=m* resultMod;

            }
            x = x/8;
        }
        if(l==4) return;
        if(l==3 && m==25 ){
            System.out.println("value -> "+value);
            int newValue = value+1;
            hse(newValue);
        }else{
            int newValue = value+1;
            hse(newValue);
        }
    }
    public static void calc(int value){
        int a = 0;
        int b = 0;
        int firstNum = value;
        while(firstNum>0){
            if (firstNum%2 == 0){
                a++;
            }else{
                b=b+firstNum%8;
            }

            System.out.println("a->" +a);
            System.out.println("b->" +b);

            firstNum = firstNum/8;
            System.out.println("firstNum->" +firstNum);
            System.out.println("----------");
        }
        System.out.println("a->"+a);
        System.out.println("b->"+b);
        if(a==1 && b==10) {
            System.out.println("value is -> " +value);
        }else{
            value++;
            calc(value);
        }

    }
}
