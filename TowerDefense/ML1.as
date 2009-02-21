package
{
	public class ML1 extends Entity
	{
		// get all necessary images
		[Embed(source='base.png')]
		private var base:Class;
		[Embed(source='ML1.png')]
		private var gun0:Class;
		[Embed(source='ML1-1.png')]
		private var gun1:Class;
		[Embed(source='ML1-2.png')]
		private var gun2:Class;
		[Embed(source='ML1-3.png')]
		private var gun3:Class;
		[Embed(source='ML1-4.png')]
		private var gun4:Class;
		private var manned:Boolean;
		private var shoot:Boolean = false;
		private var angle=0;
		private var generatedChildren = false;
		private var frame:int=0;
		private var gunimg:Image = new Image(new gun0(),0,0,20,20);
		private var baseimg:Image = new Image(new base(),0,0,20,20);
		private var otherEnt:Array;
		public function ML1(x:int, y:int, others:Array)
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
		
		// overridden new children
		public override function newChildren():Boolean{
			if(generatedChildren){
				generatedChildren = false;
				return true;
			}
			return false;
		}
		
		public override function getChildren():Array{
			var temp:Array = new Array();
			for(var i:int;i<4;i++){
				var spread:Number=(i-2)/4;
				temp.push(new MS1(x,y,(int)(4*Math.cos(angle+spread)),(int)(4*Math.sin(angle+spread)),otherEnt));	
			}
			return temp;	
		}
		// overridden step
		public override function step():void{
			for(var i:int=0;i<otherEnt.length;i++){
				var temp:Entity = (Entity)(otherEnt[i]);
				//shoot creep
				if(temp.getTypeID()==2){
					var creep:Creep = (Creep)(temp);
					if(Math.sqrt(Math.pow(temp.getX()-super.getX(),2)+Math.pow(temp.getY()-super.getY(),2))<100&&creep.getLife()>=0){
						if(frame>10)
						angle = Math.atan2(temp.getY()-super.getY(),temp.getX()-super.getX());
						gunimg.setAngle(angle);
						if(shoot==false){
							shoot=true;
							generatedChildren = true;
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
			if(shoot==true){
				if(frame==0){
					gunimg.changeImage(new gun1());
					frame++;
				}else{
					if(frame==1){
						gunimg.changeImage(new gun2());
						frame++;
					}else{
						if(frame==2){
							gunimg.changeImage(new gun3());
							frame++;
						}
						else{
							if(frame==3){
								gunimg.changeImage(new gun4());
								frame++;
							}
							else{
								if(frame<30){
									gunimg.changeImage(new gun0());
									frame++;
								}
								else{										
									frame=0;
									shoot=false;	
								}
							}
						}
					}
				}
			}
			
		}

	}
}