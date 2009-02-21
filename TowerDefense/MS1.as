package
{
	import flash.display.DisplayObject;
	
	public class MS1 extends Entity
	{
		// get all necessary images
		[Embed(source='m1.png')]
		private var ms1:Class;
		[Embed(source='m1-2.png')]
		private var ms1b:Class;
		private var shoot:Boolean = false;
		private var angle:Number=0;
		private var frame:int=0;
		private var ms1img:Image = new Image(new ms1(),0,0,20,20);
		private var otherEnt:Array;
		private var vx:Number=0;
		private var vy:Number=0;
		private var countdown:int=20;
		private var life:int = 0;
		private var speed:Number = 10;
		private var explode:Boolean=false;
		public function MS1(x:int, y:int, vx:Number, vy:Number, others:Array)
		{
			ms1img.setX(x);
			ms1img.setY(y);	
			var imgs:Array = new Array();
			imgs.push(ms1img);
			super(imgs,x,y,3);
			otherEnt = others;
			this.vx = vx;
			this.vy = vy;
			angle = Math.atan2(vy,vx);
			ms1img.setAngle(angle);
		}
		public override function newChildren():Boolean{
			if(explode){
				explode = false;
				return true;
			}
			return false;
		}
		public override function getChildren():Array{
			var temp:Array = new Array();
			temp.push(new Explosion(x,y));
			return temp;
		}
		public override function step():void{
			life++;
			if(life>40){
				explode=true;
				killMe();
			}
			if(countdown==0){
				speed-=0.1
				var mindistance:Number=-1;
				var targetindex:int=-1;
				for(var i:int=0;i<otherEnt.length;i++){
					var temp:Entity = (Entity)(otherEnt[i]);
					//shoot creep
					if(temp.getTypeID()==2){
						var creep:Creep = (Creep)(temp);
						var distance = Math.sqrt(Math.pow(temp.getX()-super.getX(),2)+Math.pow(temp.getY()-super.getY(),2));
						if(distance<200&&creep.getLife()>=0&&(distance<mindistance||mindistance<0)){
							mindistance = distance;
							targetindex = i;
						}
					}
				}
				if(targetindex!=-1){
					var creep:Creep = (Creep)(otherEnt[targetindex]);
					var h_angle:Number = Math.atan2(creep.getY()-y,creep.getX()-x)+Math.random()/4;
					ms1img.setAngle(h_angle);
					vx = speed*Math.cos(h_angle);
					vy = speed*Math.sin(h_angle);
					if(mindistance<10){
						explode = true;
						killMe();
						creep.damage(12);
					}	
				}
					ms1img.changeImage(new ms1b());


			}
			else{
				countdown--;
			}
			x+=vx;
			y+=vy;
			//reset x,y
			ms1img.setX(super.getX());
			ms1img.setY(super.getY());
		}

	}
}