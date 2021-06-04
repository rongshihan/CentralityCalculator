package Tool;

import java.io.*;
import java.util.ArrayList;

public class ReadTxt {

    public String[][] Read(String path){
        String result[][] = null;

        File file = new File(path);
        BufferedReader br = null;

        String s = null;// 每行的字符串
        ArrayList<String> list = new ArrayList<>();// 得到的所有字符串

        int row = 0;// 读取的行数
        int column;// 每行非空元素个数
        StringBuilder sb = new StringBuilder();// 用于连接字符
        try {
            br = new BufferedReader(new FileReader(file));

            while ((s = br.readLine()) != null){
                row++;
                list.add(s);
            }
            br.close();

            result = new String[row][row];

            for (int i = 0;i < list.size();i++){
                s = list.get(i);
                column = -1;
                for (int j = 0;j < s.length();j++) {
                    char temp = s.charAt(j);
                    if (temp != ' ') {
                        sb.append(temp);
                    } else {
                        column++;
                        result[i][column] = sb.toString();
                        sb.delete(0, sb.length());
                    }
                }
                column++;
                result[i][column] = sb.toString();
                sb.delete(0, sb.length());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
