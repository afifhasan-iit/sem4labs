
class DemoString{
    String str;

    private  DemoString(String str){
        this.str=str;
    }

    public static class DemoStringBuilder{
        String str="";

        DemoStringBuilder(){

        }

        DemoStringBuilder(String str){
            this.str = str;
        }

        DemoStringBuilder append(String str){
            this.str += str;
            return this;
        }
        DemoStringBuilder reverse(){
            String temp="";
            for (int i = str.length() - 1; i >= 0; i--) {
                temp += str.charAt(i);
            }
            str=temp;
            return this;
        }
        DemoStringBuilder insert(int index, char c){

            String result = "";

            for (int i = 0; i < str.length(); i++) {
                if (i == index) {
                    result += c;
                }
                result += str.charAt(i);
            }

            if (index == str.length()) {
                result += c;
            }
            str = result;
            return this;
        }
        DemoStringBuilder insert( String str){
            this.str  += str;
            return this;
        }

        DemoStringBuilder delete(int i,int j){
                String result = "";

                for (int k = 0; k < str.length(); k++) {
                    if (k < i || k >= j) {
                        result += str.charAt(k);
                    }
                }

                str = result;
                return this;
        }

        DemoString build(){
            return new DemoString(str);
        }


        }
    }




public class Main  {
    public static void main(String[] args) {
        DemoString str = new DemoString.DemoStringBuilder()
                .insert("hello world")
                .append(" everyone")
                .build();
        System.out.println(str.str);
    }
}