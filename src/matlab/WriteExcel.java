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

public class WriteExcel {

	private String URL = new String();
	private List<Object>  alList = new ArrayList<Object>();
	private List<Object>  alListfull = new ArrayList<Object>();
	
	private List<String> medicalname = new ArrayList<String>(); //ҩ��
	private List<String> location = new ArrayList<String>();  //������
	private List<String> count = new ArrayList<String>();  //����
	
	public List<String> re_medicalname = new ArrayList<String>();  //ȥ�غ��ҩƷ
	public List<String> re_count = new ArrayList<String>();  //ȥ�غ��ҩƷ
	public List<String> re_location = new ArrayList<String>();  //ȥ�غ��ҩƷ
	
	
	public WriteExcel(String URL){
		this.URL = URL;
	}
	
	public void readexcel() throws IOException, BiffException{
		Workbook workbook = Workbook.getWorkbook(new File(this.URL));
		Sheet st = workbook.getSheets()[0];
			int b=0;
			boolean mark = false;
			for (int i = 0; i < st.getRows(); i++) {
				List<String>  al = new ArrayList<String>();
				mark = false;
				for (int j = 0; j < st.getColumns(); j++) {
				      Cell c00 = st.getCell(j, i);
				      // ͨ�õĻ�ȡcellֵ�ķ�ʽ,�����ַ���
				      String strc00 = c00.getContents().trim();
				      if(strc00.length()==0){
				    	  mark = true;
				    	  break;
				      }
				      al.add(j,strc00);
				}
				if(mark){
					al.clear();
				}else{
					alList.add(b,al);
					b++;
				}	
		    }
		workbook.close();
		for(int i=0;i<alList.size();i++){
			List<String> line = (List<String>)alList.get(i);
			String medicalname = line.get(0).toLowerCase();
			String locationname = line.get(2).toLowerCase();
			if(locationname.contains(",")){
				int index = locationname.indexOf(",");
				locationname = locationname.substring(0,index);
			}
			line.set(0,medicalname);
			line.set(2,locationname);
			alList.set(i,line);
		}
	}

	public void readexcelfull() throws IOException, BiffException{
		Workbook workbook = Workbook.getWorkbook(new File(this.URL));
		Sheet st = workbook.getSheets()[0];
			int b=0;
			for (int i = 0; i < st.getRows(); i++) {
				List<String>  al = new ArrayList<String>();
				for (int j = 0; j < st.getColumns(); j++){
				      Cell c00 = st.getCell(j, i);
				      // ͨ�õĻ�ȡcellֵ�ķ�ʽ,�����ַ���
				      String strc00 = c00.getContents().trim();
				      if(strc00.length()==0){
				    	  al.add(j,"!");
				    	  continue;
				      }
				      al.add(j,strc00);
				}
				alListfull.add(b,al);
				b++;
		    }
		workbook.close();
		for(int i=0;i<alListfull.size();i++){
			List<String> line = (List<String>)alListfull.get(i);
			String medicalname = line.get(0).toLowerCase();
			String locationname = line.get(2).toLowerCase();
			if(locationname.contains(",")){
				int index = locationname.indexOf(",");
				locationname = locationname.substring(0,index);
			}
			line.set(0,medicalname);
			line.set(2,locationname);
			alListfull.set(i,line);
		}
	}
	
	public void collection(){
		int k = alListfull.size();
		for(int i=0 ; i<k ; i++){
			List<String> list = (List<String>)alListfull.get(i);
			if(!list.get(0).equals("!")){
				medicalname.add(list.get(0));
			}
			if(!list.get(1).equals("!")){
				count.add(list.get(1));
			}
			if(!list.get(2).equals("!")){
				location.add(list.get(2));		
			}
		}
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
	
	public void getContent(){
		this.re_count = removeDuplicateWithOrder(this.count);
		this.re_medicalname = removeDuplicateWithOrder(this.medicalname);
		this.re_location = removeDuplicateWithOrder(this.location);
	}
	
	public int random(int k){
		return (int)(Math.random()*k);
	}
	
    public  void writeExcel(String fileName){
        WritableWorkbook wwb = null;
        try {
            //����Ҫʹ��Workbook��Ĺ�����������һ����д��Ĺ�����(Workbook)����
            wwb = Workbook.createWorkbook(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(wwb!=null){
            //����һ����д��Ĺ�����
            //Workbook��createSheet������������������һ���ǹ���������ƣ��ڶ����ǹ������ڹ������е�λ��
            WritableSheet ws = wwb.createSheet("medical", 0);
            
            //���濪ʼ��ӵ�Ԫ��
            for(int i=0;i<alList.size();i++){
            	List<String> line = (List<String>)alList.get(i);
            	int Cols = line.size();
                for(int j=0;j<Cols;j++){
                    //������Ҫע����ǣ���Excel�У���һ��������ʾ�У��ڶ�����ʾ��
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

    public  void writeExcelfull(String fileName){
        WritableWorkbook wwb = null;
        try {
            //����Ҫʹ��Workbook��Ĺ�����������һ����д��Ĺ�����(Workbook)����
            wwb = Workbook.createWorkbook(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(wwb!=null){
            //����һ����д��Ĺ�����
            //Workbook��createSheet������������������һ���ǹ���������ƣ��ڶ����ǹ������ڹ������е�λ��
            WritableSheet ws = wwb.createSheet("medical", 0);
            
            //���濪ʼ��ӵ�Ԫ��
            for(int i=0;i<alListfull.size();i++){
            	List<String> line = (List<String>)alListfull.get(i);
            	int Cols = line.size();
                for(int j=0;j<Cols;j++){
                    //������Ҫע����ǣ���Excel�У���һ��������ʾ�У��ڶ�����ʾ��
                	Label label = null;
                	if(line.get(j).equals("!")){
                		if(j==0){
                		  int p = random(this.re_medicalname.size());
                		  label = new Label(j,i,this.re_medicalname.get(p)); 
                		}
                		else if(j==1){
                		  int p = random(this.re_count.size());
                		  label = new Label(j,i,this.re_count.get(p));  
                		}
                		else if(j==2){
                		  int p = random(this.re_location.size());
                		  label = new Label(j,i,this.re_location.get(p));  
                		}
                	}else{
                		  label = new Label(j,i,line.get(j));
                	}
                	
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
    
    public static void main(String[] agrs){
    	WriteExcel excel = new WriteExcel("E:/workspace/Matlab/medical.xls");
    	try{
    		excel.readexcel();
    		excel.readexcelfull();
    		excel.collection();
    		excel.getContent();
    		//excel.writeExcel("E:/workspace/Matlab/medical1.xls");
    		excel.writeExcelfull("E:/workspace/Matlab/medical2.xls");
    	}catch(Exception e){
    		e.getStackTrace();
    	}
    }
}
