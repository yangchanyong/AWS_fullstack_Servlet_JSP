package test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileTest {
	public static void main(String[] args) throws IOException {
//		System.out.println(File.separator); \ 출력
//		System.out.println(File.pathSeparator); ; 출력
		
		// c드라이브 root
//		File file = new File("c:" + File.separator);
//		System.out.println(file);
//		// c드라이브는 디렉토리이기 때문에 true가 나온다ㅣ
//		System.out.println(file.isDirectory());
//		System.out.println(file.isFile());
		
		// 파일 인스턴스 생성
//		File file = new File("abcd");
//		System.out.println(file.exists());
//		System.out.println(file.createNewFile());
//		
//		File file2 = new File("abcde");
//		System.out.println(file2.exists());
//		// abcde 폴더 생성
//		System.out.println(file2.mkdir());
		
		// c:/upload/2023/03/14 폴더 생성
		// test.txt
//		File file = new File("c:/upload/2023/03/14");
//		File file = new File("c:/upload/", new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
//		file.mkdirs();
//		
//		File file2 = new File(file, "test.txt");
//		file2.createNewFile();
		
		// file 탐색
//		File file = new File("c:/workspaces/workspace-web");
//		File[] files = file.listFiles();
//		for(File f : files) {
////			System.out.println(f);
////			file의 시간정보 확인
//			System.out.print(new SimpleDateFormat("yyyy-MM-dd a HH:mm:ss").format(f.lastModified())); 
//			if(f.isDirectory()) {
//				System.out.print("         <DIR>             ");
//			}
//			if(f.isFile()) {
//				System.out.print("          " + f.length() + "bytes");
//			}
//			System.out.println("       " +f.getName());
//		}
//		String str = "a.b.c.txt";
		String str = "abcde";
		System.out.println(str.substring(str.lastIndexOf(".")));
		
//		System.out.println(new File("c:/upload", "abcd.txt").getPath());
	}
}
