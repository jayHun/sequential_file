package sequence2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

// Performing Insert, Change, Delete function
// Filemanager System
// Authorized by hun

public class sequence2 {

	public static void main(String[] args) throws IOException {

		int n = 0;							// �Է¹޴� Ʈ����� ���ڵ� ��
		int transchk = 0;					// n�� Ʈ����� ���ڵ� �� �� ���� ���� ��츦 ���ϱ� ���� ����
		String [] trans = new String[7];	// Ʈ������� �Ӽ������� �����ϴ� �迭
		String str = null;
		Scanner tr = new Scanner(System.in);
		FileWriter fwin = new FileWriter("./input.txt");	// input.txt ���� ���� & ����
		FileWriter fwout = new FileWriter("./output.txt");	// output.txt ���� ���� & ����
		
		// Ʈ����� �� n �Է� �� ����ó��
		n = tr.nextInt();
		if(n<1 || n>100){
			System.out.println(">> 1���� 100������ Ʈ������� �Է��ϼ���.");
			tr.close();
			fwin.close();
			fwout.close();
			System.out.println(">> �ý��� ����");
			return;
		}
		
		fwin.write(Integer.toString(n) + "\r\n");
		
		// table ��ü �迭 ���� �� �ʱ�ȭ
		table[] table = new table[n];
		for(int i=0; i<n; i++){
			table[i] = new table("null", "null", "null", "null", "null", "null");
		}
		
		// Ʈ����� ���� �� �˻�
		try{
			while(true){
				str = tr.next();
				// n�� Ʈ����� ���� ���� ������� ����ó��
				if(transchk+1 > n){
					System.out.println(n + "���� ���� Ʈ������� �Է��Ͽ����ϴ�.");
					break;
				}
				fwin.write(str+"\r\n");    // ���� �Է¹��� ��ɾ input.txt���� ���ο� ����
				trans = str.split(",");    // ���� �Է¹��� ��ɾ ,������ ��� trans �迭�� ���� (index: 0~6)
				
				if(trans[0].equals("I")){
					checkInsert(table, trans, transchk);
				}else if(trans[0].equals("C")){
					checkChange(table, trans, transchk);
				}else if(trans[0].equals("D")){
					checkDelete(table, trans, transchk);
				}else
					fwin.write("�������� �ʴ� ����Դϴ�.\r\n");
				transchk++;
			}
			sortFile(table, fwout);			// �й� ������ ���ڵ带 �����Ͽ� output.txt�� ����ϴ� sortFile �޼��� ȣ��
			
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println(transchk + "���� ���� Ʈ������� �Է��Ͽ����ϴ�.");
		}finally{
			System.out.println(">> �ý��� ����");
		}
		tr.close();    // scanner ��ü �ݱ�
		fwin.close();  // input.txt ���� �ݱ�
		fwout.close(); // output.txt ���� �ݱ�
	}
	
	// ���� ���ڵ� �� ī���� �޼���
	public static int countRecord(table[] table){
		int cnt=0;
		for(int i=0; i<table.length; i++){
			if(table[i].id.equals("null")){
				continue;
			}else{
				cnt++; continue;
			}
		}
		return cnt;
	}
	
	// ���� ����� ���ڵ带 �й� ���� �������� �����ϴ� �޼��� 
	public static void sortFile(table[] table, FileWriter fwout) throws IOException{
		int[] pnumarray = new int[countRecord(table)];
		int cnt=0;
		for(int i=0; i<table.length; i++){
			if(table[i].id.equals("null")){
				continue;
			}else{
				pnumarray[cnt] = Integer.parseInt(table[i].getId());
				cnt++;
			}
		}
		Arrays.sort(pnumarray);		// �й��� �������� �������� ����
		for(cnt=0; cnt<pnumarray.length; cnt++){
			for(int i=0; i<table.length; i++){
				if(table[i].id.equals(Integer.toString(pnumarray[cnt]))){
					fwout.write(table[i].getId() + " " + table[i].getName() + " " + table[i].getMajor() + " " + 
							table[i].getGrade() + " " + table[i].getPnum() + " " + table[i].getGender() + "\r\n");
					break;
				}else continue;
			}
		}
	}
	
	// ���� üũ & ���� ���� �޼���
	public static void checkInsert(table[] table, String [] trans, int transchk){ 
		for(int i=0; i<table.length; i++){
			if(table[i].id.equals("null")){
				table[i].setId(trans[1]);
				table[i].setName(trans[2]);
				table[i].setMajor(trans[3]);
				table[i].setGrade(trans[4]);
				table[i].setPnum(trans[5]);
				table[i].setGender(trans[6]);
				return;
			}
			// Ʈ����� ���ڵ��� �й��� table ��ü �迭 ���� �й� �� �� ����ó��
			else if(table[i].id.equals(trans[1])){
				System.out.println((transchk+1) + "��° Ʈ�����(����) ���� ����[�ߺ��� Ű ���� ���ڵ�]");
				return;
			}else{
				continue;
			}
		}
	}
	
	// ���� üũ & ���� ���� �޼���
	public static void checkChange(table[] table, String [] trans, int transchk){
		for(int i=0; i<table.length; i++){
			if(table[i].id.equals("null") || !table[i].id.equals(trans[1])){
				continue;
			}else if(table[i].id.equals(trans[1])){
				table[i].setName(trans[2]);
				table[i].setMajor(trans[3]);
				table[i].setGrade(trans[4]);
				table[i].setPnum(trans[5]);
				table[i].setGender(trans[6]);
				return;
			}
		}
		System.out.println((transchk+1) + "��° Ʈ�����(����) ���� ����[������ Ű ���� ���ڵ�]");
	}
	
	// ���� üũ & ���� �޼���
	public static void checkDelete(table[] table, String [] trans, int transchk){
		for(int i=0; i<table.length; i++){
			if(table[i].id.equals("null") || !table[i].id.equals(trans[1])){
				continue;
			}else if(table[i].id.equals(trans[1])){
				table[i].setId("null");
				table[i].setName("null");
				table[i].setMajor("null");
				table[i].setGrade("null");
				table[i].setPnum("null");
				table[i].setGender("null");
				return;
			}
		}
		System.out.println((transchk+1) + "��° Ʈ�����(����) ���� ����[������ Ű ���� ���ڵ�]");
	}
	
	// ���̺� �ʵ� ���� Ŭ����
	static class table{
		private String id;
		private String name;
		private String major;
		private String grade;
		private String pnum;
		private String gender;
		
		// ��ü ������ ȣ��Ǵ� ������
		private table(String id, String name, String major, String grade, String pnum, String gender){
			this.id = id;
			this.name = name;
			this.major = major;
			this.grade = grade;
			this.pnum = pnum;
			this.gender = gender;
		}
		
		// set�޼���
		protected void setId(String id){
			this.id = id;
		}
		protected void setName(String name){
			this.name = name;
		}
		protected void setMajor(String major){
			this.major = major;
		}
		protected void setGrade(String grade){
			this.grade = grade;
		}
		protected void setPnum(String pnum){
			this.pnum = pnum;
		}
		protected void setGender(String gender){
			this.gender = gender;
		}
		
		// get�޼���
		protected String getId(){
			return id;
		}
		protected String getName(){
			return name;
		}
		protected String getMajor(){
			return major;
		}
		protected String getGrade(){
			return grade;
		}
		protected String getPnum(){
			return pnum;
		}
		protected String getGender(){
			return gender;
		}
	}
}