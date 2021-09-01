package com.ykt.ykt2020OPP.shortDay10;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music {
	static{
		try {
			//输入流
			FileInputStream fis =new FileInputStream("src/Against the Current - Legends Never Die.mp3");
			//缓冲流
			BufferedInputStream bis=new BufferedInputStream(fis);
			Player p=new Player(bis);
			//播放
			p.play();
			System.out.println("音乐播放中。。。");
		} catch (Exception e) {
			throw new RuntimeException("播放失败。。");
		}
	}
	public static void main(String[] args) {
		Music m=new Music();
	}
}
