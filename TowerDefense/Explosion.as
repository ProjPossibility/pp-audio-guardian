package
{
	public class Explosion extends Entity
	{
		[Embed(source='explosion1.png')]
		private var exp1img:Class;
		[Embed(source='explosion2.png')]
		private var exp2img:Class;
		[Embed(source='explosion3.png')]
		private var exp3img:Class;
		[Embed(source='explosion4.png')]
		private var exp4img:Class;
		[Embed(source='explosion5.png')]
		private var exp5img:Class;
		[Embed(source='explosion6.png')]
		private var exp6img:Class;
		
		private var frame:int=0;
		private var	img:Image = new Image(new exp1img(),0,0,20,20);
		public function Explosion(x:int, y:int)
		{
			var imgarray:Array= new Array();
			imgarray.push(img);
			super(imgarray,x,y,98);
			img.setX(super.getX());
			img.setY(super.getY());
			img.decreaseAlpha(0.5);
		}
		public override function step():void{
			if(frame==1)
				img.changeImage(new exp2img());
			else{
				if(frame==2)
					img.changeImage(new exp3img());
				else{
					if(frame==3)
						img.changeImage(new exp4img());
					else{
						if(frame==4)
							img.changeImage(new exp5img());
						else{
							if(frame==5)
								img.changeImage(new exp6img());
							else{
								if(frame>15)
									killMe();
							}
						}
					}
				}
			}
			frame++;
			img.decreaseAlpha(0.05);
		}
	}
}