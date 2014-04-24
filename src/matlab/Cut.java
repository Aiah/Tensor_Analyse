package matlab;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Cut {
	private String URL = new String();
	private List<Object>  alList = new ArrayList<Object>();
	private List<String>  location = new ArrayList<String>();
	
	public Cut(String URL){
		this.URL = URL;
	}
	
	public void readexcel() throws IOException, BiffException{
		Workbook workbook = Workbook.getWorkbook(new File(this.URL));
		Sheet st = workbook.getSheets()[0];
			int b=0;
			for (int i = 0; i < st.getRows(); i++) {
				List<String>  al = new ArrayList<String>();
				for (int j = 0; j < st.getColumns(); j++) {
				      Cell c00 = st.getCell(j, i);
				      // 通用的获取cell值的方式,返回字符串
				      String strc00 = c00.getContents().trim();
				      al.add(j,strc00);
				}
				alList.add(b,al);
				b++;
		    }
		workbook.close();
	}
	
	public void collection(){
		int k = alList.size();		
		for(int i=0 ; i<k ; i++){
			List<String> list = (List<String>)alList.get(i);	
			location.add(list.get(2));		
		}
		this.location = removeDuplicateWithOrder(location);
	}
	
	public List removeDuplicateWithOrder(List list) {
        HashSet set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
	}
	
	public int random(){
		return (int)(Math.random()*3);
	}
	
    public  void writeExcel(String fileName, List<Object> list1,List<Object> list2){
        WritableWorkbook wwb = null;
        try {
            //首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
            wwb = Workbook.createWorkbook(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(wwb!=null){
            //创建一个可写入的工作表
            //Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
            WritableSheet ws = wwb.createSheet("medical", 0);
            
            //下面开始添加单元格
            int k = list1.size();
            for(int i=0;i<list1.size();i++){
            	List<String> line = (List<String>)list1.get(i);
            	int Cols = line.size();
                for(int j=0;j<Cols;j++){
                    //这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
                	Label label = new Label(j,i,line.get(j));  
                    try {           	
                        ws.addCell(label);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            
            for(int i=0;i<list2.size();i++){
            	List<String> line = (List<String>)list2.get(i);
            	int Cols = line.size();
                for(int j=0;j<Cols;j++){
                    //这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
                	Label label = new Label(j,i+k+1,line.get(j));  
                    try {           	
                        ws.addCell(label);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            

            try {
                wwb.write();
                wwb.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } 

	public static int[] randomCommon(int min, int max, int n){  
	    if (n > (max - min + 1) || max < min) {  
	           return null;  
	       }  
	    int[] result = new int[n];  
	    int count = 0;  
	    while(count < n) {  
	        int num = (int) (Math.random() * (max - min)) + min;  
	        boolean flag = true;  
	        for (int j = 0; j < n; j++) {  
	            if(num == result[j]){  
	                flag = false;  
	                break;  
	            }  
	        }  
	        if(flag){  
	            result[count] = num;  
	            count++;  
	        }  
	    }
	    if(n==1){
	    	return result;  
	    }else{
	    	for(int i=0;i<n-1;i++){
	    		for(int j=0;j<n-1-i;j++){
	    			if(result[j]>result[j+1]){
	    				int c=result[j];
	    				result[j] = result[j+1];
	    				result[j+1] = c;
	    			}
	    		}
	    	}
	    	return result;
	    }
	}
	
    public  void writeExcel(String fileName, List<Object> list1){
        WritableWorkbook wwb = null;
        try {
            //首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
            wwb = Workbook.createWorkbook(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(wwb!=null){
            //创建一个可写入的工作表
            //Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
            WritableSheet ws = wwb.createSheet("medical", 0);
            
            //下面开始添加单元格
            int k = list1.size();
            for(int i=0;i<list1.size();i++){
            	List<String> line = (List<String>)list1.get(i);
            	int Cols = line.size();
                for(int j=0;j<Cols;j++){
                    //这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
                	Label label = new Label(j,i,line.get(j));  
                    try {           	
                        ws.addCell(label);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                wwb.write();
                wwb.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
     
	
	public static void main(String[] args){
		 List<Integer>  intarray = new ArrayList<Integer>();
		 List<Object>  List1 = new ArrayList<Object>();
		 List<Object>  List2 = new ArrayList<Object>();
		 
		Cut cut = new Cut("E:/workspace/Matlab/medical2.xls");
		
		/*
		try{
			cut.readexcel();
			cut.collection();
			int M = cut.alList.size();
			int N = cut.location.size();
			for(int i=0;i<N;i++){
				int start=-1,end=-1;
				boolean st = false;
				for(int j=0;j<M;j++){
					List<String> loc = (List<String>)cut.alList.get(j);
					if(cut.location.get(i).equals(loc.get(2))){
						if(!st){
							start = j;
							st=true;
						}
						else{
							end = j;
						}
					}
				}
			
				if(end!=-1 && start!=-1 && end>start){
					int[] array = null;
					if(end-start ==1 && cut.random()==0){
						array = randomCommon(start,end,1);
					}
					else if(end-start == 2)
					{
					    array = randomCommon(start,end,1);
					}
					else if((end-start>=3)&&(end-start<=4)){
						array = randomCommon(start,end,2);
					}
					else if(end-start==5 || end-start==6){
						array = randomCommon(start,end,3);
					}
					else{
						array = randomCommon(start,end,(end-start+1)/2);
					}
					for(int p=0;p<array.length;p++){
						intarray.add(array[p]);
					}
				}
			}
			int count=0;
			for(int i=0;i<cut.alList.size();i++){
				if(count<intarray.size() && intarray.get(count).equals(i)){
					List2.add(cut.alList.get(i));
					count++;
				}else{
					List1.add(cut.alList.get(i));
				}
			}
			System.out.print("\n"+List1.size()+" "+List2.size()+"\n");
			cut.writeExcel("E:/workspace/Matlab/data1.xls", List1,List2);
			
		}
		*/
		try{
			cut.readexcel();
			int M = cut.alList.size();
			int[] num = randomCommon(0,M-1,120); 
			int count=0;
			for(int i=0;i<M;i++){
				if(count<num.length && num[count]==i){
					List1.add(cut.alList.get(i));
					count++;
				}
			}
			cut.writeExcel("E:/workspace/Matlab/data2.xls",List1);
		}
		catch(Exception e){
			e.getStackTrace();
		}
	} 
}
