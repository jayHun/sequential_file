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

		int n = 0;							// 입력받는 트랜잭션 레코드 수
		int transchk = 0;					// n과 트랜잭션 레코드 수 가 같지 않을 경우를 비교하기 위한 변수
		String [] trans = new String[7];	// 트랜잭션의 속성값들을 저장하는 배열
		String str = null;
		Scanner tr = new Scanner(System.in);
		FileWriter fwin = new FileWriter("./input.txt");	// input.txt 파일 생성 & 오픈
		FileWriter fwout = new FileWriter("./output.txt");	// output.txt 파일 생성 & 오픈
		
		// 트랜잭션 수 n 입력 및 예외처리
		n = tr.nextInt();
		if(n<1 || n>100){
			System.out.println(">> 1부터 100까지의 트랜잭션을 입력하세요.");
			tr.close();
			fwin.close();
			fwout.close();
			System.out.println(">> 시스템 종료");
			return;
		}
		
		fwin.write(Integer.toString(n) + "\r\n");
		
		// table 객체 배열 선언 및 초기화
		table[] table = new table[n];
		for(int i=0; i<n; i++){
			table[i] = new table("null", "null", "null", "null", "null", "null");
		}
		
		// 트랜잭션 생성 및 검사
		try{
			while(true){
				str = tr.next();
				// n과 트랜잭션 수가 같지 않을경우 예외처리
				if(transchk+1 > n){
					System.out.println(n + "보다 많은 트랜잭션을 입력하였습니다.");
					break;
				}
				fwin.write(str+"\r\n");    // 현재 입력받은 명령어를 input.txt파일 라인에 저장
				trans = str.split(",");    // 현재 입력받은 명령어를 ,단위로 끊어서 trans 배열에 저장 (index: 0~6)
				
				if(trans[0].equals("I")){
					checkInsert(table, trans, transchk);
				}else if(trans[0].equals("C")){
					checkChange(table, trans, transchk);
				}else if(trans[0].equals("D")){
					checkDelete(table, trans, transchk);
				}else
					fwin.write("존재하지 않는 명령입니다.\r\n");
				transchk++;
			}
			sortFile(table, fwout);			// 학번 순으로 레코드를 정렬하여 output.txt에 출력하는 sortFile 메서드 호출
			
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println(transchk + "보다 많은 트랜잭션을 입력하였습니다.");
		}finally{
			System.out.println(">> 시스템 종료");
		}
		tr.close();    // scanner 객체 닫기
		fwin.close();  // input.txt 파일 닫기
		fwout.close(); // output.txt 파일 닫기
	}
	
	// 실질 레코드 수 카운팅 메서드
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
	
	// 최종 저장된 레코드를 학번 기준 오름차순 정렬하는 메서드 
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
		Arrays.sort(pnumarray);		// 학번을 기준으로 오름차순 정렬
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
	
	// 삽입 체크 & 삽입 실행 메서드
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
			// 트랜잭션 레코드의 학번과 table 객체 배열 내의 학번 비교 및 예외처리
			else if(table[i].id.equals(trans[1])){
				System.out.println((transchk+1) + "번째 트랜잭션(삽입) 실행 실패[중복된 키 값의 레코드]");
				return;
			}else{
				continue;
			}
		}
	}
	
	// 수정 체크 & 수정 실행 메서드
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
		System.out.println((transchk+1) + "번째 트랜잭션(수정) 실행 실패[누락된 키 값의 레코드]");
	}
	
	// 삭제 체크 & 실행 메서드
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
		System.out.println((transchk+1) + "번째 트랜잭션(삭제) 실행 실패[누락된 키 값의 레코드]");
	}
	
	// 테이블 필드 정의 클래스
	static class table{
		private String id;
		private String name;
		private String major;
		private String grade;
		private String pnum;
		private String gender;
		
		// 객체 생성시 호출되는 생성자
		private table(String id, String name, String major, String grade, String pnum, String gender){
			this.id = id;
			this.name = name;
			this.major = major;
			this.grade = grade;
			this.pnum = pnum;
			this.gender = gender;
		}
		
		// set메서드
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
		
		// get메서드
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