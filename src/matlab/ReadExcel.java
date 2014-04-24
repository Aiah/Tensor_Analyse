package matlab;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {
	
	public int Tensor[][][] = null;
	public int modelOne[][] = null;
	public int modelTwo[][] = null;
	public int modelThree[][] = null;
	

	private List<String> medicalname = new ArrayList<String>(); //药名
	private List<String> location = new ArrayList<String>();  //地理名
	private List<Integer> count = new ArrayList<Integer>();  //分数
	
	public List<String> re_medicalname = new ArrayList<String>();  //去重后的药品
	public List<Integer> re_count = new ArrayList<Integer>();  //去重后的药品
	public List<String> re_location = new ArrayList<String>();  //去重后的药品
	
	private int countsize;
	private int locationsize;
	private int medicalnamesize;
	private String URL= new String();
	
	
	public ReadExcel(String URL){
		this.URL = URL;
	}
	
	public void readexcel(String path) throws IOException, BiffException{
		Workbook workbook = Workbook.getWorkbook(new File(path));
		Sheet st = workbook.getSheets()[0];
			for (int i = 0; i < st.getRows(); i++) {
				Cell c0 = st.getCell(0, i);
				Cell c1 = st.getCell(1, i);
				Cell c2 = st.getCell(2, i);
				String strc0 = c0.getContents().trim();
				String strc1 = c1.getContents().trim();
				String strc2 = c2.getContents().trim();
				medicalname.add(strc0);
				count.add(Integer.parseInt(strc1.substring(0,strc1.indexOf(' '))));
				location.add(strc2);	
		    }
		workbook.close();
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

	public void getTensorsize(){
		this.re_count = removeDuplicateWithOrder(this.count);
		this.re_medicalname = removeDuplicateWithOrder(this.medicalname);
		this.re_location = removeDuplicateWithOrder(this.location);
		this.countsize = re_count.size();
		this.medicalnamesize = re_medicalname.size();
		this.locationsize = re_location.size();
	}
	
	
	public void maketensor(){
		this.Tensor = new int [this.countsize][this.locationsize][this.medicalnamesize];
        for(int i=0;i<this.countsize;i++){
        	for(int j=0;j<this.locationsize;j++){
        		for(int k=0;k<this.medicalnamesize;k++){
        			Tensor[i][j][k]=0;
        		}
        	}
        }
        
        for(int i=0;i<medicalname.size();i++){
			int L = 0;
			int M = 0;
			int N = 0;
			for(L=0;L<countsize;L++){
				if(count.get(i)== (L+1)){
					break;
				}
			}	
			for(N=0;N<medicalnamesize;N++){
				if(medicalname.get(i).equals(re_medicalname.get(N))){
					break;
				}
			}
			for(M=0;M<locationsize;M++){
				if(location.get(i).equals(re_location.get(M))){
					break;
				}
			}
			Tensor[L][M][N]++;
        }
	}
	
	
	public int[][] getModelOne(int Tensor[][][]){
		int L=Tensor.length;
		int M=Tensor[0].length;
		int N=Tensor[0][0].length;
		int [][] modelone = new int[M][L*N];
		for(int i=0;i<L;i++){
			for(int j=0;j<N;j++){
				for(int k=0;k<M;k++){
					modelone[k][i*N+j] = Tensor[i][k][j];
				}
			}
		}
		return modelone;
	}
	
	public int[][] getModelTwo(int Tensor[][][]){
		int L=Tensor.length;
		int M=Tensor[0].length;
		int N=Tensor[0][0].length;
		int [][] modeltwo = new int[N][L*M];
		for(int i=0;i<L;i++){
			for(int j=0;j<M;j++){
				for(int k=0;k<N;k++){
					modeltwo[k][i*M+j] = Tensor[i][j][k];
				}
			}
		}
		return modeltwo;
	}
	
	public int[][] getModelThree(int Tensor[][][]){
		int L=Tensor.length;
		int M=Tensor[0].length;
		int N=Tensor[0][0].length;
		int [][] modelthree = new int[L][M*N];
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				for(int k=0;k<L;k++){
					modelthree[k][i*M+j] = Tensor[k][j][i];
				}
			}
		}
		return modelthree;
	}
	
	public void getResult(){
		try{
			this.readexcel(this.URL);
			this.getTensorsize();
			this.maketensor();
			this.modelOne = this.getModelOne(this.Tensor);
			this.modelTwo = this.getModelTwo(this.Tensor);
			this.modelThree = this.getModelThree(this.Tensor);
			this.medicalname.clear();
			this.count.clear();
			this.location.clear();
		}
		catch(IOException e){
			e.getStackTrace();
		}
		catch(BiffException e){
			e.getStackTrace();
		}
	}	

	public int getM(){
		return this.Tensor[0].length;
	}
	
	public int getN(){
		return this.Tensor[0][0].length;
	}
	
	public int getL(){
		return this.Tensor.length;
	}
}
