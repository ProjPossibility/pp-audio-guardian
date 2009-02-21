package
{
	public class BlackHoleTurret extends Entity //This turret slows the Creep by 20%
	{
		// get all necessary images
		[Embed(source='base.png')]
		private var base:Class;
		[Embed(source='gsc.png')]
		private var gun0:Class;
		[Embed(source='gsc0.png')]
		private var gun1:Class;
		[Embed(source='gsc1.png')]
		private var gun2:Class;
		[Embed(source='gsc2.png')]
		private var gun3:Class;
		[Embed(source='gsc3.png')]
		private var gun4:Class;
		private var manned:Boolean;
		private var shoot:Boolean = false;
		private var angle:int=0;

		private var frame:int=0;
		private var gunimg:Image = new Image(new gun0(),0,0,17,19);
		private var baseimg:Image = new Image(new base(),0,0,20,20);
		private var otherEnt:Array;
		
		public function BlackHoleTurret(x:int, y:int, others:Array)
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
		
		public override function step():void
		{
			for(var i:int=0;i<otherEnt.length;i++)
			{
				var temp:Entity = (Entity)(otherEnt[i]);
				//shoot creep
				if(temp.getTypeID()==2)
				{
					var creep:Creep = (Creep)(temp);
					if(Math.sqrt(Math.pow(temp.getX()-super.getX(),2)+Math.pow(temp.getY()-super.getY(),2))<60&&creep.getLife()>=0)
					{
						gunimg.setAngle(Math.atan2(temp.getY()-super.getY(),temp.getX()-super.getX()));
						//if(shoot==false)
						//{
							if( ! creep.isSlowed() )
							{
								shoot=true;
								creep.slowDown(creep.getSpeed() * .80);
							}
						//}
					}
				}
			}
			
			//reset x,y
			baseimg.setX(super.getX());
			baseimg.setY(super.getY());
			gunimg.setX(super.getX());
			gunimg.setY(super.getY());
			
			//animate image
			if(shoot==true)
			{
				if(frame==0)
				{
					gunimg.changeImage(new gun1());
					frame++;
				}
				else
				{
					if(frame==1)
					{
						gunimg.changeImage(new gun2());
						frame++;
					}
					else
					{
						if(frame==2)
						{
							gunimg.changeImage(new gun3());
							frame++;
						}
						else
						{
							if(frame==3)
							{
								gunimg.changeImage(new gun4());
								frame++;
							}
							else
							{
								gunimg.changeImage(new gun0());
								if(frame>10)
								{
									frame=0;
									shoot=false;
								}
								else
								{
									frame++;
								}
							}
						}
					}
				}
			}	
		}
	}
}