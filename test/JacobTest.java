

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JacobTest
{
     //建立一個word物件
     private ActiveXComponent MsWordApp = null;

     //建立兩個word組件
     private Dispatch document = null;
     private Dispatch selection = null;

     //建構子
     public JacobTest()
     {
         super();
     }


     /**
     * 開啟word檔案
     *
     * @param makeVisible 顯示不顯示(true:顯示;false:不顯示)
     *
     */
     public void openWord(boolean makeVisible)
     {
           //打開word(如果word未開啟時)
           if (MsWordApp == null)
           {
                  MsWordApp = new ActiveXComponent("Word.Application");
           }

           //設置word是可見或不可見(true:顯示;false:不顯示)
           Dispatch.put(MsWordApp, "Visible",new Variant(makeVisible));
     }

     /**
     * 建立word的文本內容
     *
     */
     public void createNewDocument()
     {
           //建立一個Dispatch物件
           Dispatch documents = Dispatch.get(MsWordApp,"Documents").toDispatch();
           document = Dispatch.call(documents,"Add").toDispatch();
     }

     /**
     * 格式化時間字串
     *
     *@param date_str 原始時間字串
     *@return 修改後的字串
     *
     */
     public String ReplaceDateStr(String date_str)
     {
           String str = "";
           String[] date_str_arr = Tool.splitString(date_str,"-");

           if(date_str_arr.length > 0)
           {
               str = date_str_arr[0] + "/" + date_str_arr[1] + "/" + date_str_arr[2];
           }
           else
           {
               str = date_str;
           }

           return str;
     }


     /**
     * 寫入資料到word中
     *
     * @param title 本文標題
     * @param textToInsertarr 要寫入的內容陣列集合
     *
     */
     public void insertText(String title,ArrayList textToInsertarr)
     {
           selection = Dispatch.get(MsWordApp,"Selection").toDispatch();                                               //輸入內容需要的物件

           Dispatch alignment = Dispatch.get(selection, "ParagraphFormat").toDispatch();                               //行列格式化需要的物件
           Dispatch font = Dispatch.get(selection, "Font").toDispatch();                                               //字型格式化需要的物件
           Dispatch font1 = Dispatch.get(selection, "Font").toDispatch();                                              //字型格式化需要的物件
          Dispatch image = Dispatch.get(selection, "InLineShapes").toDispatch();                                    //放入圖片需要的物件
           //String mm = "D:"+File.separator+"IRMAS_COMBINE"+File.separator+"images"+File.separator+"mis_login.jpg";   //圖片來源路徑

           //格式化時間
           java.util.Date ddate = new Date();
           SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM月-yy", java.util.Locale.TRADITIONAL_CHINESE); //oracle要的時間格式
           Timestamp ts1 = new Timestamp(ddate.getTime());
           String date_str = (ts1.toString()).substring(0,10);
           String result_str = ReplaceDateStr(date_str);       //格式化後的時間

           //文件標題
           //Dispatch.call(selection, "TypeParagraph");                  //空一行段落
           Dispatch.put(alignment, "Alignment", "1");                    //(1:置中 2:靠右 3:靠左)
           Dispatch.put(font1, "Bold", "1");                             //字型租體
           Dispatch.put(font1, "Color", "1,0,0,0");                      //字型顏色(1,0,0,0=>紅色  1,1,0,0=>棕色)
           //Dispatch.put(font, "Italic", "1");                          //字型斜體
           Dispatch.call(selection, "TypeText", title);                  //寫入標題內容

           //標題格行
           Dispatch.call(selection, "TypeParagraph");                    //空一行段落
           Dispatch.put(alignment, "Alignment", "3");                    //(1:置中 2:靠右 3:靠左)
           Dispatch.put(selection,"Text","        ");
           Dispatch.call(selection, "MoveDown");                         //游標往下一行

           //插入圖片
           /*
           Dispatch.call(selection, "TypeParagraph");
           Dispatch.put(alignment, "Alignment", "2");                    //(1:置中 2:靠右 3:靠左)
           Dispatch.call(image, "AddPicture", mm);                       //寫入圖片
           */

          /*--------不輸入到表格時------------
          //主要內容(即參數陣列中的值)
          for(int i=0;i<textToInsertarr.size();i++)
          {
               String arr_tostr = textToInsertarr.get(i).toString();
               String arr_substr = arr_tostr.substring(1,arr_tostr.length()-1);    //去掉前後'['和']'
               String[] arr_split = arr_substr.split(",");                         //字串陣列(在分隔每個元素值)

               for(int j=0;j<arr_split.length;j++)
               {
                      //主要內容
                          Dispatch.call(selection, "TypeParagraph");
                      Dispatch.put(selection, "Text", arr_split[j]);               //寫入word的內容
                          Dispatch.put(font, "Bold", "0");                             //字型租體(1:租體 0:取消租體)
                      //Dispatch.put(font, "Italic", "1");                         //字型斜體(1:斜體 0:取消斜體)
                      //Dispatch.put(font, "Underline", "1");                      //文字加底線(1:加底線 0:不加底線)
                      Dispatch.call(selection, "MoveDown");                        //游標往下一行(才不會蓋過上一輸入的位置)
               }

               //每寫入一次資料空一行,以區隔之用
                  Dispatch.call(selection, "TypeParagraph");
               Dispatch.put(selection, "Text", "                   ");
               Dispatch.call(selection, "MoveDown");                               //游標往下一行
          }
          */

             //依參數內的陣列元素總個數計算要輸入到表格的列數
                int all_count = 0;
             for(int p=0;p<textToInsertarr.size();p++)
             {
                 String arr_tostr = textToInsertarr.get(p).toString();               //先將陣列元素轉成字串
                    String arr_substr = arr_tostr.substring(1,arr_tostr.length()-1);    //去掉前後'['和']'
                 String[] arr_split = arr_substr.split(",");                         //字串陣列(在分隔每個元素值)
                 int num = arr_split.length;

                 all_count += num;   //累加個數
               }

             //建立表格
                Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
             Dispatch range = Dispatch.get(selection, "Range").toDispatch();
             Dispatch newTable = Dispatch.call(tables,"Add",range,new Variant(all_count),new Variant(1),new Variant(1)).toDispatch();  //設置列數,欄數,表格外框寬度
                Dispatch.call(selection, "MoveRight");                              //游標移到最右邊
                putTxtToCell(font,alignment,1,1,1,textToInsertarr);                 //表格內寫入內容(從第1列第1欄開始)
             //mergeCell(1,1,1,all_count,1);                                     //表格合併(從第1列第1欄開始,第X列第1欄結束)
             //autoFitTable();                                                   //自動調整表格

             int count = 0;         //計算合併表格後的列數遞增(例如:前五列合併成一列,則往下繼續合併時,要考慮加上合併後的那一列)
             //依陣列筆數合併表格
                for(int k=0;k<textToInsertarr.size();k++)
             {
                  String arr_tostr = textToInsertarr.get(k).toString();               //先將陣列元素轉成字串
                      String arr_substr = arr_tostr.substring(1,arr_tostr.length()-1);    //去掉前後'['和']'
                  String[] arr_split = arr_substr.split(",");                         //字串陣列(在分隔每個元素值)
                  int num = arr_split.length;

                  if(k == 0)
                  {   //第一次合併時,num值不需加前一列
                          mergeCell(1,k+1,1,num,1);
                      count++;
                  }
                  else
                  {   //第二次合併之後,num值要加前一列,以此類推...
                      mergeCell(1,k+1,1,num+count,1);
                      count++;
                  }
             }

           //取消選擇(因為最後insert進去的文字會顯示反白,所以要取消)
           Dispatch.call(selection,"MoveRight",new Variant(1),new Variant(1));

           //插入頁首頁尾
             //取得活動窗體對象
             Dispatch ActiveWindow = MsWordApp.getProperty("ActiveWindow").toDispatch();
           //取得活動窗格對象
             Dispatch ActivePane = Dispatch.get(ActiveWindow,"ActivePane").toDispatch();
           //取得視窗對象
             Dispatch View = Dispatch.get(ActivePane,"View").toDispatch();

           //9是設置頁首(游標所在處)
           Dispatch.put(View,"SeekView","9");                   //頁首中的資訊
             Dispatch.put(alignment, "Alignment", "2");           //(1:置中 2:靠右 3:靠左)
           Dispatch.put(selection,"Text",result_str);           //初始化時間

           //10是設置頁尾(游標所在處)
           Dispatch.put(View,"SeekView","10");                  //頁尾中的資訊
             Dispatch.put(alignment, "Alignment", "1");           //(1:置中 2:靠右 3:靠左)
           Dispatch.put(selection,"Text",new Variant(1));       //初始化從1開始
     }

    /**
    * 合併表格
     *
    * @param tableIndex 表格起始點
    * @param fstCellRowIdx 開始列
    * @param fstCellColIdx 開始欄
    * @param secCellRowIdx 結束列
    * @param secCellColIdx 結束欄
    */
    public void mergeCell(int tableIndex, int fstCellRowIdx, int fstCellColIdx,int secCellRowIdx, int secCellColIdx)
    {
        // 所有表格
          Dispatch tables = Dispatch.get(document, "Tables").toDispatch();

        // 要填充的表格
          Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex)).toDispatch();
        Dispatch fstCell = Dispatch.call(table, "Cell",new Variant(fstCellRowIdx), new Variant(fstCellColIdx)).toDispatch();
        Dispatch secCell = Dispatch.call(table, "Cell",new Variant(secCellRowIdx), new Variant(secCellColIdx)).toDispatch();

        Dispatch.call(fstCell, "Merge", secCell);
    }

     /**
    * 在指定的表格裡填入內容
     *
    * @param tableIndex 表格起始點
    * @param cellRowIdx 第幾列
    * @param cellColIdx 第幾欄
    * @param txt 內容字串陣列
    */
    public void putTxtToCell(Dispatch font,Dispatch alignment,int tableIndex, int cellRowIdx, int cellColIdx,ArrayList txt)
    {
         // 所有表格
           Dispatch tables = Dispatch.get(document, "Tables").toDispatch();

        //主要內容(即參數陣列中的值)
        for(int i=0;i<txt.size();i++)
        {
            String arr_tostr = txt.get(i).toString();                           //先將陣列元素轉成字串
               String arr_substr = arr_tostr.substring(1,arr_tostr.length()-1);    //去掉前後'['和']'
            String[] arr_split = arr_substr.split(",");                         //字串陣列(在分隔每個元素值)

            for(int j=0;j<arr_split.length;j++)
            {
                // 要填入的表格(對表格列依序填入內容),cellRowIdx++代表從第一列開始
                   Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex)).toDispatch();
                Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx++),new Variant(cellColIdx)).toDispatch();
                //Dispatch.put(cell, "Height",new Variant(1));    //設置列高
                   Dispatch.call(cell, "Select");

                   //主要內容
                      //Dispatch.call(selection, "TypeParagraph");                        //空一行段落
                      //Dispatch.put(alignment, "Alignment", "3");                        //(1:置中 2:靠右 3:靠左)
                   if(j==0)
                   {
                        Dispatch.put(selection, "Text", arr_split[j]);                      //寫入word的內容
                             Dispatch.put(font, "Bold", "1");                                    //字型租體(1:租體 0:取消租體)
                        Dispatch.put(font, "Color", "1,1,1,1");                             //字型顏色
                             //Dispatch.put(font, "Italic", "1");                                //字型斜體(1:斜體 0:取消斜體)
                        //Dispatch.put(font, "Underline", "1");                             //文字加底線
                             Dispatch.call(selection, "MoveDown");                               //游標往下一行(才不會輸入蓋過上一輸入位置)
                   }
                   else
                   {
                        if(arr_split[j].indexOf(" ") != -1)
                        {
                            String str = arr_split[j].replaceAll(" ","         ");            //作字串隔行對齊用

                            Dispatch.call(selection, "TypeParagraph");                          //空一行段落
                                 Dispatch.put(alignment, "Alignment", "3");                          //(1:置中 2:靠右 3:靠左)
                            Dispatch.put(selection, "Text", str);                               //寫入word的內容
                                 Dispatch.put(font, "Bold", "0");                                    //字型租體(1:租體 0:取消租體)
                            Dispatch.put(font, "Color", "1,1,1,0");                             //字型顏色
                                 //Dispatch.put(font, "Italic", "1");                                //字型斜體(1:斜體 0:取消斜體)
                            //Dispatch.put(font, "Underline", "1");                             //文字加底線
                                 Dispatch.call(selection, "MoveDown");                               //游標往下一行(才不會輸入蓋過上一輸入位置)
                        }
                        else
                        {
                            Dispatch.put(selection, "Text", arr_split[j]);                      //寫入word的內容
                                 Dispatch.put(font, "Bold", "0");                                    //字型租體(1:租體 0:取消租體)
                            Dispatch.put(font, "Color", "1,1,1,0"); //字型顏色
                                 //Dispatch.put(font, "Italic", "1");                                //字型斜體(1:斜體 0:取消斜體)
                            //Dispatch.put(font, "Underline", "1");                             //文字加底線
                                 Dispatch.call(selection, "MoveDown"); //游標往下一行(才不會輸入蓋過上一輸入位置)

                        }
                   }

            }

            //每寫入一次資料空一行,以區隔之用
            /*
            Dispatch.call(selection, "TypeParagraph");
            Dispatch.put(selection, "Text", "                   ");
            Dispatch.call(selection, "MoveDown");                               //游標往下一行
            */
        }
    }

     /**
     * 自動調整表格
      *
     */
     public void autoFitTable()
     {
          Dispatch tables = Dispatch.get(document, "Tables").toDispatch();

          int count = Dispatch.get(tables, "Count").toInt();   //word中的表格數量

          for(int i=0;i<count;i++)
          {
              Dispatch table = Dispatch.call(tables, "Item", new Variant(i+1)).toDispatch();
              Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
              Dispatch.call(cols, "AutoFit");
          }
    }

    /**
    * 另存檔案
     *
    * @param filename 要儲存的檔案名稱
     *
    */
     public void saveFileAs(String filename)
     {
           Dispatch.call(document,"SaveAs",filename);
     }

     /**
     * 列印word文件
      *
     */
     public void printFile()
     {
           Dispatch.call(document,"PrintOut");
     }

     /**
     * 關閉文本內容(如果未開啟word編輯時,釋放ActiveX執行緒)
     *
     */
     public void closeDocument()
     {
           // 0 = 沒有儲存改變
           // -1 = 有儲存改變
           // -2 = 提示儲存改變
           Dispatch.call(document, "Close", new Variant(0));
           document = null;
     }

     /**
     * 關閉word(如果未開啟word編輯時,釋放ActiveX執行緒)
     *
     */
     public void closeWord()
     {
           Dispatch.call(MsWordApp,"Quit");
           MsWordApp = null;
           document = null;
     }
     public static void main(String[] args) {
		JacobTest test = new JacobTest();

		test.openWord(true);
		test.createNewDocument();
		ArrayList<String> strs = new ArrayList<String>();
		strs.add("bbbbbbbbbbb");
		test.insertText("a",strs  );
		test.saveFileAs("d:\\a.doc");
	}

}


  class Tool  {
   public Tool() {

    }
   public static String [] splitString(String str ,String split){
       return str.split(split);
    }
}
