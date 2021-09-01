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
	 * Jpanel��Javaͼ���û����棨GUI�����߰�swing�е���������࣬
	 * ������javax.swing���У���һ������������,
	 * ���Լ��뵽JFrame�����
	 */
	public static final int WIDTH=450;//���ڵĿ�
	public static final int HEIGHT=650;//���ڵĸߡ�
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
	public int state=START;//Ĭ��״̬
	
	private Boss boss=new Boss();
	private Bossbullet[] leftbossbullet={};
	private Bossbullet[] bossbullet={};
	private Bossbullet[] rightbossbullet={};
	
	private long timeindex=0;
	private long time=0;
	
	static{//ͼƬ��ʼ��;
		start=FlyingObject.loadImage("start.png");
		pause=FlyingObject.loadImage("pause.png");
		gameover=FlyingObject.loadImage("gameover.png");
		victory=FlyingObject.loadImage("victory.png");
	}
	//���Է���
	//public void action(){
//		FlyingObject[] fly={new Airplane(),new BigAirplane(),new Helicopter()};
//		for(int i=0;i<fly.length;i++){
//			fly[i].step();
//		}
//		
//		FlyingObject f=new Airplane();
//		f.getImage();
//	}
	//���ɵл�
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
	//�����볡
	private int enemiesindex=0;
	private void enemiesAction(){
		enemiesindex++;
		if(enemiesindex%40==0){//ÿ��400ms����һ������
			//�Ե�������enemies������
			enemies=Arrays.copyOf(enemies, enemies.length+1);
			FlyingObject f=nextone();//����һ�����˶���
			enemies[enemies.length-1]=f;
			//System.out.println(enemies.length);
		}
	}
	
	
	//�ӵ��볡
	private int index=0;
	private void bulletAction(){
		index++;
		if(index%30==0){//ÿ��300ms���ɶ���
			Bullet[] bs=hero.heroshoot();
			//�������������
			bullets=Arrays.copyOf(bullets, bullets.length+bs.length);
			//����ĸ��ƣ������ɵ��ӵ�ײ��������bullets�У�
			System.arraycopy(bs,0, bullets, bullets.length-bs.length, bs.length);
			//System.out.println(bullets.length);
		}
	}
	
	//Ӣ�ۻ����ӵ���ײ
	private void bulletbangAction(){
		//���������ӵ�
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			//�������ел�
			for(int j=0;j<enemies.length;j++){
				FlyingObject f=enemies[j];
				//�ж�ÿ���ӵ��Ƿ������ǵõл���ײ
				if(b.islife()&&f.islife()&&f.hit(b)){
					b.godead();//�ı��ӵ�����������״̬
					f.godead();//�ı�л�����������״̬
					if(f instanceof Award){
						Award a=(Award)f;
						int type=a.getaward();
						switch (type) {
						case Award.LIFE:
							//Ӣ�ۼ�����
							hero.addlife();
							break;

						case Award.FIRE://��������ֵ
							hero.adddoublefire();
							break;
						}
					}
					if(f instanceof Score){
						Score s=(Score)f;
						score+=s.getScore();//�÷�
					}
				}
			}
		}
	}
	
	//Ӣ�ۻ��ӵ���boss��ײ
	private void herobulletboss(){
		//����Ӣ�ۻ��ӵ�
		for(int i=0;i<bullets.length;i++){
			Bullet f=bullets[i];
			if(boss.islife()&&f.islife()&&f.hit(boss)){
				f.godead();//Ӣ�ۻ��ӵ�ȥ��
				boss.subbosslife();//boss����
				if(boss.getbosslife()<=0){
					boss.godead();//bossȥ��
					state=GAME_OVER;//��Ϸ����
				}
			}
		}
	}
	
	
	
	//Ӣ�ۻ��ӵ���boss���ӵ���ײ
	private void herobulletbossbulletleft(){
		//��������Ӣ�ۻ��ӵ�
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			//��������boss���ӵ�
			for(int j=0;j<leftbossbullet.length;j++){
				Bossbullet f=leftbossbullet[j];
				if(b.islife()&&f.islife()&&b.hit(f)){
					f.godead();//Ӣ�ۻ��ӵ�ȥ��
					b.godead();//boss���ӵ�ȥ��
					hero.addlife();//Ӣ�ۻ�����
				}
			}
		}
	}
	
	//Ӣ�ۻ��ӵ���boss�м��ӵ���ײ
	private void herobulletbossbullet(){
		//��������Ӣ�ۻ��ӵ�
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			//��������boss�м��ӵ�
			for(int j=0;j<bossbullet.length;j++){
				Bossbullet f=bossbullet[j];
				if(b.islife()&&f.islife()&&b.hit(f)){
					f.godead();//Ӣ�ۻ��ӵ�ȥ��
					b.godead();//boss�м��ӵ�ȥ��
					hero.addlife();//Ӣ�ۻ�����
				}
			}
		}
	}
	
	//Ӣ�ۻ��ӵ���boss���ӵ���ײ
	private void herobulletbossbulletright(){
		//��������Ӣ�ۻ��ӵ�
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			//��������boss���ӵ�
			for(int j=0;j<rightbossbullet.length;j++){
				Bossbullet f=rightbossbullet[j];
				if(b.islife()&&f.islife()&&b.hit(f)){
					f.godead();//Ӣ�ۻ��ӵ�ȥ��
					b.godead();//boss���ӵ�ȥ��
					hero.addlife();//Ӣ�ۻ�����
				}
			}
		}
	}
	
	//Ӣ�ۻ��͵л���ײ
	private void herobangaction(){
		//�������ел�
		for(int i=0;i<enemies.length;i++){
			FlyingObject f=enemies[i];
			if(hero.islife()&&f.islife()&&f.hit(hero)){
				f.godead();//�ı�л�״̬Ϊ��
				//Ӣ�ۻ�����
				hero.sublife();
				System.out.println(hero.getlife());
				//Ӣ�ۻ��������
				hero.cleaedoublefire();
			}
		}
	}
	
	//Ӣ�ۻ���boss��ײ
	private void herobangboss(){
		if(hero.hit(boss)){
			hero.sublife();//Ӣ�ۻ�����
			hero.cleaedoublefire();//Ӣ�ۻ��������
		}
	}
	
	//Ӣ�ۻ���boss���ӵ���ײ
	private void herobossbulletleft(){
		//�����������ӵ�
		for(int i=0;i<leftbossbullet.length;i++){
			Bossbullet p=leftbossbullet[i];
			if(hero.islife()&&p.islife()&&hero.hit(p)){
				p.godead();//boss�ӵ�ȥ��
				hero.cleaedoublefire();//Ӣ�ۻ�����ֵ���
				hero.sublife();//Ӣ�ۻ�����
			}
		}
	}
	
	
	//Ӣ�ۻ���boss�м��ӵ���ײ
	private void herobossbullet(){
		//�����������ӵ�
		for(int i=0;i<bossbullet.length;i++){
			Bossbullet p=bossbullet[i];
			if(hero.islife()&&p.islife()&&hero.hit(p)){
				p.godead();//boss�м��ӵ�ȥ��
				hero.cleaedoublefire();//Ӣ�ۻ�����ֵ���
				hero.sublife();//Ӣ�ۻ�����
			}
		}
	}
	
	//Ӣ�ۻ���boss���ӵ���ײ
	private void herobossbulletright(){
		//�����������ӵ�
		for(int i=0;i<rightbossbullet.length;i++){
			Bossbullet p=rightbossbullet[i];
			if(hero.islife()&&p.islife()&&hero.hit(p)){
				p.godead();//boss���ӵ�ȥ��
				hero.cleaedoublefire();//Ӣ�ۻ�����ֵ���
				hero.sublife();//Ӣ�ۻ�����
			}
		}
	}	
	
	
	
	//�ж�Ӣ�ۻ��Ƿ�����
	public void checkhgameoveraction(){
		if(hero.getlife()<=0){
			state=GAME_OVER;
		}
	}
	
	
	public void action(){
		//��ȡ������
		MouseAdapter m=new MouseAdapter() {
			//����ƶ��¼�
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING){
					//�ƶ����x�ƶ�
					//�ƶ����x�ƶ�
					int x=e.getX();
					int y=e.getY();
					hero.herostep(x, y);
					//System.out.println("x"+x+",y"+y);
				}
			}
			//������¼�
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
			 //����Ƴ�״̬
			 public void mouseExited(MouseEvent e) {
				 if(state==RUNNING){
					 state=PAUSE;
				 }
			 }
			 //�������״̬
			 public void mouseEntered(MouseEvent e) {
				 if(state==PAUSE){
					 state=RUNNING;
				 }
			 }
		};
		//�������¼�
		this.addMouseListener(m);
		//��껬���¼�
		this.addMouseMotionListener(m);
		
		
		//������ʱ��
		Timer timer=new Timer();
		//timer.schedule(ִ������ʱ��)
		int interval=10;
		timer.schedule(new TimerTask() {
			//��дtimerTask�������е�run����������
			public void run() {
				if(state==RUNNING){
					stepaction();//�������ƶ�
					enemiesAction();//�����볡
					bulletAction();//�ӵ��볡
					bulletbangAction();//Ӣ�ۻ��ӵ��͵л���ײ
					herobangaction();//Ӣ�ۼ���л���ײ
					checkhgameoveraction();//Ӣ�ۻ���������Ϸ����
					flyingobjectoutaction();//������Խ�磨�ڴ�й©���⣩
					
					
					if(time>=5){//5s֮��boss����
						leftbossbulletaction();//����ӵ��볡
						bossbulletaction();//�м��ӵ��볡
						rightbossbulletaction();//�ұ��ӵ��볡
						//Ӣ�ۻ��ӵ���boss��ײ
						herobulletboss();
						//Ӣ�ۻ��ӵ���boss�ӵ���ײ
						herobulletbossbulletleft();//boss���ӵ�
						herobulletbossbullet();//boss�м��ӵ�
						herobulletbossbulletright();//boss���ӵ�
						//Ӣ�ۻ���boos��ײ
						herobangboss();
						//Ӣ�ۻ���boss�ӵ���ײ
						herobossbulletleft();//���ӵ�
						herobossbullet();//���ӵ�
						herobossbulletright();//���ӵ�
						//boss�ӵ�Խ�����⴦��
						bossbulletout();
					}
					
					if(timeindex++%100==0){
						time+=1;
					}
					
				}
				repaint();//repaint��jpanel�е÷����������ػ�����д����paint������
				
			}

		}, interval,interval);//��һ��interval��10ms��ִ�����񣬵ڶ���interval��ÿ��10msִ��һ��
	}
	
	//�������ƶ�
	private void stepaction(){
		sky.step();//�ƶ�����
		//�����л����ƶ���Ϊ
		for(int i=0;i<enemies.length;i++){
			enemies[i].step();
		}
		//����ÿ���ӵ����ƶ���Ϊ
		for(int i=0;i<bullets.length;i++){
			bullets[i].step();
		}
		
		if(time>=5){
			boss.step();//boss�ƶ�
			
			//����boss���ӵ��ƶ�
			for(int i=0;i<leftbossbullet.length;i++){
				leftbossbullet[i].leftstep();
			}
			
			
			//����boss�м��ӵ��ƶ�
			for(int i=0;i<bossbullet.length;i++){
				bossbullet[i].step();
			}
			
			
			//����boss���ӵ��ƶ�
			for(int i=0;i<rightbossbullet.length;i++){
				rightbossbullet[i].rightstep();
			}
		}
		
	}
	
	public void paint(Graphics g){
		sky.paintobject(g);//�����
		
		//�����л����ƶ���Ϊ������
		for(int i=0;i<enemies.length;i++){
			enemies[i].paintobject(g);
			}
		//����ÿ���ӵ����ƶ���Ϊ������
		for(int i=0;i<bullets.length;i++){
			bullets[i].paintobject(g);
		}
		hero.paintobject(g);//��Ӣ�ۻ�
		
		if(time>=5){
			boss.paintobject(g);//��boss
			
			//��boss���ӵ�
			for(int i=0;i<leftbossbullet.length;i++){
				leftbossbullet[i].paintobject(g);
			}
			
			//��boss�м��ӵ�
			for(int i=0;i<bossbullet.length;i++){
				bossbullet[i].paintobject(g);
			}
			
			//��boss���ӵ�
			for(int i=0;i<rightbossbullet.length;i++){
				rightbossbullet[i].paintobject(g);
			}
			
		}
		

		//���������С����ʽ
		Font font=new Font("����",Font.TYPE1_FONT,25);
		g.setFont(font);
		//���û��ʵ���ɫ
		g.setColor(Color.gray);
		//����x��y���껰�ַ�
		g.drawString("LIFE "+hero.getlife(), 15, 30);
		g.drawString("FIRE "+hero.doublefire(), 15, 55);
		g.drawString("SCORE "+score, 15, 80);
		g.drawString("BOSSLIFE "+boss.getbosslife(), 15, 105);
		
		//���ݲ�ͬ��״̬����ͬ����Ƭ
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
	
	
	//���������Խ������
	public void flyingobjectoutaction(){
		//1.�л�Խ��
		int flyingobjectindex=0;
		FlyingObject[] flyinglive=new FlyingObject[enemies.length];
		for(int i=0;i<enemies.length;i++){//�������е��ˣ�Խ��Ͳ�Խ�磩
			FlyingObject f=enemies[i];
			if(!f.flyingobjectout()&&!f.isremove()){//��Խ�粢û�б��Ƴ��õл�
				flyinglive[flyingobjectindex++]=f;
			}
		}
		enemies=Arrays.copyOf(flyinglive,flyingobjectindex );
		
		
		//2.�ӵ�Խ��
		int buttleindex=0;
		Bullet[] bulletlive=new Bullet[bullets.length];
		for(int i=0;i<bullets.length;i++){//���������ӵ���Խ���벻Խ�磩
			Bullet b=bullets[i];
			if(!b.flyingobjectout()&&!b.isremove()){//����Խ�粢û�б��Ƴ����ӵ���
				bulletlive[buttleindex++]=b;
			}
		}
		bullets=Arrays.copyOf(bulletlive,buttleindex);
		
	}
	
	//boss�ӵ�Խ������
	private void bossbulletout(){
		int leftbossbulletindex=0;//���ӵ�Խ������
		Bossbullet[] leftbossbulletlive=new Bossbullet[leftbossbullet.length];
		for(int i=0;i<leftbossbullet.length;i++){
			Bossbullet m=leftbossbullet[i];
			if(!m.flyingobjectout()&&!m.isremove()){
				leftbossbulletlive[leftbossbulletindex++]=m;
			}
		}
		leftbossbullet=Arrays.copyOf(leftbossbulletlive,leftbossbulletindex);
		
		int bossbulletindex=0;//�м��ӵ�Խ������
		Bossbullet[] bossbulletlive=new Bossbullet[bossbullet.length];
		for(int i=0;i<bossbullet.length;i++){
			Bossbullet m=bossbullet[i];
			if(!m.flyingobjectout()&&!m.isremove()){
				bossbulletlive[bossbulletindex++]=m;
			}
		}
		bossbullet=Arrays.copyOf(bossbulletlive,bossbulletindex);
		
		int rightbossbulletindex=0;//���ӵ�Խ������
		Bossbullet[] rightbossbulletlive=new Bossbullet[rightbossbullet.length];
		for(int i=0;i<rightbossbullet.length;i++){
			Bossbullet m=rightbossbullet[i];
			if(!m.flyingobjectout()&&!m.isremove()){
				rightbossbulletlive[rightbossbulletindex++]=m;
			}
		}
		rightbossbullet=Arrays.copyOf(rightbossbulletlive,rightbossbulletindex);
		
	}
	
	
	//����ӵ��볡
	private int liftbossbulletindex=0;
	public void leftbossbulletaction(){
		liftbossbulletindex++;
		if(liftbossbulletindex%70==0){
			leftbossbullet=Arrays.copyOf(leftbossbullet,leftbossbullet.length+1);
			Bossbullet p=boss.bossleftshoot();
			leftbossbullet[leftbossbullet.length-1]=p;
		}
	}
	
	
	//�м��ӵ��볡
	private int bossbulletindex=0;
	public void bossbulletaction(){
		bossbulletindex++;
		if(bossbulletindex%70==0){
			bossbullet=Arrays.copyOf(bossbullet,bossbullet.length+1);
			Bossbullet m=boss.bossshoot();
			bossbullet[bossbullet.length-1]=m;
		}
	}
	
	//�ұ��ӵ��볡
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
		//�������ڣ����
		JFrame rame=new JFrame();
		//�������
		World world=new World();
		//�������ӵ�������
		rame.add(world);
		//���ô��ڴ�С
		rame.setSize(WIDTH, HEIGHT);
		//1.���ô��ڿɼ�  2.�����ȥ����paint��������
		rame.setVisible(true);
		//���ùرմ���Ҳ���ǹرճ����˳�
		rame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//���ô��ھ�����ʾ
		rame.setLocationRelativeTo(null);
		
		//���������ִ��
		world.action();
		//���ֲ���
		while(true){
			Music m=new Music();
		}
	}
}
