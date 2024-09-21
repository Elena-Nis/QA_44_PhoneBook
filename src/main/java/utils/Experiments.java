package utils;

public class Experiments {
    public static void main(String[] args) {
        String res = method(1, 2).toString();
    }

    public static <T> T method(Integer... per){  //method("str1", "str2", "str3")
        return (T) per[per.length-1];
    }
}
