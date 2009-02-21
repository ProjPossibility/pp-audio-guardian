package
{
	public class Turret extends Entity
	{
		private var manned:Boolean;
		private var shoot:Boolean = false;
		private var angle:Number=0;
		[Embed(source='base.png')]
		private var base:Class;
		private var baseimg:Image = new Image(new base(),0,0,20,20);
		[Embed(source='cg1.png')]
		private var cg1:Class;
		[Embed(source='cg2.png')]
		private var cg2:Class;		
		[Embed(source='cg3.png')]
		private var cg3:Class;
		[Embed(source='cg4.png')]
		private var cg4:Class;
		
		private var frame:int=0;
		private var gunimg:Image = new Image(new cg1(),0,0,12,20);
		private var otherEnt:Array;
		public function Turret(x:int, y:int, others:Array)
		{
			baseimg.setX(x);
			baseimg.setY(y);
			gunimg.setX(x);
			gunimg.setY(y);		
			var imgs:Array = new Array();
			imgs.push(baseimg);
			imgs.push(gunimg);
			super(imgs,x,y,1);
			manned = true;
			otherEnt = others;
		}
		public override function step():void{
			for(var i:int=0;i<otherEnt.length;i++){
				var temp:Entity = (Entity)(otherEnt[i]);
				//shoot creep
				if(temp.getTypeID()==2){
					var creep:Creep = (Creep)(temp);
					if(Math.sqrt(Math.pow(temp.getX()-super.getX(),2)+Math.pow(temp.getY()-super.getY(),2))<100&&creep.getLife()>=0){
						gunimg.setAngle(Math.atan2(temp.getY()-super.getY(),temp.getX()-super.getX()));
						shoot=true;
						creep.damage(10);
						break;
					}
				}
			}
			
			//reset x,y
			baseimg.setX(super.getX());
			baseimg.setY(super.getY());
			gunimg.setX(super.getX());
			gunimg.setY(super.getY());
			
			//animate image
			if(frame==0&&shoot==true){
				var rand = Math.random();
				if(rand<0.30)
				gunimg.changeImage(new cg2());
				if(rand>0.70)
				gunimg.changeImage(new cg3());
				else
				gunimg.changeImage(new cg2());
				
				
				frame++;
			}else{
				if(frame==1){
					gunimg.changeImage(new cg4());
					frame++;
				}else{
						gunimg.changeImage(new cg1());
						frame=0;
						shoot=false;
				}
			}
			
		}

	}
}