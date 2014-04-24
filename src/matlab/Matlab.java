package matlab;

import java.util.ArrayList;
import java.util.List;

import Mat.Mat;

import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

public class Matlab {
	
	private int[][][] Tensor = null;
	private int[] size= new int[3];
	private double[][] U1=null;
	private double[][] U2=null;
	private double[][] U3=null;
	private double[][] S1=null;
	private double[][] S2=null;
	private double[][] S3=null;
	
	private MWNumericArray A=null;	
	private MWNumericArray B=null;	
	private MWNumericArray C=null;	
	private MWNumericArray SIZE=null;
	private List<String> location = null;
	private List<String> medicalname = null;
	
	private Mat matrix = null;
	
	public List<Integer> rank(double[] a,double[][] SS){
		int size = a.length;
		double max = 0 ;
		int k = 0;
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0;i<size/2+1;i++){
			for(int j=0;j<size;j++){
				if(a[j]>max && !inside(list,j))
				{
					max=a[j];
				    k = j;	
				}
			}
			list.add(k);
			max = 0;
		}
		
		double min = SS[list.get(list.size()-1)][list.get(list.size()-1)];
		
		int num1=0,num2=0;
		List<Integer> num = new ArrayList<Integer>();
		List<Integer> postion = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			if(SS[list.get(i)][list.get(i)]==min){
				num1++;
				postion.add(i);
			}
		}
		for(int i=0;i<SS.length;i++){
			if(SS[i][i] == min){
				num2++;
				num.add(i);
			}
		}
		if(num2>num1){
			int m=0;
			List<Integer> check = new ArrayList<Integer>();
			for(int i=0;i<num1;i++){
				while(inside(check,m = num.get((int)(Math.random()*num2)))){
				}
				check.add(m);
				list.set(postion.get(i),m);
			}
		}
		
		for(int i=0;i<list.size()-1;i++){
			for(int j=list.size()-1;j>i;j--){
				if(list.get(j)<list.get(j-1)){
					int t = list.get(j);
					int h = list.get(j-1);
					list.set(j,h);
					list.set(j-1,t);
				}
			}
		}
		return list;
	}
	
	public boolean inside(List<Integer> list, int a){
		int size = list.size();
		for(int i=0;i<size;i++){
			if(a == list.get(i)){
				return true;
			}
		}
		return false;
	} 
	
	public double[][] productReU(double [][] U,double[][] S){
		 
		 int size = S.length;
		 int N = U.length;
		 double [] array = new double[size];
		 for(int i=0;i<size;i++){
		    array[i] = S[i][i];
		 }
		 List<Integer> list = rank(array,S);
		 int M = list.size();
		 double [][] ReU =new double[N][M];
		 
		 for(int i=0;i<list.size();i++){
			 int k= list.get(i);
			 for(int j=0;j<N;j++){
				 ReU[j][i] = U[j][k]; 
			 }
		 }
		 return ReU;
	}
	
	public boolean check(Recommand a, List<Recommand> list){
		for(Recommand b : list){
			if((b.getL()==a.getL())&&(b.getM()==a.getM())&&(b.getN()==a.getN())){
				if(a.getSimOption()>0 && b.getSimOption()>0 && a.getSimOption()>b.getSimOption()){
					b.setSimOption(a.getSimOption());
					return true;
				}else if(a.getSimOption()>0.1 && b.getSimOption()<0 ){
					b.setSimOption(a.getSimOption());
					return true;
				}else if(a.getSimOption()<0 && b.getSimOption()<0 && a.getSimOption()<b.getSimOption()){
					b.setSimOption(a.getSimOption());
					return true;
				}else{
					return true;
				}
			}
		}
		return false;
	}
	
	public List<String> copy(List<String> a){
		List<String> array = new ArrayList<String>();
		for(String name : a){
			array.add(name);
		}
		return array;
	}
	
	public void getResource(){
		ReadExcel excel = new ReadExcel("E:/workspace/Matlab/data1.xls");
		excel.getResult();
		this.Tensor = excel.Tensor;
		int M = excel.getM();
		int N = excel.getN();
		int L = excel.getL();
		this.size[0] = M;
		this.size[1] = N; 
		this.size[2] = L;
	    this.A = new MWNumericArray(excel.modelOne, MWClassID.SINGLE);
	    this.B = new MWNumericArray(excel.modelTwo, MWClassID.SINGLE);
	    this.C = new MWNumericArray(excel.modelThree, MWClassID.SINGLE);
	    this.SIZE = new MWNumericArray(this.size, MWClassID.SINGLE);
		this.medicalname = this.copy(excel.re_medicalname);
		this.location = this.copy(excel.re_location);
		excel=null;
		
	}
	
	public void getMatrix(){
	    Object[] result = null; 
	    
	    try{
	    	matrix = new Mat();
	    	result = matrix.matrix(9,this.A,this.B,this.C);	
			this.U1 = (double[][])((MWNumericArray)result[0]).toDoubleArray();
			this.U2 = (double[][])((MWNumericArray)result[1]).toDoubleArray();
			this.U3 = (double[][])((MWNumericArray)result[2]).toDoubleArray();
			this.S1 = (double[][])((MWNumericArray)result[3]).toDoubleArray();
			this.S2 = (double[][])((MWNumericArray)result[4]).toDoubleArray();
			this.S3 = (double[][])((MWNumericArray)result[5]).toDoubleArray(); 
	    }		  
	    catch (Exception e){
			 e.getStackTrace();
	    }	
		finally
		{
		    MWNumericArray.disposeArray(B);
		    MWNumericArray.disposeArray(C);
		    MWArray.disposeArray(result);
		}
	}
	
	public List<Recommand> getResult(){
		 Object[] result = null; 
	     MWNumericArray REU1 =null;
	     MWNumericArray REU2 =null;
	     MWNumericArray REU3 =null;
		
	     List<Recommand> commandlist = null;
		 try{	 	
			 double[][] reU1 = this.productReU(U1,S1);
			 double[][] reU2 = this.productReU(U2,S2);
			 double[][] reU3 = this.productReU(U3,S3);			 

			 REU1 = new MWNumericArray(reU1, MWClassID.DOUBLE);
			 REU2 = new MWNumericArray(reU2, MWClassID.DOUBLE);
			 REU3 = new MWNumericArray(reU3, MWClassID.DOUBLE);
			 reU1 = null;
			 reU2 = null;
			 reU3 = null;
			 
			 result = this.matrix.tensorsvd(1,this.SIZE,this.A,REU1,REU2,REU3);
			 double[][][] SimTensor =(double[][][])((MWNumericArray)result[0]).toDoubleArray();
			 result=null;
			 
			 commandlist = new ArrayList<Recommand>();
			 for(int i=0;i<SimTensor[0][0].length;i++){
				 for(int j=0;j<SimTensor.length;j++){
					 for(int k=0;k<SimTensor[0].length;k++){
						if((SimTensor[j][k][i]!=(double)this.Tensor[i][j][k])&&(SimTensor[j][k][i]>0.1)&&(this.Tensor[i][j][k]<0.001))
						{
							commandlist.add(new Recommand(i,j,k,SimTensor[j][k][i]));
						}	
					 }
				 }
			 }	 
		  }
		  catch (Exception e){
			 e.getStackTrace();
	      }
		 
		  finally
		  {
		    MWArray.disposeArray(result);
			MWNumericArray.disposeArray(REU1);
			MWNumericArray.disposeArray(REU2);
			MWNumericArray.disposeArray(REU3);
		  }
		return commandlist;
	}
	
	public static void main(String[] args)
	{
	 try{
		Matlab matlab = new Matlab();
		matlab.getResource();
		matlab.getMatrix();
		List<Recommand> parm = new ArrayList<Recommand>();
		List<Recommand> result = new ArrayList<Recommand>();
		int count = 5;
		while(count-- >0){
			parm = matlab.getResult();
			for(Recommand c : parm){
				if(!matlab.check(c,result)){
					result.add(c);
				}
			}
			parm.clear();
		}
		for(Recommand i : result){
			System.out.print((i.getL()+1)+" "+matlab.location.get(i.getM())+" "+matlab.medicalname.get(i.getN())+" "+i.getSimOption()+"\n");
		}
		System.out.print(result.size()+"\n");
	 }catch(Exception e){
		 e.getStackTrace();
	 }
    }
}

class Recommand{
	private int M;
	private int N;
	private int L;
	private double SimOption;
	
	public Recommand(int a,int b,int c,double d){
		this.L = a;
		this.M = b;
		this.N = c;
		this.SimOption = d;
	}
	public int getM(){
		return this.M;
	}
	public int getN(){
		return this.N;
	}
	public int getL(){
		return this.L;
	}
	public double getSimOption(){
		return this.SimOption;
	}
	public void setSimOption(double s){
		this.SimOption = s;
	}
}