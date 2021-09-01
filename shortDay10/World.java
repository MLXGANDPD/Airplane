package com.ykt.ykt2020OPP.shortDay10;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import day09.music;

public class World extends JPanel{
	/*
	 * Jpanel是Java图形用户界面（GUI）工具包swing中的面板容器类，
	 * 包含在javax.swing包中，是一种轻量级容器,
	 * 可以加入到JFrame相框中
	 */
	public static final int WIDTH=450;//窗口的宽
	public static final int HEIGHT=650;//窗口的高。
	private FlyingObject[] enemies={};
	private Sky sky=new Sky();
	private Bullet bullet=new Bullet(200,150);
	private Hero hero=new Hero();
	private Bullet[] bullets={};
	private int score;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	public static BufferedImage victory;
	public static final int START=0;
	public static final int PAUSE=1;
	public static final int RUNNING=2;
	public static final int GAME_OVER=3;
	public static final int VICTORY=4;
	public int state=START;//默认状态
	
	private Boss boss=new Boss();
	private Bossbullet[] leftbossbullet={};
	private Bossbullet[] bossbullet={};
	private Bossbullet[] rightbossbullet={};
	
	private long timeindex=0;
	private long time=0;
	
	static{//图片初始化;
		start=FlyingObject.loadImage("start.png");
		pause=FlyingObject.loadImage("pause.png");
		gameover=FlyingObject.loadImage("gameover.png");
		victory=FlyingObject.loadImage("victory.png");
	}
	//测试方法
	//public void action(){
//		FlyingObject[] fly={new Airplane(),new BigAirplane(),new Helicopter()};
//		for(int i=0;i<fly.length;i++){
//			fly[i].step();
//		}
//		
//		FlyingObject f=new Airplane();
//		f.getImage();
//	}
	//生成敌机
	private FlyingObject nextone(){
		Random random=new Random();
		int type=random.nextInt(21);
		if(type<8){
			return new Airplane();
		}else if(type<14){
			return new BigAirplane();
		}else{
			return new Helicopter();
		}
		
	}
	//敌人入场
	private int enemiesindex=0;
	private void enemiesAction(){
		enemiesindex++;
		if(enemiesindex%40==0){//每隔400ms生成一个对象
			//对敌人数组enemies得扩容
			enemies=Arrays.copyOf(enemies, enemies.length+1);
			FlyingObject f=nextone();//生成一个敌人对象
			enemies[enemies.length-1]=f;
			//System.out.println(enemies.length);
		}
	}
	
	
	//子弹入场
	private int index=0;
	private void bulletAction(){
		index++;
		if(index%30==0){//每隔300ms生成对象
			Bullet[] bs=hero.heroshoot();
			//对数组进行扩容
			bullets=Arrays.copyOf(bullets, bullets.length+bs.length);
			//数组的复制（把生成的子弹撞到新数组bullets中）
			System.arraycopy(bs,0, bullets, bullets.length-bs.length, bs.length);
			//System.out.println(bullets.length);
		}
	}
	
	//英雄机和子弹相撞
	private void bulletbangAction(){
		//遍历所有子弹
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			//遍历所有敌机
			for(int j=0;j<enemies.length;j++){
				FlyingObject f=enemies[j];
				//判断每个子弹是否与我们得敌机相撞
				if(b.islife()&&f.islife()&&f.hit(b)){
					b.godead();//改编子弹对象是死得状态
					f.godead();//改编敌机对象是死得状态
					if(f instanceof Award){
						Award a=(Award)f;
						int type=a.getaward();
						switch (type) {
						case Award.LIFE:
							//英雄级加命
							hero.addlife();
							break;

						case Award.FIRE://奖励火力值
							hero.adddoublefire();
							break;
						}
					}
					if(f instanceof Score){
						Score s=(Score)f;
						score+=s.getScore();//得分
					}
				}
			}
		}
	}
	
	//英雄机子弹和boss相撞
	private void herobulletboss(){
		//遍历英雄机子弹
		for(int i=0;i<bullets.length;i++){
			Bullet f=bullets[i];
			if(boss.islife()&&f.islife()&&f.hit(boss)){
				f.godead();//英雄机子弹去死
				boss.subbosslife();//boss减命
				if(boss.getbosslife()<=0){
					boss.godead();//boss去死
					state=GAME_OVER;//游戏结束
				}
			}
		}
	}
	
	
	
	//英雄机子弹和boss左子弹相撞
	private void herobulletbossbulletleft(){
		//遍历所有英雄机子弹
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			//遍历所有boss左子弹
			for(int j=0;j<leftbossbullet.length;j++){
				Bossbullet f=leftbossbullet[j];
				if(b.islife()&&f.islife()&&b.hit(f)){
					f.godead();//英雄机子弹去死
					b.godead();//boss左子弹去死
					hero.addlife();//英雄机加命
				}
			}
		}
	}
	
	//英雄机子弹和boss中间子弹相撞
	private void herobulletbossbullet(){
		//遍历所有英雄机子弹
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			//遍历所有boss中间子弹
			for(int j=0;j<bossbullet.length;j++){
				Bossbullet f=bossbullet[j];
				if(b.islife()&&f.islife()&&b.hit(f)){
					f.godead();//英雄机子弹去死
					b.godead();//boss中间子弹去死
					hero.addlife();//英雄机加命
				}
			}
		}
	}
	
	//英雄机子弹和boss右子弹相撞
	private void herobulletbossbulletright(){
		//遍历所有英雄机子弹
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			//遍历所有boss右子弹
			for(int j=0;j<rightbossbullet.length;j++){
				Bossbullet f=rightbossbullet[j];
				if(b.islife()&&f.islife()&&b.hit(f)){
					f.godead();//英雄机子弹去死
					b.godead();//boss右子弹去死
					hero.addlife();//英雄机加命
				}
			}
		}
	}
	
	//英雄机和敌机相撞
	private void herobangaction(){
		//遍历所有敌机
		for(int i=0;i<enemies.length;i++){
			FlyingObject f=enemies[i];
			if(hero.islife()&&f.islife()&&f.hit(hero)){
				f.godead();//改变敌机状态为死
				//英雄机减命
				hero.sublife();
				System.out.println(hero.getlife());
				//英雄机活力清空
				hero.cleaedoublefire();
			}
		}
	}
	
	//英雄机和boss相撞
	private void herobangboss(){
		if(hero.hit(boss)){
			hero.sublife();//英雄机减命
			hero.cleaedoublefire();//英雄机火力清空
		}
	}
	
	//英雄机和boss左子弹相撞
	private void herobossbulletleft(){
		//遍历所有左子弹
		for(int i=0;i<leftbossbullet.length;i++){
			Bossbullet p=leftbossbullet[i];
			if(hero.islife()&&p.islife()&&hero.hit(p)){
				p.godead();//boss子弹去死
				hero.cleaedoublefire();//英雄机火力值清空
				hero.sublife();//英雄机减命
			}
		}
	}
	
	
	//英雄机和boss中间子弹相撞
	private void herobossbullet(){
		//遍历所有左子弹
		for(int i=0;i<bossbullet.length;i++){
			Bossbullet p=bossbullet[i];
			if(hero.islife()&&p.islife()&&hero.hit(p)){
				p.godead();//boss中间子弹去死
				hero.cleaedoublefire();//英雄机火力值清空
				hero.sublife();//英雄机减命
			}
		}
	}
	
	//英雄机和boss右子弹相撞
	private void herobossbulletright(){
		//遍历所有左子弹
		for(int i=0;i<rightbossbullet.length;i++){
			Bossbullet p=rightbossbullet[i];
			if(hero.islife()&&p.islife()&&hero.hit(p)){
				p.godead();//boss右子弹去死
				hero.cleaedoublefire();//英雄机火力值清空
				hero.sublife();//英雄机减命
			}
		}
	}	
	
	
	
	//判断英雄机是否死亡
	public void checkhgameoveraction(){
		if(hero.getlife()<=0){
			state=GAME_OVER;
		}
	}
	
	
	public void action(){
		//获取侦听器
		MouseAdapter m=new MouseAdapter() {
			//鼠标移动事件
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING){
					//移动鼠标x移动
					//移动鼠标x移动
					int x=e.getX();
					int y=e.getY();
					hero.herostep(x, y);
					//System.out.println("x"+x+",y"+y);
				}
			}
			//鼠标点击事件
			 public void mouseClicked(MouseEvent e) {
				 if(state==START){
					 hero=new Hero();
					 sky=new Sky();
					 boss=new Boss();
					 leftbossbullet=new Bossbullet[0];
					 bossbullet=new Bossbullet[0];
					 rightbossbullet=new Bossbullet[0];
					 score=0;
					 time=0;
					 enemies=new FlyingObject[0];
					 bullets=new Bullet[0];
					 state=RUNNING;
				 }
				 if(state==GAME_OVER){
					 state=START;
				 }
			 }
			 //鼠标移除状态
			 public void mouseExited(MouseEvent e) {
				 if(state==RUNNING){
					 state=PAUSE;
				 }
			 }
			 //鼠标移入状态
			 public void mouseEntered(MouseEvent e) {
				 if(state==PAUSE){
					 state=RUNNING;
				 }
			 }
		};
		//鼠标操作事件
		this.addMouseListener(m);
		//鼠标滑动事件
		this.addMouseMotionListener(m);
		
		
		//创建定时器
		Timer timer=new Timer();
		//timer.schedule(执行任务，时间)
		int interval=10;
		timer.schedule(new TimerTask() {
			//重写timerTask抽象类中的run（）方法；
			public void run() {
				if(state==RUNNING){
					stepaction();//飞行物移动
					enemiesAction();//敌人入场
					bulletAction();//子弹入场
					bulletbangAction();//英雄机子弹和敌机相撞
					herobangaction();//英雄级与敌机相撞
					checkhgameoveraction();//英雄机死亡，游戏结束
					flyingobjectoutaction();//飞行物越界（内存泄漏问题）
					
					
					if(time>=5){//5s之后boss出现
						leftbossbulletaction();//左边子弹入场
						bossbulletaction();//中间子弹入场
						rightbossbulletaction();//右边子弹入场
						//英雄机子弹和boss相撞
						herobulletboss();
						//英雄机子弹和boss子弹相撞
						herobulletbossbulletleft();//boss左子弹
						herobulletbossbullet();//boss中间子弹
						herobulletbossbulletright();//boss右子弹
						//英雄机和boos相撞
						herobangboss();
						//英雄机和boss子弹相撞
						herobossbulletleft();//左子弹
						herobossbullet();//左子弹
						herobossbulletright();//左子弹
						//boss子弹越界问题处理
						bossbulletout();
					}
					
					if(timeindex++%100==0){
						time+=1;
					}
					
				}
				repaint();//repaint是jpanel中得方法，它是重画，重写调用paint（）；
				
			}

		}, interval,interval);//第一个interval是10ms后执行任务，第二个interval是每隔10ms执行一次
	}
	
	//飞行物移动
	private void stepaction(){
		sky.step();//移动背景
		//遍历敌机得移动行为
		for(int i=0;i<enemies.length;i++){
			enemies[i].step();
		}
		//遍历每颗子弹得移动行为
		for(int i=0;i<bullets.length;i++){
			bullets[i].step();
		}
		
		if(time>=5){
			boss.step();//boss移动
			
			//遍历boss左子弹移动
			for(int i=0;i<leftbossbullet.length;i++){
				leftbossbullet[i].leftstep();
			}
			
			
			//遍历boss中间子弹移动
			for(int i=0;i<bossbullet.length;i++){
				bossbullet[i].step();
			}
			
			
			//遍历boss右子弹移动
			for(int i=0;i<rightbossbullet.length;i++){
				rightbossbullet[i].rightstep();
			}
		}
		
	}
	
	public void paint(Graphics g){
		sky.paintobject(g);//画天空
		
		//遍历敌机的移动行为（画）
		for(int i=0;i<enemies.length;i++){
			enemies[i].paintobject(g);
			}
		//遍历每颗子弹的移动行为（画）
		for(int i=0;i<bullets.length;i++){
			bullets[i].paintobject(g);
		}
		hero.paintobject(g);//画英雄机
		
		if(time>=5){
			boss.paintobject(g);//画boss
			
			//画boss左子弹
			for(int i=0;i<leftbossbullet.length;i++){
				leftbossbullet[i].paintobject(g);
			}
			
			//画boss中间子弹
			for(int i=0;i<bossbullet.length;i++){
				bossbullet[i].paintobject(g);
			}
			
			//画boss右子弹
			for(int i=0;i<rightbossbullet.length;i++){
				rightbossbullet[i].paintobject(g);
			}
			
		}
		

		//设置字体大小和样式
		Font font=new Font("楷体",Font.TYPE1_FONT,25);
		g.setFont(font);
		//设置画笔得颜色
		g.setColor(Color.gray);
		//根据x，y坐标话字符
		g.drawString("LIFE "+hero.getlife(), 15, 30);
		g.drawString("FIRE "+hero.doublefire(), 15, 55);
		g.drawString("SCORE "+score, 15, 80);
		g.drawString("BOSSLIFE "+boss.getbosslife(), 15, 105);
		
		//根据不同的状态画不同的照片
		switch (state) {
		case START:
			g.drawImage(start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(gameover,0,0,null);
			break;
		case VICTORY:
			g.drawImage(victory,0,0,null);
			break;
		
		}
	}
	
	
	//处理飞行物越界问题
	public void flyingobjectoutaction(){
		//1.敌机越界
		int flyingobjectindex=0;
		FlyingObject[] flyinglive=new FlyingObject[enemies.length];
		for(int i=0;i<enemies.length;i++){//遍历所有敌人（越界和不越界）
			FlyingObject f=enemies[i];
			if(!f.flyingobjectout()&&!f.isremove()){//不越界并没有被移除得敌机
				flyinglive[flyingobjectindex++]=f;
			}
		}
		enemies=Arrays.copyOf(flyinglive,flyingobjectindex );
		
		
		//2.子弹越界
		int buttleindex=0;
		Bullet[] bulletlive=new Bullet[bullets.length];
		for(int i=0;i<bullets.length;i++){//遍历所有子弹（越界与不越界）
			Bullet b=bullets[i];
			if(!b.flyingobjectout()&&!b.isremove()){//（不越界并没有被移除得子弹）
				bulletlive[buttleindex++]=b;
			}
		}
		bullets=Arrays.copyOf(bulletlive,buttleindex);
		
	}
	
	//boss子弹越界问题
	private void bossbulletout(){
		int leftbossbulletindex=0;//左子弹越界问题
		Bossbullet[] leftbossbulletlive=new Bossbullet[leftbossbullet.length];
		for(int i=0;i<leftbossbullet.length;i++){
			Bossbullet m=leftbossbullet[i];
			if(!m.flyingobjectout()&&!m.isremove()){
				leftbossbulletlive[leftbossbulletindex++]=m;
			}
		}
		leftbossbullet=Arrays.copyOf(leftbossbulletlive,leftbossbulletindex);
		
		int bossbulletindex=0;//中间子弹越界问题
		Bossbullet[] bossbulletlive=new Bossbullet[bossbullet.length];
		for(int i=0;i<bossbullet.length;i++){
			Bossbullet m=bossbullet[i];
			if(!m.flyingobjectout()&&!m.isremove()){
				bossbulletlive[bossbulletindex++]=m;
			}
		}
		bossbullet=Arrays.copyOf(bossbulletlive,bossbulletindex);
		
		int rightbossbulletindex=0;//右子弹越界问题
		Bossbullet[] rightbossbulletlive=new Bossbullet[rightbossbullet.length];
		for(int i=0;i<rightbossbullet.length;i++){
			Bossbullet m=rightbossbullet[i];
			if(!m.flyingobjectout()&&!m.isremove()){
				rightbossbulletlive[rightbossbulletindex++]=m;
			}
		}
		rightbossbullet=Arrays.copyOf(rightbossbulletlive,rightbossbulletindex);
		
	}
	
	
	//左边子弹入场
	private int liftbossbulletindex=0;
	public void leftbossbulletaction(){
		liftbossbulletindex++;
		if(liftbossbulletindex%70==0){
			leftbossbullet=Arrays.copyOf(leftbossbullet,leftbossbullet.length+1);
			Bossbullet p=boss.bossleftshoot();
			leftbossbullet[leftbossbullet.length-1]=p;
		}
	}
	
	
	//中间子弹入场
	private int bossbulletindex=0;
	public void bossbulletaction(){
		bossbulletindex++;
		if(bossbulletindex%70==0){
			bossbullet=Arrays.copyOf(bossbullet,bossbullet.length+1);
			Bossbullet m=boss.bossshoot();
			bossbullet[bossbullet.length-1]=m;
		}
	}
	
	//右边子弹入场
	private int rightbossbulletindex=0;
	public void rightbossbulletaction(){
		rightbossbulletindex++;
		if(rightbossbulletindex%70==0){
			rightbossbullet=Arrays.copyOf(rightbossbullet,rightbossbullet.length+1);
			Bossbullet k=boss.bossrightshoot();
			rightbossbullet[rightbossbullet.length-1]=k;
		}
	}
	public static void main(String[] args) {
		//创建窗口（相框）
		JFrame rame=new JFrame();
		//创建面板
		World world=new World();
		//将面板添加到窗口中
		rame.add(world);
		//设置窗口大小
		rame.setSize(WIDTH, HEIGHT);
		//1.设置窗口可见  2.尽快的去调用paint（）方法
		rame.setVisible(true);
		//设置关闭窗口也就是关闭程序并退出
		rame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置窗口居中显示
		rame.setLocationRelativeTo(null);
		
		//启动程序的执行
		world.action();
		//音乐播放
		while(true){
			Music m=new Music();
		}
	}
}
