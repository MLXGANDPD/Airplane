package com.ykt.ykt2020OPP.shortDay10;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music {
	static{
		try {
			//������
			FileInputStream fis =new FileInputStream("src/Against the Current - Legends Never Die.mp3");
			//������
			BufferedInputStream bis=new BufferedInputStream(fis);
			Player p=new Player(bis);
			//����
			p.play();
			System.out.println("���ֲ����С�����");
		} catch (Exception e) {
			throw new RuntimeException("����ʧ�ܡ���");
		}
	}
	public static void main(String[] args) {
		Music m=new Music();
	}
}
