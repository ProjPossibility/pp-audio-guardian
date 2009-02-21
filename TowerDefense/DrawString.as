package
{
	import flash.display.Sprite;
	import flash.text.*;
	public class DrawString extends Sprite
	{	
		public function DrawString(x:int, y:int, width:int, height:int, text:String)
		{
							var field:TextField = new TextField();
							field.x = x;
							field.y = y;
							field.width = width;
							field.height = height;
							field.text = text;
							addChild(field);	
		}

	}
}