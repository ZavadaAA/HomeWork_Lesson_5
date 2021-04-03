package Lesson_5;
import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void writeFile(AppData appData, File file){
        try (PrintWriter out = new PrintWriter(file)) {
            int a = 0;
            String[] header = appData.getHeader();
            for (String v : header){
                out.print(v);
                if (a < header.length - 1) out.print(";");
                a++;
            }
            out.println();

            int[][] data = appData.getData();
            for (int i = 0; i < data.length; i++){
                for (int j = 0; j < data[i].length; j++){
                    out.print( data[i][j]);
                    if (j < header.length - 1) out.print(";");
                }

                out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AppData readFile(File file) {
        AppData appData = new AppData();

        ArrayList<int[]> dataList = new ArrayList<int[]>();
        try{
            BufferedReader in = new BufferedReader(new FileReader(file));
            String[] header = in.readLine().split(" ; ");
            appData.setHeader(header);

            String readLine = null;
            while ( (readLine = in.readLine()) != null) {
                String[] strLine = readLine.split(" ; ");


                int[] intLine = new int[strLine.length];
                for (int i = 0; i < strLine.length; i++){
                    intLine[i] = Integer.parseInt(strLine[i]);
                }
                dataList.add(intLine);
            }

            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        int[][] data = new int[dataList.size()][];
        for(int i = 0; i < dataList.size(); i++ ){
            data[i] = dataList.get(i);
        }
        appData.setData(data);
        return appData;
    }


    public static void main(String args[]) {
        File file = new File("my_file.csv");
        try{
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] header = {"Value1","Value2","Value3"};
        //q-ty of rows
        int r = 3;
        int[][] data = new int[r][3];
        for(int i = 0; i < r; i++){
            data[i][0] = 100 + i;
            data[i][1] = 200 + i;
            data[i][2] = 300 + i;
        }

        AppData appData = new AppData(header, data);

        writeFile(appData, file);

        AppData readAppData = readFile(file);

        writeFile(readAppData, new File("new_file.txt"));

    }
}