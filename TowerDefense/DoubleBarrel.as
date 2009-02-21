package
{
	public class DoubleBarrel extends Entity
	{
		// get all necessary images
		[Embed(source='base.png')]
		private var base:Class;
		[Embed(source='gun.png')]
		private var gun0:Class;
		[Embed(source='gun1.png')]
		private var gun1:Class;
		[Embed(source='gun1b.png')]
		private var gun1b:Class;
		[Embed(source='gun1c.png')]
		private var gun1c:Class;
		[Embed(source='gun2.png')]
		private var gun2:Class;
		[Embed(source='gun3.png')]
		private var gun3:Class;
		[Embed(source='gun4.png')]
		private var gun4:Class;
		[Embed(source='gun5.png')]
		private var gun5:Class;
		private var manned:Boolean;
		private var shoot:Boolean = false;
		private var angle=0;

		private var frame:int=0;
		private var gunimg:Image = new Image(new gun0(),0,0,20,20);
		private var baseimg:Image = new Image(new base(),0,0,20,20);
		private var otherEnt:Array;
		public function DoubleBarrel(x:int, y:int, others:Array)
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
						if(shoot==false){
						shoot=true;
						creep.damage(3);
						}
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
				gunimg.changeImage(new gun1());
				if(rand>0.70)
				gunimg.changeImage(new gun1b());
				else
				gunimg.changeImage(new gun1c());
				frame++;
			}else{
				if(frame==1){
					gunimg.changeImage(new gun2());
					frame++;
				}else{
						gunimg.changeImage(new gun0());
						frame=0;
						shoot=false;
				}
			}
			
		}

	}
}