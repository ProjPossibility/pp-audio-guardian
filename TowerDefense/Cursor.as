package
{
	import flash.display.DisplayObject;
	
	public class Cursor extends Entity
	{
		[Embed(source='arrow.png')]
		private var crosshair:Class;
		private var cursorimg:Image = new Image(new crosshair(),0,0,10,10);
		private var display:DisplayObject;
		public function Cursor(x:int,y:int,d:DisplayObject)
		{
			var imgarray:Array = new Array();
			imgarray.push(cursorimg);
			super(imgarray,x,y,99);
			display = d;
		}
		public override function step():void{
			super.x = display.mouseX;
			super.y = display.mouseY;
			cursorimg.setX(display.mouseX);
			cursorimg.setY(display.mouseY);
		}
	}
}